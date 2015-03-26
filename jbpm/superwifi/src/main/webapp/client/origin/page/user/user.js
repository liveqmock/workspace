/**
 * 用户管理
 */
define(['avalon', 'util', 'json', 'moment', '../../module/merchantdomain/merchantdomain', '../../widget/dialog/dialog', '../../widget/form/form', '../../widget/grid/grid'], function (avalon, util, JSON, moment) {
    var win = this,
        app = win.app,
        pageMod = {};
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName, routeData) {
            var addUserDialogId = pageName + '-add-user-dialog',
                gridId = pageName + '-grid',
                merchantdomainId = pageName + '-merchantdomain',
                userFormId = pageName + '-user-form';
            var loginUserData = app.getAppData("user");
            var pageVm = avalon.define(pageName, function (vm) {
                vm.keyword = "";
                vm.operType = "add";
                vm.authorityList = [];
                vm.$merchantdomainOpts = {
                    "merchantdomainId": merchantdomainId,
                    "autoAdjustMerchantChange": false   //取消自动调整商户设置
                };
                vm.$gridOpts = {
                    "gridId": gridId,
                    "disableCheckAll": false,   //开启全选模式
                    "recordUnit": "个用户",
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_TBAR'),
                            tmplTbar,
                            tmplMain,
                            tmplBbar;
                        tmplTbar = tmplTemp[0];
                        tmplTemp = tmplTemp[1].split("MS_OPTION_MAIN");
                        tmplMain = tmplTemp[0];
                        tmplBbar = tmplTemp[1];
                        return '<h2 class="page-h2">' +
                            '<span>共有<ins class="num">{{gridTotalSize}}</ins>{{recordUnit}}</span>' +
                            '<a href="javascript:;" class="add-btn main-btn-green" ms-click="openAddUser" data-type="add">添加用户</a>' +
                            '</h2>' + tmplMain + '<div class="ui-grid-bbar fn-clear">' +
                            '<div class="grid-action fn-left">' +
                            '<button type="button" class="check-all-btn main-btn"' +
                            ' ms-visible="!disableCheckAll"' +
                            ' ms-click="scCheckAll">全选</button>' +
                            '&nbsp;' +
                            '<button type="button" class="approve-btn main-btn"' +
                            ' ms-click="deleteUser">解除权限</button>' +
                            '&nbsp;' +
                            '</div>' +
                            '<div class="pagination fn-right"' +
                            ' ms-widget="pagination,$,$paginationOpts"></div>' +
                            '</div>';
                    },
                    "columns": [
                        {
                            "dataIndex": "userInfoName",
                            "text": "姓名"
                        },
                        {
                            "dataIndex": "mobile",
                            "text": "手机号"
                        },
                        {
                            "dataIndex": "merchantName",
                            "text": "所属范围"
                        },
                        {
                            "dataIndex": "operation",
                            "text": "操作",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<span class="format-a">' +
                                    '<a href="javascript:;" ms-click="openAddUser" data-type="edit" data-id="' + rowData.id + '">修改</a>' +
                                    '<a href="javascript:;" ms-click="deleteUser" ms-visible="' + (rowData.authority_count > 0) + '" data-id="' + rowData.id + '">解除权限</a>' +
                                    '</span>';
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        _.extend(requestData, {
                            "userInfoName": $.trim(pageVm.keyword)
                        });
                        util.c2s({
                            "url": app.BASE_PATH + "controller/user/listUsers.do",
                            "data": requestData,
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    callback.call(this, data);
                                }
                            }
                        });
                    },
                    "deleteUser": function () {
                        var gridVm = avalon.getVm(gridId);
                        if ($(this).data("id")) {
                            gridVm.unselectById('all');
                            gridVm.selectById($(this).data("id"));
                        }
                        if (gridVm.getSelectedData().length > 0) {
                            var idData = _.map(gridVm.getSelectedData(), function (itemData) {
                                return itemData.userInfoId;
                            });
                            util.confirm({
                                "iconType": "ask",
                                "content": "<p style='line-height: 25px'>解除权限后，该用户将无法继续登陆并使用超级WIFI产品您确定要解除用户权限吗？</p>",
                                "onSubmit": function () {
                                    util.c2s({
                                        "url": app.BASE_PATH + "controller/user/deleteUser.do",
                                        "data": {"userIdList": idData},
                                        "success": function (responseData) {
                                            if (responseData.flag) {
                                                util.alert({
                                                    "iconType": "info",
                                                    "content": "<p style='line-height: 25px'>用户权限已解除，该用户无法再登录并使用雅座回头宝。</p>",
                                                    "onSubmit":function(){
                                                        gridVm.load();
                                                    }
                                                });

                                            }
                                        }
                                    });
                                },
                                "onCancel": function () {
                                    gridVm.unselectById('all');
                                }
                            });
                        } else {
                            util.alert({
                                "iconType": "info",
                                "content": "请选择用户！"
                            });
                        }
                    }
                };
                //添加修改用户dialog
                vm.$addUserDialogOpts = {
                    "dialogId": addUserDialogId,
                    "width": 500,
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
                        //清除验证信息
                        var formVm = avalon.getVm(userFormId),
                            merchantdomainVm = avalon.getVm(merchantdomainId);
                        formVm.reset();
                        formVm.userInfoName = "";
                        formVm.mobile = "";
                        avalon.getVm(gridId).unselectById('all');
                        setAuthority("remove");
                        merchantdomainVm.clear();
                        merchantdomainVm.setDomainData(loginUserData.merchantId, loginUserData.merchantName);
                    },
                    "submit": function () {
                        var requestData,
                            dialogVm = avalon.getVm(addUserDialogId),
                            formVm = avalon.getVm(userFormId),
                            gridVm = avalon.getVm(gridId);
                        if (formVm.validate()) {
                            requestData = formVm.getFormData();
                            if (pageVm.operType == 'add') {
                                _.extend(requestData, {"authorityIds": getAuthority()});
                            } else {
                                _.extend(requestData, {"authorityIds": getAuthority(), "userInfoId": gridVm.getSelectedData()[0].userInfoId});
                            }
                            util.c2s({
                                "url": app.BASE_PATH + "controller/user/addOrUpdateUser.do",
                                "data": requestData,
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        avalon.getVm(gridId).load();
                                        dialogVm.close();
                                    }
                                }
                            });
                        }
                    }
                };
                //添加用户 Form
                vm.$addUserFormOpts = {
                    "formId": userFormId,
                    "field": [
                        {
                            "selector": ".user-name",
                            "name": "userInfoName",
                            "excludeHidden": true,
                            "rule": ["noempty", function (val, rs) {
                                if (rs[0] !== true) {
                                    return "用户的姓名不能为空！";
                                } else {
                                    return true;
                                }
                            }]
                        },
                        {
                            "selector": ".mobile",
                            "name": "mobile",
                            "excludeHidden": true,
                            "rule": ["noempty", function (val, rs) {
                                if (rs[0] !== true) {
                                    return "手机号码不能为空";
                                } else if (!val.match(/^[0-9]{11}$/g)) {
                                    return "请输入正确的手机号码！"
                                } else {
                                    return true;
                                }
                            }]
                        },
                        {
                            "selector": ".merchant-domain",
                            "name": "merchantId",
                            "getValue": function () {
                                return avalon.getVm(merchantdomainId).domainId;
                            },
                            "rule": function (val) {
                                if (!val) {
                                    return "用户所属范围不能为空";
                                } else {
                                    return true;
                                }
                            }
                        }
                    ],
                    "userInfoName": "",
                    "mobile": ""
                };
                //打开弹层
                vm.openAddUser = function () {
                    var dialogVm = avalon.getVm(addUserDialogId),
                        gridVm = avalon.getVm(gridId),
                        formVm = avalon.getVm(userFormId),
                        merchantdomainVm = avalon.getVm(merchantdomainId);
                    pageVm.operType = $(this).data('type');
                    if (pageVm.operType == 'add') {
                        dialogVm.title = "添加用户";
                    } else {
                        dialogVm.title = "修改用户";
                        gridVm.selectById($(this).data('id'));
                        var selectData = gridVm.getSelectedData();
                        formVm.userInfoName = selectData[0].userInfoName;
                        formVm.mobile = selectData[0].mobile;
                        getAuthorityById(selectData[0].userInfoId);
                        //设置所属商户数据
                        merchantdomainVm.setDomainData(selectData[0].merchantId, selectData[0].merchantName);
                    }
                    dialogVm.open();
                };
                vm.query = function () {
                    avalon.getVm(gridId).load();
                };
                //选择权限
                vm.checkAuthority = function () {
                    if ($(this).hasClass("checked")) {
                        $(this).removeClass("checked");
                    } else {
                        $(this).addClass("checked");
                    }
                }
            });
            avalon.scan(pageEl[0]);
            avalon.getVm(gridId).load();
            initAuthority();
            //初始化门店信息
            avalon.getVm(merchantdomainId).initDomain();
            //初始化权限列表
            function initAuthority() {
                util.c2s({
                    "url": app.BASE_PATH + "controller/resource/listResources.do",
                    "success": function (responseData) {
                        if (responseData.flag) {
                            pageVm.authorityList = responseData.data;
                        }
                    }
                });
            }

            //获取权限
            function getAuthority() {
                var arr = [];
                $(".authority-list li").each(function () {
                    if ($(this).hasClass("checked")) {
                        arr.push($(this).data("value"));
                    }
                });
                return arr;
            }

            //设置权限
            function setAuthority(arrList) {
                if (arrList == "remove") {
                    $(".authority-list li").removeClass('checked');
                } else {
                    _.each(arrList, function (item) {
                        $(".authority-list li").each(function () {
                            if ($(this).data("value") == item) {
                                $(this).addClass("checked");
                                return false;
                            }
                        });
                    });
                }
            }

            //根据ID获取用户权限
            function getAuthorityById(id) {
                util.c2s({
                    "url": app.BASE_PATH + "controller/resource/getAuthoritiesByUserId.do",
                    "data": {"userId": +id},
                    "success": function (responseData) {
                        if (responseData.flag) {
                            setAuthority(responseData.data);
                        }
                    }
                });
            }
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var addUserDialogId = pageName + '-add-user-dialog',
                gridId = pageName + '-grid',
                merchantdomainId = pageName + '-merchantdomain',
                userFormId = pageName + '-user-form';
            $(avalon.getVm(gridId).widgetElement).remove();
            $(avalon.getVm(merchantdomainId).widgetElement).remove();
            $(avalon.getVm(userFormId).widgetElement).remove();
            $(avalon.getVm(addUserDialogId).widgetElement).remove();
        }
    });

    return pageMod;
});
