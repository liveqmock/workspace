/**
 * 销售服务-待办事项
 */
define(['avalon', 'util', 'json','moment', '../../../../widget/dialog/dialog', '../../../../widget/form/select', '../../../../widget/form/form', '../../../../widget/calendar/calendar', '../../../../widget/uploader/uploader', '../../../../module/interview/interview', '../../../../module/survey/survey'], function (avalon, util,JSON) {
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


                /*测试可删除-start*/
                vm.$interviewOpts = {
                    "interviewId": getTypeId('-interviewModule'),
                    "choose" : "edit"

                };
                vm.$surveyOpts = {
                    "surveyId": getTypeId('-surveyModule')
                    //"readonly" : ''
                };

                vm.addInterval = function(){
                    var dialog = avalon.getVm(getTypeId('-interviewModule'));
                    dialog.choose = "add";
                    dialog.open()
                };

                vm.addSurvey = function () {
                    var dialog = avalon.getVm(getTypeId('-surveyModule'));
                    dialog.merchantId = pageVm.merchantId;
                    dialog.readonly = 0;
                    dialog.open()

                };
                /*测试可删除-end*/












                vm.navCrumbs = [{
                    "text":"我的主页",
                    "href":"#/sales/home"
                }, {
                    "text":'待办事项'
                }];
                vm.path=erp.BASE_PATH;
                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.merchantId = parseInt(routeData.params["id"]);
                vm.imgUrl = '';
                vm.isLogo = false;
                vm.pageData={};

                vm.localHref = function(url){
                    util.jumpPage(url);
                };
                /*merchant信息*/
                var merchantModel = function(){
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
                        "format_name": "",
                        "attachment_name": "",
                        "attachment_path": ""
                    };
                };
                merchantModel();
                /*merchant信息-end*/
                /*添加问题*/
                var questionModel = function(){
                    vm.options1=[];
                    vm.$addQuestionDialogOpts = {//弹框初始化
                        "dialogId": getTypeId("addQuestionDialog"),
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
                            var formVm = avalon.getVm(getTypeId("addQuestionForm"));
                            formVm.reset();
                            formVm.title='';
                            formVm.description='';
                        },

                        "submit": function (evt) {
                            var requestData,
                                dialogVm = avalon.getVm(getTypeId("addQuestionDialog")),
                                formVm = avalon.getVm(getTypeId("addQuestionForm"));
                            if (formVm.validate()) {
                                requestData = formVm.getFormData();
                                var problemType=avalon.getVm(getTypeId("addQuestionSelect")).selectedValue;
                                requestData.merchantId = pageVm.merchantId;
                                requestData.problemType=problemType;
                                requestData.problemStatus=1;
                                requestData=JSON.stringify(requestData);
                                //发送后台请求，编辑或添加
                                util.c2s({
                                    "url": erp.BASE_PATH + 'sysProblem/saveSysProblem.do',
                                    "type": "post",
                                    "contentType" : 'application/json',
                                    "data": requestData,
                                    "success": function (responseData) {
                                        if (responseData.flag == 1) {
                                            dialogVm.close();
                                        }
                                    }
                                });
                            }
                        }
                    };
                    vm.openAdd = function(){
                        var scheduleVm = avalon.getVm(getTypeId("addQuestionSelect"));
                        if(!vm.options1.length){
                            util.c2s({
                                "url": erp.BASE_PATH + "dictionary/querySysDictionaryByType.do",
                                "type": "post",
                                "data":  'dictionaryType=00000040',
                                "success": function (responseData) {
                                    var data;
                                    if (responseData.flag) {
                                        data = responseData.data;
                                        vm.options1=_.map(data, function (itemData) {
                                            return {
                                                "text": itemData["dictionary_value"],
                                                "value": itemData["dictionary_key"]
                                            };
                                        });
                                        scheduleVm.setOptions(vm.options1);
                                    }
                                }
                            });
                        }else{
                            scheduleVm.setOptions(vm.options1);
                        }
                        var dialogVm = avalon.getVm(getTypeId("addQuestionDialog")),
                            formVm = avalon.getVm(getTypeId("addQuestionForm"));
                        dialogVm.title = '添加问题';
                        formVm.title='';
                        formVm.description='';
                        dialogVm.open();
                    };
                    vm.$addQuestionFormOpts = {//form表单初始化
                        "formId": getTypeId("addQuestionForm"),
                        "field":
                            [{
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
                        "title":"",
                        "description":""
                    };
                    vm.$scheduleOpts = {//elect初始化
                        "selectId": getTypeId("addQuestionSelect"),
                        "options": [],
                        "selectedIndex": 1
                    };
                };
                questionModel();
                /*添加问题-end*/
                /*调研单*/
                var researchFn = function(){
                    vm.researchTabs = 1;//调研单切换依据
                    vm.researchCheckboxPublicity = {};//现有宣传物料
                    vm.researchRequestData = {//调研单入参数据
                        "moduleType" : "",
                        "merchantId" : vm.merchantId,//商户id
                        "storeName":"",//门店名称
                        "contact":'',///门店负责人
                        "format":"",//业态
                        "businessArea":'',//营业面积
                        "dailyPassengerFlow":'',//日均客流量
                        "boardingTime":'',//上客集中时间
                        "tableAverage":'',//桌均消费
                        "mealsNumber":'',//餐台数
                        "roomsNumber":"",//包间数
                        "attendanceRatio":"",//上座比例
                        "isAccessInternet": 0,//是否具备上网条件
                        "publicityMaterial":[]//现有宣传物料

                    };
                    var researchModelObj = {
                        "moduleType" : "",
                        "merchantId" : vm.merchantId,//商户id
                        "storeName":"",//门店名称
                        "contact":'',///门店负责人
                        "format":"",//业态
                        "businessArea":'',//营业面积
                        "dailyPassengerFlow":'',//日均客流量
                        "boardingTime":'',//上客集中时间
                        "tableAverage":'',//桌均消费
                        "mealsNumber":'',//餐台数
                        "roomsNumber":"",//包间数
                        "attendanceRatio":"",//上座比例
                        "isAccessInternet": 0,//是否具备上网条件
                        "publicityMaterial":[]//现有宣传物料
                    };
                    function selectToObj(k,data){//select转换对象
                        var arr= [];
                        for(var i= 0,len=data[k].length;i<len;i++){
                            var obj= {};
                            obj.text= data[k][i].dictionary_value;
                            obj.value= data[k][i].dictionary_key;
                            arr.push(obj);
                        }
                        return arr;
                    }
                    function isChecked(arr,value){//checkbox增减
                        if(this.checked){
                            for(var j=0;j<arr.length;j++){
                                if(value == arr[j]){
                                    arr.splice(j,1);
                                }
                            }
                            arr.push(value);
                        }else{
                            for(var i=0;i<arr.length;i++){
                                if(value == arr[i]){
                                    arr.splice(i,1);
                                }
                            }
                        }
                    }
                    function checkedMatch(checkArr,arr){//checkbox匹配选中设置
                        for(var i=0;i<checkArr.length;i++){
                            for(var j=0;j<arr.length;j++){
                                if(checkArr[i].value == arr[j] ){
                                    checkArr[i].isChecked = true;
                                }
                            }
                        }
                    }
                    function changeDefauteValue(obj,value){//设置初始值
                        for(var i=0;i<obj.length;i++){
                            if(obj[i].value==value){
                                return i;
                            }
                        }
                    }
                    vm.changeResearch = function(value){//切换
                        vm.researchTabs = value;
                    };
                    vm.isAccess = function(value){//门店网络条件
                        pageVm.researchRequestData.isAccessInternet = value;
                    };
                    vm.addCheckboxPublicity = function(value){//现有宣传物料
                        isChecked.call(this,pageVm.researchRequestData.publicityMaterial,value);
                    };
                    vm.addResearch = function(){//添加调研单dialog
                        pageVm.researchTabs = 1;
                        var dialogVm=avalon.getVm(getTypeId("researchDialog"));
                        var researchSelectVm = avalon.getVm(getTypeId("researchSelect"));//业态
                        var requestData = ['00000001','00000075','00000076'];
                        requestData = JSON.stringify(requestData);
                        util.c2s({
                            "url": erp.BASE_PATH + "dictionary/querySysDictionaryByTypeList.do",
                            "type": "post",
                            "contentType" : 'application/json',
                            "data":  requestData,
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    for(var k in data){
                                        if(k == '00000001'){
                                            researchSelectVm.setOptions(selectToObj(k,data),changeDefauteValue(selectToObj(k,data),pageVm.researchRequestData.format));//业态
                                        }else if(k == '00000076'){
                                            pageVm.researchCheckboxPublicity = selectToObj(k,data);
                                            checkedMatch(pageVm.researchCheckboxPublicity,pageVm.researchRequestData.publicityMaterial);
                                        }
                                    }
                                    dialogVm.title="门户调研单";
                                    if(pageVm.pageData.isDisabled == 3){
                                        dialogVm.readonly = 1;
                                    }
                                    dialogVm.open();
                                }
                            }
                        });
                    };
                    vm.$addResearchFormOpts = {//添加调研单form
                        "formId": getTypeId("researchForm"),
                        "field": [
                            {
                                "selector": ".storeName",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "门店名称不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".contact",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "门店负责人不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".businessArea",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "营业面积不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".dailyPassengerFlow",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "日均客流量不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".boardingTime",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "上客集中时间不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".tableAverage",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "桌均消费不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".mealsNumber",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "餐台数不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".roomsNumber",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "包间数不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".attendanceRatio",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "上座比例不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            }
                        ]
                    };
                    vm.$addResearchSelectOpts = {//业态
                        "selectId": getTypeId("researchSelect"),
                        "options": [],
                        "selectedIndex": 0
                    };
                    vm.$addResearchOpts= {
                        "dialogId": getTypeId("researchDialog"),
                        "width" : 650,
                        "readonly" : 0,
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
                        "onOpen": function () {},
                        "onClose": function () {
                            avalon.getVm(getTypeId("researchForm")).reset();
                            pageVm.researchRequestData = researchModelObj;
                        },
                        "submit": function (evt) {
                            vm.researchRequestData.format = avalon.getVm(getTypeId("researchSelect")).selectedValue;//业态
                            pageVm.researchRequestData.moduleType = 'mkt';
                            var requestData = JSON.stringify(pageVm.researchRequestData.$model);

                            if (avalon.getVm(getTypeId("researchForm")).validate()) {
                                util.c2s({
                                    "url": erp.BASE_PATH + 'mktShopSurvey/saveMktShopSurvey.do',
                                    "type": "post",
                                    "contentType": 'application/json',
                                    "data": requestData,
                                    "success": function (responseData) {
                                        if (responseData.flag == 1) {
                                            //关闭弹框
                                            avalon.getVm(getTypeId("researchDialog")).close();
                                            updateList({"merchantId": pageVm.merchantId}, "mktShopSurvey/listMktShopSurveys.do", waitingCallBack);
                                            pageVm.researchRequestData = researchModelObj;
                                            pageVm.researchRequestData.id = '';
                                        }
                                    }
                                });
                            }
                        }
                    };
                    /*修改调研单*/
                    vm.editShop = function(id){
                        util.c2s({
                            "url": erp.BASE_PATH + 'mktShopSurvey/edit.do',
                            "type": "get",
                            //"contentType" : 'application/json',
                            "data": {"id" : id},
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    vm.researchRequestData = responseData.data;
                                    vm.addResearch();
                                }
                            }
                        });
                    };
                    /*修改调研单-end*/
                    /*删除调研单*/
                    vm.removeResearch = function(id){
                        var requestData = {id : id,moduleType : 'mkt'};
                        requestData = JSON.stringify(requestData);
                        util.confirm({
                            "content": "你确定要删除调研单吗？",
                            "onSubmit": function () {
                                util.c2s({
                                    "url": erp.BASE_PATH + 'mktShopSurvey/deleteMktShopSurvey.do',
                                    "type": "post",
                                    "contentType": 'application/json',
                                    "data": requestData,
                                    "success": function (responseData) {
                                        if (responseData.flag == 1) {
                                            updateList({"merchantId": pageVm.merchantId}, "mktShopSurvey/listMktShopSurveys.do", waitingCallBack);
                                        }
                                    }
                                });
                            }
                        });
                    };
                    /*删除调研单-end*/
                };
                researchFn();
                /*调研单-end*/
                /*访谈单*/
                var interFn = function(){//访谈单
                    vm.interviewTabs = 1;//访谈单切换依据
                    vm.interCheckboxCardType = {};//会员卡类型
                    vm.interCheckboxManagementSystem = {};//会员卡管理系统
                    vm.interCheckboxMemberRight = {};//会员权益
                    vm.merchantLogo = '';
                    vm.interRequestData = {//访谈单入参数数据
                        "merchantName" : '123',
                        "moduleType" : '',
                        "merchantId" : vm.merchantId,//商户id
                        "contact" : "",//商户负责人
                        "storeNumber" : "",//门店数
                        "businessArea":"",//营业面积
                        "passengerFlow":"",//午晚市客流
                        "dailyTurnover":'',//日均营业额
                        "perOrder":"",//单均消费
                        "perCapita":"",//人均消费
                        "dailyCashierNumber":"",//日收银笔数
                        "groupPurchase":'',//团购
                        "coupon":'',//优惠券
                        "bankCardOffer":'',//银行卡优惠
                        "otherShopDiscount":'',//其他店内优惠
                        "whiteListSource":'',//白名单来源
                        "whiteListNumberStr":'',//白名单数量
                        "sendCardNumberStr":'',//已发卡数量
                        "isElectronicFiles":'0',//是否可提供电子档
                        "joinType" : '',//直营/加盟
                        "near" : '',//附近
                        "customerType" : '',//主要顾客类型
                        "cardType" : [],//会员卡类型
                        "managementSystem" : [],//会员卡管理系统
                        "memberRight" : [],//会员权益
                        "networkCondition" : '',//门店网络条件
                        "storeTraining" : '',//门店实施培训
                        "fileName":'',
                        "originalFileName":'',
                        "fileSize":1020
                    };
                    vm.addCheckboxVipType = function(value){//会员卡类型checkbox
                        isChecked.call(this,pageVm.interRequestData.cardType,value);
                    };
                    vm.addCheckboxVipSys = function(value){//会员卡管理系统checkbox
                        isChecked.call(this,pageVm.interRequestData.managementSystem,value);
                    };
                    vm.addCheckboxVipRight = function(value){//会员权益checkbox
                        isChecked.call(this,pageVm.interRequestData.memberRight,value);
                    };
                    vm.isElectronicFiles = function(){
                        if(this.checked){
                            pageVm.interRequestData.isElectronicFiles = '1'
                        }else{
                            pageVm.interRequestData.isElectronicFiles = '0'
                        }
                    };
                    function selectToObj(k,data){//select转换对象
                        var arr= [];
                        for(var i= 0,len=data[k].length;i<len;i++){
                            var obj= {};
                            obj.text= data[k][i].dictionary_value;
                            obj.value= data[k][i].dictionary_key;
                            arr.push(obj);
                        }
                        return arr;
                    }
                    function isChecked(arr,value){//checkbox增减
                        if(this.checked){
                            for(var j=0;j<arr.length;j++){
                                if(value == arr[j]){
                                    arr.splice(j,1);
                                }
                            }
                            arr.push(value);
                        }else{
                            for(var i=0;i<arr.length;i++){
                                if(value == arr[i]){
                                    arr.splice(i,1);
                                }
                            }
                        }
                    }
                    function checkedMatch(checkArr,arr){//checkbox匹配选中设置
                        for(var i=0;i<checkArr.length;i++){
                            for(var j=0;j<arr.length;j++){
                                if(checkArr[i].value == arr[j] ){
                                    checkArr[i].isChecked = true;
                                }
                            }
                        }
                    }
                    function changeDefauteValue(obj,value){//设置初始值
                        for(var i=0;i<obj.length;i++){
                            if(obj[i].value==value){
                                return i;
                            }
                        }
                    }
                    vm.addInterview = function(){
                        var dialogVm = avalon.getVm(getTypeId('interDialog'));
                        var formVm = avalon.getVm(getTypeId("interForm"));
                        var interTypeVm = avalon.getVm(getTypeId("interType"));//直营、加盟
                        var interNearVm = avalon.getVm(getTypeId("interNear"));//附近
                        var interUserVm = avalon.getVm(getTypeId("interUser"));//用户
                        var interNetVm = avalon.getVm(getTypeId("interNet"));//网络条件
                        var interTrainVm = avalon.getVm(getTypeId("interTrain"));//门店培训
                        var requestData = ['00000066','00000067','00000068','00000069','00000070','00000071','00000073','00000074'];
                        requestData = JSON.stringify(requestData);
                        util.c2s({
                            "url": erp.BASE_PATH + "dictionary/querySysDictionaryByTypeList.do",
                            "type": "post",
                            "contentType" : 'application/json',
                            "data":  requestData,
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    for(var k in data){
                                        if(k == '00000066'){
                                            interTypeVm.setOptions(selectToObj(k,data),changeDefauteValue(selectToObj(k,data),pageVm.interRequestData.joinType));
                                        }else if(k == '00000067'){
                                            interNearVm.setOptions(selectToObj(k,data),changeDefauteValue(selectToObj(k,data),pageVm.interRequestData.near));
                                        }else if(k == '00000068'){
                                            interUserVm.setOptions(selectToObj(k,data),changeDefauteValue(selectToObj(k,data),pageVm.interRequestData.customerType));
                                        }else if(k == '00000069'){
                                            pageVm.interCheckboxCardType = selectToObj(k,data);
                                            checkedMatch(pageVm.interCheckboxCardType,pageVm.interRequestData.cardType);
                                        }else if(k == '00000070'){
                                            pageVm.interCheckboxManagementSystem = selectToObj(k,data);
                                            checkedMatch(pageVm.interCheckboxManagementSystem,pageVm.interRequestData.managementSystem);
                                        }else if(k == '00000071'){
                                            pageVm.interCheckboxMemberRight = selectToObj(k,data);
                                            checkedMatch(pageVm.interCheckboxMemberRight,pageVm.interRequestData.memberRight);
                                        }else if(k == '00000073'){
                                            interNetVm.setOptions(selectToObj(k,data),changeDefauteValue(selectToObj(k,data),pageVm.interRequestData.networkCondition));
                                        }else if(k == '00000074'){
                                            interTrainVm.setOptions(selectToObj(k,data),changeDefauteValue(selectToObj(k,data),pageVm.interRequestData.storeTraining));
                                        }
                                    }
                                    dialogVm.title="商户访谈单";
                                    formVm.merchantName = pageVm.merchantData.merchant_name;
                                    if(pageVm.pageData.isDisabled == 3){
                                        dialogVm.readonly = 1;
                                    }
                                    if(pageVm.merchantData.attachment_name){//logo图
                                        pageVm.imgUrl=pageVm.path+pageVm.merchantData.filePath+'/'+pageVm.merchantData.attachment_path+'/'+pageVm.merchantData.attachment_name;
                                        pageVm.isLogo = true;
                                    }else{
                                        pageVm.imgUrl=pageVm.path+'images/defaultImg.jpg';
                                        pageVm.isLogo = false;
                                    }
                                    vm.interviewTabs = 1;
                                    dialogVm.open();
                                }
                            }
                        });

                    };
                    vm.changeInterviewTabs = function(value){
                        vm.interviewTabs = value;
                    };
                    vm.$addInterviewOpts= {
                        "dialogId": getTypeId('interDialog'),
                        "width" : 670,
                        "readonly" : 0,
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
                        "onOpen": function () {},
                        "onClose": function () {
                            var formVm = avalon.getVm(getTypeId("interForm"));
                            formVm.reset();
                            formVm.merchantName = '';

                        },
                        "submit": function (evt) {
                            console.log(pageVm.imgUrl);
                            vm.interRequestData.joinType = avalon.getVm(getTypeId("interType")).selectedValue;//直营、加盟
                            vm.interRequestData.near = avalon.getVm(getTypeId("interNear")).selectedValue;//附近
                            vm.interRequestData.customerType = avalon.getVm(getTypeId("interUser")).selectedValue;//用户
                            vm.interRequestData.networkCondition = avalon.getVm(getTypeId("interNet")).selectedValue;//网络条件
                            vm.interRequestData.storeTraining = avalon.getVm(getTypeId("interTrain")).selectedValue;//门店培训
                            vm.interRequestData.moduleType = "mkt";
                            var requestData = JSON.stringify(vm.interRequestData.$model);
                            if (avalon.getVm(getTypeId("interForm")).validate()) {
                                if(!pageVm.isLogo){
                                    util.confirm({
                                        "content": "请上传logo图！",
                                        "onSubmit": function (){}
                                    });
                                    return false;
                                }
                                util.c2s({
                                    "url": erp.BASE_PATH + 'mktBrandInterview/saveMktBrandInterview.do',
                                    "type": "post",
                                    "contentType": 'application/json',
                                    "data": requestData,
                                    "success": function (responseData) {
                                        if (responseData.flag == 1) {
                                            avalon.getVm(getTypeId('interDialog')).close();
                                            updateList({"merchantId":pageVm.merchantId},"mktShopSurvey/listMktShopSurveys.do",waitingCallBack);
                                            updateList({"merchantId":pageVm.merchantId},"synMerchant/getMerchantInfoByOrder.do",merchantCallBack);
                                        }
                                    }
                                });
                            }
                        }
                    };
                    vm.$addInterviewFormOpts = {
                        "formId": getTypeId("interForm"),
                        "field": [
                            {
                                "selector": ".contact",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "商户负责人不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".storeNumber",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "门店数不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".businessArea",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "营业面积不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".passengerFlow",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "午晚市客流不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".dailyTurnover",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "日均营业额不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".perOrder",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "单均消费不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".perCapita",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "人均消费不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".dailyCashierNumber",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "日收银笔数不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".groupPurchase",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "团购不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".coupon",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "优惠券不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".bankCardOffer",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "银行卡优惠不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".otherShopDiscount",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "其他店内优惠不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".sendCardNumber",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "已发卡数量不能为空";
                                    } else {
                                        var reg = /^[0-9]+\.?\d*$/;
                                        if(!reg.test(val)){
                                            return "已发卡数量必须为数字";
                                        }
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".whiteListSource",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "白名单来源不能为空";
                                    } else {
                                        return true;
                                    }
                                }]
                            },
                            {
                                "selector": ".whiteListNumber",
                                "rule": ["noempty", function (val, rs) {
                                    if (rs[0] !== true) {
                                        return "白名单数量不能为空";
                                    } else {
                                        var reg = /^[0-9]+\.?\d*$/;
                                        if(!reg.test(val)){
                                            return "白名单数量必须为数字";
                                        }
                                        return true;
                                    }
                                }]
                            }
                        ],
                        "merchantName" : '123'
                    };
                    vm.$addInterviewTypeSelectOpts = {//直营、加盟
                        "selectId": getTypeId("interType"),
                        "options": [],
                        "selectedIndex": 0
                    };
                    vm.$addInterviewNearSelectOpts = {//附近
                        "selectId": getTypeId("interNear"),
                        "options": [],
                        "selectedIndex": 0
                    };
                    vm.$addInterviewUserSelectOpts = {//用户
                        "selectId": getTypeId("interUser"),
                        "options": [],
                        "selectedIndex": 0
                    };
                    vm.$addInterviewNetSelectOpts = {//网络条件
                        "selectId": getTypeId("interNet"),
                        "options": [],
                        "selectedIndex": 0
                    };
                    vm.$addInterviewTrainSelectOpts = {//门店培训
                        "selectId": getTypeId("interTrain"),
                        "options": [],
                        "selectedIndex": 0
                    };
                    /*修改访谈单*/
                    vm.editInterview = function(id){
                        util.c2s({
                            "url": erp.BASE_PATH + 'mktBrandInterview/edit.do',
                            "type": "get",
                            "data": {"merchantId" : id},
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    vm.interRequestData = responseData.data;
                                    pageVm.merchantLogo = vm.path+responseData.data.relativePath;
                                    vm.addInterview();
                                }
                            }
                        });

                    };
                    /*上传logo*/
                    vm.$UploaderImgOpts = {//上传logo
                        "uploaderId": getTypeId('UploaderImgId'),
                        "uploadifyOpts": {
                            "uploader": erp.BASE_PATH + 'synMerchant/uploadLogo.do',
                            "fileObjName": "myfiles",
                            "multi": false, //多选
                            "fileTypeDesc": "上传附件(*.*)",
                            "fileTypeExts": "*.jpg; *.png; *.gif",
                            "formData": function () {
                                return {
                                    "sessionId": loginUserData.sessionId,
                                    "merchantId": pageVm.merchantId
                                };
                            },
                            "width": 140,
                            "height": 30
                        },
                        "onSuccessResponseData": function (responseText, file) {
                            responseText=JSON.parse(responseText);
                            if(responseText.flag){
                                var data = responseText.data[0];
                                pageVm.isLogo = true;
                                pageVm.imgUrl=data.relativePath;
                                pageVm.interRequestData.fileName = data.fileName;
                                pageVm.interRequestData.originalFileName = data.originalFileName;
                                pageVm.interRequestData.fileSize = data.fileSize;
                            }
                        }
                    };
                };
                interFn();
                /*访谈单-end*/
                /*修改访谈单-end*/
                /*通讯录*/
                var communicationFn = function(){
                    vm.communicationRequestData = {//通讯录入参数据
                        "moduleType":"mkt",
                        "merchantId":vm.merchantId,
                        "name":"",//姓名
                        "genderType":1,//性别
                        "birthday":'',//生日
                        "position":"",//职位
                        "mobilePhone":"",//手机
                        "telephone":"",//座机
                        "roleType":"3",//角色
                        "mail":''//邮箱
                    };
                    var communicationModel = {//通讯录入参数据
                        "moduleType":"mkt",
                        "merchantId":vm.merchantId,
                        "name":"",//姓名
                        "genderType":1,//性别
                        "birthday":'',//生日
                        "position":"",//职位
                        "mobilePhone":"",//手机
                        "telephone":"",//座机
                        "roleType":"3",//角色
                        "mail":''//邮箱
                    };
                    vm.addCommunication = function(selectType){
                        var dialogVm = avalon.getVm(getTypeId("addCommunicationDialog"));
                        var formVm = avalon.getVm(getTypeId("addCommunicationForm"));
                        var communicationSelectVm = avalon.getVm(getTypeId("addCommunicationSelect"));//角色
                        util.c2s({
                            "url": erp.BASE_PATH + "dictionary/querySysDictionaryByType.do",
                            "type": "post",
                            "data":  'dictionaryType=00000065',
                            "success": function (responseData) {
                                var data,obj;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    obj=_.map(data, function (itemData) {
                                        return {
                                            "text": itemData["dictionary_value"],
                                            "value": itemData["dictionary_key"]
                                        };
                                    });
                                    function changeDefauteValue(obj,value){//设置初始值
                                        for(var i=0;i<obj.length;i++){
                                            if(obj[i].value==value){
                                                return i;
                                            }
                                        }
                                    }
                                    communicationSelectVm.setOptions(obj,changeDefauteValue(obj,pageVm.communicationRequestData.roleType));
                                }
                            }
                        });
                        if(selectType == 'add'){
                            dialogVm.title = "添加联系人";
                            dialogVm.readonly = 0;
                            formVm.beginTime = moment(new Date()).format('YYYY-MM-DD');
                        }else if(selectType == 'edit'){
                            dialogVm.title = "查看联系人";
                            dialogVm.readonly = 1;
                            formVm.beginTime = moment(pageVm.communicationRequestData.birthday).format('YYYY-MM-DD');
                        }
                        dialogVm.open();
                    };
                    vm.changeSex = function(value){
                        pageVm.communicationRequestData.genderType = value;
                    };
                    vm.$addCommunicationOpts = {
                        "dialogId": getTypeId("addCommunicationDialog"),
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
                        "readonly": 0,
                        "onOpen": function () {},
                        "onClose": function () {
                            //清除验证信息
                            var formVm = avalon.getVm(getTypeId("addCommunicationForm"));
                            formVm.reset();
                            formVm.beginTime = '';
                        },
                        "submit": function (evt) {
                            var requestData,
                                dialogVm = avalon.getVm(getTypeId("addCommunicationDialog")),
                                formVm = avalon.getVm(getTypeId("addCommunicationForm"));
                            pageVm.communicationRequestData.roleType = avalon.getVm(getTypeId("addCommunicationSelect")).selectedValue;
                            pageVm.communicationRequestData.birthday = moment(formVm.beginTime, 'YYYY-MM-DD')._d/1;
                            pageVm.communicationRequestData.moduleType = 'mkt';
                            requestData = JSON.stringify(pageVm.communicationRequestData.$model);
                            if (formVm.validate()) {
                                util.c2s({
                                    "url": erp.BASE_PATH + 'contact/saveMktContact.do',
                                    "type": "post",
                                    "contentType" : 'application/json',
                                    "data": requestData,
                                    "success": function (responseData) {
                                        if (responseData.flag == 1) {
                                            dialogVm.close();
                                            updateList({"merchantId":pageVm.merchantId},"mktShopSurvey/listMktShopSurveys.do",waitingCallBack);
                                            vm.communicationRequestData = communicationModel;
                                        }
                                    }
                                });
                            }
                        }
                    };
                    vm.$addCommunicationFormOpts = {//form表单初始化
                        "formId": getTypeId("addCommunicationForm"),
                        "field":
                            [
                                {
                                    "selector": ".name",
                                    "rule": ["noempty", function (val, rs) {
                                        if (rs[0] !== true) {
                                            return "姓名不能为空";
                                        } else {
                                            return true;
                                        }
                                    }]
                                },
                                {
                                    "selector": ".position",
                                    "rule": ["noempty", function (val, rs) {
                                        if (rs[0] !== true) {
                                            return "职位不能为空";
                                        } else {
                                            return true;
                                        }
                                    }]
                                },
                                {
                                    "selector": ".mobilePhone",
                                    "rule": ["noempty", function (val, rs) {
                                        if (rs[0] !== true) {
                                            return "手机不能为空";
                                        } else {
                                            return true;
                                        }
                                    }]
                                },
                                {
                                    "selector": ".telephone",
                                    "rule": ["noempty", function (val, rs) {
                                        if (rs[0] !== true) {
                                            return "座机不能为空";
                                        } else {
                                            return true;
                                        }
                                    }]
                                },
                                {
                                    "selector": ".email",
                                    "rule": ["noempty", function (val, rs) {
                                        if (rs[0] !== true) {
                                            return "邮箱不能为空";
                                        } else {
                                            return true;
                                        }
                                    }]
                                }
                            ],
                        "beginTime" : ''
                    };
                    vm.$communicationOpts = {//角色select
                        "selectId": getTypeId("addCommunicationSelect"),
                        "options": [],
                        "selectedIndex": 1,
                        "beginTime" : moment(new Date()).format('YYYY-MM-DD')

                    };
                    vm.$startDateOpts = {//日历-生日插件
                        "calendarId": getTypeId("startDateCalendarId"),
                        //"minDate": new Date(),
                        "onClickDate": function (d) {
                            var formVm = avalon.getVm(getTypeId("addCommunicationForm"));
                            formVm.beginTime = moment(d).format('YYYY-MM-DD');
                            $(this.widgetElement).hide();
                        }
                    };
                    vm.openStartDateCalendar = function () {//日历-生日插件
                        var formVm = avalon.getVm(getTypeId("addCommunicationForm"));
                        var meEl = $(this),
                            calendarVm = avalon.getVm(getTypeId("startDateCalendarId")),
                            calendarEl,
                            inputOffset = meEl.offset();
                        if (!calendarVm) {
                            calendarEl = $('<div ms-widget="calendar,$,$startDateOpts"></div>');
                            calendarEl.css({
                                "position": "absolute",
                                "z-index": 10000
                            }).hide().appendTo('body');
                            avalon.scan(calendarEl[0], [vm]);
                            calendarVm = avalon.getVm(getTypeId("startDateCalendarId"));
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
                        if (formVm.beginTime) {
                            calendarVm.focusDate = moment(formVm.beginTime, 'YYYY-MM-DD')._d;
                        } else {
                            calendarVm.focusDate = new Date();
                        }
                        calendarEl.css({
                            "top": inputOffset.top + meEl.outerHeight() - 1,
                            "left": inputOffset.left
                        }).show();
                    };
                };
                communicationFn();
                /*通讯录-end*/
                /*查看通讯录*/
                vm.showCommunication = function(str){
                    util.c2s({
                        "url": erp.BASE_PATH + "contact/editContact.do",
                        "type": "post",
                        "data":  {contactIdStr:str},
                        "success": function (responseData) {
                            var data;
                            if (responseData.flag) {
                                data = responseData.data;
                                pageVm.communicationRequestData = data;
                                vm.addCommunication('edit');
                            }
                        }
                    });
                };
                /*查看通讯录*/
                /*步骤确认完成*/
                vm.stepEnd = function() {
                    util.confirm({
                        "content": "你确定完成了吗？",
                        "onSubmit": function () {
                            util.c2s({
                                "url": erp.BASE_PATH + "mktShopSurvey/salesComfirm.do",
                                "type": "get",
                                "contentType": 'application/json',
                                "data": {merchantId: pageVm.merchantId},
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        updateList({"merchantId": pageVm.merchantId}, "mktShopSurvey/listMktShopSurveys.do", waitingCallBack);
                                    }
                                }
                            });
                        }
                    });
                };
                /*步骤确认完成-end*/
            });
            avalon.scan(pageEl[0]);
            /*页面初始化请求渲染*/
            function updateList(obj,url,callBack){//其他渲染方法
                var requestData=JSON.stringify(obj);
                pageVm.pageData = [];
                util.c2s({
                    "url": erp.BASE_PATH + url,
                    "type": "post",
                    "contentType" : 'application/json',
                    "data": requestData,
                    "success": callBack
                });
            }
            /*页面初始化请求渲染-end*/
            /*商户回调*/
            updateList({"merchantId":pageVm.merchantId},"synMerchant/getMerchantInfoByOrder.do",merchantCallBack);
            function merchantCallBack(responseData){
                var data;
                if (responseData.flag) {
                    data = responseData.data;
                    pageVm.interRequestData.merchantName = data.merchantInfo.merchant_name;
                    var obj = {};
                    obj.productList = data.productList;
                    obj.filePath = data.filePath;
                    var tmpObj = data.merchantInfo;
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
                }
            }
            console.log(pageVm.path);
            /*商户回调end*/
            /*list*/
            updateList({"merchantId":pageVm.merchantId},"mktShopSurvey/listMktShopSurveys.do",waitingCallBack);
            function waitingCallBack(responseData){
                var data;
                if (responseData.flag) {
                    data = responseData.data;
                    pageVm.pageData= data;
                }
            }
            /*list-end*/
            /*页面初始化请求渲染-end*/
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
