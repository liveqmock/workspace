/**
 * 后端客服-客户服务看板
 */
define(['avalon', 'util', 'moment', 'json', '../../../../widget/dialog/dialog', '../../../../widget/form/select', '../../../../widget/form/form', '../../../../widget/calendar/calendar', '../../../../widget/uploader/uploader', '../../../../module/opencard/opencard', '../../../../module/addquestion/addquestion'], function (avalon, util) {
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
                vm.navCrumbs = [
                    {
                        "text": "后端客服",
                        "href": "#/backend/customerservice"
                    },
                    {
                        "text": 'test'
                    }
                ];
                vm.path = erp.BASE_PATH;
                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.merchantId = parseInt(routeData.params["id"]);
                vm.itemStatus = routeData.params["status"];
                vm.merchantName = '';
                vm.pageData = {};


                /*merchant信息*/
                var merchantModel = function () {
                    vm.merchantData = {//商户信息出参
                        "productList": [],
                        "filePath": "",
                        "merchant_id": '',
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
                vm.$addquestionOpts = {
                    "addquestionId": getTypeId('-addquestionops'),
                    addType: 1,
                    merchantId: 0,
                    merchantName: '',
                    callFn: function () {

                    }

                };
                vm.addQuestions = function () {
                    var dialogVm = avalon.getVm(getTypeId('-addquestionops'));
                    dialogVm.addType = 0;
                    dialogVm.merchantId = vm.merchantId;
                    dialogVm.merchantName = vm.merchantName;
                    dialogVm.open();
                };


                /*添加问题结束*/
                function getProcessId(str) {
                    for (var i = 0; i < vm.pageData.length; i++) {
                        if (vm.pageData[i].stepNum == str) {
                            return vm.pageData[i].id;
                        }
                    }
                }

                /*删除附件*/
                vm.removeAttach = function (fid, id) {
                    util.confirm({
                        "content": "你确定要删除文档吗？",
                        "onSubmit": function () {
                            util.c2s({
                                "url": erp.BASE_PATH + "fesOnlineProcess/deleteAttachment/" + fid + "/" + id + ".do",
                                "type": "get",
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        updateList({"merchantId": pageVm.merchantId, "userId": loginUserData.id, "resourceRemark": "end_custom_service"}, "fesOnlineProcess/listFesOnlineProcesss.do", listWaiting);
                                    }
                                }
                            });
                        }
                    });
                };
                /*删除附件-end*/
                /*微信申请*/
                var weixinmodle = function () {
                    vm.removeWeixin = function (fid, id) {//删除
                        var obj = {
                            id: id,
                            processId: fid,
                            merchantId: pageVm.merchantId
                        };
                        var requestData = JSON.stringify(obj);
                        util.confirm({
                            "content": "确定要删除选中的微信申请吗？",
                            "onSubmit": function () {
                                util.c2s({
                                    "url": erp.BASE_PATH + "applySetting/deleteRemark.do",
                                    "type": "post",
                                    "contentType": 'application/json',
                                    "data": requestData,
                                    "success": function (responseData) {
                                        if (responseData.flag) {
                                            updateList({"merchantId": pageVm.merchantId, "userId": loginUserData.id, "resourceRemark": "end_custom_service"}, "fesOnlineProcess/listFesOnlineProcesss.do", listWaiting);
                                        }
                                    }
                                });
                            }
                        });

                    };
                    vm.addWeixin = function () {
                        var dialogVm = avalon.getVm(getTypeId("addWeixinDialog"));
                        dialogVm.weixinId = this.$vmodel.$model.el.id;
                        dialogVm.title = "备注";
                        dialogVm.open();
                    };
                    vm.$addWeixinDialogOpts = {
                        "dialogId": getTypeId("addWeixinDialog"),
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
                        "onOpen": function () {
                        },
                        "onClose": function () {
                            var formVm = avalon.getVm(getTypeId("addWeixinForm"));
                            formVm.reset();
                        },
                        "submit": function (evt) {
                            var formVm = avalon.getVm(getTypeId("addWeixinForm"));
                            var dialogVm = avalon.getVm(getTypeId("addWeixinDialog"));
                            if (formVm.validate()) {
                                if (formVm.description.length > 250) {
                                    util.confirm({
                                        "content": '内容不能超过250个字！'
                                    });
                                }
                                var obj = formVm.getFormData();
                                obj.processId = dialogVm.weixinId;
                                obj.merchantId = pageVm.merchantId;
                                var requestData = JSON.stringify(obj);

                                util.c2s({
                                    "url": erp.BASE_PATH + 'applySetting/addRemark.do',
                                    "type": "post",
                                    "contentType": 'application/json',
                                    "data": requestData,
                                    "success": function (responseData) {
                                        if (responseData.flag == 1) {
                                            //关闭弹框
                                            dialogVm.close();
                                            updateList({"merchantId": pageVm.merchantId, "userId": loginUserData.id, "resourceRemark": "end_custom_service"}, "fesOnlineProcess/listFesOnlineProcesss.do", listWaiting);
                                        }
                                    }
                                });
                            }
                        }
                    };
                    vm.$addWeixinFormOpts = {
                        "formId": getTypeId("addWeixinForm"),
                        "field": [
                            {
                                "selector": ".description",
                                "name": "remark",
                                "rule": ["noempty", function (val, rs) {
                                    if (val.length > 250) {
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
                        "description": ""
                    };
                };
                weixinmodle();
                /*微信申请-end*/
                /*后台设置*/

                vm.addAfter = function () {
                    var dialogVm = avalon.getVm(getTypeId("addAfterDialog"));
                    dialogVm.id = this.$vmodel.$model.el.id;
                    dialogVm.title = "备注";
                    dialogVm.open();
                };
                vm.removeAfter = function (fid, id) {//删除
                    var obj = {
                        id: id,
                        processId: fid,
                        merchantId: pageVm.merchantId
                    };
                    var requestData = JSON.stringify(obj);
                    util.confirm({
                        "content": "确定要删除选中的后台设置吗？",
                        "onSubmit": function () {
                            util.c2s({
                                "url": erp.BASE_PATH + "fesOnlineProcess/deleteBackGroundContent.do",//要修改
                                "type": "post",
                                "contentType": 'application/json',
                                "data": requestData,
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        updateList({"merchantId": pageVm.merchantId, "userId": loginUserData.id, "resourceRemark": "end_custom_service"}, "fesOnlineProcess/listFesOnlineProcesss.do", listWaiting);
                                    }
                                }
                            });
                        }
                    });
                };
                vm.$addSetDialogOpts = {
                    "dialogId": getTypeId("addAfterDialog"),
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
                    "onOpen": function () {
                    },
                    "onClose": function () {
                        var formVm = avalon.getVm(getTypeId("addAfterFormlog"));
                        formVm.reset();
                    },
                    "submit": function (evt) {
                        var formVm = avalon.getVm(getTypeId("addAfterFormlog"));
                        var dialogVm = avalon.getVm(getTypeId("addAfterDialog"));
                        if (formVm.validate()) {
                            if (formVm.description.length > 250) {
                                util.confirm({
                                    "content": '内容不能超过250个字！'
                                });
                            }
                            var obj = formVm.getFormData();
                            obj.id = dialogVm.id;
                            obj.merchantId = pageVm.merchantId;
                            obj.onlineProcessStatus = '03';
                            var requestData = JSON.stringify(obj);
                            util.c2s({
                                "url": erp.BASE_PATH + 'fesOnlineProcess/saveBackGroundContent.do',
                                "type": "post",
                                "contentType": 'application/json',
                                "data": requestData,
                                "success": function (responseData) {
                                    if (responseData.flag == 1) {
                                        //关闭弹框
                                        dialogVm.close();
                                        updateList({"merchantId": pageVm.merchantId, "userId": loginUserData.id, "resourceRemark": "end_custom_service"}, "fesOnlineProcess/listFesOnlineProcesss.do", listWaiting);
                                    }
                                }
                            });
                        }
                    }
                };
                vm.$addSetFormOpts = {
                    "formId": getTypeId("addAfterFormlog"),
                    "field": [
                        {
                            "selector": ".set-description",
                            "name": "remark",
                            "rule": ["noempty", function (val, rs) {
                                if (val.length > 250) {
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
                    "description": ""
                };
                /*后台设置-end*/
                /*驳回*/
                vm.callBackMess = function(){
                    var dialogVm = avalon.getVm(getTypeId("addCallBackDialog"));
                    dialogVm.id = this.$vmodel.$model.el.id;
                    dialogVm.title = "驳回";
                    dialogVm.open();
                };
                vm.$addCallBackOpts = {
                    "dialogId": getTypeId("addCallBackDialog"),
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
                    "onOpen": function () {
                    },
                    "onClose": function () {
                        var formVm = avalon.getVm(getTypeId("addCallBackFormlog"));
                        formVm.reset();
                    },
                    "submit": function (evt) {
                        var formVm = avalon.getVm(getTypeId("addCallBackFormlog"));
                        var dialogVm = avalon.getVm(getTypeId("addCallBackDialog"));
                        if (formVm.validate()) {
                            if (formVm.description.length > 250) {
                                util.confirm({
                                    "content": '内容不能超过250个字！'
                                });
                            }
                            var obj = formVm.getFormData();
                            obj.id = dialogVm.id;
                            obj.merchantId = pageVm.merchantId;
                            obj.onlineProcessStatus = '20';
                            var requestData = JSON.stringify(obj);
                            util.c2s({
                                "url": erp.BASE_PATH + 'fesOnlineProcess/updateFesOnlineProcessStatusForReject.do',
                                "type": "post",
                                "contentType": 'application/json',
                                "data": requestData,
                                "success": function (responseData) {
                                    if (responseData.flag == 1) {
                                        //关闭弹框
                                        dialogVm.close();
                                        updateList({"merchantId": pageVm.merchantId, "userId": loginUserData.id, "resourceRemark": "end_custom_service", "itemStatus": pageVm.itemStatus}, "fesOnlineProcess/listFesOnlineProcesss.do", listWaiting);
                                    }
                                }
                            });
                        }
                    }
                };
                vm.$addCallBackFormOpts = {
                    "formId": getTypeId("addCallBackFormlog"),
                    "field": [
                        {
                            "selector": ".call-back-description",
                            "name": "remark",
                            "rule": ["noempty", function (val, rs) {
                                if (val.length > 250) {
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
                    "description": ""
                };
                /*驳回-end*/
                /*上传开卡表单*/
                vm.$step63UploaderOpts = {
                    "uploaderId": getTypeId("step63Uploder"),
                    "uploadifyOpts": {
                        "uploader": erp.BASE_PATH + 'fesOnlineProcess/uploadFiles.do',
                        "fileObjName": "myfile",
                        "multi": false, //多选
                        "fileTypeDesc": "上传附件(*.*)",
                        "fileTypeExts": "*.*",
                        "formData": function () {
                            return {
                                "sessionId": loginUserData.sessionId,
                                "processId": getProcessId('entity_cards'),
                                "typeId": 12
                            };
                        },
                        "width": 120,
                        "height": 30,
                        "onUploadProgress": function (file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
                            var ratio = (bytesUploaded / bytesTotal) * 100 > 100 ? 100 : (bytesUploaded / bytesTotal) * 100;
                            ratio = ratio + '%';
                            var fileName = file.name;
                            var tip = '';
                            if (ratio === '100%') {
                                tip = '上传完成';
                            } else {
                                tip = '正在上传：' + fileName;
                            }
                            if (!bytesUploaded) {
                                $('.step6-upfile-list').append(
                                    '<div class="upfile-file" style="position:relative;width:598px;height: 25px;border:1px solid #ccc;background:#bebebe;">' +
                                        '<p class="loding-file" style="position:absolute;left:0;top:-1px;width:' + ratio + ';height:27px;background:#67bb0d;"></p>' +
                                        '<p class="tips-model" style="position:absolute;left:0;top:0;height:25px;line-height:25px;text-align:center;width:100%;color:#fff;">' + tip + '</p>' +
                                        '</div>'
                                );
                            } else {
                                $('.loding-file').css('width', ratio);
                                $('.tips-model').html(tip);
                            }
                        }


                    },
                    "onSuccessResponseData": function (responseText, file) {
                        responseText = JSON.parse(responseText);
                        if (responseText.flag) {
                            setTimeout(function () {
                                $('.upfile-file').remove();
                                updateList({"merchantId": pageVm.merchantId, "userId": loginUserData.id, "resourceRemark": "end_custom_service"}, "fesOnlineProcess/listFesOnlineProcesss.do", listWaiting);
                            }, 1000);
                        } else {
                            $('.upfile-file').remove();
                            util.alert({
                                "content": "上传失败！",
                                "iconType": "error"
                            });
                        }
                    }
                };
                /*上传开卡表单-end*/
                /*申请开卡*/
                vm.$opencardOpts = {
                    "opencardId": getTypeId('-opencardId'),
                    "options": [],
                    "displayType": 'read',
                    "processId": 0,
                    "merchantId": 0,
                    "merchantName": '',
                    "callFn": function (responseData) {
                        if (responseData.flag) {
                            for (var i = 0; i < vm.pageData.length; i++) {
                                if (vm.pageData[i].stepNum == stepNum) {
                                    vm.pageData[i].onlineProcessStatus = status;
                                    vm.pageData[i].listAttachmentAndOperateLog.push(responseData.data);
                                }

                            }
                        }
                    }
                };
                vm.openCard = function (processId, readonly) {
                    var dialogVm = avalon.getVm(getTypeId('-opencardId'));
                    dialogVm.displayType = 'read';
                    dialogVm.processId = processId;
                    dialogVm.merchantId = vm.merchantId;
                    dialogVm.merchantName = vm.merchantName;
                    dialogVm.open();
                };
                /*申请开卡-end*/
                /*回访单*/
                vm.huiFShowType = 'add';
                vm.step9AccessId = 0;
                vm.acessRequestData = [];
                vm.step9Access = function (type, id, status) {
                    var formVm = avalon.getVm(getTypeId("addAccessForm"));
                    var dialogVm = avalon.getVm(getTypeId("addAccessDialog"));
                    var url;
                    if (type == 'add') {
                        vm.step9AccessId = 0;
                        var requestData = JSON.stringify({documentType: "1"});
                        url = 'sysQuestion/listSysQuestions.do'
                    } else if (type == 'edit') {
                        vm.step9AccessId = id;
                        var requestData = JSON.stringify({"id": id});
                        url = 'sysDocument/querySysDocumentById.do'
                    } else if (type == 'read') {
                        vm.step9AccessId = id;
                        var requestData = JSON.stringify({"id": id});
                        url = 'sysDocument/querySysDocumentById.do'
                    }
                    util.c2s({
                        "url": erp.BASE_PATH + url,
                        "type": "post",
                        "contentType": 'application/json',
                        "data": requestData,
                        "success": function (responseData) {
                            function getArrData(arr) {
                                var tmpArr = [];
                                for (var i = 0; i < arr.length; i++) {
                                    var obj = {};
                                    obj.optionContent = arr[i]['optionContent'];
                                    obj.isSelected = arr[i]['isSelected'];
                                    obj.isOpenTextarea = arr[i]['isOpenTextarea'];
                                    obj.tip = arr[i]['tip'];
                                    obj.isOpenTextarea = arr[i]['isOpenTextarea'];
                                    if (arr[i]['comment']) {
                                        obj.comment = arr[i]['comment'];
                                    } else {
                                        obj.comment = '';
                                    }
                                    tmpArr.push(obj);
                                }
                                return tmpArr;
                            }

                            function responseToData(data) {
                                var arr = [];
                                for (var i = 0; i < data.length; i++) {
                                    var obj = {};
                                    obj.questionId = data[i]['id'];
                                    obj.questionType = data[i]['question_type'];
                                    obj.title = data[i]['title'];
                                    obj.tip = data[i]['tip'];
                                    if (data[i]['comment']) {
                                        obj.comment = data[i]['comment'];
                                    } else {
                                        obj.comment = '';
                                    }
                                    if (data[i]['sysQuestionOptionList']) {
                                        obj.sysDocumentDtlOptionList = getArrData(data[i]['sysQuestionOptionList']);
                                    }
                                    arr.push(obj);
                                }
                                return arr;
                            }
                            if (responseData.flag == 1) {
                                var data;
                                if (type == 'add') {
                                    vm.huiFShowType = 'add';
                                    data = responseData.data;
                                    data = responseToData(data);
                                    vm.acessRequestData = data;
                                    dialogVm.readonly = false;
                                } else if (type == 'edit') {
                                    vm.huiFShowType = 'edit';
                                    data = responseData.data.sysDocumentDtlList;
                                    vm.acessRequestData = data;
                                    dialogVm.readonly = false;
                                } else if (type == 'read') {
                                    vm.huiFShowType = 'read';
                                    data = responseData.data.sysDocumentDtlList;
                                    vm.acessRequestData = data;
                                    dialogVm.readonly = true;
                                }
                                if(type == 'read' ||type == 'edit'){
                                    if(vm.acessRequestData[0].sysDocumentDtlOptionList[1].isSelected == '1' && vm.acessRequestData[0].sysDocumentDtlOptionList[1].optionContent != '是'){//选中了否
                                        for (var i = 1; i < vm.acessRequestData.length; i++) {
                                            $('.huiFang' + i).hide();
                                            $('.huiFang' + i).find('.valid-error').hide().html();
                                            $('.huiFang' + i).find('textarea').removeClass('valid-error-field').removeAttr('isrule');
                                            $('.huiFang' + i).find('.input-hidden-' + i).removeAttr('isrule');
                                            $('.huiFang' + i).find('input').removeAttr('checked');
                                            $('.input-hidden-' + i).val('');
                                        }
                                        $('.input-hidden-0').val('xxx');
                                    }else if(vm.acessRequestData[0].sysDocumentDtlOptionList[0].isSelected == '1' && vm.acessRequestData[0].sysDocumentDtlOptionList[0].optionContent == '是'){//选中了是
                                        for (var i = 0; i < vm.acessRequestData.length; i++) {
                                            if(vm.acessRequestData[i].sysDocumentDtlOptionList.length){
                                                var flag = false;
                                                for(var j = 0; j < vm.acessRequestData[i].sysDocumentDtlOptionList.length; j++){
                                                    if(vm.acessRequestData[i].sysDocumentDtlOptionList[j].isSelected == '1'){
                                                        flag = true;
                                                    }
                                                }
                                                if(flag){
                                                    $('.input-hidden-' + i).val('xxx');
                                                }
                                            }
                                        }
                                    }
                                }
                                dialogVm.title = "回访单";
                                dialogVm.open();

                            }
                        }
                    });
                };
                vm.radioBox = function () {//以下是写死得逻辑，内容改变后必须修改此处
                    var con = this.$vmodel.$outer.el.sysDocumentDtlOptionList;
                    for(var i=0;i<con.length;i++){
                        con[i].isSelected = '0';
                        con[i].comment = '';
                    }
                    this.$vmodel.el.isSelected = '1';
                    var selfCon = this.$vmodel.el;
                    var parentIndex = this.$vmodel.$outer.$index;
                    $('.input-hidden-' + parentIndex).val('xxx');
                    var dialogVm = avalon.getVm(getTypeId("addAccessDialog"));
                    util.testing(dialogVm.widgetElement, $('.input-hidden-' + parentIndex));
                    if (selfCon.optionContent == '是' && parentIndex == 0) {
                        for (var i = 1; i <= 4; i++) {
                            $('.huiFang' + i).show();
                            $('.huiFang' + i).find('.input-hidden-' + i).attr('isrule',true);
                            $('.huiFang' + i).find('textarea').attr('isrule',true);
                        }
                        avalon.scan(dialogVm.widgetElement, [].concat());
                    } else if (selfCon.optionContent != '是' && parentIndex == 0) {
                        for (var i = 1; i <= 4; i++) {
                            if(vm.acessRequestData[i].questionType == '1'||vm.acessRequestData[i].questionType == '2'){
                                for(var j = 0; j < vm.acessRequestData[i].sysDocumentDtlOptionList.length; j++){
                                    vm.acessRequestData[i].sysDocumentDtlOptionList[j].isSelected = '0';
                                }
                            }else if(vm.acessRequestData[i].questionType == '3'){
                                vm.acessRequestData[i].comment = '';
                            }
                            $('.huiFang' + i).hide();
                            $('.huiFang' + i).find('.valid-error').hide().html();
                            $('.huiFang' + i).find('textarea').removeClass('valid-error-field').removeAttr('isrule');
                            $('.huiFang' + i).find('.input-hidden-' + i).removeAttr('isrule');
                            $('.huiFang' + i).find('input').removeAttr('checked');
                            $('.input-hidden-' + i).val('');
                        }
                    }
                };
                vm.checkBox = function () {
                    var flag = false;
                    var con = this.$vmodel.el;
                    var parentIndex = this.$vmodel.$outer.$index;
                    if (con.isSelected == '1') {
                        con.isSelected = '0';
                    } else {
                        con.isSelected = '1';
                    }
                    var parentArr = this.$vmodel.$outer.el.sysDocumentDtlOptionList;
                    for (var i = 0; i < parentArr.length; i++) {
                        if (parentArr[i].isSelected == '1') {
                            flag = true;
                        }
                    }
                    if (flag) {
                        $('.input-hidden-' + parentIndex).val('xxx');
                    } else {
                        $('.input-hidden-' + parentIndex).val('');
                    }
                    var dialogVm = avalon.getVm(getTypeId("addAccessDialog"));
                    util.testing(dialogVm.widgetElement, $('.input-hidden-' + parentIndex));
                };
                vm.$addAccessDialogOpts = {
                    "dialogId": getTypeId("addAccessDialog"),
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                            footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                '<div class="ui-dialog-btns" ms-if="!readonly">' +
                                '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">保&nbsp;存</button>' +
                                '<span class="separation"></span>' +
                                '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button>' +
                                '</div>' +
                                '<div class="ui-dialog-btns" ms-if="readonly">' +
                                '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">关&nbsp;闭</button>' +
                                '</div>' +
                                '</div>';
                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "width": 600,
                    "readonly": false,
                    "onOpen": function () {
                        setTimeout(function () {
                            $('.huifangdan-con').scrollTop(0);
                        }, 500)
                    },
                    "onClose": function () {
                        vm.acessRequestData = [];
                    },
                    "submit": function (evt) {
                        var dialogVm = avalon.getVm(getTypeId("addAccessDialog"));
                        var requestData = {};
                        var url;
                        var flag = util.testing(dialogVm.widgetElement);
                        if (flag) {
                            if (pageVm.step9AccessId) {
                                requestData.id = pageVm.step9AccessId;
                                url = 'sysDocument/saveDocumentAndUpdateStatusForStep9.do';
                            } else {
                                url = 'sysDocument/saveDocumentAndUpdateStatusForStep9.do';
                            }
                            requestData.documentType = 1;
                            requestData.merchantId = pageVm.merchantId;
                            requestData.sysDocumentDtlList = pageVm.acessRequestData.$model;
                            requestData = JSON.stringify(requestData);
                            util.c2s({
                                "url": erp.BASE_PATH + url,
                                "type": "post",
                                "contentType": 'application/json',
                                "data": requestData,
                                "success": function (responseData) {
                                    if (responseData.flag == 1) {
                                        //关闭弹框
                                        dialogVm.close();
                                        updateList({"merchantId": pageVm.merchantId, "userId": loginUserData.id, "resourceRemark": "end_custom_service"}, "fesOnlineProcess/listFesOnlineProcesss.do", listWaiting);
                                    }
                                }
                            });
                        }else{//处理跳转到错误的位置
                            var errorEle = $('.huifangdan-con',dialogVm.widgetElement).find('.valid-error')[0];
                            if(errorEle){
                                var elePosition = $(errorEle).closest('.ff-value').position('.f-body',dialogVm.widgetElement).top+$('.f-body',dialogVm.widgetElement).scrollTop();
                                $('.f-body').scrollTop(elePosition<0?0:elePosition);
                            }
                        }
                    }
                };
                vm.$addAccessFormOpts = {
                    "formId": getTypeId("addAccessForm")
                };
                /*回访单-end*/
                /*回访录音*/
                vm.$step9AttachUploaderOpts = {//上传9
                    "uploaderId": getTypeId('-attachUploaderId'),
                    "uploadifyOpts": {
                        "uploader": erp.BASE_PATH + 'sysDocument/uploadRecordAndSaveSysDocumentForStep9.do',
                        "fileObjName": "myfile",
                        "multi": false, //多选
                        "fileTypeDesc": "上传附件(*.*)",
                        "fileTypeExts": "*.*",
                        "formData": function () {
                            return {
                                "sessionId": loginUserData.sessionId,
                                inputSysDocument: JSON.stringify({
                                    "merchantId": pageVm.merchantId, //"商户ID"
                                    "documentType": 1 //"填单类型"
                                })
                            };
                        },
                        "width": 110,
                        "height": 30,
                        "onUploadProgress": function (file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
                            var ratio = (bytesUploaded / bytesTotal) * 100 > 100 ? 100 : (bytesUploaded / bytesTotal) * 100;
                            ratio = ratio + '%';
                            var fileName = file.name;
                            var tip = '';
                            if (ratio === '100%') {
                                tip = '上传完成';
                            } else {
                                tip = '正在上传：' + fileName;
                            }
                            if (!bytesUploaded) {
                                $('.step9-upfile-list').append(
                                    '<div class="upfile-file" style="position:relative;width:598px;height: 25px;border:1px solid #ccc;background:#bebebe;">' +
                                        '<p class="loding-file" style="position:absolute;left:0;top:-1px;width:' + ratio + ';height:27px;background:#67bb0d;"></p>' +
                                        '<p class="tips-model" style="position:absolute;left:0;top:0;height:25px;line-height:25px;text-align:center;width:100%;color:#fff;">' + tip + '</p>' +
                                        '</div>'
                                );
                            } else {
                                $('.loding-file').css('width', ratio);
                                $('.tips-model').html(tip);
                            }
                        }
                    },
                    "onSuccessResponseData": function (responseText, file) {
                        responseText = JSON.parse(responseText);
                        if (responseText.flag) {
                            setTimeout(function () {
                                $('.upfile-file').remove();
                                updateList({"merchantId": pageVm.merchantId, "userId": loginUserData.id, "resourceRemark": "end_custom_service"}, "fesOnlineProcess/listFesOnlineProcesss.do", listWaiting);
                            }, 1000);
                        } else {
                            $('.upfile-file').remove();
                            util.alert({
                                "content": "上传失败！",
                                "iconType": "error"
                            });
                        }
                    }
                };
                /*回访录音－end*/
                /*更改完成状态码*/
                vm.stepEnd = function (id, status, str, stepNum) {
                    var obj = {
                        id: id,
                        onlineProcessStatus: status
                    };
                    var requestData = JSON.stringify(obj);
                    util.confirm({
                        "content": str,
                        "onSubmit": function () {
                            util.c2s({
                                "url": erp.BASE_PATH + "fesOnlineProcess/updateFesOnlineProcessStatus.do",
                                "type": "post",
                                "contentType": 'application/json',
                                "data": requestData,
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        for (var i = 0; i < vm.pageData.length; i++) {
                                            if (vm.pageData[i].stepNum == stepNum) {
                                                vm.pageData[i].listAttachmentAndOperateLog.push(responseData.data);
                                                vm.pageData[i].onlineProcessStatus = responseData.data.onlineProcessStatus;
                                            }

                                        }
                                    }
                                }
                            });
                        }
                    });

                };
                /*更改完成状态吗-end*/
            });
            avalon.scan(pageEl[0]);
            /*页面初始化请求渲染*/
            function updateList(obj, url, callBack) {//其他渲染方法
                var requestData = JSON.stringify(obj);
                util.c2s({
                    "url": erp.BASE_PATH + url,
                    "type": "post",
                    "contentType": 'application/json',
                    "data": requestData,
                    "success": callBack
                });
            }

            /*页面初始化请求渲染-end*/
            /*商户回调*/
            updateList({"merchantId": pageVm.merchantId}, "synMerchant/getMerchantInfoByOrder.do", merchantCallBack);
            function merchantCallBack(responseData) {
                var data;
                if (responseData.flag) {
                    data = responseData.data;
                    var obj = {};
                    obj.productList = data.productList;
                    obj.filePath = data.filePath;
                    var tmpObj = data.merchantInfo;
                    for (var k in tmpObj) {
                        var value = tmpObj.hasOwnProperty(k);
                        if (value) {
                            obj[k] = tmpObj[k];
                        }
                    }
                    if (obj.attachment_name) {
                        obj.imgUrl = pageVm.path + obj.filePath + '/' + obj.attachment_path + '/' + obj.attachment_name;
                    } else {
                        obj.imgUrl = pageVm.path + 'portal/origin/asset/image/merchant-default-logo.jpg';
                    }
                    pageVm.merchantData = obj;
                    pageVm.navCrumbs[1].text = obj.merchant_name;
                    pageVm.merchantName = obj.merchant_name;
                }
            }

            /*商户回调end*/
            /*list*/
            updateList({"merchantId": pageVm.merchantId, "userId": loginUserData.id, "resourceRemark": "end_custom_service", "itemStatus": pageVm.itemStatus}, "fesOnlineProcess/listFesOnlineProcesss.do", listWaiting);
            function listWaiting(responseData) {
                var data;
                if (responseData.flag) {
                    data = responseData.data;
                    var arr = [];
                    var uploadVm = avalon.getVm(getTypeId('-attachUploaderId'));
                    uploadVm && $(uploadVm.widgetElement).remove();
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].stepNum == 'micro_message') {
                            arr.push(data[i]);
                        }
                        if (data[i].stepNum == 'final_program') {
                            arr.push(data[i]);
                        }
                        if (data[i].stepNum == 'entity_cards') {
                            arr.push(data[i]);
                        }
                        if (data[i].stepNum == 'background_set') {
                            arr.push(data[i]);
                        }
                        if (data[i].stepNum == 'train') {
                            arr.push(data[i]);
                        }
                    }
                    pageVm.pageData = arr;
                }
            }
            /*list-end*/
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

