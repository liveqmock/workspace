/**
 * SPA入口
 * Created by q13 on 14-5-9.
 */
define(["avalon", "util", "common", "../module/merchantdomain/merchantdomain", "../module/chargepwdsetting/chargepwdsetting", "../module/useroperation/useroperation", "ready!"], function (avalon, util, common) {
    var win = this,
        app = win.app,
        page = app.page,
        pageEvent = page.pageEvent,
        pageMod = {},
        timer = null;
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function () {
            var loginUserData = app.getAppData('user');
            page.pageEvent.on('beforeswitch', function (pageName, routeData) {
                //只执行一次
                page.pageEvent.off('beforeswitch', arguments.callee);
                if (routeData.noSupportFace && loginUserData.faceShopType === 3) {
                    avalon.nextTick(function () {
                        util.alert({
                            "iconType": "error",
                            "content": "对不起，在门店下无此功能权限。",
                            "onSubmit": function () {
                                util.jumpPage('#/welcome'); //跳到欢迎页
                            }
                        });
                    });
                    return false;
                }
                if (routeData.noSupportRegion && loginUserData.faceShopType === 2) {
                    avalon.nextTick(function () {
                        util.alert({
                            "iconType": "error",
                            "content": "对不起，在域下无此功能权限。",
                            "onSubmit": function () {
                                util.jumpPage('#/welcome'); //跳到欢迎页
                            }
                        });
                    });
                    return false;
                }
                if (routeData.noSupportGroup && loginUserData.faceShopType === 1) {
                    avalon.nextTick(function () {
                        util.alert({
                            "iconType": "error",
                            "content": "对不起，在集团下无此功能权限。",
                            "onSubmit": function () {
                                util.jumpPage('#/welcome'); //跳到欢迎页
                            }
                        });
                    });
                    return false;
                }
            });
            //公共逻辑初始化后进入页面逻辑
            common.setup(function () {
                //util.regPageNav('.test-page-nav');
                //创建app controller
                var appBdEl = $('.app-bd'),
                    appHdEl = $('.app-hd');
                var loginUserData = app.getAppData('user');
                var appVm = avalon.define('app', function (vm) {
                    vm.showOper = false;
                    vm.operTop = 0;
                    vm.operLeft = 0;
                    vm.appBdMinHeight = 0; //内容区最小高度
                    vm.pageTitle = '';
                    vm.blankImgSrc = app.ASSET_PATH + 'image/s.gif';
                    vm.loginUserName = loginUserData.userName;
                    vm.adjustBdMinHeight = function () {
                        var minHeight = $('.app-inner-l', appBdEl).height(),
                            hdHeight = appHdEl.outerHeight(),
                            winHeight = $(win).height();
                        if (minHeight < winHeight - hdHeight) {
                            minHeight = winHeight - hdHeight;
                        }
                        vm.appBdMinHeight = minHeight;
                    };
                    vm.$merchantDomainOpts = {
                        "merchantdomainId": "app-merchant-domain"
                    };
                    vm.$chargepwdsettingOpts = {
                        "chargepwdsettingId": "app-charge-pwd-setting"
                    };
                    vm.$useroperaitonOpts = {
                        "useroperaitonId": "app-charge-pwd-useroperaiton"
                    };
                    vm.showOperation = function () {
                        clearTimeout(timer);
                        timer = setTimeout(function () {
                            appVm.showOper = true;
                            appVm.operTop = $('.tips-layer').offset().top + 62;
                            appVm.operLeft = $('.tips-layer').offset().left - 88;
                        }, 100);

                    };
                    vm.closeOperation = function () {
                        clearTimeout(timer);
                        timer = setTimeout(function () {
                            appVm.showOper = false;
                        }, 200)
                    };
                    vm.showMe = function () {
                        clearTimeout(timer);
                        timer = setTimeout(function () {
                            appVm.showOper = true;
                        }, 50);
                    };
                    vm.closeMe = function () {
                        clearTimeout(timer);
                        timer = setTimeout(function () {
                            appVm.showOper = false;
                        }, 200)
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
                //设置title
                pageEvent.on('inited switched', function (pageEl, pageName, routeData) {
                    appVm.adjustBdMinHeight();
                    appVm.pageTitle = routeData.title;
                });
                //注册页面事件，对于只支持集团和域的页面，如果当前domain在门店，则路由跳转后再强制跳回来
                page.pageEvent.on('beforeswitch', function (pageName, routeData) {
                    var domainVm = avalon.getVm("app-merchant-domain");
                    if (routeData.noSupportFace && domainVm.merchantType === 3) {
                        util.alert({
                            "iconType": "error",
                            "content": "对不起，在门店下无此功能权限。",
                            "onSubmit": function () {
                                util.jumpPage('#/' + page.lastPagePath); //跳到上个页面
                            }
                        });
                        return false;
                    }
                    if (routeData.noSupportRegion && domainVm.merchantType === 2) {
                        util.alert({
                            "iconType": "error",
                            "content": "对不起，在域下无此功能权限。",
                            "onSubmit": function () {
                                util.jumpPage('#/' + page.lastPagePath); //跳到上个页面
                            }
                        });
                        return false;
                    }
                    if (routeData.noSupportGroup && domainVm.merchantType === 1) {
                        util.alert({
                            "iconType": "error",
                            "content": "对不起，在集团下无此功能权限。",
                            "onSubmit": function () {
                                util.jumpPage('#/' + page.lastPagePath); //跳到上个页面
                            }
                        });
                        return false;
                    }
                });
                avalon.scan();
                //初始化门店信息
                avalon.getVm('app-merchant-domain').initDomain();

                //空导航自动跳到首页
                var firstActionPageRoute;  //第一个功能路由
                if (!page.lastPagePath) {
                    //如果是门店，直接跳到广告设置页
                    if (loginUserData.faceShopType === 3) {
                        firstActionPageRoute = page.routes[1];
                        if (firstActionPageRoute) {
                            util.jumpPage(firstActionPageRoute.path || "#" + firstActionPageRoute.route);
                        } else {
                            util.jumpPage('#/welcome');
                        }
                    } else {
                        util.jumpPage('#/welcome');
                    }
                }
                //自动高亮对应的功能栏
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
                    //$('.icon-toggle', tempNavLinkEl).click();

                }
            });
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function () {
        }
    });

    //构建左侧页面导航菜单
    function createPageNav(routeData, level) {
        var htmlStr = '',
            listCls;
        level = level || 0;
        level++; //先加1
        _.each(routeData, function (itemData) {
            var href,
                navTitle = itemData.navTitle || itemData.title,
                linkText = '',
                itemCls = '',
                linkCls = '',
                msClick = '',
                msHover = '',
                phref = itemData.phref ? 'phref=' + itemData.phref : '',
                forceRefresh = !!itemData.forceRefresh;
            if (itemData.path) {
                href = itemData.path;
            } else {
                href = itemData.route ? '#' + itemData.route : 'javascript:;';
            }
            //导航内容
            linkText = '<i class="icon-paper iconfont">' + getWebFontFromRoute(itemData.route) + '</i><i class="icon-arrow iconfont">&#xe600;</i>';
            //悬浮样式
            msHover = 'ms-hover=state-hover';
            if (itemData.isHidden) {
                itemCls = 'fn-hide';
            }
            if (forceRefresh) {
                linkCls += 'force-refresh-link';
            }
            htmlStr += '<li class="page-nav-item ' + itemCls + '"><div class="nav-title"><a ' + msHover + ' href="' + href + '" ' + msClick + ' ' + phref + ' class="' + linkCls + '" title="' + navTitle + '">' + linkText + '</a></div>';
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
        return '<ul class="' + listCls + ' page-nav-list page-nav-' + level + '">' + htmlStr + '</ul>';
    }

    /**
     * 根据导航路由获取图标编码
     */
    function getWebFontFromRoute(route) {
        var webFont;
        switch (route) {
            case "/home":
                webFont = "&#xe607;";
                break;
            case "/network":
                webFont = "&#xe606;";
                break;
            case "/dailypaper":
                webFont = "&#xe605;";
                break;
            case "/adv":
                webFont = "&#xe603;";
                break;
            case "/service":
                webFont = "&#xe607;";
                break;
            case "/user":
                webFont = "&#xe604;";
                break;
            case "/member":
                webFont = "&#xe612;";
                break;
            default:
                webFont = "";
                break;
        }
        return webFont;
    }

    return pageMod;
});
