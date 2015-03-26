/**
 * 资料库-list
 */
define(['avalon', 'util', 'json', 'moment', '../../../../widget/pagination/pagination','../../../../widget/form/select','../../../../widget/uploader/uploader', '../../../../widget/calendar/calendar', '../../../../widget/editor/editor'], function (avalon, util, JSON, moment) {
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
                    "text":"产品运营",
                    "href":"#/productoperad/list"
                }, {
                    "text":'创建运营信息'
                }];
                vm.titleType = '创建运营信息';
                vm.pageType = parseInt(routeData.params["type"]);
                vm.fileId = parseInt(routeData.params["id"]);
                if(!vm.pageType){
                    vm.titleType = '修改运营信息';
                    vm.navCrumbs[1].text = '修改运营信息';
                }
                var loginUserData = erp.getAppData('user');


                vm.dateType = '';//时间类型（判断是开始还是结束日期）
                vm.isEdit = '';//运营内容
                vm.isProducts = '';//是否选中所属产品
                vm.isPromotionPlatform = '';//是否选择推广平台


                vm.productType = [//所属产品
                    {text:'CRM',value:1,isSelect:'0'},
                    {text:'ERP',value:2,isSelect:'0'},
                    {text:'软POS',value:3,isSelect:'0'},
                    {text:'运营平台',value:4,isSelect:'0'},
                    {text:'超级报表',value:5,isSelect:'0'},
                    {text:'超级预定',value:6,isSelect:'0'},
                    {text:'微信',value:7,isSelect:'0'},
                    {text:'超级Wifi',value:8,isSelect:'0'}
                ];
                vm.promotionPlatform = [//推广平台
                    {text:'CRM',value:1,isSelect:'0'},
                    {text:'ERP',value:2,isSelect:'0'},
                    {text:'软POS',value:3,isSelect:'0'},
                    {text:'微信ERP',value:4,isSelect:'0'}
                ];

                var newDate = new Date();
                vm.requestData = {
                    "id":"",
                    "title": "",//标题
                    "content": "",//运营内容
                    "productType": [],//所属产品
                    "promotionPlatform": [],//推广平台
                    "beginTime": moment(newDate).format('YYYY-MM-DD'),//推广开始日期
                    "endTime": moment(newDate).format('YYYY-MM-DD'),//推广结束日期
                    "isOpen": "1"//是否开启推广
                };
                vm.setChecked= function(val,arr){//设置多选框错误提示
                    var flag = true;
                    if(this.checked){
                        for(var i=0;i<arr.length;i++){
                            if(arr[i] == val){
                                flag = false;
                            }
                        }
                        if(flag){
                            arr.push(val);
                        }
                    }else{
                        for(var k=0;k<arr.length;k++){
                            if(arr[k] == val){
                                arr.splice(k,1);
                            }
                        }
                    }
                };
                vm.setProducts = function(){//所属产品
                    var val = this.value;
                    var arr = pageVm.requestData.productType;
                    pageVm.setChecked.call(this,val,arr);
                    if(arr.length){
                        $('.isProducts').val(val);
                    }else{
                        $('.isProducts').val('');
                    }
                    var oForm = $('.add-edit-content')[0];
                    var obj = $('.isProducts',oForm);
                    util.testing(oForm,obj);
                };
                vm.setPlatform = function(){//推广平台
                    var val = this.value;
                    var arr = pageVm.requestData.promotionPlatform;
                    pageVm.setChecked.call(this,val,arr);
                    if(arr.length){
                        $('.isPromotionPlatform').val(val);
                    }else{
                        $('.isPromotionPlatform').val('');
                    }
                    var oForm = $('.add-edit-content')[0];
                    var obj = $('.isPromotionPlatform',oForm);
                    util.testing(oForm,obj);
                };
                vm.setRadio = function(){//是否推广
                    var val = this.value;
                    pageVm.requestData.isOpen = val;
                };


                vm.$dateOpts = {//日期时间
                    "calendarId": getTypeId('DateCalendarId'),
                    "minDate": new Date(),
                    "onClickDate": function (d) {
                        if(pageVm.dateType == 'beginTime'){
                            pageVm.requestData.beginTime = moment(d).format('YYYY-MM-DD');
                            var beginTime = moment(pageVm.requestData.beginTime)/1;
                            var endTime = moment(pageVm.requestData.endTime)/1;
                            if(endTime<beginTime){
                                pageVm.requestData.endTime = moment(d).format('YYYY-MM-DD');
                            }
                        }else if(pageVm.dateType == 'endTime'){
                            pageVm.requestData.endTime = moment(d).format('YYYY-MM-DD');
                        }
                        $(this.widgetElement).hide();
                        //pageVm.isDateSelect = true;
                    }
                };
                vm.openDateCalendar = function () {
                    var meEl = $(this),
                        calendarVm = avalon.getVm(getTypeId('DateCalendarId')),
                        calendarEl,
                        inputOffset = meEl.offset();
                        pageVm.dateType = meEl.attr('data-type');
                    if (!calendarVm) {
                        calendarEl = $('<div ms-widget="calendar,$,$dateOpts"></div>');
                        calendarEl.css({
                            "position": "absolute",
                            "z-index": 10000
                        }).hide().appendTo('body');
                        avalon.scan(calendarEl[0], [vm]);
                        calendarVm = avalon.getVm(getTypeId('DateCalendarId'));
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
                    if(pageVm.dateType == 'beginTime'){
                        if (pageVm.requestData.beginTime) {
                            calendarVm.focusDate = moment(pageVm.requestData.beginTime, 'YYYY-MM-DD')._d;
                        } else {
                            calendarVm.focusDate = new Date();
                        }
                        calendarVm.minDate = moment(moment(new Date()).format('YYYY-MM-DD'), 'YYYY-MM-DD')._d;
                        //if(pageVm.isDateSelect){
                          //  calendarVm.maxDate = moment(pageVm.requestData.endDate, 'YYYY-MM-DD')._d;
                        //}

                    }else if(pageVm.dateType == 'endTime'){
                        if (pageVm.requestData.endTime) {
                            calendarVm.focusDate = moment(pageVm.requestData.endTime, 'YYYY-MM-DD')._d;
                        } else {
                            calendarVm.focusDate = new Date();
                        }
                        calendarVm.minDate = moment(pageVm.requestData.beginTime, 'YYYY-MM-DD')._d;
                        calendarVm.maxDate = '';

                    }

                    calendarEl.css({
                        "top": inputOffset.top + meEl.outerHeight() - 1,
                        "left": inputOffset.left
                    }).show();
                };
                vm.$editorOpts = {//邮件模板
                    "editorId": getTypeId('editorId'),
                    "ueditorOptions": {
                        "UEDITOR_HOME_URL": erp.WIDGET_PATH + 'editor/ueditor/',
                        "serverUrl": erp.BASE_PATH + 'portal/origin/widget/editor/ueditor/jsp/controller.jsp',
                        "toolbars": [[
                            'fullscreen', 'source', '|', 'undo', 'redo', '|',
                            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                            'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                            'directionalityltr', 'directionalityrtl', 'indent', '|',
                            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter','simpleupload'
                        ]]
                    }
                };
                setTimeout(function(){$('#edui1').css('z-index',10);},1000);
                vm.saveData = function(){//提交
                    var editorVm = avalon.getVm(getTypeId('editorId'));
                    $('.isEdit').val(editorVm.core.getContent());
                    var oForm = $('.add-edit-content')[0];
                    if(util.testing(oForm)){
                        var obj = pageVm.requestData.$model;
                        obj.content = encodeURIComponent(editorVm.core.getContent());
                        obj.beginTime = moment(pageVm.requestData.$model.beginTime)/1;
                        obj.endTime = moment(pageVm.requestData.$model.endTime)/1;
                        util.c2s({//添加
                            "url": erp.BASE_PATH + 'sysProductOperation/saveSysProductOperation.do',
                            "type": "post",
                            "contentType": 'application/json',
                            "data":  JSON.stringify(obj),
                            "success": function (responseData) {
                                if (responseData.flag) {
                                    util.alert({
                                        "content": "操作成功！",
                                        "iconType": "success",
                                        "onSubmit":function () {
                                            window.location.href = erp.BASE_PATH + "#!/productoperad/list";
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
            function setCheckBox (arr,arrt,className) {//设置多选框默认选中
                for(var i=0;i<arr.length;i++){
                    for(var j=0;j<arrt.length;j++){
                        if(arr[i] == arrt[j].value){
                            arrt[j].isSelect = '1';
                            $('.'+className).val(arrt[j].value);
                        }
                    }
                }
            }
            if(!pageVm.pageType){
                updateList({id:pageVm.fileId},"sysProductOperation/getSysProductOperation.do",listCallBackValue);
            }
            function listCallBackValue(responseData){
                var data;
                if (responseData.flag) {
                    data=responseData.data;
                    try{
                        data.content = decodeURIComponent(data.content);
                    }catch (e){
                        data.content = data.content;
                    }
                    pageVm.requestData={
                        "id":data.id,
                        "title": data.title,//标题
                        "content": data.content,//运营内容
                        "productType": data.productType,//所属产品
                        "promotionPlatform": data.promotionPlatform,//推广平台
                        "beginTime": moment(data.beginTime).format('YYYY-MM-DD'),//推广开始日期
                        "endTime": moment(data.endTime).format('YYYY-MM-DD'),//推广结束日期
                        "isOpen": data.isOpen//是否开启推广
                    };
                    var editorVm = avalon.getVm(getTypeId('editorId'));
                    setTimeout(function(){
                        editorVm.core.setContent(data.content);
                        $('#edui1').css('z-index',10);
                    },1000);
                    setCheckBox(data.productType,pageVm.productType,'isProducts');
                    setCheckBox(data.promotionPlatform,pageVm.promotionPlatform,'isPromotionPlatform');
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


