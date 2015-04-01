/**
 * Created by ZHANG on 2014/8/7.
 */
/**
 * 前端服务-活动申请
 */
define(['avalon', 'util', '../../../../widget/autocomplete/autocomplete', '../../../../widget/pagination/pagination','../../../../widget/form/select', '../../../../widget/dialog/dialog', '../../../../widget/form/form', '../../../../widget/uploader/uploader'], function (avalon, util) {
    var win = this,
        erp = win.erp,
        pageMod = {};
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName) {
            var createActiveDialogId = pageName + '-create-active-dialog',//创建活动弹框
                autoCompleteActiveId = pageName + '-auto-complete-active',//联想搜索弹框
                createActiveFormId = pageName + '-create-active-form',//调用添加问题form验证
                scheduleId = pageName + '-schedule',//调用select组件
                attachUploaderId = pageName + '-attach-uploader',//上传
                brandId = pageName + '-brandId',
                paginationId = pageName + '-pagination';//分页
            var loginUserData = erp.getAppData('user');
            var pageVm = avalon.define(pageName, function (vm) {
                vm.navCrumbs = [{
                    "text":"我的主页",
                    "href":"#/frontend/home"
                }, {
                    "text":"活动申请"
                }];
                vm.url=erp.BASE_PATH;
                vm.pageData = {};
                vm.pageDataStatus = {};
                vm.options1=[];
                vm.upfile = '';
                vm.createData = {//创建申请参数
                    merchantId : 0,//number
                    activityTitle : '',//string
                    activityType : '',//string(number)
                    attachmentId : 0 ,//number
                    description : ''//string
                };
                vm.requestData = {//搜索参数
                    merchantName:'',
                    pageNumber:1,
                    pageSize:10,
                    status:''
                };
                //创建活动申请
                vm.createActive = function(){
                    var dialogVm = avalon.getVm(createActiveDialogId),
                        formVm = avalon.getVm(createActiveFormId),
                        scheduleVm = avalon.getVm(scheduleId);
                    if(!vm.options1.length){//获取select
                        util.c2s({
                            "url": erp.BASE_PATH + "dictionary/querySysDictionaryByType.do",
                            "type": "post",
                            "data":  'dictionaryType=00000056',
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

                    dialogVm.title = '营销活动申请';
                    formVm.activityTitle='';
                    formVm.description='';
                    dialogVm.open();
                };


                vm.$brandOpts = {
                    "autocompleteId": brandId,
                    "placeholder": "请指定商户",
                    "delayTime": 300,   //延时300ms请求
                    "onChange": function (text, callback) {
                        if (text.length) {
                            util.c2s({
                                "url": erp.BASE_PATH + "synMerchant/getSynMerchantInfoByUserId.do",
                                // "contentType": "application/json",
                                "data": {
                                    "merchantName": text
                                },
                                "success": function (responseData) {
                                    var rows;
                                    if (responseData.flag) {
                                        rows = responseData.data;
                                        callback(_.map(rows, function (itemData) {
                                            itemData.text = itemData.merchant_name || "未知";
                                            itemData.value = itemData.merchant_id;
                                            return itemData;
                                        }));
                                    }
                                }
                            }, {
                                "mask": false
                            });
                        } else {
                            callback([]);   //空查询显示空
                        }
                    },
                    "onSelect": function (selectedValue) {
                        var optionData = avalon.getVm(brandId).getOptionByValue(selectedValue);
                        pageVm.createData.$model.merchantId = optionData.merchant_id;
                    }
                };



                vm.$attachUploaderOpts = {//上传
                    "uploaderId": attachUploaderId,
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
                        "width": 80,
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
                            vm.upfile = data.originalFileName;
                        }
                    }

                };
                vm.$createActiveDialogOpts = {
                    "dialogId" : createActiveDialogId,
                    "autoFocusInput": false,
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
                        pageVm.complaintsId = 0;
                        vm.upfile = '';
                    },
                    "submit": function (evt) {
                        var obj,
                            dialogVm = avalon.getVm(createActiveDialogId),
                            formVm = avalon.getVm(createActiveFormId),
                            scheduleVm = avalon.getVm(scheduleId),
                            autocompleteVm = avalon.getVm(autoCompleteActiveId);

                        if (formVm.validate()) {
                            obj = formVm.getFormData();
                            if(pageVm.createData.$model.merchantId == 0){
                                util.confirm({
                                    "content": "请选择商户!",
                                    "onSubmit": function () {
                                    }
                                });
                                return false;
                            }
                            pageVm.createData.$model.activityTitle = obj.activityTitle;
                            pageVm.createData.$model.description = obj.description;
                            pageVm.createData.$model.activityType = scheduleVm.selectedValue;
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
                                        updateList(vm.requestData.$model);
                                        //关闭弹框
                                        dialogVm.close();
                                        pageVm.complaintsId = 0;
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
                vm.$scheduleOpts = {//elect初始化
                    "selectId": scheduleId,
                    "options": [],
                    "selectedIndex": 1
                };
                //创建活动申请结束

                vm.changeStatus = function(statusId){//根据状态查询
                    var paginationVm = avalon.getVm(paginationId);
                    statusId = ''+statusId;
                    vm.requestData.status = statusId;
                    vm.requestData.pageNumber=1;
                    paginationVm.currentPage =1;
                    updateList(vm.requestData.$model);
                };
                vm.search = function(){//搜索
                    updateList(vm.requestData.$model);
                };

                vm.$paginationOpts = {//分页
                    "paginationId": paginationId,
                    "total": 0,
                    "onJump": function (evt, vm) {
                        var paginationVm = avalon.getVm(paginationId);
                        pageVm.requestData.pageNumber=paginationVm.currentPage;
                        updateList(pageVm.requestData.$model);
                    }
                };
            });



            avalon.scan(pageEl[0]);
            updateList(pageVm.requestData.$model);
            function updateList(obj){
                var requestData=JSON.stringify(obj);
                util.c2s({
                    "url": erp.BASE_PATH + "/activity/listActivity.do",
                    "type": "post",
                    //"contentType" : 'application/json',
                    "data": obj,
                    "success": function (responseData) {
                        var paginationVm = avalon.getVm(paginationId);
                        if (responseData.flag) {
                            pageVm.pageDataStatus=responseData.data.statusList;
                            pageVm.pageData=responseData.data.rows;
                            paginationVm.total = responseData.data.totalSize;
                        }
                    }
                });
            }
            /*页面初始化请求渲染end*/
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var createActiveDialogId = pageName + '-create-active-dialog',//创建活动弹框
                autoCompleteActiveId = pageName + '-auto-complete-active',//联想搜索弹框
                createActiveFormId = pageName + '-create-active-form',//调用添加问题form验证
                scheduleId = pageName + '-schedule',//调用select组件
                attachUploaderId = pageName + '-attach-uploader',//上传
                brandId = pageName + '-brandId',
                paginationId = pageName + '-pagination';//分页
            avalon.getVm(autoCompleteActiveId)&&$(avalon.getVm(autoCompleteActiveId).widgetElement).remove();
            avalon.getVm(scheduleId)&&$(avalon.getVm(scheduleId).widgetElement).remove();
            avalon.getVm(attachUploaderId)&&$(avalon.getVm(attachUploaderId).widgetElement).remove();
            avalon.getVm(brandId)&&$(avalon.getVm(brandId).widgetElement).remove();
            avalon.getVm(paginationId)&&$(avalon.getVm(paginationId).widgetElement).remove();
            avalon.getVm(createActiveFormId)&&$(avalon.getVm(createActiveFormId).widgetElement).remove();
            avalon.getVm(createActiveDialogId)&&$(avalon.getVm(createActiveDialogId).widgetElement).remove();
        }
    });
    return pageMod;
});
