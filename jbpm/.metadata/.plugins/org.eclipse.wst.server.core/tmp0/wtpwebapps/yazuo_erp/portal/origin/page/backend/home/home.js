/**
 * 后端服务-我的主页
 */
define(['avalon', 'util', 'json', '../../../widget/pagination/pagination', '../../../widget/form/select', '../../../widget/grid/grid', '../../../module/persontree/persontree'], function (avalon, util, JSON) {
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
                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.pageStatus = 1;//标签切换依据


                vm.localHref = function (url) {//页面跳转
                    util.jumpPage(url);
                };
                vm.permission = {//权限
                    unassigned_137 : util.hasPermission("unassigned_137"),//未指派
                    incomplete_138 : util.hasPermission("incomplete_138"),//未完成
                    complete_139 : util.hasPermission("complete_139"),//已完成
                    assign_141 : util.hasPermission("assign_141"),//指派
                    batch_assign_142 : util.hasPermission("batch_assign_142"),//批量指派
                    create_reqs_140 : util.hasPermission("create_reqs_140")//创建需求
                    //create_reqs_140 : util.hasPermission("create_reqs_140")//进入月报讲解
                };
                vm.changePageStatus = function (value) {//大标签切换
                    vm.pageStatus = value;
                    if (value == 1) {
                        avalon.getVm(getTypeId('-grid1')).load();
                    } else if (value == 2) {
                        avalon.getVm(getTypeId('-grid2')).load();
                    } else if (value == 3) {
                        avalon.getVm(getTypeId('-grid3')).load();
                    }
                };
                vm.$grid1Opts = {
                    "gridId": getTypeId('-grid1'),
                    "disableCheckAll": (vm['permission']['batch_assign_142'])?false:true,  //是否禁用全选
                    "disableCheckbox": (vm['permission']['batch_assign_142'])?false:true, //是否禁用选择框，默认不禁用
                    "getTemplate": function (tmpl) {
                        var tmpHade = tmpl.split('MS_OPTION_TBAR');
                        var tmpMain = tmpHade[1].split('MS_OPTION_MAIN')[0];
                        var newFooter = '<div class="ui-grid-bbar fn-clear">' +
                            '<div class="grid-action fn-left" ms-if="permission.batch_assign_142">' +
                            '<button type="button" class="check-all-btn main-btn" ms-click="scCheckAll">全选</button>&nbsp;' +
                            '<button type="button" class="batch-remove-btn main-btn" ms-click="assign">批量指派</button>' +
                            '</div>' +
                            '<div class="pagination fn-right" ms-widget="pagination,$,$paginationOpts"></div>' +
                            '</div>';
                        return '<div class="ui-grid-search">' +
                            '<span ms-widget="select,$,$year1Opts"></span>' +
                            '<span ms-widget="select,$,$month1Opts"></span>' +
                            '<input style="width: 200px;" type="text" class="input-text search" ms-duplex="keyword" placeholder="需求标题">' +
                            '<button type="button" class="query-btn main-btn" ms-click="search">搜索</button>' +
                            '</div>MS_OPTION_TBAR' + tmpMain + newFooter;
                    },
                    "columns": [
                        {
                            "dataIndex": "insertTime",
                            "text": "日期",
                            "renderer" : function(v, rowData){
                                return moment(rowData["insertTime"]).format('MM.DD HH:mm');
                            }
                        },
                        {
                            "dataIndex": "title",
                            "text": "需求标题",
                            "html": true,
                            "renderer" : function(v, rowData){
                                var userId = rowData.dicRowHandler.userId;
                                var pageType = rowData.resourceExtraRemark;
                                var requestId = rowData.id;
                                return "<div style='text-align: left'><a href='javascript:;' data-pageType="+pageType+" data-requestId="+requestId+" data-userId="+userId+" ms-click=isPermission>"+rowData["title"]+"</a></div>";
                            }
                        },
                        {
                            "dataIndex": "resourceName",
                            "text": "全部类型",
                            "html": true,
                            "hideable": false,
                            "headerWidget": {
                                "type": "select",
                                "widgetConfig": {
                                    "selectId": getTypeId('-type1'),
                                    "options": [],
                                    "onSelect": function(v,t,o){
                                        avalon.getVm(getTypeId('-grid1')).load();
                                    }
                                }
                            },
                            "renderer" : function(v, rowData){
                                return rowData["resourceName"];
                            }
                        },
                        {
                            "dataIndex": "dicRowContactCat",
                            "text": "来源",
                            "html": true,
                            "renderer" : function(v, rowData){
                                return rowData["dicRowContactCat"]["text"];
                            }
                        },
                        {
                            "dataIndex": "select",
                            "text": "操作",
                            "html": true,
                            "renderer" : function(){
                                return "<a ms-if='permission.assign_141' href='javascript:;' ms-click='assign' ms-data-id='$outer.el.id'>指派</a><a ms-if='!permission.assign_141' href='javascript:;'>无操作权限</a>";
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        var selectYear = avalon.getVm(getTypeId('-year1')).selectedValue;
                        var selectMonth = avalon.getVm(getTypeId('-month1')).selectedValue;
                        var resourceRemark = avalon.getVm(getTypeId('-type1')).selectedValue || '';
                        var startTime ,endTime;
                        if(!(selectMonth.toString().length)){
                            startTime = '';
                            endTime = ''
                        }else{
                            startTime = moment(selectYear+'-'+selectMonth,'YYYY-MM')/1;
                            endTime =moment(startTime).add('months',1)/1;
                        }
                        requestData = _.extend(requestData, {
                            "title": _.str.trim(this.keyword),  //搜索关键字
                            "resourceRemark": resourceRemark, //资源编码
                            "startTime": startTime , //开始时间
                            "endTime": endTime, //结束时间
                            "status" : 1
                        });
                        util.c2s({
                            "url": erp.BASE_PATH + "besRequirement/listBesRequirements.do",
                            "data": JSON.stringify(requestData),
                            "contentType" : 'application/json',
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    callback.call(this, data);
                                }
                            }
                        });
                    },
                    "search" : function(){
                        avalon.getVm(getTypeId('-grid1')).load();
                    },
                    "assign" : function(){
                        var gridVm = avalon.getVm(getTypeId('-grid1'));
                        var id = $(this).attr('data-id');
                        var gridData;
                        if(id){
                            gridVm.selectById(id);
                            gridData = gridVm.getSelectedData();
                        }else{
                            gridData = gridVm.getSelectedData();
                        }
                        if(gridData.length){
                            var arr = _.map(gridData,function(item){
                                return item['id'];
                            });
                            avalon.getVm(getTypeId('-persontree')).ids = arr;
                            avalon.getVm(getTypeId('-persontree')).open();
                        }else{
                            util.alert(
                                {
                                    "content": '您还没有选择要指派的需求！',
                                    "iconType": "error"
                                }
                            );
                        }
                    },
                    "isPermission" : function(){
                        var requestId = $(this).attr('data-requestId');
                        var userId = $(this).attr('data-userId');
                        var pageType = $(this).attr('data-pageType');
                        switch (pageType) {
                            case 'monthly_rpt_161':
                                pageVm.localHref('#/backend/home/monthly/'+requestId);
                        }
                    },
                    "keyword" : ''
                };


                //未完成
                vm.$grid2Opts = {
                    "gridId": getTypeId('-grid2'),
                    "disableCheckAll": true,  //是否禁用全选
                    "disableCheckbox": true, //是否禁用选择框，默认不禁用
                    "getTemplate": function (tmpl) {
                        var tmpHade = tmpl.split('MS_OPTION_TBAR');
                        var tmpMain = tmpHade[1].split('MS_OPTION_MAIN')[0];
                        var newFooter = '<div class="ui-grid-bbar fn-clear">' +
                            '<div class="pagination fn-right" ms-widget="pagination,$,$paginationOpts"></div>' +
                            '</div>';
                        return '<div class="ui-grid-search">' +
                            '<span ms-widget="select,$,$year2Opts"></span>' +
                            '<span ms-widget="select,$,$month2Opts"></span>' +
                            '<input style="width: 200px;" type="text" class="input-text search" ms-duplex="keyword" placeholder="需求标题">' +
                            '<button type="button" class="query-btn main-btn" ms-click="search">搜索</button>' +
                            '</div>MS_OPTION_TBAR' + tmpMain + newFooter;
                    },
                    "columns": [
                        {
                            "dataIndex": "insertTime",
                            "text": "日期",
                            "renderer" : function(v, rowData){
                                return moment(rowData["insertTime"]).format('MM.DD HH:mm');
                            }
                        },
                        {
                            "dataIndex": "title",
                            "text": "需求标题",
                            "html": true,
                            "renderer" : function(v, rowData){
                                var userId = rowData.dicRowHandler.userId;
                                var pageType = rowData.resourceExtraRemark;
                                var requestId = rowData.id;
                                return "<div style='text-align: left'><a href='javascript:;' data-pageType="+pageType+" data-requestId="+requestId+" data-userId="+userId+" ms-click=isPermission>"+rowData["title"]+"</a></div>";
                            }
                        },
                        {
                            "dataIndex": "resourceName",
                            "text": "全部类型",
                            "html": true,
                            "hideable": false,
                            "headerWidget": {
                                "type": "select",
                                "widgetConfig": {
                                    "selectId": getTypeId('-type2'),
                                    "options": [],
                                    "onSelect": function(v,t,o){
                                        avalon.getVm(getTypeId('-grid2')).load();
                                    }
                                }
                            },
                            "renderer" : function(v, rowData){
                                return rowData["resourceName"];
                            }
                        },
                        {
                            "dataIndex": "dicRowContactCat",
                            "text": "来源",
                            "html": true,
                            "renderer" : function(v, rowData){
                                return v["text"];
                            }
                        },
                        {
                            "dataIndex": "dicRowHandler",
                            "text": "处理人",
                            "html": true,
                            "hideable": false,
                            "headerWidget": {
                                "type": "select",
                                "widgetConfig": {
                                    "selectId": getTypeId('-person1'),
                                    "options": [],
                                    "onSelect": function(v,t,o){
                                        avalon.getVm(getTypeId('-grid2')).load();
                                    }
                                }
                            },
                            "renderer" : function(v, rowData){
                                return v["userName"];
                            }
                        },
                        {
                            "dataIndex": "dicRowReqStatus",
                            "text": "状态",
                            "html": true,
                            "renderer" : function(v,rowData){
                                return v["text"];
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        var selectYear = avalon.getVm(getTypeId('-year2')).selectedValue;
                        var selectMonth = avalon.getVm(getTypeId('-month2')).selectedValue;
                        var handlerId = avalon.getVm(getTypeId('-person1')).selectedValue || "";
                        var resourceRemark = avalon.getVm(getTypeId('-type2')).selectedValue || '';
                        var startTime ,endTime;
                        if(!(selectMonth.toString().length)){
                            startTime = '';
                            endTime = '';
                        }else{
                            startTime = moment(selectYear+'-'+selectMonth,'YYYY-MM')/1;
                            endTime =moment(startTime).add('months',1)/1;
                        }

                        requestData = _.extend(requestData, {
                            "title": _.str.trim(this.keyword),  // 搜索关键字
                            "handlerId" : handlerId,
                            "status" : 2,
                            "resourceRemark": resourceRemark, //资源编码
                            "startTime": startTime || "", //开始时间
                            "endTime": endTime || ""//结束时间
                        });
                        util.c2s({
                            "url": erp.BASE_PATH + "besRequirement/listBesRequirements.do",
                            "data": JSON.stringify(requestData),
                            "contentType" : 'application/json',
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    callback.call(this, data);
                                }
                            }
                        });
                    },
                    "search" : function(){
                        avalon.getVm(getTypeId('-grid2')).load();
                    },
                    "isPermission" : function(){
                        var requestId = $(this).attr('data-requestId');
                        var userId = $(this).attr('data-userId');
                        var pageType = $(this).attr('data-pageType');
                        if(pageVm.permission.assign_141 || pageVm.permission.batch_assign_142){
                            switch (pageType) {
                                case 'monthly_rpt_161':
                                    pageVm.localHref('#/backend/home/monthly/'+requestId);
                            }
                        }else if(userId == pageVm.pageUserId){
                            switch (pageType) {
                                case 'monthly_rpt_161':
                                    pageVm.localHref('#/backend/home/monthly/'+requestId);
                            }
                        }else{
                            util.alert({
                                "content": "您没有此权限！",
                                "iconType": "info"
                            });
                        }
                    },
                    "keyword" : ''
                };
                vm.$grid3Opts = {
                    "gridId": getTypeId('-grid3'),
                    "disableCheckAll": true,  //是否禁用全选
                    "disableCheckbox": true, //是否禁用选择框，默认不禁用
                    "getTemplate": function (tmpl) {
                        var tmpHade = tmpl.split('MS_OPTION_TBAR');
                        var tmpMain = tmpHade[1].split('MS_OPTION_MAIN')[0];
                        var newFooter = '<div class="ui-grid-bbar fn-clear">' +
                            '<div class="pagination fn-right" ms-widget="pagination,$,$paginationOpts"></div>' +
                            '</div>';
                        return '<div class="ui-grid-search">' +
                            '<span ms-widget="select,$,$year3Opts"></span>' +
                            '<span ms-widget="select,$,$month3Opts"></span>' +
                            '<input style="width: 200px;" type="text" class="input-text search" ms-duplex="keyword" placeholder="需求标题">' +
                            '<button type="button" class="query-btn main-btn" ms-click="search">搜索</button>' +
                            '</div>MS_OPTION_TBAR' + tmpMain + newFooter;
                    },
                    "columns": [
                        {
                            "dataIndex": "insertTime",
                            "text": "日期",
                            "renderer" : function(v, rowData){
                                return moment(rowData["insertTime"]).format('MM.DD HH:mm');
                            }
                        },
                        {
                            "dataIndex": "title",
                            "text": "需求标题",
                            "html": true,
                            "renderer" : function(v, rowData){
                                var userId = rowData.dicRowHandler.userId;
                                var pageType = rowData.resourceExtraRemark;
                                var requestId = rowData.id;
                                return "<div style='text-align: left'><a href='javascript:;' data-pageType="+pageType+" data-requestId="+requestId+" data-userId="+userId+" ms-click=isPermission>"+rowData["title"]+"</a></div>";
                            }
                        },
                        {
                            "dataIndex": "resourceName",
                            "text": "全部类型",
                            "html": true,
                            "hideable": false,
                            "headerWidget": {
                                "type": "select",
                                "widgetConfig": {
                                    "selectId": getTypeId('-type3'),
                                    "options": [],
                                    "onSelect": function(v,t,o){
                                        avalon.getVm(getTypeId('-grid3')).load();
                                    }
                                }
                            },
                            "renderer" : function(v, rowData){
                                return rowData["resourceName"];
                            }
                        },
                        {
                            "dataIndex": "dicRowContactCat",
                            "text": "来源",
                            "html": true,
                            "renderer" : function(v, rowData){
                                return v["text"];
                            }
                        },
                        {
                            "dataIndex": "dicRowHandler",
                            "text": "处理人",
                            "html": true,
                            "hideable": false,
                            "headerWidget": {
                                "type": "select",
                                "widgetConfig": {
                                    "selectId": getTypeId('-person2'),
                                    "options": [],
                                    "onSelect": function(v,t,o){
                                        avalon.getVm(getTypeId('-grid3')).load();
                                    }
                                }
                            },
                            "renderer" : function(v, rowData){
                                return v["userName"];
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        var selectYear = avalon.getVm(getTypeId('-year3')).selectedValue;
                        var selectMonth = avalon.getVm(getTypeId('-month3')).selectedValue;
                        var handlerId = avalon.getVm(getTypeId('-person2')).selectedValue || "";
                        var resourceRemark = avalon.getVm(getTypeId('-type3')).selectedValue || '';
                        var startTime ,endTime;
                        if(!(selectMonth.toString().length)){
                            startTime = '';
                            endTime = '';
                        }else{
                            startTime = moment(selectYear+'-'+selectMonth,'YYYY-MM')/1;
                            endTime =moment(startTime).add('months',1)/1;
                        }

                        requestData = _.extend(requestData, {
                            "title": _.str.trim(this.keyword),  // 搜索关键字
                            "handlerId" : handlerId,
                            "status" : 3,
                            "resourceRemark": resourceRemark, //资源编码
                            "startTime": startTime || "", //开始时间
                            "endTime": endTime || ""//结束时间
                        });
                        util.c2s({
                            "url": erp.BASE_PATH + "besRequirement/listBesRequirements.do",
                            "data": JSON.stringify(requestData),
                            "contentType" : 'application/json',
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    callback.call(this, data);
                                }
                            }
                        });
                    },
                    "search" : function(){
                        avalon.getVm(getTypeId('-grid3')).load();
                    },
                    "isPermission" : function(){
                        var requestId = $(this).attr('data-requestId');
                        var userId = $(this).attr('data-userId');
                        var pageType = $(this).attr('data-pageType');
                        switch (pageType) {
                            case 'monthly_rpt_161':
                                pageVm.localHref('#/backend/home/monthly/'+requestId);
                        }
                    },
                    "keyword" : ''
                };

                vm.$treeOpts = {//指派处理人
                    "persontreeId" : getTypeId('-persontree'),
                    "merchantId" : '',
                    "ids" : [],
                    "callFn" : function(responseData){
                        avalon.getVm(getTypeId('-grid1')).load();
                    }
                };
                vm.$year1Opts = {
                    "selectId" :  getTypeId('-year1'),
                    "options" : []
                };
                vm.$month1Opts = {
                    "selectId" :  getTypeId('-month1'),
                    "options" : []
                };
                vm.$year2Opts = {
                    "selectId" :  getTypeId('-year2'),
                    "options" : []
                };
                vm.$month2Opts = {
                    "selectId" :  getTypeId('-month2'),
                    "options" : []
                };
                vm.$year3Opts = {
                    "selectId" :  getTypeId('-year3'),
                    "options" : []
                };
                vm.$month3Opts = {
                    "selectId" :  getTypeId('-month3'),
                    "options" : []
                };
            });
            avalon.scan(pageEl[0]);

            //获取当前日期
            var curDate = new Date();
            var curYear = curDate.getFullYear();//当前年
            var curMonth = curDate.getMonth()+1;//当前月
            var yearArr = [];
            for(var i=2013;i<=curYear;i++){
                yearArr.push({"text": i+"年","value": i});
            }
            var monthArr = [
                {"text": "全部","value": ''},
                {"text": "1月","value": '01'},
                {"text": "2月","value": '02'},
                {"text": "3月","value": '03'},
                {"text": "4月","value": '04'},
                {"text": "5月","value": '05'},
                {"text": "6月","value": '06'},
                {"text": "7月","value": '07'},
                {"text": "8月","value": '08'},
                {"text": "9月","value": '09'},
                {"text": "10月","value": '10'},
                {"text": "11月","value": '11'},
                {"text": "12月","value": '12'}
            ];
            var person = [
                {"text": "全部","value": ''},
                {"text": "自己","value": pageVm.pageUserId},
                {"text": "其他人","value": -1}
            ];

            avalon.getVm(getTypeId('-year1')).setOptions(yearArr);
            avalon.getVm(getTypeId('-year1')).selectValue(curYear,true);
            avalon.getVm(getTypeId('-year2')).setOptions(yearArr);
            avalon.getVm(getTypeId('-year2')).selectValue(curYear,true);
            avalon.getVm(getTypeId('-year3')).setOptions(yearArr);
            avalon.getVm(getTypeId('-year3')).selectValue(curYear,true);
            avalon.getVm(getTypeId('-month1')).setOptions(monthArr);
            avalon.getVm(getTypeId('-month1')).selectValue(curMonth,true);
            avalon.getVm(getTypeId('-month2')).setOptions(monthArr);
            avalon.getVm(getTypeId('-month2')).selectValue(curMonth,true);
            avalon.getVm(getTypeId('-month3')).setOptions(monthArr);
            avalon.getVm(getTypeId('-month3')).selectValue(curMonth,true);
            avalon.getVm(getTypeId('-person1')).setOptions(person);
            avalon.getVm(getTypeId('-person2')).setOptions(person);
            //全部类型，操作人等接口调用
            util.c2s({
                "url": erp.BASE_PATH + "besRequirement/listBesReqConditions.do",
                "data": JSON.stringify({}),
                "contentType" : 'application/json',
                "success": function (responseData) {
                    var data;
                    var typeArr = [{"text":"全部","value":""}];
                    if (responseData.flag) {
                        data = responseData.data;
                        var arr = _.map(data.parameResources,function(item,index){
                            return {'text':item.resourceName,'value':item.remark}
                        });
                        typeArr = typeArr.concat(arr);
                        avalon.getVm(getTypeId('-type1')).setOptions(typeArr);
                        avalon.getVm(getTypeId('-type2')).setOptions(typeArr);
                        avalon.getVm(getTypeId('-type3')).setOptions(typeArr);
                    }
                }
            });
            if(pageVm.permission.unassigned_137){//是否有未指派的权限（加载不同的tab）
                pageVm.pageStatus = 1;
                avalon.getVm(getTypeId('-grid1')).load();
            }else if(pageVm.permission.incomplete_138){
                pageVm.pageStatus = 2;
                avalon.getVm(getTypeId('-grid2')).load();
            }else if(pageVm.permission.complete_139){
                pageVm.pageStatus = 3;
                avalon.getVm(getTypeId('-grid3')).load();
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


