/**
 * 期末考试规则
 */
define(['avalon', 'util', 'json', '../../../../widget/form/form', '../../../../widget/dialog/dialog', '../../../../widget/uploader/uploader', '../../../../widget/form/select', '../../../../module/qselector/qselector'], function (avalon, util, JSON) {
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
                writeRangeId = pageName + '-write-range';
            var courseId = routeData.params["id"] / 1; //从上一个页面练级中获取id
            var courseName = decodeURIComponent(routeData.params["courseName"]);

            var pageVm = avalon.define(pageName, function (vm) {
                vm.state = pageName.split('-').pop();
                vm.navCrumbs = [{
                    "text": "课程管理",
                    "href": "#/study/coursemanage"
                }, {
                    "text": courseName,
                    "href": "#/study/coursemanage"
                }, {
                    "text": "期末考试规则"
                }];
                vm.total = ''; //增加考题数量
                vm.timeLimit = ''; //时限
                vm.passScore = ''; //及格分数线
                vm.ruleId = 0; //规则ID
                vm.interviewScores = ''; //互动及格分数线
                vm.selectedWriteList = []; //write 选择的必考题
                vm.examRange = "all";   //all or part 全部试题选择和部分试题选择
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
                    "listPath": "finalExamRule/getQuestionByCourseId.do",
                    "onListRequestData": function (requestData) {
                        requestData.courseId = courseId;
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
                /**
                 * 打开试题选择弹框
                 */
                vm.openQuestionSelector = function () {
                    var selectorVm = avalon.getVm(qselectorId);
                    selectorVm.title = "选择必考题";
                    selectorVm.open();
                };
                vm.removeWriteItem = function () {
                    var itemM = this.$vmodel.$model;
                    vm.selectedWriteList.removeAt(itemM.$index);
                };
                vm.$formOpts = {
                    "formId": formId,
                    "field": [{
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
                        "selector": ".pass-score",
                        "name": "passScore",
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
                    }, {
                        "selector": ".interview-scores",
                        "name": "interviewScores",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "互动及格分数线不能为空";
                            } else {
                                if (!/^\d{1,6}$/.test(val)) {
                                    return "请输入有效的互动及格分数";
                                }
                                if (parseFloat(val, 10) === 0 || val.slice(0, 2) === '00') {
                                    return "请输入有效的互动及格分数";
                                }
                                return true;
                            }
                        }]
                    }],
                    "action": [{
                        "selector": ".f-submit",
                        "handler": function (evt) {
                            var formVm = avalon.getVm(formId),
                                rangeVm = avalon.getVm(writeRangeId),
                                requestData,
                                questionIds = [];
                            if (formVm.validate()) {
                                requestData = formVm.getFormData();
                                //附加额外参数
                                _.extend(requestData, {
                                    "id": pageVm.ruleId,
                                    "courseId": courseId,
                                    "finalExamRuleType": rangeVm.selectedValue
                                });

                                //考题范围
                                if (pageVm.examRange == "part") {
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
                                }
                                requestData.questionIdList = questionIds;
                                util.c2s({
                                    "url": erp.BASE_PATH + "finalExamRule/addFinalExamRule.do",
                                    "type": "post",
                                    "contentType": 'application/json',
                                    "data": JSON.stringify(requestData),
                                    "success": function (responseData) {
                                        if (responseData.flag) {
                                            util.alert({
                                                'iconType': 'success',
                                                'content': responseData.message,
                                                "onSubmit": function () {
                                                    util.jumpPage('#/study/coursemanage');  //跳转到课程管理
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
                    "url": erp.BASE_PATH + "finalExamRule/getFinalExamRuleByCourseId.do",
                    "data": JSON.stringify({
                        "courseId": courseId
                    }),
                    "contentType": 'application/json',
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            pageVm.ruleId = data.id || 0; //规则ID
                            pageVm.total = data.total || ''; //增加考题数量
                            pageVm.timeLimit = data.time_limit || ''; //时限
                            pageVm.passScore = data.pass_score || ''; //及格分数线
                            pageVm.interviewScores = data.interview_scores || ''; //互动及格分数线

                            avalon.getVm(writeRangeId).select(data.final_exam_rule_type / 1);
                            pageVm.selectedWriteList = _.map(data.finalExamQuestions, function (rowData) {
                                return {
                                    "id": rowData["question_id"],
                                    "question": rowData["question"],
                                    "questionType": rowData["question_type"],
                                    "pptName": rowData["ppt_name"]
                                };
                            });
                        }
                    }
                });
            }
            avalon.scan(pageEl[0], [pageVm]);
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var formId = pageName + '-form',
                qselectorId = pageName + '-qselector',
                writeRangeId = pageName + '-write-range';
            $(avalon.getVm(writeRangeId).widgetElement).remove();
            $(avalon.getVm(qselectorId).widgetElement).remove();
            $(avalon.getVm(formId).widgetElement).remove();
        }
    });

    return pageMod;
});
