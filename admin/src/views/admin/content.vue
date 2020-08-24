<template>
  <div>
    <h4 class="lighter">
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/course" class="pink"> {{course.name}} </router-link>
    </h4>
    <hr>

    <file v-bind:input-id="'content-file-upload'"
          v-bind:text="'Upload File'"
          v-bind:suffixs="['jpg', 'jpeg', 'png', 'mp4']"
          v-bind:use="FILE_USE.COURSE.key"
          v-bind:after-upload="afterUploadContentFile"></file>
    <br>
    <table id="file-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>File Name</th>
        <th>URL</th>
        <th>Size</th>
        <th>Operation</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="(f, i) in files" v-bind:key="f.id">
        <td>{{f.name}}</td>
        <td>{{f.url}}</td>
        <td>{{f.size | formatFileSize}}</td>
        <td>
          <button v-on:click="delFile(f)" class="btn btn-white btn-xs btn-warning btn-round">
            <i class="ace-icon fa fa-times red2"></i>
            Delete
          </button>
        </td>
      </tr>
      </tbody>
    </table>
    <form class="form-horizontal">
      <div class="form-group">
        <div class="col-lg-12">
          {{saveContentLabel}}
        </div>
      </div>
      <div class="form-group">
        <div class="col-lg-12">
          <div id="content"></div>
        </div>
      </div>
    </form>
    <p>
      <button type="button" class="btn btn-white btn-info btn-round" v-on:click="saveContent()">
        <i class="ace-icon fa fa-plus blue"></i>
        Save
      </button>&nbsp;
      <router-link to="/business/course" type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
        <i class="ace-icon fa fa-times"></i>
        Back To Course
      </router-link>
    </p>
  </div>
</template>

<script>
    import File from "../../components/file";
    export default {
        components: {File},
        name: "business-course-content",
        data: function() {
            return {
                course: {},
                FILE_USE: FILE_USE,
                saveContentLabel: "",
                files: [],
                saveContentInterval: {},
            }
        },
        mounted: function() {
            let _this = this;

            let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
            if (Tool.isEmpty(course)) {
                _this.$router.push("/welcome");
            }
            _this.course = course;

            _this.init();

            // sidebar激活样式方法一
            this.$parent.activeSidebar("business-course-sidebar");

        },
        destroyed: function() {
            let _this = this;
            console.log("Component destroyed...");
            clearInterval(_this.saveContentInterval);
        },
        methods: {

            /**
             * initialize Summernote WYSIWYG editor
             */
            init() {
                let _this = this;
                let course = _this.course;
                let id = course.id;
                $("#content").summernote({
                    focus: true,
                    height: 300
                });

                // clear history
                $("#content").summernote('code', '');
                _this.saveContentLabel = "";

                // load content files
                _this.listContentFiles();

                Loading.show();
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/admin/course/search-content/' + id).then((response)=>{
                    Loading.hide();
                    let resp = response.data;

                    if (resp.success) {
                        if (resp.content) {
                            $("#content").summernote('code', resp.content.content);
                        }
                        // automatic save
                        _this.saveContentInterval = setInterval(function() {
                            _this.saveContent();
                        }, 5000);
                    } else {
                        Toast.warning(resp.message);
                    }
                });
            },

            /**
             * Save Content
             */
            saveContent () {
                let _this = this;
                let content = $("#content").summernote("code");
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/save-content', {
                    id: _this.course.id,
                    content: content
                }).then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    if (resp.success) {
                        let now = Tool.dateFormat("yyyy-MM-dd hh:mm:ss");
                        _this.saveContentLabel = "Saved at: " + now;
                    } else {
                        Toast.warning(resp.message);
                    }
                });
            },

            /**
             * retrieve content files
             */
            listContentFiles() {
                let _this = this;
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/admin/course-content-file/list/' + _this.course.id).then((response)=>{
                    let resp = response.data;
                    if (resp.success) {
                        _this.files = resp.content;
                    }
                });
            },

            /**
             * upload file, then save file record
             */
            afterUploadContentFile(response) {
                let _this = this;
                console.log("Start uploading file...");
                let file = response.content;
                file.courseId = _this.course.id;
                file.url = file.path;
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course-content-file/save', file).then((response)=>{
                    let resp = response.data;
                    if (resp.success) {
                        Toast.success("File uploaded!");
                        _this.files.push(resp.content);
                    }
                });

            },

            /**
             * delete content file
             */
            delFile(f) {
                let _this = this;
                Confirm.show("You won't be able to revert this!", function () {
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/course-content-file/delete/' + f.id).then((response)=>{
                        let resp = response.data;
                        if (resp.success) {
                            Toast.success("File deleted!");
                            Tool.removeObj(_this.files, f);
                        }
                    });
                });
            },
        }
    }
</script>