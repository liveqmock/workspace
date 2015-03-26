/**
 * 登录用户新增内容
 * Created by zhangsw on 14-10-27.
 */
define(["avalon", "util", "json", "text!./addproduct.html", "text!./addproduct.css"], function(avalon, util, JSON, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    try {
        styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'addproduct/'));
    } catch (e) {
        styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'addproduct/');
    }
    var module = erp.module.addproduct = function(element, data, vmodels) {
        var opts = data.addproductOptions,
            elEl = $(element);
        var loginUserData = erp.getAppData('user');
        var vmodel = avalon.define(data.addproductId, function (vm) {
            vm.widgetElement = element;
            vm.list = [];
            var len,sum,interval;
            util.c2s({
                "url": erp.BASE_PATH + 'sysProductOperation/listSysProductOperations.do',
                "type": "post",
                "contentType": 'application/json',
                "data": JSON.stringify({"inputPromotionPlatform":"2","userId":loginUserData.id}),
                "success": function (responseData) {
                    var data;
                    if (responseData.flag == 1) {
                        for(var i=0;i<responseData.data.length;i++){
                            try{
                                responseData.data[i].content = decodeURIComponent(responseData.data[i].content);
                            }catch (e){
                                responseData.data[i].content = responseData.data[i].content;
                            }

                        }
                        data = responseData.data;
                        if(data.length){
                            vmodel.list = data;
                            $(elEl).show();
                            vmodel.autoScroll();
                        }
                    }
                }
            });
            vm.close = function(){
                util.c2s({
                    "url": erp.BASE_PATH + 'sysUserRequirement/saveSysUserRequirement.do',
                    "type": "post",
                    "contentType": 'application/json',
                    "data": JSON.stringify({
                        "promotionPlatform": "2",
                        "userId": loginUserData.id,
                        "userName":loginUserData.userName,  //用户名
                        "tel": loginUserData.tel,  //手机号
                        "position": loginUserData.position //职位
                    }),
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            $(elEl).hide();
                        }
                    }
                });
            };
            vm.autoScroll = function(){
                sum = 0;
                len = $('.item',$(elEl)).length;
                interval = 4000;
                if(sum == 0 ){
                    $('.prev',$(elEl)).hide();
                }
                if(sum == len-1){
                    $('.next',$(elEl)).hide();
                    $('.button').show();
                    $('.close').show();
                }else{
                    $('.button').hide();
                    $('.close').hide();
                }
            };

            vm.displayButton = function () {
                if(sum == 0 ){
                    $('.prev',$(elEl)).hide();
                }else{
                    $('.prev',$(elEl)).show();
                }
                if(sum >= len-1){
                    $('.next',$(elEl)).hide();
                    $('.button').show();
                    $('.close').show();
                }else{
                    $('.next',$(elEl)).show();
                    $('.button').hide();
                    $('.close').hide();
                }
            };
            vm.next = function(){
                if(sum<len-1){
                    sum++;
                    $('.addproduct-outer').animate({left:-700*sum});
                    vm.displayButton();
                }
            };
            vm.prev = function(){
                if(sum>0){
                    sum--;
                    $('.addproduct-outer').animate({left:-700*sum});
                    vm.displayButton();
                }
            };



            vm.$init = function() {
                elEl.addClass('module-addproduct').hide();
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm.$remove = function() {

            };
            vm.$skipArray = ["widgetElement"];

        });
        return vmodel;
    };
    module.version = 1.0;
    module.defaults = {

    };
    return avalon;
});
