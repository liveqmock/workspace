/**
 * 店员名单管理
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
            var addBlackDialogId = pageName + '-add-black-dialog',
                gridId = pageName + '-grid',
                blackFormId = pageName + '-black-form';
            var pageVm = avalon.define(pageName, function (vm) {
                vm.keyword = "";
                vm.operType = "add";
                vm.searchType = "search";
                vm.$gridOpts = {
                    "gridId": gridId,
                    "disableCheckAll": false,   //开启全选模式
                    "recordUnit": "个用户",
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
                            '<span>共有<ins class="num">{{gridTotalSize}}</ins>{{recordUnit}}</span>' +
                            '<a href="javascript:;" class="add-btn main-btn-green" ms-click="openAddUser" data-type="add">手动添加用户</a>' +
                            '<a href="javascript:;" class="add-btn main-btn-green" ms-click="tbList" data-type="add">同步用户列表</a>' +
                            '</h2>' + tmplMain + '<div class="ui-grid-bbar fn-clear">' +
                            '<div class="grid-action fn-left">' +
                            '<button type="button" class="check-all-btn main-btn"' +
                            ' ms-visible="!disableCheckAll"' +
                            ' ms-click="scCheckAll">全选</button>' +
                            '&nbsp;' +
                            '<button type="button" class="approve-btn main-btn"' +
                            ' ms-click="openAddUser" data-type="del">从店员名单中移除</button>' +
                            '&nbsp;' +
                            '<button type="button" class="approve-btn main-btn"' +
                            ' ms-click="openAddUser" data-type="addWhite">添加至白名单</button>' +
                            '&nbsp;' +
                            '</div>' +
                            '<div class="pagination fn-right"' +
                            ' ms-widget="pagination,$,$paginationOpts"></div>' +
                            '</div>';
                    },
                    "columns": [
                        {
                            "dataIndex": "phoneNum",
                            "text": "手机号"
                        },
                        {
                            "dataIndex": "mac",
                            "text": "MAC码"
                        },
                        {
                            "dataIndex": "insertTime",
                            "text": "添加时间",
                            "renderer": function (v) {
                                return moment(v).format('YYYY-MM-DD');
                            }
                        },
                        {
                            "dataIndex": "operation",
                            "text": "操作",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<span class="format-a">' +
                                    '<a href="javascript:;" ms-click="openAddUser" data-type="del" data-id="' + rowData.id + '">从店员名单中移除</a>' +
                                    '<a href="javascript:;" ms-click="openAddUser" data-type="addWhite" data-id="' + rowData.id + '">添加至白名单</a>' +
                                    '</span>';
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        _.extend(requestData, {
                            "phoneNum": pageVm.searchType == 'search' ? $.trim(pageVm.keyword) : "",
                            "source": pageVm.searchType == 'out' ? 2 : 0,
                            "type": 1
                        });
                        util.c2s({
                            "url": app.BASE_PATH + "controller/networkmanagement/getBlackWhiteList.do",
                            "data": requestData,
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    callback.call(this, data);
                                }
                            }
                        });
                    }
                };
                //添加删除白名单dialog
                vm.$addBlackDialogOpts = {
                    "dialogId": addBlackDialogId,
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
                        var formVm = avalon.getVm(blackFormId);
                        formVm.reset();
                        formVm.phoneNum = "";
                        formVm.managerPsd = "";
                        formVm.time = "";
                        avalon.getVm(gridId).unselectById('all');
                    },
                    "submit": function () {
                        var requestData,
                            phoneList = [],
                            dialogVm = avalon.getVm(addBlackDialogId),
                            formVm = avalon.getVm(blackFormId),
                            gridVm = avalon.getVm(gridId);
                        if (formVm.validate()) {
                            requestData = formVm.getFormData();
                            if (pageVm.operType == 'add') {
                                phoneList.push(requestData.phoneNum);
                                _.extend(requestData, {"type": 1, "actionType": 1, "phoneNum": phoneList});
                                util.c2s({
                                    "url": app.BASE_PATH + "controller/networkmanagement/addOrUpdateBlackWhiteList.do",
                                    "data": requestData,
                                    "success": function (responseData) {
                                        if (responseData.flag) {
                                            avalon.getVm(gridId).load();
                                            dialogVm.close();
                                        }
                                    }
                                });
                            } else if (pageVm.operType == 'del') {
                                phoneList = _.map(gridVm.getSelectedData(), function (itemData) {
                                    return itemData.phoneNum;
                                });
                                _.extend(requestData, {"type": 1, "actionType": 2, "phoneNum": phoneList});
                                util.c2s({
                                    "url": app.BASE_PATH + "controller/networkmanagement/addOrUpdateBlackWhiteList.do",
                                    "data": requestData,
                                    "success": function (responseData) {
                                        if (responseData.flag) {
                                            gridVm.load();
                                            dialogVm.close();
                                        }
                                    }
                                });
                            } else if (pageVm.operType == 'addWhite') {
                                phoneList = _.map(gridVm.getSelectedData(), function (itemData) {
                                    return itemData.phoneNum;
                                });
                                _.extend(requestData, {"type": 2, "actionType": 3, "phoneNum": phoneList});
                                util.c2s({
                                    "url": app.BASE_PATH + "controller/networkmanagement/addOrUpdateBlackWhiteList.do",
                                    "data": requestData,
                                    "success": function (responseData) {
                                        if (responseData.flag) {
                                            avalon.getVm(gridId).load();
                                            dialogVm.close();
                                        }
                                    }
                                });
                            } else {
                                util.c2s({
                                    "url": app.BASE_PATH + "controller/merchant/setShopTime.do",
                                    "data": requestData,
                                    "success": function (responseData) {
                                        if (responseData.flag) {
                                            dialogVm.close();
                                        }
                                    }
                                });
                            }
                        }
                    }
                };
                //添加白名单 Form
                vm.$addBlackFormOpts = {
                    "formId": blackFormId,
                    "field": [
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
                        } ,
                        {
                            "selector": ".time",
                            "name": "time",
                            "excludeHidden": true,
                            "rule": ["noempty", function (val, rs) {
                                if (rs[0] !== true) {
                                    return "时长不能为空";
                                } else {
                                    return true;
                                }
                            }],
                            "getValue": function () {
                                return +$(this).val();
                            }
                        }
                    ],
                    "phoneNum": "",
                    "managerPsd": "",
                    "time": ""
                };
                //打开弹层
                vm.openAddUser = function () {
                    var dialogVm = avalon.getVm(addBlackDialogId), gridVm = avalon.getVm(gridId);
                    pageVm.operType = $(this).data('type');
                    if (pageVm.operType == 'add') {
                        dialogVm.title = '添加白名单用户';
                        dialogVm.open();
                    } else {
                        if (pageVm.operType == 'setTime') {
                            util.c2s({
                                "url": app.BASE_PATH + "controller/merchant/getShopTime.do",
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        avalon.getVm(blackFormId).time = responseData.data;
                                        dialogVm.title = '驻店时长设置';
                                        dialogVm.open();
                                    }
                                }
                            });
                        } else {
                            if ($(this).data('id')) {
                                gridVm.unselectById('all');
                                gridVm.selectById($(this).data('id'));
                            }
                            if (gridVm.getSelectedData().length > 0) {
                                dialogVm.title = pageVm.operType == 'del' ? '解除店员名单' : '添加至白名单';
                                dialogVm.open();
                            } else {
                                util.alert({
                                    "iconType": "info",
                                    "content": "请选择用户！"
                                });
                            }
                        }
                    }
                };
                vm.query = function () {
                    pageVm.searchType = $(this).data('type');
                    avalon.getVm(gridId).load();
                };
                //同步用户列表
                vm.tbList = function () {
                    util.c2s({
                        "url": app.BASE_PATH + "controller/networkmanagement/synchronousDate.do",
                        "success": function (responseData) {
                            if (responseData.flag) {
                                util.alert({
                                    "iconType": "success",
                                    "content": "同步成功！"
                                });
                            }
                        }
                    });
                }
            });
            avalon.scan(pageEl[0]);
            avalon.getVm(gridId).load();
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var addBlackDialogId = pageName + '-add-black-dialog',
                gridId = pageName + '-grid',
                blackFormId = pageName + '-black-form';
            $(avalon.getVm(addBlackDialogId).widgetElement).remove();
            $(avalon.getVm(gridId).widgetElement).remove();
            $(avalon.getVm(blackFormId).widgetElement).remove();
        }
    });

    return pageMod;
});
