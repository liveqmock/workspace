/**
 * 前端服务-营销活动
 */
define(['avalon', 'util', '../../../../widget/form/select','../../../../widget/dialog/dialog', '../../../../widget/form/form','../../../../widget/uploader/uploader', '../../../../widget/editor/editor'], function (avalon, util) {
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
            var getTypeId = function (n) {//设置和获取widget的id;
                var widgetId = pageName + n;
                tempWidgetIdStore.push(widgetId);
                return widgetId;
            };
            var pageVm = avalon.define(pageName, function (vm) {
                vm.navCrumbs = [{
                    "text":"我的主页",
                    "href":"#/frontend/home"
                }, {
                    "text":"营销活动"
                }];
                vm.path = erp.BASE_PATH;
                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.merchantId = parseInt(routeData.params["id"]);
                vm.imgUrl = '';
                vm.localHref = function(url){//页面跳转
                    util.jumpPage(url);
                };
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
                        "format_name": "",
                        "attachment_name": "test_logo",
                        "attachment_path": "881"
                    };
                };
                merchantModel();
                /*merchant信息-end*/
                /*添加问题*/
                var questionModel = function(){
                    vm.options1=[];
                    vm.$addQuestionDialogOpts = {//弹框初始化
                        "dialogId": getTypeId('addQuestionDialogId'),
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
                            var formVm = avalon.getVm(getTypeId('addQuestionFormId'));
                            formVm.reset();
                            formVm.title='';
                            formVm.description='';
                        },

                        "submit": function (evt) {
                            var requestData,
                                dialogVm = avalon.getVm(getTypeId('addQuestionDialogId')),
                                formVm = avalon.getVm(getTypeId('addQuestionFormId'));
                            if (formVm.validate()) {
                                requestData = formVm.getFormData();
                                var problemType=avalon.getVm(getTypeId('scheduleId')).selectedValue;
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
                        var scheduleVm = avalon.getVm(getTypeId('scheduleIds'));
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
                        var dialogVm = avalon.getVm(getTypeId('addQuestionDialogId')),
                            formVm = avalon.getVm(getTypeId('addQuestionFormId'));
                        dialogVm.title = '添加问题';
                        formVm.title='';
                        formVm.description='';
                        dialogVm.open();
                    };
                    vm.$addQuestionFormOpts = {//form表单初始化
                        "formId": getTypeId('addQuestionFormId'),
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
                    vm.$scheduleOptss = {//elect初始化
                        "selectId": getTypeId('scheduleIds'),
                        "options": [],
                        "selectedIndex": 1
                    };
                };
                questionModel();
                /*添加问题-end*/
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
                /*营销活动*/
                var salesModel =function(){
                    vm.responseSalesData = [
                        {
                            "active_name": "全年优惠营销---11-1",//名称
                            "active_begin": "2014-07-01",//创建时间
                            "active_end": "2015-01-01",  //终止时间
                            "type_name": "全年优惠营销"  // 类型
                        }
                    ];

                    util.c2s({
                        "url": erp.BASE_PATH + 'activeCrm/getActiveExecutiving.do',
                        "type": "post",
                        "data": {"brandId":vm.merchantId},
                        "success": function(responseData){
                            if (responseData.flag) {
                                data = responseData.data;
                                var modelArr=[
                                    "active_name","active_begin","active_end","type_name"
                                ];
                                pageVm.responseSalesData = forValue(data,modelArr);
                            }
                        }
                    });
                };
                salesModel();
                /*营销活动*/


                /*发送月报*/
                vm.linkMan = {
                    data: [],
                    flag: 0
                };
                vm.mailModel = {
                    data: [],
                    flag: 0
                };
                vm.mailTo = '';
                vm.mailRequestData = {
                    sendAddress : '',// 发件人地址
                    to : [],//联系人邮件地址
                    context : '',//邮件正文(字符串)
                    subject : ''//邮件标题(字符串)
                };

                vm.$editorOpts = {
                    "editorId": getTypeId('editorId'),
                    "ueditorOptions": {
                        "UEDITOR_HOME_URL": erp.WIDGET_PATH + 'editor/ueditor/',
                        "serverUrl": erp.BASE_PATH,
                        "toolbars": [[
                            'fullscreen', 'source', '|', 'undo', 'redo', '|',
                            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                            'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                            'directionalityltr', 'directionalityrtl', 'indent', '|',
                            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter'
                        ]]
                    }
                };
                vm.onLineMail = function(){
                    //vm.mailRequestData.id=this.$vmodel.$model.el.id;
                    var dialog = avalon.getVm(getTypeId('onLineMailDialogId'));
                    dialog.title='发送月报';
                    dialog.open();
                };
                vm.addLinkman = function(){
                    $(document).on('click',function(e){
                        var target = e.target;
                        if(target.id != 'addLinkMan'){
                            pageVm.linkMan.flag = 0;
                        }
                        if(target.id != 'addMailModel'){
                            pageVm.mailModel.flag = 0;
                        }
                    });
                    util.c2s({
                        "url": erp.BASE_PATH + 'contact/searchContacts.do',
                        "type": "post",
                        "contentType" : 'application/json',
                        "data": JSON.stringify({'merchantId': pageVm.merchantId}),
                        "success": function (responseData) {
                            if (responseData.flag == 1) {
                                pageVm.linkMan.flag = 1;
                                pageVm.linkMan.data = responseData.data.rows;
                            }
                        }
                    });
                };
                vm.addPut = function(){
                    var mailVal=this.$vmodel.$model.el.mail;
                    if(pageVm.mailTo.length){
                        pageVm.mailTo+=';'+mailVal;
                    }else{
                        pageVm.mailTo+=mailVal;
                    }

                    pageVm.linkMan.flag = 0;
                };
                vm.addMailModel = function(){
                    util.c2s({
                        "url": erp.BASE_PATH + 'fesOnlineProcess/getEmailTemplates.do',
                        "type": "post",
                        "contentType" : 'application/json',
                        "data": JSON.stringify({"mailTemplateType":"002"}),
                        "success": function (responseData) {
                            if (responseData.flag == 1) {
                                pageVm.mailModel.data = responseData.data.rows;
                                pageVm.mailModel.flag = 1;
                            }
                        }
                    });
                };
                vm.addPutEmailModel = function(){
                    var modelVal=this.$vmodel.$model.el.content;
                    var editorVm = avalon.getVm(getTypeId('editorId'));
                    editorVm.core.setContent(modelVal);
                    pageVm.mailModel.flag = 0;
                };
                vm.$onLineMailDialogOpts = {
                    "dialogId": getTypeId('onLineMailDialogId'),
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
                        var formVm = avalon.getVm(getTypeId('addQuestionFormId'));
                        formVm.reset();
                    },
                    "width" : 700,
                    "submit": function (evt) {
                        var dialogVm = avalon.getVm(getTypeId('onLineMailDialogId')),
                            formVm = avalon.getVm(getTypeId('onLineMailFormId')),
                            editorVm = avalon.getVm(getTypeId('editorId'));
                        if (formVm.validate()) {
                            pageVm.mailRequestData.context=editorVm.core.getContent();
                            pageVm.mailRequestData.to = pageVm.mailTo.split(';');
                            util.c2s({
                                "url": erp.BASE_PATH + 'fesOnlineProcess/sendEmail.do',
                                "type": "post",
                                "contentType" : 'application/json',
                                "data": JSON.stringify(pageVm.mailRequestData.$model),
                                "success": function (responseData) {
                                    if (responseData.flag == 1) {
                                        dialogVm.close();
                                    }
                                }
                            });

                        }
                    }
                };
                vm.$onLineMailFormOpts ={
                    "formId": getTypeId('onLineMailFormId'),
                    "field":
                        [
                            {
                                "selector": ".mail-to",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "收件人不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".mail-subject",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "邮件标题不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".mail-name-fa",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "发件人不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": '[name="content"]',
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "内容不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            }
                        ]
                };
                /*发送月报*/
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
            /*商户*/
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


