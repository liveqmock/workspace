/**
 * 项目级引导逻辑，定义命名空间、公共配置、一些预处理等等。
 * 依赖avalon.js文件
 * Created by q13 on 14-5-9.
 */

/* global BASE_PATH, VERSION */

(function (avalon, globalNs) {
    var doc = document,
        app = {};
    //一些预定义常量
    var version = VERSION,  //前端版本号
        basePath = BASE_PATH || location.protocol + '//' + location.host + location.pathname, //项目基础路径
        portalPath = basePath + 'portal/',
        originPath = basePath + 'portal/origin/',
        assetPath = originPath + 'asset/',
        modulePath = originPath + 'module/',
        pagePath = originPath + 'page/',
        widgetPath = originPath + 'widget/';
    _.extend(app, {
        "VERSION": version,
        "PUBLISH_MODEL": PUBLISH_MODEL || "development",
        "BASE_PATH": basePath,
        "PORTAL_PATH": portalPath,
        "ORIGIN_PATH": originPath,
        "ASSET_PATH": assetPath,
        "MODULE_PATH": modulePath,
        "PAGE_PATH": pagePath,
        "WIDGET_PATH": widgetPath,
        /**
         * 获取app数据
         */
        "getAppData": function (key) {
            var appData = app.appData || {};
            return appData[key];
        },
        /**
         * 设置app数据，key-value方式
         */
        "setAppData": function (key, value) {
            var appData = app.appData || {};
            appData[key] = value;
            app.appData = appData;
        }
    });
    //页面信息命名空间
    var page = {};
    //形式如下：
    app.page = page;
    //module命名空间设定
    app.module = {};
    //AMD配置
    require.config({
        "paths": {
            "mmHistory": assetPath + "third/avalon/mmHistory.js",
            "mmRouter": assetPath + "third/avalon/mmRouter.js",
            "events": assetPath + "events.js",
            "common": assetPath + "common.js",
            "util": assetPath + "util.js",
            "index": assetPath + "index.js",
            "json": assetPath + "third/json2.js",
            "dialog": widgetPath + "dialog/dialog",
            //"zrender": assetPath + "third/ecomfe/zrender/zrender.js",
            "raphael": assetPath + "third/raphael/raphael.js",
            "uploadify": assetPath + "third/uploadify/jquery.uploadify.js",
            "moment": assetPath + "third/moment.min.js",
            //"jwplayer": "http://jwpsrv.com/library/VcB03veHEeOlxiIACyaB8g.js",
            "jwplayer": assetPath + "third/jwplayer/jwplayer.js",
            "sortable": assetPath + "third/jquery-sortable.js",
            "graphael": assetPath + "third/graphael/g.raphael.js",
            "highcharts": assetPath + "third/highcharts/js/highcharts-all.js"
        },
        "shim": {
            "json": {
                exports: "JSON"
            },
            "uploadify": {
                exports: function () {
                    return {
                        "uploadify": $.fn.uploadify,
                        "SWF_PATH": assetPath + "third/uploadify/uploadify.swf"
                    };
                }
            },
            "jwplayer": {
                exports: "jwplayer"
            },
            "sortable": {
                exports: "$.fn.sortable"
            },
            "graphael": {
                deps: ["raphael"],
                exports: "Raphael"
            },
            "highcharts": {
                exports: "Highcharts"
            },
            "moment": {
                exports: "moment"
            }
        },
        "debug": app.PUBLISH_MODEL === "development"   //是否处于debug模式
    });
    //提前声明准备好的amd
    _.extend(avalon.modules, {
        "eve": {    //raphael 依赖
            "state": 2
        }
    });
    //扩展过滤器
    _.extend(avalon.filters, {
        "encodeURIComponent": function (str) {
            return encodeURIComponent(str);
        }
    });
    //重载ms-value绑定，用于input的点击清空
    var valueHandler = avalon.bindingHandlers.value;
    avalon.bindingHandlers.value = function (data, vmodels) {
        var text = data.value.trim(),
            element = data.element,
            msValueStore = app.msValueStore || [];
        //清空原存储和已被删除element的存储
        msValueStore = _.reject(msValueStore, function (itemData) {
            return itemData.element === element || (!$.contains(doc.body, itemData.element));
        });
        //设置存储
        msValueStore.push({
            "element": element,
            "text": text,
            "vmodels": vmodels
        });
        app.msValueStore = msValueStore;
        return valueHandler.apply(this, arguments);
    };

    //给异步资源请求添加版本号
    version = version.split('.');

    _.each(["js", "css", "text"], function (pluginName) {

        var plugins = require.config.plugins,
            corePlugin = plugins[pluginName];
        plugins[pluginName] = function (url) {
            var args = Array.prototype.slice.call(arguments, 0),
                urlArr = url.split('?');
            if (/\/asset\//.test(url)) {
                url = urlArr[0] + '?v=' + version[0] + '-' + version[1];
            } else if (/\/module\//.test(url)) {
                url = urlArr[0] + '?v=' + version[0] + '-' + version[2];
            } else if (/\/page\//.test(url)) {
                url = urlArr[0] + '?v=' + version[0] + '-' + version[3];
            } else if (/\/widget\//.test(url)) {
                url = urlArr[0] + '?v=' + version[0] + '-' + version[4];
            }
            if  (urlArr[1]) {
                url += '&' + urlArr[1];
            }
            //console.info(args);
            return corePlugin.apply(this, [url].concat(args.slice(1)));
        };
        //扩展名
        plugins[pluginName].ext = corePlugin.ext;
    });
    globalNs.app = app; //抛出对外引用
}(window.avalon, window));
