/**
 * 添加&修改话术
 */
define(['avalon', 'util', 'json', '../../../../widget/pagination/pagination','../../../../widget/form/select', '../../../../widget/editor/editor'], function (avalon, util, JSON) {
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
                    "text":"话术管理",
                    "href":"#/backend/languagemanage"
                }, {
                    "text":'添加话术'
                }];
                vm.titleType = '添加话术';
                vm.pageType = parseInt(routeData.params["type"]);
                vm.fileId = parseInt(routeData.params["id"]);
                if(!vm.pageType){
                    vm.titleType = '修改话术';
                    vm.navCrumbs[1].text = '修改话术';
                }
                vm.type2 = 0;
                vm.$type1SelectOpts = {//类型1
                    "selectId" : getTypeId('-type1'),
                    "options" : [],
                    "onSelect" : function(v,t,o){
                        vm.requestData.resourceRemark = v;
                        if(o.children.length){
                            vm.type2 = 1;
                            vm.setSelect(o.children,'-type2');
                        }else{
                            vm.type2 = 0;
                            avalon.getVm(getTypeId('-type2')).setOptions([]);
                            vm.requestData.resourceExtraRemark = '';
                        }
                    }
                };
                vm.$type2SelectOpts = {//类型2
                    "selectId" : getTypeId('-type2'),
                    "options" : [],
                    "onSelect" : function(v,t,o){
                        vm.requestData.resourceExtraRemark = v;
                    }
                };
                vm.requestData = {
                    "id": "",
                    "resourceRemark": "",
                    "resourceExtraRemark": "",
                    "title": "",
                    "content": ""
                };
                vm.$editorOpts = {
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
                            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter'
                        ]]
                    }
                };
                /*edit失去焦点不为空判断*/
                $('.page-backend-languagemanage-addedit').on('mousedown',function(){
                    var editorVm = avalon.getVm(getTypeId('editorId'));
                    if(editorVm.core.isFocus()){
                        vm.requestData.content = editorVm.core.getContent();
                        $('.email-hidden-content').val(vm.requestData.content);
                        util.testing($('.pageVm.widgetElement')[0],$('.email-hidden-content'));

                    }
                });

                /*edit失去焦点不为空判断 -end*/
                /*设置联动select方法*/
                vm.setSelect = function(arr,name){
                    var newArr = _.filter(arr,function(item,index){
                        return item.isSelected;
                    });
                    avalon.getVm(getTypeId(name)).setOptions(arr);
                    if(newArr.length){
                        avalon.getVm(getTypeId(name)).selectValue(newArr[0].value,true);
                    }
                };
                /*设置联动select方法-end*/
                vm.saveData = function(){
                    var ele = pageVm.widgetElement;
                    var editorVm = avalon.getVm(getTypeId('editorId'));
                    vm.requestData.content = editorVm.core.getContent();
                    if(util.testing(ele)){
                        util.c2s({
                            "url": erp.BASE_PATH + 'besTalkingSkills/saveBesTalkingSkills.do',
                            "type": "post",
                            "contentType" : 'application/json',
                            "data": JSON.stringify(vm.requestData.$model),
                            "success": function(responseData){
                                if (responseData.flag) {
                                    util.alert({
                                        "content": "操作成功！",
                                        "iconType": "success",
                                        "onSubmit":function () {
                                            window.location.href = erp.BASE_PATH + "#!/backend/languagemanage";
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
            function getData(obj,url,callBack){
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
            var editorVm = avalon.getVm(getTypeId('editorId'));
            editorVm.core.addListener('ready', function() {
                if(!pageVm.pageType){
                    getData({id:pageVm.fileId},"besTalkingSkills/loadBesTalkingSkills.do",listCallBackValue);
                }else{
                    getData({},"besTalkingSkills/loadBesTalkingSkills.do",listCallBackValue);
                }
            });
            function listCallBackValue(responseData){
                var data;
                if (responseData.flag) {
                    data=responseData.data;
                    pageVm.requestData.id = data.id;
                    pageVm.requestData.resourceRemark = data.resourceRemark;
                    pageVm.requestData.resourceExtraRemark = data.resourceExtraRemark;
                    pageVm.requestData.title = data.title;
                    pageVm.requestData.content = data.content;
                    pageVm.setSelect(data.dropDownList,'-type1');
                    $('.email-hidden-content').val(data.content);
                    editorVm.core.setContent(data.content);//编辑器家在完成后，
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
            $('body').off();
        }
    });
    return pageMod;
});


