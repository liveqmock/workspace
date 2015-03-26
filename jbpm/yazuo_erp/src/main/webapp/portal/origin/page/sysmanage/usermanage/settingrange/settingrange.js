/**
 * 用户信息的添加和编辑
 */
define(['avalon', 'util', 'json', '../../../../widget/form/form', '../../../../widget/form/select', '../../../../widget/uploader/uploader'], function (avalon, util, JSON) {
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
            var formId = pageName + '-form';
            var pageVm = avalon.define(pageName, function (vm) {
                vm.navCrumbs = [{
                    "text": "用户管理",
                    "href": "#/sysmanage/usermanage"
                }, {
                    "text": "设置管辖范围"
                }];
                vm.userName = decodeURIComponent(routeData.params["userName"]);
                vm.$skipArray = ["tempRangeId"];
                vm.rangeIdSelected = [];  //选中范围id列表
                vm.exceptUserIdSelected = [];   //选中范围中排除的用户id列表
                vm.tempUserList = [];   //所属部门的员工列表
                vm.tempUserListVisible = false; //控制员工列表的可见性
                vm.tempRangeId = '';    //当前点击的管辖范围部门id

                vm.$formOpts = {    //表单配置
                    "formId": formId,
                    "field": [{
                        "selector": ".control-range-wrapper",   //管辖范围
                        "name": "group",
                        "getValue": function () {
                            return vm.rangeIdSelected.$model;   //返回管辖范围部门id
                        }
                    }],
                    "action": [{
                        "selector": ".f-submit",
                        "handler": function () {
                            var requestData,
                                formVm = avalon.getVm(formId);
                            if (formVm.validate()) {
                                requestData = formVm.getFormData();
                                //附加管辖范围排除人的信息
                                requestData["exceptUser"] = vm.exceptUserIdSelected.$model;
                                //附加用户id
                                _.extend(requestData, {
                                    "id": routeData.params["id"]
                                });
                                //发送后台请求，编辑或添加
                                util.c2s({
                                    "url": erp.BASE_PATH + 'user/updateManager.do',
                                    "type": "post",
                                    "contentType":"application/json",
                                    "data":JSON.stringify(requestData),
                                    "success": function (responseData) {
                                        if (responseData.flag == 1) {
                                            util.alert({
                                                "content": responseData.message,
                                                "iconType": "success",
                                                "onSubmit": function () {
                                                    util.jumpPage('#/sysmanage/usermanage');
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    }]
                };
                vm.switchNodeVisible = function (evt) {
                    var meEl = $(this),
                        nodeTextEl = meEl.closest('.node-text'),
                        nextListEl = nodeTextEl.next('.node-list'),
                        visibleState = meEl.data('visibleState') || 'shown';
                    if (visibleState == "shown") {
                        nextListEl.slideUp('fast');
                        meEl.data('visibleState', 'hidden').html('&#xe600;');
                    } else {
                        nextListEl.slideDown('fast');
                        meEl.data('visibleState', 'shown').html('&#xe601;');
                    }
                };
                vm.clickNodeCk = function (evt) {
                    var meEl = $(this),
                        nodeTextEl = meEl.closest('.node-text'),
                        nextListEl = nodeTextEl.next('.node-list');
                     //多层选中上级节点
                    var checkParentNode = function (currentNodeTextEl) {
                        var parentNodeTextEl = currentNodeTextEl.closest('.node-list').prev('.node-text'),
                            siblingsItemEl = currentNodeTextEl.closest('.node-item').siblings(),
                            isAllChecked = true;
                        siblingsItemEl.each(function () {
                            var itemEl = $(this);
                            if (!$('.ck-h', itemEl).prop('checked')) {
                                isAllChecked = false;
                                return false;
                            }
                        });
                        if (isAllChecked && parentNodeTextEl.length) {
                            $('.ck-h', parentNodeTextEl).prop('checked', true);
                            checkParentNode(parentNodeTextEl);
                        }
                    };
                    //多层取消上级节点
                    var uncheckParentNode = function (currentNodeTextEl) {
                        var itemEl = currentNodeTextEl.parent(),
                            parentNodeTextEl = currentNodeTextEl.closest('.node-list').prev('.node-text');
                        if (parentNodeTextEl.length) {
                            $('.ck-h', parentNodeTextEl).prop('checked', false);
                            uncheckParentNode(parentNodeTextEl);
                        }
                    };
                    //先关闭部门下员工列表
                    vm.closeTempUserList();
                    if (meEl.prop('checked')) {
                        //选中上级
                        //checkParentNode(nodeTextEl);  //取消向下找的机制
                        //全选下级
                        $('.ck-h', nextListEl).prop('checked', true);
                        //打开部门下员工列表
                        vm.tempRangeId = nodeTextEl.data('groupId');
                        vm.tempUserListVisible = true;
                    } else {
                        //如果是最后一个选中节点，取消深层上级节点选中
                        //uncheckParentNode(nodeTextEl);    //取消向下找的机制
                        //取消下级选中
                        $('.ck-h', nextListEl).prop('checked', false);
                    }
                    //设置选中的部门id
                    vm.rangeIdSelected.clear();
                    $(':checked', controlRangeWEl).each(function () {
                        vm.rangeIdSelected.push($(this).val());
                    });
                };
                vm.closeTempUserList = function () {
                    vm.tempRangeId = '';
                    vm.tempUserListVisible = false;
                };
                vm.clickTempUserItem = function () {
                    var itemM = this.$vmodel.$model,
                        index = itemM.$index,
                        existIndex = -1;
                    if (itemM.el.isChecked) {   //取消选中
                        vm.tempUserList.set(index, {
                            "isChecked": false
                        });
                        //添加到排除列表
                        if (!_.some(vm.exceptUserIdSelected, function (itemData) {
                            return itemData.id == itemM.el.id;
                        })) {
                            vm.exceptUserIdSelected.push({
                                "id": itemM.el.id,
                                "belongToGroup": itemM.el.group_id
                            });
                        }

                    } else {
                        vm.tempUserList.set(index, {
                            "isChecked": true
                        });
                        //从排除列表里删除
                        if (_.some(vm.exceptUserIdSelected, function (itemData, i) {
                            if (itemData.id == itemM.el.id) {
                                existIndex = i;
                            }
                        })) {
                            vm.exceptUserIdSelected.removeAt(existIndex);
                        }
                    }
                };
                //监控tempUserListVisible,刷新并选中员工列表
                vm.$watch('tempUserListVisible', function (isVisible) {
                    if (isVisible) {
                        util.c2s({
                            "url": erp.BASE_PATH + 'user/getUserOfGroup.do',
                            "data": {
                                "groupId": vm.tempRangeId
                            },
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    vm.tempUserList = _.map(data, function (itemData) {
                                        return _.extend(itemData, {
                                            "isChecked": true
                                        });
                                    });
                                }
                            }
                        });
                    }
                });
            });
            avalon.scan(pageEl[0]);
            //监测选中的管辖部门id
            pageVm.rangeIdSelected.$watch('length', function () {
                pageVm.exceptUserIdSelected = _.filter(pageVm.exceptUserIdSelected.$model, function (itemData) {
                    if (_.some(pageVm.rangeIdSelected, function (groupId) {
                        return groupId == itemData.belongToGroup;
                    })) {
                        return true;
                    }
                });
            });
            //管辖范围组织一棵部门树
            var createGroupTreeForRange = function (nodeData) {
                var htmlStr = '',
                    nodeTextCls = '',
                    nodeListCls = '';
                if (!nodeData.childrenList || nodeData.childrenList.length === 0) {
                    nodeTextCls = 'leaf-node-text';
                }
                if (nodeData.isRoot) {
                    nodeTextCls += ' root-node-text';
                    nodeListCls = 'root-node-list';
                }
                htmlStr += '<div class="node-text ' + nodeTextCls + '" data-group-id="' + nodeData.id + '">' +
                    '<i class="visible-h iconfont" ms-click="switchNodeVisible">&#xe601;</i><span class="label-for"><input type="checkbox" class="ck-h" ms-click="clickNodeCk" ms-value="' + nodeData.id + '" />&nbsp;' +
                    '<label class="text-content">' + nodeData.text + '</label></span></div>';
                if (nodeData.childrenList && nodeData.childrenList.length > 0) {
                    htmlStr += '<ul class="node-list ' + nodeListCls + '">';
                    _.each(nodeData.childrenList, function (itemData) {
                        htmlStr += '<li class="node-item">' + createGroupTreeForRange(itemData) + '</li>';
                    });
                    htmlStr += '</ul>';
                }
                return htmlStr;
            };

            var controlRangeWEl = $('.control-range-wrapper', pageEl);
            util.c2s({
                "url": erp.BASE_PATH + 'group/initGroup.do',
                "success": function (responseData) {
                    var data;
                    if (responseData.flag) {
                        data = responseData.data;
                         rootNodeData = {
                                 "id": 0,
                                 "text": "root",
                                 "childrenList": data,
                                 "isRoot": true
                             };
                        //渲染管辖范围树
                        controlRangeWEl.html(createGroupTreeForRange(rootNodeData));
                        avalon.scan(controlRangeWEl[0], [pageVm]);
                        //设置默认的管辖范围
                        util.c2s({
                            "url": erp.BASE_PATH + 'user/edit.do',
                            "data": {
                                "id": routeData.params["id"],
                                "editContent":"managerSetting"
                            },
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    //选中checkbox
                                    _.each(data.group, function (groupId) {
                                        $('[value="' + groupId + '"]', controlRangeWEl).prop('checked', true);
                                    });
                                    pageVm.rangeIdSelected = _.map(data.group, function (groupId) {
                                        return groupId + "";
                                    });
                                    pageVm.exceptUserIdSelected = _.map(data.exceptUser, function (itemData) {
                                        return {
                                            "id": itemData.user_id,
                                            "belongToGroup": itemData.group_id
                                        };
                                    });
                                }
                            }
                        });
                    }
                }
            });
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
              var formId = pageName + '-form';
              $(avalon.getVm(formId).widgetElement).remove();
        }
    });

    return pageMod;
});
