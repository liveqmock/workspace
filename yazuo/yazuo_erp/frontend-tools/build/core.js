var fs = require('fs'),
    path = require('path'),
    _ = require("lodash"),
    minimatch = require("minimatch"),
    esprima = require('esprima'),
    escodegen = require('escodegen'),
    esmangle = require('esmangle'),
    css = require('css'),
    minify = require('html-minifier').minify,
    CleanCSS = require('clean-css'),
    mkdirp = require('mkdirp'),
    config = require('../config.json');
var recordCache = {}, //维持已处理过的记录表，避免重复处理
    modNameReg = /[\.|\\|/|_|-]/g,
    cssUrlReg = /url\s*\(\s*(['"]?)([^"'\)]*)\1\s*\)/gi;

function builder(opts) {
    var relativePath,
        tempPath,
        fileContent,
        ast,
        sourceProcessed,
        fileExt,
        selfConfig,
        bundleName,
        modName,
        isAmd = false;
    fileExt = path.extname(opts.filePath);  //获取扩展名
    //修正filePath扩展名
    if (fileExt === ".text") {
        opts.filePath = opts.filePath.slice(0, -5);  //去掉.text扩展名
    }
    relativePath = path.relative(opts.cwdPath, opts.filePath);
    //console.log(relativePath);
    bundleName = relativePath.split(path.sep)[1];
    /*if (relativePath.split(path.sep)[relativePath.split(path.sep).length - 1] === "form.css") {
        console.log(relativePath);
        throw 1;
        return;
    }*/
    if (!bundleName) {
        console.log(relativePath + '不在打包范围内');
        return;
    }
    selfConfig = config[opts.mode];
    //console.log(config);
    //首先判断是否在忽略列表里
    //TODO: 可以做个小优化，维持一个内部对象，不用每次都用glob匹配
    if (_.some(selfConfig.ignore, function (globPath) {
        return minimatch(relativePath, globPath);
    })) {
        return; //直接返回
    }
    //再判断是否已解析过
    if (recordCache[relativePath]) {
        return; //直接返回
    }
    console.log(relativePath + ' build start.');
    //同步执行
    fileContent = fs.readFileSync(opts.filePath, {
        "encoding": "utf8"
    });

    //判断是否是js文件
    if (fileExt === ".js") {
        //解析语法树
        //fileContent = 'a.b({"a": 1})';
        ast = esprima.parse(fileContent, {
            raw: true,
            loc: true
        });
        //console.log(JSON.stringify(ast, null, 4));
        //语法树遍历
        //console.log(JSON.stringify(ast, null, 4));
        walker(ast, function (node) {
            var jsModName,
                args,
                deps;
            if (node.type == "ExpressionStatement" &&
                node.expression.callee &&
                node.expression.callee.type == "MemberExpression" &&
                node.expression.callee.object.type == "Identifier" &&
                node.expression.callee.object.name == "require" &&
                node.expression.callee.property.name == "config") {
                _.some(node.expression.arguments, function (itemData) {
                    if (itemData.type == "ObjectExpression") {
                        return _.some(itemData.properties, function (itemData2) {
                            if (itemData2.type == "Property" && itemData2.key.value == "paths") {
                                //console.log(JSON.stringify(itemData2.value, null, 4));
                                _.each(itemData2.value.properties, function (itemData3) {
                                    var pathValue,
                                        realPath;
                                    pathValue = itemData3.value.value;
                                    if (!(/^http/.test(pathValue)) && !(/^https/.test(pathValue))) {
                                        realPath = path.normalize(path.dirname(opts.filePath) + '/' + pathValue);
                                        pathValue = selfConfig.servicePortalPath + path.relative(opts.cwdPath, realPath).split('\\').join('/');
                                        itemData3.value.value = pathValue;
                                        return true;
                                    }
                                });
                                return true;
                            }
                        });
                    }
                });
                //console.log(JSON.stringify(node, null, 4));
            }
            //amd模块打包处理
            if (node.type == "CallExpression" && node.callee.type == "Identifier" && node.callee.name == "define") { //widget.define被认为是MemberExpression
                args = node.arguments;
                //console.log(node.arguments[0]);

                if (args.length === 2) { //未命名的module
                    deps = args[0].elements;
                    //反查alias映射，优先使用别名
                    if (!_.some(selfConfig.alias, function (v, k) {
                        //console.log(node.arguments[0]);
                        if (v === relativePath.replace(/\\/g, '/')) {
                            jsModName = k;
                            return true;
                        }
                    })) {
                        jsModName = relativePath.replace(modNameReg, ''); //根据路径设置module name
                    }
                    //插入module name
                    args.unshift({
                        type: 'Literal',
                        value: jsModName
                    });
                    modName = jsModName;
                    //塞入module name
                    //console.log(jsModName);
                } else if (args.length === 3) { //已命名的module
                    deps = args[1].elements;
                    modName = args[0].value;
                }
                //先处理依赖
                if (deps) {
                    _.each(deps, function (dep) {
                        var depPath = dep.value,
                            realPath,
                            tempPath,
                            tempAlias,
                            depRelativePath,
                            alias = selfConfig.alias[depPath];
                        if (!(/^\./.test(depPath)) && !(/^http/.test(depPath)) && !(/^https/.test(depPath)) && !(/^text!/.test(depPath)) && !(/^css!/.test(depPath)) && alias) { //认为是别名
                            realPath = opts.cwdPath + '/' + alias; //如果能找到别名路径设置，走解析，如果找不到什么都不做
                        } else {
                            //针对js文件相对引用做处理
                            if (/^\./.test(depPath)) {
                                if (!path.extname(depPath)) {
                                    depPath = depPath + '.js';
                                }
                                realPath = path.normalize(path.dirname(opts.filePath) + '/' + depPath);
                                depRelativePath = path.relative(opts.cwdPath, realPath);
                                //看是否在忽略列表里
                                if (!_.some(selfConfig.ignore, function (globPath) {
                                    return minimatch(depRelativePath, globPath);
                                })) {
                                    //反查alias映射，优先使用别名
                                    if (_.some(selfConfig.alias, function (v, k) {
                                        //console.log(node.arguments[0]);
                                        if (v === depRelativePath.replace(/\\/g, '/')) {
                                            tempAlias = k;
                                            return true;
                                        }
                                    })) {
                                        //替换成正确模块名
                                        dep.value = tempAlias;
                                    } else {
                                        //替换成正确模块名
                                        dep.value = path.relative(opts.cwdPath, realPath).replace(modNameReg, '');
                                    }
                                } else {
                                    //反查alias映射，优先使用别名
                                    if (_.some(selfConfig.alias, function (v, k) {
                                        //console.log(node.arguments[0]);
                                        if (v === depRelativePath.replace(/\\/g, '/')) {
                                            tempAlias = k;
                                            return true;
                                        }
                                    })) {
                                        //替换成正确模块名
                                        dep.value = tempAlias;
                                    } else {
                                        //用http请求直接替换
                                        dep.value = selfConfig.servicePortalPath + depRelativePath.split('\\').join('/');
                                    }
                                    realPath = '';
                                }
                                //console.log(depPath);
                                //console.log(path.relative(opts.cwdPath, realPath));

                            } else if (/^css!/.test(depPath)) {    //样式处理
                                tempPath = depPath.slice(4);
                                if (!(/^http/.test(tempPath)) && !(/^https/.test(tempPath))) {
                                    realPath = path.normalize(path.dirname(opts.filePath) + '/' + tempPath);
                                    //标记这个依赖循环后干掉
                                    dep.isIgnore = true;
                                }
                            } else if (/^text!/.test(depPath)) { //文本处理
                                tempPath = depPath.slice(5);
                                if (!(/^http/.test(tempPath)) && !(/^https/.test(tempPath))) {
                                    realPath = path.normalize(path.dirname(opts.filePath) + '/' + tempPath);
                                    depRelativePath = path.relative(opts.cwdPath, realPath);
                                    //看是否在忽略列表里
                                    if (!_.some(selfConfig.ignore, function (globPath) {
                                        return minimatch(depRelativePath, globPath);
                                    })) {
                                        //改名
                                        dep.value = path.relative(opts.cwdPath, realPath).replace(modNameReg, '');
                                        //添加.text扩展名
                                        realPath = realPath + '.text';
                                    } else {
                                        dep.value = 'text!' + selfConfig.servicePortalPath + depRelativePath.split('\\').join('/');
                                        realPath = '';  //不需要处理
                                    }

                                    /*if (dep.value === "originwidgetformformcss") {
                                        console.log(realPath);
                                        throw 1;
                                    }*/

                                }
                            }
                            //http依赖不处理
                        }
                        realPath && builder(_.extend({}, opts, {
                            "filePath": realPath
                        }));
                    });
                    //去掉被标记的依赖
                    args[1].elements = _.reject(deps, function (itemData) {
                        return itemData.isIgnore;
                    });
                }
                isAmd = true;
                //return !isAmd;  //中断遍历
            }
            //console.log(JSON.stringify(node));
        });
        if (isAmd) {
            sourceProcessed = minifyJs(ast);
            //sourceProcessed = escodegen.generate(ast);
            //设置加载完毕标志
            //sourceProcessed = 'avalon.modules["' + modName + '"] = {"state": 2}; ' + sourceProcessed;
            //行首加入来源注释
            sourceProcessed = '//From ' + relativePath + '\n' + sourceProcessed;
            //判断是否需要单独打包
            if (_.some(selfConfig.alone, function (itemPath) {
                return itemPath === relativePath.split('\\').join('/');
            })) {
                tempPath = relativePath.split(path.sep);
                tempPath.shift();
                tempPath = tempPath.join(path.sep);
                mkdirp.sync(path.dirname(opts.distPath + '/' + tempPath));
                fs.writeFileSync(opts.distPath + '/' + tempPath, sourceProcessed);
            } else {
                //append到对应的集成文件中
                fs.writeFileSync(opts.distPath + '/' + bundleName + '.js', sourceProcessed + '\n\n', {
                    "flag": "a"
                });
            }
        } else {    //普通js文件处理
            //行首加入来源注释
            sourceProcessed = '//From ' + relativePath + '\n' + minifyJs(ast);
            //放到dist下对应的位置
            tempPath = relativePath.split(path.sep);
            tempPath.shift();
            tempPath = tempPath.join(path.sep);
            mkdirp.sync(path.dirname(opts.distPath + '/' + tempPath));
            fs.writeFileSync(opts.distPath + '/' + tempPath, sourceProcessed);
        }
    } else if (fileExt === ".css") {
        /*ast = css.parse(fileContent, {
            "silent": true  //失败后保持沉默
        });
        //地址引用替换成绝对路径
        //语法树遍历
        walker(ast, function (node) {
            if (node.type == "declaration" && (node.property == "background" || node.property == "background-image" || node.property == "src")) {
                node.value = node.value.replace(cssUrlReg, function (match) {
                    var url,
                        urlPath,
                        realPath;
                    match = match.replace(/\s/g, '');
                    url = match.slice(4, -1).replace(/"|'/g, '');
                    if (/^\/|https:|http:|data:/i.test(url) === false) {
                        realPath = path.normalize(path.dirname(opts.filePath) + '/' + url);
                        url = selfConfig.servicePortalPath + path.relative(opts.cwdPath, realPath).split('\\').join('/');
                    }
                    return 'url("' + url + '")';
                });
            }
        });
        sourceProcessed = css.stringify(ast, {
            "compress": true
        });*/
        //css 语法树抽象对css hack支持的不够好，采用简单的正则替换法
        sourceProcessed = fileContent.replace(cssUrlReg, function (match) {
            var url,
                urlPath,
                realPath;
            match = match.replace(/\s/g, '');
            url = match.slice(4, -1).replace(/"|'/g, '');
            if (/^\/|https:|http:|data:/i.test(url) === false) {
                realPath = path.normalize(path.dirname(opts.filePath) + '/' + url);
                url = selfConfig.servicePortalPath + path.relative(opts.cwdPath, realPath).split('\\').join('/');
            }
            return 'url("' + url + '")';
        });
        sourceProcessed = new CleanCSS().minify(sourceProcessed);   //压缩
        //行首加入来源注释
        sourceProcessed = '/* From ' + relativePath + ' */\n' + sourceProcessed;
        //append到对应的集成文件中
        fs.writeFileSync(opts.distPath + '/' + bundleName + '.css', sourceProcessed + '\n\n', {
            "flag": "a"
        });
    } else if (fileExt === ".text") {
        modName = path.relative(opts.cwdPath, opts.filePath).replace(modNameReg, '');
        if (path.extname(opts.filePath) === ".css") {   //url地址替换
            fileContent = fileContent.replace(cssUrlReg, function (match) {
                var url,
                    urlPath,
                    realPath;
                match = match.replace(/\s/g, '');
                url = match.slice(4, -1).replace(/"|'/g, '');
                if (/^\/|https:|http:|data:/i.test(url) === false) {
                    realPath = path.normalize(path.dirname(opts.filePath) + '/' + url);
                    url = selfConfig.servicePortalPath + path.relative(opts.cwdPath, realPath).split('\\').join('/');
                }
                return 'url("' + url + '")';
            });
            //进入压缩
            sourceProcessed = 'define("' + modName + '", [], function () {' +
                'return decodeURIComponent("' + encodeURIComponent(fileContent) + '");' +
            '});';
        } else {    //html压缩
            //进入压缩
            sourceProcessed = 'define("' + modName + '", [], function () {' +
                'return decodeURIComponent("' + encodeURIComponent(minify(fileContent, {
                    "removeComments": true,
                    "collapseWhitespace": true
                })) + '");' +
            '});';
        }

        //设置加载完毕标志
        //sourceProcessed = 'avalon.modules["' + modName + '"] = {"state": 2}; ' + sourceProcessed;
        //行首加入来源注释
        sourceProcessed = '//From ' + relativePath + '\n' + sourceProcessed;
        fs.writeFileSync(opts.distPath + '/' + bundleName + '.js', sourceProcessed + '\n\n', {
            "flag": "a"
        });
    }
    //记录已处理的文件
    //console.log(relativePath);
    recordCache[relativePath] = true;
    console.log(relativePath + ' build end.');
}
/**
 * 遍历器
 * @param {Type} node
 * @param {Type} callback
 */
function walker(node, callback) {
    if (_.isObject(node) || _.isArray(node)) {
        if (_.isObject(node) && callback(node) === false) {
            return;
        }
        _.each(node, function (subNode) {
            walker(subNode, callback);
        });
    }
}

function obfuscateJs(syntax) {
    var result = esmangle.optimize(syntax, null);
    result = esmangle.mangle(result);
    return result;
}

function minifyJs(syntax) {
    var option;
    option = {
        format: {
            indent: {
                style: ''
            },
            quotes: 'auto',
            compact: true
        }
    };
    try {
        syntax = obfuscateJs(syntax);
        syntax = escodegen.generate(syntax, option);
    } catch (evt) {
        syntax = '//error';
    }
    return syntax;
}
module.exports = builder;
