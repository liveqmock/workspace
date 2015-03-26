/**
 * 模拟form组件，验证提交
 */
define(["avalon", "text!./form.css"], function (avalon, cssText) {
    var styleEl = $("#widget-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="widget-style"></style>');
        //styleEl.appendTo('head');
    }
    if (!styleEl.data('widgetForm')) {
        try {
            styleEl.html(styleEl.html() + avalon.adjustWidgetCssUrl(cssText, 'form/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += avalon.adjustWidgetCssUrl(cssText, 'form/');
        }
        styleEl.data('widgetForm', true);
    }
    var widget = avalon.ui.form = function (element, data, vmodels) {
        var opts = data.formOptions,
            elEl = $(element);

        var vmodel = avalon.define(data.formId, function (vm) {
            var field,
                action,
                onGetFormData;

            avalon.mix(vm, opts);
            field = vm.field;
            action = vm.action;
            onGetFormData = vm.onGetFormData;

            vm.widgetElement = element;
            //vm.$field = field;
            vm.$init = function () {
                //事件注册，只筛选存在的field
                var existField = getExistField(element, field);
                _.each(existField, function (fieldItem, i) {
                    var fieldItem_ = transFieldApi(fieldItem, i, opts.tipCls, element);
                    if (fieldItem_.triggerEventType) { //对于input、textarea和select做即时验证(change时触发验证)
                        elEl.on(fieldItem_.triggerEventType, fieldItem.selector, function (evt) {
                            vm._validateField(fieldItem, i);
                        });
                    }
                });
                _.each(action, function (actionItem) {
                    var eventType = actionItem.eventType || "click",
                        handler = actionItem.handler;
                    elEl.on(eventType, actionItem.selector, function (evt) {
                        return handler.call(this, evt);
                    });
                });
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm.$remove = function () {
                elEl.off();
                element.innerHTML = "";
            };
            vm.getFormData = function () {
                var formData = {},
                    existField = getExistField(element, field);
                _.each(existField, function (fieldItem, i) {
                    var fieldItem_ = transFieldApi(fieldItem, i, opts.tipCls, element),
                        fieldEl = fieldItem_.selector,
                        name = fieldItem_.name,
                        getValue = fieldItem_.getValue;
                    var val;
                    if (!(fieldItem.excludeHidden && fieldEl.is(':hidden'))) { //排除需要隐藏掉的field
                        val = getValue.call(fieldEl);
                        formData[name] = val;
                    }
                });
                return onGetFormData.call(vmodel, formData);
            };
            vm.reset = function () {
                var existField = getExistField(element, field);
                element.reset && element.reset(); //如果是表单先调用reset方法
                _.each(existField, function (fieldItem, i) {
                    var fieldItem_ = transFieldApi(fieldItem, i, opts.tipCls, element),
                        fieldEl = fieldItem_.selector,
                        reset = fieldItem_.reset,
                        tipDeep = fieldItem_.tipDeep,
                        tipEl = fieldItem_.tipEl;
                    reset && reset.call(fieldEl);
                    tipEl.empty().removeClass(opts.errorCls).hide();
                    fieldEl.removeClass(opts.errorCls + '-field');
                });
            };
            vm.clearValidateTip = function () {
                var existField = getExistField(element, field);
                _.each(existField, function (fieldItem, i) {
                    var fieldItem_ = transFieldApi(fieldItem, i, opts.tipCls, element),
                        fieldEl = fieldItem_.selector,
                        tipDeep = fieldItem_.tipDeep,
                        tipEl = fieldItem_.tipEl;
                    tipEl.empty().removeClass(opts.errorCls).hide();
                    fieldEl.removeClass(opts.errorCls + '-field');
                });
            };
            vm.validate = function () {
                var allPassed = true,
                    isFirstVisibleError = true;
                var existField = getExistField(element, field);
                _.each(existField, function (fieldItem, i) {
                    var isPassed;
                    var fieldItem_ = transFieldApi(fieldItem, i, opts.tipCls, element);
                    if (!(fieldItem.excludeHidden && transFieldApi(fieldItem, i, opts.tipCls, element).selector.is(':hidden'))) { //排除需要隐藏掉的field
                        isPassed = vm._validateField(fieldItem, i);
                        if (!isPassed) {
                            allPassed = false;
                            if (isFirstVisibleError && fieldItem_.selector.is(':visible')) {
                                //第一个出错的field滚动到可视区域顶部
                                scrollToViewTop(fieldItem_.selector);
                                isFirstVisibleError = false;
                            }
                        }
                    }
                });
                return allPassed;
            };
            vm._validateField = function (fieldConfig, index) {
                var fieldConfig_ = transFieldApi(fieldConfig, index, opts.tipCls, element),
                    fieldEl = fieldConfig_.selector,
                    rule = fieldConfig_.rule,
                    getValue = fieldConfig_.getValue,
                    tipEl = fieldConfig_.tipEl,
                    isPassed,
                    ruleResult = [];

                rule = [].concat(rule); //支持多rule验证，前面rule的返回结果会组成一个[]传递给当前rule
                _.each(rule, function (ruleItem) {
                    var exer;
                    if (_.isString(ruleItem)) {
                        exer = defaultRule[ruleItem];
                    } else { //只支持两种类型，一个是字符串行，一个是functin类型
                        exer = ruleItem;
                    }
                    ruleResult.push(exer.call(fieldEl, getValue.call(fieldEl), ruleResult, element) || false);
                });
                var errorMsg = ruleResult[ruleResult.length - 1]; //只提示最后一个rule验证后的结果
                if (errorMsg !== true) {
                    if (_.isString(errorMsg) || errorMsg === false) {
                        if (errorMsg === false) {
                            errorMsg = '';
                        }
                        tipEl.html(errorMsg);

                        tipEl.addClass(opts.errorCls).slideDown('fast'); //添加错误状态className
                        fieldEl.addClass(opts.errorCls + '-field');
                        //fieldEl.data('passed', false);
                        isPassed = false;
                    } else { //调用者可自定义验证后的ui处理，认为返回一个对象
                        errorMsg.handler && errorMsg.handler.call(fieldEl, !!errorMsg.isPassed, opts);
                        isPassed = !!errorMsg.isPassed;
                    }

                } else {
                    tipEl.empty().removeClass(opts.errorCls).hide();
                    fieldEl.removeClass(opts.errorCls + '-field');
                    //fieldEl.data('passed', true);
                    isPassed = true;
                }
                return isPassed;
            };
            vm.$skipArray = ["widgetElement", "field", "tipCls", "errorCls", "action"];

        });
        return vmodel;
    };
    widget.version = 1.0;
    widget.defaults = {
        "field": [],
        /*"tipCls": "field-tip",
        "errorCls": "state-error",*/
        "tipCls": "ff-value-tip",
        "errorCls": "valid-error",
        "action": [],
        "onGetFormData": function (formData) {
            return formData;
        } //获取form数据拦截
    };

    //=================== 公共属性函数 =========================//
    var defaultRule = {
        "noempty": function (val) {
            if (val.length === 0) {
                return false;
            } else {
                return true;
            }
        },
        "maxlength": function (val, ruleOpts) {
            var maxLength = this.attr('maxlength') || 100000;
            if (val.length > maxLength) {
                return false;
            } else {
                return true;
            }
        }
    };
    /**
     * 获取当前存在的selector
     * @param {Type} parentSelector
     * @param {Type} field
     */
    function getExistField(parentSelector, field) {
        var pEl = $(parentSelector);
        return _.filter(field, function (fieldItem) {
            return $.contains(pEl[0], $(fieldItem.selector, pEl)[0]);
        });
    }
    /**
     * 转换成field配置项对应的接口
     */
    function transFieldApi(fieldConfig, index, tipCls, element) {
        var fieldEl = $(fieldConfig.selector, $(element)),
            name = fieldConfig.name || fieldEl.attr('name') || ('field' + index),
            rule = fieldConfig.rule || fieldRule,
            getValue = fieldConfig.getValue || getFieldValue,
            reset = fieldConfig.reset === true ? fieldReset : fieldConfig.reset,
            tipDeep = fieldConfig.tipDeep || 3,
            triggerEventType = getTriggerEventType(fieldEl),
            tipEl = getTipMsg.call(fieldEl, tipDeep, '.' + tipCls);
        return {
            "selector": fieldEl,
            "name": name,
            "rule": rule,
            "getValue": getValue,
            "reset": reset,
            "tipDeep": tipDeep,
            "triggerEventType": triggerEventType,
            "tipEl": tipEl
        };
    }
    /**
     * 根据selector判断验证触发的时机
     *
     * @param {Type} selector
     */
    function getTriggerEventType(selector) {
        var elEl = $(selector),
            eventType;
        if (elEl.is(':text') || elEl.is(':checkbox') || elEl.is(':radio') || elEl.is(':file') || elEl.is(':password') || elEl.is('textarea') || elEl.is('select')) {
            eventType = "change";
        }
        return eventType;
    }

    function fieldRule() {
        return true; //默认永远正确无比
    }

    function getFieldValue() {
        var elEl = $(this),
            val;
        if (elEl.is(':text') || elEl.is('[type="hidden"]') || elEl.is(':file') || elEl.is(':password') || elEl.is('textarea') || elEl.is('select')) { //对于这三种类型只取匹配selector的第一个值
            val = elEl.val();
        } else if (elEl.is(':checkbox') || elEl.is(':radio')) { //认为是一个集合
            val = [];
            elEl.filter(':checked').each(function () {
                val.push($(this).val());
            });
            if (val.length == 1) {
                val = val[0];
            }
        } else {
            val = elEl.data('value');
        }
        if (_.isString(val)) {
            val = _.str.trim(val);
        }
        return val;
    }

    function fieldReset() {
        var elEl = $(this);
        if (elEl.is(':text') || elEl.is(':file') || elEl.is(':password') || elEl.is('textarea') || elEl.is('select')) {
            elEl.val("");
        } else if (elEl.is(':checkbox') || elEl.is(':radio')) {
            elEl.prop('checked', false);
        } else { //其他类型直接empty
            elEl.empty();
        }
    }
    /**
     * 获取error message 容器，最大向上查找 tipDeep 层，如果没找到，默认在element后追加一个
     * @param {Type}
     */
    function getTipMsg(tipDeep, selector) {
        var elEl = $(this),
            tempEl = elEl,
            tipEl;
        while (tipDeep > 0) {
            tipEl = tempEl.siblings(selector) || tempEl.find(selector);
            if (tipEl.length > 0) {
                break;
            } else {
                tempEl = tempEl.parent();
                tipDeep--;
            }
        }
        if (!tipEl || tipEl.length === 0) {
            tipEl = $('<span class="field-tip"></span>');
            tipEl.insertAfter(elEl.last());
        }
        return tipEl;
    }
    /**
     * 尝试把selector代表的dom滚动到可视区域顶部
     * @param {Jquery} selector jquery选择符
     */
    function scrollToViewTop(selector) {
        var elEl = $(selector),
            targetEl = elEl,
            pEl = elEl.parent(),
            elOffset,
            pOffset,
            disSize,
            offsetType;
        while (!pEl.is(document)) {
            if (pEl.css('overflow') !== "visible" && pEl.css('overflow') !== "hidden") {
                pEl.scrollTop(0);
                elOffset = elEl.offset();
                pOffset = pEl.offset();
                disSize = elOffset.top - pOffset.top;
                if (!isNaN(disSize)) {
                    pEl.scrollTop(disSize < 0 ? 0 : disSize);
                    elEl = pEl;
                }
            }
            pEl = pEl.parent();
        }
        //最终调整下window的滚动条位置
        $(window).scrollTop(targetEl.offset().top);
    }
    return avalon;
});
