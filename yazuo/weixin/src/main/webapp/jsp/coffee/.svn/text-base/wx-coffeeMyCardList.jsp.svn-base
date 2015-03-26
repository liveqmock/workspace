<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String base_Path = request.getScheme() + "://"+ request.getServerName() + path;
%>
<html>
<head>
<title>我申请的卡</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0, maximum-scale=1.0"/> 
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/wx_card.css" />
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.mobile/jquery.mobile.js"></script>
</head>
<body class="index-bg">
<div data-role="page">
    <div data-role="header" class="shop-header">
    	<a target="_self" href="<%=base_Path %>/weixin/${brandId}/sellCardIndex.do?weixinId=${openid}&showwxpaytitle=1" data-role="button"><img src="${pageContext.request.contextPath}/imageCoffee/back.png" width="16" />返回</a>
    	<h1>我申请的卡</h1>
    </div>
	 <div data-role="content" class="card-content">
	<c:choose>
	<c:when  test="${!empty orderList}">
	<c:forEach items="${orderList}" varStatus="i" var="item">
	<div class="my-card-list">
            <ul class="card-list my-card">
                <li class="clear-wrap">
                    <span class="card-icon icon-arrow-b icon-arrow-t"></span>
                    <div class="card-img">
                     <c:choose>
						<c:when  test="${not empty item.cardPictureUrl}" >
                    		<img src="${item.cardPictureUrl}" width="130" />
                    	</c:when>
                    	<c:otherwise>
                    		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard.png" width="130" />
                    	</c:otherwise>
                    </c:choose>
					<%--  <c:choose>
							<c:when  test="${item.goods_id eq 88 }" >
                        		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard1.jpg" width="130" />
                        	</c:when>
                        	<c:when test="${item.goods_id eq 90 }" >
                        		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard2.jpg" width="130" />
                        	</c:when>
                        	<c:when test="${item.goods_id eq 89 }" >
                        		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard3.jpg" width="130" />
                        	</c:when>
                        	<c:when test="${item.goods_id eq 87 }" >
                        		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard4.jpg" width="130" />
                        	</c:when>
                        	<c:when test="${item.goods_id eq 91}" >
                        		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard5.jpg" width="130" />
                        	</c:when>
                        	<c:otherwise>
                        		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard.png" width="130" />
                        	</c:otherwise>
                        </c:choose> --%>
					</div>
                    <div class="card-word">
                        <p class="card-name">${item.product_info}</p>
                        <p class="card-money">
	                        <em class="grey98">￥</em><i class="num-red">${item.now_price_cash}</i>
	                       	<c:if test="${item.original_price_show eq 1}">
	                        <em class="old-money grey98">${item.original_price }</em>
	                        </c:if>
                        </p>
                    </div>
                    <span class="card-zt grey98">
						${item.order_card_name }
					</span>
                </li>
            </ul>
            <section class="card-order" style="display:none" >
                <div class="order-meg">
                    <h1>订单信息:</h1>
                    <p><span class="gray999 w1">订单号:</span><span>${item.out_trade_no }</span></p>
                    <c:if test="${item.product_type ne 2 }">
                    	<p><span class="gray999 w1">订单状态:</span><span>${item.order_card_name }</span></p>
                    </c:if>
                    <p><span class="gray999 w1">下单时间:</span><span>${fn:substring(item.time_end, 0, 19)}</span></p>
                    <c:if test="${item.deliver_state eq 1 && item.product_type ne 2}">
	                    <p><span class="gray999 w1">快递公司:</span><span>${item.express_company }</span></p>
	                    <p><span class="gray999 w1">快递单号:</span><span>${item.express_no }</span></p>
					</c:if>
                </div>
                <div class="order-meg">
                <c:choose>
                	<c:when test="${item.product_type eq 2 }">
                		<h1>办卡人信息:</h1>
	                    <p><span class="gray999">绑定手机号:</span><span>${item.mobile }</span></p>
                	</c:when>
                	<c:otherwise>
	                    <h1>收货信息:</h1>
	                    <p><span class="gray999">收  货 人:</span><span>${item.receiver }</span></p>
	                    <p class="clear-wrap"><span class="gray999 left customer-tit">详细地址:</span>
	                    <span class="left customer-add">${item.first_addr }&nbsp;${item.second_addr }&nbsp;${item.third_addr }&nbsp;${item.detail_addr }</span></p>
	                    <p><span class="gray999">联系方式:</span><span>${item.mobile}</span></p>
                	</c:otherwise>
                </c:choose>
                </div>
            </section>
        </div>
        </c:forEach>
	</c:when>
	<c:otherwise>
		 <div class="my-card-no">
            <p class="no-word">没有找到对应的申请记录！</p>    
            <p class="gray999">原因可能是:<br/>
            1、您还没有申请过会员卡。<br/>        
            2、当前页面来自于分享链接，为保证个人账户信息安全，无法查看申请记录。您可以通过关注卡属商户微信公众账号，选择在线办卡 - 我申请的卡进行查看。 
			</p>

        </div>
	</c:otherwise>
	</c:choose>
	</div>
	
</div>

	<script type="text/javascript">
	//当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady(){
		WeixinJSBridge.call('hideOptionMenu');
	});
	
	$(function(){
		$(".card-list").click( function(){
			var dis = $(this).siblings(".card-order").css("display");
			if(dis=="none"){
				$(this).children("li").children(".card-icon").addClass("icon-arrow-t");
				$(this).siblings(".card-order").show();
			}else{
				$(this).children("li").children(".card-icon").removeClass("icon-arrow-t");
				$(this).siblings(".card-order").hide();
			}
		});
	});
	</script>
</body>
</html>
