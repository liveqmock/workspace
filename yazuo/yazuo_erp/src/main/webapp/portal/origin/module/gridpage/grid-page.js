/**
 * TODO  待完善
 * common-page 组件，包含通用功能增删改查，模仿position,js实现增删改查
 */
define(['avalon', 'util', '../../widget/pagination/pagination', '../../widget/dialog/dialog', '../../widget/form/form'], function (avalon, util) {
     var win = this,
        erp = win.erp,
        pageMod = {
	        "$overwrite": function (vm) {
	        	/*
	        	 * 需要重写 VM对象如属性gridTotalSize
	        	 * 方式1：用extend
		        	  _.extend(vm, {
		            	"openEdit": function(){ alert(1); }  //测试代码
		            	//}
		            );
		         * 方式2：直接改变对象 vm，如 ：vm.gridTotalSize=1000   
	        	 */
	        }
     	};
     erp.module.pageMod = pageMod;//对外暴露
    _.extend(pageMod, _.clone({
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
                        formVm.positionName = '';
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
                                    "id": id
                                });
                                url = erp.BASE_PATH + 'position/edit.do';
                            } else {
                                url = erp.BASE_PATH + 'position/savePosition.do';
                            }
                            //发送后台请求，编辑或添加
                            util.c2s({
                                "url": url,
                                "type": "post",
                                "data": requestData,
                                "success": function (responseData) {
                                    if (responseData.flag == 1) {
                                        if (addEditState == 'edit') {   //编辑状态前端更新数据
                                            _.some(vm.gridData.$model, function (itemData, i) {
                                                if (itemData.id == id) {
                                                    vm.gridData.set(i, {
                                                        "position_name": requestData["positionName"]
                                                    });
                                                    return true;
                                                }
                                            });
                                        } else { //添加状态直接刷新数据
                                            updateGrid(true);
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
                        "selector": ".position-name",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "职位名称不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }],
                    "positionName": ""
                };
                vm.openAdd = function () {
                    var dialogVm = avalon.getVm(addEditDialogId),
                        formVm = avalon.getVm(addEditFormId);
                    dialogVm.title = '添加职位';
                    formVm.positionName = '';
                    vm.addEditState = 'add';    //设置状态
                    //清除选中状态
                    vm.gridSelected.clear();
                    dialogVm.open();
                };
                vm.openEdit = function () {
                    var dialogVm = avalon.getVm(addEditDialogId),
                        formVm = avalon.getVm(addEditFormId),
                        meEl = avalon(this),
                        positionId = meEl.data('positionId') + "",
                        positionName = meEl.data('positionName');
                    dialogVm.title = '编辑职位';
                    formVm.positionName = positionName;
                    vm.addEditState = 'edit';    //设置状态
                    //选中当前行，取消其他行选中
                    vm.gridSelected = [positionId];
                    dialogVm.open();
                };
                vm.removeGridItem = function () {
                    var meEl = avalon(this),
                        positionId = meEl.data('positionId') + "";
                    //选中当前行，取消其他行选中
                    vm.gridSelected = [positionId];
                    //删除对应职位
                    vm._removePosition();
                };
                vm.batchRemoveGridItem = function () {
                    vm._removePosition();
                };
                vm._removePosition = function () {
                    var ids,
                        requestData,
                        url;
                    if (vm.gridSelected.size() === 0) {
                        util.alert({
                            "content": "请先选中职位"
                        });
                    } else {
                        ids = vm.gridSelected.$model;
                        if (ids.length === 1) {
                            url = erp.BASE_PATH + 'position/delete.do';
                            requestData = {
                                "id": ids[0]
                            };
                        } else {
                            url = erp.BASE_PATH + 'position/deleteMany.do';
                            requestData = {
                                "idString": ids
                            };
                        }
                        util.confirm({
                            "content": "确定要删除选中的职位吗？",
                            "onSubmit": function () {
                                util.c2s({
                                    "url": url,
                                    "type": "post",
                                    "data": requestData,
                                    "success": function (responseData) {
                                        if (responseData.flag == 1) {   //删除成功，重刷grid
                                            updateGrid(true);
                                        }
                                    }
                                });
                            }
                        });
                    }
                };
                /***********此处为更改的代码
                _.extend(vm, {
                	"openEdit": function(){
                		alert(1);
                		}
                	}
                );
                 ***********此处为更改的代码*/
                erp.module.pageMod.$overwrite(vm);
            });
            pageVm.gridSelected.$watch("length", function(n) {
                pageVm.gridAllChecked = n === pageVm.gridData.size();
            });

            avalon.scan(pageEl[0],[pageVm]);
            //更新grid数据
            updateGrid();

            function updateGrid(jumpFirst) {
                var paginationVm = avalon.getVm(paginationId),
                    requestData = {};
                if (jumpFirst) {
                    requestData.pageSize = paginationVm.perPages;
                    requestData.pageNumber = 1;
                } else {
                    requestData.pageSize = paginationVm.perPages;
                    requestData.pageNumber = paginationVm.currentPage;
                }
                util.c2s({
                    "url": erp.BASE_PATH + "position/list.do",
                    //"type": "get",
                    "data": requestData,
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            paginationVm.total = data.totalSize;
                            if (jumpFirst) {
                                paginationVm.currentPage = 1;   //重设成第一页
                            }
                            pageVm.gridData = data.rows;
                            pageVm.gridTotalSize = data.totalSize;
                            pageVm.gridAllChecked = false;  //取消全选
                            pageVm.gridSelected.clear();
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
            $('.add-edit-position-dialog .add-edit-form').remove();
            $('.add-edit-position-dialog').remove();
            $('.pagination', pageEl).remove();
        }
    }));
    return pageMod;
});

/**
 * 以下是测试例子，如 position.js
 * 
 define(['avalon', 'util', '../../../widget/pagination/pagination', '../../../widget/dialog/dialog',
        '../../../widget/form/form','../../../module/gridpage/grid-page'], function (avalon, util) {

	_.extend(erp.module.pageMod, {
		 "$overwrite": function (vm) {
	            vm.openEdit =  function(){ alert(333); } 
	        }
    });
	//alert(JSON.stringify(erp));
    return erp.module.pageMod;
});
 * 
 */
