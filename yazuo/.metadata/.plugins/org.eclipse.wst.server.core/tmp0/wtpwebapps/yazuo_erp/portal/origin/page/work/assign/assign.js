/**
 * 添加工作计划
 */
define(['avalon', 'util', 'moment', 'json', '../../../widget/form/form', '../../../widget/form/select', '../../../widget/autocomplete/autocomplete.js', '../../../widget/uploader/uploader', '../../../widget/calendar/calendar', '../../../module/address/address'], function (avalon, util, moment, JSON) {
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
            var startHourSelectorId = pageName + '-start-hour',
                startMinuteSelectorId = pageName + '-start-minute',
                projectSelectorId = pageName + '-project',
                planItemTypeId = pageName + '-plan-item-type',
                contactId = pageName + '-contact',
                addNewContactId = pageName + '-add-new-contact',
                communicationFormTypeId = pageName + 'communication-form-type',
                attachUploaderId = pageName + '-attach-uploader',
                assignerSelectorId = pageName + '-assigner',
                startDateCalendarId = pageName + '-start-date',
                formId = pageName + '-form';
            var loginUserData = erp.getAppData('user');
            var uploadInfoEl = $('.upload-info', pageEl);

            var pageVm = avalon.define(pageName, function (vm) {
                vm.$skipArray = ["originPlanTypeOptions"];
                vm.belongToRouteName = routeData.route.split('/')[1];   //所属主导航路由，可能是通过不同的路由定位到这个页面
                if (vm.belongToRouteName === "work") {
                    vm.navCrumbs = [{
                        "text": "工作计划",
                        "href": "#/work/plan"
                    }, {
                        "text": "工作指派"
                    }];
                } else if (vm.belongToRouteName === "frontend") {
                    vm.navCrumbs = [{
                        "text": "我的主页",
                        "href": "#/frontend/home"
                    }, {
                        "text": "工作指派"
                    }];
                }
                vm.$assignerOpts = {
                    "selectId": assignerSelectorId,
                    "options": [],
                    "onSelect": function (selectedValue) {
                        var projectVm = avalon.getVm(projectSelectorId),
                            projectSelectVm = projectVm.getSelectVm();
                            contactVm = avalon.getVm(contactId);
                        //根据被指派人id不同，项目显示不同
                        pageVm.$merchantData = [];
                        projectVm.inputText = "";
                        projectSelectVm.setOptions([]);
                        if (selectedValue !== -1) {
                            util.c2s({
                                "url": erp.BASE_PATH + "synMerchant/getSynMerchantByUserId.do",
                                "type": "get",
                                "contentType":"application/json",
                                "data": {
                                    "userId": selectedValue
                                },
                                "success": function (responseData) {
                                    var data,
                                        listData;
                                    if (responseData.flag) {
                                        data = responseData.data;
                                        listData = [{
                                            "text": "其它",
                                            "value": 0
                                        }].concat(_.map(data, function (itemData) {
                                            return {
                                                "text": itemData.merchant_name,
                                                "value": itemData.merchant_id
                                            };
                                        }));
                                        pageVm.$merchantData = listData;
                                        projectSelectVm.setOptions([{
                                            "text": "其它",
                                            "value": 0
                                        }], -1);
                                    }
                                }
                            });
                        }
                        //设置商户联系人数据
                        if (selectedValue > 0 && projectSelectVm.selectedValue > 0) {
                            vm.updateContactByProject(projectSelectVm.selectedValue, selectedValue);
                        } else {
                            contactVm.setOptions([{
                                "value": 0,
                                "text": "请选择"
                            }]);
                        }
                        //控制显示隐藏联系人下拉框
                        if (selectedValue === 0) {  //如果选中其他，则隐藏商户联系人
                            vm.showContact = false;
                        } else {
                            vm.showContact = true;
                        }
                    }
                };
                vm.$startHourOpts = {
                    "selectId": startHourSelectorId,
                    "options": _.map(_.range(0, 24), function (num) {
                        return {
                            "text": _.str.lpad(num, 2, '0'),
                            "value": num
                        };
                    }),
                    "selectedIndex": 9
                };
                vm.$startMinuteOpts = {
                    "selectId": startMinuteSelectorId,
                    "options": _.map(_.range(0, 60), function (num) {
                        return {
                            "text": _.str.lpad(num, 2, '0'),
                            "value": num
                        };
                    })
                };
                vm.$projectOpts = {
                    "autocompleteId": projectSelectorId,
                    "placeholder": "请搜索",
                    "onChange": function (text, callback) {
                        var listData;
                        if (text.length && vm.$merchantData) {
                            try {
                                listData = _.filter(vm.$merchantData, function (itemData) {
                                    return itemData.text.search(new RegExp(text, 'ig')) !== -1;
                                });
                                //永远附加其它项
                                if (!_.some(listData, function (itemData) {
                                    return itemData.value === 0;
                                })) {
                                    listData.push({
                                        "text": "其它",
                                        "value": 0
                                    });
                                }
                                callback(listData);
                            } catch(evt) {
                                callback([{
                                    "text": "其它",
                                    "value": 0
                                }]);   //空查询显示空
                            }
                        } else {
                            //callback(vm.$merchantData);   //空查询显示全部
                            callback([{
                                "text": "其它",
                                "value": 0
                            }]);   
                        }
                    },
                    "onSelect": function (selectedValue) {
                        var tempDate = moment(vm.currentDate).subtract('1', 'months'),  //上个月的月报
                            meVm = avalon.getVm(projectSelectorId).getSelectVm(),
                            planItemTypeVm = avalon.getVm(planItemTypeId),
                            contactVm = avalon.getVm(contactId);
                        if (planItemTypeVm.selectedValue === "1") {    //如果是讲解月报，自动生成标题且不能改
                            if (selectedValue > 0) {
                                vm.title = meVm.selectedText + tempDate.year() + '年' + (tempDate.month() + 1) + '月' + "月报讲解";
                            } else {
                                vm.title = "";
                                planItemTypeVm.selectValue(0);
                            }
                        }
                        //设置商户联系人数据
                        if (!vm.lockContact) {
                            if (selectedValue > 0) {
                                vm.updateContactByProject(selectedValue);
                            } else {
                                contactVm.setOptions([{
                                    "value": 0,
                                    "text": "请选择"
                                }]);
                            }
                        }
                        //控制显示隐藏联系人下拉框
                        if (selectedValue === 0) {  //如果选中其他，则隐藏商户联系人
                            vm.showContact = false;
                        } else {
                            vm.showContact = true;
                        }
                    }
                };
                // vm.$projectOpts = {
                //     "selectId": projectSelectorId,
                //     "options": [{
                //         "text": "请选择",
                //         "value": -1
                //     }],
                //     "onSelect": function (selectedValue, selectedText) {
                //         var tempDate = moment(vm.currentDate).subtract('1', 'months'),  //上个月的月报
                //             meVm = avalon.getVm(projectSelectorId),
                //             planItemTypeVm = avalon.getVm(planItemTypeId),
                //             contactVm = avalon.getVm(contactId),
                //             assignerVm = avalon.getVm(assignerSelectorId);
                //         if (planItemTypeVm.selectedValue === "1") {    //如果是讲解月报，自动生成标题且不能改
                //             if (selectedValue > 0) {
                //                 vm.title = meVm.selectedText + tempDate.year() + '年' + (tempDate.month() + 1) + '月' + "月报讲解";
                //             } else {
                //                 vm.title = "";
                //                 planItemTypeVm.selectValue(0);
                //             }
                //         }
                //         //设置商户联系人数据
                //         if (selectedValue > 0 && assignerVm.selectedValue > 0) {
                //             vm.updateContactByProject(selectedValue, assignerVm.selectedValue);
                //             vm.showContact = true;
                //         } else {
                //             contactVm.setOptions([{
                //                 "value": 0,
                //                 "text": "请选择"
                //             }]);
                //             if (selectedValue === 0) {  //如果选中其他，则隐藏商户联系人
                //                 vm.showContact = false;
                //             } else {
                //                 vm.showContact = true;
                //             }
                //         }
                //     }
                // };
                /**
                 * 根据项目id更新商户联系人列表
                 * @param {[[Type]]} projectId [[Description]]
                 */
                vm.updateContactByProject = function (projectId, assignerId, callback) {
                    var contactVm = avalon.getVm(contactId);
                    util.c2s({
                        "url": erp.BASE_PATH + 'contact/listContacts.do',
                        "type": "post",
                        "contentType":"application/json",
                        "data":JSON.stringify({
                            "merchantId": projectId,
                            "merchantName": "",
                            "name": "",
                            "pageNumber": 1,
                            "pageSize": 10000//,  //给一极大值保证返回全部信息
                            //"userId": assignerId
                        }),
                        "success": function (responseData) {
                            var rows;
                            if (responseData.flag) {
                                rows = responseData.data.rows;
                                contactVm.setOptions([{
                                    "value": 0,
                                    "text": "请选择"
                                }].concat(_.map(rows, function (itemData) {
                                    return {
                                        "value": itemData.id,
                                        "text": itemData.name
                                    };
                                })));
                                callback && callback();
                            }
                        }
                    });
                };
                vm.title = '';
                vm.$merchantData = [];
                vm.atDailyPaper = false;    //标示是否处于讲解日报事项中
                vm.$planItemTypeOpts = {
                    "selectId": planItemTypeId,
                    "options": [],
                    "onSelect": function (selectedValue, selectedText) {
                        var tempDate = moment(vm.currentDate).subtract('1', 'months'),  //上个月的月报
                            meVm = avalon.getVm(planItemTypeId),
                            projectVm = avalon.getVm(projectSelectorId).getSelectVm();
                        if (selectedValue === "1") {    //如果是讲解月报，自动生成标题且不能改
                            if (projectVm.selectedValue > 0) {
                                vm.title = projectVm.selectedText + tempDate.year() + '年' + (tempDate.month() + 1) + '月' + "月报讲解";
                                //设置处于讲解日报事项只读
                                vm.atDailyPaper = true;
                            } else {
                                vm.title = "";
                                meVm.selectValue(0);
                                vm.atDailyPaper = false;
                                util.alert({
                                    "iconType": "error",
                                    "content": "请选择有效的项目选项。"
                                });
                            }
                        } else {
                            vm.title = "";
                            vm.atDailyPaper = false;
                        }
                    }
                };
                vm.showContact = true;
                vm.$contactOpts = {
                    "selectId": contactId,
                    "options": [{
                        "value": 0,
                        "text": "请选择"
                    }],
                    "getTemplate": function (tmpl) {
                        tmpl += '<div class="bbar" style="line-height: 34px; background: #eee; border-top: 1px solid #ccc; text-indent: 10px;"><a href="javascript:;" ms-click="addContact">添加联系人</a></div>';
                        return tmpl;
                    }
                };
                vm.$addNewContactOpts = {
                    "addressId": addNewContactId,
                    "displayType": 'add',
                    "isSearch": false,
                    "moduleType": 'fes',
                    "merchantId": 0,
                    "callFn": function (responseData, requestData) {
                        var projectVm = avalon.getVm(projectSelectorId).getSelectVm(),
                            assignerVm = avalon.getVm(assignerSelectorId);
                        vm.updateContactByProject(projectVm.selectedValue, assignerVm.selectedValue, function () {
                            //选中新添加的联系人
                            //projectVm.selectValue(requestData);
                        });
                    }
                };
                vm.addContact = function () {
                    var addNewContactVm = avalon.getVm(addNewContactId),
                        contactVm = avalon.getVm(contactId),
                        projectVm = avalon.getVm(projectSelectorId).getSelectVm(),
                        assignerVm = avalon.getVm(assignerSelectorId);
                    if (projectVm.selectedValue <= 0 || assignerVm.selectedValue <= 0) {
                        util.alert({
                            "iconType": "error",
                            "content": "请选择一个项目或解决人"
                        });
                    } else {
                        addNewContactVm.requestData = {
                            "merchantId": projectVm.selectedValue
                        };
                        addNewContactVm.merchantId = projectVm.selectedValue;
                        addNewContactVm.displayType = 'add';
                        addNewContactVm.isSearch = false;
                        addNewContactVm.open();
                    }
                    contactVm.close();
                };
                vm.$communicationFormTypeOpts = {
                    "selectId": communicationFormTypeId,
                    "options": []
                };
                vm.$attachUploaderOpts = {
                    "uploaderId": attachUploaderId,
                    "uploadifyOpts": {
                        "uploader": erp.BASE_PATH + 'fesPlan/uploadPlanFiles.do',
                        "fileObjName": "myfiles",
                        "multi": true, //多选
                        "fileTypeDesc": "上传附件(*.*)",
                        "fileTypeExts": "*.*",
                        //"fileSizeLimit": "20MB",
                        "formData": {
                            "sessionId": loginUserData.sessionId
                         },
                        "width": 116,
                        "height": 30
                    },
                    "defaultExtKeyData": {
                        "originalFileName": "",
                        "attachmentName": "",
                        "attachmentSize": "",
                        "attachmentSuffix": ""
                    },
                    "onSuccessResponseData": function (reponseText, file) {
                        var responseData = JSON.parse(reponseText),
                            result = {};
                        if (responseData.flag) {
                            result["originalFileName"] = responseData.data[0].originalFileName;
                            result["attachmentName"] = responseData.data[0].fileName;
                            result["attachmentSize"] = responseData.data[0].fileSize + "";
                            result["attachmentSuffix"] = file.type.slice(1);    //不带点
                        }
                        return result;
                    },
                    "removeItem": function () {
                        var itemVm = this.$vmodel,
                            fileId = itemVm.el.id;
                        avalon.getVm(attachUploaderId).removeFile(fileId);
                    }
                };
                vm.$formOpts = {
                    "formId": formId,
                    "field": [{
                        "selector": ".assigner",
                        "name": "userId",
                        "getValue": function () {
                            return avalon.getVm(assignerSelectorId).selectedValue;
                        },
                        "rule": function (val) {
                            if (val === -1) {
                                return "请选择指派人";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".start-date",
                        "name": "startTime",
                        "getValue": function () {
                            var val = _.str.trim($(this).val());
                            if (val) {
                                return moment(val + ' ' + avalon.getVm(startHourSelectorId).selectedValue + ':' + avalon.getVm(startMinuteSelectorId).selectedValue, 'YYYY-MM-DD H:m') / 1;
                            } else {
                                return 0;
                            }
                        },
                        "rule": function (val) {
                            if (!val) {
                                return "请设置开始时间";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".project",
                        "name": "merchantId",
                        "getValue": function () {
                            return avalon.getVm(projectSelectorId).getSelectVm().selectedValue;
                        },
                        "rule": function (val) {
                            if (val === "" || val === null) {
                                return "请选择对应的项目";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".plan-item-type",
                        "name": "planItemType",
                        "getValue": function () {
                            return avalon.getVm(planItemTypeId).selectedValue;
                        },
                        "rule": function (val) {
                            if (!val) {
                                return "请选择对应的事项类型";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".contact",
                        "name": "contactId",
                        "excludeHidden": true,
                        "getValue": function () {
                            return avalon.getVm(contactId).selectedValue;
                        },
                        "rule": function (val) {
                            if (!val) {
                                return "请选择对应的商户联系人";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".communication-form-type",
                        "name": "communicationFormType",
                        "getValue": function () {
                            return avalon.getVm(communicationFormTypeId).selectedValue;
                        },
                        "rule": function (val) {
                            if (!val) {
                                return "请选择对应的沟通方式";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".title",
                        "name": "title",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "标题不能为空";
                            } else {
                                if (val.length > 64) {
                                    return "标题不能超过64个汉字";
                                }
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".desc",
                        "name": "description",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "说明不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".attach-uploader",
                        "name": "attachmentList",
                        "reset": function () {
                            var uploaderVm = avalon.getVm(attachUploaderId);
                            if (vm.formState == "add") {
                                uploaderVm.removeAllFile();
                            }
                        },
                        "getValue": function () {
                            var uploaderVm = avalon.getVm(attachUploaderId),
                                uploadList = uploaderVm.uploadList,
                                result = [];
                            _.each(uploadList.$model, function (itemData, i) {
                                result[i] = {
                                    "originalFileName": itemData.name,
                                    "attachmentName": itemData.attachmentName,
                                    "attachmentSize": itemData.attachmentSize + "",
                                    "attachmentSuffix": itemData.attachmentSuffix,
                                    "attachmentId": itemData.attachmentId
                                };
                            });
                            return result;
                        }
                    }]
                };
                vm.startDate = moment().format('YYYY-MM-DD');
                vm.originPlanTypeOptions = [];
                vm.$watch("startDate", function (v) {
                    var planItemTypeVm = avalon.getVm(planItemTypeId);
                    //如果是工作计划是当前日期，滤掉讲解日报项
                    if (moment(v, 'YYYY-MM-DD').isSame(new Date(), 'day')) {
                        planItemTypeVm.setOptions(_.reject(vm.originPlanTypeOptions, function (itemData) {
                            return itemData.value === "1";
                        }));
                    } else {
                        planItemTypeVm.setOptions(vm.originPlanTypeOptions);
                    }
                });
                vm.$startDateOpts = {
                    "calendarId": startDateCalendarId,
                    "onClickDate": function (d) {
                        vm.startDate = moment(d).format('YYYY-MM-DD');
                        $(this.widgetElement).hide();
                    }
                };
                /**
                 * 打开开始日期选择面板
                 */
                vm.openStartDateCalendar = function () {
                    var meEl = $(this),
                        calendarVm = avalon.getVm(startDateCalendarId),
                        calendarEl,
                        inputOffset = meEl.offset();
                    if (!calendarVm) {
                        calendarEl = $('<div ms-widget="calendar,$,$startDateOpts"></div>');
                        calendarEl.css({
                            "position": "absolute",
                            "z-index": 10000
                        }).hide().appendTo('body');
                        avalon.scan(calendarEl[0], [vm]);
                        calendarVm = avalon.getVm(startDateCalendarId);
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
                        calendarVm.focusDate = new Date();
                    }

                    calendarEl.css({
                        "top": inputOffset.top + meEl.outerHeight() - 1,
                        "left": inputOffset.left
                    }).show();
                };
                /**
                 * 表单提交
                 */
                vm.submit = function () {
                    var formVm = avalon.getVm(formId),
                        uploaderVm = avalon.getVm(attachUploaderId),
                        requestData,
                        exer;
                    if (formVm.validate()) {
                        exer = function () {
                            requestData = formVm.getFormData();
                            //附加其他默认信息
                            requestData["endTime"] = 0;
                            requestData["sponsor"] = loginUserData.id;    //发起人
                            requestData["remark"] = "";
                            requestData["plansSource"] = "2";   //1=工作计划 or 2=工作指派
                            requestData["isSendSms"] = false;   //默认是false
                            requestData["isRemind"] = false;
                            requestData["remindTime"] = 0;
                            //处理项目信息，如果项目是0，变成null
                            if (requestData["merchantId"] === 0) {
                                requestData["merchantId"] = null;
                            }
                            util.c2s({
                                "url": erp.BASE_PATH + 'fesPlan/saveFesPlan.do',
                                "type": "post",
                                "contentType":"application/json",
                                "data":JSON.stringify(requestData),
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        util.alert({
                                            "content": responseData.message,
                                            "iconType": "success",
                                            "onSubmit": function () {
                                                //util.jumpPage('#/work/plan');
                                                if (vm.belongToRouteName === "work") {
                                                    util.jumpPage('#/work/plan');
                                                } else if (vm.belongToRouteName === "frontend") {
                                                    util.jumpPage('#/frontend/home');
                                                }
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
            //手动扫描上传附件区域
            uploadInfoEl.removeClass('fn-hide').removeAttr('ms-skip');
            avalon.scan(uploadInfoEl[0], [avalon.getVm(attachUploaderId)]);

            initCpt();

            /**
             * 组件初始化
             */
            function initCpt () {
                var assignerVm = avalon.getVm(assignerSelectorId),
                    planItemTypeVm = avalon.getVm(planItemTypeId),
                    communicationFormTypeVm = avalon.getVm(communicationFormTypeId);
                //请求指派人列表
                util.c2s({
                    "url": erp.BASE_PATH + "group/getSubordinateEmployees.do",
                    "type": "post",
                    "contentType":"application/json",
                    "data": JSON.stringify({
                        "baseUserId": loginUserData.id
                    }),
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            assignerVm.setOptions([{
                                "text": "请选择",
                                "value": -1
                            }].concat(_.map(data, function (itemData) {
                                return {
                                    "text": itemData.userName,
                                    "value": itemData.id
                                };
                            })));
                        }
                    }
                });
                //请求项目列表
                /*util.c2s({
                    "url": erp.BASE_PATH + "synMerchant/getSynMerchantByUserId.do",
                    "type": "get",
                    "contentType":"application/json",
                    "data": {
                        "userId": loginUserData.id
                    },
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            projectVm.setOptions([{
                                "text": "请选择",
                                "value": -1
                            }].concat(_.map(data, function (itemData) {
                                return {
                                    "text": itemData.merchant_name,
                                    "value": itemData.merchant_id
                                };
                            })).concat({
                                "text": "其它",
                                "value": 0
                            }));
                        }
                    }
                });*/
                //请求事项类型
                util.c2s({
                    "url": erp.BASE_PATH + "dictionary/querySysDictionaryByType.do",
                    "type": "post",
                    "data":  'dictionaryType=00000063',
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            pageVm.originPlanTypeOptions = [{
                                "text": "请选择",
                                "value": 0
                            }].concat(_.map(data, function (itemData) {
                                return {
                                    "text": itemData["dictionary_value"],
                                    "value": itemData["dictionary_key"]
                                };
                            }));
                            //如果是工作计划是当前日期，滤掉讲解日报项
                            if (pageVm.startDate) {
                                if (moment(pageVm.startDate, 'YYYY-MM-DD').isSame(new Date(), 'day')) {
                                    planItemTypeVm.setOptions(_.reject(pageVm.originPlanTypeOptions, function (itemData) {
                                        return itemData.value === "1";
                                    }));
                                } else {
                                    planItemTypeVm.setOptions(pageVm.originPlanTypeOptions);
                                }
                            }
                            // planItemTypeVm.setOptions([{
                            //     "text": "请选择",
                            //     "value": 0
                            // }]);
                        }
                    }
                });
                //请求沟通方式
                util.c2s({
                    "url": erp.BASE_PATH + "dictionary/querySysDictionaryByType.do",
                    "type": "post",
                    "data":  'dictionaryType=00000064',
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            communicationFormTypeVm.setOptions([{
                                "text": "请选择",
                                "value": 0
                            }].concat(_.map(data, function (itemData) {
                                return {
                                    "text": itemData["dictionary_value"],
                                    "value": itemData["dictionary_key"]
                                };
                            })));
                        }
                    }
                });
            }
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var startHourSelectorId = pageName + '-start-hour',
                startMinuteSelectorId = pageName + '-start-minute',
                projectSelectorId = pageName + '-project',
                planItemTypeId = pageName + '-plan-item-type',
                contactId = pageName + '-contact',
                addNewContactId = pageName + '-add-new-contact',
                communicationFormTypeId = pageName + 'communication-form-type',
                attachUploaderId = pageName + '-attach-uploader',
                formId = pageName + '-form',
                assignerSelectorId = pageName + '-assigner',
                startDateCalendarId = pageName + '-start-date';
            var calendarVm = avalon.getVm(startDateCalendarId),
                addNewContactVm = avalon.getVm(addNewContactId);
            calendarVm && $(calendarVm.widgetElement).remove();

            $(avalon.getVm(attachUploaderId).widgetElement).remove();
            $(avalon.getVm(projectSelectorId).widgetElement).remove();
            $(avalon.getVm(planItemTypeId).widgetElement).remove();
            $(avalon.getVm(contactId).widgetElement).remove();
            addNewContactVm && $(addNewContactVm.widgetElement).remove();
            $(avalon.getVm(communicationFormTypeId).widgetElement).remove();
            $(avalon.getVm(startMinuteSelectorId).widgetElement).remove();
            $(avalon.getVm(startHourSelectorId).widgetElement).remove();
            $(avalon.getVm(assignerSelectorId).widgetElement).remove();
            $(avalon.getVm(formId).widgetElement).remove();
        }
    });

    return pageMod;
});
