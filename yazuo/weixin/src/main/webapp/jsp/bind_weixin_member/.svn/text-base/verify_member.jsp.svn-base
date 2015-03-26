<%@page import="org.apache.activemq.openwire.DataStreamMarshaller"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<title>会员绑定微信</title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/crm.css" />
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>js/jquery/jquery.form.js"></script>
<script language="javascript">
    //获取验证码
    $('#getIdentifyNO').live('click',function(){
    	if(!vad_sub('.mem-phone')){
    		 $('.error-mesg').text('');
    		 $(".error-mesg").text("手机号输入错误");
    	     return false;
    	}
    	 var count = 60;//每间隔六十秒获取验证码
    	 var timeId = "";
    	 function countSecond(){
    		if(count==0){
    		window.clearInterval(timeId);
    		count=60;
    		$('#getIdentifyNO').html('获取验证码');
    		document.getElementById('getIdentifyNO').disabled = false;
    		return false;
    		}
    		$('#getIdentifyNO').html(count+'秒后获取');
    		document.getElementById('getIdentifyNO').disabled = true;
    		count--;
    	}
    	  timeId =window.setInterval(countSecond,1000);

       //获取验证码之前先对用户基本信息验证
    	$.ajax({
    	 type: "post",
    	 url:  '<%=path%>/bindOldMember/registerIdentifyingCode.do',
    	 cache: false,
    	 dataType : "text",
    	 data: "brandId=${brandId}&weixinId=${weixinId}&title=${title}&phoneNo="+$('#phoneNo').val().trim()+"&random="+Math.random(),
    	 success: function(json){
    		 if(json.trim().length==3){//成功获取验证码
    			 $('.error-mesg').text('');
    			 $('.error-mesg').text('验证码已发送');
    		 }else if(json.trim()=="-1"){
    			 $('.error-mesg').text('');
    			 $('.error-mesg').text('抱歉，微信端暂时无法注册会员，请到门店办理');
    		 }else{
    			 $('.error-mesg').text('');
    			 $('.error-mesg').text('请重新获取');
    		 }
    	 },
    	 error: function(XMLHttpRequest){
    		 if (processCommErr(XMLHttpRequest)) {
    			 $('.error-mesg').text('');
    		     $('.error-mesg').text('获取失败请重新获取');
    		 }
    	 }
    	});
    });
    
 function vad_sub(id) {
	var reg = /^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\d{8}$/;// 手机号码验证
	if (!reg.test($(id).val()) || $(id).val().trim() == '') {// 为空或错误时提示错误信
		return false;
	} else {
		return true;
	}
}

function verifyMember () {
	var identifyNo = $("#identifyNo").val().trim();
	if (!vad_sub("#phoneNo")) {
		 $('.error-mesg').text('输入手机号不合规则');
		 return;
	} else if (identifyNo==null || identifyNo=="") {
		$('.error-mesg').text('请输入验证码');
		return;
	}
	$.ajax({
		url:"<%=path%>/bindOldMember/verifyAndAddMember.do?brandId=${brandId}&weixinId=${weixinId}"
		+"&identifyCode="+identifyNo+"&phoneNo="+$('#phoneNo').val().trim()+"&random="+Math.random(),
		type:"post",
		dataType:"json",
		success:function(data) {
			if (data.flag==1) {
				window.location.href = "<%=path%>"+data.url;
			} else {
				$('.error-mesg').text(data.msg);
			}		
		}
	});
}

</script>
</head>
<body>
	<div class="w">
		<section class="mem-main" id="registerInforamtion">
		<h1>会员绑定微信</h1>
		<form method="post" id="userInfo">
			<p id="userPhone">
				<label>手 机：</label>
				<span><input type="tel" class="text mem-phone" name="phoneNo" id="phoneNo" /></span>
			</p>
		</form>
		<div class="clear_wrap" id="userIndentity">
			<p class="yzm left">
				<label>验证码：</label>
				<span><input type="text" class="text mem-vadm" placeholder="请输入验证码" id="identifyNo" /></span>
			</p>
			<a href="javascript:void(0)" class="yz_btn right" id="getIdentifyNO">获取验证码</a>
		</div>
		<div class="sub-div" id="userSubmit">
			<span class="error-mesg"></span>
			 <a href="javascript:verifyMember();" class="mem-sub mem-submit">提&nbsp;&nbsp;&nbsp;交</a>
		</div>
		</section>
		<!--提交成功-->
		<section class="mem-main" style="display:none" id="submitInformation">
		<div class="mem-succ">
			<img src="<%=path%>/images/suc1.png" width="40" /><span id="cccc">您已成功成为粉丝</span>
		</div>
		<div class="sub-div">
			<a href="#" class="mem-sub" id="submitSuccess">确&nbsp;&nbsp;&nbsp;定</a>
		</div>
		</section>
	</div>
</body>
</html>
