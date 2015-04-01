/**
 * 前端服务-制卡物料
 */
define(['avalon', 'util', '../../../widget/pagination/pagination', '../../../widget/form/select'], function (avalon, util) {
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
        "$init": function (pageEl, pageName) {
            var getTypeId = function (n) {//设置和获取widget的id;
                var widgetId = pageName + n;
                tempWidgetIdStore.push(widgetId);
                return widgetId;
            };
            var pageVm = avalon.define(pageName, function (vm) {
                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.pageData = {};
                vm.backlogNumber = 0;
                vm.backlog = {//待办事项
                    "userId": vm.pageUserId,
                    "inputItemTypes":["01"],
                    "merchantName":"",
                    "pageNumber":1,
                    "itemStatus": '0',
                    "pageSize":10,
                    "businessTypes":['4','5','6']
                };
                vm.doToStatus = function(value){
                    pageVm.backlog.inputItemTypes = value;
                    updateList(pageVm.backlog.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);
                };
                vm.$paginationOpts = {//分页
                    "paginationId": getTypeId('paginationId'),
                    "total": 0,
                    "onJump": function (evt, vm) {
                        var paginationVm = avalon.getVm(getTypeId('paginationId'));
                        pageVm.backlog.pageNumber=paginationVm.currentPage;
                        updateList(pageVm.backlog.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);
                    }
                };
                //状态（未完成、已完成）
                vm.$statusSelectOpts = {
                    "selectId": getTypeId('-statusSelectId'),
                    "options": [{text:'未完成',value:0},{text:'已完成',value:1}],
                    "onSelect": function (v, t, o) {
                        var paginationVm = avalon.getVm(getTypeId('paginationId'));
                        pageVm.backlog.itemStatus = v;
                        pageVm.backlog.pageNumber=1;
                        paginationVm.currentPage = 1;
                        updateList(pageVm.backlog.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);
                    }
                }
            });
            avalon.scan(pageEl[0]);
            /*页面初始化请求渲染*/
            function updateList(obj,url,callBack){
                var requestData=JSON.stringify(obj);
                util.c2s({
                    "url": erp.BASE_PATH + url,
                    "type": "post",
                    "contentType" : 'application/json',
                    "data": requestData,
                    "success": callBack
                });
            }
            /*页面初始化请求渲染end*/
            /*代办事项回调*/
            updateList(pageVm.backlog.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);
            function backlogCallBack(responseData){
                var data,
                    paginationVm = avalon.getVm(getTypeId('paginationId'));
                if (responseData.flag) {
                    data = responseData.data;
                    paginationVm.total = data.totalSize;
                    pageVm.pageData= data.rows;
                    pageVm.backlogNumber = data.totalSize;

                }
            }
            /*代办事项回调end*/
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
