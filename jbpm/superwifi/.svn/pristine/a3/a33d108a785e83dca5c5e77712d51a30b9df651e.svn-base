/**
 * 访问限制
 */
define(['avalon', 'util', 'json', 'moment', '../../../widget/dialog/dialog', '../../../widget/form/form', '../../../widget/grid/grid'], function (avalon, util, JSON, moment) {
    var win = this,
        app = win.app,
        pageMod = {};
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName, routeData) {
            var webAppDialogId = pageName + '-add-web-app-dialog',
                webGridId = pageName + '-web-grid',
                appGridId = pageName + '-app-grid',
                appSelectId = pageName + '-app-select',
                webAppFormId = pageName + '-white-form';
            var pageVm = avalon.define(pageName, function (vm) {
                vm.keyword = "";
                vm.dialogType = "";
                vm.tabType = "web";
                vm.webPhb = [];
                vm.appPhb = [];
                //tab切换
                vm.toggleTab = function () {
                    if ($(this).data('type') == 'webTab') {
                        pageVm.tabType = "web";
                    } else {
                        pageVm.tabType = "app";
                    }
                };
                vm.$webGridOpts = {
                    "gridId": webGridId,
                    "disableCheckAll": false,   //开启全选模式
                    "recordUnit": "个网址",
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_TBAR'),
                            tmplTbar,
                            tmplMain,
                            tmplBbar;
                        tmplTbar = tmplTemp[0];
                        tmplTemp = tmplTemp[1].split("MS_OPTION_MAIN");
                        tmplMain = tmplTemp[0];
                        tmplBbar = tmplTemp[1];
                        return '<h2 class="page-h2">' +
                            '<span>已屏蔽<ins class="num">{{gridTotalSize}}</ins>{{recordUnit}}</span>' +
                            '<a href="javascript:;" class="add-btn main-btn-green" ms-click="openAdd" data-type="web">添加网址</a>' +
                            '</h2>' + tmplMain + '<div class="ui-grid-bbar fn-clear">' +
                            '<div class="grid-action fn-left">' +
                            '<button type="button" class="check-all-btn main-btn"' +
                            ' ms-visible="!disableCheckAll"' +
                            ' ms-click="scCheckAll">全选</button>' +
                            '&nbsp;' +
                            '<button type="button" class="approve-btn main-btn"' +
                            ' ms-click="openAdd" data-type="relieveWeb">解除限制</button>' +
                            '&nbsp;' +
                            '</div>' +
                            '<div class="pagination fn-right"' +
                            ' ms-widget="pagination,$,$paginationOpts"></div>' +
                            '</div>';
                    },
                    "columns": [
                        {
                            "dataIndex": "url",
                            "text": "网址"
                        },
                        {
                            "dataIndex": "operation",
                            "text": "操作",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<span class="format-a">' +
                                    '<a href="javascript:;" ms-click="openAdd" data-type="relieveWeb" data-id="' + rowData.id + '">解除限制</a>' +
                                    '</span>';
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        _.extend(requestData, {
                            "pageSize": 5,
                            "type": 1
                        });
                        util.c2s({
                            "url": app.BASE_PATH + "controller/networkmanagement/getAccessRestrictionsList.do",
                            "data": requestData,
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    callback.call(this, data);
                                }
                            }
                        }, {'withData': true});
                    }
                };
                vm.$appGridOpts = {
                    "gridId": appGridId,
                    "disableCheckAll": false,   //开启全选模式
                    "recordUnit": "个APP",
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_TBAR'),
                            tmplTbar,
                            tmplMain,
                            tmplBbar;
                        tmplTbar = tmplTemp[0];
                        tmplTemp = tmplTemp[1].split("MS_OPTION_MAIN");
                        tmplMain = tmplTemp[0];
                        tmplBbar = tmplTemp[1];
                        return '<h2 class="page-h2">' +
                            '<span>已屏蔽<ins class="num">{{gridTotalSize}}</ins>{{recordUnit}}</span>' +
                            '<a href="javascript:;" class="add-btn main-btn-green" ms-click="openAdd" data-type="app">添加APP</a>' +
                            '</h2>' + tmplMain + '<div class="ui-grid-bbar fn-clear">' +
                            '<div class="grid-action fn-left">' +
                            '<button type="button" class="check-all-btn main-btn"' +
                            ' ms-visible="!disableCheckAll"' +
                            ' ms-click="scCheckAll">全选</button>' +
                            '&nbsp;' +
                            '<button type="button" class="approve-btn main-btn"' +
                            ' ms-click="openAdd" data-type="relieveApp">解除限制</button>' +
                            '&nbsp;' +
                            '</div>' +
                            '<div class="pagination fn-right"' +
                            ' ms-widget="pagination,$,$paginationOpts"></div>' +
                            '</div>';
                    },
                    "columns": [
                        {
                            "dataIndex": "name",
                            "text": "APP名称"
                        },
                        {
                            "dataIndex": "operation",
                            "text": "操作",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<span class="format-a">' +
                                    '<a href="javascript:;" ms-click="openAdd" data-type="relieveApp" data-id="' + rowData.id + '">解除限制</a>' +
                                    '</span>';
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        _.extend(requestData, {
                            "pageSize": 5,
                            "type": 2
                        });
                        util.c2s({
                            "url": app.BASE_PATH + "controller/networkmanagement/getAccessRestrictionsList.do",
                            "data": requestData,
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    callback.call(this, data);
                                }
                            }
                        }, {'withData': true});
                    }
                };
                //添加删除白名单dialog
                vm.$webAppDialogOpts = {
                    "dialogId": webAppDialogId,
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
                        var formVm = avalon.getVm(webAppFormId);
                        formVm.reset();
                        formVm.url = "";
                        formVm.managerPsd = "";
                        avalon.getVm(webGridId).unselectById('all');
                        avalon.getVm(appGridId).unselectById('all');
                        avalon.getVm(appSelectId).select(0, true);
                    },
                    "submit": function () {
                        var requestData,
                            url,
                            phoneList,
                            dialogVm = avalon.getVm(webAppDialogId),
                            formVm = avalon.getVm(webAppFormId),
                            webGridVm = avalon.getVm(webGridId),
                            appGridVm = avalon.getVm(appGridId);
                        if (formVm.validate()) {
                            requestData = formVm.getFormData();
                            switch (pageVm.dialogType) {
                                case "web":
                                    _.extend(requestData, {"type": 1});
                                    url = app.BASE_PATH + "controller/networkmanagement/addAccessRestrictions.do";
                                    break;
                                case "app":
                                    _.extend(requestData, {"type": 2});
                                    url = app.BASE_PATH + "controller/networkmanagement/addAccessRestrictions.do";
                                    break;
                                case "relieveWeb":
                                    phoneList = _.map(webGridVm.getSelectedData(), function (itemData) {
                                        return itemData.id;
                                    });
                                    _.extend(requestData, {"id": phoneList});
                                    url = app.BASE_PATH + "controller/networkmanagement/deleteAccessRestrictions.do";
                                    break;
                                case "relieveApp":
                                    phoneList = _.map(appGridVm.getSelectedData(), function (itemData) {
                                        return itemData.id;
                                    });
                                    _.extend(requestData, {"id": phoneList});
                                    url = app.BASE_PATH + "controller/networkmanagement/deleteAccessRestrictions.do";
                                    break;
                            }
                            util.c2s({
                                "url": url,
                                "data": requestData,
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        switch (pageVm.dialogType) {
                                            case "web":
                                                webGridVm.load();
                                                break;
                                            case "app":
                                                appGridVm.load();
                                                break;
                                            case "relieveWeb":
                                                webGridVm.load();
                                                break;
                                            case "relieveApp":
                                                appGridVm.load();
                                                break;
                                        }
                                        dialogVm.close();
                                    }
                                }
                            });
                        }
                    }
                };
                //添加白名单 Form
                vm.$webAppFormOpts = {
                    "formId": webAppFormId,
                    "field": [
                        {
                            "selector": ".web-url",
                            "name": "url",
                            "excludeHidden": true,
                            "rule": ["noempty", function (val, rs) {
                                if (rs[0] !== true) {
                                    return "地址不能为空";
                                } else {
                                    return true;
                                }
                            }]
                        },
                        {
                            "selector": ".app-list",
                            "excludeHidden": true,
                            "name": "appName",
                            "getValue": function () {
                                return avalon.getVm(appSelectId).selectedText;
                            },
                            "rule": function () {
                                if (avalon.getVm(appSelectId).selectedValue == 0) {
                                    return "请选择APP！";
                                } else {
                                    return true;
                                }
                            }
                        },
                        {
                            "selector": ".app-list",
                            "excludeHidden": true,
                            "name": "url",
                            "getValue": function () {
                                return avalon.getVm(appSelectId).selectedValue;
                            }
                        },
                        {
                            "selector": ".manager-psd",
                            "name": "managerPsd",
                            "excludeHidden": true,
                            "rule": ["noempty", function (val, rs) {
                                if (rs[0] !== true) {
                                    return "主管密码不能为空！";
                                } else {
                                    return true;
                                }
                            }]
                        }
                    ],
                    "url": "",
                    "managerPsd": ""
                };
                //app列表Select
                vm.$appListOpts = {
                    "selectId": appSelectId,
                    "options": []
                };
                //打开弹层
                vm.openAdd = function () {
                    var dialogVm = avalon.getVm(webAppDialogId), webGridVm = avalon.getVm(webGridId), appGridVm = avalon.getVm(appGridId);
                    pageVm.dialogType = $(this).data('type');
                    switch (pageVm.dialogType) {
                        case "web":
                            dialogVm.title = "添加网页访问限制";
                            dialogVm.open();
                            break;
                        case "app":
                            dialogVm.title = "添加APP访问限制";

                            dialogVm.open();
                            break;
                        case "relieveWeb":
                            if ($(this).data('id')) {
                                webGridVm.unselectById('all');
                                webGridVm.selectById($(this).data('id'));
                            }
                            if (webGridVm.getSelectedData().length > 0) {
                                dialogVm.title = "解除网站访问限制";
                                dialogVm.open();
                            } else {
                                util.alert({
                                    "iconType": "info",
                                    "content": "请选择用户！"
                                });
                            }
                            break;
                        case "relieveApp":
                            if ($(this).data('id')) {
                                appGridVm.unselectById('all');
                                appGridVm.selectById($(this).data('id'));
                            }
                            if (appGridVm.getSelectedData().length > 0) {
                                dialogVm.title = "解除APP访问限制";
                                dialogVm.open();
                            } else {
                                util.alert({
                                    "iconType": "info",
                                    "content": "请选择用户！"
                                });
                            }
                            break;
                    }
                };
            });
            avalon.scan(pageEl[0]);
            avalon.getVm(webGridId).load();
            avalon.getVm(appGridId).load();
            initApp();
            initPhb();
            //初始化APP列表
            function initApp() {
                util.c2s({
                    "url": app.BASE_PATH + "controller/networkmanagement/getAppInfoList.do",
                    "success": function (responseData) {
                        if (responseData.flag) {
                            var data = responseData.data.data;
                            avalon.getVm(appSelectId).setOptions(
                                [
                                    {"text": "请选择要屏蔽的APP", "value": 0}
                                ].concat(_.map(data, function (itemData) {
                                        return {
                                            "text": itemData.name,
                                            "value": itemData.url
                                        };
                                    })));
                        }
                    }
                });
            }

            //初始化排行榜
            function initPhb() {
                util.c2s({
                    "url": app.BASE_PATH + "controller/networkmanagement/getTopAccessRestrictionsList.do",
                    "data": {'type': 1},
                    "success": function (responseData) {
                        if (responseData.flag) {
                            pageVm.webPhb = responseData.data;
                        }
                    }
                }, {'withData': true});
                util.c2s({
                    "url": app.BASE_PATH + "controller/networkmanagement/getTopAccessRestrictionsList.do",
                    "data": {'type': 2},
                    "success": function (responseData) {
                        if (responseData.flag) {
                            pageVm.appPhb = responseData.data;
                        }
                    }
                }, {'withData': true});
            }
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var webAppDialogId = pageName + '-add-web-app-dialog',
                webGridId = pageName + '-web-grid',
                appGridId = pageName + '-app-grid',
                appSelectId = pageName + '-app-select',
                webAppFormId = pageName + '-white-form';
            $(avalon.getVm(webAppDialogId).widgetElement).remove();
            $(avalon.getVm(webGridId).widgetElement).remove();
            $(avalon.getVm(appGridId).widgetElement).remove();
            $(avalon.getVm(webAppFormId).widgetElement).remove();
            $(avalon.getVm(appSelectId).widgetElement).remove();
        }
    });

    return pageMod;
});
