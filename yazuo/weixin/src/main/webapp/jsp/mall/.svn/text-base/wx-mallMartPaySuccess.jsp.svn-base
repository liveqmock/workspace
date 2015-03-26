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
    	<h1>购买成功</h1>
    </div>
    
    <div data-role="content" class="shop-content">
    	<section class="buy-success">
        	<div class="success-word"><img src="${pageContext.request.contextPath}/imageMall/icon05.png" width="30" /><span> 恭喜您，支付成功！</span></div>
            <ul class="buy-list">
            	<li><span class="gray999">商品名称：</span><span>${order.productInfo }</span></li>
            	<li><span class="gray999">商品数量：</span><span>${order.buyNum }份</span></li>
            	<c:if test="${order.total_fee  gt 0 }">
            		<li><span class="gray999">商品价格：</span><span>${order.total_fee} 元</span></li>
            	</c:if>
            	<c:if test="${order.integral  gt 0 }">
	            	<li><span class="gray999">扣除积分：</span><span>${order.integral }</span></li>
            	</c:if>
            	<li><span class="gray999">订单号：</span><span>${order.out_trade_no}</span></li>
            	<li><span class="gray999">交易时间：</span><span><fmt:formatDate value="${order.timeBegin}" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            	<li><span class="gray999">交易状态：</span><span>支付成功</span></li>
            </ul>
     		<a target="_self" href="${pageContext.request.contextPath}/weixin/phonePage/registerPage.do?brandId=${brandId}&&weixinId=${weixinId}" class="shop-btn">点击查看详情</a>
        </section>
    </div>
    
</div> 
</body>
</html>
