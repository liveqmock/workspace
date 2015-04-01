/**
 * 申请开卡模块
 */
define(["avalon", "util", "json", "moment", "text!./opencard.html", "text!./opencard.css", "common", "../../widget/dialog/dialog", '../../widget/form/select'], function (avalon, util, JSON, moment, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    var styleData = styleEl.data('module') || {};

    if (!styleData["opencard"]) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'opencard/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'opencard/');
        }
        styleData["opencard"] = true;
        styleEl.data('module', styleData);
    }
    var module = erp.module.opencard = function (element, data, vmodels) {
        var opts = data.opencardOptions,
            elEl = $(element);
        var getTypeId = function (n) {//设置和获取widget的id;
            if(!erp.openCard){
                erp.openCard = [];
            }
            erp.openCard.push(data.opencardId + n);
            return data.opencardId + n;
        };



        var modelVm = avalon.define(data.opencardId, function (vm) {
            avalon.mix(vm, opts);
            var count = 0;
            function setVipSelectName(str) {
                count++;
                var name = getTypeId(str+count);
                return name;
            }
            //会员等级
            var vipArr = [];
            //商户名称
            vm.merchantName = '';
            vm.displayType = 'add';
            vm.readonly = false;
            vm.sessionId = '';
            vm.uping = false;
            //发送后端数据
            vm.id = 0;
            vm.processId = 0;
            vm.merchantId = 0;
            vm.fesOpenCardApplicationDtls = [];
            //发送后端数据
            vm.$addCardDialogOpts = {//对话框dialog
                "dialogId": getTypeId('-opencardDialogId'),
                "title":"开卡申请",
                "width": 600,
                "getTemplate": function (tmpl) {
                    var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                        footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                            '<div class="ui-dialog-btns" ms-visible="!readonly">' +
                            '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">保&nbsp;存</button>' +
                            '<span class="separation"></span>' +
                            '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button>' +
                            '</div>' +
                            '<div class="ui-dialog-btns" ms-visible="readonly">' +
                            '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">关&nbsp;闭</button>' +
                            '</div>' +
                            '</div>';
                    return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                },
                "onOpen": function () {
                },
                "onClose": function () {
                    vm.fesOpenCardApplicationDtls = [];
                },
                "submit": function (evt) {
                    var flag;
                    var dialog = avalon.getVm(getTypeId('-opencardDialogId'));
                    flag = util.testing($(dialog.widgetElement)[0]);
                    if(flag){
                        var data = vm.fesOpenCardApplicationDtls.$model;
                        for(var i=0;i<data.length;i++){
                            data[i].memberLevel = avalon.getVm(data[i].memberLevel).selectedValue;
                            delete data[i].$memberLevelOpts;
                            delete data[i].upFileFlag;
                            delete data[i].$cardUploaderOpts;
                        }
                        var requestData = {
                            id: vm.id,
                            processId : vm.processId,
                            merchantId : vm.merchantId,
                            fesOpenCardApplicationDtls : data
                        };
                        util.c2s({
                            "url": erp.BASE_PATH + "applySetting/saveOpenCardApply.do",
                            "type": "post",
                            "contentType": 'application/json',
                            "data":  JSON.stringify(requestData),
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    vm.callFn(responseData);
                                    avalon.getVm(getTypeId('-opencardDialogId')).close();
                                }
                            }
                        });
                    }else{//处理跳转到错误的位置
                        var errorEle = $(dialog.widgetElement).find('.valid-error')[0];
                        var pos1 = $(errorEle).closest('.ff-value').position().top;
                        var pos2 = $(errorEle).closest('.model').position().top;
                        var elePosition = pos1+pos2+$('.open-card-con',dialog.widgetElement)[0].scrollTop;
                        $('.open-card-con').scrollTop(elePosition>0?elePosition:0);
                    }

                }
            };

            vm.open = function () {
                util.c2s({
                    "url": erp.BASE_PATH + "applySetting/loadOpenCardApply.do",
                    "type": "post",
                    "contentType": 'application/json',
                    "data": JSON.stringify({"processId": vm.processId}),
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data;
                            vipArr = data.membershipDic;
                            if(vm.displayType == 'read'){
                                vm.readonly = true;
                                function getText(n){
                                    for(var i=0;i<vipArr.length;i++){
                                        if(n == vipArr[i].value){
                                            return vipArr[i].text
                                        }
                                    }
                                }
                                var fesOpenCardApplicationDtls = data.fesOpenCardApplicationDtls;
                                for(var i=0;i<fesOpenCardApplicationDtls.length;i++){
                                    fesOpenCardApplicationDtls[i].memberLevel = getText(fesOpenCardApplicationDtls[i].memberLevel);
                                }
                                vm.fesOpenCardApplicationDtls = data.fesOpenCardApplicationDtls;
                            }else if(vm.displayType == 'edit'){
                                vm.readonly = false;
                                var fesOpenCardApplicationDtls = data.fesOpenCardApplicationDtls;
                                vm.id = data.id;
                                for(var i=0;i<fesOpenCardApplicationDtls.length;i++){
                                    var setValue = function(){
                                        for(var j = 0; j<vipArr.length; j++){
                                            if(vipArr[j].value == fesOpenCardApplicationDtls[i].memberLevel){
                                                return j;
                                            }
                                        }
                                    };
                                    var name = setVipSelectName('vipSelectId');
                                    var upFileName= setVipSelectName('vipUpFileId');
                                    var modelObj = {
                                        attachmentId: fesOpenCardApplicationDtls[i].attachmentId,
                                        cardName: fesOpenCardApplicationDtls[i].cardName,
                                        cardTag: fesOpenCardApplicationDtls[i].cardTag,
                                        cardAmount: fesOpenCardApplicationDtls[i].cardAmount,
                                        validityTerm: fesOpenCardApplicationDtls[i].validityTerm,
                                        isContainFour: fesOpenCardApplicationDtls[i].isContainFour,
                                        isContainSeven: fesOpenCardApplicationDtls[i].isContainSeven,
                                        isContainSecurityCode: fesOpenCardApplicationDtls[i].isContainSecurityCode,
                                        memberLevel:name,
                                        $memberLevelOpts:{
                                            selectId:name,
                                            options:vipArr,
                                            selectedIndex:setValue()
                                        },
                                        $cardUploaderOpts:getUpFileObj(upFileName),
                                        upFileFlag : upFileName,
                                        sysAttachment : fesOpenCardApplicationDtls[i].sysAttachment
                                    };
                                    vm.fesOpenCardApplicationDtls.push(modelObj);
                                }

                            }else{
                                vm.readonly = false;
                                var name = setVipSelectName('vipSelectId');
                                var upFileName= setVipSelectName('vipUpFileId');
                                var modelObj = {
                                    attachmentId: 0,
                                    cardName: '',
                                    cardTag: '',
                                    cardAmount: '',
                                    validityTerm: '',
                                    isContainFour: '0',
                                    isContainSeven: '0',
                                    isContainSecurityCode: '0',
                                    memberLevel:name,
                                    $memberLevelOpts:{
                                        selectId:name,
                                        options:vipArr
                                    },
                                    $cardUploaderOpts:getUpFileObj(upFileName),
                                    upFileFlag : upFileName,
                                    sysAttachment : {
                                        "originalFileName" :"", //"原始文件名";
                                        "fileFullPath" :"" //文件全路径， 不包含主机名和端口
                                    }
                                };
                                vm.fesOpenCardApplicationDtls.push(modelObj);
                            }
                            avalon.getVm(getTypeId('-opencardDialogId')).open();
                        }
                    }
                });
            };
            function getUpFileObj (id) {
                return {//上传
                    "uploaderId": id,
                    "uploadifyOpts": {
                        "uploader": erp.BASE_PATH + 'applySetting/uploadOpenCardAttachment.do',
                            "fileObjName": "myfile",
                            "multi": false, //多选
                            "fileTypeDesc": "上传附件(*.*)",
                            "fileTypeExts": "*.jpg; *.png; *.gif",
                            "formData": function () {
                            return {
                                "sessionId": vm.sessionId
                            };
                        },
                        "width": 100,
                        "height": 30,
                        "onUploadProgress": function (file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
                            vm.uping = true;
                            var ratio = (bytesUploaded / bytesTotal) * 100 > 100 ? 100 : (bytesUploaded / bytesTotal) * 100;
                            ratio = ratio+'%';
                            var fileName = file.name;
                            var tip = '';
                            if (ratio === '100%') {
                                tip = '上传完成';
                            } else {
                                tip = '正在上传：' + fileName;
                            }
                            if (!bytesUploaded) {
                                $('.'+id).append(
                                    '<div class="upfile-model" style="position:relative;width:400px;height: 25px;border:1px solid #ccc;background:#bebebe;">' +
                                        '<p class="upfile-loding" style="position:absolute;left:0;top:-1px;width:' + ratio + ';height:27px;background:#67bb0d;"></p>' +
                                        '<p class="upfile-tips" style="position:absolute;left:0;top:0;height:25px;line-height:25px;text-align:center;width:100%;color:#fff;">' + tip + '</p>' +
                                        '</div>'
                                );
                            } else {
                                $('.upfile-loding').css('width',ratio);
                                $('.upfile-tips').html(tip);
                            }
                        }
                    },
                    "onSuccessResponseData": function (responseText, file) {
                        responseText = JSON.parse(responseText);
                        vm.uping = false;
                        if (responseText.flag) {
                            setTimeout(function () {
                                $('.upfile-model').remove();
                                var data = responseText.data;
                                _.map(vm.fesOpenCardApplicationDtls,function(item,index){
                                    if(item.upFileFlag == id){
                                        item.attachmentId = data.id;
                                        item.sysAttachment.originalFileName =  data.originalFileName;
                                        item.sysAttachment.fileFullPath =  data.fileFullPath;
                                    }
                                });
                                var dialog = avalon.getVm(getTypeId('-opencardDialogId'));
                                util.testing($(dialog.widgetElement)[0],$('.'+id+'input'));
                            }, 1000);
                        } else {
                            $('.upfile-model').remove();
                            util.alert({
                                "content": "上传失败！",
                                "iconType": "error"
                            });
                        }
                    }
                }
            }
            vm.addCar = function(){
                var name = setVipSelectName('vipSelectId');
                var upFileName= setVipSelectName('vipUpFileId');
                var modelObj = {
                    attachmentId: 0,
                    cardName: '',
                    cardTag: '',
                    cardAmount: '',
                    validityTerm: '',
                    isContainFour: '0',
                    isContainSeven: '0',
                    isContainSecurityCode: '0',
                    memberLevel:name,
                    $memberLevelOpts:{
                        selectId:name,
                        options:vipArr
                    },
                    $cardUploaderOpts:getUpFileObj(upFileName),
                    upFileFlag : upFileName,
                    sysAttachment : {
                        "originalFileName" :"", //"原始文件名";
                        "fileFullPath" :"" //文件全路径， 不包含主机名和端口
                    }
                };
                vm.fesOpenCardApplicationDtls.push(modelObj);
                $('.open-card-con').scrollTop($('.open-card-con')[0].scrollHeight);
            };
            vm.remove = function(el){
                vm.fesOpenCardApplicationDtls.remove(el);
            };

            vm.$init = function () {
                elEl.addClass('module-opencard');
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [modelVm].concat(vmodels));
            };
            vm.$remove = function () {
                _.each(erp.openCard, function (widgetId) {
                    var vm = avalon.getVm(widgetId);
                    vm && $(vm.widgetElement).remove();
                });
                erp.openCard.length = 0;
                elEl.removeClass('module-opencard').off().empty();
            };
        });
        return modelVm;
    };
    module.defaults = {
        displayType : 'add',
        processId : 0,
        merchantId : 0,
        merchantName : '',
        sessionId : '',
        callFn: avalon.noop
    };
    return avalon;
});
/*申请开卡模块-end*/