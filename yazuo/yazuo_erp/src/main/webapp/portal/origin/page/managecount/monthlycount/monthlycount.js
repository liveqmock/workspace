/**
 * 月报统计管理
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
                //用户id
                vm.pageUserId = erp.getAppData('user').id;
                //页面跳转
                vm.localHref = function (url) {
                    util.jumpPage(url);
                };
                //权限
                vm.permission = {

                };
                //获取当月最多天数
                vm.getMaxDay = function (year, month) {
                    return new Date(year, month, 0).getDate();
                };
                //年
                vm.$yearSelectOpts = {
                    "selectId" : getTypeId('-year'),
                    "options" : [],
                    "onSelect" : function(v,t,o){

                    }
                };
                //月
                vm.$monthSelectOpts = {
                    "selectId" : getTypeId('-month'),
                    "options" : [],
                    "onSelect" : function(v,t,o){
                        var day = [];
                        var currYear = avalon.getVm(getTypeId('-year')).selectedValue;
                        for(var i=1;i<=getMaxDay(currYear,v);i++){
                            day.push({'text':i,'value':i})
                        }
                        var maxDay = getMaxDay(avalon.getVm(getTypeId('-year')).selectedValue,v);
                        if(vm.checkDate>maxDay){
                            var indexAt = maxDay-1;
                        }else{
                            var indexAt = vm.checkDate-1;
                        }
                        avalon.getVm(getTypeId('-day')).setOptions(day,indexAt);
                    }
                };
                //是否是后台返回的日期
                vm.isCallDate = true;
                //上一个月份
                vm.pervMonth = 0;
                //头部选项内容
                vm.conditions = [];
                //精简选项和全部选项的依据（1=全部选项，0=精简选项）
                vm.side = 1;
                //已选项条件数组
                vm.selected = [];
                //发送后台已选项条件数组
                vm.selectedModel = [];
                //月报发送考核日期
                vm.checkDate = '';
                //控制精简和全部选项
                vm.selectShowHide = function () {
                    var val = $(this).attr('data-id');
                    if(val == 1){
                        vm.conditions = vm.conditions2;
                        vm.side = 0;
                    }else{
                        vm.conditions = vm.conditions1;
                        vm.side = 1;
                    }
                };
                //增加发送后台的选项数据
                vm.sendSelected = function(){
                    var isSelected = false;
                    var obj = {
                        "type" : $(this).attr('data-type'),
                        "value": $(this).attr('data-value'),
                        "title": $(this).html()
                    };
                    for(var i=0;i<vm.selectedModel.length;i++){
                        if(vm.selectedModel[i]['type'] == obj['type']){
                            vm.selectedModel[i]['title'] = obj['title'];
                            vm.selectedModel[i]['value'] = obj['value'];
                            isSelected = true;
                        }
                    }
                    if(!isSelected){
                        vm.selectedModel.push(obj);
                    }
                    avalon.getVm(getTypeId('-grid')).load();
                };
                //删除发送后台的选项数据
                vm.removeSelected = function(){
                    var type = $(this).attr('data-type');
                    var value = $(this).attr('data-value');
                    for(var i=0;i<vm.selectedModel.length;i++){
                        if(vm.selectedModel[i]['type'] == type && vm.selectedModel[i]['value'] == value){
                            vm.selectedModel.splice(i,1);
                        }
                    }
                    avalon.getVm(getTypeId('-grid')).load();
                };
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
                            '<div class="grid-tit1">已选条件：</div>' +
                            '<div class="grid-tit2">' +
                            '<p class="options" ms-repeat="selected">{{el.title}}<i class="iconfont" ms-click="removeSelected" ms-data-value="el.value" ms-data-type="el.type">&#xe616;</i></p>' +
                            '<p>共有<span class="num">{{gridTotalSize}}</span>条信息</p>' +
                            '</div>' +
                            '</div>'+
                            '<div class="ui-grid-search fn-right">' +
                            '<span>月报讲解考核，每月&nbsp;&nbsp;</span>' +
                            '<span style="width: 80px;" class="schedule" ms-widget="select,$,$daySelectOpts"></span>&nbsp;&nbsp;日' +
                            '</div>' +
                            '</div>MS_OPTION_TBAR' + tmpHade[1];
                    },
                    "columns": [
                        {
                            "dataIndex": "merchantname",
                            "text": "商户",
                            "html": true,
                            "renderer" : function(v, rowData){
                                if(rowData['merchantname']){
                                    return "<div style='text-align: left'>"+rowData['merchantname']+"</div>";
                                }else{
                                    return '-';
                                }
                            }

                        },
                        {
                            "dataIndex": "contact",
                            "text": "联系人",
                            "renderer" : function(v, rowData){
                                if(rowData['contact']){
                                    return rowData['contact'];
                                }else{
                                    return '-';
                                }
                            }
                        },
                        {
                            "dataIndex": "username",
                            "text": "前端",
                            "renderer" : function(v, rowData){
                                if(rowData['username']){
                                    return rowData['username'];
                                }else{
                                    return '-';
                                }
                            }
                        },
                        {
                            "dataIndex": "satisfied",
                            "text": "满意度",
                            "renderer" : function(v, rowData){
                                if(rowData['satisfied']){
                                    return rowData['satisfied'];
                                }else{
                                    return '-';
                                }
                            }
                        },
                        {
                            "dataIndex": "called",
                            "text": "回访",
                            "renderer" : function(v, rowData){
                                if(rowData['called']){
                                    return rowData['called'];
                                }else{
                                    return '-';
                                }
                            }
                        },
                        {
                            "dataIndex": "calledtime",
                            "text": "回访时间",
                            "renderer" : function(v, rowData){
                                if(rowData['calledtime']){
                                    return moment(rowData['calledtime']).format('YYYY/MM/DD');
                                }else{
                                    return '-';
                                }
                            }
                        },
                        {
                            "dataIndex": "explainedtime",
                            "text": "讲解时间",
                            "renderer" : function(v, rowData){
                                if(rowData['explainedtime']){
                                    return moment(rowData['explainedtime']).format('YYYY/MM/DD');
                                }else{
                                    return '-';
                                }
                            }
                        },
                        {
                            "dataIndex": "sent",
                            "text": "发送",
                            "renderer" : function(v, rowData){
                                if(rowData['sent']){
                                    return rowData['sent'];
                                }else{
                                    return '-';
                                }
                            }
                        },
                        {
                            "dataIndex": "explained",
                            "text": "讲解",
                            "renderer" : function(v, rowData){
                                if(rowData['explained']){
                                    return rowData['explained'];
                                }else{
                                    return '-';
                                }
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        var year = avalon.getVm(getTypeId('-year')).selectedValue;
                        var month = avalon.getVm(getTypeId('-month')).selectedValue;
                        month = (month.toString().length == 1)?('0'+month):month;
                        year = year || currYear;
                        month = month || currMonth+1;
                        var beginTime = moment(year+'-'+month+'-01')/1;
                        requestData = _.extend(requestData, {
                            "merchantName": _.str.trim(vm.keyword),//搜索关键字
                            "beginTime": beginTime,
                            "conditions": pageVm.selectedModel.$model,
                            "checkDate" : pageVm.checkDate
                        });

                        
                        util.c2s({
                            "url": erp.BASE_PATH + "/besMonthlyReport/statistics.do",
                            "contentType": 'application/json',
                            "data": JSON.stringify(requestData),
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    pageVm.conditions = data.conditions;
                                    pageVm.conditions1 = [].concat(data.conditions);
                                    pageVm.conditions2 = ([].concat(data.conditions));
                                    pageVm.conditions2.length = 2;
                                    for(var i=0;i<data.selected.length;i++){
                                        if(data.selected[i]['value'] == '0'){
                                            data.selected.splice(i,1);
                                        }
                                    }
                                    pageVm.selected = data.selected;
                                    pageVm.selectedModel = data.selected;
                                    pageVm.isCallDate = true;
                                    pageVm.checkDate = data.checkDate;
                                    var maxDay = getMaxDay(avalon.getVm(getTypeId('-year')).selectedValue,avalon.getVm(getTypeId('-month')).selectedValue);
                                    if(data.checkDate>maxDay){
                                        data.checkDate = maxDay;
                                    }
                                    avalon.getVm(getTypeId('-day')).selectValue(data.checkDate);
                                    callback.call(this, data.resultSet);
                                }
                            }
                        });
                    },
                    "$daySelectOpts" : {//日
                        "selectId" : getTypeId('-day'),
                        "options" : [],
                        "onSelect" : function(v,t,k){
                            var pervMonth = avalon.getVm(getTypeId('-month')).selectedValue;
                            //判断月份是否改变（如果改变不请求后台，未改变就请求后台）
                            if(pervMonth == vm.pervMonth){
                                pageVm.checkDate = v.toString();
                                avalon.getVm(getTypeId('-grid')).load();
                            }else{
                                vm.pervMonth = pervMonth;
                            }
                        }
                    }
                };
                /*grid-end*/
                vm.search = function(){
                    avalon.getVm(getTypeId('-grid')).load();
                };






            });
            avalon.scan(pageEl[0]);
            var year = [];
            var month = [];
            var day = [];
            var d= new Date();
            var currYear = d.getFullYear();
            var currMonth = d.getMonth();
            var currDay = d.getDate();

            //获取当月最多天数
            function getMaxDay(year, month) {
                return new Date(year, month, 0).getDate();
            }
            for(var i=2013;i<=d.getFullYear();i++){
                year.push({'text':i+'月','value':i})
            }
            for(var i=1;i<=12;i++){
                month.push({'text':i+'月','value':i})
            }
            for(var i=1;i<=getMaxDay(currYear,currMonth+1);i++){
                day.push({'text':i,'value':i})
            }
            avalon.getVm(getTypeId('-year')).setOptions(year);
            avalon.getVm(getTypeId('-year')).selectValue(currYear);

            avalon.getVm(getTypeId('-month')).setOptions(month);
            avalon.getVm(getTypeId('-month')).selectValue(currMonth+1);
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


