/**
 * 学员学习界面
 */
define(['avalon', 'util', 'json', 'moment', '../../../../widget/media/player', '../../../../widget/dialog/dialog'], function (avalon, util, JSON, moment) {
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
            var playerId = pageName + '-player',
                dialogId = pageName + '-dialog',
                loginUserData = erp.getAppData("user");
            var pageVm = avalon.define(pageName, function (vm) {
                vm.navCrumbs = [{
                    "text": "课程学习",
                    "href": "#/study/coursestudy"
                }, {
                    "text": ''  //课件名
                }];
                vm.examEnable = true;   //如果是老员工，并且考卷规则被设置成不支持老员工开始，则不显示开始考试按钮
                vm.isExist = true;  //是否存在此课件，可能被删除
                vm.paperType = "0"; //试卷类型
                vm.coursewareName = '';
                vm.coursewareSummary = '';
                vm.studyHistoryList = [];
                vm.examHistoryList = [];
                vm.studied = false; //默认设置成未学过
                vm.$coursewareOpts = {
                    "playerId": playerId,
                    "jwplayerOptions": {
                        "width": 798,
                        "height": 558,
                        //"file": erp.ORIGIN_PATH + "data/1.mp4",
                        "image": erp.ASSET_PATH + "image/media-bg.png"
                        //"primary": "flash"
                    }
                };
                vm.$completedOpts = {
                    "dialogId": dialogId,
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                            footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                '<div class="ui-dialog-btns">' +
                                    '<button type="button" class="submit-btn ui-dialog-btn" ms-if="examEnable" ms-click="enterExam">开始考试</button>' +
                                    '<span class="separation"></span>' +
                                    '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="replay">重新播放</button>' +
                                '</div>' +
                            '</div>';
                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "enterExam": function (evt) {   //进入考试
                        if (vm.paperType == "0") {
                            util.jumpPage('#/study/coursestudy/plainexam/' + routeData.params["learningProgressId"] + '/' + routeData.params["courseId"] + '/' + routeData.params["id"]);
                        } else if (vm.paperType == "1") {
                            util.jumpPage('#/study/coursestudy/recordexam/' + routeData.params["learningProgressId"] + '/' + routeData.params["courseId"] + '/' + routeData.params["id"]);
                        } else if (vm.paperType == "2") { //实操题
                            util.jumpPage('#/study/coursestudy/practiceexam/' + routeData.params["learningProgressId"] + '/' + routeData.params["courseId"] + '/' + routeData.params["id"]);
                        }
                        avalon.getVm(dialogId).close();
                    },
                    "replay": function (evt) {  //重新播放
                        playerCore.play(true);
                        avalon.getVm(dialogId).close();
                    }
                };
                vm.enterExam = function () {
                    if (vm.paperType == "0") {
                        util.jumpPage('#/study/coursestudy/plainexam/' + routeData.params["learningProgressId"] + '/' + routeData.params["courseId"] + '/' + routeData.params["id"]);
                    } else if (vm.paperType == "1") {
                        util.jumpPage('#/study/coursestudy/recordexam/' + routeData.params["learningProgressId"] + '/' + routeData.params["courseId"] + '/' + routeData.params["id"]);
                    } else if (vm.paperType == "2") { //实操题
                        util.jumpPage('#/study/coursestudy/practiceexam/' + routeData.params["learningProgressId"] + '/' + routeData.params["courseId"] + '/' + routeData.params["id"]);
                    }

                };
                vm.enterStudyPage = function () {
                    util.jumpPage('#/study/coursestudy');
                };
                vm.enterHistoryPage = function () {
                    util.jumpPage('#/study/history');
                };
            });
            avalon.scan(pageEl[0]);

            //设置player播放控制
            var playerCore = avalon.getVm(playerId).playerCore,
                stopListening = false,
                readyBeginPlay = true;
            /*playerCore.onSeek(function (evt) {
                var position = evt.position,
                    offset = evt.offset;
                if (!stopListening && !pageVm.studied) {    //未学习的情况下要控制进度
                //if (!stopListening) {
                    setTimeout(function () {
                        if (offset > position) {    //控制不能向前快进
                            stopListening = true;
                            playerCore.seek(position);
                        }
                    }, 10);
                } else {
                    stopListening = false;
                }
            });*/
            //开始播放事件，计时开始
            playerCore.onPlay(function (evt) {
                if (evt.oldstate == "BUFFERING" && readyBeginPlay) {  //从头开始
                    util.c2s({
                        "url": erp.BASE_PATH + "train/student/startStudy.do",
                        "data": {
                            "isOldStaff": loginUserData.isFormal,
                            "courseId": routeData.params["courseId"],
                            "coursewareId": routeData.params["id"],
                            "learningProgressId": routeData.params["learningProgressId"] == 0 ? "" : routeData.params["learningProgressId"]    //学习进度
                        },
                        "success": function (reponseData) {
                            if (reponseData.flag) {

                            }
                        }
                    });
                    readyBeginPlay = false;
                }
            });
            //完成播放事件，计时结束
            playerCore.onComplete(function () { //播放完成表示学习完毕
                util.c2s({
                    "url": erp.BASE_PATH + "train/student/completeStudy.do",
                    "data": {
                        "isOldStaff": loginUserData.isFormal,
                        "courseId": routeData.params["courseId"],
                        "coursewareId": routeData.params["id"],
                        "learningProgressId": routeData.params["learningProgressId"] == 0 ? "" : routeData.params["learningProgressId"]    //学习进度
                    },
                    "success": function (reponseData) {
                        if (reponseData.flag) {
                            var dialogVm = avalon.getVm(dialogId);
                            //学习完毕，可以进入考试
                            pageVm.studied = true;
                            dialogVm.open();
                        }
                    }
                });
                readyBeginPlay = true;
            });
            //设置默认数据
            setDefaultData();

            /*===========私有函数放下面==============*/

            /**
             * 设置默认数据
             * @param {Type}
             */
            function setDefaultData() {
                util.c2s({
                    "url": erp.BASE_PATH + "train/student/study.do",
                    "data": {
                        "isOldStaff": loginUserData.isFormal,
                        "courseId": routeData.params["courseId"],
                        "coursewareId": routeData.params["id"],
                        "learningProgressId": routeData.params["learningProgressId"] == 0 ? "" : routeData.params["learningProgressId"]    //学习进度
                    },
                    "success": function (reponseData) {
                        var data,
                            attachmentVO,
                            studyStudentRecordVOs,
                            examStudentRecordVOs,
                            smvpAttachData,
                            videoRenditions;
                        if (reponseData.flag) {
                            data = reponseData.data;
                            attachmentVO = data.attachmentVO;
                            studyStudentRecordVOs = data.studyStudentRecords;
                            examStudentRecordVOs = data.examStudentRecords;
                            smvpAttachData = attachmentVO.smvpAttachData;
                            videoRenditions = smvpAttachData.entries[0].renditions;
                            //判断是否可以进行考试
                            pageVm.examEnable = !(loginUserData.isFormal == "1" && data.isTest == "0"); //只有是老员工并且考卷规则未开始老员工考试才不能考，其他都可考
                            //导航
                            pageVm.navCrumbs.set(1, {
                                "text": data.coursewareName
                            });
                            pageVm.coursewareName = data.coursewareName;
                            //设置title summary信息
                            pageVm.coursewareSummary = '主讲人：' + data.speaker + '&nbsp;&nbsp;&nbsp;&nbsp;时长：' + data.courseHours;
                            //设置视频信息
                            playerCore.load([{
                                "file": videoRenditions[0].urls.smf.url.slice(0, -20) + '.mp4'   //参考 http://support.smvp.cn/apis?name=entries.json, 接口有调整
                                //"file": videoRenditions[1] ? videoRenditions[1].url : videoRenditions[0].url //参考 http://support.smvp.cn/apis?name=entries.json
                                //"file":  attachmentVO.uri  //e.g. : rtmp://192.168.236.31:1935/Learning-Paths
                                //"file":  erp.ORIGIN_PATH + "data/1.flv"
                            }]);
                            //设置学习历史
                            pageVm.studyHistoryList = _.map(studyStudentRecordVOs, function (itemData) {
                                return {
                                    "historyTime": moment(itemData.beginTime).format('MM月DD日 HH:mm')
                                };
                            });
                            //设置考试历史
                            pageVm.examHistoryList = _.map(examStudentRecordVOs, function (itemData) {
                                return {
                                    "historyTime": moment(itemData.beginTime).format('MM月DD日 HH:mm'),
                                    "examScore": itemData.score,
                                    "isPassed": true
                                };
                            });
                            //设置存在标志
                            pageVm.isExist = true;  //false会给出不存在的提示

                            //是否学习过
                            pageVm.studied = data.studied;
                            //试卷类型
                            pageVm.paperType = data.paperType;
                        }
                    }
                });
            }
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var playerId = pageName + '-player',
                dialogId = pageName + '-dialog',
                playerVm = avalon.getVm(playerId);
            $(avalon.getVm(dialogId).widgetElement).remove();
            playerVm && $(playerVm.widgetElement).remove();
        }
    });

    return pageMod;
});
