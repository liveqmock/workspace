/**
 * 项目级公共处理逻辑，项目初始化接口定义
 */
define(["avalon", "util"], function(avalon, util) {
    var win = this,
        doc = document,
        app = win.app;
    //扩展avalon,添加module注册机制
    (function () {
        var module = app.module,
            rword = /[^, ]+/g, //切割字符串为一个个小块，以空格或豆号分开它们，结合replace实现字符串的forEach
            VMODELS = avalon.vmodels,
            noop = function () {},
            supportMutationEvents = win.dispatchEvent && doc.implementation.hasFeature("MutationEvents", "2.0");

        /**
         * 判断propertyName是否是深引用的属性链
         */
        function isObjectInDeeper (host, propertyName) {
            propertyName = propertyName.split(".");
            if (_.every(propertyName, function (itemName) {
                if (host.hasOwnProperty(itemName) && typeof host[itemName] === "object") {
                    host = host[itemName];
                    return true;
                }
            })) {
                return true;
            } else {
                return false;
            };
        }
        /**
         * 获取propertyName深引用链的值
         */
        function getPropertyValue (host, propertyName) {
            var v;
            propertyName = propertyName.split(".");
            _.each(propertyName, function (itemName) {
                v = host[itemName];
                host = v;
            });
            return v;
        }
        _.extend(avalon.bindingHandlers, {
            "module": function (data, vmodels) {
                var args = data.value.match(rword);
                var elem = data.element;
                var moduleName = args[0];
                if (args[1] === "$" || !args[1]) {
                    args[1] = moduleName + setTimeout("1");
                }
                data.value = args.join(",");
                var constructor = module[moduleName];
                if (typeof constructor === "function") { //ms-module="tabs,tabsAAA,optname"
                    vmodels = elem.vmodels || vmodels;
                    var optName = args[2] || moduleName; //尝试获得配置项的名字，没有则取module的名字
                    for (var i = 0, v; v = vmodels[i++]; ) {
                        if (v.hasOwnProperty(optName) && typeof v[optName] === "object") {
                            var nearestVM = v;
                            break;
                        }
                    }
                    if (nearestVM) {
                        var vmOptions = nearestVM[optName];
                        vmOptions = vmOptions.$model || vmOptions;
                        var id = vmOptions[moduleName + "Id"];
                        if (typeof id === "string") {
                            args[1] = id;
                        }
                    }
                    var widgetData = avalon.getWidgetData(elem, args[0]); //抽取data-tooltip-text、data-tooltip-attr属性，组成一个配置对象
                    data[moduleName + "Id"] = args[1];
                    data[moduleName + "Options"] = avalon.mix({}, constructor.defaults, vmOptions || {}, widgetData);
                    elem.removeAttribute("ms-module");
                    var vmodel = constructor(elem, data, vmodels) || {}; //防止组件不返回VM
                    data.evaluator = noop;
                    if (!elem.msData) { //保持和widget一致
                        elem.msData = {};
                        elem.msData['ms-module'] = moduleName;
                    }
                    elem.msData["ms-module-id"] = vmodel.$id || "";
                    if (vmodel.hasOwnProperty("$init")) {
                        vmodel.$init();
                    }
                    if (vmodel.hasOwnProperty("$remove")) {
                        function offTree() {
                            if (!elem.msRetain && !document.documentElement.contains(elem)) {
                                vmodel.$remove();
                                elem.msData = {};
                                delete VMODELS[vmodel.$id];
                                return false;
                            }
                        }
                        if (window.chrome) {
                            elem.addEventListener("DOMNodeRemovedFromDocument", function() {
                                setTimeout(offTree);
                            })
                        } else {
                            avalon.tick(offTree);
                        }
                    }
                } else if (vmodels.length) { //如果该组件还没有加载，那么保存当前的vmodels
                    elem.vmodels = vmodels;
                }
            },
            "widget": function(data, vmodels) {
                var args = data.value.match(rword);
                var elem = data.element;
                var widget = args[0];
                if (args[1] === "$" || !args[1]) {
                    args[1] = widget + setTimeout("1");
                }
                data.value = args.join(",");
                var constructor = avalon.ui[widget];
                if (typeof constructor === "function") { //ms-widget="tabs,tabsAAA,optname"
                    vmodels = elem.vmodels || vmodels;
                    var optName = args[2] || widget; //尝试获得配置项的名字，没有则取widget的名字
                    for (var i = 0, v; v = vmodels[i++]; ) {
                        //if (v.hasOwnProperty(optName) && typeof v[optName] === "object") {
                        if (isObjectInDeeper(v, optName)) {
                            var nearestVM = v;
                            break;
                        }
                    }
                    if (nearestVM) {
                        //var vmOptions = nearestVM[optName];
                        var vmOptions = getPropertyValue(nearestVM, optName);
                        vmOptions = vmOptions.$model || vmOptions;
                        var id = vmOptions[widget + "Id"];
                        if (typeof id === "string") {
                            args[1] = id;
                        }
                    }
                    var widgetData = avalon.getWidgetData(elem, args[0]); //抽取data-tooltip-text、data-tooltip-attr属性，组成一个配置对象
                    data[widget + "Id"] = args[1];
                    data[widget + "Options"] = avalon.mix({}, constructor.defaults, vmOptions || {}, widgetData);
                    elem.removeAttribute("ms-widget");
                    var vmodel = constructor(elem, data, vmodels) || {}; //防止组件不返回VM
                    data.evaluator = noop;
                    elem.msData["ms-widget-id"] = vmodel.$id || "";
                    if (vmodel.hasOwnProperty("$init")) {
                        vmodel.$init();
                    }
                    if (vmodel.hasOwnProperty("$remove")) {
                        function offTree() {
                            if (!elem.msRetain && !document.documentElement.contains(elem)) {
                                vmodel.$remove();
                                elem.msData = {};
                                delete VMODELS[vmodel.$id];
                                return false;
                            }
                        }
                        if (window.chrome) {
                            elem.addEventListener("DOMNodeRemovedFromDocument", function() {
                                setTimeout(offTree);
                            })
                        } else {
                            avalon.tick(offTree);
                        }
                    }
                } else if (vmodels.length) { //如果该组件还没有加载，那么保存当前的vmodels
                    elem.vmodels = vmodels;
                }
            }
        });
        app.module = module;
    }());
    /**
     * 对html标签设置ie子版本标志className for css hack
     */
    var setCssHackCls = function () {
        var htmlEl = $('html');
        _.each([6, 7, 8, 9], function (version) {
            if (util.isIE(version)) {
                htmlEl.addClass('ie-' + version);
            }
        });
    };
    /**
     * 表单元素初始化
     */
    var formInit = function () {
        var i = 0,
            tId;
        $('body').on('focus', '.input-text,textarea', function () {
            $(this).addClass('input-focus');
        }).on('blur', '.input-text,textarea', function () {
            $(this).removeClass('input-focus');
        }).on('click', '.mn-radio', function () {   //模拟单选框
            var meEl = $(this),
                parentEl = meEl.parent();
            if (!meEl.hasClass('mn-radio-state-readonly')) {
                $('.mn-radio-state-selected', parentEl).removeClass('mn-radio-state-selected');
                meEl.addClass('mn-radio-state-selected');
            }
        }).on('click', '.label-for', function () {    //label-for绑定
            var meEl = $(this),
                inputEl = $('input', meEl),
                labelEl = $('label', meEl),
                inputId = inputEl.attr('id');
            if (!inputId) {
                inputId = 'label-for-' + i;
                inputEl.attr('id', inputId);
                i++;
            }
            if (!labelEl.attr('for')) {
                labelEl.attr('for', inputId);
            }
        }).on('focus', '.input-text', function () {    //input：text聚焦后显示关闭icon
            var iconCloseEl = $('#input-text-close'),
                meEl = $(this),
                meOffset = meEl.offset();
            //如果当前input处于readonly或者disabled，不显示按钮
            if (meEl.prop('readonly') || meEl.prop('disabled')) {
                return;
            }
            if (!iconCloseEl.length) {
                iconCloseEl = $('<div id="input-text-close">&#10005;</div>');
                iconCloseEl.appendTo('body');
            }
            iconCloseEl.data('input', meEl);
            //定位
            clearTimeout(tId);
            iconCloseEl.css({
                "top": meOffset.top + 1,
                "left": meOffset.left + meEl.outerWidth() - 32
            }).show();

            //及时清理msValueStore存储
            var msValueStore = app.msValueStore;
            msValueStore = _.reject(msValueStore, function (itemData) {
                return !$.contains(doc.body, itemData.element);
            });
            app.msValueStore = msValueStore;
        }).on('blur', '.input-text', function () {
            var iconCloseEl = $('#input-text-close');
            clearTimeout(tId);
            tId = setTimeout(function () {
                iconCloseEl.removeData('input').hide();
            }, 160);
        }).on('click', '#input-text-close', function () {
            var iconCloseEl = $(this),
                inputEl = iconCloseEl.data('input'),
                msValueStore = app.msValueStore;
            clearTimeout(tId);
            _.some(msValueStore, function (itemData) {
                var text,
                    vmodels;
                if (itemData.element === inputEl[0]) {
                    text = itemData.text;
                    vmodels = itemData.vmodels;
                    _.some(vmodels, function (vm) {
                        if (vm.hasOwnProperty(text)) {
                            vm[text] = "";
                            return true;
                        }
                    });
                    return true;
                }
            });
            inputEl.val("");
            iconCloseEl.removeData('input').hide();
        });
    };
    var regForceRefresh = function () {
        //强制刷新
        $('body').on('click', '.force-refresh-link', function () {
            var href = $(this).attr('href').split('#')[1],
                currentHash = avalon.History.getHash(window.location).slice(2);
            if (href === currentHash) {
                avalon.router.navigate(href);
            }
        });
    };
    /**
     * 初始化入口
     */
    var setup = function (callback) {
        setCssHackCls();
        formInit();
        regForceRefresh();
        //注册路由
        var pageWEl = $('#page-wrapper'),
            pageMaskWEl = $('.app-bd .app-inner');
        util.regRouter(pageWEl, pageMaskWEl, function () {
            callback && callback();
        });
    };
    return {
        "setup": setup
    };
});
