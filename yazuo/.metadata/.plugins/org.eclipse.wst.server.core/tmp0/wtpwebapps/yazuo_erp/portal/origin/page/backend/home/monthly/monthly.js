/**
 * 后端服务-月报讲解
 */
define(['avalon', 'util', 'json', '../../../../module/persontree/persontree', '../../../../widget/form/select', '../../../../widget/calendar/calendar', '../../../../module/visit/visit', '../../../../module/visitinfo/visitinfo', '../../../../widget/dialog/dialog', '../../../../widget/form/form'], function (avalon, util, JSON) {
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
                    "href":"#/backend/home"
                }, {
                    "text":'test'
                }];
                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.requestId = routeData.params["id"]/1;
                vm.path=erp.BASE_PATH;
                vm.showHide = 'hide';//标题内容收起展开
                vm.id = '';//需求id
                vm.merchantId = '';//商户id
                vm.pageStatus = '';//页面状态
                //查看回访单信息
                vm.readQuestions = [];//查看回访单信息
                //title信息如下：
                vm.title = '';//大标题
                vm.titleCon = '';//讲解内容
                vm.titleStatus = '';//讲解状态
                //头部附件信息
                vm.attachments = [];//附件[数组]
                vm.operstionLogs = [];//流水
                //需求信息如下：
                vm.insertTime = '';//需求创建时间
                vm.merchantName = '';//商户
                vm.contactName = '';//联系人
                vm.linkType = '';//联系人所属类型
                vm.mobilePhone = '';//联系人电话
                vm.dicRowSourceCat = '';//来源
                vm.resourceName = '';//类型
                vm.resourceType = '';//类型所属类型
                vm.dicRowHandler = '';//处理人
                vm.fesUserName = '';//前端负责人
                vm.fesUserTel = '';//前端负责人电话



                vm.permission = {//权限
                    assign_141 : util.hasPermission("assign_141")//指派
                };
                vm.selectShowHide = function(val){//控制显示隐藏
                    vm.showHide = val;
                };

                /*放弃*/
                vm.$quitDialogOpts = {
                    "dialogId" : getTypeId("-quitDialog"),
                    "title" : "放弃",
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                            footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                '<div class="ui-dialog-btns">' +
                                '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">保&nbsp;存</button>' +
                                '<span class="separation"></span>' +
                                '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button>' +
                                '</div>' +
                                '</div>';
                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "onOpen": function () {},
                    "onClose": function () {
                        var formVm = avalon.getVm(getTypeId('-quitForm'));
                        formVm.remark = '';
                        formVm.reset();
                    },
                    "submit": function (evt) {
                        var requestData,
                            dialogVm = avalon.getVm(getTypeId("-quitDialog")),
                            formVm = avalon.getVm(getTypeId("-quitForm"));
                        if (formVm.validate()) {
                            util.c2s({
                                "url": erp.BASE_PATH + "besRequirement/saveQuitOrComplete.do",
                                "data": JSON.stringify(
                                    {
                                        "id": pageVm.requestId,  //需求的ID
                                        "btnFlag": "1",  //按钮标识   1：放弃    其他 完成
                                        "remark": formVm.remark  //只有btnFlag为1 的时候有效
                                    }
                                ),
                                "contentType" : 'application/json',
                                "success": function (responseData) {
                                    var data;
                                    if (responseData.flag) {
                                        data = responseData.data;
                                        pageVm.operstionLogs = data;
                                        dialogVm.close();
                                        pageVm.pageStatus = 3;
                                    }
                                }
                            });
                        }
                    }
                };
                vm.$quitFormOpts = {
                    "formId": getTypeId("-quitForm"),
                    "field":
                        [{
                            "selector": ".quit-description",
                            "name": "remark",
                            "rule": ["noempty", function (val, rs) {
                                if(val.length>250){
                                    return "内容不能超过250个字！";
                                }
                                if (rs[0] !== true) {
                                    return "内容不能为空";
                                } else {
                                    return true;
                                }
                            }]
                        }
                        ],
                    "remark":""
                };
                /*放弃-end*/
                /*回访记录*/
                vm.$visitOpts = {
                    "visitId": getTypeId('-visitModule'),
                    "id" : '',
                    "name" : '',
                    "mobilePhone" : '',
                    "list" : [],
                    "callFnFailure" : function(responseData){//回访电话失败
                        if (responseData.flag) {
                            var data = responseData.data;
                            pageVm.operstionLogs = data;
                        }
                    },
                    "callFnSuccess" : function(){//回访电话成功
                        util.c2s({
                            "url": erp.BASE_PATH + "besRequirement/loadTelConnectPage.do",
                            "data": JSON.stringify(
                                {
                                    "id": pageVm.id,  //需求ID
                                    "documentType": "4",  //问卷文档类型  月报为4
                                    "merchantId": pageVm.merchantId, //商户ID
                                    "takingSkillResource": "re_visite_151" //话术类型编码
                                }
                            ),
                            "contentType" : 'application/json',
                            "success": function (responseData) {
                                var data;
                                var dialogVm = avalon.getVm(getTypeId('-visitInfoModule'));
                                if (responseData.flag) {
                                    data = responseData.data;
                                    dialogVm.language = data['talkingSkills'];//话术
                                    dialogVm.questions = data.document.sysDocumentDtlList;//vm.responseToData(data['questions']);//问题
                                    dialogVm.logName = data['callRecord']['contactMap']['name'];//回访记录姓名
                                    dialogVm.logMobilePhone = data['callRecord']['contactMap']['mobile_phone'];//回访记录电话
                                    dialogVm.linkArr = data['changeContact']['contactCat'];//变更联系人数组
                                    dialogVm.documentType = 4;
                                    dialogVm.requirementId = vm.id;//需求的ID
                                    dialogVm.merchantId = vm.merchantId;//商户id
                                    dialogVm.open();
                                }
                            }
                        });
                    }
                };
                vm.addVisit = function () {
                    var type= $(this).attr('data-type');
                    /*1=回访，2=再次回访，3=放弃，4=完成，5=指派处理人，6=查看回访单*/
                    if(type == 1||type == 2){
                        var dialog = avalon.getVm(getTypeId('-visitModule'));
                        dialog.open()
                    }else if(type == 3){
                        avalon.getVm(getTypeId("-quitDialog")).open();
                    }else if(type == 4){
                        util.c2s({
                            "url": erp.BASE_PATH + "besRequirement/saveQuitOrComplete.do",
                            "data": JSON.stringify(
                                {
                                    "id": pageVm.requestId,  //需求的ID
                                    "btnFlag": "2"  //按钮标识   1：放弃    其他 完成
                                }
                            ),
                            "contentType" : 'application/json',
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    pageVm.operstionLogs = data;
                                    pageVm.pageStatus = 3;
                                }
                            }
                        });
                    }else if(type == 5){
                        vm.assign();
                    }else if(type == 6){
                        util.c2s({
                            "url": erp.BASE_PATH + '/besRequirement/getDocumentDtls.do',
                            "type": "post",
                            "contentType": 'application/json',
                            "data": JSON.stringify({id:vm.requestId,documentType:'4'}),
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    var data = responseData.data;
                                    vm.readQuestions = data;
                                    avalon.getVm(getTypeId("-questionsDialogId")).open();
                                }
                            }
                        });
                    }
                };
                /*回访记录-end*/
                /*指派处理人*/
                vm.$treeOpts = {//指派处理人
                    "persontreeId" : getTypeId('-persontree'),
                    "merchantId" : '',
                    "ids" : [],
                    "callFn" : function(responseData){
                        updatePage();
                    }
                };
                vm.assign = function(){//指派处理人
                    avalon.getVm(getTypeId('-persontree')).ids.push(pageVm.id);
                    avalon.getVm(getTypeId('-persontree')).open();
                };
                /*指派处理人end*/
                /*回访记录-详细*/
                vm.getArrData = function(arr){//格式化回访单数据
                    var tmpArr = [];
                    for(var i=0;i<arr.length;i++){
                        var obj = {};
                        obj.optionContent = arr[i]['optionContent'];
                        obj.isSelected = arr[i]['isSelected'];
                        obj.isOpenTextarea = arr[i]['isOpenTextarea'];
                        obj.tip = arr[i]['tip'];
                        obj.isOpenTextarea = arr[i]['isOpenTextarea'];
                        if(arr[i]['comment']){
                            obj.comment = arr[i]['comment'];
                        }else{
                            obj.comment = '';
                        }
                        tmpArr.push(obj);
                    }
                    return tmpArr;
                };
                vm.responseToData = function(data){//格式化回访单数据
                    var arr = [];
                    for(var i=0;i<data.length;i++){
                        var obj = {};
                        obj.questionId = data[i]['id'];
                        obj.questionType = data[i]['question_type'];
                        obj.title = data[i]['title'];
                        obj.tip = data[i]['tip'];
                        if(data[i]['comment']){
                            obj.comment = data[i]['comment'];
                        }else{
                            obj.comment = '';
                        }
                        if(data[i]['sysQuestionOptionList']){
                            obj.sysDocumentDtlOptionList = vm.getArrData(data[i]['sysQuestionOptionList']);
                        }
                        arr.push(obj);
                    }
                    return arr;
                };
                vm.$visitInfoOpts = {
                    "visitinfoId": getTypeId('-visitInfoModule'),
                    "requirementId" : 0,//需求的ID
                    "merchantId" : 0,//商户ID
                    "logName" : '',
                    "logMobilePhone" : '',
                    "language" : [],
                    "questions" : [],
                    "linkArr" : [],
                    "callFn" : function(responseData,linkData){
                        var data = responseData.data;
                        var visitVm = avalon.getVm(getTypeId('-visitModule'));
                        pageVm.operstionLogs = data;
                        pageVm.contactName = linkData.name;//联系人
                        pageVm.mobilePhone = linkData.mobilePhone;//联系人电话
                        visitVm.name = linkData.name;
                        visitVm.mobilePhone = linkData.mobilePhone;
                    }
                };
                /*回访记录-详细-end*/
                /*查看回访单*/

                vm.$questionsDialogOpts = {
                    "dialogId": getTypeId("-questionsDialogId"),
                    "title": '查看回访单',
                    "autoFocusInput": false,
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                            footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                '<div class="ui-dialog-btns">' +
                                '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">关&nbsp;闭</button>' +
                                '</div>' +
                                '</div>';
                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "width": 600,
                    "readonly": false,
                    "onOpen": function () {},
                    "onClose": function () {},
                    "submit": function (evt) {

                    }
                };
                /*查看回访单-end*/
            });
            avalon.scan(pageEl[0]);
            /*页面初始化请求渲染*/
            function updatePage(){
                util.c2s({
                    "url": erp.BASE_PATH + "besRequirement/getBesRequirement.do",
                    "data": JSON.stringify({"id": pageVm.requestId}),
                    "contentType" : 'application/json',
                    "success": function (responseData) {
                        var data;
                        var visitVm = avalon.getVm(getTypeId('-visitModule'));
                        if (responseData.flag) {
                            data = responseData.data;
                            pageVm.merchantId = data['besRequirement']['merchantId'];//商户ID
                            pageVm.id = data['besRequirement']['id'];//需求id
                            pageVm.pageStatus = data['besRequirement']['status'];//页面状态
                            pageVm.title = data['besRequirement']['title'];//页面标题
                            pageVm.titleCon = data['besRequirement']['content'];//讲解内容
                            pageVm.titleStatus = data['besRequirement']['reVisitedText'];//讲解状态
                            pageVm.attachments = data['fesPlan']['attachments'];//附件
                            pageVm.merchantName = data['besRequirement']['merchantName'];//商户
                            pageVm.contactName = data['besRequirement']['mktContact']['name'];//联系人
                            pageVm.linkType = data['besRequirement']['dicRowContactCat']['text'];//联系人所属类型
                            pageVm.mobilePhone = data['besRequirement']['mktContact']['mobilePhone'];//联系人电话
                            pageVm.dicRowSourceCat = data['besRequirement']['dicRowSourceCat']['text'];//来源
                            pageVm.resourceName = data['besRequirement']['resourceName'];//类型
                            pageVm.resourceType = data['besRequirement']['resourceExtraName'];//类型所属类型
                            pageVm.dicRowHandler = data['besRequirement']['dicRowHandler']['userName'];//处理人
                            pageVm.fesUserName = data['besRequirement']['fesUserName'];//前端负责人
                            pageVm.insertTime = data['besRequirement']['insertTime'];//需求创建时间
                            pageVm.fesUserTel = data['besRequirement']['fesUserTel'];//前端负责人电话
                            pageVm.operstionLogs = data['besRequirement']['operstionLogs'];
                            //回访记录内容
                            visitVm.id= data['besRequirement']['id'];
                            visitVm.name = data['popUpDiv']['userName'];
                            visitVm.mobilePhone = data['popUpDiv']['mobilePhone'];
                            visitVm.list = data['popUpDiv']['revisteDic'];
                            //页面面包屑
                            pageVm.navCrumbs[1]['text'] = pageVm.title;
                        }
                    }
                });
            }
            updatePage();
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


