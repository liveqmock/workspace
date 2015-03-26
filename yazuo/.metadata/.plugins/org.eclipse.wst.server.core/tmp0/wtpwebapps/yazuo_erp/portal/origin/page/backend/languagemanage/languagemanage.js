/**
 * 话术管理-list
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
                    add_tel_skills_145 : util.hasPermission("add_tel_skills_145"),//添加话术
                    modify_146 : util.hasPermission("modify_146"),//修改话术
                    delete_147 : util.hasPermission("delete_147")//删除话术
                };
                vm.localHref = function (url) {//页面跳转
                    util.jumpPage(url);
                };
                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.path = erp.BASE_PATH;//路径
                vm.content = '';//查看一项内容
                vm.pageDataRows = [];//页面全部内容
                vm.$typeSelectOpts = {//类型
                    "selectId": getTypeId('-type'),
                    "options": [{value :'',text : '全部类型'}],
                    "onSelect" : function(){}
                };
                /*vm.test = [{isSelected:'1',name:'测试'},{isSelected:'0',name:'测试2'},{isSelected:'0',name:'测试3'},{isSelected:'0',name:'测试4'},{isSelected:'0',name:'测试5'}];
                vm.testFn = function(){
                    var isSelected = this.$vmodel.el.isSelected;
                    if(isSelected == '1'){
                        this.$vmodel.el.isSelected = '0'
                        if(this.$vmodel.el.name == '测试'){
                            alert(1)
                            for(var i=1;i<vm.test.length;i++){
                                vm.test[i].isSelected = '0';
                            }
                        }
                    }else{
                        this.$vmodel.el.isSelected = '1'
                    }
                };*/
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
                            '共<span class="num">{{gridTotalSize}}</span>条话术' +
                            '</div>'+
                            '<div class="ui-grid-search fn-right">' +
                            '<span class="schedule" ms-widget="select,$,$typeSelectOpts"></span>' +
                            '<input type="text" class="input-text" placeholder="标题" ms-duplex="keyword">' +
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
                            "text": "创建日期",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return "<div class='language-time'>"+ moment(rowData["insertTime"]).format('YYYY-MM-DD HH:mm')+"</div>";
                            }
                        },
                        {
                            "dataIndex": "title",
                            "text": "标题",
                            "html": true,
                            "renderer": function(v,rowData){
                                return "<div class='language-title'>"+rowData['title']+"</div>";
                            }
                        },
                        {
                            "dataIndex": "content",
                            "text": "内容",
                            "html": true,
                            "renderer": function(v,rowData){
                                var text = $(rowData['content']).text()||rowData['content'];
                                return "<div class='language-con'>"+text+"</div>";
                            }
                        },
                        {
                            "dataIndex": "amount",
                            "text": "类型",
                            "html": true,
                            "renderer": function(v,rowData){
                                if(rowData['secondResource']){
                                    return "<div class='language-type'>"+rowData['firstResource']+'('+rowData['secondResource']+')'+"</div>";
                                }else{
                                    return "<div class='language-type'>"+rowData['firstResource']+"</div>";
                                }
                            }
                        },
                        {
                            "dataIndex": "isOpen",
                            "text": "操作",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<div class="choose">' +
                                    '<a href="javascript:;" data-id='+rowData["id"]+' ms-click="readCon">查看</a>' +
                                    '<a ms-if="permission.modify_146" href="javascript:;" data-id='+rowData["id"]+' ms-click="openEdit">修改</a>' +
                                    '<a ms-if="permission.delete_147" href="javascript:;" data-id='+rowData["id"]+' ms-click="removeOne">删除</a>' +
                                    '<a ms-if="!permission.delete_147 && !permission.modify_146" href="javascript:;">无操作权限</a>' +
                                    '</div>';
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        requestData = _.extend(requestData, {
                            "title": _.str.trim(vm.keyword),// 搜索关键字
                            "resourceRemark": avalon.getVm(getTypeId('-type')).selectedValue//类型
                        });
                        util.c2s({
                            "url": erp.BASE_PATH + "besTalkingSkills/listBesTalkingSkills.do",
                            "contentType": 'application/json',
                            "data": JSON.stringify(requestData),
                            "success": function (responseData) {
                                var data;
                                var select = avalon.getVm(getTypeId("-type"));
                                var selectIndex = 0;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    var val =_.map(data.parameResources, function (itemData) {
                                        return {
                                            "text": itemData["resourceName"],
                                            "value": itemData["remark"]
                                        };
                                    });
                                    val.unshift({value :'',text : '全部类型'});
                                    for(var i=0;i<data.parameResources.length;i++){
                                        if(data.parameResources[i].isSelected === '1'){
                                            selectIndex = i+1;
                                        }
                                    }
                                    select.setOptions(val,selectIndex);
                                    vm.pageDataRows = data.rows;
                                    callback.call(this, data);
                                }
                            }
                        });
                    },
                    "search": function(){
                        avalon.getVm(getTypeId('-grid')).load();
                    },
                    "removeOne": function(){
                        var id = $(this).attr('data-id');
                        util.confirm({
                            "content" : '你确定要删除选中的信息吗？',
                            "onSubmit": function(){
                                util.c2s({
                                    "url": erp.BASE_PATH + "besTalkingSkills/deleteBesTalkingSkills/"+id+".do",
                                    //"contentType": 'application/json',
                                    //"data": JSON.stringify({id:id,isOpen:val}),
                                    "success": function (responseData) {
                                        if (responseData.flag) {
                                            avalon.getVm(getTypeId('-grid')).load();
                                        }
                                    }
                                });
                            }
                        });
                    },
                    "openEdit": function(){
                        var id = $(this).attr('data-id');
                        util.jumpPage("#/backend/languagemanage/addedit/0/"+id);
                    },
                    "readCon": function(){
                        var con = '';
                        var id = $(this).attr('data-id');
                        _.map(vm.pageDataRows,function(item,index){
                            if(item['id'] == id){
                                con = item['content'];
                                return;
                            }
                        });
                        vm.content = con;
                        var dialogVm = avalon.getVm(getTypeId('-languageId'));
                        dialogVm.open();
                    }
                };
                /*grid-end*/
                vm.$conDialogOpts = {
                    "title": "内容",
                    "dialogId": getTypeId('-languageId'),
                    "width": 620,
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                            footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                '<div class="ui-dialog-btns">' +
                                '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">关&nbsp;闭</button>' +
                                '</div>' +
                                '</div>';
                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "onOpen": function () {},
                    "onClose": function () {},
                    "submit": function (evt) {}
                };
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


