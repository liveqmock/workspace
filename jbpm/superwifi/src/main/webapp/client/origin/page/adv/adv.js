/**
 * 广告管理
 */
define(['avalon', 'util', 'json', 'moment', '../../widget/dialog/dialog', '../../widget/form/form', '../../widget/uploader/uploader', '../../widget/editor/editor'], function (avalon, util, JSON, moment) {
    var win = this,
        app = win.app,
        pageMod = {};
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName, routeData) {
            var uploaderId = pageName + '-uploader',
                articleContentId = pageName + '-article-content';
            var loginUserData = app.getAppData('user');
            var pageVm = avalon.define(pageName, function (vm) {
                vm.uploaderImg = "";
                vm.initImg = "";
                vm.dataImg = "";
                vm.currentLink = "";
                vm.currentMessage = "";
                vm.oldMessage = "";
                vm.showUpload = false;
                vm.editOrSave = "编辑";
                vm.editOrSaveTwo = "编辑";
                vm.editOrSaveThree = "编辑";
                vm.isSendMessage = true;
                vm.oldIsSend = true;
                vm.showPageSet = false;
                vm.linkType = 1;
                vm.showMessageSet = false;
                vm.wordNum = 0;
                vm.messageNum = 0;
                vm.showCard = false;
                vm.cardList = [];
                vm.cardNameString = "请选择会员卡";
                vm.messageId = "";
                vm.imgType = 0;
                vm.isCrmMerchant = false;
                //广告图保存和编辑
                vm.editProtal = function () {
                    if (pageVm.editOrSave == "保存") {
                        if (pageVm.dataImg) {
                            util.c2s({
                                "url": app.BASE_PATH + "controller/merchant/savePortalPic.do",
                                "data": {"myfile": pageVm.dataImg},
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        pageVm.showUpload = false;
                                        pageVm.editOrSave = "编辑";
                                    }
                                }
                            });
                        } else {
                            pageVm.cancelProtal();
                            vm.imgType = 1;
                        }
                    } else {
                        pageVm.showUpload = true;
                        pageVm.editOrSave = "保存";
                        pageVm.imgType = 1;
                    }
                };
                vm.cancelProtal = function () {
                    pageVm.showUpload = false;
                    pageVm.editOrSave = "编辑";
                    pageVm.uploaderImg = pageVm.initImg;
                };
                //选中select
                vm.checkedSel = function () {
                    pageVm.linkType = $(this).data('type');
                    pageVm.currentLink = $(this).data('typeName');
                };
                //连接方式编辑
                vm.editLinkType = function () {
                    var requestData = {};
                    if (pageVm.editOrSaveTwo == "保存") {
                        if (pageVm.linkType == 1) {
                            _.extend(requestData, {"memberCenterType": 1, "cardTypeList": [], "html": ""})
                        } else if (pageVm.linkType == 2) {
                            _.extend(requestData, {"memberCenterType": 2, "cardTypeList": [], "html": avalon.getVm(articleContentId).core.getContent()})
                        } else if (pageVm.linkType == 3) {
                            _.extend(requestData, {"memberCenterType": 3, "cardTypeList": getSelectValue($('#card-ul li span')), "html": ""})
                        }
                        util.c2s({
                            "url": app.BASE_PATH + "controller/merchant/updateHomePageInfo.do",
                            "data": requestData,
                            "success": function (responseData) {
                                if (responseData.flag) {
                                    pageVm.showPageSet = false;
                                    pageVm.editOrSaveTwo = "编辑";
                                    pageVm.showCard = false;
                                }
                            }
                        });
                    } else {
                        pageVm.showPageSet = true;
                        pageVm.editOrSaveTwo = "保存";
                        pageVm.imgType = 2;
                    }
                };
                vm.cancelLinkType = function () {
                    pageVm.showPageSet = false;
                    pageVm.editOrSaveTwo = "编辑";
                    pageVm.showCard = false;
                    initLinkPage();
                };
                //会员卡连接
                vm.showCardFn = function () {
                    var parentSel = $(this).parent();
                    $('.down-list-box').css({'top': parentSel.offset().top - 26, 'left': parentSel.offset().left})
                    pageVm.showCard = pageVm.showCard ? false : true;
                };
                vm.cardCheck = function () {
                    if ($(this).hasClass('checked-st')) {
                        $(this).removeClass('checked-st');
                    } else {
                        $(this).addClass('checked-st');
                    }
                };
                vm.saveCheck = function () {
                    var nameArr = getSelectName($('#card-ul li span'));
                    if (nameArr.length > 0) {
                        $('.down-outer-c').html(nameArr.join(','));
                    } else {
                        $('.down-outer-c').html('请选择会员卡');
                    }
                    pageVm.showCard = false;
                };
                vm.cancelCheck = function () {
                    pageVm.showCard = false;
                };
                //短信设置
                vm.showMessage = function () {
                    if (pageVm.editOrSaveThree == "保存") {
                        util.c2s({
                            "url": app.BASE_PATH + "controller/messageInfo/addMessageInfo.do",
                            "data": {"id": pageVm.messageId, "isSendMessage": pageVm.isSendMessage, 'content': pageVm.currentMessage, 'length': pageVm.wordNum, 'counts': pageVm.messageNum, 'messageType': 1},
                            "success": function (responseData) {
                                if (responseData.flag) {
                                    pageVm.showMessageSet = false;
                                    pageVm.editOrSaveThree = "编辑";
                                    initMessage();
                                }
                            }
                        });
                    } else {
                        pageVm.showMessageSet = true;
                        pageVm.editOrSaveThree = "保存";
                        pageVm.imgType = 3;
                    }
                };
                vm.messageCheck = function () {
                    pageVm.isSendMessage = pageVm.isSendMessage ? false : true;
                };
                vm.cancelMessage = function () {
                    //计算短信字数
                    pageVm.showMessageSet = false;
                    pageVm.editOrSaveThree = "编辑";
                    pageVm.currentMessage = pageVm.oldMessage;
                    pageVm.isSendMessage = pageVm.oldIsSend;
                };
                vm.countWord = function () {
                    var textValue = $('.message-text').val().length;
                    pageVm.wordNum = textValue;
                    pageVm.messageNum = Math.ceil(textValue / 70);
                };
                //富文本
                vm.$articleContentOpts = {
                    "editorId": articleContentId,
                    "ueditorOptions": {
                        "UEDITOR_HOME_URL": app.WIDGET_PATH + 'editor/ueditor/',
                        "serverUrl": app.BASE_PATH + 'jsp/controller.jsp',
                        //"serverUrl": app.BASE_PATH + 'index.jsp',
                        "initialFrameHeight": 300,
                        "toolbars": [
                            [
                                'fullscreen', 'source', '|', 'undo', 'redo', '|',
                                'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                                'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                                'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                                'directionalityltr', 'directionalityrtl', 'indent', '|',
                                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                                'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|', 'simpleupload', 'insertimage'
                            ]
                        ]
                    }
                };
                //首页推荐图片
                vm.$uploaderOpts = {    //上传配置
                    "uploaderId": uploaderId,
                    "uploadifyOpts": {
                        "uploader": app.BASE_PATH + 'controller/merchant/upLoadPortalPic.do',
                        "formData": {
                            "sessionid": loginUserData.sessionid || ""
                        },
                        "fileObjName": "myfile",
                        "multi": false,
                        "fileTypeDesc": "图片文件(*.gif; *.jpg; *.png; *.jpeg)",
                        "fileTypeExts": "*.gif; *.jpg; *.png; *.jpeg",
                        "fileSizeLimit": "70KB",
                        "width": 102,
                        "height": 30,
                        "onSelect": function (file) {   //清除上一次的上传信息
                            var uploaderVm = avalon.getVm(uploaderId);
                            if (uploaderVm.uploadList.size()) {
                                uploaderVm.removeFile(uploaderVm.uploadList[0].id);
                            }
                        }
                    },
                    "onSuccessResponseData": function (reponseText, file) {
                        var responseData = JSON.parse(reponseText),
                            data;
                        if (responseData.flag) {
                            data = responseData.data;
                            vm.uploaderImg = data.imageAddr;
                            vm.dataImg = data.imageName;
                        } else {
                            util.alert({
                                "content": responseData.message,
                                "iconType": "error"
                            });
                        }
                    }
                };
            });
            avalon.scan(pageEl[0]);
            initProtal();
            initMessage();
            getCardList();

            //获取广告图
            function initProtal() {
                util.c2s({
                    "url": app.BASE_PATH + "controller/merchant/getPortalPicByMerchantId.do",
                    "success": function (responseData) {
                        if (responseData.flag) {
                            pageVm.initImg = pageVm.uploaderImg = responseData.data ? responseData.data : "";
                        }
                    }
                });
            }

            //获取短信
            function initMessage() {
                util.c2s({
                    "url": app.BASE_PATH + "controller/messageInfo/getMessageInfo.do",
                    "success": function (responseData) {
                        var data = responseData.data.messageInfo;
                        if (responseData.flag) {
                            pageVm.oldMessage = pageVm.currentMessage = data.content;
                            pageVm.isSendMessage = pageVm.oldIsSend = data.isSendMessage;
                            pageVm.messageId = data.id;
                            pageVm.countWord();
                        }
                    }
                });
            }

            //获取连接信息
            function initLinkPage() {
                var articleContentVm = avalon.getVm(articleContentId);
                util.c2s({
                    "url": app.BASE_PATH + "controller/merchant/getHomePageInfo.do",
                    "success": function (responseData) {
                        if (responseData.flag) {
                            var data = responseData.data, ids = data.cardTypeIds;
                            pageVm.currentLink = data.memberCenterName ? data.memberCenterName : "还没有设置";
                            pageVm.linkType = data.memberCenterType;
                            pageVm.isCrmMerchant = data.isCrmMerchant;
                            //articleContentVm.core.addListener("ready", function () {
                            try {
                                articleContentVm.core.setContent(data.html);
                            } catch (evt) {
                                articleContentVm.core.addListener("ready", function () {
                                    articleContentVm.core.setContent(data.html);
                                });
                            }

                            //});
                            if (ids.length > 0) {
                                setSelect($('#card-ul li span'), ids);
                                var nameArr = getSelectName($('#card-ul li span'));
                                pageVm.cardNameString = nameArr.join(',');
                            }
                        }
                    }
                });
            }

            //获取卡
            function getCardList() {
                util.c2s({
                    "url": app.BASE_PATH + "controller/merchant/getCardTypeIdsFromCrm.do",
                    "success": function (responseData) {
                        if (responseData.flag) {
                            pageVm.cardList = responseData.data;
                            initLinkPage();
                        }
                    }
                });
            }

            //获取选中的select
            function getSelectValue(sel) {
                var arr = [];
                sel.each(function () {
                    if ($(this).hasClass("checked-st")) {
                        arr.push($(this).data("value"));
                    }
                });
                return arr;
            }

            function getSelectName(sel) {
                var arr = [];
                sel.each(function () {
                    if ($(this).hasClass("checked-st")) {
                        arr.push($(this).data("name"));
                    }
                });
                return arr;
            }

            //设置选中
            function setSelect(sel, arrList) {
                if (arrList == "remove") {
                    sel.removeClass('checked-st');
                } else {
                    _.each(arrList, function (item) {
                        sel.each(function () {
                            if ($(this).data("value") == item) {
                                $(this).addClass("checked-st");
                            }
                        });
                    });
                }
            }
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var uploaderId = pageName + '-uploader';
            $(avalon.getVm(uploaderId).widgetElement).remove();
        }
    });

    return pageMod;
});
