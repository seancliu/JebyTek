<template>
  <div>
    <button type="button" v-on:click="selectFile()" class="btn btn-white btn-default btn-round">
      <i class="ace-icon fa fa-upload"></i>
      {{text}}
    </button>
    <input class="hidden" type="file" ref="file" v-on:change="uploadFile()" v-bind:id="inputId+'-input'">
  </div>
</template>

<script>
  export default {
    name: 'big-file',
    props: {
        text: {
            default: "Upload Big File"
        },
        inputId: {
            default: "file-upload"
        },
        suffixs: {
            default: []
        },
        use: {
            default: ""
        },
        afterUpload: {
            type: Function,
            default: null
        }
    },
    data: function () {
      return {

      }
    },
    methods: {
        uploadFile() {
            let _this = this;
            let formData = new window.FormData();
            let file = _this.$refs.file.files[0];

            // 生成文件标识，标识多次上传的是不是同一个文件
            let key = hex_md5(file.name + file.size + file.type);
            let key10 = parseInt(key, 16);
            let key62 = Tool._10to62(key10);
            console.log(key, key10, key62);
            /*
              d41d8cd98f00b204e9800998ecf8427e
              2.8194976848941264e+38
              6sfSqfOwzmik4A4icMYuUe
             */

            // check file extension
            let suffixs = _this.suffixs;
            let fileName = file.name;
            let suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length).toLowerCase();
            let validateSuffix = false;
            for (let i = 0; i < suffixs.length; i++) {
                if (suffixs[i].toLowerCase() == suffix) {
                    validateSuffix = true;
                    break;
                }
            }
            if (!validateSuffix) {
                Toast.warning("File type error! Please select " + suffixs.join(", "));
                $("#" + _this.inputId + "-input").val("");
                return;
            }

            // sharding
            let shardSize = 5 * 1024 * 1024;    // 5MB per shard
            let shardIndex = 0;

            let size = file.size;
            let shardTotal = Math.ceil(size / shardSize);

            let param = {
                'shardIndex': shardIndex,
                'shardSize': shardSize,
                'shardTotal': shardTotal,
                'use': _this.use,
                'name': file.name,
                'suffix': suffix,
                'size': size,
                'key': key62
            }

            _this.upload(param)
        },

        check: function (param) {
            let _this = this;
            _this.$ajax.get(process.env.VUE_APP_SERVER + '/file/admin/check/' + param.key)
                .then((response)=>{
                    let resp = response.content;
                    if (resp.success) {
                        let obj = resp.content;
                        if (!obj) {
                            param.shardIndex = 0;
                            console.log("No related shards found. Upload from shard 0.");
                            _this.upload(param);
                        } else {
                            param.shardIndex = obj.shardIndex + 1;
                            console.log("Start uploading from shard " + param.shardIndex);
                            _this.upload(param);
                        }
                    } else {
                        Toast.warning("Upload failed");
                        $("#" + _this.inputId + "-input").val("");
                    }
                });
        },

        upload: function (param) {
            let _this = this;
            let shardIndex = param.shardIndex;
            let shardTotal = param.shardTotal;
            let shardSize = param.shardSize;
            let fileShard = _this.getFileShard(shardIndex, shardSize);

            let fileReader = new FileReader();
            fileReader.onload = function (e) {
                let base64 = e.target.result;
                param.shard = base64;

                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/upload', param).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    if (shardIndex < shardTotal - 1) {
                        // continue to upload next shard
                        param.shardIndex = param.shardIndex + 1;
                        _this.upload(param);
                    } else {
                        _this.afterUpload(resp);
                        $("#" + _this.inputId + "-input").val("");
                    }

                });
            };
            fileReader.readAsDataURL(fileShard);
        },

        getFileShard: function (shardIndex, shardSize) {
            let _this = this;
            let file = _this.$refs.file.files[0];
            let start = shardIndex * shardSize; // start index of current shard
            let end = Math.min(file.size, start + shardSize); // end index of current shard
            let fileShard = file.slice(start, end);
            return fileShard;
        },

        selectFile() {
            let _this = this;
            $("#" + _this.inputId + "-input").trigger("click");
        },

    }
  }
</script>