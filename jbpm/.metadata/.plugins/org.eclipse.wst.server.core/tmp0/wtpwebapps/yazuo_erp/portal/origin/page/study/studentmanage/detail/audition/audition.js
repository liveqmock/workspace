/**
 * 学员管理-ppt录音试听
 */
define(['avalon', 'util', 'json', '../../../../../module/pptplayer/pptplayer', '../../../../../widget/media/player', '../../../../../widget/form/form'], function (avalon, util, JSON) {
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
                vm.permission = {
                    "markTheRecord": util.hasPermission('mark_the_record')    //  录音评分
                    //"humanMark": util.hasPermission('human_mark')  // 真人互动评分
                };
                //vm.$skipArray = ["tId"];
                vm.navCrumbs = [{
                    "text": "学员管理",
                    "href": "#/study/studentmanage"
                }, {
                    "text": '',  //查看详情
                    "href": ''
                }, {
                    "text": ''
                }];
                vm.studentName = "";
                vm.studentId = 0;
                vm.pptName = "";
                vm.auditionSummary ="";
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
                            '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">' + (vm.permission.markTheRecord ? '保存并返回查看详情' : '返回详情列表') + '</button>' +
                            '<span class="separation"></span>' +
                            '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="replay">重新试听</button>' +
                            '</div>' +
                            '</div>';
                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "onClose": function () {
                        //清除验证信息
                        var formVm = avalon.getVm(doScoreFormId_);
                        formVm.reset();
                    },
                    "submit": function (evt) {
                        var requestData,
                            dialogVm = avalon.getVm(playOverDialogId),
                            formVm;
                        if (!vm.permission.markTheRecord) {    //没有录音评分直接返回查看详情列表
                            util.jumpPage("#/study/studentmanage/detail/" + vm.studentId);
                        } else {
                            formVm = avalon.getVm(doScoreFormId_);
                            if (formVm.validate()) {
                                requestData = formVm.getFormData();
                                //附加当前的pageId
                                _.extend(requestData, {
                                    "paperId": routeData.params["id"] / 1
                                });
                                //提交
                                util.c2s({
                                    "url": erp.BASE_PATH + 'studentManagement/updatePptPaper.do',
                                    "type": "post",
                                    "contentType": "application/json",
                                    "data": JSON.stringify(requestData),
                                    "success": function (responseData) {
                                        if (responseData.flag) {
                                            dialogVm.close();
                                            util.alert({
                                                "iconType": "success",
                                                "content": responseData.message,
                                                "onSubmit": function () {
                                                    util.jumpPage("#/study/studentmanage/detail/" + vm.studentId);
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }

                    },
                    "replay": function () {
                        var dialogVm = avalon.getVm(playOverDialogId);
                        dialogVm.close();
                        //重新播放
                        playerCore.play(true);
                    }
                };
                vm.$doScoreDialogOpts = {
                    "dialogId": doScoreDialogId,
                    "title": "录音评分",
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
                        //清除验证信息
                        var formVm = avalon.getVm(doScoreFormId);
                        formVm.reset();
                    },
                    "submit": function (evt) {
                        var requestData,
                            dialogVm = avalon.getVm(doScoreDialogId),
                            formVm = avalon.getVm(doScoreFormId);
                        if (formVm.validate()) {
                            requestData = formVm.getFormData();
                            //附加当前的pageId
                            _.extend(requestData, {
                                "paperId": routeData.params["id"] / 1
                            });
                            //提交
                            util.c2s({
                                "url": erp.BASE_PATH + 'studentManagement/updatePptPaper.do',
                                "type": "post",
                                "contentType": "application/json",
                                "data": JSON.stringify(requestData),
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        util.alert({
                                            "iconType": "success",
                                            "content": responseData.message
                                        });
                                        dialogVm.close();
                                    }
                                }
                            });
                        }
                    }
                };
                vm.$doScoreFormOpts = {
                    "formId": doScoreFormId,
                    "field": [{
                        "selector": ".score",
                        "name": "mark",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "评分不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".comment",
                        "name": "comment",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "评语不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }]
                };
                vm.$doScoreFormOpts_ = {
                    "formId": doScoreFormId_,
                    "field": [{
                        "selector": ".score",
                        "name": "mark",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "评分不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".comment",
                        "name": "comment",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "评语不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }]
                };
                vm.doScore = function () {
                    var dialogVm = avalon.getVm(doScoreDialogId);
                    dialogVm.open();
                }
            });
            avalon.scan(pageEl[0]);

            //更新数据
            setDefaultData();

            //录音播放完毕后自动弹出窗口
            var playerCore = avalon.getVm(audioId).playerCore;
            playerCore.onComplete(function () { //播放完成
                var dialogVm = avalon.getVm(playOverDialogId);
                if ($(avalon.getVm(doScoreDialogId).widgetElement).is(":hidden")) {
                    dialogVm.open();
                }
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
                            ppt;
                        if (reponseData.flag) {
                            pptVm = avalon.getVm(pptId);
                            data = reponseData.data;
                            audioInfo = data.audioInfo;
                            studentInfo = data.studentInfo,
                            ppt = data.ppt;
                            //驼峰式转换
                            /*data = _.map(reponseData.data, function (itemData) {
                                return util.keyToCamelize(itemData);
                            });*/
                            //设置导航
                            pageVm.navCrumbs.set(1, {
                                "text": "查看详情-" + studentInfo.userName,
                                "href": "#/study/studentmanage/detail/" + studentInfo.id //studentId
                            });
                            pageVm.navCrumbs.set(2, {
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
                                "file":  erp.BASE_PATH + data.audioPath +'/'+ audioInfo.attachment_path + "/" +audioInfo.attachment_name
                                //"file":  erp.ORIGIN_PATH + 'data/1.mp3'
                            }]);
                            //设置学生信息
                            pageVm.studentName = studentInfo.userName;
                            pageVm.studentId = studentInfo.id;
                            pageVm.pptName = ppt.ppt_name;
                            //pageVm.auditionSummary = ppt.ppt_name + '&nbsp;&nbsp;时长:' + audioInfo.hours + '小时';
                            pageVm.auditionSummary = ppt.ppt_name;  //暂时不显示录音时长
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
