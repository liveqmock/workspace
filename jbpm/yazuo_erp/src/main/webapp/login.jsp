<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>登录</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <link rel="stylesheet" href="portal/origin/asset/base.css">
        <link rel="stylesheet" href="portal/origin/asset/login.css">
        <script src="portal/origin/asset/third/jquery-1.11.1.js"></script>
        <script src="portal/origin/asset/third/json2.js"></script>
        <script src="portal/origin/asset/third/store.js"></script>
        <script>
            jQuery(function ($) {
                var winEl = $(window),
                    bodyEl = $('body'),
                    loginFormEl = $('.login-form', bodyEl),
                    accountEl = $('.account', loginFormEl),
                    pwdEl = $('.pwd', loginFormEl),
                    rememberCkEl = $('#is-remember'),
                    accountStore = store.get('account'),
                    forgetPwdLinkEl = $('.forget-pwd-link', bodyEl),
                    forgetPwdDialogEl = $('.find-pwd-dialog', bodyEl),
                    forgetPwdOlEl = $('.find-pwd-overlay', bodyEl),
                    noFlashTipEl = $('.no-flash-tip', loginFormEl),
                    submitEl = $('.f-submit', loginFormEl);
                //尝试关闭录音播放
                var softPos = document.getElementById('softPos');
                if (softPos && softPos.RecordClose) {
                    softPos.RecordClose();
                }

                //判断是否支持记住密码
                if (!store.enabled) {
                    alert('当前浏览器不支持本地存储，请联系系统管理员');
                    rememberCkEl.prop('disabled', true);
                } else {
                    rememberCkEl.prop('checked', true);
                    //默认填写最后一次登录的账号信息
                    if (accountStore) {
                        accountEl.val(accountStore[accountStore.length - 1].account);
                        pwdEl.val(accountStore[accountStore.length - 1].pwd);
                    }
                    //监听account的change事件，自动填充对应的密码
                    accountEl.change(function () {
                        var account = $.trim($(this).val()),
                            i;
                        if (accountStore) {
                            for (i = 0; i < accountStore.length; i++) {
                                if (accountStore[i].account == account && rememberCkEl.prop('checked')) {
                                    pwdEl.val(accountStore[i].pwd);
                                    break;
                                }
                            }
                        }
                    });
                }
                //忘记密码
                forgetPwdLinkEl.click(function () {
                    forgetPwdDialogEl.css({
                        "top": (winEl.height() - forgetPwdDialogEl.outerHeight()) / 2,
                        "left": (winEl.width() - forgetPwdDialogEl.outerWidth()) / 2
                    }).show();
                    forgetPwdOlEl.show();
                    $('.mobile', forgetPwdDialogEl).val("");
                    $('.mobile', forgetPwdDialogEl)[0].focus();
                    return false;
                });
                forgetPwdDialogEl.on('click', '.f-cancel', function () {
                    forgetPwdDialogEl.hide();
                    forgetPwdOlEl.hide();
                }).on('click', '.f-submit', function () {
                    var mobile = $.trim($('.mobile', forgetPwdDialogEl).val());
                    if (mobile) {
                        $.ajax({
                            type: "POST",
                            url:'login/getPWD.do',
                            data:{
                                "tel": mobile
                            },
                            dataType: "json",
                            success: function(data){
                                 if(!data.flag) {
                                    alert(data.message);
                                 } else {
                                    alert(data.message);
                                    forgetPwdDialogEl.hide();
                                    forgetPwdOlEl.hide();
                                 }
                            },
                            error: function () {
                                alert("服务器连接失败，请与管理员联系");
                            }
                        });
                    } else {
                        alert('请输入手机号');
                    }
                });
                winEl.resize(function () {
                    if (forgetPwdDialogEl.is(':visible')) {
                        forgetPwdDialogEl.css({
                            "top": (winEl.height() - forgetPwdDialogEl.outerHeight()) / 2,
                            "left": (winEl.width() - forgetPwdDialogEl.outerWidth()) / 2
                        });
                    }
                });

                //提交
                loginFormEl.on('click', '.f-submit', function () {
                    var tel = $.trim(accountEl.val()),
                        pwd = $.trim(pwdEl.val()),
                        softPos = document.getElementById('softPos');
                    $.ajax({
                         type: "post",
                         contentType : 'application/json',
                         url:'login/verifyMac.do?tel='+tel,
                         dataType:"json",
                         success:function(responseData){
                             var isVerified = false,
                                 i,
                                 mac,
                                 data;
                             if(!responseData.flag){
                                 alert(responseData.message);
                             }else{
                                 //验证MAC地址是否与本机相同
                                 data = responseData.data;
                                 if(!softPos || !softPos.VerifyMAC){
                                	 //需要给苹果客户端一个入口，如果是mac地址为111111，不需要特殊验证
                                	 for(i=0; i < data.length; i++){
	                                     mac = data[i].mac;
	                                     if ('111111'== mac) {
	                                        isVerified = true;
	                                        break;
	                                     }
	                                 }
                                	 if(!isVerified){
	                                     alert("你的浏览器不支持MAC地址校验, 需要安装一个插件");
	                                     return false;
                                	 }
                                }else {
	                                 for(i=0; i < data.length; i++){
	                                     mac = data[i].mac;
	                                     if ('111111'== mac) continue;
	                                     if (softPos.VerifyMAC(mac) == 0) {
	                                        isVerified = true;
	                                        break;
	                                     }
	                                 }
                              	  }
                                 if(!isVerified){
                                     alert("你只能在自己的电脑上登录");
                                     return;
                                 }else{
                                      //验证登录用户的用户名和密码合法性
                                     $.ajax({
                                        type: "post",
                                        contentType : 'application/json',
                                        url:'login/loginSubmit.do',
                                        data: '{"tel":"' + tel + '", "password":"' + pwd +'"}',
                                        dataType:"json",
                                        success:function(responseData){
                                            var accountStore,
                                                i;
                                            if (responseData.flag) {
                                                if (rememberCkEl.prop('checked')) { //记住密码
                                                    accountStore = store.get('account') || [];
                                                    for (i = 0; i < accountStore.length; i++) {
                                                        if (accountStore[i].account == tel) {
                                                            //accountStore[i].pwd = pwd;
                                                            //alreadyExist = true;
                                                            accountStore.splice(i, 1);  //保证记录最后一次登录最新
                                                            break;
                                                        }
                                                    }
                                                    accountStore.push({
                                                        "account": tel,
                                                        "pwd": pwd
                                                    });
                                                    store.set('account', accountStore);
                                                }
                                                location.href = "./index.jsp";
                                            } else {
                                                alert(responseData["message"]);
                                            }
                                        },
                                        error: function () {
                                            alert("服务器连接失败，请与管理员联系");
                                        }
                                    });
                                 }
                             }
                         },
                        error: function () {
                            alert("服务器连接失败，请与管理员联系");
                        }
                     });
                     return false;
                });
                
                //测试是否安装flash插件，已安装才可登录
                if (isFlashPlayerInstalled()) {
                    noFlashTipEl.hide();
                    submitEl.removeClass('submit-disabled').prop('disabled', false);
                } else {
                    noFlashTipEl.show();
                    submitEl.addClass('submit-disabled').prop('disabled', true);
                }
                
                //私有函数
                function isFlashPlayerInstalled () {
                    var isIE = !-[1,],
                        isInstalled = false;
                    if(isIE){
                        try{
                            var swf1 = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
                            isInstalled = true;
                        } catch(evt){ }
                    } else {
                        try{
                            var swf2 = navigator.plugins['Shockwave Flash'];
                            if(swf2){
                                isInstalled = true;
                            }
                        } catch(evt){}
                    }
                    return isInstalled;
                }
            });
        </script>
    </head>
    <body>
        <div class="page">
            <div class="page-bd">
                <div class="page-bd-inner">
                    <div class="logo page-bd-north">雅座销售ERP</div>
                    <div class="page-bd-center">
                        <form class="login-form" action="#" method="post">
                            <div class="f-title">用户登录</div>
                           <!-- <div class="f-field fn-clear">
                                <label class="ff-label fn-left">组织名：</label>
                                <div class="ff-value fn-left">
                                    <input type="text" class="organization input-text" />
                                </div>
                            </div>-->
                            <div class="f-field fn-clear">
                                <label class="ff-label fn-left">账&nbsp;&nbsp;号：</label>
                                <div class="ff-value fn-left">
                                    <input type="text" class="account input-text" />
                                </div>
                            </div>
                            <div class="f-field fn-clear">
                                <label class="ff-label fn-left">密&nbsp;&nbsp;码：</label>
                                <div class="ff-value fn-left">
                                    <input type="password" class="pwd input-text" />
                                </div>
                            </div>
                            <div class="remember-field f-field fn-clear">
                                <label class="ff-label fn-left">&nbsp;</label>
                                <div class="ff-value fn-left">
                                    <div class="ff-value-inner fn-clear">
                                        <div class="ff-value-l fn-left">
                                            <input id="is-remember" type="checkbox" class="is-remember" />&nbsp;&nbsp;<label for="is-remember">记住密码</label>
                                        </div>
                                        <div class="ff-value-r fn-right">
                                            <a href="#" class="forget-pwd-link">忘记密码</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="f-field fn-clear">
                                <label class="ff-label fn-left">&nbsp;</label>
                                <div class="ff-value fn-left">
                                    <button type="button" class="f-submit submit-disabled" disabled="disabled">登&nbsp;录</button>
                                </div>
                            </div>
                            <div class="no-flash-tip">您还没有安装Flash插件，将无法登录ERP系统，点击<a href="http://get.adobe.com/cn/flashplayer/" target="_blank">安装插件</a></div>
                        </form>
                    </div>
                    <div class="page-bd-south fn-clear">
                        <div class="south-l fn-left">
                            <span class="icon-yazuo-logo iconfont"></span>&nbsp;<span class="icon-text">做会员，就找雅座</span>
                        </div>
                        <div class="south-r fn-right">咨询电话：400-1055-888</div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 找回密码 -->
        <div class="find-pwd-dialog">
            <div class="f-field">
                <label>手机号：</label><input type="text" class="mobile" name="mobile" />
            </div>
            <div class="f-action">
                <button type="button" class="f-submit">提交</button>&nbsp;&nbsp;<button type="button" class="f-cancel">取消</button>
            </div>
        </div>
        <div class="find-pwd-overlay"></div>
        <!-- 嵌入插件获得本机MAC地址 -->
        <embed type="application/npsoftpos" width=0 height=0 id="softPos"/>
    </body>
</html>

