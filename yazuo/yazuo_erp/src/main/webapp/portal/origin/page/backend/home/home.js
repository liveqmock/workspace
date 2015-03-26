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
                    assign_141 : util.hasPermission("assign_141"),//未指派-指派
                    abandon_142 : util.hasPermission("abandon_142"),//未指派-放弃
                    incomplete_138 : util.hasPermission("incomplete_138"),//未完成
                    abandon_172 : util.hasPermission("abandon_172"),//未完成-放弃
                    complete_139 : util.hasPermission("complete_139"),//已完成
                    mine_reqs_176 : util.hasPermission("mine_reqs_176"),//我发起
                    abandoned_reqs_177 : util.hasPermission("abandoned_reqs_177"),//已放弃
                    create_reqs_140 : util.hasPermission("create_reqs_140")//创建需求
                };
                vm.changePageStatus = function (value) {//大标签切换
                    vm.pageStatus = value;
                    if (value == 1) {
                        avalon.getVm(getTypeId('-grid1')).load();
                    } else if (value == 2) {
                        avalon.getVm(getTypeId('-grid2')).load();
                    } else if (value == 3) {
                        avalon.getVm(getTypeId('-grid3')).load();
                    } else if (value == 4) {
                        avalon.getVm(getTypeId('-grid4')).load();
                    } else if (value == 5) {
                        avalon.getVm(getTypeId('-grid5')).load();
                    }
                };
                //页面逻辑判断需要的便利
                vm.isAllAssign = false;//是否可以批了指派
                vm.giveUpIdArr = [];//放弃和批量放弃的
                vm.currGridId = 0;//
                //页面逻辑判断需要的便利-end
                vm.$grid1Opts = {
                    "gridId": getTypeId('-grid1'),
                    "disableCheckAll": (vm['permission']['assign_141']||vm['permission']['abandon_142'])?false:true,  //是否禁用全选
                    "disableCheckbox": (vm['permission']['assign_141']||vm['permission']['abandon_142'])?false:true, //是否禁用选择框，默认不禁用
                    "getTemplate": function (tmpl) {
                        var tmpHade = tmpl.split('MS_OPTION_TBAR');
                        var tmpMain = tmpHade[1].split('MS_OPTION_MAIN')[0];
                        var newFooter = '<div class="ui-grid-bbar fn-clear">' +
                            '<div class="grid-action fn-left" ms-if="permission.assign_141 || permission.abandon_142">' +
                            '<button type="button" class="check-all-btn main-btn" ms-click="scCheckAll">全选</button>&nbsp;' +
                            '<button ms-if="isAllAssign && permission.assign_141" type="button" class="batch-remove-btn main-btn" ms-click="assign">批量指派</button>&nbsp;' +
                            '<button ms-if="!isAllAssign && permission.assign_141" type="button" class="batch-remove-btn minor-btn">批量指派</button>&nbsp;' +
                            '<button ms-if="permission.abandon_142" type="button" class="batch-remove-btn main-btn" attr-type="all" attr-gridId="1" ms-click="giveUp">批量放弃</button>' +
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
                            "dataIndex": "nodeLastTime",
                            "text": "日期",
                            "renderer" : function(v, rowData){
                                return moment(rowData["nodeLastTime"]).format('MM.DD HH:mm');
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
                                return "<div class='grid-list-width'><a href='javascript:;' data-pageType="+pageType+" data-requestId="+requestId+" data-userId="+userId+" ms-click=isPermission>"+rowData["title"]+"</a></div>";
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
                                        if(vm.pageStatus == 1){
                                            avalon.getVm(getTypeId('-grid1')).load();
                                        }
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
                                return "<a ms-if='permission.abandon_142' href='javascript:;' attr-gridId='1' attr-type='once' ms-click='giveUp' ms-data-id='$outer.el.id'>放弃</a>&nbsp;&nbsp;<a ms-if='permission.assign_141' href='javascript:;' ms-click='assign' ms-data-id='$outer.el.id'>指派</a>";
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        var selectYear = avalon.getVm(getTypeId('-year1')).selectedValue;
                        var selectMonth = avalon.getVm(getTypeId('-month1')).selectedValue;
                        var resourceRemark = avalon.getVm(getTypeId('-type1')).selectedValue || '';
                        var startTime ,endTime;
                        if(!(selectMonth.toString().length)){
                            var nextYear = parseInt(selectYear)+1;
                            startTime = moment(selectYear+'-'+selectMonth,'YYYY-MM')/1;
                            endTime = moment(nextYear+'-'+selectMonth,'YYYY-MM')/1;
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
                                var data,flag = true;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    callback.call(this, data);
                                    for(var i=0;i<data.rows.length;i++){
                                        if(data.rows[0].resourceName != data.rows[i].resourceName){
                                            flag = false;
                                        }
                                    }
                                    if(flag){
                                        vm.isAllAssign = true;
                                    }else{
                                        vm.isAllAssign = false;
                                    }
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
                    "keyword" : ''
                };


                //未完成
                vm.$grid2Opts = {
                    "gridId": getTypeId('-grid2'),
                    "disableCheckAll": (vm['permission']['abandon_172'])?false:true, //是否禁用全选
                    "disableCheckbox": (vm['permission']['abandon_172'])?false:true, //是否禁用选择框，默认不禁用
                    "getTemplate": function (tmpl) {
                        var tmpHade = tmpl.split('MS_OPTION_TBAR');
                        var tmpMain = tmpHade[1].split('MS_OPTION_MAIN')[0];
                        var newFooter = '<div class="ui-grid-bbar fn-clear">' +
                            '<div class="grid-action fn-left" ms-if="permission.abandon_172">' +
                            '<button type="button" class="check-all-btn main-btn" ms-click="scCheckAll">全选</button>&nbsp;' +
                            '<button type="button" class="batch-remove-btn main-btn" attr-type="all" attr-gridId="2" ms-click="giveUp">批量放弃</button>' +
                            '</div>' +
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
                            "dataIndex": "nodeLastTime",
                            "text": "日期",
                            "renderer" : function(v, rowData){
                                return moment(rowData["nodeLastTime"]).format('MM.DD HH:mm');
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
                                return "<div class='grid-list-width'><a href='javascript:;' data-pageType="+pageType+" data-requestId="+requestId+" data-userId="+userId+" ms-click=isPermission>"+rowData["title"]+"</a></div>";
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
                                        if(vm.pageStatus == 2){
                                            avalon.getVm(getTypeId('-grid2')).load();
                                        }
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
                                        if(vm.pageStatus == 2){
                                            avalon.getVm(getTypeId('-grid2')).load();
                                        }
                                    }
                                }
                            },
                            "renderer" : function(v, rowData){
                                return v["userName"];
                            }
                        },
                        {
                            "dataIndex": "dicRowReqStatus",//dicRowProcessStatus
                            "text": "状态",
                            "html": true,
                            "renderer" : function(v,rowData){
                                return v["text"];
                                //return "<div class='grid-status-width'>"+rowData['dicRowProcessStatus']['text']+"</div>";
                            }
                        },
                        {
                            "dataIndex": "select",
                            "text": "操作",
                            "html": true,
                            "renderer" : function(){
                                return "<a ms-if='permission.abandon_172' href='javascript:;' attr-gridId='2' attr-type='once' ms-click='giveUp' ms-data-id='$outer.el.id'>放弃</a>";
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
                            var nextYear = parseInt(selectYear)+1;
                            startTime = moment(selectYear+'-'+selectMonth,'YYYY-MM')/1;
                            endTime = moment(nextYear+'-'+selectMonth,'YYYY-MM')/1;
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
                            "dataIndex": "nodeLastTime",
                            "text": "日期",
                            "renderer" : function(v, rowData){
                                return moment(rowData["nodeLastTime"]).format('MM.DD HH:mm');
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
                                return "<div class='grid-list-width'><a href='javascript:;' data-pageType="+pageType+" data-requestId="+requestId+" data-userId="+userId+" ms-click=isPermission>"+rowData["title"]+"</a></div>";
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
                                        if(vm.pageStatus == 3){
                                            avalon.getVm(getTypeId('-grid3')).load();
                                        }
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
                                        if(vm.pageStatus == 3){
                                            avalon.getVm(getTypeId('-grid3')).load();
                                        }
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
                            var nextYear = parseInt(selectYear)+1;
                            startTime = moment(selectYear+'-'+selectMonth,'YYYY-MM')/1;
                            endTime = moment(nextYear+'-'+selectMonth,'YYYY-MM')/1;
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

                    "keyword" : ''
                };
                vm.$grid4Opts = {
                    "gridId": getTypeId('-grid4'),
                    "disableCheckAll": true,  //是否禁用全选
                    "disableCheckbox": true, //是否禁用选择框，默认不禁用
                    "getTemplate": function (tmpl) {
                        var tmpHade = tmpl.split('MS_OPTION_TBAR');
                        var tmpMain = tmpHade[1].split('MS_OPTION_MAIN')[0];
                        var newFooter = '<div class="ui-grid-bbar fn-clear">' +
                            '<div class="pagination fn-right" ms-widget="pagination,$,$paginationOpts"></div>' +
                            '</div>';
                        return '<div class="ui-grid-search">' +
                            '<span ms-widget="select,$,$year4Opts"></span>' +
                            '<span ms-widget="select,$,$month4Opts"></span>' +
                            '<input style="width: 200px;" type="text" class="input-text search" ms-duplex="keyword" placeholder="需求标题">' +
                            '<button type="button" class="query-btn main-btn" ms-click="search">搜索</button>' +
                            '</div>MS_OPTION_TBAR' + tmpMain + newFooter;
                    },
                    "columns": [
                        {
                            "dataIndex": "nodeLastTime",
                            "text": "日期",
                            "renderer" : function(v, rowData){
                                return moment(rowData["nodeLastTime"]).format('MM.DD HH:mm');
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
                                return "<div class='grid-list-width'><a href='javascript:;' data-pageType="+pageType+" data-requestId="+requestId+" data-userId="+userId+" ms-click=isPermission>"+rowData["title"]+"</a></div>";
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
                                    "selectId": getTypeId('-type4'),
                                    "options": [],
                                    "onSelect": function(v,t,o){
                                        if(vm.pageStatus == 4){
                                            avalon.getVm(getTypeId('-grid4')).load();
                                        }
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
                                    "selectId": getTypeId('-person3'),
                                    "options": [],
                                    "onSelect": function(v,t,o){
                                        if(vm.pageStatus == 4){
                                            avalon.getVm(getTypeId('-grid4')).load();
                                        }
                                    }
                                }
                            },
                            "renderer" : function(v, rowData){
                                return v["userName"];
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        var selectYear = avalon.getVm(getTypeId('-year4')).selectedValue;
                        var selectMonth = avalon.getVm(getTypeId('-month4')).selectedValue;
                        var handlerId = avalon.getVm(getTypeId('-person3')).selectedValue || "";
                        var resourceRemark = avalon.getVm(getTypeId('-type4')).selectedValue || '';
                        var startTime ,endTime;
                        if(!(selectMonth.toString().length)){
                            var nextYear = parseInt(selectYear)+1;
                            startTime = moment(selectYear+'-'+selectMonth,'YYYY-MM')/1;
                            endTime = moment(nextYear+'-'+selectMonth,'YYYY-MM')/1;
                        }else{
                            startTime = moment(selectYear+'-'+selectMonth,'YYYY-MM')/1;
                            endTime =moment(startTime).add('months',1)/1;
                        }

                        requestData = _.extend(requestData, {
                            "title": _.str.trim(this.keyword),  // 搜索关键字
                            "handlerId" : handlerId,
                            "status" : 0,
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
                        avalon.getVm(getTypeId('-grid4')).load();
                    },

                    "keyword" : ''
                };
                vm.$grid5Opts = {
                    "gridId": getTypeId('-grid5'),
                    "disableCheckAll": true,  //是否禁用全选
                    "disableCheckbox": true, //是否禁用选择框，默认不禁用
                    "getTemplate": function (tmpl) {
                        var tmpHade = tmpl.split('MS_OPTION_TBAR');
                        var tmpMain = tmpHade[1].split('MS_OPTION_MAIN')[0];
                        var newFooter = '<div class="ui-grid-bbar fn-clear">' +
                            '<div class="pagination fn-right" ms-widget="pagination,$,$paginationOpts"></div>' +
                            '</div>';
                        return '<div class="ui-grid-search">' +
                            '<span ms-widget="select,$,$year5Opts"></span>' +
                            '<span ms-widget="select,$,$month5Opts"></span>' +
                            '<input style="width: 200px;" type="text" class="input-text search" ms-duplex="keyword" placeholder="需求标题">' +
                            '<button type="button" class="query-btn main-btn" ms-click="search">搜索</button>' +
                            '</div>MS_OPTION_TBAR' + tmpMain + newFooter;
                    },
                    "columns": [
                        {
                            "dataIndex": "nodeLastTime",
                            "text": "日期",
                            "renderer" : function(v, rowData){
                                return moment(rowData["nodeLastTime"]).format('MM.DD HH:mm');
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
                                return "<div class='grid-list-width'><a href='javascript:;' data-pageType="+pageType+" data-requestId="+requestId+" data-userId="+userId+" ms-click=isPermission>"+rowData["title"]+"</a></div>";
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
                                    "selectId": getTypeId('-type5'),
                                    "options": [],
                                    "onSelect": function(v,t,o){
                                        if(vm.pageStatus == 5){
                                            avalon.getVm(getTypeId('-grid5')).load();
                                        }
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
                                    "selectId": getTypeId('-person4'),
                                    "options": [],
                                    "onSelect": function(v,t,o){
                                        if(vm.pageStatus == 5){
                                            avalon.getVm(getTypeId('-grid5')).load();
                                        }
                                    }
                                }
                            },
                            "renderer" : function(v, rowData){
                                return v["userName"];
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        var selectYear = avalon.getVm(getTypeId('-year5')).selectedValue;
                        var selectMonth = avalon.getVm(getTypeId('-month5')).selectedValue;
                        var handlerId = avalon.getVm(getTypeId('-person4')).selectedValue || "";
                        var resourceRemark = avalon.getVm(getTypeId('-type5')).selectedValue || '';
                        var startTime ,endTime;
                        if(!(selectMonth.toString().length)){
                            var nextYear = parseInt(selectYear)+1;
                            startTime = moment(selectYear+'-'+selectMonth,'YYYY-MM')/1;
                            endTime = moment(nextYear+'-'+selectMonth,'YYYY-MM')/1;
                        }else{
                            startTime = moment(selectYear+'-'+selectMonth,'YYYY-MM')/1;
                            endTime =moment(startTime).add('months',1)/1;
                        }

                        requestData = _.extend(requestData, {
                            "title": _.str.trim(this.keyword),  // 搜索关键字
                            "handlerId" : handlerId,
                            "status" : 4,
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
                        avalon.getVm(getTypeId('-grid5')).load();
                    },
                    "keyword" : ''
                };
                /*根据返回字段跳到相关页面*/
                vm.isPermission = function(){
                    var requestId = $(this).attr('data-requestId');
                    var userId = $(this).attr('data-userId');
                    var pageType = $(this).attr('data-pageType');
                    switch (pageType) {
                        case 'monthly_rpt_161'://跳转到月报
                            pageVm.localHref('#/backend/home/monthly/'+requestId);

                    }
                };
                /*根据返回字段跳到相关页面-end*/
                /*放弃&批量放弃*/
                vm.giveUp = function(){
                    var type = $(this).attr('attr-type');
                    var id = $(this).attr('data-id');
                    var gridId = $(this).attr('attr-gridId');
                    var gridVm = avalon.getVm(getTypeId('-grid'+gridId));
                    var selectVm = avalon.getVm(getTypeId("-quitDialog"));
                    var gridData;
                    if(type == 'all'){
                        selectVm.title = '批量放弃';
                        gridData = gridVm.getSelectedData();
                        if(gridData.length){
                            pageVm.giveUpIdArr = _.map(gridData,function(item){
                                return item['id'];
                            });
                        }else{
                            util.alert(
                                {
                                    "content": '您还没有选择要放弃的需求！',
                                    "iconType": "error"
                                }
                            );
                            return;
                        }
                    }else if(type == 'once'){
                        selectVm.title = '放弃';
                        gridVm.selectById(id);
                        pageVm.giveUpIdArr[0] = gridVm.getSelectedData()[0].id;
                    }
                    vm.currGridId = gridId;
                    selectVm.open();
                };

                vm.$quitDialogOpts = {
                    "dialogId" : getTypeId("-quitDialog"),
                    "title" : "放弃",
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
                    "onOpen": function () {},
                    "onClose": function () {
                        var formVm = avalon.getVm(getTypeId('-quitForm'));
                        formVm.remark = '';
                        pageVm.giveUpIdArr = [];
                        formVm.reset();
                    },
                    "submit": function (evt) {
                        var requestData,
                            dialogVm = avalon.getVm(getTypeId("-quitDialog")),
                            formVm = avalon.getVm(getTypeId("-quitForm"));
                        if (formVm.validate()) {
                            util.c2s({
                                "url": erp.BASE_PATH + "besRequirement/batchSaveQuit.do",
                                "data": JSON.stringify(
                                    {
                                        "ids": pageVm.giveUpIdArr,
                                        "content": formVm.remark
                                    }
                                ),
                                "contentType" : 'application/json',
                                "success": function (responseData) {
                                    var data;
                                    if (responseData.flag) {
                                        dialogVm.close();
                                        avalon.getVm(getTypeId('-grid'+pageVm.currGridId)).load();
                                    }
                                }
                            });
                        }
                    }
                };
                vm.$quitFormOpts = {
                    "formId": getTypeId("-quitForm"),
                    "field":
                        [{
                            "selector": ".quit-description",
                            "name": "remark",
                            "rule": ["noempty", function (val, rs) {
                                if(val.length>250){
                                    return "内容不能超过250个字！";
                                }
                                if (rs[0] !== true) {
                                    return "内容不能为空";
                                } else {
                                    return true;
                                }
                            }]
                        }
                        ],
                    "remark":""
                };

                /*放弃&批量放弃-end*/

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
                vm.$year4Opts = {
                    "selectId" :  getTypeId('-year4'),
                    "options" : []
                };
                vm.$month4Opts = {
                    "selectId" :  getTypeId('-month4'),
                    "options" : []
                };
                vm.$year5Opts = {
                    "selectId" :  getTypeId('-year5'),
                    "options" : []
                };
                vm.$month5Opts = {
                    "selectId" :  getTypeId('-month5'),
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
            _.map(_.range(1,6),function(item){
                avalon.getVm(getTypeId('-year'+item)).setOptions(yearArr);
                avalon.getVm(getTypeId('-year'+item)).selectValue(curYear,true);
                avalon.getVm(getTypeId('-month'+item)).setOptions(monthArr);
                avalon.getVm(getTypeId('-month'+item)).selectValue(curMonth,true);
                if(item<5){
                    avalon.getVm(getTypeId('-person'+item)).setOptions(person);
                }
            });

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
                        _.map(_.range(1,6),function(item){
                            avalon.getVm(getTypeId('-type'+item)).setOptions(typeArr);
                        });
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
            }else if(pageVm.permission.mine_reqs_176){
                pageVm.pageStatus = 4;
                avalon.getVm(getTypeId('-grid4')).load();
            }else if(pageVm.permission.abandoned_reqs_177){
                pageVm.pageStatus = 5;
                avalon.getVm(getTypeId('-grid5')).load();
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


