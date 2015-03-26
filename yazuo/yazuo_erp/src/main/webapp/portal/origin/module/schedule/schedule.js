/**
 * 工作计划日历
 */
define(["avalon", "util", "json", "moment", "text!./schedule.html", "text!./schedule.css", "common", "../../widget/dialog/dialog", "../../widget/form/form", "../../widget/calendar/calendar.js"], function(avalon, util, JSON, moment, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    var styleData = styleEl.data('module') || {};
    if (!styleData["schedule"]) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'schedule/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'schedule/');
        }
        styleData["schedule"] = true;
        styleEl.data('module', styleData);
    }
    var module = erp.module.schedule = function(element, data, vmodels) {
        var opts = data.scheduleOptions,
            elEl = $(element),
            now = new Date();
        var exportDialogId = data.scheduleId + '-export-dialog',
            exportFormId = data.scheduleId + '-export-form',
            exportCalendarId = data.scheduleId + '-export-calendar';
        var vmodel = avalon.define(data.scheduleId, function (vm) {
            avalon.mix(vm, opts);
            vm.planExportPermission = util.hasPermission('plan_export_171');    //true表示拥有计划导出的权限
            vm.widgetElement = element;
            vm.currentReferDate = moment(now).date(0)._d;
            vm.dateRows = [];
            /**
             * 根据referDate更新日历
             * @param referDate 参考日期，每月的1号，以天为单位
             */
            vm.updateDateRows = function (referDate) {
                var rows = [],
                    readyRows = [],
                    firstDateOfWeek;
                referDate = moment(referDate || moment(now).date(1));  //默认当前月第一天
                firstDateOfWeek = referDate.clone().startOf('week');
                if (firstDateOfWeek.isSame(referDate, 'day')) {
                    firstDateOfWeek = firstDateOfWeek.subtract('days', 7);
                }
                //填充42天
                _.each(_.range(0, 42), function (i) {
                    var d = moment(firstDateOfWeek).add('days', i);
                    rows.push({
                        "date": d._d,
                        "isCurrentMonth": d.month() === referDate.month(),
                        "isToday": d.isSame(now, 'day'),
                        "planList": []
                    });
                });
                vm.currentReferDate = referDate._d;
                //查询本月的计划信息
                vm.onFetchPlan({
                    "startTime": rows[0].date / 1,
                    "endTime": rows[rows.length - 1].date / 1
                }, function (planList) {
                    _.each(planList, function (itemData) {
                        _.some(rows, function (itemData2) {
                            var startTime = moment(itemData.start_time);
                            if (startTime.isSame(itemData2.date, 'day')) {
                                itemData2["planList"].push({
                                    "id": itemData.id,
                                    "title": itemData.title,
                                    "status": itemData.status,
                                    "isTimeout": startTime.isBefore(now, 'day'),
                                    "json": JSON.stringify(itemData)
                                });
                                return true;
                            }
                        });
                    });
                    //每7循环
                    _.each(rows, function (row, i) {
                        var dateRowsIndex = Math.floor(i / 7);
                        readyRows[dateRowsIndex] = readyRows[dateRowsIndex] || [];
                        readyRows[dateRowsIndex].push(row);
                    });
                    //重设dateRows
                    vm.dateRows = readyRows;
                });
            };
            /**
             * 月份减1
             */
            vm.navPrev = function () {
                vm.updateDateRows(moment(vm.currentReferDate).subtract('months', 1));
            };
            /**
             * 月份加1
             */
            vm.navNext = function () {
                vm.updateDateRows(moment(vm.currentReferDate).add('months', 1));
            };
            /**
             * 导航到今天
             */
            vm.navToday = function () {
                vm.updateDateRows(moment(now).date(1));
            };
            vm.exportPlan = function () {
                var exportDialogVm = avalon.getVm(exportDialogId);
                exportDialogVm.open();
            };
            /**
             * 点击日期cell触发
             */
            vm.clickDateCell = function (evt) {
                return vm.onClickDateCell.call(this, evt);
            };
            vm.$exportDialogOpts = {
                "dialogId": exportDialogId,
                "title": "导出",
                "width": 540,
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
                    var formVm = avalon.getVm(exportFormId);
                    formVm.reset();
                    formVm.startDate = '';
                    formVm.endDate = '';
                    formVm.currentDateName = '';
                },
                "submit": function (evt) {
                    var requestData,
                        url,
                        dialogVm = avalon.getVm(exportDialogId),
                        formVm = avalon.getVm(exportFormId);
                    if (formVm.validate()) {
                        requestData = formVm.getFormData();
                        dialogVm.close();
                        //直接导出
                        window.open(erp.BASE_PATH + 'fesPlanReport/exportPlanReport.do?startTime=' + (moment(requestData.startTime, 'YYYY-MM-DD') / 1) + '&endTime=' + (moment(requestData.endTime, 'YYYY-MM-DD') / 1), '_self');
                    }
                }
            };
            vm.$exportFormOpts = {
                "formId": exportFormId,
                "field": [{
                    "selector": ".start-date",
                    "rule": ["noempty", function (val, rs) {
                        if (rs[0] !== true) {
                            return "开始时间不能为空";
                        } else {
                            return true;
                        }
                    }]
                }, {
                    "selector": ".end-date",
                    "rule": ["noempty", function (val, rs) {
                        if (rs[0] !== true) {
                            return "结束时间不能为空";
                        } else {
                            return true;
                        }
                    }]
                }],
                "startDate": "",
                "endDate": "",
                "currentDateName": ""
            };
            vm.$exportCalendarOpts = {
                "calendarId": exportCalendarId,
                "onClickDate": function (d) {
                    var formVm = avalon.getVm(exportFormId);
                    formVm[formVm.currentDateName] = moment(d).format('YYYY-MM-DD');
                    $(this.widgetElement).hide();
                }
            };
            /**
             * 打开导出日期选择面板
             */
            vm.openExportDateCalendar = function () {
                var meEl = $(this),
                    calendarVm = avalon.getVm(exportCalendarId),
                    formVm = avalon.getVm(exportFormId),
                    calendarEl,
                    inputOffset = meEl.offset(),
                    dateName = meEl.data('date-name') + 'Date';
                if (!calendarVm) {
                    calendarEl = $('<div ms-widget="calendar,$,$exportCalendarOpts"></div>');
                    calendarEl.css({
                        "position": "absolute",
                        "z-index": 10000
                    }).hide().appendTo('body');
                    avalon.scan(calendarEl[0], [vm]);
                    calendarVm = avalon.getVm(exportCalendarId);
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
                if (formVm[dateName]) {
                    calendarVm.focusDate = moment(formVm[dateName], 'YYYY-MM-DD')._d;
                } else {
                    calendarVm.focusDate = new Date();
                }
                //设置最大值和最小值
                if (dateName === "startDate") {
                    if (formVm.endDate) {
                        calendarVm.maxDate = moment(formVm.endDate, 'YYYY-MM-DD')._d;
                        calendarVm.minDate = null;
                    } else {
                        calendarVm.maxDate = null;
                        calendarVm.minDate = null;
                    }
                } else {
                    if (formVm.startDate) {
                        calendarVm.minDate = moment(formVm.startDate, 'YYYY-MM-DD')._d;
                        calendarVm.maxDate = null;
                    } else {
                        calendarVm.maxDate = null;
                        calendarVm.minDate = null;
                    }
                }
                //设置当前操作的input
                formVm.currentDateName = dateName;

                calendarEl.css({
                    "top": inputOffset.top + meEl.outerHeight() - 1,
                    "left": inputOffset.left
                }).show();
            };
            vm.$init = function() {
                elEl.addClass('module-schedule');
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
                //第一次刷新日历
                vm.updateDateRows();
            };
            vm.$remove = function() {
                var exportDialogId = data.scheduleId + '-export-dialog',
                    exportFormId = data.scheduleId + '-export-form',
                    exportCalendarId = data.scheduleId + '-export-calendar';
                var calendarVm = avalon.getVm(exportCalendarId);
                elEl.removeClass('module-schedule').off().empty();
                calendarVm && $(calendarVm.widgetElement).remove();
                $(avalon.getVm(exportFormId).widgetElement).remove();
                $(avalon.getVm(exportDialogId).widgetElement).remove();
            };
            vm.$skipArray = ["widgetElement"];
        });
        return vmodel;
    };
    module.version = 1.0;
    module.defaults = {
        "onFetchPlan": function (requestData, callback) {    //请求工作计划触发
            callback([]);
        },
        "onClickDateCell": avalon.noop   //点击日期cell监听
    };
    return avalon;
});
