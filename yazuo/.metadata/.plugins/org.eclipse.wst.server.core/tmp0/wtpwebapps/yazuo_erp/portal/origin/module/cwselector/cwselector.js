/**
 * 课件选择组件
 */
define(["avalon", "util", "text!./cwselector.html", "text!./cwselector.css", "common", "../../widget/dialog/dialog"], function(avalon, util, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    var styleData = styleEl.data('module') || {};
    if (!styleData["cwselector"]) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'cwselector/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'cwselector/');
        }
        styleData["cwselector"] = true;
        styleEl.data('module', styleData);
    }
    var module = erp.module.cwselector = function(element, data, vmodels) {
        var opts = data.cwselectorOptions,
            coursewareDialogId = data.cwselectorId + '-dialog',
            elEl = $(element);
        var vmodel = avalon.define(data.cwselectorId, function (vm) {
            avalon.mix(vm, opts);
            vm.widgetElement = element;
            vm.dialogId = coursewareDialogId;
            vm.$coursewareOpts = {
                "dialogId": coursewareDialogId,
                "title": "选择课件",
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
                "coursewareList": [],
                "queryCourseName": "",
                "onClose": function () {
                    //清除item
                    var dialogVm = avalon.getVm(coursewareDialogId);
                    dialogVm.coursewareList.clear();
                    dialogVm.queryCourseName = "";
                    //滚动到顶部
                    $('.list-wrapper', dialogVm.widgetElement).scrollTop(0);
                },
                "onOpen": function () {
                    var dialogVm = avalon.getVm(coursewareDialogId);
                    dialogVm.updateList();
                },
                "submit": function (evt) {
                    var dialogVm = avalon.getVm(coursewareDialogId),
                        result = [];
                    //获得所选的课件id
                    _.each(dialogVm.coursewareList.$model, function (itemM) {
                        if (itemM.isSelected) {
                            result.push(itemM);
                        }
                    });
                    opts.onSubmit && opts.onSubmit.call(vm, result);
                    dialogVm.close();
                },
                "updateList": function () {
                    var dialogVm = avalon.getVm(coursewareDialogId);
                    var queryCourseName = _.str.trim(dialogVm.queryCourseName);
                    util.c2s({
                        "url": erp.BASE_PATH + "courseware/getCourseware.do",
                        "data": {
                            "coursewareName": queryCourseName,
                            "pageSize": 10000,  //保证全部请求过来
                            "pageNumber": 1
                        },
                        "success": function (responseData) {
                            var data;
                            if (responseData.flag) {
                                data = responseData.data;
                                dialogVm.coursewareList.clear();
                                _.each(data.rows, function (rowData) {
                                    var newData = {};
                                    _.each(_.keys(rowData), function (key) {
                                        newData[_.str.camelize(key)] = rowData[key];
                                    });
                                    dialogVm.coursewareList.push(_.extend(newData , {
                                        "isSelected": false
                                    }));
                                });
                                opts.onListReady && opts.onListReady.call(vm, dialogVm.coursewareList);
                            }
                        }
                    });
                },
                "clickQueryBtn": function () {
                    var dialogVm = avalon.getVm(coursewareDialogId);
                    dialogVm.updateList();
                },
                "inputEnterKey": function (evt) {
                    var dialogVm = avalon.getVm(coursewareDialogId);
                    if (evt.keyCode == 13) {
                        dialogVm.updateList();
                    }
                },
                "selectCourseware": function (evt) {
                    var dialogVm = avalon.getVm(coursewareDialogId),
                        itemM = this.$vmodel.$model,
                        index = itemM.$index;
                    if (!opts.multi) {  //单选模式
                        _.each(dialogVm.coursewareList, function (itemM) {
                            itemM.isSelected = false;
                        });
                        dialogVm.coursewareList.set(index, {
                            "isSelected": true
                        });
                    } else {    //多选模式,反转选中
                        dialogVm.coursewareList.set(index, {
                            "isSelected": !dialogVm.coursewareList[index]["isSelected"]
                        });
                    }
                }
            };
            vm.open = function () {
                avalon.getVm(coursewareDialogId).open();
            };
            vm.$init = function() {
                elEl.addClass('module-cwselector fn-hide');
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm.$remove = function() {
                $(avalon.getVm(coursewareDialogId).widgetElement).remove();
                elEl.empty();
            };
            vm.$skipArray = ["widgetElement", "dialogId"];
        });
        return vmodel;
    };
    module.version = 1.0;
    module.defaults = {
        "multi": false,  //默认支持单选
        "onListReady": avalon.noop, //列表数据设置好后拦截
        "onSubmit": avalon.noop
    };
    return avalon;
});
