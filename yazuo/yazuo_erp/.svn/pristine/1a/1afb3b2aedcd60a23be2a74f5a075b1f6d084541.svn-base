/**
 * 用户信息的添加和编辑
 */
define(['avalon', 'util', 'json', '../../../../widget/form/form', '../../../../widget/form/select'], function (avalon, util, JSON) {
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
            var loginUserData = erp.getAppData('user');
            var pageVm = avalon.define(pageName, function (vm) {
                vm.navCrumbs = [{
                    "text": "用户管理",
                    "href": "#/sysmanage/usermanage"
                }, {
                    "text": "设置权限组"
                }];
                vm.userName = decodeURIComponent(routeData.params["userName"]);
                vm.roleList = []; //角色列表
                vm.$formOpts = {    //表单配置
                    "formId": formId,
                    "field": [{
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
                    }],
                    "action": [{
                        "selector": ".f-submit",
                        "handler": function () {
                            var requestData,
                                formVm = avalon.getVm(formId);
                            if (formVm.validate()) {
                                requestData = formVm.getFormData();
                                //数组化
                                requestData["roleIds"] = [].concat(requestData["roleIds"]);
                                //附加用户id
                                _.extend(requestData, {
                                    "id": routeData.params["id"]
                                });
                                //发送后台请求，编辑
                                util.c2s({
                                    "url": erp.BASE_PATH + 'user/updateRole.do',
                                    "type": "post",
                                    "data": requestData,
                                    "success": function (responseData) {
                                        if (responseData.flag == 1) {
                                            util.alert({
                                                "content": responseData.message,
                                                "iconType": "success",
                                                "onSubmit": function () {
                                                    if (routeData.params["id"] == loginUserData.id) {
                                                        location.reload();
                                                    } else {
                                                        util.jumpPage('#/sysmanage/usermanage');
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    }]
                };
            });
            avalon.scan(pageEl[0]);

            //请求权限数据
            updatePermission(function () {
                setDefaultPermission();
            });

            /**加载权限树列表*/
            function updatePermission (callback) {
                //权限组列表
                util.c2s({
                    "url": erp.BASE_PATH + 'role/initRole.do',
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            pageVm.roleList = _.map(data, function (itemData) {
                                return _.extend(itemData, {
                                    "isChecked": false
                                });
                            });
                            callback && callback();
                        }
                    }
                });
            }

            /**取已选中的权限组*/
            function setDefaultPermission() {
                util.c2s({
                    "url": erp.BASE_PATH + 'user/edit.do',
                    "data": {
                        "id": routeData.params["id"],
                        "editContent":"roleSetting"
                    },
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            _.each(data, function (roleId) {
                                var at = -1;
                                _.some(pageVm.roleList.$model, function (itemData, i) {
                                    if (itemData.id == roleId) {
                                        at = i;
                                        return true;
                                    }
                                });
                                pageVm.roleList.set(at, {
                                    "isChecked": true
                                });
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
