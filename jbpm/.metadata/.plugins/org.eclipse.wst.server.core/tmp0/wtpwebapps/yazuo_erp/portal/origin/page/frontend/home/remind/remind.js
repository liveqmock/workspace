/**
 * Created by ZHANG on 2014/8/7.
 */
/**
 * 前端服务-提醒
 */
define(['avalon', 'util', 'json', '../../../../widget/pagination/pagination'], function (avalon, util,JSON) {
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
        "$init": function (pageEl, pageName,routeData) {
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
                    "text":"提醒"
                }];

                var phref = '';
                switch (routeData.route) {
                    case "/sales/home/remind":
                        phref = "#/sales/home";
                        break;
                    case "/frontend/home/remind":
                        phref = "#/frontend/home";
                        break;
                    case "/backend/remind":
                        phref = "#/backend/customerservice";
                        break;
                }
                vm.navCrumbs[0].href= phref;
                vm.url=erp.BASE_PATH;
                vm.pageData={};
                vm.statusTabs='';//标签切换依赖
                vm.requestData={
                    userId : vm.pageUserId,
                    merchantName:'',
                    pageNumber:1,
                    pageSize:10,
                    itemStatus:''
                };
                vm.changeStatus = function(statusId){//切换标签
                    var paginationVm = avalon.getVm(getTypeId('pagination'));
                    pageVm.requestData.pageNumber=1;
                    paginationVm.currentPage = 1;
                    pageVm.requestData.itemStatus=statusId;
                    updateList(pageVm.requestData.$model);
                };
                vm.search = function(){//搜索
                    updateList(pageVm.requestData.$model);
                };
                vm.modStatus = function(id){//修改状态
                    id=id.toString();
                    util.c2s({
                        "url": erp.BASE_PATH + "activity/updateRemind.do",
                        "type": "post",
                        //"contentType" : 'application/json',
                        "data": {id:id},
                        "success": function (responseData) {
                            updateList(pageVm.requestData.$model);
                        }
                    });
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
            });
            avalon.scan(pageEl[0]);
            /*页面初始化请求渲染*/
            updateList(pageVm.requestData.$model);
            function updateList(obj){
                var requestData=JSON.stringify(obj);
                util.c2s({
                    "url": erp.BASE_PATH + "activity/listRemind.do",
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
