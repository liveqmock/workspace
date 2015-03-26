/**
 * 资料库-info
 */
define(['avalon', 'util', 'json', 'moment', '../../../../widget/pagination/pagination','../../../../widget/form/select', '../../../../widget/grid/grid'], function (avalon, util, JSON,moment) {
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
                    "text":"产品运营",
                    "href":"#/productoperad/list"
                }, {
                    "text":'资料详情'
                }];
                vm.path=erp.BASE_PATH;
                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.fileId = parseInt(routeData.params["id"]);


                /*grid-start*/
                vm.keyword = "";
                vm.$gridOpts = {
                    "gridId": getTypeId('-grid'),
                    "disableCheckAll": true,  //是否禁用全选
                    "disableCheckbox": true, //是否禁用选择框，默认不禁用
                    "getTemplate": function (tmpl) {
                        var tmpHade = tmpl.split('MS_OPTION_TBAR');
                        return '<div class="ui-grid-tbar fn-clear">' +
                            '<div class="fn-left">' +
                            '共<span class="num">{{gridTotalSize}}</span>个需求商' +
                            '</div>'+
                            '<div class="ui-grid-search fn-right">' +
                            '<input type="text" class="input-text" placeholder="商户名" ms-duplex="keyword">' +
                            '<button type="button" ' +
                            'class="query-btn main-btn" ' +
                            'ms-click="search"' +
                            '>搜索</button>' +
                            '</div>' +
                            '</div>MS_OPTION_TBAR' + tmpHade[1];
                    },
                    "columns": [
                        {
                            "dataIndex": "insertTime",
                            "text": "日期",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return moment(rowData["insertTime"]).format('YYYY-MM-DD');
                            }
                        },
                        {
                            "dataIndex": "merchantName",
                            "text": "商户名"
                        },
                        {
                            "dataIndex": "storeName",
                            "text": "门店名称"
                        },
                        {
                            "dataIndex": "userName",
                            "text": "姓名"
                        },
                        {
                            "dataIndex": "tel",
                            "text": "手机号"
                        },
                        {
                            "dataIndex": "position",
                            "text": "职位"
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        requestData = _.extend(requestData, {
                            "merchantName": _.str.trim(vm.keyword),  // 搜索关键字
                            "productOperationId":vm.fileId
                        });
                        util.c2s({
                            "url": erp.BASE_PATH + "sysUserRequirement/listSysUserRequirements.do",
                            "contentType": 'application/json',
                            "data": JSON.stringify(requestData),
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    callback.call(this, data);
                                }
                            }
                        });
                    },
                    "search": function(){
                        console.log(vm.keyword);
                        avalon.getVm(getTypeId('-grid')).load();
                    },
                    "jump": function(){
                        var id=$(this).attr("data-id");
                        util.jumpPage('#/productoperad/list/info/'+id);
                    }
                };
                /*grid-end*/
            });
            avalon.scan(pageEl[0]);
            avalon.getVm(getTypeId('-grid')).load();
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


