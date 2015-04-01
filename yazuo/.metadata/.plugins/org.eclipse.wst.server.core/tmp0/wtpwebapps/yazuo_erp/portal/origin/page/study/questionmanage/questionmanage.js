/**
 * 试题管理
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
        "$init": function (pageEl, pageName,routeData) {
            var paginationId = pageName + '-pagination',
                scheduleId = pageName + '-schedule';
            var coursewareName = decodeURIComponent(routeData.params["coursewareName"]);
            var pageVm = avalon.define(pageName, function (vm) {
                vm.permission = {
                    "magQuestion": util.hasPermission('mag_question')  //添加试题/修改试题/删除试题
                };
                //题型列表
                vm.$scheduleOpts = {
                    "selectId": scheduleId,
                    "options": [{
                        "text": "所有题型",
                        "value": ""
                    }, {
                        "text": "单选",
                        "value": "0"
                    }, {
                        "text": "多选",
                        "value": "1"
                    }, {
                        "text": "判断",
                        "value": "2"
                    },{
                        "text": "PPT",
                        "value": "3"
                    },{
                        "text": "产品实操",
                        "value": "4"
                    },{
                        "text": "问答题",
                        "value": "5"
                    }],
                    "selectedIndex": 0
                };
                vm.questionTitle = '';
                vm.coursewareName = (coursewareName === 'undefined'?'' : coursewareName); //课件名
                vm.$positionOpts = {
                    "options": [],
                    "selectedIndex": 0
                };
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
                vm.jumpAddPage = function () {
                    var linkEl = $('<a href="#/study/questionmanage/add"></a>');
                    linkEl.appendTo('body');
                    linkEl[0].click();
                    linkEl.remove();
                };
                vm.removeGridItem = function () {
                    var meEl = $(this),
                        questionId = meEl.data('id')+"";
                    //选中当前行，取消其他行选中
                    vm.gridSelected = [questionId];
                    //删除对应试题
                    vm._removeQuestion();
                };
                vm.batchRemoveGridItem = function () {
                    vm._removeQuestion();
                };
                vm._removeQuestion = function () {
                    var ids,
                        requestData,
                        url;
                    if (vm.gridSelected.size() === 0) {
                        util.alert({
                            "content": "请先选中试题"
                        });
                    } else {
                        ids = vm.gridSelected.$model;
                        url = erp.BASE_PATH + 'question/deleteQuestion.do';
                        requestData = {
                                "idString": ids
                            };
                        util.confirm({
                            "content": "确定要删除选中的试题吗？",
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

            function updateGrid(jumpFirst) {
                var paginationVm = avalon.getVm(paginationId),
                    requestData = {
                        "question": pageVm.questionTitle,
                        "coursewareName": pageVm.coursewareName,
                        "questionType": avalon.getVm(scheduleId).selectedValue
                    };
                if (jumpFirst) {
                    requestData.pageSize = paginationVm.perPages;
                    requestData.pageNumber = 1;
                } else {
                    requestData.pageSize = paginationVm.perPages;
                    requestData.pageNumber = paginationVm.currentPage;
                }
                util.c2s({
                    "url": erp.BASE_PATH + "question/getQuestionsInfo.do",
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
                scheduleId = pageName + '-schedule';
            $(avalon.getVm(scheduleId).widgetElement).remove();
            $(avalon.getVm(paginationId).widgetElement).remove();
        }
    });

    return pageMod;
});
