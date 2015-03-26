var path = require('path'),
    glob=require("glob"),
    fs = require('fs'),
    _ = require("lodash"),
    rimraf = require("rimraf"),
    builder = require('./build/core');
var cwd = __dirname + "/../src/main/webapp/portal",
    dist = cwd + '/origin/dist',
    buildType = "local";
process.argv.forEach(function (val, index, array) {
    if (index === 2 && (val === "local" || val === "remote")) {
        buildType = val;
    }
});
//清理dist目录
rimraf.sync(dist);
fs.mkdirSync(dist);
_.each(["**/*.js", "**/*.css"], function (globPath) {
    glob(globPath, {
        "cwd": cwd
    }, function (err, filePaths) {
        if (err) throw err;
        //console.log(filePaths);
        filePaths.forEach(function (filePath) {
            //builder(cwd + '/' + filePath, cwd, dist);
            builder({
                "filePath": cwd + '/' + filePath,
                "cwdPath": cwd,
                "distPath": dist,
                "mode": buildType //默认开发模式
            });
        });
    });
});

