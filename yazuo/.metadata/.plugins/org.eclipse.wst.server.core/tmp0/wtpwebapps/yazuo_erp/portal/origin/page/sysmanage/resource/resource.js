define(['avalon', 'util', '../../../widget/pagination/pagination', '../../../widget/dialog/dialog', '../../../widget/form/form'], function (avalon, util) {
     var win = this,
        erp = win.erp,
        pageMod = {};
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName) {
            var addEditDialogId = pageName + '-add-edit-dialog',
                addEditFormId = pageName + '-add-edit-form',
                paginationId = pageName + '-pagination';
            var pageVm = avalon.define(pageName, function (vm) {
                vm.navCrumbs = [];
                vm.parentId = 0; //默认为0，页面入口
                vm.gridTotalSize = 0;
                vm.gridData = [];
                vm.gridAllChecked = false;
                vm.gridSelected = [];
                vm.addEditState = '';
                vm.gridCheckAll = function () {
                    if (this.checked) {
                        vm.gridSelected = _.map(vm.gridData.$model, function (itemData) {
                            return itemData.id + "";
                        });
                        //vm.gridAllChecked = true;
                    } else {
                        vm.gridSelected.clear();
                        //vm.gridAllChecked = false;
                    }
                };
                /**
                 * 表格左下脚一个全选的快捷方式
                 */
                vm.scCheckAll = function () {
                    vm.gridSelected = _.map(vm.gridData.$model, function (itemData) {
                        return itemData.id + "";
                    });
                };
                vm.$paginationOpts = {
                    "paginationId": paginationId,
                    "total": 0,
                    "onJump": function (evt, vm) {
                        updateGrid();
                    }
                };
                vm.$addEditDialogOpts = {
                    "dialogId": addEditDialogId,
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
                    "onOpen": function () {},
                    "onClose": function () {
                        //清除验证信息
                        var formVm = avalon.getVm(addEditFormId);
                        formVm.resourceName = '';
                        formVm.reset();
                    },
                    "submit": function (evt) {
                        var requestData,
                            url,
                            dialogVm = avalon.getVm(addEditDialogId),
                            formVm = avalon.getVm(addEditFormId),
                            addEditState = vm.addEditState,
                            id;
                        if (formVm.validate()) {
                            requestData = formVm.getFormData();
                            //编辑态附加参数
                            if (addEditState == 'edit') {
                                id = vm.gridSelected[0];
                                _.extend(requestData, {
                                    id: id
                                });
                                url = erp.BASE_PATH + 'resource/updateResource.do';
                            } else {
                                url = erp.BASE_PATH + 'resource/saveResource.do';
                            }
                            //发送后台请求，编辑或添加
                            util.c2s({
                                "url": url,
                                "contentType" : 'application/json',
                                "type": "post",
                                "data": JSON.stringify(requestData),
                                "success": function (responseData) {
                                    if (responseData.flag == 1) {
                                        if (addEditState == 'edit') {  //编辑状态前端更新数据
                                            _.some(vm.gridData.$model, function (itemData, i) {
                                                if (itemData.id == id) {
                                                    vm.gridData.set(i, {
                                                        "resource_name": requestData["resourceName"]
                                                    });
                                                    return true;
                                                }
                                            });
                                        } else { //添加状态直接刷新数据
                                            updateGrid();
                                        }
                                        //关闭弹框
                                        dialogVm.close();
                                    }
                                }
                            });
                        }
                    }
                };
                vm.$addEditFormOpts = {
                    "formId": addEditFormId,
                    "field": [{
                        "selector": ".resource-name",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "资源名称不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }],
                    "resourceName": ""
                };
                vm.triggerOnOrOff = function(element){
                    var meEl = avalon(this),
                        url =  erp.BASE_PATH + 'resource/triggerOnOrOffResource/'+meEl.data('resourceId')+'.do';
                    var itemData = this.$vmodel.$model.el,
                        atIndex = this.$vmodel.$model.$index;
                    util.c2s({
                        "url": url,
                        "type": "get",
                        "contentType" : 'application/json',
                        "success": function (responseData) {
                            if (responseData.flag) {  //更新成功，重刷grid
                                if(itemData.is_visible == '0'){
                                    vm.gridData.set(atIndex, {
                                        "is_visible_text": "启用",
                                        "control_is_visible": "禁用",
                                        "is_visible": "1"
                                    });
                                    //element.originalTarget.$vmodel.el.is_visible_text = '启用';
                                }else{
                                    //element.originalTarget.$vmodel.el.is_visible_text = '禁用';
                                    vm.gridData.set(atIndex, {
                                        "is_visible_text": "禁用",
                                        "control_is_visible": "启用",
                                        "is_visible": "0"
                                    });
                                }
                                //meEl.element.text =  meEl.element.text == '禁用'? '启用': '禁用';
                                //updateGrid(true);
                            }
                        }
                    });
                };
                vm.openEdit = function () {
                    var dialogVm = avalon.getVm(addEditDialogId),
                        formVm = avalon.getVm(addEditFormId),
                        meEl = avalon(this),
                        resourceId = meEl.data('resourceId') + "",
                        resourceName = meEl.data('resourceName');
                    dialogVm.title = '修改名称';
                    formVm.resourceName = resourceName;
                    vm.addEditState = 'edit';    //设置状态
                    //选中当前行，取消其他行选中
                    vm.gridSelected = [resourceId];
                    dialogVm.open();
                };
                vm.batchTriggerOnGridItem = function () {
                    vm.batchTriggerTrigger(1);
                };
                vm.batchTriggerOffGridItem = function () {
                    vm.batchTriggerTrigger(0);
                };
                vm.batchTriggerTrigger = function (flag) {
                    var ids,
                        requestData,
                        url;
                    if (vm.gridSelected.size() === 0) {
                        util.alert({
                            "content": "请先选中资源"
                        });
                    } else {
                        ids = vm.gridSelected.$model;
                            url = erp.BASE_PATH + 'resource/updateResources.do';
                            requestData = {
                                "idString": ids, "flag": flag //1：启用   2：禁用
                            };
                        util.c2s({
                            "url": url,
                            "type": "post",
                            "contentType" : 'application/json',
                            "data": JSON.stringify(requestData),
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    updateGrid(false, vm.parentId);
                                }
                            }
                        });
                    }
                };
                vm.viewChildren = function(){
                    var meEl = avalon(this);
                    var resourceId = meEl.data('resourceId');
                    updateGrid(false, resourceId);
                };
                vm.clickPageNav = function () {
                    var resourceId = avalon(this).data('resourceId');
                    updateGrid(false, resourceId);
                };
            });
            pageVm.gridSelected.$watch("length", function(n) {
                pageVm.gridAllChecked = n === pageVm.gridData.size();
            });

            avalon.scan(pageEl[0],[pageVm]);
            //更新grid数据
            updateGrid();
            //更新grid数据
            function updateGrid(jumpFirst, parentId) {
                var paginationVm = avalon.getVm(paginationId),
                    requestData = {};
                if (jumpFirst) {
                    requestData.pageSize = paginationVm.perPages;
                    requestData.pageNumber = 1;
                } else {
                    requestData.pageSize = paginationVm.perPages;
                    requestData.pageNumber = paginationVm.currentPage;
                }
                if(parentId){
                    requestData.parentId = parentId;
                    pageVm.parentId = parentId;
                }else{
                    requestData.parentId = 0;
                    pageVm.parentId = 0;
                }
                util.c2s({
                    "url": erp.BASE_PATH + "resource/list/"+requestData.parentId +"/"+requestData.pageNumber +"/"+requestData.pageSize +".do",
                    "type": 'get',
                    "contentType" : 'application/json',
                    "success": function (responseData) {
                        var data,
                            pageNavData;
                        if (responseData.flag==1) {
                            data = responseData.data;
                            pageNavData = data.pageNavigate;    //导航数据
                            paginationVm.total = data.totalSize;
                            if (jumpFirst) {
                                paginationVm.currentPage = 1;   //重设成第一页
                            }
                            _.each(data.rows, function (row) {
                                if (row.is_visible == "1") {
                                    row.is_visible_text = "启用";
                                    row.control_is_visible = "禁用";
                                }else{
                                    row.is_visible_text = "禁用";
                                    row.control_is_visible = "启用";
                                }
                            });
                            pageVm.gridData = data.rows;
                            pageVm.gridTotalSize = data.totalSize;
                            pageVm.gridAllChecked = false;  //取消全选
                            pageVm.gridSelected.clear();
                            //显示导航
                            pageVm.navCrumbs = _.map(pageNavData, function (itemData, i) {
                                return {
                                    "id": itemData.id,
                                    "text": itemData.resourceName,
                                    "href": "javascript:;"
                                };
                            });
                            pageVm.navCrumbs.unshift({
                                "id": 0,
                                "text": "资源管理",
                                "href": "#/sysmanage/resource"
                            });
                            //删掉最后一个导航链接
                            pageVm.navCrumbs.set(pageVm.navCrumbs.size()-1, {
                                "href": false
                            });
                        }
                    }
                });
            }
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            //需要单独清除dialog vm
            //var dialogVm = avalon.getVm(pageName + '-add-edit');
            //$(dialogVm.widgetElement).remove();
            $('.add-edit-resource-dialog .add-edit-form').remove();
            $('.add-edit-resource-dialog').remove();
            $('.pagination', pageEl).remove();
        }
    });

    return pageMod;
});
