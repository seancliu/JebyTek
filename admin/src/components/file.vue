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
    name: 'file',
    props: {
        text: {
            default: "Upload File"
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

            // key ("file") must match the param in the controller on the backend
            formData.append('file', document.querySelector('#file-upload-input').files[0]);
            formData.append('use', _this.use);
            Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/upload', formData).then((response)=>{
                Loading.hide();
                let resp = response.data;
                let avatar = resp.content;
                _this.afterUpload(resp);
                $("#" + _this.inputId + "-input").val("");
            });
        },

        selectFile() {
            $("#file-upload-input").trigger("click");
        }
    }
  }
</script>