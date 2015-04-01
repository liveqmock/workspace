/**
 * 资料库-list
 */
define(['avalon', 'util', 'json', '../../../../widget/pagination/pagination','../../../../widget/form/select','../../../../widget/uploader/uploader'], function (avalon, util, JSON) {
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
                    "text":"资料库",
                    "href":"#/databank/list"
                }, {
                    "text":'上传资料'
                }];
                vm.titleType = '上传资料';
                vm.pageType = parseInt(routeData.params["type"]);
                vm.fileId = parseInt(routeData.params["id"]);
                if(!vm.pageType){
                    vm.titleType = '修改资料';
                    vm.navCrumbs[1].text = '修改资料';
                }

                var loginUserData = erp.getAppData('user');
                vm.modifyEdit = 0;//是否修改了上传附件
                vm.searchData = { //查询参数
                    "pageSize" : 10,
                    "pageNumber":1,
                    "id":vm.fileId
                };
                vm.fileName = '';
                vm.personType = [//适用人群数组
                    {text:'客户服务经理',value:1,isSelect:'0'},
                    {text:'客户经理',value:2,isSelect:'0'}
                ];
                vm.$dataTypeOpts = {//资料类型
                    "selectId": getTypeId("dataTypeOpts"),
                    "options": [
                        {text:'案例类',value:'1'},
                        {text:'制度类',value:'2'},
                        {text:'销售工具类',value:'3'},
                        {text:'培训材料类',value:'4'},
                        {text:'客户回馈',value:'5'},
                        {text:'行业资讯',value:'6'},
                        {text:'市场活动',value:'7'},
                        {text:'Q&A',value:'8'},
                        {text:'其它',value:'9'}
                    ],
                    "selectedIndex": 0
                };

                vm.requestData = {
                    "id":"",
                    "title": "",//标题
                    "description": "",//描述
                    "applyCrowdType": [],//使用人群，1-客服经理，2-销售，对应数据字典[00000094]
                    "dataType" :'',
                    "sysAttachment": {
                        originalFileName: '',//原始文件名
                        attachmentName: '',//附件名称
                        attachmentSize: '',//附件大小
                        attachmentSuffix: ''//附件后缀
                    }
                };
                vm.personTypeSele = function(){//改变多选框状态
                    var val = this.value;
                    if(this.checked){
                        pageVm.requestData.applyCrowdType.push(val);
                    }else{
                        var arr = pageVm.requestData.applyCrowdType;
                        for(var i=0;i<arr.length;i++){
                            if(arr[i] == val){
                                arr.splice(i,1);
                            }
                        }
                    }
                    var arr = pageVm.requestData.applyCrowdType;
                    if(arr.length){
                        pageVm.isType = 1;
                    }else{
                        pageVm.isType = 0;
                    }
                };
                vm.isData = 1;//是否上传图片
                vm.isType = 1;//是否选择适应人群
                vm.saveData = function(){//提交
                    var oForm = $('.add-edit-content')[0];
                    var url;
                    var flag;
                    var exer;
                    var uploaderVm = avalon.getVm(getTypeId('dataUploaderId'));
                    flag = util.testing(oForm);
                    if(!pageVm.requestData.sysAttachment.attachmentSize){
                        vm.isData = 0;
                        flag = 0;
                    }
                    var arr=pageVm.requestData.applyCrowdType;
                    if(arr.length==0){
                        vm.isType = 0;
                        flag = 0;
                    }
                    pageVm.requestData.$model.dataType = avalon.getVm(getTypeId("dataTypeOpts")).selectedValue;

                    if(pageVm.fileId){
                        url = 'sysDatabase/updateSysDatabase.do';
                        pageVm.requestData.$model.id = pageVm.fileId;
                    }else{
                        url = "sysDatabase/saveSysDatabase.do";
                    }
                    if(flag){
                        exer = function(){
                            if(!vm.modifyEdit&&!vm.pageType){
                                delete pageVm.requestData.$model.sysAttachment;
                            }
                            util.c2s({//添加
                                "url": erp.BASE_PATH + url,
                                "type": "post",
                                "contentType": 'application/json',
                                "data":  JSON.stringify(pageVm.requestData.$model),
                                "success": function (responseData) {
                                    if (responseData.flag) {
                                        util.alert({
                                            "content": "操作成功！",
                                            "iconType": "success",
                                            "onSubmit":function () {
                                                window.location.href = erp.BASE_PATH + "#!/databank/list";
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
                };
                vm.$dataUploaderOpts = {//上传
                    "uploaderId": getTypeId('dataUploaderId'),
                    "uploadifyOpts": {
                        "uploader": erp.BASE_PATH + 'sysDatabase/uploadFiles.do',
                        "fileObjName": "myfiles",
                        "multi": false, //多选
                        "fileTypeDesc": "上传附件(*.*)",
                        "fileTypeExts": "*.doc;*.docx;*.xls;*.xlsx;*.ppt;*.pptx;*.pdf;*.rar;*.zip",
                        "formData": function () {
                            return {
                                "sessionId": loginUserData.sessionId
                            };
                        },
                        "width": 80,
                        "height": 30,
                        'progressData': 'speed'
                    },
                    "onSuccessResponseData": function (responseText, file) {
                        responseText=JSON.parse(responseText);
                        if(responseText.flag){
                            pageVm.isData = 1;
                            pageVm.modifyEdit = 1;
                            var data = responseText.data[0];
                            var index = data.originalFileName.lastIndexOf('.');
                            var suffix = data.originalFileName.substr(index+1);
                            pageVm.requestData.sysAttachment.originalFileName = data.originalFileName;
                            pageVm.requestData.sysAttachment.attachmentName = data.fileName;
                            pageVm.requestData.sysAttachment.attachmentSize = data.fileSize;
                            pageVm.requestData.sysAttachment.attachmentSuffix = suffix;
                            pageVm.fileName = data.originalFileName;
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
            /*页面初始化请求渲染*/
            function updateList(obj,url,callBack){
                var requestData=JSON.stringify(obj);
                util.c2s({
                    "url": erp.BASE_PATH + url,
                    "type": "post",
                    "contentType" : 'application/json',
                    "data": requestData,
                    "success": callBack
                });
            }
            /*页面初始化请求渲染end*/
            if(!pageVm.pageType){
                updateList(pageVm.searchData.$model,"sysDatabase/querySysDatabaseById.do",listCallBackValue);
            }
            function listCallBackValue(responseData){
                var data;
                if (responseData.flag) {
                    data=responseData.data;
                    pageVm.requestData={
                        "title": data.title,//标题
                        "description": data.description,//描述
                        "applyCrowdType": data.apply_crowd_type,//使用人群，1-客服经理，2-销售，对应数据字典[00000094]
                        "sysAttachment": {
                            originalFileName: data.sysAttachment.originalFileName,//原始文件名
                            attachmentName: data.sysAttachment.attachmentName,//附件名称
                            attachmentSize: data.sysAttachment.attachmentSize,//附件大小
                            attachmentSuffix: data.sysAttachment.attachmentSuffix//附件后缀
                        }
                    };
                    pageVm.fileName = data.sysAttachment.originalFileName;
                    var arr = data.apply_crowd_type;
                    var arrt = pageVm.personType.$model;
                    for(var i=0;i<arr.length;i++){
                        for(var j=0;j<arrt.length;j++){
                            if(arr[i] == arrt[j].value){
                                pageVm.personType[j].isSelect = '1';
                            }
                        }
                    }

                    //改变select默认值
                    var data_type = data.data_type;
                    var dataTypes = pageVm.$dataTypeOpts.options;
                    var dataTypesOps = avalon.getVm(getTypeId("dataTypeOpts"));
                    for(var k=0;k<dataTypes.length;k++){
                        if(dataTypes[k].value == data_type){
                            dataTypesOps.setOptions([
                                {text:'案例类',value:'1'},
                                {text:'制度类',value:'2'},
                                {text:'销售工具类',value:'3'},
                                {text:'培训材料类',value:'4'},
                                {text:'客户回馈',value:'5'},
                                {text:'行业资讯',value:'6'},
                                {text:'市场活动',value:'7'},
                                {text:'Q&A',value:'8'},
                                {text:'其它',value:'9'}
                            ],k);
                        }
                    }

                }
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


