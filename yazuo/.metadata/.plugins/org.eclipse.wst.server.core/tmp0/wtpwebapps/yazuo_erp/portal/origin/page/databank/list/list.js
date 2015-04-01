/**
 * 资料库-list
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
        "$init": function (pageEl, pageName) {
            var getTypeId = function (n) {//设置和获取widget的id;
                var widgetId = pageName + n;
                tempWidgetIdStore.push(widgetId);
                return widgetId;
            };
            var pageVm = avalon.define(pageName, function (vm) {
                vm.permission = {//权限
                    "data_upload" : util.hasPermission("data_upload"),//上传
                    "data_update" : util.hasPermission("data_update"),//修改
                    "data_delete": util.hasPermission("data_delete"),//删除
                    "data_download" : util.hasPermission("data_download"),//下载
                    "data_review" : util.hasPermission("data_review")//查看
                };
                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.path=erp.BASE_PATH;
                vm.localHref = function(url){//页面跳转
                    util.jumpPage(url);
                };
                vm.responseData = {//入参
                    "pageSize" : 10,//每页必输
                    "pageNumber":1,//查询页数
                    "attachmentSuffix":"",//后缀
                    "applyCrowdType":"",//适用人群
                    "title":"",//标题
                    "dataType":""
                };
                vm.sum = 0;
                vm.responseDataList = [];//出参
                vm.$personTypeOpts = {//全部人群
                    "selectId": getTypeId("personTypeOpts"),
                    "options": [],
                    "selectedIndex": 0
                };
                vm.$layoutTypeOpts = {//全部格式
                    "selectId": getTypeId("layoutTypeOpts"),
                    "options": [
                        {text:'全部格式',value:''},
                        {text:'doc',value:'doc'},
                        {text:'docx',value:'docx'},
                        {text:'xls',value:'xls'},
                        {text:'xlsx',value:'xlsx'},
                        {text:'ppt',value:'ppt'},
                        {text:'pptx',value:'pptx'},
                        {text:'pdf',value:'pdf'},
                        {text:'rar',value:'rar'},
                        {text:'zip',value:'zip'}
                    ],
                    "selectedIndex": 0
                };
                vm.$dataTypeOpts = {//资料类型
                    "selectId": getTypeId("dataTypeOpts"),
                    "options": [],
                    "selectedIndex": 0
                };

                util.c2s({//获取适用人群
                    "url": erp.BASE_PATH + "dictionary/querySysDictionaryByType.do",
                    "type": "post",
                    "data":  'dictionaryType=00000094',
                    "success": function (responseData) {
                        var data;
                        var select = avalon.getVm(getTypeId("personTypeOpts"));
                        if (responseData.flag) {
                            data = responseData.data;
                            var val =_.map(data, function (itemData) {
                                return {
                                    "text": itemData["dictionary_value"],
                                    "value": itemData["dictionary_key"]
                                };
                            });
                            val.unshift({text:'全部人群',value:''});
                            select.setOptions(val);
                        }
                    }
                });
                util.c2s({//资料类型
                    "url": erp.BASE_PATH + "dictionary/querySysDictionaryByType.do",
                    "type": "post",
                    "data":  'dictionaryType=00000095',
                    "success": function (responseData) {
                        var data;
                        var select = avalon.getVm(getTypeId("dataTypeOpts"));
                        if (responseData.flag) {
                            data = responseData.data;
                            var val =_.map(data, function (itemData) {
                                return {
                                    "text": itemData["dictionary_value"],
                                    "value": itemData["dictionary_key"]
                                };
                            });
                            val.unshift({text:'全部类型',value:''});
                            select.setOptions(val);
                        }
                    }
                });
                vm.search = function(){//搜索
                    var personType = avalon.getVm(getTypeId("personTypeOpts")).selectedValue;
                    var layoutType = avalon.getVm(getTypeId("layoutTypeOpts")).selectedValue;
                    var dataType = avalon.getVm(getTypeId("dataTypeOpts")).selectedValue;
                    pageVm.responseData.attachmentSuffix= layoutType;
                    pageVm.responseData.applyCrowdType= personType;
                    pageVm.responseData.dataType= dataType;
                    var paginationVm = avalon.getVm(getTypeId("paginationPage"));
                    pageVm.responseData.pageNumber =1;
                    paginationVm.currentPage = 1;
                    updateList(pageVm.responseData.$model,"sysDatabase/querySysDatabase.do",listCallBackValue);

                };
                vm.removeFile = function(id){//删除附件
                    util.confirm({
                        "content": "你确定要删除吗？",
                        "onSubmit": function () {
                            util.c2s({
                                "url": erp.BASE_PATH + "sysDatabase/deleteSysDatabase.do",
                                "type": "post",
                                "contentType" : 'application/json',
                                "data":  JSON.stringify({id:id}),
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        util.alert({
                                            "content": "已删除",
                                            "iconType": "success"
                                        });
                                        updateList(pageVm.responseData.$model,"sysDatabase/querySysDatabase.do",listCallBackValue);
                                    }
                                }
                            });
                        }
                    });
                };
                vm.toInfo = function(id){
                    var url = "#/databank/list/info/"+id;
                    if(pageVm.permission.data_review){
                        util.jumpPage(url);
                    }else{
                        util.alert({
                            "content": "您没有查看详情的权限！",
                            "iconType": "info"
                        });
                    }

                };





                vm.downFile = function(id){//保存下载记录
                    var cons = this.$vmodel.$model.el;
                    var url = cons.databaseFilePath+'/'+cons.attachment_path+'/'+cons.attachment_name;
                    window.location=pageVm.path+"sysDatabase/download.do?relPath="+url+"&userId="+pageVm.pageUserId+"&attachmentId="+id;

                    /*util.c2s({
                        "url": erp.BASE_PATH + 'sysDatabase/download.do',
                        "type": "get",
                        "contentType" : 'application/json',
                        "data": {
                            "relPath":url,
                            "userId":pageVm.pageUserId,
                            "attachmentId":id
                        },
                        "success": function(responseData){
                            if(responseData.flag){
                                window.location=pageVm.path+url;
                            }
                        },
                        "complete": function () {
                            var fff = 1;
                            console.info(arguments[0].getAllResponseHeaders());
                        }
                    });*/
                };




                //分页
                vm.$paginationOpts = {
                    "paginationId": getTypeId("paginationPage"),
                    "total": 0,
                    "onJump": function (evt, vm) {
                        var paginationVm = avalon.getVm(getTypeId("paginationPage"));
                        pageVm.responseData.pageNumber = paginationVm.currentPage;
                        updateList(pageVm.responseData.$model,"sysDatabase/querySysDatabase.do",listCallBackValue);
                    }
                };
                //分页end
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
            updateList(pageVm.responseData.$model,"sysDatabase/querySysDatabase.do",listCallBackValue);

            function listCallBackValue(responseData){
                var data,
                    paginationVm = avalon.getVm(getTypeId("paginationPage"));
                if (responseData.flag) {
                    data = responseData.data;
                    pageVm.sum = data.totalSize;
                    pageVm.responseDataList = data.rows;
                    paginationVm.total = data.totalSize;
                }
            }

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


