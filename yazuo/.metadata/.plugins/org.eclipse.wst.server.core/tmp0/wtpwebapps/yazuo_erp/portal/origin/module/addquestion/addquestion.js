/**
 * 申请开卡模块
 */
define(["avalon", "util", "json", "moment", "text!./addquestion.html", "text!./addquestion.css", "common", '../../widget/form/form', '../../widget/autocomplete/autocomplete', "../../widget/dialog/dialog"], function (avalon, util, JSON, moment, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    var styleData = styleEl.data('module') || {};

    if (!styleData["addquestion"]) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'addquestion/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'addquestion/');
        }
        styleData["addquestion"] = true;
        styleEl.data('module', styleData);
    }
    var module = erp.module.addquestion = function (element, data, vmodels) {
        var opts = data.addquestionOptions,
            elEl = $(element);
        var getTypeId = function (n) {//设置和获取widget的id;
            if(!erp.addquestion){
                erp.addquestion = [];
            }
            erp.addquestion.push(data.addquestionId + n);
            return data.addquestionId + n;
        };



        var modelVm = avalon.define(data.addquestionId, function (vm) {
            avalon.mix(vm, opts);

            vm.addType = 1;//1是需要搜索商户名称，0是不需要商户名称
            vm.merchantId = 0;
            vm.merchantName = '';


            /*添加问题*/
            vm.$brandOpts = {
                "autocompleteId": getTypeId('brandId'),
                "placeholder": "请指定商户",
                "delayTime": 300,   //延时300ms请求
                "onChange": function (text, callback) {
                    if (text.length) {
                        util.c2s({
                            "url": erp.BASE_PATH + "synMerchant/getSynMerchantInfo.do",
                            // "contentType": "application/json",
                            "data": {
                                "merchantName": text
                            },
                            "success": function (responseData) {
                                var rows;
                                if (responseData.flag) {
                                    rows = responseData.data;
                                    callback(_.map(rows, function (itemData) {
                                        itemData.text = itemData.merchant_name || "未知";
                                        itemData.value = itemData.merchant_id;
                                        return itemData;
                                    }));
                                }
                            }
                        }, {
                            "mask": false
                        });
                    } else {
                        callback([]);   //空查询显示空
                    }
                },
                "onSelect": function (selectedValue) {
                    var marchantId = avalon.getVm(getTypeId('brandId')).getOptionByValue(selectedValue).value;
                    vm.merchantId = marchantId;
                }
            };

            vm.$addQuestionDialogOpts = {//弹框初始化
                "dialogId": getTypeId('addQuestionDialogId'),
                'title': '添加问题',
                "getTemplate": function (tmpl) {
                    var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                        footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                            '<div class="ui-dialog-btns">' +
                            '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">保&nbsp;存</button>' +
                            '<span class="separation"></span>' +
                            '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button>' +
                            '</div>' +
                            '</div>';
                    return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                },
                "onOpen": function () {},
                "onClose": function () {
                    //清除验证信息
                    var formVm = avalon.getVm(getTypeId('addQuestionFormId'));
                    formVm.reset();
                    formVm.merchantId='';
                    formVm.title='';
                    formVm.description='';
                },
                "submit": function (evt) {
                    var requestData,
                        dialogVm = avalon.getVm(getTypeId('addQuestionDialogId')),
                        formVm = avalon.getVm(getTypeId('addQuestionFormId'));
                    if (formVm.validate()) {
                        var productName = avalon.getVm(getTypeId('scheduleId')).selectedValue;
                        var source = avalon.getVm(getTypeId('scheduleFormId')).selectedValue;
                        requestData = formVm.getFormData();
                        requestData.merchantId = vm.merchantId;
                        requestData.productName=productName;
                        requestData.source=source;
                        requestData.problemStatus  = 1;
                        requestData=JSON.stringify(requestData);
                        //发送后台请求，编辑或添加
                        util.c2s({
                            "url": erp.BASE_PATH + 'sysProblem/saveSysProblem.do',
                            "type": "post",
                            "contentType" : 'application/json',
                            "data": requestData,
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    //关闭弹框
                                    dialogVm.close();
                                }
                            }
                        });
                    }
                }
            };

            vm.$addQuestionFormOpts = {//form表单初始化
                "formId": getTypeId('addQuestionFormId'),
                "field":
                    [
                        {
                            "selector": ".merchantId",
                            "rule": ["noempty", function (val, rs) {
                                if (!vm.merchantId) {
                                    return "商户名称不能为空";
                                } else {
                                    return true;
                                }
                            }]
                        },
                        {
                            "selector": ".title",
                            "rule": ["noempty", function (val, rs) {
                                if (rs[0] !== true) {
                                    return "问题标题不能为空";
                                } else {
                                    return true;
                                }
                            }]
                        },
                        {
                            "selector": ".description",
                            "rule": ["noempty", function (val, rs) {
                                if (rs[0] !== true) {
                                    return "问题类型不能为空";
                                } else {
                                    return true;
                                }
                            }]
                        }
                    ],
                "merchantId":"",
                "title":"",
                "description":""
            };
            vm.$scheduleOpts = {//产品名称
                "selectId": getTypeId('scheduleId'),
                "options": [],
                "selectedIndex": 1
            };
            vm.$formOpts = {//问题来源
                "selectId": getTypeId('scheduleFormId'),
                "options": [],
                "selectedIndex": 1
            };
            vm.open = function(){
                util.c2s({
                    "url": erp.BASE_PATH + 'sysProblem/loadSysProblem.do',
                    "type": "post",
                    "contentType" : 'application/json',
                    "data": JSON.stringify({}),
                    "success": function (responseData) {
                        if (responseData.flag) {
                            var data = responseData.data;
                            avalon.getVm(getTypeId('scheduleId')).setOptions(data.productName);
                            avalon.getVm(getTypeId('scheduleFormId')).setOptions(data.source);
                            avalon.getVm(getTypeId('addQuestionDialogId')).open();
                        }
                    }
                });
            };

            /*添加问题结束*/

            vm.$init = function () {
                elEl.addClass('module-addquestion');
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [modelVm].concat(vmodels));
            };
            vm.$remove = function () {
                _.each(erp.addquestion, function (widgetId) {
                    var vm = avalon.getVm(widgetId);
                    vm && $(vm.widgetElement).remove();
                });
                erp.addquestion.length = 0;
                elEl.removeClass('module-addquestion').off().empty();
            };
        });
        return modelVm;
    };
    module.defaults = {
        addType : 1,
        merchantId : 0,
        merchantName : '',
        callFn : avalon.noop
    };
    return avalon;
});
/*申请开卡模块-end*/