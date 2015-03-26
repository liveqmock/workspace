/**
 * 需求列表
 */
define(['avalon', 'util', 'moment', 'json', '../../../widget/pagination/pagination', '../../../widget/form/form', '../../../widget/form/select', '../../../widget/dialog/dialog', '../../../widget/calendar/calendar'], function (avalon, util, moment, JSON) {
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
            var paginationId = pageName + '-pagination',
                dialogId = pageName + '-dialog',
                formId = pageName + '-form',
                planTypeSelectorId = pageName + 'plan-type-selector',
                projectSelectorId = pageName + 'project-selector',
                statusSelectorId = pageName + 'status-selector',
                yearSelectorId = pageName + 'year-selector',
                monthSelectorId = pageName + 'month-selector',
                planTypeSelectorId_ = pageName + 'plan-type-selector-',
                statusSelectorId_ = pageName + 'status-selector-',
                yearSelectorId_ = pageName + 'year-selector-',
                monthSelectorId_ = pageName + 'month-selector-',
                calendarId = pageName + '-calendar',
                prioritySelectorId_ = pageName + 'prioritySelectorId_',//优先级
                prioritySelectorId = pageName + 'prioritySelectorId',//搜索优先级
                reqSourceSelectorId_ = pageName + 'reqSourceSelectorId_',//来源;
                reqSourceSelectorId = pageName + 'reqSourceSelectorId';//搜索来源;

            var pageVm = avalon.define(pageName, function (vm) {
                vm.permission = {
                    "add": util.hasPermission('requirement_insert'),     //添加权限
                    "edit": util.hasPermission('requirement_update'),   //编辑权限
                    "delete": util.hasPermission('requirement_delete')    //删除权限
                };
                vm.operator = erp.getAppData('user').userName;
                vm.demandName = '';

                vm.reqSource = '';//来源
                vm.priority = '';//优先级
                vm.$yearOpts = {
                    "selectId": yearSelectorId,
                    "options": _.map(_.range(moment().year() + 1, moment().year() - 10, -1), function (year) {
                        return {
                            "text": year + '年',
                            "value": year
                        };
                    }),
                    "selectedIndex": 1
                };

                vm.$priorityOpts = {//优先级
                    "selectId": prioritySelectorId,
                    "options": [
                        {
                            "text": "全部级别",
                            "value": ""
                        },
                        {
                            "text": "1",
                            "value": "1"
                        },
                        {
                            "text": "2",
                            "value": "2"
                        },
                        {
                            "text": "3",
                            "value": "3"
                        },
                        {
                            "text": "4",
                            "value": "4"
                        },
                        {
                            "text": "5",
                            "value": "5"
                        }
                     ]
                };
                vm.$reqSourceOpts= {//来源
                    "selectId": reqSourceSelectorId,
                        "options": [{
                        "text": "全部来源",
                        "value": ""
                    }],
                    "selectedIndex": 0
                };
                vm.$monthOpts = {
                    "selectId": monthSelectorId,
                    "options": [{
                        "text": "全部",
                        "value": 0
                    }].concat(_.map(_.range(1, 13), function (month) {
                        return {
                            "text": month + '月',
                            "value": month
                        };
                    })),
                    "selectedIndex": 0
                };
                vm.$planTypeOpts = {
                    "selectId": planTypeSelectorId,
                    "options": [{
                        "text": "计划类型",
                        "value": ""
                     }],
                    "selectedIndex": 0
                };
                vm.$projectOpts = {
                    "selectId": projectSelectorId,
                    "options": [{
                        "text": "",
                        "value": ""
                     }],
                    "selectedIndex": 0
                };
                vm.$statusOpts = {
                    "selectId": statusSelectorId,
                    "options": [{
                        "text": "",
                        "value": ""
                     }],
                    "selectedIndex": 0
                };
                vm.gridTotalSize = 0;
                vm.gridData = [];
                vm.gridAllChecked = false;
                vm.gridSelected = [];
                vm.gridCheckAll = function () {
                    if (this.checked) {
                        vm.gridSelected = _.map(vm.gridData.$model, function (itemData) {
                            return itemData.id + '';
                        });
                    } else {
                        vm.gridSelected.clear();
                    }
                };
                /**
                 * 表格左下脚一个全选的快捷方式
                 */
                vm.scCheckAll = function () {
                    vm.gridSelected = _.map(vm.gridData.$model, function (itemData) {
                        return itemData.id + '';
                    });
                };
                vm.search = function () {
                    updateGrid(true);   //点查询跳到第一页
                };
                vm.$paginationOpts = {
                    "paginationId": paginationId,
                    "total": 0,
                    "perPages": 30,
                    "onJump": function (evt, vm) {
                        updateGrid();
                    }
                };
                vm.$aeDialogOpts = {
                    "title": "添加需求",
                    "dialogId": dialogId,
                    "width": 920,
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                            footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                '<div class="ui-dialog-btns">' +
                                    '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">确&nbsp;定</button>' +
                                    '<span class="separation"></span>' +
                                    '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close" ms-visible="addEditState != \'view\'">取&nbsp;消</button>' +
                                '</div>' +
                            '</div>';
                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "onOpen": function () {
                        //编辑态下设置默认值
                        var selectedData,
                            formVm = avalon.getVm(formId);
                        if (vm.addEditState == "edit" || vm.addEditState == "view") {
                            selectedData = _.find(vm.gridData.$model, function (itemData) {
                                return itemData.id == vm.gridSelected[0];
                            });
                            //来源
                            formVm.reqSource = selectedData.reqSourceText;
                            avalon.getVm(reqSourceSelectorId_).selectValue(selectedData.reqSource);
                            //优先级
                            formVm.priority = selectedData.priority;//优先级
                            avalon.getVm(prioritySelectorId_).selectValue(selectedData.priority);
                            //添加人
                            formVm.operator = selectedData.operator;

                            formVm.demandName = selectedData.name;
                            formVm.belongProject = _.map(selectedData.reqProjects, function (itemData) {
                                return itemData.id + '';
                            });
                            formVm.devChargePerson = selectedData.leader;
                            //计划类型
                            avalon.getVm(planTypeSelectorId_).selectValue(selectedData.requirementPlanType);
                            //计划月份
                            //avalon.getVm(yearSelectorId_).select(avalon.getVm(yearSelectorId).selectIndex);
                            //avalon.getVm(monthSelectorId_).select(avalon.getVm(monthSelectorId).selectIndex);
                            avalon.getVm(yearSelectorId_).selectValue(moment(selectedData.planTime).year());
                            avalon.getVm(monthSelectorId_).selectValue(moment(selectedData.planTime).month() + 1);
                            //状态
                            avalon.getVm(statusSelectorId_).selectValue(selectedData.requirementStatus);
                            formVm.statusText = selectedData.requirementStatusVal;
                            //是否通过需求评审
                            formVm.isRequirementsReview = selectedData.isRequirementsReview + '';
                            formVm.requirementsReviewText = translateIsRequirementsReview(selectedData.isRequirementsReview);
                            //是否通过产品评审
                            formVm.isProductReview = selectedData.isProductReview + '';
                            formVm.productReviewText = translateIsProductReview(selectedData.isProductReview);

                            formVm.requirementsReviewOpinion = selectedData.requirementsReviewOpinion;
                            formVm.productReviewOpinion = selectedData.productReviewOpinion;
                            //通过需求的评审时间
                            formVm.demandReviewTime = selectedData.requirementsReviewTime;
                            //通过产品的评审时间
                            formVm.productReviewTime = selectedData.productReviewTime;
                            //研发开始时间
                            formVm.devStartTime = selectedData.developmentTime;
                            //预计完成时间
                            formVm.devEndTime = selectedData.planEndTime;
                            //计划类型显示文本
                            formVm.planTypeText = selectedData.requirementPlanTypeVal;
                            //计划月份显示
                            formVm.planDateText = moment(avalon.getVm(yearSelectorId_).selectedValue + '-' + avalon.getVm(monthSelectorId_).selectedValue, 'YYYY-M').format('YYYY年MM月');
                            formVm.remark = selectedData.remark;
                            //上线时间
                            formVm.onlineTime = selectedData.onlineTime;
                        }
                    },
                    "onClose": function () {
                        //清除验证信息
                        var formVm = avalon.getVm(formId);
                        formVm.reset();
                        formVm.demandName = '';
                        formVm.devChargePerson = '';
                        formVm.demandReviewTime = '';
                        formVm.productReviewTime = '';
                        formVm.requirementsReviewOpinion = '';
                        formVm.productReviewOpinion = '';
                        formVm.devStartTime = '';
                        formVm.devEndTime = '';
                        avalon.getVm(planTypeSelectorId_).select(0, true);
                        avalon.getVm(yearSelectorId_).select(1, true);
                        avalon.getVm(monthSelectorId_).select(0, true);
                        avalon.getVm(statusSelectorId_).select(0, true);
                        avalon.getVm(reqSourceSelectorId_).select(0, true);
                        avalon.getVm(prioritySelectorId_).select(0, true);
                        formVm.isRequirementsReview = "";
                        formVm.isProductReview = "";
                        formVm.planTypeText = "";
                        formVm.planDateText = "";
                        formVm.belongProject = [];
                        formVm.remark = "";
                        formVm.statusText = "";
                        formVm.reqSource = "";//来源
                        formVm.priority = "";//优先级
                        //formVm.operator = '';//操作人
                        formVm.requirementsReviewText = "";
                        formVm.productReviewText = "";
                        formVm.onlineTime = "";
                    },
                    "submit": function (evt) {
                        var requestData,
                            url = erp.BASE_PATH + 'reqRequirement/saveReqRequirement.do',
                            dialogVm = avalon.getVm(dialogId),
                            formVm = avalon.getVm(formId),
                            addEditState = vm.addEditState,
                            id;
                        if (addEditState === "view") {  //查看状态下直接关闭
                            dialogVm.close();
                            return;
                        }
                        if (formVm.validate()) {
                            requestData = formVm.getFormData();
                            //编辑态附加参数
                            if (addEditState == 'edit') {
                                id = vm.gridSelected[0];
                                _.extend(requestData, {
                                    "id": id
                                });
                            }
                            //附加结束时间
                            _.extend(requestData, {
                                "endPlanTime": +moment(requestData["startPlanTime"]).add('months', 1)
                            });
                            //保证projectIds是list形式
                            requestData["projectIds"] = [].concat(requestData["projectIds"]);
                            //发送后台请求，编辑或添加

                            util.c2s({
                                "url": url,
                                "type": "post",
                                "data": JSON.stringify(requestData),
                                "contentType": "application/json",
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        if (addEditState == 'edit') {   //编辑状态更新数据,请求当前页
                                            updateGrid();
                                        } else { //添加状态直接刷新数据
                                            //重置过滤条件
                                            vm.resetQueryFilter();
                                            updateGrid(true);
                                        }
                                        //关闭弹框
                                        dialogVm.close();
                                    }
                                }
                            });
                        }
                    }
                };
                vm.$aeFormOpts = {
                    "formId": formId,
                    "field": [{
                        "selector": ".demand-name",
                        "name": "name",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "需求名称不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".belong-project",
                        "name": "projectIds",
                        "rule": function (val) {
                            if (!val.length) {
                                return "所属项目不能为空";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".dev-charge-person",
                        "name": "leader",
                        "rule": function (val) {
                            if (!val.length) {
                                return "开发负责人";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".plan-type",   //计划类型
                        "name": "requirementPlanType",
                        "getValue": function () {
                            return avalon.getVm(planTypeSelectorId_).selectedValue;
                        },
                        "rule": function (val) {
                            if (!val) {
                                return "请选择一种计划类型";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".plan-date",   //计划月份
                        "name": "startPlanTime",
                        "getValue": function () {
                            return +moment(avalon.getVm(yearSelectorId_).selectedValue + '-' + avalon.getVm(monthSelectorId_).selectedValue, 'YYYY-M');
                        }
                    }, {
                        "selector": ".status",   //状态
                        "name": "requirementStatus",
                        "getValue": function () {
                            return avalon.getVm(statusSelectorId_).selectedValue;
                        },
                        "rule": function (val) {
                            if (!val) {
                                return "请选择一种状态";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".reqSource",   //来源
                        "name": "reqSource",
                        "getValue": function () {
                            return avalon.getVm(reqSourceSelectorId_).selectedValue;
                        },
                        "rule": function (val) {
                            if (!val) {
                                return "请选择一种来源";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".priority",   //优先级
                        "name": "priority",
                        "getValue": function () {
                            return avalon.getVm(prioritySelectorId_).selectedValue;
                        },
                        "rule": function (val) {
                            if (!val) {
                                return "请选择优先级";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".demand-review-box",   //是否通过需求评审
                        "name": "isRequirementsReview",
                        "getValue": function () {
                            return avalon.getVm(formId).isRequirementsReview;
                        }
                    }, {
                        "selector": ".demand-no-passed-reason",   //未通过需求评审原因
                        "name": "requirementsReviewOpinion",
                        "rule": ["maxlength", function (val, rs) {
                            if (rs[0] !== true) {
                                return "原因不能超过200字限制";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".product-review-box",   //是否通过产品评审
                        "name": "isProductReview",
                        "getValue": function () {
                            return avalon.getVm(formId).isProductReview;
                        }
                    }, {
                        "selector": ".product-no-passed-reason",   //未通过产品评审原因
                        "name": "productReviewOpinion",
                        "rule": ["maxlength", function (val, rs) {
                            if (rs[0] !== true) {
                                return "原因不能超过200字限制";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".demand-review-time",   //需求评审时间
                        "name": "requirementsReviewTime",
                        "getValue": function () {
                            var val = _.str.trim($(this).val());
                            if (val) {
                                val = +moment(val, 'YYYY-MM-DD');
                            }
                            return val;
                        }
                    }, {
                        "selector": ".product-review-time",   //产品评审时间
                        "name": "productReviewTime",
                        "getValue": function () {
                            var val = _.str.trim($(this).val());
                            if (val) {
                                val = +moment(val, 'YYYY-MM-DD');
                            }
                            return val;
                        }
                    }, {
                        "selector": ".dev-start-time",   //研发开始时间
                        "name": "developmentTime",
                        "getValue": function () {
                            var val = _.str.trim($(this).val());
                            if (val) {
                                val = +moment(val, 'YYYY-MM-DD');
                            }
                            return val;
                        }
                    }, {
                        "selector": ".dev-end-time",   //预计完成时间
                        "name": "planEndTime",
                        "getValue": function () {
                            var val = _.str.trim($(this).val());
                            if (val) {
                                val = +moment(val, 'YYYY-MM-DD');
                            }
                            return val;
                        }
                    }, {
                        "selector": ".remark",   //备注
                        "name": "remark",
                        "rule": ["maxlength", function (val, rs) {
                            if (rs[0] !== true) {
                                return "备注不能超过200字限制";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".online-time",   //上线时间
                        "name": "onlineTime",
                        "getValue": function () {
                            var val = _.str.trim($(this).val());
                            if (val) {
                                val = +moment(val, 'YYYY-MM-DD');
                            }
                            return val;
                        }
                    }],
                    "$planTypeOpts_": {
                        "selectId": planTypeSelectorId_,
                        "options": [{
                            "text": "计划类型",
                            "value": ""
                         }],
                        "selectedIndex": 0
                    },
                    "$yearOpts_": {
                        "selectId": yearSelectorId_,
                        "options": _.map(_.range(moment().year() +1, moment().year() - 10, -1), function (year) {
                            return {
                                "text": year + '年',
                                "value": year
                            };
                        }),
                        "selectedIndex": 1
                    },
                    "$monthOpts_": {
                        "selectId": monthSelectorId_,
                        "options": _.map(_.range(1, 13), function (month) {
                            return {
                                "text": month + '月',
                                "value": month
                            };
                        }),
                        "selectedIndex": moment().month()
                    },
                    "$statusOpts_": {
                        "selectId": statusSelectorId_,
                        "options": [{
                            "text": "",
                            "value": ""
                         }],
                        "selectedIndex": 0
                    },
                    "$reqSourceOpts_":{//来源
                        "selectId": reqSourceSelectorId_,
                            "options": [{
                            "text": "",
                            "value": ""
                        }],
                        "selectedIndex": 0
                    },
                    "$priorityOpts_":{//优先级
                        "selectId": prioritySelectorId_,
                        "options": [
                            {
                                "text": "1",
                                "value": "1"
                            },
                            {
                                "text": "2",
                                "value": "2"
                            },
                            {
                                "text": "3",
                                "value": "3"
                            },
                            {
                                "text": "4",
                                "value": "4"
                            },
                            {
                                "text": "5",
                                "value": "5"
                            }
                        ],
                            "selectedIndex": 0
                    },
                    "reqSource": "",//来源
                    "priority": "",//优先级
                    "operator": vm.operator,//操作人
                    "demandName": "",
                    "projectList": [],
                    "belongProject": [],
                    "devChargePerson": "",
                    "demandReviewTime": "",
                    "productReviewTime": "",
                    "devStartTime": "",
                    "devEndTime": "",
                    "isRequirementsReview": "",
                    "isProductReview": "",
                    "planTypeText": "", //计划类型文本显示
                    "planDateText": "", //计划时间文本显示
                    "requirementsReviewOpinion": "",
                    "productReviewOpinion": "",
                    "remark": "",
                    "statusText": "",
                    "requirementsReviewText": "",
                    "productReviewText": "",
                    "onlineTime": "",   //上线时间
                    "formState": "add"  //表单状态，用于控制哪些字段可编辑，add or edit
                };
                vm.$calendarOpts = {
                    "calendarId": calendarId,
                    "bindInputName": "",
                    "onClickDate": function (d) {
                        var formVm = avalon.getVm(formId),
                            calendarVm = avalon.getVm(calendarId),
                            bindInputName = calendarVm.bindInputName;
                        formVm[bindInputName] = moment(d).format('YYYY-MM-DD');
                        $(calendarVm.widgetElement).hide();
                    }
                };
                vm.addEditState = 'add'; //add/edit/view ,添加/编辑/查看弹框状态
                vm.$watch('addEditState', function (val) {
                    avalon.getVm(formId).formState = val;
                });
                vm.addDemand = function () {
                    var dialogVm = avalon.getVm(dialogId);
                    vm.addEditState = 'add';
                    dialogVm.title = "添加需求";
                    dialogVm.open();
                };
                vm.editGridItem = function () {
                    var dialogVm = avalon.getVm(dialogId);
                    var itemM = this.$vmodel.$model;
                    //选中当前行，取消其他行选中
                    vm.gridSelected = [itemM.el.id + ''];
                    //切换到编辑模式
                    vm.addEditState = 'edit';
                    dialogVm.title = "编辑需求";
                    //打开编辑框
                    dialogVm.open();
                };
                vm.viewGridItem = function () {
                    var dialogVm = avalon.getVm(dialogId);
                    var itemM = this.$vmodel.$model;
                    //选中当前行，取消其他行选中
                    vm.gridSelected = [itemM.el.id + ''];
                    //切换到编辑模式
                    vm.addEditState = 'view';
                    dialogVm.title = "查看需求";
                    //打开编辑框
                    dialogVm.open();
                };
                vm.getCalendarVm = function () {
                    var calendarVm = avalon.getVm(calendarId),
                        calendarEl;
                    if (!calendarVm) {
                        calendarEl = $('<div class="demand-calendar fn-hide" ms-widget="calendar,$,$calendarOpts"></div>');
                        calendarEl.appendTo('body');
                        avalon.scan(calendarEl[0], vm);
                        calendarVm = avalon.getVm(calendarId);
                        //点击其他地方隐藏日历
                        util.regGlobalMdExcept({
                            "element": calendarEl,
                            "handler": function () {
                                calendarEl.hide();
                            }
                        });
                    }
                    return calendarVm;
                };
                vm.showCalendar = function () {
                    var inputEl = $(this),
                        val = _.str.trim(inputEl.val()),
                        inputOffset = inputEl.offset(),
                        calendarVm = vm.getCalendarVm(),
                        calendarEl = $(calendarVm.widgetElement),
                        top = inputOffset.top + inputEl.height() + 1;
                    if (inputOffset.top + inputEl.height() + 1 + calendarEl.outerHeight() > $(win).scrollTop() + $(win).height()) {
                        top = inputOffset.top - calendarEl.outerHeight();
                    }
                    calendarVm.bindInputName = inputEl.attr('name');
                    if (val) {
                        calendarVm.focusDate = moment(val, 'YYYY-MM-DD')._d;
                    } else {
                        calendarVm.focusDate = new Date();
                    }
                    calendarEl.css({
                        "left": inputOffset.left,
                        "top": top
                    }).show();
                };
                vm.showNoPassedTip = function () {
                    var tip = avalon(this).data('tip'),
                        tipId = pageName + '-no-passed-tip';
                    var meEl = $(this),
                        tipEl = $('#' + tipId),
                        meOffset = meEl.offset();
                    if (!tipEl.length) {
                        tipEl = $('<div id="' + tipId + '" class="grid-tip"></div>');
                        tipEl.html('<span class="text-content"></span><span class="icon-arrow"></span>');
                        tipEl.hide().appendTo('body');
                    }
                    $('.text-content', tipEl).text(tip);
                    tipEl.css({
                        "top": meOffset.top - tipEl.height() - 20,
                        "left": meOffset.left - tipEl.width() / 2
                    }).show();
                };
                vm.hideNoPassedTip = function () {
                    $('#' + pageName + '-no-passed-tip').hide();
                };
                vm.removeGridItem = function () {
                    var itemM = this.$vmodel.$model;
                    //选中当前行，取消其他行选中
                    vm.gridSelected = [itemM.el.id + ''];
                    //删除对应的item
                    vm._removeDemand();
                };
                vm.batchRemoveGridItem = function () {
                    vm._removeDemand();
                };
                vm._removeDemand = function () {
                    var ids,
                        requestData,
                        url;
                    if (vm.gridSelected.size() === 0) {
                        util.alert({
                            "content": "请先选中需求"
                        });
                    } else {
                        ids = vm.gridSelected.$model;
                        url = erp.BASE_PATH + 'reqRequirement/deleteReqRequirement/' + ids[0] + '.do';  //只支持删除第一个
                        util.confirm({
                            "content": "确定要删除选中的需求吗？",
                            "onSubmit": function () {
                                util.c2s({
                                    "url": url,
                                    "type": "post",
                                    "dataType":"json",
                                    "contentType": 'application/json',
                                    "success": function (responseData) {
                                        if (responseData.flag) {   //删除成功，手动清除前端数据
                                            _.each(ids, function (id) {
                                                var atIndex = -1;
                                                _.some(vm.gridData.$model, function (itemData, i) {
                                                    if (itemData.id == id) {
                                                        atIndex = i;
                                                    }
                                                });
                                                if (atIndex != -1) {
                                                    vm.gridData.removeAt(atIndex);
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        });
                    }
                };
                vm.resetQueryFilter = function () {
                    var now = moment();
                    vm.demandName = '';
                    avalon.getVm(yearSelectorId).selectValue(now.year());
                    avalon.getVm(monthSelectorId).selectValue(0);
                    //avalon.getVm(planTypeSelectorId).select(0);
                    avalon.getVm(projectSelectorId).select(0);
                    avalon.getVm(statusSelectorId).select(0);
                };
            });
            avalon.scan(pageEl[0]);
            //初始化组件
            initCpt(function () {
                //更新grid数据
                updateGrid(true);
            });
            //监听是否评审
            avalon.getVm(formId).$watch('isRequirementsReview', function (isRequirementsReview) {
                var formVm = avalon.getVm(formId);
                if (isRequirementsReview == '2') {
                    formVm.demandReviewTime = '';
                }
            });
            avalon.getVm(formId).$watch('isProductReview', function (isProductReview) {
                var formVm = avalon.getVm(formId);
                if (isProductReview == '2') {
                    formVm.productReviewTime = '';
                }
            });
            //未通过原因显示
            pageEl.on('mouseenter', '.no-passed-flag', function (evt) {
                pageVm.showNoPassedTip.call(this, evt);
            }).on('mouseleave', '.no-passed-flag', function (evt) {
                pageVm.hideNoPassedTip.call(this, evt);
            });

            function updateGrid(jumpFirst) {
                var paginationVm = avalon.getVm(paginationId),
                    yearSelectorVm = avalon.getVm(yearSelectorId),
                    monthSelectorVm = avalon.getVm(monthSelectorId),
                    requestData = {
                        "name": pageVm.demandName,
                        //"startPlanTime": +moment(avalon.getVm(yearSelectorId).selectedValue + '-' + avalon.getVm(monthSelectorId).selectedValue, 'YYYY-M'),
                        //"endPlanTime": +moment(avalon.getVm(yearSelectorId).selectedValue + '-' + (avalon.getVm(monthSelectorId).selectedValue + 1), 'YYYY-M'),
                        "priority": avalon.getVm(prioritySelectorId).selectedValue,
                        "reqSource": avalon.getVm(reqSourceSelectorId).selectedValue,
                        //"requirementPlanType": avalon.getVm(planTypeSelectorId).selectedValue,
                        "projectIds": avalon.getVm(projectSelectorId).selectedValue ? ([].concat(avalon.getVm(projectSelectorId).selectedValue)) : [],
                        "requirementStatus": avalon.getVm(statusSelectorId).selectedValue
                    };
                if (monthSelectorVm.selectedValue) {    //某个月
                    requestData["startPlanTime"] = +moment(yearSelectorVm.selectedValue + '-' + monthSelectorVm.selectedValue, 'YYYY-M');
                    requestData["endPlanTime"] = moment(requestData["startPlanTime"]).add('months', 1).subtract('seconds', 1) / 1;
                } else {    //全年查询
                    requestData["startPlanTime"] = +moment(yearSelectorVm.selectedValue, 'YYYY');
                    requestData["endPlanTime"] = +moment(yearSelectorVm.selectedValue, 'YYYY').add('years', 1).subtract('seconds', 1) / 1;
                }
                if (jumpFirst) {
                    requestData.pageSize = paginationVm.perPages;
                    requestData.pageNumber = 1;
                } else {
                    requestData.pageSize = paginationVm.perPages;
                    requestData.pageNumber = paginationVm.currentPage;
                }
                util.c2s({
                    "url": erp.BASE_PATH + "reqRequirement/listReqRequirements.do",
                    "data": JSON.stringify(requestData),
                    "contentType": "application/json",
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            paginationVm.total = data.totalSize;
                            if (jumpFirst) {
                                paginationVm.currentPage = 1;   //重设成第一页
                            }
                            pageVm.gridData = _.map(data.rows, function (itemData) {
                                itemData["projectNames"] = _.map(itemData["reqProjects"], function (projectData) {
                                    return projectData["name"];
                                }).join(',');
                                itemData["isOutDateOfPlanEndTime"] = itemData["onlineTime"] ? (!moment(itemData["onlineTime"]).isAfter(moment(itemData["planEndTime"]), 'day') ? false : true) : false;
                                itemData["requirementsReviewTime"] = itemData["requirementsReviewTime"] ? moment(itemData["requirementsReviewTime"]).format('YYYY-MM-DD') : '';
                                itemData["productReviewTime"] = itemData["productReviewTime"] ? moment(itemData["productReviewTime"]).format('YYYY-MM-DD') : '';
                                itemData["developmentTime"] = itemData["developmentTime"] ? moment(itemData["developmentTime"]).format('YYYY-MM-DD') : '';
                                itemData["planEndTime"] = itemData["planEndTime"] ? moment(itemData["planEndTime"]).format('YYYY-MM-DD') : '';
                                itemData["isRequirementsReviewText"] = '<span data-tip="' + itemData["requirementsReviewOpinion"] + '" class="' + (itemData["isRequirementsReview"] == '0' ? 'no-passed-flag state-out-date': '') + '">' + translateIsRequirementsReview(itemData["isRequirementsReview"]) + '</span>';
                                itemData["isProductReviewText"] = '<span data-tip="' + itemData["productReviewOpinion"] + '" class="' + (itemData["isProductReview"] == '0' ? 'no-passed-flag state-out-date': '') + '">' + translateIsProductReview(itemData["isProductReview"]) + '</span>';
                                //计划时间
                                itemData["planTimeText"] = itemData["planTime"] ? moment(itemData["planTime"]).format('YYYY/MM') : '';
                                //上线时间
                                itemData["onlineTime"] = itemData["onlineTime"] ? moment(itemData["onlineTime"]).format('YYYY-MM-DD') : '';
                                return itemData;
                            });
                            pageVm.gridTotalSize = data.totalSize;
                            pageVm.gridAllChecked = false;  //取消全选
                            pageVm.gridSelected.clear();

                        }
                    }
                });
            }
            /**
             * 初始化组件数据
             */
            function initCpt (callback) {
                //var requestData = {};
                util.c2s({
                    "url": erp.BASE_PATH + "reqRequirement/getDropDownLists.do",
                    /*"data": requestData,*/
                    "contentType": "application/json",
                    "type": "get",
                    "success": function (responseData) {
                        var data,
                            planType,
                            project,
                            reqSource,
                            state;
                        var formVm = avalon.getVm(formId);
                        if (responseData.flag) {
                            data = responseData.data;
                            planType = data.planType;
                            project = data.project;
                            state = data.state;
                            reqSource = data.reqSource;
                            //初始化计划类型selector
                            /*avalon.getVm(planTypeSelectorId).setOptions([{
                                "text": "计划类型",
                                "value": ""
                            }].concat(planType));*/
                            avalon.getVm(reqSourceSelectorId).setOptions([{
                                "text": "全部来源",
                                "value": ""
                            }].concat(reqSource));
                            avalon.getVm(planTypeSelectorId_).setOptions(planType);
                            avalon.getVm(projectSelectorId).setOptions([{
                                "text": "全部项目",
                                "value": ""
                            }].concat(project));
                            avalon.getVm(statusSelectorId).setOptions([{
                                "text": "全部状态",
                                "value": ""
                            }].concat(state));
                            avalon.getVm(statusSelectorId_).setOptions([{
                                "text": "全部状态",
                                "value": ""
                            }].concat(state));  //放开延期限制
                            //设置项目列表
                            formVm.projectList = [].concat(project);
                            //初始化各个组件
                            callback && callback.call(this, data);
                        }
                    }
                });
            }

            /*test*/
            util.c2s({
                "url": erp.BASE_PATH + "dictionary/querySysDictionaryByType.do",
                "type": "post",
                "data":  'dictionaryType=00000116',
                "success": function (responseData) {
                    var data,obj;
                    if (responseData.flag) {
                        data = responseData.data;
                        obj=_.map(data, function (itemData) {
                            return {
                                "text": itemData["dictionary_value"],
                                "value": itemData["dictionary_key"]
                            };
                        });
                        avalon.getVm(reqSourceSelectorId_).setOptions(obj);
                    }
                }
            });
            /*test-end*/

            //翻译
            function translateIsRequirementsReview (isRequirementsReview) {
                var isRequirementsReviewText = '';
                switch (isRequirementsReview) {
                    case "0":
                        isRequirementsReviewText = '未通过';
                        break;
                    case "1":
                        isRequirementsReviewText = '通过';
                        break;
                    case "2":
                        isRequirementsReviewText = '无需评审';
                        break;
                    default:
                        break;
                }
                return isRequirementsReviewText;
            }

            function translateIsProductReview (isProductReview) {
                var isProductReviewText = '';
                switch (isProductReview) {
                    case "0":
                        isProductReviewText = '未通过';
                        break;
                    case "1":
                        isProductReviewText = '通过';
                        break;
                    case "2":
                        isProductReviewText = '无需评审';
                        break;
                    default:
                        break;
                }
                return isProductReviewText;
            }
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var paginationId = pageName + '-pagination',
                dialogId = pageName + '-dialog',
                formId = pageName + '-form',
                planTypeSelectorId = pageName + 'plan-type-selector',
                projectSelectorId = pageName + 'project-selector',
                statusSelectorId = pageName + 'status-selector',
                yearSelectorId = pageName + 'year-selector',
                monthSelectorId = pageName + 'month-selector',
                planTypeSelectorId_ = pageName + 'plan-type-selector-',
                statusSelectorId_ = pageName + 'status-selector-',
                yearSelectorId_ = pageName + 'year-selector-',
                monthSelectorId_ = pageName + 'month-selector-',
                calendarId = pageName + '-calendar',
                prioritySelectorId_ = pageName + 'prioritySelectorId_',//优先级
                reqSourceSelectorId_ = pageName + 'reqSourceSelectorId_',//来源;
                prioritySelectorId = pageName + 'prioritySelectorId_',//搜索优先级
                reqSourceSelectorId = pageName + 'reqSourceSelectorId_';//搜索来源;
            var calendarVm = avalon.getVm(calendarId);
            calendarVm && $(calendarVm.widgetElement).remove();
            $(avalon.getVm(monthSelectorId_).widgetElement).remove();
            $(avalon.getVm(yearSelectorId_).widgetElement).remove();
            $(avalon.getVm(statusSelectorId_).widgetElement).remove();
            $(avalon.getVm(planTypeSelectorId_).widgetElement).remove();
            $(avalon.getVm(monthSelectorId).widgetElement).remove();
            $(avalon.getVm(yearSelectorId).widgetElement).remove();
            $(avalon.getVm(statusSelectorId).widgetElement).remove();
            $(avalon.getVm(projectSelectorId).widgetElement).remove();
            //$(avalon.getVm(planTypeSelectorId).widgetElement).remove();
            $(avalon.getVm(formId).widgetElement).remove();
            $(avalon.getVm(dialogId).widgetElement).remove();
            $(avalon.getVm(paginationId).widgetElement).remove();
            $(avalon.getVm(prioritySelectorId_).widgetElement).remove();
            $(avalon.getVm(reqSourceSelectorId_).widgetElement).remove();
            $(avalon.getVm(prioritySelectorId).widgetElement).remove();
            $(avalon.getVm(reqSourceSelectorId).widgetElement).remove();
        }
    });

    return pageMod;
});
