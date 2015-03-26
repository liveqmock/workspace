/**
 * 首页
 */
define(['avalon', 'util', 'json', 'moment', 'highcharts', '../../widget/dialog/dialog', '../../widget/form/form'], function (avalon, util, JSON, moment, Highcharts) {
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
            var devicePanelEl = $('.device-panel', pageEl),
                realTimePanelEl = $('.real-time-panel', pageEl),
                deviceLinkChartWEl = $('.chart-wrapper', devicePanelEl),
                addBwDialogId = pageName + '-add-bw-dialog',
                bwFormId = pageName + '-add-bw-form',
                streamChartWEl = $('.chart-wrapper', realTimePanelEl);
            var deviceLinkChartId = pageName + '-device-link',
                streamChartId = pageName + '-stream';
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
                vm.$skipArray = ['deviceLinkChart'];
                vm.dialogType = "";
                vm.deviceList = [/*{
                 "deviceName": "设备1",
                 "linkNum": 30,
                 "usedRatio": "10%"
                 }, {
                 "deviceName": "设备2",
                 "linkNum": 60,
                 "usedRatio": "10%"
                 }, {
                 "deviceName": "设备3",
                 "linkNum": 60,
                 "usedRatio": "10%"
                 }, {
                 "deviceName": "设备4",
                 "linkNum": 60,
                 "usedRatio": "10%"
                 }, {
                 "deviceName": "设备5",
                 "linkNum": 60,
                 "usedRatio": "10%"
                 }*/];
                vm.deviceNum = 0;   //设备总数
                vm.whitelistUserNum = 0;    //白名单人数
                vm.blacklistUserNum = 0;    //店员名单人数
                vm.firstDeviceIndex = 0;    //设备连接详情列表第一个设备的index
                vm.deviceNavHandler = function () {    //向左导航
                    var meEl = $(this),
                        detailViewEl = $('.detail-view', devicePanelEl),
                        deviceListEl = $('.device-list', detailViewEl),
                        itemWidth = $('.device-item', deviceListEl).width(),
                        nextDeviceIndex;
                    if (meEl.hasClass('state-enabled')) {
                        if (meEl.hasClass('nav-left')) {
                            nextDeviceIndex = vm.firstDeviceIndex - 1;
                        } else {
                            nextDeviceIndex = vm.firstDeviceIndex + 1;
                        }
                        deviceListEl.stop(true, true).animate({
                            "left": -(itemWidth * nextDeviceIndex) + 'px'
                        }, 'fast', function () {
                            vm.firstDeviceIndex = nextDeviceIndex;
                        });
                    }
                };
                /**
                 * 重绘设备连接图
                 */
                vm.deviceLinkChart = null;
                vm.redrawDeviceLinkChart = function (chartData) {
                    //先尝试删除原来的chart
                    vm.deviceLinkChart && vm.deviceLinkChart.destroy();
                    var totalRatio = 0;
                    _.each(chartData, function (itemData) {
                        totalRatio += itemData.ratio;
                    });
                    //重新创建dom容器
                    deviceLinkChartWEl.html('<div id="' + deviceLinkChartId + '" class="device-chart"></div>');
                    vm.deviceLinkChart = new Highcharts.Chart(avalon.mix(true, {}, defaultChartOpts, {
                        chart: {
                            plotBackgroundColor: null,
                            plotBorderWidth: 0,
                            plotShadow: false,
                            height: 180,
                            spacing: [0, 0, 0, 0],
                            marginTop: -20,
                            renderTo: deviceLinkChartId
                        },
                        title: {
                            useHTML: true,
                            text: '<div style="text-align: center;"><p style="padding-bottom: 10px; border-bottom: 1px solid #c8c8c8; line-height: 1em; margin-left: 20px; margin-right: 20px;">使用总量</p><p style="padding-left: 22px; color: #586a70;"><span style="font-size: 60px; line-height: 1em; vertical-align: bottom;">' + totalRatio + '</span><span style="font-size: 26px; vertical-align: bottom;">%</span></p></div>',
                            align: 'center',
                            verticalAlign: 'middle',
                            y: -36
                        },
                        tooltip: {
                            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                        },
                        plotOptions: {
                            pie: {
                                dataLabels: {
                                    enabled: false
                                },
                                startAngle: 0,
                                endAngle: 360,
                                center: ['50%', '50%'],
                                showInLegend: false
                            }
                        },
                        series: [
                            {
                                type: 'pie',
                                name: '占比',
                                innerSize: '100%',
                                data: _.map(chartData, function (itemData) {
                                    return {
                                        "name": itemData.name,
                                        "y": itemData.ratio
                                    };
                                })
                            }
                        ]
                    }));
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
                            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                                'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
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
                            borderWidth: 0
                        },
                        series: [
                            {
                                name: 'Tokyo',
                                data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
                            },
                            {
                                name: 'New York',
                                data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
                            },
                            {
                                name: 'Berlin',
                                data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
                            },
                            {
                                name: 'London',
                                data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
                            }
                        ]
                    }));
                };
                /**
                 * 更新设备连接情况统计
                 */
                vm.updateDeviceLinkData = function () {
                    util.c2s({
                        "url": app.BASE_PATH + 'controller/device/getConnetNum.do',
                        "success": function (responseData) {
                            var rows,
                                chartData = [];
                            if (responseData.flag) {
                                rows = responseData.data.oneUseInfo;
                                vm.deviceList = _.map(rows, function (itemData, i) {
                                    chartData.push({
                                        "name": itemData.devName,
                                        "ratio": parseFloat(itemData.percent, 10)
                                    });
                                    return {
                                        "deviceName": itemData.devName,
                                        "linkNum": itemData.count,
                                        "usedRatio": itemData.percent,
                                        "color": defaultChartOpts.colors[i]
                                    };
                                });
                                //重画设备连接设备
                                vm.redrawDeviceLinkChart(chartData);
                                //设置设备总数
                                vm.deviceNum = rows.length;
                            }
                        }
                    });
                };
                vm.deviceListVisibleHandler = function () {
                    var listMoreEl = $('.total-analysis .device-list-more', devicePanelEl),
                        meEl = $(this);
                    var state = meEl.data('state');
                    if (state === "down") {
                        listMoreEl.slideDown('fast', function () {
                            meEl.data('state', 'up');
                            meEl.html('&#xe60e;');
                        });
                    } else {
                        listMoreEl.slideUp('fast', function () {
                            meEl.data('state', 'down');
                            meEl.html('&#xe60f;');
                        });
                    }
                };
                vm.actionVisibleHandler = function () {
                    var meEl = $(this),
                        arrowEl = $('.icon-arrow', meEl),
                        listEl = meEl.siblings('.action-list');
                    var state = meEl.data('state');
                    if (state === "down") {
                        listEl.slideDown('fast', function () {
                            meEl.data('state', 'up');
                        });
                        arrowEl.html('&#xe60a;');
                    } else {
                        listEl.slideUp('fast', function () {
                            meEl.data('state', 'down');
                        });
                        arrowEl.html('&#xe609;');
                    }
                };
                /**
                 * 筛选实时流量看板
                 */
                vm.filterRealTimeCate = function () {
                    var meEl = avalon(this),
                        cate = meEl.data('cate');
                    vm.realTimeCate = cate;
                };
                /**
                 * 更新白名单和黑名单人数信息
                 */
                vm.updateUserNum = function () {
                    util.c2s({
                        "url": app.BASE_PATH + 'controller/networkmanagement/getBlackWhiteCountNum.do',
                        "data": {
                            "type": 1 //黑名单
                        },
                        "success": function (responseData) {
                            if (responseData.flag) {
                                vm.blacklistUserNum = responseData.data;
                            }
                        }
                    }, {
                        "mask": false
                    });
                    util.c2s({
                        "url": app.BASE_PATH + 'controller/networkmanagement/getBlackWhiteCountNum.do',
                        "data": {
                            "type": 2 //白名单
                        },
                        "success": function (responseData) {
                            if (responseData.flag) {
                                vm.whitelistUserNum = responseData.data;
                            }
                        }
                    }, {
                        "mask": false
                    });
                };
                //添加黑白名单dialog
                vm.$addBwDialogOpts = {
                    "dialogId": addBwDialogId,
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
                        var formVm = avalon.getVm(bwFormId);
                        formVm.reset();
                        formVm.managerPsd = "";
                        formVm.phoneNum = "";
                    },
                    "submit": function () {
                        var requestData,
                            url,
                            arr = [],
                            dialogVm = avalon.getVm(addBwDialogId),
                            formVm = avalon.getVm(bwFormId);
                        if (formVm.validate()) {
                            requestData = formVm.getFormData();
                            switch (pageVm.dialogType) {
                                case "white":
                                    arr.push(requestData.phoneNum);
                                    _.extend(requestData, {"type": 2, "actionType": 1, "phoneNum": arr});
                                    url = app.BASE_PATH + "controller/networkmanagement/addOrUpdateBlackWhiteList.do";
                                    break;
                                case "black":
                                    arr.push(requestData.phoneNum);
                                    _.extend(requestData, {"type": 1, "actionType": 1, "phoneNum": arr});
                                    url = app.BASE_PATH + "controller/networkmanagement/addOrUpdateBlackWhiteList.do";
                                    break;
                            }
                            util.c2s({
                                "url": url,
                                "data": requestData,
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        pageVm.updateUserNum();
                                        dialogVm.close();
                                    }
                                }
                            });
                        }
                    }
                };
                //添加网址和APP Form
                vm.$addBwFormOpts = {
                    "formId": bwFormId,
                    "field": [
                        {
                            "selector": ".manager-psd",
                            "name": "managerPsd",
                            "excludeHidden": true,
                            "rule": ["noempty", function (val, rs) {
                                if (rs[0] !== true) {
                                    return "主管密码不能为空！";
                                } else {
                                    return true;
                                }
                            }]
                        },
                        {
                            "selector": ".phone-num",
                            "name": "phoneNum",
                            "excludeHidden": true,
                            "rule": ["noempty", function (val, rs) {
                                if (rs[0] !== true) {
                                    return "手机号码不能为空";
                                } else if (!val.match(/^[0-9]{11}$/g)) {
                                    return "请输入正确的手机号码！"
                                } else {
                                    return true;
                                }
                            }]
                        }

                    ],
                    "managerPsd": "",
                    "phoneNum": ""
                };
                //打开弹层
                vm.openWebDialog = function () {
                    var dialogVm = avalon.getVm(addBwDialogId);
                    pageVm.dialogType = $(this).data('type');
                    switch (pageVm.dialogType) {
                        case "white":
                            dialogVm.title = "添加白名单用户";
                            break;
                        case "black":
                            dialogVm.title = "添加店员名单";
                            break;
                    }
                    dialogVm.open();
                };
            });
            avalon.scan(pageEl[0]);
            pageVm.updateDeviceLinkData();
            pageVm.updateUserNum(); //更新黑白名单信息

            util.regGlobalMdExcept({
                "element": $('.whitelist-action', devicePanelEl),
                "handler": function () {
                    var actionEl = $('.whitelist-action', devicePanelEl),
                        titleEl = $('.action-title', actionEl),
                        arrowEl = $('.icon-arrow', actionEl);
                    titleEl.data('state', 'down');
                    arrowEl.html('&#xe609;');
                    $('.action-list', actionEl).hide();
                }
            });
            util.regGlobalMdExcept({
                "element": $('.blacklist-action', devicePanelEl),
                "handler": function () {
                    var actionEl = $('.blacklist-action', devicePanelEl),
                        titleEl = $('.action-title', actionEl),
                        arrowEl = $('.icon-arrow', actionEl);
                    titleEl.data('state', 'down');
                    arrowEl.html('&#xe609;');
                    $('.action-list', actionEl).hide();
                }
            });

            page.pageEvent.on('inited', function (pageEl, pageName2) {
                //保证图表自适应尺寸
                if (pageName2 == pageName) {
                    //pageVm.deviceLinkChart.reflow();
                    pageVm.redrawStreamChart();
                    page.pageEvent.off('inited', arguments.callee); //保证只执行一次
                }
            });
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var addBwDialogId = pageName + '-add-bw-dialog',
                bwFormId = pageName + '-add-bw-form';
            $(avalon.getVm(addBwDialogId).widgetElement).remove();
            $(avalon.getVm(bwFormId).widgetElement).remove();
        }
    });

    return pageMod;
});
