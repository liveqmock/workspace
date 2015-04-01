/**
 * 知识库分类结构
 * Created by q13 on 14-5-9.
 */
define(["avalon", "util", "json", "text!./knowledgecate.html", "text!./knowledgecate.css", "common"], function(avalon, util, JSON, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    try {
        styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'knowledgecate/'));
    } catch (e) {
        styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'knowledgecate/');
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

    var module = erp.module.knowledgecate = function(element, data, vmodels) {
        var opts = data.knowledgecateOptions,
            elEl = $(element),
            contextMenuEl = $('.module-knowledgecate-context-menu'),
            dialogId = data.knowledgecateId + '-dialog',
            formId = data.knowledgecateId + '-form';
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
        var vmodel = avalon.define(data.knowledgecateId, function (vm) {
            avalon.mix(vm, opts);
            vm.widgetElement = element;
            vm.nodeTextSelected = null;
            vm.actionType = '';
            vm.permission = {//权限
                knowledgeAdd: util.hasPermission('knowledge_add_182'),//添加
                knowledgeUpdate: util.hasPermission('knowledge_update_183'),//修改
                knowledgeDelete: util.hasPermission('knowledge_delete_184')//删除"
            };
            vm.addSameLevel = function () {
                var dialogVm = avalon.getVm(dialogId);
                dialogVm.title = '添加同级分类';
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
                formVm.knowledgecateName = nodeTextSelected.data('knowledgecateName');
                dialogVm.title = '重命名';
                dialogVm.open();
            };
            vm.removeNode = function () {
                var id = vm.nodeTextSelected.data('knowledgecateId');
                util.confirm({
                    "content": "确定要删除选中的分类吗？",
                    "onSubmit": function () {
                        util.c2s({
                            "url":  erp.BASE_PATH +  "knowledgeKind/delete.do",
                            "type": "post",
                            "data": JSON.stringify({
                                id: id
                            }),
                            "contentType" : 'application/json',
                            "success": function (responseData) {
                                var pNodeTextEl,
                                    nodeListEl;
                                if (responseData.flag == 1) {   //清理ui
                                    util.alert({
                                        "content": responseData.message,
                                        "iconType": "success"
                                    });
                                    //vm.forceRefresh(vm.getParentNodeText()); //强制刷新上级
                                    nodeListEl = vm.nodeTextSelected.closest('.node-list');
                                    pNodeTextEl = nodeListEl.prev('.node-text');
                                    if ($('.node-item', nodeListEl).length <= 1) {
                                        nodeListEl.remove();
                                        pNodeTextEl.addClass('leaf-node-text');
                                    } else {
                                        vm.nodeTextSelected.closest('.node-item').remove();
                                    }
                                    vm.nodeTextSelected = null;
                                    vm.adjustWidth();
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
                tempHtmlStr = tempHtmlStr.replace('MS_VAR_NODE_ID', nodeData.id);
                tempHtmlStr = tempHtmlStr.replace('MS_VAR_NODE_NAME', nodeData.groupName);
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
                    formVm.knowledgecateName = '';
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
                            url = erp.BASE_PATH +  "knowledgeKind/save.do";
                            _.extend(requestData, {
                                "parentId": vm.getParentNodeText().data('knowledgecateId') //父节点id
                            });
                        } else if (actionType == 'addNextLevel'){    //添加子级
                            url = erp.BASE_PATH + "knowledgeKind/save.do";
                            _.extend(requestData, {
                                "parentId": vm.nodeTextSelected.data('knowledgecateId') //当前节点id
                            });
                        } else if (actionType == 'renameNode') {    //重命名
                            url = erp.BASE_PATH +  "knowledgeKind/update.do";
                            _.extend(requestData, {
                                "id": vm.nodeTextSelected.data('knowledgecateId'), //当前节点id
                                "parentId": vm.getParentNodeText().data('knowledgecateId') //父节点id
                            });
                        }
                        //一级分类parentId处理
                        if (requestData.parentId === -1) {
                            requestData.parentId = null;
                        }
                        //发送后台请求
                        util.c2s({
                            //"url": erp.ORIGIN_PATH + 'data/success.json',
                            "url": url,
                            "data": JSON.stringify(requestData),
                            "contentType" : 'application/json',
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag == 1) {
                                    data = responseData.data;
                                    if (actionType == 'addSameLevel') {   //添加同级
                                        vm.appendNode(vm.nodeTextSelected.closest('.node-list').prev('.node-text'), {
                                            "id": data.id,
                                            "groupName": data.title
                                        });
                                    } else if (actionType == 'addNextLevel'){    //添加子级
                                        vm.appendNode(vm.nodeTextSelected, {
                                            "id": data.id,
                                            "groupName": data.title
                                        });
                                    } else if (actionType == 'renameNode') {    //重命名
                                        $('.text-name', vm.nodeTextSelected).text(data.title);
                                        vm.nodeTextSelected.data('knowledgecateName', data.title);
                                        //调整宽度
                                        vm.adjustWidth();
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
                    "selector": ".knowledgecate-name",
                    "name": "title",
                    "rule": ["noempty", function (val, rs) {
                        if (rs[0] !== true) {
                            return "组织名称不能为空";
                        } else {
                            return true;
                        }
                    }]
                }],
                "knowledgecateName": ""
                //"knowledgecateId": ""
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
                        "top": evt.pageY + 'px',
                        //"top": nodeTextOffset.top + 'px',
                        "left": evt.pageX + 12 + 'px'
                        //"left": nodeTextOffset.left + nodeTextWidth + 'px'
                    });
                    //设置选中node
                    vm.nodeTextSelected = nodeTextEl;
                    //当是根节点时，添加同级不显示, 不可重命名, 本身亦不可删除
                    if (nodeTextEl.hasClass('root-node-text')) {
                        $('.add-same-level-item', contextMenuEl).hide();
                        $('.rename-node-item', contextMenuEl).hide();
                        $('.remove-node-item', contextMenuEl).hide();
                    } else {
                        $('.add-same-level-item', contextMenuEl).show();
                        $('.rename-node-item', contextMenuEl).show();
                        //当是叶子节点时，才可以删除
                        if (nodeTextEl.hasClass('leaf-node-text')) {
                            $('.remove-node-item', contextMenuEl).show();
                        } else {
                            $('.remove-node-item', contextMenuEl).hide();
                        }
                    }
                    //扫描一遍
                    avalon.scan(contextMenuEl[0], [vmodel]);
                    contextMenuEl.show();
                    evt.stopPropagation();
                }
            };
            vm.redraw = function () {
                var createRootNode = function (rootData, isRoot) {
                    var htmlStr = nodeTmpl.replace('MS_VAR_GROUP_NAME', rootData.title),
                        nodeTextCls = '';
                    if (_.isUndefined(isRoot)) {
                        nodeTextCls = 'root-node-text';
                    }
                    // if (rootData.isLeaf) {
                    //     nodeTextCls = 'leaf-node-text';
                    // }
                    if (rootData.children && rootData.children.length > 0) {    //如果有子集，表示处于展开状态
                        nodeTextCls += ' state-expend';
                    } else {
                        nodeTextCls += ' state-contract';   //表示处于收起状态
                        nodeTextCls += ' leaf-node-text';
                    }
                    htmlStr = htmlStr.replace('MS_VAR_NODE_TEXT_CLS', nodeTextCls);
                    htmlStr = htmlStr.replace('MS_VAR_NODE_ID', rootData.id);
                    htmlStr = htmlStr.replace('MS_VAR_NODE_NAME', rootData.title);
                    if (rootData.children && rootData.children.length > 0) {
                        htmlStr += '<ul class="node-list fn-left">';
                        _.each(rootData.children, function (itemData, i) {
                            var nodeItemCls = "";
                            // if (i === 0) {
                            //     nodeItemCls = "first-node-item ";
                            // } 
                            // if (i === (rootData.children.length - 1)) {
                            //     nodeItemCls += "last-node-item ";
                            // }
                            htmlStr += '<li class="' + nodeItemCls + 'node-item fn-clear">' + createRootNode(itemData, false) + '</li>';
                        });
                        htmlStr += '</ul>';
                    }
                    return htmlStr;
                };
                elEl.html(elTmpl);
                util.c2s({
                    //"url": erp.ORIGIN_PATH + 'data/group.json',
                    "url": erp.BASE_PATH +  "knowledgeKind/getAll.do",
                    "contentType" : 'application/json',
                    "success": function (responseData) {
                        //alert(JSON.stringify(responseData));
                        var treeData, rootData;
                        if (responseData.flag) {
                            treeData = vm.a2t(responseData.data);
                            rootData = {
                                "id": -1,
                                "parentId": -1,
                                "title": "知识分类",
                                "ancestors": "-1",
                                "children": treeData
                            };
                            $('.node-tree', elEl).html(createRootNode(rootData));
                            //重新扫描
                            avalon.scan(element, [vmodel].concat(vmodels));
                            //调整宽度
                            vmodel.adjustWidth();
                        }
                    }
                }, {
                    "mask": false
                });
            };
            vm.a2t = function (arrayData) {
                var i,
                    j;
                var move = function (cn, pn, isFind) {
                    if (cn.parentId == pn.id) {
                        pn.children = pn.children || [];
                        pn.children.push(cn);
                        return true;
                    } else {
                        if (pn.children && pn.children.length) {
                            return _.some(pn.children, function (itemData) {
                                return move(cn, itemData);
                            });
                        }
                    }
                    return false;
                }; 
                for (i = 0; i < arrayData.length; i++) {
                    for (j = 0; j < arrayData.length; j++) {
                        if (i != j) {
                            if (move(arrayData[i], arrayData[j])) {
                                arrayData.splice(i, 1);
                                i--;
                                break;
                            }
                        }
                    }
                }
                return arrayData;
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
                    nextListEl.show();
                    meEl.html('&#xe601;');
                    nodeTextEl.removeClass('state-contract').addClass('state-expend');
                    //调整宽度
                    vm.adjustWidth();
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
                elEl.addClass('module-knowledgecate');
                //重画
                vm.redraw();
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm.$remove = function() {
                var dialogId = data.knowledgecateId + '-dialog',
                    formId = data.knowledgecateId + '-form';
                $(avalon.getVm(formId).widgetElement).remove();
                $(avalon.getVm(dialogId).widgetElement).remove();
                element.oncontextmenu = null;
                elEl.off().empty();
                contextMenuEl.off().remove();
            };
            vm.$skipArray = ["widgetElement", "nodeTextSelected"];

        });
        return vmodel;
    };
    module.version = 1.0;
    module.defaults = {};
    return avalon;
});
