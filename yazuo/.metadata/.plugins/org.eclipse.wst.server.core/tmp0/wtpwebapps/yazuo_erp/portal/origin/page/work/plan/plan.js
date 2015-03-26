/**
 * 工作计划
 */
define(['avalon', 'util', 'moment', 'json', '../../../module/schedule/schedule'], function (avalon, util, moment, JSON) {
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
            var loginUserData = erp.getAppData('user'),
                now = new Date();
            var scheduleId = pageName + '-schedule';
            var pageVm = avalon.define(pageName, function (vm) {
                vm.$scheduleOpts = {
                    "scheduleId": scheduleId,
                    "onFetchPlan": function (requestData, callback) {
                        //查询本月的计划信息
                        util.c2s({
                            "url": erp.BASE_PATH + "fesPlan/queryFesPlanList.do",
                            "type": "post",
                            "contentType":"application/json",
                            "data": JSON.stringify(_.extend({
                                "userId": loginUserData.id
                            }, requestData)),
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    callback(data);
                                } else {
                                    callback([]);
                                }
                            }
                        });
                    },
                    "onClickDateCell": function (evt) {
                        var itemM = this.$vmodel.$model,
                            cellDate = itemM.el.date,
                            targetEl = $(evt.target);
                        if (!moment(cellDate).isBefore(now, 'day')) {   //今天或以后日期点击跳转到添加计划页
                            if (targetEl.is('.plan-item')) {   //跳到相应的编辑页
                                if (targetEl.data('status') == "3" || targetEl.data('status') == "4" || targetEl.data('status') == "2" || targetEl.data('status') == "5") { //已完成/已放弃/已延期/已求助跳到单条查看页
                                    util.jumpPage('#/work/plan/list/' + moment(cellDate) / 1 + '/' + targetEl.data('planId'));  //跳到单独的plan查看
                                } else {
                                    util.jumpPage('#/work/plan/edit/' + moment(cellDate).hour(9) / 1 + '/' + targetEl.data('planId'));
                                }
                            } else {    //默认跳到添加页面
                                util.jumpPage('#/work/plan/add/' + moment(cellDate).hour(9) / 1);   //默认添加09：00这个时间端
                            }
                        } else {
                            if (targetEl.is('.plan-item')) {
                                util.jumpPage('#/work/plan/list/' + moment(cellDate) / 1 + '/' + targetEl.data('planId'));  //跳到单独的plan查看
                            } else {
                                util.jumpPage('#/work/plan/list/' + moment(cellDate) / 1);
                            }
                        }
                    }
                };
            });
            avalon.scan(pageEl[0]);
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var scheduleId = pageName + '-schedule';
            $(avalon.getVm(scheduleId).widgetElement).remove();
        }
    });

    return pageMod;
});
