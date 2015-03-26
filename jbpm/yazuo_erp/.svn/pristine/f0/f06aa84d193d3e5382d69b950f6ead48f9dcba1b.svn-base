/**
 * 综合数据统计
 */
define(['avalon', 'util', 'moment', 'highcharts','../../../widget/form/select', '../../../widget/calendar/calendar'], function (avalon, util, moment, Highcharts) {
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
            var ubDotUserId = pageName + '-ub-dot-user',
                ubDotBrandId = pageName + '-ub-dot-brand',
                ubRangeUserId = pageName + '-ub-range-user',
                ubRangeRdId = pageName + '-ub-range-rd',
                calendar1Id = pageName + '-calendar-1',
                calendar2Id = pageName + '-calendar-2',
                calendar3Id = pageName + '-calendar-3',
                ubChartHolderId = pageName + '-ub-chart-holder',
                funChartHolderId = pageName + '-fun-chart-holder',
                funCateId = pageName + '-fun-cate',
                funHbId = pageName + '-fun-hb',
                funSendId = pageName + 'fun-send',
                funGtId = pageName + 'fun-gt';
            var now = new Date(),
                rangeDateCalendarEl = $('.range-date-calendar', pageEl),
                calendarSelectorPanelEl = $('.date-range-selector .selector-panel', pageEl),
                ubChartPanelEl = $('.ub-section .chart-box .chart-panel', pageEl),
                funChartPanelEl = $('.fun-section .chart-box .chart-panel', pageEl);
            //默认的chart配置项
            var defaultChartOpts = {
                chart: {
                    style: {
                        fontFamily: 'tahoma,arial,"Hiragino Sans GB","微软雅黑","Microsoft YaHei","WenQuanYi Micro Hei","\5b8b\4f53"', // default font
                        fontSize: '14px',
                        color: '#666666'
                    }
                },
                credits: {
                    "enabled": false
                },
                exporting: {
                    "enabled": false
                },
                colors: ['#e0769b', '#e0ad76', '#dae076', '#66ccb5', '#76cae0', '#76a9e0', '#7678e0'],
                title: {
                    "style": {
                        "color": "#666666",
                        "fontSize": "14px"
                    }
                },
                legend: {
                    "itemStyle": {
                        "color": "#666666",
                        "fontSize": "14px",
                        "fontWeight": "normal"
                    }
                },
                labels: {
                    "style": {
                        "color": "#666666",
                        "fontSize": "14px"
                    }
                },
                tooltip: {
                    "style": {
                        "color": "#666666",
                        "fontSize": "14px",
                        "fontWeight": "normal"
                    },
                    "headerFormat": '<span style="font-weight:bold;">{point.key}</span><br/>'
                },
                plotOptions: {
                    pie: {
                        dataLabels: {
                            "style": {
                                "color": "#666666",
                                "fontSize": "14px"
                            }
                        },
                        showInLegend: true
                    }
                },
                yAxis: {
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#666666'
                    }]
                }
            };
            var pageVm = avalon.define(pageName, function (vm) {
                vm.$skipArray = ["ubChart"];
                vm.statisticsState = 'dot'; //dot or range
                /*vm.$watch('statisticsState', function () {
                    //刷新整个页面
                    vm.refreshPage();
                });*/
                vm.showRangeCalendar = function () {
                    calendarSelectorPanelEl.show();
                };
                vm.$calendar1Opts = {
                    "calendarId": calendar1Id,
                    "disableYearMonthPanel": true,
                    "focusDate": moment().subtract('months', 2)._d,
                    "focusChangeWithClick": false,
                    "onClickDate": function (d) {
                        vm.selectDateRange(d);
                    }
                };
                vm.$calendar2Opts = {
                    "calendarId": calendar2Id,
                    "disableYearMonthPanel": true,
                    "focusDate": moment().subtract('months', 1)._d,
                    "focusChangeWithClick": false,
                    "onClickDate": function (d) {
                        vm.selectDateRange(d);
                    }
                };
                vm.$calendar3Opts = {
                    "calendarId": calendar3Id,
                    "disableYearMonthPanel": true,
                    "focusDate": new Date(),
                    "maxDate": now,
                    "onClickDate": function (d) {
                        vm.selectDateRange(d);
                    }
                };
                vm.isCurrentDate = true;
                vm.startDate = null;    //选择时间段的开始时间
                vm.endDate = null;  //选择时间段的结束时间
                vm.startDateStr = '';
                vm.endDateStr = '';
                //vm.startDateStr = moment(now).format('YYYY-MM-DD');
                //vm.endDateStr = moment(now).format('YYYY-MM-DD');
                vm.selectedRangeDateStr = '';
                //vm.selectedRangeDateStr = moment(now).format('YYYY.MM.DD') + ' - ' + moment(now).format('YYYY.MM.DD');
                vm.selectDateRange = function (d) {  //点击操作选中时间段
                    if (!vm.startDate || vm.endDate) {   //重新开始选择开始时间
                        vm.endDate = null;
                        vm.startDate = d;
                    } else {
                        if (moment(d).isBefore(vm.startDate, 'day')) {
                            util.alert({
                                "iconType": "info",
                                "content": "选择结束时间要大于开始时间"
                            });
                        } else {
                            vm.endDate = d;
                        }
                    }
                };
                vm.rangeDateCb = function () {
                    var dateCellEl = $('.ui-calendar-date-panel td', rangeDateCalendarEl);
                    dateCellEl.removeClass('is-start-date is-end-date is-range-date');
                    if (vm.startDate || vm.endDate) {
                        dateCellEl.each(function () {
                            var cellEl = $(this),
                                dateStr = cellEl.data('date'),
                                dateData = moment(dateStr, 'YYYY-MM-DD');
                            if (vm.endDate && dateStr == moment(vm.endDate).format('YYYY-MM-DD')) {
                                cellEl.addClass('is-end-date');
                            }
                            if (vm.startDate && dateStr == moment(vm.startDate).format('YYYY-MM-DD')) {
                                cellEl.addClass('is-start-date');
                            }
                            if (vm.startDate && vm.endDate && !dateData.isBefore(vm.startDate, 'day') && !dateData.isAfter(vm.endDate, 'day')) {
                                cellEl.addClass('is-range-date');
                            }
                        });
                        vm.startDateStr = vm.startDate ? moment(vm.startDate).format('YYYY-MM-DD') : '';
                        vm.endDateStr = vm.endDate ? moment(vm.endDate).format('YYYY-MM-DD') : '';
                        if (vm.startDate && vm.endDate) {
                            vm.selectedRangeDateStr = moment(vm.startDate).format('YYYY.MM.DD') + ' - ' + moment(vm.endDate).format('YYYY.MM.DD');
                        } else {
                            vm.selectedRangeDateStr = '';
                        }
                    } else {
                        vm.startDateStr = '';
                        vm.selectedRangeDateStr = '';
                    }
                };
                vm.getCurrentFirstDate = function () {
                    var dateCellEl = $('.ui-calendar-date-panel td', rangeDateCalendarEl);
                    return moment(dateCellEl.first().data('date'), 'YYYY-MM-DD')._d;
                };
                //时间段选择高亮处理
                vm.$watch("startDate", function (d) {
                    vm.rangeDateCb();
                });
                vm.$watch("endDate", function (d) {
                    vm.rangeDateCb();
                });
                vm.formatInputDate = function (dateStr) {
                    dateStr = dateStr.replace(/\s+/g, "");
                    //dateStr = _.str.trim(dateStr, '-');
                    var dateArr = dateStr.split("");
                    var ret = "";
                    for (var i = 0, n = dateArr.length; i < n; i++) {
                        if (i > 9) {//不能超过10位
                            break;
                        }
                        if (/[\d|-]/.test(dateArr[i])) {
                            if (i == 4 && dateArr[4] != "-") {
                                ret += "-";
                            }
                            if (i == 7 && dateArr[7] != "-") {
                                ret += "-";
                            }
                            ret += dateArr[i];
                        }
                    }
                    return ret;
                };
                vm.$watch("startDateStr", function (dateStr) {
                    var tempDate;
                    vm.startDateStr = vm.formatInputDate(dateStr);
                    if (vm.startDateStr.length === 10) {   //输入完毕
                        tempDate = moment(vm.startDateStr, 'YYYY-MM-DD');
                        if (tempDate.isValid()) {   //是一个合法的日期
                            vm.startDate = tempDate._d;
                        } else {
                            //vm.startDate = null;
                        }
                    }
                });
                vm.$watch("endDateStr", function (dateStr) {
                    var tempDate,
                        lastCalendarVm = avalon.getVm(calendar3Id);
                    vm.endDateStr = vm.formatInputDate(dateStr);
                    if (vm.endDateStr.length === 10) {   //输入完毕
                        tempDate = moment(vm.endDateStr, 'YYYY-MM-DD');
                        if (tempDate.isValid() && !tempDate.isBefore(vm.startDate, 'day')) {   //是一个合法的日期,并且不能在开始时间前
                            //focus 到结束时间
                            //lastCalendarVm.focusDate = tempDate._d;
                            if (moment(tempDate._d).isBefore(vm.getCurrentFirstDate())) {
                                lastCalendarVm.focusDate = tempDate._d;
                            }
                            vm.endDate = tempDate._d;
                        } else {
                            //vm.endDate = null;
                        }
                    }
                });
                vm.navToNextYear = function () {
                    var lastCalendarVm = avalon.getVm(calendar3Id),
                        focusDate = moment(lastCalendarVm.focusDate);
                    //尝试加1年
                    focusDate = focusDate.add('years', 1);
                    if (focusDate.isAfter(now, 'month')) {
                        lastCalendarVm.focusDate = now;
                        vm.isCurrentDate = true;
                    } else {
                        lastCalendarVm.focusDate = focusDate._d;
                        vm.isCurrentDate = false;
                    }
                    //手动触发startDate和endDate监听
                    vm.$fire('startDate', vm.startDate);
                    vm.$fire('endDate', vm.endDate);
                };
                vm.navToPrevYear = function () {
                    var lastCalendarVm = avalon.getVm(calendar3Id),
                        focusDate = moment(lastCalendarVm.focusDate);
                    //尝试减1年
                    focusDate = focusDate.subtract('years', 1);
                    lastCalendarVm.focusDate = focusDate._d;
                    vm.isCurrentDate = false;
                    //手动触发startDate和endDate监听
                    vm.$fire('startDate', vm.startDate);
                    vm.$fire('endDate', vm.endDate);
                };
                vm.navToNextMonth = function () {
                    var lastCalendarVm = avalon.getVm(calendar3Id),
                        focusDate = moment(lastCalendarVm.focusDate);
                    //尝试加1个月
                    focusDate = focusDate.add('months', 1);
                    if (focusDate.isAfter(now, 'month')) {
                        lastCalendarVm.focusDate = now;
                        vm.isCurrentDate = true;
                    } else {
                        lastCalendarVm.focusDate = focusDate._d;
                        vm.isCurrentDate = false;
                    }
                    //手动触发startDate和endDate监听
                    vm.$fire('startDate', vm.startDate);
                    vm.$fire('endDate', vm.endDate);
                };
                vm.navToPrevMonth = function () {
                    var lastCalendarVm = avalon.getVm(calendar3Id),
                        focusDate = moment(lastCalendarVm.focusDate);
                    //尝试减1个月
                    focusDate = focusDate.subtract('months', 1);
                    lastCalendarVm.focusDate = focusDate._d;
                    vm.isCurrentDate = false;
                    //手动触发startDate和endDate监听
                    vm.$fire('startDate', vm.startDate);
                    vm.$fire('endDate', vm.endDate);
                };
                /**
                 * 点击日期区间选择面板的确定按钮
                 */
                vm.confirmRangeDate = function () {
                    //if (moment(vm.endDate).isAfter(vm.startDate, 'day')) {
                    if (!vm.endDate || !vm.startDate) {
                        util.alert({
                            "iconType": "error",
                            "content": "请选择有效的时间段"
                        });
                        return;
                    }
                    if (!moment(vm.endDate).isBefore(vm.startDate, 'day')) {    //可以选择同一天
                        if (moment(vm.endDate).isSame(vm.startDate, 'day') && moment(vm.endDate).isSame(now, 'day')) {  //不能选择今天时间段，直接跳到今天统计
                            vm.statisticsState = 'dot';
                            vm.startDate = null;
                            vm.endDate = null;
                            calendarSelectorPanelEl.hide();
                            //刷新整个页面
                            vm.refreshPage();
                        } else {
                            vm.statisticsState = 'range';
                            calendarSelectorPanelEl.hide();
                            //刷新整个页面
                            vm.refreshPage();
                        }
                    } else {
                        util.alert({
                            "iconType": "error",
                            "content": "请设置开始时间小于结束时间"
                        });
                    }
                };
                vm.closeCalendarPanel = function () {
                    //vm.startDate = null;
                    //vm.endDate = null;
                    calendarSelectorPanelEl.hide();
                };
                /**
                 * 切换到今天统计
                 */
                vm.switchToToday = function () {
                    vm.statisticsState = 'dot';
                    vm.startDate = null;
                    vm.endDate = null;
                    //刷新整个页面
                    vm.refreshPage();
                };
                vm.ubState = 'user';    //user or business
                vm.totalUserNum = 0;
                vm.addedUserNum = 0;
                vm.totalBrandNum = 0;
                vm.addedBrandNum = 0;
                vm.totalMerchantNum = 0;
                vm.addedMerchantNum = 0;
                vm.userStatisticsData = []; //用户统计列表
                vm.businessStatisticsData = []; //品牌和商户统计列表
                vm.ubChartBoxTitle = '用户分布图表';
                vm.$ubDotUserOpts = {
                    "selectId": ubDotUserId,
                    "options": [{
                        "text": "总用户",
                        "value": false
                    }, {
                        "text": "新增用户",
                        "value": true
                    }],
                    "selectedIndex": 0,
                    "onSelect": function () {
                        vm.fetchUbData('chart', function (data) {
                            vm.redrawUbChart(data);
                        });
                    }
                };
                vm.$ubDotBrandOpts = {
                    "selectId": ubDotBrandId,
                    "options": [{
                        "text": "总品牌",
                        "value": false
                    }, {
                        "text": "新增品牌",
                        "value": true
                    }],
                    "selectedIndex": 0,
                    "onSelect": function () {
                        vm.fetchUbData('chart', function (data) {
                            vm.redrawUbChart(data);
                        });
                    }
                };
                vm.$ubRangeUserOpts = {
                    "selectId": ubRangeUserId,
                    "options": [{
                        "text": "新增用户",
                        "value": "newUser"
                    }, {
                        "text": "登录人数",
                        "value": "loginUser"
                    }, {
                        "text": "登录次数",
                        "value": "loginTimes"
                    }],
                    "selectedIndex": 0,
                    "onSelect": function () {
                        vm.fetchUbData('chart', function (data) {
                            vm.redrawUbChart(data);
                        });
                    }
                };
                vm.$ubRangeRdOpts = {
                    "selectId": ubRangeRdId,
                    "options": [{
                        "text": "角色占比",
                        "value": "role"
                    }, {
                        "text": "时间分布",
                        "value": "date"
                    }],
                    "selectedIndex": 0,
                    "onSelect": function () {
                        vm.fetchUbData('chart', function (data) {
                            vm.redrawUbChart(data);
                        });
                    }
                };
                vm.switchUbState = function () {
                    vm.ubState = avalon(this).data('ubState');
                };
                vm.$watch('ubState', function () {
                    //获取用户和商户数据渲染页面
                    vm.fetchUbData('grid', function (data) {
                        vm.reRenderUbGrid(data);
                    });
                    vm.fetchUbData('chart', function (data) {
                        vm.redrawUbChart(data);
                    });
                });
                vm.ubChart = null;
                /**
                 * 用户和商户统计获取数据接口
                 * @param type grid or chart
                 */
                vm.fetchUbData = function (type, callback) {
                    var url,
                        requestData = {};
                    if (vm.ubState == 'user') { //不同的状态传参不一样
                        if (type == 'grid') {
                            url = 'new/superReport/userTableData.do';
                        } else if (type == 'chart') {
                            url = 'new/superReport/userChartData.do';
                            if (vm.statisticsState == 'dot') {
                                requestData["isNew"] = avalon.getVm(ubDotUserId).selectedValue;
                            }
                            if (vm.statisticsState == 'range') {
                                requestData["queryType"] = avalon.getVm(ubRangeUserId).selectedValue;
                                requestData["queryFlag"] = avalon.getVm(ubRangeRdId).selectedValue;
                            }
                        }
                    } else if (vm.ubState == 'business') {
                        if (type == 'grid') {
                            url = 'new/superReport/brandTableData.do';
                        } else if (type == 'chart') {
                            url = 'new/superReport/brandChartData.do';
                            if (vm.statisticsState == 'dot') {
                                requestData["isNew"] = avalon.getVm(ubDotBrandId).selectedValue;
                            }
                        }
                    }
                    //附加时间参数
                    if (vm.statisticsState == 'dot') {
                        requestData["beginDate"] = requestData["endDate"] = moment(now).unix();
                    } else if (vm.statisticsState == 'range') {
                        requestData["beginDate"] = moment(vm.startDate).unix();
                        requestData["endDate"] = moment(vm.endDate).unix();
                    }
                    util.c2s({
                        "url": erp.BASE_PATH + url,
                        "data": requestData,
                        "success": function (responseData) {
                            if (responseData.flag) {
                                callback && callback.call(vm, responseData.data);
                            }
                        }
                    });
                };
                /*vm.getUbUserGridCate = function (cateType) {
                    var cateName = '';
                    switch (cateType) {
                        case "average":
                            cateName = '近90天平均';
                            break;
                        case "hisMax":
                            cateName = '历史最高';
                            break;
                        case "today":
                            cateName = '今天';
                            break;
                        case 'yesterday':
                            cateName = '昨天';
                            break;
                        default:
                            break;
                    }
                    return cateName;
                };*/
                /**
                 * 重新渲染用户和商户统计表格
                 */
                vm.reRenderUbGrid = function (gridData) {
                    var total = gridData.total,
                        statistics = gridData.statistics,
                        userStatisticsData = [],
                        businessStatisticsData = [];
                    if (vm.statisticsState == 'dot') {  //统计今天的
                        vm.totalUserNum = total.userCount;
                        vm.addedUserNum = total.newUserCount;
                        vm.totalBrandNum = total.brandCount;
                        vm.addedBrandNum = total.newBrandCount;
                        vm.totalMerchantNum = total.faceShopCount;
                        vm.addedMerchantNum = total.newFaceShopCount;

                        if (vm.ubState == 'user') {
                            vm.userStatisticsData = _.map(statistics, function (itemData) {
                                if (itemData.cate == 'hisMax') {
                                    _.each(["loginNumHisMaxDate", "loginTimesHisMaxDate", "newNumHisMaxDate"], function (key) {
                                        itemData[key] = moment.unix(itemData[key]).format('YYYY-MM-DD');
                                    });
                                }
                                return itemData;
                            });
                        } else if (vm.ubState == 'business') {
                            vm.businessStatisticsData = _.map(statistics, function (itemData) {
                                if (itemData.cate == 'hisMax') {
                                    _.each(["newBrandHisMaxDate", "activeBrandNumHisMaxDate", "newMerchantHisMaxDate", "activeMerchantNumHisMaxDate"], function (key) {
                                        itemData[key] = moment.unix(itemData[key]).format('YYYY-MM-DD');
                                    });
                                }
                                return itemData;
                            });
                        }
                    } else if (vm.statisticsState == 'range') { //统计时间段
                        vm.addedUserNum = total.newUserCount;
                        vm.addedBrandNum = total.newBrandCount;
                        vm.addedMerchantNum = total.newFaceShopCount;

                        if (vm.ubState == 'user') {
                            vm.userStatisticsData = statistics;
                        } else if (vm.ubState == 'business') {
                            vm.businessStatisticsData = statistics;
                        }
                    }
                };
                /**
                 * 重画用户和商户统计图表
                 */
                vm.redrawUbChart = function (chartData) {
                    var statisticsState = vm.statisticsState,
                        ubState = vm.ubState;
                    //先尝试删除原来的chart
                    vm.ubChart && vm.ubChart.destroy();
                    //重新创建dom容器
                    ubChartPanelEl.html('<div id="' + ubChartHolderId + '"></div>');
                    if (statisticsState == 'dot') { //统计今天
                        if (ubState == 'user') {    //统计用户
                            vm.ubChart = new Highcharts.Chart(avalon.mix(true, {}, defaultChartOpts, {
                                chart: {
                                    renderTo: ubChartHolderId,
                                    type: 'pie'
                                },
                                title: {
                                    text: moment(now).format('YYYY-MM-DD') + avalon.getVm(ubDotUserId).selectedText + '数量'
                                },
                                legend: {
                                    align: 'right',
                                    verticalAlign: 'middle',
                                    width: 200,
                                    layout: 'vertical',
                                    borderWidth: 1,
                                    borderColor: '#e9e9e9',
                                    backgroundColor: '#e9e9e9',
                                    padding: 20,
                                    itemMarginBottom: 10
                                },
                                plotOptions: {
                                    pie: {
                                        dataLabels: {
                                            format: '{point.userCount}<br/>占{point.percentage:.1f} %'
                                        }
                                    }
                                },
                                series: [{
                                    "name": "占比",
                                    "data":  _.map(chartData, function (itemData) {
                                        return {
                                            "name": itemData.role,
                                            "y": itemData.ratio,
                                            "userCount": itemData.userCount
                                        };
                                    })
                                }]
                            }));
                        } else if (ubState == 'business') {
                            vm.ubChart = new Highcharts.Chart(avalon.mix(true, {}, defaultChartOpts, {
                                chart: {
                                    renderTo: ubChartHolderId,
                                    type: 'column'
                                    //spacingTop: 40
                                },
                                title: {
                                    text: '近7日' + avalon.getVm(ubDotBrandId).selectedText + '数量对比'
                                },
                                /*legend: {
                                    align: 'right',
                                    verticalAlign: 'top',
                                    floating: true,
                                    y: -40
                                },*/
                                xAxis: {
                                    categories: chartData.categories
                                },
                                yAxis: {
                                    min: 0,
                                    title: {
                                        text: '总数'
                                    }
                                },
                                tooltip: {
                                    headerFormat: '<span>{point.key}</span><table>',
                                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                                        '<td style="padding:0">&nbsp;<span>{point.y}</span></td></tr>',
                                    footerFormat: '</table>',
                                    shared: true,
                                    useHTML: true
                                },
                                series: chartData.series
                            }));
                        }
                    } else if (statisticsState == 'range') {    //统计时间段
                        if (ubState == 'user') {    //统计用户
                            if (avalon.getVm(ubRangeRdId).selectedValue == "date") {    //按时间分布,线状图显示
                                vm.ubChart = new Highcharts.Chart(avalon.mix(true, {}, defaultChartOpts, {
                                    chart: {
                                        renderTo: ubChartHolderId,
                                        type: 'line'
                                    },
                                    title: {
                                        text: moment.unix(chartData.categories[0]).format('YYYY-MM-DD') + ' 至 ' + moment.unix(chartData.categories[chartData.categories.length - 1]).format('YYYY-MM-DD') + ' ' + avalon.getVm(ubRangeUserId).selectedText
                                    },
                                    xAxis: {
                                        categories: _.map(chartData.categories, function (itemData) {
                                            return moment.unix(itemData).format('YYYY-MM-DD');
                                        }),
                                        labels: {
                                            rotation: chartData.categories.length > 10 ? 45 : 0
                                        }
                                    },
                                    yAxis: {
                                        title: {
                                            text: avalon.getVm(ubRangeUserId).selectedValue == 'loginTimes' ? '次数' : '人数'
                                        }
                                    },
                                    tooltip: {
                                        valueSuffix: ''
                                    },
                                    series: chartData.series
                                }));
                            } else {
                                vm.ubChart = new Highcharts.Chart(avalon.mix(true, {}, defaultChartOpts, {
                                    chart: {
                                        renderTo: ubChartHolderId,
                                        type: 'pie'
                                    },
                                    title: {
                                        text: moment(vm.startDate).format('YYYY-MM-DD') + ' 至 ' + moment(vm.endDate).format('YYYY-MM-DD') + ' ' + avalon.getVm(ubRangeUserId).selectedText + '数量'
                                    },
                                    legend: {
                                        align: 'right',
                                        verticalAlign: 'middle',
                                        width: 200,
                                        layout: 'vertical',
                                        borderWidth: 1,
                                        borderColor: '#e9e9e9',
                                        backgroundColor: '#e9e9e9',
                                        padding: 20,
                                        itemMarginBottom: 10
                                    },
                                    plotOptions: {
                                        pie: {
                                            dataLabels: {
                                                format: '{point.userCount}<br/>占{point.percentage:.1f} %'
                                            }
                                        }
                                    },
                                    series: [{
                                        "name": "占比",
                                        "data":  _.map(chartData, function (itemData) {
                                            return {
                                                "name": itemData.role,
                                                "y": itemData.ratio,
                                                "userCount": itemData.userCount
                                            };
                                        })
                                    }]
                                }));
                            }

                        } else if (ubState == 'business') {
                            vm.ubChart = new Highcharts.Chart(avalon.mix(true, {}, defaultChartOpts, {
                                chart: {
                                    renderTo: ubChartHolderId,
                                    type: 'line'
                                },
                                title: {
                                    text: moment.unix(chartData.categories[0]).format('YYYY-MM-DD') + ' 至 ' + moment.unix(chartData.categories[chartData.categories.length - 1]).format('YYYY-MM-DD') + ' 品牌分布图表'
                                },
                                xAxis: {
                                    categories: _.map(chartData.categories, function (itemData) {
                                        return moment.unix(itemData).format('YYYY-MM-DD');
                                    }),
                                    labels: {
                                        rotation: chartData.categories.length > 10 ? 45 : 0
                                    }
                                },
                                yAxis: {
                                    title: {
                                        text: '品牌'
                                    }
                                },
                                tooltip: {
                                    valueSuffix: ''
                                },
                                series: chartData.series
                            }));
                        }
                    }
                };
                /**
                 *  以下是功能统计配置
                 */
                vm.$funCateOpts = {
                    "selectId": funCateId,
                    "options": [{
                        "text": "全部功能",
                        "value": ""
                    }, {
                        "text": "发送日报",
                        "value": "SEND_REPORT"
                    }, {
                        "text": "查看日报",
                        "value": "VIEW_REPORT"
                    }, {
                        "text": "查看月报",
                        "value": "VIEW_MONTHREPORT"
                    }, {
                        "text": "月报导出",
                        "value": "EXPORT_REPORT"
                    }, {
                        "text": "数据统计",
                        "value": "VIEW_STATISTICS"
                    }, {
                        "text": "发送评论",
                        "value": "SEND_COMMENT"
                    }, {
                        "text": "查看评论",
                        "value": "VIEW_COMMENT"
                    }, {
                        "text": "发送通知",
                        "value": "SEND_NOTICE"
                    }, {
                        "text": "查看通知",
                        "value": "VIEW_NOTICE"
                    }, {
                        "text": "标注",
                        "value": "VIEW_MARK"
                    }],
                    "selectedIndex": 0,
                    "onSelect": function () {
                        vm.fetchFunData('chart', function (data) {
                            vm.redrawFunChart(data);
                        });
                    }
                };
                vm.$funHbOpts = {
                    "selectId": funHbId,
                    "options": [{
                        "text": "全部图表",
                        "value": ""
                    }, {
                        "text": "日环比图",
                        "value": "CHART_DAY"
                    }, {
                        "text": "月同比图",
                        "value": "CHART_MONTH"
                    }/*, {  //暂时去掉
                        "text": "PK",
                        "value": "PK"
                    }*/],
                    "selectedIndex": 0,
                    "onSelect": function () {
                        vm.fetchFunData('chart', function (data) {
                            vm.redrawFunChart(data);
                        });
                    }
                };
                vm.$funSendOpts = {
                    "selectId": funSendId,
                    "options": [{
                        "text": "全部数据",
                        "value": ""
                    }, {
                        "text": "发送日报",
                        "value": "SEND_REPORT"
                    }, {
                        "text": "发送通知",
                        "value": "SEND_NOTICE"
                    }],
                    "selectedIndex": 0,
                    "onSelect": function (val) {
                        if (!val) {
                            avalon.getVm(funGtId).setOptions([{
                                "text": "时间分布",
                                "value": "date"
                            }]);
                        } else {
                            avalon.getVm(funGtId).setOptions([{
                                "text": "功能占比",
                                "value": "function"
                            }, {
                                "text": "时间分布",
                                "value": "date"
                            }]);
                        }
                        vm.fetchFunData('chart', function (data) {
                            vm.redrawFunChart(data);
                        });

                    }
                };
                vm.$funGtOpts = {
                    "selectId": funGtId,
                    "options": [{
                        "text": "功能占比",
                        "value": "function"
                    }, {
                        "text": "时间分布",
                        "value": "date"
                    }],
                    "selectedIndex": 0,
                    "onSelect": function () {
                        vm.fetchFunData('chart', function (data) {
                            vm.redrawFunChart(data);
                        });
                    }
                };
                vm.funStatisticsData = [];
                vm.funState = 'user';   //user times clicks sending
                vm.switchFunState = function () {
                    vm.funState = avalon(this).data('funState');
                };
                vm.$watch('funState', function (val) {
                    if (val == "sending" && !avalon.getVm(funSendId).selectedValue) {
                        avalon.getVm(funGtId).setOptions([{
                            "text": "时间分布",
                            "value": "date"
                        }]);
                    } else {
                        avalon.getVm(funGtId).setOptions([{
                            "text": "功能占比",
                            "value": "function"
                        }, {
                            "text": "时间分布",
                            "value": "date"
                        }]);
                    }
                    //获取用户和商户数据渲染页面
                    vm.fetchFunData('grid', function (data) {
                        vm.reRenderFunGrid(data);
                    });
                    vm.fetchFunData('chart', function (data) {
                        vm.redrawFunChart(data);
                    });

                });
                vm.funChart = null;
                /**
                 * 获取功能统计数据
                 */
                vm.fetchFunData = function (type, callback) {
                    var url,
                        requestData = {};
                    if (type == 'grid') {
                        url = 'new/superReport/functionTableData.do';
                    } else if (type == 'chart') {
                        url = 'new/superReport/functionChartData.do';
                        if (vm.funState == 'user' || vm.funState == 'times') {
                            requestData["path"] = avalon.getVm(funCateId).selectedValue;    //类别
                        } else if (vm.funState == 'clicks') {
                            requestData["path"] = avalon.getVm(funHbId).selectedValue;
                        } else if (vm.funState == 'sending') {
                            requestData["path"] = avalon.getVm(funSendId).selectedValue;
                        }
                        if (vm.statisticsState == 'range') {
                            requestData["queryFlag"] = avalon.getVm(funGtId).selectedValue;
                        } else {
                            requestData["queryFlag"] = "function";  //默认上传功能占比
                        }
                    }
                    //附加type参数
                    requestData["type"] = vm.translateFunState(vm.funState);
                    //附加时间参数
                    if (vm.statisticsState == 'dot') {
                        requestData["beginDate"] = requestData["endDate"] = moment(now).unix();
                    } else if (vm.statisticsState == 'range') {
                        requestData["beginDate"] = moment(vm.startDate).unix();
                        requestData["endDate"] = moment(vm.endDate).unix();
                    }
                    util.c2s({
                        "url": erp.BASE_PATH + url,
                        "data": requestData,
                        "success": function (responseData) {
                            if (responseData.flag) {
                                callback && callback.call(vm, responseData.data);
                            }
                        }
                    });
                };
                /**
                 * 重新渲染功能统计表格
                 */
                vm.reRenderFunGrid = function (gridData) {
                    vm.funStatisticsData = _.map(gridData, function (itemData) {
                        _.each(['sendReportCountHisMaxDate', 'viewReportCountHisMaxDate', 'viewMonthReportCountHisMaxDate', 'exportReportCountHisMaxDate', 'viewStatisticsCountHisMaxDate', 'sendCommentCountHisMaxDate', 'viewCommentCountHisMaxDate', 'sendNoticeCountHisMaxDate', 'viewNoticeCountHisMaxDate', 'viewMarkCountHisMaxDate', 'chartDayCountHisMaxDate', 'chartMonthCountHisMaxDate'], function (key) {
                            if (itemData[key]) {
                                itemData[key] = moment.unix(itemData[key]).format('YYYY-MM-DD');
                            }
                        });
                        return itemData;
                    });
                };
                /**
                 * 重画功能统计图表
                 */
                vm.redrawFunChart = function (chartData) {
                    var statisticsState = vm.statisticsState,
                        funState = vm.funState,
                        cateSelectorId;
                    //先尝试删除原来的chart
                    vm.funChart && vm.funChart.destroy();
                    //重新创建dom容器
                    funChartPanelEl.html('<div id="' + funChartHolderId + '"></div>');
                    if (funState == 'user' || funState == 'times') {
                        cateSelectorId = funCateId;
                    } else if (vm.funState == 'clicks') {
                        cateSelectorId = funHbId;
                    } else if (vm.funState == 'sending') {
                        cateSelectorId = funSendId;
                    }
                    if (statisticsState == 'dot') { //统计今天
                        vm.funChart = new Highcharts.Chart(avalon.mix(true, {}, defaultChartOpts, {
                            chart: {
                                renderTo: funChartHolderId,
                                type: 'pie'
                            },
                            title: {
                                text: moment(now).format('YYYY-MM-DD') + avalon.getVm(cateSelectorId).selectedText
                            },
                            legend: {
                                align: 'right',
                                verticalAlign: 'middle',
                                width: 200,
                                layout: 'vertical',
                                borderWidth: 1,
                                borderColor: '#e9e9e9',
                                backgroundColor: '#e9e9e9',
                                padding: 20,
                                itemMarginBottom: 10
                            },
                            plotOptions: {
                                pie: {
                                    dataLabels: {
                                        format: '{point.userCount}<br/>占{point.percentage:.1f} %'
                                    }
                                }
                            },
                            series: [{
                                "name": "占比",
                                "data":  _.map(chartData, function (itemData) {
                                    return {
                                        "name": itemData.name,
                                        "y": itemData.ratio,
                                        "userCount": itemData.count
                                    };
                                })
                            }]
                        }));
                    } else if (statisticsState == 'range') {
                        if (avalon.getVm(funGtId).selectedValue == 'date') {    //按时间分布是线状图
                            vm.funChart = new Highcharts.Chart(avalon.mix(true, {}, defaultChartOpts, {
                                chart: {
                                    renderTo: funChartHolderId,
                                    type: 'line'
                                },
                                title: {
                                    text: moment.unix(chartData.categories[0]).format('YYYY-MM-DD') + ' 至 ' + moment.unix(chartData.categories[chartData.categories.length - 1]).format('YYYY-MM-DD') + ' ' + avalon.getVm(cateSelectorId).selectedText
                                },
                                xAxis: {
                                    categories: _.map(chartData.categories, function (itemData) {
                                        return moment.unix(itemData).format('YYYY-MM-DD');
                                    }),
                                    labels: {
                                        rotation: chartData.categories.length > 10 ? 45 : 0
                                    }
                                },
                                yAxis: {
                                    title: {
                                        text: '总数'
                                    }
                                },
                                tooltip: {
                                    valueSuffix: ''
                                },
                                series: chartData.series
                            }));
                        } else {
                            vm.funChart = new Highcharts.Chart(avalon.mix(true, {}, defaultChartOpts, {
                                chart: {
                                    renderTo: funChartHolderId,
                                    type: 'pie'
                                },
                                title: {
                                    text: moment(vm.startDate).format('YYYY-MM-DD') + ' 至 ' + moment(vm.endDate).format('YYYY-MM-DD') + ' ' + avalon.getVm(cateSelectorId).selectedText
                                },
                                legend: {
                                    align: 'right',
                                    verticalAlign: 'middle',
                                    width: 200,
                                    layout: 'vertical',
                                    borderWidth: 1,
                                    borderColor: '#e9e9e9',
                                    backgroundColor: '#e9e9e9',
                                    padding: 20,
                                    itemMarginBottom: 10
                                },
                                plotOptions: {
                                    pie: {
                                        dataLabels: {
                                            format: '{point.userCount}<br/>占{point.percentage:.1f} %'
                                        }
                                    }
                                },
                                series: [{
                                    "name": "占比",
                                    "data":  _.map(chartData, function (itemData) {
                                        return {
                                            "name": itemData.name,
                                            "y": itemData.ratio,
                                            "userCount": itemData.count
                                        };
                                    })
                                }]
                            }));
                        }
                    }
                };
                /**
                 * 刷新整个页面
                 */
                vm.refreshPage = function () {
                    //获取用户和商户数据渲染页面
                    vm.fetchUbData('grid', function (data) {
                        vm.reRenderUbGrid(data);
                    });
                    vm.fetchUbData('chart', function (data) {
                        vm.redrawUbChart(data);
                    });
                    //获取功能统计数据渲染页面
                    vm.fetchFunData('grid', function (data) {
                        vm.reRenderFunGrid(data);
                    });
                    vm.fetchFunData('chart', function (data) {
                        vm.redrawFunChart(data);
                    });
                };
                /**
                 * 翻译功能统计状态
                 */
                vm.translateFunState = function (funState) {
                    var translateValue = '';
                    switch (funState) {
                        case "user":
                            translateValue = 'personCount';
                            break;
                        case "times":
                            translateValue = 'count';
                            break;
                        case "clicks":
                            translateValue = 'clickCount';
                            break;
                        case "sending":
                            translateValue = 'sendCount';
                            break;
                        default:
                            break;
                    }
                    return translateValue;
                };
            });
            avalon.scan(pageEl[0]);
            avalon.nextTick(function () {
                pageVm.refreshPage();
            });

            //日历联动
            avalon.getVm(calendar3Id).$watch('focusDate', function (val) {
                avalon.getVm(calendar2Id).focusDate = moment(val).subtract('months', 1)._d;
                avalon.getVm(calendar1Id).focusDate = moment(val).subtract('months', 2)._d;
            });
            //全局隐藏calendar panel
            util.regGlobalMdExcept({
                "element": calendarSelectorPanelEl,
                "handler": function () {
                    pageVm.closeCalendarPanel();
                }
            });
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var ubDotUserId = pageName + '-ub-dot-user',
                ubDotBrandId = pageName + '-ub-dot-brand',
                ubRangeUserId = pageName + '-ub-range-user',
                ubRangeRdId = pageName + '-ub-range-rd',
                calendar1Id = pageName + '-calendar-1',
                calendar2Id = pageName + '-calendar-2',
                calendar3Id = pageName + '-calendar-3',
                funCateId = pageName + '-fun-cate',
                funHbId = pageName + '-fun-hb',
                funSendId = pageName + 'fun-send',
                funGtId = pageName + 'fun-gt';
            var pageVm = avalon.getVm(pageName);

            $(avalon.getVm(ubDotUserId).widgetElement).remove();
            $(avalon.getVm(ubDotBrandId).widgetElement).remove();
            $(avalon.getVm(ubRangeUserId).widgetElement).remove();
            $(avalon.getVm(calendar1Id).widgetElement).remove();
            $(avalon.getVm(calendar2Id).widgetElement).remove();
            $(avalon.getVm(calendar3Id).widgetElement).remove();
            $(avalon.getVm(funCateId).widgetElement).remove();
            $(avalon.getVm(funHbId).widgetElement).remove();
            $(avalon.getVm(funSendId).widgetElement).remove();
            $(avalon.getVm(funGtId).widgetElement).remove();
            //尝试销毁图表
            pageVm.ubChart && pageVm.ubChart.destroy();
            pageVm.funChart && pageVm.funChart.destroy();
            pageVm.ubChart = null;
            pageVm.funChart = null;
        }
    });

    return pageMod;
});
