/**
 * Created by ZHANG on 2014/8/7.
 */
/**
 * 前端服务-问题管理
 */
define(['avalon', 'util', '../../../../widget/pagination/pagination','../../../../widget/form/select', '../../../../widget/pagination/pagination', '../../../../widget/dialog/dialog', '../../../../widget/form/form', '../../../../module/addquestion/addquestion'], function (avalon, util) {
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
                    "href":''
                }, {
                    "text":"问题管理"
                }];

                vm.permission = {//权限
                    "fes_assign_persion" : util.hasPermission("fes_assign_persion"),//指派处理人
                    "fes_deal_with_qst" : util.hasPermission("fes_deal_with_qst"),//回复
                    "fes_inform_custome" : util.hasPermission("fes_inform_custome"),//处理完成
                    "fes_redo" : util.hasPermission("fes_redo"),//不予处理
                    "tell_customer_167" : util.hasPermission("tell_customer_167"),//告知客户
                    "activate_168" : util.hasPermission("activate_168")//激活问题
                };

                //是否显示面包屑
                vm.showNav = true;
                var phref = '';
                switch (routeData.route) {
                    case "/sales/home/questionmanage":
                        phref = "#/sales/home";
                        break;
                    case "/frontend/home/questionmanage":
                        phref = "#/frontend/home";
                        break;
                    case "/backend/questionmanage":
                        phref = "#/backend/customerservice";
                        break;
                    default :
                        vm.showNav = false;
                        break;
                }

                vm.navCrumbs[0].href= phref;
                vm.searchId = 0;//商户搜索id
                vm.url=erp.BASE_PATH;
                vm.merchantId = parseInt(routeData.params["id"]);
                vm.pageData={};
                vm.options1=[];
                vm.assignedArr=[];
                vm.processClass=0;
                vm.requestDataAssigned={//指派处理人数据
                    id : 0,
                    solveredBy : 0,
                    problemStatus : '2',
                    merchantId : '',
                    title : ''
                };
                vm.requestDataHuifu = {//回复
                    problemId : 0,
                    description : ''
                };
                vm.requestDataDispose={//处理问题数据
                    id : 0,
                    problemStatus : '3',/*3-5*/
                    remark : '',
                    problemType : '',
                    solvingType : '1'
                };



                vm.crequestDataNotice={//通知商户和重新激活
                    id : 0,
                    problemStatus : ''
                };
                vm.requestData={//查询数据
                    merchantId:vm.merchantId,
                    title:'',
                    merchantName:'',
                    pageNumber:1,
                    pageSize:10,
                    problemStatus:''
                };
                vm.search=function(){
                    updateList(vm.requestData.$model);
                };
                vm.change=function(index){
                    var paginationVm = avalon.getVm(getTypeId('paginationId'));
                    vm.requestData.problemStatus = index;
                    vm.requestData.pageSize = paginationVm.perPages;
                    vm.requestData.pageNumber = 1;
                    paginationVm.currentPage = 1;
                    updateList(vm.requestData.$model);

                };
                vm.$paginationOpts = {//分页
                    "paginationId": getTypeId('paginationId'),
                    "total": 0,
                    "onJump": function (evt, vm) {
                        var paginationVm = avalon.getVm(getTypeId('paginationId'));
                        pageVm.requestData.pageNumber=paginationVm.currentPage;
                        updateList(pageVm.requestData.$model);
                    }
                };
                /*添加问题*/
                vm.$addquestionOpts = {
                    "addquestionId" : getTypeId('-addquestionops'),
                    addType : 1,
                    merchantId : 0,
                    merchantName : '',
                    callFn : function(){
                        updateList(vm.requestData.$model);
                    }

                };
                vm.addQuestions = function(){
                    var dialogVm = avalon.getVm(getTypeId('-addquestionops'));
                    dialogVm.addType = 1;
                    dialogVm.merchantId = 0;
                    dialogVm.merchantName = '测试';
                    dialogVm.open();
                };
                /*添加问题结束*/


                /*指派处理人*/
                vm.assigned = function(id,merchantId,title){
                    vm.requestDataAssigned.id = id;
                    vm.requestDataAssigned.merchantId = merchantId;
                    vm.requestDataAssigned.title = title;
                    var requestData={remark:'fes_inform_custome'};
                    requestData=JSON.stringify(requestData);
                    util.c2s({
                        "url": erp.BASE_PATH + "user/getAllUsersByResourceCode.do",
                        "type": "post",
                        "contentType" : 'application/json',
                        "data": requestData,
                        "success": function (responseData) {
                            var data;
                            if (responseData.flag) {
                                data = responseData.data;
                                vm.assignedArr=data;
                            }
                        }
                    });
                    var dialogVm = avalon.getVm(getTypeId('assignedDialogId'));
                    dialogVm.title = '指派处理人';
                    dialogVm.open();
                };
                vm.assignedFn = function(id){
                    vm.processClass = id;
                    vm.requestDataAssigned.solveredBy = id;
                };
                vm.$assignedDialogOpts={
                    "dialogId": getTypeId('assignedDialogId'),
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                            footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                '<div class="ui-dialog-btns">' +
                                '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">确&nbsp;定</button>' +
                                '<span class="separation"></span>' +
                                '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button>' +
                                '</div>' +
                                '</div>';
                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "onClose": function () {
                        pageVm.requestDataAssigned.solveredBy = 0;
                        vm.processClass = 0;
                    },
                    "submit": function (evt) {
                        var solveredBy = pageVm.requestDataAssigned.$model.solveredBy;
                        if(!solveredBy){
                            return false;
                        }
                        var dialogVm=avalon.getVm(getTypeId('assignedDialogId'));
                        var requestData=JSON.stringify(pageVm.requestDataAssigned.$model);
                        util.c2s({
                            "url": erp.BASE_PATH + 'sysProblem/updateSysProblem.do',
                            "type": "post",
                            "contentType" : 'application/json',
                            "data": requestData,
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    dialogVm.close();
                                    updateList(vm.requestData.$model);
                                }
                            }
                        });
                    }
                };
                /*指派处理人结束*/
                /*回复&问题完成&不予处理*/
                vm.handerType = 1;//处理状态(1是回复，2是处理完成，3是不予处理)
                vm.dispose = function(dataId,n){
                    var dialogVm = avalon.getVm(getTypeId('disposeDialogId'));
                    vm.handerType = n;
                    if(n == 1){
                        dialogVm.title = '回复';
                        vm.requestDataHuifu.problemId = dataId;
                    }else if(n == 2){
                        dialogVm.title = '处理完成';
                        vm.requestDataDispose.id = dataId;
                        vm.requestDataDispose.problemStatus = 3;
                    }else if(n == 3){
                        dialogVm.title = '不予处理';
                        vm.requestDataDispose.id = dataId;
                        vm.requestDataDispose.problemStatus = 5;
                    }
                    if(n == 2||n ==3){
                        util.c2s({
                            "url": erp.BASE_PATH + 'sysProblem/loadSysProblem.do',
                            "type": "post",
                            "contentType" : 'application/json',
                            "data": JSON.stringify({}),
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    var data = responseData.data;
                                    avalon.getVm(getTypeId('questionTypeOptsid')).setOptions(data.problemType);
                                    dialogVm.open();
                                }
                            }
                        });
                    }else if(n == 1){
                        dialogVm.open();
                    }

                };

                vm.$assignedDisposeOpts={
                    "dialogId": getTypeId('disposeDialogId'),
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                            footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                '<div class="ui-dialog-btns">' +
                                '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">确&nbsp;定</button>' +
                                '<span class="separation"></span>' +
                                '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button>' +
                                '</div>' +
                                '</div>';
                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "onClose": function () {
                        var formVm = avalon.getVm(getTypeId("assignedform"));
                        formVm.reset();
                    },
                    "submit": function (evt) {
                        var dialogVm=avalon.getVm(getTypeId('disposeDialogId'));
                        var formVm = avalon.getVm(getTypeId("assignedform"));
                        if (formVm.validate()) {
                            if(vm.handerType == 1){
                                var requestData=pageVm.requestDataHuifu.$model;
                                requestData.description = formVm.getFormData().remark;
                                util.c2s({
                                    "url": erp.BASE_PATH + 'sysProblem/addComments.do',
                                    "type": "post",
                                    "contentType" : 'application/json',
                                    "data": JSON.stringify(requestData),
                                    "success": function (responseData) {
                                        if (responseData.flag == 1) {
                                            dialogVm.close();
                                            updateList(vm.requestData.$model);
                                        }
                                    }
                                });
                            }else{
                                var requestData=pageVm.requestDataDispose.$model;
                                requestData.remark = formVm.getFormData().remark;
                                requestData.problemType = avalon.getVm(getTypeId('questionTypeOptsid')).selectedValue;
                                util.c2s({
                                    "url": erp.BASE_PATH + 'sysProblem/updateSysProblem.do',
                                    "type": "post",
                                    "contentType" : 'application/json',
                                    "data": JSON.stringify(requestData),
                                    "success": function (responseData) {
                                        if (responseData.flag == 1) {
                                            dialogVm.close();
                                            updateList(vm.requestData.$model);
                                        }
                                    }
                                });
                            }
                        }
                    }
                };
                vm.$questionTypeOpts = {
                    "selectId" : getTypeId('questionTypeOptsid'),
                    "options" : [{text:111,value:222}]

                };
                vm.$assignedformOpts = {
                    "formId": getTypeId("assignedform"),
                    "field":
                        [{
                            "selector": ".question-remark",

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
                        ]
                };
                /*问题处理结束*/
                /*确认通知客户*/
                vm.confirmNotice = function(dataId){
                    vm.crequestDataNotice.id = dataId;
                    vm.crequestDataNotice.problemStatus = '4';
                    var requestData=JSON.stringify(pageVm.crequestDataNotice.$model);
                    util.confirm({
                        "content": "你确定已告知客户了吗？",
                        "onSubmit": function () {
                            util.c2s({
                                "url": erp.BASE_PATH + 'sysProblem/updateSysProblem.do',
                                "type": "post",
                                "contentType" : 'application/json',
                                "data": requestData,
                                "success": function (responseData) {
                                    if (responseData.flag == 1) {
                                        updateList(vm.requestData.$model);
                                    }
                                }
                            });
                        }
                    });
                };

                /*确认通知客户结束*/
                /*重新处理*/
                vm.again = function(dataId){
                    vm.crequestDataNotice.id = dataId;
                    vm.crequestDataNotice.problemStatus = '6';
                    var requestData=JSON.stringify(pageVm.crequestDataNotice.$model);
                    util.confirm({
                        "content": "你确定要重新激活问题吗？",
                        "onSubmit": function () {
                            util.c2s({
                                "url": erp.BASE_PATH + 'sysProblem/updateSysProblem.do',
                                "type": "post",
                                "contentType" : 'application/json',
                                "data": requestData,
                                "success": function (responseData) {
                                    if (responseData.flag == 1) {
                                        updateList(vm.requestData.$model);
                                    }
                                }
                            });
                        }
                    });
                };
                /*重新处理结束*/

            });



            avalon.scan(pageEl[0]);
            /*页面初始化请求渲染*/
            var obj={
                title:'',
                merchantName:'',
                pageNumber:1,
                pageSize:10,
                problemStatus:''
            };

            updateList(obj);
            function updateList(obj){
                var requestData=JSON.stringify(obj);
                util.c2s({
                    "url": erp.BASE_PATH + "sysProblem/listSysProblems.do",
                    "type": "post",
                    "contentType" : 'application/json',
                    "data": requestData,
                    "success": function (responseData) {
                        var data,
                        paginationVm = avalon.getVm(getTypeId('paginationId'));
                        if (responseData.flag) {
                            data = responseData.data;
                            paginationVm.total = data.totalSize;
                            pageVm.pageData=data.rows;
                            //pageVm.requestData.title='';
                            //pageVm.requestData.merchantName='';
                        }
                    }
                });

            }
            /*页面初始化请求渲染end*/
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
