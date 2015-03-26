/**
 * 课程学习
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
            var loginUserData = erp.getAppData("user");
            var pageVm = avalon.define(pageName, function (vm) {
                vm.requiredList = [];
                vm.optionalList = [];
                vm.speaker = '';
                vm.coursename = '';
                vm.endTime = '';
            });
            avalon.scan(pageEl[0]);

            if (loginUserData.isFormal == "1") {   //如果是老员工，主动跳转到老员工课件学习页
                util.jumpPage("#/study/coursestudy/oldstaffstudy");
            } else {
                updateList();
            }
            /*===========私有函数放下面==============*/
            function updateList() {
                util.c2s({
                    "url": erp.BASE_PATH + "train/student/getAllCoursewares.do",
                    "success": function (reponseData) {
                        var data,
                            required,
                            optional,
                            course,
                            finalExamAvail;
                        if (reponseData.flag) {
                            data = reponseData.data;
                            required = data.required;
                            optional = data.optional;
                            course = data.course;
                            finalExamAvail = data.finalExamAvail;

                            pageVm.requiredList = formatCourseData(required, course);
                            pageVm.requiredList.push({
                                "indexNum": required.length + 1,
                                "isStudied": finalExamAvail ? true : false,
                                "content": finalExamAvail ? '<a href="#/study/coursestudy/plainexam/' + course.learningProgressId + '/' + course.id + '/0">期末考试</a>' : '期末考试'
                            });
                            pageVm.optionalList = formatCourseData(optional, course);
                            pageVm.speaker = data.teachername;
                            if (course) {
                                pageVm.coursename = course.courseName;
                                pageVm.endTime = course.endTime ? moment(course.endTime).format('YYYY-MM-DD HH:mm') : "";
                            }
                            if (!required.length && !optional.length) {
                                util.alert({
                                    "iconType": "info",
                                    "content": reponseData.message
                                });
                            }
                        }
                    }
                });
            }

            function formatCourseData(courseList, course) {
                return _.map(courseList, function (itemData, i) {
                    var content = '《' + itemData.coursewareName + '》',
                        isStudied = false;
                    content += '&nbsp;&nbsp;&nbsp;&nbsp;';
                    content += '课时：' + itemData.courseHours;
                    content += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    content += '主讲人：' + itemData.speaker;
                    if (itemData.studied) { //已学习的，显示复习课程
                        content += '&nbsp;&nbsp;<a href="#/study/coursestudy/process/' + course.learningProgressId + '/' + course.id + '/' + itemData.id + '">复习课程</a>';
                        isStudied = true;
                    }
                    if (itemData.isNew) {
                        content += '&nbsp;&nbsp;<a href="#/study/coursestudy/process/' + course.learningProgressId + '/' + course.id + '/' + itemData.id + '">开始学习</a>';
                    }
                    if (itemData.examined) {
                        if(itemData.paperType == "0"){
                            content += '&nbsp;-&nbsp;<a href="#/study/coursestudy/plainexam/' + course.learningProgressId + '/' + course.id + '/' + itemData.id + '">重新考试</a>';
                        } else if (itemData.paperType == "1") {
                            content += '&nbsp;-&nbsp;<a href="#/study/coursestudy/recordexam/' + course.learningProgressId + '/' + course.id + '/' + itemData.id + '">重新考试</a>';
                        } else if (itemData.paperType == "2") { //实操题
                            content += '&nbsp;-&nbsp;<a href="#/study/coursestudy/practiceexam/' + course.learningProgressId + '/' + course.id + '/' + itemData.id + '">重新考试</a>';
                        }
                    }
                    if (itemData.studied && !itemData.examined) {
                        if(itemData.paperType == "0"){
                            content += '&nbsp;-&nbsp;<a href="#/study/coursestudy/plainexam/' + course.learningProgressId + '/' + course.id + '/' + itemData.id + '">马上考试</a>';
                        } else if (itemData.paperType == "1") {
                            content += '&nbsp;-&nbsp;<a href="#/study/coursestudy/recordexam/' + course.learningProgressId + '/' + course.id + '/' + itemData.id + '">马上考试</a>';
                        } else if (itemData.paperType == "2") {
                            content += '&nbsp;-&nbsp;<a href="#/study/coursestudy/practiceexam/' + course.learningProgressId + '/' + course.id + '/' + itemData.id + '">马上考试</a>';
                        }
                    }
                    return {
                        "indexNum": i + 1,
                        "content": content,
                        "isNew": itemData.isNew,
                        "isStudied": isStudied
                    };
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
