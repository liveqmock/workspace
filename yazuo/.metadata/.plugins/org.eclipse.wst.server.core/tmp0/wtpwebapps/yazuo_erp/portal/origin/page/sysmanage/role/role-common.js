/**
 * 权限组管理-添加/删除
 */
define(['avalon', 'util'], function (avalon, util) {
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
            var permissionBoxEl = $('.permission-box', pageEl),
                formId = pageName + '-form';

            var pageVm = avalon.define(pageName, function (vm) {
                vm.navCrumbs = [{
                    "text": "权限组管理",
                    "href": "#/sysmanage/role"
                }, {
                    "text": (pageName.slice(-3) === "add" ? "添加权限组" : "修改权限组")
                }];
                vm.formState = (pageName.slice(-3) === "add" ? "add" : "edit");
                vm.roleName = (pageName.slice(-3) === "add" ? "" : decodeURIComponent(routeData.params["rolename"]));
                vm.$formOpts = {
                    "formId": formId,
                    "field": [{
                        "selector": ".role-name",
                        "name": "roleName",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "权限组名称不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".permission-box .check-h",
                        "name": "permission",
                        "tipDeep": 10,
                        "rule": function (vals) {
                            if (vals.length === 0) {
                                return "请至少分配一种权限";
                            } else {
                                return true;
                            }
                        }
                    }],
                    "action": [{
                        "selector": ".f-submit",
                        "handler": function () {
                            var requestData,
                                url = "role/saveRole.do",
                                formVm = avalon.getVm(formId);
                            if (formVm.validate()) {
                                requestData = formVm.getFormData();
                                //一维化
                                requestData["permission"] = [].concat(requestData["permission"]);
                                if (vm.formState === "edit") {
                                    _.extend(requestData, {
                                        "id": routeData.params["id"]
                                    });
                                }
                                util.c2s({
                                    "url": erp.BASE_PATH + url,
                                    "data": requestData,
                                    "success": function (responseData) {
                                        if (responseData.flag) {
                                             if (vm.formState === "add") {   //添加后要重置表单状态
                                                 formVm.reset();
                                             }
                                            util.alert({
                                                "content": responseData.message,
                                                "iconType": "success",
                                                "onSubmit":function () {
                                                     //window.location.href = erp.BASE_PATH + "#!/sysmanage/role";
                                                     location.reload(); //强制刷新，保证权限能即时生效
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    }]
                };
                vm.clickNode = function (evt) {
                    var meEl = $(this),
                        itemEl = meEl.closest('.permission-item'), //直接包含
                        isChecked = meEl.prop('checked');

                    //只有所有的子节点选中，才会选中父节点
                    /*var traverseUpCheck = function (currentCkEl) {
                        var itemEl = currentCkEl.closest('.permission-item'), //直接包含
                            siblingsEl = itemEl.siblings('.permission-item'),
                            parentItemEl = itemEl.parent().closest('.permission-item'),
                            parentCkEl = parentItemEl.children('.permission-title').find(':checkbox');
                        if (currentCkEl.prop('checked') && siblingsEl.children('.permission-title').find(':checked').length === siblingsEl.children('.permission-title').find(':checkbox').length) {
                            parentCkEl.prop('checked', true);
                        }
                        if (parentCkEl.closest('.permission-item').length > 0) {
                            traverseUpCheck(parentCkEl);
                        }
                    };
                    var traverseUpUncheck = function (currentCkEl) {
                        var itemEl = currentCkEl.closest('.permission-item'), //直接包含
                            siblingsEl = itemEl.siblings('.permission-item'),
                            parentItemEl = itemEl.parent().closest('.permission-item'),
                            parentCkEl = parentItemEl.children('.permission-title').find(':checkbox');
                        parentCkEl.prop('checked', false);

                        if (parentCkEl.closest('.permission-item').length > 0) {
                            traverseUpUncheck(parentCkEl);
                        }
                    };*/
                    //只要有一个子节点选中，就会选中父节点
                    var traverseUpCheck = function (currentCkEl) {
                        var itemEl = currentCkEl.closest('.permission-item'), //直接包含
                            parentItemEl = itemEl.parent().closest('.permission-item'),
                            parentCkEl = parentItemEl.children('.permission-title').find(':checkbox');
                        parentCkEl.prop('checked', true);

                        if (parentCkEl.closest('.permission-item').length > 0) {
                            traverseUpCheck(parentCkEl);
                        }
                    };
                    var traverseUpUncheck = function (currentCkEl) {
                        var itemEl = currentCkEl.closest('.permission-item'), //直接包含
                            siblingsEl = itemEl.siblings('.permission-item'),
                            parentItemEl = itemEl.parent().closest('.permission-item'),
                            parentCkEl = parentItemEl.children('.permission-title').find(':checkbox');
                        if (siblingsEl.children('.permission-title').find(':checked').length === 0) {
                            parentCkEl.prop('checked', false);

                            if (parentCkEl.closest('.permission-item').length > 0) {
                                traverseUpUncheck(parentCkEl);
                            }
                        }

                    };
                    if (isChecked) {
                        //处理上级全选
                        traverseUpCheck(meEl);
                        //处理下级选中
                        itemEl.children('.permission-list').find(':checkbox').prop('checked', true);
                    } else {
                        //traverseUpUncheck(meEl);
                        itemEl.children('.permission-list').find(':checkbox').prop('checked', false);
                    }

                };
                vm.toggleVisible = function (evt) {
                    var meEl = $(this),
                        itemEl = meEl.closest('.permission-item'), //直接包含
                        subListEl = itemEl.children('.permission-list'),
                        isHidden = meEl.data('isHidden');
                    if (!isHidden) {
                        subListEl.slideUp('fast');
                        meEl.data('isHidden', true);
                        meEl.html('&#xe600;');
                    } else {
                        subListEl.slideDown('fast');
                        meEl.data('isHidden', false);
                        meEl.html('&#xe601;');
                    }
                };
            });
            //请求权限数据
            updatePermission(function () {
                if (pageVm.formState === "edit") {
                    setDefaultPermission();
                }
                avalon.scan(pageEl[0],[pageVm]);
            });
            //avalon.scan(pageEl[0],[pageVm]);
            /**
             * 更新权限树
             * @param {Type} callback
             */
            function updatePermission(callback) {
                util.c2s({
                    "url": erp.BASE_PATH + 'role/initTree.do',
                    //"url": erp.ORIGIN_PATH + 'data/tree.json',
                    "success": function (responseData) {
                        var data,
                            htmlStr = '';
                        function createDeepList(listData) {
                            _.each(listData, function (itemData) {
                                var itemCls = '';
                                if (!itemData.childrenList || itemData.childrenList.length === 0) {
                                    itemCls = 'leaf-item';
                                }
                                htmlStr += '<li class="permission-item '+ itemCls +'">' +
                                    '<div class="permission-title">' +
                                        '<i class="visible-h iconfont" ms-click="toggleVisible">&#xe601;</i>&nbsp;<span class="label-for"><input type="checkbox" class="check-h" value="' + itemData.id + '" ms-click="clickNode" />&nbsp;' +
                                        '<label class="permission-name">' + itemData.text + '</label></span>' +
                                    '</div>';
                                if (itemData.childrenList && itemData.childrenList.length > 0) {
                                    htmlStr += '<ul class="permission-list">';
                                    createDeepList(itemData.childrenList);
                                    htmlStr += '</ul></li>';
                                } else {
                                    htmlStr += '</li>';
                                }
                            });
                        }
                        if (responseData.flag == 1) {
                            data = responseData.data;
                            data = [].concat(data); //保证返回的是数组结构
                            createDeepList(data);   //构建deep list结构
                            permissionBoxEl.html('<ul class="permission-list">' + htmlStr + '</ul>');
                            callback && callback.call(this, data);
                        }
                    }
                });
            }
            function setDefaultPermission() {
                util.c2s({
                    "url": erp.BASE_PATH + 'role/edit.do',
                    "data": {
                        "id": routeData.params["id"]
                    },
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            _.each(data, function (itemData) {
                                $('.check-h[value="' + itemData + '"]', permissionBoxEl).prop('checked', true);
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
            var formId = pageName + '-form';
            $(avalon.getVm(formId).widgetElement).remove(); //表单widget删除
        }
    });

    return pageMod;
});
