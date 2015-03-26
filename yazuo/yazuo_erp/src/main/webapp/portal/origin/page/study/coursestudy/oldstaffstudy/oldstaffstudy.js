/**
 * 老员工课件学习
 */
define(['avalon', 'util', 'json', 'moment'], function (avalon, util, JSON, moment) {
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
            var pageVm = avalon.define(pageName, function (vm) {
                vm.studyList = [];
                vm.historyList = [];
                vm.clickStartStudy = function () {
                    var itemData = this.$vmodel.$model.el,
                        coursewareId = itemData.id,
                        learningProgressId = itemData.learningProgressId;
                    util.jumpPage("#/study/coursestudy/process/" + learningProgressId + "/0/" + coursewareId);
                };
                vm.clickStartExam = function () {
                    var itemData = this.$vmodel.$model.el;
                    if (itemData.paperType == "0") {
                        util.jumpPage('#/study/coursestudy/plainexam/' + itemData.learningProgressId + '/0/' + itemData.id);
                    } else if (itemData.paperType == "1") {
                        util.jumpPage('#/study/coursestudy/recordexam/' + itemData.learningProgressId + '/0/' + itemData.id);
                    } else if (itemData.paperType == "2") { //实操题
                        util.jumpPage('#/study/coursestudy/practiceexam/' + itemData.learningProgressId + '/0/' + itemData.id);
                    }
                };
            });
            avalon.scan(pageEl[0]);

            updateList();

            /*===========私有函数放下面==============*/
            function updateList() {
                util.c2s({
                    "url": erp.BASE_PATH + "train/student/getAllCoursewares.do",
                    "data": {
                        "isOldStaff": 1 //表示老员工
                    },
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            pageVm.studyList = _.map(data.learningCoursewareList, function (itemData, i) {
                                itemData.indexNum = _.str.lpad(i + 1, 2, "0");
                                itemData.coursewareEndTimeText = moment(itemData.coursewareEndTime).format("YYYY.MM.DD HH:mm");
                                return itemData;
                            });
                            pageVm.historyList = _.map(data.historyCoursewareList, function (itemData, i) {
                                itemData.indexNum = _.str.lpad(i + 1, 2, "0");
                                itemData.coursewareEndTimeText = moment(itemData.coursewareEndTime).format("YYYY.MM.DD HH:mm");
                                return itemData;
                            });
                        }
                    }
                });
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
