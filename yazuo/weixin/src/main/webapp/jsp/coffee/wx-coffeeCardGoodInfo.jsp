<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String base_Path = request.getScheme() + "://"+ request.getServerName() + path;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0, maximum-scale=1.0"/> 
<title>会员卡详情</title>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/wx_card.css" />
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.mobile/jquery.mobile.js"></script>
</head>

<body>
<div data-role="page">
    <div data-role="header" class="shop-header">
    	<a target="_self" href="<%=base_Path %>/weixin/${brandId}/sellCardIndex.do?weixinId=${openid}&showwxpaytitle=1" data-role="button"><img src="${pageContext.request.contextPath}/imageCoffee/back.png" width="16" />返回</a>
    	<h1>会员卡详情</h1>
    </div>
    
    
    <div data-role="content" class="card-content">
    	<section class="card-pic">
        	<div class="goods-images">
            	<p>
            	 <c:choose>
						<c:when  test="${not empty goodvo.cardPictureUrl}" >
		            		<img src="${goodvo.cardPictureUrl}" width="130" />
                    	</c:when>
                    	<c:otherwise>
                    		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard.png" width="130" />
                    	</c:otherwise>
                    </c:choose>
            		<%-- <c:choose>
						<c:when  test="${goodvo.id eq 88 }" >
                       		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard1.jpg" width="130" />
                       	</c:when>
                       	<c:when test="${goodvo.id eq 90 }" >
                       		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard2.jpg" width="130" />
                       	</c:when>
                       	<c:when test="${goodvo.id eq 89 }" >
                       		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard3.jpg" width="130" />
                       	</c:when>
                       	<c:when test="${goodvo.id eq 87 }" >
                       		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard4.jpg" width="130" />
                       	</c:when>
                       		<c:when test="${goodvo.id eq 91}" >
                       		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard5.jpg" width="130" />
                       	</c:when>
                       	<c:otherwise>
                       		<img src="${pageContext.request.contextPath}/imageCoffee/sellcard.png" width="130" />
                       	</c:otherwise>
                      </c:choose> --%>
	              </p>
            </div>
            <div class="goods-money clear-wrap">
            	<span class="left orange"><i class="money-tag">¥</i>
            	<i class="money-num">
		            ${goodvo.nowPriceCash}
		    	</i>
		    		<c:if test="${goodvo.originalPriceShow eq 1}">
		    		<em class="old-money grey98">${goodvo.originalPrice}</em>
		    		</c:if>
		    	</span> 
                <div class="right">
                	<a target="_blank" href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=${dict.appId }&redirect_uri=<%=base_Path %>/weixin/${brandId }/buyCard.do?goodCode=${goodvo.id}&response_type=code&scope=snsapi_base&state=<%=Math.random()%>#wechat_redirect" class="shop-btn">立即申请</a>
                	<!--  <a target="_self" href="<%=base_Path %>/weixin/${brandId }/buyCard.do?goodCode=${goodvo.id}&response_type=code&scope=snsapi_base&state=${goodvo.id}#wechat_redirect" class="shop-btn">立即申请</a>-->
                </div>
            </div>
        </section>
        <c:if test="${goodvo.productType ne 2}">
	        <section class="card-step">
	        	<h1>申请流程</h1>
	            <p class="text-center"><img src="${pageContext.request.contextPath}/imageCoffee/t03.png" width="290" /></p>
	        </section>
        </c:if>
	    <div class="card-sm">在线申请的会员卡不能退呦~</div>
        <section class="card-message">
        	<h1>权益声明</h1>
            <div class="message-content">
            	<pre style="word-wrap:break-word;">${goodvo.rules}</pre>
            </div>
        </section>
    </div>
</div>

	
<script type="text/javascript">
//当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
document.addEventListener('WeixinJSBridgeReady', function onBridgeReady(){
	WeixinJSBridge.call('showOptionMenu');
	});
	
	var imgsType = '${goodvo.typeId}';
	var imgs ='<%=basePath%>imageCoffee/sellcard.png';
	
	var dataForWeixin={
		       
	        appId:"",
	        MsgImg:imgs,

	        TLImg:imgs,
	   	     
	        url:"<%=basePath%>weixin/${brandId}/cardInfoPage.do?goodCode=${goodvo.id }&showwxpaytitle=1",
	     
	        title:"咖啡陪你在线办卡",
	     
	        desc:"点击查看卡详情，快来抢购吧！",
	     
	        fakeid:"",
	     
	        callback:function(){}
	     
	     };
	</script>
</body>
</html>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/weixin.js"></script>