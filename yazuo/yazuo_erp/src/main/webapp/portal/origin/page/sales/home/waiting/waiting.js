/**
 * 销售服务-待办事项
 */
define(['avalon', 'util', 'json','moment', '../../../../widget/dialog/dialog', '../../../../widget/form/select', '../../../../widget/form/form', '../../../../widget/calendar/calendar', '../../../../widget/uploader/uploader', '../../../../module/interview/interview', '../../../../module/survey/survey', '../../../../module/address/address'], function (avalon, util,JSON) {
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
            var loginUserData = erp.getAppData('user');
            var pageVm = avalon.define(pageName, function (vm) {
                vm.navCrumbs = [{
                    "text":"我的主页",
                    "href":"#/sales/home"
                }, {
                    "text":'待办事项'
                }];
                vm.path=erp.BASE_PATH;
                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.merchantId = parseInt(routeData.params["id"]);
                vm.itemStatus = parseInt(routeData.params["status"]);
                vm.imgUrl = '';
                vm.isLogo = false;
                vm.pageData={};
                vm.type = routeData.params["type"];
                if(vm.type == 1){
                    vm.navCrumbs[0].href="#/backend/customerservice";
                }

                vm.localHref = function(url){
                    util.jumpPage(url);
                };
                /*merchant信息*/
                var merchantModel = function(){
                    vm.merchantData = {//商户信息出参
                        "productList": [],
                        "filePath": "",
                        "merchant_status": "",
                        "merchant_name": "",
                        "front_user_name": "",
                        "sale_user_name": "",
                        "assist_user_name": '',
                        "person_avg": "",
                        "desk_avg": "",
                        "store_number": "",
                        "begin_time": '',
                        "plan_online_time": '',
                        "format_name": "",
                        "attachment_name": "",
                        "attachment_path": ""
                    };
                };
                merchantModel();
                /*merchant信息-end*/
                /*添加问题*/
                var questionModel = function(){
                    vm.options1=[];
                    vm.$addQuestionDialogOpts = {//弹框初始化
                        "dialogId": getTypeId("addQuestionDialog"),
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
                            //清除验证信息
                            var formVm = avalon.getVm(getTypeId("addQuestionForm"));
                            formVm.reset();
                            formVm.title='';
                            formVm.description='';
                        },

                        "submit": function (evt) {
                            var requestData,
                                dialogVm = avalon.getVm(getTypeId("addQuestionDialog")),
                                formVm = avalon.getVm(getTypeId("addQuestionForm"));
                            if (formVm.validate()) {
                                requestData = formVm.getFormData();
                                var problemType=avalon.getVm(getTypeId("addQuestionSelect")).selectedValue;
                                requestData.merchantId = pageVm.merchantId;
                                requestData.problemType=problemType;
                                requestData.problemStatus=1;
                                requestData=JSON.stringify(requestData);
                                //发送后台请求，编辑或添加
                                util.c2s({
                                    "url": erp.BASE_PATH + 'sysProblem/saveSysProblem.do',
                                    "type": "post",
                                    "contentType" : 'application/json',
                                    "data": requestData,
                                    "success": function (responseData) {
                                        if (responseData.flag == 1) {
                                            dialogVm.close();
                                        }
                                    }
                                });
                            }
                        }
                    };
                    vm.openAdd = function(){
                        var scheduleVm = avalon.getVm(getTypeId("addQuestionSelect"));
                        if(!vm.options1.length){
                            util.c2s({
                                "url": erp.BASE_PATH + "dictionary/querySysDictionaryByType.do",
                                "type": "post",
                                "data":  'dictionaryType=00000040',
                                "success": function (responseData) {
                                    var data;
                                    if (responseData.flag) {
                                        data = responseData.data;
                                        vm.options1=_.map(data, function (itemData) {
                                            return {
                                                "text": itemData["dictionary_value"],
                                                "value": itemData["dictionary_key"]
                                            };
                                        });
                                        scheduleVm.setOptions(vm.options1);
                                    }
                                }
                            });
                        }else{
                            scheduleVm.setOptions(vm.options1);
                        }
                        var dialogVm = avalon.getVm(getTypeId("addQuestionDialog")),
                            formVm = avalon.getVm(getTypeId("addQuestionForm"));
                        dialogVm.title = '添加问题';
                        formVm.title='';
                        formVm.description='';
                        dialogVm.open();
                    };
                    vm.$addQuestionFormOpts = {//form表单初始化
                        "formId": getTypeId("addQuestionForm"),
                        "field":
                            [{
                                "selector": ".title",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "问题标题不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                                {
                                    "selector": ".description",
                                    "rule": ["noempty", function (val, rs) {
                                        if (rs[0] !== true) {
                                            return "问题类型不能为空";
                                        } else {
                                            return true;
                                        }
                                    }]
                                }
                            ],
                        "title":"",
                        "description":""
                    };
                    vm.$scheduleOpts = {//elect初始化
                        "selectId": getTypeId("addQuestionSelect"),
                        "options": [],
                        "selectedIndex": 1
                    };
                };
                questionModel();
                /*添加问题-end*/
                /*调研单*/
                vm.$surveyOpts = {
                    "surveyId": getTypeId('-surveyModule'),
                    "displayType" : '',
                    "merchantId" : '',
                    "callFn" : function(){
                        updateList({"merchantId":pageVm.merchantId},"mktShopSurvey/listMktShopSurveys.do",waitingCallBack);
                        updateList({"merchantId":pageVm.merchantId},"synMerchant/getMerchantInfoByOrder.do",merchantCallBack);
                    }
                };
                vm.addSurvey = function () {
                    var dialog = avalon.getVm(getTypeId('-surveyModule'));
                    dialog.displayType = 'add';
                    dialog.moduleType = 'mkt';
                    dialog.merchantId = pageVm.merchantId;
                    dialog.requestData = {
                        merchantId:pageVm.merchantId,
                        id: $(this).attr('data-id')
                    };
                    dialog.open()
                };
                vm.readSurvey = function () {
                    var dialog = avalon.getVm(getTypeId('-surveyModule'));
                    dialog.displayType = 'read';
                    dialog.moduleType = 'fes';
                    dialog.merchantId = pageVm.merchantId;
                    dialog.requestData = {
                        merchantId: pageVm.merchantId,
                        id: $(this).attr('data-id')
                    };
                    dialog.open()
                };
                /*删除调研单*/
                vm.removeResearch = function(id,storeId){
                    var requestData = {id : id,moduleType : 'mkt',storeId: storeId};
                    requestData = JSON.stringify(requestData);
                    util.confirm({
                        "content": "你确定要删除调研单吗？",
                        "onSubmit": function () {
                            util.c2s({
                                "url": erp.BASE_PATH + 'mktShopSurvey/deleteMktShopSurvey.do',
                                "type": "post",
                                "contentType": 'application/json',
                                "data": requestData,
                                "success": function (responseData) {
                                    if (responseData.flag == 1) {
                                        updateList({"merchantId": pageVm.merchantId}, "mktShopSurvey/listMktShopSurveys.do", waitingCallBack);
                                    }
                                }
                            });
                        }
                    });
                };
                /*删除调研单-end*/
                /*调研单-end*/
                /*访谈单*/
                vm.$interviewOpts = {
                    "interviewId": getTypeId('-interviewModule'),
                    "sessionId" : '',
                    "displayType" : '',
                    "moduleType" : '',
                    "merchantId" : '',
                    "callFn" : function(){
                        updateList({"merchantId":pageVm.merchantId},"mktShopSurvey/listMktShopSurveys.do",waitingCallBack);
                        updateList({"merchantId":pageVm.merchantId},"synMerchant/getMerchantInfoByOrder.do",merchantCallBack);
                    }
                };
                vm.addInterval = function(){
                    var dialog = avalon.getVm(getTypeId('-interviewModule'));
                    dialog.requestData = {"merchantId": pageVm.merchantId};
                    dialog.sessionId = loginUserData.sessionId;
                    dialog.displayType = 'add';
                    dialog.moduleType = 'mkt';
                    dialog.merchantId = pageVm.merchantId;
                    dialog.requestData = {
                        merchantId:pageVm.merchantId
                    };
                    dialog.open()
                };
                vm.readInterval = function(){
                    var dialog = avalon.getVm(getTypeId('-interviewModule'));
                    dialog.requestData = {"merchantId": pageVm.merchantId};
                    dialog.sessionId = loginUserData.sessionId;
                    dialog.displayType = 'read';
                    dialog.moduleType = 'mkt';
                    dialog.merchantId = pageVm.merchantId;
                    dialog.requestData = {
                        merchantId:pageVm.merchantId
                    };
                    dialog.open()
                };

                /*访谈单-end*/
                /*通讯录*/
                vm.$addressOpts = {
                    "addressId": getTypeId('-addressModule'),
                    "displayType" : 'add',
                    "moduleType" : 'mkt',
                    "isSearch" : false,
                    "merchantId" : 0
                };
                vm.addCommunication = function(name){
                    var dialog = avalon.getVm(getTypeId('-addressModule'));
                    dialog.callFn = function(){
                        if(name){
                            util.c2s({
                                "url": erp.BASE_PATH + 'contact/searchContacts.do',
                                "type": "post",
                                "contentType": 'application/json',
                                "data": JSON.stringify({"merchantId": pageVm.merchantId}),
                                "success": function (responseData) {
                                    var data;
                                    if(responseData.flag){
                                        data = responseData.data.rows;
                                        var addressList = avalon.getVm(name);
                                        var arr =_.map(data, function (itemData) {
                                            return {
                                                "text": itemData["name"],
                                                "value": itemData["id"]
                                            };
                                        });
                                        arr.unshift({
                                            "text": "请选择",
                                            "value": ''
                                        });
                                        //addressList.setOptions(arr);
                                    }
                                }
                            });
                        }
                        updateList({"merchantId":pageVm.merchantId},"mktShopSurvey/listMktShopSurveys.do",waitingCallBack);
                        updateList({"merchantId":pageVm.merchantId},"synMerchant/getMerchantInfoByOrder.do",merchantCallBack);
                    };
                    if($(this).attr('data-id')){
                        dialog.requestData = {
                            merchantId:pageVm.merchantId,
                            id:$(this).attr('data-id')
                        };
                    }else{
                        dialog.requestData = {
                            merchantId:pageVm.merchantId
                        };
                    }
                    if($(this).attr('data-readonly')){
                        dialog.displayType = 'read';

                    }else{
                        dialog.displayType = 'add';
                    }
                    dialog.open();


                };
                vm.readCommunication = function(){
                    var dialog = avalon.getVm(getTypeId('-addressModule'));
                    dialog.requestData = {
                        merchantId:pageVm.merchantId,
                        id:$(this).attr('data-id')
                    };
                    dialog.displayType = 'read';
                    dialog.open();
                };
                /*通讯录-end*/

                /*步骤确认完成*/
                vm.stepEnd = function() {
                    util.confirm({
                        "content": "你确定完成了吗？",
                        "onSubmit": function () {
                            util.c2s({
                                "url": erp.BASE_PATH + "mktShopSurvey/salesComfirm.do",
                                "type": "get",
                                "contentType": 'application/json',
                                "data": {merchantId: pageVm.merchantId},
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        //updateList({"merchantId": pageVm.merchantId}, "mktShopSurvey/listMktShopSurveys.do", waitingCallBack);
                                        window.location.href = erp.BASE_PATH + "#!/backend/customerservice";
                                    }
                                }
                            });
                        }
                    });
                };
                /*步骤确认完成-end*/
                /*测试代码*/
                vm.testFn = function (){
                    //通讯录
                    var address = {"merchantName":"商户名称测试-2372","moduleType":"","merchantId":vm.merchantId,"name":"测试","genderType":1,"birthday":"2014-12-22","position":"测试","mobilePhone":"13288888888","telephone":"","roleType":"1","mail":"123@123.cn"};
                    util.c2s({
                        "url": erp.BASE_PATH + 'contact/saveMktContact.do',
                        "type": "post",
                        "contentType" : 'application/json',
                        "data": JSON.stringify(address),
                        "success": function (responseData) {
                            if (responseData.flag == 1) {
                                var interview = {"merchantId":vm.merchantId,"merchantName":"商户名称测试-2368","contactId":2967,"format":["1"],"storeNumber":"123","joinType":"1","businessArea":"","customerType":"1","dailyTurnover":"123","cashCountPerDay":"123","perOrder":"123","perCapita":"123","groupPurchase":"123","coupon":"123","bankCardOffer":"123","otherShopDiscount":"123","cardType":["1"],"managementSystem":["1"],"memberRight":["1"],"sendCardNumber":"123","potentialCustomerSource":"123","potentialCustomerNum":"123","networkCondition":"0","networkSpeed":"1","attachDocument":{"originalFileName":""},"attachmentId":"","moduleType":"mkt","fileName":"","originalFileName":"","relativePath":"","networkRemark":"","fileSize":""};
                                util.c2s({
                                    "url": erp.BASE_PATH + 'mktBrandInterview/saveMktBrandInterview.do',
                                    "type": "post",
                                    "contentType": 'application/json',
                                    "data": JSON.stringify(interview),
                                    "success": function (responseData) {
                                        if (responseData.flag == 1) {
                                            var survery = {"merchantId":vm.merchantId,"id":"","storeId":1234,"contactId":2967,"format":["1"],"near":"2","businessArea":"213","dailyPassengerFlow":"123","tableAverage":"123","mealsNumber":"123","roomsNumber":"123","attendanceRatio":"123","networkCondition":"0","networkSpeed":"1","publicityMaterial":["5"],"moduleType":"mkt","networkRemark":"","materRemark":""};
                                            util.c2s({
                                                "url": erp.BASE_PATH + 'mktShopSurvey/saveMktShopSurvey.do',
                                                "type": "post",
                                                "contentType": 'application/json',
                                                "data": JSON.stringify(survery),
                                                "success": function (responseData) {
                                                    if (responseData.flag == 1) {
                                                        util.c2s({
                                                            "url": erp.BASE_PATH + "mktShopSurvey/salesComfirm.do",
                                                            "type": "get",
                                                            "contentType": 'application/json',
                                                            "data": {merchantId: pageVm.merchantId},
                                                            "success": function (responseData) {
                                                                if (responseData.flag) {
                                                                    //updateList({"merchantId": pageVm.merchantId}, "mktShopSurvey/listMktShopSurveys.do", waitingCallBack);
                                                                    window.location.href = erp.BASE_PATH + "#!/backend/customerservice";
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                };
                /*测试代码-end*/
            });
            avalon.scan(pageEl[0]);
            /*页面初始化请求渲染*/
            function updateList(obj,url,callBack){//其他渲染方法
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
            /*页面初始化请求渲染-end*/
            /*商户回调*/
            updateList({"merchantId":pageVm.merchantId},"synMerchant/getMerchantInfoByOrder.do",merchantCallBack);
            function merchantCallBack(responseData){
                var data;
                if (responseData.flag) {
                    data = responseData.data;
                    var obj = {};
                    obj.productList = data.productList;
                    obj.filePath = data.filePath;
                    var tmpObj = data.merchantInfo;
                    for(var k in tmpObj){
                        var value = tmpObj.hasOwnProperty(k);
                        if(value){
                            obj[k] = tmpObj[k];
                        }
                    }
                    if(obj.attachment_name){
                        obj.imgUrl=pageVm.path+obj.filePath+'/'+obj.attachment_path+'/'+obj.attachment_name;
                    }else{
                        obj.imgUrl=pageVm.path+'portal/origin/asset/image/merchant-default-logo.jpg';
                    }
                    pageVm.merchantData= obj;
                }
            }
            /*商户回调end*/
            /*list*/
            updateList({"merchantId":pageVm.merchantId,"itemStatus": pageVm.itemStatus},"mktShopSurvey/listMktShopSurveys.do",waitingCallBack);
            function waitingCallBack(responseData){
                var data;
                if (responseData.flag) {
                    data = responseData.data;
                    pageVm.pageData= data;
                }
            }
            /*list-end*/
            /*页面初始化请求渲染-end*/
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
