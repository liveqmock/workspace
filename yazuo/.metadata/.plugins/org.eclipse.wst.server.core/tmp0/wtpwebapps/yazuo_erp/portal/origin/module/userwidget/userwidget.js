/**
 * 登录用户信息挂件
 * Created by q13 on 14-5-9.
 */
define(["avalon", "util", "text!./userwidget.html", "text!./userwidget.css", "common", "../../widget/dialog/dialog", "../../widget/form/form"], function(avalon, util, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    try {
        styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'userwidget/'));
    } catch (e) {
        styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'userwidget/');
    }

    var module = erp.module.userwidget = function(element, data, vmodels) {
        var opts = data.userwidgetOptions,
            elEl = $(element),
            dialogId = 'module-userwidget-dialog',
            formId = 'module-userwidget-form';
        var loginUserData = erp.getAppData('user');
        var vmodel = avalon.define(data.userwidgetId, function (vm) {
            vm.widgetElement = element;
            vm.userName = loginUserData.userName;
            //vm.userAvatar = erp.ASSET_PATH + 'image/user-avatar-default.png';
            vm.userAvatar = loginUserData.userImage || erp.ASSET_PATH + 'image/user-avatar-default.png';
            vm.$dialogOpts = {
                "dialogId": dialogId,
                "title": "修改密码",
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
                    //清除验证信息
                    var formVm = avalon.getVm(formId);
                    formVm.reset();
                },
                "submit": function () {
                    var requestData,
                        dialogVm = avalon.getVm(dialogId),
                        formVm = avalon.getVm(formId);
                    if (formVm.validate()) {
                        requestData = formVm.getFormData();
                        //发送后台请求，编辑或添加
                        util.c2s({
                            "url": erp.BASE_PATH + 'login/changePWD.do',
                            "type": "post",
                            "data": requestData,
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    //alert(responseData.message);
                                    util.alert({
                                        "iconType": "success",
                                        "content": responseData.message
                                    });
                                    dialogVm.close();
                                }
                            }
                        });
                    }
                }
            };
            vm.$formOpts = {
                "formId": formId,
                "field": [{
                    "selector": ".old-pwd",
                    "rule": ["noempty", function (val, rs) {
                        if (rs[0] !== true) {
                            return "原密码不能为空";
                        } else {
                            return true;
                        }
                    }]
                }, {
                    "selector": ".new-pwd",
                    "rule": ["noempty", function (val, rs) {
                        if (rs[0] !== true) {
                            return "新密码不能为空";
                        } else {
                            return true;
                        }
                    }]
                }, {
                    "selector": ".confirm-pwd",
                    "rule": ["noempty", function (val, rs) {
                        if (rs[0] !== true) {
                            return "确认密码不能为空";
                        } else {
                            return true;
                        }
                    }, function (val, rs, formElement) {
                        if (_.isString(rs[1])) {
                            return rs[1];
                        } else {
                            if (val !== _.str.trim($('.new-pwd', formElement).val())) {
                                return '确认密码不一致';
                            } else {
                                return true;
                            }
                        }
                    }]
                }]
            };
            vm.openPwd = function () {
                avalon.getVm(dialogId).open();
            };
            /**
             * 点击修改个人信息
             */
            vm.clickModifyInfo = function () {
                util.jumpPage('#/profile/edit/' + loginUserData.id);
            };
            vm.logOut = function () {
                util.c2s({
                    url: erp.BASE_PATH +'login/removeSessionUser.do',
                    success:function(responseData){
                        if (responseData.flag) {
                            //有后台timeout控制跳转
                            //window.location = erp.BASE_PATH+ "login.jsp";
                        }
                    }
                });
            };
            vm.$init = function() {
                elEl.addClass('module-userwidget');
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm.$remove = function() {
                $(avalon.getVm(formId).widgetElement).remove();
                $(avalon.getVm(dialogId).widgetElement).remove();
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
