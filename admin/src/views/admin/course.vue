<template>
  <div>
    <p>
      <button v-on:click="add()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-edit"></i>
        Add
      </button>
      &nbsp;
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        Refresh
      </button>
    </p>

    <pagination ref="pagination" v-bind:list="list"></pagination>

    <div class="row">
      <div v-for="course in courses" class="col-md-4">
        <div class="thumbnail search-thumbnail">
          <img v-show="!course.thumb" class="media-object" src="/static/image/default_thumb_640.jpg" />
          <img v-show="course.thumb" class="media-object" v-bind:src="course.thumb" />
          <div class="caption">
            <div class="clearfix">
              <span class="pull-right label label-primary info-label">{{COURSE_LEVEL | optionKV(course.level)}}</span>
              <span class="pull-right label label-primary info-label">{{COURSE_CHARGE | optionKV(course.charge)}}</span>
              <span class="pull-right label label-primary info-label">{{COURSE_STATUS | optionKV(course.status)}}</span>
            </div>

            <h3 class="search-title">
              <a href="#" class="blue">{{course.name}}</a>
            </h3>
            <div v-for="instructor in instructors.filter(inst=>{return inst.id===course.instructorId})" class="profile-activity clearfix">
              <div>
                <img v-show="!instructor.avatar" class="pull-left" src="/ace/assets/images/avatars/avatar5.png">
                <img v-show="instructor.avatar" class="pull-left" v-bind:src="instructor.avatar">
                <a class="user" href="#"> {{instructor.name}} </a>
                <br>
                {{instructor.title}}
              </div>
            </div>
            <p>
              <span class="blue bolder bigger-150"><i class="fa fa-usd"></i>&nbsp;{{course.price}}</span>
            </p>
            <p>{{course.description}}</p>
            <p>
              <span class="badge badge-info">{{course.id}}</span>
              <span class="badge badge-info">Index: {{course.idx}}</span>
              <span class="badge badge-info">{{course.time | formatSecond}}</span>
            </p>
            <p>
              <button v-on:click="toChapter(course)" class="btn btn-white btn-xs btn-info btn-round">
                Chapters
              </button>&nbsp;
              <button v-on:click="editContent(course)" class="btn btn-white btn-xs btn-info btn-round">
                Content
              </button>&nbsp;
              <button v-on:click="openIdxModal(course)" class="btn btn-white btn-xs btn-info btn-round">
                Index
              </button>&nbsp;
              <button v-on:click="edit(course)" class="btn btn-white btn-xs btn-info btn-round">
                Edit
              </button>&nbsp;
              <button v-on:click="del(course.id)" class="btn btn-white btn-xs btn-warning btn-round">
                Delete
              </button>
            </p>
          </div>
        </div>
      </div>
    </div>

    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Add a course</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
                <div class="form-group">
                  <label class="col-sm-2 control-label">
                    Category
                  </label>
                  <div class="col-sm-10">
                    <ul id="tree" class="ztree"></ul>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Thumb</label>
                  <div class="col-sm-10">
                    <file v-bind:input-id="'thumb-upload'"
                          v-bind:text="'Upload Thumbnail'"
                          v-bind:suffixs="['jpg', 'jpeg', 'png']"
                          v-bind:use="FILE_USE.COURSE.key"
                          v-bind:after-upload="afterUpload">
                    </file>
                    <div v-show="course.thumb" class="row">
                      <div class="col-md-6">
                        <img v-bind:src="course.thumb" class="img-responsive">
                      </div>
                    </div>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Name</label>
                  <div class="col-sm-10">
                    <input v-model="course.name" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Instructor</label>
                  <div class="col-sm-10">
                    <select v-model="course.instructorId" class="form-control">
                      <option v-for="o in instructors" v-bind:value="o.id">{{o.name}}</option>
                    </select>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Description</label>
                  <div class="col-sm-10">
                    <input v-model="course.description" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Time (in secs)</label>
                  <div class="col-sm-10">
                    <input v-model="course.time" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Price (USD)</label>
                  <div class="col-sm-10">
                    <input v-model="course.price" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">Level</label>
                    <div class="col-sm-10">
                      <select v-model="course.level" class="form-control">
                        <option v-for="o in COURSE_LEVEL" v-bind:value="o.key">{{o.value}}</option>
                      </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">Charge</label>
                    <div class="col-sm-10">
                      <select v-model="course.charge" class="form-control">
                        <option v-for="o in COURSE_CHARGE" v-bind:value="o.key">{{o.value}}</option>
                      </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">Status</label>
                    <div class="col-sm-10">
                      <select v-model="course.status" class="form-control">
                        <option v-for="o in COURSE_STATUS" v-bind:value="o.key">{{o.value}}</option>
                      </select>
                    </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Enrolled Count</label>
                  <div class="col-sm-10">
                    <input v-model="course.enrolled" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Index</label>
                  <div class="col-sm-10">
                    <input v-model="course.idx" class="form-control" disabled>
                  </div>
                </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            <button v-on:click="save()" type="button" class="btn btn-primary">Save</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div id="course-content-modal" class="modal fade" tabindex="-1" role="dialog" style="overflow: auto">
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Content Editor</h4>
          </div>
          <div class="modal-body">
            <file v-bind:input-id="'content-file-upload'"
                  v-bind:text="'上传文件1'"
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
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
              <i class="ace-icon fa fa-times"></i>
              Cancel
            </button>
            <button type="button" class="btn btn-white btn-info btn-round" v-on:click="saveContent()">
              <i class="ace-icon fa fa-plus blue"></i>
              Save
            </button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div id="course-idx-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Index</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="control-label col-lg-3">
                  Current Index
                </label>
                <div class="col-lg-9">
                  <input class="form-control" v-model="idx.curIdx" name="curIdx" disabled>
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-lg-3">
                  New Index
                </label>
                <div class="col-lg-9">
                  <input class="form-control" v-model="idx.newIdx" name="newIdx">
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
              <i class="ace-icon fa fa-times"></i>
              Cancel
            </button>
            <button type="button" class="btn btn-white btn-info btn-round" v-on:click="updateIdx()">
              <i class="ace-icon fa fa-plus blue"></i>
              Update
            </button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
  </div>

