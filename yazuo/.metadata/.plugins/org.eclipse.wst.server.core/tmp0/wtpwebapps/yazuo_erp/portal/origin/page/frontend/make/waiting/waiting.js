/**
 * 制卡物料-待办事项
 */
define(['avalon', 'util', 'moment', 'json', '../../../../module/autocomplete/autocomplete', '../../../../widget/dialog/dialog', '../../../../widget/form/select', '../../../../widget/form/form', '../../../../widget/calendar/calendar', '../../../../widget/uploader/uploader', '../../../../module/opencard/opencard', '../../../../module/address/address', '../../../../module/addquestion/addquestion'], function (avalon, util, moment, JSON) {
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
                        "text": "制卡物料",
                        "href": "#/frontend/make"
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
                vm.imgUrl = '';
                /*merchant信息*/
                var merchantModel = function(){
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
                    addquestionId : getTypeId('-addquestionops'),
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
                function getProcessId (str){
                    for(var i=0;i<vm.pageData.length;i++){
                        if(vm.pageData[i].stepNum == str){
                            return vm.pageData[i].id;
                        }
                    }
                }
                /*上传卡样元文件*/
                vm.$step61UploaderOpts = {//上传
                    "uploaderId": getTypeId("step61Uploder"),
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
                                "typeId": 3
                            };
                        },
                        "width": 120,
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
                                $('.step6-upfile-list1').append(
                                    '<div class="step6-upfile-model" style="position:relative;width:598px;height: 25px;border:1px solid #ccc;background:#bebebe;">' +
                                        '<p class="step6-loding-list1" style="position:absolute;left:0;top:-1px;width:' + ratio + ';height:27px;background:#67bb0d;"></p>' +
                                        '<p class="step6-tips-list1" style="position:absolute;left:0;top:0;height:25px;line-height:25px;text-align:center;width:100%;color:#fff;">' + tip + '</p>' +
                                    '</div>'
                                );
                            } else {
                                $('.step6-loding-list1').css('width',ratio);
                                $('.step6-tips-list1').html(tip);
                            }
                        }
                    },
                    "onSuccessResponseData": function (responseText, file) {
                        responseText = JSON.parse(responseText);
                        if (responseText.flag) {
                            setTimeout(function () {
                                $('.step6-upfile-model').remove();
                                updateList({"merchantId": pageVm.merchantId, "userId": loginUserData.id,"resourceRemark" :"fes_card_and_materials"}, "fesOnlineProcess/listFesOnlineProcesss.do", listWaiting);
                            }, 1000);
                        } else {
                            $('.step6-upfile-model').remove();
                            util.alert({
                                "content": "上传失败！",
                                "iconType": "error"
                            });
                        }
                    }
                };
                /*上传卡样源文件-end*/
                /*上传卡样确认单*/
                vm.$step62UploaderOpts = {
                    "uploaderId": getTypeId("step62Uploder"),
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
                                 "typeId": 4
                             };
                         },
                        "width": 120,
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
                                $('.step6-upfile-list1').append(
                                    '<div class="upfile-model" style="position:relative;width:598px;height: 25px;border:1px solid #ccc;background:#bebebe;">' +
                                        '<p class="loding-file" style="position:absolute;left:0;top:-1px;width:' + ratio + ';height:27px;background:#67bb0d;"></p>' +
                                        '<p class="tips-model" style="position:absolute;left:0;top:0;height:25px;line-height:25px;text-align:center;width:100%;color:#fff;">' + tip + '</p>' +
                                        '</div>'
                                );
                            } else {
                                $('.loding-file').css('width',ratio);
                                $('.tips-model').html(tip);
                            }
                        }
                    },
                    "onSuccessResponseData": function (responseText, file) {
                        responseText = JSON.parse(responseText);
                        if (responseText.flag) {
                            setTimeout(function () {
                                $('.upfile-model').remove();
                                updateList({"merchantId": pageVm.merchantId, "userId": loginUserData.id,"resourceRemark" :"fes_card_and_materials"}, "fesOnlineProcess/listFesOnlineProcesss.do", listWaiting);
                            }, 1000);
                        } else {
                            $('.upfile-model').remove();
                            util.alert({
                                "content": "上传失败！",
                                "iconType": "error"
                            });
                        }


                    }
                };
                /*上传卡样确认单-end*/
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
                                        updateList({"merchantId": pageVm.merchantId, "userId": loginUserData.id,"resourceRemark" :"fes_card_and_materials"}, "fesOnlineProcess/listFesOnlineProcesss.do", listWaiting);
                                    }
                                }
                            });
                        }
                    });

                };
                /*删除附件-end*/
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
                                        for(var i=0;i<vm.pageData.length;i++){
                                            if(vm.pageData[i].stepNum == responseData.data.stepNum){
                                                vm.pageData[i].onlineProcessStatus = status;
                                                vm.pageData[i].listAttachmentAndOperateLog.push(responseData.data);
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    });

                };
                /*更改完成状态吗-end*/
                /*申请开卡*/
                vm.$opencardOpts = {
                    "opencardId" : getTypeId('-opencardId'),
                    "options" : [],
                    "displayType" : 'add',
                    "processId" : 0,
                    "merchantId" : 0,
                    "merchantName" : '',
                    "sessionId": loginUserData.sessionId,
                    "callFn" : function(responseData){
                        if (responseData.flag) {
                            updateList({"merchantId": pageVm.merchantId, "userId": loginUserData.id, "itemStatus": pageVm.itemStatus, "resourceRemark" :"fes_card_and_materials"}, "fesOnlineProcess/listFesOnlineProcesss.do", listWaiting);
                            /*for(var i=0;i<vm.pageData.length;i++){
                                if(vm.pageData[i].stepNum == responseData.data.stepNum){
                                    vm.pageData[i].listAttachmentAndOperateLog.push(responseData.data);
                                    vm.pageData[i].onlineProcessStatus = responseData.data.onlineProcessStatus;
                                }
                            }*/
                        }
                    }
                };

                vm.openCardTmp = function (processId,displayType) {
                    var dialogVm = avalon.getVm(getTypeId('-opencardId'));
                    dialogVm.processId = processId;
                    dialogVm.sessionId = loginUserData.sessionId;
                    dialogVm.displayType = displayType;
                    dialogVm.merchantId = vm.merchantId;
                    dialogVm.merchantName = vm.merchantName;
                    dialogVm.open();
                };
                /*申请开卡-end*/

                /*上传物料设计源文件*/
                vm.$step71UploaderOpts = {
                    "uploaderId": getTypeId("step71Uploder"),
                    "uploadifyOpts": {
                        "uploader": erp.BASE_PATH + 'fesOnlineProcess/uploadFiles.do',
                        "fileObjName": "myfile",
                        "multi": false, //多选
                        "fileTypeDesc": "上传附件(*.*)",
                        "fileTypeExts": "*.*",
                        "formData": function () {
                            return {
                                "sessionId": loginUserData.sessionId,
                                "processId": getProcessId('materials_design'),
                                "typeId": 6
                            };
                        },
                        "width": 90,
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
                                $('.step7-upfile-list1').append(
                                    '<div class="step7-upfile-model" style="position:relative;width:598px;height: 25px;border:1px solid #ccc;background:#bebebe;">' +
                                        '<p class="step7-loding-list1" style="position:absolute;left:0;top:-1px;width:' + ratio + ';height:27px;background:#67bb0d;"></p>' +
                                        '<p class="step7-tips-list1" style="position:absolute;left:0;top:0;height:25px;line-height:25px;text-align:center;width:100%;color:#fff;">' + tip + '</p>' +
                                        '</div>'
                                );
                            } else {
                                $('.step7-loding-list1').css('width',ratio);
                                $('.step7-tips-list1').html(tip);
                            }
                        }
                    },
                    "onSuccessResponseData": function (responseText, file) {
                        responseText = JSON.parse(responseText);
                        if (responseText.flag) {
                            setTimeout(function () {
                                $('.step7-upfile-model').remove();
                                updateList({"merchantId": pageVm.merchantId, "userId": loginUserData.id,"resourceRemark" :"fes_card_and_materials"}, "fesOnlineProcess/listFesOnlineProcesss.do", listWaiting);
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
                /*上传物料设计源文件-end*/
                /*选择缩略图*/
                var sltModle = function(){
                      vm.sltImg = '';
                      vm.imgUrl = '';
                      vm.uploadData = { };
                        vm.$step72UploaderOpts = {
                            "uploaderId": getTypeId("step72Uploder"),
                            "uploadifyOpts": {
                                "uploader": erp.BASE_PATH + 'fesOnlineProcess/uploadTempFiles.do',
                                "fileObjName": "myfile",
                                "multi": false, //多选
                                "fileTypeDesc": "上传附件(*.*)",
                                "fileTypeExts": "*.jpg; *.png; *.gif",
                                "formData": function () {
                                    return {
                                        "sessionId": loginUserData.sessionId
                                    };
                                },
                                "width": 120,
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
                                        $('.step-slt').append(
                                            '<div class="upfile-model" style="position:relative;width:428px;height: 25px;border:1px solid #ccc;background:#bebebe;">' +
                                                '<p class="loding-file" style="position:absolute;left:0;top:-1px;width:' + ratio + ';height:27px;background:#67bb0d;"></p>' +
                                                '<p class="tips-model" style="position:absolute;left:0;top:0;height:25px;line-height:25px;text-align:center;width:100%;color:#fff;">' + tip + '</p>' +
                                                '</div>'
                                        );
                                    } else {
                                        $('.loding-file').css('width',ratio);
                                        $('.tips-model').html(tip);
                                    }
                                }
                            },
                            "onSuccessResponseData": function (responseText, file) {
                                responseText = JSON.parse(responseText);
                                if (responseText.flag) {
                                    setTimeout(function () {
                                        $('.upfile-model').remove();
                                        var data = responseText.data;
                                        data.processId= getProcessId('materials_design');
                                        data.typeId= avalon.getVm(getTypeId("selectSltId")).selectedValue;
                                        pageVm.uploadData = data;
                                        pageVm.sltImg = erp.BASE_PATH+data.fileFullPath;
                                    }, 1000);
                                } else {
                                    $('.upfile-model').remove();
                                    util.alert({
                                        "content": "上传失败！",
                                        "iconType": "error"
                                    });
                                }
                            }

                        };

                      vm.uploaderImgType = function(id){
                          var dialogVm = avalon.getVm(getTypeId("sltDialog"));
                          dialogVm.title = "上传缩略图";
                          util.c2s({
                              "url": erp.BASE_PATH + 'fesOnlineProcess/getFesMaterialTypeById.do',
                              "type": "post",
                              "contentType": 'application/json',
                              "data": JSON.stringify({"id":id}),
                              "success": function (responseData) {
                                  if (responseData.flag) {
                                      avalon.getVm(getTypeId("selectSltId")).setOptions(responseData.data);
                                  }
                              }
                          });
                          dialogVm.open();
                      };
                      vm.$addsltDialogOpts = {
                          "dialogId": getTypeId("sltDialog"),
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
                          "onOpen": function () {
                          },
                          "onClose": function () {
                              vm.sltImg = '';
                              pageVm.uploadData = [];
                          },
                          "submit": function (evt) {
                              var dialogVm = avalon.getVm(getTypeId("sltDialog"));
                              var requestData = JSON.stringify(pageVm.uploadData.$model);
                              var uploaderVm = avalon.getVm(getTypeId("step72Uploder"));
                              var exer = function(){
                                  if(requestData.length>2){
                                      util.c2s({
                                          "url": erp.BASE_PATH + 'fesOnlineProcess/uploadFilesForCommit.do',
                                          "type": "post",
                                          "contentType": 'application/json',
                                          "data": requestData,
                                          "success": function (responseData) {
                                              if (responseData.flag == 1) {
                                                  dialogVm.close();
                                                  pageVm.uploadData = [];
                                                  updateList({"merchantId": pageVm.merchantId, "userId": loginUserData.id,"resourceRemark" :"fes_card_and_materials"}, "fesOnlineProcess/listFesOnlineProcesss.do", listWaiting);
                                              }
                                          }
                                      });
                                  }else{
                                      util.alert({
                                          "content": "您还没有上传图片！",
                                          "iconType": "error"
                                      });
                                  }
                              };
                              if (uploaderVm.uploading) {
                                  util.showGlobalMask();  //打开全局遮罩
                                  uploaderVm.$unwatch('uploading');
                                  uploaderVm.$watch('uploading', function (val) {
                                      if (!val) {  //全部上传成功
                                          util.hideGlobalMask();  //隐藏全局遮罩
                                          exer();
                                          uploaderVm.$unwatch('uploading');
                                      }
                                  });
                              } else {    //直接上传
                                  exer();
                              }

                          }
                      };
                      vm.$selectSltOpts = {//缩略图类型
                          "selectId": getTypeId("selectSltId"),
                          "options": [],
                          "onSelect":function(){},
                          "selectedIndex": 0
                      };
                };
                sltModle();
                /*选择缩略图-end*/
                /*实体卡配送*/

                vm.isSendSMSToUser = 1;
                function selectToObj(k,data){//select转换对象
                    var arr= [];
                    for(var i= 0,len=data[k].length;i<len;i++){
                        var obj= {};
                        obj.text= data[k][i].dictionary_value;
                        obj.value= data[k][i].dictionary_key;
                        arr.push(obj);
                    }
                    return arr;
                }
                function isChecked(arr,value){//checkbox增减
                    if(this.checked){
                        for(var j=0;j<arr.length;j++){
                            if(value == arr[j]){
                                arr.splice(j,1);
                            }
                        }
                        arr.push(value);
                    }else{
                        for(var i=0;i<arr.length;i++){
                            if(value == arr[i]){
                                arr.splice(i,1);
                            }
                        }
                    }
                }
                function checkedMatch(checkArr,arr){//checkbox匹配选中设置
                    for(var i=0;i<checkArr.length;i++){
                        for(var j=0;j<arr.length;j++){
                            if(checkArr[i].value == arr[j] ){
                                checkArr[i].isChecked = true;
                            }
                        }
                    }
                }
                vm.changeSendSms = function(){
                    if(vm.isSendSMSToUser == 1){
                        vm.isSendSMSToUser = 0;
                    }else{
                        vm.isSendSMSToUser = 1;
                    }
                };
                vm.stowageCard = function(value){
                    var dialogVm = avalon.getVm(getTypeId("stowageDialog"));
                    var selectVm = avalon.getVm(getTypeId("selectMessId"));
                    dialogVm.processId = value;
                    var requestData = JSON.stringify({"merchantId" : vm.merchantId});
                    util.c2s({
                        "url": erp.BASE_PATH + "contact/queryContact.do",
                        "type": "post",
                        "contentType" : 'application/json',
                        "data":  requestData,
                        "success": function (responseData) {
                            if(responseData.flag){
                                var data=responseData.data;
                                var arr=[];
                                for(var i=0;i<data.length;i++){
                                    var obj = {};
                                    obj.value = data[i].id;
                                    obj.text = data[i].name;
                                    arr.push(obj);
                                }
                                selectVm.setOptions(arr);
                            }
                        }
                    });
                    dialogVm.title = "实体卡配送";
                    dialogVm.open();
                };
                vm.$stowageDialogOpts = {
                    "dialogId": getTypeId("stowageDialog"),
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
                    "onOpen": function () {
                    },
                    "onClose": function () {
                        var formVm = avalon.getVm(getTypeId("stowageForm"));
                        formVm.reset();
                        avalon.getVm(getTypeId("selectProcessOpts")).select(0,true);
                    },
                    "submit": function (evt) {
                        var requestData;
                        var dialogVm = avalon.getVm(getTypeId("stowageDialog"));
                        var formVm = avalon.getVm(getTypeId("stowageForm"));
                        var selectVm = avalon.getVm(getTypeId("selectMessId"));
                        if (formVm.validate()) {
                            requestData = formVm.getFormData();
                            requestData.contactId = selectVm.selectedValue;
                            //requestData.merchantId = pageVm.merchantId;
                            requestData.processId = dialogVm.processId;
                            requestData.fesStowageDtls = [{"category":"2"}];
                            requestData.logisticsCompany = avalon.getVm(getTypeId("selectProcessOpts")).selectedValue;
                            requestData.isSendSMSToUser = vm.isSendSMSToUser;
                            requestData = JSON.stringify(requestData);
                            util.c2s({
                                "url": erp.BASE_PATH + "applySetting/saveFesStowage.do",
                                "type": "post",
                                "contentType" : 'application/json',
                                "data":  requestData,
                                "success": function (responseData) {
                                    if(responseData.flag){
                                        dialogVm.close();
                                        vm.pageData[0].listAttachmentAndOperateLog.push(responseData.data);
                                        vm.pageData[0].onlineProcessStatus = responseData.data.onlineProcessStatus;
                                        //updateList({"merchantId": pageVm.merchantId, "userId": loginUserData.id}, "fesOnlineProcess/listFesOnlineProcesss.do", listWaiting);
                                    }
                                }
                            });
                        }

                    }
                };
                vm.selectProcessOption = [];
                vm.$selectProcessOpts = {
                    "selectId": getTypeId("selectProcessOpts"),
                    "options": [],
                    "selectedIndex": 0
                };
                util.c2s({
                    "url": erp.BASE_PATH + "dictionary/querySysDictionaryByType.do",
                    "type": "post",
                    "data":  'dictionaryType=00000115',
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            pageVm.selectProcessOption = _.map(data, function (itemData) {
                                return {
                                    "text": itemData["dictionary_value"],
                                    "value": itemData["dictionary_key"]
                                };
                            });
                            avalon.getVm(getTypeId("selectProcessOpts")).setOptions(pageVm.selectProcessOption);
                        }
                    }
                });
                vm.$stowageFormOpts = {
                    "formId": getTypeId("stowageForm"),
                    "field": [
                        {
                            "selector": ".logisticsNum",
                            "rule": ["noempty", function (val, rs) {
                                if (rs[0] !== true) {
                                    return "快递单号不能为空";
                                } else {
                                    return true;
                                }
                            }]
                        }
                    ],
                    "logisticsNum": "",
                    "logisticsCompany": ""
                };
                vm.$selectMessOpts = {
                    "selectId": getTypeId("selectMessId"),
                    "options": [],
                    "getTemplate": function (tmpl) {
                        var tmps = tmpl.split('MS_OPTION_HEADER');
                        tmpl = 'MS_OPTION_HEADER<ul class="select-list" ms-on-click="selectItem"ms-css-min-width="minWidth">' +
                            '<li class="select-item" ms-repeat="options" ms-css-min-width="minWidth - 16"' +
                            'ms-class="state-selected:$index==selectedIndex" ms-class-last-item="$last" ms-hover="state-hover" ' +
                            'ms-data-index="$index" ms-attr-title="el.text" ms-data-value="el.value">{{el.text}}</li>' +
                            '<li><a style="display: block;line-height: 35px;background: #eee;border-top: 1px solid #ccc;cursor: pointer;text-indent: 10px;" data-num="selectMessId"  ms-click=addCommunication href="javascript:;">添加联系人</a></li>' +
                            '</ul>';
                        return tmps[0] + tmpl;
                    },
                    "selectedIndex": 0
                };
                /*实体卡配送-end*/
                /*通讯录*/
                vm.$addressOpts = {
                    "addressId": getTypeId('-addressModule'),
                    "readonly": false,
                    "moduleType": 'fes',
                    "merchantId": 0
                };
                vm.addCommunication = function () {
                    var name = $(this).attr('data-num');
                    var dialog = avalon.getVm(getTypeId('-addressModule'));
                    dialog.readonly = false;
                    dialog.callFn = function (responseData) {
                        var data = responseData.data;
                        var arr = {
                            text:data['name'],
                            value:data['id']
                        };
                        avalon.getVm(getTypeId(name)).panelVmodel.options.push(arr);
                        avalon.getVm(getTypeId(name)).selectValue(arr.value);
                    };
                    dialog.requestData = {
                        merchantId: pageVm.merchantId
                    };
                    dialog.open();
                };
                /*通讯录-end*/
                /*下单制卡*/
                vm.makeData = {//下单制卡发送后台数据
                    "id": 0,
                    "onlineProcessStatus": status,
                    "planReciveCardTime": moment(moment()).format('YYYY-MM-DD')
                    //"type":''
                };
                vm.$makeCardDialogOpts = {
                    "dialogId" : getTypeId('-makeCardDialogId'),
                    "title" : '下单制卡',
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
                    "onOpen": function () {
                    },
                    "onClose": function () {

                    },
                    "submit": function (evt) {
                        var requestData = vm.makeData.$model;
                        requestData.planReciveCardTime = moment(requestData.planReciveCardTime,'YYYY-MM-DD')/1;
                        util.c2s({
                            "url": erp.BASE_PATH + "fesOnlineProcess/updateFesOnlineProcessStatus.do",
                            "type": "post",
                            "contentType" : 'application/json',
                            "data":  JSON.stringify(requestData),
                            "success": function (responseData) {
                                if(responseData.flag){
                                    avalon.getVm(getTypeId('-makeCardDialogId')).close();
                                    for(var i=0;i<vm.pageData.length;i++){
                                        if(vm.pageData[i].stepNum == responseData.data.stepNum){
                                            vm.pageData[i].listAttachmentAndOperateLog.push(responseData.data);
                                            vm.pageData[i].onlineProcessStatus = responseData.data.onlineProcessStatus;
                                        }
                                    }
                                }
                            }
                        });
                    }
                };
                vm.makeCard = function(id,status,type){
                    var dialogVm = avalon.getVm(getTypeId('-makeCardDialogId'));
                    vm.makeData.id = id;
                    vm.makeData.onlineProcessStatus = status;
                    //vm.makeData.type = type;
                    dialogVm.open();
                };
                vm.$makeDateOpts = {//日历插件
                    "calendarId": getTypeId('-makeCardCalendId'),
                    "minDate": new Date(),
                    "onClickDate": function (d) {
                        vm.makeData.planReciveCardTime = moment(d).format('YYYY-MM-DD');
                        $(this.widgetElement).hide();
                    }
                };
                vm.openMakeCalendar = function () {//日历插件
                    var meEl = $(this),
                        calendarVm = avalon.getVm(getTypeId('-makeCardCalendId')),
                        calendarEl,
                        inputOffset = meEl.offset();
                    if (!calendarVm) {
                        calendarEl = $('<div ms-widget="calendar,$,$makeDateOpts"></div>');
                        calendarEl.css({
                            "position": "absolute",
                            "z-index": 10000
                        }).hide().appendTo('body');
                        avalon.scan(calendarEl[0], [vm]);
                        calendarVm = avalon.getVm(getTypeId('-makeCardCalendId'));
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
                    if (vm.planReciveCardTime) {
                        calendarVm.focusDate = moment(vm.makeData.planReciveCardTime, 'YYYY-MM-DD')._d;
                    } else {
                        calendarVm.focusDate = moment(moment(), 'YYYY-MM-DD')._d;
                    }
                    calendarVm.minDate = moment(moment(), 'YYYY-MM-DD')._d;
                    calendarEl.css({
                        "top": inputOffset.top + meEl.outerHeight() - 1,
                        "left": inputOffset.left
                    }).show();
                };

                /*下单制卡-end*/
                //设计需求单
                vm.setp7ReqData = {
                    "id": 0,//编辑的时候需要传id
                    "processId": 0,//流程id
                    "accessId": 1,  //对接人
                    "description": "",
                    "attachmentId": 0,//附件id
                    "sysAttachmentVO": {//"附件信息"
                        "attachmentName": '',
                        "fileFullPath": ''
                    },
                    "fesMaterialRequestDtlVOs": [
                        {
                            "materialRequestType": "1",  //设计需求类型
                            "specificationType": "1",  //设计需求规格
                            "description": ""//文本框输入
                        },
                        {
                            "materialRequestType": "2",
                            "specificationType": "1",
                            "description": ""//文本框输入
                        },
                        {
                            "materialRequestType": "3",
                            "specificationType": "1",
                            "description": ""//文本框输入
                        },
                        {
                            "materialRequestType": "4",
                            "specificationType": "1",
                            "description": ""//文本框输入
                        },
                        {
                            "materialRequestType": "5",
                            "specificationType": "1",
                            "description": ""//文本框输入
                        },
                        {
                            "materialRequestType": "6",
                            "specificationType": "1",
                            "description": ""//文本框输入
                        },
                        {
                            "materialRequestType": "7",
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
                                "attachmentName": '',
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
                        if (flag) {
                            pageVm.setp7ReqData.processId = pageVm.stepData[6].id;
                            pageVm.setp7ReqData.accessId = avalon.getVm(getTypeId('step7NameSelect')).selectedValue;
                            var requestData = JSON.stringify(pageVm.setp7ReqData.$model);
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
                    "onSelect": function(v,t,o){
                        pageVm.setp7ReqData.accessId = v;
                        pageVm.accessName = t;
                    }
                };
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
                            /*return {
                             "sessionId": loginUserData.sessionId,
                             "processId": vm.stepData[6].id,
                             "typeId": 15
                             };*/
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
                                vm.setp7ReqData.sysAttachmentVO.attachmentName = data.attachmentName;
                                vm.setp7ReqData.sysAttachmentVO.fileFullPath = data.fileFullPath;
                                vm.step7Uploading = true;
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
                //设计需求单end
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
                    pageVm.merchantData= obj;
                    pageVm.navCrumbs[1].text = obj.merchant_name;
                    pageVm.merchantName = obj.merchant_name;
                }
            }
            /*商户回调end*/
            /*list*/
            updateList({"merchantId": pageVm.merchantId, "userId": loginUserData.id, "itemStatus": pageVm.itemStatus, "resourceRemark" :"fes_card_and_materials"}, "fesOnlineProcess/listFesOnlineProcesss.do", listWaiting);
            function listWaiting(responseData) {
                var data;
                var step61UploderVm = avalon.getVm(getTypeId("step61Uploder"));
                var step62UploderVm = avalon.getVm(getTypeId("step62Uploder"));
                var step71UploderVm = avalon.getVm(getTypeId("step71Uploder"));
                if (responseData.flag) {
                    step61UploderVm && $(step61UploderVm.widgetElement).remove();
                    step62UploderVm && $(step62UploderVm.widgetElement).remove();
                    step71UploderVm && $(step71UploderVm.widgetElement).remove();
                    data = responseData.data;
                    var arr = [];
                    for(var i=0;i<data.length;i++){
                        if(data[i].stepNum == 'entity_cards'){
                            arr.push(data[i]);
                        }
                        if(data[i].stepNum == 'materials_design'){
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

