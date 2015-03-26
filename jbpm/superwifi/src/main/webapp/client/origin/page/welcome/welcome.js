/**
 * 欢迎页
 */
define(['avalon', 'util', 'json', 'moment'], function (avalon, util, JSON, moment) {
    var win = this,
        app = win.app,
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
                vm.noPermission = !app.getAppData('permission').length;
                vm.noCourse = !app.getAppData('user').hasCourse;
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
