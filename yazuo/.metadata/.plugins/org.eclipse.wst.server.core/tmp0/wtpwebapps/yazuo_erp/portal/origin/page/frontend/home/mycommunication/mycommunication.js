/**
 * Created by ZHANG on 2014/8/7.
 */
/**
 * 前端服务-通讯录
 */
define(['avalon', 'util', 'json','moment','../../../../widget/pagination/pagination','../../../../widget/form/select', '../../../../widget/pagination/pagination', '../../../../widget/dialog/dialog', '../../../../widget/form/form', '../../../../widget/calendar/calendar', '../../../../widget/autocomplete/autocomplete', '../../../../module/address/address'], function (avalon, util) {
    var win = this,
        erp = win.erp,
        pageMod = {},
        tempWidgetIdStore = [];
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName, routeData) {
            var getTypeId = function (n) {//设置和获取widget的id;
                var widgetId = pageName + n;
                tempWidgetIdStore.push(widgetId);
                return widgetId;
            };
            var pageVm = avalon.define(pageName, function (vm) {
                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.navCrumbs = [{
                    "text":"我的主页",
                    "href":''
                }, {
                    "text":"通讯录"
                }];
                vm.permission = {//权限
                    "change_contact_169" : util.hasPermission("change_contact_169"),//修改
                    "add_contact_173" : util.hasPermission('add_contact_173'),//修改全部
                    "del_contact_174" : util.hasPermission('del_contact_174'),//删除
                    "fes_add_contact" : util.hasPermission('fes_add_contact')//添加
                };
                var phref = '';
                switch (routeData.route) {
                    case "/sales/home/mycommunication(/:mid)":
                        phref = "#/sales/home";
                        break;
                    case "/frontend/home/mycommunication(/:mid)":
                        phref = "#/frontend/home";
                        break;
                    case "/backend/mycommunication(/:mid)":
                        phref = "#/backend/customerservice";
                        break;
                }
                vm.navCrumbs[0].href= phref;
                vm.url=erp.BASE_PATH;
                vm.pageData={};
                var merchantId = '';
                if(!merchantId){
                    merchantId = parseInt(routeData.params['mid']);
                }
                vm.requestData={//入参
                    userId: vm.pageUserId,
                    name:'',
                    merchantId : merchantId,
                    merchantName:'',
                    pageNumber:1,
                    pageSize:10
                };

                vm.search = function(){
                    var paginationVm = avalon.getVm(getTypeId('pagination'));
                    pageVm.requestData.pageNumber=1;
                    paginationVm.currentPage =1;
                    updateList(pageVm.requestData.$model);
                };
                vm.$paginationOpts = {//分页
                    "paginationId": getTypeId('pagination'),
                    "total": 0,
                    "onJump": function (evt, vm) {
                        var paginationVm = avalon.getVm(getTypeId('pagination'));
                        pageVm.requestData.pageNumber=paginationVm.currentPage;
                        updateList(pageVm.requestData.$model);
                    }
                };

                /*通讯录*/
                vm.$addressOpts = {
                    "addressId": getTypeId('-addressModule'),
                    "moduleType": 'fes',
                    "displayType": 'add',
                    "isSearch" : true,
                    "merchantId": 0,
                    "callFn" : function(){
                        var paginationVm = avalon.getVm(getTypeId('pagination'));
                        pageVm.requestData.pageNumber=1;
                        paginationVm.currentPage =1;
                        updateList(pageVm.requestData.$model);
                    }
                };
                vm.editCommunication = function () {//修改部分字段
                    var id = $(this).attr('data-id');
                    var merchantId = $(this).attr('data-merchantId');
                    var dialog = avalon.getVm(getTypeId('-addressModule'));
                    dialog.displayType = 'edit';
                    dialog.isSearch = false;
                    dialog.requestData = {
                        merchantId: merchantId,
                        id:id
                    };
                    dialog.open();
                };
                vm.editAllCommunication = function () {//修改全部字段
                    var id = $(this).attr('data-id');
                    var merchantId = $(this).attr('data-merchantId');
                    var dialog = avalon.getVm(getTypeId('-addressModule'));
                    dialog.displayType = 'allEdit';
                    dialog.editRange = 'all';
                    dialog.isSearch = false;
                    dialog.requestData = {
                        merchantId: merchantId,
                        id:id
                    };
                    dialog.open();
                };
                vm.addCommunication = function(){//添加通讯录
                    var dialog = avalon.getVm(getTypeId('-addressModule'));
                    dialog.displayType = 'add';
                    dialog.isSearch = true;
                    dialog.requestData = {};
                    dialog.open();
                };
                vm.removeCommunication = function(){//删除通讯录
                    var id = $(this).attr('data-id');
                    util.confirm({
                        "content": "你确定要删除吗？",
                        "onSubmit": function () {
                            util.c2s({
                                "url": erp.BASE_PATH + "contact/deleteMktContact/"+id+".do",
                                "type": "post",
                                "contentType" : 'application/json',
                                "data": {},
                                "success": function (responseData) {
                                    var data,
                                        paginationVm = avalon.getVm(getTypeId('pagination'));
                                    if (responseData.flag) {
                                        util.alert({
                                            "content": "删除成功！",
                                            "iconType": "success",
                                            "onSubmit":function () {
                                                var paginationVm = avalon.getVm(getTypeId('pagination'));
                                                pageVm.requestData.pageNumber=1;
                                                paginationVm.currentPage =1;
                                                updateList(pageVm.requestData.$model);
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });
                };
                /*通讯录-end*/
            });
            avalon.scan(pageEl[0]);
            /*页面初始化请求渲染*/
            updateList(pageVm.requestData.$model);
            function updateList(obj){
                var requestData=JSON.stringify(obj);
                util.c2s({
                    "url": erp.BASE_PATH + "contact/listContacts.do",
                    "type": "post",
                    "contentType" : 'application/json',
                    "data": requestData,
                    "success": function (responseData) {
                        var data,
                        paginationVm = avalon.getVm(getTypeId('pagination'));
                        if (responseData.flag) {
                            data = responseData.data;
                            paginationVm.total = data.totalSize;
                            pageVm.pageData=data.rows;
                            pageVm.requestData.pageNumber = 1;
                        }
                    }
                });
            }
            /*页面初始化请求渲染end*/
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            _.each(tempWidgetIdStore, function (widgetId) {
                var vm = avalon.getVm(widgetId);
                vm && $(vm.widgetElement).remove();
            });
            tempWidgetIdStore.length = 0;
        }
    });

    return pageMod;
});


