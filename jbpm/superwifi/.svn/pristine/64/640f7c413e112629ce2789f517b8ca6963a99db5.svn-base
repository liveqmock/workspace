/**
 * 设备名称管理
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
            var editDeviceDialogId = pageName + '-edit-device-dialog',
                gridId = pageName + '-grid',
                deviceFormId = pageName + '-device-form';
            var pageVm = avalon.define(pageName, function (vm) {
                vm.$gridOpts = {
                    "gridId": gridId,
                    "disableCheckAll": true,   //开启全选模式
                    "disableCheckbox": true,
                    "recordUnit": "台设备",
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
                            '</h2>' + tmplMain + '<div class="ui-grid-bbar fn-clear">' +
                            '<div class="pagination fn-right"' +
                            ' ms-widget="pagination,$,$paginationOpts"></div>' +
                            '</div>';
                    },
                    "columns": [
                        {
                            "dataIndex": "thisIndex",
                            "text": "序号",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '{{$outer.$index+1}}';
                            }
                        },
                        {
                            "dataIndex": "deviceName",
                            "text": "设备名称",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return rowData.devSSID[0].deviceName;
                            }
                        },
                        {
                            "dataIndex": "operation",
                            "text": "操作",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<span class="format-a">' +
                                    '<a href="javascript:;" ms-click="openDeviceDialog($outer.el)">修改名称</a>' +
                                    '</span>';
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        _.extend(requestData, {
                            "pageSize": 1000
                        });
                        util.c2s({
                            "url": app.BASE_PATH + "controller/device/getDeviceDetail.do",
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
                //添加网址dialog
                vm.$editDeviceDialogOpts = {
                    "dialogId": editDeviceDialogId,
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
                        var formVm = avalon.getVm(deviceFormId);
                        formVm.reset();
                        formVm.deviceName = "";
                        formVm.managerPsd = "";
                        avalon.getVm(gridId).unselectById('all');
                    },
                    "submit": function () {
                        var requestData,
                            dialogVm = avalon.getVm(editDeviceDialogId),
                            formVm = avalon.getVm(deviceFormId),
                            gridVm = avalon.getVm(gridId);
                        if (formVm.validate()) {
                            requestData = formVm.getFormData();
                            _.extend(requestData,{'deviceId':gridVm.getSelectedData()[0].id,'devSSIDid':gridVm.getSelectedData()[0].devSSID[0].id});
                            util.c2s({
                                "url": app.BASE_PATH + "controller/device/updateDeviceDetail.do",
                                "data": requestData,
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        avalon.getVm(gridId).load();
                                        dialogVm.close();
                                    }
                                }
                            });
                        }
                    }
                };
                //添加网址和APP Form
                vm.$editDeviceFormOpts = {
                    "formId": deviceFormId,
                    "field": [
                        {
                            "selector": ".device-name",
                            "name": "deviceName",
                            "rule": ["noempty", function (val, rs) {
                                if (rs[0] !== true) {
                                    return "设备名称不能为空";
                                } else {
                                    return true;
                                }
                            }]
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
                    "deviceName": "",
                    "managerPsd": ""
                };
                //打开弹层
                vm.openDeviceDialog = function (el) {
                    var dialogVm = avalon.getVm(editDeviceDialogId), formVm = avalon.getVm(deviceFormId), gridVm = avalon.getVm(gridId);
                    gridVm.selectById(el.id);
                    dialogVm.title = '设备名称管理';
                    formVm.deviceName = el.devSSID[0].deviceName;
                    dialogVm.open();
                };
            });
            avalon.scan(pageEl[0]);
            avalon.getVm(gridId).load();
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var editDeviceDialogId = pageName + '-edit-device-dialog',
                gridId = pageName + '-grid',
                deviceFormId = pageName + '-device-form';
            $(avalon.getVm(editDeviceDialogId).widgetElement).remove();
            $(avalon.getVm(gridId).widgetElement).remove();
            $(avalon.getVm(deviceFormId).widgetElement).remove();
        }
    });

    return pageMod;
});
