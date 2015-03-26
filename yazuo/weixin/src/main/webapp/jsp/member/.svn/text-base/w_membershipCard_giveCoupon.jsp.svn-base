<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/crm.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
<script src="${pageContext.request.contextPath}/js/jquery/1.8.3/jquery-1.8.3.min.js"></script>



<title>券赠送</title>
</head>
<body class='grayBody'>
	<!-- 赠送表单 -->
	<div id="JS_couponGiveForm">
		<h3>您正在向好友赠送优惠券</h3>
		<p class="couponGiveFormItem"><label>赠送内容：</label>${toSend.couponName} &nbsp;&nbsp;${toSend.couponNum}张</p>
		<c:choose>
		<c:when test="${toSend.brandId eq 1631 }">
			<p class="couponGiveFormItem"><label>活动有效期：</label>${toSend.beginTime } 至 ${toSend.endTime }</p>
		</c:when>
		<c:otherwise>
			<p class="couponGiveFormItem"><label>有效期：</label>${toSend.beginTime } 至 ${toSend.endTime }</p>
		</c:otherwise>
		</c:choose>
		<h3>请输入好友的手机号：</h3>
		<p class="couponGivePhoneNum"><label>手&nbsp;&nbsp;机：</label><input id="JS_couponGivePhoneNum" type="text" placeHolder='请输入' /></p>
		<p id="couponGiveError">请填写手机号</p>
		<a href="javascript:;" class="giveCouponOrangeBtn" id="JS_giveCouponFormSubmitBtn">提&nbsp;&nbsp;交</a>
	</div>
	<!-- 赠送表单 end-->
	<!-- 赠送成功 提示  -->
	<div id="JS_couponGiveSuccess" style="display:none">
		<h3 class="couponGiveSuccessTitle"><p> <i class="couponGiveSuccessIco"></i> 优惠券赠送成功 </p></h3>
		<p class="couponGivePeople">您已成功奖优惠券赠送给好友<br/> <span id="JS_sendSuccessPhoneNum"></span></p>
		<div class="couponGiveSuccessDetail">
			优惠券内容：
			<p>${toSend.couponName }1张</p>
			<p>${toSend.beginTime } 至 ${toSend.endTime }</p>			
		</div>
		<a href="${pageContext.request.contextPath}/weixin/phonePage/membershipCard.do?brandId=${toSend.brandId}&weixinId=${toSend.weixinId}" class="giveCouponOrangeBtn">点击返回会员中心</a>
	</div>
	<!-- 赠送失败提示  -->
	<div id="JS_couponGivefail" style="display:none">
		<h3 class="couponGiveSuccessTitle"><p> <i class="couponGiveSuccessIco"></i> 啊哦，赠送遇到了问题 </p></h3>
		<p class="couponGivePeople">您赠送给好友<br/> <span id="JS_sendFailPhoneNum"></span>失败，请您稍后尝试。</p>
		<a href="${pageContext.request.contextPath}/weixin/phonePage/membershipCard.do?brandId=${toSend.brandId}&weixinId=${toSend.weixinId}" class="giveCouponOrangeBtn" >点击返回会员中心</a>
	</div>
	<!-- 提示 赠送 弹窗 -->
	<div class="popWin" id="JS_couponGivePopWin">
		<div class="popBg"></div>
		<div class="popInfo">
	    	<div class="centerTxt">
				<h3>请确认您好友的手机号</h3>
				<p>优惠券送出后将无法收回呦~</p>
				<strong id="JS_sendToPhoneNum"></strong>
				<p><a href="javascript:send();" class="popOrangeBtn" id="JS_couponGivePopSureBtn">确 定</a>
					<a href="javascript:;" class="popOrangeBtn" id="JS_couponGivePopSureBtn1">确 定</a>
					<a href="javascript:;" class="popGrayBtn" id="JS_couponGivePopCancelBtn">取 消</a></p>
	    	</div>		
	    </div>
	</div>   
	<!-- 提示 赠送 弹窗 end-->
	
<script>
function isPhoneNum(phoneNum){
	var regPhone = /^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\d{8}$/;
    if(regPhone.test(phoneNum)==false){
    	return false;    	
     }else{
     	return true;
     }
}

function send(){
	$("#JS_couponGivePopSureBtn").hide();
	$("#JS_couponGivePopSureBtn1").show();
	var couponGivePhoneNum=$("#JS_sendToPhoneNum").text();
	$(".popOrangeBtn").addClass("popGrayBtn");	
	$.ajax({
		 type: "post",
		 url:  "${pageContext.request.contextPath}/weixin/${toSend.brandId}/sendFriend.do",
		 cache: false,
		 //async: false, //设为false就是同步请求
		 dataType : "json",
		 data: "fromUserId=${toSend.fromUserId}&couponId=${toSend.couponId}&mobile="+couponGivePhoneNum+"&weixinId=${toSend.weixinId}&couponNum=${toSend.couponNum}&couponName=${toSend.couponName}",
		 success: function(restr){
			 if("success"==restr.flag){
				//success 后
				$("#JS_couponGivePopWin").hide();
				$("#JS_couponGiveForm").hide();
				$("#JS_sendSuccessPhoneNum").text(couponGivePhoneNum+"~");
				$("#JS_couponGivefail").hide();	
				$("#JS_couponGiveSuccess").show();
		 	 }else{
		 		$("#JS_couponGivePopWin").hide();
				$("#JS_couponGiveForm").hide();
				$("#JS_sendFailPhoneNum").text(couponGivePhoneNum+"~");
				$("#JS_couponGiveSuccess").hide();
				$("#JS_couponGivefail").show();	
			 }
		}
  	});
	
}

jQuery(function($) {
	$("#JS_couponGivePhoneNum").on("focus",function(){
		$("#couponGiveError").hide();
	});
	//点击页面确定赠送按钮
	$('#JS_giveCouponFormSubmitBtn').on('click',function() {
		var phoneNum=$.trim($("#JS_couponGivePhoneNum").val());
		if(phoneNum == ''){
			$("#couponGiveError").text("请填写手机号");
			$("#couponGiveError").show();
		}else if(!isPhoneNum(phoneNum)){
			$("#couponGiveError").text("填写的手机号有误");
			$("#couponGiveError").show();
		}else{
			$("#JS_sendToPhoneNum").text(phoneNum);
			$("#JS_couponGivePopSureBtn1").hide();
			$("#JS_couponGivePopWin").show();			
		}
	});
	
	//弹窗 取消
	$("#JS_couponGivePopCancelBtn").on("click",function(){
		$(this).parents(".popWin").hide();
	});
	//弹窗 确定 赠送按钮
	/* $("#JS_couponGivePopSureBtn").on("click",function(){
		
		
		
	}); */
});
</script>	
</body>
</html>
