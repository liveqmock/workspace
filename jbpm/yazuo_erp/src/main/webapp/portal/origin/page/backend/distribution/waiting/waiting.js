/**
 * 设备配送-待办事项
 */
define(['avalon', 'util','moment', 'json', '../../../../widget/dialog/dialog', '../../../../widget/form/select', '../../../../widget/form/form', '../../../../widget/calendar/calendar', '../../../../widget/uploader/uploader', '../../../../module/equipmentdistribution/equipmentdistribution', '../../../../module/addquestion/addquestion'], function (avalon, util) {
     var win = this,
        erp = win.erp,
        pageMod = {};
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName, routeData) {
            var getTypeId = function (n) {//设置和获取widget的id;
                if(!erp.arr){
                    erp.arr = [];
                }
                erp.arr.push(pageName + n);
                return pageName + n;
            };
            var loginUserData = erp.getAppData('user');
            var pageVm = avalon.define(pageName, function (vm) {
                    vm.navCrumbs = [{
                        "text":"设备配送",
                        "href":"#/backend/distribution"
                    }, {
                        "text":'test'
                    }];
                    vm.path=erp.BASE_PATH;
                    vm.pageUserId = erp.getAppData('user').id;//用户id
                    vm.merchantId = parseInt(routeData.params["id"]);
                    vm.merchantName = '';
                    vm.pageData = {};
                    vm.imgUrl ='';
                    /*merchant信息*/
                    var merchantModel = function(){
                        vm.merchantData = {//商户信息出参
                            "productList": [ ],
                            "filePath": "",
                            "merchant_id": '',
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
                            "attachment_path": "",
                            "imgUrl" : ''
                        };
                    };
                    merchantModel();
                    /*merchant信息-end*/
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
                    /*更改完成状态码*/
                    vm.stepEnd = function(id,status,str,stepNum){
                        var obj= {
                            id : id,
                            onlineProcessStatus : status
                        };
                        var requestData = JSON.stringify(obj);
                        util.confirm({
                            "content": str,
                            "onSubmit": function () {
                                util.c2s({
                                    "url": erp.BASE_PATH + "fesOnlineProcess/updateFesOnlineProcessStatus.do",
                                    "type": "post",
                                    "contentType" : 'application/json',
                                    "data": requestData,
                                    "success": function (responseData) {
                                        if(responseData.flag){
                                            for(var i=0;i<vm.pageData.length;i++){
                                                if(vm.pageData[i].stepNum == stepNum){
                                                    vm.pageData[i].listAttachmentAndOperateLog.push(responseData.data);
                                                    vm.pageData[i].onlineProcessStatus = responseData.data.onlineProcessStatus;
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                        });

                    };
                    /*更改完成状态吗-end*/

                    /*设备配送*/
                    vm.$equipmentdistributionOpts = {
                        "equipmentdistributionId": getTypeId('equipmentdistribution-'),
                        "merchantId" : vm.merchantId,
                        "processId" : 0,
                        "callFn" : function(responseData){
                            console.log(responseData)
                            if (responseData.flag == 1) {
                                for(var i=0;i<vm.pageData.length;i++){
                                    if(vm.pageData[i].stepNum == 'equipment_distribution'){
                                        console.log(vm.pageData[i])
                                        vm.pageData[i].listAttachmentAndOperateLog.push(responseData.data);
                                        vm.pageData[i].onlineProcessStatus = '04';
                                    }
                                }
                            }
                        }
                    };
                    vm.step8Distri = function(value){
                        avalon.getVm(getTypeId('equipmentdistribution-')).merchantId = vm.merchantId;
                        avalon.getVm(getTypeId('equipmentdistribution-')).processId = value;
                        avalon.getVm(getTypeId('equipmentdistribution-')).open();
                    };
                    /*设备配送-end*/
                });
                avalon.scan(pageEl[0]);
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
                /*页面初始化请求渲染-end*/
                /*商户回调*/
                updateList({"merchantId":pageVm.merchantId},"synMerchant/getMerchantInfoByOrder.do",merchantCallBack);
                function merchantCallBack(responseData){
                    var data;
                    if (responseData.flag) {
                        data = responseData.data;
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
                        pageVm.navCrumbs[1].text = obj.merchant_name;
                        pageVm.merchantName = obj.merchant_name;
                    }
                }
                /*商户回调end*/
                /*list*/
                updateList({"merchantId": pageVm.merchantId, "userId": loginUserData.id,"resourceRemark":"end_delivery_service"}, "fesOnlineProcess/listFesOnlineProcesss.do", listWaiting);
                function listWaiting(responseData) {
                    var data;
                    if (responseData.flag) {
                        data = responseData.data;
                        var arr = [];
                        for(var i=0;i<data.length;i++){
                            if(data[i].stepNum == 'equipment_distribution'){
                                arr.push(data[i]);
                            }
                        }
                        pageVm.pageData = arr;
                    }
                }
                /*list-end*/
            /*页面初始化请求渲染-end*/
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            for(var i=0;i<erp.arr.length;i++){
                var obj = avalon.getVm(erp.arr[i]);
                if(obj){
                    $(obj.widgetElement).remove();
                }
            }
            erp.arr.length = 0;
        }
    });


    return pageMod;
});

