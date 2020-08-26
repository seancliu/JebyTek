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
            let key = hex_md5(file);
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
            let shardSize = 5 * 1024 * 1024;    // 20MB per shard
            let shardIndex = 0;
            let start = shardIndex * shardSize; // start index of current shard
            let end = Math.min(file.size, start + shardSize); // end index of current shard
            let fileShard = file.slice(start, end);

            let size = file.size;
            let shardTotal = Math.ceil(size / shardSize);

            // key ("shard") must match the param in the controller on the backend
            formData.append('shard', fileShard);
            formData.append('shardIndex', shardIndex);
            formData.append('shardSize', shardSize);
            formData.append('shardTotal', shardTotal);
            formData.append('use', _this.use);
            formData.append('name', file.name);
            formData.append('suffix', suffix);
            formData.append('size', size);
            formData.append('key', key62)
            Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/upload', formData).then((response)=>{
                Loading.hide();
                let resp = response.data;
                _this.afterUpload(resp);
                $("#" + _this.inputId + "-input").val("");
            });
        },

        selectFile() {
            let _this = this;
            $("#" + _this.inputId + "-input").trigger("click");
        }
    }
  }
</script>