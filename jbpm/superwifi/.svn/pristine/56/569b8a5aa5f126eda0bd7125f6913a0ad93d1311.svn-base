/**
 * 工具库
 * Created by q13 on 14-5-9.
 */
define(["avalon", "mmRouter", "events", 'json', "dialog"], function (avalon, mmRouter, Events, JSON, avalon_) {
    var win = this,
        doc = document,
        app = this.app,
        page = app.page,
        util = {};
    var aslice = Array.prototype.slice,
        pageEvent = new Events(); //设置页面相关自定义事件
    var loginUserData = app.getAppData('user');
    //设置事件引用
    page.pageEvent = pageEvent;
    util = _.extend(util, {
        "generateID": function () {
            //生成UUID http://stackoverflow.com/questions/105034/how-to-create-a-guid-uuid-in-javascript
            return "q13" + Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
        },
        "ajax": (function () {
            var xhrStore = [];
            return function (opts, cusOpts) {
                opts = _.extend({
                    "dataType": "json"
                }, opts || {});
                cusOpts = _.extend({
                    "mask": false, // true/false or jquery selector
                    "ajaxType": "none", // ignore(等上次请求完才能发请求)/abort(直接中断上次请求)/none(可发多个相同请求)
                    "withData": true //在ajaxType不等于none时起作用
                }, cusOpts || {});
                //处理遮罩
                var maskEl,
                    maskRefEl,
                    maskRefOffset,
                    maskRefTid,
                    maskRefExecute,
                    maskRefResizer;
                if (cusOpts.mask === true) { //全局遮罩共用一个遮罩
                    maskEl = $('.ajax-global-mask');
                    if (maskEl.length === 0) {
                        maskEl = $('<div class="ajax-mask ajax-global-mask"></div>');
                        maskEl.html('<div class="ajax-mask-inner"></div>');
                        maskEl.appendTo('body');
                    }
                    //maskEl.addClass('ajax-global-mask');
                    maskEl.show();
                } else if (cusOpts.mask !== false) { //局部遮罩各自用自己的遮罩
                    maskEl = $('<div class="ajax-mask ajax-part-mask"></div>');
                    maskEl.html('<div class="ajax-mask-inner"></div>');
                    maskEl.hide().appendTo('body');
                    maskRefEl = $(cusOpts.mask);
                    maskRefExecute = function () {
                        maskRefOffset = maskRefEl.offset();
                        maskEl.css({
                            "top": maskRefOffset.top,
                            "left": maskRefOffset.left,
                            "width": maskRefEl.outerWidth(),
                            "height": maskRefEl.outerHeight()
                        });
                    };
                    //注册window resize事件
                    maskRefResizer = function () {
                        clearTimeout(maskRefTid);
                        maskRefTid = setTimeout(function () {
                            maskRefExecute();
                        }, 300);
                    };
                    $(window).bind('resize', maskRefResizer);
                    //先执行一次
                    avalon.nextTick(function () {
                        maskRefExecute();
                        maskEl.show();
                    });
                }
                //处理ajax类型，防止多次请求
                var isIgnore = false;
                if (cusOpts.ajaxType == "abort" || cusOpts.ajaxType == "ignore") {
                    _.some(xhrStore, function (itemData) {
                        var data = itemData.data,
                            url = itemData.url,
                            xhr,
                            state;
                        if (url == opts.url && (!cusOpts.withData || (cusOpts.withData && _.isEqual(data, opts.data)))) {  //带请求参数判定和不带请求参数判定
                            xhr = itemData.xhr;
                            state = xhr.state();
                            if (state === "pending") {
                                cusOpts.ajaxType == "abort" ? xhr.abort() : (isIgnore = true);
                                return true;
                            }
                        }
                    });
                    if (isIgnore) { //需要等待的请求直接返回，不做任何操作
                        return;
                    }
                }
                var xhr,
                    id;
                xhr = $.ajax(opts);
                //生成唯一id
                id = util.generateID();
                xhr.always(function () {
                    //隐藏遮罩
                    if (cusOpts.mask === true) {    //全局遮罩需要等所有带全局遮罩的请求完成才能隐藏
                        if (_.every(_.filter(xhrStore, function (itemData) {
                            return itemData.id != id && itemData.mask === true;
                        }), function (itemData) {
                            return itemData.xhr.state() !== "pending";
                        })) {
                            maskEl.hide();
                        }
                    } else if (cusOpts.mask !== false) {    //局部遮罩各自清除
                        maskEl.remove();
                        maskEl = null;
                        maskRefTid && clearTimeout(maskRefTid) && (maskRefTid = null);
                        maskRefResizer && $(window).unbind('resize', maskRefResizer) && (maskRefResizer = null);
                    }
                    //清除对应存储
                    xhrStore = _.reject(xhrStore, function (itemData) {
                        return itemData.id == id;
                    });
                });
                //设置存储
                xhrStore.push({
                    "xhr": xhr,
                    "url": opts.url,
                    "data": opts.data,
                    "id": id,
                    "mask": cusOpts.mask
                });
                return xhr;
            };
        }()),
        /**
         * 与后台通信接口，异步请求方式，json格式
         */
        "c2s": function (opts, cusOpts) {
            var xhr;
            opts = _.extend({
                "type": "post",
                "contentType": "application/json; charset=utf-8",
                "dataType": "json",
                "cache": false
            }, opts);
            //设置默认的品牌和当前门店请求参数
            opts.data = _.extend({
                "brandId": loginUserData.brandId,
                "merchantId": loginUserData.currentMerchantId || loginUserData.merchantId
            }, opts.data || {});

            opts.data = JSON.stringify(opts.data);
            cusOpts = _.extend({
                "mask": true,
                "ajaxType": "ignore",    //防止二次提交
                "withData": true
            }, cusOpts || {});
            xhr = util.ajax(opts, cusOpts);
            if (xhr) {
                xhr.done(function (responseData) {
                    if (!responseData.flag) {   //错误统一处理
                        if (responseData.timeout) { //超时自动跳到登录页
                            location.href = app.BASE_PATH + 'login.jsp';
                        } else {
                            util.alert({
                                "content": responseData.message,
                                "iconType": "error"
                            });
                        }

                    }
                });
                xhr.fail(function () {
                    util.alert({
                        "content": "服务端请求失败",
                        "iconType": "error"
                    });
                });
            }
            return xhr;
        },
        /**
         * 注册app router
         * @param {Selector} pageWSelector 页面容器选择符
         * @param {Selector} maskWSelector 页面切换遮罩选择符
         * @param {Function} callback
         */
        "regRouter": function (pageWSelector, maskWSelector, callback) {
            var maskWEl,
                pageWEl = $(pageWSelector), //页面容器
                pageMaskEl; //页面切换遮罩
            if (!callback && _.isFunction(maskWSelector)) {
                callback = maskWSelector;
                maskWSelector = pageWSelector;
            }
            maskWEl = $(maskWSelector);
            pageMaskEl = $('.page-mask', maskWEl);

            var flatRouteData = function (routeData) {
                var rs = [];
                _.each(routeData, function (itemData) {
                    //过滤权限
                    var belongToPermission = itemData.belongToPermission;
                    if (!belongToPermission.length) {   //空权限，滤掉
                        return;
                    }
                    //只有在权限内的才会被注册路由
                    if (belongToPermission === "all" || _.some(belongToPermission, function (permissionName) {
                        return util.hasPermission(permissionName);
                    })) {
                        //生成一份新的路由拷贝
                        if (itemData.route) {
                            rs.push(_.extend({}, itemData));
                        }
                        //下一级
                        if (itemData.children) {
                            rs = rs.concat(flatRouteData(itemData.children));
                        }
                    }
                });
                return rs;
            };
            /**
             * 获取有效地路由数据，filter掉无权限访问的路由, 返回树形结构
             */
            var getValidRouteData = function (routeData) {
                var rs = [];
                _.each(routeData, function (itemData) {
                    var newData;
                    //过滤权限
                    var belongToPermission = itemData.belongToPermission;

                    if (!belongToPermission.length) {   //空权限，滤掉
                        return;
                    }
                    //只有在权限内的才会被注册路由
                    if (belongToPermission === "all" || _.some(belongToPermission, function (permissionName) {
                        return util.hasPermission(permissionName);
                    })) {
                        //生成一份新的路由拷贝
                        newData = _.extend({}, itemData);
                        rs.push(newData);
                        //下一级
                        if (itemData.children) {
                            newData.children = getValidRouteData(itemData.children);
                        }
                    }
                });

                return rs;
            };
            var activePage = function (pageName, slient) {
                var cacheStore = page.cacheStore,
                    pageData = cacheStore[pageName],
                    pageEl = pageData["pageEl"];
                $('.page-state-active', pageWEl).removeClass('page-state-active');
                pageEl.addClass('page-state-active');
                //隐藏页面切换遮罩
                pageMaskEl.hide();
                //触发自定义事件
                !slient && pageEvent.trigger('switched', pageEl, pageName, pageData["routeData"]);
            };
            var removePage = function (pageName) {
                var cacheStore = page.cacheStore,
                    pageData = cacheStore[pageName],
                    routeData = pageData["routeData"],
                    pageEl = pageData["pageEl"];
                //调用$remove
                pageData.$remove(pageEl, pageName, routeData);
                //事件注册清理和element移除
                pageEl.off();
                pageEl.remove();

                //释放页面存储引用
                cacheStore[pageName] = null;
                delete cacheStore[pageName];
                //尝试清除页面可能存在的vm
                avalon.vmodels[pageName] = null;
                delete avalon.vmodels[pageName];
                //触发页面清除事件
                pageEvent.trigger('removed', pageName);
            };
            if (pageMaskEl.length === 0) {
                pageMaskEl = $('<div class="page-mask"></div>');
                pageMaskEl.appendTo(maskWEl);
            }
            util.ajax({
                "url": app.PAGE_PATH + 'router.json',
                "dataType": "json",
                "type": "get",
                "success": function (responseData) {
                    //路由注册
                    var rows = responseData.rows;
                    //保存路由信息
                    page.routes = getValidRouteData(rows);

                    //page.routes = rows;
                    page.lastPagePath = '';    //保存上一个页面路径信息
                    page.lastRoute = '';    //最近的一个路由信息
                    page.lastRouteData = null;    //最近的一个路由完整信息

                    _.each(flatRouteData(rows), function (row) {

                        var route = row.route,
                            css = [].concat(row.css),
                            js = [].concat(row.js),
                            tmpl = row.html,
                            isCache = row.isCache,
                            deps,
                            htmlPath,
                            cacheTmplContent;   //保存请求的模板引用，每个页面都缓存，会不会有性能问题
                        //get方式注册路由
                        mmRouter.router.get(route, function () {
                            //加载顺序:样式->结构->逻辑
                            var router = this,
                            //path = this.path.slice(1),
                                path = route.slice(1),  //去掉第一个 /
                                path = path.replace(/\(.+\)/g, ''),   //去掉可选参数
                                path = path.replace(/\/:.+/g, ''),   //去掉params
                                path = path.replace(/\/\?.+/g, ''),   //去掉query
                                pageName = path.replace(/\//g, '-'),
                                fileName = path.split('/').pop(),
                                cacheStore = page.cacheStore || {},
                                lastPagePath = page.lastPagePath;


                            //给pageName添加一个"page-"前缀，以示和module的区分
                            pageName = 'page-' + pageName;
                            //添加beforeswitch事件，如果回调返回false，阻断以下执行
                            if (pageEvent.trigger('beforeswitch', pageName, _.extend({}, row)) === false) {
                                return;
                            }
                            //先清理前一个路由对应的页面是否缓存，非缓存页面清理掉
                            if (lastPagePath) {
                                lastPagePath = lastPagePath.replace(/\//g, '-');
                                lastPagePath = 'page-' + lastPagePath;
                                if (cacheStore[lastPagePath] && !cacheStore[lastPagePath].isCache) {
                                    removePage(lastPagePath);
                                }
                            }
                            //更新上一个页面路径
                            page.lastPagePath = path;
                            //更新最近一个路由信息
                            page.lastRoute = route;
                            page.lastRouteData = row;
                            //打开页面切换遮罩
                            pageMaskEl.show();
                            //如果是已缓存的page,直接显示出来
                            if (cacheStore[pageName] && cacheStore[pageName].isCache) {
                                activePage(pageName);
                                return;
                            }
                            if (!deps) {    //对于不缓存的page，第二次加载时依赖已经准备好了
                                //替换true标志,补全路径前缀
                                if (app.PUBLISH_MODEL === "development") {
                                    css = _.map(css, function (itemPath) {
                                        if (itemPath === true) {
                                            return 'css!' + app.PAGE_PATH + path + '/' + fileName + '.css';
                                        } else {
                                            return 'css!' + app.ORIGIN_PATH + itemPath;
                                        }
                                    });
                                } else {
                                    css = [];   //已提前加载
                                }
                                js = _.map(js, function (itemPath) {
                                    var realPath;
                                    if (itemPath === true) {
                                        realPath = app.PAGE_PATH + path + '/' + fileName + '.js';
                                    } else {
                                        realPath = app.ORIGIN_PATH + itemPath;
                                    }
                                    if (app.PUBLISH_MODEL === "development") {
                                        return realPath;
                                    } else if (app.PUBLISH_MODEL === "product") {
                                        return realPath.slice(app.PORTAL_PATH.length).replace(/[\.|\\|/|_|-]/g, '');
                                    }
                                });
                                //确定html的加载路径，支持页面模板复用
                                if (tmpl) {
                                    if (tmpl === true) {
                                        htmlPath = 'text!' + app.PAGE_PATH + path + '/' + fileName + '.html';
                                    } else {
                                        htmlPath = 'text!' + app.ORIGIN_PATH + tmpl;
                                    }
                                } else {
                                    htmlPath = 'text!' + app.PAGE_PATH + path + '/' + fileName + '.html';
                                }
                                deps = css.concat(htmlPath, js);
                            }
                            setTimeout(function () {   //保证清理上一个页面的操作(removePage)正常结束
                                require(deps, function () {
                                    var args = aslice.call(arguments, 0),
                                        pageText = cacheTmplContent || args[0],
                                        pageJs = args[args.length - 1], //默认最后一个脚本是页面逻辑脚本
                                        routeDataExt = {};  //扩展的当前页面导航信息
                                    _.extend(routeDataExt, row, router);
                                    //var pageEl = $(pageText); //生成jquery对象的快捷引用
                                    var pageEl = $('<div></div>'); //生成jquery对象的快捷引用
                                    pageEl.addClass('page ' + pageName).attr('ms-controller', pageName).appendTo(pageWEl); //追加到dom tree
                                    //渲染page html
                                    pageEl.html(pageText);
                                    //设置页面模板内容存储
                                    if (!cacheTmplContent) {
                                        cacheTmplContent = pageText;
                                        deps.splice(css.length, 1); //依赖中去掉页面模板引用，从cacheTmplContent中取模板内容，大量页面缓存可能会造成内存压力，待测
                                    }
                                    //执行页面逻辑
                                    if (pageJs.$init) {
                                        pageJs.$init(pageEl, pageName, routeDataExt);
                                    } else {
                                        alert("请在页面（" + htmlPath + "）逻辑中设置$init初始化函数");
                                    }
                                    //页面引用存储
                                    cacheStore[pageName] = {
                                        "pageEl": pageEl,
                                        "isCache": isCache,
                                        "routeData": routeDataExt,
                                        "$remove": pageJs.$remove || function () {
                                        }
                                    };
                                    page.cacheStore = cacheStore; //重设会原引用
                                    //激活当前页面，不触发switched事件
                                    activePage(pageName, true);
                                    //触发页面inited事件
                                    pageEvent.trigger('inited', pageEl, pageName, routeDataExt);
                                });
                            }, 300);

                        });
                    });
                    mmRouter.history.start({
                        html5Mode: false //强制使用hash hack，兼容大部分浏览器
                    });
                    callback && callback.call(this, responseData);
                }
            });
        },
        /**
         * 判断IE6、7、8、9版本
         */
        "isIE": function (ver) {
            var tempDom = doc.createElement('b');
            tempDom.innerHTML = '<!--[if IE ' + ver + ']><i></i><![endif]-->';
            return tempDom.getElementsByTagName('i').length === 1;
        },
        /**
         * 注册导航供点亮
         */
        "regPageNav": (function () {
            var store = [],
                lightNav = function (currentHash) {
                    //currentHash = currentHash || avalon.History.getHash(window.location);
                    currentHash = currentHash || page.lastRoute;
                    if (currentHash.slice(0, 2) == "#!") {
                        currentHash = currentHash.slice(2);
                    }
                    if (currentHash.slice(0, 1) == "#") {
                        currentHash = currentHash.slice(1);
                    }
                    if (currentHash.length > 0) {
                        store = _.filter(store, function (storeItem) {
                            var elEl = storeItem.element,
                                href = elEl.attr('href'),
                                phref = elEl.attr('phref');
                            href = href.split('#')[1];  //同时处理ie6/7下自动追加域名的问题
                            if (!$.contains(doc, elEl[0])) {
                                return false;
                            }
                            //if (elEl.is(':visible') && href === currentHash) {
                            if (href === currentHash || href === currentHash.replace(/\(.+\)/g, '')) {  //考虑到可选参数的匹配，可过滤掉可选参数再进行全等比较
                                elEl.addClass('page-nav-state-current');
                                if (phref) {
                                    lightNav(phref);
                                }
                            }
                            return true;
                        });
                    }
                };
            //监听页面切换事件和页面init事件
            pageEvent.on('inited switched', function () {
                $('.page-nav-state-current').removeClass('page-nav-state-current');
                lightNav();
            });
            return function (linkSelector) {
                var linkEl = $(linkSelector);
                linkEl.each(function () {
                    var everyEl = $(this);
                    if (!_.some(store, function (storeItem) {
                        if (storeItem.element[0] === everyEl[0]) {
                            return true;
                        }
                    })) {
                        store.push({
                            "element": everyEl
                        });
                    }
                });
            };
        }()),
        /**
         * 自定义alert
         */
        "alert": function (opts) {
            var model,
                vmodel,
                iconChar;
            opts = _.extend({
                "title": "提示",
                "content": "",
                "iconType": "error", //图标提示类型
                "modal": true,
                "onSubmit": avalon.noop,
                "submitText": "确&nbsp;定"
            }, opts || {});
            switch (opts.iconType) {
                case "info":
                    iconChar = "icon-info";
                    break;
                case "ask":
                    iconChar = "icon-info";
                    break;
                case "success":
                    iconChar = "icon-success";
                    break;
                case "error":
                    iconChar = "icon-error";
                    break;
                default:
                    iconChar = "";
                    break;
            }
            model = {
                "title": opts.title,
                "modal": opts.modal,
                "autoOpen": true,
                "showClose": false,
                //"width": 500,
                "getTemplate": function (tmpl) {
                    var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                        footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                            '<div class="ui-dialog-btns">' +
                            '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">' + opts.submitText + '</button>' +
                            '</div>' +
                            '</div>';
                    return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                },
                "onOpen": function () {
                    this.setContent('<div class="fn-clear"><span class="tip-content ' + iconChar + '">' + opts.content + '</span></div>', true);
                },
                "onClose": function () {
                    $(this.widgetElement).remove();
                },
                "submit": function (evt) {
                    opts.onSubmit.call(this, evt);
                    vmodel.close();
                }
            };
            vmodel = avalon.dialog({
                "model": model
            });
            //添加className
            $(vmodel.widgetElement).addClass('global-alert global-alert-confirm');
        },
        /**
         * 自定义confirm
         */
        "confirm": function (opts) {
            var model,
                vmodel,
                iconChar;
            opts = _.extend({
                "title": "确认",
                "content": "",
                "iconType": "ask", //图标提示类型
                "modal": true,
                "onSubmit": avalon.noop,
                "onCancel": avalon.noop,
                "submitText": "确&nbsp;定",
                "cancelText": "取&nbsp;消"
            }, opts || {});
            switch (opts.iconType) {
                case "info":
                    iconChar = "icon-info";
                    break;
                case "ask":
                    iconChar = "icon-info";
                    break;
                case "success":
                    iconChar = "icon-success";
                    break;
                case "error":
                    iconChar = "icon-error";
                    break;
                default:
                    iconChar = "";
                    break;
            }
            model = {
                "title": opts.title,
                "modal": opts.modal,
                "autoOpen": true,
                "showClose": false,
                //"width": 500,
                "getTemplate": function (tmpl) {
                    var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                        footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                            '<div class="ui-dialog-btns">' +
                            '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">' + opts.submitText + '</button>' +
                            '<span class="separation"></span>' +
                            '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="cancel">' + opts.cancelText + '</button>' +
                            '</div>' +
                            '</div>';
                    return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                },
                "onOpen": function () {
                    this.setContent('<div class="fn-clear"><span class="tip-content ' + iconChar + '">' + opts.content + '</span></div>', true);
                },
                "onClose": function () {
                    $(this.widgetElement).remove();
                },
                "submit": function (evt) {
                    if (opts.onSubmit.call(this, evt) !== false) {
                        vmodel.close();
                    }
                },
                "cancel": function (evt) {
                    if (opts.onCancel.call(this, evt) !== false) {
                        vmodel.close();
                    }
                }
            };
            vmodel = avalon.dialog({
                "model": model
            });
            //添加className
            $(vmodel.widgetElement).addClass('global-confirm global-alert-confirm');
        },
        "onLazyResize": function (handler, ms, immTrigger) {
            var tId;
            if (typeof (ms) === "boolean") {
                immTrigger = ms;
                ms = 0;
            }
            $(win).resize(function (evt) {
                clearTimeout(tId);
                tId = setTimeout(function () {
                    handler();
                }, ms || 0);
            });
            if (immTrigger) {
                handler();
            }
        },
        /**
         * 参考avalon-ext中adjustWidgetCssUrl
         * 解决module style合并后引用图片路径的问题
         */
        "adjustModuleCssUrl": function (cssText, relPath) {
            var regUrl = /url\s*\(\s*(['"]?)([^"'\)]*)\1\s*\)/gi; //From https://github.com/nanjingboy/grunt-css-url-replace/blob/master/lib/replace.js
            var moduleBasePath,
                publishModel = app.PUBLISH_MODEL;
            moduleBasePath = app.MODULE_PATH;
            if (publishModel === "product") {   //如果是生产模式原样返回
                return cssText;
            }
            return cssText.replace(regUrl, function (match) {
                var url,
                    urlPath;
                match = match.replace(/\s/g, '');
                url = match.slice(4, -1).replace(/"|'/g, '');
                if (/^\/|https:|http:|data:/i.test(url) === false) {
                    url = moduleBasePath + relPath + url;
                }
                return 'url("' + url + '")';
            });
        },
        /**
         * 注册全局mousedown事件，只要触发点不在指定的范围内
         */
        "regGlobalMdExcept": (function () {
            var store = [];
            //点击页面其他位置
            $('body').on('mousedown', function (evt) {
                store = _.reject(store, function (itemData) {   //清除已不在dom tree中的element
                    return _.some(itemData.element, function (itemEl) {
                        return !$.contains(document.body, itemEl[0]);
                    });
                });
                _.each(store, function (itemData) {
                    var element = itemData.element,
                        handler = itemData.handler;
                    if (_.every(element, function (itemEl) {
                        if (!itemEl.is(evt.target) && itemEl.find(evt.target).length === 0) {
                            return true;
                        }
                    })) {
                        if (handler) {
                            handler.call(this, evt);
                        } else {
                            element.hide();    //默认为隐藏行为
                        }
                    }
                });
            });
            return function (opts) {
                store.push({
                    "element": _.map([].concat(opts.element), function (item) {
                        return $(item);
                    }),
                    "handler": opts.handler
                });
            };
        }()),
        "hasPermission": function (permissionName) {
            var permissionData = app.getAppData('permission');
            return _.some(permissionData, function (permissionName2) {
                return permissionName2 === permissionName;
            });
        },
        /**
         * 手动跳转页面，可记录hash导航
         */
        "jumpPage": function (link) {
            var linkEl = $('<a href="' + link + '"></a>');
            linkEl.appendTo('body');
            linkEl[0].click();
            linkEl.remove();
        },
        /**
         * 将obj的key转为驼峰式
         * @param keys 可选，用于筛选需要提取的key字段，为空默认提取全部
         */
        "keyToCamelize": function (obj, keys) {
            var newObj = null;
            if (obj) {
                newObj = {};
                if (!keys) {
                    _.each(_.keys(obj), function (key) {
                        newObj[_.str.camelize(key)] = obj[key];
                    });
                } else {
                    _.each(keys, function (key) {
                        newObj[key] = newObj[_.str.dasherize(key)];
                    });
                }
            }
            return newObj;
        },
        "log": function (data) {
            var loggerEl = $('#logger');
            if (!loggerEl.length) {
                loggerEl = $('<div id="logger"></div>');
                loggerEl.css({
                    "position": "fixed",
                    "top": 0,
                    "left": 0,
                    "z-index": 10000,
                    "background": "white"
                }).appendTo('body');
            }
            loggerEl.html(JSON.stringify(data));
        },
        /**
         * 显示全局遮罩
         */
        "showGlobalMask": function () {
            var globalMaskEl = $('.global-mask');
            if (!globalMaskEl.length) {
                globalMaskEl = $('<div class="global-mask"></div>');
                globalMaskEl.appendTo('body');
            }
            globalMaskEl.show();
        },
        /**
         * 隐藏全局遮罩
         */
        "hideGlobalMask": function () {
            var globalMaskEl = $('.global-mask');
            globalMaskEl.hide();
        },
        /**
         * 设置一次输入框焦点聚焦
         * @param {Jquery selector} input:text/textarea 的jquery 选择符
         */
        "setInputTextFocusOnce": function (inputTextSelector) {
            var inputEl = $(inputTextSelector);
            if (!inputEl.hasClass('input-focus')) {
                inputEl.get(0).focus();
            }
        },
        /**
         * 获得输入框光标位置
         * @param {Jquery selector} input:text/textarea 的jquery 选择符
         * @returns {{text: string, start: number, end: number}}
         */
        "getCursorPosition": function (inputTextSelector) {
            var rangeData = {
                    text: "",
                    start: 0,
                    end: 0
                },
                inputDom = $(inputTextSelector)[0];
            util.setInputTextFocusOnce(inputTextSelector);    //重复设置textarea focus在ie下会导致window.scroll定位错乱，所有保证最多设置一次
            if (inputDom.setSelectionRange) { // W3C
                rangeData.start = inputDom.selectionStart;
                rangeData.end = inputDom.selectionEnd;
                rangeData.text = (rangeData.start != rangeData.end) ? inputDom.value.substring(rangeData.start, rangeData.end) : "";
            } else if (document.selection) { // IE
                var i,
                    oS = document.selection.createRange(),
                    oR = document.body.createTextRange();
                oR.moveToElementText(inputDom);
                rangeData.text = oS.text;
                rangeData.bookmark = oS.getBookmark();
                // object.moveStart(sUnit [, iCount])
                // Return Value: Integer that returns the number of units moved.
                for (i = 0; oR.compareEndPoints('StartToStart', oS) < 0 && oS.moveStart("character", -1) !== 0; i++) {
                    // Why? You can alert(inputDom.value.length)
                    if (inputDom.value.charAt(i) == '\n') {
                        i++;
                    }
                }
                rangeData.start = i;
                rangeData.end = rangeData.text.length + rangeData.start;
            }
            return rangeData;
        },
        /**
         * 设置输入框光标位置
         * @param {Jquery selector} input:text/textarea 的jquery 选择符
         * @param rangeData
         */
        "setCursorPosition": function (inputTextSelector, rangeData) {
            var start,
                end,
                inputDom = $(inputTextSelector)[0];
            if (!rangeData) {
                alert("必须定义光标位置");
                return false;
            }
            start = rangeData.start;
            end = rangeData.end;

            if (inputDom.setSelectionRange) { // W3C
                util.setInputTextFocusOnce(inputTextSelector);    //重复设置textarea focus在ie下会导致window.scroll定位错乱，所有保证最多设置一次
                inputDom.setSelectionRange(start, end);
            } else if (inputDom.createTextRange) { // IE
                var oR = inputDom.createTextRange();

                //Fix IE from counting the newline characters as two seperate characters
                var breakPos,
                    i;
                //设置断点位置
                breakPos = start;
                for (i = 0; i < breakPos; i++) {
                    if (inputDom.value.charAt(i).search(/[\r\n]/) != -1) {
                        start = start - 0.5;
                    }
                }
                //设置断点位置
                breakPos = end;
                for (i = 0; i < breakPos; i++) {
                    if (inputDom.value.charAt(i).search(/[\r\n]/) != -1) {
                        end = end - 0.5;
                    }
                }

                oR.moveEnd('textedit', -1);
                oR.moveStart('character', start);
                oR.moveEnd('character', end - start);
                oR.select();
            }
        },
        /**
         * 将内容追加到光标的位置，并保持光标位置不动
         * @param {Jquery selector} input:text/textarea 的jquery 选择符
         * @param text
         */
        "appendTextAtCursor": function (inputTextSelector, text) {
            var inputEl = $(inputTextSelector),
                inputDom = inputEl[0],
                val = $(inputTextSelector).val();
            var rangeData = util.getCursorPosition(inputDom);
            inputEl.val(val.slice(0, rangeData.start) + text + val.slice(rangeData.end));
            //重设光标位置
            util.setCursorPosition(inputDom, _.extend(rangeData, {
                "start": rangeData.start + text.length,
                "end": rangeData.start + text.length
            }));
        },
        /**
         * 强制刷新当前地址
         */
        "forceRefresh": function () {
            var hashHref = avalon.History.getHash(window.location).slice(2);
            if (hashHref) {
                avalon.router.navigate(hashHref);
            } else {
                location.reload();
            }
        },


        /**
         * 表单验证
         */
        "testType": {
            "empty": function (el, tip) {
                var value = $(el).val().replace(' ', '');
                if (value.length > 0) {
                    util.selectTip(el, tip, 'hide');
                    return true;
                } else {
                    util.selectTip(el, tip, 'show');
                    return false;
                }
            },
            "tel": function (el, tip) {
                var value = $(el).val();
                var reg = /^(\({0,1}\d{3,4})\){0,1}(-){0,1}(\d{7,8})$/;
                if (reg.test(value)) {
                    util.selectTip(el, tip, 'hide');
                    return true;
                } else {
                    util.selectTip(el, tip, 'show');
                    return false;
                }
            },
            "mail": function (el, tip) {
                var value = $(el).val();
                var reg = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
                if (reg.test(value)) {
                    util.selectTip(el, tip, 'hide');
                    return true;
                } else {
                    util.selectTip(el, tip, 'show');
                    return false;
                }
            },
            "mobile": function (el, tip) {
                var value = $(el).val().replace(' ', '');
                var reg = /^1[0-9]{10}$/;
                if (reg.test(value)) {
                    util.selectTip(el, tip, 'hide');
                    return true;
                } else {
                    util.selectTip(el, tip, 'show');
                    return false;
                }
            },
            "number": function (el, tip) {
                var value = $(el).val().replace(' ', '');
                var reg = /^[0-9]+$/;
                if (reg.test(value)) {
                    util.selectTip(el, tip, 'hide');
                    return true;
                } else {
                    util.selectTip(el, tip, 'show');
                    return false;
                }
            }
        },
        "getTips": function (eles, index) {//获取提示语
            var len = eles.length;
            if (index + 1 > len) {
                alert("缺少错误提示！");
                return false;
            } else {
                return eles[index];
            }
        },
        "selectTip": function (el, tip, type) {//错误提示隐藏和显示控制
            var nextErr = $(el).parent().find('div.ff-value-tip')[0];
            if (nextErr) {
                if (type == 'show') {
                    $(nextErr).addClass('valid-error').text(tip).show();
                    $(el).addClass('valid-error-field');
                } else if (type == 'hide') {
                    $(nextErr).hide();
                    $(el).removeClass('valid-error-field');
                    $(nextErr).removeClass('valid-error');
                }
            } else {
                nextErr = $(el).parent().parent().find('div.ff-value-tip')[0];
                if (nextErr) {
                    if (type == 'show') {
                        $(nextErr).addClass('valid-error').text(tip).show();
                        $(el).addClass('valid-error-field');
                    } else if (type == 'hide') {
                        $(nextErr).hide();
                        $(el).removeClass('valid-error-field');
                        $(nextErr).removeClass('valid-error');
                    }
                } else {
                    nextErr = $(el).parent().parent().parent().find('div.ff-value-tip')[0];
                    if (nextErr) {
                        if (type == 'show') {
                            $(nextErr).addClass('valid-error').text(tip).show();
                            $(el).addClass('valid-error-field');
                        } else if (type == 'hide') {
                            $(nextErr).hide();
                            $(el).removeClass('valid-error-field');
                            $(nextErr).removeClass('valid-error');
                        }
                    } else {
                        $(el).parent().append("<div class='ff-value-tip valid-error'>" + tip + "</div>");
                        $(el).addClass('valid-error-field');
                    }
                }
            }
        },
        "typeFn": function (item, rules, errTips) {//遍历元素所有验证规则
            var flag = true;
            for (var i = 0; i < rules.length; i++) {
                switch (rules[i]) {
                    case "empty"://空验证
                        flag = util.testType.empty(item, util.getTips(errTips, i));
                        if (!flag) {
                            return false;
                        }
                        break;
                    case "mobile"://手机号
                        flag = util.testType.mobile(item, util.getTips(errTips, i));
                        if (!flag) {
                            return false;
                        }
                        break;
                    case "mail"://邮箱
                        flag = util.testType.mail(item, util.getTips(errTips, i));
                        if (!flag) {
                            return false;
                        }
                        break;
                    case "tel"://电话
                        flag = util.testType.tel(item, util.getTips(errTips, i));
                        if (!flag) {
                            return false;
                        }
                        break;
                    case "number"://数字
                        flag = util.testType.number(item, util.getTips(errTips, i));
                        if (!flag) {
                            return false;
                        }
                        break;
                }
            }
            return true;
        },
        "testStart": function (item) {//开始验证
            var rules = $(item).attr('data-rules').split('&');//验证类型
            var errTips = $(item).attr('data-tips').split('&');//提示语
            var isempty = $(item).attr('isempty');//是否可以为空
            if (isempty) {
                var value = $(item).val().replace(' ', '');
                if (value > 0) {
                    return util.typeFn(item, rules, errTips);
                } else {
                    util.selectTip(item, '', 'hide');
                    return true;
                }
            } else {
                return util.typeFn(item, rules, errTips);
            }
        },
        "testing": function (eles, jqObj) {//入口方法，业务层调用（eles为dom对象）
            var testEles = jqObj || $("[isrule=true]", eles);
            var flag, isThrough = true;

            testEles.each(function (index, item) {//循环遍历
                flag = util.testStart(item);//提交时验证
                $(item).on('blur', function () {
                    util.testStart(this)
                });//注册onblur事件
                if (!flag) {
                    isThrough = false;
                }
            });
            return isThrough;
        },
        "testCancel": function (ele) {//取消验证
            $('input', ele).removeClass('valid-error-field');
            $('textarea', ele).removeClass('valid-error-field');
            $('div.ff-value-tip', ele).hide();
        }





        /**
         * 表单验证end
         */
    });
    return util;
});
