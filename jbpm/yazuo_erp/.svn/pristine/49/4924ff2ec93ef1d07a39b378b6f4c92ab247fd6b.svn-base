/**
 * 视频库管理
 */
define(['avalon', 'util'], function (avalon, util) {
     var win = this,
        erp = win.erp,
        pageMod = {};
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName) {
            var pageVm = avalon.define(pageName, function (vm) {
                //vm.externalLink = erp.BASE_PATH + 'erp/video/toVideoItems.do';
                vm.externalLink = 'http://192.168.236.31:8083/yazuo_erp/erp/video/toVideoItems.do';
                vm.iframeHeight = 1200;
                vm.onIframeLoad = function () {
                    /*var bodyEl = $(this.contentWindow.document.body);
                    $('.page-hd', bodyEl).hide();
                    vm.iframeHeight = bodyEl.height() + 60;*/
                };
            });
            avalon.scan(pageEl[0]);
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {

        }
    });

    return pageMod;
});
