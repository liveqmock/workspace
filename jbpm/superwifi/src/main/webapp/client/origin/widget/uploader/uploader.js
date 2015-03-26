/**
 * 上传组件，依赖第三方库 uploadify
 */
define(["avalon", "uploadify"], function (avalon, uploadify) {
    var counts = 0;
    var widget = avalon.ui.uploader = function (element, data, vmodels) {
        var elEl = $(element);
        var opts = data.uploaderOptions;
        var getPureFileData = function () {
            return _.extend({
                "creationdate": new Date(),
                "filestatus": -1,
                "id": "",
                "index": -1,
                "modificationdate": new Date(),
                "name": "",
                "post": null,
                "size": 0,
                "type": "*.*",
                "uploaded": false,
                "errorCode": 0
            }, opts.defaultExtKeyData);
        };
        elEl.addClass("ui-uploader");
        var vmodel = avalon.define(data.uploaderId, function (vm) {
            var uploaderId = 'uploader-' + counts;
            counts++;
            avalon.mix(vm, opts);
            vm.widgetElement = element;
            vm.$skipArray = ["widgetElement", "uploadifyOpts", "uploaderId"];//这些属性不被监控
            vm.uploaderId = uploaderId;
            vm.uploadList = []; //所有操作的文件
            vm.uploaded = [];   //成功上传的文件
            vm.failed = []; //上传失败的文件
            vm.completed = [];   //成功和失败的文件
            vm.uploading = false; //上传状态
            vm._selectFile = function (file) {
                vm.updateList(_.extend(getPureFileData(), file));
            };
            vm.removeFile = function (fileId) {
                //删除失败的file
                var atIndex = -1;
                _.some(vm.uploadList, function (itemData, i) {
                    if (itemData.id == fileId) {
                        atIndex = i;
                        return true;
                    }
                });
                if (atIndex > -1) {
                    vm.uploadList.removeAt(atIndex);
                }
                //删除失败的file id 记录
                atIndex = -1;
                _.some(vm.uploaded, function (itemData, i) {
                    if (itemData == fileId) {
                        atIndex = i;
                        return true;
                    }
                });
                if (atIndex > -1) {
                    vm.uploaded.removeAt(atIndex);
                }
                //删除失败的file id 记录
                atIndex = -1;
                _.some(vm.failed, function (itemData, i) {
                    if (itemData == fileId) {
                        atIndex = i;
                        return true;
                    }
                });
                if (atIndex > -1) {
                    vm.failed.removeAt(atIndex);
                }
                //删除失败的file id 记录
                atIndex = -1;
                _.some(vm.completed, function (itemData, i) {
                    if (itemData == fileId) {
                        atIndex = i;
                        return true;
                    }
                });
                if (atIndex > -1) {
                    vm.completed.removeAt(atIndex);
                }
                //从uploadify中尝试取消
                try {
                    $('#' + uploaderId).uploadify('cancel', fileId);
                } catch (e) {
                }
            };
            /**
             * 根据文件id获取上传信息
             */
            vm.getFileById = function (fileId) {
                return _.find(vm.uploadList.$model, function (itemData) {
                    return itemData.id == fileId;
                });
            };
            vm.removeAllFile = function () {
                vm.uploadList.clear();
                vm.uploaded.clear();
                vm.failed.clear();
                vm.completed.clear();
                //从uploadify中尝试取消所有文件
                try {
                    $('#' + uploaderId).uploadify('cancel', "*");
                } catch (e) {
                }
            };
            vm._uploadSucess = function (file, responseText) {
                vm.updateList(_.extend({
                    "id": file.id,
                    "name": file.name,
                    "uploaded": true   //表明上传成功
                }, opts.onSuccessResponseData(responseText, file)));  //附加成功请求信息
                //vm.uploaded.push(file.id);
                //vm.completed.push(file.id);
            };
            vm._uploadError = function (file, errorCode) {
                vm.updateList({
                    "id": file.id,
                    "uploaded": false,   //表明上传失败
                    "errorCode": errorCode
                });
                //vm.failed.push(file.id);
                //vm.completed.push(file.id);
            };
            vm.updateList = function (uploadData) {
                var tempData;
                if (_.isUndefined(uploadData.id) || !_.some(vm.uploadList, function (itemData, i) {
                    var prevUploaded;
                    if (itemData.id == uploadData.id) {
                        prevUploaded = itemData.uploaded;
                        vm.uploadList.set(i, uploadData);
                        if (vm.uploadList[i].uploaded != prevUploaded) {    //上传状态反转，处理相应failed和uploaded存储
                            if (prevUploaded) {
                                vm.uploaded = _.reject(vm.uploaded.$model, function (uploadedId) {
                                    return uploadedId == uploadData.id;
                                });
                                vm.failed.push(uploadData.id);
                            } else {
                                vm.failed = _.reject(vm.failed.$model, function (uploadedId) {
                                    return uploadedId == uploadData.id;
                                });
                                vm.uploaded.push(uploadData.id);
                            }
                        }
                        return true;
                    }
                })) {   //如果没找到，追加到最后
                    tempData = _.extend(getPureFileData(), {
                        "id": generateID()
                    }, uploadData);
                    vm.uploadList.push(tempData);
                    if (tempData.uploaded) {
                        vm.uploaded.push(tempData.id);
                    } else {
                        vm.failed.push(tempData.id);
                    }
                    vm.completed.push(tempData.id);
                }
            };
            vm.$init = function () {
                //准备结构
                var hostEl = $('<span class="uploader-host"></span>');
                if (elEl.css('position') == 'static') {
                    elEl.css('position', 'relative');
                }
                hostEl.css({
                    "display": "block",
                    "position": "absolute",
                    "top": 0,
                    "left": 0,
                    "width": opts.uploadifyOpts["width"] || elEl.width(),
                    "height": opts.uploadifyOpts["height"] || elEl.height(),
                    "overflow": "hidden"
                }).appendTo(elEl);
                hostEl.html('<span id="' + uploaderId + '"></span>');
                //创建flash
                var onSelect = opts.uploadifyOpts["onSelect"],
                    onUploadSuccess = opts.uploadifyOpts["onUploadSuccess"],
                    onUploadError = opts.uploadifyOpts["onUploadError"],
                    onUploadStart = opts.uploadifyOpts["onUploadStart"],
                    onUploadProgress = opts.uploadifyOpts["onUploadProgress"],
                    onQueueComplete = opts.uploadifyOpts["onQueueComplete"],
                    formData = opts.uploadifyOpts["formData_"] || opts.uploadifyOpts["formData"];
                if (_.isFunction(formData)) {   //如果formData是funciton，延时附加参数
                    opts.uploadifyOpts["formData"] = {};    //替换原来的配置项，不推荐，应该把处理挪出$init方法
                    !opts.uploadifyOpts["formData_"] && (opts.uploadifyOpts["formData_"] = formData);
                }
                $('#' + uploaderId).uploadify(_.extend({
                    "buttonText": "",
                    "swf": uploadify.SWF_PATH,
                    "onFallback": function () {
                        elEl.width('auto').html('<span class="no-flash-player-tip">上传按钮暂不可使用，请点击<a href="http://get.adobe.com/cn/flashplayer/otherversions/" target="_blank">安装FLASH插件</a>，后重新启动程序</span>');
                    }
                }, opts.uploadifyOpts, {
                    "onSelect": function () {
                        var args = Array.prototype.slice.call(arguments, 0);
                        onSelect && onSelect.apply(this, args); //用户拦onSelect截优先内部处理
                        vm._selectFile.apply(vm, args);
                    },
                    "onUploadSuccess": function () {
                        var args = Array.prototype.slice.call(arguments, 0);
                        vm._uploadSucess.apply(vm, args);
                        onUploadSuccess && onUploadSuccess.apply(this, args);
                    },
                    "onUploadError": function () {
                        var args = Array.prototype.slice.call(arguments, 0);

                        vm._uploadError.apply(vm, args);
                        onUploadError && onUploadError.apply(this, args);
                    },
                    "onUploadStart": function () {
                        var args = Array.prototype.slice.call(arguments, 0);
                        vm.uploading = true;
                        //附加function类型的formData
                        if (_.isFunction(formData)) {
                            $('#' + uploaderId).uploadify("settings", "formData", formData());
                            //formData = null;    //防止多次附加
                        }
                        onUploadStart && onUploadStart.apply(this, args);
                    },
                    "onUploadProgress": function () {
                        var args = Array.prototype.slice.call(arguments, 0);
                        onUploadProgress && onUploadProgress.apply(this, args);
                    },
                    "onQueueComplete": function () {
                        var args = Array.prototype.slice.call(arguments, 0);
                        vm.uploading = false;
                        onQueueComplete && onQueueComplete.apply(this, args);
                    }
                }));
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm.$remove = function () {
                try {
                    $('#' + uploaderId).uploadify("destroy");   //清理uploader,firefox下有报错，可能有内存泄露
                } catch (evt) {
                }
                elEl.empty();
            };
        });
        /*vmodel.completed.$watch('length', function (len) {
         if (len == vmodel.uploadList.size()) {
         vm.uploading = false;
         } else {
         vm.uploading = true;
         }
         });*/
        return vmodel;
    };
    widget.defaults = {
        "uploadifyOpts": {
            "uploader": ''
        },
        "defaultExtKeyData": {  //用于扩展uploadList监控数组中每个对象的字段信息
            "responseText": ""
        },
        "onSuccessResponseData": function (responseText) {
            return {
                "responseText": responseText
            };
        }
    };

    //生成UUID http://stackoverflow.com/questions/105034/how-to-create-a-guid-uuid-in-javascript
    function generateID() {
        return "uploader" + Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15)
    }

    return avalon;
});
