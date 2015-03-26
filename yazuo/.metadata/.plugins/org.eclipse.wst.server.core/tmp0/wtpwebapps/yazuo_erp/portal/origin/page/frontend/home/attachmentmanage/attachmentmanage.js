/**
 * Created by ZHANG on 2014/8/7.
 */
/**
 * 前端服务-附件管理
 */
define(['avalon', 'util', '../../../../widget/pagination/pagination'], function (avalon, util) {
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
                vm.navCrumbs = [{
                    "text":"我的主页",
                    "href":''
                }, {
                    "text":"附件管理"
                }];

                var phref = '';
                switch (routeData.route) {
                    case "/sales/home/attachmentmanage":
                        phref = "#/sales/home";
                        break;
                    case "/frontend/home/attachmentmanage":
                        phref = "#/frontend/home";
                        break;
                    case "/backend/attachmentmanage":
                        phref = "#/backend/customerservice";
                        break;
                }
                vm.navCrumbs[0].href= phref;

                vm.url=erp.BASE_PATH;
                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.pageData={};
                vm.statusTabs='';//标签切换依赖
                vm.requestData={
                    merchantId : '',
                    attachmentType:'plan',
                    merchantName:'',
                    pageNumber:1,
                    pageSize:10,
                    userId : vm.pageUserId
                };
                vm.changeStatus = function(statusStr){//切换标签
                    var paginationVm = avalon.getVm(getTypeId('paginationId'));
                    paginationVm.currentPage = 1;
                    pageVm.requestData.pageNumber = 1;
                    pageVm.requestData.attachmentType = statusStr;
                    pageVm.requestData.merchantName='';
                    pageVm.pageData = {};
                    updateList(pageVm.requestData.$model);
                };
                vm.search = function(){
                    updateList(pageVm.requestData.$model);
                };
                vm.$paginationOpts = {//分页
                    "paginationId": getTypeId('paginationId'),
                    "total": 0,
                    "onJump": function (evt, vm) {
                        var paginationVm = avalon.getVm(getTypeId('paginationId'));
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
                    "url": erp.BASE_PATH + "mailAndAttachment/listAttachment.do",
                    "type": "post",
                    "contentType" : 'application/json',
                    "data": requestData,
                    "success": function (responseData) {
                        var data,
                        paginationVm = avalon.getVm(getTypeId('paginationId'));
                        if (responseData.flag) {
                            data = responseData.data;
                            paginationVm.total = data.totalSize;
                            pageVm.pageData=data.rows;
                            //pageVm.requestData.merchantName='';
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
