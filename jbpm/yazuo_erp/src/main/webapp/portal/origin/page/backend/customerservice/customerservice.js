/**
 * 后端服务-我的主页
 */
define(['avalon', 'util', '../../../widget/pagination/pagination','../../../widget/form/select', '../../../widget/grid/grid'], function (avalon, util) {
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
                vm.pageStatus= 1;
                vm.pageDataMerchant= {};
                vm.pageDataBacklog = {};
                vm.options1=[];
                vm.dotolen = 0;//代办事项个数
                vm.distributionFd = {};//分配负责人
                vm.backlog = {//待办事项
                    "userId": vm.pageUserId,
                    "inputItemTypes":["01","02","03","04"],
                    "merchantName":"",
                    "pageNumber":1,
                    "pageSize":10,
                    "businessTypes":['1','9','10','11','3','7','12','13']
                };
                vm.endBacklog = {//已完成
                    "userId": vm.pageUserId,
                    "inputItemTypes":["01","02","03","04"],
                    "merchantName":"",
                    "pageNumber":1,
                    "pageSize":10,
                    "itemStatus":1,
                    "businessTypes":['1','9','10','11','3','7','12','13']
                };
                vm.requestData= {//我的商户
                    "merchantStatus" : "200",
                    "userId" : "",
                    "pageSize" : 10,
                    "pageNumber" : 1,
                    "merchantName" : "",
                    "merchantStatusType" : 0
                };
                vm.localHref = function(url){//页面跳转
                    util.jumpPage(url);
                };
                vm.permission = {//权限
                    "end_attachment_mag" : util.hasPermission('end_attachment_mag'),//附件管理
                    "end_email_temp" : util.hasPermission('end_email_temp'),//邮件模板
                    "end_complaint_mag" : util.hasPermission('end_complaint_mag'),//投诉处理
                    "end_merchant_mag" : util.hasPermission('end_merchant_mag'),//商户管理
                    "end_custom_service_todolist" : util.hasPermission('end_custom_service_todolist')//待办事项
                };
                vm.changePageStatus= function(value){//大标签切换
                    var paginationVm = avalon.getVm(getTypeId('paginationId'));
                    paginationVm.currentPage = 1;
                    vm.pageStatus= value;
                    if(value == 1){
                        pageVm.requestData.merchantName= '';
                        pageVm.requestData.pageNumber =1;
                        updateList(pageVm.requestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);
                    }else if(value == 2){
                        pageVm.backlog.merchantName= '';
                        pageVm.backlog.pageNumber =1;
                        updateList(pageVm.backlog.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);
                    }else if(value == 3){
                        pageVm.backlog.merchantName= '';
                        pageVm.backlog.pageNumber =1;
                        updateList(pageVm.endBacklog.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);
                    }
                };
                /**
                 * 我的商户
                 */
                /*分配负责人*/
                vm.distributionFd = {};//分配负责人
                vm.distribution = function(){
                    var dialogVm = avalon.getVm(getTypeId('distributionDialogId'));
                    var scheduleVm = avalon.getVm(getTypeId('fdId'));
                    var requestData={"remark":"end_custom_service"};
                    requestData=JSON.stringify(requestData);
                    if(!this.$vmodel.$model.el.listAssistantUsers.length){
                        vm.distributionFd.$model.userId = "";
                    }else{
                        vm.distributionFd.$model.userId = this.$vmodel.$model.el.listAssistantUsers[0].id;
                    }
                    vm.distributionFd.$model.merchantId = this.$vmodel.$model.el.merchantId;

                    if(!vm.options1.length){
                        util.c2s({
                            "url": erp.BASE_PATH + "user/getAllUsersByResourceCode.do",
                            "type": "post",
                            "contentType" : 'application/json',
                            "data": requestData,
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    vm.options1=_.map(data, function (itemData) {
                                        return {
                                            "text": itemData["userName"],
                                            "value": itemData["id"]
                                        };
                                    });
                                    scheduleVm.setOptions(vm.options1);
                                    dialogVm.title = '分配负责人';
                                    dialogVm.open();
                                }
                            }
                        });
                    }else{
                        scheduleVm.setOptions(vm.options1);
                        dialogVm.title = '分配负责人';
                        dialogVm.open();
                    }
                };
                vm.$scheduleOpts = {//
                    "selectId": getTypeId('fdId'),
                    "options": [],
                    "selectedIndex": 1
                };
                vm.$distributionDialogOpts={
                    "dialogId": getTypeId('distributionDialogId'),
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                            footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                '<div class="ui-dialog-btns">' +
                                '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">确&nbsp;定</button>' +
                                '<span class="separation"></span>' +
                                '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button>' +
                                '</div>' +
                                '</div>';
                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "onClose": function () {},
                    "submit": function (evt) {
                        var dialogVm=avalon.getVm(getTypeId('distributionDialogId')),
                            searchVm = avalon.getVm(getTypeId('fdId')),
                            requestData;
                        pageVm.distributionFd.$model.newUserId = searchVm.selectedValue;
                        requestData=JSON.stringify(pageVm.distributionFd.$model);

                        util.c2s({
                            "url": erp.BASE_PATH + 'fesOnlineProcess/saveSysAssistantMerchant.do',
                            "type": "post",
                            "contentType" : 'application/json',
                            "data": requestData,
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    dialogVm.close();
                                    //pageVm.requestData.userId = '';
                                    updateList(pageVm.requestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);                                }
                            }
                        });
                    }
                };
                /*分配负责人结束*/
                vm.changeStatus= function(value){//小标签切换
                    var paginationVm = avalon.getVm(getTypeId('paginationId'));
                    paginationVm.currentPage = 1;
                    vm.requestData.pageNumber =1;
                    vm.requestData.merchantStatus= value;
                    updateList(pageVm.requestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);
                };
                vm.search=function(){//商户搜索
                    updateList(pageVm.requestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);
                };
                vm.showProgressTip = function () {//步骤说明显示
                    var itemM = this.$vmodel.$model,
                        tip = itemM.el.stepText,
                        tipId = pageName + '-progress-tip';
                    var meEl = $(this),
                        tipEl = $('#' + tipId),
                        meOffset = meEl.offset();
                    if (!tipEl.length) {
                        tipEl = $('<div id="' + tipId + '" class="grid-tip"></div>');
                        tipEl.html('<span class="text-content"></span><span class="icon-arrow"></span>');
                        tipEl.hide().appendTo('body');
                    }
                    $('.text-content', tipEl).text(tip);
                    tipEl.css({
                        "top": meOffset.top - tipEl.height() - 20,
                        "left": meOffset.left - tipEl.width() / 2 - 11
                    }).show();
                };
                vm.hideProgressTip = function () {//步骤说明隐藏
                    $('#' + pageName + '-progress-tip').hide();
                };
                /**
                 * 我的商户，全部商户 -end
                 */

                /**
                 * 代办事项
                 */
                vm.backlogSearch = function(){
                    var paginationVm = avalon.getVm(getTypeId('paginationId'));
                    paginationVm.currentPage = 1;
                    pageVm.backlog.pageNumber =1;
                    updateList(pageVm.backlog.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);

                };
                vm.doToStatus = function(value){
                    var paginationVm = avalon.getVm(getTypeId('paginationId'));
                    paginationVm.currentPage = 1;
                    pageVm.backlog.pageNumber =1;
                    pageVm.backlog.inputItemTypes = value;
                    updateList(pageVm.backlog.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);
                };
                /**
                 * 代办事项 -end
                 */
                /*已完成*/

                /*已完成-end*/
                //分页
                vm.$paginationOpts = {
                    "paginationId": getTypeId('paginationId'),
                    "total": 0,
                    "onJump": function (evt, vm) {
                        var paginationVm = avalon.getVm(getTypeId('paginationId'));
                        if(pageVm.pageStatus == 0 || pageVm.pageStatus ==1){
                            pageVm.requestData.pageNumber=paginationVm.currentPage;
                            updateList(pageVm.requestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);
                        }else if(pageVm.pageStatus == 2){
                            pageVm.backlog.pageNumber=paginationVm.currentPage;
                            updateList(pageVm.backlog.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);
                        }
                    }
                };
                //分页end
            });
            avalon.scan(pageEl[0]);
            /*页面初始化请求渲染*/
            function updateList(obj,url,callBack){
                var requestData=JSON.stringify(obj);
                pageVm.pageData = [];
                util.c2s({
                    "url": erp.BASE_PATH + url,
                    "type": "post",
                    "contentType" : 'application/json',
                    "data": requestData,
                    "success": callBack
                });
            }
            /*页面初始化请求渲染end*/
            updateList(pageVm.backlog.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBackValue);//获取代办事项个数
            /*我的商户回调*/
            updateList(pageVm.requestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);
            function merchantCallBack(responseData){
                responseData = eval(responseData);
                var data,
                    paginationVm = avalon.getVm(getTypeId('paginationId'));
                if (responseData.flag) {
                    data = responseData.data;
                    paginationVm.total = data.totalSize;
                    pageVm.pageDataMerchant= data.rows;
                }
            }
            /*我的商户end*/
            /*代办事项回调*/
            function backlogCallBack(responseData){
                var data,
                    paginationVm = avalon.getVm(getTypeId('paginationId'));
                if (responseData.flag) {
                    data = responseData.data;
                    paginationVm.total = data.totalSize;
                    pageVm.pageDataBacklog= data.rows;
                }
            }
            /*代办事项回调end*/
            function backlogCallBackValue(responseData){//代办事项个数
                var data;
                if (responseData.flag) {
                    data = responseData.data;
                    pageVm.dotolen= data.totalSize;
                }
            }
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


