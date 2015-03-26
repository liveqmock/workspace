/**
 * 创建需求-
 */
define(['avalon', 'util', 'json', 'moment','../../../../widget/form/select','../../../../widget/uploader/uploader', '../../../../widget/calendar/calendar', '../../../../widget/autocomplete/autocomplete'], function (avalon, util, JSON, moment) {
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
                    "text":"我的主页",
                    "href":"#/backend/home"
                }, {
                    "text":'创建需求'
                }];
                var loginUserData = erp.getAppData('user');
                /*vm.titleType = '创建运营信息';
                vm.pageType = parseInt(routeData.params["type"]);
                vm.fileId = parseInt(routeData.params["id"]);
                if(!vm.pageType){
                    vm.titleType = '修改运营信息';
                    vm.navCrumbs[1].text = '修改运营信息';
                }*/

                /*设置联动select方法*/
                vm.setSelect = function (arr,name){
                    var newArr = _.filter(arr,function(item,index){
                        return item.isSelected;
                    });
                    avalon.getVm(getTypeId(name)).setOptions(arr);
                    if(newArr.length){
                        avalon.getVm(getTypeId(name)).selectValue(newArr[0].value,true);
                    }
                };
                /*设置联动select方法-end*/
                /*test-start*/
                vm.obj = {
                    contact: [
                        {
                            isSelected: 1,
                            text: "前端",
                            value: "marketing_act_148",
                            children: [
                                {text: "张三", isSelected: 1, value: "weixin_159", mobile: "111123456",children:[]},
                                {text: "李四", isSelected: 0, value: "others_160", mobile: "222123123", children:[]}
                            ]
                        },
                        {
                            isSelected: 0,
                            text: "商户",
                            value: "marketing_act_148",
                            children: [
                                {text: "王五", isSelected: 0, value: "weixin_159", mobile: "333123456", children:[]},
                                {text: "赵六", isSelected: 0, value: "others_160", mobile: "444123456", children:[]}
                            ]
                        }
                    ],
                    source: [
                        {
                            isSelected: 1,
                            text: "商户",
                            value: "marketing_act_148",
                            children: [
                                {text: "400", isSelected: 0, value: "weixin_159", children:[]},
                                {text: "其他", isSelected: 0, value: "others_160", children:[]}
                            ]
                        },
                        {
                            isSelected: 0,
                            text: "营销活动",
                            value: "marketing_act_148",
                            children: [
                                {text: "微信", isSelected: 0, value: "weixin_159", children:[]},
                                {text: "其他", isSelected: 0, value: "others_160", children:[]}
                            ]
                        }
                    ],
                    type: [
                        {
                            isSelected: 0,
                            text: "商户",
                            value: "marketing_act_148",
                            children: [
                                {text: "微信", isSelected: 0, value: "weixin_159", children:[]},
                                {text: "其他", isSelected: 0, value: "others_160", children:[]}
                            ]
                        },
                        {
                            isSelected: 0,
                            text: "营销活动",
                            value: "marketing_act_148",
                            children: [
                                {text: "微信", isSelected: 0, value: "weixin_159", children:[]},
                                {text: "其他", isSelected: 0, value: "others_160", children:[]}
                            ]
                        }
                    ]
                }
                /*test-end*/
                vm.$contactSelectOpts = {//联系人类型
                    "selectId" : getTypeId('-contactSelect1'),
                    "options" : [],
                    "onSelect" : function(v,t,o){
                        //vm.requestData.resourceRemark = v;
                        if(o.children.length){
                            //vm.type2 = 1;
                            vm.setSelect(o.children,'-contactSelect2');
                        }else{
                            //vm.type2 = 0;
                            //avalon.getVm(getTypeId('-type2')).setOptions([]);
                            //vm.requestData.resourceExtraRemark = '';
                        }
                    }
                };
                vm.$contactSelectOpts2 = {//联系人姓名
                    "selectId" : getTypeId('-contactSelect2'),
                    "options" : [],
                    "onSelect" : function(v,t,o){
                        //vm.requestData.resourceRemark = v;
                        if(o.children.length){
                            //vm.type2 = 1;
                            //vm.setSelect(o.children,'-type2');
                        }else{
                            //vm.type2 = 0;
                            //avalon.getVm(getTypeId('-type2')).setOptions([]);
                            //vm.requestData.resourceExtraRemark = '';
                        }
                    }
                };
                vm.$sourceSelectOpts = {//来源类型
                    "selectId" : getTypeId('-sourceSelect1'),
                    "options" : [],
                    "onSelect" : function(v,t,o){
                        //vm.requestData.resourceRemark = v;
                        if(o.children.length){
                            //vm.type2 = 1;
                            vm.setSelect(o.children,'-sourceSelect2');
                        }else{
                            //vm.type2 = 0;
                            //avalon.getVm(getTypeId('-type2')).setOptions([]);
                            //vm.requestData.resourceExtraRemark = '';
                        }
                    }
                };
                vm.$sourceSelectOpts2 = {//来源内容
                    "selectId" : getTypeId('-sourceSelect2'),
                    "options" : [],
                    "onSelect" : function(v,t,o){
                        //vm.requestData.resourceRemark = v;
                        if(o.children.length){
                            //vm.type2 = 1;
                            vm.setSelect(o.children,'-contactSelect2');
                        }else{
                            //vm.type2 = 0;
                            //avalon.getVm(getTypeId('-type2')).setOptions([]);
                            //vm.requestData.resourceExtraRemark = '';
                        }
                    }
                };
                vm.$typeSelectOpts1 = {//类型1
                    "selectId" : getTypeId('-typeSelect1'),
                    "options" : [],
                    "onSelect" : function(v,t,o){
                        //vm.requestData.resourceRemark = v;
                        if(o.children.length){
                            //vm.type2 = 1;
                            vm.setSelect(o.children,'-typeSelect2');
                        }else{
                            //vm.type2 = 0;
                            //avalon.getVm(getTypeId('-type2')).setOptions([]);
                            //vm.requestData.resourceExtraRemark = '';
                        }
                    }
                };
                vm.$typeSelectOpts2 = {//类型2
                    "selectId" : getTypeId('-typeSelect2'),
                    "options" : [],
                    "onSelect" : function(v,t,o){
                        //vm.requestData.resourceRemark = v;
                        if(o.children.length){
                            //vm.type2 = 1;
                            //vm.setSelect(o.children,'-typeSelect2');
                        }else{
                            //vm.type2 = 0;
                            //avalon.getVm(getTypeId('-type2')).setOptions([]);
                            //vm.requestData.resourceExtraRemark = '';
                        }
                    }
                };
                vm.saveData = function(){//提交

                };

            });
            avalon.scan(pageEl[0]);
            /*页面初始化请求渲染*/
            pageVm.setSelect(pageVm.obj.contact,'-contactSelect1');
            pageVm.setSelect(pageVm.obj.source,'-sourceSelect1');
            pageVm.setSelect(pageVm.obj.type,'-typeSelect1');
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


