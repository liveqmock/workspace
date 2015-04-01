/**
 * 部门组织结构
 */
define(['avalon', 'util', '../../../module/knowledgecate/knowledgecate'], function (avalon, util, knowledgecate) {
     var win = this,
        erp = win.erp,
        page = erp.page,
        pageMod = {};
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName) {
            var knowledgecateId = pageName + '-knowledgecate';
            var pageVm = avalon.define(pageName, function (vm) {
                vm.$knowledgecateOpts = {
                    "knowledgecateId": knowledgecateId
                };
            });
            avalon.scan(pageEl[0],[pageVm]);
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var knowledgecateId = pageName + '-knowledgecate';
            $(avalon.getVm(knowledgecateId).widgetElement).remove();
        }
    });

    return pageMod;
});
