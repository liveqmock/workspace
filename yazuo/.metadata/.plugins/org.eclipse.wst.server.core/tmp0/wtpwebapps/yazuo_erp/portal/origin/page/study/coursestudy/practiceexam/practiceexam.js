/**
 * 考试-实操题
 */
define(['avalon', 'util', 'json'], function (avalon, util, JSON) {
     var win = this,
        erp = win.erp,
        page = erp.page,
        pageMod = {};
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName, routeData) {
            var pageVm = avalon.define(pageName, function (vm) {
                vm.$skipArray = ['firstExamPath'];
                vm.navCrumbs = [{
                    "text": "课程学习",
                    "href": "#/study/coursestudy"
                }, {
                    "text": '',  //课程名
                    "href": "#"
                }, {
                    "text": '课后习题'
                }];
                vm.duration = "";
                vm.leftTime = 0;
                vm.originLeftTime = 0;
                vm.questionList = [];
                vm.firstExamPath = "";
                vm.learningProgressId = routeData.params["learningProgressId"];
                vm.courseId = routeData.params["courseId"];
                vm.coursewareId = routeData.params["id"];
                vm.examPaperId = 0;
                vm.jumpFirstExam = function () {
                    util.jumpPage('#/study/coursestudy/practiceexam' + vm.firstExamPath);
                };
            });
            avalon.scan(pageEl[0]);

            //更新数据
            setDefaultData();
            //监听页面切换事件，当切换到当前页面时，重新更新数据
            page.pageEvent.on('switched', function (pageEl2, pageName2) {
                if (pageName2 === pageName) {
                    resetData();
                    setDefaultData();
                }
            });

            /*===========私有函数放下面==============*/

            function setDefaultData() {
                util.c2s({
                    "url": erp.BASE_PATH + "train/exam/examine.do",
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
                            pageVm.navCrumbs.set(1, {
                                "text": data.paperName,
                                "href": "#/study/coursestudy/process/" + routeData.params["learningProgressId"] + '/' + routeData.params["courseId"] + "/" + data.coursewareId
                            });
                            //设置列表
                            pageVm.questionList = _.map(data.traExamDtlVOs, function (itemData, i) {
                                if (i === 0) {
                                    pageVm.firstExamPath = itemData.url;
                                }
                                return {
                                    "id": itemData.id,
                                    "content": itemData.content,
                                    "url": itemData.url,
                                    "isPassed": false   //标识是否通过考题操作
                                };
                            });
                            //时限
                            pageVm.duration = (parseFloat(data.timeLimit, 10) * 60).toFixed(0);
                            pageVm.leftTime = Math.ceil(parseFloat(data.timeLimit, 10) * 60 * 60);  //以s为单位向上取整
                            pageVm.originLeftTime = pageVm.leftTime;    //保留原始考试时间
                            pageVm.examPaperId = data.id;
                            //pageVm.leftTime = 10;
                        }
                    }
                });
            }

            function resetData () {
                pageVm.questionList.clear();
                pageVm.duration = "";
                pageVm.leftTime = 0;
            }
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
        }
    });

    return pageMod;
});
