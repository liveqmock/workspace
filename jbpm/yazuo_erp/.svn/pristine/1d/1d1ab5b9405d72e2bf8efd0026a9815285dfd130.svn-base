/**
 * ppt播放组件
 */
define(["avalon", "util", "text!./pptplayer.html", "text!./pptplayer.css", "common"], function(avalon, util, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    var styleData = styleEl.data('module') || {};
    if (!styleData["pptplayer"]) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'pptplayer/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'pptplayer/');
        }
        styleData["pptplayer"] = true;
        styleEl.data('module', styleData);
    }
    var module = erp.module.pptplayer = function(element, data, vmodels) {
        var opts = data.pptplayerOptions,
            elEl = $(element);
        var vmodel = avalon.define(data.pptplayerId, function (vm) {
            avalon.mix(vm, opts);
            vm.widgetElement = element;
            vm.currentPath = erp.ASSET_PATH + 'image/s.gif';
            vm.currentIndex = 0;
            vm.movePassedNum = 0;   //移过了多少item，向上隐藏了多少item
            /*vm.pptList = [{
                "imagePath": erp.ASSET_PATH + 'image/s.gif'
            }, {
                "imagePath": erp.ASSET_PATH + 'image/s.gif'
            },{
                "imagePath": erp.ASSET_PATH + 'image/s.gif'
            },{
                "imagePath": erp.ASSET_PATH + 'image/s.gif'
            },{
                "imagePath": erp.ASSET_PATH + 'image/s.gif'
            },{
                "imagePath": erp.ASSET_PATH + 'image/s.gif'
            },{
                "imagePath": erp.ASSET_PATH + 'image/s.gif'
            },{
                "imagePath": erp.ASSET_PATH + 'image/s.gif'
            },{
                "imagePath": erp.ASSET_PATH + 'image/s.gif'
            },{
                "imagePath": erp.ASSET_PATH + 'image/s.gif'
            },{
                "imagePath": erp.ASSET_PATH + 'image/s.gif'
            },{
                "imagePath": erp.ASSET_PATH + 'image/s.gif'
            }];*/
            vm.pptList = [];
            /**
             * 向上移动,每次移动一个单位
             */
            vm.navUp = function () {
                vm.move(1);
            };
            /**
             * 向下移动,每次移动一个单位
             */
            vm.navDown = function () {
                vm.move(-1);
            };
            /**
             * @param num 一次移动的item个数,带方向, 正数表示向上移动，负数表示向下移动
             */
            vm.move = function (num) {
                var navListWEl = $('.ppt-nav-list-wrapper', elEl),
                    navListEl = $('.ppt-nav-list', elEl),
                    itemDistance = $('.ppt-nav-item', elEl).outerHeight() + 12, //每个item的间隔距离 = item高度 + margin
                    mayPassedNum = vm.movePassedNum + num,
                    maxShownNum = Math.ceil(navListWEl.height() / itemDistance);
                if (mayPassedNum < 0) {
                    vm.move(num + 1);   //向下过了
                    return;
                }
                if (mayPassedNum >= vm.pptList.size()) {   //向上过了，已1个item间隔的距离往回倒
                    vm.move(num - 1);
                    return;
                }
                navListEl.stop(true, true).animate({
                    "top": -(mayPassedNum * itemDistance) + 'px'
                },"fast");
                //保证当前预览的item可见性
                if (vm.currentIndex < mayPassedNum) {
                    vm.currentIndex = mayPassedNum;
                }
                if (vm.currentIndex >= mayPassedNum + maxShownNum) {
                    vm.currentIndex = mayPassedNum + maxShownNum - 1;
                }
                vm.movePassedNum = mayPassedNum;
            };
            /**
             * 点击导航选中对应的item
             */
            vm.clickNavItem = function () {
                var itemM = this.$vmodel.$model,
                    index = itemM.$index;
                vm.currentIndex = index;
            };
            vm.$init = function() {
                elEl.addClass('module-pptplayer');
                elEl.html('<div class="ppt-wrapper fn-clear">'+ tmpl + '</div>');
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm.$remove = function() {

            };
            vm.$skipArray = ["widgetElement"];

            vm.$watch('currentIndex', function (val) {
                vm.currentPath = vm.pptList[val].imagePath;
            });

        });
        return vmodel;
    };
    module.version = 1.0;
    module.defaults = {

    };
    return avalon;
});
