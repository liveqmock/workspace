/**
 * 资料库-info
 */
define(['avalon', 'util', 'json', 'moment', '../../../../widget/pagination/pagination','../../../../widget/form/select'], function (avalon, util, JSON) {
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
                vm.navCrumbs = [{
                    "text":"资料库",
                    "href":"#/databank/list"
                }, {
                    "text":'资料详情'
                }];
                vm.path=erp.BASE_PATH;
                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.fileId = parseInt(routeData.params["id"]);
                vm.userImgPath = erp['appData']['user']['userImagePath'];
                vm.requestData = {//入参
                    "pageSize" : 1000,
                    "pageNumber":1,
                    "id":vm.fileId
                };
                vm.responseData = {};//出参




                vm.downFile = function(id){//保存下载记录
                    var url = pageVm.responseData.databaseFilePath+'/'+pageVm.responseData.sysAttachment.attachmentPath+'/'+pageVm.responseData.sysAttachment.attachmentName;
                    window.location=pageVm.path+"sysDatabase/download.do?relPath="+url+"&userId="+pageVm.pageUserId+"&attachmentId="+id;
                    setTimeout(function(){
                        updateList(pageVm.requestData.$model,"sysDatabase/querySysDatabaseById.do",listCallBackValue);
                    },500);

                    /*util.c2s({
                        "url": erp.BASE_PATH + 'sysDatabase/download.do',
                        "type": "post",
                        "contentType" : 'application/json',
                        "data": JSON.stringify({
                            "userId":pageVm.pageUserId,
                            "attachmentId":id,
                            "relPath":url
                        }),
                        "success": function(responseData){
                            if(responseData.flag){
                                window.location=pageVm.path+url;
                                updateList(pageVm.requestData.$model,"sysDatabase/querySysDatabaseById.do",listCallBackValue);
                            }
                        }
                    });*/
                };




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
            updateList(pageVm.requestData.$model,"sysDatabase/querySysDatabaseById.do",listCallBackValue);

            function listCallBackValue(responseData){
                var data;
                if (responseData.flag) {
                    data=responseData.data;
                    var rows=data.sysAttachmentUserList.rows;
                    for(var i=0;i<rows.length;i++){
                        rows[i].insert_time = moment(rows[i].insert_time).format('YYYY-MM-DD');
                    }
                    pageVm.responseData = data;
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


