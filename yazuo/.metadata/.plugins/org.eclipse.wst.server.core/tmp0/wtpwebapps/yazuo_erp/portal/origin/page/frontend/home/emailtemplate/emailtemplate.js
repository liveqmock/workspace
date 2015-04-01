/**
 * Created by ZHANG on 2014/8/7.
 */
/**
 * 前端服务-邮件模板
 */
define(['avalon', 'util', '../../../../widget/pagination/pagination', '../../../../widget/form/select', '../../../../widget/dialog/dialog', '../../../../widget/form/form', '../../../../widget/editor/editor'], function (avalon, util) {
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
        "$init": function (pageEl, pageName,routeData) {
            var getTypeId = function (n) {//设置和获取widget的id;
                var widgetId = pageName + n;
                tempWidgetIdStore.push(widgetId);
                return widgetId;
            };
            var pageVm = avalon.define(pageName, function (vm) {
                vm.navCrumbs = [{
                    "text":"我的主页",
                    "href":''
                }, {
                    "text":"邮件模板"
                }];

                var phref = '';
                switch (routeData.route) {
                    case "/sales/home/emailtemplate":
                        phref = "#/sales/home";
                        break;
                    case "/frontend/home/emailtemplate":
                        phref = "#/frontend/home";
                        break;
                    case "/backend/emailtemplate":
                        phref = "#/backend/customerservice";
                        break;
                }
                vm.navCrumbs[0].href= phref;


                vm.options1=[];
                vm.url=erp.BASE_PATH;
                vm.isShowMess = true;
                vm.pageData={};

                vm.requestData = {//查询
                    name:'',
                    pageNumber:1,
                    pageSize:10
                };
                vm.agvData = [];
                vm.$editorOpts = {
                    "editorId": getTypeId('editorId'),
                    "ueditorOptions": {
                        "UEDITOR_HOME_URL": erp.WIDGET_PATH + 'editor/ueditor/',
                        "serverUrl": erp.BASE_PATH + 'portal/origin/widget/editor/ueditor/jsp/controller.jsp',
                        "toolbars": [[
                            'fullscreen', 'source', '|', 'undo', 'redo', '|',
                            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                            'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                            'directionalityltr', 'directionalityrtl', 'indent', '|',
                            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter','simpleupload'
                        ]]
                    }
                };
                //添加邮件引用参数
                vm.appendValue = function (){
                    var text = '['+this.innerHTML+']';
                    var editorVm = avalon.getVm(getTypeId('editorId'));
                    //if(editorVm.core.isFocus()){
                        editorVm.core.execCommand('insertHtml',text);
                    //}
                };
                //添加短信引用参数
                vm.appMessValue = function (){
                    var text = '['+this.innerHTML+']';
                    util.appendTextAtCursor('.mess-textarea-con', text);
                };
                //添加邮件模板
                vm.addEmailTemplate =function (){
                    var dialogVm = avalon.getVm(getTypeId('addEmailDialogId')),
                        formVm = avalon.getVm(getTypeId('addEmailFormId')),
                        scheduleVm = avalon.getVm(getTypeId('scheduleId'));
                    if(!vm.options1.length){//获取select
                        util.c2s({
                            "url": erp.BASE_PATH + "mailAndAttachment/initType.do",
                            "type": "post",
                            "data":  'dictionaryType=00000060',
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    vm.options1=_.map(data, function (itemData) {
                                        return {
                                            "text": itemData["text"],
                                            "value": itemData["value"]
                                        };
                                    });
                                    scheduleVm.setOptions(vm.options1);
                                }
                            }
                        });
                    }else{
                        scheduleVm.setOptions(vm.options1);
                    }

                    dialogVm.title = "添加邮件模板";
                    formVm.name = "";
                    formVm.title = "";
                    dialogVm.state = 1;
                    dialogVm.open();
                };
                vm.$scheduleOpts = {//elect初始化
                    "selectId": getTypeId('scheduleId'),
                    "options": [],
                    "selectedIndex": 1
                };
                vm.$addEmilFormOpts = {//form表单初始化
                    "formId": getTypeId('addEmailFormId'),
                    "field":
                        [{
                            "selector": ".name",
                            "rule": ["noempty", function (val, rs) {
                                if (rs[0] !== true) {
                                    return "名称不能为空";
                                } else {
                                    return true;
                                }
                            }]
                        },
                            {
                                "selector": ".title",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "标题不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": '[name="content"]',
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": '[name="smsContent"]',
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "短信内容不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            }
                        ],
                    "name" : "",
                    "title" : "",
                    "isSendSMS" : "1",
                    "smsContent": "",
                    "mailTemplateTypeText": "",//模板类型
                    "emailConText": ""//邮件正文
                };
                vm.changeCheckbox = function(){
                    var formVm = avalon.getVm(getTypeId('addEmailFormId'));
                    if(formVm.isSendSMS == '1'){
                        formVm.isSendSMS = "0";
                        formVm.smsContent = "";
                        vm.isShowMess = false;
                    }else{
                        formVm.isSendSMS = "1";
                        vm.isShowMess = true;
                    }
                };
                vm.$addEmailDialogOpts = {
                    "dialogId" : getTypeId('addEmailDialogId'),
                    "width": 800,
                    "state": 0,
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                            footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                '<div class="ui-dialog-btns">' +
                                '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit" ms-if="state == 1">保&nbsp;存</button><button type="button" class="submit-btn ui-dialog-btn" ms-click="submit" ms-if="state == 2">修&nbsp;改</button>' +
                                '<span class="separation"></span>' +
                                '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button>' +
                                '</div>' +
                                '</div>';

                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "onOpen": function () {},
                    "onClose": function () {
                        var formVm = avalon.getVm(getTypeId('addEmailFormId')),
                            editorVm = avalon.getVm(getTypeId('editorId'));
                        formVm.reset();
                        formVm.title = "";
                        formVm.name = "";
                        formVm.smsContent = "";
                        editorVm.core.setContent("");
                    },
                    "submit": function (evt) {
                        var formVm = avalon.getVm(getTypeId('addEmailFormId')),
                            dialogVm = avalon.getVm(getTypeId('addEmailDialogId')),
                            scheduleVm = avalon.getVm(getTypeId('scheduleId'));
                        if (formVm.validate()) {
                            var requestData = formVm.getFormData();
                            requestData.mailTemplateType = scheduleVm.selectedValue;
                            requestData.isSendSMS = formVm.isSendSMS;
                            if(dialogVm.state == 2){
                                requestData.id = pageVm.editId;
                            }
                            requestData = JSON.stringify(requestData);
                            //发送后台请求，编辑或添加
                            util.c2s({
                                "url": erp.BASE_PATH + 'mailAndAttachment/saveMailTemplate.do',
                                "type": "post",
                                "contentType" : 'application/json',
                                "data": requestData,
                                "success": function (responseData) {
                                    if (responseData.flag == 1) {
                                        updateList(vm.requestData.$model);
                                        //关闭弹框
                                        dialogVm.close();
                                    }
                                }
                            });
                        }
                    }
                };

                //添加邮件模板结束
                vm.search = function(){//搜索
                    updateList(pageVm.requestData.$model);
                };
                vm.read = function(){//查看
                    var dialogVm = avalon.getVm(getTypeId('addEmailDialogId')),
                        formVm = avalon.getVm(getTypeId('addEmailFormId')),
                        scheduleVm = avalon.getVm(getTypeId('scheduleId')),
                        editorVm = avalon.getVm(getTypeId('editorId'));
                    if(!vm.options1.length){//获取select
                        util.c2s({
                            "url": erp.BASE_PATH + "mailAndAttachment/initType.do",
                            "type": "post",
                            "data":  'dictionaryType=00000060',
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    vm.options1=_.map(data, function (itemData) {
                                        return {
                                            "text": itemData["text"],
                                            "value": itemData["value"]
                                        };
                                    });
                                    scheduleVm.setOptions(vm.options1);
                                }
                            }
                        });
                    }else{
                        scheduleVm.setOptions(vm.options1);
                    }
                    dialogVm.title = "查看邮件模板";
                    formVm.name = this.$vmodel.$model.el.name;
                    formVm.title = this.$vmodel.$model.el.title;
                    formVm.isSendSMS = this.$vmodel.$model.el.isSendSMS;
                    formVm.smsContent = this.$vmodel.$model.el.smsContent;
                    avalon.getVm(getTypeId('scheduleId')).selectValue(this.$vmodel.$model.el.mailTemplateType);
                    formVm.mailTemplateTypeText = this.$vmodel.$model.el.mailTemplateType == '001'?'上线完成时邮件模板':'月报模板';
                    formVm.emailConText = this.$vmodel.$model.el.content;
                    editorVm.core.setContent(this.$vmodel.$model.el.content);
                    dialogVm.state = 0;
                    if(formVm.isSendSMS == '1'){
                        vm.isShowMess = true;
                    }else{
                        vm.isShowMess = false;
                    }
                    dialogVm.open();
                };

                vm.edit = function(){//修改
                    var dialogVm = avalon.getVm(getTypeId('addEmailDialogId')),
                        formVm = avalon.getVm(getTypeId('addEmailFormId')),
                        scheduleVm = avalon.getVm(getTypeId('scheduleId')),
                        editorVm = avalon.getVm(getTypeId('editorId'));
                    if(!vm.options1.length){//获取select
                        util.c2s({
                            "url": erp.BASE_PATH + "mailAndAttachment/initType.do",
                            "type": "post",
                            "data":  'dictionaryType=00000060',
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    vm.options1=_.map(data, function (itemData) {
                                        return {
                                            "text": itemData["text"],
                                            "value": itemData["value"]
                                        };
                                    });
                                    scheduleVm.setOptions(vm.options1);
                                }
                            }
                        });
                    }else{
                        scheduleVm.setOptions(vm.options1);
                    }
                    dialogVm.title = "修改邮件模板";
                    formVm.name = this.$vmodel.$model.el.name;
                    formVm.title = this.$vmodel.$model.el.title;
                    formVm.isSendSMS = this.$vmodel.$model.el.isSendSMS;
                    formVm.smsContent = this.$vmodel.$model.el.smsContent;
                    avalon.getVm(getTypeId('scheduleId')).selectValue(this.$vmodel.$model.el.mailTemplateType);
                    editorVm.core.setContent(this.$vmodel.$model.el.content);
                    dialogVm.state = 2;
                    vm.editId = this.$vmodel.$model.el.id;
                    if(formVm.isSendSMS == '1'){
                        vm.isShowMess = true;
                    }else{
                        vm.isShowMess = false;
                    }
                    dialogVm.open();
                };
                vm.remove = function(){
                    var requestData = {};
                    requestData.id = this.$vmodel.$model.el.id;
                    util.confirm({
                        "content": "你确定要删除选择的模板吗？",
                        "onSubmit": function () {
                            util.c2s({
                                "url": erp.BASE_PATH + "mailAndAttachment/deleteMailTemplate.do",
                                "type": "post",
                                "data":  requestData,
                                "success": function (responseData) {
                                    updateList(pageVm.requestData.$model);
                                }
                            });
                        }
                    });

                };

                vm.$paginationOpts = {//分页
                    "paginationId": getTypeId('paginationId'),
                    "total": 0,
                    "onJump": function (evt, vm) {
                        var paginationVm = avalon.getVm(getTypeId('paginationId'));
                        pageVm.requestData.pageNumber=paginationVm.currentPage;
                        updateList(pageVm.requestData.$model);
                    }
                };






            });



            avalon.scan(pageEl[0]);

            /*页面初始化请求渲染*/
            updateList(pageVm.requestData.$model);
            function updateList(obj){
                //var requestData=JSON.stringify(obj);
                util.c2s({
                    "url": erp.BASE_PATH + "mailAndAttachment/listMailTemplates.do",
                    "type": "post",
                    //"contentType" : 'application/json',
                    "data": obj,
                    "success": function (responseData) {
                        var data,
                        paginationVm = avalon.getVm(getTypeId('paginationId'));
                        if (responseData.flag) {
                            data = responseData.data;
                            paginationVm.total = data.totalSize;
                            pageVm.pageData=data.rows;
                        }
                    }
                });
            }
            /*页面初始化请求渲染end*/
            /*邮件模板引用参数*/
            util.c2s({
                "url": erp.BASE_PATH + "mailAndAttachment/initParameter.do",
                "type": "post",
                //"contentType" : 'application/json',
                "data": JSON.stringify({}),
                "success": function (responseData) {
                    if (responseData.flag) {
                        data = responseData.data;
                        pageVm.agvData = data;
                    }
                }
            });
            /*邮件模板引用参数-end*/
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
