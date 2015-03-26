<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <link rel="stylesheet" href="client/origin/asset/base.css" />
    <link rel="stylesheet" href="client/origin/asset/login.css" />
    <script src="client/origin/asset/third/jquery-1.11.1.min.js"></script>
    <script>
        jQuery(function ($) {
            var formEl = $('.login-form');
            formEl.on('click', '.icon-clear', function () {
                var meEl = $(this),
                    fieldEl = meEl.closest('.f-field'),
                    inputEl = $('input', fieldEl);
                inputEl.val('');
                inputEl[0].focus();
                meEl.hide();
            }).on('keyup focus', ':text,:password', function () {
                var meEl = $(this),
                    fieldEl = meEl.closest('.f-field'),
                    clearEl = $('.icon-clear', fieldEl);
                if ($.trim(meEl.val()).length) {
                    clearEl.show();
                } else {
                    clearEl.hide();
                }
            }).on('focus', ':text,:password', function () {
                var meEl = $(this),
                    fieldEl = meEl.closest('.f-field');
                fieldEl.addClass('f-field-input-focus');
            }).on('blur', ':text,:password', function () {
                var meEl = $(this),
                    fieldEl = meEl.closest('.f-field');
                fieldEl.removeClass('f-field-input-focus');
            }).on('submit', function () {
                var accountEl = $('.account', formEl),
                    pwdEl = $('.pwd', formEl);
                var requestData,
                    account = $.trim(accountEl.val()),
                    pwd = $.trim(pwdEl.val());
                if (!account) {
                    alert('请输入账号');
                    return false;
                }
                if (!pwd) {
                    alert('请输入密码');
                    return false;
                }
                $.ajax({
                    "type": "post",
                    "url": "<%=basePath%>j_spring_security_check",
                    "data": {
                        "j_username": account,
                        "j_password": pwd
                    },
                    "success": function (responseData) {
                        if (responseData.flag) {    //登录成功进入功能页
                            location.href = "<%=basePath%>client.jsp";
                        } else {
                            alert(responseData.message);
                        }
                    }
                });
                return false;
            }).on('click', '.forget-pwd-link', function () {
                var h = $(window).height(),w = $(window).width();
                $('.login-dialog-phone').val('');
                $('.login-mark-layer').height(h).show();
                $('.login-dialog').css({'top':h/2-110,'left':w/2-180}).show();
                $('.input-area').show();
                $('.tips-area').hide();
                $('.phone-tips').hide();
                $('.login-bt-area').show();
            });
            $(".input-area input").keyup(function(){
                $('.phone-tips').hide();
            });
            $(".login-dialog").on("click","#submitPhone",function(){
                if($.trim($('.login-dialog-phone').val())==""){
                    $('.phone-tips').html('手机号码不能为空').show();
                }else if($('.input-area').is(':visible')){
                    $(this).hide();
                    $('#loadingBt').css("display","inline-block");
                    $.ajax({
                        "type": "post",
                        "url": "<%=basePath%>controller/user/resetUserPasswd.do",
                        "data": '{"mobileNumber": "' + $('.login-dialog-phone').val() +'"}',
                        "dataType": "json",
                        "contentType": "application/json; charset=utf-8",
                        "success": function (responseData) {
                            if (responseData.flag) {    //登录成功进入功能页
                                $('.input-area').hide();
                                $('.tips-area').show();
                                $('#submitPhone').css("display","inline-block");
                                $('#loadingBt').hide();
                                $('.login-bt-area').hide();
                                setTimeout(function(){
                                    $('.login-mark-layer').hide();
                                    $('.login-dialog').hide();
                                },2000);
                            }else{
                                $('.phone-tips').html(responseData.message).show();
                            }
                            $('#submitPhone').css("display","inline-block");
                            $('#loadingBt').hide();
                        }
                    });
                }else{
                    $('.login-mark-layer').hide();
                    $('.login-dialog').hide();
                }
            }).on("click","#cancelSubmit",function(){
                    $('.login-mark-layer').hide();
                    $('.login-dialog').hide();
            });
            $(window).resize(function(){
                var h = $(window).height(),w = $(window).width();
                $('.login-mark-layer').height(h);
                $('.login-dialog').css({'top':h/2-110,'left':w/2-180});
            });
        });
    </script>
</head>
<body>
    <div class="page">
        <div class="page-bd">
            <div class="page-bd-bg"><img src="client/origin/asset/image/login-bg.jpg" alt="" /></div>
            <div class="page-bd-inner">
                <div class="page-bd-center">
                    <form class="login-form" action="#" method="post">
                        <div class="logo"></div>
                        <div class="login-form-north"></div>
                        <div class="login-form-center">
                            <div class="f-title">用户登录</div>
                            <div class="f-field f-field-input">
                                <div class="f-field-inner">
                                    <label class="ff-label">账&nbsp;&nbsp;号：</label>
                                    <input type="text" class="account input-text" placeholder="在此输入您的手机号" />
                                    <!--<img class="icon-clear" src="client/origin/asset/image/s.gif" alt="删除" />-->
                                    <span class="icon-clear">&#10005;</span>
                                </div>
                            </div>
                            <div class="pwd-field f-field f-field-input">
                                <div class="f-field-inner">
                                    <label class="ff-label">密&nbsp;&nbsp;码：</label>
                                    <input type="password" class="pwd input-text" placeholder="请输入6位数字密码" />
                                    <!--<img class="icon-clear" src="client/origin/asset/image/s.gif" alt="删除" />-->
                                    <span class="icon-clear">&#10005;</span>
                                </div>
                            </div>
                            <div class="remember-field f-field fn-clear">
                                <!--<div class="ff-value-l fn-left">
                                    <input id="is-remember" type="checkbox" class="is-remember" />&nbsp;<label for="is-remember">记住密码</label>
                                </div>-->
                                <div class="ff-value-r fn-right">
                                    <a href="javascript:;" class="forget-pwd-link">忘记密码</a>
                                </div>
                            </div>
                            <div class="f-action f-field fn-clear">
                                <button type="submit" class="f-submit">用户登录</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="page-bd-south">雅座在线（北京）科技发展有限公司</div>
        </div>
    </div>
<div class="login-mark-layer"></div>
<div class="login-dialog">
    <h2 class="login-dialog-t">找回密码</h2>
    <div class="input-area">
        <p>请输入您的手机号：</p>
        <p><input type="text" class="login-dialog-phone" placeholder="在这里输入手机号"></p>
        <p class='phone-tips'></p>
    </div>
    <div class="tips-area">
        <p>临时密码已通过短信形式发送至您的手机，请注意查收。</p>
    </div>
    <div class="login-bt-area">
        <a href="javascript:;" id="submitPhone" class="main-btn-green">确认</a><a href="javascript:;" id="loadingBt" class="main-btn-green grey-bt">发送中...</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="javascript:;" id="cancelSubmit" class="main-btn">取消</a>
    </div>
</div>
</body>
</html>
