/**
 * 课件管理
 */
define(['avalon', 'util', '../../../widget/pagination/pagination', '../../../widget/dialog/dialog', '../../../widget/form/form', '../../../widget/form/select'], function (avalon, util) {
     var win = this,
        erp = win.erp,
        pageMod = {};
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName) {
            var paginationId = pageName + '-pagination',
                courseListTipId = pageName + '-course-list-tip';
            var pageVm = avalon.define(pageName, function (vm) {
                vm.permission = {
                    "magCourseware": util.hasPermission('mag_course_ware'),     //添加课件/修改课件/删除课件
                    "paperRule": util.hasPermission('paper_rule'),   //考卷规则
                    "magQuestion": util.hasPermission('mag_question')  //添加试题/修改试题/删除试题
                };
                vm.courseName = '';
                vm.coursewareName = '';
                vm.gridTotalSize = 0;
                vm.gridData = [];
                vm.gridAllChecked = false;
                vm.gridSelected = [];
                vm.gridCheckAll = function () {
                    if (this.checked) {
                        vm.gridSelected = _.map(vm.gridData.$model, function (itemData) {
                            return itemData.id;
                        });
                        //vm.gridAllChecked = true;
                    } else {
                        vm.gridSelected.clear();
                        //vm.gridAllChecked = false;
                    }
                };
                /**
                 * 表格左下脚一个全选的快捷方式
                 */
                vm.scCheckAll = function () {
                    vm.gridSelected = _.map(vm.gridData.$model, function (itemData) {
                        return itemData.id;
                    });
                };
                vm.$paginationOpts = {
                    "paginationId": paginationId,
                    "total": 0,
                    "onJump": function (evt, vm) {
                        updateGrid();
                    }
                };
                vm.search = function(){
                    updateGrid(true);
                };
                vm.jumpAddQuestionPage = function () {
                    var linkEl = $('<a href="#/study/questionmanage/add"></a>');
                    linkEl.appendTo('body');
                    linkEl[0].click();
                    linkEl.remove();
               };
               vm.jumpQueryQuestionPage = function(){
                   var linkEl = $('<a href="#/study/questionmanage"></a>');
                   linkEl.appendTo('body');
                   linkEl[0].click();
                   linkEl.remove();
               };
               vm.jumpAddPage = function(){
                   var linkEl = $('<a href="#/study/coursewaremanage/add"></a>');
                   linkEl.appendTo('body');
                   linkEl[0].click();
                   linkEl.remove();
               };
                vm.removeGridItem = function () {
                    var meEl = $(this),
                        questionId = meEl.data('id')+"";
                    //选中当前行，取消其他行选中
                    vm.gridSelected = [questionId];
                    //删除对应课件
                    vm._removeCourseware();
                };
                vm.batchRemoveGridItem = function () {
                    vm._removeCourseware();
                };
                vm._removeCourseware = function () {
                    var ids,
                        requestData,
                        url;
                    if (vm.gridSelected.size() === 0) {
                        util.alert({
                            "content": "请先选中课件"
                        });
                    } else {
                        ids = vm.gridSelected.$model;
                        url = erp.BASE_PATH + 'courseware/deleteCourseware.do';
                        requestData = {
                                "idString": ids
                            };
                        util.confirm({
                            "content": "确定要删除选中的课件吗？",
                            "onSubmit": function () {
                                util.c2s({
                                    "url": url,
                                    "type": "post",
                                    "data": JSON.stringify(requestData),
                                    "contentType" : 'application/json',
                                    "success": function (responseData) {
                                        if (responseData.flag == 1) {   //删除成功，重刷grid
                                            updateGrid(true);
                                        }
                                    }
                                });
                            }
                        });
                    }
                };

            });
            pageVm.gridSelected.$watch("length", function(n) {
                pageVm.gridAllChecked = n === pageVm.gridData.size();
            });

            avalon.scan(pageEl[0],[pageVm]);
            //更新grid数据
            updateGrid();
            //鼠标悬浮显示课程列表
            pageEl.on('mouseenter', '.course-count', function () {
                var meEl = $(this),
                    index = avalon(this).data('index'),
                    tipEl = $('#' + courseListTipId),
                    refOffset = meEl.offset(),
                    coursewareData = pageVm.gridData[index],
                    courseCount = coursewareData.coursecount,
                    courseList = meEl.data('courseList');

                var renderAndShowList = function (listData) {
                    var htmlStr = '';
                    _.each(listData, function (courseName) {
                        htmlStr += '<li class="course-item">' + courseName + '</li>';
                    });
                    tipEl.html(htmlStr).css({
                        "top": refOffset.top,
                        "left": refOffset.left + meEl.width()
                    }).show();
                };

                if (!tipEl.length) {
                    tipEl = $('<ul id="' + courseListTipId + '"></ul>');
                    tipEl.appendTo('body');
                }
                if (courseCount > 0) {
                    if (!courseList) {
                        util.c2s({
                            "url": erp.BASE_PATH + "courseware/getCourseByCoursewareId.do",
                            "type": "post",
                            //"contentType" : 'application/json',
                            "data": {
                                "coursewareId": coursewareData.id
                            },
                            "success": function (responseData) {
                                var data,
                                    courseList;
                                if (responseData.flag == 1) {
                                    data = responseData.data;
                                    courseList = _.map(data, function (itemData) {
                                        return itemData.course_name;
                                    });
                                    renderAndShowList(courseList);
                                    meEl.data('courseList', courseList);
                                }
                            }
                        }, {
                            "mask": false,
                            "withData": false,
                            "ajaxType": "abort" //终端上一次执行
                        });
                    } else {
                        renderAndShowList(courseList);
                    }
                }
            }).on('mouseleave', '.course-count', function () {
                $('#' + courseListTipId).hide();
            });

            function updateGrid(jumpFirst) {
                var paginationVm = avalon.getVm(paginationId),
                    requestData = {
                        "courseName": pageVm.courseName,
                        "coursewareName": pageVm.coursewareName

                    };
                if (jumpFirst) {
                    requestData.pageSize = paginationVm.perPages;
                    requestData.pageNumber = 1;
                } else {
                    requestData.pageSize = paginationVm.perPages;
                    requestData.pageNumber = paginationVm.currentPage;
                }
                util.c2s({
                    "url": erp.BASE_PATH + "courseware/getCoursewareInfo.do",
                   // "type": "get",
                    "data": requestData,
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                              data = responseData.data;
                              paginationVm.total = data.totalSize;
                              if (jumpFirst) {
                                  paginationVm.currentPage = 1;   //重设成第一页
                              }
                              pageVm.gridData = data.rows;
                              pageVm.gridTotalSize = data.totalSize;
                              pageVm.gridAllChecked = false;  //取消全选
                              pageVm.gridSelected.clear();

                        }
                    }
                });
            }
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var paginationId = pageName + '-pagination',
                courseListTipId = pageName + '-course-list-tip';
            $(avalon.getVm(paginationId).widgetElement).remove();
            $('#' + courseListTipId).remove();
        }
    });

    return pageMod;
});
