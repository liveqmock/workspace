/**
 * 修改主管密码，登录密码
 */
define(["avalon", "util", "text!./useroperation.html", "text!./useroperation.css", "common", "../../widget/dialog/dialog", "../../widget/form/form"], function (avalon, util, tmpl, cssText) {
    var win = this,
        app = win.app,
        page = app.page;
    var styleEl = $("#module-style");

    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    if (!styleEl.data('useroperation')) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'useroperation/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'useroperation/');
        }
        styleEl.data('useroperation', true);
    }
    var loginUserData = app.getAppData('user');
    var module = app.module.useroperation = function (element, data, vmodels) {
        var opts = data.useroperationOptions,
            dialogId = data.useroperationId + '-dialog',
            formId = data.useroperationId + '-form',
            dialogOneId = data.useroperationId + '-one-dialog',
            formOneId = data.useroperationId + '-one-form',
            elEl = $(element);
        var vmodel = avalon.define(data.useroperationId, function (vm) {
            vm.widgetElement = element;
            vm.pswType = 0;
            vm.hasPromise = false;
            vm.logoutUrl = app.BASE_PATH + 'logout';
            avalon.mix(vm, opts);
            vm.$dialogOpts = {
                "dialogId": dialogId,
                "getTemplate": function (tmpl) {
                    var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                        footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                            '<div class="ui-dialog-btns">' +
                            '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">确定</button>' +
                            '<span class="separation"></span>' +
                            '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取消</button>' +
                            '</div>' +
                            '</div>';
                    return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                },
                "onOpen": function () {
                },
                "onClose": function () {
                    //清除验证信息
                    var formVm = avalon.getVm(formId);
                    formVm.reset();
                },
                "submit": function (evt) {
                    var requestData,
                        dialogVm = avalon.getVm(dialogId),
                        url,
                        formVm = avalon.getVm(formId);
                    if (formVm.validate()) {
                        requestData = formVm.getFormData();
                        if (vm.pswType == 1) {
                            url = app.BASE_PATH + "controller/merchant/updateAdminPassWord.do";
                        } else {
                            _.extend(requestData, {"userInfoId": loginUserData.id, "flag": !loginUserData.isSetLoginPwd});
                            url = app.BASE_PATH + "controller/user/changePwd.do";
                        }
                        //发送后台请求，编辑或添加
                        util.c2s({
                            "url": url,
                            "data": requestData,
                            "success": function (responseData) {
                                if (responseData.flag) {
                                    util.alert({
                                        "content": '修改成功',
                                        "iconType": "success"
                                    });
                                    setTimeout(function(){
                                        dialogVm.close();
                                        $('.global-alert-confirm').remove();
                                        $('.ui-dialog-mask').hide();
                                    },2000);
                                }
                            }
                        });
                    }
                }
            };
            vm.$formOpts = {
                "formId": formId,
                "field": [
                    {
                        "selector": ".old-pwd",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "原始密码不能为空";
                            } else {
                                return true;
                            }
                        }]
                    },
                    {
                        "selector": ".new-pwd",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "新密码不能为空";
                            } else if(!val.match(/^\d{6}$/g)){
                                return "密码为6位数字！"
                            }else{
                                return true;
                            }
                        }]
                    },
                    {
                        "selector": ".confirm-pwd",
                        "name": "timeLimit",
                        "rule": ["noempty", function (val, rs, formElement) {
                            if (val !== _.str.trim($('.new-pwd', formElement).val())) {
                                return '确认密码不一致';
                            } else {
                                return true;
                            }
                        }]
                    }
                ]
            };
            vm.$dialogOneOpts = {
                "dialogId": dialogOneId,
                "getTemplate": function (tmpl) {
                    var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                        footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                            '<div class="ui-dialog-btns">' +
                            '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">确定</button>' +
                            '<span class="separation"></span>' +
                            '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取消</button>' +
                            '</div>' +
                            '</div>';
                    return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                },
                "onOpen": function () {
                },
                "onClose": function () {
                    //清除验证信息
                    var formVm = avalon.getVm(formOneId);
                    formVm.reset();
                },
                "submit": function (evt) {
                    var requestData,
                        dialogVm = avalon.getVm(dialogOneId),
                        url,
                        formVm = avalon.getVm(formOneId);
                    if (formVm.validate()) {
                        requestData = formVm.getFormData();
                        if (vm.pswType == 1) {
                            url = app.BASE_PATH + "controller/merchant/findAdminPassword.do";
                        } else {
                            url = app.BASE_PATH + "controller/user/resetUserPasswd.do";
                        }
                        //发送后台请求，编辑或添加
                        util.c2s({
                            "url": url,
                            "data": requestData,
                            "success": function (responseData) {
                                if (responseData.flag) {
                                    util.alert({
                                        "content": "<p style='line-height: 25px'>临时密码已通过短信形式发送至您的手机，请注意查收。</p>",
                                        "iconType": "success"
                                    });
                                    setTimeout(function(){
                                        $('.global-alert-confirm').remove();
                                    },10);
                                }
                            }
                        });
                    }
                }
            };
            vm.$formOneOpts = {
                "formId": formOneId,
                "field": [
                    {
                        "selector": ".phone-num",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "手机号码不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }
                ]
            };
            vm.showDialog = function () {
                var dialogVm = avalon.getVm(dialogId);
                vm.pswType = $(this).data('type');
                if (vm.pswType == 1) {
                    dialogVm.title = "修改主管密码"
                } else {
                    dialogVm.title = "修改登录密码"
                }
                dialogVm.closeIconText = '<i class="icon-close ui-icon">&#xe605;</i>';
                dialogVm.open();
            };
            vm.showGetDialog = function () {
                var dialogVm = avalon.getVm(dialogOneId);
                if (vm.pswType == 1) {
                    dialogVm.title = "找回主管密码"
                } else {
                    dialogVm.title = "找回登录密码"
                }
                dialogVm.open();
            };
            vm.$init = function () {
                elEl.addClass('module-useroperation');
                elEl.html(tmpl);
                avalon.scan(element, [vmodel].concat(vmodels));
                if (loginUserData.isSetLoginPwd) {
                    var dialogVm = avalon.getVm(dialogId);
                    vm.pswType = 2;
                    dialogVm.title = "修改登录密码";
                    dialogVm.closeIconText = "";
                    dialogVm.open();
                }
                if (util.hasPermission('superwifi_supervisor_set')) {
                    vm.hasPromise = true;
                }
            };
            vm.$remove = function () {
                elEl.removeClass("module-useroperation");
                elEl.off();
                elEl.empty();
            };
            vm.$skipArray = ["widgetElement"];
        });

        return vmodel;
    };
    module.version = 1.0;
    module.defaults = {
    };
    return avalon;
});
