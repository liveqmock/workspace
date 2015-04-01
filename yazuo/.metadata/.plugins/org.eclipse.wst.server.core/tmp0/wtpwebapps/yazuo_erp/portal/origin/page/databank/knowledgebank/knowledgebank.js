/**
 * 知识库-list
 */
define(['avalon', 'util', 'json', 'moment', '../../../widget/pagination/pagination','../../../widget/form/select', '../../../widget/grid/grid','../../../widget/calendar/calendar', '../../../widget/tree/tree'], function (avalon, util, JSON, moment) {
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
                    knowledge_kind_add_185: util.hasPermission('knowledge_kind_add_185'),//添加
                    knowledge_kind_update_186: util.hasPermission('knowledge_kind_update_186'),//修改
                    knowledge_kind_delete_187:util.hasPermission('knowledge_kind_delete_187')//删除"
                };
                //页面应用逻辑变量
                vm.title = '';//标题
                vm.username = '';//作者
                vm.content = '';//内容
                vm.beginUpdatedTime = '';//开始时间
                vm.endUpdatedTime = ''; //结束时间
                vm.initRequest = true;
                vm.kindId = 0;//分类ID
                vm.kindText = '';//分类标题
                vm.treeData = [];//树数据
                //页面应用逻辑变量-end
                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.path=erp.BASE_PATH;
                vm.localAddEdit = function(){//进入创建页面
                    util.jumpPage('#/databank/knowledgebank/addedit/add/add/'+vm.kindId);
                };


                vm.$gridOpts = {
                    "gridId": getTypeId('-grid'),
                    "disableCheckAll": true, //是否禁用全选
                    "disableCheckbox": true, //是否禁用选择框，默认不禁用
                    "getTemplate": function (tmpl) {
                        var tmpHade = tmpl.split('MS_OPTION_TBAR');
                        var tmpMain = tmpHade[1].split('MS_OPTION_MAIN')[0];
                        var newFooter = '<div class="ui-grid-bbar fn-clear">' +
                            '<div class="grid-action fn-left" ms-if="permission.abandon_172">' +
                            '</div>' +
                            '<div class="pagination fn-right" ms-widget="pagination,$,$paginationOpts"></div>' +
                            '</div>';
                        return '<div class="ui-grid-search"></div>MS_OPTION_TBAR' + tmpMain + newFooter;
                    },
                    "columns": [
                        {
                            "dataIndex": "id",
                            "text": "ID"
                        },
                        {
                            "dataIndex": "title",
                            "text": "标题",
                            "html": true,
                            "renderer" : function(v, rowData){
                                var id = rowData['id'];
                                return "<div class='grid-title-width'><a href='javascript:;' ms-data-id="+id+" ms-click='localInfo'>"+rowData["title"]+"</a></div>";
                            }
                        },
                        {
                            "dataIndex": "kindCn",
                            "text": "分类",
                            "html": true,
                            "renderer" : function(v, rowData){
                                return "<div class='grid-class-width'>"+rowData["kindCn"]+"</div>";
                            }
                        },
                        {
                            "dataIndex": "description",
                            "text": "描述",
                            "html": true,
                            "renderer" : function(v, rowData){
                                return "<div class='grid-con-width'>"+rowData["description"]+"</div>";
                            }
                        },
                        {
                            "dataIndex": "updatedTime",
                            "text": "更新时间",
                            "html": true,
                            "renderer" : function(v, rowData){
                                return "<div>"+moment(rowData["updatedTime"]).format('YYYY/MM/DD')+"</div>";
                            }
                        },
                        {
                            "dataIndex": "username",
                            "text": "作者"
                        },
                        {
                            "dataIndex": "select",
                            "text": "操作",
                            "html": true,
                            "renderer" : function(v, rowData){
                                var id = rowData['id'];
                                return "<div class='grid-select-width'><a href='#/databank/knowledgebank/addedit/edit/"+id+"' ms-if='permission.knowledge_kind_update_186'>编辑</a>&nbsp;&nbsp;<a href='javascript:;' ms-if='permission.knowledge_kind_delete_187' ms-data-id="+id+" ms-click=delKnowledge>删除</a></div>";
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        if(pageVm.initRequest){//初始化
                            requestData = _.extend(requestData, {});
                        }else if(!pageVm.initRequest && pageVm.kindId){//点击分类
                            requestData = _.extend(requestData, {
                                "title": _.str.trim(pageVm.title),
                                "username" : _.str.trim(pageVm.username),
                                "content" : _.str.trim(pageVm.content),
                                "beginUpdatedTime": moment(pageVm.beginUpdatedTime)/1, //开始时间
                                "endUpdatedTime": (moment(pageVm.endUpdatedTime)/1)-1, //结束时间
                                "kindId": pageVm.kindId
                            });
                        }else if(!pageVm.initRequest && !pageVm.kindId){//点击搜索
                            requestData = _.extend(requestData, {
                                "title": _.str.trim(pageVm.title),
                                "username" : _.str.trim(pageVm.username),
                                "content" : _.str.trim(pageVm.content),
                                "beginUpdatedTime": moment(pageVm.beginUpdatedTime)/1, //开始时间
                                "endUpdatedTime": (moment(pageVm.endUpdatedTime)/1)-1 //结束时间
                            });
                        }
                        if(pageVm.initRequest){//初始化
                            util.c2s({
                                "url": erp.BASE_PATH + "/knowledge/getAll.do",
                                "data": JSON.stringify(requestData),
                                "contentType" : 'application/json',
                                "success": function (responseData) {
                                    var data;
                                    if (responseData.flag) {
                                        pageVm.initRequest = false;
                                        data = responseData.data;
                                        callback.call(this, data.resultSet);
                                        var treeVm = avalon.getVm(getTypeId('-classZtreeId'));//分类树
                                        pageVm.treeData = pageVm.eachArr(data['allKnowledgeKinds']);
                                        treeVm.updateTree(pageVm.treeData,true);//分类树
                                    }
                                }
                            });
                        }else if(!pageVm.initRequest){
                            util.c2s({
                                "url": erp.BASE_PATH + "/knowledge/search.do",
                                "data": JSON.stringify(requestData),
                                "contentType" : 'application/json',
                                "success": function (responseData) {
                                    var data;
                                    if (responseData.flag) {
                                        pageVm.initRequest = false;
                                        data = responseData.data;
                                        callback.call(this, data.resultSet);
                                    }
                                }
                            });
                        }

                    }
                };
                vm.search = function(){
                    avalon.getVm(getTypeId('-grid')).load();
                };
                //跳转到详情页
                vm.localInfo = function(){
                    var id = $(this).attr('data-id');
                    util.jumpPage('#/databank/knowledgebank/info/'+id);
                };
                //日期选择
                vm.$startDateOpts = {
                    "calendarId": getTypeId('startDateCalendarId'),
                    "onClickDate": function (d) {
                        pageVm.beginUpdatedTime = moment(d).format('YYYY-MM-DD');
                        $(this.widgetElement).hide();
                    }
                };
                vm.openStartDateCalendar = function () {
                    var meEl = $(this),
                        calendarVm = avalon.getVm(getTypeId('startDateCalendarId')),
                        calendarEl,
                        inputOffset = meEl.offset();
                    if (!calendarVm) {
                        calendarEl = $('<div ms-widget="calendar,$,$startDateOpts"></div>');
                        calendarEl.css({
                            "position": "absolute",
                            "z-index": 10000
                        }).hide().appendTo('body');
                        avalon.scan(calendarEl[0], [vm]);
                        calendarVm = avalon.getVm(getTypeId('startDateCalendarId'));
                        //注册全局点击绑定
                        util.regGlobalMdExcept({
                            "element": calendarEl,
                            "handler": function () {
                                calendarEl.hide();
                            }
                        });
                    } else {
                        calendarEl = $(calendarVm.widgetElement);
                    }
                    //设置focus Date
                    if (pageVm.beginUpdatedTime) {
                        calendarVm.focusDate = moment(pageVm.beginUpdatedTime, 'YYYY-MM-DD')._d;
                    } else {
                        calendarVm.focusDate = new Date();
                    }

                    if (pageVm.endUpdatedTime) {
                        calendarVm.maxDate = moment(pageVm.endUpdatedTime, 'YYYY-MM-DD')._d;
                    }else{
                        calendarVm.maxDate = moment()._d;
                    }
                    calendarEl.css({
                        "top": inputOffset.top + meEl.outerHeight() - 1,
                        "left": inputOffset.left
                    }).show();
                };
                vm.$endDateOpts = {
                    "calendarId": getTypeId('endDateCalendarId'),
                    "onClickDate": function (d) {
                        pageVm.endUpdatedTime = moment(d).format('YYYY-MM-DD');
                        $(this.widgetElement).hide();
                    }
                };
                vm.openEndDateCalendar = function () {
                    var meEl = $(this),
                        calendarVm = avalon.getVm(getTypeId('endDateCalendarId')),
                        calendarEl,
                        inputOffset = meEl.offset();
                    if (!calendarVm) {
                        calendarEl = $('<div ms-widget="calendar,$,$endDateOpts"></div>');
                        calendarEl.css({
                            "position": "absolute",
                            "z-index": 10000
                        }).hide().appendTo('body');
                        avalon.scan(calendarEl[0], [vm]);
                        calendarVm = avalon.getVm(getTypeId('endDateCalendarId'));
                        //注册全局点击绑定
                        util.regGlobalMdExcept({
                            "element": calendarEl,
                            "handler": function () {
                                calendarEl.hide();
                            }
                        });
                    } else {
                        calendarEl = $(calendarVm.widgetElement);
                    }
                    //设置focus Date
                    if (pageVm.endUpdatedTime) {
                        calendarVm.focusDate = moment(pageVm.endUpdatedTime, 'YYYY-MM-DD')._d;
                    } else {
                        calendarVm.focusDate = new Date();
                    }

                    if (pageVm.beginUpdatedTime) {
                        calendarVm.minDate = moment(pageVm.beginUpdatedTime, 'YYYY-MM-DD')._d;
                        calendarVm.maxDate = moment()._d;
                    }else{
                        calendarVm.maxDate = moment()._d;
                    }
                    calendarEl.css({
                        "top": inputOffset.top + meEl.outerHeight() - 1,
                        "left": inputOffset.left
                    }).show();
                };
                //日期选择-end
                //分类树
                vm.$ztreeOpts = {
                    "treeId": getTypeId('-classZtreeId'),
                    "ztreeOptions": {
                        "setting": {
                            "check": {
                                "enable": false,
                                "chkboxType": {
                                    "Y": "s",
                                    "N": "s"
                                }
                            },
                            "callback": {
                                "onClick": function (evt, ztreeId, node) {//点击节点触发
                                    //if(!node.children){
                                        pageVm.kindId = node.id;
                                        avalon.getVm(getTypeId('-grid')).load();
                                    //}
                                }
                            },
                            "data": {
                                "simpleData": {
                                    "enable": true
                                }
                            }
                        },
                        "treeData": []
                    }
                };
                vm.eachArr =function (arr){
                    var arr = _.map(arr, function (item) {
                        return {
                            id: item.id,
                            name: item.title,
                            pId: item.parentId == ''?0:item.parentId
                        };
                    });
                    return arr;
                };
                vm.searchKind = function(){//搜索分类
                    util.c2s({
                        "url": erp.BASE_PATH + "/knowledgeKind/search.do",
                        "data": JSON.stringify({title:pageVm.kindText}),
                        "contentType" : 'application/json',
                        "success": function (responseData) {
                            var data;
                            if (responseData.flag) {
                                data = responseData.data;
                                var treeVm = avalon.getVm(getTypeId('-classZtreeId'));//分类树
                                pageVm.treeData = pageVm.eachArr(data);//分类树
                                treeVm.updateTree(pageVm.treeData,true);//分类树
                            }
                        }
                    });
                };
                vm.delKnowledge = function(){
                    var id = $(this).attr('data-id');
                    util.confirm({
                        "content": "你确定要删除吗？",
                        "onSubmit": function () {
                            util.c2s({
                                "url": erp.BASE_PATH + "/knowledge/delete.do",
                                "data": JSON.stringify({id:id}),
                                "contentType" : 'application/json',
                                "success": function (responseData) {
                                    var data;
                                    if (responseData.flag) {
                                        avalon.getVm(getTypeId('-grid')).load();
                                    }
                                }
                            });
                        }
                    });
                }
            });
            //拖拽
            var oDragEle = $('.dragEle');
            oDragEle.mousedown(function(e){
                e = e || window.event;
                var boxLeft = this.offsetLeft;//拖动前盒子离父元素的宽度
                var startX = e.clientX;//鼠标的原始坐标
                var moveSpeed = 0;//拖动的距离
                var leftWidth = $('.page-knowledgebank-left').width();//左侧的原始宽度
                var rightWidth = $('.page-knowledgebank-right').width();//右侧的原始宽度
                var isDrag = true;
                document.onmousemove = function(e){
                    if(!isDrag){return;}
                    e = e || window.event;
                    var eventX = e.clientX;
                    moveSpeed = eventX+boxLeft-startX;
                    oDragEle.css({left:moveSpeed});
                    if(moveSpeed<0){
                        moveSpeed = 0;
                        oDragEle.css({left:moveSpeed});
                    }
                    $('.page-knowledgebank-left').width(leftWidth+moveSpeed-boxLeft);
                    $('.page-knowledgebank-right').width(rightWidth-moveSpeed+boxLeft);
                };
                document.onmouseup = function(){
                    isDrag = false;
                }

            });
            //拖拽-end

            avalon.scan(pageEl[0]);
            /*页面初始化请求渲染
            (function updateList(obj,url,callBack){
                var requestData=JSON.stringify({
                    pageSize: 10,
                    pageNumber: 1});
                util.c2s({
                    "url": erp.BASE_PATH + "/knowledge/getAll.do",
                    "type": "post",
                    "contentType" : 'application/json',
                    "data": requestData,
                    "success": function(responseData){
                        var data;
                        if(responseData.flag){
                            var data = responseData.data;
                            console.log(data)
                        }
                    }
                });
            }());
            页面初始化请求渲染end*/
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
            avalon.getVm('treeBox') && $(avalon.getVm('treeBox').widgetElement).remove();
            delete avalon['vmodels']['treeBox'];
        }
    });
    return pageMod;
});


