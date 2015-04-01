/**
 * 部门组织结构
 */
define(['avalon', 'util', '../../../module/organization/organization'], function (avalon, util, organization) {
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

            });
            avalon.scan(pageEl[0],[pageVm]);
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {

        }
    });

    return pageMod;
});
