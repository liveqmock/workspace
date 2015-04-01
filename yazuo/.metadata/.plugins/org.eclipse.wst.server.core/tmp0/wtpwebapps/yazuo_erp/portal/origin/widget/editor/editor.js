/**
 * editor组件，依赖第三方库 ueditor http://ueditor.baidu.com/
 */

//配置ueditor以amd方式加载
require.config({
    "paths": {
        "ueditorconfig": "./ueditor/ueditor.config.js",
        "ueditor": "./ueditor/ueditor.all.min.js"
    },
    "shim": {
        "ueditorconfig": {
            exports: "UEDITOR_CONFIG"
        },
        "ueditor": {
            exports: "UE"
        }
    }
});
//提前声明准备好的amd
_.extend(avalon.modules, {
    "": {    //raphael 依赖
        "state": 2
    }
});
define(["avalon", "ueditorconfig", "ueditor", "text!./editor.css"], function(avalon, UEDITOR_CONFIG, UE, cssText) {
    var styleEl = $("#widget-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="widget-style"></style>');
        styleEl.appendTo('head');
    }
    if (!styleEl.data('widgetEditor')) {
        try {
            styleEl.html(styleEl.html() + avalon.adjustWidgetCssUrl(cssText, 'editor/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += avalon.adjustWidgetCssUrl(cssText, 'editor/');
        }
        styleEl.data('widgetEditor', true);
    }
    var widget = avalon.ui.editor = function(element, data, vmodels) {
        var elEl = $(element);
        var opts = data.editorOptions;
        elEl.addClass("ui-editor");
        opts.ueditorOptions = avalon.mix(true, opts.ueditorOptions || {}, widget.defaults.ueditorOptions);
        var vmodel = avalon.define(data.editorId, function(vm) {
            var editorId = data.editorId + '-ueditor';

            avalon.mix(vm, opts);
            vm.$skipArray = ["widgetElement", "ueditorOptions", "core"];//这些属性不被监控
            vm.widgetElement = element;

            vm.$init = function() {
                //准备结构
                elEl.html('<script id="' + editorId + '" name="content" type="text/plain"></script>');
                //初始化ueditor,设置核心引用
                vm.core = UE.getEditor(editorId, vm.ueditorOptions);
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm.$remove = function() {
                //destroy ueditor
                vm.core.destroy();
                vm.core = null;
                elEl.removeClass('ui-editor').off().empty();
            };
        });
        return vmodel;
    };
    widget.version = 1.0;
    widget.defaults = {
        "ueditorOptions": {
            "autoHeight": true,
            "enableAutoSave": false,
            "zIndex": 2
        }
    };
    return avalon;
});
