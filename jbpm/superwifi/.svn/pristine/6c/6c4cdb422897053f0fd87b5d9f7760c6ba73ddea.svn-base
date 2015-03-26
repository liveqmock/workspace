<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>SUPER Wifi</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%
        String path = request.getContextPath();
        int port = request.getServerPort();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + port + path + "/";
        if(port == 80){
            basePath = request.getScheme() + "://" + request.getServerName() + path + "/";
        }
    %>

    <!-- Place favicon.ico and apple-touch-icon(s) in the root directory -->
    <link href='http://fonts.useso.com/css?family=Roboto' rel='stylesheet' type='text/css'>
    <script>
        var VERSION="0.0.0.0.0"; //版本号设置规则:all.asset.module.page.widget
        var PUBLISH_MODEL = "development";
        var BASE_PATH="<%=basePath%>";  //web根目录地址
        var WIDGET_BASE_PATH="<%=basePath%>client/origin/widget/";  //widget根目录地址
        (function () {
            var versions = VERSION.split(".");
            var searchParams = location.search.slice(1),
                tempParam;
            searchParams = searchParams.split("&");
            for (var i = 0; i < searchParams.length; i++) {
                tempParam = searchParams[i].split("=");
                if (tempParam[0] === "model" && tempParam[1]) {
                    PUBLISH_MODEL = tempParam[1];
                    break;
                }
            }
            if (PUBLISH_MODEL === "development") {
                document.write('<link rel="stylesheet" href="client/origin/asset/base.css?v=' + versions[0] + '.' + versions[1] + '">');
                document.write('<link rel="stylesheet" href="client/origin/widget/widget.css?v=' + versions[0] + '.' + versions[4] + '">');
                document.write('<link rel="stylesheet" href="client/origin/asset/common.css?v=' + versions[0] + '.' + versions[1] + '">');
                document.write('<link rel="stylesheet" href="client/origin/asset/index.css?v=' + versions[0] + '.' + versions[1] + '">');
                document.write('<script src="client/origin/asset/third/underscore/underscore.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="client/origin/asset/third/underscore/underscore.string.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="client/origin/asset/third/jquery-1.11.1.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="client/origin/asset/third/avalon/avalon.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="client/origin/asset/avalon-ext.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="client/origin/asset/boot.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
            } else if (PUBLISH_MODEL === "product") {
                document.write('<link rel="stylesheet" href="client/origin/dist/asset.css?v=' + versions[0] + '.' + versions[1] + '">');
                document.write('<link rel="stylesheet" href="client/origin/dist/widget.css?v=' + versions[0] + '.' + versions[4] + '">');
                document.write('<link rel="stylesheet" href="client/origin/dist/page.css?v=' + versions[0] + '.' + versions[3] + '">');
                document.write('<script src="client/origin/asset/third/underscore/underscore-min.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="client/origin/asset/third/underscore/underscore.string.min.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="client/origin/asset/third/jquery-1.11.1.min.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="client/origin/asset/third/avalon/avalon.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="client/origin/dist/asset/avalon-ext.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="client/origin/dist/asset/boot.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="client/origin/dist/widget.js?v=' + versions[0] + '.' + versions[4] + '"><\/script>');
                document.write('<script src="client/origin/dist/asset.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="client/origin/dist/asset/util.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="client/origin/dist/asset/common.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="client/origin/dist/module.js?v=' + versions[0] + '.' + versions[2] + '"><\/script>');
                document.write('<script src="client/origin/dist/page.js?v=' + versions[0] + '.' + versions[3] + '"><\/script>');
                document.write('<script src="client/origin/dist/asset/index.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
            }
        }());
    </script>
    <script>
        app.setAppData('permission', <security:authentication property="principal.authorityList"  htmlEscape="false"/>);   //用户权限
        app.setAppData('user', <security:authentication property="principal.user"  htmlEscape="false"/>);   //用户
    </script>
</head>
<body>
    <link rel="stylesheet" href="client/origin/asset/widget.css?v=0.0">
    <div class="app" ms-controller="app">
        <div class="app-hd">
            <div class="app-hd-inner app-inner">
                <div class="logo-wrapper fn-left">
                    <a class="logo-l" href="<%=basePath%>">SUPER Wifi</a>
                </div>
                <div class="domain-wrapper fn-left">
                    <span class="merchant-domain" ms-module="merchantdomain,$,$merchantDomainOpts"></span>
                </div>
                <div class="login-info-wrapper fn-right">
                    <label>欢迎您！</label>&nbsp;&nbsp;<span class="login-user-name">{{loginUserName}}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!--<a href="javascript:;" class="icon-bell iconfont">&#xe60c;</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--><a href="javascript:;" ms-mouseenter="showOperation" ms-mouseleave="closeOperation" class="icon-user iconfont">&#xe60d;</a>
                    <div class='tips-layer' ms-visible="showOper"></div>
                </div>
            </div>
        </div>
        <div class="app-bd">
            <div class="app-inner">
                <div class="app-inner-l fn-left">
                    <div class="app-inner-l-inner">
                        <div class="page-nav-wrapper"></div>
                    </div>
                </div>
                <div class="app-inner-r" ms-css-min-height="appBdMinHeight">
                    <div id="page-wrapper">

                    </div>
                </div>
            </div>
        </div>
        <div ms-module="chargepwdsetting,$,$chargepwdsettingOpts"></div>
        <div ms-module="useroperation,$,$useroperationOpts"></div>
    </div>
    <script>
        require(["index","ready!"],function(index){
            index.$init();
        });
    </script>
</body>
</html>
