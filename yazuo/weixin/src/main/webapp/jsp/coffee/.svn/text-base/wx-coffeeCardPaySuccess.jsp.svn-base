<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String base_Path = request.getScheme() + "://"+ request.getServerName() + path;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0, maximum-scale=1.0"/> 
<title>购买成功</title>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/wx_card.css" />
	<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.mobile/jquery.mobile.js"></script>
</head>
<style>
.w1{width:56px;}
.w2{width:80%;word-wrap:break-word;}
</style>
<body>

<div data-role="page">
    <div data-role="header" class="shop-header">
    	<h1>申请会员卡成功</h1>
    </div>
    
    <div data-role="content" class="shop-content">
    	<section class="buy-success">
        	<div class="success-word"><img src="${pageContext.request.contextPath}/imageCoffee/icon05.png" width="30" />
        		<c:if test="${order.productType eq 2 }">
        			<span> 恭喜您，办卡成功！</span>
        		</c:if>
        		<c:if test="${order.productType ne 2 }">
        			<span> 恭喜您，支付成功！</span>
        		</c:if>
        	</div>
            <ul class="buy-list">
            	<li class="clear-wrap"><span class="gray999 left">卡名称:</span><span class="left">${order.productInfo }</span></li>
            	<c:if test="${order.productType eq 2 }">
            		<li class="clear-wrap"><span class="gray999 left">卡号:</span><span class="left">${order.xnCardNo }</span></li>
            	</c:if>
            	<li class="clear-wrap"><span class="gray999 left">购买数量:</span><span class="left">${order.buyNum }份</span></li>
            	<li class="clear-wrap"><span class="gray999 left">商品总价:</span><span class="left">${order.total_fee }元</span></li>
            	<li class="clear-wrap"><span class="gray999 left w1">订单号:</span><span class="left w2">${order.out_trade_no}</span></li>
            	<li class="clear-wrap"><span class="gray999 left">交易时间:</span><span class="left">${fn:substring(order.time_end, 0, 19)}</span></li>
            	<c:if test="${order.productType ne 2 }">
            		<li><span class="w1">会员卡很快会为您寄出，收到后请关注"<span class="shop-name">caffebene中国</span>"微信公众账号。绑定会员卡后即可使用。</span></li>
            	</c:if>
            	<li class="text-center">
                	<p><img src="${pageContext.request.contextPath}/imageCoffee/t.jpg" width="258" /></p>
                    <p class="orange marT10">扫描二维码关注"caffebene中国"公众号！</p>
                </li>
            </ul>
        </section>
    </div>
    
</div> 
</body>
</html>
