/**
 * 业务分配
 */
define(['avalon', 'util', 'json', 'moment', '../../../widget/pagination/pagination', '../../../widget/form/select', '../../../widget/grid/grid'], function (avalon, util, JSON, moment) {
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

                };
                vm.pageUserId = erp.getAppData('user').id;//用户id



                vm.search = function (){
                    avalon.getVm(getTypeId('-grid')).load();
                };


                /*grid-start*/
                vm.keyword = "";
                vm.$gridOpts = {
                    "gridId": getTypeId('-grid'),
                    "disableCheckAll": false,  //是否禁用全选
                    "disableCheckbox": false, //是否禁用选择框，默认不禁用
                    "getTemplate": function (tmpl) {
                        var tmpHade = tmpl.split('MS_OPTION_TBAR');
                        var tmpMain = tmpHade[1].split('MS_OPTION_MAIN')[0];
                        var newFooter = '<div class="ui-grid-bbar fn-clear">' +
                            '<div class="grid-action fn-left">' +
                            '<button type="button" class="check-all-btn main-btn" ms-click="scCheckAll">全选</button>&nbsp;' +
                            '<button type="button" class="batch-remove-btn main-btn" ms-click="assign">批量修改</button>' +
                            '</div>' +
                            '<div class="pagination fn-right" ms-widget="pagination,$,$paginationOpts"></div>' +
                            '</div>';
                        return '<div class="ui-grid-search">' +
                            '<input type="text" class="input-text search" ms-duplex="keyword" placeholder="姓名">&nbsp;&nbsp;' +
                            '<input type="text" class="input-text search" ms-duplex="keyword" placeholder="职位">&nbsp;&nbsp;' +
                            '<button type="button" class="query-btn main-btn" ms-click="search">搜索</button>&nbsp;&nbsp;' +
                            '</div>MS_OPTION_TBAR' + tmpMain + newFooter;
                    },
                    "columns": [
                        {
                            "dataIndex": "merchant_name",
                            "text": "姓名",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<div style="text-align: left">{{$outer.el.merchant_name}}</div>'
                            }
                        },
                        {
                            "dataIndex": "front_user_name",
                            "text": "职位"
                        },
                        {
                            "dataIndex": "open_number",
                            "text": "相关业务",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<span ms-visible="$outer.el.open_number == 0">未填写</span><span class="end" ms-visible="$outer.el.open_number == 1">已填写</span>'
                            }
                        },
                        {
                            "dataIndex": "job_overview",
                            "text": "操作",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<a href="#">修改</a>'
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        requestData = _.extend(requestData, {
                            "merchantName": _.str.trim(pageVm.keyword)

                        });
                        util.c2s({
                            "url": erp.BASE_PATH + "besMonthlyCheck/queryBesMonthlyCheckList.do",
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
                    }
                };
                /*grid-end*/
            });
            avalon.scan(pageEl[0]);
            avalon.getVm(getTypeId('-grid')).load();
            /*页面初始化请求渲染*/
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


