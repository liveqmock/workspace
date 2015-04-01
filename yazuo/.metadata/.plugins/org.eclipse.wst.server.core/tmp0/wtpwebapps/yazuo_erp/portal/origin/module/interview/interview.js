/**
 * 商户访谈单
 */
define(["avalon", "util", "json", "moment", "text!./interview.html", "text!./interview.css", "common", "../../widget/dialog/dialog", "../../widget/form/form", "../../widget/form/select", '../../module/address/address'], function (avalon, util, JSON, moment, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    var styleData = styleEl.data('module') || {};

    if (!styleData["interview"]) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'interview/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'interview/');
        }
        styleData["interview"] = true;
        styleEl.data('module', styleData);
    }
    var module = erp.module.interview = function (element, data, vmodels) {
        var opts = data.interviewOptions,
            elEl = $(element);
        var interviewDialogId = data.interviewId + '-dialog',//dialog对话框
            interviewFormId = data.interviewId + '-form',//form对话框
            merchantDutyId = data.interviewId + '-duty',//商户负责人
            interviewJoin = data.interviewId + '-join',//直营、加盟
            interviewCustomer = data.interviewId + '-customer',//主要客户类型
            interviewUploadLogo = data.interviewId + '-logo',//上传logo
            interviewUploadFile = data.interviewId + '-file',//上传电子文档
            addressModule = data.interviewId + '-addressModule';//通讯录

        var vmodel = avalon.define(data.interviewId, function (vm) {
            avalon.mix(vm, opts);
            vm.interviewTabs = 1;//tab切换
            vm.changeInterviewTabs = function (index) {//tab切换
                vm.interviewTabs = index;
                $(this).removeClass('warn');
            };
            vm.readonly = 0;
            vm.displayType = '';//页面操作类型
            vm.argv = merchantDutyId;//添加通讯录传参
            vm.dicFormat = [];//业态
            vm.dicCardType = [];//卡类型
            vm.dicMemberSys = [];//会员系统
            vm.dicNetworkSpeedMemberRight = [];//会员权益
            vm.dicNetworkCondition = [];//网络条件
            vm.dicNetworkSpeed = [];//网络速度

            vm.dicforParentText = '';//业态中文父目录显示
            vm.dicforChildrenText = [];//业态中文子目录显示
            vm.formatParentId = '';//业态value父
            vm.formatChildId = [];//业态value子
            vm.formatChild = [];//业态子内容

            vm.requestData = {};//请求数据

            vm.data = {//保存数据
                merchantId : '',//商户ID
                merchantName : vm.merchantName,//商户名称
                contactId : '',//商户负责人ID
                format : [],//业态
                storeNumber : '',//门店数量
                joinType : '', //直营/加盟
                businessArea : '',//营业面积
                customerType : '',//主要客户类型
                dailyTurnover : '',//日均营业额
                cashCountPerDay : '',//日均收银笔数
                perOrder : '',//桌均消费
                perCapita : '',//人均消费
                groupPurchase : '',//团购
                coupon : '',//优惠券
                bankCardOffer : '',//银行卡优惠
                otherShopDiscount : '',//其他店内优惠
                cardType : [],//原有会员卡类别
                managementSystem : [],//会员卡管理系统"
                memberRight : [],//会员权益
                sendCardNumber : '',//已发卡数量
                potentialCustomerSource : '',//潜客来源
                potentialCustomerNum : '',//潜客数量
                networkCondition : '',//门店网络条件
                networkSpeed : '', //门店网络速度
                attachDocument: {},//电子文档
                attachmentId : '',//电子文档ID
                moduleType : '',//
                fileName :'',
                originalFileName :'',
                relativePath : '',
                networkRemark : '',// 备注内容
                fileSize : 0
            };
            vm.$merchantDutyOpts = {//商户负责人
                "selectId": merchantDutyId,
                "options": [],
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
                    var value = $('.inter-contactId').val();
                    vm.data.contactId = v;
                    $('.interview-merchant-person-text').html(t);
                    if(value == 'first'){
                        $('.inter-contactId').val(v);
                    }else{
                        $('.inter-contactId').val(v);
                        var dialog = avalon.getVm(interviewDialogId);
                        util.testing($(dialog.widgetElement)[0],$('.inter-contactId'));
                    }
                }
            };
            vm.$interviewJoinSelectOpts = {//直营加盟
                "selectId": interviewJoin,
                "options": [],
                "onSelect" : function(v,t,o){
                    var value = $('.inter-join').val();
                    vm.data.joinType = v;
                    $('.interview-join-text').html(t);
                    if(value == 'first'){
                        $('.inter-join').val(v);
                    }else{
                        $('.inter-join').val(v);
                        var dialog = avalon.getVm(interviewDialogId);
                        util.testing($(dialog.widgetElement)[0],$('.inter-join'));
                    }
                }
            };
            vm.$interviewCustomerSelectOpts = {//主要客户类型
                "selectId": interviewCustomer,
                "options": [],
                "onSelect" : function(v,t,o){
                    var value = $('.inter-customer').val();
                    vm.data.customerType = v;
                    $('.interview-customer-text').html(t);
                    if(value == 'first'){
                        $('.inter-customer').val(v);
                    }else{
                        $('.inter-customer').val(v);
                        var dialog = avalon.getVm(interviewDialogId);
                        util.testing($(dialog.widgetElement)[0],$('.inter-customer'));
                    }
                }
            };

            vm.$interviewFormOpts = {//商户访谈单Form
                "formId": interviewFormId
            };
            vm.$uploadLogOpts = {//上传logo
                "uploaderId": interviewUploadLogo,
                "uploadifyOpts": {
                    "uploader": erp.BASE_PATH + 'synMerchant/uploadLogo.do',
                    "fileObjName": "myfiles",
                    "multi": false, //多选
                    "fileTypeDesc": "上传附件(*.*)",
                    "fileTypeExts": "*.jpg; *.png; *.gif",
                    "formData": function () {
                        return {
                            "sessionId": vm.sessionId,
                            "merchantId": vm.data.merchantId
                        };
                    },
                    "width": 140,
                    "height": 30
                },
                "onSuccessResponseData": function (responseText, file) {
                    responseText=JSON.parse(responseText);
                    if(responseText.flag){
                        var data = responseText.data[0];
                        var vmData = vm.data;
                        vmData.fileName = data.fileName;//
                        vmData.originalFileName = data.originalFileName;
                        vmData.fileSize = data.fileSize;
                        vmData.relativePath = data.relativePath;//logo路径
                        $('.inter-logo').val(data.relativePath);
                        var dialog = avalon.getVm(interviewDialogId);
                        util.testing($(dialog.widgetElement)[0],$('.inter-logo'));
                    }
                }
            };
            vm.$uploadFile = {//上传电子文档
                "uploaderId": interviewUploadFile,
                "uploadifyOpts": {
                    "uploader": erp.BASE_PATH + 'mktBrandInterview/uploadAttachment.do',
                    "fileObjName": "myfile",
                    "multi": false, //多选
                    "fileTypeDesc": "上传附件(*.*)",
                    "fileTypeExts": "*.*",
                    "formData": function () {
                        return {
                            "sessionId": vm.sessionId
                        };
                    },
                    "width": 140,
                    "height": 30
                },
                "onSuccessResponseData": function (responseText, file) {
                    responseText=JSON.parse(responseText);
                    if(responseText.flag){
                        var data  = responseText.data;
                        vm.data.attachmentId = data.id;
                        vm.data.attachDocument.originalFileName = data.originalFileName;
                    }
                }
            };

            vm.$interviewOpts = {//对话框dialog
                "dialogId": interviewDialogId,
                "width": 670,

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
                    var formVm = avalon.getVm(interviewFormId);
                    var dialog = avalon.getVm(interviewDialogId);
                    util.testCancel($(dialog.widgetElement)[0]);
                    $('.inter-contactId').val('first');
                    $('.inter-join').val('first');
                    $('.inter-customer').val('first');
                    for(var i=1;i<=4;i++){
                        $('.inter-tabs'+i,$(dialog.widgetElement)).removeClass('warn');
                    }
                    formVm.reset();
                    _.each(vm.data.$model,function(val,key){
                        vm.data[key] = '';
                    });
                    vm.dicforParentText = '';//业态中文父目录显示
                    vm.dicforChildrenText = [];//业态中文子目录显示
                    vm.formatParentId = '';//业态value父
                    vm.formatChildId = [];//业态value子
                    vm.formatChild = [];//业态子内容
                },
                "submit": function (evt) {
                    var flag = true;
                    var dialog = avalon.getVm(interviewDialogId);
                    flag = util.testing($(dialog.widgetElement)[0]);
                    for(var i=1;i<=4;i++){
                        if($('.inter-con'+i+' .valid-error-field').length){
                            $('.inter-tabs'+i).addClass('warn')
                        }else{
                            $('.inter-tabs'+i).removeClass('warn')
                        }
                    }
                    $('.inter-tabs'+vm.surveyTabs).removeClass('warn');

                    if(flag){
                        vm.data.merchantId = vm.merchantId;
                        vm.data.moduleType = vm.moduleType;
                        if(vm.data.networkCondition instanceof Array){
                            vm.data.networkCondition = vm.data.networkCondition.join();
                        }
                        if(vm.data.networkSpeed instanceof Array){
                            vm.data.networkSpeed = vm.data.networkSpeed.join();
                        }
                        if(!(vm.data.originalFileName)){
                            vm.data.relativePath = '';
                        }
                        vm.data.format = [].concat(vm.formatChildId,vm.formatParentId);
                        util.c2s({
                            "url": erp.BASE_PATH + 'mktBrandInterview/saveMktBrandInterview.do',
                            "type": "post",
                            "contentType": 'application/json',
                            "data": JSON.stringify(vm.data.$model),
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    var dialog = avalon.getVm(interviewDialogId);
                                    dialog.close();
                                    vm.callFn(responseData);
                                    //关闭弹框
                                }
                            }
                        });
                    }else{//处理跳转到错误的位置
                        var errorEle = $('.interview-head',dialog.widgetElement).find('.valid-error')[0]||$('.inter-con'+vm.interviewTabs,dialog.widgetElement).find('.valid-error')[0];
                        if(errorEle){
                            var elePosition = $(errorEle).closest('.ff-value').position('.f-body',dialog.widgetElement).top+$('.f-body',dialog.widgetElement).scrollTop();
                            $('.f-body').scrollTop(elePosition<0?0:elePosition);
                        }
                    }
                }
            };
            vm.open = function () {
                util.c2s({
                    "url": erp.BASE_PATH + 'mktBrandInterview/loadMktBrandInterview.do',
                    "type": "post",
                    "contentType": 'application/json',
                    "data": JSON.stringify(vm.requestData.$model),
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag == 1) {
                            data = responseData.data;
                            var vmData = vm.data;

                            if(typeof data.attachDocument == 'object'){
                                vmData.attachDocument = data.attachDocument;//电子文档
                            }else{
                                vmData.attachDocument = {
                                    originalFileName : ''
                                }
                            }
                            vmData.attachmentId = data.attachmentId;//电子文档
                            vmData.moduleType = '';//
                            vmData.fileName = data.fileName;//
                            vmData.originalFileName = data.originalFileName;
                            vmData.fileSize = data.fileSize;
                            if(!data.relativePath){
                                vmData.relativePath = erp.BASE_PATH+'portal/origin/asset/image/merchant-default-logo.jpg';
                            }else{
                                vmData.relativePath = data.relativePath;//logo路径
                            }
                            vm.dicFormat = data.dicFormat;//业态
                            vmData.merchantName = data.merchantName;//商户名称
                            vmData.storeNumber  = data.storeNumber;//门店数量
                            vmData.businessArea = data.businessArea;//营业面积
                            vmData.dailyTurnover = data.dailyTurnover;//日均营业额
                            vmData.cashCountPerDay = data.cashCountPerDay;//门店日均收银笔数
                            vmData.perOrder = data.perOrder;//桌均消费
                            vmData.perCapita = data.perCapita;//人均消费
                            vmData.groupPurchase = data.groupPurchase;//团购
                            vmData.coupon = data.coupon;//优惠券
                            vmData.bankCardOffer = data.bankCardOffer;//银行卡优惠
                            vmData.otherShopDiscount = data.otherShopDiscount;//其他店内优惠
                            vmData.sendCardNumber = data.sendCardNumber;//已发卡数量
                            vmData.potentialCustomerSource = data.potentialCustomerSource;//潜客来源
                            vmData.potentialCustomerNum = data.potentialCustomerNum;//潜客数量
                            vmData.networkRemark = data.networkRemark;
                            /*门店负责人doorDutyId*/
                            var interContactId = {
                                checkedVals : data['selectedContact']['checkedVals'],
                                dropdownlist : []
                            };
                            for(var i=0;i<data['selectedContact']['dropdownlist'].length;i++){
                                var obj = {
                                    text:data['selectedContact']['dropdownlist'][i].name,
                                    value:data['selectedContact']['dropdownlist'][i].id
                                };
                                interContactId.dropdownlist.push(obj);
                            }
                            vm.setSelectCon(
                                {
                                    obj:interContactId['dropdownlist'],
                                    vals:interContactId['checkedVals']
                                },
                                {
                                    name:merchantDutyId
                                }
                            );
                            /*门店负责人doorDutyId end*/
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
                            vm.setCheckRadio(//原有会员卡类型
                                {
                                    obj:data['dicCardType']['dicObject'],
                                    vals:data['dicCardType']['checkedVals']
                                },
                                {
                                    sendData:'cardType',
                                    showData:'dicCardType',
                                    className:'inter-diccardtype'
                                }
                            );
                            vm.setCheckRadio(//会员卡系统
                                {
                                    obj:data['dicMagSystem']['dicObject'],
                                    vals:data['dicMagSystem']['checkedVals']
                                },
                                {
                                    sendData:'managementSystem',
                                    showData:'dicMemberSys',
                                    className:'inter-membersys'
                                }
                            );
                            vm.setCheckRadio(//会员权益
                                {
                                    obj:data['dicMemberRight']['dicObject'],
                                    vals:data['dicMemberRight']['checkedVals']
                                },
                                {
                                    sendData:'memberRight',
                                    showData:'dicNetworkSpeedMemberRight',
                                    className:'inter-right'
                                }
                            );
                            vm.setCheckRadio(//门店网络条件
                                {
                                    obj:data['dicNetworkCondition']['dicObject'],
                                    vals:data['dicNetworkCondition']['checkedVals']
                                },
                                {
                                    sendData:'networkCondition',
                                    showData:'dicNetworkCondition',
                                    className:'inter-dicNetworkCondition'
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
                                    className:'inter-dicNetworkSpeed'
                                }
                            );
                            vm.setSelectCon(//直营加盟
                                {
                                    obj:data['dicJoinType']['dicObject'],
                                    vals:data['dicJoinType']['checkedVals']
                                },
                                {
                                    name:interviewJoin
                                }
                            );
                            vm.setSelectCon(//主要客户类型
                                {
                                    obj:data['dicCustomerType']['dicObject'],
                                    vals:data['dicCustomerType']['checkedVals']
                                },
                                {
                                    name:interviewCustomer
                                }
                            );

                            if(vm.displayType == 'add'||vm.displayType == 'edit'){
                                vm.readonly = false;
                            }else if(vm.displayType == 'read'){
                                vm.readonly = true;
                            }
                            avalon.getVm(interviewDialogId).title = "商户访谈单";
                            vm.interviewTabs = 1;
                            avalon.getVm(interviewDialogId).open();

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
                    return;
                }
                var dialog = avalon.getVm(interviewDialogId);
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
                }else if(type == 'radio'){
                    vm.data[name] = val;
                    $('.'+className).val(vm.data[name]);
                }
                var dialog = avalon.getVm(interviewDialogId);
                util.testing($(dialog.widgetElement)[0],$('.'+className));
            };
            /*业态*/
            //显示业态弹层
            vm._showFormat = function(){
                var top = $('.interview-format-choose').offset().top-20;
                var left = $('.interview-format-choose').offset().left;
                $('.interview-format-box').css({top:top,left:left});
                $('.interview-format-box').show();
                $('.interview-position-scroll').scroll(function(){$('.interview-format-box').hide();});

                $(document).on('click',function(e){
                    e=e||window.event;
                    var target=e.target||e.srcElement;
                    if(!$('.interview-format').has(target).length&&!$('.interview-format-box').has(target).length){
                        $('.interview-format-box').hide();
                    }
                    if(target === $('.close-interview-format')[0]){
                        $('.interview-format-box').hide();
                    }
                    if(vm.dicforParentText.length){
                        var dialog = avalon.getVm(interviewDialogId);
                        util.testing($(dialog.widgetElement)[0],$('.interview-format-testing'));
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
                elEl.addClass('module-interview');
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            /*通讯录*/
            vm.$addressOpts = {
                "addressId": addressModule,
                "readonly": false,
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
                    var merchantDutyArr = avalon.getVm(merchantDutyId).panelVmodel.options;
                    merchantDutyArr.push(arr);
                    avalon.getVm(merchantDutyId).setOptions(merchantDutyArr,merchantDutyArr.length-1);
                };
                if ($(this).attr('data-id')) {
                    dialog.requestData = {
                        merchantId: vmodel.merchantId,
                        id: $(this).attr('data-id')
                    };
                } else {
                    dialog.requestData = {
                        merchantId: vmodel.merchantId
                    };
                }
                dialog.open();

            };
            /*通讯录-end*/
            vm.$remove = function () {
                avalon.getVm(merchantDutyId)&&$(avalon.getVm(merchantDutyId).widgetElement).remove();
                avalon.getVm(interviewJoin)&&$(avalon.getVm(interviewJoin).widgetElement).remove();
                avalon.getVm(interviewCustomer)&&$(avalon.getVm(interviewCustomer).widgetElement).remove();
                avalon.getVm(interviewUploadLogo)&&$(avalon.getVm(interviewUploadLogo).widgetElement).remove();
                avalon.getVm(interviewUploadFile)&&$(avalon.getVm(interviewUploadFile).widgetElement).remove();
                avalon.getVm(addressModule)&&$(avalon.getVm(addressModule).widgetElement).remove();
                avalon.getVm(interviewFormId)&&$(avalon.getVm(interviewFormId).widgetElement).remove();
                avalon.getVm(interviewDialogId)&&$(avalon.getVm(interviewDialogId).widgetElement).remove();
                elEl.removeClass('module-interview').off().empty();
            };
        });
        return vmodel;

    };
    module.defaults = {
        "moduleType" : '',
        "sessionId" : 0,
        "displayType" : 'read',//add,edit,read
        "merchantId" : 0,
        "callFn" : avalon.noop
    };
    return avalon;
});