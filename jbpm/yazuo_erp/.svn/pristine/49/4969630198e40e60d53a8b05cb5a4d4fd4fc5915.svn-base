/**
 * 后端客服-客户服务看板
 */
define(['avalon', 'util','moment', 'json', '../../../../widget/dialog/dialog', '../../../../widget/form/select', '../../../../widget/form/form', '../../../../widget/calendar/calendar', '../../../../widget/uploader/uploader', '../../../../module/addquestion/addquestion'], function (avalon, util) {
     var win = this,
         erp = win.erp,
         pageMod = {},
         tempWidgetIdStore = [];
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName, routeData) {
            var getTypeId = function (n) {//设置和获取widget的id;
                var widgetId = pageName + n;
                tempWidgetIdStore.push(widgetId);
                return widgetId;
            };
            var loginUserData = erp.getAppData('user');
            var pageVm = avalon.define(pageName, function (vm) {
                function forValue (data,arr){
                    var tmpArr = [];
                    if(data instanceof Array){
                        for(var i=0;i<data.length;i++){
                            var tmpObj = {};
                            for( var k in data[i]){
                                var value = data[i].hasOwnProperty(k);
                                if(value){
                                    for(var j=0;j<arr.length;j++){
                                        if(k == arr[j]){
                                            tmpObj[arr[j]] = data[i][k];
                                        }
                                    }
                                }
                            }
                            tmpArr.push(tmpObj);
                        }
                    }else{
                        var tmpObj = {};
                        for( var k in data){
                            var value = data[i].hasOwnProperty(k);
                            if(value){
                                for(var j=0;j<arr.length;j++){
                                    if(k == arr[j]){
                                        tmpObj[arr[j]] = data[i][k];
                                    }
                                }
                            }
                        }
                        tmpArr.push(tmpObj);
                    }
                    return tmpArr;
                }
                vm.navCrumbs = [{
                    "text":"我的主页",
                    "href":"#/backend/customerservice"
                }, {
                    "text":'待办事项'
                }];
                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.merchantId= parseInt(routeData.params["id"]);
                vm.merchantName = '';
                vm.pageStatus= 1;
                vm.upLineType = 1;//上线回访操作状态（1是进行，2是完成）
                vm.activeType = 1;//营销活动操作状态（1是进行，2是完成）
                vm.path = erp.BASE_PATH;

                /*merchant信息*/
                vm.merchantData = {//商户信息出参
                    "productList": [],
                    "filePath": "",
                    "merchant_status": "",
                    "merchant_name": "",
                    "front_user_name": "",
                    "sale_user_name": "",
                    "assist_user_name": '',
                    "person_avg": "",
                    "desk_avg": "",
                    "store_number": "",
                    "begin_time": '',
                    "plan_online_time": '',
                    "online_time": '',
                    "format_name": "",
                    "attachment_name": "",
                    "attachment_path": ""
                };
                /*merchant信息-end*/
                /*代办事项数据*/
                vm.stepData = [];
                /*代办事项数据-end*/
                vm.changePageStatus= function(value,obj){//大标签切换
                    vm.pageStatus= value;
                    if(value == 2){
                        vm.cardFn();
                    }
                };
                /*卡信息*/
                vm.responseCardData = [
                    {
                        "card_count": 0,//（卡余量）
                        "card_price": 0,//（单价）
                        "card_type": "",//（卡类型）
                        "merchant_type": "",//（商户类型）
                        "is_sold_card": false,//（是否实体卡）
                        "merchant_name": "",//（商户名称）
                        "merchant_id": 0,//（商户ID，不取）
                        "cardtype_id": 0,
                        "description": "",
                        "card_name" : ""//（卡名称）
                    }
                ];
                vm.cardFn = function(){
                    util.c2s({
                        "url": erp.BASE_PATH + 'synMembershipCard/listSynMembershipCards.do',
                        "type": "post",
                        "data": {pageNumber:1,pageSize:10000,merchantId:pageVm.merchantId},
                        "success": function(responseData){
                            if(responseData.flag){
                                var arr = responseData.data.rows;
                                var modelArr=[
                                    "card_count","card_price","card_type","merchant_type","is_sold_card","merchant_name","cardtype_id","description","card_name"
                                ];
                                pageVm.responseCardData = forValue(arr,modelArr);
                            }
                        }
                    });
                };
                /*卡信息-end*/
                /*添加问题*/
                vm.$addquestionOpts = {
                    "addquestionId" : getTypeId('-addquestionops'),
                    addType : 1,
                    merchantId : 0,
                    merchantName : '',
                    callFn : function(){
                    }
                };
                vm.addQuestions = function(){
                    var dialogVm = avalon.getVm(getTypeId('-addquestionops'));
                    dialogVm.addType = 0;
                    dialogVm.merchantId = vm.merchantId;
                    dialogVm.merchantName = vm.merchantName;
                    dialogVm.open();
                };
                /*添加问题结束*/
                /*回访单*/
                vm.accessSwitch = 1;//标签切换依据
                vm.accessType = 'add';
                vm.step9AccessId = 0;
                vm.acessRequestData = {};
                vm.accessSwitchFn = function(value){
                    vm.accessSwitch = value
                    $(this).removeClass('warn');
                };
                vm.step9Access =function(type){
                    var dialogVm = avalon.getVm(getTypeId("addAccessDialog"));
                    var url;
                    if(type == 'add'){
                        vm.step9AccessId = 0;
                        var requestData = JSON.stringify([{'documentType':'2'}, {'documentType':'3'}]);
                        url = 'sysQuestion/listMutiSysQuestions.do'
                    }else if(type == 'edit'){
                        vm.step9AccessId = 1;
                        var requestData = JSON.stringify({"merchantId" : pageVm.merchantId});
                        url = 'sysDocument/getSysDocumentByMerchantId.do'
                    }else if(type == 'read'){
                        vm.step9AccessId = 1;
                        var requestData = JSON.stringify({"merchantId" : pageVm.merchantId});
                        url = 'sysDocument/getSysDocumentByMerchantId.do'
                    }
                    util.c2s({
                        "url": erp.BASE_PATH + url,
                        "type": "post",
                        "contentType" : 'application/json',
                        "data": requestData,
                        "success": function (responseData) {
                            function getArrData(arr){
                                var tmpArr = [];
                                for(var i=0;i<arr.length;i++){
                                    var obj = {};
                                    obj.optionContent = arr[i]['optionContent'];
                                    obj.isSelected = arr[i]['isSelected'];
                                    obj.isOpenTextarea = arr[i]['isOpenTextarea'];
                                    obj.tip = arr[i]['tip'];
                                    obj.isOpenTextarea = arr[i]['isOpenTextarea'];
                                    if(arr[i]['comment']){
                                        obj.comment = arr[i]['comment'];
                                    }else{
                                        obj.comment = '';
                                    }
                                    tmpArr.push(obj);
                                }
                                return tmpArr;
                            }
                            function responseToData(data){
                                var arr = [];
                                for(var i=0;i<data.length;i++){
                                    var obj = {};
                                    obj.questionId = data[i]['id'];
                                    obj.questionType = data[i]['question_type'];
                                    obj.title = data[i]['title'];
                                    obj.tip = data[i]['tip'];
                                    if(data[i]['comment']){
                                        obj.comment = data[i]['comment'];
                                    }else{
                                        obj.comment = '';
                                    }
                                    if(data[i]['sysQuestionOptionList']){
                                        obj.sysDocumentDtlOptionList = getArrData(data[i]['sysQuestionOptionList']);
                                    }
                                    arr.push(obj);
                                }
                                return arr;
                            }
                            if (responseData.flag == 1) {
                                if(type == 'add'){
                                    avalon.getVm(getTypeId('addAccessDialog')).readonly = false;
                                    vm.accessType = 'add';
                                    var data = responseData.data;
                                    var obj = {};
                                    obj.data1 = responseToData(data[2]);
                                    obj.data2 = responseToData(data[3]);
                                    vm.acessRequestData = obj;
                                }else if(type == 'edit'){
                                    avalon.getVm(getTypeId('addAccessDialog')).readonly = false;
                                    vm.accessType = 'edit';
                                    var data = responseData.data;
                                    var obj = {};
                                    obj.data1 = data[0].sysDocumentDtlList;
                                    obj.data2 = data[1].sysDocumentDtlList;
                                    vm.acessRequestData = obj;
                                }else if(type == 'read'){
                                    avalon.getVm(getTypeId('addAccessDialog')).readonly = true;
                                    vm.accessType = 'read';
                                    var data = responseData.data;
                                    var obj = {};
                                    obj.data1 = data[0].sysDocumentDtlList;
                                    obj.data2 = data[1].sysDocumentDtlList;
                                    vm.acessRequestData = obj;
                                }
                                if(type == 'edit'||type == 'read'){
                                    setTimeout(function(){
                                        //门店收银回访
                                        for (var i = 0; i < vm.acessRequestData.data1.length; i++) {
                                            if(vm.acessRequestData.data1[i].sysDocumentDtlOptionList.length){
                                                var flag = false;
                                                for(var j = 0; j < vm.acessRequestData.data1[i].sysDocumentDtlOptionList.length; j++){
                                                    if(vm.acessRequestData.data1[i].sysDocumentDtlOptionList[j].isSelected == '1'){
                                                        flag = true;
                                                    }
                                                }
                                                if(flag){
                                                    $('.line-input-hidden-1-'+i).val('xxx');
                                                }
                                            }
                                        }
                                        //门店财务回访
                                        if(vm.acessRequestData.data2[0].sysDocumentDtlOptionList[1].isSelected == '1' && vm.acessRequestData.data2[0].sysDocumentDtlOptionList[1].optionContent == '没用过'){//选中了否
                                            for (var i = 1; i < vm.acessRequestData.data2.length; i++) {
                                                $('.line-hui-fang2' + i).hide();
                                                $('.line-hui-fang2' + i).find('.valid-error').hide().html();
                                                $('.line-hui-fang2' + i).find('textarea').removeClass('valid-error-field').removeAttr('isrule');
                                                $('.line-hui-fang2' + i).find('.line-input-hidden-2-' + i).removeAttr('isrule');
                                                $('.line-hui-fang2' + i).find('input').removeAttr('checked');
                                                $('.line-input-hidden-2-' + i).val('');
                                            }
                                            $('.line-input-hidden-2-0').val('xxx');
                                        }else if(vm.acessRequestData.data2[0].sysDocumentDtlOptionList[0].isSelected == '1' && vm.acessRequestData.data2[0].sysDocumentDtlOptionList[0].optionContent == '使用过'){//选中了是
                                            for (var i = 0; i < vm.acessRequestData.data2.length; i++) {
                                                if(vm.acessRequestData.data2[i].sysDocumentDtlOptionList.length){
                                                    var flag = false;
                                                    for(var j = 0; j < vm.acessRequestData.data2[i].sysDocumentDtlOptionList.length; j++){
                                                        if(vm.acessRequestData.data2[i].sysDocumentDtlOptionList[j].isSelected == '1'){
                                                            flag = true;
                                                        }
                                                    }
                                                    if(flag){
                                                        $('.line-input-hidden-2-'+i).val('xxx');
                                                    }
                                                }
                                            }
                                        }
                                    },1000);
                                }
                                vm.accessSwitch = 1;
                                dialogVm.title = "回访单";
                                dialogVm.open();

                            }
                        }
                    });
                };
                vm.radioBox = function(n){//以下是写死得逻辑，内容改变后必须修改此处
                    var con = this.$vmodel.$outer.el.sysDocumentDtlOptionList;
                    for(var i=0;i<con.length;i++){
                        con[i].isSelected = '0';
                        con[i].comment = '';
                    }
                    this.$vmodel.el.isSelected = '1';
                    var selfCon = this.$vmodel.el;
                    var parentIndex = this.$vmodel.$outer.$index;
                    $('.line-input-hidden-'+n+'-' + parentIndex).val('xxx');
                    var dialogVm = avalon.getVm(getTypeId("addAccessDialog"));
                    util.testing(dialogVm.widgetElement, $('.line-input-hidden-'+n+'-' + parentIndex));
                    if(n==2){
                        if (selfCon.optionContent == '使用过' && parentIndex == 0) {
                            for (var i = 1; i <= 5; i++) {
                                $('.line-hui-fang2' + i).show();
                                $('.line-hui-fang2' + i).find('.line-input-hidden-2-' + i).attr('isrule',true);
                                $('.line-hui-fang2' + i).find('textarea').attr('isrule',true);
                            }
                            avalon.scan(dialogVm.widgetElement, [].concat());
                        } else if (selfCon.optionContent == '没用过' && parentIndex == 0) {
                            for (var i = 1; i <= 5; i++) {
                                if(vm.acessRequestData.data2[i].questionType == '1'||vm.acessRequestData.data2[i].questionType == '2'){
                                    for(var j = 0; j < vm.acessRequestData.data2[i].sysDocumentDtlOptionList.length; j++){
                                        vm.acessRequestData.data2[i].sysDocumentDtlOptionList[j].isSelected = '0';
                                    }
                                }else if(vm.acessRequestData.data2[i].questionType == '3'){
                                    vm.acessRequestData.data2[i].comment = '';
                                }
                                $('.line-hui-fang2' + i).hide();
                                $('.line-hui-fang2' + i).find('.valid-error').hide().html();
                                $('.line-hui-fang2' + i).find('textarea').removeClass('valid-error-field').removeAttr('isrule');
                                $('.line-hui-fang2' + i).find('.line-input-hidden-2-' + i).removeAttr('isrule');
                                $('.line-hui-fang2' + i).find('input').removeAttr('checked');
                                $('.line-input-hidden-2-' + i).val('');
                            }
                        }
                    }
                };
                vm.checkBox = function(n){
                    var con = this.$vmodel.$model.el;
                    if(con.isSelected == '1'){
                        con.isSelected = '0';
                    }else{
                        con.isSelected = '1';
                    }
                };
                vm.$addAccessDialogOpts = {
                    "dialogId" : getTypeId("addAccessDialog"),
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
                    "width": 660,
                    "readonly": false,
                    "onOpen": function () {},
                    "onClose": function () {
                        var formVm = avalon.getVm(getTypeId("addAccessForm"));
                        formVm.reset();
                    },
                    "submit": function (evt) {
                        var dialogVm = avalon.getVm(getTypeId("addAccessDialog"));
                        var requestData = [];
                        var obj1 = {};
                        var obj2 = {};
                        var url;
                        var flag = util.testing(dialogVm.widgetElement);
                        for(var i=1;i<=2;i++){
                            if($('.access-con-'+i+' .valid-error-field').length){
                                $('.access-tabs-'+i).addClass('warn')
                            }else{
                                $('.access-tabs-'+i).removeClass('warn')
                            }
                        }
                        $('.access-tabs-'+vm.accessSwitch).removeClass('warn');
                        if (flag) {
                            url = 'sysDocument/saveDocumentAndUpdateStatusAfterStep10.do';
                            obj1.documentType = 2;
                            obj1.merchantId= pageVm.merchantId;
                            obj2.documentType = 3;
                            obj2.merchantId= pageVm.merchantId;
                            obj1.sysDocumentDtlList=pageVm.acessRequestData.data1.$model;
                            obj2.sysDocumentDtlList=pageVm.acessRequestData.data2.$model;
                            requestData.push(obj1);
                            requestData.push(obj2);
                            requestData = JSON.stringify(requestData);
                            util.c2s({
                                "url": erp.BASE_PATH + url,
                                "type": "post",
                                "contentType" : 'application/json',
                                "data": requestData,
                                "success": function (responseData) {
                                    if (responseData.flag == 1) {
                                        //关闭弹框
                                        dialogVm.close();
                                        updateList({"merchantId":pageVm.merchantId},"fesOnlineProcess/listFesOnlineProcesssAfterOnline.do",waitingList);
                                    }
                                }
                            });
                        }else{//处理跳转到错误的位置
                            var errorEle = $('.linewaiting-con',dialogVm.widgetElement).find('.valid-error')[0];
                            if(errorEle){
                                var elePosition = $(errorEle).closest('.ff-value').position('.linewaiting-con',dialogVm.widgetElement).top+$('.linewaiting-con',dialogVm.widgetElement).scrollTop();
                                $('.linewaiting-con').scrollTop(elePosition<0?0:elePosition);
                            }
                        }
                    }
                };
                vm.$addAccessFormOpts = {
                    "formId": getTypeId("addAccessForm")
                };
                /*回访单-end*/


                /*回访录音*/
                vm.$step9AttachUploaderOpts = {
                    "uploaderId": getTypeId('-lineAttachUploaderId'),
                    "uploadifyOpts": {
                        "uploader": erp.BASE_PATH + 'sysDocument/uploadRecordAndSaveSysDocumentAfterOnline.do',
                        "fileObjName": "myfile",
                        "multi": false, //多选
                        "fileTypeDesc": "上传附件(*.*)",
                        "fileTypeExts": "*.*",
                        "formData": function () {
                            return {
                                "sessionId": loginUserData.sessionId,
                                inputSysDocuments: JSON.stringify(
                                    [{
                                    "merchantId": pageVm.merchantId, //"商户ID",
                                    "documentType":2
                                },
                                {
                                    "merchantId": pageVm.merchantId, //"商户ID",
                                    "documentType":3
                                }]
                                )
                            };
                        },
                        "width": 110,
                        "height": 30,
                        "onUploadProgress": function (file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
                            var ratio = (bytesUploaded / bytesTotal) * 100 > 100 ? 100 : (bytesUploaded / bytesTotal) * 100;
                            ratio = ratio + '%';
                            var fileName = file.name;
                            var tip = '';
                            if (ratio === '100%') {
                                tip = '上传完成';
                            } else {
                                tip = '正在上传：' + fileName;
                            }
                            if (!bytesUploaded) {
                                $('.upline-upfile-list').append(
                                    '<div class="upfile-file" style="position:relative;width:598px;height: 25px;border:1px solid #ccc;background:#bebebe;">' +
                                        '<p class="loding-file" style="position:absolute;left:0;top:-1px;width:' + ratio + ';height:27px;background:#67bb0d;"></p>' +
                                        '<p class="tips-model" style="position:absolute;left:0;top:0;height:25px;line-height:25px;text-align:center;width:100%;color:#fff;">' + tip + '</p>' +
                                        '</div>'
                                );
                            } else {
                                $('.loding-file').css('width', ratio);
                                $('.tips-model').html(tip);
                            }
                        }
                    },
                    "onSuccessResponseData": function (responseText, file) {
                        responseText=JSON.parse(responseText);
                        if(responseText.flag){
                            setTimeout(function () {
                                $('.upfile-file').remove();
                                updateList({"merchantId":pageVm.merchantId},"fesOnlineProcess/listFesOnlineProcesssAfterOnline.do",waitingList);
                            }, 1000);
                        }else {
                            $('.upfile-file').remove();
                            util.alert({
                                "content": "上传失败！",
                                "iconType": "error"
                            });
                        }
                    }
                };
                /*回访录音－end*/
                /*关闭回访代办事项*/
                vm.disposeOpen = function (){
                    util.confirm({
                        "content": '你确认已经回访了吗？',
                        "onSubmit": function () {
                            util.c2s({
                                "url": erp.BASE_PATH + "sysDocument/closeToDoListsAfterViste.do",
                                "type": "post",
                                //"contentType" : 'application/json',
                                "data": {"merchantId": pageVm.merchantId},
                                "success": function (responseData) {
                                    if(responseData.flag){
                                        vm.upLineType = 2;
                                    }
                                }
                            });
                        }
                    });
                };
                /*关闭回访代办事项－end*/
                /*关闭活动*/
                vm.activeReqData = {
                    "activityId":'',
                    "status":'',
                    "reason" : ''
                };
                vm.activeSelect = function(status,id){
                    if(status == '2'){
                        var str = '您确定活动申请已经创建了吗？';
                        util.confirm({
                            "content": str,
                            "onSubmit": function () {
                                util.c2s({
                                    "url": erp.BASE_PATH + "activity/updateActivityStatus.do",
                                    "type": "post",
                                    //"contentType" : 'application/json',
                                    "data": {"activityId":id,"status":status},
                                    "success": function (responseData) {
                                        if(responseData.flag){
                                            vm.activeType = 2;
                                        }
                                    }
                                });
                            }
                        });
                    }else if(status == '3'){
                        pageVm.activeReqData.activityId = id;
                        pageVm.activeReqData.status = status;
                        var dialogVm = avalon.getVm(getTypeId('activeDialogId'));
                        dialogVm.title = '关闭申请';
                        dialogVm.open();
                    }

                };
                vm.$activeDisposeOpts={
                    "dialogId": getTypeId('activeDialogId'),
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                            footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                '<div class="ui-dialog-btns">' +
                                '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">确&nbsp;定</button>' +
                                '<span class="separation"></span>' +
                                '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button>' +
                                '</div>' +
                                '</div>';
                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "onClose": function () {
                        var formVm = avalon.getVm(getTypeId("activeform"));
                        formVm.reason = '';
                    },
                    "submit": function (evt) {
                        var dialogVm=avalon.getVm(getTypeId('activeDialogId'));
                        var formVm = avalon.getVm(getTypeId("activeform"));
                        if (formVm.validate()) {
                            var reqData = pageVm.activeReqData.$model;
                            util.c2s({
                                "url": erp.BASE_PATH + 'activity/updateActivityStatus.do',
                                "type": "post",
                                //"contentType" : 'application/json',
                                "data": {"activityId":reqData.activityId,"status":reqData.status,"reason":reqData.reason},
                                "success": function (responseData) {
                                    if (responseData.flag == 1) {
                                        dialogVm.close();
                                        formVm.reason = '';
                                        vm.activeType = 2;
                                    }
                                }
                            });
                        }
                    }

                };
                vm.$activeformOpts = {
                    "formId": getTypeId("activeform"),
                    "field":
                        [{
                            "selector": ".reason",
                            "name": "reason",
                            "rule": ["noempty", function (val, rs) {
                                if(val.length>250){
                                    return "内容不能超过250个字！";
                                }
                                if (rs[0] !== true) {
                                    return "内容不能为空";
                                } else {
                                    return true;
                                }
                            }]
                        }
                        ]
                };
                /*关闭活动－end*/

            });
            avalon.scan(pageEl[0]);
            /*页面初始化请求渲染*/
            function updateList(obj,url,callBack){
                var requestData=JSON.stringify(obj);
                util.c2s({
                    "url": erp.BASE_PATH + url,
                    "type": "post",
                    "contentType" : 'application/json',
                    "data": requestData,
                    "success": callBack
                });
            }
            /*页面初始化请求渲染end*/
            /*商户*/
            updateList({"merchantId":pageVm.merchantId},"synMerchant/getMerchantInfoByOrder.do",merchantCallBack);
            function merchantCallBack(responseData){
                var data;
                if (responseData.flag) {
                    data = responseData.data;
                    var obj = {};
                    obj.productList = data.productList;
                    obj.userMerchantList = data.userMerchantList;
                    obj.filePath = data.filePath;
                    var tmpObj = data.merchantInfo;
                    for(var k in data){
                        var value = data.hasOwnProperty(k);
                        if(value){
                            if(typeof(data[k])!= 'object'){
                                obj[k] = data[k];
                            }
                        }
                    }
                    for(var k in tmpObj){
                        var value = tmpObj.hasOwnProperty(k);
                        if(value){
                            obj[k] = tmpObj[k];
                        }
                    }
                    if(obj.attachment_name){
                        obj.imgUrl=pageVm.path+obj.filePath+'/'+obj.attachment_path+'/'+obj.attachment_name;
                    }else{
                        obj.imgUrl=pageVm.path+'portal/origin/asset/image/merchant-default-logo.jpg';
                    }
                    pageVm.merchantData= obj;
                    pageVm.merchantName = obj.merchant_name;
                    var userId = loginUserData.id;
                    if(obj.userMerchantList){
                        for(var i=0;i<obj.userMerchantList.length;i++){
                            if(userId == obj.userMerchantList[i].userId){
                                pageVm.promise = true;
                                return;
                            }else{
                                pageVm.promise = false;
                            }
                        }
                    }
                }
            }
            /*商户回调end*/
            /*代办事项列表*/
            updateList({"merchantId":pageVm.merchantId},"fesOnlineProcess/listFesOnlineProcesssAfterOnline.do",waitingList);
            function waitingList (responseData){
                var data;
                if (responseData.flag) {
                    data = responseData.data;
                    var arr = [];
                    var uploadVm = avalon.getVm(getTypeId('-lineAttachUploaderId'));
                    uploadVm && $(uploadVm.widgetElement).remove();
                    for(var i=0;i<data.length;i++){
                        if(data[i].fesStep.stepName == '营销活动创建'){
                            var tmpArr = data[i].fesMarketingActivitys;
                            for(var j=0;j<tmpArr.length;j++){
                                tmpArr[j].fesStep ={stepName :'营销活动创建'};
                            }
                            arr = tmpArr;
                        }else{
                            arr.push(data[i]);
                        }
                    }
                    pageVm.stepData = arr;
                }
            }
            /*代办事项列表－end*/
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            _.each(tempWidgetIdStore, function (widgetId) {
                var vm = avalon.getVm(widgetId);
                vm && $(vm.widgetElement).remove();
            });
            tempWidgetIdStore.length = 0;
        }
    });


    return pageMod;
});

