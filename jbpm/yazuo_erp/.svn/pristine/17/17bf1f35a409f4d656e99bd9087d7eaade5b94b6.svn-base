/**
 * 指派需求
 */
define(["avalon", "util", "json", "moment", "text!./persontree.html", "text!./persontree.css", "common", "../../widget/dialog/dialog", "../../widget/form/select", '../../widget/tree/tree'], function (avalon, util, JSON, moment, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    var styleData = styleEl.data('module') || {};

    if (!styleData["persontree"]) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'persontree/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'persontree/');
        }
        styleData["persontree"] = true;
        styleEl.data('module', styleData);
    }
    var module = erp.module.persontree = function (element, data, vmodels) {
        var opts = data.persontreeOptions,
            elEl = $(element);
        var persontreeDialogId = data.persontreeId + '-dialog',//dialog对话框
            ztreeId = data.persontreeId + '-treeId';//树

        var model = avalon.define(data.persontreeId, function (vm) {
            avalon.mix(vm, opts);
            vm.find = 1;//搜索类型依据
            vm.groupId = '';//搜索条件依据
            vm.personId = '';//处理人id
            vm.key = '';
            vm.personArr = [];
            vm.ids = []; //指派时的数据

            vm.findAll = function(){
                vm.find = 0;
                vm.groupId = '';
                vm.key = '';
                util.c2s({
                    "url": erp.BASE_PATH + 'user/getUserOfGroup.do',
                    "type": "post",
                    "data":  {"groupId": vm.groupId},
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag == 1) {
                            data = responseData.data;
                            vm.personArr = data;
                            vm.personId = ''
                        }
                    }
                });
            };
            vm.selectedPerId = function(){
                $(this).addClass('cur').siblings().removeClass('cur');
                var dataId = $(this).attr('data-id');
                vm.personId = dataId;
            };
            vm.searchPer = function(){
                vm.personId = ''
                if(!vm.key){
                    util.c2s({
                        "url": erp.BASE_PATH + 'user/getUserOfGroup.do',
                        "type": "post",
                        "data":  {"groupId": vm.groupId},
                        "success": function (responseData) {
                            var data;
                            if (responseData.flag == 1) {
                                data = responseData.data;
                                vm.personArr = data;
                                vm.personId = ''
                            }
                        }
                    });
                }else{
                    vm.personArr = _.filter(vm.personArr.$model,function(item){
                        return (item['user_name'].indexOf(vm.key) !== -1);
                    });
                }
            };
            vm.$persontreeOpts = {//对话框dialog
                "dialogId": persontreeDialogId,
                "width": 580,
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
                "onOpen": function () {
                },
                "onClose": function () {
                    model.ids = [];
                },
                "submit": function (evt) {
                    if(!vm.personId){
                        util.alert(
                            {
                                "content": '请指派一个用户！',
                                "iconType": "error"
                            }
                        );
                    }else{
                        util.c2s({
                            "url": erp.BASE_PATH + 'besRequirement/saveBesRequirement.do',
                            "type": "post",
                            "data":  JSON.stringify({ids:vm.ids,handlerId:vm.personId}),
                            "contentType": 'application/json',
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    avalon.getVm(persontreeDialogId).close();
                                    model.ids = [];
                                    model.callFn(responseData);
                                }
                            }
                        });
                    }

                }
            };
            vm.$ztreeOpts = {//树
                "treeId": ztreeId,
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
                            "onClick": function (evt, ztreeId, node) {    //点击节点触发
                                if(node.children){return;}
                                vm.find = 1;
                                vm.key = '';
                                vm.groupId = node.id;
                                util.c2s({
                                    "url": erp.BASE_PATH + 'user/getUserOfGroup.do',
                                    "type": "post",
                                    "data":  {"groupId": vm.groupId},
                                    "success": function (responseData) {
                                        var data;
                                        if (responseData.flag == 1) {
                                            data = responseData.data;
                                            vm.personArr = data;
                                            vm.personId = ''
                                        }
                                    }
                                });
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
            vm.open = function () {
                util.c2s({
                    "url": erp.BASE_PATH + 'group/getSysGroupsForXTree.do',
                    "type": "post",
                    "contentType": 'application/json',
                    "data": '',
                    "success": function (responseData) {
                        var data;
                        var treeVm = avalon.getVm(ztreeId);
                        if (responseData.flag == 1) {
                            data = responseData.data;
                            treeVm.updateTree([]);
                            treeVm.updateTree(_.map(data, function (item) {
                                return {
                                    id: item.id,
                                    name: item.name,
                                    pId: item.pid
                                };
                            }));
                            avalon.getVm(persontreeDialogId).title = "指派需求给";
                            avalon.getVm(persontreeDialogId).open();
                            vm.findAll();
                        }
                    }
                });
            };

            vm.$init = function () {
                elEl.addClass('module-persontree');
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [model].concat(vmodels));
            };
            vm.$remove = function () {
                avalon.getVm(ztreeId) && $(avalon.getVm(ztreeId).widgetElement).remove();
                $(avalon.getVm(persontreeDialogId).widgetElement).remove();
                elEl.removeClass('module-persontree').off().empty();
            };
        });
        return model;
    };
    module.defaults = {
        "merchantId" : '',//暂不需要此参数
        "ids" : [],
        "callFn" : avalon.noop
    };
    return avalon;
});