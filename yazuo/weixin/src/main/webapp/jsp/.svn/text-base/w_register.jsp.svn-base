<%@page import="com.yazuo.weixin.util.JudgeMenuShow"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	BusinessVO business = ((BusinessVO) request
	.getAttribute("business"));
	Integer brandId = business.getBrandId();
	String title = business.getTitle();
	String weixinId=(String)request.getAttribute("weixinId");
	Boolean hasImage=(Boolean)request.getAttribute("hasImage");
	
%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/crm.css" />
<title><%=title%></title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#ip input").focus(function() {
			$(this).val('');
		});
		
	});
	function identifyingAndRegister() {
		var phoneNo = $("#phoneNo").val();
		var brandId = $("#brandId").val();
		var weixinId = $("#weixinId").val();
		var identifyingCode = $("#identifyingCode").val();
		if (!/^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\d{8}$/.test(phoneNo)) {
			 $("#phoneNo").addClass("bro");
             $(".cuowu1").removeClass("yincang");
			return false;
		}
		$("#phoneNo").removeClass("bro");
        $(".cuowu1").addClass("yincang");
        if (identifyingCode==""||identifyingCode=="验证码") {
			 $("#identifyingCode").addClass("bro");
            $(".cuowu2").removeClass("yincang");
			return false;
		}
		$("#identifyingCode").removeClass("bro");
        $(".cuowu2").addClass("yincang");
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/phonePage/identifyingAndRegister.do',
			cache : false,
			dataType : "text",
			data : "identifyingCode=" + identifyingCode + "&brandId="+brandId+"&weixinId="+weixinId+"&phoneNo="+phoneNo,
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					 $("#identifyingCode").addClass("bro");
		             $(".cuowu2").removeClass("yincang");
				}
			},
			success : function(json) {
					if(json=='1'){
						window.location.reload();
						
					}else if(json=='2'){
						alert("抱歉，微信端暂时无法注册会员，请到门店办理");
					}else {
						 $("#identifyingCode").addClass("bro");
			             $(".cuowu2").removeClass("yincang");
					}
			}
		});
	}
	function sendIdentifyingCode() {
		var phoneNo = $("#phoneNo").val();
		var brandId = $("#brandId").val();
		var weixinId = $("#weixinId").val();
		var title = $("#title").val();
		if (!/^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\d{8}$/.test(phoneNo)) {
			  $("#phoneNo").addClass("bro");
              $(".cuowu1").removeClass("yincang");
			return false;
		}
		$("#phoneNo").removeClass("bro");
        $(".cuowu1").addClass("yincang");
        
        var count = 60;//每间隔六十秒获取验证码
   	    var timeId = "";
		function countSecond(){
			if(count==0){
				window.clearInterval(timeId);
				count=60;
				$('#button2').html('获取验证码');
				document.getElementById('button2').disabled = false;
				$(".cuowu2").addClass("yincang");
				return false;
			}
			$('#button2').html(count+'秒后获取');
			document.getElementById('button2').disabled = true;
			count--;
		}
		timeId =window.setInterval(countSecond,1000);
        
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/phonePage/sendIdentifyingCode.do',
			cache : false,
			dataType : "text",
			data : "phoneNo=" + phoneNo+"&brandId="+brandId+"&weixinId="+weixinId+"&title="+title,
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					 $("#phoneNo").addClass("bro");
		              $(".cuowu1").removeClass("yincang");
				}
			},
			success : function(json) {
				if (json == "1") {
					$("#button2").text("验证码已发放"); 
					$("#button2").attr("disabled",true); 
				} else if (json == "0"){
					 $("#phoneNo").addClass("bro");
		              $(".cuowu1").removeClass("yincang");
				}else if (json == "-1"){
					alert("抱歉，微信端暂时无法注册会员，请到门店办理");
				}

			}
		});
	}

</script>
</head>
<body class="www">
	<div class="w">
		<section class="w-top">
		<div class="top-bg"></div>
		<section class="company-logo">  <%if(hasImage||business.getCompanyWeiboId().equals("1111111111")){%>
        <img src="<%=basePath%>/user-upload-image/${business.brandId}/${business.smallimageName}" width="75" height="75" />
        <%}else{%>
        <img src="http://tp4.sinaimg.cn/<%=business.getCompanyWeiboId() %>/180/1122/9" width="75" height="75" />
        <%} %> </section> <section class="company-name">
		<h1><%=title%></h1>
		</section> 
		<section class="top-menu"> 
			<a href="#" class="sel">会员</a>
			 <a href="<%=path%>/weixin/phonePage/preference.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">优惠</a>
			 <a href="<%=path%>/weixin/newFun/subbranch.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">门店</a>
			<%if (JudgeMenuShow.judgeMenu(brandId)) { %>
				<a href="<%=path%>/weixin/newFun/showStory.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">陪你</a>
			<%} else {%>
				<a href="<%=path%>/weixin/newFun/dishes.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>&r=<%=Math.random()%>">菜品</a>
			<%} %> 
			 <!-- 
			<a href="<%=path%>/weixin/phonePage/subbranch.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">门店</a>
			<a href="<%=path%>/weixin/phonePage/dishes.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">菜品</a>
			--> 
		</section>
		 </section>
		<section class="clear_wrap"></section>
		<section class="w-main"> <section class="zhongxia">
		<form action="" method="post" id="ip">
			<input type="text" name="phoneNo" id="phoneNo" value="&nbsp;手机号 " />
			<input type="text" name="identifyingCode" id="identifyingCode" value="&nbsp;验证码" />
			<input type="hidden" id="brandId" name="brandId" value="<%=brandId.toString()%>">
			<input type="hidden" id="weixinId" name="weixinId" value="<%=weixinId%>">
			<input type="hidden" id="title" name="title" value="<%=title%>">
			<button1 class="jh" onclick="identifyingAndRegister()"></button1>
		</form>
		<button class="jh2" onclick="sendIdentifyingCode()" id="button2">获取验证码</button>
		<img class="cuowu1 yincang" src="<%=basePath%>/images/cuowu1.png">
        <img class="cuowu2 yincang" src="<%=basePath%>/images/cuowu2.png"> </section> </section>
	</div>
</body>
</html>
