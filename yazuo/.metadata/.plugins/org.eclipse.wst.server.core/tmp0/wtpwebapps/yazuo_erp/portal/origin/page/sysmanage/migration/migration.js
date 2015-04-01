/**
 * miniErp数据导入
 */
define(['avalon', 'util', 'moment', 'json', '../../../widget/form/form', '../../../widget/form/select', '../../../widget/uploader/uploader', '../../../widget/autocomplete/autocomplete', '../../../widget/calendar/calendar'], function (avalon, util, moment, JSON) {
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
            var formId = pageName + '-form',
                brandId = pageName + '-brand',
                merchantListId = pageName + '-merchant-list',
                serviceYearsId = pageName + '-service-years',
                freeMonthsId = pageName + '-free-months',
                onlineStatusId = pageName + '-online-status',
                saleId = pageName + '-sale',
                advisorId = pageName + '-advisor',
                calendarId = pageName + '-calendar';
            var loginUserData = erp.getAppData('user');
            var pageVm = avalon.define(pageName, function (vm) {
                vm.navCrumbs = [{
                    "text": "miniERP数据迁移"
                }];
                vm.showMerchantList = false;
                vm.showAdvisor = true;
                vm.merchantName = "";
                vm.crmId = 0;
                vm.$brandOpts = {
                    "autocompleteId": brandId,
                    "placeholder": "请指定商户",
                    "delayTime": 300,   //延时300ms请求
                    "onChange": function (text, callback) {
                        if (text.length) {
                            util.c2s({
                                "url": erp.BASE_PATH + "brands/getBrandsInfo.do",
                               // "contentType": "application/json",
                                "data": {
                                    "name": text
                                },
                                "success": function (responseData) {
                                    var rows;
                                    if (responseData.flag) {
                                        rows = responseData.data;
                                        callback(_.map(rows, function (itemData) {
                                            itemData.text = itemData.name || "未知";
                                            itemData.value = itemData.id;
                                            return itemData;
                                        }));
                                    }
                                }
                            }, {
                                "mask": false
                            });
                        } else {
                            callback([]);   //空查询显示空
                        }
                    },
                    "onSelect": function (selectedValue) {
                        var optionData = avalon.getVm(brandId).getOptionByValue(selectedValue);
                        if (!optionData.crm_id) {

                            vm.showMerchantList = true;
                            avalon.getVm(merchantListId) && (avalon.getVm(merchantListId).inputText = optionData.name);
                            vm.merchantName = "";
                        } else {
                            vm.showMerchantList = false;
                            avalon.getVm(merchantListId) && (avalon.getVm(merchantListId).inputText = "");
                            vm.merchantName = optionData.merchant_name;
                            vm.crmId = optionData.crm_id;
                        }
                    }
                };
                vm.$merchantListOpts = {
                    "autocompleteId": merchantListId,
                    "onChange": function (text, callback) {
                        if (text.length) {
                            //设置商户列表数据
                            util.c2s({
                                "url": erp.BASE_PATH + "merchantCrm/getMerchantsInfo.do",
                               // "contentType": "application/json",
                                "data": {
                                    "merchantName": text
                                },
                                "success": function (responseData) {
                                    var rows;
                                    if (responseData.flag) {
                                        rows = responseData.data;
                                        callback(_.map(rows, function (itemData) {
                                            itemData.text = itemData.merchant_name || "未知";
                                            itemData.value = itemData.merchant_id;
                                            return itemData;
                                        }));
                                    }
                                }
                            });
                        } else {
                            callback([]);   //空查询显示空
                        }
                    },
                    "onSelect": function (selectedValue, selectedText) {
                        vm.merchantName = selectedText;
                        vm.crmId = selectedValue;
                    }
                };
                vm.$serviceYearsOpts = {
                    "selectId": serviceYearsId,
                    "options": _.map(_.range(0, 11), function (num) {
                        return {
                            "text": num + '年',
                            "value": num
                        };
                    })
                };
                vm.$freeMonthsOpts = {
                    "selectId": freeMonthsId,
                    "options": _.map(_.range(0, 7), function (num) {
                        return {
                            "text": num + '个月',
                            "value": num
                        };
                    })
                };
                vm.$onlineStatusOpts = {
                    "selectId": onlineStatusId,
                    "options": [{
                        "text": "已上线",
                        "value": 1
                    }, {
                        "text": "商户创建",
                        "value": 2
                    }],
                    "onSelect": function (selectedValue) {
                        if (selectedValue === 2) {
                            vm.showAdvisor = false;
                        } else {
                            vm.showAdvisor = true;
                        }
                    }
                };
                vm.$saleOpts = {
                    "selectId": saleId
                };
                vm.$advisorOpts = {
                    "selectId": advisorId
                };
                vm.$formOpts = {
                    "formId": formId,
                    "field": [{
                        "selector": ".brand-name",
                        "name": "mini_merchant_id",
                        "getValue": function () {
                            return avalon.getVm(avalon.getVm(brandId).selectId).selectedValue;
                        },
                        "rule": function (val) {
                            if (!val) {
                                return "请选择品牌";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".merchant-name",
                        "name": "merchant_id",
                        "getValue": function () {
                            return vm.crmId;
                        },
                        "rule": function (val) {
                            if (!val) {
                                return "请选择商户";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".service-years",
                        "name": "service_year",
                        "getValue": function () {
                            return avalon.getVm(serviceYearsId).selectedValue;
                        }
                    }, {
                        "selector": ".free-months",
                        "name": "free_month",
                        "getValue": function () {
                            return avalon.getVm(freeMonthsId).selectedValue;
                        }
                    }, {
                        "selector": ".start-date",
                        "name": "service_start_time",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "服务开始时间不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".online-status",
                        "name": "merchant_status",
                        "getValue": function () {
                            return avalon.getVm(onlineStatusId).selectedValue+'';
                        }
                    }, {
                        "selector": ".sale",
                        "name": "sales_user_id",
                        "getValue": function () {
                            return avalon.getVm(saleId).selectedValue;
                        },
                        "rule": function (val) {
                            if (!val) {
                                return "请选择销售人员";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".advisor",
                        "name": "front_user_id",
                        "excludeHidden": true,
                        "getValue": function () {
                            return avalon.getVm(advisorId).selectedValue;
                        },
                        "rule": function (val) {
                            if (!val) {
                                return "请选择前端顾问";
                            } else {
                                return true;
                            }
                        }
                    }]
                };
                vm.startDate = "";
                vm.$calendarOpts = {
                    "calendarId": calendarId,
                    "onClickDate": function (d) {
                        vm.startDate = moment(d).format('YYYY-MM-DD');
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
                    if (vm.startDate) {
                        calendarVm.focusDate = moment(vm.startDate, 'YYYY-MM-DD')._d;
                    } else {
                        calendarVm.focusDate = now;
                    }
                    calendarEl.css({
                        "top": inputOffset.top + meEl.outerHeight() - 1,
                        "left": inputOffset.left
                    }).show();
                };
                vm.submit = function () {
                    var requestData,
                        formVm = avalon.getVm(formId);
                    if (formVm.validate()) {
                        requestData = formVm.getFormData();
                        requestData["service_start_time"] = moment(requestData["service_start_time"], "YYYY-MM-DD") / 1;
                        //发送后台请求
                        util.c2s({
                            "url": erp.BASE_PATH + 'synMerchant/saveSynMerchantForOnlineBefore.do',
                            "type": "post",
                            "contentType": "application/json",
                            "data": JSON.stringify(requestData),
                            "success": function (responseData) {
                                if (responseData.flag) {
                                    util.alert({
                                        "iconType": "success",
                                        "content": responseData.message,
                                        "onSubmit": function () {
                                            //强制刷新
                                            util.forceRefresh();
                                        }
                                    });
                                }
                            }
                        });
                    }
                };
            });

            avalon.scan(pageEl[0]);

            //更新数据
            initCpt();

            //私有函数

            /**
             * 刷新页面数据
             */
            function initCpt () {
                //获取销售数据
                util.c2s({
                    "url": erp.BASE_PATH + "user/getAllUsersByResourceCode.do",
                    "contentType": "application/json",
                    "data": JSON.stringify({
                        "remark": "sales_service"
                    }),
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            avalon.getVm(saleId).setOptions(_.map(data, function (itemData) {
                                return {
                                    "value": itemData.id,
                                    "text": itemData.userName
                                };
                            }));

                        }
                    }
                });
                //获取前端人员
                util.c2s({
                    "url": erp.BASE_PATH + "user/getAllUsersByResourceCode.do",
                    "contentType": "application/json",
                    "data": JSON.stringify({
                        "remark": "front_end_service"
                    }),
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            avalon.getVm(advisorId).setOptions(_.map(data, function (itemData) {
                                return {
                                    "value": itemData.id,
                                    "text": itemData.userName
                                };
                            }));

                        }
                    }
                });
            }
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var formId = pageName + '-form',
                    brandId = pageName + '-brand',
                    merchantListId = pageName + '-merchant-list',
                    serviceYearsId = pageName + '-service-years',
                    freeMonthsId = pageName + '-free-months',
                    onlineStatusId = pageName + '-online-status',
                    saleId = pageName + '-sale',
                    advisorId = pageName + '-advisor',
                    calendarId = pageName + '-calendar';
            var calendarVm = avalon.getVm(calendarId),
                   merchantListVm = avalon.getVm(merchantListId);
            calendarVm && $(calendarVm.widgetElement).remove();
            $(avalon.getVm(brandId).widgetElement).remove();
            merchantListVm && $(merchantListVm.widgetElement).remove();
            $(avalon.getVm(serviceYearsId).widgetElement).remove();
            $(avalon.getVm(freeMonthsId).widgetElement).remove();
            $(avalon.getVm(onlineStatusId).widgetElement).remove();
            $(avalon.getVm(saleId).widgetElement).remove();
            $(avalon.getVm(advisorId).widgetElement).remove();
            $(avalon.getVm(formId).widgetElement).remove();
        }
    });

    return pageMod;
});
