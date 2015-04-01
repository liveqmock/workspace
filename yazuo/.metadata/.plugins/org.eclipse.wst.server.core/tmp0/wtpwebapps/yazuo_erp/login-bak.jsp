<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>登录</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%@ include file="common/meta.jsp"%>
<link href="<%=basePath%>css/jquery-ui-1.10.4.custom.min.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="<%=basePath%>js/jquery/jquery-ui-1.10.4.custom.min.js"></script>
<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

#login-box {
	width: 300px;
	padding: 20px;
	margin: 100px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}
</style>
</head>
<body>

	<h1>雅座ERP系统登录页面</h1>

	<div id="login-box">

		<h3>输入手机号和密码登录系统</h3>

		<div class="msg"></div>

		<form name='loginForm' action="" method='POST'>
			<table>
				<tr>
					<td>手机号:</td>
					<td><input type='text' class='text-tel' name='tel'></td>
				</tr>
				<tr>
					<td>密码:</td>
					<td><input type='password' class='text-password' name='password' /></td>
				</tr>
				<tr>
					<td colspan='2'><input name="submit" type="submit" class='btn-login' value="登录" /></td>
					<td>
					  <a href="javascript:;" class="text-findPWD">找回密码</a>
					</td>
				</tr>
			</table>
		</form>
	    <div class="findPWD-dialog fn-hide">  
	       <div>
			    请输入您的手机号（登陆账号）：<input type='tel' class='findpwd-text' id='JS_findpwdTxt' placeHolder='请输入…' />
			    <span class='errorTip fn-hide' id='JS_errorTip'>输入的手机号码有误，请重新输入！</span>
	       </div>
	       <div class="f-actions">
           		<button type="button" class="f-submit">确&nbsp;定</button>&nbsp;&nbsp;
           		<button type="button" class="f-cancel">取&nbsp;消</button>
      	  </div>	
        </div>
   </div>
        <!-- 嵌入插件获得本机MAC地址 -->
        <embed type="application/npsoftpos" width=0 height=0 id="SoftPos"/>
	<script>
        jQuery(function($){
        	$('.text-tel').focus();
        	//$('.btn-login').removeAttr("disabled");
        	$('.fn-hide').hide();
            var findPWDDialogEl=$('.findPWD-dialog');
        	$('.btn-login').on('click', function(){
        		var objThis = $(this);
        		//objThis.attr("disabled", "disabled");
        		var tel = jQuery.trim($('.text-tel').val());
        		var password = jQuery.trim($('.text-password').val());
                var mydata =  JSON.stringify({'tel': tel, 'password': password});
                if(tel.length==0){
                    alert('请输入手机号');
                    return;
                }
	             $.ajax({
	                 type: "post",
	                 contentType : 'application/json',
	                 url:'<%=basePath%>login/verifyMac.do?tel='+tel,
	                 dataType:"json",
	                 success:function(responseData){
	                	 if(responseData.flag==0){
	                		 alert(responseData.message);
	                	 }else{
	                		 //验证MAC地址是否与本机相同
	                		 var flag = 0;
		                	 for(var i=0;i<responseData.data.length;i++){
	                    		var mac = responseData.data[i].mac;
	                    	   	 var  SoftPos = document.getElementById('SoftPos');
	                    	   	 if(!SoftPos||!SoftPos.VerifyMAC){
	                    	   		 alert("你的浏览器不支持MAC地址校验, 需要安装一个插件");
	                    	   		 return;
	                    	   	 }
	                    		 var result = SoftPos.VerifyMAC(mac);
	                    		 if(result==0){	
	                    			 flag = 1;
	                    			 break;
	                    		 }
	                    	 }
		                	 if(flag == 0){
		                		 alert("你只能在自己的电脑上登录");
		                	 }else{
	                   			  //验证登录用户的用户名和密码合法性
	                   			 $.ajax({
	                                    type: "post",
	                                    contentType : 'application/json',
	                                    url:'<%=basePath%>login/loginSubmit.do',
	                                    data:mydata,
	                                    dataType:"json",
	                                    success:function(responseData){
	                                    	//alert( JSON.stringify(responseData));
	                                        if (responseData.flag == "1") {
	                                        	window.location="<%=basePath%>";
	                                        }
	                                        $('.msg').text(responseData["message"]);
	                                        //objThis.removeAttr("disabled");
	                                    }
	                              });
		                	 }
	                	 }
	                 }
	             });
                return false;
        	});
            //弹框初始化
            findPWDDialogEl.dialog({
                modal: true,
                autoOpen:false,
                title:"",
                closeText:"&#10005;",
                width:500,
                height:300,
                close: function( event, ui ) {
                    $('.findpwd-text').val("");
                }
            });
        	//点击找回密码链接
        	$('.text-findPWD').on('click', function(evt){
        		findPWDDialogEl.dialog('open');
                evt.preventDefault();
        		$(this).show();
        		$('.f-submit').removeAttr("disabled");
        	});
        	//找回密码
       	 	 findPWDDialogEl.on('click','.f-submit',function(evt){
       	 		 //点击确定按钮
        		var objThis = $(this);
                 var tel= _.str.trim($('.findpwd-text',findPWDDialogEl).val());
                 if(tel.length==0){
                     alert('请输入手机号');
                     return;
                 }
    			 $.ajax({
                     type: "POST",
                     url:'<%=basePath%>login/getPWD.do',
                     data:{
                         "tel":tel
                     },
                     dataType:"json",
                     success:function(data){
                         if(data.flag == 0){
                             alert("手机号有误");
                         }else{
                             //show the delete button.
                             alert(data.msg);
                             findPWDDialogEl.dialog("close");
                         }
                     }
                 });
             }).on('click','.f-cancel',function(evt){   //点击取消按钮
                 findPWDDialogEl.dialog('close');
             });
        });
    </script>
</body>
</html>

