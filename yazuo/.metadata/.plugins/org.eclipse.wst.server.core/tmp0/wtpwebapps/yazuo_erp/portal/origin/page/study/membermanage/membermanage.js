/**
 * 组员管理
 */
define(['avalon', 'util', 'json','../../../widget/grid/grid'], function (avalon, util, JSON) {

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

            var gridId = pageName + '-grid',
                loginUserData = erp.getAppData('user');

            var pageVm = avalon.define(pageName, function (vm) {
                vm.keyword = "";
                vm.$gridOpts = {
                    "gridId": gridId,
                    "recordUnit": "个人",
                    "disableCheckAll": true,   //开启全选模式
                    "disableCheckbox": true,
                    "columns": [
                        {
                            "dataIndex": "user_name",
                            "text": "姓名"
                        },
                        {
                            "dataIndex": "tel",
                            "text": "手机号"
                        },
                        {
                            "dataIndex": "group_name",
                            "text": "部门"
                        },
                        {
                            "dataIndex": "position_name",
                            "text": "职位"
                        },
                        {
                            "dataIndex": "coursewareCount",
                            "text": "正在学习的课件"
                        },
                        {
                            "dataIndex": "operation",
                            "text": "操作",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<a ms-href="#/study/membermanage/viewstudy/{{$outer.el.id}}">查看学习情况</a>&nbsp;&nbsp;&nbsp;&nbsp;' +
                                    '<a href="#/study/membermanage/history/' + rowData.id + '">查看历史</a></td>';
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        _.extend(requestData, {
                            "subUserName": _.str.trim(vm.keyword),
                            "baseUserId":+loginUserData.id
                        });
                        util.c2s({
                            "type": "post",
                            "url": erp.BASE_PATH + "teamMember/queryMemberList.do",
                            "data": JSON.stringify(requestData),
                            "contentType": 'application/json',
                            "dataType": "json",
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    callback.call(this, data);
                                }
                            }
                        });
                    }
                };
                vm.query = function () {
                    avalon.getVm(gridId).load();
                };

            });
            avalon.scan(pageEl[0], [pageVm]);
            //更新grid数据
            avalon.getVm(gridId).load();
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var gridId = pageName + '-grid';
            $(avalon.getVm(gridId).widgetElement).remove();
        }
    });

    return pageMod;
});
