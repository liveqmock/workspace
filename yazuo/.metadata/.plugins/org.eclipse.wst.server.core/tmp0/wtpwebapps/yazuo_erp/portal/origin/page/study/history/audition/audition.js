/**
 * 历史回顾-ppt录音试听
 */
define(['avalon', 'util', 'json', '../../../../module/pptplayer/pptplayer', '../../../../widget/media/player', '../../../../widget/form/form'], function (avalon, util, JSON) {
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
            var pptId = pageName + '-ppt',
                audioId = pageName + '-audio',
                doScoreDialogId = pageName + '-do-score-dialog',
                playOverDialogId = pageName + '-play-over-dialog',
                doScoreFormId = pageName + 'do-score-form',
                doScoreFormId_ = pageName + 'do-score-form_';
            var pageVm = avalon.define(pageName, function (vm) {
                //vm.$skipArray = ["tId"];
                vm.navCrumbs = [{
                    "text": "历史回顾",
                    "href": "#/study/history"
                }, {
                    "text": ''
                }];
                vm.studentName = "";
                vm.studentId = 0;
                vm.pptName = "";
                vm.auditionSummary = "";
                vm.teacherComment = "";
                vm.$pptOpts = {
                    "pptplayerId": pptId
                };
                vm.$audioOpts = {
                    "playerId": audioId,
                    "jwplayerOptions": {
                        "width": 800,
                        "height": 30
                    }
                };
                vm.$playOverDialogOpts = {
                    "dialogId": playOverDialogId,
                    "title": "提示",
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                            footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                            '<div class="ui-dialog-btns">' +
                            '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">返回历史回顾</button>' +
                            '<span class="separation"></span>' +
                            '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="replay">重新播放</button>' +
                            '</div>' +
                            '</div>';
                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "onClose": function () {
                        //清除验证信息
                        var formVm = avalon.getVm(doScoreFormId_);
                        formVm && formVm.reset();
                    },
                    "submit": function (evt) {
                        var dialogVm = avalon.getVm(playOverDialogId);
                        dialogVm.close();
                        util.jumpPage('#/study/history');
                    },
                    "replay": function () {
                        var dialogVm = avalon.getVm(playOverDialogId);
                        dialogVm.close();
                        //重新播放
                        playerCore.play(true);
                    }
                };
            });
            avalon.scan(pageEl[0]);

            //更新数据
            setDefaultData();

            //录音播放完毕后自动弹出窗口
            var playerCore = avalon.getVm(audioId).playerCore;
            playerCore.onComplete(function () { //播放完成
                var dialogVm = avalon.getVm(playOverDialogId);
                dialogVm.open();
            });

            /*===========私有函数放下面==============*/

            function setDefaultData() {
                util.c2s({
                    "url": erp.BASE_PATH + "studentManagement/queryPptExamInfo.do",
                    "data": JSON.stringify({
                        "paperId": routeData.params["id"] / 1 //课件id
                    }),
                    "contentType": "application/json",
                    "success": function (reponseData) {
                        var data,
                            pptVm,
                            audioInfo,
                            studentInfo,
                            ppt,
                            traExamPaper;
                        if (reponseData.flag) {
                            pptVm = avalon.getVm(pptId);
                            data = reponseData.data;
                            audioInfo = data.audioInfo;
                            studentInfo = data.studentInfo;
                            ppt = data.ppt;
                            traExamPaper = data.traExamPaper;
                            //驼峰式转换
                            /*data = _.map(reponseData.data, function (itemData) {
                                return util.keyToCamelize(itemData);
                            });*/
                            //设置导航
                            pageVm.navCrumbs.set(1, {
                                "text": ppt.ppt_name
                            });
                            //设置ppt列表
                            pptVm.pptList = _.map(data.pptDtlList, function (itemData) {
                                return {
                                    "imagePath": erp.BASE_PATH + data.pptPhotoPath + '/' + ppt.ppt_path + '/' + itemData.ppt_dtl_name
                                };
                            });
                            pptVm.currentPath = pptVm.pptList[0].imagePath; //设置第一张ppt图片为当前图片
                            //设置录音
                            playerCore.load([{
                                "file":  erp.BASE_PATH + data.audioPath + '/' + audioInfo.attachment_path + "/" +audioInfo.attachment_name
                                //"file":  erp.ORIGIN_PATH + 'data/1.mp3'
                            }]);
                            //设置学生信息
                            pageVm.studentName = studentInfo.userName;
                            pageVm.studentId = studentInfo.id;
                            pageVm.pptName = ppt.ppt_name;
                            //pageVm.auditionSummary = ppt.ppt_name + '&nbsp;&nbsp;时长:' + audioInfo.hours + '小时';
                            pageVm.auditionSummary = ppt.ppt_name;  //暂时不显示录音时长
                            //设置老师评语
                            if (traExamPaper.examPaperType == "1" && traExamPaper.comment) {
                                pageVm.teacherComment = traExamPaper.comment;
                            }
                            //判断是否需要定位到评语
                            if (routeData.params["action"] && routeData.params["action"] == "comment") {
                                $(win).scrollTop(10000);
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
            var pptId = pageName + '-ppt',
                audioId = pageName + '-audio',
                doScoreDialogId = pageName + '-do-score-dialog',
                playOverDialogId = pageName + '-play-over-dialog',
                doScoreFormId = pageName + 'do-score-form',
                doScoreFormId_ = pageName + 'do-score-form_';
            var doScoreFormVm = avalon.getVm(doScoreFormId),
                doScoreFormVm_ = avalon.getVm(doScoreFormId_),
                doScoreDialogVm = avalon.getVm(doScoreDialogId),
                playOverDialogVm = avalon.getVm(playOverDialogId);
            $(avalon.getVm(pptId).widgetElement).remove();
            $(avalon.getVm(audioId).widgetElement).remove();
            doScoreFormVm && $(doScoreFormVm.widgetElement).remove();
            doScoreFormVm_ && $(doScoreFormVm_.widgetElement).remove();
            doScoreDialogVm && $(doScoreDialogVm.widgetElement).remove();
            playOverDialogVm && $(playOverDialogVm.widgetElement).remove();
        }
    });

    return pageMod;
});
