/**
 * 考试规则
 */
define(['avalon', 'util', 'json', '../../../../widget/form/form', '../../../../widget/dialog/dialog', '../../../../widget/uploader/uploader', '../../../../widget/form/select', '../../../../module/qselector/qselector', '../../../../module/pptselector/pptselector'], function (avalon, util, JSON) {
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
                qselectorId = pageName + '-qselector',
                pptselectorId = pageName + '-pptselector',
                pptRangeId = pageName + '-ppt-range',
                writeRangeId = pageName + '-write-range';
            var coursewareId = routeData.params["id"] / 1, //从上一个页面练级中获取id
                coursewareName = decodeURIComponent(routeData.params["coursewareName"]);
            var pageVm = avalon.define(pageName, function (vm) {
                vm.state = pageName.split('-').pop();
                vm.navCrumbs = [{
                    "text": "课件管理",
                    "href": "#/study/coursewaremanage"
                }, {
                    "text": coursewareName,
                    "href": "#/study/coursewaremanage"
                }, {
                    "text": "考卷规则"
                }];
                vm.paperType = 'write'; //考卷类型
                vm.isTest = "0";    //是否老员工参加考试
                vm.total = ''; //考题数量
                vm.timeLimit = ''; //时限
                vm.pptTimeLimit = ''; //PPT时限
                vm.passingScore = ''; //及格分数线
                vm.ruleId = 0; //规则ID
                vm.selectedWriteList = []; //write 选择的必考题
                vm.examRange = "all";   //all or part 全部试题选择和部分试题选择
                vm.selectedPptName = '';    //选中的ppt名称
                vm.selectedPptId = 0;  //选中的ppt id

                vm.$writeRangeOpts = {
                    "selectId": writeRangeId,
                    "options": [{
                        "text": "全部随机",
                        "value": "0"
                    }, {
                        "text": "部分随机",
                        "value": "1"
                    }],
                    "onSelect": function (val, text, selectedIndex) {
                        if (val == '0') {
                            vm.examRange = "all";
                            //vm.selectedWriteList.clear();
                        } else {
                            vm.examRange = "part";
                        }
                    }
                };
                vm.$pptRangeOpts = {
                    "selectId": pptRangeId,
                    "options": [{
                        "text": "随机PPT",
                        "value": "0"
                    }, {
                        "text": "指定PPT",
                        "value": "1"
                    }],
                    "onSelect": function (val, text, selectedIndex) {
                        if (val == '0') {
                            vm.examRange = "all";
                            //vm.selectedPptName = '';  //不清除原来的数据
                            //vm.selectedPptId = 0;
                        } else {
                            vm.examRange = "part";
                        }
                    }
                };
                /**
                 * 试题考试设置
                 */
                vm.$qselectorOpts = {
                    "qselectorId": qselectorId,
                    "multi": true,
                    "onListRequestData": function (requestData) {
                        requestData.coursewareId = coursewareId;
                        return requestData;
                    },
                    "onListReady": function (listVm) {
                        _.each(vm.selectedWriteList.$model, function (itemData) {
                            var atIndex = -1;
                            if (_.some(listVm.$model, function (itemData2, i) {
                                if (itemData2.id == itemData.id) {
                                    atIndex = i;
                                    return true;
                                }
                            })) {
                                listVm.set(atIndex, {
                                    "isSelected": true
                                });
                            }
                        });
                    },
                    "onSubmit": function (result) {
                        if (result.length) {
                            vm.selectedWriteList = _.map(result, function (itemData) {
                                return {
                                    "id": itemData["id"],
                                    "question": itemData["question"],
                                    "questionType": itemData["questionType"]
                                };
                            });
                        } else {
                            util.alert({
                                "iconType": "error",
                                "content": "请选择至少一个试题"
                            });
                        }
                    }
                };
                vm.$pptselectorOpts = {
                    "pptselectorId": pptselectorId,
                    "onListRequestData": function (requestData) {
                        requestData.coursewareId = coursewareId;
                        return requestData;
                    },
                    "onListReady": function (listVm) {
                        var atIndex = -1;
                        if (_.some(listVm.$model, function (itemData, i) {
                            if (itemData.id == vm.selectedPptId) {
                                atIndex = i;
                                return true;
                            }
                        })) {
                            listVm.set(atIndex, {
                                "isSelected": true
                            });
                        }
                    },
                    "onSubmit": function (result) {
                        if (result.length) {
                            vm.selectedPptName = result[0].pptName + '&nbsp;';
                            vm.selectedPptId = result[0].id;
                        } else {
                            util.alert({
                                "iconType": "error",
                                "content": "请选择一个PPT"
                            });
                        }
                    }
                };
                /**
                 * 打开试题选择弹框
                 */
                vm.openQuestionSelector = function () {
                    var selectorVm = avalon.getVm(qselectorId);
                    selectorVm.title = "选择必考题";
                    selectorVm.open();
                };
                vm.openPptSelector = function () {
                    var selectorVm = avalon.getVm(pptselectorId);
                    selectorVm.title = "选择PPT";
                    selectorVm.open();
                };
                vm.switchPaperType = function (evt) {
                    var elEl = avalon(this),
                        typeName = elEl.data('name');
                    var writeRangeVm = avalon.getVm(writeRangeId),
                        pptRangeVm = avalon.getVm(pptRangeId),
                        qselectorVm = avalon.getVm(qselectorId);
                    vm.paperType = typeName;
                    //清空考题设置
                    vm.selectedWriteList.clear();
                    vm.selectedPptId = 0;
                    vm.selectedPptName = '';
                    vm.examRange = 'all';
                    writeRangeVm && writeRangeVm.select(0);
                    pptRangeVm && pptRangeVm.select(0);
                    if (typeName === "write") {
                        qselectorVm.listPath = "rule/getQuestionByCoursewareId.do";
                    } else if (typeName === "practice") {
                        qselectorVm.listPath = "rule/getPracticeQuestionByCoursewareId.do";
                    }
                };

                vm.removeWriteItem = function () {
                    var itemM = this.$vmodel.$model;
                    vm.selectedWriteList.removeAt(itemM.$index);
                };
                vm.$formOpts = {
                    "formId": formId,
                    "field": [{
                        "selector": ".paper-type",
                        "name": "paperType",
                        "getValue": function () {
                            return $(this).filter('.mn-radio-state-selected').data('name');
                        }
                    }, {
                        "selector": ".for-old-staff",
                        "name": "isTest"
                    }, {
                        "selector": ".time-limit",
                        "name": "timeLimit",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "考试时限不能为空";
                            } else {
                                if (!/^\d{1,6}$/.test(val)) {
                                    return "请输入有效的考试时限";
                                }
                                if (parseFloat(val, 10) === 0 || val.slice(0, 2) === '00') {
                                    return "请输入有效的考试时限";
                                }
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".total",
                        "name": "total",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "考题数量不能为空";
                            } else {
                                if (!/^\d{1,6}$/.test(val)) {
                                    return "请输入有效的考题数量";
                                }
                                if (parseFloat(val, 10) === 0 || val.slice(0, 2) === '00') {
                                    return "请输入有效的考题数量";
                                }
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".passing-score",
                        "name": "passingScore",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "及格分数线不能为空";
                            } else {
                                if (!/^\d{1,6}$/.test(val)) {
                                    return "请输入有效的及格分数";
                                }
                                if (parseFloat(val, 10) === 0 || val.slice(0, 2) === '00') {
                                    return "请输入有效的及格分数";
                                }
                                return true;
                            }
                        }]
                    }],
                    "action": [{
                        "selector": ".f-submit",
                        "handler": function (evt) {
                            var formVm = avalon.getVm(formId),
                                rangeVm = (pageVm.paperType == "write" || pageVm.paperType == "practice") ? avalon.getVm(writeRangeId) : avalon.getVm(pptRangeId),
                                requestData,
                                questionIds = [];
                            if (formVm.validate()) {
                                requestData = formVm.getFormData();
                                //更新或添加form fileds其他属性
                                //附加额外参数
                                _.extend(requestData, {
                                    "id": pageVm.ruleId,
                                    "coursewareId": coursewareId,
                                    "ruleType": rangeVm.selectedValue
                                });
                                //考题范围
                                if (pageVm.examRange == "part") {
                                    if (pageVm.paperType == "write" || pageVm.paperType == "practice") {
                                        questionIds = _.map(pageVm.selectedWriteList.$model, function (itemData) {
                                            return itemData.id;
                                        });
                                        if (!questionIds.length) {
                                            util.alert({
                                                'iconType': 'error',
                                                'content': '请指定有效的考题范围'
                                            });
                                            return;
                                        } else if (questionIds.length > requestData["total"] / 1) {
                                            util.alert({
                                                'iconType': 'error',
                                                'content': '必考题数量不能大于增加考题数量'
                                            });
                                            return;
                                        }
                                    } else {
                                        questionIds.push(pageVm.selectedPptId);
                                        if (!pageVm.selectedPptId) {
                                            util.alert({
                                                'iconType': 'error',
                                                'content': '请选择一个ppt考题'
                                            });
                                            return;
                                        }
                                    }
                                }
                                requestData.questionIdList = questionIds;
                                //发送后台请求，编辑或添加
                                util.c2s({
                                    "url": erp.BASE_PATH + "rule/addRule.do",
                                    "type": "post",
                                    "contentType": 'application/json',
                                    "data": JSON.stringify(requestData),
                                    "success": function (responseData) {
                                        if (responseData.flag) {
                                            util.alert({
                                                'iconType': 'success',
                                                'content': responseData.message,
                                                'onSubmit': function () {
                                                    util.jumpPage('#/study/coursewaremanage');  //跳转到课件管理
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    }]
                };
            });
            updateView();
            /**
             * 页面加载时动态填充数据
             */
            function updateView() {
                util.c2s({
                    "url": erp.BASE_PATH + "rule/getRuleByCoursewareId.do",
                    "data": JSON.stringify({
                        "coursewareId": coursewareId
                    }),
                    "contentType": 'application/json',
                    "success": function (responseData) {
                        var data;
                        var qselectorVm = avalon.getVm(qselectorId);
                        if (responseData.flag) {
                            data = responseData.data;

                            pageVm.paperType = converToPaperType(data.paper_type);
                            pageVm.isTest = data.is_test;
                            pageVm.passingScore = data.passing_score || ''; //及格分数线
                            pageVm.timeLimit = data.time_limit || ''; //时限
                            pageVm.ruleId = data.id || 0; //规则ID
                            pageVm.total = data.total || ''; //考题数量
                            if (pageVm.paperType === "write") {
                                qselectorVm.listPath = "rule/getQuestionByCoursewareId.do";
                            } else if (pageVm.paperType === "practice") {
                                qselectorVm.listPath = "rule/getPracticeQuestionByCoursewareId.do";
                            }

                            if (data.paper_type == '1') {   //ppt默认设置
                                if (data.rule_type == '1' && data.requiredQuestions.length) {
                                    pageVm.selectedPptId = data.requiredQuestions[0].question_id;
                                    pageVm.selectedPptName = data.requiredQuestions[0].question + '&nbsp;';
                                }
                                avalon.getVm(pptRangeId).select(data.rule_type / 1);
                            } else {
                                pageVm.selectedWriteList = _.map(data.requiredQuestions, function (rowData) {
                                    return {
                                        "id": rowData["question_id"],
                                        "question": rowData["question"],
                                        "questionType": rowData["question_type"],
                                        "pptName": rowData["ppt_name"]
                                    };
                                });
                                avalon.getVm(writeRangeId).select((data.rule_type || 0) / 1);
                            }
                        }
                    }
                });
            }

            avalon.scan(pageEl[0], [pageVm]);
            /**
             * paperType翻译
             */
            function converToPaperType(paperTypeValue) {
                var paperType;
                switch (paperTypeValue) {
                    case "0":
                        paperType = "write";
                        break;
                    case "1":
                        paperType = "ppt";
                        break;
                    case "2":
                        paperType = "practice";
                        break;
                    default:
                        paperType = "";
                        break;
                }
                return paperType;
            }
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var formId = pageName + '-form',
                qselectorId = pageName + '-qselector',
                pptselectorId = pageName + '-pptselector',
                pptRangeId = pageName + '-ppt-range',
                writeRangeId = pageName + '-write-range';
            var pptRangeVm = avalon.getVm(pptRangeId),
                pptSelectorVm = avalon.getVm(pptselectorId),
                qselectorVm = avalon.getVm(qselectorId),
                writeRangeVm = avalon.getVm(writeRangeId);
            writeRangeVm && $(writeRangeVm.widgetElement).remove();
            pptRangeVm && $(pptRangeVm.widgetElement).remove();
            pptSelectorVm && $(pptSelectorVm.widgetElement).remove();
            qselectorVm && $(qselectorVm.widgetElement).remove();
            $(avalon.getVm(formId).widgetElement).remove();
        }
    });

    return pageMod;
});
