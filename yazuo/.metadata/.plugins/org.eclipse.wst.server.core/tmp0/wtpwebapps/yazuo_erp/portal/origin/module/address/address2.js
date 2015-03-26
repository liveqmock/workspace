/**
 * 通讯录
 */
define(["avalon", "util", "json", "moment", "text!./address.html", "text!./address.css", "common", "../../widget/dialog/dialog", "../../widget/form/form", "../../widget/form/select"], function (avalon, util, JSON, moment, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    var styleData = styleEl.data('module') || {};

    if (!styleData["address"]) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'address/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'address/');
        }
        styleData["address"] = true;
        styleEl.data('module', styleData);
    }
    var module = erp.module.address = function (element, data, vmodels) {
        var opts = data.addressOptions,
            elEl = $(element);
        var addressDialogId = data.addressId + '-dialog',//dialog对话框
            addressFormId = data.addressId + '-form',//dialogFORM对话框
            addressBirthday = data.addressId + '-birthday',//生日
            addressFoleId = data.addressId + '-role';//角色

        var model = avalon.define(data.addressId, function (vm) {
            avalon.mix(vm, opts);
            vm.readonly = 0;
            vm.requestData = {};

            vm.data = {
                "merchantName" : '',
                "moduleType":"",
                "merchantId":'',
                "name":"",//姓名
                "genderType":1,//性别
                "birthday":'',//生日
                "position":"",//职位
                "mobilePhone":"",//手机
                "telephone":"",//座机
                "roleType":"",//角色
                "mail":''//邮箱
            };

            vm.$addressOpts = {//对话框dialog
                "dialogId": addressDialogId,
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
                    var formVm = avalon.getVm(addressFormId);
                    var dialog = avalon.getVm(addressDialogId);
                    util.testCancel($(dialog.widgetElement)[0]);
                    $('.roleId').val('first');
                    vm.data.birthday = '';
                    vm.data.merchantName = '';
                    vm.data.moduleType = '';
                    vm.data.name = '';
                    vm.data.genderType = '';
                    vm.data.position = '';
                    vm.data.mobilePhone = '';
                    vm.data.telephone = '';
                    vm.data.roleType = '';
                    vm.data.mail = '';
                    formVm.reset();
                },
                "submit": function (evt) {
                    var flag;
                    var dialog = avalon.getVm(addressDialogId);
                    flag = util.testing($(dialog.widgetElement)[0]);
                    if(flag){
                        vm.data.moduleType = vm.moduleType;
                        util.c2s({
                            "url": erp.BASE_PATH + 'contact/saveMktContact.do',
                            "type": "post",
                            "contentType" : 'application/json',
                            "data": JSON.stringify(vm.data.$model),
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    dialog.close();
                                    vm.callFn(responseData);
                                }
                            }
                        });
                    }
                }
            };
            vm.$addressFormOpts = {//FORM
                "formId" : addressFormId
            };
            vm.setGenderType = function(){
                var value = this.value;
                vm.data.genderType = +value;
            };
            vm.$addressroleSelectOpts = {//角色
                "selectId" : addressFoleId,
                "options": [],
                "selectedIndex": 0,
                "onSelect" : function(val){
                    var value = $('.roleId').val();
                    vm.data.roleType = val;
                    if(value == 'first'){
                        $('.roleId').val(val);
                    }else{
                        $('.roleId').val(val);
                        var dialog = avalon.getVm(addressDialogId);
                        util.testing($(dialog.widgetElement)[0],$('.roleId'));
                    }
                }
            };
            vm.$startDateOpts = {//日历-生日插件
                "calendarId": addressBirthday,
                //"minDate": new Date(),
                "onClickDate": function (d) {
                    //var formVm = avalon.getVm(addressFormId);
                    vm.data.birthday = moment(d).format('YYYY-MM-DD');
                    $(this.widgetElement).hide();
                }
            };
            vm.openDateCalendar = function () {//日历-生日插件
                var formVm = avalon.getVm(addressFormId);
                var meEl = $(this),
                    calendarVm = avalon.getVm(addressBirthday),
                    calendarEl,
                    inputOffset = meEl.offset();
                if (!calendarVm) {
                    calendarEl = $('<div ms-widget="calendar,$,$startDateOpts"></div>');
                    calendarEl.css({
                        "position": "absolute",
                        "z-index": 10000
                    }).hide().appendTo('body');
                    avalon.scan(calendarEl[0], [vm]);
                    calendarVm = avalon.getVm(addressBirthday);
                    //注册全局点击绑定
                    util.regGlobalMdExcept({
                        "element": calendarEl,
                        "handler": function () {
                            calendarEl.hide();
                        }
                    });
                } else {
                    calendarEl = $(calendarVm.widgetElement);
                }
                //设置focus Date
                if (vm.data.birthday) {
                    calendarVm.focusDate = moment(vm.data.birthday, 'YYYY-MM-DD')._d;
                } else {
                    calendarVm.focusDate = new Date();
                }
                calendarEl.css({
                    "top": inputOffset.top + meEl.outerHeight() - 1,
                    "left": inputOffset.left
                }).show();
            };
            vm.open = function () {
                util.c2s({
                    "url": erp.BASE_PATH + 'contact/loadMktContact.do',
                    "type": "post",
                    "contentType": 'application/json',
                    "data": JSON.stringify(vm.requestData.$model),
                    "success": function (responseData) {
                        var data,vmData;
                        if(responseData.flag){
                            data = responseData.data;
                            vmData = vm.data;
                            vmData.merchantName = data.merchantName;
                            vmData.moduleType = data.moduleType;
                            vmData.merchantId = data.merchantId;
                            vmData.name = data.name;//姓名
                            if(data.genderType){
                                vmData.genderType = data.genderType;//性别
                            }else{
                                vmData.genderType = 1;//性别
                            }
                            if(data.birthday){
                                vmData.birthday = moment(data.birthday).format('YYYY-MM-DD');//生日
                            }else{
                                vmData.birthday = moment(new Date()).format('YYYY-MM-DD');//生日

                            }
                            vmData.position = data.position;//职位
                            vmData.mobilePhone = data.mobilePhone;//手机
                            vmData.telephone = data.telephone;//座机
                            vmData.mail = data.mail;//邮箱
                            vm.setSelectCon(//角色
                                {
                                    obj:data['dicRole']['dicObject'],
                                    vals:data['dicRole']['checkedVals']
                                },
                                {
                                    name:addressFoleId,
                                    data:'roleType'
                                }
                            );

                            if(vm.readonly){
                                vm.setRead();
                                avalon.getVm(addressDialogId).title = "查看联系人";
                                avalon.getVm(addressDialogId).open();
                            }else{
                                vm.setAdd();
                                avalon.getVm(addressDialogId).title = "添加联系人";
                                avalon.getVm(addressDialogId).open();
                            }
                        }
                    }
                });


            };
            vm.setSelectCon = function (responseData,vmData) {//给每个select增加内容并过滤
                responseData.obj.unshift({value : '',text : '请选择'});
                avalon.getVm(vmData.name).setOptions(responseData.obj);
                if(responseData.vals instanceof Array){
                    if(responseData.vals[0]!==''){
                        avalon.getVm(vmData.name).selectValue(responseData.vals[0],true);
                        $('.readroleId').val(avalon.getVm(vmData.name).selectedText);
                    }
                }else if(typeof responseData.vals == 'string'||typeof responseData.vals == 'number'){
                    if(responseData.vals!==''){
                        avalon.getVm(vmData.name).selectValue(responseData.vals,true);
                        $('.readroleId').val(avalon.getVm(vmData.name).selectedText);
                    }
                }else{
                    alert(responseData.vals+"-类型有问题！");
                }
            };
            vm.setRead = function(){//设置只读
                var dialog = avalon.getVm(addressDialogId);
                $('input',dialog.widgetElement).prop('disabled',true);
            };
            vm.setAdd = function(){//设置只读
                var dialog = avalon.getVm(addressDialogId);
                $('input',dialog.widgetElement).prop('disabled',false);
            };

            vm.$init = function () {
                elEl.addClass('module-address');
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [model].concat(vmodels));
            };
            vm.$remove = function () {
                $(avalon.getVm(addressDialogId).widgetElement).remove();
                elEl.removeClass('module-address').off().empty();
            };
        });
        return model;
    };
    module.defaults = {
        "readonly" : '',
        "merchantId" : '',
        "callFn" : avalon.noop
    };
    return avalon;
});