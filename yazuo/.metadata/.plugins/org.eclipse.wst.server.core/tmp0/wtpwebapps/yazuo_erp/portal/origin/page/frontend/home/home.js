/**
 * 前端服务-我的主页
 */
define(['avalon', 'util', 'moment', '../../../widget/pagination/pagination','../../../widget/form/select','../../../widget/calendar/calendar', "../../../widget/autocomplete/autocomplete"], function (avalon, util, moment) {
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
                vm.permission = {//权限
                    "active" : util.hasPermission("fes_act_require"),
                    "remaind" : util.hasPermission("fes_remaind"),
                    "question_mag" : util.hasPermission("fes_question_mag"),
                    "address" : util.hasPermission("fes_address"),
                    "attach_mag" : util.hasPermission("fes_attach_mag"),
                    "email_temp" : util.hasPermission("fes_email_temp"),
                    "complain" : util.hasPermission("fes_complain"),
                    "performance" : util.hasPermission("fes_performance"),
                    "my_mechant" : util.hasPermission("fes_my_mechant"),
                    "all_mechant" : util.hasPermission("fes_all_mechant"),
                    "my_subordinate" : util.hasPermission("fes_my_subordinate"),
                    "homepage_todolist" : util.hasPermission("fes_homepage_todolist"),
                    "assign_p_incharge" : util.hasPermission("fes_assign_p_incharge"),
                    "delete_170" : util.hasPermission("delete_170")//删除
                };
                vm.localHref = function(url){//页面跳转
                    util.jumpPage(url);
                };

                vm.userImgPath = erp['appData']['user']['userImagePath']||'user-upload-img/pic/userPhoto/real/';
                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.pageStatus= 1;//大标签
                vm.tabsMerchantData = {};//我的商户和全部商户
                vm.tabsUnderData = {};//我的下属
                vm.tabsLogData = [];//代办事项
                vm.options1=[];
                vm.dotolen = 0;//代办事项个数
                vm.distributionFd = {
                    userId : '',
                    oldUserId : '',
                    merchantId : ''
                };//分配负责人
                vm.subordinates = {//我的下属
                    "subUserName" : "",
                    "baseUserId" : vm.pageUserId,
                    "pageSize" : 10,
                    "pageNumber" : 1
                };
                vm.backlog = {//待办事项
                    "userId": vm.pageUserId,
                    "inputItemTypes":["01","02"],
                    "merchantName":"",
                    "pageNumber":1,
                    "pageSize":10,
                    "itemStatus": 0,
                    "businessTypes":['2']
                };
                vm.requestData= {//我的商户，全部商户
                    "merchantStatus" : "",
                    "userId" : vm.pageUserId,
                    "pageSize" : 10,
                    "pageNumber" : 1,
                    "merchantName" : "",
                    "merchantStatusType" : 0
                };

                //(我的商户&全部商户)页面跳转时附加回退时候信息
                vm.callBackInfo = '';
                vm.localHrefInfo = function(){//(我的商户&全部商户)页面跳转时附加回退时候信息
                    var href = $(this).attr('data-href');
                    var merchantName = $(this).attr('data-name');
                    var text1,text2,text3,href1,href2;
                    href1 = "#/frontend/home/"+encodeURIComponent(JSON.stringify({'pageStatus':vm.pageStatus}));
                    href2 = "#/frontend/home/"+encodeURIComponent(JSON.stringify({'pageStatus':vm.pageStatus,merchantStatus:vm.requestData.merchantStatus}));
                    if(vm.pageStatus == 1){
                        text1 = '我的商户';

                    }else if(vm.pageStatus == 0){
                        text1 = '全部商户';
                    }
                    if(vm.requestData.merchantStatus == ''){
                        text2 = '全部';
                    }else if(vm.requestData.merchantStatus == 0){
                        text2 = '未上线商户';
                    }else if(vm.requestData.merchantStatus == 1){
                        text2 = '已上线商户';
                    }else if(vm.requestData.merchantStatus == 2){
                        text2 = '正常商户';
                    }else if(vm.requestData.merchantStatus == 3){
                        text2 = '问题商户';
                    }else if(vm.requestData.merchantStatus == 4){
                        text2 = '危险商户';
                    }
                    text3 = merchantName;
                    var info = [
                        {
                            "text":'我的主页',
                            "href":'#/frontend/home'
                        },
                        {
                            "text":text1,
                            "href":href1
                        },
                        {
                            "text":text2,
                            "href":href2
                        },
                        {
                            "text":text3
                        }
                    ];
                    var url = href+'/'+encodeURIComponent(JSON.stringify(info));
                    util.jumpPage(url);
                };
                //页面跳转返回（根据反正值请求相应的接口）
                vm.callBackInfo = routeData.params["info"];
                vm.callBackInfoFn = function(){
                    var data = JSON.parse(decodeURIComponent(vm.callBackInfo));
                    pageVm.pageStatus = data.pageStatus;
                    pageVm.requestData.merchantStatus = data.merchantStatus;
                    if(data.pageStatus == 1){
                        vm.requestData.userId = vm.pageUserId;
                    }else{
                        vm.requestData.userId = '';
                    }
                    if(data.merchantStatus){
                        vm.requestData.merchantStatus = data.merchantStatus;
                    }else{
                        vm.requestData.merchantStatus = '';
                    }
                    updateList(vm.requestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);
                };
                vm.changePageStatus= function(value,obj){//大标签切换
                    vm.pageStatus= value;
                    var paginationVm = avalon.getVm(getTypeId("paginationId"));
                    paginationVm.currentPage = 1;
                    if(obj == 'requestData'){//我的商户，全部商户
                        if(value == 1){
                            vm.requestData.$model.userId = vm.pageUserId;
                        }else{
                            vm.requestData.$model.userId = '';
                        }
                        pageVm.requestData.merchantStatus= '';
                        pageVm.requestData.pageNumber = 1;
                        pageVm.requestData.merchantName= '';
                        updateList(pageVm.requestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);
                    }else if(obj == 'subordinatesData'){//我的下属
                        pageVm.subordinates.pageNumber = 1;
                        pageVm.requestData.subUserName= '';
                        updateList(pageVm.subordinates.$model,"group/getSubordinateEmployees.do",subordinatesCallBack);
                    }else if(obj == 'backlog'){//代办事项
                        pageVm.backlog.pageNumber = 1;
                        pageVm.requestData.merchantName= '';
                        updateList(pageVm.backlog.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);
                    }
                };
                //代办事项状态（未完成、已完成）
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
                };


                //导出营销报告
                var date = new Date();
                var curYear = date.getFullYear();
                var curMonth = date.getMonth();
                vm.importReport = function(){


                    var dialogVm = avalon.getVm(getTypeId('importReportDialogId'));
                    dialogVm.open();
                };
                vm.$importReportOpts = {
                    "title": '导出',
                    "dialogId": getTypeId('importReportDialogId'),
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
                    "onClose": function () {
                        var formVm = avalon.getVm(getTypeId('addEditFormId'));
                        formVm.beginTime = '';
                        formVm.endTime = '';
                        formVm.reset();
                    },
                    "submit": function (evt) {
                        var formVm = avalon.getVm(getTypeId('addEditFormId'));
                        if (formVm.validate()) {
                            var requestData = formVm.getFormData();
                            requestData.beginTime = moment(requestData.beginTime)/1;
                            requestData.endTime = (moment(requestData.endTime).add('days', 1)/1)-1000;
                            window.location = erp.BASE_PATH + "fesReport/exportReport.do?startTime="+requestData.beginTime+'&endTime='+requestData.endTime;
                            avalon.getVm(getTypeId('importReportDialogId')).close();
                        }
                    }
                };

                vm.$startDateOpts = {
                    "calendarId": getTypeId('startDateCalendarId'),
                    "minDate": new Date(),
                    "onClickDate": function (d) {
                        var formVm = avalon.getVm(getTypeId('addEditFormId'));
                        formVm.beginTime = moment(d).format('YYYY-MM-DD');
                        $(this.widgetElement).hide();
                    }
                };

                vm.openStartDateCalendar = function () {
                    var formVm = avalon.getVm(getTypeId('addEditFormId'));
                    var meEl = $(this),
                        calendarVm = avalon.getVm(getTypeId('startDateCalendarId')),
                        calendarEl,
                        inputOffset = meEl.offset();
                    if (!calendarVm) {
                        calendarEl = $('<div ms-widget="calendar,$,$startDateOpts"></div>');
                        calendarEl.css({
                            "position": "absolute",
                            "z-index": 10000
                        }).hide().appendTo('body');
                        avalon.scan(calendarEl[0], [vm]);
                        calendarVm = avalon.getVm(getTypeId('startDateCalendarId'));
                        //注册全局点击绑定
                        util.regGlobalMdExcept({
                            "element": calendarEl,
                            "handler": function () {
                                calendarEl.hide();
                            }
                        });
                    } else {
                        calendarEl = $(calendarVm.widgetElement);
                    }
                    //设置focus Date
                    if (formVm.beginTime) {
                        calendarVm.focusDate = moment(formVm.beginTime, 'YYYY-MM-DD')._d;
                    } else {
                        calendarVm.focusDate = new Date();
                    }

                    if (formVm.endTime) {
                        calendarVm.focusDate = moment(formVm.endTime, 'YYYY-MM-DD')._d;
                        calendarVm.minDate = moment(formVm.endTime, 'YYYY-MM')._d;
                        calendarVm.maxDate = moment(formVm.endTime, 'YYYY-MM-DD')._d;
                    }else{
                        calendarVm.minDate = moment(formVm.endTime, 'YYYY-MM')._d;
                        calendarVm.maxDate = moment().subtract(1, 'day')._d;
                    }
                    calendarEl.css({
                        "top": inputOffset.top + meEl.outerHeight() - 1,
                        "left": inputOffset.left
                    }).show();
                };
                vm.$endDateOpts = {
                    "calendarId": getTypeId('endDateCalendarId'),
                    "minDate": new Date(),
                    "onClickDate": function (d) {
                        var formVm = avalon.getVm(getTypeId('addEditFormId'));
                        formVm.endTime = moment(d).format('YYYY-MM-DD');
                        $(this.widgetElement).hide();
                    }
                };
                vm.openEndDateCalendar = function () {
                    var formVm = avalon.getVm(getTypeId('addEditFormId'));
                    var meEl = $(this),
                        calendarVm = avalon.getVm(getTypeId('endDateCalendarId')),
                        calendarEl,
                        inputOffset = meEl.offset();
                    if (!calendarVm) {
                        calendarEl = $('<div ms-widget="calendar,$,$endDateOpts"></div>');
                        calendarEl.css({
                            "position": "absolute",
                            "z-index": 10000
                        }).hide().appendTo('body');
                        avalon.scan(calendarEl[0], [vm]);
                        calendarVm = avalon.getVm(getTypeId('endDateCalendarId'));
                        //注册全局点击绑定
                        util.regGlobalMdExcept({
                            "element": calendarEl,
                            "handler": function () {
                                calendarEl.hide();
                            }
                        });
                    } else {
                        calendarEl = $(calendarVm.widgetElement);
                    }
                    //设置focus Date
                    if (formVm.endTime) {
                        calendarVm.focusDate = moment(formVm.endTime, 'YYYY-MM-DD')._d;
                    } else {
                        calendarVm.focusDate = new Date();
                    }
                    if (formVm.beginTime) {
                        calendarVm.focusDate = moment(formVm.beginTime, 'YYYY-MM-DD')._d;
                        if(moment(formVm.beginTime, 'YYYY-MM')/1 == moment(moment().format('YYYY-MM-DD'), 'YYYY-MM')/1){
                            calendarVm.minDate = moment(formVm.beginTime, 'YYYY-MM-DD')._d;
                            calendarVm.maxDate = moment().subtract(1, 'day')._d;
                        }else {
                            calendarVm.minDate = moment(formVm.beginTime, 'YYYY-MM-DD')._d;
                            calendarVm.maxDate = moment(formVm.beginTime, 'YYYY-MM').add(1,'month').subtract(1, 'day')._d;
                        }
                    }else{
                        calendarVm.minDate = moment(moment()._d, 'YYYY-MM')._d;
                        calendarVm.maxDate = moment().subtract(1, 'day')._d;
                    }
                    calendarEl.css({
                        "top": inputOffset.top + meEl.outerHeight() - 1,
                        "left": inputOffset.left
                    }).show();
                };
                vm.$addEditFormOpts = {//表单验证
                    "formId": getTypeId('addEditFormId'),
                    "field": [
                        {
                            "selector": ".beginTime",
                            "name": "beginTime",
                            "rule": ["noempty", function (val, rs) {
                                if (rs[0] !== true) {
                                    return "开始时间不能为空";
                                } else {
                                    return true;
                                }
                            }]
                        },
                        {
                            "selector": ".endTime",
                            "name": "endTime",
                            "rule": ["noempty", function (val, rs) {
                                if (rs[0] !== true) {
                                    return "结束时间不能为空";
                                } else {
                                    return true;
                                }
                            }]
                        }
                    ],
                    "beginTime": "",
                    "endTime": ""
                };
                //导出营销报告end



                /**
                 * 我的商户，全部商户
                 */
                vm.changeStatus= function(value){//小标签切换
                    vm.requestData.merchantStatus= value;
                    var paginationVm = avalon.getVm(getTypeId("paginationId"));
                    paginationVm.currentPage = 1;
                    vm.requestData.pageNumber = 1;
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

                /*分配负责人*/
                vm.distribution = function(){
                    var dialogVm = avalon.getVm(getTypeId('distributionDialogId'));
                    var requestData={"remark":"fes_my_main_page"};
                    requestData=JSON.stringify(requestData);
                    if(!this.$vmodel.$model.el.listUsers[0]){
                        vm.distributionFd.oldUserId = "";
                    }else{
                        vm.distributionFd.oldUserId = this.$vmodel.$model.el.listUsers[0].id;
                        avalon.getVm(getTypeId('fdId')).inputText = this.$vmodel.$model.el.listUsers[0].userName;
                    }
                    vm.distributionFd.merchantId = this.$vmodel.$model.el.merchantId;

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
                                    dialogVm.title = '分配负责人';
                                    dialogVm.open();
                                }
                            }
                        });
                    }else{
                        dialogVm.title = '分配负责人';
                        dialogVm.open();
                    }
                };

                vm.$brandOpts = {
                    "autocompleteId": getTypeId('fdId'),
                    "placeholder": "请指定负责人",
                    "delayTime": 300,   //延时300ms请求
                    "onChange": function (text, callback) {
                        pageVm.distributionFd.userId = '';
                        if (text.length) {
                            var rows = _.filter(pageVm.options1.$model, function (itemData) {
                                return (itemData.text.indexOf(text) != -1);
                            });
                            callback(rows);
                        } else {
                            callback([]);   //空查询显示空
                        }
                    },
                    "onSelect": function (selectedValue) {
                        var dialogVm=avalon.getVm(getTypeId('distributionDialogId'));
                        pageVm.distributionFd.userId = selectedValue;
                        util.testing($(dialogVm.widgetElement)[0],$('.auto-complete'));
                    }
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
                    "onClose": function () {
                        avalon.getVm(getTypeId('fdId')).inputText = '';
                    },
                    "submit": function (evt) {
                        var flag = true;
                        var dialogVm=avalon.getVm(getTypeId('distributionDialogId')),
                        flag = util.testing($(dialogVm.widgetElement)[0]);
                        if(flag){
                            util.c2s({
                                "url": erp.BASE_PATH + 'myHomePage/assignPersionForMerchant.do',
                                "type": "post",
                                "contentType" : 'application/json',
                                "data": JSON.stringify(pageVm.distributionFd.$model),
                                "success": function (responseData) {
                                    if (responseData.flag == 1) {
                                        dialogVm.close();
                                        updateList(pageVm.requestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);
                                    }
                                }
                            });
                        }

                    }
                };
                /*分配负责人结束*/

                /*删除商户*/
                vm.removeMerchant = function(){
                    var merchantId = $(this).attr('data-merchantId');
                    util.confirm({
                        "content": '你确定要删除此商户吗？',
                        "onSubmit": function () {
                            util.c2s({
                                "url": erp.BASE_PATH + 'synMerchant/deleteSynMerchant.do',
                                "type": "post",
                                "contentType": 'application/json',
                                "data": JSON.stringify({"merchantId":merchantId}),
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        updateList(pageVm.requestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);
                                    }
                                }
                            });
                        }
                    });
                };
                /*删除商户-end*/
                /**
                 * 我的商户，全部商户 -end
                 */
                /**
                 * 我的下属
                 */
                vm.subordinatesSearch = function(){
                    updateList(pageVm.subordinates.$model,"group/getSubordinateEmployees.do",subordinatesCallBack);
                };
                /**
                 * 我的下属 -end
                 */
                /**
                 * 代办事项
                 */
                vm.backlogSearch = function(){
                    updateList(pageVm.backlog.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);

                };
                vm.doToStatus = function(value){
                    var paginationVm = avalon.getVm(getTypeId("paginationId"));
                    paginationVm.currentPage = 1;
                    pageVm.backlog.pageNumber = 1;
                    pageVm.backlog.inputItemTypes = value;
                    updateList(pageVm.backlog.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);

                };
                /**
                 * 代办事项 -end
                 */
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
                            pageVm.subordinates.pageNumber=paginationVm.currentPage;
                            updateList(pageVm.subordinates.$model,"group/getSubordinateEmployees.do",subordinatesCallBack);
                        }else if(pageVm.pageStatus == 3){
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
                util.c2s({
                    "url": erp.BASE_PATH + url,
                    "type": "post",
                    "contentType" : 'application/json',
                    "data": requestData,
                    "success": callBack
                });
            }
            /*
            * 根据权限判断加载相应的页面
            * 有我的商户加载我的商户，无我的商户加载全部商户，无全部商户加载我的下属，无我的下属加载代办事项
            * */

            if(pageVm.permission.my_mechant){
                pageVm.pageStatus= 1;
                //判断调用哪个接口(导航条)
                if(pageVm.callBackInfo){
                    pageVm.callBackInfoFn();
                }else{
                    updateList(pageVm.requestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);
                }
            }else if(pageVm.permission.all_mechant){
                pageVm.pageStatus= 0;
                pageVm.requestData.userId = '';
                if(pageVm.callBackInfo){
                    pageVm.callBackInfoFn();
                }else{
                    updateList(pageVm.requestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);
                }
            }else if(pageVm.permission.my_subordinate){
                pageVm.pageStatus= 2;
                updateList(pageVm.subordinates.$model,"group/getSubordinateEmployees.do",subordinatesCallBack);
            }else if(pageVm.permission.homepage_todolist){
                pageVm.pageStatus= 3;
                updateList(pageVm.backlog.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);
            }


            /*页面初始化请求渲染end*/
            updateList(pageVm.backlog.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBackValue);//获取代办事项个数
            /*我的商户，全部商户回调*/
            function merchantCallBack(responseData){
                responseData = eval(responseData);
                var data,
                    paginationVm = avalon.getVm(getTypeId('paginationId'));
                if (responseData.flag) {
                    data = responseData.data;
                    //流程进度排序
                    function compare(a,b){
                        if(a.stepId > b.stepId){
                            return 1;
                        }else{
                            return -1;
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
                    pageVm.tabsMerchantData= data.rows;
                }
            }
            /*我的商户，全部商户回调end*/
            /*我的下属回调*/
            function subordinatesCallBack(responseData){
                var data,
                    paginationVm = avalon.getVm(getTypeId('paginationId'));
                if (responseData.flag) {
                    data = responseData.data;
                    paginationVm.total = data.totalSize;
                    pageVm.tabsUnderData= data.rows;

                }
            }
            /*我的下属回调end*/
            /*代办事项回调*/
            function backlogCallBack(responseData){
                var data,
                    paginationVm = avalon.getVm(getTypeId('paginationId'));
                if (responseData.flag) {
                    data = responseData.data;
                    paginationVm.total = pageVm.dotolen = data.totalSize;
                    pageVm.tabsLogData= data.rows;
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


