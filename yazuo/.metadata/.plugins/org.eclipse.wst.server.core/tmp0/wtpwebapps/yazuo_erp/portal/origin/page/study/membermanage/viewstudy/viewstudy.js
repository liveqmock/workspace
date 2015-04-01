/**
 * 学员管理
 */
define(['avalon', 'util', 'json', 'moment'], function (avalon, util, JSON, moment) {

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
            var hashId = routeData.params["id"], lazyLoad;
            var pageVm = avalon.define(pageName, function (vm) {
                vm.userName = "";
                vm.tel = "";
                vm.group = "";
                vm.position = "";
                vm.rowsData = [];
                vm.totalSize = 0;
                vm.pageSize = 13;
                vm.pageNumber = 1;
                vm.isComplete = false;
            });
            avalon.scan(pageEl[0], [pageVm]);
            //懒加载
            lazyLoad = function () {
                if ($('body').height() - $(win).scrollTop() - $(win).height() <= 10) {    //监测滚动条滚动到底部10px左右
                    if ((pageVm.pageNumber - 1) * pageVm.pageSize < pageVm.totalSize) {
                        initData();
                    } else {
                        pageVm.isComplete = true;
                        $(win).unbind('scroll', lazyLoad);
                    }
                }
            };
            $(win).scroll(lazyLoad);
            initData();
            function initData() {
                util.c2s({
                    "type": "post",
                    "url": erp.BASE_PATH + "teamMember/queryLearningProcessList.do",
                    "data": JSON.stringify({"pageSize": pageVm.pageSize, "pageNumber": pageVm.pageNumber, "studentId": +hashId}),
                    "contentType": 'application/json',
                    "dataType": "json",
                    "success": function (responseData) {
                        var data;
                        if (responseData.flag) {
                            data = responseData.data.user;
                            pageVm.totalSize = responseData.data.totalSize;
                            pageVm.userName = data.user_name;
                            pageVm.tel = data.tel;
                            pageVm.group = data.group_name;
                            pageVm.position = data.position_name;
                            pageVm.pageNumber = pageVm.pageNumber + 1;
                            pageVm.rowsData = pageVm.rowsData.concat(_.each(responseData.data.rows, function (item) {
                                item.begin_time = item.begin_time ? moment(item.begin_time).format('YYYY-MM-DD HH:mm') + " 开始学习" : "还没有开始学习";
                            }));
                            if (pageVm.totalSize <= pageVm.pageSize) {
                                pageVm.isComplete = true;
                            }
                            if (responseData.data.totalSize === 0) {
                                util.alert({
                                    "iconType": "info",
                                    "content": "您还未学习"
                                });
                            }

                        }
                    }
                });
            }

        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {

        }
    });

    return pageMod;
});
