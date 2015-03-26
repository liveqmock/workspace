/**
 * 扩展avalon
 */
(function () {
    var avalon = this.avalon,
        subscribers;
    if (avalon) {
        /**
         * 提供widget样式合并后的修正图片路径的接口
         * 依赖 WIDGET_BASE_PATH 全局变量，默认为/
         */
        avalon.adjustWidgetCssUrl = function (cssText, relPath) {
            var regUrl = /url\s*\(\s*(['"]?)([^"'\)]*)\1\s*\)/gi; //From https://github.com/nanjingboy/grunt-css-url-replace/blob/master/lib/replace.js
            var widgetBasePath,
                publishModel;
            try {
                widgetBasePath = WIDGET_BASE_PATH;
                publishModel = PUBLISH_MODEL;
            } catch(e){}
            if (widgetBasePath && publishModel === "development") { //只有开发模式才需要补齐前缀
                 return cssText.replace(regUrl, function (match) {
                    var url,
                        urlPath;
                    match = match.replace(/\s/g, '');
                    url = match.slice(4, -1).replace(/"|'/g, '');
                    if (/^\/|https:|http:|data:/i.test(url) === false) {
                        url = widgetBasePath + relPath + url;
                    }
                    return 'url("' + url + '")';
                });
            } else {
                return cssText; //原样返回
            }
        };
        avalon.getVm = function (vmId) {
            return avalon.vmodels[vmId];
        };
        avalon.removeVm = function (vmId) {
            avalon.vmodels[vmId] = null;
            delete avalon.vmodels[vmId];
        };
    }
}());
