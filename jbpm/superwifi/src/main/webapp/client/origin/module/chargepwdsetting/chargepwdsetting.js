/**
 * 主管密码设置
 * Created by q13 on 14-5-9.
 */
define(["avalon", "util", "text!./chargepwdsetting.html", "text!./chargepwdsetting.css", "common", "../../widget/dialog/dialog", "../../widget/form/form"], function (avalon, util, tmpl, cssText) {
    var win = this,
        app = win.app,
        page = app.page;
    var styleEl = $("#module-style");

    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    if (!styleEl.data('chargepwdsetting')) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'chargepwdsetting/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'chargepwdsetting/');
        }
        styleEl.data('chargepwdsetting', true);
    }
    var loginUserData = app.getAppData('user');
    var module = app.module.chargepwdsetting = function (element, data, vmodels) {
        var opts = data.chargepwdsettingOptions,
            dialogId = data.chargepwdsettingId + '-dialog',
            formId = data.chargepwdsettingId + '-form',
            elEl = $(element);
        var vmodel = avalon.define(data.chargepwdsettingId, function (vm) {
            vm.widgetElement = element;
            avalon.mix(vm, opts);
            vm.$dialogOpts = {
                "dialogId": dialogId,
                "title": "设置主管密码",
                "getTemplate": function (tmpl) {
                    var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                        footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                            '<div class="ui-dialog-btns">' +
                            '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">完成</button>' +
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
                    var formVm = avalon.getVm(formId);
                    formVm.reset();
                },
                "submit": function (evt) {
                    var requestData,
                        dialogVm = avalon.getVm(dialogId),
                        formVm = avalon.getVm(formId);
                    if (formVm.validate()) {
                        requestData = formVm.getFormData();
                        _.extend(requestData, {"oldPassWord": ""});
                        //发送后台请求，编辑或添加
                        util.c2s({
                            "url": app.BASE_PATH + "controller/merchant/updateAdminPassWord.do",
                            "data": requestData,
                            "success": function (responseData) {
                                if (responseData.flag) {
                                    util.alert({
                                        "content": '设置成功',
                                        "iconType": "success"
                                    });
                                    //关闭弹框
                                    dialogVm.close();
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
                        "selector": ".pwd",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "主管密码不能为空";
                            } else {
                                return true;
                            }
                        }]
                    },
                    {
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
                                if (val !== _.str.trim($('.pwd', formElement).val())) {
                                    return '确认密码不一致';
                                } else {
                                    return true;
                                }
                            }
                        }]
                    }
                ]
            };
            vm.$init = function () {
                elEl.addClass('module-chargepwdsetting');
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
                /*if (loginUserData.needSetSupervisor && util.hasPermission('superwifi_supervisor_set')) {
                    avalon.nextTick(function () {
                        avalon.getVm(dialogId).open();
                    });
                }*/
            };
            vm.$remove = function () {
                elEl.removeClass("module-merchantdomain");
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
