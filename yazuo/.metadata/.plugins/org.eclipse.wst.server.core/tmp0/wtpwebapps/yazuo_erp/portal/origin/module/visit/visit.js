/**
 * 回访记录模块
 */
define(["avalon", "util", "json", "moment", "text!./visit.html", "text!./visit.css", "common", "../../widget/dialog/dialog"], function (avalon, util, JSON, moment, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    var styleData = styleEl.data('module') || {};

    if (!styleData["visit"]) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'visit/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'visit/');
        }
        styleData["visit"] = true;
        styleEl.data('module', styleData);
    }
    var module = erp.module.visit = function (element, data, vmodels) {
        var opts = data.visitOptions,
            elEl = $(element);
        var visitDialogId = data.visitId + '-dialog';//dialog对话框

        var model = avalon.define(data.visitId, function (vm) {
            avalon.mix(vm, opts);
            vm.id = '';
            vm.name = '';
            vm.mobilePhone = '';
            vm.list = [];

            vm.$visitOpts = {//对话框dialog
                "dialogId": visitDialogId,
                "title":"回访记录",
                "width": 400,
                "getTemplate": function (tmpl) {
                    var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                        footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                            '<div class="ui-dialog-btns">' +
                            '<p><button type="button" class="submit-btn ui-dialog-btn" data-id="8" ms-click="submit">电话已接通</button></p>' +
                            '<p><button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button></p>' +
                            '</div>' +
                            '</div>';
                    return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                },
                "onOpen": function () {
                },
                "onClose": function () {
                },
                "submit": function (evt) {
                    var dialog = avalon.getVm(visitDialogId);
                    dialog.close();
                    vm.callFnSuccess();
                }
            };
            vm.phoneType = function(){
                var val = $(this).attr('data-value');
                var text = $(this).html();
                var dialog = avalon.getVm(visitDialogId);
                util.c2s({
                    "url": erp.BASE_PATH + 'besRequirement/saveVisteFailTelCall.do',
                    "type": "post",
                    "contentType" : 'application/json',
                    "data": JSON.stringify({"id":vm.id,"visiteButtonName":text}),
                    "success": function (responseData) {
                        if (responseData.flag == 1) {
                            dialog.close();
                            vm.callFnFailure(responseData);
                        }
                    }
                })
            };
            vm.open = function () {
                avalon.getVm(visitDialogId).open();
            };
            vm.$init = function () {
                elEl.addClass('module-visit');
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [model].concat(vmodels));
            };
            vm.$remove = function () {
                $(avalon.getVm(visitDialogId).widgetElement).remove();
                elEl.removeClass('module-visit').off().empty();
            };
        });
        return model;
    };
    module.defaults = {
        "id" : '',
        "name" : '',
        "mobilePhone" : '',
        "list" : [],
        "callFnFailure" : avalon.noop,//回访失败
        "callFnSuccess" : avalon.noop//回访成功
    };
    return avalon;
});