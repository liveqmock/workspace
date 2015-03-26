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
<!doctype html>
<html class="no-js" lang="">
<head>
<meta charset="utf-8">
<meta name="description" content="">
<meta name="HandheldFriendly" content="True">
<meta name="MobileOptimized" content="320">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="cleartype" content="on">
<title>会员绑定微信</title>
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/a.min.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/jquery.mobile.icons.min.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/jquery.mobile.structure-1.4.5.min.css" />
<link rel="stylesheet" href="<%=path %>/css/new_member/base.css" />
<link rel="stylesheet" href="<%=path %>/css/new_member/member.css" />
<script type="text/javascript">
	var ctx = "<%=path%>";
</script>
<script type="text/javascript" src="<%=path%>/js/jquery/jquery.js"></script>
<script src="<%=path %>/js/jquery.mobile/jquery.mobile-1.4.5.min.js"></script>
<script language="javascript">
$(function (){
    //获取验证码
    $('#getIdentifyNO').on('click',function(){
    	if(!vad_sub('#phoneNo')){
	    	$(".mesg-error").show();
    		 $(".mesg-error .coupon-tips").html('');
    		 $(".mesg-error .coupon-tips").html("手机号输入错误");
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
    		 $(".mesg-error").show();
    		 if(json.trim().length==3){//成功获取验证码
    			 $(".mesg-error .coupon-tips").html('');
    			 $(".mesg-error .coupon-tips").html('验证码已通过短信的形式发送到您的手机上');
    		 }else if(json.trim()=="-1"){
    			 $(".mesg-error .coupon-tips").html('');
    			 $(".mesg-error .coupon-tips").html('抱歉，微信端暂时无法注册会员，请到门店办理');
    		 }else{
    			 $(".mesg-error .coupon-tips").html('');
    			 $(".mesg-error .coupon-tips").html('请重新获取');
    		 }
    	 },
    	 error: function(XMLHttpRequest){
    		 if (processCommErr(XMLHttpRequest)) {
    			 $(".mesg-error .coupon-tips").html('');
    		     $(".mesg-error .coupon-tips").html('获取失败请重新获取');
    		 }
    	 }
    	});
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
	$(".mesg-error").show();
	var identifyNo = $("#identifyNo").val().trim();
	if (!vad_sub("#phoneNo")) {
		 $(".mesg-error .coupon-tips").html('输入手机号不合规则');
		 return;
	} else if (identifyNo==null || identifyNo=="") {
		$(".mesg-error .coupon-tips").html('请输入验证码');
		return;
	}
	$(".m-btn3").hide();
	$(".mesg-error").hide();
	$.ajax({
		url:"<%=path%>/bindOldMember/verifyAndAddMember.do?brandId=${brandId}&weixinId=${weixinId}"
		+"&identifyCode="+identifyNo+"&phoneNo="+$('#phoneNo').val().trim()+"&random="+Math.random(),
		type:"post",
		dataType:"json",
		success:function(data) {
			if (data.flag==1) {
				$(".mesg-error").hide();
				window.location.href = "<%=path%>"+data.url;
			} else {
				$(".m-btn3").show();
				$(".mesg-error .coupon-tips").html(data.msg);
			}		
		}
	});
}

</script>
</head>
<body>

	<div data-role="page" data-theme="a">
	<div data-role="header" data-position="inline">
		<!-- <a target="_self"href="javascript:window.history.go(-1)" class="ui-btn ui-shadow ui-corner-all ui-icon-arrow-l ui-btn-icon-left"></a> -->
        <h1>会员中心</h1>        
		<!-- <a target="_self" href="#" class="ui-btn ui-shadow ui-corner-all ui-icon-home ui-btn-icon-right"></a> -->
    </div>
    <div data-role="content" data-theme="a" class="m-wrapper">
        <section class="weixin-input input-border-top bind-weixin">
    		<h2>会员绑定微信</h2>
            <div class="weixin-input-cont clear-wrap"><label>手机号：</label><input type="text" name="phoneNo" id="phoneNo" placeholder="请输入您的新手机号" /></div>
            <div class="weixin-input-cont yzm-input-cont clear-wrap">
            	<label>验证码：</label><input type="text" placeholder="请输入验证码" id="identifyNo"/>
            	<span class="yzm-btn" id="getIdentifyNO">获取验证码</span>
                <!--<span class="yzm-btn reset-yzm">(60秒)后重新获取</span>-->
            </div>            
            <div class="mesg-error"><div class="coupon-tips"></div></div>
        </section>
        <section class="sucess-btn-cont">
        	<button class="m-btn3" onclick="javascript:verifyMember();">提交</button>
        </section>
    </div>
</div>
</body>
</html>
