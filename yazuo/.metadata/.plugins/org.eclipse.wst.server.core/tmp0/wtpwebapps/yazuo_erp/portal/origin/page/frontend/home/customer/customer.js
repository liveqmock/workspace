/**
 * 我的主页-客户服务看板
 */
define(['avalon', 'util', 'json', 'moment', '../../../../widget/dialog/dialog', '../../../../widget/form/select', '../../../../widget/form/form', '../../../../widget/calendar/calendar', '../../../../widget/uploader/uploader', '../../../../module/cardrulesetting/cardrulesetting', '../../../../widget/editor/editor', '../../../../module/interview/interview', '../../../../module/survey/survey', '../../../../module/address/address', '../../../../module/opencard/opencard', '../../../../module/addquestion/addquestion'], function (avalon, util, JSON) {
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

            var uploadInfoEl = $('.upload-info', pageEl);
            var cardrulesettingId = pageName + '-cardrulesetting';
            var pageVm = avalon.define(pageName, function (vm) {
                vm.localHref = function (url) {//页面跳转
                    util.jumpPage(url + vm.merchantId);
                };
                vm.promise = false;//权限
                vm.navCrumbs = [
                    {
                        "text": "我的主页",
                        "href": "#/frontend/home"
                    },
                    {
                        "text": ""
                    }
                ];
                vm.path = erp.BASE_PATH;
                vm.merchantId = parseInt(routeData.params["id"]);
                vm.merchantName = '';
                vm.paramsInfo = routeData.params["info"];
                if(vm.paramsInfo){
                    /*设置导航*/
                    var type = routeData.params["type"];
                    if (type == 'sale') {
                        var info = JSON.parse(decodeURIComponent(routeData.params["info"]));
                        vm.navCrumbs = info;
                    }else{
                        var info = JSON.parse(decodeURIComponent(routeData.params["info"]));
                        vm.navCrumbs = info;
                    }
                    /*设置导航end*/
                }

                vm.stepData = {};
                vm.imgUrl = '';
                /*merchant信息*/
                var merchantModel = function () {
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
                                        var data = responseData.data;
                                        vm.stepData[data.stepId - 1].onlineProcessStatus = data.onlineProcessStatus;
                                        vm.stepData[data.stepId - 1].sysOperationLogVOs.push(data);
                                        vm.stepData[9].onlineProcessStatus = data.finalStatus;
                                        if ((data.stepId - 1) == 3) {
                                            //alert(status)
                                            vm.stepData[3].onlineProcessStatus = status;
                                        }
                                    }
                                }
                            });
                        }
                    });

                };
                /*更改完成状态吗-end*/
                /*删除上传*/
                vm.removeUpload = function (fid, id, index) {//删除上传
                    util.confirm({
                        "content": "确定要删除选中的附件吗？",
                        "onSubmit": function () {
                            util.c2s({
                                "url": erp.BASE_PATH + "fesOnlineProcess/deleteAttachment/" + fid + "/" + id + ".do",
                                "type": "get",
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        var data = responseData.data;
                                        if (data.stepId == 9) {
                                            vm.stepData[data.stepId - 1].listAttachmentAndOperateLog.splice(index, 1);
                                        } else {
                                            vm.stepData[data.stepId - 1].fesSysAttachments.splice(index, 1);
                                        }
                                        vm.stepData[data.stepId - 1].onlineProcessStatus = data.onlineProcessStatus;
                                    }
                                }
                            });
                        }
                    });
                };
                /*删除上传-end*/
                /**
                 * 步骤1开始
                 * */

                /*调研单*/
                vm.$surveyOpts = {
                    "surveyId": getTypeId('-surveyModule'),
                    "displayType": '',
                    "merchantId": '',
                    "callFn": function (responseData) {
                        if (responseData.flag == 1) {
                            var fid = pageVm.stepData[0].id;

                            function step1CallBack(responseData) {
                                if (responseData.flag) {
                                    pageVm.stepData.splice(0, 1, responseData.data[0]);
                                }
                            }

                            updateList({"processId": fid}, "fesOnlineProcess/listFesOnlineProcesss.do", step1CallBack);
                        }
                    }
                };
                vm.addSurvey = function (status) {
                    var dialog = avalon.getVm(getTypeId('-surveyModule'));
                    dialog.displayType = 'add';
                    dialog.moduleType = 'fes';
                    dialog.merchantId = pageVm.merchantId;
                    dialog.requestData = {
                        merchantId: pageVm.merchantId,
                        id: $(this).attr('data-id')
                    };
                    dialog.open()
                };
                vm.readSurvey = function (status) {
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
                vm.removeResearch = function (id, storeId) {
                    var requestData = {id: id, moduleType: 'fes', storeId: storeId};
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
                                        if (responseData.flag == 1) {
                                            var fid = pageVm.stepData[0].id;

                                            function step1CallBack(responseData) {
                                                if (responseData.flag) {
                                                    pageVm.stepData.splice(0, 1, responseData.data[0]);
                                                }
                                            }

                                            updateList({"processId": fid}, "fesOnlineProcess/listFesOnlineProcesss.do", step1CallBack);
                                        }
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
                    "sessionId": '',
                    "displayType": 'add',
                    "moduleType": '',
                    "merchantId": '',
                    "callFn": function (responseData) {
                        var fid = pageVm.stepData[0].id;

                        function step1CallBack(responseData) {
                            if (responseData.flag) {
                                pageVm.stepData.splice(0, 1, responseData.data[0]);
                            }
                        }

                        updateList({"processId": fid}, "fesOnlineProcess/listFesOnlineProcesss.do", step1CallBack);
                        updateList({"merchantId": pageVm.merchantId}, "synMerchant/getMerchantInfoByOrder.do", merchantCallBack);
                    }
                };
                vm.addInterval = function () {
                    var dialog = avalon.getVm(getTypeId('-interviewModule'));
                    dialog.requestData = {"merchantId": pageVm.merchantId};
                    dialog.sessionId = loginUserData.sessionId;
                    dialog.displayType = 'add';
                    dialog.moduleType = 'fes';
                    dialog.merchantId = pageVm.merchantId;
                    dialog.requestData = {
                        merchantId: pageVm.merchantId
                    };
                    dialog.open()
                };
                vm.readInterval = function(){
                    var dialog = avalon.getVm(getTypeId('-interviewModule'));
                    dialog.requestData = {"merchantId": pageVm.merchantId};
                    dialog.sessionId = loginUserData.sessionId;
                    dialog.displayType = 'read';
                    dialog.moduleType = 'fes';
                    dialog.merchantId = pageVm.merchantId;
                    dialog.requestData = {
                        merchantId: pageVm.merchantId
                    };
                    dialog.open()
                };

                /*访谈单-end*/

                /**
                 * 步骤1结束
                 * */
                /**
                 * 步骤3开始
                 * */
                vm.$step3AttachUploaderOpts = {//上传3
                    "uploaderId": getTypeId('step3AttachUploaderId'),
                    "uploadifyOpts": {
                        "uploader": erp.BASE_PATH + 'fesOnlineProcess/uploadFiles.do',
                        "fileObjName": "myfile",
                        "multi": false, //多选
                        "fileTypeDesc": "上传附件(*.*)",
                        "fileTypeExts": "*.*",
                        "formData": function () {
                            return {
                                "sessionId": loginUserData.sessionId,
                                "processId": vm.stepData[2].id
                            };
                        },
                        "width": 140,
                        "height": 30,
                        "onUploadProgress": function (file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
                            var ratio = (bytesUploaded / bytesTotal) * 100 > 100 ? 100 : (bytesUploaded / bytesTotal) * 100;
                            ratio = ratio+'%';
                            var fileName = file.name;
                            var tip = '';
                            if (ratio === '100%') {
                                tip = '上传完成';
                            } else {
                                tip = '正在上传：' + fileName;
                            }
                            if (!bytesUploaded) {
                                $('.step3-upfile').append(
                                    '<div class="step3-upfile-model" style="position:relative;width:598px;height: 25px;border:1px solid #ccc;background:#bebebe;">' +
                                        '<p class="step3-loding" style="position:absolute;left:0;top:-1px;width:' + ratio + ';height:27px;background:#67bb0d;"></p>' +
                                        '<p class="step3-tips" style="position:absolute;left:0;top:0;height:25px;line-height:25px;text-align:center;width:100%;color:#fff;">' + tip + '</p>' +
                                    '</div>'
                                );
                            } else {
                                $('.step3-loding').css('width',ratio);
                                $('.step3-tips').html(tip);
                            }
                        }
                    },
                    "onSuccessResponseData": function (responseText, file) {
                        responseText = JSON.parse(responseText);
                        if (responseText.flag) {
                            setTimeout(function () {
                                $('.step3-upfile-model').remove();
                                var data = responseText.data;
                                vm.stepData[2].fesSysAttachments.push(data);
                                vm.stepData[data.stepId - 1].onlineProcessStatus = data.onlineProcessStatus;
                            }, 1000);
                        } else {
                            $('.step3-upfile-model').remove();
                            util.alert({
                                "content": "上传失败！",
                                "iconType": "error"
                            });

                        }
                    }
                };
                /**
                 * 步骤3结束
                 * */
                /**
                 * 步骤4
                 * */
                var xx = 0;
                vm.$step4AttachUploaderOpts = {//上传4
                    "uploaderId": getTypeId('step4AttachUploaderId'),
                    "uploadifyOpts": {
                        "uploader": erp.BASE_PATH + 'fesOnlineProcess/uploadFiles.do',
                        "fileObjName": "myfile",
                        "multi": false, //多选
                        "fileTypeDesc": "上传附件(*.*)",
                        "fileTypeExts": "*.*",
                        "formData": function () {
                            return {
                                "sessionId": loginUserData.sessionId,
                                "processId": vm.stepData[3].id
                            };
                        },
                        "width": 140,
                        "height": 30,
                        "onUploadProgress": function (file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {

                            var ratio = (bytesUploaded / bytesTotal) * 100 > 100 ? 100 : (bytesUploaded / bytesTotal) * 100;
                            ratio = ratio+'%';
                            var fileName = file.name;
                            var tip = '';
                            if (ratio === '100%') {
                                tip = '上传完成';
                            } else {
                                tip = '正在上传：' + fileName;
                            }
                            if (!bytesUploaded) {
                                $('.step4-upfile').append(
                                    '<div class="step4-upfile-model" style="position:relative;width:598px;height: 25px;border:1px solid #ccc;background:#bebebe;">' +
                                        '<p class="step4-loding" style="position:absolute;left:0;top:-1px;width:' + ratio + ';height:27px;background:#67bb0d;"></p>' +
                                        '<p class="step4-tips" style="position:absolute;left:0;top:0;height:25px;line-height:25px;text-align:center;width:100%;color:#fff;">' + tip + '</p>' +
                                        '</div>'
                                );
                            } else {
                                $('.step4-loding').css('width',ratio);
                                $('.step4-tips').html(tip);
                            }
                        }
                    },
                    "onSuccessResponseData": function (responseText, file) {
                        responseText = JSON.parse(responseText);
                        if (responseText.flag) {
                            setTimeout(function () {
                                $('.step4-upfile-model').remove();
                                var data = responseText.data;
                                if (vm.stepData[3].fesSysAttachments.length) {
                                    vm.stepData[3].fesSysAttachments.splice(0, 1, data);
                                } else {
                                    vm.stepData[3].fesSysAttachments.push(data);
                                }
                                vm.stepData[data.stepId - 1].onlineProcessStatus = data.onlineProcessStatus;
                            }, 1000);
                        } else {
                            $('.step4-upfile-model').remove();
                            util.alert({
                                "content": "上传失败！",
                                "iconType": "error"
                            });
                        }
                    }
                };
                /**
                 * 步骤4结束
                 * */

                /**
                 * 步骤5开始
                 * */
                /*短信规则*/
                var messageModle = function () {
                    vm.messageRequestData = {//入参
                    };
                    vm.messageData = {//vm数据源
                    };
                    vm.controlWitch = function (str) {
                        if (this.$vmodel.$model.el.isAvail) {
                            if (str == 'parent') {
                                this.$vmodel.$model.el.isAvail = false;
                                var arr = this.$vmodel.$model.el.child;
                                var index = this.$vmodel.$model.el.id;
                                if (arr) {
                                    for (var i = 0; i < arr.length; i++) {
                                        arr[i].isAvail = false;
                                    }
                                }
                                $('.input' + index + '>input').attr('checked', false);
                            } else if (str == 'child') {
                                this.$vmodel.$model.el.isAvail = false;
                            }
                        } else {
                            var obj = this.$vmodel.$model.el;
                            if (str == 'parent') {
                                obj.isAvail = true;
                            } else if (str == 'child') {
                                obj.isAvail = true;
                            }
                        }
                    };
                    vm.appendValue = function (id) {
                        var select = '.textarea' + id;
                        var text = this.innerHTML;
                        var content = $(select).val();
                        if (content.indexOf(text) != -1) {
                            util.confirm({
                                "content": "不能重复添加内容",
                                "onSubmit": function () {
                                }
                            });
                            return false;
                        }
                        util.appendTextAtCursor(select, text);
                        function replaceText(arr, id, text) {
                            for (var i = 0; i < arr.length; i++) {
                                if (arr[i].id == id) {
                                    arr[i].templateTxt = text;
                                    return;
                                } else if (arr[i].child && arr[i].child instanceof Array) {
                                    replaceText(arr[i].child, id, text);
                                }
                            }
                        }

                        var contentAll = $(select).val();
                        replaceText(pageVm.messageData.$model, id, contentAll);
                    };
                    vm.openMessageRules = function (index) {
                        var dialogVm = avalon.getVm(getTypeId('messageRulesId'));
                        if (index == '04' || !pageVm.promise) {
                            dialogVm.readonly = true;
                        } else {
                            dialogVm.readonly = false;
                        }
                        util.c2s({
                            "url": erp.BASE_PATH + "trade/messageTemplate/listMessageTemplates.do",
                            "type": "post",
                            //"contentType" : 'application/json',
                            "data": {merchantId: pageVm.merchantId},
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    var arr = responseData.data;
                                    var list = [];
                                    var tmpList = [];
                                    var transCodeList = [];
                                    for (var i = 0; i < arr.length; i++) {
                                        transCodeList.push(arr[i].transCode);
                                    }
                                    var requestData = JSON.stringify(transCodeList);
                                    util.c2s({
                                        "url": erp.BASE_PATH + "trade/messageTemplate/listSmsTmpFields.do",
                                        "type": "post",
                                        "contentType": 'application/json',
                                        "data": requestData,
                                        "success": function (responseData) {
                                            if (responseData.flag == 1) {
                                                var data = responseData.data;
                                                for (var i = 0; i < arr.length; i++) {
                                                    for (var k in data) {
                                                        var value = data.hasOwnProperty(k);
                                                        if (value) {
                                                            if (arr[i].transCode == k) {
                                                                arr[i].listType = [];
                                                                arr[i].listType = data[k];
                                                            }
                                                        }
                                                    }
                                                }
                                                for (var i = 0; i < arr.length; i++) {
                                                    if (!arr[i].parent) {
                                                        list.push(arr[i]);
                                                    } else {
                                                        tmpList.push(arr[i]);
                                                    }
                                                }
                                                for (var i = 0; i < list.length; i++) {
                                                    for (var j = 0; j < tmpList.length; j++) {
                                                        if (tmpList[j].parent == list[i].id) {
                                                            if (!list[i].child) {
                                                                list[i].child = [];
                                                            }
                                                            list[i].child.push(tmpList[j]);
                                                        }
                                                    }
                                                }
                                                vm.messageData = list;
                                                dialogVm.title = "交易短信规则";
                                                dialogVm.open();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    };
                    vm.$messageRulesOpts = {
                        "dialogId": getTypeId('messageRulesId'),
                        "getTemplate": function (tmpl) {
                            var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                                footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                    '<div class="ui-dialog-btns" ms-if="!readonly">' +
                                    '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">保&nbsp;存</button>' +
                                    '<span class="separation"></span>' +
                                    '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button>' +
                                    '</div>' +
                                    '<div class="ui-dialog-btns" ms-if="readonly">' +
                                    '<button type="button" class="submit-btn ui-dialog-btn" ms-click="close">确&nbsp;定</button>' +
                                    '</div>' +
                                    '</div>';
                            return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                        },
                        "width": 670,
                        "readonly": false,
                        "onOpen": function () {
                        },
                        "onClose": function () {
                        },
                        "submit": function (evt) {
                            var matchArr = [];
                            util.c2s({
                                "url": erp.BASE_PATH + "trade/smstmpfeild/listTradeSmsTmpFeilds.do",
                                "type": "post",
                                "success": function (responseData) {
                                    if (responseData.flag == 1) {
                                        matchArr = responseData.data;//存储字段
                                        var arr = [];
                                        var tmpArr = pageVm.messageData.$model;
                                        for (var i = 0; i < tmpArr.length; i++) {
                                            arr.push(tmpArr[i]);
                                            if (tmpArr[i].child) {
                                                arr = arr.concat(tmpArr[i].child);
                                            }
                                        }

                                        for (var i = 0; i < arr.length; i++) {
                                            delete arr[i].listType;
                                            delete arr[i].child;
                                            var feildArr = arr[i].templateTxt.match(/\[([^\]])*\]/g);
                                            try {
                                                for (var j = 0; j < feildArr.length; j++) {
                                                    feildArr[j] = feildArr[j].replace(/]|\[/g, '');
                                                }
                                                var feildStrArr = [];
                                                for (var j = 0; j < matchArr.length; j++) {
                                                    for (var k = 0; k < feildArr.length; k++) {
                                                        if (feildArr[k] == matchArr[j].description) {
                                                            feildStrArr.push(matchArr[j].fieldName);
                                                        }
                                                    }
                                                }
                                                arr[i].templateFeilds = feildStrArr.join();

                                            } catch (e) {
                                            }
                                            for (var j = 0; j < matchArr.length; j++) {
                                                var str = arr[i].templateTxt;
                                                var modelStr = matchArr[j].description;
                                                if (str.indexOf("[" + modelStr + "]") != -1) {
                                                    var reg = RegExp('\\[' + modelStr + ']', 'g');
                                                    str = str.replace(reg, matchArr[j].fieldType);
                                                }
                                                arr[i].templateTxt = str;
                                            }
                                        }
                                        for (var i = 0; i < arr.length; i++) {
                                            arr[i].merchantId = pageVm.merchantId;
                                            arr[i].messageTemplate = arr[i].templateTxt;
                                        }
                                        var requestData = JSON.stringify(arr);
                                        util.c2s({
                                            "url": erp.BASE_PATH + 'trade/messageTemplate/saveMessageTemplates.do',
                                            "type": "post",
                                            "contentType": 'application/json',
                                            "data": requestData,
                                            "success": function (responseData) {
                                                if (responseData.flag == 1) {
                                                    var data = responseData.data;
                                                    var dialogVm = avalon.getVm(getTypeId('messageRulesId'));
                                                    dialogVm.close();
                                                    vm.stepData[4].tradeMessageTemplates.push(data);
                                                    vm.stepData[4].sysOperationLogVOs.push(data);
                                                    vm.stepData[4].onlineProcessStatus = data.onlineProcessStatus;
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    };
                    vm.$messageRulesFormOpts = {
                        "formId": getTypeId('messageRulesFormId'),
                        "field": [
                            {
                                "selector": ".beginTime",
                                "name": "beginTime",
                                "rule": []
                            }
                        ]
                    };
                };
                messageModle();
                /*短信规则-end*/

                //会员卡规则配置项
                vm.$cardrulesettingOpts = {
                    "cardrulesettingId": cardrulesettingId,
                    "merchantId": routeData.params["id"] / 1,
                    "onUpdateCallback": function (responseData) {
                        if (responseData.flag) {    //业务成功
                            var data = responseData.data;
                            vm.stepData[4].tradeCardtypes.push(data);
                            vm.stepData[4].sysOperationLogVOs.push(data);
                            vm.stepData[4].onlineProcessStatus = data.onlineProcessStatus;
                        }
                    }
                };
                vm.openCardRuleDialog = function (index) {
                    if (index == '04' || !pageVm.promise) {
                        avalon.getVm(cardrulesettingId).setReadonly(1)
                    }
                    avalon.getVm(cardrulesettingId).open();
                };
                /*会员卡规则-end*/
                /**
                 * 步骤5结束
                 * */
                /**
                 * 步骤6
                 * */
                /*申请开卡*/
                vm.$opencardOpts = {
                    "opencardId" : getTypeId('-opencardId'),
                    "options" : [],
                    "displayType" : 'read',
                    "processId" : 0,
                    "merchantId" : 0,
                    "merchantName" : '',
                    "callFn" : function(responseData){
                        alert(responseData)
                    }
                };

                vm.openCard = function (processId,readonly) {
                    var dialogVm = avalon.getVm(getTypeId('-opencardId'));
                    dialogVm.displayType = 'read';
                    dialogVm.processId = processId;
                    dialogVm.merchantId = vm.merchantId;
                    dialogVm.merchantName = vm.merchantName;
                    dialogVm.open();
                };
                /*申请开卡-end*/
                /**
                 * 步骤6结束step7Input
                 * */
                /**
                 * 步骤7
                 * */


                vm.$step7AttachUploaderOpts = {//上传7
                    "uploaderId": getTypeId('step7AttachUploaderId'),
                    "uploadifyOpts": {
                        "uploader": erp.BASE_PATH + 'fesOnlineProcess/uploadFiles.do',
                        "fileObjName": "myfile",
                        "multi": false, //多选
                        "fileTypeDesc": "上传附件(*.*)",
                        "fileTypeExts": "*.*",
                        "formData": function () {
                            return {
                                "sessionId": loginUserData.sessionId,
                                "processId": vm.stepData[6].id,
                                "typeId": 15
                            };
                        },
                        "width": 140,
                        "height": 30,
                        "onUploadProgress": function (file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
                            var ratio = (bytesUploaded / bytesTotal) * 100 > 100 ? 100 : (bytesUploaded / bytesTotal) * 100;
                            ratio = ratio+'%';
                            var fileName = file.name;
                            var tip = '';
                            if (ratio === '100%') {
                                tip = '上传完成';
                            } else {
                                tip = '正在上传：' + fileName;
                            }
                            if (!bytesUploaded) {
                                $('.step7-upfile').append(
                                    '<div class="step7-upfile-model" style="position:relative;width:598px;height: 25px;border:1px solid #ccc;background:#bebebe;">' +
                                        '<p class="step7-loding" style="position:absolute;left:0;top:-1px;width:' + ratio + ';height:27px;background:#67bb0d;"></p>' +
                                        '<p class="step7-tips" style="position:absolute;left:0;top:0;height:25px;line-height:25px;text-align:center;width:100%;color:#fff;">' + tip + '</p>' +
                                        '</div>'
                                );
                            } else {
                                $('.step7-loding').css('width',ratio);
                                $('.step7-tips').html(tip);
                            }
                        }
                    },
                    "onSuccessResponseData": function (responseText, file) {
                        responseText = JSON.parse(responseText);
                        if (responseText.flag) {
                            setTimeout(function () {
                                $('.step7-upfile-model').remove();
                                var data = responseText.data;
                                if (vm.stepData[6].fesSysAttachments.length) {
                                    vm.stepData[6].fesSysAttachments.splice(0, 1, data);
                                } else {
                                    vm.stepData[6].fesSysAttachments.push(data);
                                }
                                vm.stepData[data.stepId - 1].onlineProcessStatus = data.onlineProcessStatus;
                            }, 1000);
                        } else {
                            $('.step7-upfile-model').remove();
                            util.alert({
                                "content": "上传失败！",
                                "iconType": "error"
                            });

                        }

                    }
                };

                //设计需求单
                vm.setp7ReqData = {
                    "id": 0,//编辑的时候需要传id
                    "processId": 0,//流程id
                    "accessId": 1,  //对接人
                    "description": "",
                    "attachmentId": 0,//附件id
                    "sysAttachmentVO": {//"附件信息"
                        "originalFileName": '',
                        "fileFullPath": ''
                    },
                    "fesMaterialRequestDtlVOs": [
                        {
                            "materialRequestType": "1",  //设计需求类型
                            "specificationType": "1",  //设计需求规格
                            "description": ""//文本框输入
                        },
                        {
                            "materialRequestType": "8",
                            "specificationType": "1",
                            "description": ""//文本框输入
                        },
                        {
                            "materialRequestType": "13",
                            "specificationType": "1",
                            "description": ""//文本框输入
                        },
                        {
                            "materialRequestType": "10",
                            "specificationType": "1",
                            "description": ""//文本框输入
                        },
                        {
                            "materialRequestType": "7",
                            "specificationType": "1",
                            "description": ""//文本框输入
                        },
                        {
                            "materialRequestType": "11",
                            "specificationType": "1",
                            "description": ""//文本框输入
                        },
                        {
                            "materialRequestType": "9",
                            "specificationType": "1",
                            "description": ""//文本框输入
                        }
                    ]
                };
                vm.accessName = '';
                vm.step7Uploading = true;
                vm.step7Input = function (val,type) {
                    var dialogVm = avalon.getVm(getTypeId('step7DialogId'));
                    function requireName() {
                        util.c2s({
                            "url": erp.BASE_PATH + 'contact/searchContacts.do',
                            "type": "post",
                            "contentType": 'application/json',
                            "data": JSON.stringify({'merchantId': pageVm.merchantId}),
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    var data = responseData.data.rows;
                                    var setp7select = avalon.getVm(getTypeId('step7NameSelect'));
                                    var values = _.map(data, function (item, index) {
                                        return {
                                            "text": item["name"],
                                            "value": item["id"]
                                        };
                                    });
                                    setp7select.setOptions(values);
                                    if(type == 'edit'||type == 'add'){
                                        dialogVm.readonly = false;
                                    }else{
                                        dialogVm.readonly = true;
                                    }
                                    dialogVm.open();
                                }
                            }
                        });
                    }
                    if (val) {
                        util.c2s({
                            "url": erp.BASE_PATH + 'fesOnlineProcess/getFesMaterialRequestById.do',
                            "type": "post",
                            "contentType": 'application/json',
                            "data": JSON.stringify({'id': val}),
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    pageVm.setp7ReqData = responseData.data;
                                    requireName();
                                }
                            }
                        });
                    } else {
                        requireName();
                    }
                };
                vm.$step7DialogOpts = {
                    "dialogId": getTypeId('step7DialogId'),
                    "title": '设计需求单',
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                            footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                '<div class="ui-dialog-btns" ms-visible="!readonly">' +
                                '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">保&nbsp;存</button>' +
                                '<span class="separation"></span>' +
                                '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button>' +
                                '</div>' +
                                '<div class="ui-dialog-btns" ms-visible="readonly">' +
                                '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">关&nbsp;闭</button>' +
                                '</div>' +
                                '</div>';
                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "readonly": false,
                    "onOpen": function () {
                    },
                    "onClose": function () {
                        pageVm.setp7ReqData = {
                            "id": 0,//编辑的时候需要传id
                            "processId": 0,//流程id
                            "accessId": 1,  //对接人
                            "description": "",
                            "attachmentId": 0,
                            "sysAttachmentVO": {//"附件信息"
                                "originalFileName": '',
                                "fileFullPath": ''
                            },
                            "fesMaterialRequestDtlVOs": [
                                {
                                    "materialRequestType": "",  //设计需求类型
                                    "specificationType": "1",  //设计需求规格
                                    "description": ""//文本框输入
                                },
                                {
                                    "materialRequestType": "",
                                    "specificationType": "1",
                                    "description": ""//文本框输入
                                },
                                {
                                    "materialRequestType": "",
                                    "specificationType": "1",
                                    "description": ""//文本框输入
                                },
                                {
                                    "materialRequestType": "",
                                    "specificationType": "1",
                                    "description": ""//文本框输入
                                },
                                {
                                    "materialRequestType": "",
                                    "specificationType": "1",
                                    "description": ""//文本框输入
                                },
                                {
                                    "materialRequestType": "",
                                    "specificationType": "1",
                                    "description": ""//文本框输入
                                },
                                {
                                    "materialRequestType": "",
                                    "specificationType": "1",
                                    "description": ""//文本框输入
                                }
                            ]
                        };
                        var dialog = avalon.getVm(getTypeId('step7DialogId'));
                        util.testCancel($(dialog.widgetElement)[0]);
                    },
                    "width": 600,
                    "submit": function (evt) {
                        var dialog = avalon.getVm(getTypeId('step7DialogId'));
                        var flag = util.testing(dialog.widgetElement);
                        var test = false;
                        if (flag) {
                            pageVm.setp7ReqData.processId = pageVm.stepData[6].id;
                            pageVm.setp7ReqData.accessId = avalon.getVm(getTypeId('step7NameSelect')).selectedValue;
                            var requestData = JSON.stringify(pageVm.setp7ReqData.$model);
                            var dataObj = pageVm.setp7ReqData.fesMaterialRequestDtlVOs;
                            for(var i=1;i<dataObj.length;i++){
                                if(dataObj[i].specificationType != '1'){
                                    test = true;
                                }
                            }
                            if(!test){
                                util.alert({
                                    "content": "请至少选择一项物料！",
                                    "iconType": "error"
                                });
                                return;
                            }
                            util.c2s({
                                "url": erp.BASE_PATH + 'fesOnlineProcess/saveFesMaterialRequestAndDtls.do',
                                "type": "post",
                                "contentType": 'application/json',
                                "data": requestData,
                                "success": function (responseData) {
                                    if (responseData.flag == 1) {
                                        var data = responseData.data;
                                        pageVm.stepData.splice(6, 1, data);
                                        dialog.close();
                                    }
                                }
                            });
                        }
                    }
                };

                var step7arr = [];
                vm.$step7FormOpts = {
                    "formId": getTypeId('step7FormId'),
                    "field": step7arr
                };
                vm.$step7NameSelectOpts = {
                    "selectId": getTypeId('step7NameSelect'),
                    "options": [],
                    "selectedIndex": 1,
                    "getTemplate": function (tmpl) {
                        var tmps = tmpl.split('MS_OPTION_HEADER');
                        tmpl = 'MS_OPTION_HEADER<ul class="select-list" ms-on-click="selectItem"ms-css-min-width="minWidth">' +
                            '<li class="select-item" ms-repeat="options" ms-css-min-width="minWidth - 16"' +
                            'ms-class="state-selected:$index==selectedIndex" ms-class-last-item="$last" ms-hover="state-hover" ' +
                            'ms-data-index="$index" ms-attr-title="el.text" ms-data-value="el.value">{{el.text}}</li>' +
                            '<li><a style="display: block;line-height: 35px;background: #eee;border-top: 1px solid #ccc;cursor: pointer;text-indent: 10px;" ms-click=addCommunication("step7NameSelect") href="javascript:;">添加联系人</a></li>' +
                            '</ul>';
                        return tmps[0] + tmpl;
                    },
                    "onSelect": function(v,t,o){
                        pageVm.setp7ReqData.accessId = v;
                        pageVm.accessName = t;
                    }
                };
                /*第7步通讯录*/
                vm.$addressOpts = {
                    "addressId": getTypeId('-addressModule'),
                    "displayType": 'add',
                    "moduleType": 'fes',
                    "merchantId": 0
                };
                vm.addCommunication = function (name) {
                    var dialog = avalon.getVm(getTypeId('-addressModule'));
                    dialog.displayType = 'add';
                    dialog.callFn = function (responseData) {
                        var data = responseData.data;
                        var arr = {
                            text:data['name'],
                            value:data['id']
                        };
                        var merchantDutyArr = avalon.getVm(getTypeId(name)).panelVmodel.options;
                        merchantDutyArr.push(arr);
                        avalon.getVm(getTypeId(name)).setOptions(merchantDutyArr,merchantDutyArr.length-1);
                    };
                    dialog.requestData = {
                        merchantId: vm.merchantId
                    };
                    dialog.open();
                };
                /*7步通讯录-end*/
                //设计需求单的上传附件
                vm.$step7AttachInput = {
                    "uploaderId": getTypeId('step7AttachInput'),
                    "uploadifyOpts": {
                        "uploader": erp.BASE_PATH + 'fesOnlineProcess/uploadFesmaterialAttr.do',
                        "fileObjName": "myfile",
                        "multi": false, //多选
                        "fileTypeDesc": "上传附件(*.*)",
                        "fileTypeExts": "*.*",
                        "formData": function () {
                            return {
                                "sessionId": loginUserData.sessionId
                            };
                        },
                        "width": 140,
                        "height": 30,
                        "onUploadProgress": function (file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
                            vm.step7Uploading = false;
                            var ratio = (bytesUploaded / bytesTotal) * 100 > 100 ? 100 : (bytesUploaded / bytesTotal) * 100;
                            ratio = ratio+'%';
                            var fileName = file.name;
                            var tip = '';
                            if (ratio === '100%') {
                                tip = '上传完成';
                            } else {
                                tip = '正在上传：' + fileName;
                            }
                            if (!bytesUploaded) {
                                $('.step7-input-upfile').append(
                                    '<div class="step7-upfile-model" style="position:relative;width:400px;height: 25px;border:1px solid #ccc;background:#bebebe;">' +
                                        '<p class="step7-loding" style="position:absolute;left:0;top:-1px;width:' + ratio + ';height:27px;background:#67bb0d;"></p>' +
                                        '<p class="step7-tips" style="position:absolute;left:0;top:0;height:25px;line-height:25px;text-align:center;width:100%;color:#fff;">' + tip + '</p>' +
                                        '</div>'
                                );
                            } else {
                                $('.step7-loding').css('width',ratio);
                                $('.step7-tips').html(tip);
                            }
                        }
                    },
                    "onSuccessResponseData": function (responseText, file) {
                        responseText = JSON.parse(responseText);
                        if (responseText.flag) {
                            setTimeout(function () {
                                $('.step7-upfile-model').remove();
                                var data = responseText.data;
                                vm.setp7ReqData.attachmentId = data.id;
                                vm.setp7ReqData.sysAttachmentVO.originalFileName = data.originalFileName;
                                vm.setp7ReqData.sysAttachmentVO.fileFullPath = data.fileFullPath;
                                vm.step7Uploading = true;
                            }, 1000);
                        } else {
                            $('.step7-upfile-model').remove();
                            util.alert({
                                "content": "上传失败！",
                                "iconType": "error"
                            });
                            vm.step7Uploading = true;
                        }
                    }
                };
                //设计需求单end

                /**
                 * 步骤7结束
                 * */
                /**
                 * 步骤9开始
                 * */
                vm.$addEditDialogOpts = {//弹框初始化（步骤9）
                    "dialogId": getTypeId('addEditDialogId'),
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
                        //清除验证信息
                        var formVm = avalon.getVm(getTypeId('addEditFormId'));
                        formVm.reset();
                        formVm.beginTime = '';
                        formVm.endTime = '';
                        formVm.address = '';
                        formVm.trainer = '';
                        formVm.content = '';
                    },
                    "submit": function (evt) {
                        var requestData,
                            url,
                            dialogVm = avalon.getVm(getTypeId('addEditDialogId')),
                            formVm = avalon.getVm(getTypeId('addEditFormId')),
                            addEditState = vm.step9AddEditState,
                            processId = vm.stepData.$model[8].id;
                        if (formVm.validate()) {
                            requestData = formVm.getFormData();
                            url = erp.BASE_PATH + 'fesOnlineProcess/saveFesTrainPlan.do';
                            if (addEditState == 'edit') {
                                requestData.processId = processId;
                                requestData.id = vm.stepData.$model[8].fesTrainPlans[0].id;
                            } else if (addEditState == 'add') {
                                requestData.processId = processId;
                            }
                            requestData.beginTime = moment(requestData.beginTime, 'YYYY-MM-DD');
                            requestData.endTime = moment(requestData.endTime, 'YYYY-MM-DD');
                            requestData = JSON.stringify(requestData);

                            //发送后台请求，编辑或添加
                            util.c2s({
                                "url": url,
                                "type": "post",
                                "contentType": 'application/json',
                                "data": requestData,
                                "success": function (responseData) {
                                    if (responseData.flag == 1) {
                                        var data = responseData.data;
                                        if (addEditState == 'edit') {
                                            vm.stepData[8].fesTrainPlans[0].beginTime = data.beginTime;
                                            vm.stepData[8].fesTrainPlans[0].endTime = data.endTime;
                                            vm.stepData[8].fesTrainPlans[0].address = data.address;
                                            vm.stepData[8].fesTrainPlans[0].trainer = data.trainer;
                                            vm.stepData[8].fesTrainPlans[0].content = data.content;
                                        } else if (addEditState == 'add') {
                                            vm.stepData[8].fesTrainPlans.push(data);
                                        }
                                        //关闭弹框
                                        dialogVm.close();
                                        vm.stepData[8].onlineProcessStatus = data.onlineProcessStatus;
                                    }
                                }
                            });
                        }
                    }
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
                        },
                        {
                            "selector": ".address",
                            "name": "address",
                            "rule": ["noempty", function (val, rs) {
                                if (rs[0] !== true) {
                                    return "请填写培训地点";
                                } else {
                                    return true;
                                }
                            }]
                        },
                        {
                            "selector": ".trainer",
                            "name": "trainer",
                            "rule": ["noempty", function (val, rs) {
                                if (rs[0] !== true) {
                                    return "请填写参与人员";
                                } else {
                                    return true;
                                }
                            }]
                        },
                        {
                            "selector": ".content",
                            "name": "content",
                            "rule": ["noempty", function (val, rs) {
                                if(val.length>250){
                                    return "内容不能超过250个字！";
                                }
                                if (rs[0] !== true) {
                                    return "培训内容不能为空";
                                } else {
                                    return true;
                                }
                            }]
                        }
                    ],
                    "beginTime": "",
                    "endTime": "",
                    "address": "",
                    "trainer": "",
                    "content": ""
                };
                vm.openEditStep9 = function () {
                    var dialogVm = avalon.getVm(getTypeId('addEditDialogId')),
                        formVm = avalon.getVm(getTypeId('addEditFormId')),
                        step9 = this.$vmodel.$model.el.fesTrainPlans[0];
                    dialogVm.title = '培训计划';
                    formVm.beginTime = moment(step9.beginTime).format('YYYY-MM-DD');
                    formVm.endTime = moment(step9.endTime).format('YYYY-MM-DD');
                    formVm.address = step9.address;
                    formVm.trainer = step9.trainer;
                    formVm.content = step9.content;
                    vm.step9AddEditState = 'edit';
                    dialogVm.open();
                };
                vm.openAddStep9 = function () {
                    var dialogVm = avalon.getVm(getTypeId('addEditDialogId')),
                        formVm = avalon.getVm(getTypeId('addEditFormId'));
                    dialogVm.title = '培训计划';
                    vm.step9AddEditState = 'add';
                    dialogVm.open();
                };
                /**
                 * 初始化日历插件开始时间
                 */
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
                        calendarVm.maxDate = moment(formVm.endTime, 'YYYY-MM-DD')._d;
                    }
                    calendarEl.css({
                        "top": inputOffset.top + meEl.outerHeight() - 1,
                        "left": inputOffset.left
                    }).show();
                };
                /**
                 * 初始化日历插件结束时间
                 */
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
                        calendarVm.minDate = moment(formVm.beginTime, 'YYYY-MM-DD')._d;
                    }
                    calendarEl.css({
                        "top": inputOffset.top + meEl.outerHeight() - 1,
                        "left": inputOffset.left
                    }).show();
                };
                vm.$step9AttachUploaderOpts = {//上传9
                    "uploaderId": getTypeId('attachUploaderId'),
                    "uploadifyOpts": {
                        "uploader": erp.BASE_PATH + 'fesOnlineProcess/uploadFiles.do',
                        "fileObjName": "myfile",
                        "multi": false, //多选
                        "fileTypeDesc": "上传附件(*.*)",
                        "fileTypeExts": "*.*",
                        "formData": function () {
                            return {
                                "sessionId": loginUserData.sessionId,
                                "processId": vm.stepData[8].id,
                                "typeId": '5'
                            };
                        },
                        "width": 110,
                        "height": 30,
                        "onUploadProgress": function (file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
                            var ratio = (bytesUploaded / bytesTotal) * 100 > 100 ? 100 : (bytesUploaded / bytesTotal) * 100;
                            ratio = ratio+'%';
                            var fileName = file.name;
                            var tip = '';
                            if (ratio === '100%') {
                                tip = '上传完成';
                            } else {
                                tip = '正在上传：' + fileName;
                            }
                            if (!bytesUploaded) {
                                $('.step9-upfile').append(
                                    '<div class="step9-upfile-model" style="position:relative;width:598px;height: 25px;border:1px solid #ccc;background:#bebebe;">' +
                                        '<p class="step9-loding" style="position:absolute;left:0;top:-1px;width:' + ratio + ';height:27px;background:#67bb0d;"></p>' +
                                        '<p class="step9-tips" style="position:absolute;left:0;top:0;height:25px;line-height:25px;text-align:center;width:100%;color:#fff;">' + tip + '</p>' +
                                        '</div>'
                                );
                            } else {
                                $('.step9-loding').css('width',ratio);
                                $('.step9-tips').html(tip);
                            }

                        }
                    },
                    "onSuccessResponseData": function (responseText, file) {
                        responseText = JSON.parse(responseText);
                        if (responseText.flag) {
                            setTimeout(function () {
                                $('.step9-upfile-model').remove();
                                var data = responseText.data;
                                vm.stepData[data.stepId - 1].listAttachmentAndOperateLog.push(data);
                                vm.stepData[data.stepId - 1].onlineProcessStatus = data.onlineProcessStatus;
                            }, 1000);
                        } else {
                            $('.step9-upfile-model').remove();
                            util.alert({
                                "content": "上传失败！",
                                "iconType": "error"
                            });
                        }
                    }
                };
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
                                if(vm.acessRequestData[0].sysDocumentDtlOptionList[1].isSelected == '1' && vm.acessRequestData[0].sysDocumentDtlOptionList[1].optionContent != '是'){//选中了否
                                    for (var i = 1; i < vm.acessRequestData.length; i++) {
                                        $('.huiFang' + i).hide();
                                    }
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
                                }else{//都没选中

                                }
                                dialogVm.title = "回访单";
                                dialogVm.open();
                            }
                        }
                    });
                };
                vm.radioBox = function (arr) {//以下是写死得逻辑，内容改变后必须修改此处
                    var con = arr['sysDocumentDtlOptionList'];
                    for (var i = 0; i < con.length; i++) {
                        con[i].isSelected = '0';
                        con[i].comment = '';
                    }
                    this.$vmodel.$model.el.isSelected = '1';
                    var selfCon = this.$vmodel.$model.el;
                    var parentIndex = this.$vmodel.$model.$outer.$index;
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
                    var con = this.$vmodel.$model.el;
                    var parentIndex = this.$vmodel.$model.$outer.$index;
                    if (con.isSelected == '1') {
                        con.isSelected = '0';
                    } else {
                        con.isSelected = '1';
                    }
                    var parentArr = this.$vmodel.$model.$outer.el.sysDocumentDtlOptionList;
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
                /**
                 * 步骤9结束
                 * */
                /**
                 * 步骤10
                 * */
                var onLineMaiModel = function () {
                    vm.replaceData = [];
                    vm.linkMan = {
                        data: [],
                        flag: 0
                    };
                    vm.mailModel = {
                        data: [],
                        flag: 0
                    };
                    vm.step10smsContent = false;//是否显示短信
                    vm.mailTo = '';//收件人字符串
                    vm.errorMail = '';//错误的邮箱
                    vm.mailRequestData = {//入参
                        id: "",
                        onlineProcessStatus: '04',
                        email: {
                            sendAddress: loginUserData.userName,// 发件人地址
                            to: [],//联系人邮件地址
                            context: '',//邮件正文(字符串)
                            subject: '',//邮件标题(字符串)
                            mailTemplateId : 0,//邮件模板id
                            smsContent: ''//短信正文
                        }
                    };
                    vm.$editorOpts = {
                        "editorId": getTypeId('editorId'),
                        "ueditorOptions": {
                            "UEDITOR_HOME_URL": erp.WIDGET_PATH + 'editor/ueditor/',
                            "serverUrl": erp.BASE_PATH + 'portal/origin/widget/editor/ueditor/jsp/controller.jsp',
                            "toolbars": [
                                [
                                    'fullscreen', 'source', '|', 'undo', 'redo', '|',
                                    'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                                    'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                                    'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                                    'directionalityltr', 'directionalityrtl', 'indent', '|',
                                    'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                                    'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter','simpleupload'
                                ]
                            ]
                        }
                    };
                    vm.onLineMail = function () {
                        vm.mailRequestData.id = this.$vmodel.$model.el.id;
                        var dialog = avalon.getVm(getTypeId('onLineMailDialogId'));
                        util.c2s({
                            "url": erp.BASE_PATH + 'mailAndAttachment/replaceMailTempment.do',
                            "type": "post",
                            "contentType": 'application/json',
                            "data": JSON.stringify({'merchantId': pageVm.merchantId}),
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    var data = responseData.data;
                                    pageVm.replaceData = data;
                                    dialog.title = '发送《启动服务通知函》';
                                    dialog.open();
                                }
                            }
                        });
                    };
                    vm.addLinkman = function () {
                        $(document).on('click', function (e) {
                            var target = e.target;
                            if (target.id != 'addLinkMan') {
                                pageVm.linkMan.flag = 0;
                            }
                            if (target.id != 'addMailModel') {
                                pageVm.mailModel.flag = 0;
                            }
                        });
                        util.c2s({
                            "url": erp.BASE_PATH + 'contact/searchContacts.do',
                            "type": "post",
                            "contentType": 'application/json',
                            "data": JSON.stringify({'merchantId': pageVm.merchantId}),
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    pageVm.linkMan.flag = 1;
                                    pageVm.linkMan.data = responseData.data.rows;
                                }
                            }
                        });
                    };
                    /*添加收件人失去焦点事件*/
                    setTimeout(function(){
                        $('.customer-mail-to').on('blur',function(){
                            var flag = true;
                            var arr = pageVm.mailTo.split(';');
                            for (var i = 0; i < arr.length; i++) {
                                var reg = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
                                if(!(reg.test(arr[i]))){
                                    flag = false;
                                    vm.errorMail = arr[i];
                                    var dialogVm = avalon.getVm(getTypeId('onLineMailDialogId'));
                                    util.testing($(dialogVm.widgetElement)[0],$('.common-mail-mail-test'));
                                    return;
                                }
                            }
                            if(flag){
                                vm.errorMail = '';
                                var dialogVm = avalon.getVm(getTypeId('onLineMailDialogId'));
                                util.testing($(dialogVm.widgetElement)[0],$('.common-mail-mail-test'));
                            }
                        });
                    },2000);
                    /*添加收件人失去焦点事件-end*/

                    vm.addPut = function () {
                        var flag = true;
                        var mailVal = this.$vmodel.$model.el.mail;
                        if (pageVm.mailTo.length) {
                            var arr = pageVm.mailTo.split(';');
                            for (var i = 0; i < arr.length; i++) {
                                if (arr[i] == mailVal) {
                                    flag = false;
                                }
                            }
                            if(flag){
                                pageVm.mailTo += ';' + mailVal;
                            }
                        } else {
                            pageVm.mailTo += mailVal;
                        }
                        pageVm.linkMan.flag = 0;
                    };
                    vm.addMailModel = function () {
                        util.c2s({
                            "url": erp.BASE_PATH + 'fesOnlineProcess/getEmailTemplates.do',
                            "type": "post",
                            "contentType": 'application/json',
                            "data": JSON.stringify({"mailTemplateType": "001"}),
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    pageVm.mailModel.data = responseData.data.rows;
                                    pageVm.mailModel.flag = 1;
                                }
                            }
                        });
                    };
                    vm.addPutEmailModel = function () {
                        var modelVal = this.$vmodel.$model.el.content;
                        var id = this.$vmodel.$model.el.id;
                        var smsContent = this.$vmodel.$model.el.smsContent;
                        if(smsContent){
                            pageVm.step10smsContent = true;
                            _.map(vm.replaceData,function(item,index){
                                var reg = RegExp('\\[' + item['text'] + ']', 'g');
                                smsContent = smsContent.replace(reg ,item['value']);
                            });
                        }
                        _.map(vm.replaceData,function(item,index){
                            var reg = RegExp('\\[' + item['text'] + ']', 'g');
                            modelVal = modelVal.replace(reg ,item['value']);
                        });
                        var editorVm = avalon.getVm(getTypeId('editorId'));
                        editorVm.core.setContent(modelVal);
                        pageVm.mailModel.flag = 0;
                        pageVm.mailRequestData.email.subject = this.$vmodel.$model.el.title;
                        pageVm.mailRequestData.email.smsContent = smsContent;
                        pageVm.mailRequestData.email.mailTemplateId = id;

                    };
                    vm.$onLineMailDialogOpts = {
                        "dialogId": getTypeId('onLineMailDialogId'),
                        "autoFocusInput": false,
                        "getTemplate": function (tmpl) {
                            var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                                footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                    '<div class="ui-dialog-btns">' +
                                    '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">发&nbsp;送</button>' +
                                    '<span class="separation"></span>' +
                                    '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button>' +
                                    '</div>' +
                                    '</div>';
                            return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                        },
                        "onOpen": function () {
                        },
                        "onClose": function () {
                            vm.mailRequestData.email.to = [];
                            vm.mailRequestData.email.context = '';
                            vm.mailRequestData.email.subject = '';
                            vm.mailRequestData.email.mailTemplateId = 0
                        },
                        "width": 700,
                        "submit": function (evt) {
                            var dialogVm = avalon.getVm(getTypeId('onLineMailDialogId')),
                                editorVm = avalon.getVm(getTypeId('editorId'));
                            pageVm.mailRequestData.email.context = editorVm.core.getContent();
                            pageVm.mailRequestData.email.to = pageVm.mailTo.split(';');
                            var flag;
                            flag = util.testing($(dialogVm.widgetElement)[0]);
                            if (flag) {
                                util.c2s({
                                    "url": erp.BASE_PATH + 'fesOnlineProcess/updateOnlineStatusAndSendEmail.do',
                                    "type": "post",
                                    "contentType": 'application/json',
                                    "data": JSON.stringify(pageVm.mailRequestData.$model),
                                    "success": function (responseData) {
                                        if (responseData.flag == 1) {
                                            var data = responseData.data;
                                            vm.stepData[data.stepId - 1].onlineProcessStatus = data.onlineProcessStatus;
                                            vm.stepData[data.stepId - 1].sysOperationLogVOs.push(data);
                                            vm.stepData[9].onlineProcessStatus = data.finalStatus;
                                            dialogVm.close();
                                        }
                                    }
                                });
                            }
                        }
                    };
                    vm.$onLineMailFormOpts = {
                        "formId": getTypeId('onLineMailFormId')
                    };
                    vm.appendValue = function (){
                        var text = this.innerHTML;
                        var editorVm = avalon.getVm(getTypeId('editorId'));
                        if(editorVm.core.isFocus()){
                            editorVm.core.execCommand('insertHtml',text);
                        }
                    };
                };
                onLineMaiModel();
                /**
                 * 步骤10结束
                 * */
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
                    dialogVm.merchantId = pageVm.merchantId;
                    dialogVm.merchantName = pageVm.merchantName;
                    dialogVm.open();
                };


                /*添加问题结束*/
                /*创建活动申请*/
                var activeModel = function () {
                    vm.createData = {};//保存活动申请数据
                    vm.options2 = [];
                    vm.createActive = function () {
                        var dialogVm = avalon.getVm(getTypeId('createActiveDialogId')),
                            formVm = avalon.getVm(getTypeId('createActiveFormId')),
                            activeSelectVm = avalon.getVm(getTypeId('activeId'));
                        if (!vm.options2.length) {//获取select
                            util.c2s({
                                "url": erp.BASE_PATH + "dictionary/querySysDictionaryByType.do",
                                "type": "post",
                                "data": 'dictionaryType=00000056',
                                "success": function (responseData) {
                                    var data;
                                    if (responseData.flag) {
                                        data = responseData.data;
                                        vm.options2 = _.map(data, function (itemData) {
                                            return {
                                                "text": itemData["dictionary_value"],
                                                "value": itemData["dictionary_key"]
                                            };
                                        });
                                        activeSelectVm.setOptions(vm.options2);
                                    }
                                }
                            });
                        } else {
                            activeSelectVm.setOptions(vm.options2);
                        }

                        dialogVm.title = '营销活动申请';
                        formVm.activityTitle = '';
                        formVm.description = '';
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
                            responseText = JSON.parse(responseText);
                            if (responseText.flag) {
                                var data = responseText.data;
                                pageVm.createData.$model.attachmentName = data.attachmentName;
                                pageVm.createData.$model.originalFileName = data.originalFileName;
                                pageVm.createData.$model.fileSize = data.fileSize;
                                util.alert({
                                    "content": "上传成功！",
                                    "iconType": "success"
                                });
                            }
                        }
                    };
                    vm.$createActiveDialogOpts = {
                        "dialogId": getTypeId('createActiveDialogId'),
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
                                var requestData = JSON.stringify(pageVm.createData.$model);
                                //发送后台请求，编辑或添加
                                util.c2s({
                                    "url": erp.BASE_PATH + 'activity/saveActivity.do',
                                    "type": "post",
                                    "contentType": 'application/json',
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
                        "field": [
                            {
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
                        "activityTitle": "",
                        "description": ""
                    };
                    vm.$activeOpts = {//elect初始化
                        "selectId": getTypeId('activeId'),
                        "options": [],
                        "selectedIndex": 1
                    };
                };
                activeModel();
                /*创建活动申请结束*/

            });
            avalon.scan(pageEl[0]);
            //for test
            //pageVm.openCardRuleDialog();
            /*页面初始化请求渲染*/
            function updateList(obj, url, callBack) {
                var requestData = JSON.stringify(obj);
                pageVm.pageData = [];
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
                    pageVm.merchantName = obj.merchant_name;
                    if(!pageVm.paramsInfo){
                        pageVm.navCrumbs[1]['text'] = obj.merchant_name;
                    }
                }
            }

            /*商户回调end*/
            /*10个步骤回调*/
            updateList({"merchantId": pageVm.merchantId}, "fesOnlineProcess/listFesOnlineProcesss.do", stepCallBack);
            function stepCallBack(responseData) {
                var data, userList;
                var userId = loginUserData.id;
                if (responseData.flag) {
                    data = responseData.data;
                    pageVm.stepData = data;
                    userList = data[0].listUsers;
                    var type = routeData.params["type"];
                    if (type == 'sale') {
                        return;
                    }
                    for (var i = 0; i < userList.length; i++) {
                        if (userId == userList[i].id) {
                            pageVm.promise = true;
                            return;
                        } else {
                            pageVm.promise = false;
                        }
                    }
                }
            }
            /*10个步骤回调结束*/
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

