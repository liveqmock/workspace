/**
 * 前端服务-我的主页
 */
define(['avalon', 'util', 'moment', 'json', '../../../../widget/form/select', '../../../../widget/dialog/dialog', '../../../../widget/form/form', '../../../../widget/uploader/uploader', '../../../../module/schedule/schedule', '../../../../widget/editor/editor', '../../../../module/addquestion/addquestion'], function (avalon, util, moment, JSON) {
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
            function forValue(data, arr) {
                var tmpArr = [];
                if (data instanceof Array) {
                    for (var i = 0; i < data.length; i++) {
                        var tmpObj = {};
                        for (var k in data[i]) {
                            var value = data[i].hasOwnProperty(k);
                            if (value) {
                                for (var j = 0; j < arr.length; j++) {
                                    if (k == arr[j]) {
                                        tmpObj[arr[j]] = data[i][k];
                                    }
                                }
                            }
                        }
                        tmpArr.push(tmpObj);
                    }
                } else {
                    var tmpObj = {};
                    for (var k in data) {
                        var value = data[i].hasOwnProperty(k);
                        if (value) {
                            for (var j = 0; j < arr.length; j++) {
                                if (k == arr[j]) {
                                    tmpObj[arr[j]] = data[i][k];
                                }
                            }
                        }
                    }
                    tmpArr.push(tmpObj);
                }
                return tmpArr;
            }

            var loginUserData = erp.getAppData('user');
            var date = new Date();
            var scheduleId = pageName + '-schedule';
            var pageVm = avalon.define(pageName, function (vm) {
                var getTypeId = function (n) {//设置和获取widget的id;
                    var widgetId = pageName + n;
                    tempWidgetIdStore.push(widgetId);
                    return widgetId;
                };
                /*vm.navCrumbs = [
                    {
                        "text": "我的主页",
                        "href": "#/frontend/home"
                    },
                    {
                        "text": "客户服务看板"
                    }
                ];
                vm.belongToRouteName = routeData.route.split('/')[1];   //所属主路由导航 frontend/sales*/
                vm.path = erp.BASE_PATH;
                vm.merchantId = parseInt(routeData.params["id"]);
                vm.merchantName = '';
                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.modelType = parseInt(routeData.params["type"]);
                /*前端是1，销售是2*/
                vm.pageStatus = 1;
                vm.pageData = {};
                vm.imgUrl = '';
                vm.upfile = '';
                vm.promise = false;//权限
                vm.localHref = function (url) {//页面跳转
                    util.jumpPage(url);
                };
                /*设置导航*/
                var type = routeData.params["type"];

                if (type == 'sale') {
                    //vm.navCrumbs[0].href = "#/sales/home";
                    var info = JSON.parse(decodeURIComponent(routeData.params["info"]));
                    vm.navCrumbs = info;
                }else{
                    //vm.navCrumbs[0].href = "#/frontend/home";
                    var info = JSON.parse(decodeURIComponent(routeData.params["info"]));
                    vm.navCrumbs = info;

                }
                /*设置导航end*/



                /*提醒*/
                vm.warn = [
                    {
                        insertTime: '',
                        itemContent: ''
                    }
                ];
                /*提醒-end*/
                /*merchant信息*/
                var merchantModel = function () {
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
                };
                merchantModel();
                /*merchant信息-end*/
                vm.requestDataDispose = {//投诉处理
                    id: 0,
                    handledBy: vm.pageUserId,
                    handledTime: new Date().getTime(),
                    handledDescription: '',
                    customerComplaintStatus: 1
                };
                vm.changePageStatus = function (value, obj) {//大标签切换
                    vm.pageStatus = value;
                    if (value == 2) {
                        vm.cardFn();
                    }
                };
                /*卡信息*/
                var cardModel = function () {
                    vm.responseCardData = [
                        {
                            "card_count": 216481,//（卡余量）
                            "card_price": 0,//（单价）
                            "card_type": "粉丝会员",//（卡类型）
                            "merchant_type": "品牌",//（商户类型）
                            "is_sold_card": false,//（是否实体卡）
                            "merchant_name": "北京新辣道餐饮管理有限公司",//（商户名称）
                            "merchant_id": 1119,//（商户ID，不取）
                            "cardtype_id": 207,
                            "description": "暂无",
                            "card_name": "积分卡"//（卡名称）
                        }
                    ];
                    vm.cardFn = function () {
                        util.c2s({
                            "url": erp.BASE_PATH + 'synMembershipCard/listSynMembershipCards.do',
                            "type": "post",
                            "data": {pageNumber: 1, pageSize: 10000, merchantId: pageVm.merchantId},
                            "success": function (responseData) {
                                if (responseData.flag) {
                                    var arr = responseData.data.rows;
                                    var modelArr = [
                                        "card_count", "card_price", "card_type", "merchant_type", "is_sold_card", "merchant_name", "cardtype_id", "description", "card_name"
                                    ];
                                    pageVm.responseCardData = forValue(arr, modelArr);
                                }
                            }
                        });
                    }
                };
                cardModel();
                /*卡信息-end*/
                /*健康度*/
                var healthModel = function () {
                    var healthYear = date.getFullYear();
                    var healthMonth = (date.getMonth() + 1);
                    var healthDate = healthYear + '-' + healthMonth;
                    vm.healthResponseData = [
                        {//健康度出参
                            actualValue: 0,
                            healthDegree: 0,
                            targetType: "",
                            targetValue: 0
                        }
                    ];
                    vm.healthRequestData = {//健康度入参
                        "merchantId": vm.merchantId,
                        "date": healthDate
                    };
                    vm.healthMonth = healthMonth;
                    vm.nextHealthBtn = false;
                    vm.prevHealth = function () {
                        healthMonth--;
                        if (healthMonth <= 0) {
                            healthMonth = 12;
                            healthYear--;
                        }
                        vm.healthMonth = healthMonth;
                        healthDataCallBack();
                    };
                    vm.nextHealth = function () {
                        healthMonth++;
                        if (healthMonth > 12) {
                            healthMonth = 1;
                            healthYear++;
                        }
                        vm.healthMonth = healthMonth;
                        healthDataCallBack();
                    };
                    function healthDataCallBack() {
                        var now = new Date();
                        if (healthYear <= now.getFullYear() && healthMonth < (now.getMonth() + 1)) {
                            vm.nextHealthBtn = true;
                        } else {
                            vm.nextHealthBtn = false;
                        }

                        healthDate = healthYear + '-' + healthMonth;
                        vm.healthRequestData.date = healthDate;
                        updateList(vm.healthRequestData.$model, "health/getHealthDegreeByMerchantId.do", healthCallBack);
                    }
                };
                healthModel();
                /*健康度-end*/


                /*月日报*/
                vm.monthDayTabs = 1;
                vm.monthlyMonth = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];//月报月份
                vm.monthlyDay = [];//日报天
                vm.currentMonth = 1;//当前月份
                vm.currentDay = 1;//当前日
                vm.maxMonth = 0;//最大月
                vm.maxDay = 0;//最大天


                var curYear = date.getFullYear();
                var curMonth = date.getMonth() + 1;
                var curDay = date.getDate();
                //获取当月最多天数
                vm.getMaxDay = function (year, month) {
                    return new Date(year, month, 0).getDate();
                };

                //初始化月报日期
                vm.setMonthInfo = function () {
                    var yearArr = [],
                        atIndex = -1;
                    for (var i = 2012; i <= curYear; i++) {
                        yearArr.push({"text": i + "年", "value": i});
                    }
                    _.some(yearArr,function(item,index){
                        if (item.value === curYear) {
                            atIndex = index;
                            return true;
                        }
                    });
                    avalon.getVm(getTypeId('-monthYear')).setOptions(yearArr,atIndex);
                };
                //初始化日报日期
                vm.setDayInfo = function () {
                    pageVm.monthlyDay = [];
                    var yearArr = [],
                        atIndex = -1;
                    //设置日报的年
                    for (var i = 2012; i <= curYear; i++) {
                        yearArr.push({"text": i + "年", "value": i});
                    }
                    _.some(yearArr,function(item,index){
                        if (item.value === curYear) {
                            atIndex = index;
                            return true;
                        }
                    });
                    avalon.getVm(getTypeId('-dayYear')).setOptions(yearArr,atIndex);
                };
                //标签切换
                vm.changeMothDay = function () {
                    var val = $(this).attr('data-value');
                    vm.monthDayTabs = val;
                    if (val == 1) {
                        vm.setMonthInfo();
                    } else if (val == 2) {
                        vm.setDayInfo();
                    }
                };
                vm.$monthYearOpts = {
                    "selectId": getTypeId('-monthYear'),
                    "options": [
                        {
                            "text": "",
                            "value": ""
                        }
                    ],
                    "onSelect": function (v, t, o) {
                        if (v < curYear) {
                            vm.maxMonth = 12;
                            vm.currentMonth = 1;
                        } else {
                            vm.maxMonth = curMonth;
                            vm.currentMonth = curMonth;
                        }
                        var monthData = v +'-'+ ((vm.currentMonth.toString().length === 1)?(0+vm.currentMonth.toString()):vm.currentMonth);
                        vm.reportRequestData.date = monthData;
                        updateList(vm.reportRequestData.$model, "report/getMonthMerchantSummary.do", reportCallBack);
                    }
                };
                vm.$dayYearOpts = {//日报年
                    "selectId": getTypeId('-dayYear'),
                    "options": [
                        {
                            "text": "",
                            "value": ""
                        }
                    ],
                    "onSelect": function (v, t, o) {
                        var dayMonthArr = [];
                        var maxMonth = 0;
                        var atIndex = -1;
                        if (v < curYear) {
                            maxMonth = 12;
                            atIndex = 0;
                        } else {
                            maxMonth = curMonth;
                            atIndex = curMonth -1;
                        }
                        for (var i = 1; i<= maxMonth; i++) {
                            dayMonthArr.push({"text": i + '月', "value": i});
                        }
                        avalon.getVm(getTypeId('-dayMonth')).setOptions(dayMonthArr,atIndex);
                    }
                };
                vm.$dayMonthOpts = {//日报月
                    "selectId": getTypeId('-dayMonth'),
                    "options": [
                        {
                            "text": "",
                            "value": ""
                        }
                    ],
                    "onSelect": function (v, t, o) {
                        pageVm.monthlyDay = [];
                        var selectYear = avalon.getVm(getTypeId('-dayYear')).selectedValue;
                        var monthMaxDay = vm.getMaxDay(selectYear,v);
                        for(var i=1;i<=monthMaxDay;i++){
                            pageVm.monthlyDay.push(i);
                        }
                        if(selectYear<curYear){
                            vm.maxDay = 31;
                            vm.currentDay = 1;
                        }else if(selectYear == curYear){
                            if(v == curMonth){
                                vm.maxDay = curDay;
                                vm.currentDay = curDay;
                            }else{
                                vm.maxDay = 31;
                                vm.currentDay = 1;
                            }
                        }
                        v =((v.toString().length === 1)?(0+v.toString()):v);
                        vm.currentDay =((vm.currentDay.toString().length === 1)?(0+vm.currentDay.toString()):vm.currentDay);
                        vm.reDayRequestData.date = moment(selectYear+'-'+v+'-'+vm.currentDay)/1
                        updateList(vm.reDayRequestData.$model,"report/getDailyMerchantSummary.do",reDayCallBack);
                    }
                };

                //修改月报信息
                vm.getMonthData = function () {
                    var val = $(this).html();
                    vm.currentMonth = val;
                    var year = avalon.getVm(getTypeId('-monthYear')).selectedValue;
                    if (val.toString().length == 1) {
                        month = 0 + val.toString();
                    } else {
                        month = val;
                    }
                    var reportDate = year + '-' + month;
                    vm.reportRequestData.date = reportDate;
                    updateList(vm.reportRequestData.$model, "report/getMonthMerchantSummary.do", reportCallBack);
                };
                //修改日报信息
                vm.getDayData = function () {
                    var val = $(this).html();
                    vm.currentDay = val;
                    var curYear = avalon.getVm(getTypeId('-dayYear')).selectedValue;
                    var curMonth = avalon.getVm(getTypeId('-dayMonth')).selectedValue;
                    curMonth = ((curMonth.toString().length === 1)?(0+curMonth.toString()):curMonth);
                    val = ((val.toString().length === 1)?(0+val.toString()):val);
                    vm.reDayRequestData.date = moment(curYear + '-' + curMonth + '-' + val) / 1;
                    updateList(vm.reDayRequestData.$model, "report/getDailyMerchantSummary.do", reDayCallBack);
                };


                vm.reportResponseData = {};//月报出参
                vm.reDayResponseData = {};//日报出参
                vm.reportRequestData = {//月报入参
                    "merchantId": vm.merchantId,
                    "date": ''
                };
                vm.reDayRequestData = {//日报入参
                    "merchantId": vm.merchantId,
                    "date": ''
                };
                /*月日报-end*/


                /*修改信息*/
                vm.upLogoIng = true;//判断logo是否上传完成
                vm.dicforParentText = '';//业态中文父目录显示
                vm.dicforChildrenText = [];//业态中文子目录显示
                vm.formatParentId = '';//业态value父
                vm.formatChildId = [];//业态value子
                vm.formatChild = [];//业态子内容
                vm.modifyData = {//后台出参数
                    attachDocument: {
                        id: 0,
                        attachmentName: "",
                        fileFullPath:''
                    },
                    attachmentId: "",
                    dicFormat: [],
                    dicsales: [],
                    format: [],
                    contact: [],
                    perCapita: "",
                    perOrder: "",
                    salesId: 0,
                    contactId: 0,
                    storeNumber: ""
                };
                vm.modifyRequestData = {//后台出入参
                    "merchantId": vm.merchantId,
                    "perOrder": "",//桌均
                    "storeNumber": "",//门店数
                    "format": [],//业态id
                    "salesId": 0,//当前商户销售负责人id
                    "perCapita": "",  //人均
                    "attachmentId": "",//附件id
                    "contactId": 0//商户负责人id
                };
                vm.modifyMess = function(){
                    var dialogVm = avalon.getVm(getTypeId('-modifyMessId'));
                    util.c2s({
                        "url": erp.BASE_PATH + 'synMerchant/loadMerchantInfo.do',
                        "type": "post",
                        "contentType": 'application/json',
                        "data": JSON.stringify({'merchantId': pageVm.merchantId}),
                        "success": function (responseData) {
                            if (responseData.flag == 1) {
                                var indexAt = 0,indexAt2 = 0;
                                var data = responseData.data;
                                vm.modifyData = data;
                                vm.modifyRequestData.perOrder = data.perOrder;//桌均
                                vm.modifyRequestData.storeNumber = data.storeNumber;//门店数
                                vm.modifyRequestData.perCapita = data.perCapita;//人均
                                vm.modifyRequestData.format = data.format;//业态id
                                vm.modifyRequestData.salesId = data.salesId;//当前商户销售负责人id
                                vm.modifyRequestData.attachmentId = data.attachmentId;//附件id
                                //销售负责人
                                _.map(vm.modifyData.dicsales,function(item,index){
                                    if(item['value'] == vm.modifyData.salesId){
                                        indexAt = index;
                                    }
                                });
                                avalon.getVm(getTypeId('-salesPersonId')).setOptions(vm.modifyData.dicsales,indexAt);
                                //销售负责人end
                                //商户负责人
                                _.map(vm.modifyData.contact,function(item,index){
                                    if(item['value'] == vm.modifyData.contactId){
                                        indexAt2 = index;
                                    }
                                });
                                avalon.getVm(getTypeId('-merchantPersonId')).setOptions(vm.modifyData.contact,indexAt2);
                                //商户负责人end
                                //业态
                                _.map(vm.modifyData.dicFormat,function(item,index){
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
                                if(!(data.attachDocument.fileFullPath)){
                                    data.attachDocument.fileFullPath = 'portal/origin/asset/image/merchant-default-logo.jpg';
                                }
                                dialogVm.open();
                            }
                        }
                    });
                };
                vm.$modifyMessDisposeOpts = {
                    "dialogId": getTypeId('-modifyMessId'),
                    "title": '修改商户信息',
                    "width": '490px',
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
                    "onOpen": function () {
                    },
                    "onClose": function () {
                        vm.dicforParentText = '';//业态中文父目录显示
                        vm.dicforChildrenText = [];//业态中文子目录显示
                        vm.formatParentId = '';//业态value父
                        vm.formatChildId = [];//业态value子
                        vm.formatChild = [];//业态子内容
                    },
                    "submit": function (evt) {
                        var flag = util.testing(avalon.getVm(getTypeId('-modifyMessId')).widgetElement);
                        vm.modifyRequestData.format = [].concat(vm.formatChildId,vm.formatParentId);
                        if(flag){
                            util.c2s({
                                "url": erp.BASE_PATH + 'synMerchant/updateSynMerchantInfo.do',
                                "type": "post",
                                "contentType": 'application/json',
                                "data": JSON.stringify(vm.modifyRequestData.$model),
                                "success": function (responseData) {
                                    if (responseData.flag == 1) {
                                        avalon.getVm(getTypeId('-modifyMessId')).close();
                                        util.alert({
                                            "content": "修改成功！",
                                            "iconType": "success"
                                        });
                                    }
                                }
                            });
                        }
                    }
                };
                //显示业态弹层
                vm.showFormat = function(){
                    $('.format-box').show();
                    $(document).on('click',function(e){
                        e=e||window.event;
                        var target=e.target||e.srcElement;
                        if(!$('.table-td-relative').has(target).length){
                            $('.format-box').hide();
                        }
                        if(target === $('.closeLineFormat')[0]){
                            $('.format-box').hide();
                        }
                    })
                };
                //显示子目录且设置tree的父节点isSelected字段
                vm.showFormatChildren = function(){
                    var value = $(this).attr('data-value');
                    var text = $(this).attr('data-text');
                    var arr = vm.modifyData.dicFormat;
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
                //销售负责人
                vm.$salesPersonOpts = {
                    "selectId": getTypeId('-salesPersonId'),
                    "options": [{'text':1111,value:1111}],
                    "onSelect": function (v, t, o) {
                        vm.modifyRequestData.salesId = v;
                    }
                };
                //商户负责人
                vm.$merchantPersonOpts = {
                    "selectId": getTypeId('-merchantPersonId'),
                    "options": [{'text':1111,value:1111}],
                    "onSelect": function (v, t, o) {
                        vm.modifyRequestData.contactId = v;
                    }
                };
                //商户logo
                vm.$UploaderLogoOpts = {
                    "uploaderId": getTypeId('-UploaderLogoOptId'),
                    "uploadifyOpts": {
                        "uploader": erp.BASE_PATH + 'synMerchant/uploadCommonMethod.do',
                        "fileObjName": "myfile",
                        "multi": false, //多选
                        "fileTypeDesc": "上传附件(*.*)",
                        "fileTypeExts": "*.*",
                        "formData": function () {
                            return {
                                "sessionId": loginUserData.sessionId,
                                "flag": 1
                            };
                        },
                        "width": 110,
                        "height": 30,
                        "onUploadProgress": function (file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
                            vm.upLogoIng = false;
                            var ratio = (bytesUploaded / bytesTotal) * 100 > 100 ? 100 : (bytesUploaded / bytesTotal) * 100;
                            ratio = ratio+'%';
                            var fileName = file.name;
                            var tip = '';
                            if (ratio === '100%') {
                                tip = '上传完成';
                            } else {
                                tip = '正在上传：' + fileName;
                            }
                            if (!bytesUploaded) {
                                $('.modify-mess-logo').append(
                                    '<div class="step3-upfile-model" style="position:relative;padding:0px;width:425px;height: 25px;border:1px solid #ccc;background:#bebebe;">' +
                                        '<p class="step3-loding" style="position:absolute;left:0;top:-1px;width:' + ratio + ';height:27px;background:#67bb0d;"></p>' +
                                        '<p class="step3-tips" style="position:absolute;left:0;top:0;height:25px;line-height:25px;text-align:center;width:100%;color:#fff;">' + tip + '</p>' +
                                        '</div>'
                                );
                            } else {
                                $('.step3-loding').css('width',ratio);
                                $('.step3-tips').html(tip);
                            }
                        }
                    },
                    "onSuccessResponseData": function (responseText, file) {
                        responseText = JSON.parse(responseText);
                        if (responseText.flag) {
                            setTimeout(function () {
                                vm.upLogoIng = true;
                                $('.step3-upfile-model').remove();
                                var data = responseText.data;
                                vm.modifyData.attachDocument.fileFullPath = data.fileFullPath;
                                vm.modifyRequestData.attachmentId = data.id;
                            }, 1000);
                        } else {
                            $('.step3-upfile-model').remove();
                            util.alert({
                                "content": "上传失败！",
                                "iconType": "error"
                            });
                            vm.upLogoIng = true;
                        }
                    }
                };
                /*修改信息结束*/

                /*发送月报*/
                vm.replaceData = [];
                vm.linkMan = {
                    data: [],
                    flag: 0
                };
                vm.mailModel = {
                    data: [],
                    flag: 0
                };
                vm.mailTo = '';
                vm.errorMail = '';//邮件错误
                vm.mailRequestData = {
                    sendAddress: loginUserData.userName,// 发件人地址
                    to: [],//联系人邮件地址
                    context: '',//邮件正文(字符串)
                    subject: '',//邮件标题(字符串)
                    merchantId : vm.merchantId,
                    mailTemplateId : 0
                };
                vm.$editorOpts = {
                    "editorId": getTypeId('editorId'),
                    "ueditorOptions": {
                        "UEDITOR_HOME_URL": erp.WIDGET_PATH + 'editor/ueditor/',
                        "serverUrl": erp.BASE_PATH + 'portal/origin/widget/editor/ueditor/jsp/controller.jsp',
                        "toolbars": [
                            [
                                'fullscreen', 'source', '|', 'undo', 'redo', '|',
                                'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                                'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                                'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                                'directionalityltr', 'directionalityrtl', 'indent', '|',
                                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                                'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter','simpleupload'
                            ]
                        ]
                    }
                };
                vm.onLineMail = function () {
                    var dialog = avalon.getVm(getTypeId('onLineMailDialogId'));
                    util.c2s({
                        "url": erp.BASE_PATH + 'mailAndAttachment/replaceMailTempment.do',
                        "type": "post",
                        "contentType": 'application/json',
                        "data": JSON.stringify({'merchantId': pageVm.merchantId}),
                        "success": function (responseData) {
                            if (responseData.flag == 1) {
                                pageVm.replaceData = responseData.data;
                                dialog.title = '发送月报';
                                dialog.open();
                            }
                        }
                    });

                };
                vm.addLinkman = function () {
                    $(document).on('click', function (e) {
                        var target = e.target;
                        if (target.id != 'addLinkMan') {
                            pageVm.linkMan.flag = 0;
                        }
                        if (target.id != 'addMailModel') {
                            pageVm.mailModel.flag = 0;
                        }
                    });
                    util.c2s({
                        "url": erp.BASE_PATH + 'contact/searchContacts.do',
                        "type": "post",
                        "contentType": 'application/json',
                        "data": JSON.stringify({'merchantId': pageVm.merchantId}),
                        "success": function (responseData) {
                            if (responseData.flag == 1) {
                                pageVm.linkMan.flag = 1;
                                pageVm.linkMan.data = responseData.data.rows;
                            }
                        }
                    });
                };
                vm.addPut = function () {
                    var flag = true;
                    var mailVal = this.$vmodel.$model.el.mail;
                    if (pageVm.mailTo.length) {
                        var arr = pageVm.mailTo.split(';');
                        for (var i = 0; i < arr.length; i++) {
                            if (arr[i] == mailVal) {
                                flag = false;
                            }
                        }
                        if(flag){
                            pageVm.mailTo += ';' + mailVal;
                        }
                    } else {
                        pageVm.mailTo += mailVal;
                    }
                    pageVm.linkMan.flag = 0;
                };
                /*添加收件人失去焦点事件*/
                setTimeout(function(){
                    $('.customer-line-mail-to').on('blur',function(){
                        var flag = true;
                        var arr = pageVm.mailTo.split(';');
                        for (var i = 0; i < arr.length; i++) {
                            var reg = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
                            if(!(reg.test(arr[i]))){
                                flag = false;
                                vm.errorMail = arr[i];
                                var dialogVm = avalon.getVm(getTypeId('onLineMailDialogId'));
                                util.testing($(dialogVm.widgetElement)[0],$('.common-mail-mail-line-test'));
                                return;
                            }
                        }
                        if(flag){
                            vm.errorMail = '';
                            var dialogVm = avalon.getVm(getTypeId('onLineMailDialogId'));
                            util.testing($(dialogVm.widgetElement)[0],$('.common-mail-mail-line-test'));
                        }
                    });
                },2000);
                vm.addMailModel = function () {
                    util.c2s({
                        "url": erp.BASE_PATH + 'fesOnlineProcess/getEmailTemplates.do',
                        "type": "post",
                        "contentType": 'application/json',
                        "data": JSON.stringify({"mailTemplateType": "002"}),
                        "success": function (responseData) {
                            if (responseData.flag == 1) {
                                pageVm.mailModel.data = responseData.data.rows;
                                pageVm.mailModel.flag = 1;
                            }
                        }
                    });
                };
                //选择邮件模板
                vm.addPutEmailModel = function () {
                    var modelVal = this.$vmodel.$model.el.content;
                    _.map(vm.replaceData,function(item,index){
                        var reg = RegExp('\\[' + item['text'] + ']', 'g');
                        modelVal = modelVal.replace(reg ,item['value']);
                    });
                    var id = this.$vmodel.$model.el.id;
                    var editorVm = avalon.getVm(getTypeId('editorId'));
                    editorVm.core.setContent(modelVal);
                    pageVm.mailModel.flag = 0;
                    pageVm.mailRequestData.subject = this.$vmodel.$model.el.title;
                    vm.mailRequestData.mailTemplateId = id;
                };
                vm.$onLineMailDialogOpts = {
                    "dialogId": getTypeId('onLineMailDialogId'),
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                            footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                '<div class="ui-dialog-btns">' +
                                '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">发&nbsp;送</button>' +
                                '<span class="separation"></span>' +
                                '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button>' +
                                '</div>' +
                                '</div>';
                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "onOpen": function () {
                    },
                    "onClose": function () {
                        var editorVm = avalon.getVm(getTypeId('editorId'));
                        editorVm.core.setContent('');
                        vm.mailRequestData.to = [];
                        vm.mailRequestData.context = '';
                        vm.mailRequestData.subject = '';
                        vm.mailRequestData.mailTemplateId = 0;
                    },
                    "width": 700,
                    "submit": function (evt) {
                        var dialogVm = avalon.getVm(getTypeId('onLineMailDialogId')),
                            editorVm = avalon.getVm(getTypeId('editorId'));
                            var flag;
                            pageVm.mailRequestData.context = editorVm.core.getContent();
                            pageVm.mailRequestData.to = pageVm.mailTo.split(';');
                            flag = util.testing($(dialogVm.widgetElement)[0]);
                        if (flag) {
                            util.c2s({
                                "url": erp.BASE_PATH + 'fesOnlineProcess/sendMonthlyReprot.do',//后端要求修改（修改了接口并增加了merchantId）
                                "type": "post",
                                "contentType": 'application/json',
                                "data": JSON.stringify(pageVm.mailRequestData.$model),
                                "success": function (responseData) {
                                    if (responseData.flag == 1) {
                                        dialogVm.close();
                                    }
                                }
                            });

                        }
                    }
                };
                vm.$onLineMailFormOpts = {
                    "formId": getTypeId('onLineMailFormId')
                };
                vm.appendValue = function (){
                    var text = this.innerHTML;
                    var editorVm = avalon.getVm(getTypeId('editorId'));
                    if(editorVm.core.isFocus()){
                        editorVm.core.execCommand('insertHtml',text);
                    }
                };
                /*发送月报*/
                /**
                 * 工作进度表
                 */
                vm.$scheduleOpts = {
                    "scheduleId": scheduleId,
                    "onFetchPlan": function (requestData, callback) {
                        //查询本月的计划信息
                        util.c2s({
                            "url": erp.BASE_PATH + "fesPlan/queryFesPlanList.do",
                            "type": "post",
                            "contentType": "application/json",
                            "data": JSON.stringify(_.extend({
                                //"userId": loginUserData.id,
                                "merchantId": vm.merchantId
                            }, requestData)),
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    callback(data);
                                } else {
                                    callback([]);
                                }
                            }
                        });
                    },
                    "onClickDateCell": function (evt) {
                        var itemM = this.$vmodel.$model,
                            cellDate = itemM.el.date,
                            targetEl = $(evt.target),
                            now = new Date(),
                            planData;
                        if (!moment(cellDate).isBefore(now, 'day')) {   //今天或以后日期点击跳转到添加计划页
                            if (targetEl.is('.plan-item')) {
                                planData = targetEl.data('plan');
                                //如果是销售看板，不能进行添加和编辑操作，只能查看自己的工作计划
                                if (vm.belongToRouteName === "sales") {
                                    if (planData.user_id == loginUserData.id) {
                                        util.jumpPage('#/' + vm.belongToRouteName + '/home/customerline/plan/list/' + moment(cellDate) / 1 + '/' + targetEl.data('planId'));
                                    }
                                    return;
                                }
                                //前端服务处理
                                if (planData.user_id == loginUserData.id && targetEl.data('status') == "1") {  //登录用户id和plan所属用户id一致，并且处于未完成状态下，跳到相应的编辑页
                                    util.jumpPage('#/' + vm.belongToRouteName + '/home/customerline/plan/edit/' + moment(cellDate).hour(9) / 1 + '/' + targetEl.data('planId'));
                                } else {
                                    util.jumpPage('#/' + vm.belongToRouteName + '/home/customerline/plan/list/' + moment(cellDate) / 1 + '/' + targetEl.data('planId'));  //跳到单独的plan查看
                                }
                            } else {    //默认跳到添加页面
                                //请求接口，如果发现登录用户在查看商户的范围内，跳到相应的添加页面
                                if (vm.belongToRouteName === "frontend") {  //只有前端服务看板才有可能有添加操作
                                    isBelongToMerchant(function (yes) {
                                        if (yes) {
                                            util.jumpPage('#/' + vm.belongToRouteName + '/home/customerline/plan/add/' + moment(cellDate).hour(9) / 1);   //默认添加09：00这个时间端
                                        } else {
                                            util.alert({
                                                "content": "对不起，您无权限添加计划",
                                                "iconType": "error"
                                            });
                                        }
                                    });
                                }
                            }
                        } else {
                            if (targetEl.is('.plan-item')) {
                                if (vm.belongToRouteName === "frontend" || (vm.belongToRouteName === "sales" && planData.user_id == loginUserData.id)) {
                                    util.jumpPage('#/' + vm.belongToRouteName + '/home/customerline/plan/list/' + moment(cellDate) / 1 + '/' + targetEl.data('planId'));  //跳到单独的plan查看
                                }

                            }
                        }
                    }
                };
            });
            avalon.scan(pageEl[0]);




            /**
             * 判定当前登录用户是否在当前商户范围内,判定成功执行callback回调
             */
            function isBelongToMerchant(callback) {
                util.c2s({
                    "url": erp.BASE_PATH + "user/isExistSysUserMerchant.do",
                    "type": "post",
                    "contentType": 'application/json',
                    "data": JSON.stringify({
                        "merchantId": pageVm.merchantId,
                        "userId": loginUserData.id
                    }),
                    "success": function (responseData) {
                        if (responseData.flag) {
                            callback && callback(responseData.data);
                        }
                    }
                }, {
                    "mask": false
                });
            }

            /*页面初始化请求渲染*/
            function updateList(obj, url, callBack) {
                var requestData = JSON.stringify(obj);
                util.c2s({
                    "url": erp.BASE_PATH + url,
                    "type": "post",
                    "contentType": 'application/json',
                    "data": requestData,
                    "success": callBack
                });
            }

            /*页面初始化请求渲染end*/
            /*商户*/
            updateList({"merchantId": pageVm.merchantId}, "synMerchant/getMerchantInfoByOrder.do", merchantCallBack);
            function merchantCallBack(responseData) {
                var data;
                if (responseData.flag) {
                    data = responseData.data;
                    pageVm.merchantName = data.merchantInfo.merchant_name;
                    var obj = {};
                    obj.productList = data.productList;
                    obj.userMerchantList = data.userMerchantList;
                    obj.filePath = data.filePath;
                    var tmpObj = data.merchantInfo;
                    for (var k in data) {
                        var value = data.hasOwnProperty(k);
                        if (value) {
                            if (typeof(data[k]) != 'object') {
                                obj[k] = data[k];
                            }
                        }
                    }
                    for (var k in tmpObj) {
                        var value = tmpObj.hasOwnProperty(k);
                        if (value) {
                            obj[k] = tmpObj[k];
                        }
                    }
                    if (obj.attachment_name) {
                        obj.imgUrl = pageVm.path + obj.filePath + '/' + obj.attachment_path + '/' + obj.attachment_name;
                    } else {
                        obj.imgUrl = pageVm.path + 'portal/origin/asset/image/merchant-default-logo.jpg';
                    }
                    pageVm.merchantData = obj;
                    var userId = loginUserData.id;
                    for (var i = 0; i < obj.userMerchantList.length; i++) {
                        if (userId == obj.userMerchantList[i].userId) {
                            pageVm.promise = true;
                            return;
                        } else {
                            pageVm.promise = false;
                        }
                    }



                }
            }

            /*商户回调end*/
            /*健康度*/
            updateList(pageVm.healthRequestData.$model, "health/getHealthDegreeByMerchantId.do", healthCallBack);
            function healthCallBack(responseData) {
                var data;
                if (responseData.flag) {
                    data = responseData.data;
                    pageVm.healthResponseData = data;
                }
            }

            /*健康度end*/
            /*月报*/
            function reportCallBack(responseData) {
                var data;
                if (responseData.flag) {
                    data = responseData.data;
                    pageVm.reportResponseData = data.merchantMonthSum;
                }
            }

            /*月报end*/
            /*日报*/
            function reDayCallBack(responseData) {
                var data;
                if (responseData.flag) {
                    data = responseData.data;
                    pageVm.reDayResponseData = data;
                }
            }

            /*日报end*/
            /*提醒*/
            //updateList(pageVm.reportRequestData.$model,"activity/listNoneReadRemind.do",reportCallBack);
            function srcollUi() {
                var len = $('#warn>li').length;
                var sun = 0;
                setInterval(function () {
                    sun++;
                    if (sun >= len) {
                        sun = 0;
                    }
                    $('#warn').animate({top: 40 * -sun});
                }, 5000)

            }

            util.c2s({
                "url": erp.BASE_PATH + "activity/listNoneReadRemind.do",
                "type": "post",
                //"contentType" : 'application/json',
                "data": {merchantId: 1763},
                "success": function (responseData) {
                    if (responseData.flag) {
                        var data = responseData.data;
                        pageVm.warn = data;
                    }
                    srcollUi();

                }
            });
            /*提醒－end*/
            /*日月报*/
            pageVm.setMonthInfo();
            /*日月报-end*/
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


