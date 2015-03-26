/**
 * 试题的添加和编辑
 */
define(['avalon', 'util', 'json', 'sortable', '../../../module/cwselector/cwselector', '../../../widget/form/form', '../../../widget/dialog/dialog', '../../../widget/uploader/uploader', '../../../widget/form/select'], function (avalon, util, JSON, sortable) {
     var win = this,
        erp = win.erp,
        pageMod = {};
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName, routeData) {
            var cwselectorId = pageName + '-cwselector',
                formId = pageName + '-form',
                uploaderId = pageName + '-uploader',
                urlId = pageName + '-url';
            var coursewareId = routeData.params["id"] / 1;//从上一个页面练级中获取id
            var coursewareName = decodeURIComponent(routeData.params["coursewareName"]);
            var loginUserData = erp.getAppData('user');
            var pageVm = avalon.define(pageName, function (vm) {
                vm.state = pageName.split('-').pop();
                vm.navCrumbs = [{
                    "text": "试题管理",
                    "href": "#/study/questionmanage"
                }, {
                    "text": (vm.state === "add" ? "添加试题" : "修改试题")
                }];
                vm.questionType = (vm.state === "add" ? "single" : "");   //题型
                vm.optionList = [{  //预选答案
                    "optionContent": "",
                    "isRight": true
                }, {
                    "optionContent": "",
                    "isRight": false
                }];
                vm.boolSetting = [{
                    "optionContent": "正确",
                    "isRight": true
                }, {
                    "optionContent": "错误",
                    "isRight": false
                }];
                vm.$urlOpts = {
                    "selectId": urlId,
                    "options": []
                };
                vm.coursewareId = coursewareId ? coursewareId : 0;    //课件
                vm.coursewareName = coursewareName ? coursewareName : ""; //课件名
                vm.questionTitle = '';  //标题
                vm.referenceAnswer = '';    //参考答案
                vm.pptName = '';    //PPT名称
                vm.pptId = 0;   //编辑态下返回的ppt id
                vm.switchQuestionType = function (evt) {
                    var elEl = avalon(this),
                        typeName = elEl.data('name'),
                        formVm = avalon.getVm(formId);
                    vm.questionType = typeName;
                    //如果是单选，保证只有一个为真的选项
                    var firstTrueIndex;
                    if (vm.questionType == "single" || vm.questionType == "bool") {  //单选，保证只有一个正确答案
                        _.each(vm.optionList, function (itemM, i) {
                            if (itemM.isRight && _.isUndefined(firstTrueIndex)) {
                                firstTrueIndex = i;
                            }
                            itemM.isRight = false;
                        });
                        vm.optionList.set(firstTrueIndex || 0, {
                            "isRight": true
                        });
                    }
                    //form reset
                    formVm.clearValidateTip();
                    $('.valid-error-field, .valid-error', formVm.widgetElement).removeClass('valid-error-field');
                    $('.ff-value-tip', formVm.widgetElement).empty();
                };
                vm.addQuestionOption = function (evt) {
                    vm.optionList.push({
                        "optionContent": "",
                        "isRight": false,
                        "id": 0
                    });
                };
                vm.removeOption = function () {
                    var itemM = this.$vmodel.$model,
                        index = itemM.$index;
                    //删除对应的数据
                    vm.optionList.removeAt(index);
                };
                vm.setOptionTrue = function () {
                    var itemM = this.$vmodel.$model,
                        index = itemM.$index;
                    if (vm.questionType == "single" || vm.questionType == "bool") {  //单选和判断题，保证只有一个正确答案
                        _.each(vm.optionList, function (itemM) {
                            itemM.isRight = false;
                        });
                    }
                    vm.optionList.set(index, {
                        "isRight": true
                    });
                };
                vm.setOptionFalse = function () {
                    var itemM = this.$vmodel.$model,
                        index = itemM.$index;
                    vm.optionList.set(index, {
                        "isRight": false
                    });
                };
                vm.setBoolSetting = function () {
                    var itemM = this.$vmodel.$model,
                        index = itemM.$index;

                    vm.boolSetting[0].isRight = false;
                    vm.boolSetting[1].isRight = false;
                    vm.boolSetting.set(index, {
                        "isRight": true
                    });
                };
                vm.$uploaderOpts = {    //上传配置
                    "uploaderId": uploaderId,
                    "uploadList_": [],  //uploadList别名，为了排序
                    "clickRemoveBtn": function (evt) {
                        var uploaderVm = avalon.getVm(uploaderId),
                            itemM = this.$vmodel.$model;
                        uploaderVm.removeFile(itemM.el.id);
                    },
                    "uploadifyOpts": {
                        "uploader": erp.BASE_PATH + 'question/uploadImage.do',
                        "fileObjName": "myfiles",
                        "multi": true, //支持多选
                        "fileTypeDesc": "图片文件(*.gif; *.jpg; *.png; *.jpeg)",
                        "fileTypeExts": "*.gif; *.jpg; *.png; *.jpeg",
                        "fileSizeLimit": "2MB",
                        "formData": {
                            "sessionId": loginUserData.sessionId
                         },
                        "width": 112
                    },
                    "defaultExtKeyData": {
                        "filePath": "",
                        "fileName": "",
                        "data": ""
                    },
                    "onSuccessResponseData": function (reponseText) {
                        var responseData = JSON.parse(reponseText);
                        return {
                            "filePath": responseData.data[0].relativePath, //http绝对地址
                            "fileName": responseData.data[0].fileName
                        };
                    }
                };
                vm.$formOpts = {
                    "formId": formId,
                    "field": [{
                        "selector": ".question-type",
                        "name": "questionType",
                        "getValue": function () {
                            return $(this).filter('.mn-radio-state-selected').data('name');
                        },
                        "rule": function (val) {
                            if (!val) {
                                return "请选择一种题型";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".question-title",
                        "name": "question",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "标题不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".option-list",
                        "name": "answers",
                        "getValue": function () {
                            return vm.optionList.$model;
                        },
                        "reset": function () {
                            $('.ff-value-tip', this).removeClass("valid-error").empty().hide();
                            $('.valid-error-field', this).removeClass('valid-error-field');
                            $('textarea', this).val("");
                        },
                        "rule": function (val) {
                            /*var isSomeEmpty = _.some(val, function (itemData) {
                                return !itemData.optionContent.length;
                            });
                            if (isSomeEmpty) {
                                return "请输入答案不能为空";
                            } else {
                                return true;
                            }*/
                            var isEmptyIndexs = [],
                                withRightOption = false;
                            _.each(val, function (itemData, i) {
                                if(!itemData.optionContent.length) {
                                    isEmptyIndexs.push(i);
                                }
                                //必须保证有一个是正确答案
                                if (itemData.isRight) {
                                    withRightOption = true;
                                }
                            });

                            return {
                                "handler": function (isPassed) {
                                    var that = this;
                                    $('.ff-value-tip', this).removeClass("valid-error").empty().hide();
                                    $('.valid-error-field', this).removeClass('valid-error-field');

                                    if (!withRightOption) {
                                        util.alert({
                                            "iconType": "error",
                                            "content": "请设置至少一个正确答案"
                                        });
                                    }
                                    if (!isPassed) {
                                        _.each(isEmptyIndexs, function (index) {
                                            var itemEl = $('.option-item', that).eq(index),
                                                tipEl = $('.ff-value-tip', itemEl),
                                                contentEl = $('.option-content', itemEl);
                                            tipEl.addClass("valid-error").text("答案不能为空").slideDown('fast');  //添加错误状态className
                                            contentEl.addClass('valid-error-field');
                                        });
                                    }
                                },
                                "isPassed": !isEmptyIndexs.length && withRightOption
                            };
                        }
                    }, {
                        "selector": ".bool-answer-field",   //判断题,和单选多选互斥
                        "name": "answers",
                        "getValue": function () {
                            return vm.boolSetting.$model;
                        }
                    }, {
                        "selector": ".reference-answer",
                        "name": "referenceAnswer",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "参考答案不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".url",
                        "name": "url",
                        "excludeHidden": true,
                        "getValue": function () {
                            return avalon.getVm(urlId).selectedValue;
                        },
                        "rule": function (val) {
                            if (!val) {
                                return "URL不能为空";
                            } else {
                                return true;
                            }
                        }
                    },{
                        "selector": ".pptName",
                        "name": "pptName",
                        "rule": ["noempty", function (val, rs) {
                            if (rs[0] !== true) {
                                return "PPT名称不能为空";
                            } else {
                                return true;
                            }
                        }]
                    }, {
                        "selector": ".upload-panel",
                        "name": "fileName",
                        "getValue": function () {
                            var uploaderVm = avalon.getVm(uploaderId),
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
                            return results;
                        },
                        "rule": function () {
                            var uploaderVm = avalon.getVm(uploaderId),
                                uploadList = uploaderVm.uploadList;
                            if (!uploadList.length) {
                                return "上传ppt不能为空";
                            } else {
                                return true;
                            }
                        }
                    }, {
                        "selector": ".courseware-selected",
                        "name": "coursewareId",
                        "getValue": function () {
                            return vm.coursewareId;
                        },
                        "rule": function (val) {
                            if (!val) {
                                return "请选择归属课件";
                            } else {
                                return true;
                            }
                        }
                    }],
                    "action": [{
                        "selector": ".f-submit",
                        "handler": function (evt) {
                            var formVm = avalon.getVm(formId),
                                uploaderVm = avalon.getVm(uploaderId),
                                requestData,
                                url,
                                exer;
                            if (formVm.validate()) {
                                exer = function () {
                                    requestData = formVm.getFormData();
                                    //附加题id
                                    if (vm.state == "edit") {
                                        _.extend(requestData, {
                                            "questionId": routeData.params["id"] / 1
                                        });
                                        url = erp.BASE_PATH + 'question/updateQuestion.do';

                                        if (requestData["questionType"] == "ppt") { //表明是ppt类型，附加ppt id信息
                                            requestData["pptId"] = vm.pptId;
                                        }
                                    }else{
                                        url = erp.BASE_PATH + 'question/saveQuestion.do';
                                    }
                                    //发送后台请求，编辑或添加
                                    util.c2s({
                                        "url": url,
                                        "type": "post",
                                        "contentType" : 'application/json',
                                        "data": JSON.stringify(requestData),
                                        "success": function (responseData) {
                                            if (responseData.flag == 1) {
                                                util.alert({
                                                    "iconType": "success",
                                                    "content": responseData.message,
                                                    "onSubmit":function () {
                                                        util.jumpPage('#/study/questionmanage');
                                                    }
                                                });
                                            }
                                        }
                                    });
                                };
                                if (vm.questionType == "ppt") { //等待图片全部上传成功后再试
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
                                } else {    //其他不带上传的提交
                                    exer();
                                }
                            }
                        }
                    }]
                };
                vm.$cwselectorOpts = {
                    "cwselectorId": cwselectorId,
                    "multi": false,
                    "onListReady": function (coursewareListVm) {
                        _.some(coursewareListVm.$model, function (itemData, i) {
                            if (itemData["id"] == vm.coursewareId) {
                                coursewareListVm.set(i, {
                                    "isSelected": true
                                });
                                return true;
                            }
                        });
                    },
                    "onSubmit": function (result) {
                        if (result.length) {
                            vm.coursewareId = result[0].id;    //课件
                            vm.coursewareName = result[0].coursewareName; //课件名
                        }
                    }
                };
                vm.openCourseware = function () {
                    avalon.getVm(cwselectorId).open();
                };
                /**
                 * 删除所有的上传文件
                 */
                vm.removeAllUploadBtn = function (evt) {
                    var uploaderVm = avalon.getVm(uploaderId);
                    uploaderVm.removeAllFile();
                };
            });
            avalon.scan(pageEl[0]);

            pageVm.$watch('questionType', function (val) {
                var uploaderVm;
                if (val == "ppt") {
                    uploaderVm = avalon.getVm(uploaderId);
                    uploaderVm.uploadList.$watch('length', function () {
                        var tempList = [],
                            sameNameFileData;
                        //过滤同名文件
                        _.each(uploaderVm.uploadList.$model, function (itemData) {
                            if (!_.some(tempList, function (itemData2) {
                                return itemData2.name == itemData.name;
                            })) {
                                tempList.push(itemData);
                            } else {
                                sameNameFileData = itemData;
                            }
                        });
                        if (sameNameFileData && (tempList.length !== uploaderVm.uploadList.size())) {
                            uploaderVm.uploadList = tempList;
                            //终止同名文件上传
                            if (!sameNameFileData.uploaded) {
                                $('#' + uploaderVm.uploaderId).uploadify('cancel', sameNameFileData.id);
                            }
                        }
                    });
                    uploaderVm.uploaded.$watch('length', function () {
                        var tempList = [];
                        //只显示成功上传后的file
                        tempList = _.filter(uploaderVm.uploadList.$model, function (itemData) {
                            return itemData.uploaded;
                        });
                        //console.info(uploaderVm.uploadList);
                        //再排下序
                       /* uploaderVm.uploadList_ = _.sortBy(tempList, function (itemData) {
                            return itemData.name;
                        });*/
                        uploaderVm.uploadList_ = tempList;   //依赖拖拽排序
                    });
                    avalon.scan($('.ppt-field-wrapper .upload-panel', pageEl).removeAttr('ms-skip')[0], [uploaderVm]);
                    //上传排序
                    $(".upload-list", pageEl).sortable({
                        onDrop: function  (itemEl, container, _super) {
                            //重排uploadList顺序
                            var uploadItemsEl = $('.upload-item', pageEl),
                                sortedList = [];
                            uploadItemsEl.each(function () {
                                var fileId = avalon(this).data('fileId');
                                sortedList.push(_.find(uploaderVm.uploadList.$model, function (itemData) {
                                    return itemData.id == fileId;
                                }));
                            });
                            uploaderVm.uploadList.clear();
                            uploaderVm.uploadList = sortedList;
                            uploaderVm.uploadList_ = sortedList;
                            _super(itemEl);
                        }
                    });

                    pageVm.$unwatch('questionType');
                }
            });

            if (pageVm.state == "edit") {
                setQuestionData();
            } else {
                fetchUrlData();
            }

            //设置默认的实操题地址选项
            function fetchUrlData (callback) {
                util.ajax({
                    "url": erp.PAGE_PATH + "study/coursestudy/practiceexam/practiceexam.json",
                    "type": "get",
                    "async": false, //同步模式
                    "success": function (responseData) {
                        avalon.getVm(urlId).setOptions(responseData.rows);
                        callback && callback();
                    }
                });
            }
            //设置指定id试题的默认信息
            function setQuestionData() {
                util.c2s({
                    "url": erp.BASE_PATH + "question/getQuestionOptionByQId.do",
                    "type": "post",
                    "contentType" : 'application/json',
                    "data": JSON.stringify({
                        "questionId": routeData.params["id"] / 1
                    }),
                    "success": function (responseData) {
                        var data,
                            uploaderVm;
                        if (responseData.flag == 1) {
                            data = responseData.data;
                            pageVm.questionType = converToQuestionType(data.question_type);
                            pageVm.coursewareId = data.coursewareid;
                            pageVm.coursewareName = data.courseware_name;
                            pageVm.questionTitle = data.question;
                            pageVm.pptName = data.pptName;
                            fetchUrlData(function () {
                                avalon.getVm(urlId).selectValue(data.url);
                            });
                            //alert(data.pptName);
                            if (pageVm.questionType == "bool") {
                                pageVm.boolSetting = _.map(data.questionOptions, function (itemData) {
                                    return {
                                        "isRight": itemData["is_right"],
                                        "optionContent": itemData["option_content"],
                                        "id": itemData["id"]
                                    };
                                });
                            } else if (pageVm.questionType == "single" || pageVm.questionType == "multi") {
                                pageVm.optionList = _.map(data.questionOptions, function (itemData) {
                                    return {
                                        "isRight": itemData["is_right"],
                                        "optionContent": itemData["option_content"],
                                        "id": itemData["id"]
                                    };
                                });
                            }
                            //上传ppt
                            if (data.question_type == 3) {
                                uploaderVm = avalon.getVm(uploaderId);
                                uploaderVm.uploadList = _.map(data.pptList, function (itemData) {
                                    return {
                                        "id": util.generateID(),    //生成一个唯一id
                                        "uploaded": true,    //指示已上传完毕
                                        "filePath": erp.BASE_PATH + data.relativePath + '/' + itemData.ppt_dtl_name,
                                        "name": itemData.original_file_name,
                                        "data": JSON.stringify(itemData)  //表明是一个编辑态下已存在的文件
                                    };
                                });
                                uploaderVm.uploaded = _.map(uploaderVm.uploadList.$model, function (itemData) {
                                    return itemData.id;
                                });
                                pageVm.pptId = data.ppt_id;

                            }
                        }
                    }
                });
            }

            function converToQuestionType(questionTypeValue) {
                var questionType;
                switch (questionTypeValue) {
                    case "0":
                        questionType = "single";
                        break;
                    case "1":
                        questionType = "multi";
                        break;
                    case "2":
                        questionType = "bool";
                        break;
                    case "5":
                        questionType = "qa";
                        break;
                    case "3":
                        questionType = "ppt";
                        break;
                    case "4":
                        questionType = "practice";
                        break;
                    default:
                        questionType = "";
                        break;
                }
                return questionType;
            }

            function converToQuestionTypeValue(questionType) {
                var questionTypeValue;
                switch (questionType) {
                    case "single":
                        questionTypeValue = "0";
                        break;
                    case "multi":
                        questionTypeValue = "1";
                        break;
                    case "bool":
                        questionTypeValue = "2";
                        break;
                    case "qa":
                        questionTypeValue = "5";
                        break;
                    case "ppt":
                        questionTypeValue = "3";
                        break;
                    case "practice":
                        questionTypeValue = "4";
                        break;
                    default:
                        questionTypeValue = "";
                        break;
                }
                return questionTypeValue;
            }
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var cwselectorId = pageName + '-cwselector',
                formId = pageName + '-form',
                urlId = pageName + '-url',
                uploaderId = pageName + '-uploader',
                uploaderVm = avalon.getVm(uploaderId);

            uploaderVm && $(uploaderVm.widgetElement).remove();
            $(avalon.getVm(urlId).widgetElement).remove();
            $(avalon.getVm(cwselectorId).widgetElement).remove();
            $(avalon.getVm(formId).widgetElement).remove();
        }
    });

    return pageMod;
});
