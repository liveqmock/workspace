/**
 * 前端服务-我的主页
 */
define(['avalon', 'util', '../../../../widget/form/select','../../../../widget/dialog/dialog', '../../../../widget/form/form','../../../../widget/uploader/uploader', '../../../../module/addquestion/addquestion'], function (avalon, util) {
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
            function forValue (data,arr){
                var tmpArr = [];
                if(data instanceof Array){
                    for(var i=0;i<data.length;i++){
                        var tmpObj = {};
                        for( var k in data[i]){
                            var value = data[i].hasOwnProperty(k);
                            if(value){
                                for(var j=0;j<arr.length;j++){
                                    if(k == arr[j]){
                                        tmpObj[arr[j]] = data[i][k];
                                    }
                                }
                            }
                        }
                        tmpArr.push(tmpObj);
                    }
                }else{
                    var tmpObj = {};
                    for( var k in data){
                        var value = data[i].hasOwnProperty(k);
                        if(value){
                            for(var j=0;j<arr.length;j++){
                                if(k == arr[j]){
                                    tmpObj[arr[j]] = data[i][k];
                                }
                            }
                        }
                    }
                    tmpArr.push(tmpObj);
                }
                return tmpArr;
            }
            var disposeDialogId = pageName + '-dispose-dialog';//投诉问题弹框
            var loginUserData = erp.getAppData('user');
            var pageVm = avalon.define(pageName, function (vm) {
                var getTypeId = function (n) {//设置和获取widget的id;
                    var widgetId = pageName + n;
                    tempWidgetIdStore.push(widgetId);
                    return widgetId;
                };
                vm.navCrumbs = [{
                    "text":"我的主页",
                    "href":"#/frontend/home"
                }, {
                    "text":"待办事项"
                }];
                vm.localHref = function(url){//页面跳转
                    util.jumpPage(url);
                };
                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.merchantId = parseInt(routeData.params["id"]);
                vm.merchantName = '';
                vm.path = erp.BASE_PATH;
                vm.createData = {};//保存活动申请上传数据
                vm.options1 = [];
                vm.options2 = [];
                vm.pageStatus= 1;
                vm.pageData = {};
                vm.listData = {};
                vm.imgUrl = '';
                /*merchant信息*/
                var merchantModel = function(){
                    vm.merchantData = {//商户信息出参
                        "productList": [
                            "用户管理平台",
                            "CRM2.0",
                            "CRM4.0"
                        ],
                        "filePath": "user-upload-img/merchant/real",
                        "merchant_status": "0",
                        "merchant_name": "测试",
                        "front_user_name": "admin",
                        "sale_user_name": "18610925812",
                        "assist_user_name": null,
                        "person_avg": "",
                        "desk_avg": "",
                        "store_number": "",
                        "begin_time": 1409131953594,
                        "plan_online_time": 1412761451126,
                        "online_time": 1412761451126,
                        "format_name": "",
                        "attachment_name": "test_logo",
                        "attachment_path": "881"
                    };
                };
                merchantModel();
                /*merchant信息-end*/

                vm.requestDataDispose = {//投诉处理
                    id : 0,
                    handledBy : vm.pageUserId,
                    handledTime : new Date().getTime(),
                    handledDescription : '',
                    customerComplaintStatus : 1
                };
                vm.changePageStatus= function(value,obj){//大标签切换
                    vm.pageStatus= value;
                    if(value == 2){
                        vm.cardFn();
                    }
                };
                /*卡信息*/
                var cardModel = function(){
                    vm.responseCardData = [
                        {
                            "card_count": 216481,//（卡余量）
                            "card_price": 0,//（单价）
                            "card_type": "粉丝会员",//（卡类型）
                            "merchant_type": "品牌",//（商户类型）
                            "is_sold_card": false,//（是否实体卡）
                            "merchant_name": "北京新辣道餐饮管理有限公司",//（商户名称）
                            "merchant_id": 1119,//（商户ID，不取）
                            "cardtype_id": 207,
                            "description": "暂无",
                            "card_name" : "积分卡"//（卡名称）
                        }
                    ];
                    vm.cardFn = function(){
                        util.c2s({
                            "url": erp.BASE_PATH + 'synMembershipCard/listSynMembershipCards.do',
                            "type": "post",
                            "data": {pageNumber:1,pageSize:10000,merchantId:pageVm.merchantId},
                            "success": function(responseData){
                                if(responseData.flag){
                                    var arr = responseData.data.rows;
                                    var modelArr=[
                                        "card_count","card_price","card_type","merchant_type","is_sold_card","merchant_name","cardtype_id","description","card_name"
                                    ];
                                    pageVm.responseCardData = forValue(arr,modelArr);
                                }
                            }
                        });
                    }
                };
                cardModel();
                /*卡信息-end*/


                /*添加问题*/
                vm.$addquestionOpts = {
                    "addquestionId" : getTypeId('-addquestionops'),
                    addType : 1,
                    merchantId : 0,
                    merchantName : '',
                    callFn : function(){

                    }

                };
                vm.addQuestions = function(){
                    var dialogVm = avalon.getVm(getTypeId('-addquestionops'));
                    dialogVm.addType = 0;
                    dialogVm.merchantId = vm.merchantId;
                    dialogVm.merchantName = vm.merchantName;
                    dialogVm.open();
                };


                /*添加问题结束*/
                /*创建活动申请*/
                var activeModel = function(){
                    vm.createData = {};//保存活动申请上传数据
                    vm.options2 = [];
                    vm.createActive = function(){
                        var dialogVm = avalon.getVm(getTypeId('createActiveDialogId')),
                            formVm = avalon.getVm(getTypeId('createActiveFormId')),
                            activeSelectVm = avalon.getVm(getTypeId('activeId'));
                        if(!vm.options2.length){//获取select
                            util.c2s({
                                "url": erp.BASE_PATH + "dictionary/querySysDictionaryByType.do",
                                "type": "post",
                                "data":  'dictionaryType=00000056',
                                "success": function (responseData) {
                                    var data;
                                    if (responseData.flag) {
                                        data = responseData.data;
                                        vm.options2=_.map(data, function (itemData) {
                                            return {
                                                "text": itemData["dictionary_value"],
                                                "value": itemData["dictionary_key"]
                                            };
                                        });
                                        activeSelectVm.setOptions(vm.options2);
                                    }
                                }
                            });
                        }else{
                            activeSelectVm.setOptions(vm.options2);
                        }

                        dialogVm.title = '营销活动申请';
                        formVm.activityTitle='';
                        formVm.description='';
                        dialogVm.open();
                    };

                    vm.$activeUploaderOpts = {//上传
                        "uploaderId": getTypeId('activeUploaderId'),
                        "uploadifyOpts": {
                            "uploader": erp.BASE_PATH + 'activity/uploadFile.do',
                            "fileObjName": "myfiles",
                            "multi": false, //多选
                            "fileTypeDesc": "上传附件(*.*)",
                            "fileTypeExts": "*.*",
                            "formData": function () {
                                return {
                                    "sessionId": loginUserData.sessionId
                                };
                            },
                            "width": 110,
                            "height": 30,
                            'progressData': 'speed'
                        },
                        "onSuccessResponseData": function (responseText, file) {
                            responseText=JSON.parse(responseText);
                            if(responseText.flag){
                                var data = responseText.data;
                                pageVm.createData.$model.attachmentName = data.attachmentName;
                                pageVm.createData.$model.originalFileName = data.originalFileName;
                                pageVm.createData.$model.fileSize = data.fileSize;
                            }
                        }
                    };
                    vm.$createActiveDialogOpts = {
                        "dialogId" : getTypeId('createActiveDialogId'),
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
                            var formVm = avalon.getVm(getTypeId('createActiveFormId'));
                            formVm.reset();
                            formVm.activityTitle = '';
                            formVm.description = '';
                        },
                        "submit": function (evt) {
                            var obj,
                                dialogVm = avalon.getVm(getTypeId('createActiveDialogId')),
                                formVm = avalon.getVm(getTypeId('createActiveFormId')),
                                activeSelectVm = avalon.getVm(getTypeId('activeId'));
                            if (formVm.validate()) {
                                obj = formVm.getFormData();
                                pageVm.createData.$model.merchantId = pageVm.merchantId;
                                pageVm.createData.$model.activityTitle = obj.activityTitle;
                                pageVm.createData.$model.description = obj.description;
                                pageVm.createData.$model.activityType = activeSelectVm.selectedValue;
                                pageVm.createData.$model.attachmentId = 0;
                                var requestData=JSON.stringify(pageVm.createData.$model);
                                //发送后台请求，编辑或添加
                                util.c2s({
                                    "url": erp.BASE_PATH + 'activity/saveActivity.do',
                                    "type": "post",
                                    "contentType" : 'application/json',
                                    "data": requestData,
                                    "success": function (responseData) {
                                        if (responseData.flag == 1) {
                                            //关闭弹框
                                            dialogVm.close();
                                        }
                                    }
                                });
                            }
                        }
                    };
                    vm.$createActiveFormOpts = {//form表单初始化
                        "formId": getTypeId('createActiveFormId'),
                        "field":
                            [{
                                "selector": ".activityTitle",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "标题不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                                {
                                    "selector": ".description",
                                    "rule": ["noempty", function (val, rs) {
                                        if (rs[0] !== true) {
                                            return "内容不能为空";
                                        } else {
                                            return true;
                                        }
                                    }]
                                }
                            ],
                        "activityTitle":"",
                        "description":""
                    };
                    vm.$activeOpts = {//elect初始化
                        "selectId": getTypeId('activeId'),
                        "options": [],
                        "selectedIndex": 1
                    };
                };
                activeModel();
                /*创建活动申请结束*/
                /*投诉处理*/
                vm.disposeOpen = function(dataId,index){
                    vm.disposeIndex = index;
                    vm.requestDataDispose.id = dataId;
                    var dialogVm = avalon.getVm(disposeDialogId);
                    dialogVm.title = '投诉处理';
                    dialogVm.open();
                };
                vm.changProblemStatus = function(value){
                    vm.requestDataDispose.customerComplaintStatus = String(value);
                };
                vm.$assignedDisposeOpts={
                    "dialogId": disposeDialogId,
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
                        pageVm.requestDataDispose.handledDescription = '';
                    },
                    "submit": function (evt) {
                        var dialogVm=avalon.getVm(disposeDialogId);
                        var requestData=JSON.stringify(pageVm.requestDataDispose.$model);
                        util.c2s({
                            "url": erp.BASE_PATH + 'sysCustomerComplaint/updateSysCustomerComplaint.do',
                            "type": "post",
                            "contentType" : 'application/json',
                            "data": requestData,
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    var date = new Date();
                                    pageVm.listData[pageVm.disposeIndex].end = true;
                                    pageVm.listData[pageVm.disposeIndex].end_time = date.getTime();
                                    pageVm.listData[pageVm.disposeIndex].end_content = '投诉处理完成';
                                    console.log(pageVm.listData);
                                    /*updateList({"merchantId":pageVm.merchantId,
                                            "pageSize" : 100,
                                            "pageNumber" : 1,
                                            "customerComplaintStatus" : "0"},
                                        "sysCustomerComplaint/querySysCustomerComplaintList.do",backlogCallBack);*/
                                    dialogVm.close();
                                    pageVm.requestDataDispose.handledDescription = '';
                                }
                            }
                        });
                    }
                };
                /*投诉处理结束*/
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
            /*商户*/
            updateList({"merchantId":pageVm.merchantId},"synMerchant/getMerchantInfoByOrder.do",merchantCallBack);
            function merchantCallBack(responseData){
                var data;
                if (responseData.flag) {
                    data = responseData.data;
                    var obj = {};
                    obj.productList = data.productList;
                    obj.userMerchantList = data.userMerchantList;
                    obj.filePath = data.filePath;
                    var tmpObj = data.merchantInfo;
                    for(var k in data){
                        var value = data.hasOwnProperty(k);
                        if(value){
                            if(typeof(data[k])!= 'object'){
                                obj[k] = data[k];
                            }
                        }
                    }
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
                    pageVm.merchantName= obj.merchant_name;
                }
            }
            /*商户回调end*/
            /*代办事项回调*/
            updateList({"merchantId":pageVm.merchantId,
                        "pageSize" : 100,
                        "pageNumber" : 1,
                        "customerComplaintStatus" : "0"},
                "sysCustomerComplaint/querySysCustomerComplaintList.do",backlogCallBack);
            function backlogCallBack(responseData){
                var data,rows;
                if (responseData.flag) {
                    data = responseData.data;
                    rows = data.rows;
                    for(var i=0;i<rows.length;i++){
                        rows[i].end = false;
                        rows[i].end_time = '';
                        rows[i].end_content = '';
                    }
                    pageVm.listData= rows;
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


