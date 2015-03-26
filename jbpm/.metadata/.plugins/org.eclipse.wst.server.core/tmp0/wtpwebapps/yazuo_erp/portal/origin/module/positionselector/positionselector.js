/**
 * 职位选择组件
 */
define(["avalon", "util", "json", "text!./positionselector.html", "text!./positionselector.css", "common", "../../widget/dialog/dialog"], function(avalon, util, JSON, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    var styleData = styleEl.data('module') || {};
    if (!styleData["positionselector"]) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'positionselector/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'positionselector/');
        }
        styleData["positionselector"] = true;
        styleEl.data('module', styleData);
    }
    var module = erp.module.positionselector = function(element, data, vmodels) {
        var opts = data.positionselectorOptions,
            positionDialogId = data.positionselectorId + '-dialog',
            elEl = $(element);
        var vmodel = avalon.define(data.positionselectorId, function (vm) {
            avalon.mix(vm, opts);
            vm.widgetElement = element;
            vm.dialogId = positionDialogId;
            vm.$positionOpts = {
                "dialogId": positionDialogId,
                "title": "选择职位",
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
                "positionList": [],
                "onClose": function () {
                    //清除item
                    var dialogVm = avalon.getVm(positionDialogId);
                    //滚动到顶部
                    $('.list-wrapper', dialogVm.widgetElement).scrollTop(0);
                    dialogVm.positionList.clear();
                },
                "onOpen": function () {
                    var dialogVm = avalon.getVm(positionDialogId);
                    dialogVm.updateList();
                },
                "submit": function (evt) {
                    var dialogVm = avalon.getVm(positionDialogId),
                        result = [];
                    //获得所选的课件id
                    _.each(dialogVm.positionList.$model, function (itemM) {
                        if (itemM.isSelected) {
                            result.push(itemM);
                        }
                    });
                    opts.onSubmit && opts.onSubmit.call(vm, result);
                    dialogVm.close();
                },
                "updateList": function () {
                    var dialogVm = avalon.getVm(positionDialogId),
                        requestData = opts.onListRequestData.call(vm, {
                            "pageSize": 10000,  //保证全部请求过来
                            "pageNumber": 1
                        });
                    util.c2s({
                        "url": erp.BASE_PATH + "studentManagement/queryPosition.do",
                        "data": JSON.stringify(requestData),
                        "contentType": "application/json",
                        "success": function (responseData) {
                            var data;
                            if (responseData.flag) {
                                data = responseData.data;
                                dialogVm.positionList.clear();
                                _.each(data, function (rowData) {
                                    var newData = {};
                                    _.each(_.keys(rowData), function (key) {
                                        newData[_.str.camelize(key)] = rowData[key];
                                    });
                                    dialogVm.positionList.push(_.extend(newData , {
                                        "isSelected": false
                                    }));
                                });
                                opts.onListReady && opts.onListReady.call(vm, dialogVm.positionList);
                            }
                        }
                    }, {
                        "mask": dialogVm.widgetElement
                    });
                },
                "clickQueryBtn": function () {
                    var dialogVm = avalon.getVm(positionDialogId);
                    dialogVm.updateList();
                },
                "inputEnterKey": function (evt) {
                    var dialogVm = avalon.getVm(positionDialogId);
                    if (evt.keyCode == 13) {
                        dialogVm.updateList();
                    }
                },
                "selectPosition": function (evt) {
                    var dialogVm = avalon.getVm(positionDialogId),
                        itemM = this.$vmodel.$model,
                        index = itemM.$index;
                    if (!opts.multi) {  //单选模式
                        _.each(dialogVm.positionList, function (itemM) {
                            itemM.isSelected = false;
                        });
                        dialogVm.positionList.set(index, {
                            "isSelected": true
                        });
                    } else {    //多选模式,反转选中
                        dialogVm.positionList.set(index, {
                            "isSelected": !dialogVm.positionList[index]["isSelected"]
                        });
                    }
                }
            };
            vm.open = function () {
                avalon.getVm(positionDialogId).open();
            };
            vm.$init = function() {
                elEl.addClass('module-positionselector fn-hide');
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm.$remove = function() {
                $(avalon.getVm(positionDialogId).widgetElement).remove();
                elEl.removeClass('module-positionselector').off().empty();
            };
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
