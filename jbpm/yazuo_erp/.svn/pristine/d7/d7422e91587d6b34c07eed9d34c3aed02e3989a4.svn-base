/**
 * 自动完成组件
 */
define(["avalon", "text!./autocomplete.html", "text!./autocomplete.css", "../form/select"], function (avalon, sourceHTML, cssText) {
    var styleEl = $("#widget-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="widget-style"></style>');
        styleEl.appendTo('head');
    }
    if (!styleEl.data('widgetAutocomplete')) {
        try {
            styleEl.html(styleEl.html() + avalon.adjustWidgetCssUrl(cssText, 'autocomplete/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += avalon.adjustWidgetCssUrl(cssText, 'autocomplete/');
        }
        styleEl.data('widgetAutocomplete', true);
    }
    var widget = avalon.ui.autocomplete = function (element, data, vmodels) {
        var opts = data.autocompleteOptions,   //不考虑嵌套widget配置项继承的问题，可通过ms-controller解决
            selectId = data.autocompleteId + '-select',
            elEl = $(element),
            tId;

        var vmodel = avalon.define(data.autocompleteId, function (vm) {
            vm.$skipArray = ["widgetElement", "selectId"];
            avalon.mix(vm, opts);
            vm.widgetElement = element;
            vm.selectId = selectId;
            vm.$selectOpts = {
                "selectId": selectId,
                "options": [],
                "getTemplate": function () {
                    return opts.getTemplate(sourceHTML, opts);  //自定义模板，去掉输入框只读属性，下拉面板增加loading符
                },
                "placeholder": opts.placeholder,
                "onBeforeEmptyPanel": opts.onBeforeEmptyPanel,
                "onAfterScanPanel": opts.onAfterScanPanel,
                "onSelect": function (selectedValue, selectedText) {
                    vm.inputText = selectedText;
                    vm.onSelect.call(vm, selectedValue, selectedText);
                }
            };
            vm.inputText = "";  //双工绑定，替换select的selectedText绑定
            vm.state = "ready"; //ready or loading, 指示自动完成所处的状态
            vm.$init = function () {
                elEl.addClass("ui-autocomplete");
                elEl.html('<span class="ui-autocomplete-select" ms-widget="select,$,$selectOpts"></span>');
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
                //调整select宽度
                avalon.vmodels[selectId].textWidth = elEl.outerWidth() - 36 - 26 - 16 - 2;  //36是搜索icon的宽度，26是三角标icon宽度，16是headerText的padding, 2是余量
            };
            vm.$remove = function () {
                $(avalon.vmodels[selectId].widgetElement).remove();
                clearTimeout(tId);
                elEl.off();
                element.innerHTML = "";
            };
            /**
             * 根据value获取option数据
             */
            vm.getOptionByValue = function (value) {
                return avalon.vmodels[selectId].getOptionByValue(value);
            };
            //监听inputText输入变化，组织options数据
            vm.$watch('inputText', function (val) {
                vm.state = "loading";
                clearTimeout(tId);
                //判断是否和select的selectedText相同，如果一致，不发请求
                if (val.length && val === avalon.vmodels[selectId].selectedText) {
                    vm.state = "ready";
                    return;
                }
                tId = setTimeout(function () {
                    vm.onChange(_.str.trim(vm.inputText), function (options) {
                        avalon.vmodels[selectId].setOptions(options || [], -1); //默认不自动选中
                        vm.state = "ready";
                    });
                }, vm.delayTime);
            });
        });
        return vmodel;
    };
    widget.version = 1.0;
    widget.defaults = {
        getTemplate: function (str, options) {
            return str;
        },
        placeholder: "",
        delayTime: 0,    //延时变更数据时间
        onBeforeEmptyPanel: avalon.noop,    //继承自select
        onAfterScanPanel: avalon.noop,
        onChange: function (text, callback) {
            callback([]);
        },
        onSelect: avalon.noop
    };

    /* 私有函数 */
    return avalon;
});
