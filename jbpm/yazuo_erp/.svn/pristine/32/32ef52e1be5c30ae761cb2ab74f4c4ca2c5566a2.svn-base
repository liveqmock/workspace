/**
 * 我的主页-客户服务看板
 */
define(['avalon', 'util','moment', 'json', '../../../../widget/dialog/dialog', '../../../../widget/form/select', '../../../../widget/form/form', '../../../../widget/calendar/calendar', '../../../../widget/uploader/uploader'], function (avalon, util) {
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
            var addQuestionDialogId = pageName + '-add-Question-dialog',//调用添加问题弹框
                addQuestionFormId = pageName + '-add-Question-form',//调用添加问题form验证
                scheduleId = pageName + '-schedule',//添加问题调用select组件
                activeId = pageName + '-active-select',//活动申请调用select组件
                createActiveFormId = pageName + '-active-form',//活动申请调用form组件
                createActiveDialogId = pageName + '-active-dialog',//活动申请调用dialog组件
                activeUploaderId = pageName + '-active-uploader';//活动申请调用上传组件
            var loginUserData = erp.getAppData('user');
            var pageVm = avalon.define(pageName, function (vm) {
                    vm.navCrumbs = [{
                        "text":"我的主页",
                        "href":"#/frontend/make"
                    }, {
                        "text":"大连彤德莱餐饮管理有限公司"
                    }];
                    vm.pageUserId = erp.getAppData('user').id;//用户id
                    vm.merchantId = parseInt(routeData.params["id"]);
                    vm.createData = {};//保存活动申请数据
                    vm.options1=[];
                    vm.options2=[];
                    vm.stepData={};
                    vm.merchantData = {};




                    /*添加问题*/
                    vm.$addQuestionDialogOpts = {//弹框初始化
                        "dialogId": addQuestionDialogId,
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
                            var formVm = avalon.getVm(addQuestionFormId);
                            formVm.reset();
                            formVm.title='';
                            formVm.description='';
                        },

                        "submit": function (evt) {
                            var requestData,
                                dialogVm = avalon.getVm(addQuestionDialogId),
                                formVm = avalon.getVm(addQuestionFormId);
                            if (formVm.validate()) {
                                requestData = formVm.getFormData();
                                var problemType=avalon.getVm(scheduleId).selectedValue;
                                requestData.merchantId = pageVm.merchantId;
                                requestData.problemType=problemType;
                                requestData.problemStatus=1;
                                requestData=JSON.stringify(requestData);
                                console.log(requestData);
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
                        var scheduleVm = avalon.getVm(scheduleId);
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
                        var dialogVm = avalon.getVm(addQuestionDialogId),
                            formVm = avalon.getVm(addQuestionFormId);
                        dialogVm.title = '添加问题';
                        formVm.title='';
                        formVm.description='';
                        dialogVm.open();
                    };
                    vm.$addQuestionFormOpts = {//form表单初始化
                        "formId": addQuestionFormId,
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
                        "selectId": scheduleId,
                        "options": [],
                        "selectedIndex": 1
                    };
                    /*添加问题-end*/
                    /*创建活动申请*/
                    vm.createActive = function(){
                        var dialogVm = avalon.getVm(createActiveDialogId),
                            formVm = avalon.getVm(createActiveFormId),
                            activeSelectVm = avalon.getVm(activeId);
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
                        "uploaderId": activeUploaderId,
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
                        "dialogId" : createActiveDialogId,
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
                            var formVm = avalon.getVm(createActiveFormId);
                            formVm.reset();
                            formVm.activityTitle = '';
                            formVm.description = '';
                        },
                        "submit": function (evt) {
                            var obj,
                                dialogVm = avalon.getVm(createActiveDialogId),
                                formVm = avalon.getVm(createActiveFormId),
                                activeSelectVm = avalon.getVm(activeId);
                            if (formVm.validate()) {
                                obj = formVm.getFormData();
                                pageVm.createData.$model.merchantId = pageVm.merchantId;
                                pageVm.createData.$model.activityTitle = obj.activityTitle;
                                pageVm.createData.$model.description = obj.description;
                                pageVm.createData.$model.activityType = activeSelectVm.selectedValue;
                                pageVm.createData.$model.attachmentId = 0;
                                var requestData=JSON.stringify(pageVm.createData.$model);
                                console.log(requestData)
                                //发送后台请求，编辑或添加
                                util.c2s({
                                    "url": erp.BASE_PATH + 'activity/saveActivity.do',
                                    "type": "post",
                                    "contentType" : 'application/json',
                                    "data": requestData,
                                    "success": function (responseData) {
                                        console.log(requestData);
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
                        "formId": createActiveFormId,
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
                        "selectId": activeId,
                        "options": [],
                        "selectedIndex": 1
                    };
                    /*创建活动申请结束*/

                });
                avalon.scan(pageEl[0]);

                /*页面初始化请求渲染*/
                /*util.c2s({
                    "url": erp.BASE_PATH + "fesOnlineProcess/listFesOnlineProcesss/4.do",
                    "type": "get",
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            pageVm.stepData=data;
                            console.log(pageVm.stepData)
                        }
                    }
                });*/
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
                        pageVm.merchantData= data;
                        console.log(data);
                    }
                }
                /*商户回调end*/


            /*页面初始化请求渲染-end*/
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {

        }
    });


    return pageMod;
});

