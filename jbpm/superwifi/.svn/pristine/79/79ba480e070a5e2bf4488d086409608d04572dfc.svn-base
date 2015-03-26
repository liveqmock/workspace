/**
 * 售卡信息管理
 */
define(['avalon', 'util', 'json', '../../../widget/form/form', '../../../widget/grid/grid', '../../../widget/dialog/dialog'], function (avalon, util, JSON) {
    var win = this,
        app = win.app,
        pageMod = {};
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName) {
            var gridId = pageName + '-grid',
                merchantId = 15,
                brandId = 15,
                macAddEditDialogId = pageName + '-mac-add-edit-dialog';
            var pageVm = avalon.define(pageName, function (vm) {
                vm.navCrumbs = [
                    {
                        "text": "超级WIFI后台",
                        "href": "#/welcome"
                    },
                    {
                        "text": "门店MAC码管理"
                    }
                ];
                vm.macList = [];
                vm.submitMac = [];
                vm.keyword = "";
                vm.merchantName = "";
                vm.mId = "";
                vm.$macAddEditDialogOpts = {
                    "dialogId": macAddEditDialogId,
                    "title": "MAC码管理",
                    "width": 530,
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
                        pageVm.merchantName = '';
                        pageVm.macList = [];
                    },
                    "submit": function () {
                        var flag = validateInput($(".page-mac-add-edit-dialog")),
                            dialogVm = avalon.getVm(macAddEditDialogId),
                            gridVm = avalon.getVm(gridId);
                        if (flag) {
                            util.c2s({
                                "url": app.BASE_PATH + "controller/device/saveOrUpdateMac.do",
                                "data": JSON.stringify({merchantId: pageVm.mId, brandId: brandId, mac: pageVm.submitMac.$model}),
                                "contentType": 'application/json',
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        util.alert({
                                            "iconType": "info",
                                            "content": "修改成功！",
                                            "onSubmit": function () {
                                                dialogVm.close();
                                                gridVm.load();
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                };
                vm.$gridOpts = {
                    "gridId": gridId,
                    "disableCheckAll": true,   //开启全选模式
                    "disableCheckbox": true,
                    "recordUnit": "",
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
                            '<span>共<ins class="num">{{gridTotalSize}}</ins>{{recordUnit}}家门店</span>' +
                            '</h2>' + tmplMain + '<div class="ui-grid-bbar fn-clear">' +
                            '<div class="grid-action fn-left">' +
                            '</div>' +
                            '<div class="pagination fn-right"' +
                            ' ms-widget="pagination,$,$paginationOpts"></div>' +
                            '</div>';
                    },
                    "columns": [
                        {
                            "dataIndex": "merchantName",
                            "text": "门店名称"
                        },
                        {
                            "dataIndex": "mac",
                            "text": "设备MAC码",
                            "html": true,
                            "renderer": function (v, rowData) {
                                var str = "";
                                _.each(rowData.mac, function (itemData) {
                                    str = str + '<div>' + (itemData.mac == "" ? "xx-xx-xx-xx-xx-xx" : itemData.mac) + '</div>';
                                });
                                return str;
                            }
                        },
                        {
                            "dataIndex": "operation",
                            "text": "操作",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<span class="format-a">' +
                                    '<a href="javascript:;" ms-click="openMacDialog($outer.el)">管理MAC码</a>' +
                                    '</span>';
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        _.extend(requestData, {
                            "merchantName": _.str.trim(vm.keyword),  // 搜索关键字
                            "brandId": brandId,  //集才ID
                            "merchantId": merchantId, //商户ID
                            "isFaceShop": false
                        });
                        util.c2s({
                            "url": app.BASE_PATH + "controller/device/manageMac.do",
                            "data": JSON.stringify(requestData),
                            "contentType": 'application/json',
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    _.each(data.rows, function (item) {
                                        if (item.mac.length == 0) {
                                            item.mac.push({'id': '', 'mac': ''});
                                        }
                                    });
                                    callback.call(this, data);
                                }
                            }
                        });
                    }
                };
                vm.query = function () {
                    avalon.getVm(gridId).load();
                };
                //修改添加MAC
                vm.openMacDialog = function (el) {
                    var dialogVm = avalon.getVm(macAddEditDialogId);
                    pageVm.merchantName = el.merchantName;
                    pageVm.macList = el.mac;
                    pageVm.mId = el.merchantId;
                    dialogVm.open();
                };
                //增加MAC
                vm.addMac = function () {
                    var obj = {"id": "", "mac": ""};
                    pageVm.macList.push(obj);
                };
                //删除MAC
                vm.delMac = function (index) {
                    var dataId = $(this).data("id");
                    if (dataId != "") {
                        util.confirm({
                            "iconType": "ask",
                            "content": "您确定要删除此MAC吗？",
                            "onSubmit": function () {
                                util.c2s({
                                    "url": app.BASE_PATH + "controller/device/deteleMacById.do",
                                    "data": JSON.stringify({id: dataId}),
                                    "contentType": 'application/json',
                                    "success": function (responseData) {
                                        if (responseData.flag) {
                                            util.alert({
                                                "iconType": "info",
                                                "content": "删除成功！"
                                            });
                                            avalon.getVm(gridId).load();
                                            pageVm.macList.splice(index, 1);
                                        }
                                    }
                                });
                            }
                        });
                    } else {
                        pageVm.macList.splice(index, 1);
                    }

                };
            });
            avalon.scan(pageEl[0]);
            //加载grid
            avalon.getVm(gridId).load();
            //弹层input动态验证
            function validateInput(sel) {
                var inputList = sel.find("input"), flag = true;
                pageVm.submitMac = [];
                inputList.each(function (i, item) {
                    var value = $(this).val();
                    if ($.trim(value) == "") {
                        flag = false;
                        $(this).addClass("border-red");
                        $(this).closest(".ff-value").find(".ff-value-tip").html("不能为空！").show();
                    } else if (!value.match(/([0-9a-fA-F]{2}-){5}[0-9a-fA-F]{2}$/)) {
                        flag = false;
                        $(this).addClass("border-red");
                        $(this).closest(".ff-value").find(".ff-value-tip").html("格式不正确！").show();
                    } else {
                        pageVm.submitMac.push({'id': $(this).data('id'), 'mac': $(this).val()})
                    }
                });
                return flag;
            }

            //input绑定失去焦点事件
            $(".page-mac-add-edit-dialog").on("change", "input", function () {
                var value = $(this).val();
                if ($.trim(value) == "") {
                    $(this).addClass("border-red");
                    $(this).closest(".ff-value").find(".ff-value-tip").html("不能为空！").show();
                } else if (!value.match(/([0-9a-fA-F]{2}-){5}[0-9a-fA-F]{2}$/)) {
                    $(this).addClass("border-red");
                    $(this).closest(".ff-value").find(".ff-value-tip").html("格式不正确！").show();
                } else {
                    $(this).removeClass("border-red");
                    $(this).closest(".ff-value").find(".ff-value-tip").html("").hide();
                }
            });
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var gridId = pageName + '-grid',
                macAddEditDialogId = pageName + '-card-add-edit-dialog';
            $(avalon.getVm(macAddEditDialogId).widgetElement).remove();
            $(avalon.getVm(gridId).widgetElement).remove();
        }
    });

    return pageMod;
});
