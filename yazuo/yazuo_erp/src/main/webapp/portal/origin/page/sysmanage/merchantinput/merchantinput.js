/**
 * 商户录入
 */
define(['avalon', 'util', 'moment', 'json', '../../../widget/form/form', '../../../widget/form/select', '../../../widget/uploader/uploader', '../../../widget/calendar/calendar'], function (avalon, util, moment, JSON) {
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
                calendarId = pageName + '-calendar',
                uploaderId = pageName + '-uploader';
            var loginUserData = erp.getAppData('user');
            var pageVm = avalon.define(pageName, function (vm) {
                vm.showAdvisor = true;
                vm.merchantLogo = erp.ASSET_PATH + 'image/merchant-default-logo.jpg';
                vm.fileName = '';
                vm.originalFileName = '';
                vm.fileSize = 0;
                vm.$uploaderOpts = {
                    "uploaderId": uploaderId,
                    "uploadifyOpts": {
                        "uploader": erp.BASE_PATH + 'synMerchant/uploadLogo.do',
                         "formData": {
                            "sessionId": loginUserData.sessionId
                         },
                        "fileTypeDesc": "图片文件(*.gif; *.jpg; *.png; *.jpeg)",
                        "fileTypeExts": "*.gif; *.jpg; *.png; *.jpeg",
                        "fileSizeLimit": "2MB",
                        "fileObjName": "myfiles",
                        "multi": false //禁用多选
                    },
                    "onSuccessResponseData": function (reponseText) {
                        var responseData = JSON.parse(reponseText),
                            data = responseData.data[0];
                        vm.merchantLogo = data.relativePath;
                        vm.fileName = data.fileName;
                        vm.originalFileName = data.originalFileName;
                        vm.fileSize = data.fileSize;
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
                        "selector": ".brand-id",
                        "name": "merchant_id",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "品牌ID不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".merchant-logo",
                        "name": "fileName",
                        "getValue": function () {
                            return vm.fileName;
                        },
                        "rule": function () {
                            if (!vm.fileName.length) {
                                return "品牌Logo不能为空";
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
                        formVm = avalon.getVm(formId),
                        uploaderVm = avalon.getVm(uploaderId),
                        exer;
                    if (formVm.validate()) {
                        exer = function () {
                            requestData = formVm.getFormData();
                            requestData["merchant_id"] = requestData["merchant_id"] / 1;
                            requestData["service_start_time"] = moment(requestData["service_start_time"], "YYYY-MM-DD") / 1;
                            //附加attach参数
                            requestData["originalFileName"] = vm.originalFileName;
                            requestData["fileSize"] = vm.fileSize;
                            //发送后台请求
                            util.c2s({
                                "url": erp.BASE_PATH + 'synMerchant/saveSynMerchantExtend.do',
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
                        };
                        if (uploaderVm.uploading) {
                            util.showGlobalMask();  //打开全局遮罩
                            uploaderVm.$unwatch('uploading');
                            uploaderVm.$watch('uploading', function (val) {
                                if (!val) {  //全部上传成功
                                    util.hideGlobalMask();  //隐藏全局遮罩
                                    exer();
                                    uploaderVm.$unwatch('uploading');
                                }
                            });
                        } else {    //直接上传
                            exer();
                        }
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
                uploaderId = pageName + '-uploader',
                serviceYearsId = pageName + '-service-years',
                freeMonthsId = pageName + '-free-months',
                onlineStatusId = pageName + '-online-status',
                saleId = pageName + '-sale',
                advisorId = pageName + '-advisor',
                calendarId = pageName + '-calendar';
            var calendarVm = avalon.getVm(calendarId);
            calendarVm && $(calendarVm.widgetElement).remove();
            $(avalon.getVm(serviceYearsId).widgetElement).remove();
            $(avalon.getVm(freeMonthsId).widgetElement).remove();
            $(avalon.getVm(onlineStatusId).widgetElement).remove();
            $(avalon.getVm(saleId).widgetElement).remove();
            $(avalon.getVm(advisorId).widgetElement).remove();
            $(avalon.getVm(uploaderId).widgetElement).remove();
            $(avalon.getVm(formId).widgetElement).remove();
        }
    });

    return pageMod;
});
