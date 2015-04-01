/**
 * 学员管理
 */
define(['avalon', 'util', 'moment', '../../../module/bindselector/bindselector', '../../../widget/pagination/pagination', '../../../widget/dialog/dialog', '../../../widget/form/form', '../../../widget/form/select', '../../../widget/calendar/calendar'], function (avalon, util, moment) {
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
            var paginationId = pageName + '-pagination',
                allocateId = pageName + "-allocate",
                positionId = pageName + "-position",
                scheduleId = pageName + "-schedule",
                bindId = pageName + "-bind",
                delayCourseId = pageName + '-delay-course';
            var loginUserData = erp.getAppData('user');

            var pageVm = avalon.define(pageName, function (vm) {
                vm.permission = {
                    "teacher": util.hasPermission('teacher_item'),     //老师权限，默认权限
                    //"charge": false,
                    "charge": util.hasPermission('t_incharge_item'),   //班主任权限，高于老师权限
                    "addStudent": util.hasPermission('add_student'),    //添加学员
                    "viewStudentDetail": util.hasPermission('view_student_detail'), //查看详情
                    "unbindTSRel": util.hasPermission('unbind_t_s_rel'),    // 解除师生关系
                    "allocateT": util.hasPermission('allocate_t'),  //分配老师
                    "cDelay": util.hasPermission('c_delay')   //课程延时
                };
                //已分配/未分配列表：只有为班主任的时候显示
                vm.$allocateOpts = {
                    "selectId": allocateId,
                    "options": [/*{
                        "text": "所有学员",
                        "value": -1
                     }, */{
                        "text": "已分配老师",
                        "value": 1
                     }, {
                        "text": "未分配老师",
                        "value": 0
                     }],
                    "selectedIndex": 0,
                    "onSelect": function (selectedValue) {
                        if (!selectedValue) {   //选择未分配老师，隐藏进度下拉表
                            avalon.getVm(scheduleId).select(0);
                            vm.scheduleIsVisible = false;
                        } else {
                            vm.scheduleIsVisible = true;
                        }
                    }
                };
                vm.scheduleIsVisible = true;
                //进度列表
                vm.$scheduleOpts = {
                    "selectId": scheduleId,
                    "options": [{
                        "text": "所有进度",
                        "value": ""
                    }],
                    "selectedIndex": 0,
                    "onSelect": function (val, text, selectedIndex) {

                    }
                };
                //职位列表
                vm.$positionOpts = {
                    "selectId": positionId,
                    "options": [{
                        "text": "所有职位",
                        "value": 0
                    }],
                    "selectedIndex": 0
                };
                vm.loginUserId = loginUserData.id;
                vm.userName = "";
                vm.tel = "";
                vm.gridData = [];
                vm.todoNum = 0;
                vm.gridTotalSize = 0;
                vm.gridAllChecked = false;
                vm.gridSelected = [];
                vm.gridCheckAll = function () {
                    if (this.checked) {
                        vm.gridSelected = _.map(vm.gridData.$model, function (itemData) {
                            return itemData.studentId + "";
                        });
                    } else {
                        vm.gridSelected.clear();
                    }
                };
                /**
                 * 表格左下脚一个全选的快捷方式
                 */
                vm.scCheckAll = function () {
                    vm.gridSelected = _.map(vm.gridData.$model, function (itemData) {
                        return itemData.studentId + "";
                    });
                };
                vm.search = function () {
                    updateGrid(true);
                };
                vm.$paginationOpts = {
                    "paginationId": paginationId,
                    "total": 0,
                    "onJump": function (evt, vm) {
                        updateGrid();
                    }
                };
                vm.$allocationOpts = {
                    "bindselectorId": bindId,
                    "multi": vm.permission.charge ? false : true,
                    "listPath": vm.permission.charge ? "studentManagement/queryTeacherList.do" : "studentManagement/queryStudentListToBeAllocated.do",
                    //"listPath": "studentManagement/queryStudentListToBeAllocated.do",
                    "onListReady": function (listVm) {
                        var selectedStudentId = vm.gridSelected[0],
                            selectedStudentData = _.find(vm.gridData.$model, function (itemData) {
                                return itemData.studentId == selectedStudentId;
                            });
                        //根据studentId或teacherId自动选中对应的行
                        if (vm.permission.charge) { //班主任权限用于分配老师
                            _.some(listVm.$model, function (itemData, i) {
                                if (itemData.teacherId == selectedStudentData.teacherId) {
                                    listVm.set(i, {
                                        "isSelected": true
                                    });
                                    return true;
                                }
                            });
                        }
                    },
                    "onSubmit": function (result) {
                        var that = this,
                            requestData;
                        var selectedStudentId,
                            selectedStudentData;
                        if (result.length) {
                            selectedStudentId = vm.gridSelected[0];
                            selectedStudentData = _.find(vm.gridData.$model, function (itemData) {
                                return itemData.studentId == selectedStudentId;
                            });
                            requestData = {
                                "teacherId": vm.permission.charge ? result[0].teacherId : loginUserData.id, //默认当前登录的userID作为id
                                "studentIdList": vm.permission.charge ? [selectedStudentData.studentId] : _.map(result, function (itemData) {
                                    return itemData.id;
                                })
                                /*"courseId": avalon.getVm(this.courseSelectId).selectedValue*/
                            };
                            util.c2s({
                                "url": erp.BASE_PATH + 'studentManagement/saveTraTeacherStudent.do',
                                "type": "post",
                                "data": JSON.stringify(requestData),
                                "contentType": "application/json",
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        if (vm.permission.charge) {
                                            updateGrid();   //简单粗暴刷新当前页
                                        } else if (vm.permission.teacher) {
                                            updateGrid(true);   //简单粗暴直接刷新第一页
                                        }
                                        that.close();
                                    }
                                }
                            });
                        } else {
                            util.alert({
                                "iconType": "error",
                                "content": "请至少选择一个条目"
                            });
                        }
                    }
                };
                vm.openBindDialog = function () {
                    var bindVm = avalon.getVm(bindId),
                        itemM = this.$vmodel.$model;
                    if (vm.permission.charge) {
                        bindVm.title = "分配老师";
                        bindVm.userRole = "teacher";
                        //选中对应的行
                        vm.gridSelected.clear();
                        vm.gridSelected.push(itemM.el.studentId + "");
                    } else if (vm.permission.teacher) {
                        bindVm.title = "学员认领";
                        bindVm.userRole = "student";
                    }
                    bindVm.open();
                };
                vm.removeGridItem = function () {
                    var meEl = avalon(this),
                        studentId = meEl.data('studentId') + "";
                    //选中当前行，取消其他行选中
                    vm.gridSelected.clear();
                    vm.gridSelected.push(studentId);
                    //删除对应
                    vm._remove();
                };
                vm.batchRemoveGridItem = function () {
                    vm._remove();
                };
                vm._remove = function () {
                    var objects,
                        requestData = [],
                        url;
                    if (vm.gridSelected.size() > 0) {
                        _.each(vm.gridSelected.$model, function (studentId) {
                            var studentData = _.find(vm.gridData.$model, function (itemData) {
                                return itemData.studentId == studentId;
                            });
                            requestData.push({
                                "studentId": studentData.studentId,
                                "teacherId": studentData.teacherId
                            });
                        });
                        if (requestData.length === 1) {
                            requestData = requestData[0];
                        }
                        util.confirm({
                            "content": "确定要解除师生关系吗？",
                            "onSubmit": function () {
                                util.c2s({
                                    "url": erp.BASE_PATH + 'studentManagement/deleteTraTeacherStudent.do',
                                    "type": "post",
                                    "contentType": 'application/json',
                                    "data": JSON.stringify(requestData),
                                    "success": function (responseData) {
                                        if (responseData.flag) {
                                            updateGrid(); //TODO: 刷新当前页? 可以改为前端清除数据
                                        }
                                    }
                                });
                            }
                        });
                    } else {
                        util.alert({
                            "iconType": "error",
                            "content": "请选择至少一个学生"
                        });
                    }
                };
                vm.$delayCourseOpts = {
                    "calendarId": delayCourseId,
                    "onClickDate": function (d) {
                        var atIndex = -1,
                            studentData = _.find(vm.gridData.$model, function (itemData, i) {
                                if (itemData.studentId == vm.gridSelected[0]) {
                                    atIndex = i;
                                    return true;
                                }
                            }),
                            endDate = moment(d) / 1;
                        util.c2s({
                            "url": erp.BASE_PATH + 'studentManagement/updateCourseEndTime.do',
                            "type": "post",
                            "contentType": 'application/json',
                            "data": JSON.stringify({
                                "endDate": endDate,
                                "learningProgressId": studentData.learningProgressId,
                                "studentId": studentData.studentId,
                                "teacherId": studentData.teacherId,
                                "courseId": studentData.courseId,
                                "coursewareId": studentData.coursewareId
                            }),
                            "success": function (responseData) {
                                if (responseData.flag) {
                                    util.alert({
                                        "iconType": "success",
                                        "content": responseData.message
                                    });
                                    if (atIndex > -1) {
                                        vm.gridData.set(atIndex, {
                                            "courseEndTime": endDate,
                                            "courseStatus": "1" //课程状态变为正在学习
                                        });
                                    }
                                }
                            }
                        });
                        $(this.widgetElement).hide();
                    }
                };
                /**
                 * 课程延时
                 */
                vm.delayCourse = function () {
                    var meEl = $(this),
                        itemM = this.$vmodel.$model,
                        delayCourseVm = avalon.getVm(delayCourseId),
                        delayCourseEl,
                        inputOffset = meEl.offset();
                    if (!delayCourseVm) {
                        delayCourseEl = $('<div ms-widget="calendar,$,$delayCourseOpts"></div>');
                        delayCourseEl.css({
                            "position": "absolute",
                            "z-index": 10000
                        }).hide().appendTo('body');
                        avalon.scan(delayCourseEl[0], [vm]);
                        delayCourseVm = avalon.getVm(delayCourseId);
                        //注册全局点击绑定
                        util.regGlobalMdExcept({
                            "element": delayCourseEl,
                            "handler": function () {
                                delayCourseEl.hide();
                            }
                        });
                    } else {
                        delayCourseEl = $(delayCourseVm.widgetElement);
                    }
                    delayCourseEl.css({
                        "top": inputOffset.top + meEl.outerHeight() + 10,
                        "left": inputOffset.left - delayCourseEl.width() + meEl.width()
                    }).show();
                    //设置focusDate
                    if (itemM.el.courseEndTime) {
                        delayCourseVm.focusDate = moment(itemM.el.courseEndTime)._d;
                    } else {
                        delayCourseVm.focusDate = new Date();
                    }
                    //选中对应的行
                    vm.gridSelected.clear();
                    vm.gridSelected.push(itemM.el.studentId + "");
                };
                vm.showProgressTip = function () {
                    var itemM = this.$vmodel.$model,
                        outer = itemM.$outer,
                        tip = outer.el.progressTip,
                        tipId = pageName + '-progress-tip';
                    var meEl = $(this),
                        tipEl = $('#' + tipId),
                        meOffset = meEl.offset();
                    if (!tipEl.length) {
                        tipEl = $('<div id="' + tipId + '" class="grid-tip"></div>');
                        tipEl.html('<span class="text-content"></span><span class="icon-arrow"></span>');
                        tipEl.hide().appendTo('body');
                    }
                    if (meEl.hasClass('is-current')) {  //只当前课程提示
                        $('.text-content', tipEl).text(tip);
                        tipEl.css({
                            "top": meOffset.top - tipEl.height() - 20,
                            "left": meOffset.left - tipEl.width() / 2 - 11
                        }).show();
                    }
                };
                vm.hideProgressTip = function () {
                    $('#' + pageName + '-progress-tip').hide();
                };
            });

            pageVm.gridSelected.$watch("length", function (n) {
                pageVm.gridAllChecked = n === pageVm.gridData.size();
            });

            avalon.scan(pageEl[0]);
            //更新grid数据
            updateGrid();
            // 加载学习进度
            util.c2s({
                "url": erp.BASE_PATH + "dictionary/querySysDictionaryByType.do",
                "data": 'dictionaryType=00000005',
                "success": function (responseData) {
                    var selectVm = avalon.getVm(scheduleId);
                    if (responseData.flag == 1) {
                        selectVm.setOptions([{
                            "text": "所有进度",
                            "value": ""
                        }].concat(_.map(responseData.data, function (item) {
                            return {
                                "text": item.dictionary_value,
                                "value": item.dictionary_key
                            };
                        })));
                    }
                }
            });
            // 加载职位dropdown-list
            util.c2s({
                "url": erp.BASE_PATH + "position/initPosition.do",
                "success": function (responseData) {
                    var positionSelectVm = avalon.getVm(positionId);
                    if (responseData.flag == 1) {
                        positionSelectVm.setOptions([{
                            "text": "所有职位",
                            "value": ""
                        }].concat(_.map(responseData.data, function (item) {
                            return {
                                "text": item.position_name,
                                "value": item.id
                            };
                        })));
                    }
                }
            });
            // 加载待办事项
            if (!pageVm.permission.charge && pageVm.permission.teacher) {
                util.c2s({
                    "url": erp.BASE_PATH + "studentManagement/queryToDoListCountByTeacherId.do",
                    "data": JSON.stringify({
                        teacherId: loginUserData.id
                    }),
                    "contentType": 'application/json',
                    "success": function (responseData) {
                        var data = responseData.data;
                        pageVm.todoNum = data.totalSize;
                    }
                });
            }

            /**
             * 更新列表展示页面
             * @param jumpFirst
             */
            function updateGrid(jumpFirst) {
                var paginationVm = avalon.getVm(paginationId),
                    requestData = {
                        "userName": pageVm.userName,
                        "tel": pageVm.tel,
                        "courseStatus": avalon.getVm(scheduleId).selectedValue,
                        "positionId": avalon.getVm(positionId).selectedValue
                    };
                if (pageVm.permission.teacher) {
                    requestData.hasTeacherFlag = 1; //是老师,只查询已分配的学员
                }
                if (pageVm.permission.charge) {
                    requestData.hasTeacherFlag = avalon.getVm(allocateId).selectedValue;
                }
                //有且仅有老师权限需要附加teacherId
                if (pageVm.permission.teacher && !pageVm.permission.charge) {
                    requestData.teacherId = loginUserData.id;
                }
                //分页信息处理
                if (jumpFirst) {
                    requestData.pageSize = paginationVm.perPages;
                    requestData.pageNumber = 1;
                } else {
                    requestData.pageSize = paginationVm.perPages;
                    requestData.pageNumber = paginationVm.currentPage;
                }
                util.c2s({
                    "url": erp.BASE_PATH + "studentManagement/queryStudentList.do",
                    "data": JSON.stringify(requestData),
                    "contentType": 'application/json',
                    "success": function (responseData) {
                        var data,
                            tempGridData;
                        if (responseData.flag) {
                            data = responseData.data;
                            paginationVm.total = data.totalSize;
                            if (jumpFirst) {
                                paginationVm.currentPage = 1;   //重设成第一页
                            }
                            //转成驼峰式数据存储
                            tempGridData = _.map(data.rows, function (itemData) {
                                return util.keyToCamelize(itemData);
                            });
                            _.each(tempGridData, function (itemData) {
                                var progress = itemData.progress,
                                    coursewareList = progress.coursewareList;
                                itemData.progressList = _.map(coursewareList, function (itemData2) {
                                    var result = {
                                        "id": itemData2.id,
                                        "isStudied": itemData2.is_studied,
                                        "isCurrent": itemData2.is_current,
                                        "isTimeout": itemData2.is_timeout
                                    };
                                    /*if (itemData2.id == itemData.coursewareId && itemData.courseStatus == "1") { //当前正在学习的课件
                                        result.isCurrent = true;
                                    }
                                    if (itemData2.id == itemData.coursewareId && itemData.courseStatus == "1") {
                                        result.isTimeout = true;
                                    }*/
                                    return result;
                                });
                                //delete progress
                                delete itemData["progress"];
                            });
                            pageVm.gridData = tempGridData;
                            pageVm.gridTotalSize = data.totalSize;
                            pageVm.gridAllChecked = false;  //取消全选
                            pageVm.gridSelected.clear();
                        }
                    }
                });
            }
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var paginationId = pageName + '-pagination',
                allocateId = pageName + "-allocate",
                positionId = pageName + "-position",
                scheduleId = pageName + "-schedule",
                bindId = pageName + "-bind",
                delayCourseId = pageName + '-delay-course',
                allocateVm = avalon.getVm(allocateId),
                delayCourseVm = avalon.getVm(delayCourseId);
            $(avalon.getVm(paginationId).widgetElement).remove();
            if (allocateVm) {
                $(allocateVm.widgetElement).remove();
            }
            $(avalon.getVm(positionId).widgetElement).remove();
            $(avalon.getVm(scheduleId).widgetElement).remove();
            $(avalon.getVm(bindId).widgetElement).remove();
            delayCourseVm && $(delayCourseVm.widgetElement).remove();
            $('#' + pageName + '-progress-tip').remove();
        }
    });
    return pageMod;
});
