/**
 * 目标群体选择器
 */
define(["avalon", "util", "json", "text!./targetselector.html", "text!./targetselector.css", "common", "../../widget/dialog/dialog", "../../widget/tree/tree.js", "../../widget/form/select.js"], function(avalon, util, JSON, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    var styleData = styleEl.data('module') || {};
    if (!styleData["targetselector"]) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'targetselector/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'targetselector/');
        }
        styleData["targetselector"] = true;
        styleEl.data('module', styleData);
    }
    var module = erp.module.targetselector = function(element, data, vmodels) {
        var opts = data.targetselectorOptions,
            dialogId = data.targetselectorId + '-dialog',
            formId = data.targetselectorId + '-form',
            employeeTypeId = data.targetselectorId + '-employee-type',
            departmentId = data.targetselectorId + '-department',
            elEl = $(element);
        var vmodel = avalon.define(data.targetselectorId, function (vm) {
            avalon.mix(vm, opts);
            vm.widgetElement = element;
            vm.dialogId = dialogId;
            vm.$dialogOpts = {
                "dialogId": dialogId,
                "title": vm.title,
                "width": 600,
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
                    vm.onClose();
                },
                "onOpen": function () {
                    var dialogVm = avalon.getVm(dialogId);
                    if (vm.updateOnOpen) {
                        vm.prepareData(function () {
                            //onPrepare回调
                            vm.onPrepareData();
                        });
                    }
                    vm.onOpen();
                },
                "submit": function (evt) {
                    var dialogVm = avalon.getVm(dialogId);
                    vm.onSubmit && vm.onSubmit.call(vm, vm.getSelectedData());
                    dialogVm.close();
                }
            };
            vm.$formOpts = {
                "formId": formId
            };
            vm.$employeeTypeOpts = {
                "selectId": employeeTypeId,
                "options": [{
                    "value": "department",
                    "text": "部门"
                }, {
                    "value": "position",
                    "text": "职位"
                }, {
                    "value": "user",
                    "text": "员工"
                }],
                "onSelect": function (v) {
                    vm.currentEmployeeType = v;
                }
            };
            vm.$departmentOpts = {
                "treeId": departmentId,
                "ztreeOptions": {
                    "setting": {
                        view: {
                            showIcon: false
                        },
                        "check": {
                            "enable": true,
                            "Y" : "ps", 
                            "N" : "ps" 
                        },
                        "data": {
                            "simpleData": {
                                "enable": true
                            }
                        },
                        "callback": {
                            "onCheck": function (evt, treeId, treeNode) {    //点击节点触发
                                var treeVm = avalon.getVm(departmentId),
                                    nodes = treeVm.core.getCheckedNodes(true);
                                //筛掉半钩状态
                                nodes = _.filter(nodes, function (itemData) {
                                    return !itemData.getCheckStatus().half;
                                });
                                //先取消所有节点选中
                                _.each(vm.departmentList.$model, function (itemData, i) {
                                    vm.departmentList[i].isSelected = false;
                                });
                                //再选中勾选的
                                _.each(nodes, function (itemData) {
                                    _.some(vm.departmentList.$model, function (itemData2, i) {
                                        if (itemData2.id == itemData.id) {
                                            vm.departmentList[i].isSelected = true;
                                            return true;
                                        }
                                    });
                                });
                                //更新选择结果
                                vm.updateView();
                            }
                        }
                    },
                    "treeData": []
                }
            };
            vm.currentTargetType = "merchantContacter";
            vm.currentEmployeeType = "department";
            vm.merchantContacterSelected = false;
            vm.merchantManagerSelected = false;
            vm.employeeSelected = false;
            vm.departmentList = [];
            vm.positionList = [];
            vm.userList = [];
            vm.contacterList = [];
            vm.managerList = [];
            vm.targetSelectedText = "";
            vm.searchPositionText = "";
            vm.$watch("searchPositionText", function (v) {
                vm.filterPosition();
            });
            vm.searchUserText = "";
            vm.$watch("searchUserText", function (v) {
                vm.filterUser();
            });
            vm.filterPosition = function () {
                var t = _.str.trim(vm.searchPositionText);
                if (t) {
                    _.each(vm.positionList.$model, function (itemData, i) {
                        try {
                            if (itemData.text.search(t) !== -1) {
                                vm.positionList[i].isVisible = true;
                            } else {
                                vm.positionList[i].isVisible = false;
                            }
                        } catch(evt) {
                            vm.positionList[i].isVisible = false;
                        }
                    });
                } else {
                    _.each(vm.positionList.$model, function (itemData, i) {
                        vm.positionList[i].isVisible = true;
                    });
                }
            };
            vm.filterUser = function () {
                var t = _.str.trim(vm.searchUserText);
                if (t) {
                    _.each(vm.userList.$model, function (itemData, i) {
                        try {
                            if (itemData.text.search(t) !== -1 || itemData.tel.search(t) !== -1) {
                                vm.userList[i].isVisible = true;
                            } else {
                                vm.userList[i].isVisible = false;
                            }
                        } catch(evt) {
                            vm.userList[i].isVisible = false;
                        }
                    });
                } else {
                    _.each(vm.userList.$model, function (itemData, i) {
                        vm.userList[i].isVisible = true;
                    });
                }
            };
            vm.clickPositionItem = function () {
                var v = avalon(this).data("value"),
                    atIndex = -1;
                if (_.some(vm.positionList.$model, function (itemData, i) {
                    if (itemData.value == v) {
                        atIndex = i;
                        return true;
                    }
                })) {
                    vm.positionList[atIndex].isSelected = !vm.positionList[atIndex].isSelected; 
                }
                //更新选择结果
                vm.updateView();
            };
            vm.clickUserItem = function () {
                var v = avalon(this).data("value"),
                    atIndex = -1;
                if (_.some(vm.userList.$model, function (itemData, i) {
                    if (itemData.value == v) {
                        atIndex = i;
                        return true;
                    }
                })) {
                    vm.userList[atIndex].isSelected = !vm.userList[atIndex].isSelected; 
                }
                //更新选择结果
                vm.updateView();
            };
            vm.changeContacter = function () {
                //更新选择结果
                vm.updateView();
            };
            vm.changeManager = function () {
                //更新选择结果
                vm.updateView();
            };
            vm.prepareData = function (callback) {
                util.c2s({
                    "url": erp.BASE_PATH + 'smsTemplate/getObjects.do',
                    "type": "post",
                    "contentType": 'application/json',
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            //职位
                            vm.positionList = _.map(data.positions, function (itemData) {
                                return {
                                    "value": itemData.id,
                                    "text": itemData.positionName,
                                    "isSelected": false,
                                    "isVisible": true
                                };
                            });
                            //员工
                            vm.userList = _.map(data.users, function (itemData) {
                                return {
                                    "value": itemData.id,
                                    "text": itemData.userName,
                                    "tel": itemData.tel,
                                    "isSelected": false,
                                    "isVisible": true
                                };
                            });
                            //联系人
                            vm.contacterList = _.map(data.roleTypes, function (itemData) {
                                return {
                                    "value": itemData.value,
                                    "text": itemData.text,
                                    "isSelected": false
                                };
                            });
                            //负责人
                            vm.managerList = _.map(data.userTypes, function (itemData) {
                                return {
                                    "value": itemData.value,
                                    "text": itemData.text,
                                    "isSelected": false
                                };
                            });
                            //部门
                            vm.departmentList = _.map(data.groups, function (itemData) {
                                return {
                                    "value": itemData.id,
                                    "text": itemData.groupName,
                                    "id": itemData.id,
                                    "pId": itemData.parentId,
                                    "name": itemData.groupName,
                                    "isSelected": false
                                };
                            });
                            //更新树信息
                            avalon.getVm(departmentId).updateTree(vm.departmentList.$model, true);
                            callback && callback.call(vm, data);
                        }
                    }
                });
            };
            vm.clearData = function () {
                var dialogVm = avalon.getVm(dialogId);
                //清理
                vm.currentTargetType = "merchantContacter";
                vm.currentEmployeeType = "department";
                vm.merchantContacterSelected = false;
                vm.merchantManagerSelected = false;
                vm.employeeSelected = false;
                vm.departmentList = [];
                vm.positionList = [];
                vm.userList = [];
                vm.contacterList = [];
                vm.managerList = [];
                vm.targetSelectedText = "";
                vm.searchPositionText = "";
                avalon.getVm(departmentId).updateTree([], true);
                //定位到商户联系人tab
                vm.currentTargetType = "merchantContacter";
                //定位到部门
                vm.currentEmployeeType = "department";
                avalon.getVm(employeeTypeId).select(0);
                //滚动到顶部
                $('.list-wrapper', dialogVm.widgetElement).scrollTop(0);
            };
            vm.getSelectedData = function () {
                var value = {},
                    tempValue = [],
                    text = [],
                    tempText = [];
                //联系人
                _.each(vm.contacterList.$model, function (itemData) {
                    if (itemData.isSelected) {
                        tempText.push(itemData.text);
                        tempValue.push(itemData.value);
                    }
                });
                if (tempText.length) {
                    text.push(tempText.join('，'));
                    value.contacter = tempValue;
                }
                //商户负责人
                tempText = [];
                tempValue = [];
                _.each(vm.managerList.$model, function (itemData) {
                    if (itemData.isSelected) {
                        tempText.push(itemData.text);
                        tempValue.push(itemData.value);
                    }
                });
                if (tempText.length) {
                    text.push(tempText.join('，'));
                    value.manager = tempValue;
                }
                //部门
                tempText = [];
                tempValue = [];
                _.each(vm.departmentList.$model, function (itemData) {
                    if (itemData.isSelected) {
                        tempValue.push(itemData.value);
                    }
                });
                if (tempValue.length) {
                    text.push(vm.getDepartmentText(tempValue));
                    value.department = tempValue;
                }
                //职位
                tempText = [];
                tempValue = [];
                _.each(vm.positionList.$model, function (itemData) {
                    if (itemData.isSelected) {
                        tempText.push(itemData.text);
                        tempValue.push(itemData.value);
                    }
                });
                if (tempText.length) {
                    text.push(tempText.join('，'));
                    value.position = tempValue;
                }
                //员工
                tempText = [];
                tempValue = [];
                _.each(vm.userList.$model, function (itemData) {
                    if (itemData.isSelected) {
                        tempText.push(itemData.text);
                        tempValue.push(itemData.value);
                    }
                });
                if (tempText.length) {
                    text.push(tempText.join('，'));
                    value.user = tempValue;
                }
                return {
                    "value": value,
                    "text": text.join('；\n')
                };
            };
            vm.getDepartmentText = function (selectedIds) {
                selectedIds = [].concat(selectedIds);
                var i,
                    tempNode,
                    text = [];
                //先排序，从大到小
                selectedIds = _.sortBy(selectedIds, function (v) {
                    return v;
                }).reverse();
                //干掉所有的子节点
                for (i = 0; i < selectedIds.length; i++) {
                    tempNode = _.find(vm.departmentList.$model, function (itemData) {
                        return itemData.value == selectedIds[i];
                    });
                    //如果在所有选中id里含有当前id的父id，则把自己干掉
                    if (_.some(selectedIds, function (id) {
                        return id == tempNode.pId;
                    })) {
                        selectedIds.splice(i, 1);
                        i--;
                    }
                }
                //构建层级部门串
                _.each(selectedIds, function (id) {
                    tempNode = _.find(vm.departmentList.$model, function (itemData) {
                        return itemData.value == id;
                    });
                    if (_.some(vm.departmentList.$model, function (itemData) {
                        return itemData.pId == id;
                    })) {
                        text.push(tempNode.text + '（全体）');
                    } else {
                        text.push(tempNode.text);
                    }
                });
                return text.join('，');
            };
            vm.setSelectedData = function (selectedData) {
                var position = selectedData.position,
                    user = selectedData.user,
                    department = selectedData.department,
                    contacter = selectedData.contacter,
                    manager = selectedData.manager;
                var treeVm = avalon.getVm(departmentId);
                //职位
                _.each(vm.positionList.$model, function (v, i) {
                    vm.positionList[i].isSelected = false;
                });
                _.each(position, function (v) {
                    _.some(vm.positionList.$model, function (itemData, i) {
                        if (itemData.value == v) {
                            vm.positionList[i].isSelected = true;
                        }
                    });
                });
                //员工
                _.each(vm.userList.$model, function (v, i) {
                    vm.userList[i].isSelected = false;
                });
                _.each(user, function (v) {
                    _.some(vm.userList.$model, function (itemData, i) {
                        if (itemData.value == v) {
                            vm.userList[i].isSelected = true;
                        }
                    });
                });
                //部门
                _.each(vm.departmentList.$model, function (v, i) {
                    vm.departmentList[i].isSelected = false;
                });
                treeVm.core.checkAllNodes(false);
                _.each(department, function (v) {
                    _.some(vm.departmentList.$model, function (itemData, i) {
                        var node;
                        if (itemData.value == v) {
                            vm.departmentList[i].isSelected = true;
                            //更新部门勾选状态
                            node = treeVm.core.getNodeByParam("id", itemData.value, null);
                            treeVm.core.checkNode(node, true, true);    //联动节点勾选
                        }
                    });
                });
                
                //商户联系人
                _.each(vm.contacterList.$model, function (v, i) {
                    vm.contacterList[i].isSelected = false;
                });
                _.each(contacter, function (v) {
                    _.some(vm.contacterList.$model, function (itemData, i) {
                        if (itemData.value == v) {
                            vm.contacterList[i].isSelected = true;
                        }
                    });
                });
                //商户负责人
                _.each(vm.managerList.$model, function (v, i) {
                    vm.managerList[i].isSelected = false;
                });
                _.each(manager, function (v) {
                    _.some(vm.managerList.$model, function (itemData, i) {
                        if (itemData.value == v) {
                            vm.managerList[i].isSelected = true;
                        }
                    });
                });
            };
            /**
             * 更新视图
             */
            vm.updateView = function () {
                var selectedData = vm.getSelectedData();
                vm.targetSelectedText = selectedData.text;
                //显示对应tab有值显示
                //商户联系人tab
                if (_.some(vm.contacterList.$model, function (itemData) {
                    return itemData.isSelected;
                })) {
                    vm.merchantContacterSelected = true;
                } else {
                    vm.merchantContacterSelected = false;
                }
                //商户负责人tab
                if (_.some(vm.managerList.$model, function (itemData) {
                    return itemData.isSelected;
                })) {
                    vm.merchantManagerSelected = true;
                } else {
                    vm.merchantManagerSelected = false;
                }
                //雅座员工tab
                if (_.some(vm.departmentList.$model.concat(vm.positionList.$model).concat(vm.userList.$model), function (itemData) {
                    return itemData.isSelected;
                })) {
                    vm.employeeSelected = true;
                } else {
                    vm.employeeSelected = false;
                }
                
            };
            vm.switchTargetType = function () {
                var targetType = avalon(this).data('name');
                vm.currentTargetType = targetType;
            };
            vm.open = function () {
                avalon.getVm(dialogId).open();
            };
            vm.close = function () {
                avalon.getVm(dialogId).close();
            };
            vm.$init = function() {
                elEl.addClass('module-cwselector fn-hide');
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm.$remove = function() {
                $(avalon.getVm(departmentId).widgetElement).remove();
                $(avalon.getVm(employeeTypeId).widgetElement).remove();
                $(avalon.getVm(formId).widgetElement).remove();
                $(avalon.getVm(dialogId).widgetElement).remove();
                elEl.off().empty();
            };
            vm.$watch('title', function (val) {
                var dialogVm = avalon.getVm(dialogId);
                dialogVm.title = val;
            });
            vm.$skipArray = ["widgetElement", "dialogId"];
        });
        return vmodel;
    };
    module.version = 1.0;
    module.defaults = {
        "title": "",
        "updateOnOpen": false,  //dialog打开自动更新数据, 默认不启用，依赖手动调用prepareData接口
        "onPrepareData": avalon.noop,
        "onSubmit": avalon.noop,
        "onOpen": avalon.noop,
        "onClose": avalon.noop
    };
    return avalon;
});
