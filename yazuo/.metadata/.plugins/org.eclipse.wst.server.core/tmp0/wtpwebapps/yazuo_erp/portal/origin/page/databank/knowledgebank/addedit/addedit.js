/**
 * 知识库-knowledgebank
 */
define(['avalon', 'util', 'json', '../../../../widget/pagination/pagination','../../../../widget/form/select','../../../../widget/uploader/uploader', '../../../../widget/autocomplete/autocomplete', '../../../../widget/tree/tree'], function (avalon, util, JSON) {
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
                    "text":"知识库",
                    "href":"#/databank/knowledgebank"
                }, {
                    "text":'创建知识'
                }];
                vm.titleType = '创建知识';
                vm.pageType = routeData.params["type"];
                vm.id = routeData.params["id"];
                vm.classId = routeData.params["classId"];
                if(vm.pageType == 'edit'){
                    vm.titleType = '修改知识';
                    vm.navCrumbs[1].text = '修改知识';
                }
                var loginUserData = erp.getAppData('user');
                //页面逻辑用变量
                vm.displayTree = false;
                vm.kindText = '';
                vm.uping = false;
                vm.attachment = {};//附件
                //页面逻辑用变量end
                //发送后台数据
                vm.requestData = {
                    id: null,
                    kindId: null,
                    title:'',
                    merchantId:null,
                    businessTypeId:null,
                    description:'',
                    analysis:'',
                    resolution:'',
                    successfulCase:'',
                    talkingSkills:'',
                    attachmentId:null
                };
                vm.$ztreeOpts = {//分类树
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
                                        vm.displayTree = false;
                                        vm.requestData.kindId = node.id;
                                        vm.kindText=node.name;
                                        util.testing($('.add-edit-knowledge')[0],$('.create-class-input-hidden'));
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
                vm.treeShow = function(){
                    $('.create-knowledge-tree').css({
                        left:$('.addedit-create-tree-dom').position().left,
                        top:$('.addedit-create-tree-dom').position().top+31
                    });
                    vm.displayTree = true;
                };
                $('body').on('click',function(event){
                    event = event || window.event;
                    var target = event.target || event.srcElement;
                    if(!$('.addedit-create-tree-dom').has(target).length && !$('.create-knowledge-tree').has(target).length){
                        vm.displayTree = false;
                    }
                });
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
                            vm.requestData.merchantId = null;
                            callback([]);//空查询显示空
                        }
                    },
                    "onSelect": function (selectedValue) {
                        var merchantId = avalon.getVm(getTypeId('-brandId')).getOptionByValue(selectedValue).merchant_id;
                        vm.requestData.merchantId = merchantId;
                    }
                };
                //业态
                vm.$schedBusinessOpts = {
                    "selectId": getTypeId('-business'),
                    "options": [],
                    "onSelect": function(v,t,o){
                        vm.requestData.businessTypeId = v;
                    }
                };
                //上传
                vm.$dataUploaderOpts = {
                    "uploaderId": getTypeId('AttachUploaderId'),
                    "uploadifyOpts": {
                        "uploader": erp.BASE_PATH + 'knowledge/upload.do',
                        "fileObjName": "file",
                        "multi": false, //多选
                        "fileTypeDesc": "上传附件(*.*)",
                        "fileTypeExts": "*.*",
                        "formData": function () {
                            return {
                                "sessionId": loginUserData.sessionId
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
                                $('.upfile-create-knowledge').append(
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

                                vm.requestData.attachmentId = data.id;
                                vm.attachment = data;
                                vm.uping = false;
                            }, 1000);
                        } else {
                            $('.upfile-model-create').remove();
                            vm.uping = false;
                            util.alert({
                                "content": "上传失败！",
                                "iconType": "error"
                            });
                        }
                    }
                };
                //提交数据
                vm.saveData = function(){
                    var oForm = $('.add-edit-knowledge')[0];
                    var flag = true;
                    flag = util.testing(oForm);
                    if(flag){
                        util.c2s({
                            "url": erp.BASE_PATH + '/knowledge/save.do',
                            "type": "post",
                            "contentType" : 'application/json',
                            "data": JSON.stringify(vm.requestData.$model),
                            "success": function(responseData){
                                var data;
                                if(responseData.flag){
                                    util.alert({
                                        "content": "操作成功！",
                                        "iconType": "success",
                                        "onSubmit":function () {
                                            window.location.href = erp.BASE_PATH + "#!/databank/knowledgebank";
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
            (function requestServer(){
                if(pageVm.pageType == 'add'){
                    var requestData=JSON.stringify({});
                }else{
                    var requestData=JSON.stringify({id:pageVm.id});
                }
                util.c2s({
                    "url": erp.BASE_PATH + 'knowledge/get.do',
                    "type": "post",
                    "contentType" : 'application/json',
                    "data": requestData,
                    "success": function(responseData){
                        var data;
                        if(responseData.flag){
                            data = responseData.data;
                            var businessTypes = _.map(data.businessTypes,function(item,index){//业态
                                return {text:item['typeText'],value:item['id']}
                            });
                            businessTypes.unshift(
                                {
                                    text:'不限',
                                    value:''
                                }
                            );
                            var treeVm = avalon.getVm(getTypeId('-classZtreeId'));//分类树
                            treeVm.updateTree(pageVm.eachArr(data['knowledgeKinds']));//分类树
                            avalon.getVm(getTypeId('-business')).setOptions(businessTypes);

                            if(pageVm.pageType == 'add'){//是否列表也带过来的id
                                if(parseInt(pageVm.classId)){
                                    pageVm.requestData.kindId = pageVm.classId;
                                    _.map(data['knowledgeKinds'],function(item,index){
                                        if(item.id == pageVm.requestData.kindId){
                                            pageVm.kindText = item.title;
                                        }
                                    })
                                }
                            }
                            if(data['knowledge']){//是否是编辑
                                avalon.getVm(getTypeId('-business')).selectValue(data['knowledge']['businessTypeId']);
                                pageVm.requestData = {
                                    id: data['knowledge'].id,
                                    kindId: data['knowledge'].kindId,
                                    title:data['knowledge'].title,
                                    merchantId:data['knowledge'].merchantId,
                                    businessTypeId:data['knowledge'].businessTypeId,
                                    description:data['knowledge'].description,
                                    analysis:data['knowledge'].analysis,
                                    resolution:data['knowledge'].resolution,
                                    successfulCase:data['knowledge'].successfulCase,
                                    talkingSkills:data['knowledge'].talkingSkills,
                                    attachmentId:data['knowledge'].attachmentId
                                };
                                var kindId = data['knowledge']['kindId'];//分类id
                                 _.map(data['knowledgeKinds'],function(item,index){
                                    if(item.id == kindId){
                                        pageVm.kindText = item.title;
                                    }
                                });
                                var autoTemplateVm = avalon.getVm(getTypeId('-brandId'));//商户名称
                                autoTemplateVm.inputText = data['knowledge']['merchantName'];
                                pageVm.attachment = data['knowledge']['attachment'];
                            }
                        }
                    }
                });
            }());
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


