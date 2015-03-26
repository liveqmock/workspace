/**
 * 添加工作计划
 */
define(['avalon', 'util', 'moment', 'json', '../../../asset/events', '../../../widget/form/form', '../../../widget/form/select', '../../../widget/uploader/uploader', '../../../widget/calendar/calendar', '../../../module/address/address'], function (avalon, util, moment, JSON, Events) {
    var win = this,
        erp = win.erp,
        pageMod = {},
        pageModEvent = new Events();
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
                formId = pageName + '-form',
                remindDialogId = pageName + '-remind-dialog',
                remindTimeId = pageName + '-remind-time-selector',
                reasonDialogId = pageName + '-reason-dialog',
                reasonFormId = pageName + '-reason-form',
                unCompleteSelectorId = pageName + '-un-complete-selector',
                expectDateCalendarId = pageName + '-expect-date-calendar',
                expectTimeSelectorId = pageName + '-expect-time-selector',
                remindDateCalendarId = pageName + '-remind-date-calendar';
            var loginUserData = erp.getAppData('user');
            var uploadInfoEl = $('.upload-info', pageEl);

            var pageVm = avalon.define(pageName, function (vm) {
                vm.belongToRouteName = routeData.route.split('/')[1];   //所属主导航路由，可能是通过不同的路由定位到这个页面
                if (vm.belongToRouteName === "work") {
                    vm.navCrumbs = [{
                        "text": "工作计划",
                        "href": "#/work/plan"
                    }, {
                        "text": "添加计划"
                    }];
                } else if (vm.belongToRouteName === "frontend") {
                    vm.navCrumbs = [{
                        "text": "我的主页",
                        "href": "#/frontend/home"
                    }, {
                        "text": "添加计划"
                    }];
                } else if (vm.belongToRouteName === "sales") {
                    vm.navCrumbs = [{
                        "text": "我的主页",
                        "href": "#/sales/home"
                    }, {
                        "text": "添加计划"
                    }];
                }

                vm.formState = pageName.split('-').pop();
                vm.userId = (vm.formState === "add" ? loginUserData.id : 0);
                vm.isRemindOpen = false;    //默认提醒处于关闭状态
                vm.$watch("isRemindOpen", function (val) {
                    var remindDialogVm = avalon.getVm(remindDialogId),
                        remindTimeVm = avalon.getVm(remindTimeId);
                    if (val) {
                        vm.remindTime = remindDialogVm.remindDate + ' ' + remindTimeVm.selectedText;
                    } else {
                        vm.remindTime = "";
                    }
                });
                vm.remindTime = ""; //提醒时间
                vm.currentDate = moment(routeData.params["date"] / 1)._d;
                vm.unCompleteState = 'view'; //view or edit,表明未完成按钮所处的状态
                vm.$unCompleteOpts = {
                    "selectId": unCompleteSelectorId,
                    "options": [{
                        "text": "请选择",
                        "value": ""
                    }, {
                        "text": "放弃",
                        "value": "giveup"
                    }, {
                        "text": "延时",
                        "value": "delay"
                    }],
                    "onSelect": function (val, text) {
                        var dialogVm = avalon.getVm(reasonDialogId);
                        if (val) {
                            dialogVm.title = text;
                            dialogVm.state = val;
                            dialogVm.reasonLabel = "请说明" + text + '工作的原因';
                            dialogVm.open();
                        }
                        vm.unCompleteState = "view";
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
                    "selectedIndex": 9,
                    "onSelect": function () {
                        vm.adjustCurrentDate();
                    }
                };
                vm.$startMinuteOpts = {
                    "selectId": startMinuteSelectorId,
                    "options": _.map(_.range(0, 60), function (num) {
                        return {
                            "text": _.str.lpad(num, 2, '0'),
                            "value": num
                        };
                    }),
                    "onSelect": function () {
                        vm.adjustCurrentDate();
                    }
                };
                vm.$projectOpts = {
                    "selectId": projectSelectorId,
                    "options": [],
                    "onSelect": function (selectedValue, selectedText) {
                        var tempDate = moment(vm.currentDate).subtract('1', 'months'),  //上个月的月报
                            meVm = avalon.getVm(projectSelectorId),
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
                /**
                 * 根据项目id更新商户联系人列表
                 * @param {[[Type]]} projectId [[Description]]
                 */
                vm.updateContactByProject = function (projectId, callback) {
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
                            "pageSize": 10000  //给一极大值保证返回全部信息
                            //"userId": loginUserData.id
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
                vm.atDailyPaper = false;    //标示是否处于讲解日报事项中
                vm.$planItemTypeOpts = {
                    "selectId": planItemTypeId,
                    "options": [],
                    "onSelect": function (selectedValue, selectedText) {
                        var tempDate = moment(vm.currentDate).subtract('1', 'months'),  //上个月的月报
                            meVm = avalon.getVm(planItemTypeId),
                            projectVm = avalon.getVm(projectSelectorId);
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
                vm.lockContact = false; //是否锁定联系人列表，true时选择项目不会触发联系人选项的改变
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
                    "readonly": false,
                    "moduleType": 'fes',
                    "merchantId": 0,
                    "callFn": function (responseData, requestData) {
                        var projectVm = avalon.getVm(projectSelectorId);
                        vm.updateContactByProject(projectVm.selectedValue, function () {
                            //选中新添加的联系人
                            //projectVm.selectValue(requestData);
                        });
                    }
                };
                vm.addContact = function () {
                    var addNewContactVm = avalon.getVm(addNewContactId),
                        contactVm = avalon.getVm(contactId),
                        projectVm = avalon.getVm(projectSelectorId);
                    if (projectVm.selectedValue <= 0) {
                        util.alert({
                            "iconType": "error",
                            "content": "请选择一个项目"
                        });
                    } else {
                        addNewContactVm.requestData = {
                            "merchantId": projectVm.selectedValue
                        };
                        addNewContactVm.merchantId = projectVm.selectedValue;
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
                        "fileSizeLimit": "20MB",
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
                        var itemM = this.$vmodel.$model,
                            fileId = itemM.el.id;
                        avalon.getVm(attachUploaderId).removeFile(fileId);
                    }
                };
                vm.$formOpts = {
                    "formId": formId,
                    "field": [{
                        "selector": ".start-time",
                        "name": "startTime",
                        "getValue": function () {
                            return vm.currentDate / 1;
                        }
                    }, {
                        "selector": ".project",
                        "name": "merchantId",
                        "getValue": function () {
                            return avalon.getVm(projectSelectorId).selectedValue;
                        },
                        "rule": function (val) {
                            if (val === -1) {
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
                        "name": "description"
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
                vm.$remindDialogOpts = {
                    "dialogId": remindDialogId,
                    "title": "提醒",
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
                        //提醒时间默认选中第一个
                        if (!vm.isRemindOpen) { //再关闭的情况下设置第一个选项
                            avalon.getVm(remindTimeId).select(0);
                            avalon.getVm(remindDialogId).remindDate = moment(vm.currentDate).format('YYYY-MM-DD');
                        }
                    },
                    "remindDate": moment(vm.currentDate).format('YYYY-MM-DD'),
                    "$remindDateOpts": {
                        "calendarId": remindDateCalendarId,
                        "onClickDate": function (d) {
                            var remindTimeVm = avalon.getVm(remindTimeId),
                                now = new Date ();
                            avalon.getVm(remindDialogId).remindDate = moment(d).format('YYYY-MM-DD');
                            if (moment(d).isSame(now, 'day')) {
                                remindTimeVm.setOptions(_.map(_.range(moment(now).hour() + 1, 24), function (num) {
                                    return {
                                        "text": _.str.lpad(num, 2, '0') + ':' + '00',
                                        "value": num
                                    };
                                }));
                            } else {
                                remindTimeVm.setOptions(_.map(_.range(0, 24), function (num) {
                                    return {
                                        "text": _.str.lpad(num, 2, '0') + ':' + '00',
                                        "value": num
                                    };
                                }));
                            }

                            $(this.widgetElement).hide();
                        }
                    },
                    /**
                     * 打开提醒时间选择面板
                     */
                    "openRemindCalendar": function () {
                        var meEl = $(this),
                            calendarVm = avalon.getVm(remindDateCalendarId),
                            calendarEl,
                            inputOffset = meEl.offset(),
                            vm = avalon.getVm(remindDialogId),
                            now = new Date();
                        if (!calendarVm) {
                            calendarEl = $('<div ms-widget="calendar,$,$remindDateOpts"></div>');
                            calendarEl.css({
                                "position": "absolute",
                                "z-index": 10000
                            }).hide().appendTo('body');
                            avalon.scan(calendarEl[0], [vm]);
                            calendarVm = avalon.getVm(remindDateCalendarId);
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
                        if (vm.remindDate) {
                            calendarVm.focusDate = moment(vm.remindDate, 'YYYY-MM-DD')._d;
                        } else {
                            calendarVm.focusDate = now;
                        }
                        //设置最大和最小日期
                        calendarVm.minDate = now;
                        calendarVm.maxDate = pageVm.currentDate;

                        calendarEl.css({
                            "top": inputOffset.top + meEl.outerHeight() - 1,
                            "left": inputOffset.left
                        }).show();
                    },
                    "$remindTimeOpts": {
                        "selectId": remindTimeId,
                        "options": _.map(_.range(0, 24), function (num) {
                            return {
                                "text": _.str.lpad(num, 2, '0') + ':' + '00',
                                "value": num
                            };
                        })
                        /*"options": [{   //存储以s为单位
                            "text": "事件发生30分钟前",
                            "value": 30 * 60
                        }, {
                            "text": "事件发生1小时前",
                            "value": 60 * 60
                        }, {
                            "text": "事件发生2小时前",
                            "value": 2 * 60 * 60
                        }, {
                            "text": "事件发生3小时前",
                            "value": 3 * 60 * 60
                        }, {
                            "text": "事件发生1天前",
                            "value": 24 * 60 * 60
                        }]*/
                    },
                    "submit": function (evt) {
                        var dialogVm = avalon.getVm(remindDialogId),
                            remindTimeVm = avalon.getVm(remindTimeId),
                            requestData;
                        if (vm.formState === 'add') {
                            vm.isRemindOpen = true; //反转状态
                            dialogVm.close();
                        } else if (vm.formState === 'edit') {   //单独的接口
                            //附加参数
                            requestData = {};
                            _.extend(requestData, {
                                "planId": routeData.params["id"] / 1,
                                "isRemind": !vm.isRemindOpen,
                                "isSendSms": false,
                                //"remindTime": !vm.isRemindOpen ? moment(vm.originCurrentDate).subtract(remindTimeVm.selectedValue, 'seconds') / 1 : 0
                                "remindTime": !vm.isRemindOpen ? moment(dialogVm.remindDate + ' ' + remindTimeVm.selectedValue, 'YYYY-MM-DD H') / 1 : 0
                            });
                            util.c2s({
                                "url": erp.BASE_PATH + 'fesPlan/updateRemindFesPlanById.do',
                                "type": "post",
                                "contentType":"application/json",
                                "data":JSON.stringify(requestData),
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        util.alert({
                                            "content": responseData.message,
                                            "iconType": "success"
                                        });
                                        vm.isRemindOpen = true; //反转状态
                                        dialogVm.close();
                                    }
                                }
                            });
                        }

                    }
                };
                vm.$reasonDialogOpts = {
                    "dialogId": reasonDialogId,
                    "width": 540,
                    "title": "",
                    "state": "",
                    "reasonLabel": "",  //原因label
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
                        //清理表单元素
                        var formVm = avalon.getVm(reasonFormId);
                        formVm.reset();
                        formVm.expectDate = "";
                    },
                    "submit": function (evt) {
                        var dialogVm = avalon.getVm(reasonDialogId),
                            formVm = avalon.getVm(reasonFormId),
                            expectTimeSelectorVm = avalon.getVm(expectTimeSelectorId),
                            requestData,
                            url;
                        if (formVm.validate()) {
                            requestData = formVm.getFormData();
                            if (dialogVm.state === "giveup") {  //放弃工作原因
                                url = erp.BASE_PATH + 'fesPlan/updateAbandonFesPlanById.do';
                            } else if (dialogVm.state === "delay") {
                                url = erp.BASE_PATH + 'fesPlan/updateDelayFesPlanById.do';
                                //延期的开始时间
                                _.extend(requestData, {
                                    "startTime": moment(requestData["startTime"] + ' ' + expectTimeSelectorVm.selectedValue, 'YYYY-MM-DD H') / 1
                                });
                            } else if (dialogVm.state == "complete") {  //完成计划
                                url = erp.BASE_PATH + 'fesPlan/updateCompleteFesPlanById.do';
                            }
                            //附加计划id
                            _.extend(requestData, {
                                "planId": routeData.params["id"] / 1
                            });
                            util.c2s({
                                "url": url,
                                "type": "post",
                                "contentType":"application/json",
                                "data":JSON.stringify(requestData),
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        util.alert({
                                            "content": responseData.message,
                                            "iconType": "success",
                                            "onSubmit": function () {
                                                //跳到工作计划
                                                //util.jumpPage('#/work/plan');
                                                if (vm.belongToRouteName === "work") {
                                                    util.jumpPage('#/work/plan');
                                                } else if (vm.belongToRouteName === "frontend") {
                                                    util.jumpPage('#/frontend/home');
                                                } else if (vm.belongToRouteName === "sales") {
                                                    util.jumpPage('#/sales/home');
                                                }
                                            }
                                        });
                                        dialogVm.close();
                                    }
                                }
                            });
                        }
                    }
                };
                vm.$reasonFormOpts = {
                    "formId": reasonFormId,
                    "expectDate": "",
                    "$expectDateOpts": {
                        "calendarId": expectDateCalendarId,
                        "onClickDate": function (d) {
                            avalon.getVm(reasonFormId).expectDate = moment(d).format('YYYY-MM-DD');
                            $(this.widgetElement).hide();
                        },
                        "minDate": vm.currentDate
                    },
                    "$expectTimeOpts": {
                        "selectId": expectTimeSelectorId,
                        "options": _.map(_.range(0, 24), function (num) {
                            return {
                                "text": _.str.lpad(num, 2, '0') + ':' + '00',
                                "value": num
                            };
                        })
                    },
                    /**
                     * 打开预期时间选择面板
                     */
                    "openExpectCalendar": function () {
                        var meEl = $(this),
                            calendarVm = avalon.getVm(expectDateCalendarId),
                            calendarEl,
                            inputOffset = meEl.offset(),
                            vm = avalon.getVm(reasonFormId);
                        if (!calendarVm) {
                            calendarEl = $('<div ms-widget="calendar,$,$expectDateOpts"></div>');
                            calendarEl.css({
                                "position": "absolute",
                                "z-index": 10000
                            }).hide().appendTo('body');
                            avalon.scan(calendarEl[0], [vm]);
                            calendarVm = avalon.getVm(expectDateCalendarId);
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
                        if (vm.expectDate) {
                            calendarVm.focusDate = moment(vm.expectDate, 'YYYY-MM-DD')._d;
                        } else {
                            calendarVm.focusDate = new Date();
                        }
                        //设置最小日期
                        calendarVm.minDate = moment(pageVm.currentDate).add('days', 1)._d;

                        calendarEl.css({
                            "top": inputOffset.top + meEl.outerHeight() - 1,
                            "left": inputOffset.left
                        }).show();
                    },
                    "field": [{
                        "selector": ".reason",
                        "name": "explanation",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "原因不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".expect-date",
                        "name": "startTime",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "预期日期不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }]
                };
                vm.titleReadonly = false;    //标题只读
                vm.title = '';
                vm.desc = '';
                vm.adjustCurrentDate = function () {
                    var startHour = avalon.getVm(startHourSelectorId).selectedValue,
                        startMinute = avalon.getVm(startMinuteSelectorId).selectedValue;
                    vm.currentDate = moment(vm.currentDate).hour(startHour).minute(startMinute)._d;
                };
                vm.setUnComplete = function () {
                    var unCompleteSelectorVm = avalon.getVm(unCompleteSelectorId);
                    unCompleteSelectorVm.select(0);
                    vm.unCompleteState = 'edit';
                    unCompleteSelectorVm.open();
                };
                vm.setComplete = function () {
                    var dialogVm = avalon.getVm(reasonDialogId);
                    vm.unCompleteState = 'view';
                    dialogVm.title = "完成";
                    dialogVm.state = "complete";
                    dialogVm.reasonLabel = "请说明完成工作的情况";
                    dialogVm.open();
                };
                vm.clickRemindSetting = function () {
                    var requestData;
                    if (!vm.isRemindOpen) {
                        avalon.getVm(remindDialogId).open();
                    } else {
                        if (vm.formState === 'edit') {
                            util.confirm({
                                "iconType": "info",
                                "content": "您确定要关闭工作计划提醒吗？",
                                "onSubmit": function () {
                                    requestData = {
                                        "planId": routeData.params["id"] / 1,
                                        "isRemind": false,
                                        "isSendSms": false,
                                        "remindTime": 0
                                    };
                                    util.c2s({
                                        "url": erp.BASE_PATH + 'fesPlan/updateRemindFesPlanById.do',
                                        "type": "post",
                                        "contentType":"application/json",
                                        "data":JSON.stringify(requestData),
                                        "success": function (responseData) {
                                            if (responseData.flag) {
                                                util.alert({
                                                    "content": responseData.message,
                                                    "iconType": "success"
                                                });
                                                vm.isRemindOpen = false; //反转状态
                                                avalon.getVm(remindDialogId).remindDate = moment(vm.currentDate).format('YYYY-MM-DD');
                                                avalon.getVm(remindTimeId).select(0);
                                            }
                                        }
                                    });
                                }
                            });

                        } else if (vm.formState === 'add') {
                            vm.isRemindOpen = false; //反转状态
                            avalon.getVm(remindDialogId).remindDate = moment(vm.currentDate).format('YYYY-MM-DD');
                            avalon.getVm(remindTimeId).select(0);
                        }
                    }
                };
                /**
                 * 表单提交
                 */
                vm.submit = function () {
                    var formVm = avalon.getVm(formId),
                        remindTimeVm = avalon.getVm(remindTimeId),
                        uploaderVm = avalon.getVm(attachUploaderId),
                        requestData,
                        url,
                        exer;
                    if (formVm.validate()) {
                        exer = function () {
                            requestData = formVm.getFormData();
                            //处理提醒参数
                            requestData["isRemind"] = vm.isRemindOpen;
                            if (vm.isRemindOpen) {
                                //requestData["remindTime"] = moment(vm.currentDate).subtract(remindTimeVm.selectedValue, 'seconds') / 1;
                                requestData["remindTime"] = moment(avalon.getVm(remindDialogId).remindDate + ' ' + remindTimeVm.selectedValue, 'YYYY-MM-DD H') / 1;
                            } else {
                                requestData["remindTime"] = 0;
                            }
                            //附加其他默认信息
                            requestData["endTime"] = 0;
                            requestData["sponsor"] = loginUserData.id;    //发起人
                            requestData["userId"] = loginUserData.id;    //发起人
                            requestData["remark"] = "";
                            requestData["plansSource"] = "1";   //1=工作计划 or 2=工作指派
                            requestData["isSendSms"] = false;   //默认是false
                            //处理项目信息，如果项目是0，变成null
                            if (requestData["merchantId"] === 0) {
                                requestData["merchantId"] = null;
                            }
                            if (vm.formState === 'add') {
                                url = erp.BASE_PATH + 'fesPlan/saveFesPlan.do';
                            } else if (vm.formState === 'edit') {
                                url = erp.BASE_PATH + 'fesPlan/updateFesPlan.do';
                                requestData["planId"] = routeData.params["id"] / 1;
                            }
                            util.c2s({
                                "url": url,
                                "type": "post",
                                "contentType":"application/json",
                                "data":JSON.stringify(requestData),
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        //util.jumpPage('#/work/plan');   //跳转到工作日历
                                        if (vm.belongToRouteName === "work") {
                                            util.jumpPage('#/work/plan');
                                        } else if (vm.belongToRouteName === "frontend") {
                                            util.jumpPage('#/frontend/home');
                                        } else if (vm.belongToRouteName === "sales") {
                                            util.jumpPage('#/sales/home');
                                        }
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

            var cptInitState = {     //记录各部分组件的初始化状态，是否初始化完毕
                "planItemType": false,
                "communicationFormType": false
            };
            pageModEvent.on('cptInited', function () {
                //编辑态下填充默认数据
                if (pageVm.formState === "edit") {
                    setPlanData(function (data) {
                        initProjectSelector(function () {
                            var projectVm = avalon.getVm(projectSelectorId),
                                contactVm = avalon.getVm(contactId),
                                planItemTypeVm = avalon.getVm(planItemTypeId);
                            //设置项目默认值
                            pageVm.updateContactByProject(data.merchant_id, function () {
                                contactVm.selectValue(data.contact_id);
                                pageVm.lockContact = true;  //锁定联系人列表，不随项目选择变化而变化
                                projectVm.selectValue(data.merchant_id);
                                avalon.nextTick(function () {
                                    if (data.plan_item_type == "1") {
                                        planItemTypeVm.readonly = true;
                                        projectVm.readonly = true;
                                    } else {
                                        planItemTypeVm.readonly = false;
                                        projectVm.readonly = false;
                                        //如果是工作计划是当前日期，并且用户修改的默认事项不是讲解月报时，滤掉讲解日报项
                                        if (moment(pageVm.currentDate).isSame(new Date(), 'day')) {
                                            planItemTypeVm.setOptions(_.reject(planItemTypeVm.getOptions().$model, function (itemData) {
                                                return itemData.value == "1";
                                            }));
                                        }
                                    }
                                    //设置事项默认值
                                    planItemTypeVm.selectValue(data.plan_item_type);

                                    pageVm.lockContact = false; //解锁
                                    avalon.nextTick(function () {
                                        //设置标题
                                        pageVm.title = data.title;
                                    });
                                });
                            });
                        });
                    });
                } else {
                    initProjectSelector();
                }
            });
            initCpt();

            /**
             * 组件初始化
             */
            function initCpt () {
                var planItemTypeVm = avalon.getVm(planItemTypeId),
                    communicationFormTypeVm = avalon.getVm(communicationFormTypeId);

                //请求事项类型
                util.c2s({
                    "url": erp.BASE_PATH + "dictionary/querySysDictionaryByType.do",
                    "type": "post",
                    "data":  'dictionaryType=00000063',
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            //如果是工作计划是当前日期，创建功能日报时滤掉讲解日报项
                            if (pageVm.formState === "add" && moment(pageVm.currentDate).isSame(new Date(), 'day')) {
                                data = _.reject(data, function (itemData) {
                                    return itemData.dictionary_key === "1";
                                });
                            }
                            planItemTypeVm.setOptions([{
                                "text": "请选择",
                                "value": 0
                            }].concat(_.map(data, function (itemData) {
                                return {
                                    "text": itemData["dictionary_value"],
                                    "value": itemData["dictionary_key"]
                                };
                            })));
                            cptInitState["planItemType"] = true;
                            if (_.every(cptInitState, function (initState) {
                                return initState;
                            })) {
                                pageModEvent.trigger('cptInited');
                            }
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
                            cptInitState["communicationFormType"] = true;
                            if (_.every(cptInitState, function (initState) {
                                return initState;
                            })) {
                                pageModEvent.trigger('cptInited');
                            }
                        }
                    }
                });
            }

            function initProjectSelector (callback) {
                var projectVm = avalon.getVm(projectSelectorId);
                 //请求项目列表
                util.c2s({
                    "url": erp.BASE_PATH + "synMerchant/getSynMerchantByUserId.do",
                    "type": "get",
                    "contentType":"application/json",
                    "data": {
                        "userId": pageVm.userId
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
                            callback && callback();
                        }
                    }
                });
            }

            /**
             * 设置默认计划数据
             */
            function setPlanData (callback) {
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
                            pageVm.userId = data.user_id;
                            pageVm.currentDate = moment(data.start_time)._d;
                            pageVm.originCurrentDate = moment(data.start_time)._d;  //保存原始的开始时间
                            avalon.getVm(startHourSelectorId).selectValue(moment(data.start_time).hour());
                            avalon.getVm(startMinuteSelectorId).selectValue(moment(data.start_time).minute());
                            avalon.getVm(communicationFormTypeId).selectValue(data.communication_form_type);
                            pageVm.desc = data.description;
                            pageVm.isRemindOpen = data.is_remind;    //提醒状态
                            //设置提醒时间
                            if (pageVm.isRemindOpen) {
                                //avalon.getVm(remindTimeId).selectValue((moment(data.start_time) - moment(data.remind_time)) / 1000);
                                avalon.getVm(remindTimeId).selectValue(moment(data.remind_time).hour());
                                avalon.getVm(remindDialogId).remindDate = moment(data.remind_time).format('YYYY-MM-DD');
                            }
                            //设置附件信息
                            avalon.getVm(attachUploaderId).uploadList = _.map(data.attachmentList, function (itemData) {
                                return {
                                    "name": itemData.original_file_name,
                                    "attachmentName": itemData.attachment_name,
                                    "attachmentSize": itemData.attachment_size,
                                    "attachmentSuffix": itemData.attachment_suffix,
                                    "attachmentId": itemData.id,
                                    "id": util.generateID(),
                                    "uploaded": true,
                                    "downloadUrl": erp.BASE_PATH + 'fesOnlineProcess/download.do?relPath=' + data.planFilePath + '/' + itemData.attachment_name
                                };
                            });
                            //设置标题是否只读
                            pageVm.titleReadonly = (data.sponsor == loginUserData.id ? false : true);
                            //成功回调
                            callback && callback(data);
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
                remindDialogId = pageName + '-remind-dialog',
                remindTimeId = pageName + '-remind-time-selector',
                reasonDialogId = pageName + '-reason-dialog',
                reasonFormId = pageName + '-reason-form',
                unCompleteSelectorId = pageName + '-un-complete-selector',
                expectDateCalendarId = pageName + '-expect-date-calendar',
                expectTimeSelectorId = pageName + '-expect-time-selector',
                remindDateCalendarId = pageName + '-remind-date-calendar';
            var calendarVm = avalon.getVm(expectDateCalendarId),
                expectTimeSelectorVm = avalon.getVm(expectTimeSelectorId),
                unCompleteSelectorVm = avalon.getVm(unCompleteSelectorId),
                remindCalendarVm = avalon.getVm(remindDateCalendarId),
                addNewContactVm = avalon.getVm(addNewContactId);
            calendarVm && $(calendarVm.widgetElement).remove();
            expectTimeSelectorVm && $(expectTimeSelectorVm.widgetElement).remove();
            unCompleteSelectorVm && $(unCompleteSelectorVm.widgetElement).remove();
            remindCalendarVm && $(remindCalendarVm.widgetElement).remove();
            $(avalon.getVm(reasonFormId).widgetElement).remove();
            $(avalon.getVm(reasonDialogId).widgetElement).remove();
            $(avalon.getVm(remindTimeId).widgetElement).remove();
            $(avalon.getVm(remindDialogId).widgetElement).remove();
            $(avalon.getVm(attachUploaderId).widgetElement).remove();
            $(avalon.getVm(projectSelectorId).widgetElement).remove();
            $(avalon.getVm(planItemTypeId).widgetElement).remove();
            $(avalon.getVm(contactId).widgetElement).remove();
            addNewContactVm && $(addNewContactVm.widgetElement).remove();
            $(avalon.getVm(communicationFormTypeId).widgetElement).remove();
            $(avalon.getVm(startMinuteSelectorId).widgetElement).remove();
            $(avalon.getVm(startHourSelectorId).widgetElement).remove();
            $(avalon.getVm(formId).widgetElement).remove();
            //解除事件注册
            pageModEvent.off('cptInited');
        }
    });

    return pageMod;
});
