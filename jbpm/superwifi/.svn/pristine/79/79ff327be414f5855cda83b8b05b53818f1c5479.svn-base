/**
 * 网络管理
 */
define(['avalon', 'util', 'json', 'moment', '../../widget/dialog/dialog', '../../widget/form/select', '../../widget/form/form'], function (avalon, util, JSON, moment) {
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
            var addWebDialogId = pageName + '-add-web-dialog',
                appSelectId = pageName + '-app-select',
                linkTypeSelectId = pageName + '-link-type-select',
                webFormId = pageName + '-web-app-form';
            var pageVm = avalon.define(pageName, function (vm) {
                vm.limitTotal = 0;
                vm.limitWeb = 0;
                vm.limitApp = 0;
                vm.whiteTotal = 0;
                vm.blackTotal = 0;
                vm.ssidTotal = 0;
                vm.linkType = "微信加网页";
                vm.showDownIs = false;
                vm.showDeviceIs = false;
                vm.linkPeople = 0;
                vm.linkUsePer = 0;
                vm.linkDeviceList = [];
                vm.dialogType = "";
                vm.flowList = [];
                //显示二级
                vm.showDown = function () {
                    pageVm.showDownIs = true;
                };
                //隐藏二级
                vm.hideDown = function () {
                    pageVm.showDownIs = false;
                };
                //显示隐藏设备
                vm.showDevice = function () {
                    pageVm.showDeviceIs = pageVm.showDeviceIs ? false : true;
                };
                //添加网址dialog
                vm.$addWebDialogOpts = {
                    "dialogId": addWebDialogId,
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
                        var formVm = avalon.getVm(webFormId);
                        formVm.reset();
                        formVm.url = "";
                        formVm.managerPsd = "";
                        formVm.phoneNum = "";
                        avalon.getVm(appSelectId).select(0, true);
                        avalon.getVm(linkTypeSelectId).select(0, true);
                    },
                    "submit": function () {
                        var requestData,
                            url,
                            arr = [],
                            dialogVm = avalon.getVm(addWebDialogId),
                            formVm = avalon.getVm(webFormId);
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
                                case "link":
                                    url = app.BASE_PATH + "controller/merchant/updateConnetType.do";
                                    break;
                                case "white":
                                    arr.push(requestData.phoneNum);
                                    _.extend(requestData, {"type": 2, "actionType": 1, "phoneNum": arr});
                                    url = app.BASE_PATH + "controller/networkmanagement/addOrUpdateBlackWhiteList.do";
                                    break;
                                case "black":
                                    arr.push(requestData.phoneNum);
                                    _.extend(requestData, {"type": 1, "actionType": 1, "phoneNum": arr});
                                    url = app.BASE_PATH + "controller/networkmanagement/addOrUpdateBlackWhiteList.do";
                                    break;
                            }
                            util.c2s({
                                "url": url,
                                "data": requestData,
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        switch (pageVm.dialogType) {
                                            case "web":
                                            case "app":
                                                initLimit();
                                                break;
                                            case "link":
                                                linkType();
                                                break;
                                            case "white":
                                            case "black":
                                                initData();
                                                break;
                                        }
                                        dialogVm.close();
                                    }
                                }
                            });
                        }
                    }
                };
                //添加网址和APP Form
                vm.$addWebFormOpts = {
                    "formId": webFormId,
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
                        },
                        {
                            "selector": ".link-type",
                            "excludeHidden": true,
                            "name": "connectType",
                            "getValue": function () {
                                return avalon.getVm(linkTypeSelectId).selectedValue;
                            }
                        },
                        {
                            "selector": ".phone-num",
                            "name": "phoneNum",
                            "excludeHidden": true,
                            "rule": ["noempty", function (val, rs) {
                                if (rs[0] !== true) {
                                    return "手机号码不能为空";
                                } else if (!val.match(/^[0-9]{11}$/g)) {
                                    return "请输入正确的手机号码！"
                                } else {
                                    return true;
                                }
                            }]
                        }

                    ],
                    "url": "",
                    "managerPsd": "",
                    "phoneNum": ""
                };
                //打开弹层
                vm.openWebDialog = function () {
                    var dialogVm = avalon.getVm(addWebDialogId), formVm = avalon.getVm(webFormId), linkVal;
                    pageVm.dialogType = $(this).data('type');
                    switch (pageVm.dialogType) {
                        case "web":
                            dialogVm.title = "添加网页访问限制";
                            break;
                        case "app":
                            dialogVm.title = "添加APP访问限制";
                            break;
                        case "link":
                            if (pageVm.linkType == "网页连接") {
                                linkVal = 1;
                            } else if (pageVm.linkType == "微信连接") {
                                linkVal = 2;
                            } else {
                                linkVal = 3;
                            }
                            avalon.getVm(linkTypeSelectId).selectValue(linkVal, true);
                            dialogVm.title = "连接方式设置";
                            break;
                        case "white":
                            dialogVm.title = "添加白名单用户";
                            break;
                        case "black":
                            dialogVm.title = "添加店员名单";
                            break;
                    }
                    dialogVm.open();
                };
                //app列表Select
                vm.$appListOpts = {
                    "selectId": appSelectId,
                    "options": []
                };
                //连接方式列表Select
                vm.$linkTypeOpts = {
                    "selectId": linkTypeSelectId,
                    "options": [
                        {
                            "text": "网页连接",
                            "value": 1
                        },
                        {
                            "text": "微信连接",
                            "value": 2
                        },
                        {
                            "text": "微信和网页连接",
                            "value": 3
                        }
                    ]
                };
            });
            avalon.scan(pageEl[0]);
            initLimit();
            initData();
            linkType();
            initDevice();
            initFlow();
            initApp();
            initDeviceTotal();
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

            //初始化访问限制
            function initLimit() {
                util.c2s({
                    "url": app.BASE_PATH + "controller/networkmanagement/getAccessRestrictionsNum.do",
                    "success": function (responseData) {
                        if (responseData.flag) {
                            var data = responseData.data;
                            pageVm.limitTotal = data.total;
                            pageVm.limitWeb = data.totalWeb;
                            pageVm.limitApp = data.totalApp;
                        }
                    }
                });
            }

            //连接类型
            function linkType() {
                util.c2s({
                    "url": app.BASE_PATH + "controller/merchant/getConnectType.do",
                    "success": function (responseData) {
                        if (responseData.flag) {
                            var data = responseData.data;
                            pageVm.linkType = data.connectName;
                        }
                    }
                });
            }

            //设备连接情况
            function initDevice() {
                util.c2s({
                    "url": app.BASE_PATH + "controller/device/getConnetNum.do",
                    "success": function (responseData) {
                        if (responseData.flag) {
                            var data = responseData.data;
                            pageVm.linkPeople = data.totalUseInfo.total;
                            pageVm.linkUsePer = data.totalUseInfo.totalPercent;
                            _.each(data.oneUseInfo, function (item) {
                                item.intPer = item.percent.indexOf(".") < 0 ? item.percent.split("%")[0] : item.percent.split('.')[0];
                            });
                            pageVm.linkDeviceList = data.oneUseInfo;
                        }
                    }
                });
            }

            //设备数量
            function initDeviceTotal() {
                util.c2s({
                    "url": app.BASE_PATH + "controller/device/getCurrentDeviceCount.do",
                    "success": function (responseData) {
                        if (responseData.flag) {
                            pageVm.ssidTotal = responseData.data;
                        }
                    }
                });
            }

            //流量排行
            function initFlow() {
                util.c2s({
                    "url": app.BASE_PATH + "controller/device/getSpeedByMerchant.do",
                    "success": function (responseData) {
                        if (responseData.flag) {
                            pageVm.flowList = responseData.data.data;
                        }
                    }
                });
            }

            //初始白黑名单数
            function initData() {
                //白名单
                util.c2s({
                    "url": app.BASE_PATH + "controller/networkmanagement/getBlackWhiteCountNum.do",
                    "data": {type: 2},
                    "success": function (responseData) {
                        if (responseData.flag) {
                            pageVm.whiteTotal = responseData.data;
                        }
                    }
                }, {'withData': true});
                //店员名单
                util.c2s({
                    "url": app.BASE_PATH + "controller/networkmanagement/getBlackWhiteCountNum.do",
                    "data": {type: 1},
                    "success": function (responseData) {
                        if (responseData.flag) {
                            pageVm.blackTotal = responseData.data;
                        }
                    }
                }, {'withData': true});
            }
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var addWebDialogId = pageName + '-add-web-dialog',
                appSelectId = pageName + '-app-select',
                linkTypeSelectId = pageName + '-link-type-select',
                webFormId = pageName + '-web-app-form';
            $(avalon.getVm(addWebDialogId).widgetElement).remove();
            $(avalon.getVm(appSelectId).widgetElement).remove();
            $(avalon.getVm(linkTypeSelectId).widgetElement).remove();
            $(avalon.getVm(webFormId).widgetElement).remove();
        }
    });

    return pageMod;
});
