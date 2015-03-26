/**
 * 工作计划日历
 */
define(["avalon", "util", "json", "moment", "text!./schedule.html", "text!./schedule.css", "common"], function(avalon, util, JSON, moment, tmpl, cssText) {
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
        var vmodel = avalon.define(data.scheduleId, function (vm) {
            avalon.mix(vm, opts);
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
            /**
             * 点击日期cell触发
             */
            vm.clickDateCell = function (evt) {
                return vm.onClickDateCell.call(this, evt);
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
                elEl.removeClass('module-schedule').off().empty();
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
