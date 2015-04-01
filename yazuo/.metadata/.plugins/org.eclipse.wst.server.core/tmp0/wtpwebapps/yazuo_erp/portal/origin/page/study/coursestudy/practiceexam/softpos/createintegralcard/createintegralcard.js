/**
 * 考试-办理积分卡
 */
define(['avalon', 'util', 'moment', 'json', '../../../../../../module/practicehelper/practicehelper'], function (avalon, util, moment, JSON) {
    var win = this,
        erp = win.erp,
        page = erp.page,
        pageMod = {},
        globalKeyBind,
        adjustActionBoxPos;
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName, routeData) {
            var practicehelperId = pageName + '-exam-board';
            var softposPageEl = $('.practice-exam-softpos', pageEl),
                dialogOverlayEl = $('.dialog-overlay', pageEl),
                portalSectionEl = $('.portal-section', pageEl),
                cardActiveTipEl = $('.card-active-tip', pageEl),
                cardActiveSectionEl = $('.card-active-section', pageEl),
                mainSectionEl = $('.main-section', pageEl),
                groupConsumeEl = $('.group-consume-box', mainSectionEl),
                actionBtnBoxEl = $('.action-btn-box', mainSectionEl),
                consumeConfirmEl = $('.consume-confirm', pageEl),
                printEl = $('.print', pageEl);
            var pageVm = avalon.define(pageName, function (vm) {
                vm.$examBoardOpts = {
                    "practicehelperId": practicehelperId,
                    "examPath": "/softpos/createintegralcard"
                };
                vm.cardNo = '';
                vm.result0 = null;
                vm.step0 = function (evt) {
                    var result = null;
                    if (evt.type == "click" || (evt.type == "keydown" && evt.keyCode == 13)) {
                        if (_.str.trim(vm.cardNo).search("6201200261500028") !== -1) {
                            result = true;
                            cardActiveTipEl.show();
                            dialogOverlayEl.show();
                        } else {
                            result = false;
                        }
                        vm.result0 = result;
                        evt.stopPropagation();
                        this.blur();
                    }
                };
                vm.result1 = null;
                vm.step1 = function (evt) {
                    var result = null,
                        mobile = _.str.trim($('.mobile', cardActiveSectionEl).val()),
                        userName = _.str.trim($('.user-name', cardActiveSectionEl).val()),
                        sex = $('.sex', cardActiveSectionEl).val(),
                        birthday = $('.birthday-year', cardActiveSectionEl).val() + '-' + $('.birthday-month', cardActiveSectionEl).val() + '-' + $('.birthday-day', cardActiveSectionEl).val();
                    if (mobile === "18500068578" && userName === "小雅" && sex == "2" && birthday === "1990-10-1") {
                        result = true;
                    } else {
                        result = false;
                    }
                    vm.result1 = result;
                    if (!mobile) {
                        $('.mobile-field-tip', cardActiveSectionEl).show();
                    } else {
                        $('.mobile-field-tip', cardActiveSectionEl).hide();
                        //跳转到组合消费页
                        mainSectionEl.show();
                        cardActiveSectionEl.hide();
                        $('.total-amount', mainSectionEl)[0].select();
                    }
                };
                vm.result2 = null;
                vm.step2 = function (evt) {
                    var result = null,
                        cashAmount = _.str.trim($('.cash-amount', groupConsumeEl).val());
                    if (cashAmount === "300") {
                        result = true;
                    } else {
                        result = false;
                    }
                    vm.result2 = result;
                    //跳转到消费确认页
                    $('.cash-value', consumeConfirmEl).text(cashAmount);
                    consumeConfirmEl.show();
                    dialogOverlayEl.show();
                };
                vm.validate = function () {
                    avalon.getVm(practicehelperId).next(vm);
                };
                vm.resetCardActive = function () {
                    $('.mobile', cardActiveSectionEl).val('');
                    $('.user-name', cardActiveSectionEl).val('');
                    $('.sex', cardActiveSectionEl).val('1');
                    $('.birthday-year', cardActiveSectionEl).val('');
                    $('.birthday-month', cardActiveSectionEl).val('');
                    $('.birthday-day', cardActiveSectionEl).val('');
                    $('.mobile-field-tip', cardActiveSectionEl).hide();
                };
                vm.enterCardActive = function () {
                    vm.resetCardActive();
                    cardActiveSectionEl.show();
                    portalSectionEl.hide();
                    cardActiveTipEl.hide();
                    dialogOverlayEl.hide();
                };
                vm.cancelCardActive = function () {
                    cardActiveTipEl.hide();
                    dialogOverlayEl.hide();
                    $('.card-input', portalSectionEl)[0].focus();
                };
                vm.leaveCardActive = function () {
                    var cardInputEl = $('.card-input', portalSectionEl);
                    portalSectionEl.show();
                    cardActiveSectionEl.hide();
                    cardInputEl.val('');
                    cardInputEl[0].focus();
                };
                vm.enterPrint = function () {
                    $('.cash-value', printEl).text(_.str.trim($('.cash-amount', groupConsumeEl).val()));
                    printEl.show();
                    consumeConfirmEl.hide();
                    dialogOverlayEl.show();
                };
                vm.leaveMain = function () {
                    var cardInputEl = $('.card-input', portalSectionEl);
                    portalSectionEl.show();
                    mainSectionEl.hide();
                    cardInputEl.val('');
                    cardInputEl[0].focus();
                };
                vm.cancelConsumeConfirm = function () {
                    consumeConfirmEl.hide();
                    dialogOverlayEl.hide();
                };
                vm.reEnterPortal = function () {
                    var cardInputEl = $('.card-input', portalSectionEl);
                    printEl.hide();
                    dialogOverlayEl.hide();
                    portalSectionEl.show();
                    mainSectionEl.hide();
                    cardInputEl.val('');
                    cardInputEl[0].focus();
                    vm.validate();
                };
            });
            avalon.scan(pageEl[0]);
            //焦点放到输入框里
            setTimeout(function () {
                $('.card-input', portalSectionEl)[0].focus();
            }, 600);
            //开启倒计时
            avalon.getVm(practicehelperId).startCountdown();
            //卡激活事件绑定
            (function () {
                var birthdayWEl = $('.birthday-wrapper', cardActiveSectionEl),
                    yearEl = $('.birthday-year', birthdayWEl),
                    monthEl = $('.birthday-month', birthdayWEl),
                    dayEl = $('.birthday-day', birthdayWEl),
                    nowYear = new Date().getFullYear(),
                    nowMonth = new Date().getMonth() + 1,
                    htmlStr = '';
                //构建生日年份和月份
                _.each(_.range(0, 100), function (num) {
                    var currentYear = nowYear - num;
                    htmlStr += '<option value="' + currentYear + '">' + currentYear + '年</option>';
                });

                pageEl.on('change', '.birthday-year', function () {
                    var htmlStr = '',
                        selectedYear = yearEl.val(),
                        endMonth = (selectedYear == nowYear ? new Date().getMonth() + 2 : 13);
                    if (selectedYear == "0") {
                        monthEl.html('<option value="0"></option>');
                    } else {
                        _.each(_.range(1, endMonth), function (num) {
                            htmlStr += '<option value="' + num + '">' + num + '月</option>';
                        });
                        monthEl.html('<option value="0"></option>' + htmlStr).change();
                    }
                }).on('change', '.birthday-month', function () {
                    var htmlStr = '',
                        selectedYear = yearEl.val(),
                        selectedMonth = monthEl.val(),
                        endDay = ((selectedYear == nowYear && selectedMonth == nowMonth) ? new Date().getDate() : moment(selectedYear + '-' + selectedMonth, 'YYYY-M').endOf('month').date());
                    if (selectedYear == "0" || selectedMonth == "0") {
                        dayEl.html('<option value="0"></option>');
                    } else {
                        _.each(_.range(1, endDay + 1), function (num) {
                            htmlStr += '<option value="' + num + '">' + num + '日</option>';
                        });
                        dayEl.html('<option value="0"></option>' + htmlStr);
                    }

                }).on('keyup', '.mobile', function () {
                    var meEl = $(this),
                        v = _.str.trim(meEl.val());
                    if (v !== "") {
                        if (isNaN(v)) {
                            meEl.val(meEl.data('oldValue') || "");
                        } else {
                            meEl.val(v);
                            meEl.data('oldValue', v);
                        }
                    } else {
                        v = parseInt(_.str.trim(meEl.val()), 10);
                        meEl.val(0);
                        meEl.data('oldValue', '');
                    }
                });
                $('.birthday-year', birthdayWEl).html('<option value="0"></option>' + htmlStr);
            }());
            //主功能区事件绑定
            adjustActionBoxPos = function () {
                actionBtnBoxEl.css({
                    "top": $(win).height() - 180 + $(win).scrollTop() - 44
                });
            };
            pageEl.on('keyup', '.group-consume-box .total-amount, .group-consume-box .cash-amount', function () {
                var meEl = $(this),
                    v = _.str.trim(meEl.val());
                if (v !== "") {
                    if (isNaN(v)) {
                        meEl.val(meEl.data('oldValue') || "");
                    } else {
                        meEl.val(v);
                        meEl.data('oldValue', v);
                    }
                } else {
                    v = parseFloat(_.str.trim(meEl.val()), 10);
                    meEl.val(0);
                    meEl.data('oldValue', '');
                }
            }).on('keyup', '.group-consume-box .total-amount', function () {
                var meEl = $(this),
                    v = parseFloat(_.str.trim(meEl.val()), 10);
                if (!isNaN(v)) {
                    $('.cash-amount', groupConsumeEl).val(v);
                }
            });
            //绑定快捷键
            globalKeyBind = function (evt) {
                if (evt.keyCode == 13) {
                    if (cardActiveTipEl.is(':visible')) {
                        pageVm.enterCardActive();
                    }
                } else if (evt.keyCode == 27) {
                    if (cardActiveTipEl.is(':visible')) {
                        pageVm.cancelCardActive();
                    }
                }

            };
            $(document).on('keydown', globalKeyBind);
            $(win).on('scroll resize', adjustActionBoxPos);
            adjustActionBoxPos();

            /*===========私有函数放下面==============*/
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            var practicehelperId = pageName + '-exam-board';
            $(avalon.getVm(practicehelperId).widgetElement).remove();
            $(document).off('keydown', globalKeyBind);
            globalKeyBind = null;
            $(win).off('scroll resize', adjustActionBoxPos);
            adjustActionBoxPos = null;
        }
    });

    return pageMod;
});
