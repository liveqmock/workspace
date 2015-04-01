/**
 * 权限组管理
 */
define(['avalon', 'util', '../../../widget/pagination/pagination', '../../../widget/dialog/dialog', '../../../widget/form/form', '../../../widget/form/select'], function (avalon, util) {
    var win = this,
       erp = win.erp,
       pageMod = {};
   _.extend(pageMod, {
       /**
        * 页面初始化
        * @param pageEl
        * @param pageName
        */
       "$init": function (pageEl, pageName) {
           var paginationId = pageName + '-pagination',
               positionId = pageName + '-position';
           var pageVm = avalon.define(pageName, function (vm) {
               vm.name = '';
               vm.phone = '';
               vm.gridTotalSize = 0;
               vm.gridData = [];
               vm.gridAllChecked = false;
               vm.gridSelected = [];
               vm.addEditState = '';
               vm.gridCheckAll = function () {
                   if (this.checked) {
                       vm.gridSelected = _.map(vm.gridData.$model, function (itemData) {
                           return itemData.id + "";
                       });
                       //vm.gridAllChecked = true;
                   } else {
                       vm.gridSelected.clear();
                       //vm.gridAllChecked = false;
                   }
               };

             //查询条件
               vm.$positionOpts = {
                   "selectId": positionId,
                   "options": [],
                   "selectedIndex": 0
                };

               /**
                * 表格左下脚一个全选的快捷方式
                */
               vm.scCheckAll = function () {
                   vm.gridSelected = _.map(vm.gridData.$model, function (itemData) {
                       return itemData.id + "";
                   });
               };
               vm.$paginationOpts = {
                   "paginationId": paginationId,
                   "total": 0,
                   "onJump": function (evt, vm) {
                       updateGrid();
                   }
               };
               vm.jumpAddPage = function () {
                    var linkEl = $('<a href="#/sysmanage/usermanage/add"></a>');
                    linkEl.appendTo('body');
                    linkEl[0].click();
                    linkEl.remove();
               };
               /**
                * 列表搜索
                */
               vm.search = function () {
                   updateGrid(true);
               };

               vm.removeGridItem = function () {
                   var meEl = avalon(this),
                       roleId = meEl.data('userId') + "";
                   //选中当前行，取消其他行选中
                   vm.gridSelected = [roleId];
                   //删除对应职位
                   vm._removeUser();
               };
               vm.batchRemoveGridItem = function () {
                   vm._removeUser();
               };
               vm._removeUser = function () {
                   var ids,
                       requestData,
                       url;
                   /**
                    * 前端删除所选用户
                    */
                   var removeSelectedUser = function () {
                        //前端删除所选用户
                        _.each(ids, function (id) {
                            var atIndex = -1;
                            if (_.some(vm.gridData.$model, function (itemData, i) {
                                if (itemData.id == id) {
                                    atIndex = i;
                                    return true;
                                }
                            })) {
                                vm.gridData.removeAt(atIndex);
                                vm.gridSelected.clear();    //清除选中
                            }
                        });
                   };
                   if (vm.gridSelected.size() === 0) {
                       util.alert({
                           "content": "请先选中用户"
                       });
                   } else {
                       ids = vm.gridSelected.$model;
                       url = erp.BASE_PATH + 'user/delete.do';
                       requestData = {
                           "id": ids,
                           "defineDelete":0
                       };
                       util.confirm({
                           "content": "确定要删除选中的用户吗？",
                           "onSubmit": function () {
                               util.c2s({
                                   "url": url,
                                   "type": "post",
                                   "data": requestData,
                                   "success": function (responseData) {
                                       if (responseData.flag == 1) {   //删除成功，重刷grid
                                           //updateGrid(true);
                                           //前端删除所选用户
                                           removeSelectedUser();
                                       } else if (responseData.flag == 2){ // 删除失败,有师生关系存在
                                           requestData.defineDelete = 1;
                                           util.confirm({
                                               "content":responseData.message,
                                               "onSubmit": function () {
                                                    util.c2s({
                                                       "url": url,
                                                       "type": "post",
                                                       "data": requestData,
                                                       "success": function (responseData) {
                                                           if (responseData.flag) {   //删除成功，重刷grid
                                                               //updateGrid(true);
                                                               //前端删除所选用户
                                                                removeSelectedUser();
                                                           }
                                                        }
                                                    });
                                                 }
                                            });
                                       }
                                   }
                               });
                           }
                       });
                   }
               };

           });
           pageVm.gridSelected.$watch("length", function(n) {
               pageVm.gridAllChecked = n === pageVm.gridData.size();
           });

           avalon.scan(pageEl[0],[pageVm]);
           //更新grid数据
           updateGrid();
           //请求职位列表数据
           util.c2s({
               "url": erp.BASE_PATH + 'position/initPosition.do',
               "success": function (responseData) {
                   var positionSelectVm = avalon.getVm(positionId);
                   if (responseData.flag == 1) {   //删除成功，重刷grid
                        positionSelectVm.setOptions([{
                            "value": "",
                            "text": "全部职位"
                        }].concat(_.map(responseData.data, function (itemData) {
                           return {
                               "text": itemData.position_name,
                               "value": itemData.id
                           };
                       })), 0);
                   }
               }
           });

           function updateGrid(jumpFirst) {
               var paginationVm = avalon.getVm(paginationId),
               requestData = {
                      "userName":pageVm.name,
                       "tel": pageVm.phone,
                       "positionId": avalon.getVm(positionId).selectedValue
                   };
               if (jumpFirst) {
                   requestData.pageSize = paginationVm.perPages;
                   requestData.pageNumber = 1;
               } else {
                   requestData.pageSize = paginationVm.perPages;
                   requestData.pageNumber = paginationVm.currentPage;
               }
               util.c2s({
                   "url": erp.BASE_PATH + "user/list.do",
                   //"type": "get",
                   "data": requestData,
                   "success": function (responseData) {
                       var data;
                       if (responseData.flag) {
                           data = responseData.data;
                           paginationVm.total = data.totalSize;
                           if (jumpFirst) {
                               paginationVm.currentPage = 1;   //重设成第一页
                           }
                           //从新编辑行，增加角色串.
                           _.each(data.rows, function(row){
                               var roles = [];
                               _.each(row.listRoles, function(role){
                                   roles.push(role.roleName);
                               });
                               row.patchedRoleNames = roles.length == 0 ? '': roles.join(',');
                               if(row.position){
                                   row.positionName = row.position.positionName;
                               }else{
                                   row.positionName = '';
                               }
                           });
                           pageVm.gridData = data.rows;
                           pageVm.gridTotalSize = data.totalSize;
                           pageVm.gridAllChecked = false;  //取消全选
                           pageVm.gridSelected.clear();
                       }
                   }
               });
           }
       },
       /**
        * 页面销毁[可选]
        */
       "$remove": function (pageEl, pageName) {
           var paginationId = pageName + '-pagination',
               positionId = pageName + '-position';
           $(avalon.getVm(paginationId).widgetElement).remove();
           $(avalon.getVm(positionId).widgetElement).remove();
       }
   });

   return pageMod;
});

