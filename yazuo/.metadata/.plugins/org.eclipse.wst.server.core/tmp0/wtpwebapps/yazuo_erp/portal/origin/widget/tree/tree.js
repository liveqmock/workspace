/**
 * tree组件，依赖第三方库 http://www.ztree.me/
 */

//配置ztree以amd方式加载
require.config({
    "paths": {
        "ztree": "./ztree/js/jquery.ztree.all-3.5.min.js"
    },
    "shim": {
        "ztree": {
            exports: function () {
                return jQuery.fn.zTree;
            }
        }
    }
});
define(["avalon", "ztree", "text!./ztree/css/zTreeStyle/zTreeStyle.css", "text!./tree.css"], function(avalon, zTree, ztreeCssText, treeCssText) {
    var styleEl = $("#widget-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="widget-style"></style>');
        styleEl.appendTo('head');
    }
    if (!styleEl.data('widgetTree')) {
        try {
            styleEl.html(styleEl.html() + avalon.adjustWidgetCssUrl(ztreeCssText, 'tree/ztree/css/zTreeStyle/') + avalon.adjustWidgetCssUrl(treeCssText, 'tree/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += avalon.adjustWidgetCssUrl(ztreeCssText, 'tree/ztree/css/zTreeStyle/') + avalon.adjustWidgetCssUrl(treeCssText, 'tree/');
        }
        styleEl.data('widgetTree', true);
    }
    var widget = avalon.ui.tree = function(element, data, vmodels) {
        var elEl = $(element);
        var opts = data.treeOptions;
        elEl.addClass("ui-tree");
        var vmodel = avalon.define(data.treeId, function(vm) {
            var ztreeId = data.treeId + '-ztree';

            avalon.mix(vm, opts);
            vm.$skipArray = ["widgetElement", "ztreeOptions", "core"];//这些属性不被监控
            vm.widgetElement = element;

            vm.$init = function() {
                //准备结构
                elEl.html('<ul id="' + ztreeId + '" class="ztree"></ul>');
                //初始化ztree
                zTree.init($('#' + ztreeId), vm.ztreeOptions.setting, vm.ztreeOptions.treeData);
                //设置核心引用
                vm.core = zTree.getZTreeObj(ztreeId);
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm.$remove = function() {
                //destroy ztree
                zTree.destroy(ztreeId);
                elEl.removeClass('ui-tree').off().empty();
            };
            /*===== 以下是对ztree的扩展 ======*/
            /**
             * 删除所有的节点
             */
            vm.removeAllNode = function (isSilent) {
                var nodes = vm.core.getNodes();
                for (var i = 0; i < nodes.length; i++) {
                    vm.core.removeNode(nodes[i], !isSilent);
                    i--;
                }
            };
            /**
             * 更新整个树，删除以前的节点，设置新节点
             */
            vm.updateTree = function (nodes, isExpand) {
                vm.removeAllNode(true);
                vm.core.addNodes(null, nodes);  //addNodes第三个参数控制展开好像有问题，手动调用expandAll进行展开
                vm.core.expandAll(isExpand);
            };
        });
        return vmodel;
    };
    widget.version = 1.0;
    widget.defaults = {
        "ztreeOptions": {
            "setting": null,
            "treeData": null
        }
    };
    return avalon;
});



