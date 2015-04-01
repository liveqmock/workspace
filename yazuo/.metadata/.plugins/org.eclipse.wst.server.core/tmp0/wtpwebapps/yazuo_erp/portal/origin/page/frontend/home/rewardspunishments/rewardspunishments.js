/**
 * Created by ZHANG on 2014/8/7.
 */
/**
 * 前端服务-绩效奖惩
 */
define(['avalon', 'util', '../../../../widget/pagination/pagination'], function (avalon, util) {
    var win = this,
        erp = win.erp,
        pageMod = {};
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName) {
            var paginationId = pageName + '-pagination';//分页

            var pageVm = avalon.define(pageName, function (vm) {
                vm.navCrumbs = [{
                    "text":"我的主页",
                    "href":"#/frontend/home"
                }, {
                    "text":"我的绩效"
                }];
                vm.url=erp.BASE_PATH;
                vm.pageData={};
                vm.statusTabs='';//标签切换依赖
                vm.requestData={
                    merchantName:'',
                    pageNumber:1,
                    pageSize:10,
                    itemStatus:''
                };
                vm.changeStatus = function(statusId){//切换标签
                    pageVm.requestData.itemStatus=statusId;
                    updateList(pageVm.requestData.$model);
                };
                vm.search = function(){//搜索
                    updateList(pageVm.requestData.$model);
                };
                vm.modStatus = function(id){//修改状态
                    id=id.toString();
                    util.c2s({
                        "url": erp.BASE_PATH + "activity/updateRemind.do",
                        "type": "post",
                        //"contentType" : 'application/json',
                        "data": {id:id},
                        "success": function (responseData) {
                            updateList(pageVm.requestData.$model);
                        }
                    });


                };







            });



            avalon.scan(pageEl[0]);
            /*页面初始化请求渲染*/
            updateList(pageVm.requestData.$model);
            function updateList(obj){
                var requestData=JSON.stringify(obj);
                util.c2s({
                    "url": erp.BASE_PATH + "activity/listRemind.do",
                    "type": "post",
                    "contentType" : 'application/json',
                    "data": requestData,
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            pageVm.pageData=data.rows;
                        }
                    }
                });

            }
            /*页面初始化请求渲染end*/
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var paginationId = pageName + '-pagination';//分页
            avalon.getVm(paginationId)&&$(avalon.getVm(paginationId).widgetElement).remove();
        }
    });

    return pageMod;
});
