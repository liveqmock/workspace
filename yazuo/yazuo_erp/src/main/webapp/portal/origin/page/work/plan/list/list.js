/**
 * 工作计划列表
 */
define(['avalon', 'util', 'moment', 'json', '../../../../widget/calendar/calendar'], function (avalon, util, moment, JSON) {
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
            var currentDateCalendarId = pageName + '-current-date-calendar';
            var now = new Date();
            var loginUserData = erp.getAppData('user');

            var pageVm = avalon.define(pageName, function (vm) {
                vm.belongToRouteName = routeData.route.split('/')[1];   //所属主导航路由，可能是通过不同的路由定位到这个页面
                if (vm.belongToRouteName === "work") {
                    vm.navCrumbs = [{
                        "text": "工作计划",
                        "href": "#/work/plan"
                    }, {
                        "text": "工作列表"
                    }];
                } else if (vm.belongToRouteName === "frontend") {
                    vm.navCrumbs = [{
                        "text": "我的主页",
                        "href": "#/frontend/home"
                    }, {
                        "text": "工作列表"
                    }];
                } else if (vm.belongToRouteName === "sales") {
                    vm.navCrumbs = [{
                        "text": "我的主页",
                        "href": "#/sales/home"
                    }, {
                        "text": "工作列表"
                    }];
                }
                vm.viewType = routeData.params["id"] ? "single" : "list";   //查看类型，single只查看单独plan，list查看全部plan
                vm.currentDate = moment(routeData.params["date"] / 1)._d;
                vm.planList = [];
                vm.$currentDateOpts = {
                    "calendarId": currentDateCalendarId,
                    "onClickDate": function (d) {
                        vm.currentDate = d;
                        $(this.widgetElement).hide();
                    }
                };
                /**
                 * 打开当前日期选择面板
                 */
                vm.openCurrentDateCalendar = function () {
                    var meEl = $(this),
                        calendarVm = avalon.getVm(currentDateCalendarId),
                        calendarEl,
                        inputOffset = meEl.offset();
                    if (!calendarVm) {
                        calendarEl = $('<div ms-widget="calendar,$,$currentDateOpts"></div>');
                        calendarEl.css({
                            "position": "absolute",
                            "z-index": 10000
                        }).hide().appendTo('body');
                        avalon.scan(calendarEl[0], [vm]);
                        calendarVm = avalon.getVm(currentDateCalendarId);
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
                    if (vm.currentDate) {
                        calendarVm.focusDate = vm.currentDate;
                    } else {
                        calendarVm.focusDate = now;
                    }

                    calendarEl.css({
                        "top": inputOffset.top + meEl.outerHeight() - 1,
                        "left": inputOffset.left - (calendarEl.outerWidth() - meEl.outerWidth())
                    }).show();
                };
                /**
                 * 天减1
                 */
                vm.navPrev = function () {
                    vm.currentDate = moment(vm.currentDate).subtract('days', 1)._d;
                };
                /**
                 * 天加1
                 */
                vm.navNext = function () {
                    vm.currentDate = moment(vm.currentDate).add('days', 1)._d;
                };
                /**
                 * 导航到今天
                 */
                vm.navToday = function () {
                    vm.currentDate = moment(now).hour(0).minute(0).second(0)._d;
                };
            });
            avalon.scan(pageEl[0]);
            updatePage();
            //当前时间变化刷新列表
            pageVm.$watch('currentDate', function () {
                updatePage();
            });

            function updatePage () {
                if (routeData.params["id"]) {   //查看单独某一项
                    util.c2s({
                        "url": erp.BASE_PATH + "fesPlan/queryFesPlanById.do",
                        "type": "post",
                        "contentType":"application/json",
                        "data": JSON.stringify({
                            "planId": routeData.params["id"] / 1
                        }),
                        "success": function (responseData) {
                            var data;
                            if (responseData.flag) {
                                data = responseData.data;
                                pageVm.planList = [{
                                    "statusText": translateStatus(data.status),
                                    "status": data.status,
                                    "dateIndex": moment(data.start_time).format('YYYY-MM-DD HH:mm'),
                                    "sponsor": data.sponsor_name,
                                    "merchantName": data.merchant_name,
                                    "contactName": data.contact_name,
                                    "title": data.title,
                                    "description": data.description,
                                    "explanation": data.explanation,
                                    "attachmentList": _.map(data.attachmentList, function (itemData2) {
                                        itemData2["downloadUrl"] = erp.BASE_PATH + 'fesOnlineProcess/download.do?relPath=' + data.planFilePath + '/' + itemData2.attachment_name;
                                        return itemData2;
                                    })
                                }];
                            }
                        }
                    });
                } else {    //查看当天所有计划
                    util.c2s({
                        "url": erp.BASE_PATH + "fesPlan/queryDailyFesPlanList.do",
                        "type": "post",
                        "contentType":"application/json",
                        "data": JSON.stringify({
                            "userId": loginUserData.id,
                            "startTime": pageVm.currentDate / 1,
                            "endTime": moment(pageVm.currentDate).add('days', 1).subtract('seconds', 1) / 1
                        }),
                        "success": function (responseData) {
                            var data;
                            if (responseData.flag) {
                                data = responseData.data;
                                pageVm.planList = _.map(data, function (itemData) {
                                    return {
                                        "statusText": translateStatus(itemData.status),
                                        "status": itemData.status,
                                        "dateIndex": moment(itemData.start_time).format('YYYY-MM-DD HH:mm'),
                                        "sponsor": itemData.sponsor_name,
                                        "merchantName": itemData.merchant_name,
                                        "contactName": itemData.contact_name,
                                        "title": itemData.title,
                                        "description": itemData.description,
                                        "explanation": itemData.explanation,
                                        "attachmentList": _.map(itemData.attachmentList, function (itemData2) {
                                            itemData2["downloadUrl"] = erp.BASE_PATH + itemData.planFilePath + '/' + itemData2.attachment_name;
                                            return itemData2;
                                        })
                                    };
                                });
                            }
                        }
                    });
                }

            }

            function translateStatus (status) {
                var statusText = '';
                switch (status) {
                    case "1":
                        statusText = "未完成";
                        break;
                    case "2":
                        statusText = "已延期";
                        break;
                    case "3":
                        statusText = "已放弃";
                        break;
                    case "4":
                        statusText = "已完成";
                        break;
                    case "5":
                        statusText = "已求助";
                        break;
                    default:
                        break;
                }
                return statusText;
            }
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var currentDateCalendarId = pageName + '-current-date-calendar';
            var currentDateCalendarVm = avalon.getVm(currentDateCalendarId);
            currentDateCalendarVm && $(currentDateCalendarVm.widget).remove();
        }
    });

    return pageMod;
});
