/**
 * 申请开卡模块
 */
define(["avalon", "util", "json", "moment", "text!./opencard.html", "text!./opencard.css", "common", "../../widget/dialog/dialog"], function (avalon, util, JSON, moment, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    var styleData = styleEl.data('module') || {};

    if (!styleData["opencard"]) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'opencard/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'opencard/');
        }
        styleData["opencard"] = true;
        styleEl.data('module', styleData);
    }
    var module = erp.module.opencard = function (element, data, vmodels) {
        var opts = data.opencardOptions,
            elEl = $(element);
        var getTypeId = function (n) {//设置和获取widget的id;
            if(!erp.openCard){
                erp.openCard = [];
            }
            erp.openCard.push(data.opencardId + n);
            return data.opencardId + n;
        };



        var modelVm = avalon.define(data.opencardId, function (vm) {
            avalon.mix(vm, opts);
            var count = 0;
            function setVipSelectName() {
                count++;
                var name = getTypeId('vipSelectId'+count);
                return name;

            }
            //会员等级
            var vipArr = [];
            //商户名称
            vm.merchantName = '';
            vm.readonly = false;
            //发送后端数据
            vm.processId = 0;
            vm.merchantId = 0;
            vm.fesOpenCardApplicationDtls = [];
            //发送后端数据
            vm.$addCardDialogOpts = {//对话框dialog
                "dialogId": getTypeId('-opencardDialogId'),
                "title":"开卡申请",
                "width": 500,
                "getTemplate": function (tmpl) {
                    var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                        footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                            '<div class="ui-dialog-btns" ms-visible="!readonly">' +
                            '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">保&nbsp;存</button>' +
                            '<span class="separation"></span>' +
                            '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button>' +
                            '</div>' +
                            '<div class="ui-dialog-btns" ms-visible="readonly">' +
                            '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">关&nbsp;闭</button>' +
                            '</div>' +
                            '</div>';
                    return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                },
                "onOpen": function () {
                },
                "onClose": function () {
                    vm.fesOpenCardApplicationDtls = [];
                },
                "submit": function (evt) {
                    var flag;
                    var dialog = avalon.getVm(getTypeId('-opencardDialogId'));
                    flag = util.testing($(dialog.widgetElement)[0]);
                    if(flag){
                        var data = vm.fesOpenCardApplicationDtls.$model;
                        for(var i=0;i<data.length;i++){
                            data[i].memberLevel = avalon.getVm(data[i].memberLevel).selectedValue;
                            delete data[i].$memberLevelOpts;
                        }
                        var requestData = {
                            processId : vm.processId,
                            merchantId : vm.merchantId,
                            fesOpenCardApplicationDtls : data
                        };
                        util.c2s({
                            "url": erp.BASE_PATH + "applySetting/saveOpenCardApply.do",
                            "type": "post",
                            "contentType": 'application/json',
                            "data":  JSON.stringify(requestData),
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    vm.callFn(responseData);
                                    avalon.getVm(getTypeId('-opencardDialogId')).close();
                                }
                            }
                        });
                    }else{//处理跳转到错误的位置
                        var errorEle = $(dialog.widgetElement).find('.valid-error')[0];
                        var pos1 = $(errorEle).closest('.ff-value').position().top;
                        var pos2 = $(errorEle).closest('.model').position().top;
                        var elePosition = pos1+pos2+$('.open-card-con',dialog.widgetElement)[0].scrollTop;
                        $('.open-card-con').scrollTop(elePosition>0?elePosition:0);
                    }

                }
            };

            vm.open = function () {
                util.c2s({
                    "url": erp.BASE_PATH + "applySetting/loadOpenCardApply.do",
                    "type": "post",
                    "contentType": 'application/json',
                    "data": JSON.stringify({"processId": vm.processId}),
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            vipArr = data.membershipDic;
                            if(vm.readonly){
                                function getText(n){
                                    for(var i=0;i<vipArr.length;i++){
                                        if(n == vipArr[i].value){
                                            return vipArr[i].text
                                        }
                                    }
                                }
                                var fesOpenCardApplicationDtls = data.fesOpenCardApplicationDtls;
                                for(var i=0;i<fesOpenCardApplicationDtls.length;i++){
                                    fesOpenCardApplicationDtls[i].memberLevel = getText(fesOpenCardApplicationDtls[i].memberLevel);
                                }
                                vm.fesOpenCardApplicationDtls = data.fesOpenCardApplicationDtls
                            }else{
                                var name = setVipSelectName();
                                var modelObj = {
                                    cardName: '',
                                    cardTag: '',
                                    cardAmount: '',
                                    validityTerm: '',
                                    isContainFour: '0',
                                    isContainSeven: '0',
                                    memberLevel:name,
                                    $memberLevelOpts:{
                                        selectId:name,
                                        options:vipArr
                                    }
                                };
                                vm.fesOpenCardApplicationDtls.push(modelObj);
                            }
                            avalon.getVm(getTypeId('-opencardDialogId')).open();

                        }
                    }
                });

            };
            vm.addCar = function(){
                var name = setVipSelectName();
                var modelObj = {
                    cardName: '',
                    cardTag: '',
                    cardAmount: '',
                    validityTerm: '',
                    isContainFour: '0',
                    isContainSeven: '0',
                    memberLevel:name,
                    $memberLevelOpts:{
                        selectId:name,
                        options:vipArr
                    }
                };
                vm.fesOpenCardApplicationDtls.push(modelObj);
                $('.open-card-con').scrollTop($('.open-card-con')[0].scrollHeight);
            };
            vm.remove = function(el){
                vm.fesOpenCardApplicationDtls.remove(el);
            };

            vm.$init = function () {
                elEl.addClass('module-opencard');
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [modelVm].concat(vmodels));
            };
            vm.$remove = function () {
                _.each(erp.openCard, function (widgetId) {
                    var vm = avalon.getVm(widgetId);
                    vm && $(vm.widgetElement).remove();
                });
                erp.openCard.length = 0;
                elEl.removeClass('module-opencard').off().empty();
            };
        });
        return modelVm;
    };
    module.defaults = {
        readonly : false,
        processId : 0,
        merchantId : 0,
        merchantName : '',
        callFn: avalon.noop
    };
    return avalon;
});
/*申请开卡模块-end*/