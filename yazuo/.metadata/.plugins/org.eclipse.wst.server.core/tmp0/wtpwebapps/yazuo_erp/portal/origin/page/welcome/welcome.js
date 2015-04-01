/**
 * 欢迎页
 */
define(['avalon', 'util', 'json', 'moment', "../../module/addproduct/addproduct"], function (avalon, util, JSON, moment) {
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
            var pageBdInnterEl = $('.page-bd-inner', pageEl);
            var pageVm = avalon.define(pageName, function (vm) {
                vm.noPermission = !erp.getAppData('permission').length;
                vm.noCourse = !erp.getAppData('user').hasCourse;
            });
            avalon.scan(pageEl[0]);
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {}
    });

    return pageMod;
});
