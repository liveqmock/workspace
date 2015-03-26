/**
 * 白名单管理
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
            var addWhiteDialogId = pageName + '-add-white-dialog',
                gridId = pageName + '-grid',
                whiteFormId = pageName + '-white-form';
            var pageVm = avalon.define(pageName, function (vm) {
                vm.keyword = "";
                vm.operType = "add";
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
                            '<a href="javascript:;" class="add-btn main-btn-green" ms-click="openAddUser" data-type="add">添加用户</a>' +
                            '</h2>' + tmplMain + '<div class="ui-grid-bbar fn-clear">' +
                            '<div class="grid-action fn-left">' +
                            '<button type="button" class="check-all-btn main-btn"' +
                            ' ms-visible="!disableCheckAll"' +
                            ' ms-click="scCheckAll">全选</button>' +
                            '&nbsp;' +
                            '<button type="button" class="approve-btn main-btn"' +
                            ' ms-click="openAddUser" data-type="del">从白名单中移除</button>' +
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
                            "renderer":function(v){
                                return moment(v).format('YYYY-MM-DD');
                            }
                        },
                        {
                            "dataIndex": "operation",
                            "text": "操作",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<span class="format-a">' +
                                    '<a href="javascript:;" ms-click="openAddUser" data-type="del" data-id="' + rowData.id + '">从白名单中移除</a>' +
                                    '</span>';
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        _.extend(requestData, {
                            "phoneNum": $.trim(pageVm.keyword),
                            "type": 2
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
                vm.$addWhiteDialogOpts = {
                    "dialogId": addWhiteDialogId,
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
                        var formVm = avalon.getVm(whiteFormId);
                        formVm.reset();
                        formVm.phoneNum = "";
                        formVm.managerPsd = "";
                        avalon.getVm(gridId).unselectById('all');
                    },
                    "submit": function () {
                        var requestData,
                            arr = [],
                            dialogVm = avalon.getVm(addWhiteDialogId),
                            formVm = avalon.getVm(whiteFormId),
                            gridVm = avalon.getVm(gridId);
                        if (formVm.validate()) {
                            requestData = formVm.getFormData();
                            if (pageVm.operType == 'add') {
                                arr.push(requestData.phoneNum);
                                _.extend(requestData, {"type": 2, "actionType": 1, "phoneNum": arr});
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
                                var phoneList = _.map(gridVm.getSelectedData(), function (itemData) {
                                    return itemData.phoneNum;
                                });
                                _.extend(requestData, {"type": 2, "actionType": 2, "phoneNum": phoneList});
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
                            }
                        }
                    }
                };
                //添加白名单 Form
                vm.$addWhiteFormOpts = {
                    "formId": whiteFormId,
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
                        }
                    ],
                    "phoneNum": "",
                    "managerPsd": ""
                };
                //打开弹层
                vm.openAddUser = function () {
                    var dialogVm = avalon.getVm(addWhiteDialogId), gridVm = avalon.getVm(gridId);
                    pageVm.operType = $(this).data('type');
                    if (pageVm.operType == 'add') {
                        dialogVm.title = '添加白名单用户';
                        dialogVm.open();
                    } else {
                        if ($(this).data('id')) {
                            gridVm.unselectById('all');
                            gridVm.selectById($(this).data('id'));
                        }
                        if(gridVm.getSelectedData().length>0){
                            dialogVm.title = '解除白名单';
                            dialogVm.open();
                        }else{
                            util.alert({
                                "iconType": "info",
                                "content": "请选择用户！"
                            });
                        }

                    }
                };
                vm.query = function () {
                    avalon.getVm(gridId).load();
                };
            });
            avalon.scan(pageEl[0]);
            avalon.getVm(gridId).load();
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var addWhiteDialogId = pageName + '-add-white-dialog',
                gridId = pageName + '-grid',
                whiteFormId = pageName + '-white-form';
            $(avalon.getVm(addWhiteDialogId).widgetElement).remove();
            $(avalon.getVm(gridId).widgetElement).remove();
            $(avalon.getVm(whiteFormId).widgetElement).remove();
        }
    });

    return pageMod;
});
