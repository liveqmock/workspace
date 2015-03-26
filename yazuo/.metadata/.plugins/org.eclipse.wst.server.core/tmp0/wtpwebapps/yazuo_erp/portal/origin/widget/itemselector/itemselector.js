/**
 * 双选组件
 */
define(["avalon", "text!./itemselector.html", "text!./itemselector.css"], function (avalon, sourceHTML, cssText) {
    var styleEl = $("#widget-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="widget-style"></style>');
        styleEl.appendTo('head');
    }
    if (!styleEl.data('widgetItemselector')) {
        try {
            styleEl.html(styleEl.html() + avalon.adjustWidgetCssUrl(cssText, 'itemselector/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += avalon.adjustWidgetCssUrl(cssText, 'itemselector/');
        }
        styleEl.data('widgetItemselector', true);
    }
    var widget = avalon.ui.itemselector = function (element, data, vmodels) {
        var opts = data.itemselectorOptions,   //不考虑嵌套widget配置项继承的问题，可通过ms-controller解决
            tmpl = opts.getTemplate(sourceHTML, opts),
            elEl = $(element);

        var vmodel = avalon.define(data.itemselectorId, function (vm) {
            vm.$skipArray = ["widgetElement"];
            avalon.mix(vm, opts);
            vm.leftFilterValue = '';
            vm.$watch('leftFilterValue', function (val) {
                _.each(vm.store.$model, function (itemData, i) {
                    if (!itemData.selected) {
                        if (vm.onItemFilter(val, itemData)) {
                            vm.store.set(i, {
                                "visible": true
                            });
                        } else {
                            vm.store.set(i, {
                                "visible": false
                            });
                        }
                    }
                });
            });
            vm.rightFilterValue = '';
            vm.$watch('rightFilterValue', function (val) {
                _.each(vm.store.$model, function (itemData, i) {
                    if (itemData.selected) {
                        if (vm.onItemFilter(val, itemData)) {
                            vm.store.set(i, {
                                "visible": true
                            });
                        } else {
                            vm.store.set(i, {
                                "visible": false
                            });
                        }
                    }
                });
            });
            vm.$init = function () {
                elEl.addClass("ui-itemselector");
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm.$remove = function () {
                elEl.removeClass('ui-itemselector').off();
                element.innerHTML = "";
            };
            /**
             * 点击左item做选中操作
             */
            vm.clickLeftItem = function () {
                var atIndex = this.$vmodel.$index;
                vm.store.set(atIndex, {
                    "preSelected": false,
                    "selected": true
                });
            };
            /**
             * 右移
             */
            vm.clickMoveRight = function () {
                _.each(vm.store.$model, function (itemData, i) {
                    if (!itemData.selected && itemData.preSelected) {
                        vm.store.set(i, {
                            "selected": true,
                            "preSelected": false
                        });
                    }
                });
                //触发selectedchange自定义事件
                vm.$fire('selectedchange');
                //可能是avalon处理ms-hover有问题，导致移到右边的item都带上了item-hover className
                $('.ui-itemselector-right .ui-itemselector-item', elEl).removeClass('item-hover');
            };
            /**
             * 点击删除选中的item
             */
            vm.clickSelectedRemove = function () {
                var atIndex = this.$vmodel.$index;
                vm.store.set(atIndex, {
                    "selected": false
                });
                //触发selectedchange自定义事件
                vm.$fire('selectedchange');
            };
            /**
             * 获取选中的数据
             */
            vm.getSelectedData = function () {
                return _.filter(vm.store.$model, function (itemData) {
                    return itemData.selected;
                });
            };
        });
        return vmodel;
    };
    widget.version = 1.0;
    widget.defaults = {
        getTemplate: function (str, options) {
            return str;
        },
        showLeftTitle: true,
        showRightTitle: true,
        showLeftFilter: true,
        showRightFilter: true,
        leftTitle: '',
        rightTitle: '',
        /*store: [{
            "text": "test001",
            "visible": true,
            "selected": true,
            "preSelected": false
        },{
            "text": "test002",
            "visible": true,
            "selected": false,
            "preSelected": false
        },{
            "text": "test002",
            "visible": true,
            "selected": false,
            "preSelected": false
        }],*/
        store: [],  //text,visible,selected,preSelected都是必填项
        /**
         * 默认的过滤方式，部分匹配text
         */
        onItemFilter: function (val, itemData) {
            if (itemData.text.search(_.str.trim(val)) !== -1) {
                return true;
            }
        }
    };

    /* 私有函数 */
    return avalon;
});
