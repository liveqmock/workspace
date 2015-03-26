/**
 * SPA入口
 * Created by q13 on 14-5-9.
 */
define(["avalon", "util", "common", "ready!"], function (avalon, util, common) {
    var win = this,
        app = win.app,
        page = app.page,
        pageEvent = page.pageEvent,
        pageMod = {};
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function () {
            //公共逻辑初始化后进入页面逻辑
            common.setup(function () {
                //util.regPageNav('.test-page-nav');
                //创建app controller
                var appBdEl = $('.app-bd'),
                    appHdEl = $('.app-hd');
                var appVm = avalon.define('app', function (vm) {
                    vm.appBdMinHeight = 0;  //内容区最小高度
                    vm.pageTitle = '';
                    vm.blankImgSrc = app.ASSET_PATH + 'image/s.gif';
                    vm.adjustBdMinHeight = function () {
                        var minHeight = $('.app-inner-l', appBdEl).height(),
                            hdHeight = appHdEl.outerHeight(),
                            winHeight = $(win).height();
                        if (minHeight < winHeight - hdHeight) {
                            minHeight = winHeight - hdHeight;
                        }
                        vm.appBdMinHeight = minHeight;
                    };
                    vm.mainNavToggle = function () {
                        var mainNavItemEl = $(this).closest('.page-nav-item'),
                            iconToggleEl = $('.icon-toggle', this),
                            subListEl = mainNavItemEl.children('.page-nav-list'),
                            state = iconToggleEl.data('state');
                        if (!state || state == 'shown') {
                            subListEl.stop(true,true).slideUp('fast', function () {
                                vm.adjustBdMinHeight();
                            });
                            iconToggleEl.html('&#xe600;'); // +
                            iconToggleEl.data('state', 'hidden');
                        } else {
                            subListEl.stop(true,true).slideDown('fast', function () {
                                vm.adjustBdMinHeight();
                            });
                            iconToggleEl.html('&#xe601;'); // -
                            iconToggleEl.data('state', 'shown');
                        }
                    };
                    vm.clickNavVisibleHandler = function () {
                        var meEl = $(this),
                            leftEl = $(".app-inner-l", appBdEl),
                            leftInnerEl = $(".app-inner-l-inner", appBdEl),
                            rightEl = $(".app-inner-r", appBdEl);
                        if (meEl.hasClass("state-shown")) {
                            leftInnerEl.hide();
                            leftEl.css("overflow", "hidden").stop(true, true).animate({
                                "width": 0
                            }, "fast", function () {
                                rightEl.css("margin-left", 0);
                                meEl.css("left", 0);
                                meEl.addClass("state-hidden").removeClass("state-shown").html("&#xe625;");
                            });
                        } else {
                            leftInnerEl.show();
                            leftEl.css("overflow", "visible").stop(true, true).animate({
                                "width": "230px"
                            }, "fast", function () {
                                rightEl.css("margin-left", "231px");
                                meEl.css("left", 230 - $(win).scrollLeft() + "px");
                                meEl.addClass("state-shown").removeClass("state-hidden").html("&#xe624;");
                            });
                        }
                    };
                });
                //构建左侧菜单
                var pageNavWEl = $('.page-nav-wrapper');
                pageNavWEl.html(createPageNav(page.routes));
                //延时设置保证高度严格一致
                setTimeout(function () {
                    appVm.adjustBdMinHeight();
                }, 300);
                //注册高亮导航
                util.regPageNav($('a', pageNavWEl));
                //监听resize事件设置最小高度
                /*util.onLazyResize(function () {
                    appVm.appBdMinHeight = $(win).height() - $('.app-hd').outerHeight();
                }, 100, true);*/
                //注册scroll事件重设左侧导航位置
                var pageNavVisibleTid;
                $(win).scroll(function () {
                    var handlerEl = $('.page-nav-visible-h', appBdEl);
                    clearTimeout(pageNavVisibleTid);
                    pageNavVisibleTid = setTimeout(function () {
                        if (handlerEl.hasClass('state-shown')) {
                            handlerEl.css({
                                "left": 230 - $(this).scrollLeft() + "px"
                            });
                        }
                    }, 30);
                });
                //设置title
                pageEvent.on('inited switched', function (pageEl, pageName, routeData) {
                    appVm.adjustBdMinHeight();
                    appVm.pageTitle = routeData.title;
                });
                avalon.scan();

                //空导航自动跳到欢迎页
                if (!page.lastPagePath) {
                    util.jumpPage('#/welcome');
                }
                //自动打开对应的功能栏
                var tempNavLinkEl,
                    tempListEl,
                    tempNavTitleEl;
                if (page.lastPagePath) {
                    tempNavLinkEl = $('[href="#' + page.lastRoute + '"]', pageNavWEl);
                    tempListEl = tempNavLinkEl.closest('.page-nav-list');
                    tempNavTitleEl = tempListEl.prev('.nav-title');
                    while (tempNavTitleEl.length) {
                        tempNavLinkEl = $('a', tempNavTitleEl);
                        tempListEl = tempNavLinkEl.closest('.page-nav-list');
                        tempNavTitleEl = tempListEl.prev('.nav-title');
                    }
                    $('.icon-toggle', tempNavLinkEl).click();
                }
            });
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function () {}
    });

    //构建左侧页面导航菜单
    function createPageNav(routeData, level) {
        var htmlStr = '',
            listCls;
        level = level || 0;
        level++;    //先加1
        _.each(routeData, function (itemData) {
            var href,
                linkText = itemData.navTitle || itemData.title,
                itemCls = '',
                linkCls = '',
                msClick = '',
                msHover = '',
                phref = itemData.phref ? 'phref=' + itemData.phref : '',
                forceRefresh = !!itemData.forceRefresh;
            if (itemData.path) {
                href = itemData.path;
            } else {
                href = itemData.route? '#' + itemData.route : 'javascript:;';
            }
            if (level === 1) {
                linkText = '<i class="icon-toggle iconfont" data-state="hidden">&#xe600;</i>&nbsp;&nbsp;' + linkText;
                msClick = 'ms-click="mainNavToggle"';
            } else {
                msHover = 'ms-hover=state-hover';
            }
            if (itemData.isHidden) {
                itemCls = 'fn-hide';
            }
            if (forceRefresh) {
                linkCls += 'force-refresh-link';
            }
            htmlStr += '<li class="page-nav-item ' + itemCls + '"><div class="nav-title"><a '+ msHover +' href="' + href + '" '+ msClick +' ' + phref + ' class="' + linkCls + '">' + linkText + '</a></div>';
            if (itemData.children && itemData.children.length > 0) {
                htmlStr += createPageNav(itemData.children, level) + '</li>';
            } else {
                htmlStr += '</li>';
            }
        });
        if (level === 1) {
            listCls = 'main-page-nav-list';
        } else {
            listCls = 'sub-page-nav-list fn-hide';
        }
        return '<ul class="'+ listCls +' page-nav-list page-nav-'+ level +'">' + htmlStr + '</ul>';
    }
    return pageMod;
});
