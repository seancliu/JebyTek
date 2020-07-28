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

    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
            <th>ID</th>
            <th>TITLE</th>
            <th>COURSE_ID</th>
            <th>CHAPTER_ID</th>
            <th>VIDEO</th>
            <th>TIME IN SECS</th>
            <th>C: CHARGE; F: FREE</th>
            <th>INDEX</th>
            <th>CREATE TIME</th>
            <th>UPDATE TIME</th>
            <th>Operation</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="section in sections">
              <td>{{section.id}}</td>
              <td>{{section.title}}</td>
              <td>{{section.courseId}}</td>
              <td>{{section.chapterId}}</td>
              <td>{{section.video}}</td>
              <td>{{section.length}}</td>
              <td>{{section.charge}}</td>
              <td>{{section.idx}}</td>
              <td>{{section.createdAt}}</td>
              <td>{{section.updatedAt}}</td>


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
                        <label class="col-sm-2 control-label">ID</label>
                        <div class="col-sm-10">
                            <input v-model="section.id" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">TITLE</label>
                        <div class="col-sm-10">
                            <input v-model="section.title" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">COURSE_ID</label>
                        <div class="col-sm-10">
                            <input v-model="section.courseId" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">CHAPTER_ID</label>
                        <div class="col-sm-10">
                            <input v-model="section.chapterId" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">VIDEO</label>
                        <div class="col-sm-10">
                            <input v-model="section.video" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">TIME IN SECS</label>
                        <div class="col-sm-10">
                            <input v-model="section.length" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">C: CHARGE; F: FREE</label>
                        <div class="col-sm-10">
                            <input v-model="section.charge" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">INDEX</label>
                        <div class="col-sm-10">
                            <input v-model="section.idx" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">CREATE TIME</label>
                        <div class="col-sm-10">
                            <input v-model="section.createdAt" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">UPDATE TIME</label>
                        <div class="col-sm-10">
                            <input v-model="section.updatedAt" class="form-control">
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
        name: 'section',
        data: function() {
            return {
                section: {},
                sections: []
            }
        },

        mounted: function() {
            let _this = this;
            _this.$refs.pagination.size = 10;
            _this.list(1);
            // this.$parent.activateSidebar("business-section-sidebar")
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
            }       ,

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
            }
        }
    }
</script>