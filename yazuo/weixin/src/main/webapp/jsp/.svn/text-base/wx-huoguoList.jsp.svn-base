<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>

	<meta charset="utf-8">
	<title>我的小时代定制套餐</title>
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
	<meta content="telephone=no" name="format-detection" />
	<meta content="email=no" name="format-detection" />
	<link type="text/css" rel="stylesheet" href="<%=basePath%>css/crm-wx.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/packagePayment.css" />

</head>
<body class="whiteBg">
	<header>选择我的小时代定制套餐</header>
	<div class="package-title">  <i class="topLogoPic"></i>
		<h3>每个人都有小时代</h3>
        <p>购买小时代套餐<br/>得小时代限量版明星杯、纪念册</p>
	</div>
	<div class="packageList">
    	<ul>
		<c:forEach items="${hglist}" varStatus="i" var="item">
			
					<c:if test="${item.couponId eq '17364'}">
						<li class="color1">
						<a href="<%=basePath%>weixin/1119/goEachPage.do?cardCode=${item.couponId}&showwxpaytitle=1">
						<img src="<%=basePath %>images/indexType1.png" />
					</c:if>
					<c:if test="${item.couponId eq '17365' }">
						<li class="color2">
						<a href="<%=basePath%>weixin/1119/goEachPage.do?cardCode=${item.couponId}&showwxpaytitle=1">
						<img src="<%=basePath %>images/indexType2.png" />
					</c:if>
					<c:if test="${item.couponId eq '17366'}">
						<li class="color3">
						<a href="<%=basePath%>weixin/1119/goEachPage.do?cardCode=${item.couponId}&showwxpaytitle=1">
						<img src="<%=basePath %>images/indexType3.png" />
					</c:if>
					<c:if test="${item.couponId eq '17367'}">
						<li class="color4">
						<a href="<%=basePath%>weixin/1119/goEachPage.do?cardCode=${item.couponId}&showwxpaytitle=1">
						<img src="<%=basePath %>images/indexType4.png" />
					</c:if>
					<c:if test="${item.couponId eq '18077'}">
						<li class="color1">
						<a href="<%=basePath%>weixin/1119/goEachPage.do?cardCode=${item.couponId}&showwxpaytitle=1">
						<img src="<%=basePath %>images/indexType1.png" />
					</c:if>
					<c:if test="${item.couponId eq '18078' }">
						<li class="color2">
						<a href="<%=basePath%>weixin/1119/goEachPage.do?cardCode=${item.couponId}&showwxpaytitle=1">
						<img src="<%=basePath %>images/indexType2.png" />
					</c:if>
					<c:if test="${item.couponId eq '18079'}">
						<li class="color3">
						<a href="<%=basePath%>weixin/1119/goEachPage.do?cardCode=${item.couponId}&showwxpaytitle=1">
						<img src="<%=basePath %>images/indexType3.png" />
					</c:if>
					<c:if test="${item.couponId eq '18080'}">
						<li class="color4">
						<a href="<%=basePath%>weixin/1119/goEachPage.do?cardCode=${item.couponId}&showwxpaytitle=1">
						<img src="<%=basePath %>images/indexType4.png" />
					</c:if>
				
					<h3>${item.couponName }</h3>
					<c:set var="index" value="${fn:indexOf(item.couponDesc,'|')}"/>
					<p>${fn:substring(item.couponDesc,0,index)}套餐</p>
					
					<div class="box"><p class="boxBg"></p>
						<p class="boxInfo">网络预售价<br />￥${item.couponAmount }</p>
					</div>
					<i class="pIco arrow"></i>
				</a>
				</li>
		</c:forEach>
		</ul>
	</div>
	<div class="package-footer">
		<i class="yazuoLogo"></i>提供技术支持
	</div>
	
	<script type="text/javascript">
	//当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady(){
		WeixinJSBridge.call('showOptionMenu');
		});
	var dataForWeixin={
		       
	        appId:"",
	     
	        MsgImg:'<%=basePath%>images/xinladao.png',

	        TLImg:'<%=basePath%>images/xinladao.png',
	     
	        url:"<%=basePath%>weixin/1119/goRecharge.do?showwxpaytitle=1",
	     
	        title:"我的小时代定制套餐",
	     
	        desc:"每个人都有小时代，快来抢购你的小时代套餐吧！",
	     
	        fakeid:"",
	     
	        callback:function(){}
	     
	     };
	</script>
</body>
</html>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/weixin.js"></script>