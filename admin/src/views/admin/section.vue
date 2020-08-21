<template>
  <div>
    <h4 class="lighter">
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/course" class="pink"> {{course.name}} </router-link>：
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/chapter" class="pink"> {{chapter.name}} </router-link>
    </h4>
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

    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
            <th>ID</th>

            <th>TITLE</th>

            <th>VIDEO</th>

            <th>LENGTH</th>

            <th>PRICE</th>

            <th>INDEX</th>


            <th>Operation</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="section in sections">
          <td>{{section.id}}</td>
          <td>{{section.title}}</td>
          <td>{{section.video}}</td>
          <td>{{section.length | formatSecond}}</td>
          <td>{{CHARGE | optionKV(section.charge)}}</td>
          <td>{{section.idx}}</td>


        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button v-on:click="edit(section)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>

            <button v-on:click="del(section.id)" class="btn btn-xs btn-danger">
              <i class="ace-icon fa fa-trash-o bigger-120"></i>
            </button>
          </div>

          <div class="hidden-md hidden-lg">
            <div class="inline pos-rel">
              <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
              </button>

              <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                <li>
                  <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																			<span class="blue">
																				<i class="ace-icon fa fa-search-plus bigger-120"></i>
																			</span>
                  </a>
                </li>

                <li>
                  <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																			<span class="green">
																				<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																			</span>
                  </a>
                </li>

                <li>
                  <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																			<span class="red">
																				<i class="ace-icon fa fa-trash-o bigger-120"></i>
																			</span>
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </td>
      </tr>

      </tbody>
    </table>

    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Add a section</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
                <div class="form-group">
                    <label class="col-sm-2 control-label">TITLE</label>
                    <div class="col-sm-10">
                        <input v-model="section.title" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">COURSE</label>
                    <div class="col-sm-10">
                      <p class="form-control-static">{{course.name}}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">CHAPTER</label>
                    <div class="col-sm-10">
                      <p class="form-control-static">{{chapter.name}}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">VIDEO</label>
                    <div class="col-sm-10">
                      <file v-bind:input-id="'video-upload'"
                            v-bind:text="'Upload Video'"
                            v-bind:suffixs="['mp4']"
                            v-bind:use="FILE_USE.COURSE.key"
                            v-bind:after-upload="afterUpload">
                      </file>
                      <div v-show="section.video" class="row">
                        <div class="col-md-9">
                          <video v-bind:src="section.video" id="video" controls="controls"></video>
                        </div>
                      </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">LENGTH</label>
                    <div class="col-sm-10">
                        <input v-model="section.length" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">PRICE</label>
                    <div class="col-sm-10">
                      <select v-model="section.charge" class="form-control">
                        <option v-for="o in CHARGE" v-bind:value="o.key">{{o.value}}</option>
                      </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">INDEX</label>
                    <div class="col-sm-10">
                        <input v-model="section.idx" class="form-control">
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
  </div>

</template>

<script>
    import Pagination from "../../components/pagination";
    import File from "../../components/file";
    export default {
        components: {Pagination, File},
        name: 'business-section',
        data: function() {
            return {
                section: {},
                sections: [],
                CHARGE: SECTION_CHARGE,
                FILE_USE: FILE_USE,
                course: {},
                chapter: {}
            }
        },

        mounted: function() {
            let _this = this;
            _this.$refs.pagination.size = 10;
            let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
            let chapter = SessionStorage.get(SESSION_KEY_CHAPTER) || {};
            if (Tool.isEmpty(course) || Tool.isEmpty(chapter)) {
                _this.$router.push("/dashboard");
            }
            _this.course = course;
            _this.chapter = chapter;
            _this.list(1);
            this.$parent.activateSidebar("business-course-sidebar")
        },
        methods: {
            add() {
                let _this = this;
                _this.section = {};
                $("#form-modal").modal("show");
            },

            edit(section) {
                let _this = this;
                _this.section = $.extend({}, section);
                $("#form-modal").modal("show");
            },

            /*
            * get all sections
            * */
            list(page) {
                let _this = this;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/section/list', {
                    page: page,
                    size: _this.$refs.pagination.size,
                    courseId: _this.course.id,
                    chapterId: _this.chapter.id
                }).then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    _this.sections = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total);
                })
            },

            /*
            * save change to a section
            * */
            save() {
                let _this = this;

                //save validation
                if (1 != 1
                    || !Validator.require(_this.section.title, "TITLE")
                    || !Validator.length(_this.section.title, "TITLE", 1, 50)
                    || !Validator.length(_this.section.video, "VIDEO", 1, 200)
                ) {
                  return;
                }
                _this.section.courseId = _this.course.id;
                _this.section.chapterId = _this.chapter.id;

                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/section/save', _this.section).then((response)=>{
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
            * delete a section
            * */
            del(id) {
                let _this = this;
                Confirm.show("You won't be able to revert this!", function () {
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/section/delete/' + id).then((response)=>{
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

            afterUpload(resp) {
                let _this = this;
                let video = resp.content.path;
                _this.section.video = video;
                _this.getVideoLength();
            },

            getVideoLength() {
                let _this = this;
                let ele = document.getElementById("video");
                _this.section.length = parseInt(ele.duration, 10);
            }
        }
    }
</script>

<style scoped>
  video {
    width: 100%;
    height: auto;
    margin-top: 10px;
  }
</style>