
/**
 * 实操题考试帮助组件
 */
define(["avalon", "util", "text!./practicehelper.html", "text!./practicehelper.css", "common", "../../widget/dialog/dialog"], function(avalon, util, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    var styleData = styleEl.data('module') || {};
    if (!styleData["practicehelper"]) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'practicehelper/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'practicehelper/');
        }
        styleData["practicehelper"] = true;
        styleEl.data('module', styleData);
    }
    var module = erp.module.practicehelper = function(element, data, vmodels) {
        var opts = data.practicehelperOptions,
            elEl = $(element),
            countdownTid,
            endTipId = data.practicehelperId + '-end-tip';
        var vmodel = avalon.define(data.practicehelperId, function (vm) {
            avalon.mix(vm, opts);
            vm.widgetElement = element;
            vm.countdownText = '';
            vm.examContent = '';
            vm.$endTipOpts = {
                "dialogId": endTipId,
                "title": "提示",
                "isPassed": true,
                "getTemplate": function (tmpl) {
                    var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                        footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                        '<div class="ui-dialog-btns">' +
                        '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">重新考试</button>' +
                        '<span class="separation"></span>' +
                        '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="reStudy">重新学习</button>' +
                        '<span class="separation"></span>' +
                        '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="backToCourseList">返回课程列表</button>' +
                        '</div>' +
                        '</div>';
                    return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                },
                "onOpen": function () {
                    var dialogVm = avalon.getVm(endTipId),
                        practiceExamVm = getPracticeExamVm(true),
                        requestData;
                    //先停用倒计时
                    vm.stopCountdown();
                    //通知后台是否已通过本次考试
                    requestData = _.map(practiceExamVm.questionList.$model, function (itemData) {
                        return {
                            "id": itemData.id,
                            "isCorrect": itemData.isPassed ? 1 : 0
                        };
                    });
                    util.c2s({
                        "url": erp.BASE_PATH + "train/exam/saveExam.do",
                        "type": "post",
                        "data": JSON.stringify(requestData),
                        "contentType" : "application/json",
                        "success": function (responseData) {
                            var data;
                            if (responseData.flag) {}
                        }
                    });
                },
                "submit": function (evt) {
                    var practiceExamVm = getPracticeExamVm(true),
                        dialogVm = avalon.getVm(endTipId);
                    dialogVm.close();
                    util.jumpPage('#/study/coursestudy/practiceexam/' + practiceExamVm.learningProgressId + '/' + practiceExamVm.courseId + '/' + practiceExamVm.coursewareId);
                },
                "reStudy": function () {
                    var practiceExamVm = getPracticeExamVm(true),
                        dialogVm = avalon.getVm(endTipId);
                    dialogVm.close();
                    util.jumpPage('#/study/coursestudy/process/' + practiceExamVm.learningProgressId + '/' + practiceExamVm.courseId + '/' + practiceExamVm.coursewareId);
                },
                "backToCourseList": function () {
                    var dialogVm = avalon.getVm(endTipId);
                    dialogVm.close();
                    util.jumpPage('#/study/coursestudy');
                }
            };
            vm.clickVisibleHandler = function () {
                var meEl = $(this);
                if (meEl.hasClass("state-shown")) {
                    elEl.css("overflow", "hidden").stop(true, true).animate({
                        "width": 0
                    }, "fast", function () {
                        meEl.css("right", 0);
                        meEl.addClass("state-hidden").removeClass("state-shown").html("&#xe624;");
                    });
                } else {
                    elEl.css("overflow", "visible").stop(true, true).animate({
                        "width": "248px"
                    }, "fast", function () {
                        meEl.css("right", 248 - ($('.app').width() - $(win).width() - $(win).scrollLeft()) + "px");
                        meEl.addClass("state-shown").removeClass("state-hidden").html("&#xe625;");
                    });
                }
            };
            vm.startCountdown = function () {
                countdownExer();
            };
            vm.stopCountdown = function () {
                clearInterval(countdownTid);
            };
            vm.setValidateResult = function (isPassed) {
                var practiceExamVm = getPracticeExamVm(true);
                var atIndex = -1;
                if (_.some(practiceExamVm.questionList.$model, function (itemData, i) {
                    if (itemData.url === vmodel.examPath) {
                        atIndex = i;
                        return true;
                    }
                })) {
                    practiceExamVm.questionList.set(atIndex, {
                        "isPassed": isPassed
                    });
                }
            };
            vm.next = function (examVm) {
                var i,
                    isPassed = true,
                    p;
                var practiceExamVm = getPracticeExamVm(true),
                    endTipVm = avalon.getVm(endTipId);
                var atIndex = -1,
                    questionList = practiceExamVm.questionList;
                for (i = 0; i < 1000; i++) {
                    p = "result" + i;
                    if (examVm.hasOwnProperty(p)) {
                        if (!examVm[p]) {
                            isPassed = false;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                //设置验证信息
                vm.setValidateResult(isPassed);
                //跳转到下一题,终止或成功通过
                if (_.some(questionList.$model, function (itemData, i) {
                    if (itemData.url === vmodel.examPath) {
                        atIndex = i;
                        return true;
                    }
                })) {
                    if (isPassed) {
                        if (atIndex !== (questionList.size() - 1)) {
                            util.alert({
                                "content": "试题已通过，剩余" + (questionList.size() - atIndex - 1) + "道考题，加油哦～",
                                "iconType": "success",
                                "submitText": "下一题",
                                "onSubmit": function () {
                                    util.jumpPage('#/study/coursestudy/practiceexam' + questionList[atIndex + 1].url);
                                }
                            });
                        } else {
                            endTipVm.setTitle('恭喜您');
                            endTipVm.isPassed = true;
                            endTipVm.open();
                        }
                    } else {
                        endTipVm.setTitle('很抱歉');
                        endTipVm.isPassed = false;
                        endTipVm.open();
                    }
                }
            };
            vm.$init = function() {
                elEl.addClass('module-practicehelper');
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm.$remove = function() {
                var endTipId = data.practicehelperId + '-end-tip';
                $(win).off('scroll resize', adjustVhPosition);
                clearInterval(countdownTid);
                $(avalon.getVm(endTipId).widgetElement).remove();
                elEl.removeClass('module-practicehelper').off().empty();
            };
            vm.$skipArray = ["widgetElement"];
        });
        //更新考试信息
        updateExamInfo();

        var visibleTid;
        $(win).on('scroll resize', adjustVhPosition);

        /*==================私有函数========================*/
        function getPracticeExamVm (silent) {
            var practiceExamVm = avalon.getVm('page-study-coursestudy-practiceexam');
            if (!practiceExamVm && !silent) {
                util.alert({
                    "content": "请不要直接访问考题页面，需要从考试入口进入。",
                    "iconType": "error",
                    "onSubmit": function () {
                        util.jumpPage('#/study/coursestudy');
                    }
                });
            }
            return practiceExamVm;
        }
        function adjustVhPosition () {
            var handlerEl = $('.practice-info-panel-visible-h', elEl);
            clearTimeout(visibleTid);
            visibleTid = setTimeout(function () {
                if (handlerEl.hasClass('state-shown')) {
                    handlerEl.css({
                        //"right": 248 + $(this).scrollLeft() + "px"
                        "right": 248 - ($('.app').width() - $(this).width() - $(this).scrollLeft()) + "px"
                    });
                }
            }, 30);
        }
        /**
         * 更新考题信息
         */
        function updateExamInfo () {
            var practiceExamVm = getPracticeExamVm(),
                leftTime;
            if (practiceExamVm) {
                leftTime = practiceExamVm.leftTime;
                _.some(practiceExamVm.questionList.$model, function (itemData) {
                    if (itemData.url === vmodel.examPath) {
                        vmodel.examContent = itemData.content;
                        return true;
                    }
                });
            }
        }
        /**
         * 倒计时执行体
         */
        function countdownExer () {
            var practiceExamVm = getPracticeExamVm(true),
                endTipVm = avalon.getVm(endTipId),
                leftTime;
            function substract() {
                var hour = 0,
                    minute = 0,
                    second = 0;
                leftTime--;
                hour = Math.floor(leftTime / 3600);
                minute = Math.floor((leftTime - hour * 3600) / 60);
                second = leftTime - minute * 60 - hour * 3600;
                vmodel.countdownText = _.str.lpad(hour, 2, '0') + ':' + _.str.lpad(minute, 2, '0') + ':' + _.str.lpad(second, 2, '0');
                practiceExamVm.leftTime = leftTime;
            }
            clearInterval(countdownTid);
            if (practiceExamVm) {
                leftTime = practiceExamVm.leftTime;
                if (practiceExamVm.originLeftTime === leftTime && leftTime !== 0) { //表示计时开始
                    //本次考试计时开始
                    util.c2s({
                        "url": erp.BASE_PATH + "train/exam/startExam.do",
                        "type": "get",
                        "data": {
                            "examPaperId": practiceExamVm.examPaperId
                        },
                        "contentType" : "application/json"
                    });
                }
                if (leftTime > 0) {
                    substract();
                    countdownTid = setInterval(function () {
                        var t;
                        if (leftTime <= 0) {
                            clearInterval(countdownTid);
                            endTipVm.setTitle('很抱歉');
                            endTipVm.isPassed = false;
                            endTipVm.open();
                        } else {
                            substract();
                        }
                    }, 1000);   //向下减1s
                } else {
                    endTipVm.setTitle('很抱歉');
                    endTipVm.isPassed = false;
                    endTipVm.open();
                }
            }
        }
        return vmodel;
    };
    module.version = 1.0;
    module.defaults = {
        "examPath": ""
    };
    return avalon;
});
