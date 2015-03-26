/**
 * 当前登录用户所属商户信息，切换其他商户
 * Created by q13 on 14-5-9.
 */
define(["avalon", "util", "text!./merchantdomain.html", "text!./merchantdomain.css", "common", "../../widget/autocomplete/autocomplete", "../../widget/tree/tree"], function(avalon, util, tmpl, cssText) {
    var win = this,
        app = win.app,
        page = app.page;
    var styleEl = $("#module-style"),
        tmpl = tmpl.split('MS_OPTION_WIDGET'),
        cptTmpl = tmpl[0],
        acTmpl = tmpl[1];

    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    if (!styleEl.data('moduleMerchantdomain')) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'merchantdomain/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'merchantdomain/');
        }
        styleEl.data('moduleMerchantdomain', true);
    }
    var loginUserData = _.extend({
        "merchantId": 0,
        "merchantName": "",
        "merchantType": 0   //1表示是集团，2表示是域，3表示是门店
    }, app.getAppData('user'));
    if (loginUserData.faceShopType) {
        loginUserData.merchantType = loginUserData.faceShopType;
    }
    var merchantDataStore = null;   //存储登录用户所属的门店信息
    var module = app.module.merchantdomain = function(element, data, vmodels) {
        var opts = data.merchantdomainOptions,
            autocompleteId = data.merchantdomainId + '-autocomplete',
            treeId = data.merchantdomainId + '-tree',
            elEl = $(element);
        var vmodel = avalon.define(data.merchantdomainId, function (vm) {
            vm.widgetElement = element;
            avalon.mix(vm, opts);
            vm.$autocompleteOpts = {
                "autocompleteId": autocompleteId,
                "placeholder": "请指定商户",
                "delayTime": 600,   //延时300ms请求
                "getTemplate": function () {
                    return acTmpl;
                },
                "onChange": function (text, callback) {
                    var merchantTreeData = vm.$merchantTreeData;
                    if (text.length) {
                        if (merchantTreeData) {
                            callback(_.map(_.filter(merchantTreeData, function (itemData) {
                                if (itemData.name.search(new RegExp(text, 'g')) > -1 || itemData.shortPinyin.slice(0, text.length) === text.toUpperCase()) {  //支持中文全词匹配和拼音完全匹配
                                    return true;
                                }
                            }), function (itemData) {
                                return {
                                    "value": itemData.id,
                                    "text": itemData.name,
                                    "merchantType": itemData.merchantType
                                };
                            }));
                        } else {
                            callback([]);
                        }
                    } else {
                        callback([]);   //空查询显示空
                    }
                },
                //每次清空下拉面板前的额外清理
                "onBeforeEmptyPanel": function () {
                    var treeVm = avalon.getVm(treeId);
                    treeVm && $(treeVm.widgetElement).remove();
                },
                //每次扫描后重新设置tree信息
                "onAfterScanPanel": function () {
                    if (!vm.$merchantTreeData) { //初始化只加载一次
                        vm.updateMerchantTreeData();
                    } else {
                        avalon.getVm(treeId).updateTree(vm.$merchantTreeData, true);
                    }
                }
            };
            vm.$treeOpts = {
                "treeId": treeId,
                "ztreeOptions": {
                    "setting": {
                        view: {
                            showIcon: true
                        },
                        "data": {
                            "simpleData": {
                                "enable": true
                            }
                        },
                        "callback": {
                            "onClick": function (evt, treeId, node) {    //点击节点触发
                                //判定是否门店支持当前页面功能，不支持给出提示
                                var currentRouteData = app.page.lastRouteData;
                                if (currentRouteData.noSupportFace && node.merchantType === 3) {
                                    util.alert({
                                        "content": "对不起，在门店下无此功能权限。",
                                        "iconType": "error"
                                    });
                                    avalon.getVm(autocompleteId + '-select').close();   //主动关闭下拉面板
                                    return;
                                }
                                //域判定
                                if (currentRouteData.noSupportRegion && node.merchantType === 2) {
                                    util.alert({
                                        "content": "对不起，在域下无此功能权限。",
                                        "iconType": "error"
                                    });
                                    avalon.getVm(autocompleteId + '-select').close();   //主动关闭下拉面板
                                    return;
                                }
                                //集团判定
                                if (currentRouteData.noSupportGroup && node.merchantType === 1) {
                                    util.alert({
                                        "content": "对不起，在集团下无此功能权限。",
                                        "iconType": "error"
                                    });
                                    avalon.getVm(autocompleteId + '-select').close();   //主动关闭下拉面板
                                    return;
                                }
                                //设置当前选中商户id为domain
                                vm.domainId = node.id;
                                vm.domainText = node.name;
                                vm.merchantType = node.merchantType;
                                avalon.getVm(autocompleteId).inputText = node.name; //自动完成一并设置
                                avalon.getVm(autocompleteId + '-select').close();   //主动关闭下拉面板
                            }
                        }
                    },
                    "treeData": []
                }
            };
            vm.domainId = loginUserData.merchantId;
            vm.domainText = loginUserData.merchantName;
            vm.merchantType = loginUserData.merchantType;
            vm.$merchantTreeData = null;    //保存最新的商户树信息
            vm.$tid = 0;
            vm.$init = function() {
                elEl.addClass('module-merchantdomain');
                elEl.html(cptTmpl);
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
                avalon.getVm(autocompleteId).inputText = loginUserData.merchantName;    //设置默认商户
                //绑定blur事件
                elEl.on('blur', '.select-header-text', function () {
                    var autocompleteVm = avalon.getVm(autocompleteId),
                        selectVm = avalon.getVm(autocompleteId + '-select'),
                        selectedValue,
                        selectedOpt,
                        currentRouteData;
                    clearTimeout(vm.$tid);
                    vm.$tid = setTimeout(function () {    //保证blur在select的onSelect后执行
                        selectedValue = selectVm.selectedValue;
                        selectedOpt = selectVm.getOptionByValue(selectedValue);
                        currentRouteData = app.page.lastRouteData;
                        if (selectedOpt && selectedOpt.merchantType === 3 && currentRouteData.noSupportFace) {
                            util.alert({
                                "content": "对不起，在门店下无此功能权限。",
                                "iconType": "error"
                            });
                            autocompleteVm.inputText = vm.domainText;
                            return;
                        }
                        if (selectedOpt && selectedOpt.merchantType === 2 && currentRouteData.noSupportRegion) {
                            util.alert({
                                "content": "对不起，在域下无此功能权限。",
                                "iconType": "error"
                            });
                            autocompleteVm.inputText = vm.domainText;
                            return;
                        }
                        if (selectedOpt && selectedOpt.merchantType === 1 && currentRouteData.noSupportGroup) {
                            util.alert({
                                "content": "对不起，在集团下无此功能权限。",
                                "iconType": "error"
                            });
                            autocompleteVm.inputText = vm.domainText;
                            return;
                        }
                        if (selectedValue) {
                            vm.domainId = selectedValue;
                            vm.domainText = selectVm.selectedText;
                            vm.merchantType = selectedOpt.merchantType;
                        } else {
                            autocompleteVm.inputText = vm.domainText;
                        }
                    }, 600);
                }).on('focus', '.select-header-text', function () {
                    avalon.getVm(autocompleteId).inputText = "";
                });
                //点击tree panel不触发域更改，点击tree panel外触发更改
                $('body').on('click', function (evt) {
                    var panelEl = $('.module-merchantdomain-tree-panel'),
                        inputEl = $('.select-header-text', elEl);
                    if (panelEl.length) {
                        if (panelEl[0] === evt.target || $.contains(panelEl[0], evt.target)) {
                            clearTimeout(vm.$tid);
                        } else {
                            if (inputEl[0] !== evt.target) {
                                inputEl.blur();
                            }
                        }
                    }
                });

            };
            vm.$remove = function() {
                $(avalon.getVm(autocompleteId).widgetElement).remove();
                elEl.removeClass("module-merchantdomain");
                elEl.off();
                elEl.empty();
                clearTimeout(vm.$tid);
            };
            /**
             * 更新树信息
             */
            vm.updateMerchantTreeData = function () {
                /*util.c2s({
                    "url": app.BASE_PATH + "merchant/treelist",
                    "success": function (responseData) {
                        var rows,
                            treeVm = avalon.getVm(treeId);
                        if (responseData.flag) {
                            rows = responseData.data;
                            vm.$merchantTreeData = rows;
                            treeVm.updateTree(rows, true);  //同时展开所有节点
                        }
                    }
                }, {
                    "mask": false
                });*/
                var allData = null,
                    domainAllData = [],
                    domainData,
                    findChildrenData;
                if (merchantDataStore) {
                    /*allData = _.map(merchantDataStore, function (itemData) {
                        return {
                            "id": itemData.merchantId,
                            "pId": itemData.parent,
                            "name": itemData.merchantName,
                            "merchantType": itemData.faceShopType
                        };
                    });*/
                    //只筛选自己所属domain下的子门店
                    domainData = _.find(merchantDataStore, function (itemData) {
                        return itemData.merchantId == loginUserData.merchantId;
                    });
                    findChildrenData = function (rootNode, rows) {
                        var inData = [],
                            outData = [];
                        _.each(rows, function (itemData) {
                            if (itemData.parent == rootNode.merchantId) {
                                inData.push(itemData);
                            } else {
                                outData.push(itemData);
                            }
                        });
                        //装直接子孙
                        domainAllData = domainAllData.concat(inData);
                        _.each(inData, function (itemData) {
                            findChildrenData(itemData, outData);
                        });
                    };
                    //先把自己装进去
                    if (domainData) {
                        domainAllData.push(domainData);
                        //再装子孙元素
                        findChildrenData(domainData, merchantDataStore);
                        domainAllData = _.map(domainAllData, function (itemData) {
                            return {
                                "id": itemData.merchantId,
                                "pId": itemData.parent,
                                "name": itemData.merchantName,
                                "merchantType": itemData.faceShopType,
                                "shortPinyin": itemData.shortPinyin
                            };
                        });
                    }
                    vm.$merchantTreeData = domainAllData;
                    //vm.$merchantTreeData = allData;
                    avalon.getVm(treeId).updateTree(domainAllData, true);  //同时展开所有节点
                }
            };
            vm.initDomain = function () {
                var belongMerchantData;
                //只请求一次，获取登录用户所有的门店信息，自动保存下来
                if (!merchantDataStore) {
                    util.c2s({
                        "url": app.BASE_PATH + "controller/merchant/getMerchantList.do",
                        "data": {
                            "merchantId": loginUserData.brandId
                        },
                        "success": function (responseData) {
                            var data;
                            if (responseData.flag) {
                                data = responseData.data;
                                //存下来
                                merchantDataStore = data;
                                //找到登录用户对应的merchant信息
                                belongMerchantData = _.find(merchantDataStore, function (itemData) {
                                    return itemData.merchantId == loginUserData.merchantId;
                                });
                                if (belongMerchantData) {
                                    vmodel.domainText = belongMerchantData.merchantName;
                                    vmodel.domainId = belongMerchantData.merchantId;
                                    vmodel.merchantType = belongMerchantData.faceShopType;
                                    avalon.getVm(autocompleteId).inputText = vm.domainText;
                                    //填充登录用户信息存储
                                    app.setAppData("user", {
                                        "merchantName": belongMerchantData.merchantName
                                    });
                                }
                            }
                        }
                    });
                } else {
                    //找到登录用户对应的merchant信息
                    belongMerchantData = _.find(merchantDataStore, function (itemData) {
                        return itemData.merchantId == loginUserData.merchantId;
                    });
                    if (belongMerchantData) {
                        vmodel.domainText = belongMerchantData.merchantName;
                        vmodel.domainId = belongMerchantData.merchantId;
                        vmodel.merchantType = belongMerchantData.faceShopType;
                        avalon.getVm(autocompleteId).inputText = vm.domainText;
                    }
                }
            };
            vm.setDomainData = function (domainId, domainText) {
                vm.domainId = domainId;
                vm.domainText = domainText;
                avalon.getVm(autocompleteId).inputText = domainText;
            };
            vm.clear = function () {
                vm.domainId = 0;
                vm.domainText = '';
                vm.merchantType = 0;
                avalon.getVm(autocompleteId).inputText = '';
            };
            //监控domainId变化，重新刷tree
            vm.$watch('domainId', function (val) {
                //找到domainId下所有子孙元素
                /*var domainAllData = [],
                    domainData = _.find(merchantDataStore, function (itemData) {
                        return itemData.merchantId == val;
                    });
                var findChildrenData = function (rootNode, rows) {
                    var inData = [],
                        outData = [];
                    _.each(rows, function (itemData) {
                        if (itemData.parent == rootNode.merchantId) {
                            inData.push(itemData);
                        } else {
                            outData.push(itemData);
                        }
                    });
                    //装直接子孙
                    domainAllData = domainAllData.concat(inData);
                    _.each(inData, function (itemData) {
                        findChildrenData(itemData, outData);
                    });
                };
                //先把自己装进去
                if (domainData) {
                    domainAllData.push(domainData);
                    //再装子孙元素
                    findChildrenData(domainData, merchantDataStore);
                    domainAllData = _.map(domainAllData, function (itemData) {
                        return {
                            "id": itemData.merchantId,
                            "pId": itemData.parent,
                            "name": itemData.merchantName,
                            "merchantType": itemData.faceShopType
                        };
                    });
                }
                vm.$merchantTreeData = domainAllData;
                var treeVm = avalon.getVm(treeId);
                treeVm && treeVm.updateTree(domainAllData, true);  //同时展开所有节点 */

                //设置登录用户当前所在merchantId
                if (vm.autoAdjustMerchantChange) {
                    app.setAppData('user', {
                        "currentMerchantId": val
                    });
                    //强制刷新当前页面
                    util.forceRefresh();
                }
            });
            vm.$skipArray = ["widgetElement", "inputText"];

        });

        return vmodel;
    };
    module.version = 1.0;
    module.defaults = {
        "autoAdjustMerchantChange": true    //自动调整当前商户设置
    };
    return avalon;
});
