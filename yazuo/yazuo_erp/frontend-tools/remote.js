var Ssh = require('ssh2'),
    connect = require('connect'),
    morgan = require('morgan'),
    serveStatic = require('serve-static'),
    http = require('http'),
    url = require('url'),
    path = require('path'),
    proxy = require('proxy-middleware'),
    fs = require('fs'),
    remoteConfig = JSON.parse(fs.readFileSync('./remote.json')),
    glob=require("glob");

var config=remoteConfig.config,
    localMaps=config.localMaps,
    remote=config.remote,
    localPort=config.localPort,
    remoteRootPath=config.remoteRootPath,
    localRootPath=config.localRootPath,
    hotFiles=config.hotFiles,
    sshConfig=config.ssh;
// 使用connect
var app = connect().use(morgan('dev'));  // 使用morgan中间件
//console.log(JSON.stringify(localMaps));
//本地映射拦截
localMaps.forEach(function(itemMap){
    //console.log(path.normalize(__dirname+'/'+itemMap.localPath));
    app.use(itemMap.remotePath, serveStatic(path.normalize(__dirname+'/'+itemMap.localPath)));
});
//使用proxy-middleware中间件进行反向代理
app.use(proxy(url.parse(remote)));
//监听本地端口
app.listen(localPort, function(){console.log('Connect with: http://127.0.0.1:' + localPort)});
//热部署
/*hotFiles.forEach(function(hotFile){
    glob(hotFile, { //支持glob语法
        cwd:localRootPath
    },function (err, files) {
        if(!err){
            files.forEach(function(filePath){
                var localFilePath=localRootPath+'/'+filePath,
                    remoteFilePath=remoteRootPath+'/'+filePath;
                //console.log(localFilePath+'-------------'+remoteFilePath);
                fs.watchFile(localFilePath, function (curr, prev){
                    var c = new Ssh();
                    c.on('ready', function() {
                        console.log('Connection :: ready');
                        c.sftp(function(err, sftp) {
                            if (err) throw err;
                            sftp.on('end', function() {
                                console.log('SFTP :: SFTP session closed');
                            });
                            sftp.stat(remoteFilePath,function(err,stats){
                                if (err){   //不存在直接覆盖
                                    sftp.fastPut(localFilePath,remoteFilePath,function(err){
                                        if (!err) {
                                            console.log('success');
                                            sftp.end();
                                            c.end();
                                        }

                                    });
                                }else{
                                    sftp.unlink(remoteFilePath,function(err){
                                        if (!err){
                                            //console.log('success');
                                            sftp.fastPut(localFilePath,remoteFilePath,function(err){
                                                if (!err){
                                                    console.log('success');
                                                    sftp.end();
                                                    c.end();
                                                }

                                            });
                                        }
                                    });
                                }
                            });

                        });
                    });
                    c.on('error', function(err) {
                      console.log('Connection :: error :: ' + err);
                    });
                    c.on('end', function() {
                      console.log('Connection :: end');
                    });
                    c.on('close', function(had_error) {
                      console.log('Connection :: close');
                    });
                    c.connect(sshConfig);
                });
            });
            //console.log(JSON.stringify(files));
        }
    });
});*/
