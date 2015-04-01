/**
 * 资料库-list
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
                    "create_oper_info" : util.hasPermission("create_oper_info"),//创建
                    "modify_oper_info" : util.hasPermission("modify_oper_info"),//修改
                    "user_req_items" : util.hasPermission("user_req_items"),//详情
                    "delete_oper_info" : util.hasPermission("delete_oper_info")//删除
                };

                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.path = erp.BASE_PATH;//路径
                vm.localHref = function (url) {//页面跳转
                    util.jumpPage(url);
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
                            '共<span class="num">{{gridTotalSize}}</span>条推广信息' +
                            '</div>'+
                            '<div class="ui-grid-search fn-right">' +
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
                                return moment(rowData["insertTime"]).format('YYYY-MM-DD');
                            }
                        },
                        {
                            "dataIndex": "title",
                            "text": "营销标题",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<a href="javascript:;" ms-data-id ='+rowData["id"]+' ms-click="popCon">'+rowData["title"]+'</a>'
                            }
                        },
                        {
                            "dataIndex": "time_limit",
                            "text": "推广平台",
                            "html": true,
                            "renderer": function (v, rowData) {
                                var arr = rowData['dicPlatforms'];
                                var str = '';
                                for(var i=0;i<arr.length;i++){
                                    str +=!!str ? '、'+arr[i]['dictionaryValue']:arr[i]['dictionaryValue'];
                                }
                                return str;
                            }
                        },
                        {
                            "dataIndex": "amount",
                            "text": "需求量",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<a href="javascript:;" ms-data-id ='+rowData["id"]+' ms-click="jump">'+rowData["amount"]+'</a>'
                            }
                        },
                        {
                            "dataIndex": "beginTime",
                            "text": "推广开始",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return moment(rowData["beginTime"]).format('YYYY-MM-DD');
                            }
                        },
                        {
                            "dataIndex": "endTime",
                            "text": "推广截止",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return moment(rowData["endTime"]).format('YYYY-MM-DD');
                            }
                        },
                        {
                            "dataIndex": "isOpen",
                            "text": "操作",
                            "html": true,
                            "renderer": function (v, rowData) {
                                var isOpen = parseInt(rowData["isOpen"]);
                                if(isOpen){
                                    return '<div class="choose">' +
                                        '<span ms-if="permission.modify_oper_info"  class="stop" data-id='+rowData["id"]+' data-openType="0" ms-click="setOpen"> 停用</span>' +
                                        '<a ms-if="permission.modify_oper_info" href="javascript:;" data-id='+rowData["id"]+' ms-click="openEdit">修改</a>' +
                                        '<a ms-if="permission.delete_oper_info" href="javascript:;" data-id='+rowData["id"]+' ms-click="removeOne">删除</a>' +
                                        '</div>';
                                }else{
                                    return '<div class="choose">' +
                                        '<span ms-if="permission.modify_oper_info"  class="start" data-id='+rowData["id"]+' data-openType="1" ms-click="setOpen"> 启用</span>' +
                                        '<a ms-if="permission.modify_oper_info" href="javascript:;" data-id='+rowData["id"]+' ms-click="openEdit">修改</a>' +
                                        '<a ms-if="permission.delete_oper_info" href="javascript:;" data-id='+rowData["id"]+' ms-click="removeOne">删除</a>' +
                                        '</div>';
                                }
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        requestData = _.extend(requestData, {
                            "title": _.str.trim(vm.keyword)  // 搜索关键字
                        });
                        util.c2s({
                            "url": erp.BASE_PATH + "sysProductOperation/listSysProductOperations.do",
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
                        avalon.getVm(getTypeId('-grid')).load();
                    },
                    "jump": function(){
                        var id=$(this).attr("data-id");
                        if(pageVm.permission.user_req_items){
                            util.jumpPage('#/productoperad/list/info/'+id);
                        }else{
                            util.alert({
                                "content": "您没有查看详情的权限！",
                                "iconType": "info"
                            });
                        }

                    },
                    "setOpen": function(){
                        var id = $(this).attr('data-id');
                        var val = $(this).attr('data-openType');
                        var gridVm = avalon.getVm(getTypeId('-grid'));
                        gridVm.selectById(id);
                        var selectedGridData = gridVm.getSelectedData()[0];
                        util.c2s({
                            "url": erp.BASE_PATH + "sysProductOperation/saveSysProductOperation.do",
                            "contentType": 'application/json',
                            "data": JSON.stringify({id:id,isOpen:val}),
                            "success": function (responseData) {
                                if (responseData.flag) {
                                    gridVm.updateData({ //注意updateData参数应该是个全新的数据结构，如果指定id，则更新对应的record，未指定的话认为是新增的数据
                                        "id": selectedGridData.id,
                                        "isOpen": (selectedGridData.isOpen == "1" ? "0" : "1")
                                    });
                                    gridVm.unselectById(selectedGridData.id);  
                                }
                            }
                        });
                    },
                    "popCon": function(){
                        var id = $(this).attr('data-id');
                        util.c2s({
                            "url": erp.BASE_PATH + "sysProductOperation/getSysProductOperation.do",
                            "contentType": 'application/json',
                            "data": JSON.stringify({id:id}),
                            "success": function (responseData) {
                                if (responseData.flag) {
                                    var data = responseData.data;
                                    vm.popCon = decodeURIComponent(data.content);
                                    avalon.getVm(getTypeId('popDialogId')).title = data.title;
                                    avalon.getVm(getTypeId('popDialogId')).open();
                                }
                            }
                        });
                    },
                    "removeOne": function(){
                        var id = $(this).attr('data-id');
                        util.confirm({
                            "content" : '你确定要删除选中的信息吗？',

                            "onSubmit": function(){
                                util.c2s({
                                    "url": erp.BASE_PATH + "sysProductOperation/deleteSysProductOperation/"+id+".do",
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
                        util.jumpPage("#/productoperad/list/addedit/0/"+id);
                    }
                };
                /*grid-end*/
                //查看新功能发布
                vm.popCon = '';
                vm.$popConOpts = {
                    "title": '雅座ERP【工作管理】新功能发布',
                    "dialogId": getTypeId('popDialogId'),
                    "width": '600px',
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                            footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                '<div class="ui-dialog-btns">' +
                                /*'<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">确&nbsp;定</button>' +
                                '<span class="separation"></span>' +
                                '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button>' +*/
                                '</div>' +
                                '</div>';
                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "onClose": function () {
                    },
                    "submit": function (evt) {
                        avalon.getVm(getTypeId('popDialogId')).close();

                    }
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


