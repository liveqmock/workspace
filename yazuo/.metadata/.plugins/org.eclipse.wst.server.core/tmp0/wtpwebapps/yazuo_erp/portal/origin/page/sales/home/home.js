/**
 * 销售服务-我的主页
 */
define(['avalon', 'util', 'json', '../../../widget/pagination/pagination','../../../widget/form/select'], function (avalon, util, JSON) {
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
            var pageVm = avalon.define(pageName, function (vm) {
                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.pageStatus= 1;//大标签切换依据
                vm.merchantListData = [];//商户信息
                vm.backlogListData = [];//代办事项信息
                vm.dotolen = 0;//代办事项个数
                vm.backlogRequestData = {//待办事项入参
                    "userId": vm.pageUserId,
                    "inputItemTypes":["03"],
                    "merchantName":"",
                    "pageNumber":1,
                    "pageSize":10,
                    "businessTypes":['1']
                };
                vm.merchantRequestData= {//我的商户入参
                    "merchantStatus" : "",
                    "userId" : vm.pageUserId,
                    "pageSize" : 10,
                    "pageNumber" : 1,
                    "merchantName" : "",
                    "merchantStatusType" : 1
                };
                vm.localHref = function(url){//页面跳转
                    util.jumpPage(url);
                };
                vm.permission = {//权限
                    "sales_remaind" : util.hasPermission('sales_remaind'),//提醒
                    "sales_ques_mag" : util.hasPermission('sales_ques_mag'),//问题管理
                    "sales_address" : util.hasPermission('sales_address'),//通讯录
                    "sales_my_merchant" : util.hasPermission('sales_my_merchant'),//我的商户
                    "sales_todolist" : util.hasPermission('sales_todolist')//待办事项
                };
                vm.changePageStatus= function(value){//大标签切换
                    vm.pageStatus= value;
                    var paginationVm = avalon.getVm(getTypeId("paginationPage"));
                    paginationVm.currentPage = 1;
                    if(value == 1){
                        pageVm.merchantRequestData.merchantName= '';
                        updateList(pageVm.merchantRequestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);
                    }else if(value == 2){
                        pageVm.backlogRequestData.merchantName= '';
                        updateList(pageVm.backlogRequestData.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);
                    }
                };


                //(我的商户&全部商户)页面跳转时附加回退时候信息
                vm.callBackInfo = '';
                vm.localHrefInfo = function(){//(我的商户&全部商户)页面跳转时附加回退时候信息
                    var href = $(this).attr('data-href');
                    var merchantName = $(this).attr('data-name');
                    var text1,text2,text3,href1,href2;
                    href1 = "#/sales/home/"+encodeURIComponent(JSON.stringify({'pageStatus':vm.pageStatus}));
                    href2 = "#/sales/home/"+encodeURIComponent(JSON.stringify({'pageStatus':vm.pageStatus,merchantStatus:vm.merchantRequestData.merchantStatus}));
                    text1 = '我的商户';
                    if(vm.merchantRequestData.merchantStatus == ''){
                        text2 = '全部';
                    }else if(vm.merchantRequestData.merchantStatus == 0){
                        text2 = '未上线商户';
                    }else if(vm.merchantRequestData.merchantStatus == 1){
                        text2 = '已上线商户';
                    }else if(vm.merchantRequestData.merchantStatus == 2){
                        text2 = '正常商户';
                    }else if(vm.merchantRequestData.merchantStatus == 3){
                        text2 = '问题商户';
                    }else if(vm.merchantRequestData.merchantStatus == 4){
                        text2 = '危险商户';
                    }
                    text3 = merchantName;
                    var info = [
                        {
                            "text":'我的主页',
                            "href":'#/sales/home'
                        },
                        {
                            "text":text1,
                            "href":href1
                        },
                        {
                            "text":text2,
                            "href":href2
                        },
                        {
                            "text":text3
                        }
                    ];
                    var url = href+'/'+encodeURIComponent(JSON.stringify(info));
                    util.jumpPage(url);
                };
                //页面跳转返回（根据反正值请求相应的接口）
                vm.callBackInfo = routeData.params["info"];
                vm.callBackInfoFn = function(){
                    var data = JSON.parse(decodeURIComponent(vm.callBackInfo));
                    pageVm.pageStatus = data.pageStatus;
                    pageVm.merchantRequestData.merchantStatus = data.merchantStatus;
                    if(data.merchantStatus){
                        vm.merchantRequestData.merchantStatus = data.merchantStatus;
                    }else{
                        vm.merchantRequestData.merchantStatus = '';
                    }
                    updateList(pageVm.merchantRequestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);
                };




                /**
                 * 我的商户
                 */
                vm.changeStatus= function(value){//小标签切换
                    vm.merchantRequestData.merchantStatus= value;
                    var paginationVm = avalon.getVm(getTypeId("paginationPage"));
                    pageVm.merchantRequestData.pageNumber =1;
                    paginationVm.currentPage = 1;
                    updateList(pageVm.merchantRequestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);
                };
                vm.merchantSearch=function(){//商户搜索
                    updateList(pageVm.merchantRequestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);
                };
                vm.showProgressTip = function () {//步骤说明显示
                    var itemM = this.$vmodel.$model,
                        tip = itemM.el.stepText,
                        tipId = pageName + '-progress-tip';
                    var meEl = $(this),
                        tipEl = $('#' + tipId),
                        meOffset = meEl.offset();
                    if (!tipEl.length) {
                        tipEl = $('<div id="' + tipId + '" class="grid-tip"></div>');
                        tipEl.html('<span class="text-content"></span><span class="icon-arrow"></span>');
                        tipEl.hide().appendTo('body');
                    }
                    $('.text-content', tipEl).text(tip);
                    tipEl.css({
                        "top": meOffset.top - tipEl.height() - 20,
                        "left": meOffset.left - tipEl.width() / 2 - 11
                    }).show();
                };
                vm.hideProgressTip = function () {//步骤说明隐藏
                    $('#' + pageName + '-progress-tip').hide();
                };
                /**
                 * 我的商户-end
                 */
                /**
                 * 代办事项
                 */
                vm.backlogSearch = function(){
                    updateList(pageVm.backlogRequestData.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);
                };
                vm.doToStatus = function(value){
                    var paginationVm = avalon.getVm(getTypeId("paginationPage"));
                    paginationVm.currentPage = 1;
                    pageVm.backlogRequestData.pageNumber = 1;
                    pageVm.backlogRequestData.inputItemTypes = value;
                    updateList(pageVm.backlogRequestData.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);
                };
                /**
                 * 代办事项 -end
                 */
                //分页
                vm.$paginationOpts = {
                    "paginationId": getTypeId("paginationPage"),
                    "total": 0,
                    "onJump": function (evt, vm) {
                        var paginationVm = avalon.getVm(getTypeId("paginationPage"));
                        if(pageVm.pageStatus == 1){
                            pageVm.merchantRequestData.pageNumber = paginationVm.currentPage;
                            updateList(pageVm.merchantRequestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);
                        }else if(pageVm.pageStatus == 2){
                            pageVm.backlogRequestData.pageNumber = paginationVm.currentPage;
                            updateList(pageVm.backlogRequestData.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBack);
                        }
                    }
                };
                //分页end
            });
            avalon.scan(pageEl[0]);
            if(pageVm.callBackInfo){
                pageVm.callBackInfoFn();
            }else{
                updateList(pageVm.merchantRequestData.$model,"myHomePage/getComplexSynMerchants.do",merchantCallBack);
            }

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
            //updateList(pageVm.backlogRequestData.$model,"myHomePage/getComplexSysToDoLists.do",backlogCallBackValue);//获取代办事项个数
            /*我的商户回调*/
            function merchantCallBack(responseData){
                var data,
                    paginationVm = avalon.getVm(getTypeId("paginationPage"));
                if (responseData.flag) {
                    pageVm.merchantListData = [];
                    data = responseData.data;
                    //流程进度排序
                    function compare(a,b){
                        if(a.stepId > b.stepId){
                            return 1;
                        }else{
         	        	   return -1;
         	           } 
                    }
                    for(var i= 0,len= data.rows.length;i<len;i++){
                        data.rows[i].listSteps.sort(compare);
                    }
                    //流程进度排序结束
                    //拼健康度
                    for(var i= 0,len= data.rows.length;i<len;i++){
                        var healthType=[{index:'1',health:0},{index:'2',health:0},{index:'3',health:0},{index:'4',health:0}];
                        var arr= data.rows[i].synHealthDegrees;
                        for(var j= 0,lenArr= arr.length;j<lenArr;j++){
                            var obj= {};
                            obj.index = arr[j].targetType;
                            if(arr[j].healthDegree<100){
                                obj.health = 0;
                            }else{
                                obj.health = 1;
                            }
                            for(var m= 0,lenArrs= healthType.length;m<lenArrs;m++){
                                if(healthType[m].index == obj.index){
                                    healthType[m] = obj;
                                }
                            }
                        }
                        data.rows[i].healthType = healthType;
                    }
                    //拼健康度结束
                    //数据过滤成自己要的格式
                    var arr= data.rows;
                    for(var i=0;i<arr.length;i++){
                        var tmpData = {};
                        for(var k in arr[i]){
                            var attr = arr[i].hasOwnProperty(k);
                            if(attr){
                                if(k == 'merchantId'){//id
                                    tmpData.merchantId = arr[i][k];
                                }
                                if(k == 'merchantName'){//name
                                    tmpData.merchantName = arr[i][k];
                                }
                                if(k == 'merchantStatus'){//状态
                                    tmpData.merchantStatus = arr[i][k];
                                }
                                if(k == 'salesName'){//销售负责人
                                    var tmpArr = [];
                                    if(arr[i][k] instanceof Array){
                                        tmpArr = arr[i][k];
                                    }else{
                                        var salesObj = {};
                                        salesObj.id = 0;
                                        salesObj.tel = 0;
                                        salesObj.userName = arr[i][k];
                                        tmpArr.push(salesObj);
                                    }
                                    tmpData.salesName = tmpArr;
                                }
                                if(k == 'listUsers'){//前端负责人
                                    var tmpArr = [];
                                    if(arr[i][k] instanceof Array){
                                        tmpArr = arr[i][k];
                                    }else{
                                        var salesObj = {};
                                        salesObj.id = 0;
                                        salesObj.tel = 0;
                                        salesObj.userName = arr[i][k];
                                        tmpArr.push(salesObj);
                                    }
                                    tmpData.listUsers = tmpArr;
                                }
                                if(k == 'merchantStatusText'){//状态
                                    tmpData.merchantStatusText = arr[i][k];
                                }
                                if(k == 'listSteps'){//流程
                                    tmpData.listSteps = arr[i][k];
                                }
                                if(k == 'healthType'){//健康度
                                    tmpData.healthType = arr[i][k];
                                }
                            }
                        }
                        pageVm.merchantListData.push(tmpData);
                    }
                    //数据过滤成自己要的格式end
                    paginationVm.total = data.totalSize;
                    //pageVm.merchantRequestData.merchantName = '';
                }
            }
            /*我的商户，全部商户回调end*/

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


