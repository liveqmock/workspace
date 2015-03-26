/**
 * 管理员角色的课程管理
 */
define(['avalon', 'util', 'sortable', '../../../../module/cwselector/cwselector', '../../../../widget/pagination/pagination', '../../../../widget/dialog/dialog', '../../../../widget/form/form'], function (avalon, util, sortable) {
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
            var cwselectorId = pageName + '-cwselector';
            var courseName = decodeURIComponent(routeData.params["courseName"]);

            var pageVm = avalon.define(pageName, function (vm) {
                vm.requiredCourseware = [];
                vm.optionalCourseware = [];
                vm.courseType = '';
                vm.navCrumbs = [{
                    "text":"课程管理",
                    "href":"#/study/coursemanage"
                }, {
                    "text":courseName,
                    "href":"#/study/coursemanage"
                },{
                    "text":"查看课程详情"
                }];
                vm.$cwselectorOpts = {
                    "cwselectorId": cwselectorId,
                    "multi": true,  //支持多选
                    "onListReady": function (coursewareListVm) {
                        var listVm = vm[vm.courseType + 'Courseware'];
                        _.each(listVm.$model, function (itemData) {
                            var atIndex = -1;
                            if (_.some(coursewareListVm.$model, function (itemData2, i) {
                                if (itemData2.id == itemData.coursewareId) {
                                    atIndex = i;
                                    return true;
                                }
                            })) {
                                coursewareListVm.set(atIndex, {
                                    "isSelected": true
                                });
                            }
                        });
                        // 取点击相反的表表格
                        var t = vm.courseType =='optional' ? 'required' : 'optional',
                         coursewareVm = vm[t+'Courseware'];
                        _.each(coursewareVm.$model, function (itemData) {
                            var atIndex = -1;
                            if (_.some(coursewareListVm.$model, function (itemData2, i) {
                                if (itemData2.id == itemData.coursewareId) {
                                    atIndex = i;
                                    return true;
                                }
                            })) {
                                coursewareListVm.removeAt(atIndex); // 将对应表格存在的数据删除
                            }
                        });
                    },
                    "onSubmit": function (result) {
                        if (result.length) {
                            vm[vm.courseType + 'Courseware'].clear();
                            vm[vm.courseType + 'Courseware'] = _.map(result, function (itemData) {
                                return {
                                    "coursewareId": itemData["id"],
                                    "coursewareName": itemData["coursewareName"],
                                    "timeLimit": itemData["timeLimit"]
                                };
                            });
                        }
                    }
                };
                // 添加课件
                vm.openAdd = function () {
                    var cwselectorVm = avalon.getVm(cwselectorId);
                    vm.courseType = avalon(this).data('courseType');
                    cwselectorVm.open();
                };

                // 单个删除
                vm.removeGridItem = function () {
                    var type = avalon(this).data('courseType'),
                        itemM = this.$vmodel.$model;
                    vm[type + 'Courseware'].removeAt(itemM.$index);
                };
                // 全部删除
                vm.deleteAll = function () {
                     var type = avalon(this).data('courseType');
                    util.confirm({
                        "content": "确定将课件都删除?",
                        "onSubmit": function () {
                            //var type = avalon(this).data('courseType');
                            vm[type + 'Courseware'].removeAll();
                        }
                    });
                };
                //保存数据
                vm.saveCourseware = function () {
                    var coursewareData = [],
                        requestData = {
                            "courseId": routeData.params["id"] / 1
                        };
                    // 取必修课件
                    _.each(vm.requiredCourseware.$model, function(itemM){
                        coursewareData.push({
                            "coursewareId": itemM.coursewareId,
                            "reRequired": "1"
                        });
                    });
                    // 取选修课件
                    _.each(vm.optionalCourseware.$model, function(itemM){
                        coursewareData.push({
                            "coursewareId": itemM.coursewareId,
                            "reRequired": "0"
                        });
                    });
                    if (!coursewareData.length) { // 判断是否有添加课件
                        util.alert({
                            "iconType": "error",
                            "content": "请选择至少一个课件"
                        });
                        return;
                    }
                    // 选择的课件
                    requestData["coursewareInfos"] = coursewareData;
                    util.c2s({
                        "url": erp.BASE_PATH + "course/saveCourseware.do",
                        "type": "post",
                        "contentType":"application/json",
                        "data": JSON.stringify(requestData),
                        "success": function (responseData) {
                            if (responseData.flag) {
                                util.alert({
                                    "iconType": "success",
                                    "content": responseData.message,
                                    "onSubmit": function () {
                                        util.jumpPage('#/study/coursemanage');
                                    }
                                });
                            }
                        }
                    });
                };
            });

            avalon.scan(pageEl[0],[pageVm]);
            //更新grid数据
            updateGrid();
            //表格可拖拽排序
            $('.required-grid, .not-required-grid', pageEl).sortable({
                containerSelector: 'table',
                itemPath: '> tbody',
                itemSelector: 'tr',
                placeholder: '<tr class="drag-sort-placeholder"><td colspan="4">&nbsp;</td></tr>',
                onDrop: function  (itemEl, container, _super) {
                    var gridEl = itemEl.closest('table'),
                        courseType = gridEl.hasClass('required-grid')? "required": "optional",
                        sortedData = [];
                    //重排list
                    $('tbody tr', gridEl).each(function () {
                        var coursewareId = avalon(this).data('coursewareId');
                        _.some(pageVm[courseType + 'Courseware'].$model, function (itemData) {
                            if (itemData.coursewareId == coursewareId) {
                                sortedData.push(itemData);
                            }
                        });
                    });
                    pageVm[courseType + 'Courseware'] = sortedData;
                    _super(itemEl);
                }
            });
            // 填充表格数据
            function updateGrid() {
                util.c2s({
                    "url": erp.BASE_PATH + "course/getCourseDetail.do",
                    "data": {
                        "pageSize":10000,   //极大分页值获取全部列表数据
                        "pageNumber":1,
                        "courseId":routeData.params["id"]
                    },
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag == 1) {
                            data = responseData.data;
                            pageVm.requiredCourseware = _.map(data.requiredCourseware, function (itemData) {
                                return {
                                    "coursewareId": itemData["courseware_id"],
                                    "coursewareName": itemData["courseware_name"],
                                    "timeLimit": itemData["time_limit"]
                                };
                            });
                            pageVm.optionalCourseware = _.map(data.noRequiredCourseware, function (itemData) {
                                return {
                                    "coursewareId": itemData["courseware_id"],
                                    "coursewareName": itemData["courseware_name"],
                                    "timeLimit": itemData["time_limit"]
                                };
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
            var cwselectorId = pageName + '-cwselector';
            $(avalon.getVm(cwselectorId).widgetElement).remove();
        }
    });

    return pageMod;
});
