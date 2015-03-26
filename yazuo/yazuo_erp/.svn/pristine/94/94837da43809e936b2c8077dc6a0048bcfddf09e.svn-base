/**
 * 用户信息的添加和编辑
 */
define(['avalon', 'util', 'json', 'moment', '../../../asset/events', '../../../widget/form/form', '../../../widget/form/select', '../../../widget/uploader/uploader', '../../../widget/calendar/calendar'], function (avalon, util, JSON, moment, Events) {
    var win = this,
        erp = win.erp,
        pageMod = {},
        pageModEvent = new Events();
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName, routeData) {
            var uploaderId = pageName + '-uploader',
                positionId = pageName + '-position',
                organizationId = pageName + '-organization',
                formId = pageName + '-form',
                birthdayId = pageName + '-birthday',
                loginUserData = erp.getAppData('user');

            var pageVm = avalon.define(pageName, function (vm) {
                vm.$skipArray = ["tempRangeId"];
                vm.mainRouteName = routeData.route.split('/')[1];   //主路由名，指示是处于个人信息编辑页还是用户管理编辑页
                vm.state = pageName.split('-').pop();
                vm.navCrumbs = [{
                    "text": "用户管理",
                    "href": "#/sysmanage/usermanage"
                }, {
                    "text": (vm.state === "add" ? "添加用户" : "修改用户")
                }];
                //vm.userId = ''; //编辑态下默认的用户id
                vm.userAvatar = erp.ASSET_PATH + "image/user-avatar-default.png";
                vm.userType = 'new';    //默认用户类型
                vm.birthday = '';
                vm.sex = "0";
                vm.userName = '';
                vm.mobile = '';
                vm.macList = [{
                    "value": ""
                }];
                vm.roleList = []; //角色列表
                vm.rangeIdSelected = [];  //选中范围id列表
                vm.exceptUserIdSelected = [];   //选中范围中排除的用户id列表
                vm.tempUserList = [];   //所属部门的员工列表
                vm.tempUserListVisible = false; //控制员工列表的可见性
                vm.tempRangeId = '';    //当前点击的管辖范围部门id
                //vm.positionSelectedIndex = 0;   //职位默认选中索引号
                vm.belongToGroupId = '';    //所属部门ID
                vm.belongToGroupName = '';  //所属部门名字
                vm.fileName = ''; // 上传图片返回的名称
                vm.$uploaderOpts = {
                    "uploaderId": uploaderId,
                    "uploadifyOpts": {
                        "uploader": erp.BASE_PATH + 'user/uploadImage.do',
                         "formData": {
                            "sessionId": loginUserData.sessionId
                         },
                        "fileTypeDesc": "图片文件(*.gif; *.jpg; *.png; *.jpeg)",
                        "fileTypeExts": "*.gif; *.jpg; *.png; *.jpeg",
                        "fileSizeLimit": "2MB",
                        "fileObjName": "myfiles",
                        "multi": false //禁用多选
                    },
                    "onSuccessResponseData": function (reponseText) {
                        var responseData = JSON.parse(reponseText);
                        vm.userAvatar = responseData.data[0].relativePath; //http绝对地址
                        vm.fileName = responseData.data[0].fileName;
                    }
                };
                vm.$positionOpts = {
                    "selectId": positionId,
                    "options": [],
                    "selectIndex": 0
                };
                vm.$organizationOpts = {
                    "dialogId": organizationId,
                    "title": "选择部门",
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
                    "clickNode": function () {
                        var meEl = $(this),
                            nodeTextEl = meEl.closest('.node-text');
                        //选中当前节点，取消其他接口选中
                        vm.belongToGroupId = nodeTextEl.data('groupId');
                        vm.belongToGroupName = nodeTextEl.data('groupName');
                    },
                    "onOpen": function () {},
                    "onClose": function () {},
                    "submit": function (evt) {
                        avalon.getVm(organizationId).close();
                    }
                };

                vm.$formOpts = {    //表单配置
                    "formId": formId,
                    "field": [{
                        "selector": ".user-type",
                        "name": "isFormal",
                        "getValue": function () {
                            return $(this).filter('.mn-radio-state-selected').data('userType');
                        }
                    }, {
                        "selector": ".user-name",
                        "name": "userName",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "姓名不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".birthday",
                        "name": "birthday",
                        "getValue": function () {
                            var val = _.str.trim($(this).val());
                            if (!val.length) {
                                return 0;
                            } else {
                                return moment(val, 'YYYY-MM-DD').unix();
                            }
                        },
                        "rule": function (val) {
                            if (val === 0) {
                                return "生日不能为空";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".sex",
                         "name": "genderType"
                    }, {
                        "selector": ".mac-wrapper",
                        "name": "macs",
                        "getValue": function () {
                            var result = [];
                            _.each(vm.macList.$model, function (itemData) {
                                var mac = _.str.trim(itemData.value);
                                if (mac) {
                                    result.push(mac);
                                }
                            });
                            return result;
                        },
                        "rule": function (vals) {
                            if (!vals.length) {
                                return "MAC地址不能为空";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".mobile",
                        "name": "tel",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "手机号不能为空";
                            } else {
                                var reg=/^(13[0-9]|15[0-9]|18[0-9])\d{8}$/;
                                if(reg.test(val)){
                                    return true;
                                }else{
                                    return "手机号错误";
                                }
                            }
                        }]
                    }, {
                        "selector": ".user-avatar",
                        "name": "userImage",
                        "getValue": function () {
                            return 'img';   //返回图片标识码
                        }
                    }, {
                        "selector": ".organization",
                        "name": "groupId",
                        "getValue": function () {
                            return vm.belongToGroupId;   //返回所属部门id
                        },
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "部门不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".position",
                        "name": "positionId",
                        "getValue": function () {
                            return avalon.getVm(positionId).selectedValue;   //返回职位id
                        },
                        "rule": function (val) {
                            if (!val) {
                                return "职位不能为空";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".control-range-wrapper",   //管辖范围
                        "name": "group",
                        "getValue": function () {
                            return vm.rangeIdSelected.$model;   //返回管辖范围部门id
                        }
                    }, {
                        "selector": ".role-list-wrapper .ck-h",   //权限组
                        "name": "roleIds",
                        "tipDeep": 6,
                        "rule": function (val) {
                            if (!val.length) {
                                $(".role-list-wrapper").addClass('valid-error-field');
                                return "权限组不能为空";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".old-pwd",
                        "name": "oldPassword"
                    }, {
                        "selector": ".confirm-pwd",
                        "name": "newPassword"
                    },{
                        "selector": ".confirm-pwd",
                        "name": "password",
                        "rule": function (val, rs, formElement) {
                            var newPwd = _.str.trim($('.new-pwd', formElement).val());
                            if (val.length || newPwd.length) {
                                if (val !== newPwd) {
                                    return '确认密码不一致';
                                } else {
                                    return true;
                                }
                            } else {
                                return true;
                            }
                        }
                    }],

                    "action": [{
                        "selector": ".f-submit",
                        "handler": function () {
                            var requestData,
                                positionVm = avalon.getVm(positionId),
                                formVm = avalon.getVm(formId);
                            if (formVm.validate()) {
                                requestData = formVm.getFormData();
                                //附加管辖范围排除人的信息
                                requestData["exceptUser"] = vm.exceptUserIdSelected.$model;
                                //如果是修改态，附加userId
                                if (vm.state === "edit") {
                                    requestData["id"] = routeData.params["id"];
                                } else {
                                    requestData["roleIds"] = [].concat(requestData["roleIds"]);
                                }
                               requestData["fileName"] = vm.fileName;
                                //发送后台请求，编辑或添加
                                util.c2s({
                                    "url": erp.BASE_PATH + 'user/saveUser.do',
                                    "type": "post",
                                    "contentType":"application/json",
                                    "data":JSON.stringify(requestData),
                                    "success": function (responseData) {
                                        if (responseData.flag == 1) {

                                            //提交成功，添加态要清空，编辑态不变
                                            if (vm.state == "add") {
                                                formVm.reset();
                                                vm.userAvatar = erp.ASSET_PATH + "image/user-avatar-default.png";
                                                vm.userType = 'new';    //默认用户类型
                                                vm.sex = "0";
                                                vm.birthday = '';
                                                vm.userName = '';
                                                vm.mobile = '';
                                                //vm.roleList = []; //角色列表
                                                vm.rangeIdSelected = [];  //选中范围id列表
                                                vm.exceptUserIdSelected = [];   //选中范围中排除的用户id列表
                                                vm.tempUserList = [];   //所属部门的员工列表
                                                vm.tempUserListVisible = false; //控制员工列表的可见性
                                                vm.tempRangeId = '';    //当前点击的管辖范围部门id
                                                vm.belongToGroupId = '';    //所属部门ID
                                                vm.belongToGroupName = '';  //所属部门名字
                                                vm.fileName = ''; // 上传图片返回的名称
                                                //设置选中第一个职位, 强制刷新
                                                positionVm.select(0, true);
                                            }
                                            util.alert({
                                                "content": responseData.message,
                                                "iconType": "success",
                                                "onSubmit":function () {
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
                vm.$birthdayOpts = {
                    "calendarId": birthdayId,
                    "maxDate": new Date(),
                    "onClickDate": function (d) {
                        vm.birthday = moment(d).format('YYYY-MM-DD');
                        $(this.widgetElement).hide();
                    }
                };
                /**
                 * 打开生日日历选择面板
                 */
                vm.openBirthdayPanel = function () {
                    var meEl = $(this),
                        calendarVm = avalon.getVm(birthdayId),
                        birthdayEl,
                        inputOffset = meEl.offset();
                    if (!calendarVm) {
                        birthdayEl = $('<div ms-widget="calendar,$,$birthdayOpts"></div>');
                        birthdayEl.css({
                            "position": "absolute",
                            "z-index": 10000
                        }).hide().appendTo('body');
                        avalon.scan(birthdayEl[0], [vm]);
                        calendarVm = avalon.getVm(birthdayId);
                        //注册全局点击绑定
                        util.regGlobalMdExcept({
                            "element": birthdayEl,
                            "handler": function () {
                                birthdayEl.hide();
                            }
                        });
                    } else {
                        birthdayEl = $(calendarVm.widgetElement);
                    }
                    //设置focus Date
                    if (vm.birthday) {
                        calendarVm.focusDate = moment(vm.birthday, 'YYYY-MM-DD')._d;
                    } else {
                        calendarVm.focusDate = new Date();
                    }

                    birthdayEl.css({
                        "top": inputOffset.top + meEl.outerHeight() - 1,
                        "left": inputOffset.left
                    }).show();
                };
                /**
                 * 添加一个新的mac输入
                 */
                vm.addMoreMac = function () {
                    vm.macList.push({
                        "value": ""
                    });
                };
                /**
                 * 删除一个mac输入
                 */
                vm.removeMacItem = function () {
                    var itemM = this.$vmodel.$model,
                        index = itemM.$index;
                    vm.macList.removeAt(index);
                };
                vm.openOrganizationDialog = function (evt) {
                    var dialogVm = avalon.getVm(organizationId);
                    dialogVm.open();
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
                    $(':checked', controlRangeWEl).each(function () {
                        vm.rangeIdSelected.ensure($(this).val());
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
            //所属部门组织一棵部门树
            var createGroupTreeForGroup = function (nodeData) {
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
                htmlStr += '<div class="node-text ' + nodeTextCls +
                        '" data-group-id="' + nodeData.id +
                        '" data-group-name="' + nodeData.text +
                        '" ms-class="state-selected: belongToGroupId == ' + nodeData.id + '">' +
                    '<i class="visible-h iconfont" ms-click="switchNodeVisible">&#xe601;</i>' +
                    '<label class="text-content" ms-click="clickNode">' + nodeData.text + '</label></div>';
                if (nodeData.childrenList && nodeData.childrenList.length > 0) {
                    htmlStr += '<ul class="node-list ' + nodeListCls + '">';
                    _.each(nodeData.childrenList, function (itemData) {
                        htmlStr += '<li class="node-item">' + createGroupTreeForGroup(itemData) + '</li>';
                    });
                    htmlStr += '</ul>';
                }
                return htmlStr;
            };
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

            var cptInitState = {     //记录各部分组件的初始化状态，是否初始化完毕
                "group": false, //部门
                "position": false
            };

            if (pageVm.state === "add") {
                cptInitState["role"] = false;    //权限组
                cptInitState["range"] = false;  //管辖范围
            }

            if (pageVm.state === "add") {   //添加态需要初始化权限组
                initRole();
            } else {    //编辑态设置默认用户信息
                pageModEvent.on('cptInited', function () {
                    setUserData(); // 设置基本信息
                });
            }
            // 初始化部门树形
            var controlRangeWEl = $('.control-range-wrapper', pageEl);
            //请求部门信息
            if (pageVm.mainRouteName === "sysmanage") {
                util.c2s({
                    "url": erp.BASE_PATH + 'group/initGroup.do',
                    "success": function (responseData) {
                        var data,
                            rootNodeData;
                        if (responseData.flag) {
                            data = responseData.data;
                            rootNodeData = {
                                "id": 0,
                                "text": "root",
                                "childrenList": data,
                                "isRoot": true
                            };
                            //渲染所属部门树
                            avalon.getVm(organizationId).setContent('<div class="group-wrapper">' + createGroupTreeForGroup(rootNodeData) + '</div>');
                            if (pageVm.state === "add") {   //只有添加态有管辖范围树
                                //渲染管辖范围树
                                controlRangeWEl.html(createGroupTreeForRange(rootNodeData));
                                avalon.scan(controlRangeWEl[0], [pageVm]);
                                cptInitState["range"] = true;
                            }
                            cptInitState["group"] = true;
                            if (_.every(cptInitState, function (initState) {
                                return initState;
                            })) {
                                pageModEvent.trigger('cptInited');
                            }
                        }
                    }
                });

                //请求职位列表
                initPosition();
            } else if (pageVm.mainRouteName === "profile") {    //个人编辑页直接触发cptInited
                pageModEvent.trigger('cptInited');
            }

            //权限组列表
            function initRole () {
                util.c2s({
                    "url": erp.BASE_PATH + 'role/initRole.do',
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            pageVm.roleList = data;

                            cptInitState["role"] = true;
                            if (_.every(cptInitState, function (initState) {
                                return initState;
                            })) {
                                pageModEvent.trigger('cptInited');
                            }
                        }
                    }
                });
            }

            //职位列表
            function initPosition() {
               util.c2s({
                    "url": erp.BASE_PATH + 'position/initPosition.do',
                    "success": function (responseData) {
                        var data,
                            positionVm = avalon.getVm(positionId);
                        if (responseData.flag) {
                            data = responseData.data;
                            positionVm.setOptions(_.map(data, function (itemData) {
                                return {
                                   "text": itemData.position_name,
                                   "value": itemData.id
                               };
                            }), 0);

                            cptInitState["position"] = true;
                            if (_.every(cptInitState, function (initState) {
                                return initState;
                            })) {
                                pageModEvent.trigger('cptInited');
                            }
                        }
                    }
                });
            }

            /**获取基本信息*/
            function setUserData() {
                util.c2s({
                    "url": erp.BASE_PATH + 'user/edit.do',
                    "data": {
                        "id": routeData.params["id"],
                        "editContent":"baseSetting"
                    },
                    "success": function (responseData) {
                        var user = responseData.data;
                        var positionVm,
                            positionPanelVmodel,
                            selectedIndex = -1;
                        if (responseData.flag) {
                            pageVm.userType = user.isFormal == "0" ? "new" : "old";
                            pageVm.userName = user.userName;
                            pageVm.birthday = moment.unix(user.birthday).format('YYYY-MM-DD');
                            pageVm.sex = user.genderType;
                            pageVm.mobile = user.tel;
                            pageVm.belongToGroupId = user.groupId || "";    //所属部门ID
                            pageVm.belongToGroupName = user.groupName || "";  //所属部门名字
                            pageVm.userAvatar =user.userImage || ""; // 图像显示
                            pageVm.fileName = user.fileName || "";
                            if (pageVm.mainRouteName === "sysmanage") {
                                positionVm = avalon.getVm(positionId);
                                positionPanelVmodel = positionVm.panelVmodel;
                                pageVm.macList = _.map(user.macs, function (mac) {
                                    return {
                                        "value": mac
                                    };
                                });
                                //设置默认选中职位
                                _.some(positionPanelVmodel.options.$model, function (itemData, i) {
                                    if (itemData.value == user.positionId) {
                                        selectedIndex = i;
                                        return true;
                                    }
                                });
                                positionPanelVmodel.selectedIndex = selectedIndex;
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
            var uploaderId = pageName + '-uploader',
                positionId = pageName + '-position',
                organizationId = pageName + '-organization',
                formId = pageName + '-form',
                birthdayId = pageName + '-birthday';
            var birthdayVm = avalon.getVm(birthdayId);

            $(avalon.getVm(uploaderId).widgetElement).remove();
            if (avalon.getVm(pageName).mainRouteName === "sysmanage") {
                $(avalon.getVm(positionId).widgetElement).remove();
                $(avalon.getVm(organizationId).widgetElement).remove();
            }

            $(avalon.getVm(formId).widgetElement).remove();
            birthdayVm && $(birthdayVm.widgetElement).remove();

            //解除事件注册
            pageModEvent.off('cptInited');
        }
    });

    return pageMod;
});
