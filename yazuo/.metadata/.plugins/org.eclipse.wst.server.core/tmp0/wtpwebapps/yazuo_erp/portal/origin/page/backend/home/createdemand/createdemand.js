/**
 * 创建需求-
 */
define(['avalon', 'util', 'json', 'moment', '../../../../widget/form/select', '../../../../widget/uploader/uploader', '../../../../widget/calendar/calendar', '../../../../widget/autocomplete/autocomplete', '../../../../module/address/address', '../../../../module/persontree/persontree', '../../../../widget/tree/tree'], function (avalon, util, JSON, moment) {
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
                vm.navCrumbs = [
                    {
                        "text": "我的主页",
                        "href": "#/backend/home"
                    },
                    {
                        "text": '创建需求'
                    }
                ];
                vm.permission = {//权限
                    assign_141 : util.hasPermission("assign_141")//指派
                };
                var loginUserData = erp.getAppData('user');
                vm.pageUserId = loginUserData.id;
                vm.pageType = routeData.params["type"];
                if (vm.pageType == 'edit') {
                    vm.pageTitle = '修改需求';
                    vm.navCrumbs[1].text = '修改需求';
                    vm.pageId = routeData.params["id"] - 0;
                } else if (vm.pageType == 'add') {
                    vm.pageTitle = '创建需求';
                    vm.navCrumbs[1].text = '创建需求';
                }
                //页面逻辑需要的变量
                vm.displayTree = false;//是否显示门店树
                vm.hide1 = false;//门店地址
                vm.hide2 = false;//联系人电话
                vm.hide3 = false;//类型详细
                vm.mobile = "";//联系人电话
                vm.showContact = false;//添加联系人是否显示
                vm.uping = false;//是否上传中
                vm.attachments = {};//附件数组
                vm.testing = false;//是否开始验证
                vm.storeName = '无';//域，门店名称
                vm.treeList = false;//是否显示域，门店的列表
                vm.handlerPerson = '自己';//处理人
                vm.isHanderRemind = false;//是否可以点击提醒
                //页面逻辑需要的变量-end
                vm.handlerArr = [{"text": '无', value: ''},{"text": '自己', value: vm.pageUserId}];
                //域、门店对象
                vm.stores = [];
                vm.contact = [];


                //联系人，来源分类，来源内容，类型对象
                vm.linkSourceType = {
                    contact: [],//联系人
                    sourceCat: [],//来源分类
                    source: [],//来源详细
                    type: []//类型
                };
                /*发送后台的数据*/
                vm.merchantId = 0; //商户ID  not null
                vm.storeId = 0; //门店id  not null , 如果没有可以存 商户ID
                vm.contactCat = 0;  //联系人分类  1-商户 2-前端
                vm.contactId = 0;  //联系人ID not null
                vm.sourceCat = ""; //来源分类 not null  1-商户2-前端3-销售4-代理
                vm.sourceContent = ""; //来源内容  1-400 2-QQ 3-ERP 4-微信 5-邮件 6-传真
                vm.resourceRemark = ""; //资源代码 not null
                vm.resourceExtraRemark = ""; // 其他资源代码 可以为null
                vm.title = ""; //标题  not null
                vm.content = ""; //内容  可以为null
                vm.attachmentId = "";//附件ID  可以为null
                vm.handlerId = 0; //处理人ID  可以为null  note: 如果id在 user表中不存在会报错
                vm.isRemind = 0;//提醒是否打开
                vm.handledTime = moment().format('YYYY-MM-DD HH:mm:ss');//处理时间
                /*发送后台的数据-end*/
                var storeIndex = -1;

                /*设置联动select方法*/
                vm.setSelect = function (arr, name) {
                    var indexAt = 0;
                    _.each(arr, function (item, index) {
                        if(item.isSelected == 1){
                            indexAt = index;
                            return;
                        }
                    });
                    avalon.getVm(getTypeId(name)).setOptions(arr,indexAt);
                };
                /*设置联动select方法-end*/
                /*
                * 1，选择商户后返回：域、门店的信息，处理人成初始状态。
                * 2，每次点击域或门店都返回联系人信息，处理人成初始状态。
                * */
                //商户
                vm.$brandOpts = {
                    "autocompleteId": getTypeId('-brandId'),
                    "placeholder": "请指定商户",
                    "delayTime": 300,   //延时300ms请求
                    "onChange": function (text, callback) {
                        if (text.length) {
                            util.c2s({
                                "url": erp.BASE_PATH + "synMerchant/getSynMerchantInfo.do",
                                // "contentType": "application/json",
                                "data": {
                                    "merchantName": text
                                },
                                "success": function (responseData) {
                                    var rows;
                                    if (responseData.flag) {
                                        rows = responseData.data;
                                        callback(_.map(rows, function (itemData) {
                                            itemData.text = itemData.merchant_name || "未知";
                                            itemData.value = itemData.merchant_id;
                                            return itemData;
                                        }));
                                    }
                                }
                            }, {
                                "mask": false
                            });
                        } else {
                            callback([]);//空查询显示空
                        }
                    },
                    "onSelect": function (selectedValue) {
                        var merchantId = avalon.getVm(getTypeId('-brandId')).getOptionByValue(selectedValue).merchant_id;
                        vm.merchantId = merchantId;
                        util.c2s({
                            "url": erp.BASE_PATH + "besRequirement/getCascateSelectMerchants.do",
                            "contentType": "application/json",
                            "data": JSON.stringify({merchantId : vm.merchantId}),
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    var newObj = {"text": "请选择","value": 0};
                                    data.contact[0].children.unshift(newObj);
                                    var contactVm =  avalon.getVm(getTypeId('-contactSelect1'));
                                    contactVm.panelVmodel.options[1] = data.contact[0];
                                    contactVm.setOptions(contactVm.panelVmodel.options);
                                    util.testing($('.add-edit-create-query')[0],$('.hidden-merchantId'));
                                    var treeVm = avalon.getVm(getTypeId('-marchantZtreeId'));
                                    treeVm.updateTree(eachArr(data.merchants));
                                    vm.storeName = '请选择';
                                    vm.treeList = true;
                                }
                            }
                        });
                        $('.auto-complete-blur-event').find('.select-header-text').blur(function(){
                            setTimeout(function(){
                                var merchantId = avalon.getVm(getTypeId('-brandId')).getOptionByValue(selectedValue);
                                if(!merchantId){
                                    vm.storeName = '无';
                                    vm.treeList = false;
                                    vm.merchantId = 0;
                                }
                            },500)
                        });
                    }
                };
                function eachArr (arr){
                    var arr = _.map(arr, function (item) {
                        if(item.children){
                            return {
                                id: item.value,
                                name: item.text,
                                children: eachArr (item.children)
                            };
                        }else{
                            return {
                                id: item.value,
                                name: item.text
                            };
                        }
                    });
                    return arr;
                }
                vm.$ztreeOpts = {//树
                    "treeId": getTypeId('-marchantZtreeId'),
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
                                    vm.storeName = node.name;
                                    vm.displayTree = false;
                                    vm.storeId = node.id;
                                    util.c2s({
                                        "url": erp.BASE_PATH + "besRequirement/getCascateSelectMerchants.do",
                                        "contentType": "application/json",
                                        "data": JSON.stringify({merchantId:vm.merchantId,storeId:vm.storeId}),
                                        "success": function (responseData) {
                                            var data;
                                            if (responseData.flag) {
                                                data = responseData.data;
                                                var newObj = {"text": "请选择","value": 0};
                                                data.contact[0].children.unshift(newObj);
                                                var contactVm =  avalon.getVm(getTypeId('-contactSelect1'));
                                                contactVm.panelVmodel.options[1] = data.contact[0];
                                                contactVm.setOptions(contactVm.panelVmodel.options);
                                                util.testing($('.add-edit-create-query')[0],$('.hidden-merchantId'));
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
                vm.treeShow = function(){
                    $('.create-merchant-tree').css({
                        left:$('.input-text-store').position().left,
                        top:$('.input-text-store').position().top+31
                    });
                    vm.displayTree = true;
                };
                $('body').on('click',function(event){
                    event = event || window.event;
                    var target = event.target || event.srcElement;
                    if(target != $('.input-text-store')[0] && !$('.create-merchant-tree').has(target).length){
                        vm.displayTree = false;
                    }
                });



                //添加通讯录
                vm.$addressOpts = {
                    "addressId": getTypeId('-addressModule'),
                    "merchantId": 0,
                    "displayType": 'add',
                    "moduleType": "fes",
                    "isSearch": false,
                    "requestData": null
                };
                vm.addCommunication = function (name) {
                    var dialog = avalon.getVm(getTypeId('-addressModule'));
                    dialog.displayType = 'add';
                    dialog.merchantId = vm.merchantId;
                    dialog.callFn = function (responseData) {

                    };
                    dialog.requestData = {
                        merchantId:vm.merchantId
                    };
                    dialog.open();
                };
                //联系人类型
                vm.$contactSelectOpts1 = {
                    "selectId": getTypeId('-contactSelect1'),
                    "options": [],
                    "onSelect": function (v, t, o) {
                        vm.showContact = t == '商户'?true:false;
                        vm.contactCat = v;
                        if (o.children.length) {
                            vm.setSelect(o.children, '-contactSelect2');
                        } else {
                            vm.contactId = 0;
                        }
                    }
                };
                //联系人姓名
                vm.$contactSelectOpts2 = {
                    "selectId": getTypeId('-contactSelect2'),
                    "options": [],
                    "onSelect": function (v, t, o) {
                        vm.contactId = v;
                        if (o.mobilePhone) {
                            vm.hide2 = true;
                            vm.mobile = o.mobilePhone;
                        } else {
                            vm.hide2 = false;
                            vm.mobile = "";
                        }
                        if(vm.testing){
                            util.testing($('.add-edit-create-query')[0],$('.hidden-contactId'));
                        }
                    },
                    "getTemplate": function (tmpl) {
                        var tmps = tmpl.split('MS_OPTION_HEADER');
                        tmpl = 'MS_OPTION_HEADER<ul class="select-list" ms-on-click="selectItem"ms-css-min-width="minWidth">' +
                            '<li class="select-item" ms-repeat="options" ms-css-min-width="minWidth - 16"' +
                            'ms-class="state-selected:$index==selectedIndex" ms-class-last-item="$last" ms-hover="state-hover" ' +
                            'ms-data-index="$index" ms-attr-title="el.text" ms-data-value="el.value">{{el.text}}</li>' +
                            '<li ms-if="showContact"><a style="display: block;line-height: 35px;background: #eee;border-top: 1px solid #ccc;cursor: pointer;text-indent: 10px;" ms-click=addCommunication href="javascript:;">添加联系人</a></li>' +
                            '</ul>';
                        return tmps[0] + tmpl;
                    }
                };
                //来源类型
                vm.$sourceCatSelectOpts = {
                    "selectId": getTypeId('-sourceCatSelectId'),
                    "options": [],
                    "onSelect": function (v, t, o) {
                        vm.sourceCat = v;

                    }
                };
                //来源内容
                vm.$sourceSelectOpts = {
                    "selectId": getTypeId('-sourceSelectId'),
                    "options": [],
                    "onSelect": function (v, t, o) {
                        vm.sourceContent = v;
                    }
                };
                //类型1
                vm.$typeSelectOpts1 = {
                    "selectId": getTypeId('-typeSelect1'),
                    "options": [],
                    "onSelect": function (v, t, o) {
                        vm.resourceRemark = v;
                        if (o.children.length) {
                            vm.hide3 = true;
                            vm.setSelect(o.children, '-typeSelect2');
                        } else {
                            vm.hide3 = false;
                            avalon.getVm(getTypeId('-type2')).setOptions([]);
                            vm.resourceExtraRemark = '';
                        }
                        if(t == '营销活动' || t == '培训' || t == '回访' || t == '微信'){
                            vm.handlerPerson = '无';
                            vm.handlerId = '';
                        }else {
                            vm.handlerId = vm.pageUserId;
                        }
                    }
                };
                //类型2
                vm.$typeSelectOpts2 = {
                    "selectId": getTypeId('-typeSelect2'),
                    "options": [],
                    "onSelect": function (v, t, o) {
                        vm.resourceExtraRemark = v;
                    }
                };

                //处理时间
                vm.$dateOpts = {
                    "calendarId": getTypeId('-dateCalendarId'),
                    "disableTimePanel": false,   //默认禁用时间选择面板
                    "disableSubmit": false,  //默认禁用确定按钮
                    "onSubmit": function (d) {
                        vm.handledTime = moment(d).format('YYYY-MM-DD HH:mm:ss');
                        $(this.widgetElement).hide();
                    }
                };
                vm.openDateCalendar = function () {
                    var meEl = $(this),
                        calendarVm = avalon.getVm(getTypeId('-dateCalendarId')),
                        calendarEl,
                        inputOffset = meEl.offset();
                    if (!calendarVm) {
                        calendarEl = $('<div ms-widget="calendar,$,$dateOpts"></div>');
                        calendarEl.css({
                            "position": "absolute",
                            "z-index": 10000
                        }).hide().appendTo('body');
                        avalon.scan(calendarEl[0], [vm]);
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
                    calendarVm = avalon.getVm(getTypeId('-dateCalendarId'));
                    if (pageVm.handledTime > 0) {
                        calendarVm.focusDate = moment(pageVm.handledTime, 'YYYY-MM-DD HH:mm:ss')._d;
                    } else {
                        calendarVm.focusDate = moment(moment(), 'YYYY-MM-DD HH:mm:ss')._d;
                    }
                    calendarVm.minDate = moment(moment(), 'YYYY-MM-DD HH:mm:ss')._d;
                    calendarEl.css({
                        "top": inputOffset.top + meEl.outerHeight() - 1,
                        "left": inputOffset.left
                    }).show();
                };
                vm.removeHandlerDate = function () {
                    vm.handledTime = 0;
                };
                //提醒
                vm.setRemind = function () {
                    if(!vm.isHanderRemind){return;}
                    var type = $(this).attr('data-id') - 0;
                    vm.isRemind = type;
                };
                //处理人
                vm.$handlerSelectOpts = {
                    "selectId": getTypeId('-handlerSelect'),
                    "options": vm.handlerArr,
                    "onSelect": function (v, t, o) {
                        vm.handlerId = v;
                        if(t == '无'){
                            pageVm.isHanderRemind = false;
                            pageVm.isRemind = 0;
                        }else{
                            pageVm.isHanderRemind = true;
                        }
                    },
                    "getTemplate": function (tmpl) {
                        var tmps = tmpl.split('MS_OPTION_HEADER');
                        tmpl = 'MS_OPTION_HEADER<ul class="select-list" ms-on-click="selectItem"ms-css-min-width="minWidth">' +
                            '<li class="select-item" ms-repeat="options" ms-css-min-width="minWidth - 16"' +
                            'ms-class="state-selected:$index==selectedIndex" ms-class-last-item="$last" ms-hover="state-hover" ' +
                            'ms-data-index="$index" ms-attr-title="el.text" ms-data-value="el.value">{{el.text}}</li>' +
                            '<li><a style="display: block;line-height: 35px;background: #eee;border-top: 1px solid #ccc;cursor: pointer;text-indent: 10px;"  href="javascript:;" ms-click="assign">指派给</a></li>' +
                            '</ul>';
                        return tmps[0] + tmpl;
                    }
                };
                //指派处理人
                vm.$treeOpts = {
                    "persontreeId": getTypeId('-persontree'),
                    "getId": true,
                    "outerHandler": true,
                    "callFn": function (responseData) {
                        vm.handlerArr[2] = {text:responseData.userName,value:responseData.userId};
                        avalon.getVm(getTypeId('-handlerSelect')).setOptions(vm.handlerArr,2);
                        vm.handlerId = responseData.userId;
                    }
                };
                vm.assign = function () {
                    avalon.getVm(getTypeId('-persontree')).getId = true;
                    avalon.getVm(getTypeId('-persontree')).outerHandler = true;
                    avalon.getVm(getTypeId('-persontree')).open();
                };
                //上传
                vm.$AttachUploaderOpts = {
                    "uploaderId": getTypeId('AttachUploaderId'),
                    "uploadifyOpts": {
                        "uploader": erp.BASE_PATH + 'synMerchant/uploadCommonMethod.do',
                        "fileObjName": "myfile",
                        "multi": false, //多选
                        "fileTypeDesc": "上传附件(*.*)",
                        "fileTypeExts": "*.*",
                        "formData": function () {
                            return {
                                "sessionId": loginUserData.sessionId,
                                "flag": 2
                            };
                        },
                        "width": 100,
                        "height": 30,
                        "onUploadProgress": function (file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
                            vm.uping = true;
                            var ratio = (bytesUploaded / bytesTotal) * 100 > 100 ? 100 : (bytesUploaded / bytesTotal) * 100;
                            ratio = ratio + '%';
                            var fileName = file.name;
                            var tip = '';
                            if (ratio === '100%') {
                                tip = '上传完成';
                            } else {
                                tip = '正在上传：' + fileName;
                            }
                            if (!bytesUploaded) {
                                $('.upfile-create').append(
                                    '<div class="upfile-model-create" style="position:relative;width:598px;height: 25px;border:1px solid #ccc;background:#bebebe;">' +
                                        '<p class="loding-create" style="position:absolute;left:0;top:-1px;width:' + ratio + ';height:27px;background:#67bb0d;"></p>' +
                                        '<p class="tips-create" style="position:absolute;left:0;top:0;height:25px;line-height:25px;text-align:center;width:100%;color:#fff;">' + tip + '</p>' +
                                        '</div>'
                                );
                            } else {
                                $('.loding-create').css('width', ratio);
                                $('.tips-create').html(tip);
                            }
                        }
                    },
                    "onSuccessResponseData": function (responseText, file) {
                        responseText = JSON.parse(responseText);
                        if (responseText.flag) {
                            setTimeout(function () {
                                $('.upfile-model-create').remove();
                                var data = responseText.data;
                                vm.attachmentId = data.id;
                                vm.attachments = data;
                            }, 1000);
                        } else {
                            $('.upfile-model-create').remove();
                            util.alert({
                                "content": "上传失败！",
                                "iconType": "error"
                            });
                        }
                        vm.uping = false;
                    }
                };
                //删除附件
                /*vm.removeAttachments = function () {
                    var index = $(this).attr('data-index');
                    util.confirm({
                        "content": '你确定要删除选中的附件吗？',
                        "onSubmit": function () {

                        }
                    });
                    vm.attachments.splice(index, 1);
                };*/
                vm.saveData = function () {//提交
                    var flag;
                    vm.testing = true;
                    var data = {
                        merchantId : vm.merchantId,
                        storeId : vm.storeId,
                        contactCat : vm.contactCat,
                        contactId : vm.contactId,
                        sourceCat : vm.sourceCat,
                        sourceContent : vm.sourceContent,
                        resourceRemark : vm.resourceRemark,
                        resourceExtraRemark : vm.resourceExtraRemark,
                        title : vm.title,
                        content : vm.content,
                        attachmentId : vm.attachmentId,
                        handlerId : vm.handlerId,
                        isRemind : vm.isRemind,
                        handledTime : moment(vm.handledTime)/1
                    };
                    flag = util.testing($('.add-edit-create-query')[0]);
                    if(flag){
                        util.c2s({
                            "url": erp.BASE_PATH + "besRequirement/saveBesRequirement.do",
                            "contentType": "application/json",
                            "data": JSON.stringify(data),
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    util.alert({
                                        "content": "操作成功！",
                                        "iconType": "success",
                                        "onSubmit":function () {
                                            window.location.href = erp.BASE_PATH + "#!/backend/home";
                                        }
                                    });
                                }
                            }
                        });
                    }

                };

            });
            avalon.scan(pageEl[0]);
            /*页面初始化请求渲染*/
            if (pageVm.pageType == 'add') {
                util.c2s({
                    "url": erp.BASE_PATH + "besRequirement/loadBesRequirement.do",
                    "type": "post",
                    "contentType": 'application/json',
                    "data": JSON.stringify({}),
                    "success": function (responseData) {
                        if (responseData.flag) {
                            var data = responseData.data;
                            var newObj = {"text": "请选择","value": 0};
                            data.contact[0].children.unshift(newObj);
                            pageVm.linkSourceType = data;
                            pageVm.setSelect(pageVm.linkSourceType.contact, '-contactSelect1');//联系人
                            pageVm.setSelect(pageVm.linkSourceType.sourceCat, '-sourceCatSelectId');//来源分类
                            pageVm.setSelect(pageVm.linkSourceType.source, '-sourceSelectId');//来源详细
                            pageVm.setSelect(pageVm.linkSourceType.type, '-typeSelect1');//类型
                        }
                    }
                });
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


