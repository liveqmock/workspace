/**
 * 管理员角色的课程管理
 */
define(['avalon', 'util', '../../../widget/pagination/pagination', '../../../widget/dialog/dialog', '../../../widget/form/form', '../../../widget/grid/grid', '../../../module/positionselector/positionselector'], function (avalon, util) {

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

            var addEditDialogId = pageName + '-add-edit-dialog',
                addEditFormId = pageName + '-add-edit-form',
                gridId = pageName + '-grid',
                positionselectorId = pageName + '-positionselector';

            var pageVm = avalon.define(pageName, function (vm) {
                vm.permission = {
                    "magCourse": util.hasPermission('mag_course'),     //添加课程/修改课程/删除课程
                    "viewCourse": util.hasPermission('view_course'),   //查看课程详情
                    "finalExamRule": util.hasPermission('final_exam_rule')  //期末考试规则
                };
                vm.$gridOpts = {
                    "gridId": gridId,
                    "recordUnit": "种课程",
                    "columns": [{
                        "dataIndex": "course_name",
                        "text": "课程名称"
                    }, {
                        "dataIndex": "time_limit",
                        "text": "课程限时（小时）"
                    }, {
                        "dataIndex": "count_is_required",
                        "text": "课件安排",
                        "renderer": function (v, rowData) {
                            return '必修课' + rowData["count_is_required"] + '节,选修课' + rowData["count_is_not_required"] + '节';
                        }
                    }, {
                        "dataIndex": "operation",
                        "text": "课件安排",
                        "html": true,
                        "renderer": function (v, rowData) {
                            return '<a href="#/study/coursemanage/coursedetail/' + rowData.id + '/' + rowData.course_name + '"' +
                                ' class="edit-l" ms-if="permission.viewCourse" data-course-id="' + rowData.id +'">查看课程详情</a>&nbsp;&nbsp;&nbsp;&nbsp;'+
                            '<a href="javascript:;" class="ref-position-l"'+
                                ' ms-if="permission.finalExamRule"'+
                                ' ms-click="openPositonRef"' +
                                ' data-course-id="' + rowData.id +'">关联职位</a>&nbsp;&nbsp;&nbsp;&nbsp;'+
                            '<a href="#/study/coursemanage/examrule/' + rowData.id + '/' + rowData.course_name + '" class="edit-l"'+
                                ' ms-if="permission.finalExamRule"'+
                                ' data-course-id="' + rowData.id +'">期末考试规则</a>&nbsp;&nbsp;&nbsp;&nbsp;'+
                            '<a href="javascript:;" class="edit-l" ms-click="openEdit"'+
                                ' ms-if="permission.magCourse"'+
                                ' data-course-id="' + rowData.id +'" data-course-name="' + rowData.course_name + '"' +
                                ' data-time-limit="' + rowData.time_limit + '">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;'+
                            '<a href="javascript:;" class="remove-l" ms-if="permission.magCourse" ms-click="removeGridItem"'+
                            ' data-course-id="' + rowData.id +'">删除</a></td>';
                        }
                    }],
                    "onLoad": function (requestData, callback) {
                        util.c2s({
                            "url": erp.BASE_PATH + "course/getCourseInfo.do",
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
                    "openEdit": function () {
                        var dialogVm = avalon.getVm(addEditDialogId),
                            formVm = avalon.getVm(addEditFormId),
                            gridVm = avalon.getVm(gridId),
                            meEl = $(this),
                            courseId = meEl.data('courseId') + "",
                            courseName = meEl.data('courseName'),
                            timeLimit = meEl.data('timeLimit');
                        dialogVm.title = '编辑课程';
                        formVm.courseName = courseName;
                        formVm.timeLimit = timeLimit;
                        vm.addEditState = 'edit';    //设置状态
                        //选中当前行，取消其他行选中
                        gridVm.selectById(courseId, true);
                        dialogVm.open();
                    },
                    "openPositonRef": function () {
                        var meEl = $(this),
                            gridVm = avalon.getVm(gridId),
                            psVm = avalon.getVm(positionselectorId),
                            courseId = meEl.data('courseId');
                        //选中当前行，取消其他行选中
                        gridVm.selectById(courseId, true);
                        //打开职位选择弹框
                        psVm.open();
                    },
                    "removeGridItem": function () {
                        var meEl = $(this),
                            gridVm = avalon.getVm(gridId),
                            courseId = meEl.data('courseId');
                        //选中当前行，取消其他行选中
                        gridVm.selectById(courseId, true);
                        //删除对应职位
                        gridVm._removeCourse();
                    },
                    "batchRemoveGridItem": function () {
                        avalon.getVm(gridId)._removeCourse();
                    },
                    "_removeCourse": function () {
                        var ids,
                            requestData,
                            url,
                            gridVm = avalon.getVm(gridId),
                            selectedData = gridVm.getSelectedData();
                        if (selectedData.length === 0) {
                            util.alert({
                                "content": "请先选中课程"
                            });
                        } else {
                            ids = _.map(selectedData, function (itemData) {
                                return itemData.id;
                            });
                            url = erp.BASE_PATH + 'course/deleteCourse.do';
                            requestData = JSON.stringify({
                                idString : ids
                            });
                            util.confirm({
                                "content": "确定要删除选中的课程吗？",
                                "onSubmit": function () {
                                    util.c2s({
                                        "url": url,
                                        "type": "post",
                                        dataType:"json",
                                        contentType : 'application/json',
                                        "data": requestData,
                                        "success": function (responseData) {
                                            if (responseData.flag == 1) {   //删除成功，重刷grid
                                                gridVm.load();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }
                };
                vm.$psOpts = {
                    "positionselectorId": positionselectorId,
                    "multi": true,
                    "onListRequestData": function (requestData) {
                        var gridVm = avalon.getVm(gridId),
                            selectedData = gridVm.getSelectedData();
                        return _.extend(requestData, {
                            "courseId": selectedData[0].id / 1
                        });
                    },
                    "onListReady": function (positionListVm) {
                        _.each(positionListVm.$model, function (itemData, i) {
                            if (itemData.isChoosed) {
                                positionListVm.set(i, {
                                    "isSelected": true
                                });
                            }
                        });
                    },
                    "onSubmit": function (result) {
                        var gridVm = avalon.getVm(gridId),
                            selectedData = gridVm.getSelectedData();
                        if (result.length) {
                            util.c2s({
                                "url": erp.BASE_PATH + 'traPositionCourse/saveTraPositionCourse.do',
                                "type": "post",
                                dataType:"json",
                                contentType : 'application/json',
                                "data": JSON.stringify(_.map(result, function (itemData) {
                                    return {
                                        "positionId": itemData.id / 1,
                                        "courseId": selectedData[0].id / 1
                                    };
                                })),
                                "success": function (responseData) {

                                }
                            });
                        } else {
                            util.alert({
                                "iconType": "error",
                                "content": "请选择对应的职位"
                            });
                        }

                    }
                };
                vm.addEditState = '';
                vm.$addEditDialogOpts = {
                    "dialogId": addEditDialogId,
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
                        var formVm = avalon.getVm(addEditFormId);
                        formVm.reset();
                        formVm.courseName = '';
                        formVm.timeLimit = '';
                    },
                    "submit": function (evt) {
                        var requestData,
                            url,
                            dialogVm = avalon.getVm(addEditDialogId),
                            formVm = avalon.getVm(addEditFormId),
                            gridVm = avalon.getVm(gridId),
                            addEditState = vm.addEditState,
                            id;
                        if (formVm.validate()) {
                            requestData = formVm.getFormData();
                            //编辑态附加参数
                            if (addEditState == 'edit') {
                                id = gridVm.getSelectedData()[0].id;
                                _.extend(requestData, {
                                    "id": id
                                });
                                url = erp.BASE_PATH + 'course/updateCourse.do';
                            } else {
                                url = erp.BASE_PATH + 'course/saveCourse.do';
                            }

                            //发送后台请求，编辑或添加
                            util.c2s({
                                "url": url,
                                "type": "post",
                                "data": requestData,
                                "success": function (responseData) {
                                    if (responseData.flag == 1) {
                                        if (addEditState == 'edit') {   //编辑状态前端更新数据
                                            _.some(gridVm.gridData.$model, function (itemData, i) {
                                                if (itemData.id == id) {
                                                    gridVm.gridData.set(i, {
                                                        "course_name": requestData["courseName"],
                                                        "time_limit": requestData["timeLimit"]
                                                    });
                                                    return true;
                                                }
                                            });
                                        } else { //添加状态直接刷新数据
                                            gridVm.load();

                                        }
                                        //关闭弹框
                                        dialogVm.close();
                                    }
                                }
                            });
                        }
                    }
                };
                vm.$addEditFormOpts = {
                    "formId": addEditFormId,
                    "field": [{
                        "selector": ".course-name",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "课程名称不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".time-limit",
                        "name": "timeLimit",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "课程时限不能为空";
                            } else {
                                if (!/^-?\d+(\.\d{1,1})?$/.test(val)) { //最多保留一位小数
                                    return "请输入有效的课程时限";
                                }
                                if (parseFloat(val, 10) <= 0 || val.slice(0, 2) === '00') {
                                    return "请输入有效的课程时限";
                                }
                                return true;
                            }
                        }]
                    }],
                    "courseName": "",
                    "timeLimit":""
                };
                vm.openAdd = function () {
                    var dialogVm = avalon.getVm(addEditDialogId),
                        formVm = avalon.getVm(addEditFormId),
                        gridVm = avalon.getVm(gridId);
                    dialogVm.title = '添加课程';
                    formVm.courseName = '';
                    formVm.timeLimit = '';
                    vm.addEditState = 'add';    //设置状态
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
            var addEditDialogId = pageName + '-add-edit-dialog',
                addEditFormId = pageName + '-add-edit-form',
                gridId = pageName + '-grid',
                positionselectorId = pageName + '-positionselector';
            $(avalon.getVm(gridId).widgetElement).remove();
            $(avalon.getVm(addEditFormId).widgetElement).remove();
            $(avalon.getVm(addEditDialogId).widgetElement).remove();
            $(avalon.getVm(positionselectorId).widgetElement).remove();
        }
    });

    return pageMod;
});
