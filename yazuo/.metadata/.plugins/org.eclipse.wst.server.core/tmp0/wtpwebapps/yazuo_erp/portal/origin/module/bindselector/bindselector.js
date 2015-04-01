/**
 * 绑定用户或者分配老师
 */
define(["avalon", "util", "json", "text!./bindselector.html", "text!./bindselector.css", "common", "../../widget/dialog/dialog", "../../widget/form/select"], function(avalon, util, JSON, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    var styleData = styleEl.data('module') || {};
    if (!styleData["bindselector"]) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'bindselector/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'bindselector/');
        }
        styleData["bindselector"] = true;
        styleEl.data('module', styleData);
    }
    var module = erp.module.bindselector = function(element, data, vmodels) {
        var opts = data.bindselectorOptions,
            bindDialogId = data.bindselectorId + '-dialog',
            selectId = data.bindselectorId + '-select',
            elEl = $(element);
        var vmodel = avalon.define(data.bindselectorId, function (vm) {
            avalon.mix(vm, opts);
            vm.widgetElement = element;
            vm.dialogId = bindDialogId;
            vm.courseSelectId =  selectId;
            vm.userRole = 'student';    //用户列表角色 student or teacher
            vm.$bindOpts = {
                "dialogId": bindDialogId,
                "width": 620,
                "title": "",
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
                "userList": [],
                /*"$courseListOpts": {
                    "selectId": selectId
                },*/
                "userName": "",
                "mobile": "",
                "onClose": function () {
                    //清除item
                    var dialogVm = avalon.getVm(bindDialogId);
                    dialogVm.userList.clear();
                    dialogVm.userName = "";
                    dialogVm.mobile = "";
                    //滚动到顶部
                    $('.list-wrapper', dialogVm.widgetElement).scrollTop(0);
                },
                "onOpen": function () {
                    var dialogVm = avalon.getVm(bindDialogId);
                    //dialogVm.updateCourseList();
                    dialogVm.updateList();
                },
                "submit": function (evt) {
                    var dialogVm = avalon.getVm(bindDialogId),
                        selectVm = avalon.getVm(selectId),
                        result = [];
                    //验证是否已选择课程
                    /*if (!selectVm.selectedValue) {
                        util.alert({
                            "iconType": "error",
                            "content": "请为学员指定课程"
                        });
                        return;
                    }*/
                    //获得所选的课件id
                    _.each(dialogVm.userList.$model, function (itemM) {
                        if (itemM.isSelected) {
                            result.push(itemM);
                        }
                    });
                    if (!result.length) {
                        util.alert({
                            "iconType": "error",
                            "content": "请选择对应的老师"
                        });
                    } else {
                        /*util.confirm({
                            "iconType": "info",
                            "content": "你确定学员课程已正确分配了吗？确定后，学员课程不可更改。",
                            "onSubmit": function () {
                                opts.onSubmit && opts.onSubmit.call(vm, result);
                            }
                        });*/
                        opts.onSubmit && opts.onSubmit.call(vm, result);
                    }
                },
                /*"updateCourseList": function () {
                    // 加载关联课程dropdown-list
                    util.c2s({
                        "url": erp.BASE_PATH + "studentManagement/queryCourse.do",
                        "success": function (responseData) {
                            var selectVm = avalon.getVm(selectId);
                            if (responseData.flag) {
                                selectVm.setOptions([{
                                    "text": "请选择",
                                    "value": 0
                                }].concat(_.map(responseData.data, function (itemData) {
                                    return {
                                        "text": itemData.course_name,
                                        "value": itemData.id
                                    };
                                })));
                            }
                        }
                    });
                },*/
                "updateList": function () {
                    var dialogVm = avalon.getVm(bindDialogId);
                    var userName = _.str.trim(dialogVm.userName),
                        mobile = _.str.trim(dialogVm.mobile);
                    util.c2s({
                        "url": erp.BASE_PATH + opts.listPath,
                        "data": JSON.stringify({
                            "userName": userName,
                            "tel": mobile,
                            "pageSize": 10000,  //保证全部请求过来
                            "pageNumber": 1
                        }),
                        "contentType": "application/json",
                        "success": function (responseData) {
                            var data;
                            if (responseData.flag) {
                                data = responseData.data;
                                dialogVm.userList.clear();
                                _.each(data.rows, function (rowData) {
                                    var newData = {};
                                    _.each(_.keys(rowData), function (key) {
                                        newData[_.str.camelize(key)] = rowData[key];
                                    });
                                    dialogVm.userList.push(_.extend(newData , {
                                        "isSelected": false
                                    }));
                                });
                                opts.onListReady && opts.onListReady.call(vm, dialogVm.userList);
                            }
                        }
                    });
                },
                "clickQueryBtn": function () {
                    var dialogVm = avalon.getVm(bindDialogId);
                    dialogVm.updateList();
                },
                "inputEnterKey": function (evt) {
                    var dialogVm = avalon.getVm(bindDialogId);
                    if (evt.keyCode == 13) {
                        dialogVm.updateList();
                    }
                },
                "selectUser": function (evt) {
                    var dialogVm = avalon.getVm(bindDialogId),
                        itemM = this.$vmodel.$model,
                        index = itemM.$index;
                    if (!opts.multi) {  //单选模式
                        _.each(dialogVm.userList, function (itemM) {
                            itemM.isSelected = false;
                        });
                        dialogVm.userList.set(index, {
                            "isSelected": true
                        });
                    } else {    //多选模式,反转选中
                        dialogVm.userList.set(index, {
                            "isSelected": !dialogVm.userList[index]["isSelected"]
                        });
                    }
                }
            };
            vm.open = function () {
                avalon.getVm(bindDialogId).open();
            };
            vm.close = function () {
                avalon.getVm(bindDialogId).close();
            };
            vm.$init = function() {
                elEl.addClass('module-bindselector fn-hide');
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm.$remove = function() {
                //$(avalon.getVm(selectId).widgetElement).remove();
                $(avalon.getVm(bindDialogId).widgetElement).remove();
                elEl.empty();
            };
            vm.$skipArray = ["widgetElement", "dialogId"];

            vm.$watch("title", function (val) {
                avalon.getVm(bindDialogId).title = val;
            });
        });
        return vmodel;
    };
    module.version = 1.0;
    module.defaults = {
        "title": "",
        "multi": false,  //默认支持单选
        "listPath": "studentManagement/queryStudentListToBeAllocated.do",   //默认请求待分配的学生列表
        "onListReady": avalon.noop, //列表数据设置好后拦截
        "onSubmit": avalon.noop
    };
    return avalon;
});
