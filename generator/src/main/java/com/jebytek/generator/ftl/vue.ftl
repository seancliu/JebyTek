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
      <tr><#list fieldList as field>
            <th>${field.nameComment}</th></#list>
            <th>Operation</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="${domain} in ${domain}s">
          <#list fieldList as field>
              <td>{{${domain}.${field.nameHump}}}</td>
          </#list>


        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button v-on:click="edit(${domain})" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>

            <button v-on:click="del(${domain}.id)" class="btn btn-xs btn-danger">
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
            <h4 class="modal-title">Add a ${domain}</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
                <#list fieldList as field>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">${field.nameComment}</label>
                        <div class="col-sm-10">
                            <input v-model="${domain}.${field.nameHump}" class="form-control">
                        </div>
                    </div>
                </#list>
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
        name: '${domain}',
        data: function() {
            return {
                ${domain}: {},
                ${domain}s: []
            }
        },

        mounted: function() {
            let _this = this;
            _this.$refs.pagination.size = 10;
            _this.list(1);
            // this.$parent.activateSidebar("${module}-${domain}-sidebar")
        },
        methods: {
            add() {
                let _this = this;
                _this.${domain} = {};
                $("#form-modal").modal("show");
            },

            edit(${domain}) {
                let _this = this;
                _this.${domain} = $.extend({}, ${domain});
                $("#form-modal").modal("show");
            },

            /*
            * get all ${domain}s
            * */
            list(page) {
                let _this = this;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/list', {
                    page: page,
                    size: _this.$refs.pagination.size,
                }).then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    _this.${domain}s = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total);
                })
            },

            /*
            * save change to a ${domain}
            * */
            save() {
                let _this = this;

                //save validation


                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/save', _this.${domain}).then((response)=>{
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
            * delete a ${domain}
            * */
            del(id) {
                let _this = this;
                Confirm.show("You won't be able to revert this!", function () {
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/delete/' + id).then((response)=>{
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