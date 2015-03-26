/**
 * 会员卡规则设置
 */
define(["avalon", "util", "json", "moment", "text!./cardrulesetting.html", "text!./cardrulesetting.css", "common", "../../widget/dialog/dialog", "../../widget/form/form", "../../widget/form/select"], function(avalon, util, JSON, moment, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    var styleData = styleEl.data('module') || {};

    if (!styleData["cardrulesetting"]) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'cardrulesetting/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'cardrulesetting/');
        }
        styleData["cardrulesetting"] = true;
        styleEl.data('module', styleData);
    }
    var module = erp.module.cardrulesetting = function(element, data, vmodels) {
        var opts = data.cardrulesettingOptions,
            elEl = $(element),
            now = new Date();
        var settingDialogId = data.cardrulesettingId + '-setting-dialog',
            settingFormId = data.cardrulesettingId + '-setting-form',
            assistVmIndex = 0;    //辅助的select组件id递增算子
        var vmodel = avalon.define(data.cardrulesettingId, function (vm) {
            avalon.mix(vm, opts);
            vm.widgetElement = element;
            vm.activePanel = 'fans';   //当前激活的面板, fans/integral/store/integralStore
            vm.fansCardList = [];
            vm.integralCardList = [];
            vm.storeCardList = [];
            vm.integralStoreCardList = [];
            vm.readonly = false;    //知否处于只读状态
            vm.$settingDialogOpts = {
                "dialogId": settingDialogId,
                "title": "会员卡规则",
                "width": 700,
                "getTemplate": function (tmpl) {
                    var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                        footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                            '<div class="ui-dialog-btns" ms-if="!readonly">' +
                                '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">保&nbsp;存</button>' +
                                '<span class="separation"></span>' +
                                '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button>' +
                            '</div>' +
                            '<div class="ui-dialog-btns" ms-if="readonly">' +
                                '<button type="button" class="submit-btn ui-dialog-btn" ms-click="close">确&nbsp;定</button>' +
                            '</div>' +
                        '</div>';
                    return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                },
                "onClose": function () {
                    //清空
                    var formVm = avalon.getVm(settingFormId),
                        formEl = $(formVm.widgetElement);
                    $('.card-rebate-item', formEl).each(function () {
                        $('.close-h', this).click();
                    });
                    //清空数据源
                    _.each(["fans", "integral", "store", "integralStore"], function (panelName) {
                        vm[panelName + "CardList"].clear();
                    });
                    //form reset
                    formVm.reset();
                    $('.module-cardrulesetting-nav-item', formEl).removeData('valid').removeClass('state-error');
                    //定位到第一个tab
                    vm.activePanel = "fans";
                },
                "onOpen": function () {
                    util.c2s({
                        "url": erp.BASE_PATH + "trade/cardtype/listCardtypes.do",
                        "data": {
                            "merchantId": vm.merchantId
                        },
                        "success": function (responseData) {
                            var data;
                            if (responseData.flag) {
                                data = responseData.data;
                                _.each(data, function (itemData) {
                                    var awardRuleVos = itemData.awardRuleVos,
                                        panelName = getPanelNameFromGradeLevel(itemData.gradeLevel),
                                        consumeData = _.find(awardRuleVos, function (itemData) {
                                            return itemData.awardType === 1;    //积分奖励类型
                                        }),
                                        fixedRebate = _.find(awardRuleVos, function (itemData) {   //固定返点
                                            return itemData.awardType === 2 && itemData.rebateType === 1;
                                        }),
                                        rangeRebate = _.filter(awardRuleVos, function (itemData) { //金额段返点
                                            return itemData.awardType === 2 && itemData.rebateType === 2;
                                        });
                                    vm["add" + _.str.capitalize(panelName) + "Card"]({
                                        "cardName": itemData.cardtype,
                                        "id": itemData.id,
                                        "gradeLevel": itemData.gradeLevel,
                                        "cardPrice": itemData.price,
                                        "consumeRebate": consumeData ? consumeData.awardAmount : "",
                                        "consumeRebateId": consumeData ? consumeData.id : "",
                                        "rebateType": itemData.rebateType,
                                        "fixedRebate": fixedRebate ? {
                                            "rebateValue": fixedRebate.awardAmount,
                                            "awardType": fixedRebate.awardType,
                                            "awardAmountType": fixedRebate.awardAmountType,
                                            "id": fixedRebate.id
                                        } : null,
                                        "rangeRebate": _.map(rangeRebate, function (itemData) {
                                            return {
                                                "id": itemData.id,
                                                "beginAmount": itemData.lowerLimit,
                                                "endAmount": itemData.upperLimit,
                                                "rebateValue": itemData.awardAmount,
                                                "awardType": itemData.awardType,
                                                "awardAmountType": itemData.awardAmountType
                                            };
                                        })
                                    });
                                });
                            }
                        }
                    });
                },
                "submit": function () {
                    var formVm = avalon.getVm(settingFormId),
                        formData,
                        requestData = [];
                    if (formVm.validate()) {
                        formData = formVm.getFormData();
                        if (formData.fansCard) {
                            requestData = requestData.concat(formData.fansCard);
                        }
                        if (formData.integralCard) {
                            requestData = requestData.concat(formData.integralCard);
                        }
                        if (formData.storeCard) {
                            requestData = requestData.concat(formData.storeCard);
                        }
                        if (formData.integralStoreCard) {
                            requestData = requestData.concat(formData.integralStoreCard);
                        }
                        //id为0的转成null
                        _.each(requestData, function (itemData) {
                            if (!itemData.id) {
                                itemData.id = null;
                            }
                            _.each(itemData.awardRuleVos, function (itemData2) {
                                if (!itemData2.id) {
                                    itemData2.id = null;
                                }
                            });
                        });
                        util.c2s({
                            "url": erp.BASE_PATH + "trade/cardtype/saveCardtypes.do",
                            "contentType": "application/json",
                            "data": JSON.stringify(requestData),
                            "success": function (responseData) {
                                if (responseData.flag) {
                                    util.alert({
                                        "content": responseData.message,
                                        "iconType": "success"
                                    });
                                    avalon.getVm(settingDialogId).close();
                                }
                                //用户自定义回调
                                vm.onUpdateCallback.call(vm, responseData);
                            }
                        });
                    }
                }
            };
            vm.$settingFormOpts = {
                "formId": settingFormId,
                "field": [{
                    "selector": ".fans-panel-item .card-name",
                    "name": "fansCard",
                    //"excludeHidden": true,
                    "getValue": function () {
                        var cardItemEl = $('.fans-card-item', avalon.getVm(settingFormId).widgetElement),
                            result = [];
                        cardItemEl.each(function () {
                            var meEl = $(this),
                                cardNameEl = $('.card-name', this);
                            result.push({
                                "cardtype": _.str.trim(cardNameEl.val()),
                                "id": meEl.data('cardId'),
                                //"price": cardNameEl.data('price'),
                                "gradeLevel": 1,
                                "awardRuleVos": [],
                                "merchantId": vm.merchantId
                            });
                        });
                        return result;
                    },
                    "rule": function () {
                        var inputEl = $(this),
                            formEl = $(avalon.getVm(settingFormId).widgetElement),
                            cardItemEl = $('.fans-card-item', formEl),
                            selfNavEl = $('.module-cardrulesetting-nav-item', formEl).filter('[data-panel-name="fans"]'),
                            selfValidData = selfNavEl.data('valid') || {},
                            isPassed = true;
                        inputEl.each(function () {
                            var meEl = $(this);
                            if (!_.str.trim(meEl.val()).length) {
                                isPassed = false;
                                return false;
                            }
                        });
                        return {
                            "handler": function (isPassed, opts) {
                                if (!isPassed) {
                                    cardItemEl.each(function (i) {
                                        var fieldEl = $(this),
                                            cardNameEl = $('.card-name', fieldEl),
                                            ffValueEl = $('.ff-value', fieldEl),
                                            tipEl = $('.ff-value-tip', ffValueEl),
                                            tipText = [];
                                        if (!_.str.trim(cardNameEl.val()).length) {
                                            tipText.push('卡名称不能为空');
                                            cardNameEl.addClass(opts.errorCls + '-field');
                                        } else {
                                            cardNameEl.removeClass(opts.errorCls + '-field');
                                        }
                                        if (tipText.length) {
                                            tipEl.addClass(opts.errorCls);
                                            tipEl.text(tipText.join('，'));
                                        } else {
                                            tipEl.removeClass(opts.errorCls);
                                            tipEl.empty();
                                        }
                                    });
                                    if (vm.activePanel !== "fans") {
                                        selfNavEl.addClass('state-error');
                                        selfValidData.card = false;
                                    }
                                } else {
                                    inputEl.each(function (i) {
                                        var meEl = $(this),
                                            ffValueEl = meEl.closest('.ff-value'),
                                            tipEl = $('.ff-value-tip', ffValueEl);
                                        tipEl.removeClass(opts.errorCls).empty();
                                        meEl.removeClass(opts.errorCls + '-field');
                                    });
                                    selfValidData.card = true;
                                    if (selfValidData.card) {
                                        selfNavEl.removeClass('state-error');
                                    }
                                }
                                selfNavEl.data('valid', selfValidData);
                            },
                            "isPassed": isPassed
                        };
                    }
                }, {
                    "selector": ".integral-card-item .card-name,.integral-card-item .card-price",
                    "name": "integralCard",
                    "getValue": function () {
                        var cardItemEl = $('.integral-card-item', avalon.getVm(settingFormId).widgetElement),
                            result = [];
                        cardItemEl.each(function () {
                            var meEl = $(this),
                                cardNameEl = $('.card-name', this),
                                cardPriceEl = $('.card-price', this),
                                consumeRebateEl = $('.consume-rebate', this);
                            result.push({
                                "cardtype": _.str.trim(cardNameEl.val()),
                                "id": meEl.data('cardId'),
                                "price": _.str.trim(cardPriceEl.val()),
                                "gradeLevel": 2,
                                "awardRuleVos": [{
                                    "id": consumeRebateEl.data('consumeRebateId'),
                                    "awardAmount": _.str.trim(consumeRebateEl.val()),
                                    "awardType": 1,
                                    "transCode": "0002",
                                    "awardAmountType": 2    //按百分比
                                }],
                                "merchantId": vm.merchantId
                            });
                        });
                        return result;
                    },
                    "rule": function () {
                        var inputEl = $(this),
                            formEl = $(avalon.getVm(settingFormId).widgetElement),
                            cardItemEl = $('.integral-card-list .card-info-field', formEl),
                            selfNavEl = $('.module-cardrulesetting-nav-item', formEl).filter('[data-panel-name="integral"]'),
                            selfValidData = selfNavEl.data('valid') || {},
                            isPassed = true;
                        inputEl.each(function () {
                            var meEl = $(this);
                            if (!_.str.trim(meEl.val()).length) {
                                isPassed = false;
                                return false;
                            }
                        });
                        return {
                            "handler": function (isPassed, opts) {
                                if (!isPassed) {
                                    cardItemEl.each(function (i) {
                                        var fieldEl = $(this),
                                            cardNameEl = $('.card-name', fieldEl),
                                            cardPriceEl = $('.card-price', fieldEl),
                                            ffValueEl = $('.ff-value', fieldEl),
                                            tipEl = $('.ff-value-tip', ffValueEl),
                                            tipText = [];
                                        if (!_.str.trim(cardNameEl.val()).length) {
                                            tipText.push('卡名称不能为空');
                                            cardNameEl.addClass(opts.errorCls + '-field');
                                        } else {
                                            cardNameEl.removeClass(opts.errorCls + '-field');
                                        }
                                        if (!_.str.trim(cardPriceEl.val()).length) {
                                            tipText.push('卡单价不能为空');
                                            cardPriceEl.addClass(opts.errorCls + '-field');
                                        } else {
                                            cardPriceEl.removeClass(opts.errorCls + '-field');
                                        }
                                        if (tipText.length) {
                                            tipEl.addClass(opts.errorCls);
                                            tipEl.text(tipText.join('，'));
                                        } else {
                                            tipEl.removeClass(opts.errorCls);
                                            tipEl.empty();
                                        }
                                    });
                                    if (vm.activePanel !== "integral") {
                                        selfNavEl.addClass('state-error');
                                        selfValidData.card = false;
                                    }
                                } else {
                                    inputEl.each(function (i) {
                                        var meEl = $(this),
                                            ffValueEl = meEl.closest('.ff-value'),
                                            tipEl = $('.ff-value-tip', ffValueEl);
                                        tipEl.removeClass(opts.errorCls).empty();
                                        meEl.removeClass(opts.errorCls + '-field');
                                    });
                                    selfValidData.card = true;
                                    if (selfValidData.card && selfValidData.consume) {
                                        selfNavEl.removeClass('state-error');
                                    }
                                }
                                selfNavEl.data('valid', selfValidData);
                            },
                            "isPassed": isPassed
                        };
                    }
                }, {
                    "selector": ".integral-card-item .consume-rebate",
                    "rule": function () {
                        var inputEl = $(this),
                            formEl = $(avalon.getVm(settingFormId).widgetElement),
                            rebateItemEl = $('.integral-card-list .rebate-info-field', formEl),
                            selfNavEl = $('.module-cardrulesetting-nav-item', formEl).filter('[data-panel-name="integral"]'),
                            selfValidData = selfNavEl.data('valid') || {},
                            isPassed = true;
                        inputEl.each(function () {
                            var meEl = $(this);
                            if (!_.str.trim(meEl.val()).length) {
                                isPassed = false;
                                return false;
                            }
                        });
                        return {
                            "handler": function (isPassed, opts) {
                                if (!isPassed) {
                                    rebateItemEl.each(function (i) {
                                        var fieldEl = $(this),
                                            consumeRebateEl = $('.consume-rebate', fieldEl),
                                            ffValueEl = $('.ff-value', fieldEl),
                                            tipEl = $('.ff-value-tip', ffValueEl),
                                            tipText = [];
                                        if (!_.str.trim(consumeRebateEl.val()).length) {
                                            tipText.push('消费返点不能为空');
                                            consumeRebateEl.addClass(opts.errorCls + '-field');
                                        } else {
                                            consumeRebateEl.removeClass(opts.errorCls + '-field');
                                        }
                                        if (tipText.length) {
                                            tipEl.addClass(opts.errorCls);
                                            tipEl.text(tipText.join('，'));
                                        } else {
                                            tipEl.removeClass(opts.errorCls);
                                            tipEl.empty();
                                        }
                                    });
                                    if (vm.activePanel !== "integral") {
                                        selfNavEl.addClass('state-error');
                                        selfValidData.consume = false;
                                    }
                                } else {
                                    inputEl.each(function (i) {
                                        var meEl = $(this),
                                            ffValueEl = meEl.closest('.ff-value'),
                                            tipEl = $('.ff-value-tip', ffValueEl);
                                        tipEl.removeClass(opts.errorCls).empty();
                                        meEl.removeClass(opts.errorCls + '-field');
                                    });
                                    selfValidData.consume = true;
                                    if (selfValidData.card && selfValidData.consume) {
                                        selfNavEl.removeClass('state-error');
                                    }
                                }
                                selfNavEl.data('valid', selfValidData);
                            },
                            "isPassed": isPassed
                        };
                    }
                }, {
                    "selector": ".store-card-item .card-name",
                    "name": "storeCard",
                    "getValue": function () {
                        var cardItemEl = $('.store-card-item', avalon.getVm(settingFormId).widgetElement),
                            result = [];
                        cardItemEl.each(function () {
                            var meEl = $(this),
                                cardNameEl = $('.card-name', this),
                                rebateTypeWrapperEl = $('.rebate-type-wrapper', this),
                                rebateTypeAssistVm = avalon.getVm(rebateTypeWrapperEl.attr('id')),
                                rebateTypeVm = avalon.getVm(rebateTypeAssistVm.$rebateTypeOpts.selectId),
                                amountFixedWEl,
                                rangeRebateItemEl = $('.amount-range-wrapper .card-rebate-item', this),
                                assistVm,
                                fixedRebateSettingVm,
                                fixedRebateUnitVm,
                                awardRuleVos = [];
                            if (rebateTypeVm.selectedValue === 1) { //固定返点
                                amountFixedWEl = $('.amount-fixed-wrapper', meEl);
                                assistVm = avalon.getVm(amountFixedWEl.attr('id'));
                                fixedRebateSettingVm = avalon.getVm(assistVm.$rebateSettingOpts.selectId),
                                fixedRebateUnitVm = avalon.getVm(assistVm.$rebateUnitOpts.selectId);

                                awardRuleVos.push({
                                    "id": amountFixedWEl.data('ruleId'),
                                    "awardAmount": _.str.trim(assistVm.rebateValue),
                                    "rebateType": rebateTypeVm.selectedValue,
                                    "awardType": 2,
                                    "awardAmountType": fixedRebateSettingVm.selectedValue,
                                    "transCode": "2001"
                                });
                            } else {    //金额段返点
                                rangeRebateItemEl.each(function () {
                                    var beginAmountEl = $('.begin-amount', this),
                                        endAmountEl = $('.end-amount', this),
                                        rebateValueEl = $('.rebate-value', this),
                                        assistVm = avalon.getVm($(this).attr('id')),
                                        rebateSettingVm = avalon.getVm(assistVm.$rebateSettingOpts.selectId),
                                        rebateUnitVm = avalon.getVm(assistVm.$rebateUnitOpts.selectId);
                                    awardRuleVos.push({
                                        "id": $(this).data('ruleId'),
                                        "lowerLimit": _.str.trim(beginAmountEl.val()),
                                        "upperLimit": _.str.trim(endAmountEl.val()),
                                        "awardAmount": _.str.trim(rebateValueEl.val()),
                                        "rebateType": rebateTypeVm.selectedValue,
                                        "awardType": 2,
                                        "awardAmountType": rebateSettingVm.selectedValue,
                                        "transCode": "2001"
                                    });
                                });
                            }

                            result.push({
                                "cardtype": _.str.trim(cardNameEl.val()),
                                "id": meEl.data('cardId'),
                                "gradeLevel": 3,
                                "rebateType": rebateTypeVm.selectedValue,
                                "awardRuleVos": awardRuleVos,
                                "merchantId": vm.merchantId
                            });
                        });
                        return result;
                    },
                    "rule": function () {
                        var inputEl = $(this),
                            formEl = $(avalon.getVm(settingFormId).widgetElement),
                            cardItemEl = $('.store-card-list .card-info-field', formEl),
                            selfNavEl = $('.module-cardrulesetting-nav-item', formEl).filter('[data-panel-name="store"]'),
                            selfValidData = selfNavEl.data('valid') || {},
                            isPassed = true;
                        inputEl.each(function () {
                            var meEl = $(this);
                            if (!_.str.trim(meEl.val()).length) {
                                isPassed = false;
                                return false;
                            }
                        });
                        return {
                            "handler": function (isPassed, opts) {
                                if (!isPassed) {
                                    cardItemEl.each(function (i) {
                                        var fieldEl = $(this),
                                            cardNameEl = $('.card-name', fieldEl),
                                            ffValueEl = $('.ff-value', fieldEl),
                                            tipEl = $('.ff-value-tip', ffValueEl),
                                            tipText = [];
                                        if (!_.str.trim(cardNameEl.val()).length) {
                                            tipText.push('卡名称不能为空');
                                            cardNameEl.addClass(opts.errorCls + '-field');
                                        } else {
                                            cardNameEl.removeClass(opts.errorCls + '-field');
                                        }
                                        if (tipText.length) {
                                            tipEl.addClass(opts.errorCls);
                                            tipEl.text(tipText.join('，'));
                                        } else {
                                            tipEl.removeClass(opts.errorCls);
                                            tipEl.empty();
                                        }
                                    });
                                    if (vm.activePanel !== "store") {
                                        selfNavEl.addClass('state-error');
                                        selfValidData.card = false;
                                    }
                                } else {
                                    inputEl.each(function (i) {
                                        var meEl = $(this),
                                            ffValueEl = meEl.closest('.ff-value'),
                                            tipEl = $('.ff-value-tip', ffValueEl);
                                        tipEl.removeClass(opts.errorCls).empty();
                                        meEl.removeClass(opts.errorCls + '-field');
                                    });
                                    selfValidData.card = true;
                                    if (selfValidData.card && selfValidData.rebate) {
                                        selfNavEl.removeClass('state-error');
                                    }
                                }
                                selfNavEl.data('valid', selfValidData);
                            },
                            "isPassed": isPassed
                        };
                    }
                }, {
                    "selector": ".store-card-item .begin-amount,.store-card-item .end-amount,.store-card-item .rebate-value",
                    "rule": function () {
                        var inputEl = $(this),
                            formEl = $(avalon.getVm(settingFormId).widgetElement),
                            rebateItemEl = $('.store-card-list .rebate-info-field', formEl),
                            selfNavEl = $('.module-cardrulesetting-nav-item', formEl).filter('[data-panel-name="store"]'),
                            selfValidData = selfNavEl.data('valid') || {},
                            isPassed = true;
                        inputEl.each(function () {
                            var meEl = $(this),
                                wEl = meEl.closest('.amount-range-wrapper');
                            if (!wEl.length) {
                                wEl = meEl.closest('.amount-fixed-wrapper');
                            }
                            if (wEl.css('display') === "block" && !_.str.trim(meEl.val()).length) {
                                isPassed = false;
                                return false;
                            }
                        });
                        return {
                            "handler": function (isPassed, opts) {
                                if (!isPassed) {
                                    rebateItemEl.each(function (i) {
                                        var fieldEl = $(this),
                                            beginAmountEl = $('.begin-amount', fieldEl),
                                            endAmountEl = $('.end-amount', fieldEl),
                                            rebateValueEl = $('.rebate-value', fieldEl),
                                            ffValueEl = $('.ff-value', fieldEl),
                                            tipEl = $('.ff-value-tip', ffValueEl),
                                            tipText = [];
                                        beginAmountEl.each(function () {
                                            var meEl = $(this),
                                                val = _.str.trim(meEl.val());
                                            if (meEl.is(':visible')) {
                                                if (!val.length) {
                                                    tipText.push('起始金额不能为空');
                                                    meEl.addClass(opts.errorCls + '-field');
                                                    return false;
                                                }
                                                if (!/^-?\d+(\.\d{1,2})?$/.test(val)) { //最多保留2位小数
                                                    tipText.push('金额最多保留2位小数');
                                                    meEl.addClass(opts.errorCls + '-field');
                                                    return false;
                                                }
                                                meEl.removeClass(opts.errorCls + '-field');
                                            } else {
                                                meEl.removeClass(opts.errorCls + '-field');
                                            }
                                        });
                                        endAmountEl.each(function () {
                                            var meEl = $(this),
                                                beginAmountEl = meEl.siblings('.begin-amount'),
                                                val = _.str.trim(meEl.val());
                                            if (meEl.is(':visible')) {
                                                if (!val.length) {
                                                    tipText.push('截止金额不能为空');
                                                    meEl.addClass(opts.errorCls + '-field');
                                                    return false;
                                                }
                                                if (!/^-?\d+(\.\d{1,2})?$/.test(val)) { //最多保留2位小数
                                                    tipText.push('金额最多保留2位小数');
                                                    meEl.addClass(opts.errorCls + '-field');
                                                    return false;
                                                }
                                                if (val < parseFloat(_.str.trim(beginAmountEl.val()), 10)) {
                                                    tipText.push('起始金额不能大于截止金额');
                                                    meEl.addClass(opts.errorCls + '-field');
                                                    return false;
                                                }
                                                meEl.removeClass(opts.errorCls + '-field');
                                            } else {
                                                meEl.removeClass(opts.errorCls + '-field');
                                            }
                                        });
                                        rebateValueEl.each(function () {
                                            var meEl = $(this);
                                            if (meEl.is(':visible') && !_.str.trim(meEl.val()).length) {
                                                tipText.push('返点值不能为空');
                                                meEl.addClass(opts.errorCls + '-field');
                                                return false;
                                            } else {
                                                meEl.removeClass(opts.errorCls + '-field');
                                            }
                                        });
                                        if (tipText.length) {
                                            tipEl.addClass(opts.errorCls);
                                            tipEl.text(tipText.join('，'));
                                        } else {
                                            tipEl.removeClass(opts.errorCls);
                                            tipEl.empty();
                                        }
                                    });
                                    if (vm.activePanel !== "store") {
                                        selfNavEl.addClass('state-error');
                                        selfValidData.rebate = false;
                                    }
                                } else {
                                    inputEl.each(function (i) {
                                        var meEl = $(this),
                                            ffValueEl = meEl.closest('.ff-value'),
                                            tipEl = $('.ff-value-tip', ffValueEl);
                                        tipEl.removeClass(opts.errorCls).empty();
                                        meEl.removeClass(opts.errorCls + '-field');
                                    });
                                    selfValidData.rebate = true;
                                    if (selfValidData.card && selfValidData.rebate) {
                                        selfNavEl.removeClass('state-error');
                                    }
                                }
                                selfNavEl.data('valid', selfValidData);
                            },
                            "isPassed": isPassed
                        };
                    }
                }, {
                    "selector": ".integral-store-card-item .card-name,.integral-store-card-item .card-price",
                    "name": "integralStoreCard",
                    "getValue": function () {
                        var cardItemEl = $('.integral-store-card-item', avalon.getVm(settingFormId).widgetElement),
                            result = [];
                        cardItemEl.each(function () {
                            var meEl = $(this),
                                cardNameEl = $('.card-name', this),
                                cardPriceEl = $('.card-price', this),
                                consumeRebateEl = $('.consume-rebate', this),
                                rebateTypeWrapperEl = $('.rebate-type-wrapper', this),
                                rebateTypeAssistVm = avalon.getVm(rebateTypeWrapperEl.attr('id')),
                                rebateTypeVm = avalon.getVm(rebateTypeAssistVm.$rebateTypeOpts.selectId),
                                amountFixedWEl,
                                rangeRebateItemEl = $('.amount-range-wrapper .card-rebate-item', this),
                                assistVm,
                                fixedRebateSettingVm,
                                fixedRebateUnitVm,
                                awardRuleVos = [];
                            if (rebateTypeVm.selectedValue === 1) { //固定返点
                                amountFixedWEl = $('.amount-fixed-wrapper', meEl);
                                assistVm = avalon.getVm(amountFixedWEl.attr('id'));
                                fixedRebateSettingVm = avalon.getVm(assistVm.$rebateSettingOpts.selectId),
                                fixedRebateUnitVm = avalon.getVm(assistVm.$rebateUnitOpts.selectId);

                                awardRuleVos.push({
                                    "id": amountFixedWEl.data('ruleId'),
                                    "awardAmount": _.str.trim(assistVm.rebateValue),
                                    "rebateType": rebateTypeVm.selectedValue,
                                    "awardType": 2,
                                    "awardAmountType": fixedRebateSettingVm.selectedValue,
                                    "transCode": "2001"
                                });
                            } else {    //金额段返点
                                rangeRebateItemEl.each(function () {
                                    var beginAmountEl = $('.begin-amount', this),
                                        endAmountEl = $('.end-amount', this),
                                        rebateValueEl = $('.rebate-value', this),
                                        assistVm = avalon.getVm($(this).attr('id')),
                                        rebateSettingVm = avalon.getVm(assistVm.$rebateSettingOpts.selectId),
                                        rebateUnitVm = avalon.getVm(assistVm.$rebateUnitOpts.selectId);
                                    awardRuleVos.push({
                                        "id": $(this).data('ruleId'),
                                        "lowerLimit": _.str.trim(beginAmountEl.val()),
                                        "upperLimit": _.str.trim(endAmountEl.val()),
                                        "awardAmount": _.str.trim(rebateValueEl.val()),
                                        "rebateType": rebateTypeVm.selectedValue,
                                        "awardType": 2,
                                        "awardAmountType": rebateSettingVm.selectedValue,
                                        "transCode": "2001"
                                    });
                                });
                            }
                            //积分返点
                            awardRuleVos.push({
                                "id": consumeRebateEl.data('consumeRebateId'),
                                "awardAmount": _.str.trim(consumeRebateEl.val()),
                                "awardType": 1,
                                "transCode": "0002",
                                "awardAmountType": 2    //按百分比
                            });

                            result.push({
                                "cardtype": _.str.trim(cardNameEl.val()),
                                "price": _.str.trim(cardPriceEl.val()),
                                "id": meEl.data('cardId'),
                                "gradeLevel": 4,
                                "rebateType": rebateTypeVm.selectedValue,
                                "awardRuleVos": awardRuleVos,
                                "merchantId": vm.merchantId
                            });
                        });
                        return result;
                    },
                    "rule": function () {
                        var inputEl = $(this),
                            formEl = $(avalon.getVm(settingFormId).widgetElement),
                            cardItemEl = $('.integral-store-card-list .card-info-field', formEl),
                            selfNavEl = $('.module-cardrulesetting-nav-item', formEl).filter('[data-panel-name="integralStore"]'),
                            selfValidData = selfNavEl.data('valid') || {},
                            isPassed = true;
                        inputEl.each(function () {
                            var meEl = $(this);
                            if (!_.str.trim(meEl.val()).length) {
                                isPassed = false;
                                return false;
                            }
                        });
                        return {
                            "handler": function (isPassed, opts) {
                                if (!isPassed) {
                                    cardItemEl.each(function (i) {
                                        var fieldEl = $(this),
                                            cardNameEl = $('.card-name', fieldEl),
                                            cardPriceEl = $('.card-price', fieldEl),
                                            ffValueEl = $('.ff-value', fieldEl),
                                            tipEl = $('.ff-value-tip', ffValueEl),
                                            tipText = [];
                                        if (!_.str.trim(cardNameEl.val()).length) {
                                            tipText.push('卡名称不能为空');
                                            cardNameEl.addClass(opts.errorCls + '-field');
                                        } else {
                                            cardNameEl.removeClass(opts.errorCls + '-field');
                                        }
                                        if (!_.str.trim(cardPriceEl.val()).length) {
                                            tipText.push('卡单价不能为空');
                                            cardPriceEl.addClass(opts.errorCls + '-field');
                                        } else {
                                            cardPriceEl.removeClass(opts.errorCls + '-field');
                                        }
                                        if (tipText.length) {
                                            tipEl.addClass(opts.errorCls);
                                            tipEl.text(tipText.join('，'));
                                        } else {
                                            tipEl.removeClass(opts.errorCls);
                                            tipEl.empty();
                                        }
                                    });
                                    if (vm.activePanel !== "integralStore") {
                                        selfNavEl.addClass('state-error');
                                        selfValidData.card = false;
                                    }
                                } else {
                                    inputEl.each(function (i) {
                                        var meEl = $(this),
                                            ffValueEl = meEl.closest('.ff-value'),
                                            tipEl = $('.ff-value-tip', ffValueEl);
                                        tipEl.removeClass(opts.errorCls).empty();
                                        meEl.removeClass(opts.errorCls + '-field');
                                    });
                                    selfValidData.card = true;
                                    if (selfValidData.card && selfValidData.consume && selfValidData.rebate) {
                                        selfNavEl.removeClass('state-error');
                                    }
                                }
                                selfNavEl.data('valid', selfValidData);
                            },
                            "isPassed": isPassed
                        };
                    }
                }, {
                    "selector": ".integral-store-card-item .consume-rebate",
                    "rule": function () {
                        var inputEl = $(this),
                            formEl = $(avalon.getVm(settingFormId).widgetElement),
                            consumeItemEl = $('.integral-store-card-list .consume-info-field', formEl),
                            selfNavEl = $('.module-cardrulesetting-nav-item', formEl).filter('[data-panel-name="integralStore"]'),
                            selfValidData = selfNavEl.data('valid') || {},
                            isPassed = true;
                        inputEl.each(function () {
                            var meEl = $(this);
                            if (!_.str.trim(meEl.val()).length) {
                                isPassed = false;
                                return false;
                            }
                        });
                        return {
                            "handler": function (isPassed, opts) {
                                if (!isPassed) {
                                    consumeItemEl.each(function (i) {
                                        var fieldEl = $(this),
                                            consumeRebateEl = $('.consume-rebate', fieldEl),
                                            ffValueEl = $('.ff-value', fieldEl),
                                            tipEl = $('.ff-value-tip', ffValueEl),
                                            tipText = [];
                                        if (!_.str.trim(consumeRebateEl.val()).length) {
                                            tipText.push('消费返点不能为空');
                                            consumeRebateEl.addClass(opts.errorCls + '-field');
                                        } else {
                                            consumeRebateEl.removeClass(opts.errorCls + '-field');
                                        }
                                        if (tipText.length) {
                                            tipEl.addClass(opts.errorCls);
                                            tipEl.text(tipText.join('，'));
                                        } else {
                                            tipEl.removeClass(opts.errorCls);
                                            tipEl.empty();
                                        }
                                    });
                                    if (vm.activePanel !== "integralStore") {
                                        selfNavEl.addClass('state-error');
                                        selfValidData.consume = false;
                                    }
                                } else {
                                    inputEl.each(function (i) {
                                        var meEl = $(this),
                                            ffValueEl = meEl.closest('.ff-value'),
                                            tipEl = $('.ff-value-tip', ffValueEl);
                                        tipEl.removeClass(opts.errorCls).empty();
                                        meEl.removeClass(opts.errorCls + '-field');
                                    });
                                    selfValidData.consume = true;
                                    if (selfValidData.card && selfValidData.consume && selfValidData.rebate) {
                                        selfNavEl.removeClass('state-error');
                                    }
                                }
                                selfNavEl.data('valid', selfValidData);
                            },
                            "isPassed": isPassed
                        };
                    }
                }, {
                    "selector": ".integral-store-card-item .begin-amount,.integral-store-card-item .end-amount,.integral-store-card-item .rebate-value",
                    "rule": function () {
                        var inputEl = $(this),
                            formEl = $(avalon.getVm(settingFormId).widgetElement),
                            rebateItemEl = $('.integral-store-card-list .rebate-info-field', formEl),
                            selfNavEl = $('.module-cardrulesetting-nav-item', formEl).filter('[data-panel-name="integralStore"]'),
                            selfValidData = selfNavEl.data('valid') || {},
                            isPassed = true;
                        inputEl.each(function () {
                            var meEl = $(this),
                                wEl = meEl.closest('.amount-range-wrapper');
                            if (!wEl.length) {
                                wEl = meEl.closest('.amount-fixed-wrapper');
                            }
                            if (wEl.css('display') === "block" && !_.str.trim(meEl.val()).length) {
                                isPassed = false;
                                return false;
                            }
                        });
                        return {
                            "handler": function (isPassed, opts) {
                                if (!isPassed) {
                                    rebateItemEl.each(function (i) {
                                        var fieldEl = $(this),
                                            beginAmountEl = $('.begin-amount', fieldEl),
                                            endAmountEl = $('.end-amount', fieldEl),
                                            rebateValueEl = $('.rebate-value', fieldEl),
                                            ffValueEl = $('.ff-value', fieldEl),
                                            tipEl = $('.ff-value-tip', ffValueEl),
                                            tipText = [];
                                        beginAmountEl.each(function () {
                                            var meEl = $(this),
                                                val = _.str.trim(meEl.val());
                                            if (meEl.is(':visible')) {
                                                if (!val.length) {
                                                    tipText.push('起始金额不能为空');
                                                    meEl.addClass(opts.errorCls + '-field');
                                                    return false;
                                                }
                                                if (!/^-?\d+(\.\d{1,2})?$/.test(val)) { //最多保留2位小数
                                                    tipText.push('金额最多保留2位小数');
                                                    meEl.addClass(opts.errorCls + '-field');
                                                    return false;
                                                }
                                                meEl.removeClass(opts.errorCls + '-field');
                                            } else {
                                                meEl.removeClass(opts.errorCls + '-field');
                                            }
                                        });
                                        endAmountEl.each(function () {
                                            var meEl = $(this),
                                                beginAmountEl = meEl.siblings('.begin-amount'),
                                                val = _.str.trim(meEl.val());
                                            if (meEl.is(':visible')) {
                                                if (!val.length) {
                                                    tipText.push('截止金额不能为空');
                                                    meEl.addClass(opts.errorCls + '-field');
                                                    return false;
                                                }
                                                if (!/^-?\d+(\.\d{1,2})?$/.test(val)) { //最多保留2位小数
                                                    tipText.push('金额最多保留2位小数');
                                                    meEl.addClass(opts.errorCls + '-field');
                                                    return false;
                                                }
                                                if (val < parseFloat(_.str.trim(beginAmountEl.val()), 10)) {
                                                    tipText.push('起始金额不能大于截止金额');
                                                    meEl.addClass(opts.errorCls + '-field');
                                                    return false;
                                                }
                                                meEl.removeClass(opts.errorCls + '-field');
                                            } else {
                                                meEl.removeClass(opts.errorCls + '-field');
                                            }
                                        });
                                        rebateValueEl.each(function () {
                                            var meEl = $(this);
                                            if (meEl.is(':visible') && !_.str.trim(meEl.val()).length) {
                                                tipText.push('返点值不能为空');
                                                meEl.addClass(opts.errorCls + '-field');
                                                return false;
                                            } else {
                                                meEl.removeClass(opts.errorCls + '-field');
                                            }
                                        });
                                        if (tipText.length) {
                                            tipEl.addClass(opts.errorCls);
                                            tipEl.text(tipText.join('，'));
                                        } else {
                                            tipEl.removeClass(opts.errorCls);
                                            tipEl.empty();
                                        }
                                    });
                                    if (vm.activePanel !== "integralStore") {
                                        selfNavEl.addClass('state-error');
                                        selfValidData.rebate = false;
                                    }
                                } else {
                                    inputEl.each(function (i) {
                                        var meEl = $(this),
                                            ffValueEl = meEl.closest('.ff-value'),
                                            tipEl = $('.ff-value-tip', ffValueEl);
                                        tipEl.removeClass(opts.errorCls).empty();
                                        meEl.removeClass(opts.errorCls + '-field');
                                    });
                                    selfValidData.rebate = true;
                                    if (selfValidData.card && selfValidData.consume && selfValidData.rebate) {
                                        selfNavEl.removeClass('state-error');
                                    }
                                }
                                selfNavEl.data('valid', selfValidData);
                            },
                            "isPassed": isPassed
                        };
                    }
                }]
            };
            /**
             * 切换面板
             */
            vm.switchPanel = function () {
                var panelName = avalon(this).data('panelName');
                vm.activePanel = panelName;
                $('.module-cardrulesetting-nav-item', avalon.getVm(settingFormId).widgetElement).filter('[data-panel-name="' + panelName + '"]').removeClass('state-error');
            };
            /**
             * 点击关闭手柄移除对应的card item
             */
            vm.removeCardItem = function () {
                var panelName = avalon(this).data('panelName'),
                    cardItemEl = $(this).closest('.card-item'),
                    rebateTypeWEl,
                    fixedWEl,
                    assistVm,
                    atIndex = this.$vmodel.$index;
                if (panelName === "store" || panelName === "integralStore" ) {
                    $('.card-rebate-list .close-h', cardItemEl).click();
                    //清除返回类型assistVm
                    rebateTypeWEl = $('.rebate-type-wrapper', cardItemEl);
                    if (rebateTypeWEl.length) {
                        assistVm = avalon.getVm(rebateTypeWEl.attr('id'));
                        $(avalon.getVm(assistVm.$rebateTypeOpts.selectId).widgetElement).remove();
                        delete avalon.vmodels[assistVm.$id];
                    }
                    //清除固定返点assistVm
                    fixedWEl = $('.amount-fixed-wrapper', cardItemEl);
                    if (fixedWEl.length) {
                        assistVm = avalon.getVm(fixedWEl.attr('id'));
                        $(avalon.getVm(assistVm.$rebateSettingOpts.selectId).widgetElement).remove();
                        $(avalon.getVm(assistVm.$rebateUnitOpts.selectId).widgetElement).remove();
                        delete avalon.vmodels[assistVm.$id];
                    }
                }
                vm[panelName + "CardList"].removeAt(atIndex);
            };
            /**
             * 点击添加新卡规则
             */
            vm._clickAddCard = function () {
                var panelName = avalon(this).data('panelName');
                vm["add" + _.str.capitalize(panelName) + "Card"]();
            };
            /**
             * 添加不同类型卡信息接口
             */
            _.each(["fans", "integral", "store", "integralStore"], function (panelName) {
                vm["add" + _.str.capitalize(panelName) + "Card"] = function (cardData) {
                    var defaultItemData = {
                            "rebateType": 1,    //默认固定返点
                            "cardName": "",
                            "cardPrice": "",
                            "consumeRebate": "",
                            "id": 0,
                            "gradeLevel": 0,
                            "consumeRebateId": 0,
                            "showAmountRange": false    //默认显示固定返点
                        },
                        rangeRebate = [],
                        fixedRebate = null;
                    if (panelName === "store" || panelName === "integralStore") {
                        rangeRebate = (cardData && cardData.rangeRebate) || [{
                            "beginAmount": "",
                            "endAmount": "",
                            "rebateValue": "",
                            "id": 0,
                            "awardType": 2,
                            "awardAmountType": 0
                        }];
                        fixedRebate = (cardData && cardData.fixedRebate) || {
                            "rebateValue": "",
                            "id": 0,
                            "awardType": 2,
                            "awardAmountType": 0
                        };
                        //创建固定点辅助vm
                        _.extend(fixedRebate, vm._createAssistVm(fixedRebate, "fixed"));
                        //返点类型
                        _.extend(defaultItemData, vm._createAssistVm({
                            "$atIndex": vm[panelName + "CardList"].size(),
                            "$panelName": panelName,
                            "$fixedAssistId": fixedRebate.$assistId
                        }, "card"));
                    }
                    //创建金额段辅助vm
                    _.each(rangeRebate, function (itemData) {
                        _.extend(itemData, vm._createAssistVm(itemData, "range"));
                    });
                    vm[panelName + "CardList"].push(_.extend(defaultItemData, cardData, {
                        "rangeRebate": rangeRebate,
                        "fixedRebate": fixedRebate
                    }));

                    //扫描辅助模板
                    if (panelName === "store" || panelName === "integralStore") {
                        //扫描card模板
                        vm._scanRebateTmpl(defaultItemData, 'card');
                        //扫描固定返点模板
                        vm._scanRebateTmpl(fixedRebate, 'fixed');
                        //扫描新创建的金额段模板
                        _.each(rangeRebate, function (itemData) {
                            vm._scanRebateTmpl(itemData, 'range');
                        });
                    }
                };
            });
            vm._clickAddRebate = function () {
                var $index = this.$vmodel.$index,
                    panelName = avalon(this).data('panelName'),
                    currentStoreCard = vm[panelName + "CardList"][$index],
                    newRebateItemData = vm._getNewRangeRebate();
                currentStoreCard.rangeRebate.push(newRebateItemData);
                //扫描新创建金额段返点模板
                vm._scanRebateTmpl(newRebateItemData, 'range');
            };
            vm._getNewRangeRebate = function () {
                var newRebateItemData = {
                        "beginAmount": "",
                        "endAmount": "",
                        "rebateValue": "",
                        "id": 0,
                        "awardType": 2,
                        "awardAmountType": 0
                    };
                 _.extend(newRebateItemData, vm._createAssistVm(newRebateItemData, 'range'));
                return newRebateItemData;
            };
            /**
             * 点击删除对应的返点field
             */
            vm._clickRemoveRebate = function () {
                var assistId = $(this).closest('.card-rebate-item').attr('id'),
                    panelName = avalon(this).data('panelName');
                _.some(vm[panelName + "CardList"].$model, function (itemData, i) {
                    return _.some(itemData.rangeRebate, function (itemData2, j) {
                        var assistVm,
                            rebateSettingVm,
                            rebateUnitVm;
                        if (itemData2.$assistId == assistId) {
                            assistVm = avalon.getVm(assistId);
                            rebateSettingVm = avalon.getVm(assistVm.$rebateSettingOpts.selectId);
                            rebateUnitVm = avalon.getVm(assistVm.$rebateUnitOpts.selectId);
                            $(rebateSettingVm.widgetElement).remove();
                            $(rebateUnitVm.widgetElement).remove();
                            delete avalon.vmodels[assistId];
                            vm[panelName + "CardList"][i].rangeRebate.removeAt(j);
                            return true;
                        }
                    });
                });
            };
            /**
             * 根据返点信息创建返点vm
             */
            vm._createAssistVm = function (assistData, assistType) {
                var assistId = data.cardrulesettingId  + '-' + assistType + '-' + assistVmIndex,
                    assistVm;
                if (assistType === "range") {
                    assistVm = avalon.define(assistId, function (vm) {
                        avalon.mix(vm, assistData);
                        vm.$assistId = assistId;
                        vm.$rebateSettingOpts = {
                            "selectId": assistId + "-rebate-setting",
                            "options": [{
                                "text": "按百分比",
                                "value": 2
                            }, {
                                "text": "按固定值",
                                "value": 1
                            }],
                            "onSelect": function (selectedValue) {
                                var rebateUnitVm = avalon.getVm(assistId + "-rebate-unit");
                                if (selectedValue === 2) {
                                    rebateUnitVm.setOptions([{
                                        "text": "%储值",
                                        "value": 1
                                    }, {
                                        "text": "%积分",
                                        "value": 2
                                    }]);
                                } else {
                                    rebateUnitVm.setOptions([{
                                        "text": "储值",
                                        "value": 1
                                    }, {
                                        "text": "积分",
                                        "value": 2
                                    }]);
                                }

                            }
                        };
                        vm.$rebateUnitOpts = {
                            "selectId": assistId + "-rebate-unit",
                            "options": [{
                                "text": "%储值",
                                "value": 1
                            }, {
                                "text": "%积分",
                                "value": 2
                            }]
                        };
                    });
                } else if (assistType === "fixed") {    //固定返点
                    assistVm = avalon.define(assistId, function (vm) {
                        avalon.mix(vm, assistData);
                        vm.$assistId = assistId;
                        vm.$rebateSettingOpts = {
                            "selectId": assistId + "-rebate-setting",
                            "options": [{
                                "text": "按百分比",
                                "value": 2
                            }, {
                                "text": "按固定值",
                                "value": 1
                            }],
                            "onSelect": function (selectedValue) {
                                var rebateUnitVm = avalon.getVm(assistId + "-rebate-unit");
                                if (selectedValue === 2) {
                                    rebateUnitVm.setOptions([{
                                        "text": "%储值",
                                        "value": 1
                                    }, {
                                        "text": "%积分",
                                        "value": 2
                                    }]);
                                } else {
                                    rebateUnitVm.setOptions([{
                                        "text": "储值",
                                        "value": 1
                                    }, {
                                        "text": "积分",
                                        "value": 2
                                    }]);
                                }

                            }
                        };
                        vm.$rebateUnitOpts = {
                            "selectId": assistId + "-rebate-unit",
                            "options": [{
                                "text": "%储值",
                                "value": 1
                            }, {
                                "text": "%积分",
                                "value": 2
                            }]
                        };
                    });
                } else if (assistType === "card") {
                    assistVm = avalon.define(assistId, function (vm) {
                        avalon.mix(vm, assistData);
                        vm.$assistId = assistId;
                        vm.$rebateTypeOpts = {
                            "selectId": assistId + "-rebate-type",
                            "options": [{
                                "text": "固定返点",
                                "value": 1
                            }, {
                                "text": "金额段",
                                "value": 2
                            }],
                            "onSelect": function (selectedValue) {
                                var newRebateItemData,
                                    currentRangeRebate = vmodel[vm.$panelName + 'CardList'][vm.$atIndex];
                                if (selectedValue === 2) {
                                    //vm.showAmountRange = true;
                                    currentRangeRebate.showAmountRange = true;
                                    avalon.getVm(vm.$fixedAssistId).rebateValue = "";
                                } else {
                                    //vm.showAmountRange = false;
                                    currentRangeRebate.showAmountRange = false;
                                    //清理金额段
                                    _.each(currentRangeRebate.rangeRebate.$model, function (itemData) {
                                        var assistVm = avalon.getVm(itemData.$assistId),
                                            rebateSettingVm = avalon.getVm(assistVm.$rebateSettingOpts.selectId),
                                            rebateUnitVm = avalon.getVm(assistVm.$rebateUnitOpts.selectId);
                                        $(rebateSettingVm.widgetElement).remove();
                                        $(rebateUnitVm.widgetElement).remove();
                                        delete avalon.vmodels[itemData.$assistId];
                                    });
                                    currentRangeRebate.rangeRebate.clear();
                                    //默认加一个
                                    newRebateItemData = vmodel._getNewRangeRebate();
                                    currentRangeRebate.rangeRebate.push(newRebateItemData);
                                    vmodel._scanRebateTmpl(newRebateItemData, 'range');
                                    //vm.beginAmount = "";
                                    //vm.endAmount = "";
                                }
                                //清空验证提醒
                                $(avalon.getVm(assistId + "-rebate-type").widgetElement).closest('.ff-value').find('.ff-value-tip').empty().removeClass('valid-error');
                            }
                        };
                    });
                }
                assistVmIndex++;
                return {
                    "$assistVm": assistVm,
                    "$assistId": assistId
                };
            };
            /**
             * 根据返点信息扫描返点模板
             */
            vm._scanRebateTmpl = function (assistData, assistType) {
                var assistId = assistData.$assistId,
                    assistVm = assistData.$assistVm,
                    rebateTypeVm,
                    rebateSettingVm,
                    hostEl = $('#' + assistId);
                hostEl.children().removeAttr('ms-skip').removeClass('fn-hide');
                avalon.scan(hostEl[0], [assistVm]);
                //设置默认值
                if (assistType === "fixed" || assistType === "range") {
                    rebateSettingVm = avalon.getVm(assistVm.$rebateSettingOpts.selectId);
                    rebateSettingVm.selectValue(assistData.awardAmountType);
                } else if (assistType === "card") {
                    rebateTypeVm = avalon.getVm(assistVm.$rebateTypeOpts.selectId);
                    rebateTypeVm.selectValue(assistData.rebateType);
                }

            };
            vm.open = function () {
                avalon.getVm(settingDialogId).open();
            };
            vm.close = function () {
                avalon.getVm(settingDialogId).close();
            };
            /**
             * 设置只读状态
             * @param {Boolean} readonly true表示设置成只读状态
             */
            vm.setReadonly = function (readonly) {
                vm.readonly = !!readonly;
            };
            vm.$init = function() {
                elEl.addClass('module-cardrulesetting');
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm.$remove = function() {
                elEl.removeClass('module-cardrulesetting').off().empty();
            };
            vm.$skipArray = ["widgetElement"];
        });

        return vmodel;
    };
    module.version = 1.0;
    module.defaults = {
        "merchantId": 0,
        "onUpdateCallback": avalon.noop
    };
    //私有函数
    function getGradeLevelFromPanelName (panelName) {
        var gradeLevel = 0;
        switch (panelName) {
            case "fans":
                gradeLevel = 1;
                break;
            case "integral":
                gradeLevel = 2;
                break;
            case "store":
                gradeLevel = 3;
                break;
            case "integralStore":
                gradeLevel = 4;
                break;
            default:
                break;
        }
        return gradeLevel;
    }

    function getPanelNameFromGradeLevel (gradeLevel) {
        var panelName = '';
        switch (gradeLevel) {
            case 1:
                panelName = "fans";
                break;
            case 2:
                panelName = "integral";
                break;
            case 3:
                panelName = "store";
                break;
            case 4:
                panelName = "integralStore";
                break;
            default:
                break;
        }
        return panelName;
    }
    return avalon;
});
