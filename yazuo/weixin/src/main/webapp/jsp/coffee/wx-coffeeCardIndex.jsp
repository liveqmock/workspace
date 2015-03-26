<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<title>申请会员卡</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/wx_card.css" />
	<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.mobile/jquery.mobile.js"></script>
</head>

<body>

<div data-role="page">
    <div data-role="header" class="shop-header">
    	<a target="_self" href="${pageContext.request.contextPath}/weixin/${brandId }/myCardList.do?weixinId=${openid}&showwxpaytitle=1" data-role="button" class="ui-btn-right" >我申请的卡</a>
    	<h1>申请会员卡</h1>
    </div>
    
    <div data-role="content" class="card-content">
    	<section class="card-list">
            <h1>请选择您要申请的会员卡：</h1>
            <ul>
            <c:forEach items = "${mallGoods }" var="goodItem" varStatus="k">
                <a target="_self"  href="${pageContext.request.contextPath}/weixin/${brandId}/cardInfoPage.do?goodCode=${goodItem.id }&showwxpaytitle=1">
                    <li class="clear-wrap">
                        <span class="icon-arrow-r"></span>
                        <div class="card-img">
                        
                        <c:choose>
							<c:when  test="${not empty goodItem.cardPictureUrl}" >
	                        	<img src="${goodItem.cardPictureUrl}" width="130" />
	                    	</c:when>
	                    	<c:otherwise>
	                    		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard.png" width="130" />
	                    	</c:otherwise>
	                    </c:choose>
	                       <%--  <c:choose>
								<c:when  test="${goodItem.id eq 88 }" >
	                        		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard1.jpg" width="130" />
	                        	</c:when>
	                        	<c:when test="${goodItem.id eq 90 }" >
	                        		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard2.jpg" width="130" />
	                        	</c:when>
	                        	<c:when test="${goodItem.id eq 89 }" >
	                        		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard3.jpg" width="130" />
	                        	</c:when>
	                        	<c:when test="${goodItem.id eq 87 }" >
	                        		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard4.jpg" width="130" />
	                        	</c:when>
	                        	<c:when test="${goodItem.id eq 91}" >
	                        		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard5.jpg" width="130" />
	                        	</c:when>
	                        	<c:otherwise>
	                        		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard.png" width="130" />
	                        	</c:otherwise>
	                        </c:choose> --%>
                        </div>
                        <div class="card-word">
                            <p class="card-name">${goodItem.name }</p>
                            <p class="card-money">
								<span class="item-money">
	                            	<em class="grey98">￥</em><i class="num-red">${goodItem.nowPriceCash }</i>
	                            	<c:if test="${goodItem.originalPriceShow eq 1}">	
	                            		<em class="old-money grey98">${goodItem.originalPrice}</em>
	                            	</c:if>
	                            </span>
							</p>
                        </div>
                    </li>
                </a>
            </c:forEach>
            </ul>
        </section>
    </div>
    
    
</div> 

<script type="text/javascript">
	//当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
		document.addEventListener('WeixinJSBridgeReady', function onBridgeReady(){
		WeixinJSBridge.call('hideOptionMenu');
	});
</script>
</body>
</html>