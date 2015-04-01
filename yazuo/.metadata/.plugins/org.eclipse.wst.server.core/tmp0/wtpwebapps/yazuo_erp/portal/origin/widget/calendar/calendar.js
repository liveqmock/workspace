/**
 * 日历，依赖第三方库moment
 */
define(["avalon", "moment", "text!./calendar.html", "text!./calendar.css"], function (avalon, moment, tmpl, cssText) {
    var styleEl = $("#widget-style"),
        tmpl = tmpl.split('MS_OPTION_WIDGET'),
        bodyTmpl = tmpl[0],
        headTmpl = tmpl[1];
    if (styleEl.length === 0) {
        styleEl = $('<style id="widget-style"></style>');
        styleEl.appendTo('head');
    }
    if (!styleEl.data('calendar')) {
        try {
            styleEl.html(styleEl.html() + avalon.adjustWidgetCssUrl(cssText, 'calendar/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += avalon.adjustWidgetCssUrl(cssText, 'calendar/');
        }
        styleEl.data('calendar', true);
    }
    var widget = avalon.ui.calendar = function (element, data, vmodels) {
        var opts = data.calendarOptions,
            elEl = $(element);

        var vmodel = avalon.define(data.calendarId, function (vm) {
            avalon.mix(vm, opts);
            vm.widgetElement = element;
            vm.$skipArray = ["widgetElement", "stampStore"];
            vm.panelState = "date"; //date、month、year三个面板状态切换
            vm.stampStore = {};
            vm.hour = "00";
            vm.minute = "00";
            vm.second = "00";
            vm.$watch("hour", function (v) {
                var nv;
                nv = parseInt(v, 10);
                if (!isNaN(nv)) {
                    if (nv < 0 || nv > 23) {
                        nv = "";
                    }
                } else {
                    nv = "";
                }
                vm.hour = nv + "";
            });
            vm.$watch("minute", function (v) {
                var nv;
                nv = parseInt(v, 10);
                if (!isNaN(nv)) {
                    if (nv < 0 || nv > 59) {
                        nv = "";
                    }
                } else {
                    nv = "";
                }
                vm.minute = nv + "";
            });
            vm.$watch("second", function (v) {
                var nv;
                nv = parseInt(v, 10);
                if (!isNaN(nv)) {
                    if (nv < 0 || nv > 59) {
                        nv = "";
                    }
                } else {
                    nv = "";
                }
                vm.second = nv + "";
            });
            vm.lpadTime = function () {
                var v = _.str.trim($(this).val()) || "0";
                vm.$unwatch();  //冻结监控
                vm[avalon(this).data('timePart')] = _.str.lpad(v, 2, '0');
                vm.$watch();    //重启监控
            };
            vm.$init = function () {
                elEl.addClass('ui-calendar').html(bodyTmpl);
                vm._renderDatePanel();
                //注册全局点击
                $('body').on('mousedown', vm._globalMd);
                $('.year-selector', elEl).on('mousedown', function (evt) {
                    evt.stopPropagation();
                });
                $('.month-selector', elEl).on('mousedown', function (evt) {
                    evt.stopPropagation();
                });
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm.$remove = function () {
                elEl.removeClass('ui-calendar').empty();
                //解决全局点击注册
                $('body').off('mousedown', vm._globalMd);
                vm._globalMd = null;
            };
            vm.clearTime = function () {
                vm.hour = "00";
                vm.minute = "00";
                vm.second = "00";
            };
            vm._globalMd = function () {
                vm.panelState = 'date';
            };
            vm._clickSubmit = function () {
                vm.onSubmit.call(vm, moment(vm.focusDate).hour(vm.hour).minute(vm.minute).second(vm.second)._d);
            };
            vm._renderDatePanel = function (date, forceRefresh) {
                var datePanelEl = $('.ui-calendar-date-panel', elEl);
                date = moment(date || vm.focusDate);
                var firstDateOfMonth = date.clone().startOf('month'),
                    beginDate = firstDateOfMonth.startOf('week'),
                    tmpDate,
                    tmpCls;
                var htmlStr = '<table cellpadding="0" cellspacing="0">',
                    i = 0;
                //优化部分，如果focusDate再当前有效时间段内，不做重新渲染，直接改变class name即可
                var activeDateEl = $('.ui-calendar-active', datePanelEl),
                    firstActiveDate,
                    lastActiveDate;
                if (!forceRefresh && activeDateEl.length) {
                    firstActiveDate = moment(activeDateEl.first().data('date'), 'YYYY-MM-DD');
                    lastActiveDate = moment(activeDateEl.last().data('date'), 'YYYY-MM-DD');
                    if (!date.isBefore(firstActiveDate) && !date.isAfter(lastActiveDate)) {
                        $('.is-focus-date', datePanelEl).removeClass('is-focus-date');
                        activeDateEl.each(function () {
                            var tdEl = $(this),
                                tdDate = tdEl.data('date');
                            if (tdDate == date.format('YYYY-MM-DD')) {
                                tdEl.addClass('is-focus-date');
                                return false;
                            }
                        });
                        vm.updateDateView();
                        return;
                    }
                }
                //构建周期表头
                htmlStr += headTmpl;
                //构建date主体
                htmlStr += '<tbody class="date-body">';
                while (i < 42) {
                    tmpDate = beginDate.clone();
                    tmpDate.add('d', i);
                    //区分className
                    if (parseInt(tmpDate.format('M')) < parseInt(date.format('M'))) { //上一个月
                        tmpCls = 'prev-month-date ui-calendar-inactive';
                    } else if (parseInt(tmpDate.format('M')) > parseInt(date.format('M'))) { //下一个月
                        tmpCls = 'next-month-date ui-calendar-inactive';
                    } else {
                        tmpCls = 'current-month-date ui-calendar-active';
                    }
                    //当前日期标志
                    if (tmpDate.format('YYYY-MM-DD') == moment().format('YYYY-MM-DD')) {
                        tmpCls += ' is-today';
                    }
                    //focus date标志
                    if (tmpDate.format('YYYY-MM-DD') == moment(vm.focusDate).format('YYYY-MM-DD')) {
                        tmpCls += ' is-focus-date';
                    }
                    //最大日期判断
                    if (vm.maxDate) {
                        if (tmpDate.isAfter(vm.maxDate, 'day')) {
                            tmpCls += ' is-out-date';
                        }
                    }
                    //最小日期判断
                    if (vm.minDate) {
                        if (tmpDate.isBefore(vm.minDate, 'day')) {
                            tmpCls += ' is-out-date';
                        }
                    }
                    if (i % 7 == 0) { //模7加tr
                        if (i == 0) {
                            htmlStr += '<tr>';
                        } else {
                            htmlStr += '</tr><tr>';
                        }
                    }
                    htmlStr += '<td data-date="' + tmpDate.format('YYYY-MM-DD') + '" class="' + tmpCls + '"><span class="date-cell" ms-click="clickDateCell">' + tmpDate.format('D') + '</span></td>';
                    i++; //自增1
                }
                htmlStr += '</tr></tbody>';
                htmlStr += '</table>';
                datePanelEl.html(htmlStr);
                //更新时间戳className
                vm.updateDateView();
                //重新扫描
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm._renderYearPanel = function (year) {
                var yearSelectorEl = $('.year-selector', elEl),
                    focusDate = vm.focusDate,
                    htmlStr = '';
                year = parseInt(year || moment(focusDate).format('YYYY'), 10);
                //向前60年
                _.each(_.range(year + 60, year, -1), function (i) {
                    htmlStr += '<option value="' + i + '" class="year-item">' + i + '年</option>';
                });
                //向后60年
                _.each(_.range(year, year - 60, -1), function (i) {
                    htmlStr += '<option value="' + i + '" class="year-item" ' + (i === year ? 'selected="selected' : '') + '">' + i + '年</option>';
                });
                yearSelectorEl.html(htmlStr);
            };
            vm._renderMonthPanel = function (month) {

                var monthSelectorEl = $('.month-selector', elEl),
                    focusDate = vm.focusDate,
                    htmlStr = '';
                month = parseInt(month || moment(focusDate).format('M'), 10);

                _.each(_.range(1, 13), function (i) {
                    htmlStr += '<option value="' + i + '" class="month-item" ' + (i === month ? 'selected="selected' : '') + '">' + i + '月</option>';
                });
                monthSelectorEl.html(htmlStr);
            };
            vm.changeYear = function () {
                var selectorEl = $(this),
                    year = selectorEl.val(),
                    focusDate = vm.focusDate;
                vm.focusDate = moment(year + moment(focusDate).format('-MM-DD'), 'YYYY-MM-DD')._d;
                vm.panelState = 'date';
            };
            vm.changeMonth = function () {
                var selectorEl = $(this),
                    month = selectorEl.val(),
                    focusDate = vm.focusDate;
                vm.focusDate = moment(moment(focusDate).format('YYYY-') + month + moment(focusDate).format('-DD'), 'YYYY-MM-DD')._d;
                vm.panelState = 'date';
            };
            vm.updateDateView = function () {
                var panelState = vm.panelState;
                var datePanelEl = $('.ui-calendar-date-panel', elEl),
                    dateEl = $('.date-body td', datePanelEl);
                var stampStore = vm.stampStore,
                    stampTypes = _.keys(stampStore);
                if (panelState == "date") {
                    _.each(stampTypes, function (stampType) {
                        //先清空对应类型时间戳的className
                        dateEl.removeClass('stamp-' + stampType);
                        //逐个添加已存储的className
                        _.each(stampStore[stampType], function (val) {
                            dateEl.filter('[data-date="' + val + '"]').addClass('stamp-' + stampType);
                        });
                    });
                }
            };
            /**
             * 设置时间戳，以字符串形式存储节省内存
             * @param stampType
             * @param vals
             */
            vm.setStamp = function (stampType, vals) {
                var storeVals = vm.stampStore[stampType] || [];
                if (_.isUndefined(vals)) {
                    return;
                }
                vals = [].concat(vals);
                vals = _.map(vals, function (val) {
                    return moment(val); //扩展成moment格式的对象
                });
                _.each(vals, function (val) {
                    if (!_.some(storeVals, function (val2) { //去重
                        return val2 == val.format('YYYY-MM-DD');
                    })) {
                        storeVals.push(val.format('YYYY-MM-DD'));
                    }
                });
                //重设
                vm.stampStore[stampType] = storeVals;
            };
            /**
             * 返回特定类型的时间戳，类型为空返回全部时间戳
             * @param stampType
             */
            vm.getStamp = function (stampType) {
                if (_.isUndefined(stampType)) {
                    return vm.stampStore;
                }
                return vm.stampStore[stampType];
            };
            /**
             * 清理对应类型，对应值的时间戳
             * @param stampType
             * @param stampValue
             */
            vm.removeStamp = function (stampType, vals) {
                var that = this;
                if (_.isUndefined(stampType)) { //时间戳类型为空，全清空
                    vm.stampStore = {};
                    return;
                }
                if (_.isUndefined(vals)) { //时间戳值为空，特定类型全清空
                    vm.stampStore[stampType] = [];
                    return;
                }
                vals = [].concat(vals); //清除指定类型指定值的时间戳
                vals = _.map(vals, function (val) {
                    return moment(val); //扩展成moment格式的对象
                });
                _.each(vals, function (val) {
                    vm.stampStore[stampType] = _.reject(vm.stampStore[stampType], function (val2) {
                        return val.format('YYYY-MM-DD') == val2;
                    });
                });
            };
            /*
             * 月导航减1
             */
            vm.clickNavPrev = function () {
                var focusDate = vm.focusDate;
                vm.focusDate = moment(focusDate).subtract('months', 1)._d;
                vm.panelState = 'date';
            };
            /**
             * 月导航加1
             */
            vm.clickNavNext = function () {
                var focusDate = vm.focusDate;
                vm.focusDate = moment(focusDate).add('months', 1)._d;
                vm.panelState = 'date';
            };
            /**
             * 点击year label切换到年份选择面板
             * @private
             */
            vm.clickLabelYear = function () {
                if (!vm.disableYearMonthPanel) {
                    if (vm.panelState == "year") {
                        vm.panelState = 'date';
                    } else {
                        vm.panelState = 'year';
                    }
                }
            };
            /**
             * 点击month label切换到月份选择面板
             * @private
             */
            vm.clickLabelMonth = function () {
                if (!vm.disableYearMonthPanel) {
                    if (vm.panelState == "month") {
                        vm.panelState = 'date';
                    } else {
                        vm.panelState = 'month';
                    }
                }
            };
            vm.clickDateCell = function (evt) {
                var meEl = $(this),
                    tdEl = meEl.closest('td'),
                    dateStr = tdEl.data('date'),
                    selectedDate;
                if (!tdEl.hasClass('is-out-date')) {
                    selectedDate = moment(dateStr, 'YYYY-MM-DD')._d;
                    if (opts.focusChangeWithClick) {
                        vm.focusDate = selectedDate;
                    }
                    opts.onClickDate.call(vm, selectedDate);
                }
            };

            //监听focus date变化重画date panel
            vm.$watch('focusDate', function () {
                if (vm.panelState == "date") {
                    vm._renderDatePanel();
                } else if (vm.panelState == "year") {
                    vm._renderYearPanel();
                } else if (vm.panelState == "month") {
                    vm._renderMonthPanel();
                }
            });
            vm.$watch('panelState', function (val) {
                if (val == "date") {
                    vm._renderDatePanel();
                } else if (val == "year") {
                    vm._renderYearPanel();
                } else if (val == "month") {
                    vm._renderMonthPanel();
                }
            });
            //最大时间和最小时间改变
            vm.$watch('minDate', function () {
                if (vm.panelState == "date") {
                    vm._renderDatePanel(vm.focusDate, true);
                } else if (vm.panelState == "year") {
                    vm._renderYearPanel();
                } else if (vm.panelState == "month") {
                    vm._renderMonthPanel();
                }
            });
            vm.$watch('maxDate', function () {
                if (vm.panelState == "date") {
                    vm._renderDatePanel(vm.focusDate, true);
                } else if (vm.panelState == "year") {
                    vm._renderYearPanel();
                } else if (vm.panelState == "month") {
                    vm._renderMonthPanel();
                }
            });
        });

        return vmodel;
    };
    widget.version = 1.0;
    widget.defaults = {
        "focusDate": new Date(),
        "maxDate": null,
        "minDate": null,
        "disableYearMonthPanel": false, //禁止年份和月份面板切换
        "disableTimePanel": true,   //默认禁用时间选择面板
        "disableSubmit": true,  //默认禁用确定按钮
        "focusChangeWithClick": true,   //每次点击date cell，focusDate同时改变
        "onClickDate": avalon.noop,
        "onSubmit": avalon.noop
    };

    return avalon;
});
