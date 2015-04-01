/**
 * 管理员角色的课程管理
 */
define(['avalon', 'util', 'json', 'moment', '../../../widget/pagination/pagination', '../../../widget/dialog/dialog', '../../../widget/form/form', '../../../widget/grid/grid', '../../../widget/form/select.js', '../../../module/targetselector/targetselector.js'], function (avalon, util, JSON, moment) {
    var win = this,
        erp = win.erp,
        pageMod = {};
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName) {

            var dialogId = pageName + '-dialog',
                formId = pageName + '-form',
                tmplTypeId = pageName + '-tmpl-type',
                gridId = pageName + '-grid',
                targetselectorId = pageName + '-targetselector';

            var pageVm = avalon.define(pageName, function (vm) {
                vm.permission = {
                    "add": util.hasPermission('add_sms_template_189'),
                    "update": util.hasPermission('update_sms_template_190'),
                    "delete": util.hasPermission('delete_sms_template_191')
                };
                vm.tmplName = "";
                vm.$gridOpts = {
                    "gridId": gridId,
                    "recordUnit": "个模板",
                    "disableCheckbox": true, 
                    "columns": [{
                        "dataIndex": "title",
                        "text": "模板名称"
                    }, {
                        "dataIndex": "tplType",
                        "text": "模板类型",
                        "renderer": function (v, rowData) {
                            return rowData.tplTypeCn;
                        }
                    }, {
                        "dataIndex": "createdTime",
                        "text": "添加时间",
                        "renderer": function (v) {
                            return moment(v).format('YYYY-MM-DD HH:mm:ss');
                        }
                    }, {
                        "dataIndex": "operation",
                        "text": "操作",
                        "html": true,
                        "renderer": function (v, rowData) {
                            return '<a href="javascript:;" class="view-l"'+
                                ' ms-click="openView"' +
                                ' data-tmpl-id="' + rowData.id +'">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;'+
                            '<span ms-if="permission.update"><a href="javascript:;" class="edit-l" ms-click="openEdit"'+
                                ' data-tmpl-id="' + rowData.id + '">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;</span>'+
                            '<a href="javascript:;" class="remove-l" ms-if="permission.delete" ms-click="removeTmpl"'+
                            ' data-tmpl-id="' + rowData.id +'">删除</a></td>';
                        }
                    }],
                    "onLoad": function (requestData, callback) {
                        util.c2s({
                            "url": erp.BASE_PATH + "smsTemplate/search.do",
                            "data": JSON.stringify(_.extend(requestData, {
                                "content": vm.tmplName
                            })),
                            "contentType": 'application/json',
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data.resultSet;
                                    callback.call(this, data);
                                }
                            }
                        });
                    },
                    "openEdit": function () {
                        var dialogVm = avalon.getVm(dialogId),
                            gridVm = avalon.getVm(gridId),
                            meEl = $(this),
                            id = meEl.data('tmplId') + "";
                        //选中当前行，取消其他行选中
                        gridVm.selectById(id, true);
                        dialogVm.title = '编辑模板';
                        vm.dialogState = 'edit';    //设置状态
                        dialogVm.open();
                    },
                    "openView": function () {
                        var dialogVm = avalon.getVm(dialogId),
                            gridVm = avalon.getVm(gridId),
                            meEl = $(this),
                            id = meEl.data('tmplId') + "";
                        //选中当前行，取消其他行选中
                        gridVm.selectById(id, true);
                        dialogVm.title = '查看模板';
                        vm.dialogState = 'view';    //设置状态
                        dialogVm.open();
                    },
                    "removeTmpl": function () {
                        var gridVm = avalon.getVm(gridId),
                            meEl = $(this),
                            id = meEl.data('tmplId') + "";
                        //选中当前行，取消其他行选中
                        gridVm.selectById(id, true);
                        util.confirm({
                            "iconType": "info",
                            "content": "确定要删除选中的模板吗？",
                            "onSubmit": function () {
                                util.c2s({
                                    "url": erp.BASE_PATH + 'smsTemplate/delete.do',
                                    "type": "post",
                                    "data": JSON.stringify({
                                        "id": id
                                    }),
                                    "contentType": 'application/json',
                                    "success": function (responseData) {
                                        if (responseData.flag) {
                                            util.alert({
                                                "iconType": "success",
                                                "content": responseData.message,
                                                "onSubmit": function () {
                                                    gridVm.load();
                                                }
                                            });
                                        } else {
                                            util.alert({
                                                "iconType": "error",
                                                "content": responseData.message
                                            });
                                        }
                                    }
                                });
                            }
                        });
                    }
                };
                vm.search = function () {
                    var gridVm = avalon.getVm(gridId);
                    gridVm.load(1);
                };
                vm.$targetSelectorOpts = {
                    "targetselectorId": targetselectorId,
                    "title": "发送对象",
                    "onSubmit": function (selectedData) {
                        var formVm = avalon.getVm(formId);
                        formVm.sendTarget = selectedData.text;
                        formVm.$sendTargetValue = selectedData.value;
                    },
                    "onOpen": function () {
                        var formVm = avalon.getVm(formId),
                            selectedData = formVm.$sendTargetValue;
                        //设置发送对象
                        this.prepareData(function () {
                            if (selectedData) {
                                selectedData = formVm.$sendTargetValue;
                                this.setSelectedData({
                                    "position": selectedData.position,
                                    "user": selectedData.user,
                                    "department": selectedData.department,
                                    "contacter": selectedData.contacter,
                                    "manager": selectedData.manager
                                });
                            }
                            this.updateView();
                        });
                    },
                    "onClose": function () {
                        this.clearData();
                    }
                };
                vm.selectSendTarget = function () {
                    var targetselectorVm = avalon.getVm(targetselectorId);
                    targetselectorVm.open();
                };
                vm.dialogState = '';
                vm.$dialogOpts = {
                    "dialogId": dialogId,
                    "width": 700,
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                            footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                '<div class="ui-dialog-btns">' +
                                    '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">确&nbsp;定</button>' +
                                    '<span class="separation"></span>' +
                                    '<button type="button" class="cancel-btn ui-dialog-btn" ms-if="dialogState !== \'view\'" ms-click="close">取&nbsp;消</button>' +
                                '</div>' +
                            '</div>';
                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "onOpen": function () {
                        var tmplTypeVm = avalon.getVm(tmplTypeId),
                            formVm = avalon.getVm(formId),
                            gridVm = avalon.getVm(gridId),
                            targetselectorVm = avalon.getVm(targetselectorId);
                        util.c2s({
                            "url": erp.BASE_PATH + 'smsTemplate/getTplTypes.do',
                            "type": "post",
                            "contentType": 'application/json',
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    if (vm.dialogState === "add") {
                                        tmplTypeVm.setOptions(data.tplTypes, 0);
                                    } else if (vm.dialogState === "edit" || vm.dialogState === "view") {
                                        tmplTypeVm.setOptions(data.tplTypes, -1);
                                        //获取此模板信息
                                        util.c2s({
                                            "url": erp.BASE_PATH + 'smsTemplate/get.do',
                                            "type": "post",
                                            "contentType": 'application/json',
                                            "data": JSON.stringify({
                                                "id": gridVm.getSelectedData()[0].id 
                                            }),
                                            "success": function (responseData) {
                                                var data;
                                                if (responseData.flag) {
                                                    data = responseData.data;
                                                    tmplTypeVm.selectValue(data.tplType);
                                                    formVm.tmplName = data.title;
                                                    formVm.tmplTypeName = tmplTypeVm.selectedText;
                                                    formVm.sendContent = data.content;
                                                    //设置发送对象
                                                    targetselectorVm.prepareData(function () {
                                                        var selectedData;
                                                        this.setSelectedData({
                                                            "position": data.positionIds,
                                                            "user": data.userIds,
                                                            "department": data.groupIds,
                                                            "contacter": data.roleTypes,
                                                            "manager": data.userTypes
                                                        });
                                                        this.updateView();
                                                        selectedData = this.getSelectedData();
                                                        formVm.sendTarget = selectedData.text; 
                                                        formVm.$sendTargetValue = selectedData.value;
                                                    });
                                                }
                                            }
                                        }, {
                                            "mask": false
                                        });
                                    }
                                }
                            }
                        }, {
                            "mask": false
                        });
                    },
                    "onClose": function () {
                        //清除验证信息
                        var formVm = avalon.getVm(formId);
                        var tmplTypeVm = avalon.getVm(tmplTypeId);
                        formVm.reset();
                        tmplTypeVm.setOptions([], -1);
                        formVm.tmplName = "";
                        formVm.tmplTypeName = "";
                        formVm.sendTarget = "";
                        formVm.sendContent = "";
                        formVm.$sendTargetValue = null;
                    },
                    "submit": function (evt) {
                        var requestData,
                            url,
                            dialogVm = avalon.getVm(dialogId),
                            formVm = avalon.getVm(formId),
                            gridVm = avalon.getVm(gridId),
                            targetselectorVm = avalon.getVm(targetselectorId),
                            dialogState = vm.dialogState,
                            id,
                            sendTargetValue;
                        if (dialogState === 'view') {
                            //关闭弹框
                            dialogVm.close();
                            return;
                        }
                        if (formVm.validate()) {
                            requestData = formVm.getFormData();
                            //编辑态附加参数
                            if (dialogState == 'edit') {
                                id = gridVm.getSelectedData()[0].id;
                                _.extend(requestData, {
                                    "id": id
                                });
                                url = erp.BASE_PATH + 'smsTemplate/update.do';
                            } else {
                                url = erp.BASE_PATH + 'smsTemplate/add.do';
                            }
                            //附加发送目标参数
                            sendTargetValue = formVm.$sendTargetValue;
                            requestData.roleTypes = sendTargetValue.contacter || [];
                            requestData.userTypes = sendTargetValue.manager || [];
                            requestData.groupIds = sendTargetValue.department || [];
                            requestData.userIds = sendTargetValue.user || [];
                            requestData.positionIds = sendTargetValue.position || [];
                            delete requestData.sendTarget;
                            //发送后台请求，编辑或添加
                            util.c2s({
                                "url": url,
                                "type": "post",
                                "data": JSON.stringify(requestData),
                                "contentType": 'application/json',
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        if (dialogState == 'edit' || dialogState === "add") {   //编辑状态前端更新数据
                                            gridVm.load();
                                        } else { //添加状态直接刷新数据
                                        }
                                        //关闭弹框
                                        dialogVm.close();
                                    }
                                }
                            });
                        }
                    }
                };
                vm.$formOpts = {
                    "formId": formId,
                    "field": [{
                        "selector": ".tmpl-type",
                        "name": "tplType",
                        "getValue": function () {
                            return avalon.getVm(tmplTypeId).selectedValue;
                        },
                        "rule": function (val) {
                            if (!val) {
                                return "模板类型不能为空";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".tmpl-name",
                        "name": "title",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "模版名称不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".send-target",
                        "name": "sendTarget",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "发送对象不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".send-content",
                        "name": "content",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "正文不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }],
                    "tmplName": "",
                    "tmplTypeName": "",
                    "sendTarget": "",
                    "sendContent":"",
                    "$sendTargetValue": null
                };
                vm.$tmplTypeOpts = {
                    "selectId": tmplTypeId
                };
                vm.openAdd = function () {
                    var dialogVm = avalon.getVm(dialogId),
                        formVm = avalon.getVm(formId),
                        gridVm = avalon.getVm(gridId);
                    dialogVm.title = '添加模板';
                    vm.dialogState = 'add';    //设置状态
                    //清除选中状态
                    gridVm.unselectById('all');
                    dialogVm.open();
                };

            });

            avalon.scan(pageEl[0],[pageVm]);
            //更新grid数据
            avalon.getVm(gridId).load();
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var dialogId = pageName + '-dialog',
                formId = pageName + '-form',
                tmplTypeId = pageName + '-tmpl-type',
                gridId = pageName + '-grid',
                targetselectorId = pageName + '-targetselector';
            $(avalon.getVm(gridId).widgetElement).remove();
            $(avalon.getVm(tmplTypeId).widgetElement).remove();
            $(avalon.getVm(formId).widgetElement).remove();
            $(avalon.getVm(dialogId).widgetElement).remove();
            $(avalon.getVm(targetselectorId).widgetElement).remove();
        }
    });

    return pageMod;
});
