/**
 * 月报检查
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

                vm.$yearSelectOpts = {
                    "selectId": getTypeId('-year'),
                    "options": [
                        {
                            value : '2014',
                            text : '2014年'
                        },
                        {
                            value : '2015',
                            text : '2015年'
                        }
                    ]
                };//年

                vm.$monthSelectOpts = {
                    "selectId": getTypeId('-month'),
                    "options": [
                        {
                            value : '01',
                            text : '1月月报'
                        },
                        {
                            value : '02',
                            text : '2月月报'
                        },
                        {
                            value : '03',
                            text : '3月月报'
                        },
                        {
                            value : '04',
                            text : '4月月报'
                        },
                        {
                            value : '05',
                            text : '5月月报'
                        },
                        {
                            value : '06',
                            text : '6月月报'
                        },
                        {
                            value : '07',
                            text : '7月月报'
                        },
                        {
                            value : '08',
                            text : '8月月报'
                        },
                        {
                            value : '09',
                            text : '9月月报'
                        },
                        {
                            value : '10',
                            text : '10月月报'
                        },
                        {
                            value : '11',
                            text : '11月月报'
                        },
                        {
                            value : '12',
                            text : '12月月报'
                        }
                    ]
                };//月


                vm.$fdSelectOpts = {
                    "selectId": getTypeId('-fd'),
                    "options": [
                        {
                            value : 0,
                            text : '全部前端'
                        }
                    ]
                };//前端
                vm.$checkSelectOpts = {
                    "selectId": getTypeId('-check'),

                    "options": [
                        {
                            value : '',
                            text : '全部商户'
                        },
                        {
                            value : '1',
                            text : '明天要讲解月报'
                        }
                    ],
                    "selectedIndex": 1
                };//是否检查
                vm.$statusSelectOpts = {
                    "selectId": getTypeId('-status'),
                    "options": [
                        {
                            value : '',
                            text : '全部状态'
                        }
                    ]
                };//状态
                vm.keyword = '';
                vm.search = function (){
                    avalon.getVm(getTypeId('-grid')).load();
                };
                util.c2s({//完成状态
                    "url": erp.BASE_PATH + "dictionary/querySysDictionaryByType.do",
                    "type": "post",
                    "data":  'dictionaryType=00000101',
                    "success": function (responseData) {
                        var data;
                        var select = avalon.getVm(getTypeId("-status"));
                        if (responseData.flag) {
                            data = responseData.data;
                            var val =_.map(data, function (itemData) {
                                return {
                                    "text": itemData["dictionary_value"],
                                    "value": itemData["dictionary_key"]
                                };
                            });
                            val.unshift({value :'',text : '全部状态'});
                            select.setOptions(val);
                        }
                    }
                });
                util.c2s({//前端负责人
                    "url": erp.BASE_PATH + "user/getAllUsersByResourceCode.do",
                    "contentType": 'application/json',
                    "type": "post",
                    "data": JSON.stringify({"remark":"fes_my_main_page"}),
                    "success": function (responseData) {
                        var data;
                        var select = avalon.getVm(getTypeId("-fd"));
                        if (responseData.flag) {
                            data = responseData.data;
                            var val =_.map(data, function (itemData) {
                                return {
                                    "text": itemData["userName"],
                                    "value": itemData["id"]
                                };
                            });
                            val.unshift({value : 0,text : '全部前端'});
                            select.setOptions(val);
                        }
                    }
                });

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
                            '共<span class="num">{{gridTotalSize}}</span>个商户' +
                            '</div>' +
                            '</div>MS_OPTION_TBAR' + tmpHade[1];
                    },
                    "columns": [
                        {
                            "dataIndex": "merchant_name",
                            "text": "商户",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<div style="text-align: left">{{$outer.el.merchant_name}}</div>'
                            }
                        },
                        {
                            "dataIndex": "front_user_name",
                            "text": "前端"
                        },
                        {
                            "dataIndex": "open_number",
                            "text": "开台数",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<span ms-visible="$outer.el.open_number == 0">未填写</span><span class="end" ms-visible="$outer.el.open_number == 1">已填写</span>'
                            }
                        },
                        {
                            "dataIndex": "job_overview",
                            "text": "工作概述",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<span ms-visible="$outer.el.job_overview == 0">未填写</span><span class="end" ms-visible="$outer.el.job_overview == 1">已填写</span>'
                            }
                        },
                        {
                            "dataIndex": "effect_and_suggestion",
                            "text": "效果及建议",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<span ms-visible="$outer.el.effect_and_suggestion == 0">未填写</span><span class="end" ms-visible="$outer.el.effect_and_suggestion == 1">已填写</span>'
                            }
                        },
                        {
                            "dataIndex": "membership_development",
                            "text": "会员发展",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<span ms-visible="$outer.el.membership_development == 0">未填写</span><span class="end" ms-visible="$outer.el.membership_development == 1">已填写</span>'
                            }
                        },
                        {
                            "dataIndex": "member_consumption",
                            "text": "会员消费",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<span ms-visible="$outer.el.member_consumption == 0">未填写</span><span class="end" ms-visible="$outer.el.member_consumption == 1">已填写</span>'
                            }
                        },
                        {
                            "dataIndex": "membership_value",
                            "text": "会员储值",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<span ms-visible="$outer.el.membership_value == 0">未填写</span><span class="end" ms-visible="$outer.el.membership_value == 1">已填写</span>'
                            }
                        },
                        {
                            "dataIndex": "affiliate_marketing",
                            "text": "会员营销",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<span ms-visible="$outer.el.affiliate_marketing == 0">未填写</span><span class="end" ms-visible="$outer.el.affiliate_marketing == 1">已填写</span>'
                            }
                        },
                        {
                            "dataIndex": "population_quality",
                            "text": "群体质量",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<span ms-visible="$outer.el.population_quality == 0">未填写</span><span class="end" ms-visible="$outer.el.population_quality == 1">已填写</span>'
                            }
                        },
                        {
                            "dataIndex": "monthly_check_state",
                            "text": "状态",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<span class="warn" ms-visible="$outer.el.monthly_check_state == 0">未完成</span><span class="through" ms-visible="$outer.el.monthly_check_state == 1">已完成</span>'
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        requestData = _.extend(requestData, {
                            "reportTime" : moment(avalon.getVm(getTypeId('-year')).selectedValue+'-'+avalon.getVm(getTypeId('-month')).selectedValue)/1,
                            "merchantName": _.str.trim(pageVm.keyword),
                            "frontUserId" : avalon.getVm(getTypeId('-fd')).selectedValue,
                            "monthlyCheckState" : avalon.getVm(getTypeId('-status')).selectedValue,
                            "isCheck" : avalon.getVm(getTypeId('-check')).selectedValue
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
            var date = new Date();
            var currentYear = date.getFullYear();
            var currentMonth = date.getMonth();
            if(currentMonth == 0){
                currentMonth = 12;
                currentYear -= 1
            }
            avalon.getVm(getTypeId('-year')).selectValue(currentYear);
            avalon.getVm(getTypeId('-month')).selectValue(currentMonth);
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


