/**
 * 商户调研单
 */
define(["avalon", "util", "json", "moment", "text!./survey.html", "text!./survey.css", "common", "../../widget/dialog/dialog", "../../widget/form/select", '../../module/address/address'], function (avalon, util, JSON, moment, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    var styleData = styleEl.data('module') || {};

    if (!styleData["survey"]) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'survey/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'survey/');
        }
        styleData["survey"] = true;
        styleEl.data('module', styleData);
    }
    var module = erp.module.survey = function (element, data, vmodels) {
        var opts = data.surveyOptions,
            elEl = $(element);
        var surveyDialogId = data.surveyId + '-dialog',//dialog对话框
            surveyFormId = data.surveyId + '-form',//form对话框
            doorNameId = data.surveyId + '-doorName',//门店名称
            doorDutyId = data.surveyId + '-doorDuty',//门店负责人
            nearId = data.surveyId + '-near',//附近
            addressModule = data.surveyId + '-addressModule';//通讯录


        var model = avalon.define(data.surveyId, function (vm) {
            avalon.mix(vm, opts);
            vm.displayType = '';//操作类型，add,edit,read
            vm.readonly = false;
            vm.argv = doorDutyId;//添加通讯录传参
            vm.isRead = false;//是否只读
            vm.surveyTabs = 1;//tab切换


            vm.dicforParentText = '';//业态中文父目录显示
            vm.dicforChildrenText = [];//业态中文子目录显示
            vm.formatParentId = '';//业态value父
            vm.formatChildId = [];//业态value子
            vm.formatChild = [];//业态子内容


            vm.isRemark = false;//现有物料备注
            vm.dicFormat = [];//业态
            vm.dicNetworkCondition = [];//门店网络条件
            vm.dicNetworkSpeed = [];//门店网络速度
            vm.dicPublicityMaterial = [];//物料宣传单
            vm.requestData = {};//请求数据
            vm.data = {//保存数据
                "merchantId" : '',
                "id" : '',
                "storeId" : '',//门店名称
                "contactId" :'',//门店负责人
                "format" : [],//业态
                "near" : '',//附近
                "businessArea" : '', //营业面积
                "dailyPassengerFlow" : '', //日均客流量
                "tableAverage" : '',//桌均消费
                "mealsNumber" : '', //餐台数
                "roomsNumber" : '', //包间数
                "attendanceRatio" : '',//上座比例
                "networkCondition" : '',//门店网络条件
                "networkSpeed" : '',//门店网络速度
                "publicityMaterial" : [],//物料宣传单
                "moduleType" : '',
                "networkRemark": "",//网络条件备注
                "materRemark": "" //宣传材料备注
            };

            vm.changeSurveyTabs = function (index) {
                vm.surveyTabs = index;
                $(this).removeClass('warn');
            };
            vm.$NearSelectOpts = {//附近
                "selectId" : nearId,
                "options": [],
                "onSelect" : function(v,t,o){
                    var value = $('.near').val();
                    vm.data.near = v;
                    $('.survey-near-text').html(t);
                    if(value == 'first'){
                        $('.near').val(v);
                    }else{
                        $('.near').val(v);
                        var dialog = avalon.getVm(surveyDialogId);
                        util.testing($(dialog.widgetElement)[0],$('.near'));
                    }

                }
            };
            vm.$doorNameOpts = {//门店名称
                "selectId" : doorNameId,
                "options": [],
                "selectedIndex": 0,
                "onSelect" : function(v,t,o){
                    var value = $('.storeId').val();
                    vm.data.storeId = v;
                    $('.survey-merchant-name-text').html(t);
                    if(value == 'first'){
                        $('.storeId').val(v);
                    }else{
                        $('.storeId').val(v);
                        var dialog = avalon.getVm(surveyDialogId);
                        util.testing($(dialog.widgetElement)[0],$('.storeId'));
                    }
                }
            };
            vm.$doorDutyOpts = {//门店负责人
                "selectId": doorDutyId,
                "options": [],
                "selectedIndex": 0,
                "getTemplate": function (tmpl) {
                    var tmps = tmpl.split('MS_OPTION_HEADER');
                    tmpl = 'MS_OPTION_HEADER<ul class="select-list" ms-on-click="selectItem"ms-css-min-width="minWidth">' +
                        '<li class="select-item" ms-repeat="options" ms-css-min-width="minWidth - 16"' +
                        'ms-class="state-selected:$index==selectedIndex" ms-class-last-item="$last" ms-hover="state-hover" ' +
                        'ms-data-index="$index" ms-attr-title="el.text" ms-data-value="el.value">{{el.text}}</li>' +
                        '<li><a style="display: block;line-height: 35px;background: #eee;border-top: 1px solid #ccc;cursor: pointer;text-indent: 10px;" ms-click=addCommunication(argv) href="javascript:;">添加联系人</a></li>' +
                        '</ul>';
                    return tmps[0] + tmpl;
                },
                "onSelect" : function(v,t,o){
                    var value = $('.contactId').val();
                    vm.data.contactId = v;
                    $('.survey-merchant-person-text').html(t);
                    if(value == 'first'){
                        $('.contactId').val(v);
                    }else{
                        $('.contactId').val(v);
                        var dialog = avalon.getVm(surveyDialogId);
                        util.testing($(dialog.widgetElement)[0],$('.contactId'));
                    }
                }
            };
            vm.$surveyOpts = {//对话框dialog
                "dialogId": surveyDialogId,
                "width": 670,
                //"readonly": vm.readonly,
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
                    var formVm = avalon.getVm(surveyFormId);
                    var dialog = avalon.getVm(surveyDialogId);
                    util.testCancel($(dialog.widgetElement)[0]);
                    $('.near').val('first');
                    $('.contactId').val('first');
                    $('.storeId').val('first');
                    for(var i=1;i<=2;i++){
                        $('.tabs'+i,$(dialog.widgetElement)).removeClass('warn');
                    }
                    formVm.reset();
                    vm.dicforParentText = '';//业态中文父目录显示
                    vm.dicforChildrenText = [];//业态中文子目录显示
                    vm.formatParentId = '';//业态value父
                    vm.formatChildId = [];//业态value子
                    vm.formatChild = [];//业态子内容
                    _.each(vm.data.$model,function(val,key){
                        vm.data[key] = '';
                    });
                },
                "submit": function (evt) {
                    var flag;
                    var dialog = avalon.getVm(surveyDialogId);
                    flag = util.testing($(dialog.widgetElement)[0]);
                    for(var i=1;i<=2;i++){
                        if($('.con'+i+' .valid-error-field').length){
                            $('.tabs'+i).addClass('warn')
                        }else{
                            $('.tabs'+i).removeClass('warn')
                        }
                    }
                    $('.tabs'+vm.surveyTabs).removeClass('warn');
                    if(flag){
                        vm.data.merchantId = vm.merchantId;
                        vm.data.moduleType = vm.moduleType;
                        if(vm.data.networkCondition instanceof Array){
                            vm.data.networkCondition = vm.data.networkCondition.join();
                        }
                        if(vm.data.networkSpeed instanceof Array){
                            vm.data.networkSpeed = vm.data.networkSpeed.join();
                        }
                        vm.data.format = [].concat(vm.formatChildId,vm.formatParentId);
                        util.c2s({
                            "url": erp.BASE_PATH + 'mktShopSurvey/saveMktShopSurvey.do',
                            "type": "post",
                            "contentType": 'application/json',
                            "data": JSON.stringify(vm.data.$model),
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    var dialog = avalon.getVm(surveyDialogId);
                                    dialog.close();
                                    vm.callFn(responseData);
                                    //关闭弹框
                                }
                            }
                        });
                    }else{//处理跳转到错误的位置
                        var errorEle = $('.survey-head',dialog.widgetElement).find('.valid-error')[0]||$('.con'+vm.surveyTabs,dialog.widgetElement).find('.valid-error')[0];
                        if(errorEle){
                            var elePosition = $(errorEle).closest('.ff-value').position('.f-body',dialog.widgetElement).top+$('.f-body',dialog.widgetElement).scrollTop();
                            $('.f-body').scrollTop(elePosition<0?0:elePosition);
                        }
                    }
                }
            };
            vm.$surveyFormOpts = {
                "formId" : surveyFormId
            };
            vm.open = function () {
                util.c2s({
                    "url": erp.BASE_PATH + 'mktShopSurvey/loadMktShopSurvey.do',
                    "type": "post",
                    "contentType": 'application/json',
                    "data": JSON.stringify(vm.requestData.$model),
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag == 1) {
                            data = responseData.data;
                            vm.surveyTabs = 1;
                            vm.data.id = data.id;
                            vm.dicFormat = data.dicFormat;//业态
                            vm.data.businessArea = data.businessArea; //营业面积
                            vm.data.dailyPassengerFlow = data.dailyPassengerFlow; //日均客流量
                            vm.data.tableAverage = data.tableAverage;//桌均消费
                            vm.data.mealsNumber = data.mealsNumber; //餐台数
                            vm.data.roomsNumber = data.roomsNumber; //包间数
                            vm.data.attendanceRatio = data.attendanceRatio;//上座比例
                            vm.data.networkRemark = data.networkRemark//网络条件备注
                            vm.data.materRemark = data.materRemark //宣传材料备注
                            //业态
                            _.map(vm.dicFormat,function(item,index){
                                if(item['isSelected'] == 1){//设置业态父级
                                    vm.dicforParentText = item['text']+'-';//设置显示中文
                                    vm.formatParentId = item['value'];//设置业态父ID
                                    vm.formatChild = item['children'];//设置业态子内容
                                    _.map(item['children'],function(item,index){//设置子级
                                        if(item['isSelected'] == 1){
                                            vm.dicforChildrenText.push(item['text']);//设置显示中文
                                            vm.formatChildId.push(item['value']);//设置业态子id
                                        }
                                    })
                                }
                            });

                            //业态end
                            vm.setCheckRadio(//门店网络条件
                                {
                                    obj:data['dicNetworkCondition']['dicObject'],
                                    vals:data['dicNetworkCondition']['checkedVals']
                                },
                                {
                                    sendData:'networkCondition',
                                    showData:'dicNetworkCondition',
                                    className:'dicNetworkCondition'
                                }
                            );
                            vm.setCheckRadio(//门店网络速度
                                {
                                    obj:data['dicNetworkSpeed']['dicObject'],
                                    vals:data['dicNetworkSpeed']['checkedVals']
                                },
                                {
                                    sendData:'networkSpeed',
                                    showData:'dicNetworkSpeed',
                                    className:'dicNetworkSpeed'
                                }
                            );
                            vm.setCheckRadio(//现有物料宣传单
                                {
                                    obj:data['dicPublicityMaterial']['dicObject'],
                                    vals:data['dicPublicityMaterial']['checkedVals']
                                },
                                {
                                    sendData:'publicityMaterial',
                                    showData:'dicPublicityMaterial',
                                    className:'dicPublicityMaterial'
                                }
                            );

                            vm.setSelectCon(//附近
                                {
                                    obj:data['dicNear']['dicObject'],
                                    vals:data['dicNear']['checkedVals']
                                },
                                {
                                    name:nearId,
                                    data:'near'
                                }
                            );
                            var objSurvey = {
                                checkedVals : data['merchantsForSurvey']['checkedVals'],
                                dropdownlist : []
                            };
                            for(var i=0;i<data['merchantsForSurvey']['dropdownlist'].length;i++){
                                var obj = {
                                    text:data['merchantsForSurvey']['dropdownlist'][i].merchant_name,
                                    value:data['merchantsForSurvey']['dropdownlist'][i].merchant_id
                                };
                                objSurvey.dropdownlist.push(obj);
                            }
                            vm.setSelectCon(//门店名称doorNameId
                                {
                                    obj:objSurvey['dropdownlist'],
                                    vals:objSurvey['checkedVals']
                                },
                                {
                                    name:doorNameId,
                                    data:'storeId'
                                }
                            );
                            var mktContact = {
                                checkedVals : data['mktContactList']['checkedVals'],
                                dropdownlist : []
                            };
                            for(var i=0;i<data['mktContactList']['dropdownlist'].length;i++){
                                var obj = {
                                    text:data['mktContactList']['dropdownlist'][i].name,
                                    value:data['mktContactList']['dropdownlist'][i].id
                                };
                                mktContact.dropdownlist.push(obj);
                            }
                            vm.setSelectCon(//门店负责人doorDutyId
                                {
                                    obj:mktContact['dropdownlist'],
                                    vals:mktContact['checkedVals']
                                },
                                {
                                    name:doorDutyId,
                                    data:'doorDuty'
                                }
                            );
                            //现有宣传物料增加备注判断如下
                            for(var i=0;i<vm.data.publicityMaterial.length;i++){
                                if(9 == vm.data.publicityMaterial[i]){
                                    vm.isRemark = true;
                                }
                            }

                            if(vm.displayType == 'add'||vm.displayType == 'edit'){
                                vm.readonly = false;
                            }else if(vm.displayType == 'read'){
                                vm.readonly = true;
                            }
                            avalon.getVm(surveyDialogId).title = "门店调研单";
                            vm.surveyTabs = 1;
                            avalon.getVm(surveyDialogId).open();
                        }
                    }
                });
            };
            vm.setCheckRadio = function (responseData,vmData){//设置单选或多选对象
                if(responseData.vals instanceof Array){
                    for(var i=0;i<responseData.obj.length;i++){
                        responseData.obj[i].isSelect = 0;
                        for(var j=0;j<responseData.vals.length;j++){
                            if(responseData.obj[i].value ==  responseData.vals[j]){
                                responseData.obj[i].isSelect = 1;
                            }
                        }
                    }
                }else if(typeof responseData.vals == 'string'){
                    for(var i=0;i<responseData.obj.length;i++){
                        responseData.obj[i].isSelect = 0;
                        if(responseData.obj[i].value ==  responseData.vals){
                            responseData.obj[i].isSelect = 1;
                        }
                    }
                }else{
                    alert(responseData.vals+"-类型有问题！");
                }
                var dialog = avalon.getVm(surveyDialogId);
                $('.'+vmData.className,dialog.widgetElement).val(responseData.vals);
                vm.data[vmData.sendData] = responseData.vals;
                vm[vmData.showData] = responseData.obj;
            };
            vm.setSelectCon = function (responseData,vmData) {//给每个select增加内容并过滤
                responseData.obj.unshift({value : '',text : '请选择'});
                avalon.getVm(vmData.name).setOptions(responseData.obj);
                if(responseData.vals instanceof Array){
                    if(responseData.vals[0]!==''){
                        avalon.getVm(vmData.name).selectValue(responseData.vals[0],true);
                    }
                }else if(typeof responseData.vals == 'string'||typeof responseData.vals == 'number'){
                    if(responseData.vals!==''){
                        avalon.getVm(vmData.name).selectValue(responseData.vals,true);
                    }
                }else{
                    alert(responseData.vals+"-类型有问题！");
                }
            };
            vm.isChecked = function (name,type,className) {//根据勾选操作数据
                var val = this.value;
                var isChecked = $(this)[0].checked;
                var flag = true;
                if(type == 'checkbox'){
                    var arr = vm.data[name];
                    if(isChecked){
                        if(arr.length){
                            for(var i=0;i<arr.length;i++){
                                if(arr[i] == val){
                                    flag = false
                                }
                            }
                            if(flag){
                                arr.push(val);
                            }
                        }else {
                            arr.push(val)
                        }
                    }else{
                        if(arr.length){
                            for(var i=0;i<arr.length;i++){
                                if(arr[i] == val){
                                    arr.splice(i,1);
                                }
                            }
                        }
                    }
                    $('.'+className).val(vm.data[name].$model);
                    //现有宣传物料增加备注判断如下
                    if(name == 'publicityMaterial'){
                        var flag = false;
                        for(var i=0;i<vm.data.publicityMaterial.length;i++){
                            if(9 == vm.data.publicityMaterial[i]){
                                 flag = true;
                            }
                        }
                        if(flag){
                            vm.isRemark = true;
                        }else{
                            vm.isRemark = false;
                        }
                    }



                }else if(type == 'radio'){
                    vm.data[name] = val;
                    $('.'+className).val(vm.data[name]);
                    //门店网络条件增加备注判断如下
                }
                var dialog = avalon.getVm(surveyDialogId);
                util.testing($(dialog.widgetElement)[0],$('.'+className));
            };


            /*业态*/
            //显示业态弹层
            vm._showFormat = function(){
                var top = $('.survey-format-choose').offset().top-20;
                var left = $('.survey-format-choose').offset().left;
                $('.survey-format-box').css({top:top,left:left});
                $('.survey-format-box').show();
                $('.survey-position-scroll').scroll(function(){$('.survey-format-box').hide();});
                $('.survey-format-box').show();
                $(document).on('click',function(e){
                    e=e||window.event;
                    var target=e.target||e.srcElement;
                    if(!$('.survey-format').has(target).length&&!$('.survey-format-box').has(target).length){
                        $('.survey-format-box').hide();
                    }
                    if(target === $('.close-survey-format')[0]){
                        $('.survey-format-box').hide();
                    }
                    if(vm.dicforParentText.length){
                        var dialog = avalon.getVm(surveyDialogId);
                        util.testing($(dialog.widgetElement)[0],$('.survey-format-testing'));
                    }
                })

            };
            //显示子目录且设置tree的父节点isSelected字段
            vm.showFormatChildren = function(){
                var value = $(this).attr('data-value');
                var text = $(this).attr('data-text');
                var arr = vm.dicFormat;
                var flag =false;
                if(value != vm.formatParentId){//判断选中的业态父id是否和当前的id相同
                    for(var i=0;i<arr.length;i++){
                        arr[i].isSelected = 0;
                        var children = arr[i].children;
                        for(var j=0;j<children.length;j++){
                            children[j].isSelected = 0;
                        }
                        if(arr[i].value == value){
                            arr[i].isSelected = 1;
                            vm.dicforParentText = text+'-';
                            vm.formatParentId = value;
                            vm.formatChildId = [];
                            vm.dicforChildrenText = [];
                            vm.formatChild = arr[i].children;
                        }
                    }
                }
            };
            //选中子目录
            vm.selectedFormatChildren = function(){
                var value = $(this).attr('data-value');
                var text = $(this).attr('data-text');
                var checkbox = $(this)[0].checked;
                if(checkbox){
                    vm.formatChildId.push(value);
                    vm.dicforChildrenText.push(text);
                }else{
                    for(var i = 0;i<vm.formatChildId.length;i++){
                        if(value == vm.formatChildId[i]){
                            vm.formatChildId.splice(i,1);
                        }
                        if(text == vm.dicforChildrenText[i]){
                            vm.dicforChildrenText.splice(i,1);
                        }
                    }
                }
            };
            /*业态-end*/

            vm.$init = function () {
                elEl.addClass('module-survey');
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [model].concat(vmodels));
            };
            /*通讯录*/
            vm.$addressOpts = {
                "addressId": addressModule,
                "isSearch": false,
                "displayType": 'add',
                "moduleType": 'fes',
                "merchantId": 0
            };
            vm.addCommunication = function (name) {
                var dialog = avalon.getVm(addressModule);
                dialog.readonly = false;
                dialog.callFn = function (responseData) {
                    var data = responseData.data;
                    var arr = {
                        text:data['name'],
                        value:data['id']
                    };
                    var doorDutyArr = avalon.getVm(doorDutyId).panelVmodel.options;
                    doorDutyArr.push(arr);
                    avalon.getVm(doorDutyId).setOptions(doorDutyArr,doorDutyArr.length-1);
                };
                dialog.requestData = {
                    merchantId: vm.merchantId,
                    id: $(this).attr('data-id')
                };
                dialog.isSearch = false;
                dialog.displayType = 'add';
                dialog.open();

            };
            /*通讯录-end*/
            vm.$remove = function () {
                avalon.getVm(doorNameId)&&$(avalon.getVm(doorNameId).widgetElement).remove();
                avalon.getVm(doorDutyId)&&$(avalon.getVm(doorDutyId).widgetElement).remove();
                avalon.getVm(nearId)&&$(avalon.getVm(nearId).widgetElement).remove();
                avalon.getVm(addressModule)&&$(avalon.getVm(addressModule).widgetElement).remove();
                avalon.getVm(surveyFormId)&&$(avalon.getVm(surveyFormId).widgetElement).remove();
                avalon.getVm(surveyDialogId)&&$(avalon.getVm(surveyDialogId).widgetElement).remove();
                elEl.removeClass('module-survey').off().empty();
            };
        });
        return model;

    };
    module.defaults = {
        "displayType" : '',//add,edit,read
        "merchantId" : 0,
        "callFn" : avalon.noop
    };
    return avalon;
});