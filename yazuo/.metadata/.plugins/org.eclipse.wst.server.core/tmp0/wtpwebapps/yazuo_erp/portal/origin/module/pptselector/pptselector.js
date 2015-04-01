/**
 * ppt选择组件
 */
define(["avalon", "util", "json", "text!./pptselector.html", "text!./pptselector.css", "common", "../../widget/dialog/dialog"], function(avalon, util, JSON, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    var styleData = styleEl.data('module') || {};
    if (!styleData["pptselector"]) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'pptselector/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'pptselector/');
        }
        styleData["pptselector"] = true;
        styleEl.data('module', styleData);
    }
    var module = erp.module.pptselector = function(element, data, vmodels) {
        var opts = data.pptselectorOptions,
            dialogId = data.pptselectorId + '-dialog',
            elEl = $(element);
        var vmodel = avalon.define(data.pptselectorId, function (vm) {
            avalon.mix(vm, opts);
            vm.widgetElement = element;
            vm.dialogId = dialogId;
            vm.$pptOpts = {
                "dialogId": dialogId,
                "title": "选择PPT",
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
                "list": [],
                "qName": "",
                "onClose": function () {
                    //清除item
                    var dialogVm = avalon.getVm(dialogId);
                    dialogVm.list.clear();
                    dialogVm.qName = "";
                    //滚动到顶部
                    $('.list-wrapper', dialogVm.widgetElement).scrollTop(0);
                },
                "onOpen": function () {
                    var dialogVm = avalon.getVm(dialogId);
                    dialogVm.updateList();
                },
                "submit": function (evt) {
                    var dialogVm = avalon.getVm(dialogId),
                        result = [];
                    //获得所选的试题id
                    _.each(dialogVm.list.$model, function (itemM) {
                        if (itemM.isSelected) {
                            result.push(itemM);
                        }
                    });
                    opts.onSubmit && opts.onSubmit.call(vm, result);
                    dialogVm.close();
                },
                "updateList": function () {
                    var dialogVm = avalon.getVm(dialogId);
                    var qName = _.str.trim(dialogVm.qName),
                        requestData = {
                            "question": qName,
                            "pageSize": 10000,  //保证全部请求过来
                            "pageNumber": 1
                        };
                    requestData = opts.onListRequestData.call(vm, requestData);
                    util.c2s({
                        "url": erp.BASE_PATH + "rule/getPttByCoursewareId.do",
                        "contentType": "application/json",
                        "data": JSON.stringify(requestData),
                        "success": function (responseData) {
                            var data;
                            if (responseData.flag) {
                                data = responseData.data;
                                dialogVm.list.clear();
                                _.each(data.rows, function (rowData) {
                                    var newData = {};
                                    _.each(_.keys(rowData), function (key) {
                                        newData[_.str.camelize(key)] = rowData[key];
                                    });
                                    dialogVm.list.push(_.extend(newData , {
                                        "isSelected": false
                                    }));
                                });
                                opts.onListReady && opts.onListReady.call(vm, dialogVm.list);
                            }
                        }
                    });
                },
                "clickQueryBtn": function () {
                    var dialogVm = avalon.getVm(dialogId);
                    dialogVm.updateList();
                },
                "inputEnterKey": function (evt) {
                    var dialogVm = avalon.getVm(dialogId);
                    if (evt.keyCode == 13) {
                        dialogVm.updateList();
                    }
                },
                "selectPpt": function (evt) {
                    var dialogVm = avalon.getVm(dialogId),
                        itemM = this.$vmodel.$model,
                        index = itemM.$index;
                    if (!opts.multi) {  //单选模式
                        _.each(dialogVm.list, function (itemM) {
                            itemM.isSelected = false;
                        });
                        dialogVm.list.set(index, {
                            "isSelected": true
                        });
                    } else {    //多选模式,反转选中
                        dialogVm.list.set(index, {
                            "isSelected": !dialogVm.list[index]["isSelected"]
                        });
                    }
                }
            };
            vm.open = function () {
                avalon.getVm(dialogId).open();
            };
            vm.$init = function() {
                elEl.addClass('module-cwselector fn-hide');
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm.$remove = function() {
                $(avalon.getVm(dialogId).widgetElement).remove();
                elEl.empty();
            };
            vm.$watch('title', function (val) {
                var dialogVm = avalon.getVm(dialogId);
                dialogVm.title = val;
            });
            vm.$skipArray = ["widgetElement", "dialogId"];
        });
        return vmodel;
    };
    module.version = 1.0;
    module.defaults = {
        "multi": false,  //默认支持单选
        "onListRequestData": function (requestData) {
            return requestData;
        },
        "onListReady": avalon.noop, //列表数据设置好后拦截
        "onSubmit": avalon.noop
    };
    return avalon;
});
