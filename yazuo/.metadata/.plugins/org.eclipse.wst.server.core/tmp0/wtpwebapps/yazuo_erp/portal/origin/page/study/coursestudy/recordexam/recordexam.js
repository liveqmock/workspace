/**
 * 考试-ppt录音
 */
define(['avalon', 'util', 'json', '../../../../module/pptplayer/pptplayer', '../../../../widget/form/form'], function (avalon, util, JSON) {
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
            var pptId = pageName + '-ppt';
            var softpos = document.getElementById('softPos');
            var loginUserData = erp.getAppData('user');
            var pageVm = avalon.define(pageName, function (vm) {
                //vm.$skipArray = ["tId"];
                vm.navCrumbs = [{
                    "text": "课程学习",
                    "href": "#/study/coursestudy"
                }, {
                    "text": '',  //课程名
                    "href": "#"
                }, {
                    "text": '课后习题'
                }];
                vm.$pptplayerOpts = {
                    "pptplayerId": pptId
                };
            });
            avalon.scan(pageEl[0]);

            //更新数据
            setDefaultData();

            /*===========私有函数放下面==============*/

            function setDefaultData() {
                util.c2s({
                    "url": erp.BASE_PATH + "train/exam/pptExam.do",
                    "data": {
                        "courseId": routeData.params["courseId"],    //课程id
                        "coursewareId": routeData.params["id"], //课件id
                        "learningProgressId": routeData.params["learningProgressId"]    //学习进度
                    },
                    "success": function (reponseData) {
                        var data,
                            pptVm,
                            ppt,
                            examPaper,
                            pptDtlVOs,
                            recordOpts = [];
                        if (reponseData.flag) {
                            pptVm = avalon.getVm(pptId);
                            data = reponseData.data;
                            ppt = data.ppt;
                            pptDtlVOs = ppt.pptDtlVOs;
                            examPaper = data.exampaper;
                            //设置导航
                            pageVm.navCrumbs.set(1, {
                                "text": ppt.pptName,
                                "href": "#/study/coursestudy/process/" + routeData.params["learningProgressId"] + "/" + routeData.params["courseId"] + "/" + ppt.coursewareId
                            });
                            //设置ppt列表
                            pptVm.pptList = _.map(pptDtlVOs, function (itemData) {
                                return {
                                    "imagePath": erp.BASE_PATH + itemData.uri
                                };
                            });
                            pptVm.currentPath = pptVm.pptList[0].imagePath; //设置第一张ppt图片为当前图片
                            //自动打开录音组件
                            if (softpos && softpos.RecordOpen) {
                                recordOpts[0] = examPaper.id;  //examPaperId
                                recordOpts[1] = ppt.id;  //pptId
                                recordOpts[2] = examPaper.timeLimit / 1; //timeLimit
                                recordOpts[3] = loginUserData.sessionId; //sessionId，用于识别登录用户
                                softpos.RecordOpen(recordOpts.join(','));
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
            var pptId = pageName + '-ppt';
            var softpos = document.getElementById('softPos');
            $(avalon.getVm(pptId).widgetElement).remove();
            //关闭录音组件
            if (softpos && softpos.RecordClose) {
                softpos.RecordClose();
            }
        }
    });

    return pageMod;
});
