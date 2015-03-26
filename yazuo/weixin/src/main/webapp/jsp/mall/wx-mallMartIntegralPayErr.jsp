<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0, maximum-scale=1.0"/> 
<title>购买成功</title>
	<c:choose>
		<c:when test="${dict.pageColor eq '#FEC500'  or dict.pageColor eq '#fec500'}">
			<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/wx_shop_yellow.css" />
		</c:when>
		<c:when test="${dict.pageColor eq '#56B072'  or dict.pageColor eq '#56b072'}">
			<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/wx_shop_green.css" />
		</c:when>
		<c:otherwise>
			<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/wx_shop_orange.css" />
		</c:otherwise>
	</c:choose>
	<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.mobile/jquery.mobile.js"></script>
</head>

<body>

<div data-role="page">
    <div data-role="header" class="shop-header">
    	<h1>购买失败</h1>
    </div>
    
    <div data-role="content" class="shop-content">
    	<section class="buy-success">
        	<div class="success-word"><img src="${pageContext.request.contextPath}/imageMall/icon08.png" width="33" /> <span>抱歉，支付失败</span></div>
            <div class="fail-word"><p class="gray999">服务器繁忙，请您稍后重试</p></div>
     		<a target="_self" href="${pageContext.request.contextPath}/weixin/${brandId}/mallMartIndex.do?weixinId=${weixinId }&showwxpaytitle=1" class="shop-btn">返回首页</a>
        </section>
    </div>
    
</div>
</body>
</html>
