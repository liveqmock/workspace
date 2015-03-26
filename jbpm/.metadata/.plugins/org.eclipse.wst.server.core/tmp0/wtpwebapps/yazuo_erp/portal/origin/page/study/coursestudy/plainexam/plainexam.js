/**
 * 考试-单选题/多选题/判断题
 */
define(['avalon', 'util', 'json', '../../../../widget/form/form'], function (avalon, util, JSON) {
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
                examCompleteId = pageName + '-exam-complete';
            var loginUserData = erp.getAppData("user");
            var pageVm = avalon.define(pageName, function (vm) {
                vm.$skipArray = ["tId", "originData"];
                vm.navCrumbs = [{
                    "text": "课程学习",
                    "href": "#/study/coursestudy"
                }, {
                    "text": '',  //课程名
                    "href": "#"
                }, {
                    "text": '课后习题'
                }];
                vm.isFinalExam = (routeData.params["id"] == "0" ? true : false); //是否是期末考试
                vm.isOldStaff = (loginUserData.isFormal == "1");  //是否是老员工
                vm.duration = 0;    //以小时为单位
                vm.leftHour = 0;
                vm.leftMinute = 0;
                vm.leftSecond = 0;
                vm.tId = 0;
                vm.originData = [];
                /*vm.questionTypeList = [{
                    "questionTypeTitle": "1、单选题",
                    "questionType": "single",
                    "questionList": [{
                        "questionTitle": "1、雅座是一家什么公司",
                        "id": 1,
                        "isAnswered": false,
                        "optionList": [{
                            "optionContent": "餐饮行业，111111餐饮行业，1"
                        }, {
                            "optionContent": "餐饮行业，222222"
                        }, {
                            "optionContent": "餐饮行业，333333"
                        }, {
                            "optionContent": "餐饮行业，444444"
                        }]
                    }, {
                        "questionTitle": "2、雅座是一家什么公司",
                        "id": 2,
                        "isAnswered": false,
                        "optionList": [{
                            "optionContent": "餐饮行业，111111"
                        }, {
                            "optionContent": "餐饮行业，222222"
                        }, {
                            "optionContent": "餐饮行业，333333"
                        }, {
                            "optionContent": "餐饮行业，444444"
                        }]
                    }]
                }, {
                    "questionTypeTitle": "2、多选题",
                    "questionType": "multi",
                    "questionList": [{
                        "questionTitle": "1、雅座是一家什么公司",
                        "id": 3,
                        "isAnswered": false,
                        "optionList": [{
                            "optionContent": "餐饮行业，111111"
                        }, {
                            "optionContent": "餐饮行业，222222"
                        }, {
                            "optionContent": "餐饮行业，333333"
                        }, {
                            "optionContent": "餐饮行业，444444"
                        }]
                    }, {
                        "questionTitle": "2、雅座是一家什么公司",
                        "id": 4,
                        "isAnswered": false,
                        "optionList": [{
                            "optionContent": "餐饮行业，111111"
                        }, {
                            "optionContent": "餐饮行业，222222"
                        }, {
                            "optionContent": "餐饮行业，333333"
                        }, {
                            "optionContent": "餐饮行业，444444"
                        }]
                    }]
                }, {
                    "questionTypeTitle": "3、判断题",
                    "questionType": "bool",
                    "questionList": [{
                        "questionTitle": "1、雅座是一家什么公司",
                        "id": 5,
                        "isAnswered": false,
                        "optionList": [{
                            "optionContent": "餐饮行业，111111"
                        }, {
                            "optionContent": "餐饮行业，222222"
                        }, {
                            "optionContent": "餐饮行业，333333"
                        }, {
                            "optionContent": "餐饮行业，444444"
                        }]
                    }, {
                        "questionTitle": "2、雅座是一家什么公司",
                        "id": 6,
                        "isAnswered": false,
                        "optionList": [{
                            "optionContent": "餐饮行业，111111"
                        }, {
                            "optionContent": "餐饮行业，222222"
                        }, {
                            "optionContent": "餐饮行业，333333"
                        }, {
                            "optionContent": "餐饮行业，444444"
                        }]
                    }]
                }];*/
                vm.questionTypeList = [];
                vm.$formOpts = {
                    "formId": formId,
                    "field": [{
                        "selector": ".question-item",
                        "name": "answers",
                        "getValue": function () {
                            var result = [],
                                optionItemsEl = $('.question-option', pageEl);
                            optionItemsEl.each(function () {
                                var meEl = $(this),
                                    questionId = meEl.data('questionId'),
                                    optionId = meEl.val(),
                                    isNew = false,
                                    questionData = _.find(result, function (itemData) {
                                        return itemData.id == questionId;
                                    }),
                                    examOptionVOs;
                                if (!questionData) {
                                    questionData = {};
                                    examOptionVOs = [];
                                    isNew = true;
                                } else {
                                    examOptionVOs = questionData.examOptionVOs || [];
                                }
                                examOptionVOs.push({
                                    "id": optionId / 1,
                                    "isSelected": meEl.prop('checked')? "1" : "0"
                                });
                                questionData.id = questionId / 1;
                                questionData.examOptionVOs = examOptionVOs;
                                if (isNew) {
                                    result.push(questionData);
                                }
                            });
                            return result;
                        },
                        "rule": function () {
                            //考试时间未到时，如果有未提交试题，不能通过提交
                            var progressPanelEl = $('.progress-panel', pageEl),
                                timerPanelEl = $('.timer-panel', progressPanelEl),
                                questionStatePanelEl = $('.question-state-panel', progressPanelEl),
                                isTimeOut = true,
                                isAllAnswered = true;
                            $('.num', timerPanelEl).each(function () {
                                if ($(this).text() / 1) {
                                    isTimeOut = false;
                                    return false;
                                }
                            });
                            $('.question-item', questionStatePanelEl).each(function () {
                                if (!$(this).hasClass('is-answered')) {
                                    isAllAnswered = false;
                                    return false;
                                }
                            });
                            return {
                                "handler": function () {
                                    if (!isTimeOut && !isAllAnswered) {
                                        util.alert({
                                            "iconType": "error",
                                            "content": "请检查答题板，完成未答的题"
                                        });
                                    }
                                },
                                "isPassed": isTimeOut || isAllAnswered
                            };
                        }
                    }],
                    "action": [{
                        "selector": ".f-submit",
                        "handler": function () {
                            var formVm = avalon.getVm(formId),
                                examCompleteVm = avalon.getVm(examCompleteId);
                            var requestData,
                                url;
                            if (formVm.validate()) {
                                requestData = formVm.getFormData();
                                if (vm.isFinalExam) {
                                    url = erp.BASE_PATH + "train/exam/saveFinalExam.do";
                                } else {
                                    url = erp.BASE_PATH + "train/exam/saveExam.do";
                                }
                                if (_.isEmpty(requestData)) {
                                    util.alert({
                                        "iconType": "error",
                                        "content": "没有任何试题作答"
                                    });
                                    return;
                                }
                                //发送后台请求，编辑或添加
                                util.c2s({
                                    "url": url,
                                    "type": "post",
                                    "data": JSON.stringify(requestData["answers"]),
                                    "contentType" : "application/json",
                                    "success": function (responseData) {
                                        var data,
                                            examResult,
                                            judgement,
                                            multiple,
                                            single,
                                            nextCourse,
                                            isFinalExamAvailable,
                                            courseId,
                                            detailList = [];
                                        if (responseData.flag) {
                                            /*util.alert({
                                                "iconType": "success",
                                                "content": responseData.message
                                            });*/
                                            //util.jumpPage('#/study/coursestudy');   //自动跳转到课程学习
                                            data = responseData.data;
                                            examResult = data.examResult;
                                            judgement = examResult.judgement;
                                            multiple = examResult.multiple;
                                            single = examResult.single;
                                            nextCourse = data.nextCourse;
                                            isFinalExamAvailable = data.isFinalExamAvailable;
                                            courseId = data.courseId;

                                            examCompleteVm.detailList.clear();
                                            if (single) {
                                                examCompleteVm.detailList.push({
                                                    "questionTypeName": "单选题",
                                                    "rightList": single.correct,
                                                    "wrongList": single.wrong,
                                                    "leftList": single.unanswered,
                                                    "score": single.score
                                                });
                                            }
                                            if (multiple) {
                                                examCompleteVm.detailList.push({
                                                    "questionTypeName": "多选题",
                                                    "rightList": multiple.correct,
                                                    "wrongList": multiple.wrong,
                                                    "leftList": multiple.unanswered,
                                                    "score": multiple.score
                                                });
                                            }
                                            if (judgement) {
                                                examCompleteVm.detailList.push({
                                                    "questionTypeName": "判断题",
                                                    "rightList": judgement.correct,
                                                    "wrongList": judgement.wrong,
                                                    "leftList": judgement.unanswered,
                                                    "score": judgement.score
                                                });
                                            }
                                           /* examCompleteVm.detailList = [{
                                                "questionTypeName": "单选题",
                                                "rightList": [141, 142, 143],
                                                "wrongList": [141, 142, 143],
                                                "leftList": [141, 142, 143],
                                                "score": 16
                                            }, {
                                                "questionTypeName": "多选题",
                                                "rightList": [141, 142, 143],
                                                "wrongList": [141, 142, 143],
                                                "leftList": [141, 142, 143],
                                                "score": 16
                                            }, {
                                                "questionTypeName": "判断题",
                                                "rightList": [141, 142, 143],
                                                "wrongList": [141, 142, 143],
                                                "leftList": [141, 142, 143],
                                                "score": 16
                                            }];*/
                                            examCompleteVm.isPassed = data.passed;
                                            if (data.passed) {
                                                examCompleteVm.tipContent = '恭喜您，亲~您取得了' + data.score + '分的好成绩，要继续保持哦~';
                                            } else {
                                                examCompleteVm.tipContent = '很抱歉，您的成绩只有' + data.score + '分，无法通过考试。<br/>小伙伴，继续加油吧！';
                                            }
                                            examCompleteVm.nextCourse = nextCourse;
                                            examCompleteVm.isFinalExamAvailable = isFinalExamAvailable;
                                            examCompleteVm.enterNextStudyText = isFinalExamAvailable? "进入期末考试" : "学习下一节课程";
                                            examCompleteVm.courseId = courseId;
                                            examCompleteVm.open();
                                        }
                                    }
                                });
                            }
                        }
                    }]
                };
                vm.$examCompleteTip = {
                    "dialogId": examCompleteId,
                    "title": "",
                    "showClose": false,
                    "width": 580,
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                            footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                '<div class="ui-dialog-btns">' +
                                    '<button type="button" class="ui-dialog-btn" ms-if="!isFinalExam && !!nextCourse && !isOldStaff" ms-class-1="submit-btn: isPassed" ms-class-2="cancel-btn: !isPassed" ms-click="enterNextStudy">{{enterNextStudyText}}</button>' +
                                    '<span class="separation"></span>' +
                                    '<button type="button" class="submit-btn ui-dialog-btn" ms-click="returnCourseList">返回课程列表</button>' +
                                    '<span class="separation"></span>' +
                                    '<button type="button" class="submit-btn ui-dialog-btn" ms-click="reExam" ms-if="!(isFinalExam && isPassed)">重新考试</button>' +
                                '</div>' +
                            '</div>';
                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "tipContent": "",   //提示内容
                    "detailList": [],  //答题详情
                    "isPassed": false, //是否通过考试
                    "questionList": [],  //答题列表
                    "nextCourse": null,
                    "isFinalExamAvailable": false,
                    "enterNextStudyText": "学习下一节课程",
                    "courseId": 0,
                    "clickNumField": function () {
                        var dialogVm = avalon.getVm(examCompleteId),
                            meEl = $(this),
                            qListWEl = $('.question-list-wrapper', meEl),
                            listData,
                            itemM = this.$vmodel.$model;
                        //组织数据
                        if (meEl.hasClass('right-num')) {
                            listData = itemM.el.rightList;
                        } else if (meEl.hasClass('wrong-num')) {
                            listData = itemM.el.wrongList;
                        } else if (meEl.hasClass('left-num')) {
                            listData = itemM.el.leftList;
                        }
                        dialogVm.questionList = _.map(listData, function (id) {
                            var questionData = _.find(vm.originData, function (itemData) {
                                return itemData.id == id;
                            });
                            return {
                                "questionContent": questionData.content,
                                "questionTypeName": getQuestionTypeName(questionData.questionType)
                            };
                        });
                        //渲染出来
                        if (!qListWEl.length) {
                            qListWEl = $('<div class="question-list-wrapper"></div>');
                            qListWEl.html('<ul class="question-list"><li class="question-item" ms-repeat="questionList">' +
                                '{{$index + 1}}、' + '{{el.questionContent}}' + '({{el.questionTypeName}})' +
                            '</li></ul>');
                            qListWEl.appendTo(meEl);
                            avalon.scan(qListWEl[0], [dialogVm]);
                            //注册全局点击
                            util.regGlobalMdExcept({
                                "element": qListWEl,
                                "handler": function () {
                                    qListWEl.hide();
                                }
                            });
                        } else {
                            qListWEl.show();
                        }
                    },
                    "enterNextStudy": function () {
                        var dialogVm = avalon.getVm(examCompleteId);
                        if (dialogVm.isPassed) {
                            if (dialogVm.isFinalExamAvailable) {    //直接进入期末考试
                                util.jumpPage('#/study/coursestudy/plainexam/' + routeData.params["learningProgressId"] + '/' + dialogVm.courseId + '/0');
                            } else {
                                if (dialogVm.nextCourse) {
                                    util.jumpPage('#/study/coursestudy/process/' + routeData.params["learningProgressId"] + '/' + dialogVm.nextCourse.courseId + '/' + dialogVm.nextCourse.coursewareId);   //跳到下一节课程学习
                                }
                            }
                            dialogVm.close();
                        }
                    },
                    "returnCourseList": function () {
                        var dialogVm = avalon.getVm(examCompleteId);
                        util.jumpPage('#/study/coursestudy');   //返回课程列表
                        dialogVm.close();
                    },
                    "reExam": function () {
                        var formVm = avalon.getVm(formId),
                            dialogVm = avalon.getVm(examCompleteId);   //重新考试
                        /*var progressPanelEl = $('.progress-panel', pageEl),
                            questionStatePanelEl = $('.question-state-panel', progressPanelEl);
                        formVm.reset();
                        _.each(pageVm.questionTypeList, function (itemVm) {
                            _.each(itemVm.questionList, function (itemVm2) {
                                itemVm2.isAnswered = false;
                            });
                        });*/
                        dialogVm.close();
                        //改为重刷页面，进入一个新的考试
                        var coursewareId = routeData.params["id"];
                        avalon.router.navigate('/study/coursestudy/plainexam/' + routeData.params["learningProgressId"] + '/' + routeData.params["courseId"] + '/' + coursewareId);
                    },
                    "onClose": function () {
                        var dialogVm = avalon.getVm(examCompleteId);
                        dialogVm.tipContent = "";
                        dialogVm.detailList.clear();
                        dialogVm.isPassed = false;
                        dialogVm.questionList.clear();
                    }
                };
                //考试时间有调整将重新计时
                vm.$watch('duration', function (val) {
                    var totalSeconds = Math.floor(val * 60 * 60);
                    clearInterval(vm.tId);
                    vm.tId = setInterval(function () {
                        var result;
                        result = formatTime(totalSeconds);
                        vm.leftHour = result.hour;
                        vm.leftMinute = result.minute;
                        vm.leftSecond = result.second;
                        if (totalSeconds <= 0) {
                            clearInterval(vm.tId);
                            //自动提交
                            $('.f-submit', pageEl).click();
                        } else {
                            totalSeconds--;
                        }
                    }, 1000);
                });
            });
            avalon.scan(pageEl[0]);

            //更新数据
            setDefaultData();

            //test
            /*setTimeout(function () {
                pageVm.duration = 1;
            }, 5000);*/

            pageEl.on('change', '.option-item input', function () {
                var meEl = $(this),
                    optionListEl = meEl.closest('.option-list'),
                    quesitonId = meEl.data('questionId'),
                    isSomeChecked = false,  //false 表示未答题，true表示已答题
                    atTIndex = -1,
                    atQIndex = -1;
                $('input', optionListEl).each(function () {
                    if ($(this).prop('checked')) {
                        isSomeChecked = true;
                        return false;
                    }
                });
                _.some(pageVm.questionTypeList.$model, function (questionTypeData, i) {
                    if (_.some(questionTypeData.questionList, function (questionData, j) {
                        if (questionData.id == quesitonId) {
                            atQIndex = j;
                            return true;
                        }
                    })) {
                        atTIndex = i;
                        return true;
                    }
                });
                if (atQIndex > -1) {
                    pageVm.questionTypeList[atTIndex].questionList.set(atQIndex, {
                        "isAnswered": isSomeChecked
                    });
                }
            });

            /*===========私有函数放下面==============*/

            function getQuestionTypeName(questionType) {
                var questionTypeName = '';
                switch (questionType) {
                    case "0":
                        questionTypeName = "单选题";
                        break;
                    case "1":
                        questionTypeName = "多选题";
                        break;
                    case "2":
                        questionTypeName = "判断题";
                        break;
                }
                return questionTypeName;
            }

            function formatTime(seconds) {
                var hour = 0,
                    minute = 0,
                    second = 0;
                hour = Math.floor(seconds / 3600);
                minute = Math.floor((seconds - hour * 3600) / 60);
                second = seconds - minute * 60 - hour * 3600;
                return {
                    "hour": hour,
                    "minute": minute,
                    "second": second
                };
            }

            function formatExamData(examData) {
                var tempData,
                    result = [],
                    questionTypeNameMap = {
                        "0": "single",
                        "1": "multi",
                        "2": "bool"
                    },
                    questionTypeTextMap = {
                        "0": "单选题",
                        "1": "多选题",
                        "2": "判断题"
                    };
                tempData = _.groupBy(examData, function (itemData) {
                    return itemData.questionType;
                });
                _.each(tempData, function (questionList, key) {
                    var questionListFormatData = _.map(questionList, function (questionData) {
                        return {
                            "questionTitle": questionData.content,
                            "id": questionData.id,
                            "isAnswered": false,
                            "optionList": _.map(questionData.examOptionVOs, function (itemData) {
                                return {
                                    "optionContent": itemData.optionContent,
                                    "id": itemData.id
                                };
                            })
                        };
                    });
                    result.push({
                        "questionTypeTitle": questionTypeTextMap[key],
                        "questionType": questionTypeNameMap[key],
                        "questionList" : questionListFormatData
                    });
                });
                return result;
            }

            function setDefaultData() {
                util.c2s({
                    "url": pageVm.isFinalExam ? (erp.BASE_PATH + "train/exam/finalExamine.do") : (erp.BASE_PATH + "train/exam/examine.do"), //如果只有crouseId， 认为进入了期末考试
                    "data": {
                        "courseId": routeData.params["courseId"],    //课程id
                        "coursewareId": routeData.params["id"], //课件id
                        "learningProgressId": routeData.params["learningProgressId"]    //学习进度
                    },
                    "success": function (reponseData) {
                        var data;
                        if (reponseData.flag) {
                            data = reponseData.data;
                            //设置导航
                            if (!pageVm.isFinalExam) {
                                pageVm.navCrumbs.set(1, {
                                    "text": data.paperName,
                                    "href": "#/study/coursestudy/process/" + routeData.params["learningProgressId"] + '/' + routeData.params["courseId"] + "/" + data.coursewareId
                                });
                            } else {
                                pageVm.navCrumbs.removeAt(1);
                            }
                            //设置列表
                            pageVm.originData = data.traExamDtlVOs; //保存原始数据
                            pageVm.questionTypeList = formatExamData(data.traExamDtlVOs);
                            //时限
                            pageVm.duration = parseFloat(data.timeLimit, 10);
                            //本次考试计时开始
                            if (!pageVm.isFinalExam) {  //期末考试不需要调用开始计时
                                util.c2s({
                                    "url": erp.BASE_PATH + "train/exam/startExam.do",
                                    "type": "get",
                                    "data": {
                                        "examPaperId": data.id
                                    },
                                    "contentType" : "application/json"
                                });
                            }

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
                examCompleteId = pageName + '-exam-complete';
            $(avalon.getVm(examCompleteId).widgetElement).remove();
            $(avalon.getVm(formId).widgetElement).remove();
        }
    });

    return pageMod;
});
