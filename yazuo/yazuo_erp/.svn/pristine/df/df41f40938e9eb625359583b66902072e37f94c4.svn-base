/**
 * Created by ZHANG on 2014/8/7.
 */
/**
 * 前端服务-下属页面
 */
define(['avalon', 'util', 'moment', '../../../../widget/pagination/pagination', '../../../../module/schedule/schedule'], function (avalon, util, moment) {
    var win = this,
        erp = win.erp,
        pageMod = {};
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName, routeData) {
            var paginationId = pageName + '-pagination',//分页
                scheduleId = pageName + '-schedule';

            var pageVm = avalon.define(pageName, function (vm) {
                vm.navCrumbs = [{
                    "text":"我的主页",
                    "href":"#/frontend/home"
                }, {
                    "text":"我的下属"
                }];
                vm.subId = parseInt(routeData.params["id"]);
                vm.url=erp.BASE_PATH;
                vm.pageData = {};
                vm.statusTabs = 1;//标签切换依赖
                vm.requestData = {//我的商户
                    "merchantStatus" : "",
                    "merchantStatusType" : '0',
                    "userId" : vm.subId,
                    "pageSize" : 10,
                    "pageNumber" : 1,
                    "merchantName" : ""
                };
                vm.backlog = {//待办事项
                    "userId": vm.subId,
                    "inputItemTypes":["01","02"],
                    "merchantName":"",
                    "pageNumber":1,
                    "pageSize":10
                };
                vm.paginationShow = true;
                vm.changeStatus = function(statusId){//切换标签
                    var paginationVm = avalon.getVm(paginationId);
                    pageVm.statusTabs = statusId;
                    paginationVm.currentPage = 1;
                    vm.paginationShow = true;
                    if(statusId == 1){
                        pageVm.requestData.pageNumber = 1;
                        updateList(pageVm.requestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);
                    }else if(statusId == 2){
                        pageVm.backlog.pageNumber = 1;
                        updateList(pageVm.backlog.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);
                    }else if(statusId == 3){
                        vm.paginationShow = false;
                    }
                };
                vm.changeMerchantStatus = function(statusId){//我的商户
                    vm.requestData.merchantStatus= statusId;
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
                vm.doToStatus = function(value){//代办事项
                    pageVm.backlog.inputItemTypes = value;
                    updateList(pageVm.backlog.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);
                };

                //分页
                vm.$paginationOpts = {
                    "paginationId": paginationId,
                    "total": 0,
                    "onJump": function (evt, vm) {
                        var paginationVm = avalon.getVm(paginationId);
                        if(pageVm.statusTabs == 1){
                            pageVm.requestData.pageNumber=paginationVm.currentPage;
                            updateList(pageVm.requestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);
                        }else if(pageVm.statusTabs == 2){
                            pageVm.backlog.pageNumber=paginationVm.currentPage;
                            updateList(pageVm.backlog.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);
                        }else if(pageVm.statusTabs == 3){
                            //pageVm.backlog.pageNumber=paginationVm.currentPage;
                            //updateList(pageVm.backlog.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);
                        }
                    }
                };
                vm.$scheduleOpts = {
                    "scheduleId": scheduleId,
                    "onFetchPlan": function (requestData, callback) {
                        //查询本月的计划信息
                        util.c2s({
                            "url": erp.BASE_PATH + "fesPlan/queryFesPlanList.do",
                            "type": "post",
                            "contentType":"application/json",
                            "data": JSON.stringify(_.extend({
                                "userId": vm.subId
                            }, requestData)),
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    callback(data);
                                } else {
                                    callback([]);
                                }
                            }
                        });
                    },
                    "onClickDateCell": function (evt) {
                        var itemM = this.$vmodel.$model,
                            cellDate = itemM.el.date,
                            targetEl = $(evt.target),
                            now = new Date();
                        if (!moment(cellDate).isBefore(now, 'day')) {   //今天或以后日期点击跳转到添加计划页
                            if (targetEl.is('.plan-item')) {   //跳到相应的编辑页
                                util.jumpPage('#/frontend/home/subordinates/plan/list/' + moment(cellDate) / 1 + '/' + targetEl.data('planId'));  //跳到单独的plan查看
                            } else {    //默认跳到添加页面
                                util.jumpPage('#/frontend/home/subordinates/assign');
                            }
                        } else {
                            if (targetEl.is('.plan-item')) {
                                util.jumpPage('#/frontend/home/subordinates/plan/list/' + moment(cellDate) / 1 + '/' + targetEl.data('planId'));  //跳到单独的plan查看
                            }
                        }
                    }
                };
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
            /*我的商户，全部商户回调*/
            updateList(pageVm.requestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);
            function merchantCallBack(responseData){
                responseData = eval(responseData);
                var data,
                    paginationVm = avalon.getVm(paginationId);
                if (responseData.flag) {
                    data = responseData.data;
                    //流程进度排序
                    function compare(a,b){
                        if(a.stepId > b.stepId){
                            return 1;
                        }
                    }
                    for(var i= 0,len= data.rows.length;i<len;i++){
                        data.rows[i].listSteps.sort(compare);
                    }
                    //流程进度排序结束
                    //拼健康度
                    for(var i= 0,len= data.rows.length;i<len;i++){
                        var healthType=[{index:'1',health:0},{index:'2',health:0},{index:'3',health:0},{index:'4',health:0}];
                        var arr= data.rows[i].synHealthDegrees;
                        for(var j= 0,lenArr= arr.length;j<lenArr;j++){
                            var obj= {};
                            obj.index = arr[j].targetType;
                            if(arr[j].healthDegree<100){
                                obj.health = 0;
                            }else{
                                obj.health = 1;
                            }
                            for(var m= 0,lenArrs= healthType.length;m<lenArrs;m++){
                                if(healthType[m].index == obj.index){
                                    healthType[m] = obj;
                                }
                            }
                        }
                        data.rows[i].healthType = healthType;
                    }

                    //拼健康度结束
                    paginationVm.total = data.totalSize;
                    pageVm.pageData= data.rows;
                    pageVm.requestData.merchantName = '';
                }
            }
            /*我的商户，全部商户回调end*/
            /*代办事项回调*/
            function backlogCallBack(responseData){
                var data,
                    paginationVm = avalon.getVm(paginationId);
                if (responseData.flag) {
                    data = responseData.data;
                    paginationVm.total = data.totalSize;
                    pageVm.pageData= data.rows;
                    pageVm.backlog.merchantName = '';

                }
            }
            /*代办事项回调end*/
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var paginationId = pageName + '-pagination',//分页
                scheduleId = pageName + '-schedule';
            avalon.getVm(paginationId)&&$(avalon.getVm(paginationId).widgetElement).remove();
            avalon.getVm(scheduleId)&&$(avalon.getVm(scheduleId).widgetElement).remove();
        }
    });

    return pageMod;
});
