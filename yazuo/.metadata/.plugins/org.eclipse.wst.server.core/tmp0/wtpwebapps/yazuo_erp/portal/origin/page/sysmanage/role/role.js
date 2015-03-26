/**
 * 权限组管理
 */
define(['avalon', 'util', '../../../widget/pagination/pagination', '../../../widget/dialog/dialog', '../../../widget/form/form'], function (avalon, util) {
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
           var addEditDialogId = pageName + '-add-edit-dialog',
               addEditFormId = pageName + '-add-edit-form',
               paginationId = pageName + '-pagination';
           var pageVm = avalon.define(pageName, function (vm) {
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
               
               vm.removeGridItem = function () {
                   var meEl = avalon(this),
                       roleId = meEl.data('roleId') + "";
                   //选中当前行，取消其他行选中
                   vm.gridSelected = [roleId];
                   //删除对应职位
                   vm._removeRole();
               };
               vm.batchRemoveGridItem = function () {
                   vm._removeRole();
               };
               /**
                * 跳转到添加页面
                */
               vm.jumpAddPage = function (evt) {
                   var linkEl = $('<a href="#/sysmanage/role/add"></a>');
                   linkEl.appendTo('body');
                   linkEl[0].click();
                   linkEl.remove();
                   //avalon.router.navigate('/sysmanage/role/add');
               };
               vm._removeRole = function () {
                   var ids,
                       requestData,
                       url;
                   if (vm.gridSelected.size() === 0) {
                       util.alert({
                           "content": "请先选中权限组"
                       });
                   } else {
                       ids = vm.gridSelected.$model;
                       url = erp.BASE_PATH + 'role/delete.do';
                       requestData = {
                           "id": ids
                       };
                       util.confirm({
                           "content": "确定要删除选中的权限组吗？",
                           "onSubmit": function () {
                               util.c2s({
                                   "url": url,
                                   "type": "post",
                                   "data": requestData,
                                   "success": function (responseData) {
                                       if (responseData.flag == 1) {   //删除成功，重刷grid
                                           updateGrid(true);
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

           function updateGrid(jumpFirst) {
               var paginationVm = avalon.getVm(paginationId),
                   requestData = {};
               if (jumpFirst) {
                   requestData.pageSize = paginationVm.perPages;
                   requestData.pageNumber = 1;
               } else {
                   requestData.pageSize = paginationVm.perPages;
                   requestData.pageNumber = paginationVm.currentPage;
               }
               util.c2s({
                   "url": erp.BASE_PATH + "role/list.do",
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
           //需要单独清除dialog vm
           //var dialogVm = avalon.getVm(pageName + '-add-edit');
           //$(dialogVm.widgetElement).remove();
           $('.add-edit-position-dialog .add-edit-form').remove();
           $('.add-edit-position-dialog').remove();
           $('.pagination', pageEl).remove();
       }
   });

   return pageMod;
});

