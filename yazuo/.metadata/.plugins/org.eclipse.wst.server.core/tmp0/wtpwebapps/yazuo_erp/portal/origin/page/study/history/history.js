/**
 * 历史回顾
 */
define(['avalon', 'util', 'json', 'moment'], function (avalon, util, JSON, moment) {
    var win = this,
        erp = win.erp,
        pageMod = {},
        lazyLoad,
        loginUserData = erp.getAppData('user');
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName, routeData) {
            var pageVm = avalon.define(pageName, function (vm) {
                vm.$skipArray = ["originData"];
                vm.lineList = [/*{
                    "dateIndex": "今天",
                    "isToday": true,
                    "detailList": [{
                        "timeIndex": "19:22",
                        "content": "这是一段描述"
                    }, {
                        "timeIndex": "19:22",
                        "content": "这是一段描述"
                    }]
                }, {
                    "dateIndex": "昨天",
                    "isYestoday": true,
                    "detailList": [{
                        "timeIndex": "19:22",
                        "content": "这是一段描述"
                    }]
                }*/];
                vm.originData = []; //保存原始数据
                vm.totalSize = 0;
                vm.pageSize = 13;
                vm.pageNumber = 1;
                vm.isComplete = false;
            });
            avalon.scan(pageEl[0]);
            //第一次更新数据
            updateList();
            //懒加载
            lazyLoad = function () {
                if ($('body').height() - $(win).scrollTop() - $(win).height() <= 10) {    //监测滚动条滚动到底部10px左右
                    if ((pageVm.pageNumber-1) * pageVm.pageSize < pageVm.totalSize) {
                        updateList();
                    } else {
                        pageVm.isComplete = true;
                        $(win).unbind('scroll', lazyLoad);
                    }
                }
            };
            $(win).scroll(lazyLoad);

            /*===========私有函数放下面==============*/
            function updateList() {
                util.c2s({
                    "url": erp.BASE_PATH + "studentManagement/queryTraStudentRecordByStudentId.do",
                    "contentType" : 'application/json',
                    "data": JSON.stringify({
                        "studentId": routeData["params"].id ? routeData["params"].id / 1 : loginUserData.id,
                        //"studentId": 38,
                        "pageSize": pageVm.pageSize,
                        "pageNumber": pageVm.pageNumber
                    }),
                    "success": function (reponseData) {
                        //var tempList;
                        if (reponseData.flag) {
                            pageVm.originData = pageVm.originData.concat(reponseData.data.rows);
                            pageVm.lineList = formatListData(pageVm.originData);
                            //pageVm.lineList = pageVm.lineList.concat(formatListData(reponseData.data.rows));

                            pageVm.totalSize = reponseData.data.totalSize;
                            pageVm.pageNumber = pageVm.pageNumber + 1;

                            if (pageVm.totalSize <= pageVm.pageSize) {
                                pageVm.isComplete = true;
                            }
                            if (reponseData.data.totalSize === 0) {
                                util.alert({
                                    "iconType": "info",
                                    "content": "您还未产生历史回顾"
                                });
                            }
                        }
                    }
                });
            }

            function formatListData(listData) {
                var result = [],
                    tempRows;
                tempRows = _.groupBy(listData, function (itemData) {
                    return moment(itemData.begin_time).format('YYYY-MM-DD');
                });
                _.each(tempRows, function (detailData, key) {
                    var today = moment().format('YYYY-MM-DD'),
                        yestoday = moment().subtract('days', 1).format('YYYY-MM-DD'),
                        isToday = false,
                        isYestoday = false,
                        dateIndex = key.split('-').slice(1).join('.');
                    if (key == today) {
                        dateIndex = "今天";
                        isToday = true;
                    }
                    if (key == yestoday) {
                        dateIndex = "昨天";
                        isYestoday = true;
                    }
                    result.push({
                        "dateIndex": dateIndex,
                        "isToday": isToday,
                        "isYestoday": isYestoday,
                        "detailList": _.map(detailData, function (itemData) {
                            var actionHtmlStr = '', //item action
                                reExamLink = '';
                            if (itemData.operating_type == "0") {   //学习
                                actionHtmlStr = '<a href="#/study/coursestudy/process/' + itemData.learning_progress_id + '/' + itemData.course_id + '/' + itemData.courseware_id + '">复习课程</a>';
                            } else if (itemData.operating_type == "1"){ //参加
                                if (itemData.exam_paper_type == "1") {  //ppt
                                    actionHtmlStr = '<a href="#/study/history/audition/' + itemData.paper_id + '">试听录音</a>';
                                    reExamLink = '#/study/coursestudy/recordexam/' + itemData.learning_progress_id + '/' + itemData.course_id + '/' + itemData.courseware_id;
                                } else if (itemData.exam_paper_type == "0"){    //笔试题
                                    reExamLink = '#/study/coursestudy/plainexam/' + itemData.learning_progress_id + '/' + itemData.course_id + '/' + itemData.courseware_id;
                                } else if (itemData.exam_paper_type == "5") {   //期末考试
                                    reExamLink = '#/study/coursestudy/plainexam/' + itemData.learning_progress_id + '/' + itemData.course_id + '/0';
                                }
                                if (itemData.exam_paper_type == "1") {   //ppt讲解和期末考试
                                    if (itemData.paper_status == "3" && itemData.paper_comment) {
                                        actionHtmlStr += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#/study/history/audition/' + itemData.paper_id + '/comment">查看评语</a>';
                                    }
                                }
                                //只有未通过期末考试才可以重新考试，其他考试随意
                                if ((itemData.paper_status == "4" && itemData.exam_paper_type == "5") || itemData.exam_paper_type != "5") {
                                    actionHtmlStr += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="' + reExamLink + '">重新考试</a>';
                                }
                            }
                            if (!itemData.learning_progress_id) {   //如果学习进度id是0, 不显示功能按钮
                                actionHtmlStr = "";
                            }
                            return {
                                "timeIndex": moment(itemData.begin_time).format('HH:mm'),
                                "content": itemData.description,
                                "action": actionHtmlStr
                            };
                        })
                    });
                });
                return result;
            }
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            $(win).unbind('scroll', lazyLoad);
            lazyLoad = null;
        }
    });

    return pageMod;
});
