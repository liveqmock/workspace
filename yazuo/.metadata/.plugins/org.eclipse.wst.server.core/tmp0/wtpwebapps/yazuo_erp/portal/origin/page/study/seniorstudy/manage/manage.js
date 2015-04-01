/**
 * 学员管理
 */
define(['avalon', 'util', 'json', 'moment', '../../../../widget/grid/grid', '../../../../widget/calendar/calendar'], function (avalon, util, JSON, moment) {

    var win = this,
        erp = win.erp,
        pageMod = {};
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName, routeData) {
            var gridId = pageName + '-grid',
                calendarId = pageName + '-calendar';
            var pageVm = avalon.define(pageName, function (vm) {
                vm.removeTheStudentsPermission = util.hasPermission("remove_the_students");
                vm.removeAllTheStudentsPermission = util.hasPermission("remove_all_the_students");
                vm.timeDelayLearningPermission = util.hasPermission("time_delay_learning");
                vm.hashId = routeData.params["id"];
                vm.courseName = decodeURIComponent(routeData.params["name"]);
                vm.tabIndex = 1;
                vm.courseStatus = "";
                vm.sumSize = 0;
                vm.learningSize = 0;
                vm.timeOutSize = 0;
                vm.graduationSize = 0;
                vm.learningProgressId = "";
                vm.studentName = "";
                vm.keyword = "";
                vm.$calendarOpts = {
                    "calendarId": calendarId,
                    "onClickDate": function (d) {
                        //vm[vm.$focusDateInputName + "Date"] = moment(d).format('YYYY-MM-DD');
                        $(this.widgetElement).hide();
                        util.confirm({
                            "iconType": "info",
                            "content": "确定要延时" + vm.studentName + "学习时间至" + moment(d).format('YYYY-MM-DD') + "吗？",
                            "onSubmit": function () {
                                util.c2s({
                                    "type": "post",
                                    "url": erp.BASE_PATH + "oldStaffManagement/executeDelay.do",
                                    "data": JSON.stringify({learningProgressId: +vm.learningProgressId, endDate: d.getTime()}),
                                    "contentType": 'application/json',
                                    "dataType": "json",
                                    "success": function (responseData) {
                                        if (responseData.flag) {
                                            avalon.getVm(gridId).load();
                                        }
                                    }
                                });
                            }
                        });
                    }
                };
                /**
                 * 打开时间选择面板
                 */
                vm.openCalendar = function () {
                    var meEl = $(this),
                        calendarVm = avalon.getVm(calendarId),
                        calendarEl,
                        inputOffset = meEl.offset(),
                        dateName = meEl.data('dateName'),
                        now = new Date();
                    vm.learningProgressId = meEl.data("id");
                    vm.studentName = meEl.data("studentName");
                    if (!calendarVm) {
                        calendarEl = $('<div ms-widget="calendar,$,$calendarOpts"></div>');
                        calendarEl.css({
                            "position": "absolute",
                            "z-index": 10000
                        }).hide().appendTo('body');
                        avalon.scan(calendarEl[0], [vm]);
                        calendarVm = avalon.getVm(calendarId);
                        //注册全局点击绑定
                        util.regGlobalMdExcept({
                            "element": calendarEl,
                            "handler": function () {
                                calendarEl.hide();
                            }
                        });
                    } else {
                        calendarEl = $(calendarVm.widgetElement);
                    }
                    //设置focus Date
                    if (vm[dateName + "Date"]) {
                        calendarVm.focusDate = moment(vm[dateName + "Date"], 'YYYY-MM-DD')._d;
                    } else {
                        calendarVm.focusDate = now;
                    }
                    //设置最大和最小日期
                    if (dateName === "start" && vm.endDate) {
                        calendarVm.maxDate = moment(vm.endDate, 'YYYY-MM-DD').subtract('days', 1)._d;
                        calendarVm.minDate = null;
                    }
                    if (dateName === "end" && vm.startDate) {
                        calendarVm.minDate = moment(vm.startDate, 'YYYY-MM-DD').add('days', 1)._d;
                        calendarVm.maxDate = null;
                    }
                    calendarEl.css({
                        "top": inputOffset.top + meEl.outerHeight() + 5,
                        "left": inputOffset.left - 245
                    }).show();
                };
                vm.$gridOpts = {
                    "gridId": gridId,
                    "showRemoveAll": false,   //是否显示全部移除按钮
                    "disableCheckbox": true,
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_TBAR'),
                            tmplMain,
                            tmplBbar;
                        tmplTemp = tmplTemp[1].split("MS_OPTION_MAIN");
                        tmplMain = tmplTemp[0];
                        return tmplMain + '<div class="ui-grid-bbar fn-clear">' +
                            '<div class="grid-action fn-left">' +
                            '<button type="button" class="check-all-btn main-btn"' +
                            ' ms-if="removeAllTheStudentsPermission"' +
                            ' ms-visible="showRemoveAll"' +
                            ' ms-click="delAll">移除全部学员</button>' +
                            '&nbsp;' +
                            '</div>' +
                            '<div class="pagination fn-right"' +
                            ' ms-widget="pagination,$,$paginationOpts"></div>' +
                            '</div>';
                    },
                    "columns": [
                        {
                            "dataIndex": "user_name",
                            "text": "姓名"
                        },
                        {
                            "dataIndex": "tel",
                            "text": "手机号"
                        },
                        {
                            "dataIndex": "position_name",
                            "text": "职称"
                        },
                        {
                            "dataIndex": "group_name",
                            "text": "部门"
                        },
                        {
                            "dataIndex": "tip",
                            "text": "学习状态",
                            "html": true,
                            "renderer": function (v, rowData) {
                                if (rowData.course_status == 3) {
                                    return '<span class="font-red">' + v + '</span> '
                                } else {
                                    return v;
                                }
                            }
                        },
                        {
                            "dataIndex": "operation",
                            "text": "操作",
                            "html": true,
                            "renderer": function (v, rowData) {
                                var htmlStr = '<a href="javascript:;" ms-if="timeDelayLearningPermission" ms-click="openCalendar" ms-visible="$outer.el.course_status==3" ms-data-student-name="$outer.el.user_name" data-id="' + rowData.learning_progress_id + '" data-date-name="start">延时学习</a>';
                                if (rowData.course_status == "1" || rowData.course_status == "3") {
                                    htmlStr = '<a href="javascript:;" ms-click="delStudent" ms-data-id="$outer.el.id" ms-if="removeTheStudentsPermission">移除学员</a>&nbsp;&nbsp;&nbsp;&nbsp;' + htmlStr;
                                }
                                return htmlStr;
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        var gridVm = avalon.getVm(gridId);
                        _.extend(requestData, {
                            "coursewareId": +vm.hashId,
                            "courseStatus": vm.courseStatus,
                            "userName": _.str.trim(vm.keyword),
                            "tel": ''
                        });
                        util.c2s({
                            "type": "post",
                            "url": erp.BASE_PATH + "oldStaffManagement/queryOldStaffList.do",
                            "data": JSON.stringify(requestData),
                            "contentType": 'application/json',
                            "dataType": "json",
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    vm.sumSize = data.sumSize;
                                    vm.learningSize = data.learningSize;
                                    vm.timeOutSize = data.timeOutSize;
                                    vm.graduationSize = data.graduationSize;
                                    //控制全部移除按钮的显示和隐藏
                                    if (_.some(data.rows, function (itemData) {
                                        return itemData.course_status == "1" || itemData.course_status == "3";
                                    })) {
                                        gridVm.showRemoveAll = true;
                                    } else {
                                        gridVm.showRemoveAll = false;
                                    }
                                    callback.call(this, data);
                                }
                            }
                        });
                    },
                    "delStudent": function () {
                        var id = [+$(this).data("id")];
                        delAjax(id);
                    },
                    "delAll": function () {
                        var gridVm = avalon.getVm(gridId), id = [];
                        gridVm.scCheckAll();
                        id = _.map(gridVm.getSelectedData(), function (item) {
                            return item.id / 1; //保证是int型
                        });
                        delAjax(id);
                    }
                };
                vm.query = function () {
                    avalon.getVm(gridId).load();
                };
                vm.switchTab = function () {
                    var type = $(this).data("type");
                    if (type == vm.tabIndex) {
                        return;
                    }
                    switch (type) {
                        case 1:
                            vm.courseStatus = "";
                            vm.tabIndex = 1;
                            break;
                        case 2:
                            vm.courseStatus = "1";
                            vm.tabIndex = 2;
                            break;
                        case 3:
                            vm.tabIndex = 3;
                            vm.courseStatus = "3";
                            break;
                        case 4:
                            vm.tabIndex = 4;
                            vm.courseStatus = "2";
                            break
                    }
                    avalon.getVm(gridId).load(1);
                };
            });

            avalon.scan(pageEl[0], [pageVm]);
            //更新grid数据
            avalon.getVm(gridId).load();
            //删除学员Ajax
            function delAjax(id) {
                util.confirm({
                    "iconType": "info",
                    "content": "确定要删除该学员吗？",
                    "onSubmit": function () {
                        util.c2s({
                            "type": "post",
                            "url": erp.BASE_PATH + "oldStaffManagement/executeRemove.do",
                            "data": JSON.stringify({learningProgressIdList: id}),
                            "contentType": 'application/json',
                            "dataType": "json",
                            "success": function (responseData) {
                                if (responseData.flag) {
                                    avalon.getVm(gridId).load();
                                }
                            }
                        });
                    },
                    "onCancel": function () {
                        avalon.getVm(gridId).unselectById("all");
                    }
                });
            }

        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var gridId = pageName + '-grid',
                calendarId = pageName + '-calendar',
                calendarVm = avalon.getVm(calendarId);
            $(avalon.getVm(gridId).widgetElement).remove();
            calendarVm && $(calendarVm.widgetElement).remove();
        }
    });

    return pageMod;
});
