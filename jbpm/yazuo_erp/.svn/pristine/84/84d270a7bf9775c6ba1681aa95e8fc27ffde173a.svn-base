var fs = require('fs'),
    path = require('path'),
    glob=require("glob"),
    watch = require("watch");
var projectBasePath = path.normalize(__dirname + '/../'),
    projectSrcPath = path.normalize(projectBasePath + '/src/main/webapp/'),
    projectTargetPath = path.normalize(__dirname + '/../target/yazuo_erp-0.0.1-SNAPSHOT/');

watch.watchTree(projectSrcPath,{
    "ignoreDotFiles": true,
    "interval": 1000
}, function (f, curr, prev) {
    var targetPath,
        srcState;
    if (typeof(f)=="string") {
        targetPath = f.replace(/\\src\\main\\webapp/g, '\\target\\yazuo_erp-0.0.1-SNAPSHOT');
    }
    if (typeof f == "object" && prev === null && curr === null) {
        // Finished walking the tree
        console.log('watching start!');
    } else if (curr.nlink === 0) {  // f was removed //TODO: 直接删除文件夹
        srcState = fs.statSync(targetPath);
        if (srcState.isDirectory()) {
            fs.rmdirSync(targetPath);
        } else if (srcState.isFile()) {
            fs.unlinkSync(targetPath);
        }     
        console.log('remove: ' + srcState.isDirectory());
    } else { //new and change
        srcState = fs.statSync(f);
        if (srcState.isDirectory()) {
            deepMkdir(targetPath, function (err) {
                if (!err) {
                    console.log('replace done at: ' + targetPath);
                }
            });
        } else if (srcState.isFile()) {
            deepWriteFile(targetPath, fs.readFileSync(f), function (err) {
                if (!err) {
                    console.log('replace done at: ' + targetPath);
                }
            });
        }
    }
});

function deepWriteFile(filePath, content, callback) {
    var dirName = path.dirname(filePath);
    //console.log(filePath);
    fs.exists(dirName, function(exists) {
        //console.log(exists);
        if(exists) {
            fs.writeFile(filePath, content, callback);
        } else {
            
            deepMkdir(dirName, function (err) {
                if  (!err) {
                    fs.writeFile(filePath, content, callback);
                }
            });
        }
    });
};

function deepMkdir(dirpath, mode, callback) {
    if(arguments.length === 2) {
        callback = mode;
        mode = 0777;
    }
    //console.log(dirpath);
    fs.exists(dirpath, function(exists) {
        if(exists) {
            callback(null);
        } else {
            deepMkdir(path.dirname(dirpath), mode, function(err) {
                if(err) {
                    return callback(err);
                }
                fs.mkdir(dirpath, mode, callback);
            });
        }
    });
};
//console.log('start success!');
