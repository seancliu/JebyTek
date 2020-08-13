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
      <div v-for="instructor in instructors" class="col-md-3 center">
        <div>
          <span class="profile-picture">
            <img v-show="!instructor.avatar" class="editable img-responsive editable-click editable-empty" src="/ace/assets/images/avatars/profile-pic.jpg" v-bind:title="instructor.intro"/>
            <img v-show="instructor.avatar" class="editable img-responsive editable-click editable-empty" v-bind:src="instructor.avatar" v-bind:title="instructor.intro"/>
          </span>

          <div class="space-4"></div>

          <div class="width-85 label label-info label-xlg arrowed-in arrowed-in-right">
            <div class="inline position-relative">
              <a href="javascript:;" class="user-title-label dropdown-toggle" data-toggle="dropdown">
                <i class="ace-icon fa fa-circle light-green"></i>
                &nbsp;
                <span class="white">{{instructor.title}}</span>
              </a>
            </div>
          </div>
        </div>

        <div class="space-6"></div>

        <a href="javascript:;" class="text-info bigger-110" v-bind:title="instructor.motto">
          <i class="ace-icon fa fa-user"></i>
          {{instructor.name}}&nbsp;({{instructor.alias}})
        </a>

        <div class="space-6"></div>

        <div class="profile-social-links align-center">
          <button v-on:click="edit(instructor)" class="btn btn-xs btn-info">
            <i class="ace-icon fa fa-pencil bigger-120"></i>
          </button>
          &nbsp;
          <button v-on:click="del(instructor.id)" class="btn btn-xs btn-danger">
            <i class="ace-icon fa fa-trash-o bigger-120"></i>
          </button>
        </div>

        <div class="hr hr16 dotted"></div>

      </div>
    </div>

    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Add a instructor</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
                <div class="form-group">
                  <label class="col-sm-2 control-label">Name</label>
                  <div class="col-sm-10">
                    <input v-model="instructor.name" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Alias</label>
                  <div class="col-sm-10">
                    <input v-model="instructor.alias" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Avatar</label>
                  <div class="col-sm-10">
                    <button type="button" v-on:click="selectAvatar()" class="btn btn-white btn-default btn-round">
                      <i class="ace-icon fa fa-upload"></i>
                      Upload Avatar
                    </button>
                    <input class="hidden" type="file" v-on:change="uploadAvatar()" id="file-upload-input">
                    <div v-show="instructor.avatar" class="row">
                      <div class="col-md-4">
                        <img v-bind:src="instructor.avatar" class="img-responsive">
                      </div>
                    </div>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Title</label>
                  <div class="col-sm-10">
                    <input v-model="instructor.title" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Motto</label>
                  <div class="col-sm-10">
                    <input v-model="instructor.motto" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Intro</label>
                  <div class="col-sm-10">
                    <textarea v-model="instructor.intro" class="form-control" rows="5"></textarea>
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
    export default {
        components: {Pagination},
        name: 'business-instructor',
        data: function() {
            return {
                instructor: {},
                instructors: [],
            }
        },

        mounted: function() {
            let _this = this;
            _this.$refs.pagination.size = 10;
            _this.list(1);
            // this.$parent.activateSidebar("business-instructor-sidebar")
        },
        methods: {
            add() {
                let _this = this;
                _this.instructor = {};
                $("#form-modal").modal("show");
            },

            edit(instructor) {
                let _this = this;
                _this.instructor = $.extend({}, instructor);
                $("#form-modal").modal("show");
            },

            /*
            * get all instructors
            * */
            list(page) {
                let _this = this;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/instructor/list', {
                    page: page,
                    size: _this.$refs.pagination.size,
                }).then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    _this.instructors = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total);
                })
            },

            /*
            * save change to a instructor
            * */
            save() {
                let _this = this;

                //save validation
                if (1 != 1
                    || !Validator.require(_this.instructor.name, "Name")
                    || !Validator.length(_this.instructor.name, "Name", 1, 50)
                    || !Validator.length(_this.instructor.alias, "Alias", 1, 50)
                    || !Validator.length(_this.instructor.avatar, "Avatar", 1, 100)
                    || !Validator.length(_this.instructor.title, "Title", 1, 50)
                    || !Validator.length(_this.instructor.motto, "Motto", 1, 50)
                    || !Validator.length(_this.instructor.intro, "Intro", 1, 500)
                ) {
                  return;
                }

                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/instructor/save', _this.instructor).then((response)=>{
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
            }       ,

            /*
            * delete a instructor
            * */
            del(id) {
                let _this = this;
                Confirm.show("You won't be able to revert this!", function () {
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/instructor/delete/' + id).then((response)=>{
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

            uploadAvatar() {
                let _this = this;
                let formData = new window.FormData();
                // key ("file") must match the param in the controller on the backend
                formData.append('file', document.querySelector('#file-upload-input').files[0]);
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/upload', formData).then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    let avatar = resp.content;
                    _this.instructor.avatar = avatar;
                });
            },

            selectAvatar() {
                $("#file-upload-input").trigger("click");
            }
        }
    }
</script>