/**
 * 登录用户信息挂件
 * Created by q13 on 14-5-9.
 */
define(["avalon", "util", "json", "text!./organization.html", "text!./organization.css", "common"], function(avalon, util, JSON, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    try {
        styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'organization/'));
    } catch (e) {
        styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'organization/');
    }
    var elTmpl,
        nodeTmpl,
        contextMenuTmpl,
        tempTmpl;
    tmpl = tmpl.split('MS_OPTION_MODULE');
    elTmpl = tmpl[0];
    tempTmpl = tmpl[1].split('MS_OPTION_CONTEXT_MENU');
    nodeTmpl = tempTmpl[0];
    contextMenuTmpl = tempTmpl[1];

    var module = erp.module.organization = function(element, data, vmodels) {
        var opts = data.organizationOptions,
            elEl = $(element),
            contextMenuEl = $('.organization-context-menu'),
            dialogId = data.organizationId + '-dialog',
            formId = data.organizationId + '-form';
        if (contextMenuEl.length === 0) {
            contextMenuEl = $(contextMenuTmpl);
            contextMenuEl.appendTo('body');
            contextMenuEl.on('click', '.context-item', function (evt) {
                var meEl = $(this),
                    actionType = meEl.data('actionType');
                vmodel.actionType = actionType;
                vmodel[actionType]();
                contextMenuEl.hide();
            }).on('mousedown', function (evt) {
                evt.stopPropagation();
            });
            $('body').on('mousedown', function () {
                contextMenuEl.hide();
            });
        }
        //禁用右键菜单
        element.oncontextmenu = function () {
            return false;
        };
        //var gPanel; //画图面板
        var vmodel = avalon.define(data.organizationId, function (vm) {
            avalon.mix(vm, opts);
            vm.widgetElement = element;
            vm.nodeTextSelected = null;
            vm.actionType = '';

            vm.addSameLevel = function () {
                var dialogVm = avalon.getVm(dialogId);
                dialogVm.title = '添加同级部门';
                dialogVm.open();
            };
            vm.addNextLevel = function () {
                var dialogVm = avalon.getVm(dialogId);
                dialogVm.title = '添加子级部门';
                dialogVm.open();
            };
            vm.renameNode = function () {
                var dialogVm = avalon.getVm(dialogId),
                    formVm = avalon.getVm(formId),
                    nodeTextSelected = vm.nodeTextSelected;
                formVm.organizationName = nodeTextSelected.data('organizationName');
                dialogVm.title = '重命名';
                dialogVm.open();
            };
            vm.removeNode = function () {
                var id = vm.nodeTextSelected.data('organizationId');
                util.confirm({
                    "content": "确定要删除选中的部门吗？",
                    "onSubmit": function () {
                        util.c2s({
                            "url":  erp.BASE_PATH +  "group/deleteGroup/" + id + ".do",
                            "type": "get",
                            "success": function (responseData) {
                                if (responseData.flag == 1) {   //清理ui
                                    util.alert({
                                        "content": responseData.message,
                                        "iconType": "success"
                                    });
                                    vm.forceRefresh(vm.getParentNodeText()); //强制刷新上级
                                }
                            }
                        });
                    }
                });
            };
            vm.appendNode = function (nodeTextSelector, nodeData) {
                var nodeTextEl = $(nodeTextSelector),
                    nextListEl = nodeTextEl.next('.node-list'),
                    tempHtmlStr;
                if (nextListEl.length === 0) {
                    nextListEl = $('<ul class="node-list fn-left"></ul>');
                    nextListEl.insertAfter(nodeTextEl);
                }
                tempHtmlStr = nodeTmpl.replace('MS_VAR_GROUP_NAME', nodeData.groupName);
                tempHtmlStr = tempHtmlStr.replace('MS_VAR_NODE_TEXT_CLS', 'state-contract leaf-node-text');
                tempHtmlStr = tempHtmlStr.replace('MS_VAR_ORGANIZATION_ID', nodeData.id);
                tempHtmlStr = tempHtmlStr.replace('MS_VAR_ORGANIZATION_NAME', nodeData.groupName);
                nextListEl.append('<li class="node-item fn-clear">' + tempHtmlStr + '</li>');

                if (nodeTextEl.hasClass('state-contract')) {
                    $('.visible-h', nodeTextEl).html('&#xe601;');
                    nodeTextEl.removeClass('state-contract').addClass('state-expend');
                }
                nodeTextEl.removeClass('leaf-node-text');
                 //重新扫描
                avalon.scan(nextListEl[0], [vmodel].concat(vmodels));
                //调整宽度
                vm.adjustWidth();
            };
            vm.$dialogOpts = {
                "dialogId": dialogId,
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
                    var formVm = avalon.getVm(formId);
                    formVm.organizationName = '';
                    formVm.reset();
                },
                "submit": function (evt) {
                    var requestData,
                        url,
                        dialogVm = avalon.getVm(dialogId),
                        formVm = avalon.getVm(formId),
                        actionType = vm.actionType;
                    if (formVm.validate()) {
                        requestData = formVm.getFormData();
                        //编辑态附加参数
                        if (actionType == 'addSameLevel') {   //添加同级
                            url = erp.BASE_PATH +  "group/addSameLevel.do";
                            _.extend(requestData, {
                                "id": vm.getParentNodeText().data('organizationId') //父节点id
                            });
                        } else if (actionType == 'addNextLevel'){    //添加子级
                            url = erp.BASE_PATH + "group/addNextLevel.do";
                            _.extend(requestData, {
                                "id": vm.nodeTextSelected.data('organizationId') //当前节点id
                            });
                        } else if (actionType == 'renameNode') {    //重命名
                            url = erp.BASE_PATH +  "group/renameGroup.do";
                            _.extend(requestData, {
                                "id": vm.nodeTextSelected.data('organizationId') //当前节点id
                            });
                        }
                        //发送后台请求
                        util.c2s({
                            //"url": erp.ORIGIN_PATH + 'data/success.json',
                            "url": url,
                            "data": JSON.stringify(requestData),
                            "contentType" : 'application/json',
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    if (actionType == 'addSameLevel') {   //添加同级
                                        vm.appendNode(vm.nodeTextSelected.closest('.node-list').prev('.node-text'), {
                                            "id": responseData["data"],
                                            "groupName": requestData["groupName"]
                                        });
                                    } else if (actionType == 'addNextLevel'){    //添加子级
                                        vm.forceRefresh();
                                    } else if (actionType == 'renameNode') {    //重命名
                                        $('.text-name', vm.nodeTextSelected).text(requestData["groupName"]);
                                        vm.nodeTextSelected.data('organizationName', requestData["groupName"]);
                                    }
                                    //关闭弹框
                                    dialogVm.close();
                                }
                            }
                        });
                    }
                }
            };
            vm.$formOpts = {
                "formId": formId,
                "field": [{
                    "selector": ".organization-name",
                    "name": "groupName",
                    "rule": ["noempty", function (val, rs) {
                        if (rs[0] !== true) {
                            return "组织名称不能为空";
                        } else {
                            return true;
                        }
                    }]
                }, {
                    "selector": ".organization-id",
                    "name": "id"
                }],
                "organizationName": ""
                //"organizationId": ""
            };
            vm.showContextMenu = function (evt) {
                var nodeTextEl = $(this),
                    nodeTextWidth,
                    nodeTextOffset;
                if (evt.which === 3) {  //右键
                    //调整右键菜单位置
                    nodeTextWidth = nodeTextEl.outerWidth();
                    nodeTextOffset = nodeTextEl.offset();
                    contextMenuEl.css({
                        "top": nodeTextOffset.top + 'px',
                        "left": nodeTextOffset.left + nodeTextWidth + 'px'
                    });
                    //设置选中node
                    vm.nodeTextSelected = nodeTextEl;
                    //当是根节点时，添加同级不显示
                    if (nodeTextEl.hasClass('root-node-text')) {
                        $('.add-same-level-item', contextMenuEl).hide();
                    } else {
                        $('.add-same-level-item', contextMenuEl).show();
                    }
                    //当是叶子节点时，才可以删除
                    if (nodeTextEl.hasClass('leaf-node-text')) {
                        $('.remove-node-item', contextMenuEl).show();
                    } else {
                        $('.remove-node-item', contextMenuEl).hide();
                    }
                    contextMenuEl.show();
                    evt.stopPropagation();
                }
            };
            vm.redraw = function () {
                var createRootNode = function (rootData, isRoot) {
                    var htmlStr = nodeTmpl.replace('MS_VAR_GROUP_NAME', rootData.groupName),
                        nodeTextCls = '';
                    if (_.isUndefined(isRoot)) {
                        nodeTextCls = 'root-node-text';
                    }
                    if (rootData.isLeaf) {
                        nodeTextCls = 'leaf-node-text';
                    }
                    if (rootData.children && rootData.children.length > 0) {    //如果有子集，表示处于展开状态
                        nodeTextCls += ' state-expend';
                    } else {
                        nodeTextCls += ' state-contract';   //表示处于收起状态
                    }
                    htmlStr = htmlStr.replace('MS_VAR_NODE_TEXT_CLS', nodeTextCls);
                    htmlStr = htmlStr.replace('MS_VAR_ORGANIZATION_ID', rootData.id);
                    htmlStr = htmlStr.replace('MS_VAR_ORGANIZATION_NAME', rootData.groupName);
                    if (rootData.children && rootData.children.length > 0) {
                        htmlStr += '<ul class="node-list fn-left">';
                        _.each(rootData.children, function (itemData) {
                            htmlStr += '<li class="node-item fn-clear">' + createRootNode(itemData, false) + '</li>';
                        });
                        htmlStr += '</ul>';
                    }
                    return htmlStr;
                };
                elEl.html(elTmpl);
                util.c2s({
                    //"url": erp.ORIGIN_PATH + 'data/group.json',
                    "url": erp.BASE_PATH +  "group/getSysGroupsByParentId.do",
                    "data": JSON.stringify({parentId: 1, groupName: "总裁办"}),
                    "contentType" : 'application/json',
                    "success": function (responseData) {
                        //alert(JSON.stringify(responseData));
                        if (responseData.flag) {
                            $('.node-tree', elEl).html(createRootNode(responseData.data));
                            //重新扫描
                            avalon.scan(element, [vmodel].concat(vmodels));
                        }
                    }
                }, {
                    "mask": false
                });
            };
            /**
             * 获取上一级父节点
             */
            vm.getParentNodeText = function (nodeTextSelector) {
                var nodeTextEl = nodeTextSelector? $(nodeTextSelector) : vm.nodeTextSelected; //没传值默认刷新当前节点
                return nodeTextEl.closest('.node-list').prev('.node-text');
            };
            /**
             * 强制刷新节点
             */
            vm.forceRefresh = function (nodeTextSelector) {
                var nodeTextEl = nodeTextSelector? $(nodeTextSelector) : vm.nodeTextSelected; //没传值默认刷新当前节点
                if (nodeTextEl.length) {
                    nodeTextEl.removeClass('state-expend').removeClass('leaf-node-text').addClass('state-contract');
                    nodeTextEl.next('.node-list').remove();
                    $('.visible-h', nodeTextEl).click();    //手动触发点击事件
                }
            };
            vm.clickNode = function (evt) {
                var meEl = $(this),
                    nodeTextEl = meEl.closest('.node-text'),
                    nextListEl = nodeTextEl.next('.node-list');
                if (nodeTextEl.hasClass('state-contract')) {
                    if (nextListEl.length === 0) {
                        nextListEl = $('<ul class="node-list fn-left"></ul>');
                        nextListEl.insertAfter(nodeTextEl);

                        var requestData = {
                            parentId: avalon(nodeTextEl[0]).data('organizationId'),
                            groupName: $('.text-name', nodeTextEl).text()
                        };
                        util.c2s({
                           // "url": erp.ORIGIN_PATH + 'data/group-list.json',
                            "url": erp.BASE_PATH +  "group/getSysGroupsByParentId.do",
                            "data": JSON.stringify(requestData),
                            "contentType" : 'application/json',
                            "success": function (responseData) {
                                var htmlStr = '',
                                    rows;
                                if (responseData.flag) {
                                    rows = responseData.data.children;
                                    if (rows.length) {
                                        _.each(rows, function (itemData) {
                                            var tempHtmlStr = '',
                                                nodeTextCls = 'state-contract';
                                            if (itemData.isLeaf) {
                                                nodeTextCls += ' leaf-node-text';
                                            }
                                            tempHtmlStr = nodeTmpl.replace('MS_VAR_GROUP_NAME', itemData.groupName);
                                            tempHtmlStr = tempHtmlStr.replace('MS_VAR_NODE_TEXT_CLS', nodeTextCls);
                                            tempHtmlStr = tempHtmlStr.replace('MS_VAR_ORGANIZATION_ID', itemData.id);
                                            tempHtmlStr = tempHtmlStr.replace('MS_VAR_ORGANIZATION_NAME', itemData.groupName);
                                            htmlStr += '<li class="node-item fn-clear">' + tempHtmlStr + '</li>';
                                        });
                                        nextListEl.html(htmlStr);
                                        //重新扫描
                                        avalon.scan(nextListEl[0], [vmodel].concat(vmodels));
                                    } else {
                                        //无子节点，设置父节点为叶子节点
                                        nodeTextEl.addClass('leaf-node-text');
                                    }
                                    //调整宽度
                                    vm.adjustWidth();
                                }
                            }
                        }, {
                            "mask": false
                        });
                    } else {
                        nextListEl.show();
                        //调整宽度
                        vm.adjustWidth();
                    }
                    meEl.html('&#xe601;');
                    nodeTextEl.removeClass('state-contract').addClass('state-expend');
                } else {
                    nextListEl.hide();
                    meEl.html('&#xe600;');
                    nodeTextEl.removeClass('state-expend').addClass('state-contract');
                    //调整宽度
                    vm.adjustWidth();
                }

            };
            vm.adjustWidth = function () {
                var nodeTreeEl = $('.node-tree', elEl),
                    rootNodeTextEl = nodeTreeEl.children('.root-node-text'),
                    nodeListEl = nodeTreeEl.children('.node-list');
                nodeTreeEl.width(10000);    //先设置一个极大值，再计算实际宽度
                nodeTreeEl.width(rootNodeTextEl.outerWidth() + nodeListEl.outerWidth(true));
            };
            vm.$init = function() {
                elEl.addClass('module-organization');
                //重画
                vm.redraw();
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm.$remove = function() {

            };
            vm.$skipArray = ["widgetElement", "nodeTextSelected"];

        });
        return vmodel;
    };
    module.version = 1.0;
    module.defaults = {};
    return avalon;
});
