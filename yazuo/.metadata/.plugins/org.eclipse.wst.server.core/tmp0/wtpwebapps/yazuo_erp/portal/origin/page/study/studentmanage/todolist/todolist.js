/**
 * 待办事项
 */
define(['avalon', 'util', 'moment', '../../../../widget/pagination/pagination', '../../../../widget/dialog/dialog', '../../../../widget/form/form'], function (avalon, util, moment) {
    var win = this,
        erp = win.erp,
        pageMod = {},
        lazyLoad;
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName, routeData) {
            var doScoreDialogId = pageName + '-do-score-dialog',
                doScoreFormId = pageName + '-do-score-form';
            var pageVm = avalon.define(pageName, function (vm) {
                vm.permission = {
                    "tryToLRecord": util.hasPermission('try_to_l_record'),    // 试听录音
                    "markTheRecord": util.hasPermission('mark_the_record'),    //  录音评分
                    "humanMark": util.hasPermission('human_mark')  // 真人互动评分
                };
                vm.todoList = [];
                vm.currentPaperId = 0;
                vm.currentExamPaperType = 0;
                vm.navCrumbs = [{
                    "text": "学员管理",
                    "href": "#/study/studentmanage"
                }, {
                    "text": "待办事项"
                }];
                vm.$doScoreDialogOpts = {
                    "dialogId": doScoreDialogId,
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
                    "onOpen": function () {
                        var dialogVm = avalon.getVm(doScoreDialogId);
                        if (vm.currentExamPaperType == 5) {
                            dialogVm.title = "真人互动评分";
                        } else if (vm.currentExamPaperType == 1) {
                            dialogVm.title = "进行评分";
                        }
                    },
                    "onClose": function () {
                        //清除验证信息
                        var formVm = avalon.getVm(doScoreFormId);
                        formVm.reset();
                    },
                    "submit": function (evt) {
                        var requestData,
                            dialogVm = avalon.getVm(doScoreDialogId),
                            formVm = avalon.getVm(doScoreFormId),
                            url;
                        if (formVm.validate()) {
                            if (vm.currentExamPaperType == 5) { //真人互动
                                url = 'studentManagement/updateMark.do';
                            } else if (vm.currentExamPaperType == 1) {
                                url = 'studentManagement/updatePptPaper.do';
                            }
                            requestData = formVm.getFormData();
                            //附加当前的paperId
                            _.extend(requestData, {
                                "paperId": vm.currentPaperId / 1
                            });
                            //提交
                            util.c2s({
                                "url": erp.BASE_PATH + url,
                                "type": "post",
                                "contentType": "application/json",
                                "data": JSON.stringify(requestData),
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        util.alert({
                                            "iconType": "success",
                                            "content": responseData.message,
                                            "onSubmit": function () {
                                                _.some(pageVm.todoList.$model, function (itemData, i) {
                                                    if (itemData.paperId == vm.currentPaperId) {    //直接删掉
                                                        pageVm.todoList.removeAt(i);
                                                        return true;
                                                    }
                                                });
                                            }
                                        });
                                        dialogVm.close();
                                    } else {
                                        util.alert({
                                            "iconType": "error",
                                            "content": responseData.message
                                        });
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
                                if (!/^\d{1,6}$/.test(val)) {
                                    return "请输入有效的分数";
                                }
                                if (val.slice(0, 1) === '0') {
                                    return "请输入有效的分数";
                                }
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
                vm.openDoScore = function (evt) {
                    var dialogVm = avalon.getVm(doScoreDialogId);
                    var itemM = this.$vmodel.$model,
                        examPaperType = itemM.el.examPaperType;
                    vm.currentPaperId = avalon(this).data('paperId');
                    vm.currentExamPaperType = examPaperType;
                    //打开dialog
                    dialogVm.open();
                    evt.preventDefault();
                };
                vm.totalSize = 0;
                vm.pageSize = 13;
                vm.pageNumber = 1;
                vm.isComplete = false;
            });

            avalon.scan(pageEl[0]);
            // 加载待办事项
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

            function updateList() {
                var requestData = {
                    "pageSize": pageVm.pageSize,
                    "pageNumber": pageVm.pageNumber
                }, url;
                if (routeData.route === "/study/seniorstudy/todolist/:id") {    //老员工领导查看待办
                    url = "oldStaffManagement/queryToDoListByLeaderId.do"
                    requestData.baseUserId = routeData.params["id"] / 1;
                } else if (routeData.route === "/study/studentmanage/todolist/:id") {   //老师查看待办列表
                    url = "studentManagement/queryToDoListByTeacherId.do";
                    requestData.teacherId = routeData.params["id"] / 1;
                }
                util.c2s({
                    "url": erp.BASE_PATH + url,
                    "contentType": 'application/json',
                    "data": JSON.stringify(requestData),
                    "success": function (responseData) {
                        var data,
                            rows;
                        if (responseData.flag) {
                            data = responseData.data;
                            rows = data.rows;
                            pageVm.todoList = pageVm.todoList.concat(_.map(rows, function (rowData) {
                                return {
                                    "beginTime": moment(rowData.begin_time / 1).format('YYYY-MM-DD HH:mm:ss'),
                                    "studentName": rowData.student_name,
                                    "description": rowData.description,
                                    "examPaperType": rowData.exam_paper_type,
                                    "paperId": rowData.paper_id
                                };
                            }));
                            pageVm.totalSize = data.totalSize;
                            pageVm.pageNumber = pageVm.pageNumber + 1;

                            if (pageVm.totalSize <= pageVm.pageSize) {
                                pageVm.isComplete = true;
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
            var doScoreDialogId = pageName + '-do-score-dialog',
                doScoreFormId = pageName + '-do-score-form';
            $(avalon.getVm(doScoreFormId).widgetElement).remove();
            $(avalon.getVm(doScoreDialogId).widgetElement).remove();

            $(win).unbind('scroll', lazyLoad);
            lazyLoad = null;
        }
    });

    return pageMod;
});
