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

            <th>Parent ID</th>

            <th>Name</th>

            <th>Index</th>
            <th>Operation</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="category in categorys">
        <td>{{category.id}}</td>
        <td>{{category.parent}}</td>
        <td>{{category.name}}</td>
        <td>{{category.idx}}</td>


        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button v-on:click="edit(category)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>

            <button v-on:click="del(category.id)" class="btn btn-xs btn-danger">
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
            <h4 class="modal-title">Add a category</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
                <div class="form-group">
                  <label class="col-sm-2 control-label">Parent ID</label>
                  <div class="col-sm-10">
                    <input v-model="category.parent" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Name</label>
                  <div class="col-sm-10">
                    <input v-model="category.name" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Index</label>
                  <div class="col-sm-10">
                    <input v-model="category.idx" class="form-control">
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
        name: 'business-category',
        data: function() {
            return {
                category: {},
                categorys: [],
            }
        },

        mounted: function() {
            let _this = this;
            _this.$refs.pagination.size = 10;
            _this.list(1);
            // this.$parent.activateSidebar("business-category-sidebar")
        },
        methods: {
            add() {
                let _this = this;
                _this.category = {};
                $("#form-modal").modal("show");
            },

            edit(category) {
                let _this = this;
                _this.category = $.extend({}, category);
                $("#form-modal").modal("show");
            },

            /*
            * get all categorys
            * */
            list(page) {
                let _this = this;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/category/list', {
                    page: page,
                    size: _this.$refs.pagination.size,
                }).then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    _this.categorys = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total);
                })
            },

            /*
            * save change to a category
            * */
            save() {
                let _this = this;

                //save validation
                if (1 != 1
                    || !Validator.require(_this.category.parent, "Parent ID")
                    || !Validator.require(_this.category.name, "Name")
                    || !Validator.length(_this.category.name, "Name", 1, 50)
                ) {
                  return;
                }

                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/category/save', _this.category).then((response)=>{
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
            * delete a category
            * */
            del(id) {
                let _this = this;
                Confirm.show("You won't be able to revert this!", function () {
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/category/delete/' + id).then((response)=>{
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