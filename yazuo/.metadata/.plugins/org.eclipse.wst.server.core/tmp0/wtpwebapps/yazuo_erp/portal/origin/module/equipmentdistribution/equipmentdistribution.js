/**
 * 指派需求
 */
define(["avalon", "util", "json", "moment", "text!./equipmentdistribution.html", "text!./equipmentdistribution.css", "common", "../../widget/dialog/dialog", "../../widget/form/select", '../../module/address/address'], function (avalon, util, JSON, moment, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    var styleData = styleEl.data('module') || {};

    if (!styleData["equipmentdistribution"]) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'equipmentdistribution/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'equipmentdistribution/');
        }
        styleData["equipmentdistribution"] = true;
        styleEl.data('module', styleData);
    }
    var module = erp.module.equipmentdistribution = function (element, data, vmodels) {
        var opts = data.equipmentdistributionOptions,
            elEl = $(element);

        var modelvm = avalon.define(data.equipmentdistributionId, function (vm) {
            var getTypeId = function (n) {//设置和获取widget的id;
                if(!erp.model){
                    erp.modelEquipmentDistribution = [];
                }
                erp.modelEquipmentDistribution.push(data.equipmentdistributionId + n);
                return data.equipmentdistributionId + n;
            };
            avalon.mix(vm, opts);
            vm.readonly = false;
            vm.merchantId = 0;
            vm.processId = 0;

            //累加计数
            var count = 0;
            //设备名称
            var category = [];
            //收件人，短信通知人
            var contacts = [];
            //快递公司
            var logisticCompany = [];
            //用于保存收件人和短信通知人
            var personArr = ['-selectSeizeOpts','-selectMessOpts'];
            //发送后台参数
            vm.requestData = [
                {
                    "id" : "",
                    "processId" : vm.processId,  //流程ID
                    "logisticsCompany" : "", //物流公司
                    "logisticsNum" : "",//快递单号
                    "contactId" : 0,//联系人ID
                    "recipient" : "",//收件人ID
                    "fesStowageDtls" : [
                        {
                            "category" : "", //设备名称value
                            "amount" : 1,//设备数量
                            "unitType" : "" //单位
                        }
                    ]
                }
            ];
            vm.$equipmentdistributionOpts = {//对话框dialog
                "dialogId": getTypeId('-dialog'),
                "width": 500,
                "title": "设备配送明细",
                "getTemplate": function (tmpl) {
                    var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                        footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                            '<div class="ui-dialog-btns" ms-if="!readonly">' +
                            '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">保&nbsp;存</button>' +
                            '<span class="separation"></span>' +
                            '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button>' +
                            '</div>' +
                            '<div class="ui-dialog-btns" ms-if="readonly">' +
                            '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">关&nbsp;闭</button>' +
                            '</div>' +
                            '</div>';
                    return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                },
                "onOpen": function () {
                },
                "onClose": function () {
                    var dialog = avalon.getVm(getTypeId('-dialog'));
                    $('.remove-button').click(function(){
                        var count = $(this).attr('data-count');
                        var num = $(this).attr('data-num');
                        var outerIndex = $(this).attr('data-outer-index');
                        var innerIndex = $(this).attr('data-inner-index');
                        avalon.getVm(getTypeId('-selectProcessOpts'+num+count))&&$(avalon.getVm(getTypeId('-selectProcessOpts'+num+count)).widgetElement).remove();
                        delete avalon.vmodels['model'+num+count];
                        $('.model'+num+count,'.module-equipment-distribution').remove();
                        if(vm.requestData[outerIndex].fesStowageDtls instanceof Array){
                            vm.requestData[outerIndex].fesStowageDtls[innerIndex] = '';
                            vm.requestData[outerIndex].$model.fesStowageDtls[innerIndex] = '';
                        }
                    });
                    $('.remove-button').trigger('click');

                    $('.remove-all').click(function(){
                        var num = $(this).attr('data-num');
                        var outerIndex = $(this).attr('data-outer-index');
                        vm.requestData[outerIndex] = '';
                        vm.requestData.$model[outerIndex] = '';
                        avalon.getVm(getTypeId('-selectProcessOpts'+num+count))&&$(avalon.getVm(getTypeId('-selectProcessOpts'+num+count)).widgetElement).remove();
                        $('.model'+num).remove();
                        delete avalon.vmodels['model'+num];
                    });
                    $('.remove-all').trigger('click');
                    util.testCancel($(dialog.widgetElement)[0]);


                },
                "submit": function (evt) {
                    var flag;
                    var dialog = avalon.getVm(getTypeId('-dialog'));
                    flag = util.testing($(dialog.widgetElement)[0]);
                    var requestData = vm.requestData.$model;
                    for(var i = requestData.length;i>0;i--){
                        if(requestData[i-1] == ''){
                            requestData.splice(i-1,1);
                        }else{
                            var len = requestData[i-1].fesStowageDtls;
                            for(var j = len.length;j>0;j--){
                                if(requestData[i-1].fesStowageDtls[j-1] == ''){
                                    requestData[i-1].fesStowageDtls.splice(j-1,1);
                                }
                            }
                        }
                    }
                    if(flag){
                        util.c2s({
                            "url": erp.BASE_PATH + "fesStowage/saveFesStowages.do",
                            "type": "post",
                            "contentType" : 'application/json',
                            "data":  JSON.stringify(requestData),
                            "success": function (responseData) {
                                if(responseData.flag){
                                    avalon.getVm(getTypeId('-dialog')).close();
                                    vm.callFn(responseData);
                                }
                            }
                        });
                    }else{//处理跳转到错误的位置
                        var errorEle = $(dialog.widgetElement).find('.valid-error')[0];
                        var pos1 = $(errorEle).closest('.ff-value').position().top;
                        var pos2 = $(errorEle).closest('.model').position().top;
                        var elePosition = pos1+pos2+$('.content',dialog.widgetElement)[0].scrollTop;
                        $('.content').scrollTop(elePosition>0?elePosition:0);
                    }

                }
            };
            //设备名称
            vm.$selectProcessOpts = {
                "selectId" : getTypeId('-selectProcessOpts'),
                "options" : [],
                "onSelect" : function(v,t,o){
                    vm.requestData[0].fesStowageDtls[0].category = v;
                    $('.unit0').html(o.unit);
                }
            };
            //快递公司
            vm.$selectExpressOpts = {
                "selectId" : getTypeId('-selectExpressOpts'),
                "options" : [],
                "onSelect" : function(v,t,o){
                    vm.requestData[0].logisticsCompany = v;
                }
            };
            //收件人
            vm.$selectSeizeOpts = {
                "selectId" : getTypeId('-selectSeizeOpts'),
                "options" : [],
                "getTemplate": function (tmpl) {
                    var tmps = tmpl.split('MS_OPTION_HEADER');
                    tmpl = 'MS_OPTION_HEADER<ul class="select-list" ms-on-click="selectItem"ms-css-min-width="minWidth">' +
                        '<li class="select-item" ms-repeat="options" ms-css-min-width="minWidth - 16"' +
                        'ms-class="state-selected:$index==selectedIndex" ms-class-last-item="$last" ms-hover="state-hover" ' +
                        'ms-data-index="$index" ms-attr-title="el.text" ms-data-value="el.value">{{el.text}}</li>' +
                        '<li><a style="display: block;line-height: 35px;background: #eee;border-top: 1px solid #ccc;cursor: pointer;text-indent: 10px;" data-num="-selectSeizeOpts" ms-click=addCommunication href="javascript:;">添加联系人</a></li>' +
                        '</ul>';
                    return tmps[0] + tmpl;
                },
                "onSelect" : function(v,t,o){
                    vm.requestData[0].recipient = v;
                }
            };
            //短信通知人
            vm.$selectMessOpts = {
                "selectId" : getTypeId('-selectMessOpts'),
                "options" : [],
                "getTemplate": function (tmpl) {
                    var tmps = tmpl.split('MS_OPTION_HEADER');
                    tmpl = 'MS_OPTION_HEADER<ul class="select-list" ms-on-click="selectItem"ms-css-min-width="minWidth">' +
                        '<li class="select-item" ms-repeat="options" ms-css-min-width="minWidth - 16"' +
                        'ms-class="state-selected:$index==selectedIndex" ms-class-last-item="$last" ms-hover="state-hover" ' +
                        'ms-data-index="$index" ms-attr-title="el.text" ms-data-value="el.value">{{el.text}}</li>' +
                        '<li><a style="display: block;line-height: 35px;background: #eee;border-top: 1px solid #ccc;cursor: pointer;text-indent: 10px;"  data-num="-selectMessOpts" ms-click=addCommunication href="javascript:;">添加联系人</a></li>' +
                        '</ul>';
                    return tmps[0] + tmpl;
                },
                "onSelect" : function(v,t,o){
                    vm.requestData[0].contactId = v;
                }
            };
            vm.open = function () {
                util.c2s({
                    "url": erp.BASE_PATH + "fesStowage/loadFesStowage.do",
                    "type": "post",
                    "contentType" : 'application/json',
                    "data":  JSON.stringify({"merchantId" : vm.merchantId}),
                    "success": function (responseData) {
                        if(responseData.flag){
                            vm.requestData[0].processId = vm.processId;
                            var data = responseData.data;
                            //设备名称
                            category = data.category;
                            avalon.getVm(getTypeId('-selectProcessOpts')).setOptions(category);
                            //收件人，短信通知人
                            contacts = data.contacts;
                            avalon.getVm(getTypeId('-selectSeizeOpts')).setOptions(contacts);
                            avalon.getVm(getTypeId('-selectMessOpts')).setOptions(contacts);
                            //快递公司
                            logisticCompany = data.logisticCompany;
                            avalon.getVm(getTypeId('-selectExpressOpts')).setOptions(logisticCompany);
                            avalon.getVm(getTypeId('-dialog')).open();
                        }
                    }
                });

            };
            //添加多个设备名称
            vm.addInner = function(){
                count++;//累加记录（不重复）
                var num = $(this).attr('data-num');//外层循环
                var outerIndex = $(this).attr('data-outer-index');
                var innerIndex = vm.requestData[outerIndex].fesStowageDtls.length;
                var innerObj = {
                    "category" : "", //设备名称value
                    "amount" : 1,//设备数量
                    "unitType" : "" //单位
                };
                vm.requestData[outerIndex].fesStowageDtls.push(innerObj);
                var model = '<div class="select-model model'+num+count+'" ms-controller="model'+num+count+'"><span class="schedule" ms-widget="select,$,$selectProcessOpts'+num+count+'"></span><input class="input-text card-number" ms-duplex="requestData['+outerIndex+'].fesStowageDtls['+innerIndex+'].amount" placeholder="数量" data-rules="empty" data-tips="数量不能为空！" isrule=true><span class="unit unit'+num+count+'"></span><b class="card-add remove-button" data-num='+num+' data-count='+count+' data-outer-index='+outerIndex+' data-inner-index='+innerIndex+' ms-click="removeInner">-</b><div class="ff-value-tip"></div></div>';
                $('.inner'+num,'.module-equipment-distribution').append(model);
                avalon.define('model'+num+count,function(vm){
                    vm.num = num;
                    vm.count = count;
                    vm['$selectProcessOpts'+num+count] = {
                        "selectId": getTypeId('selectProcessOpts'+num+count),
                        "options": [],
                        "onSelect": function (v, t, o) {
                            modelvm.requestData[outerIndex].fesStowageDtls[innerIndex].category = v;
                            $('.unit'+vm.num+vm.count).html(o.unit);
                        }
                    };
                });
                avalon.scan($('.module-equipment-distribution').eq(0)[0],[vm]);
                avalon.getVm(getTypeId('selectProcessOpts'+num+count)).setOptions(category);
            };
            //移除一个设备名称
            vm.removeInner = function(){
                var count = $(this).attr('data-count');
                var num = $(this).attr('data-num');
                var outerIndex = $(this).attr('data-outer-index');
                var innerIndex = $(this).attr('data-inner-index');
                avalon.getVm(getTypeId('-selectProcessOpts'+num+count))&&$(avalon.getVm(getTypeId('-selectProcessOpts'+num+count)).widgetElement).remove();
                delete avalon.vmodels['model'+num+count];
                $('.model'+num+count,'.module-equipment-distribution').remove();
                if(vm.requestData[outerIndex].fesStowageDtls instanceof Array){
                    vm.requestData[outerIndex].fesStowageDtls[innerIndex] = '';
                    vm.requestData[outerIndex].$model.fesStowageDtls[innerIndex] = '';
                }
            };
            //添加多个设备明细
            vm.addOuter = function(){
                var outerObj = {
                    "id" : "",
                    "processId" : vm.processId,  //流程ID
                    "logisticsCompany" : "", //物流公司
                    "logisticsNum" : "",//快递单号
                    "contactId" : 0,//联系人ID
                    "recipient" : "",//收件人ID
                    "fesStowageDtls" : [
                        {
                            "category" : "", //设备名称value
                            "amount" : 1,//设备数量
                            "unitType" : "" //单位
                        }
                    ]
                };
                vm.requestData.push(outerObj);
                var outerIndex = vm.requestData.length-1;
                count++;
                var num = ($('.content').children('div').length+1).toString();
                var model = '<div class="model model'+num+count+'" ms-controller="model'+num+count+'"><table class="outer'+num+'">'+
                                '<tr class="f-field ff-value">'+
                                    '<td class="table-let"><span class="warn">*</span>设备名称：</td>'+
                                    '<td class="inner'+num+'">'+
                                        '<div class="select-model model'+num+count+'">'+
                                            '<span class="schedule" ms-widget="select,$,$selectProcessOpts'+num+count+'"></span><input class="input-text card-number" ms-duplex="requestData['+outerIndex+'].fesStowageDtls[0].amount" placeholder="数量" data-rules="empty" data-tips="数量不能为空！" isrule=true><span class="unit unit'+num+0+'">个</span><b class="card-add" data-num="'+num+'" data-outer-index="'+outerIndex+'" ms-click="addInner">+</b><div class="ff-value-tip"></div>'+
                                        '</div>'+
                                    '</td>'+
                                '</tr>'+
                                '<tr>'+
                                    '<td class="table-let"><span class="warn">*</span>快递公司：</td>'+
                                    '<td><span class="schedule width-select" ms-widget="select,$,$selectExpressOpts'+num+count+'"></span></td>'+
                                '</tr>'+
                                '<tr class="f-field ff-value">'+
                                     '<td class="table-let"><span class="warn">*</span>快递单号：</td>'+
                                     '<td><input type="text" class="input-text width-select" data-rules="empty" data-tips="快递单号不能为空！" isrule=true ms-duplex="requestData['+outerIndex+'].logisticsNum"><div class="ff-value-tip"></div></td>'+
                                '</tr>'+
                                '<tr>'+
                                     '<td class="table-let"><span class="warn">*</span>收件人：</td>'+
                                     '<td><span class="schedule width-select" ms-widget="select,$,$selectSeizeOpts'+num+count+'"></span></td>'+
                                '</tr>'+
                                '<tr>'+
                                     '<td class="table-let"><span class="warn">*</span>短信通知：</td>'+
                                '<td><span class="schedule width-select" ms-widget="select,$,$selectMessOpts'+num+count+'"></span></td>'+
                                '</tr>'+
                             '</table><i class="iconfont remove-all" data-outer-index="'+outerIndex+'" data-num="'+num+count+'" ms-click="removeOuter">&#xe616;</i></div>';
                $('.content','.module-equipment-distribution').append(model);
                avalon.define('model'+num+count,function(vm){
                    //设备名称
                    vm['$selectProcessOpts'+num+count] = {
                        "selectId": getTypeId('-selectProcessOpts'+num+count),
                        "options": [],
                        "onSelect": function (v, t, o) {
                            modelvm.requestData[outerIndex].fesStowageDtls[0].category = v;
                            $('.unit'+num+0).html(o.unit);
                        }
                    };
                    //快递公司
                    vm['$selectExpressOpts'+num+count] = {
                        "selectId" : getTypeId('-selectExpressOpts'+num+count),
                        "options" : [],
                        "onSelect" : function(v,t,o){
                            modelvm.requestData[outerIndex].logisticsCompany = v;
                        }
                    };
                    //收件人
                    vm['$selectSeizeOpts'+num+count] = {
                        "selectId" : getTypeId('-selectSeizeOpts'+num+count),
                        "options" : [],
                        "getTemplate": function (tmpl) {
                            var tmps = tmpl.split('MS_OPTION_HEADER');
                            tmpl = 'MS_OPTION_HEADER<ul class="select-list" ms-on-click="selectItem"ms-css-min-width="minWidth">' +
                                '<li class="select-item" ms-repeat="options" ms-css-min-width="minWidth - 16"' +
                                'ms-class="state-selected:$index==selectedIndex" ms-class-last-item="$last" ms-hover="state-hover" ' +
                                'ms-data-index="$index" ms-attr-title="el.text" ms-data-value="el.value">{{el.text}}</li>' +
                                '<li><a style="display: block;line-height: 35px;background: #eee;border-top: 1px solid #ccc;cursor: pointer;text-indent: 10px;" data-num="-selectSeizeOpts'+num+count+'" ms-click=addCommunication href="javascript:;">添加联系人</a></li>' +
                                '</ul>';
                            return tmps[0] + tmpl;
                        },
                        "onSelect" : function(v,t,o){
                            modelvm.requestData[outerIndex].recipient = v;
                        }
                    };
                    //短信通知人
                    vm['$selectMessOpts'+num+count] = {
                        "selectId" : getTypeId('-selectMessOpts'+num+count),
                        "options" : [],
                        "getTemplate": function (tmpl) {
                            var tmps = tmpl.split('MS_OPTION_HEADER');
                            tmpl = 'MS_OPTION_HEADER<ul class="select-list" ms-on-click="selectItem"ms-css-min-width="minWidth">' +
                                '<li class="select-item" ms-repeat="options" ms-css-min-width="minWidth - 16"' +
                                'ms-class="state-selected:$index==selectedIndex" ms-class-last-item="$last" ms-hover="state-hover" ' +
                                'ms-data-index="$index" ms-attr-title="el.text" ms-data-value="el.value">{{el.text}}</li>' +
                                '<li><a style="display: block;line-height: 35px;background: #eee;border-top: 1px solid #ccc;cursor: pointer;text-indent: 10px;" data-num="-selectMessOpts'+num+count+'"  ms-click=addCommunication href="javascript:;">添加联系人</a></li>' +
                                '</ul>';
                            return tmps[0] + tmpl;
                        },
                        "onSelect" : function(v,t,o){
                            modelvm.requestData[outerIndex].contactId = v;
                        }
                    };
                });
                avalon.scan($('.module-equipment-distribution').eq(0)[0],[vm]);
                avalon.getVm(getTypeId('-selectProcessOpts'+num+count)).setOptions(category);
                avalon.getVm(getTypeId('-selectExpressOpts'+num+count)).setOptions(logisticCompany);
                avalon.getVm(getTypeId('-selectSeizeOpts'+num+count)).setOptions(contacts);
                avalon.getVm(getTypeId('-selectMessOpts'+num+count)).setOptions(contacts);
                $('.content','.module-equipment-distribution').scrollTop($('.content','.module-equipment-distribution')[0].scrollHeight);
                personArr.push('-selectSeizeOpts'+num+count);
                personArr.push('-selectMessOpts'+num+count);
            };
            //移除一个设备明细
            vm.removeOuter = function(){
                var num = $(this).attr('data-num');
                var outerIndex = $(this).attr('data-outer-index');
                vm.requestData[outerIndex] = '';
                vm.requestData.$model[outerIndex] = '';
                avalon.getVm(getTypeId('-selectProcessOpts'+num+count))&&$(avalon.getVm(getTypeId('-selectProcessOpts'+num+count)).widgetElement).remove();
                $('.model'+num).remove();
                delete avalon.vmodels['model'+num];
            };
            /*通讯录*/
            vm.$addressOpts = {
                "addressId": getTypeId('-addressModule'),
                "isSearch": false,
                "displayType": 'add',
                "moduleType": 'fes',
                "merchantId": 0
            };
            vm.addCommunication = function () {
                var name = $(this).attr('data-num');
                var dialog = avalon.getVm(getTypeId('-addressModule'));
                dialog.readonly = false;
                dialog.callFn = function (responseData) {
                    var data = responseData.data;
                    var arr = {
                        text:data['name'],
                        value:data['id']
                    };
                    contacts.push(arr);
                    for(var i = 0;i<personArr.length;i++){
                        avalon.getVm(getTypeId(personArr[i]))&&avalon.getVm(getTypeId(personArr[i])).panelVmodel.options.push(arr);
                    }
                    avalon.getVm(getTypeId(name)).selectValue(arr.value);
                };
                dialog.requestData = {
                    merchantId: modelvm.merchantId
                };
                dialog.isSearch = false;
                dialog.displayType = 'add';
                dialog.open();

            };
            /*通讯录-end*/

            vm.$init = function () {
                elEl.addClass('module-equipmentdistribution');
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [modelvm].concat(vmodels));
            };
            vm.$remove = function () {
                _.each(erp.modelEquipmentDistribution, function (widgetId) {
                    var vm = avalon.getVm(widgetId);
                    vm && $(vm.widgetElement).remove();
                });
                erp.modelEquipmentDistribution.length = 0;
                elEl.removeClass('module-equipmentdistribution').off().empty();
            };
        });
        return modelvm;
    };
    module.defaults = {
        "merchantId" : 0,
        "processId" : 0,
        "callFn" : avalon.noop
    };
    return avalon;
});