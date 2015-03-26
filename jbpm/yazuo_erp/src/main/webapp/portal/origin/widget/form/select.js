/**
 * 模拟select组件，只具备单选功能
 */
define(["avalon", "text!./form.html", "text!./form.css"], function(avalon, sourceHTML, cssText) {
    var styleEl = $("#widget-style"),
        widgetArr = sourceHTML.split("MS_OPTION_WIDGET"),
        selectTmpl = widgetArr[0],
        selectNum = 0; //保存select数量
    if (styleEl.length === 0) {
        styleEl = $('<style id="widget-style"></style>');
        styleEl.appendTo('head');
    }
    if (!styleEl.data('widgetForm')) {
        //styleEl.html(styleEl.html() + cssText);
        try {
            styleEl.html(styleEl.html() + avalon.adjustWidgetCssUrl(cssText, 'form/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += avalon.adjustWidgetCssUrl(cssText, 'form/');
        }

        styleEl.data('widgetForm', true);
    }
    var widget = avalon.ui.select = function(element, data, vmodels) {
        var opts = data.selectOptions,   //不考虑嵌套widget配置项继承的问题，可通过ms-controller解决
            template = opts.getTemplate(selectTmpl, opts),
            elEl = $(element),
            selectPanelEl = $('.ui-select-panel');
        if (selectPanelEl.length === 0) {
            selectPanelEl = $('<div class="ui-select-panel"></div>');
            selectPanelEl.appendTo('body');
        }
        selectNum++;    //每创建一个select加1

        var panelVmodel = avalon.define(data.selectId + '-panel', function (vm) {
            var options = opts.options;
            if (!options) {
                options = [];
            }
            if (_.isString(options)) {
                options = parseJSON(options);
            }
            vm.options = options;
            vm.minWidth = 0;
            vm.selectedIndex = opts.selectedIndex || 0;
            vm.selectItem = function (evt) {
                var targetEl = $(evt.target),
                    index;
                if (targetEl.is('.select-item') && !targetEl.hasClass('state-disabled')) {
                    index = avalon(evt.target).data('index');
                    vm.selectedIndex = index;
                }
                //隐藏面板容器
                //设置箭头方向向下
                vmodel._switchIconState("down");
                selectPanelEl.hide();
                //opts.onSelect.call(vm, vmodel.selectedValue, vmodel.selectedText, vm.selectedIndex);
            };
        });

        var vmodel = avalon.define(data.selectId, function (vm) {
            var options = panelVmodel.options || [],
                selectedIndex = panelVmodel.selectedIndex || 0,
                tmpl = template.split('MS_OPTION_HEADER'),
                headerTmpl = tmpl[0],
                panelTmpl = tmpl[1],
                iconMap = {
                    "up": "&#xe600;",
                    "down": "&#xe601;"
                };
            vm.panelVmodel = panelVmodel;
            vm.widgetElement = element;
            vm.selectedValue = options[selectedIndex] ? options[selectedIndex].value : '';
            vm.selectedText = options[selectedIndex] ? options[selectedIndex].text : '';
            vm.selectIconText = iconMap.down; //默认向下
            vm.isActive = false;
            vm.textWidth = "auto";
            vm.placeholder = opts.placeholder;
            vm.readonly = opts.readonly;    //只读控制
            vm.disabled = opts.disabled;    //禁用控制

            vm.$init = function() {
                elEl.addClass("ui-select-header");
                elEl.attr('ms-class-ui-select-header-active', 'isActive');
                elEl.attr('ms-class-1', 'ui-select-header-readonly: readonly');
                elEl.attr('ms-class-2', 'ui-select-header-disabled: disabled');
                elEl.html(headerTmpl);
                elEl.on('click', function (evt) {
                    if (vm.readonly || vm.disabled) {
                        return; //只读或禁用状态不响应用户操作
                    }
                    opts.onBeforeEmptyPanel.call(vm, panelVmodel);  //清空panel前
                    setTimeout(function () {
                        selectPanelEl.html(panelTmpl);
                        //设置面板最小宽度
                        panelVmodel.minWidth = elEl.width();
                        //重新扫描
                        avalon.scan(selectPanelEl[0], [panelVmodel].concat(vmodels));
                        opts.onAfterScanPanel.call(vm, panelVmodel);    //扫描panel后

                        adjustPosition(elEl, selectPanelEl);   //自适应位置
                        selectPanelEl.show();
                        //设置箭头方向向上
                        vm._switchIconState("up");
                    }, 90); //保证ie8-下onBeforeEmptyPanel清理正常结束，大概是一个ui组件的清理周期的3倍，avalon里ie8-下组件周期是30ms
                }).on('focus', '.select-header-text', function () {
                    vm.isActive = true;
                }).on('blur', '.select-header-text', function () {
                    vm.isActive = false;
                }).on('keydown', '.select-header-text', function (evt) {
                    if (evt.keyCode === 38) {
                        vm._circleSelect('up');
                    } else if (evt.keyCode === 40) {
                        vm._circleSelect('down');
                    } else if (evt.keyCode === 13) {    //回车
                        vm.close(); //主动关闭
                        $('.select-header-text', elEl)[0].blur();   //使输入框失去焦点
                    }
                });
                //点击页面其他地方隐藏panel
                $(document).on('mousedown', function (evt) {
                    if (!elEl.is(evt.target) && !$(evt.target).is(selectPanelEl) && selectPanelEl.find(evt.target).length === 0) {
                        selectPanelEl.is(':visible') && selectPanelEl.hide();
                        //设置箭头方向向下
                        vm._switchIconState("down");
                    }
                });
                //设置header text区宽度
                vm.textWidth = elEl.outerWidth() - 26 - 16 - 2;  //26是icon宽度，16是headerText的padding, 2是余量
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm.$remove = function() {
                selectNum--;
                element.innerHTML = "";
                elEl.removeClass('ui-select-header').off(); //清除事件注册
                //清除附属vmodel
                vm.panelVmodel = null;
                delete avalon.vmodels[panelVmodel.$id];
                //清除select panel
                if (!selectNum) {
                    selectPanelEl[0].msData = {};
                    selectPanelEl.remove();
                }
            };
            /**
             * 循环选中
             */
            vm._circleSelect = function (direction) {
                var selectedIndex = panelVmodel.selectedIndex,
                    selectPanelH = selectPanelEl.outerHeight(),
                    scrollTop = 0;
                if (direction === "up") {
                    selectedIndex--;
                } else if (direction === "down") {
                    selectedIndex++;
                }
                if (selectedIndex === -1) {
                    selectedIndex = panelVmodel.options.size() - 1;
                }
                if (selectedIndex === panelVmodel.options.size()) {
                    selectedIndex = 0;
                }
                panelVmodel.selectedIndex = selectedIndex;
                //滚动条跟随
                scrollTop = $('.select-item', selectPanelEl).outerHeight() * (selectedIndex + 1) - selectPanelH;
                selectPanelEl.scrollTop(scrollTop);
            };
            vm._switchIconState = function (state) {
                vm.selectIconText = iconMap[state];
            };
            vm.setOptions = function (options, selectedIndex) {
                selectedIndex = selectedIndex || 0;
                if (selectedIndex > options.length - 1) {
                    selectedIndex = options.length - 1;
                }
                panelVmodel.selectedIndex = -1;
                panelVmodel.options = options || [];
                panelVmodel.selectedIndex = selectedIndex;
            };
            vm.getOptions = function () {
                return panelVmodel.options;
            };
             /**
             * 根据value获取option数据
             */
            vm.getOptionByValue = function (value) {
                return _.find(panelVmodel.options.$model, function (itemData, i) {
                    if (itemData.value == value) {
                        return true;
                    }
                });
            };
            vm.select = function (selectedIndex, isForce) {
                if (isForce) {
                    panelVmodel.selectedIndex = -1;
                }
                panelVmodel.selectedIndex = selectedIndex || 0;
            };
            vm.selectValue = function (selectedValue, isForce) {
                var atIndex = -1;
                _.some(panelVmodel.options.$model, function (itemData, i) {
                    if (itemData.value == selectedValue) {
                        atIndex = i;
                        return true;
                    }
                });
                if (atIndex !== -1) {
                    if (isForce) {
                        panelVmodel.selectedIndex = -1;
                    }
                    panelVmodel.selectedIndex = atIndex;
                }
            };
            /**
             * 主动打开下拉面板
             */
            vm.open = function () {
                elEl.trigger('click');
            };
            /**
             * 主动关闭下拉面板
             */
            vm.close = function () {
                selectPanelEl.hide();
                //设置箭头方向向下
                vm._switchIconState("down");
            };
            vm.$skipArray = ["widgetElement", "panelVmodel"];

        });
        //监测panelVmodel中optins的变化
        panelVmodel.$watch('selectedIndex', function (newValue) {
            if (newValue > -1) {
                vmodel.selectedText = panelVmodel.options[newValue].text;
                vmodel.selectedValue = panelVmodel.options[newValue].value;
                opts.onSelect.call(panelVmodel, vmodel.selectedValue, vmodel.selectedText, panelVmodel.options[newValue].$model);
            } else {
                vmodel.selectedText = '';
                vmodel.selectedValue = null; // TODO:类型不一致会不会有问题?
            }
        });
        return vmodel;
    };
    widget.version = 1.0;
    widget.defaults = {
        placeholder: '',
        readonly: false,
        disabled: false,
        options: [],
        getTemplate: function(str, options) {
            return str;
        },
        onSelect: avalon.noop,
        //每次清空下拉面板前调用，给调用者做额外清空的机会
        onBeforeEmptyPanel: avalon.noop,
        //每次扫描下拉面板后调用，给调用者操作的机会
        onAfterScanPanel: avalon.noop
    };

    /* 私有函数 */
    function adjustPosition(baseSelector, panelSelector) {
        var baseEl = $(baseSelector),
            panelEl = $(panelSelector),
            winEl = $(window),
            baseOffset = baseEl.offset(),
            baseHeight = baseEl.outerHeight(),
            baseWidth = baseEl.outerWidth(),
            panelHeight = panelEl.outerHeight(),
            panelWidth = panelEl.outerWidth(),
            winHeight = winEl.height(),
            winWidth = winEl.width(),
            winScrollTop = winEl.scrollTop(),
            winScrollLeft = winEl.scrollLeft();
        //先垂直设置
        if (baseOffset.top - winScrollTop > winHeight - (baseOffset.top - winScrollTop + baseHeight)) { //如果上方的高度大于下方
            //设置面板最大高度
            panelEl.css({
                "max-height": baseOffset.top - winScrollTop + 'px'
            });
            panelHeight = panelEl.outerHeight();    //重新获取面板高度

            if (baseOffset.top + baseHeight + panelHeight - winScrollTop <= winHeight) {
                panelEl.css({
                    "top": baseOffset.top + baseHeight - 1 + 'px'
                });

            } else {
                panelEl.css({
                    "top": baseOffset.top - panelHeight + 1 + 'px'
                });
            }
        } else {    //否则永远从下方显示
            //设置面板最大高度
            panelEl.css({
                "max-height": winHeight - (baseOffset.top - winScrollTop + baseHeight) - 2 + 'px'
            });
            panelHeight = panelEl.outerHeight();    //重新获取面板高度

            panelEl.css({
                "top": baseOffset.top + baseHeight - 1 + 'px'
            });
        }

        //再水平设置
        if (baseOffset.left + panelWidth - winScrollTop <= winWidth) {
            panelEl.css({
                "left": baseOffset.left + 'px'
            });
        } else {
            panelEl.css({
                "left": baseOffset.left + baseWidth - panelWidth + 'px'
            });
        }
    }
    var rbrace = /(?:\{[\s\S]*\}|\[[\s\S]*\])$/,
        rvalidchars = /^[\],:{}\s]*$/,
        rvalidbraces = /(?:^|:|,)(?:\s*\[)+/g,
        rvalidescape = /\\(?:["\\\/bfnrt]|u[\da-fA-F]{4})/g,
        rvalidtokens = /"[^"\\\r\n]*"|true|false|null|-?(?:\d+\.|)\d+(?:[eE][+-]?\d+|)/g;
    var parseJSON = this.JSON ? JSON.parse : function(data) {
        if (typeof data === "string") {
            data = data.trim();
            if (data) {
                if (rvalidchars.test(data.replace(rvalidescape, "@")
                        .replace(rvalidtokens, "]")
                        .replace(rvalidbraces, ""))) {
                    return (new Function("return " + data))();
                }
            }
            avalon.error("Invalid JSON: " + data);
        }
    };
    return avalon;
});
