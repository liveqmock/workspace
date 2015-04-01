/**
 * Created by ZHANG on 2014/8/7.
 */
/**
 * 前端服务-投诉管理
 */
define(['avalon', 'util', '../../../../widget/autocomplete/autocomplete', '../../../../widget/pagination/pagination', '../../../../widget/dialog/dialog', '../../../../widget/form/form', '../../../../widget/uploader/uploader'], function (avalon, util) {
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
            var submitComDialogId = pageName + '-submit-com-dialog',//提交投诉弹框
                autoCompleteActiveId = pageName + '-auto-complete-active',//联想搜索弹框
                submitComFormId = pageName + '-submit-com-form',//提交投诉form验证
                scheduleId = pageName + '-schedule',//调用select组件
                brandId = pageName + '-brandId',
                attachUploaderId = pageName + '-attach-uploader',//上传
                paginationId = pageName + '-pagination';//分页
            var loginUserData = erp.getAppData('user');

            var pageVm = avalon.define(pageName, function (vm) {
                vm.navCrumbs = [{
                    "text":"我的主页",
                    "href":''
                }, {
                    "text":"投诉管理"
                }];
                var phref = '';
                switch (routeData.route) {
                    case "/sales/home/complaintsmanage":
                        phref = "#/sales/home";
                        break;
                    case "/frontend/home/complaintsmanage":
                        phref = "#/frontend/home";
                        break;
                    case "/backend/complaintsmanage":
                        phref = "#/backend/customerservice";
                        break;
                }
                vm.navCrumbs[0].href= phref;
                vm.upfile = '';
                vm.complaintsId= 0;
                vm.merchantId = parseInt(routeData.params["id"]);
                vm.url = erp.BASE_PATH;
                vm.pageData = {};
                vm.options1 = [];
                vm.checkBoxArr = [];//多选信息
                vm.sysAttachment = {};//上传信息
                vm.statusTabs='';//标签切换依赖
                vm.requestData={
                    merchantId: vm.merchantId,
                    merchantName:'',
                    pageNumber:1,
                    pageSize:10,
                    customerComplaintStatus:''
                };
                vm.changeStatus = function(statusId){//切换标签
                    var paginationVm = avalon.getVm(paginationId);
                    pageVm.requestData.customerComplaintStatus=statusId;
                    vm.requestData.pageNumber=1;
                    paginationVm.currentPage =1;
                    updateList(pageVm.requestData.$model);
                };
                vm.search = function(){//搜索
                    updateList(pageVm.requestData.$model);
                };
                vm.modStatus = function(id){//修改状态
                    id=id.toString();
                    util.c2s({
                        "url": erp.BASE_PATH + "activity/updateRemind.do",
                        "type": "post",
                        //"contentType" : 'application/json',
                        "data": {id:id},
                        "success": function (responseData) {
                            updateList(pageVm.requestData.$model);
                        }
                    });
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
                vm.showCon = function(){//显示
                    var obj= this.$vmodel.$model.el;
                    obj.state= 1;
                };
                vm.hideCon = function(){//隐藏
                    var obj= this.$vmodel.$model.el;
                    obj.state= 0;
                };

                /*提交投诉*/
                vm.submitCom = function(){
                    var dialogVm = avalon.getVm(submitComDialogId),
                        formVm = avalon.getVm(submitComFormId);
                    if(!vm.options1.length){//获取select
                        util.c2s({
                            "url": erp.BASE_PATH + "dictionary/querySysDictionaryByType.do",
                            "type": "post",
                            "data":  'dictionaryType=00000049',
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
                                }
                            }
                        });
                    }
                    dialogVm.title = "提交投诉";
                    formVm.complaintTitle = "";
                    formVm.complaintContent = "";
                    dialogVm.open();
                };
                vm.addType = function(){
                    var value=this.$vmodel.$model.el.value;
                    if(this.checked){
                        vm.checkBoxArr.$model.push(value);
                    }else{
                        for(var i= 0,len= vm.checkBoxArr.$model.length;i<len;i++){
                            if(value == vm.checkBoxArr.$model[i]){
                                vm.checkBoxArr.$model.splice(i,1);
                            }
                        }
                    }
                };
                vm.$submitComDialogOpts = {//dialog
                    dialogId : submitComDialogId,
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
                        var formVm = avalon.getVm(submitComFormId);
                        formVm.reset();
                        pageVm.complaintsId = 0;
                        formVm.complaintTitle = '';
                        formVm.complaintContent = '';
                        pageVm.upfile = '';
                    },
                    "submit": function (evt) {
                        var obj,
                            dialogVm = avalon.getVm(submitComDialogId),
                            formVm = avalon.getVm(submitComFormId),
                            autocompleteVm = avalon.getVm(autoCompleteActiveId);

                        if (formVm.validate()) {
                            var time=new Date().getTime();
                            obj = formVm.getFormData();
                            obj.merchantId = parseInt(pageVm.complaintsId);
                            if(!obj.merchantId){
                                util.confirm({
                                    "content": "请选择商户!",
                                    "onSubmit": function () {

                                    }
                                });
                                return false;
                            }
                            obj.customerComplaintType = pageVm.checkBoxArr.$model;
                            obj.sysAttachment = pageVm.sysAttachment.$model;
                            obj.complaintTime = time;
                            var requestData=JSON.stringify(obj);
                            //发送后台请求，编辑或添加
                            util.c2s({
                                "url": erp.BASE_PATH + 'sysCustomerComplaint/saveSysCustomerComplaint.do',
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
                vm.$submitComFormOpts = {//form表单初始化
                    "formId": submitComFormId,
                    "field":
                        [{
                            "selector": ".complaintTitle",
                            "rule": ["noempty", function (val, rs) {
                                if (rs[0] !== true) {
                                    return "标题不能为空";
                                } else {
                                    return true;
                                }
                            }]
                        },
                            {
                                "selector": ".complaintContent",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "内容不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            }
                        ],
                    "complaintTitle":"",
                    "complaintContent":""
                };
                vm.$scheduleOpts = {//elect初始化
                    "selectId": scheduleId,
                    "options": [],
                    "selectedIndex": 1
                };
                vm.$attachUploaderOpts = {//上传
                    "uploaderId": attachUploaderId,
                    "uploadifyOpts": {
                        "uploader": erp.BASE_PATH + 'sysCustomerComplaint/uploadFiles.do',
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
                            var data = responseText.data[0];
                            pageVm.sysAttachment.$model.originalFileName = data.originalFileName;
                            pageVm.sysAttachment.$model.attachmentName = data.fileName;
                            pageVm.sysAttachment.$model.attachmentSize = file.size.toString();
                            pageVm.sysAttachment.$model.attachmentSuffix = file.type;
                            pageVm.upfile = data.originalFileName;
                        }
                    }

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
                        pageVm.complaintsId = optionData.merchant_id;
                    }
                };

                /*提交投诉结束*/
            });



            avalon.scan(pageEl[0]);
            /*页面初始化请求渲染*/
            updateList(pageVm.requestData.$model);
            function updateList(obj){
                var requestData=JSON.stringify(obj);
                util.c2s({
                    "url": erp.BASE_PATH + "sysCustomerComplaint/querySysCustomerComplaintList.do",
                    "type": "post",
                    "contentType" : 'application/json',
                    "data": requestData,
                    "success": function (responseData) {
                        var data,
                        paginationVm = avalon.getVm(paginationId);
                        if (responseData.flag) {
                            data = responseData.data;
                            paginationVm.total = data.totalSize;
                            for(var i= 0,len= data.rows.length;i< len;i++){
                                data.rows[i].state = 0;
                            }
                            pageVm.pageData=data.rows;
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
            var submitComDialogId = pageName + '-submit-com-dialog',//提交投诉弹框
                autoCompleteActiveId = pageName + '-auto-complete-active',//联想搜索弹框
                submitComFormId = pageName + '-submit-com-form',//提交投诉form验证
                scheduleId = pageName + '-schedule',//调用select组件
                brandId = pageName + '-brandId',
                attachUploaderId = pageName + '-attach-uploader',//上传
                paginationId = pageName + '-pagination';//分页
            avalon.getVm(autoCompleteActiveId)&&$(avalon.getVm(autoCompleteActiveId).widgetElement).remove();
            avalon.getVm(scheduleId)&&$(avalon.getVm(scheduleId).widgetElement).remove();
            avalon.getVm(brandId)&&$(avalon.getVm(brandId).widgetElement).remove();
            avalon.getVm(attachUploaderId)&&$(avalon.getVm(attachUploaderId).widgetElement).remove();
            avalon.getVm(paginationId)&&$(avalon.getVm(paginationId).widgetElement).remove();
            avalon.getVm(submitComFormId)&&$(avalon.getVm(submitComFormId).widgetElement).remove();
            avalon.getVm(submitComDialogId)&&$(avalon.getVm(submitComDialogId).widgetElement).remove();

        }
    });

    return pageMod;
});
