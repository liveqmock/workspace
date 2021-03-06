jQuery(function ($) {
    //设置基准字大小
    var winEl = $(window);
    winEl.on('resize', function () {
        var winW = winEl.width();
        //保证不超过640px宽度
        if (winW > 640) {
            winW = 640;
        }
        $('html').css({
            "font-size": 24 * winW / 640 + 'px'
        });
    }).resize();
    //默认的chart配置项
    var defaultChartOpts = {
        chart: {
            style: {
                fontFamily: 'Helvetica Neue", Helvetica, STHeiTi, sans-serif', // default font
fontSize: '14px',
color: '#444444'
            }
        },
        credits: {
            "enabled": false
        },
        exporting: {
            "enabled": false
        },
        colors: ['#7bc6d6', '#fca753'],
        title: {
            "style": {
                "color": "#444444",
                "fontSize": "14px"
            }
        },
        legend: {
            "itemStyle": {
                "color": "#444444",
                "fontSize": "14px",
                "fontWeight": "normal"
            }
        },
        labels: {
            "style": {
                "color": "#444444",
                "fontSize": "14px"
            }
        },
        tooltip: {
            "style": {
                "color": "#444444",
                "fontSize": "14px",
                "fontWeight": "normal"
            },
            "headerFormat": ''
        },
        plotOptions: {
            pie: {
                dataLabels: {
                    "style": {
                        "color": "#444444",
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
                color: '#444444'
            }]
        }
    };

    //初始化变量
    var monthMemberNumEl = $('.month-member-num'),  //本月新入会员
        yearMemberNumEl = $('.year-member-num'),    //本年会员
        monthStorageNumEl = $('.month-storage-num'),    //本月新增储值
        totalStorageNumEl = $('.total-storage-num'),    //储值沉淀
        deskRateEl = $('.desk-rate'),   //会员开台数
        incomeNumEl = $('.income-num'), //营销收入
        yearNumEl = $('.year-num'), //统计年份
        monthNumEl = $('.month-num'),   //统计月份
        newMemberTipEl = $('.new-member-tip'),
        newStorageTipEl = $('.new-storage-tip'),
        reportPageEl = $('.report-page'),
        errorPageEl = $('.error-page'),
        expiredPageEl = $('.expired-page');

    //请求数据
    var searchStr = location.search,
        searchArr,
        key = "";
    if (searchStr) {
        searchArr = searchStr.slice(1).split('&');
        _.some(searchArr, function (itemData) {
            var pArr = itemData.split('=');
            if (pArr[0] === "key") {
                key = pArr[1];
                return true;
            }
        });
    }
    $.ajax({
        "url": '../../../../../report/marketing.do',
        "type": "post",
        "dataType": "json",
        "data": JSON.stringify({
            "key": key
        }),
        "contentType": 'application/json',
        "success": function (responseData) {
            var data,
                dateObj;
            if (responseData.flag) {
                $('html, body').removeClass('page-full');
                data = responseData.data;
                if (data.expired) {
                    $('html, body').addClass('page-full');
                    expiredPageEl.show();
                    return;
                }
                reportPageEl.show();
                dateObj = moment(data.month);
                monthMemberNumEl.text(data.membershipNumOfMon);
                yearMemberNumEl.text(data.membershipNumOfYear);
                monthStorageNumEl.text(data.storedValueOfMon.toFixed(2));
                totalStorageNumEl.text(data.storedValueOfFormer.toFixed(2));
                deskRateEl.text((data.OpenNumPercent * 100).toFixed(2) + '%');
                incomeNumEl.text(data.marketingIncome.toFixed(2));
                yearNumEl.text(dateObj.year());
                monthNumEl.text(dateObj.month()+1);
                if (data.membershipNumOfMon < data.membershipNumOfLastMon) {
                    newMemberTipEl.text('有下滑');
                } else {
                    newMemberTipEl.text('有进步哦');
                }
                if (data.storedValueOfMon < data.storedValueOfLastMon) {
                    newStorageTipEl.text('有下滑');
                } else {
                    newStorageTipEl.text('有进步哦');
                }
                //图表统计
                var memberChart = new Highcharts.Chart($.extend(true, {}, defaultChartOpts, {
                    chart: {
                        renderTo: 'member-chart',
                    type: 'column',
                    backgroundColor: '#EDF5FD'
                    },
                    title: {
                        text: null
                    },
                    subtitle: {
                        text: null
                    },
                    plotOptions: {
                        column: {
                            dataLabels: {
                                enabled: true,
                                style: {
                                    "fontSize": "18px"
                                }
                            }
                        }
                    },
                    xAxis: {
                        labels: {
                            enabled: false
                        }
                    },
                    yAxis: {
                        opposite: false,
                        title: {
                            "text": null
                        }
                    },
                    series: [{
                        name: (dateObj.clone().month() === 0 ? 12 : dateObj.clone().month()) + '月',
                        data: [data.membershipNumOfLastMon]
                    }, {
                        name: dateObj.month()+1 + '月',
                        data: [data.membershipNumOfMon]
                    }]
                }));
                var storageChart = new Highcharts.Chart($.extend(true, {}, defaultChartOpts, {
                    chart: {
                        renderTo: 'storage-chart',
                    type: 'column',
                    backgroundColor: '#E0EDFC'
                    },
                    title: {
                        text: null
                    },
                    subtitle: {
                        text: null
                    },
                    plotOptions: {
                        column: {
                            dataLabels: {
                                enabled: true,
                                style: {
                                    "fontSize": "18px"
                                }
                            }
                        }
                    },
                    xAxis: {
                        labels: {
                            enabled: false
                        }
                    },
                    yAxis: {
                        opposite: false,
                        title: {
                            "text": null
                        }
                    },
                    series: [{
                        name: (dateObj.clone().month() === 0 ? 12 : dateObj.clone().month()) + '月',
                        data: [data.storedValueOfLastMon]
                    }, {
                        name: dateObj.month()+1 + '月',
                        data: [data.storedValueOfMon]
                    }]
                }));
                var deskChart = new Highcharts.Chart($.extend(true, {}, defaultChartOpts, {
                    chart: {
                        renderTo: 'desk-chart',
                    type: 'pie',
                    backgroundColor: '#EDF5FD'
                    },
                    title: {
                        text: null
                    },
                    subtitle: {
                        text: null
                    },
                    tooltip: {
                        pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: true,
                                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                                style: {
                                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                                }
                            }
                        }
                    },
                    xAxis: {
                        labels: {
                            enabled: false
                        }
                    },
                    yAxis: {
                        opposite: false,
                        title: {
                            "text": null
                        }
                    },
                    series: [{
                        name: '',
                        data: [[
                            "非会员开台", (1-data.OpenNumPercent)
                        ], [
                            "会员开台", data.OpenNumPercent
                        ]]
                    }]
                }));
            } else {
                $('html, body').addClass('page-full');
                errorPageEl.show();
            }
        }
    });
});