</template>

<script>
    import Pagination from "../../components/pagination";
    import File from "../../components/file";
    export default {
        components: {Pagination,File},
        name: 'business-course',
        data: function() {
            return {
                course: {},
                courses: [],
                COURSE_LEVEL: COURSE_LEVEL,
                COURSE_CHARGE: COURSE_CHARGE,
                COURSE_STATUS: COURSE_STATUS,
                FILE_USE: FILE_USE,
                categories: [],
                tree: {},
                saveContentLabel: "",
                idx: {
                    id: "",
                    curIdx: 0,
                    newIdx: 0
                },
                instructors: [],
                files: [],
            }
        },

        mounted: function() {
            let _this = this;
            _this.$refs.pagination.size = 10;
            _this.allCategories();
            _this.allInstructors();
            _this.list(1);
            // this.$parent.activateSidebar("business-course-sidebar")
        },
        methods: {
            add() {
                let _this = this;
                _this.course = {
                    sort: _this.$refs.pagination.total + 1
                };
                _this.tree.checkAllNodes(false);
                $("#form-modal").modal("show");
            },

            edit(course) {
                let _this = this;
                _this.course = $.extend({}, course);
                _this.listCategory(course.id);
                $("#form-modal").modal("show");
            },

            /*
            * get all courses
            * */
            list(page) {
                let _this = this;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/list', {
                    page: page,
                    size: _this.$refs.pagination.size,
                }).then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    _this.courses = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total);
                })
            },

            /*
            * save change to a course
            * */
            save() {
                let _this = this;

                //save validation
                if (1 != 1
                    || !Validator.require(_this.course.name, "Name")
                    || !Validator.length(_this.course.name, "Name", 1, 50)
                    || !Validator.length(_this.course.description, "Description", 1, 2000)
                    || !Validator.length(_this.course.thumb, "Thumb", 1, 100)
                ) {
                  return;
                }

                let categorys = _this.tree.getCheckedNodes();
                if (Tool.isEmpty(categorys)) {
                    Toast.warning("Please select categories");
                    return;
                }
                _this.course.categorys = categorys;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/save', _this.course).then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    if (resp.success) {
                        $("#form-modal").modal("hide");
                        _this.list(1);
                        Toast.success("Saved successfully!");
                    } else {
                        Toast.warning(resp.message)
                    }
                })
            },

            /*
            * delete a course
            * */
            del(id) {
                let _this = this;
                Confirm.show("You won't be able to revert this!", function () {
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/course/delete/' + id).then((response)=>{
                        Loading.hide();
                        let resp = response.data;
                        if (resp.success) {
                            _this.list(1);
                            Swal.fire(
                                'Deleted!',
                                'Your file has been deleted.',
                                'success'
                            )
                        }
                    })
                });
            },


            /**
             * navigate to chapters
             */
            toChapter(course) {
                let _this = this;
                SessionStorage.set(SESSION_KEY_COURSE, course);
                _this.$router.push("/business/chapter");
            },

            allCategories() {
                let _this = this;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/category/all').then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    _this.categories = resp.content;
                    _this.initTree();
                })
            },

            initTree() {
                let _this = this;
                let setting = {
                    check: {
                        enable: true
                    },
                    data: {
                        simpleData: {
                            idKey: "id",
                            pIdKey: "parent",
                            rootPId: "00000000",
                            enable: true
                        }
                    }
                };

                let zNodes = _this.categories;

                _this.tree = $.fn.zTree.init($("#tree"), setting, zNodes);

                // expand all categories
                _this.tree.expandAll(true);
            },

            /**
             * query all categories related to given courseId
             * @param courseId
             */
            listCategory(courseId) {
                let _this = this;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/list-category/' + courseId).then((res)=>{
                    Loading.hide();
                    console.log("All categories：", res);
                    let response = res.data;
                    let categorys = response.content;

                    // check all categories in the result
                    _this.tree.checkAllNodes(false);
                    for (let i = 0; i < categorys.length; i++) {
                        let node = _this.tree.getNodeByParam("id", categorys[i].categoryId);
                        _this.tree.checkNode(node, true);
                    }
                })
            },

            /**
             * Edit content in Summernote WYSIWYG editor
             */
            editContent(course) {
                let _this = this;
                let id = course.id;
                _this.course = course;
                $("#content").summernote({
                    focus: true,
                    height: 300
                });
                // 先清空历史文本
                $("#content").summernote('code', '');
                _this.saveContentLabel = "";

                // load content files
                _this.listContentFiles();

                Loading.show();
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/admin/course/search-content/' + id).then((response)=>{
                    Loading.hide();
                    let resp = response.data;

                    if (resp.success) {
                        $("#course-content-modal").modal({backdrop: 'static', keyboard: false});
                        if (resp.content) {
                            $("#content").summernote('code', resp.content.content);
                        }
                        // 定时自动保存
                        let saveContentInterval = setInterval(function() {
                            _this.saveContent();
                        }, 5000);
                        // 关闭内容框时，清空自动保存任务
                        $('#course-content-modal').on('hidden.bs.modal', function (e) {
                            clearInterval(saveContentInterval);
                        })
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
                        // Toast.success("Content saved!");
                        let now = Tool.dateFormat("yyyy-MM-dd hh:mm:ss");
                        _this.saveContentLabel = "Saved at: " + now;
                    } else {
                        Toast.warning(resp.message);
                    }
                });
            },

            openIdxModal(course) {
                let _this = this;
                _this.idx = {
                    id: course.id,
                    curIdx: course.idx,
                    newIdx: course.idx
                };
                $("#course-idx-modal").modal("show");
            },

            /**
             * update index
             */
            updateIdx() {
                let _this = this;
                if (_this.idx.newIdx === _this.idx.curIdx) {
                    Toast.warning("Index doesn't change!");
                    return;
                }
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/course/idx", _this.idx).then((res) => {
                    let response = res.data;

                    if (response.success) {
                        Toast.success("Index updated!");
                        $("#course-sort-modal").modal("hide");
                        _this.list(1);
                    } else {
                        Toast.error("Update failed!");
                    }
                });
            },

            allInstructors() {
                let _this = this;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/instructor/all').then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    _this.instructors = resp.content;
                })
            },

            afterUpload(resp) {
                let _this = this;
                let thumb = resp.content.path;
                _this.course.thumb = thumb;
            },

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
             * 上传内容文件后，保存内容文件记录
             */
            afterUploadContentFile(response) {
                let _this = this;
                console.log("开始保存文件记录");
                let file = response.content;
                file.courseId = _this.course.id;
                file.url = file.path;
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course-content-file/save', file).then((response)=>{
                    let resp = response.data;
                    if (resp.success) {
                        Toast.success("上传文件成功");
                        _this.files.push(resp.content);
                    }
                });

            },

            /**
             * 删除内容文件
             */
            delFile(f) {
                let _this = this;
                Confirm.show("删除课程后不可恢复，确认删除？", function () {
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/course-content-file/delete/' + f.id).then((response)=>{
                        let resp = response.data;
                        if (resp.success) {
                            Toast.success("删除文件成功");
                            Tool.removeObj(_this.files, f);
                        }
                    });
                });
            },
        }
    }
</script>

<style scoped>
  .caption h3 {
    font-size: 20px;
  }

  @media (max-width: 1199px) {
    .caption h3 {
      font-size: 16px;
    }
  }
</style>