/**
 * 会员查询
 */
define(['avalon', 'util', 'json', 'moment', 'highcharts', '../../widget/dialog/dialog', '../../widget/form/form', '../../widget/calendar/calendar'], function (avalon, util, JSON, moment, Highcharts) {
    var win = this,
        app = win.app,
        page = app.page,
        pageMod = {};
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName, routeData) {
            var realTimePanelEl = $('.real-time-panel', pageEl),
                exportDialogId = pageName + '-export-dialog',
                exportFormId = pageName + '-export-form',
                calendarId = pageName + '-calendar',
                streamChartWEl = $('.chart-wrapper', realTimePanelEl);
            var streamChartId = pageName + '-stream';
            //默认的chart配置项
            var defaultChartOpts = {
                chart: {
                    style: {
                        fontFamily: 'tahoma,arial,"Hiragino Sans GB","微软雅黑","Microsoft YaHei","WenQuanYi Micro Hei","\5b8b\4f53"', // default font
                        fontSize: '14px',
                        color: '#666666'
                    },
                    backgroundColor: '#ffffff'
                },
                credits: {
                    "enabled": false
                },
                exporting: {
                    "enabled": false
                },
                colors: ['#7fc343', '#16a363', '#558ac5', '#2097a9', '#eca22d', '#f26f2f', '#36c0a1', '#f0575a', '#d5749f', '#ad725b'],
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
                    plotLines: [
                        {
                            value: 0,
                            width: 1,
                            color: '#748186'
                        }
                    ]
                }
            };
            var pageVm = avalon.define(pageName, function (vm) {
                vm.editOrSave = "编辑";
                vm.showMessageSet = false;
                vm.isSendMessage = true;
                vm.currentMessage = "";
                vm.newMember = 0;
                vm.totalWifi = 0;
                vm.totalNewMember = 0;
                vm.wifiStart = "";
                vm.wifiEnd = "";
                vm.oldMessage = "";
                vm.showMessage = function () {
                    if (pageVm.editOrSave == "保存") {
                        util.c2s({
                            "url": app.BASE_PATH + "controller/member/setMemberSendMobile.do",
                            "data": {"isSendMemStatic": pageVm.isSendMessage, 'sendMobile': pageVm.currentMessage},
                            "success": function (responseData) {
                                if (responseData.flag) {
                                    pageVm.showMessageSet = false;
                                    pageVm.editOrSave = "编辑";
                                }
                            }
                        });
                    } else {
                        pageVm.showMessageSet = true;
                        pageVm.editOrSave = "保存";
                    }
                };
                vm.messageCheck = function () {
                    pageVm.isSendMessage = pageVm.isSendMessage ? false : true;
                };
                vm.cancelMessage = function () {
                    pageVm.showMessageSet = false;
                    pageVm.editOrSave = "编辑";
                    initPhone();
                };
                vm.startDate = "";
                vm.endDate = "";
                vm.$focusDateInputName = ""; //start or end，指示焦点在哪个时间输入框
                vm.$calendarOpts = {
                    "calendarId": calendarId,
                    "onClickDate": function (d) {
                        vm[vm.$focusDateInputName + "Date"] = moment(d).format('YYYY-MM-DD');
                        $(this.widgetElement).hide();
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
                    vm.$focusDateInputName = dateName;
                    calendarEl.css({
                        "top": inputOffset.top + meEl.outerHeight() - 1,
                        "left": inputOffset.left
                    }).show();
                };
                /**
                 * 重绘实时流量图
                 */
                vm.realTimeCate = "minute";
                vm.streamChart = null;
                vm.redrawStreamChart = function (chartData) {
                    //先尝试删除原来的chart
                    vm.streamChart && vm.streamChart.destroy();
                    //重新创建dom容器
                    streamChartWEl.html('<div id="' + streamChartId + '" class="stream-chart"></div>');
                    vm.streamChart = new Highcharts.Chart(avalon.mix(true, {}, defaultChartOpts, {
                        chart: {
                            height: 240,
                            spacing: [0, 0, 0, 0],
                            renderTo: streamChartId
                        },
                        title: {
                            text: '&nbsp;',
                            useHTML: true
                        },
                        subtitle: {
                            text: ' '
                        },
                        plotOptions: {
                            "line": {
                                "marker": {
                                    "symbol": "circle"
                                }
                            }
                        },
                        xAxis: {
                            lineColor: "#748186",
                            gridLineColor: "#d0d4d8",
                            gridLineWidth: 1,
                            labels: {
                                style: {
                                    color: "#748186"
                                }
                            },
                            //gridLineDashStyle: "Solid",
                            categories: chartData.categories
                        },
                        yAxis: {
                            //minorTickInterval: 'auto',
                            lineColor: "#748186",
                            lineWidth: 1,
                            gridLineColor: "#e0e3e4",
                            gridLineWidth: 1,
                            gridLineDashStyle: "Dot",
                            labels: {
                                style: {
                                    color: "#748186"
                                }
                            },
                            title: {
                                text: null
                            },
                            plotLines: [
                                {
                                    value: 0,
                                    width: 1,
                                    color: '#808080'
                                }
                            ]
                        },
                        tooltip: {
                            /*useHTML: true,
                             shape: 'square',
                             formatter: function () {
                             return '<div style="background: ' + this.series.color + '; padding: 4px 6px; position: relative; color: #ffffff;">' + this.y + '<i class="iconfont" style="position: absolute; bottom: -10px; left: 50%; margin-left: -8px; color: ' + this.series.color + '">&#xe608;</i></div>';
                             },
                             style: {
                             "padding": "0px"
                             }*/
                            formatter: function () {
                                return this.y;
                            }
                        },
                        legend: {
                            //layout: 'vertical',
                            itemStyle: {
                                "fontSize": "13px"
                            },
                            align: 'bottom',
                            //verticalAlign: 'middle',
                            borderWidth: 0,
                            enabled: false
                        },
                        series: [
                            {
                                'name': 'fff',
                                'data': chartData.data
                            }
                        ]
                    }));
                };
                //添加修改用户dialog
                vm.$exportDialogOpts = {
                    "dialogId": exportDialogId,
                    "width": 500,
                    "title": '导出会员资料',
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
                        pageVm.startDate = "";
                        pageVm.endDate = "";
                    },
                    "submit": function () {
                        var requestData,
                            dialogVm = avalon.getVm(exportDialogId),
                            formVm = avalon.getVm(exportFormId);
                        if (formVm.validate()) {
                            requestData = formVm.getFormData();
                            util.c2s({
                                "url": app.BASE_PATH + "controller/member/exportMemberInfo.do",
                                "data": requestData,
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        util.jumpPage(responseData.data.imageAddr);
                                        dialogVm.close();
                                    }
                                }
                            });
                        }
                    }
                };
                //导出 Form
                vm.$exportFormOpts = {
                    "formId": exportFormId,
                    "field": [
                        {
                            "selector": ".start-date",
                            "name": "startTime",
                            "rule": [ function () {
                                if (pageVm.startDate == "") {
                                    return "日期不能为空！";
                                } else {
                                    return true;
                                }
                            }],
                            "getValue": function () {
                                return moment(pageVm.startDate, 'YYYY-MM-DD') / 1;
                            }
                        },
                        {
                            "selector": ".end-date",
                            "name": "endTime",
                            "rule": [ function () {
                                if (pageVm.endDate == "") {
                                    return "日期不能为空！";
                                } else {
                                    return true;
                                }
                            }],
                            "getValue": function () {
                                return moment(pageVm.endDate, 'YYYY-MM-DD') / 1;
                            }
                        }
                    ]
                };
                //打开弹层
                vm.openDialog = function () {
                    var dialogVm = avalon.getVm(exportDialogId);
                    dialogVm.open();
                };
            });
            avalon.scan(pageEl[0]);
            initChart();
            initPhone();
            function initChart() {
                util.c2s({
                    "url": app.BASE_PATH + 'controller/member/memberAddStatic.do',
                    "data": {'startTime': '', 'endTime': ''},
                    "success": function (responseData) {
                        var rows;
                        if (responseData.flag) {
                            rows = responseData.data;
                            pageVm.newMember = rows.todayCount;
                            pageVm.totalWifi = rows.loginCount;
                            pageVm.totalNewMember = rows.totalCount;
                            pageVm.wifiStart = rows.startTime;
                            pageVm.wifiEnd = rows.endTime;
                            pageVm.redrawStreamChart({
                                "categories": rows.dateList,
                                "data": rows.countList
                            });
                        }
                    }
                });
            }

            function initPhone() {
                util.c2s({
                    "url": app.BASE_PATH + 'controller/member/getBossMobile.do',
                    "success": function (responseData) {
                        var rows;
                        if (responseData.flag) {
                            rows = responseData.data;
                            pageVm.isSendMessage = rows.isSendMemStatic || "";
                            pageVm.currentMessage = rows.sendMemStatic || "";
                            pageVm.oldMessage = rows.sendMemStatic || "";
                        }
                    }
                });
            }
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var exportDialogId = pageName + '-export-dialog',
                exportFormId = pageName + '-export-form',
                calendarId = pageName + '-calendar';
            var calendarVm = avalon.getVm(calendarId);
            calendarVm && $(calendarVm.widgetElement).remove();
            $(avalon.getVm(exportFormId).widgetElement).remove();
            $(avalon.getVm(exportDialogId).widgetElement).remove();
        }
    });

    return pageMod;
});
