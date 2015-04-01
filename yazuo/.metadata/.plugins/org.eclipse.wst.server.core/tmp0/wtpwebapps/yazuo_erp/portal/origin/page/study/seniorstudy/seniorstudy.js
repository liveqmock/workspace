/**
 * 老员工学习
 */
define(['avalon', 'util', 'json', '../../../widget/dialog/dialog', '../../../widget/form/form', '../../../widget/grid/grid', '../../../widget/tree/tree'], function (avalon, util, JSON) {

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

            var createStudyDialogId = pageName + '-create-study-dialog',
                treeId = pageName + '-tree',
                gridId = pageName + '-grid';
            var loginUserData = erp.getAppData("user");

            var pageVm = avalon.define(pageName, function (vm) {
                vm.initiateStudiesPermission = util.hasPermission("initiate_studies");
                vm.keyword = "";
                vm.$treeOpts = {
                    "treeId": treeId,
                    "ztreeOptions": {
                        "setting": {
                            "check": {
                                "enable": true,
                                "chkboxType": {
                                    "Y": "s",
                                    "N": "s"
                                }
                            },
                            "callback": {
                                "onClick": function (evt, treeId, node) {    //点击节点触发
                                }
                            },
                            "data": {
                                "simpleData": {
                                    "enable": true
                                }
                            }
                        }
                    },
                    "treeData": []
                };
                vm.$gridOpts = {
                    "gridId": gridId,
                    "recordUnit": "个课件",
                    "disableCheckAll": true,   //开启全选模式
                    "disableCheckbox": true,
                    "columns": [
                        {
                            "dataIndex": "id",
                            "text": "ID"
                        },
                        {
                            "dataIndex": "courseware_name",
                            "text": "课件名称"
                        },
                        {
                            "dataIndex": "hours",
                            "text": "时长（分）"
                        },
                        {
                            "dataIndex": "speaker",
                            "text": "主讲人"
                        },
                        {
                            "dataIndex": "num",
                            "text": "学习人数"
                        },
                        {
                            "dataIndex": "operation",
                            "text": "操作",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<a ms-href="#/study/seniorstudy/manage/{{$outer.el.id}}/{{$outer.el.courseware_name}}">学员管理</a>&nbsp;&nbsp;&nbsp;&nbsp;' +
                                    '<a href="javascript:;" ms-click="openStudy" ms-data-id="$outer.el.id" ms-if="initiateStudiesPermission">发起学习</a></td>';
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        _.extend(requestData, {
                            "coursewareName": _.str.trim(vm.keyword)
                        });
                        util.c2s({
                            "type": "post",
                            "url": erp.BASE_PATH + "oldStaffManagement/queryCoursewareList.do",
                            "data": JSON.stringify(requestData),
                            "contentType": 'application/json',
                            "dataType": "json",
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    callback.call(this, data);
                                }
                            }
                        });
                    }
                };
                vm.query = function () {
                    avalon.getVm(gridId).load();
                };
                vm.studyType = ["按部门", "按职位", "按员工"];
                vm.currentType = "按部门";
                vm.typeNum = 1;
                vm.showIs = false;
                vm.positionData = [];
                vm.employeeData = [];
                vm.keyName = "";
                vm.todoNum = 0; //待办事项数量
                vm.loginUserId = loginUserData.id;  //登录用户Id
                vm.showType = function () {
                    vm.showIs = true;
                };
                vm.chooseType = function (el) {
                    var type = avalon(this).data("type");
                    vm.showIs = false;
                    if (el == vm.currentType) {
                        return;
                    }
                    vm.positionData = [];
                    vm.employeeData = [];
                    switch (type) {
                        case 1:
                            vm.typeNum = 1;
                            break;
                        case 2:
                            getTree("2");
                            vm.typeNum = 2;
                            break;
                        case 3:
                            getTree("3");
                            vm.typeNum = 3;
                            break;
                    }
                    vm.currentType = el;
                };
                vm.chooseSel = function () {
                    if ($(this).hasClass("choose-bg")) {
                        $(this).removeClass("choose-bg");
                    } else {
                        $(this).addClass("choose-bg");
                    }
                };
                vm.searchName = function () {
                    if (_.str.trim(vm.keyName)) {
                        var reg = new RegExp(vm.keyName);
                        vm.employeeData = _.filter(vm.employeeData, function (item) {
                            return item.userName.match(reg) ? true : false;
                        });
                    } else {
                        getTree("3");
                    }
                };
                vm.openStudy = function () {
                    var dialogVm = avalon.getVm(createStudyDialogId),
                        gridVm = avalon.getVm(gridId);
                    dialogVm.title = '发起学习';
                    //选中状态
                    gridVm.selectById($(this).data("id"), true);
                    dialogVm.open();
                };
                vm.$createStudyDialogOpts = {
                    "dialogId": createStudyDialogId,
                    "width": 540,
                    "excludeStudied": true,
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
                        vm.showIs = false;
                        vm.currentType = "按部门";
                        vm.typeNum = 1;
                        vm.positionData = [];
                        vm.employeeData = [];
                    },
                    "submit": function () {
                        var flag = $("input[name='flag']").prop("checked") ? "1" : "0",
                            gridVm = avalon.getVm(gridId),
                            requestData = {"flag": flag, "coursewareId": +gridVm.getSelectedData()[0].id},
                            idList = [],
                            tagName;
                        switch (vm.typeNum) {
                            case 1:
                                tagName = "部门人员";
                                idList = _.map(avalon.getVm(treeId).core.getCheckedNodes(), function (item) {
                                    return item.id;
                                });
                                _.extend(requestData, {idList: idList, conditionType: "1"});
                                break;
                            case 2:
                                tagName = "职位人员";
                                _.extend(requestData, {idList: getSelId($(".position-list .choose-bg")), conditionType: "2"});
                                break;
                            case 3:
                                tagName = "员工";
                                _.extend(requestData, {idList: getSelId($(".employee-list .choose-bg")), conditionType: "3"});
                                break;
                        }
                        //提交ajax
                        util.confirm({
                            "iconType": "info",
                            "content": "你确定所选的" + tagName + "学习" + gridVm.getSelectedData()[0].courseware_name + "吗？",
                            "onSubmit": function () {
                                if (requestData.idList.length > 0) {
                                    util.c2s({
                                        "type": "post",
                                        "url": erp.BASE_PATH + "oldStaffManagement/executeTermBegin.do",
                                        "data": JSON.stringify(requestData),
                                        "contentType": 'application/json',
                                        "dataType": "json",
                                        "success": function (responseData) {
                                            if (responseData.flag) {
                                                util.alert({
                                                    "iconType": "success",
                                                    "content": responseData.message
                                                });
                                                avalon.getVm(createStudyDialogId).close();
                                                //gridVm.unselectById('all');
                                                gridVm.load();
                                            }
                                        }
                                    });
                                } else {
                                    util.alert({
                                        "iconType": "info",
                                        "content": "请选择部门、职位或员工!"
                                    });
                                }
                            }
                        })
                    }
                };
            });
            avalon.scan(pageEl[0], [pageVm]);
            //更新grid数据
            avalon.getVm(gridId).load();
            getTree("1");
            // 加载待办事项
            util.c2s({
                "url": erp.BASE_PATH + "oldStaffManagement/queryToDoListCountByLeaderId.do",
                "data": JSON.stringify({
                    "baseUserId": loginUserData.id
                }),
                "contentType": 'application/json',
                "success": function (responseData) {
                    var data = responseData.data;
                    if (responseData.flag) {
                        pageVm.todoNum = data.totalSize;
                    }
                }
            });
            //获取部门,职位，员工AJax
            function getTree(type) {
                var treeVm = avalon.getVm(treeId), 
                    dialogVm = avalon.getVm(createStudyDialogId),
                    requestData,
                    gridSelectedData = avalon.getVm(gridId).getSelectedData();
                if (type == "3") {
                    requestData = {queryType: type, pageSize: 100000, pageNumber: 1};
                    requestData.coursewareId = gridSelectedData[0].id / 1;
                } else {
                    requestData = {queryType: type};
                }
                requestData.flag = dialogVm.excludeStudied ? "1" : "0";
                util.c2s({
                    "type": "post",
                    "url": erp.BASE_PATH + "oldStaffManagement/queryInfo.do",
                    "data": JSON.stringify(requestData),
                    "contentType": 'application/json',
                    "dataType": "json",
                    "success": function (responseData) {
                        var data = responseData.data.sysGroups;
                        if (responseData.flag) {
                            switch (type) {
                                case "1":
                                    //treeVm.core.getCheckedNodes()
                                    treeVm.updateTree(_.map(data, function (item) {
                                        return {
                                            id: item.id,
                                            name: item.groupName,
                                            pId: item.parentId
                                        };
                                    }));
                                    break;
                                case "2":
                                    pageVm.positionData = responseData.data.sysPositionList;
                                    break;
                                case "3":
                                    pageVm.employeeData = responseData.data.sysUserList.rows;
                                    break;
                            }

                        }
                    }
                });
            }

            //获取选中元素ID
            function getSelId(sel) {
                var arr = [];
                sel.each(function (item) {
                    arr.push($(this).data("id"));
                });
                return arr;
            }
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var createStudyDialogId = pageName + '-create-study-dialog',
                treeId = pageName + '-tree',
                gridId = pageName + '-grid';
            $(avalon.getVm(gridId).widgetElement).remove();
            $(avalon.getVm(createStudyDialogId).widgetElement).remove();
            $(avalon.getVm(treeId).widgetElement).remove();
        }
    });

    return pageMod;
});
