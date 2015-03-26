/**
 * 武林大会设置
 */
define(['avalon', 'util', 'moment', 'json', '../../../widget/itemselector/itemselector', '../../../widget/dialog/dialog', '../../../widget/form/select'], function (avalon, util, moment, JSON) {
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
            var addSupportDialogId = pageName + '-add-support-dialog',
                userSelectorId = pageName + '-user-selector',
                currentYearId = pageName + '-current-year',
                currentMonthId = pageName + '-current-month';
            var now = moment(),
                nowYear = now.year(),
                nowMonth = now.month();
            var pageVm = avalon.define(pageName, function (vm) {
                vm.activeTeamId = 0;
                vm.activeTeamAtIndex = -1;
                vm.$addSupportOpts = {
                    "dialogId": addSupportDialogId,
                    "title": "添加支持者",
                    "width": 600,
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                            footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                '<div class="ui-dialog-btns">' +
                                    '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">确&nbsp;定</button>' +
                                    '<span class="separation"></span>' +
                                    '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button>' +
                                '</div>' +
                            '</div>';
                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "submit": function () {
                        var userSelectorVm = avalon.getVm(userSelectorId),
                            selectedData = userSelectorVm.getSelectedData();
                        //保存支持者
                        util.c2s({
                            "url": erp.BASE_PATH + "mktTeam/saveSupporter.do",
                            "type": "post",
                            "contentType": "application/json",
                            "data": JSON.stringify({
                                "id": vm.activeTeamId,
                                "supportList": _.map(selectedData, function (itemData) {
                                    return {
                                        "id": itemData.id
                                    };
                                })
                            }),
                            "success": function (responseData) {
                                if (responseData.flag) {
                                    //更新当前team支持者列表
                                    fetchSupportList({
                                        "teamId": vm.activeTeamId
                                    }, function (data) {
                                        vm.teamList.set(vm.activeTeamAtIndex, {
                                            "supportNum": data.length,
                                            "supportList": _.map(data, function (itemData) {
                                                return {
                                                    "id": itemData.id,
                                                    "supportName": itemData.userName,
                                                    "position": itemData.position.positionName
                                                };
                                            })
                                        });
                                    });
                                    avalon.getVm(addSupportDialogId).close();
                                }
                            }
                        });
                    }
                };
                vm.$userSelectorOpts = {
                    "itemselectorId": userSelectorId,
                    "rightTitle": "已添加的支持者（0）",
                    "showLeftTitle": false,
                    "showRightFilter": false,
                    "onItemFilter": function (val, itemData) {
                        if (itemData.userName.slice(0, val.length) === val || itemData.positionName.slice(0, val.length) === val) {
                            return true;
                        }
                    }
                };
                vm.$currentYearOpts = {
                    "selectId": currentYearId,
                    "options": _.map(_.range(0, 10), function (num) {
                        return {
                            "text": nowYear - num + '年',
                            "value": nowYear - num
                        };
                    }),
                    "onSelect": function () {
                        updateList();
                    }
                };
                vm.$currentMonthOpts = {
                    "selectId": currentMonthId,
                    "options": _.map(_.range(1, 13), function (num) {
                        return {
                            "text": num + '月',
                            "value": num
                        };
                    }),
                    "onSelect": function () {
                        updateList();
                    },
                    "selectedIndex": nowMonth
                };
                /*vm.teamList = [{
                    "teamName": "ffff",
                    "teamLeader": "test",
                    "teamMember": "haha、haha2",
                    "teamWord": "uuuuuuuuuuuuu",
                    "odds": "一赔三",
                    "oddsState": "r",
                    "oddsBtnText": "修改",
                    "income": "6500",
                    "incomeState": "r",
                    "incomeBtnText": "修改",
                    "supportVisibleCtrBtnText": "查看支持者",
                    "supportVisibleState": true,
                    "supportNum": "33",
                    "supportList": [{
                        "supportName": "who"
                    }, {
                        "supportName": "who2"
                    }]
                }, {
                    "teamName": "hhhh",
                    "teamLeader": "test",
                    "teamMember": "haha、haha2",
                    "teamWord": "uuuuuuuuuuuuu",
                    "odds": "一赔三",
                    "oddsState": "r",
                    "oddsBtnText": "修改",
                    "income": "6500",
                    "incomeState": "r",
                    "incomeBtnText": "修改",
                    "supportVisibleCtrBtnText": "查看支持者",
                    "supportVisibleState": false,
                    "supportNum": "33",
                    "supportList": [{
                        "supportName": "who"
                    }, {
                        "supportName": "who2"
                    }]
                }];*/
                vm.teamList = [];
                vm.clickOddsBtn = function () {
                    var atIndex = this.$vmodel.$model.$index,
                        itemData = this.$vmodel.$model.el,
                        oddsState = vm.teamList[atIndex].oddsState,
                        oddsInputValue,
                        tempInputValue;
                    if (oddsState === "e") {    //保存提交
                        oddsInputValue = _.str.trim($(this).siblings('.odds-editor').val());
                        if (!oddsInputValue.length) {   //空的话还是原来的值
                            tempInputValue = vm.teamList[atIndex].odds;
                            vm.teamList[atIndex].odds = '';
                            vm.teamList[atIndex].odds = tempInputValue;
                            vm.teamList[atIndex].oddsState = 'r';
                            vm.teamList[atIndex].oddsBtnText = '修改';
                            return;
                        }
                        util.c2s({
                            "url": erp.BASE_PATH + "mktTeam/saveMktTeam.do",
                            "type": "post",
                            "contentType": "application/json",
                            "data": JSON.stringify({
                                "id": itemData.id,
                                "teamOdds": oddsInputValue
                            }),
                            "success": function (responseData) {
                                if (responseData.flag) {
                                    vm.teamList.set(atIndex, {
                                        "oddsState": "r",
                                        "oddsBtnText": "修改",
                                        "odds": oddsInputValue
                                    });
                                }
                            }
                        });
                    } else {
                        vm.teamList.set(atIndex, {
                            "oddsState": "e",
                            "oddsBtnText": "保存"
                        });
                    }
                };
                vm.clickIncomeBtn = function () {
                    var atIndex = this.$vmodel.$model.$index,
                        itemData = this.$vmodel.$model.el,
                        incomeState = vm.teamList[atIndex].incomeState,
                        incomeInputValue,
                        tempInputValue;
                    if (incomeState === "e") {    //保存提交
                        incomeInputValue = _.str.trim($(this).closest('.income-box').find('.income-editor').val());
                        if (!incomeInputValue.length) {   //空的话还是原来的值
                            tempInputValue = vm.teamList[atIndex].income;
                            vm.teamList[atIndex].income = '';
                            vm.teamList[atIndex].income = tempInputValue;
                            vm.teamList[atIndex].incomeState = 'r';
                            vm.teamList[atIndex].incomeBtnText = '修改';
                            return;
                        }
                        if (!/^-?\d+(\.\d{1,2})?$/.test(incomeInputValue)) { //最多保留2位小数
                            util.alert({
                                "content": "请输入正确格式的数字，最多保留2位小数",
                                "iconType": "error"
                            });
                            return;
                        }
                        util.c2s({
                            "url": erp.BASE_PATH + "mktTeam/saveMktTeam.do",
                            "type": "post",
                            "contentType": "application/json",
                            "data": JSON.stringify({
                                "id": itemData.id,
                                "salesDegrees": incomeInputValue
                            }),
                            "success": function (responseData) {
                                if (responseData.flag) {
                                    vm.teamList.set(atIndex, {
                                        "incomeState": "r",
                                        "incomeBtnText": "修改",
                                        "income": incomeInputValue
                                    });
                                }
                            }
                        });
                    } else {
                        vm.teamList.set(atIndex, {
                            "incomeState": "e",
                            "incomeBtnText": "保存"
                        });
                    }
                };
                vm.clickSupportVisibleBtn = function () {
                    var atIndex = this.$vmodel.$model.$index,
                        itemData = this.$vmodel.$model.el,
                        supportVisibleState = vm.teamList[atIndex].supportVisibleState;
                    if (supportVisibleState) {
                        vm.teamList.set(atIndex, {
                            "supportVisibleState": false,
                            "supportVisibleCtrBtnText": "查看支持者",
                            "supportList": []
                        });
                    } else {
                        fetchSupportList({
                            "teamId": itemData.id
                        }, function (data) {
                            vm.teamList.set(atIndex, {
                                "supportVisibleState": true,
                                "supportVisibleCtrBtnText": "收起",
                                "supportNum": data.length,
                                "supportList": _.map(data, function (itemData) {
                                    return {
                                        "id": itemData.id,
                                        "supportName": itemData.userName,
                                        "position": itemData.position.positionName
                                    };
                                })
                            });
                        });
                    }
                };
                /**
                 * 添加更多的支持者
                 */
                vm.addMoreSupport = function () {
                    var supportDialogVm = avalon.getVm(addSupportDialogId),
                        userSelectorVm = avalon.getVm(userSelectorId),
                        teamData = this.$vmodel.$model.el,
                        atIndex = this.$vmodel.$model.$index,
                        supportList = this.$vmodel.$model.el.supportList;
                    //查看所有的用户
                    util.c2s({
                        "url": erp.BASE_PATH + "mktTeam/listNoChooseSupport.do",
                        "type": "get",
                        "data": {
                            "teamId": teamData.id
                        },
                        "success": function (responseData) {
                            var data,
                                chooseSupportList,
                                noChooseSupportList;
                            if (responseData.flag) {
                                //filter选中的item
                                data = responseData.data;
                                chooseSupportList = data.chooseSupportList;
                                noChooseSupportList = data.noChooseSupportList;
                                //设置已选中的item
                                userSelectorVm.store = _.map(chooseSupportList, function (itemData) {
                                    /*var selected = _.some(supportList, function (itemData2) {
                                        return itemData2.id == itemData.id;
                                    });*/
                                    return {
                                        "text": '<div class="fn-clear"><div class="fn-left">' + itemData.userName + '</div><div class="fn-right">' + itemData.position.positionName + '</div></div>',
                                        "visible": true,
                                        "selected": true,
                                        "preSelected": false,
                                        "userName": itemData.userName,
                                        "positionName": itemData.position.positionName,
                                        "id": itemData.id
                                    };
                                }).concat(_.map(noChooseSupportList, function (itemData) {
                                    return {
                                        "text": '<div class="fn-clear"><div class="fn-left">' + itemData.userName + '</div><div class="fn-right">' + itemData.position.positionName + '</div></div>',
                                        "visible": true,
                                        "selected": false,
                                        "preSelected": false,
                                        "userName": itemData.userName,
                                        "positionName": itemData.position.positionName,
                                        "id": itemData.id
                                    };
                                }));
                                userSelectorVm.rightTitle = '已添加的支持者（' + chooseSupportList.length + '）';
                                //设置当前操作的team信息
                                vm.activeTeamId = teamData.id;
                                vm.activeTeamAtIndex = atIndex;
                                supportDialogVm.open();
                            }
                        }
                    });
                };
                /**
                 * 删除支持者
                 */
                vm.removeSupport = function () {
                    var meEl = avalon(this),
                        teamId = meEl.data('teamId'),
                        userId = meEl.data('userId'),
                        atIndex = this.$vmodel.$outer.$index;
                    util.c2s({
                        "url": erp.BASE_PATH + 'mktTeam/deleteSupport.do',
                        "type": "post",
                        "data": {
                            "teamId": teamId,
                            "supportId": userId
                        },
                        "success": function (responseData) {
                            if (responseData.flag) {
                                fetchSupportList({
                                    "teamId": teamId
                                }, function (data) {
                                    vm.teamList.set(atIndex, {
                                        "supportNum": data.length,
                                        "supportList": _.map(data, function (itemData) {
                                            return {
                                                "id": itemData.id,
                                                "supportName": itemData.userName,
                                                "position": itemData.position.positionName
                                            };
                                        })
                                    });
                                });
                            }
                        }
                    });
                };
            });

            avalon.scan(pageEl[0]);
            //监听selectedchange事件
            avalon.getVm(userSelectorId).$watch('selectedchange', function () {
                this.rightTitle = '已添加的支持者（' + _.filter(this.store.$model, function (itemData) {
                    return itemData.selected;
                }).length + '）';
            });
            //更新列表
            updateList();
            //for test
            //avalon.getVm(addSupportDialogId).open();
            /**
             * 更新team列表
             */
            function updateList () {
                var currentYearVm = avalon.getVm(currentYearId),
                    currentMonthVm = avalon.getVm(currentMonthId);
                util.c2s({
                    "url": erp.BASE_PATH + "mktTeam/list.do",
                    "type": "get",
                    "data": {
                        "date": moment(currentYearVm.selectedValue + '-' + currentMonthVm.selectedValue, 'YYYY-M') / 1
                    },
                    "success": function (responseData) {
                        var data = responseData.data;
                        if (responseData.flag) {
                            pageVm.teamList = _.map(data, function (itemData) {
                                return {
                                    "id": itemData.id,
                                    "teamName": itemData.teamName,
                                    "teamLeader": itemData.leaderName,
                                    "teamMember": itemData.teamMember,
                                    "teamWord": itemData.teamSlogan,
                                    "odds": itemData.teamOdds,
                                    "oddsState": "r",
                                    "oddsBtnText": "修改",
                                    "income": itemData.salesDegrees,
                                    "incomeState": "r",
                                    "incomeBtnText": "修改",
                                    "supportVisibleCtrBtnText": "查看支持者",
                                    "supportVisibleState": false,
                                    "supportNum": itemData.supportCount,
                                    "supportList": []
                                };
                            });
                        }
                    }
                });
            }
            /**
             * 获取指定team的所有支持者
             */
            function fetchSupportList (requestData, callback) {
                util.c2s({
                    "url": erp.BASE_PATH + 'mktTeam/listSupport.do',
                    "type": "get",
                    "data": requestData,
                    "success": function (responseData) {
                        if (responseData.flag) {
                            callback && callback(responseData.data);
                        }
                    }
                });
            }
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var addSupportDialogId = pageName + '-add-support-dialog',
                userSelectorId = pageName + '-user-selector',
                currentYearId = pageName + '-current-year',
                currentMonthId = pageName + '-current-month';
            $(avalon.getVm(currentYearId).widgetElement).remove();
            $(avalon.getVm(currentMonthId).widgetElement).remove();
            $(avalon.getVm(userSelectorId).widgetElement).remove();
            $(avalon.getVm(addSupportDialogId).widgetElement).remove();
        }
    });

    return pageMod;
});
