/**
 * 工作计划
 */
define(['avalon', 'util', 'moment', 'json','../../../widget/form/select'], function (avalon, util, moment, JSON) {
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
            var loginUserData = erp.getAppData('user');
            var pageVm = avalon.define(pageName, function (vm) {
                vm.pageData = [
                    {
                        "id" : 0,
                        "leaderImage" : "",
                        "leaderName" : "",
                        "teamMember" : "",
                        "teamOdds" : "",
                        "salesDegrees" : "",
                        "supporters" : [],
                        "lold" : ""

                    }
                ];
                vm.loldStatus = function(index){
                    var status = this.$vmodel.$model.el.lold;
                    if(status == 'hide'){
                        vm.pageData[index].lold = 'show';
                    }else if(status == 'show'){
                        vm.pageData[index].lold = 'hide';
                    }
                };
                vm.$yearOpts = {
                    "selectId" : getTypeId('yearId'),
                    "options": [
                        {text:'2014年',value:2014}
                    ],
                    "onSelect":function(){}
                };
                var firstDate=new Date;
                var firstMonth=firstDate.getMonth();
                vm.$monthOpts = {
                    "selectId" : getTypeId('monthId'),
                    "options": [
                        {text:'1月',value:1},
                        {text:'2月',value:2},
                        {text:'3月',value:3},
                        {text:'4月',value:4},
                        {text:'5月',value:5},
                        {text:'6月',value:6},
                        {text:'7月',value:7},
                        {text:'8月',value:8},
                        {text:'9月',value:9},
                        {text:'10月',value:10},
                        {text:'11月',value:11},
                        {text:'12月',value:12}
                    ],
                    "onSelect":function(){
                        var yearVm = avalon.getVm(getTypeId('yearId'));
                        var monthVm = avalon.getVm(getTypeId('monthId'));
                        var date = yearVm.selectedValue+'-'+monthVm.selectedValue;
                        //var newDate = new Date(date);
                        var newDate=moment(date, 'yyyy-M')/1;
                        updateList({"isFromAssiant":true,"date":newDate},"mktTeam/list.do",wldhCallBack);
                    },
                    "selectedIndex": firstMonth
                };
            });
            avalon.scan(pageEl[0]);
            /*页面初始化请求渲染*/
            function updateList(obj,url,callBack){//其他渲染方法
                util.c2s({
                    "url": erp.BASE_PATH + url,
                    "type": "post",
                    "data": obj,
                    "success": callBack
                });
            }
            /*页面初始化请求渲染-end*/
            /*商户回调*/
            var date = new Date();
            updateList({"isFromAssiant":true,"date":date.getTime()},"mktTeam/list.do",wldhCallBack);
            function wldhCallBack(responseData){
                var data;
                if (responseData.flag) {
                    data = responseData.data;
                    pageVm.pageData = [];
                    for(var i=0;i<data.length;i++){
                        var tmpObj={};
                        var tmpArr = [];
                        tmpObj.id = data[i]['id'];
                        tmpObj.leaderImage = data[i]['leaderImage'];
                        tmpObj.leaderName = data[i]['leaderName'];
                        tmpObj.teamMember = data[i]['teamMember'];
                        tmpObj.teamOdds = data[i]['teamOdds'];
                        tmpObj.salesDegrees = data[i]['salesDegrees'];
                        tmpObj.lold = "hide";
                        tmpObj.supporters = [];
                        forArr = data[i]['supportList'];
                        var count = 0;
                        for(var j=0;j<forArr.length;j++){
                            count++;
                            tmpArr.push(forArr[j]['userName']);
                            if(count>=18){
                                count = 0;
                                tmpObj.supporters.push(tmpArr);
                                tmpArr = [];
                            }
                        }
                        if(tmpArr.length){
                            tmpObj.supporters.push(tmpArr);
                        }
                        pageVm.pageData.push(tmpObj);
                    }
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
