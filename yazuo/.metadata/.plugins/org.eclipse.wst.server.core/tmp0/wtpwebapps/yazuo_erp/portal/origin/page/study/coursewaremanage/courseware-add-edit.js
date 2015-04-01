/**
 * 课件的添加和编辑
 */
define(['avalon', 'util', 'json', '../../../widget/form/form', '../../../widget/dialog/dialog', '../../../widget/uploader/uploader'], function (avalon, util, JSON) {
     var win = this,
        erp = win.erp,
        pageMod = {};
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName,routeData) {
            var formId = pageName + '-form',
                uploaderId = pageName + '-uploader',
                loginUserData = erp.getAppData('user'),
                smvp = loginUserData.smvp;

            var pageVm = avalon.define(pageName, function (vm) {
                vm.state = pageName.split('-').pop();
                vm.navCrumbs = [{
                    "text": "课件管理",
                    "href": "#/study/coursewaremanage"
                }, {
                    "text": (vm.state === "add" ? "添加课件" : "修改课件")
                }];
                vm.belongToCourseList = [];
                vm.coursewareName = ''; //课件名
                vm.speaker = '';  //主讲人
                vm.timeLimit = '';    //学习时限
                //vm.attachmentId = 0;
                vm.attachName = '';
                vm.videoId = "";    //云存储返回的视频id
                vm.hours = '';
                vm.$uploaderOpts = {    //上传配置
                    "uploaderId": uploaderId,
                    "clickRemoveBtn": function (evt) {
                        var uploaderVm = avalon.getVm(uploaderId),
                            itemM = this.$vmodel.$model;
                        uploaderVm.removeFile(itemM.el.id);
                    },
                    "defaultExtKeyData": {
                        //"filePath": "",
                        //"fileName": "",
                        //"data": ""
                    },
                    "uploadifyOpts": {
                        "swf": "http://static.smvp.cn/flash/uploadify.swf",
                        //"uploader": erp.BASE_PATH + 'courseware/uploadVideo.do',
                        "uploader": "http://api.alpha.smvp.cn/entries/upload",
                        /*"formData": {
                            "sessionId": loginUserData.sessionId
                        },*/
                        "formData": {
                            //"token": "OgLeEZWvsFOCxI2hKfhcwSJ3o2JNCtuwUDX5F1yghpZVSeG-r8aeBZ39t6ZXGeeW",
                            "token": smvp.token,
                            "entry": JSON.stringify({
                                //"title": '视频标题', //视频标题，字符串类型
                                //tags: ['标签'],
                                //category:'618128557966044544',
                                "category": _.find(smvp.category, function (itemData) {
                                    return itemData.name === "课件";
                                }).id,
                                "activated": true//是否上线，javascript布尔类型，true表上线，false表下线
                                //description: '我的第一个视频' //视频描述，字符串类型
                            })
                        },
                        "fileObjName": "myfiles",
                        "multi": false,
                        "fileTypeDesc": "视频文件(*.mp4)",
                        "fileTypeExts": "*.mp4",
                        "fileSizeLimit": "1024MB",
                        "width": 116,
                        "height": 30,
                        "onSelect" : function(file) {   //清除上一次的上传信息
                            var uploaderVm = avalon.getVm(uploaderId);
                            if (uploaderVm.uploadList.size() > 1) {
                                uploaderVm.removeFile(uploaderVm.uploadList[0].id);
                            }
                        }
                    },
                    "onSuccessResponseData": function (reponseText, file) {
                        var responseData = JSON.parse(reponseText);
                        //设置上传信息
                       /* vm.attachName = file.name;
                        //清除编辑态下附件上传id
                        vm.attachmentId = 0;
                        return {
                            "filePath": responseData.data[0].relativePath, //http绝对地址
                            "fileName": responseData.data[0].fileName
                        };*/
                        //设置获取的视频id
                        vm.videoId = responseData.id;
                        vm.attachName = responseData.title;
                    }
                };
                vm.$formOpts = {
                    "formId": formId,
                    "field": [{
                        "selector": ".courseware-name",
                        "name": "coursewareName",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "课件名称不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".speaker",
                        "name": "speaker",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "主讲人不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".video-uploader",
                        //"name": "attachmentId",
                        "name": "videoId",
                        "getValue": function () {
                            /*var uploaderVm = avalon.getVm(uploaderId),
                                uploadList = uploaderVm.uploadList,
                                results = [];
                            _.each(uploadList.$model, function (itemData) {
                                if (itemData.uploaded) {
                                    results.push({
                                        "fileName": itemData.fileName,
                                        "originalFileName": itemData.name,
                                        "data": itemData.data
                                    });
                                    return true;
                                }
                            });
                            return results;*/
                            return vm.videoId;
                        },
                        "reset": function () {
                            var uploaderVm = avalon.getVm(uploaderId);
                            if (vm.state == "add") {
                                uploaderVm.removeAllFile();
                            }
                        },
                        "rule": function () {
                            var uploaderVm = avalon.getVm(uploaderId),
                                uploadList = uploaderVm.uploadList;
                            if (!uploadList.size()) {
                                return "上传视频不能为空";
                            } else {
                                return true;
                            }
                        }
                    },{
                        "selector": ".time-limit",
                        "name": "timeLimit",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "学习时限不能为空";
                            } else {
                                if (!/^-?\d+(\.\d{1,1})?$/.test(val)) { //最多保留一位小数
                                    return "请输入有效的学习时限";
                                }
                                if (parseFloat(val, 10) <= 0 || val.slice(0, 2) === '00') {
                                    return "请输入有效地时间值";
                                }
                                return true;
                            }
                        }]

                    },{
                        "selector": ".hours",
                        "name": "hours",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "规定时长不能为空";
                            } else {
                                if (!/^\d{1,6}$/.test(val)) {
                                    return "请输入有效的规定时长";
                                }
                                if (parseFloat(val, 10) === 0 || val.slice(0, 2) === '00') {
                                    return "请输入有效的规定时长";
                                }
                                return true;
                            }
                        }]

                    }],
                    "action": [{
                        "selector": ".f-submit",
                        "handler": function (evt) {
                            var formVm = avalon.getVm(formId),
                                uploaderVm = avalon.getVm(uploaderId),
                                requestData,
                                exer,
                                url;
                            if (formVm.validate()) {
                                exer = function () {
                                    requestData = formVm.getFormData();
                                    //附加编辑态下课件id和视频id
                                    if (vm.state == "edit") {
                                        _.extend(requestData, {
                                            "coursewareId": routeData.params["id"]/1
                                        });
                                        /*if (vm.attachmentId) {
                                            _.extend(requestData, {
                                                "attachmentId": vm.attachmentId
                                            });
                                        }*/
                                        url = erp.BASE_PATH + "courseware/updateCourseware.do";
                                    }else{
                                        url = erp.BASE_PATH + "courseware/saveCourseware.do";
                                    }
                                    //附加视频名称
                                    requestData["originalFileName"] = vm.attachName;
                                    //发送后台请求，编辑或添加
                                    util.c2s({
                                        "url": url,
                                        "type": "post",
                                        "contentType" : 'application/json',
                                        "data": JSON.stringify(requestData),
                                        "success": function (responseData) {
                                            if (responseData.flag) {
                                                util.alert({
                                                    "iconType": "success",
                                                    "content": responseData.message,
                                                    "onSubmit":function () {
                                                        window.location.href = erp.BASE_PATH + "#!/study/coursewaremanage";
                                                   }
                                                });
                                            }
                                        }
                                    });
                                };
                                if (uploaderVm.uploading) {
                                    util.showGlobalMask();  //打开全局遮罩
                                    uploaderVm.$unwatch('uploading');
                                    uploaderVm.$watch('uploading', function (val) {
                                        if (!val) {  //全部上传成功
                                            util.hideGlobalMask();  //隐藏全局遮罩
                                            exer();
                                            uploaderVm.$unwatch('uploading');
                                        }
                                    });
                                } else {    //直接上传
                                    exer();
                                }
                            }
                        }
                    }]
                };
            });
            avalon.scan(pageEl[0]);

            if (pageVm.state == "edit") {
                setCoursewareData();
            }

            function setCoursewareData() {
                util.c2s({
                    "url": erp.BASE_PATH + "courseware/getCoursewareInfoByCoursewareId.do",
                    "type": "post",
                    "contentType" : 'application/json',
                    "data": JSON.stringify({
                        "coursewareId": routeData.params["id"] / 1
                    }),
                    "success": function (responseData) {
                        var data;
                        var uploaderVm = avalon.getVm(uploaderId);
                        if (responseData.flag) {
                            data = responseData.data;
                            pageVm.coursewareName = data.courseware_name;
                            pageVm.speaker = data.speaker;
                            //pageVm.attachmentId = data.attachment_id;
                            pageVm.attachName = data.remark;
                            pageVm.timeLimit = data.time_limit;
                            pageVm.hours = data.hours;
                            pageVm.belongToCourseList = _.map(data.courseInfo, function (itemData) {
                                return {
                                    "courseName": itemData.course_name
                                };
                            });
                            uploaderVm.uploadList.push({
                                "id": util.generateID(),
                                "name": data.attachment_name
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
            var formId = pageName + '-form',
                uploaderId = pageName + '-uploader';
            $(avalon.getVm(uploaderId).widgetElement).remove();
            $(avalon.getVm(formId).widgetElement).remove();
        }
    });

    return pageMod;
});
