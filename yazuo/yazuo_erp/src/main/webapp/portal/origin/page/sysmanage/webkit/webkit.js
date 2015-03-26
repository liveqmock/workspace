/**
 * webkit管理
 */
define(['avalon', 'util', 'moment', '../../../widget/pagination/pagination', '../../../widget/dialog/dialog', '../../../widget/form/form', '../../../widget/form/select', '../../../widget/grid/grid', '../../../widget/uploader/uploader'], function (avalon, util, moment) {
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

            var gridId = pageName + '-grid',
                addDialogId = pageName + '-add-dialog',
                addFormId = pageName + '-add-form',
                uploaderId = pageName + '-uploader',
                versionNum1Id = pageName + '-version-num-1',
                versionNum2Id = pageName + '-version-num-2',
                versionNum3Id = pageName + '-version-num-3';
            var loginUserData = erp.getAppData('user');

            var pageVm = avalon.define(pageName, function (vm) {
                vm.permission = {};
                vm.$gridOpts = {
                    "gridId": gridId,
                    "recordUnit": "个版本",
                    "tableLayout": "fixed",
                    "columns": [{
                        "dataIndex": "versionNum",
                        "text": "版本号"
                    }, {
                        "dataIndex": "updateTime",
                        "text": "更新日期",
                        "renderer": function (v) {
                            return moment(v).format('YYYY-MM-DD HH:mm:ss');
                        }
                    }, {
                        "dataIndex": "description",
                        "text": "更新说明",
                        "width": 420
                    }, {
                        "dataIndex": "fileSize",
                        "text": "文件大小（M）",
                        "renderer": function (v) {
                            return (v / 1024 / 1024).toFixed(2);
                        }
                    }, {
                        "dataIndex": "operation",
                        "text": "操作",
                        "html": true,
                        "textAlign": "left",
                        "renderer": function (v, rowData) {
                            return '<a href="' + rowData["downloadLink"] + '" class="download-l">下载应用</a>&nbsp;&nbsp;&nbsp;&nbsp;'+
                            '<a href="javascript:;" class="remove-l" ms-click="removeGridItem"'+
                            ' data-record-id="' + rowData.id +'">删除</a></td>';
                        }
                    }],
                    "onLoad": function (requestData, callback) {
                        util.c2s({
                            "url": erp.BASE_PATH + "webkit/list.do",
                            "data": requestData,
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    callback.call(this, data);
                                }
                            }
                        });
                    },
                    "removeGridItem": function () {
                        var meEl = $(this),
                            gridVm = avalon.getVm(gridId),
                            recordId = meEl.data('recordId');
                        //选中当前行，取消其他行选中
                        gridVm.selectById(recordId, true);
                        //删除对应职位
                        gridVm._removeRecord();
                    },
                    "_removeRecord": function () {
                        var ids,
                            requestData,
                            url,
                            gridVm = avalon.getVm(gridId),
                            selectedData = gridVm.getSelectedData();
                        if (selectedData.length === 0) {
                            util.alert({
                                "content": "请先选中记录"
                            });
                        } else {
                            ids = _.map(selectedData, function (itemData) {
                                return itemData.id;
                            });
                            url = erp.BASE_PATH + 'webkit/deleteWebkit.do';
                            requestData = {
                                "id" : ids[0]
                            };
                            util.confirm({
                                "content": "确定要删除选中的版本吗？",
                                "onSubmit": function () {
                                    util.c2s({
                                        "url": url,
                                        "type": "post",
                                        dataType:"json",
                                        //contentType : 'application/json',
                                        "data": requestData,
                                        "success": function (responseData) {
                                            if (responseData.flag) {   //删除成功，重刷grid
                                                gridVm.load();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }
                };
                vm.$addDialogOpts = {
                    "dialogId": addDialogId,
                    "title": "上传新版应用",
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
                    "onOpen": function () {},
                    "onClose": function () {
                        //清除验证信息
                        var formVm = avalon.getVm(addFormId);
                        formVm.reset();
                        formVm.originalFileName = "",
                        formVm.attachmentName = "",
                        formVm.attachmentSize = 0,
                        avalon.getVm(versionNum1Id).select(0, true);
                        avalon.getVm(versionNum2Id).select(0, true);
                        avalon.getVm(versionNum3Id).select(0, true);
                    },
                    "submit": function (evt) {
                        var requestData,
                            url,
                            dialogVm = avalon.getVm(addDialogId),
                            formVm = avalon.getVm(addFormId),
                            gridVm = avalon.getVm(gridId),
                            uploaderVm = avalon.getVm(uploaderId),
                            exer,
                            id;
                        if (formVm.validate()) {
                            url = erp.BASE_PATH + 'webkit/saveWebkit.do';
                            exer = function () {
                                requestData = formVm.getFormData();
                                //参数调整
                                requestData["versionNum"] = 'v.' + avalon.getVm(versionNum1Id).selectedValue + '.' +
                                    avalon.getVm(versionNum2Id).selectedValue + '.' +
                                    avalon.getVm(versionNum3Id).selectedValue;
                                //发送后台请求，编辑或添加
                                util.c2s({
                                    "url": url,
                                    "type": "post",
                                    "contentType" : 'application/json',
                                    "data": JSON.stringify(requestData),
                                    "success": function (responseData) {
                                        if (responseData.flag) {
                                            util.alert({
                                                "iconType": "success",
                                                "content": responseData.message,
                                                "onSubmit":function () {
                                                    dialogVm.close();
                                                    gridVm.load(1); //跳到第一页
                                               }
                                            });
                                        }
                                    }
                                });
                            };
                            if (uploaderVm.uploading) {
                                util.showGlobalMask();  //打开全局遮罩
                                uploaderVm.$unwatch('uploading');
                                uploaderVm.$watch('uploading', function (val) {
                                    if (!val) {  //全部上传成功
                                        util.hideGlobalMask();  //隐藏全局遮罩
                                        exer();
                                        uploaderVm.$unwatch('uploading');
                                    }
                                });
                            } else {    //直接上传
                                exer();
                            }
                        }
                    }
                };
                vm.$addFormOpts = {
                    "formId": addFormId,
                    "field": [/*{
                        "selector": ".version-tail",
                        "name": "versionNum",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "版本尾号不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }, */{
                        "selector": ".version-uploader",
                        "name": "attachmentVO",
                        "getValue": function () {
                            var formVm = avalon.getVm(addFormId);
                            return {
                                "attachmentName": formVm.attachmentName,
                                "originalFileName": formVm.originalFileName,
                                "attachmentSize": formVm.attachmentSize
                            };
                        },
                        "rule": function () {
                            var formVm = avalon.getVm(addFormId);
                            if (!formVm.attachmentName.length) {
                                return "上传版本文件不能为空";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".update-desc",
                        "name": "description",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "更新说明不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": '[name="page_sysmanage_webkit_add_is_forced_upgrade"]',
                        "name": "isForcedUpgrade"
                    }],
                    "originalFileName": "",
                    "attachmentName": "",
                    "attachmentSize": 0,
                    "$uploaderOpts": {    //上传配置
                        "uploaderId": uploaderId,
                        "uploadifyOpts": {
                            "uploader": erp.BASE_PATH + 'webkit/uploadImage.do',
                            "formData": {
                                "sessionId": loginUserData.sessionId
                            },
                            "fileObjName": "myfiles",
                            "multi": false,
                            "fileTypeDesc": "Webkit文件(*.*)",
                            "fileTypeExts": "*.*",
                            "fileSizeLimit": "100MB",
                            "width": 88,
                            "height": 30,
                            "onSelect" : function(file) {   //清除上一次的上传信息
                                var uploaderVm = avalon.getVm(uploaderId);
                                if (uploaderVm.uploadList.size()) {
                                    uploaderVm.removeFile(uploaderVm.uploadList[0].id);
                                }
                            }
                        },
                        "onSuccessResponseData": function (reponseText, file) {
                            var responseData = JSON.parse(reponseText),
                                data;
                            var formVm = avalon.getVm(addFormId);
                            if (responseData.flag) {
                                data = responseData.data[0];
                                formVm.originalFileName = data.originalFileName;
                                formVm.attachmentName = data.fileName;
                                formVm.attachmentSize = data.fileSize;
                            } else {
                                util.alert({
                                    "content": responseData.message,
                                    "iconType": "error"
                                });
                            }
                        }
                    },
                    "$versionNum1Opts": {
                        "selectId": versionNum1Id,
                        "options": _.map(_.range(1, 31), function (num) {
                            return {
                                "text": num,
                                "value": num
                            };
                        })
                    },
                    "$versionNum2Opts": {
                        "selectId": versionNum2Id,
                        "options": _.map(_.range(0, 31), function (num) {
                            return {
                                "text": num,
                                "value": num
                            };
                        })
                    },
                    "$versionNum3Opts": {
                        "selectId": versionNum3Id,
                        "options": _.map(_.range(0, 31), function (num) {
                            return {
                                "text": num,
                                "value": num
                            };
                        })
                    }
                };
                vm.openAdd = function () {
                    avalon.getVm(addDialogId).open();
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
            var gridId = pageName + '-grid',
                addDialogId = pageName + '-add-dialog',
                addFormId = pageName + '-add-form',
                uploaderId = pageName + '-uploader',
                versionNum1Id = pageName + '-version-num-1',
                versionNum2Id = pageName + '-version-num-2',
                versionNum3Id = pageName + '-version-num-3';
            $(avalon.getVm(gridId).widgetElement).remove();
            $(avalon.getVm(versionNum1Id).widgetElement).remove();
            $(avalon.getVm(versionNum2Id).widgetElement).remove();
            $(avalon.getVm(versionNum3Id).widgetElement).remove();
            $(avalon.getVm(uploaderId).widgetElement).remove();
            $(avalon.getVm(addFormId).widgetElement).remove();
            $(avalon.getVm(addDialogId).widgetElement).remove();
        }
    });

    return pageMod;
});
