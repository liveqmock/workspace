<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<title>微信商城</title>
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
    	<a target="_self" href="${pageContext.request.contextPath}/weixin/${brandId}/mallMartIndex.do?weixinId=${weixinId }&showwxpaytitle=1" data-role="button"><img src="${pageContext.request.contextPath}/imageMall/back.png" width="16" />返回</a>
    	<h1>商品详情</h1>
    </div>
    <div data-role="content" class="shop-content">
    	<section class="goods-pic">
        	<div class="goods-images">
            	<p>
            	<c:if test="${!empty goodvo.picurl }">
					<c:choose>
		    			<c:when test="${fn:startsWith(goodvo.picurl, 'M00/')}">
							<img src="${newPictureUrl}${goodvo.picurl}" width="290" />
							<c:set var="sharePic" value="${newPictureUrl}${goodvo.picurl}"></c:set>
		    			</c:when>
		    			<c:otherwise>
							<img src="${caFileIp}${goodvo.picurl}" width="290" />
							<c:set var="sharePic" value="${caFileIp}${goodvo.picurl}"></c:set>
		    			</c:otherwise>
		    		</c:choose>
				</c:if>
            	</p>
	               <p class="clear-wrap goods-time">
	                <c:if test="${goodvo.expireType == 1 }" ><!-- 固定使用期限 -->
			            <span class="left">
			           	 有效期:<fmt:formatDate value="${goodvo.expireBegin}" pattern="yyyy-MM-dd"/>至<fmt:formatDate value="${goodvo.expireEnd}" pattern="yyyy-MM-dd"/>
			            </span>
			        </c:if>
		        	<c:if test='${goodvo.expireType == 2 }'> <!-- 相对期限 -->
			           <span class="left">使用期:自购买之日起第${goodvo.expireDaysBegin}天至第${goodvo.expireDays}天</span>
		        	</c:if>
	        	<span class="right">商品剩余：${goodvo.remainNum }份</span></p>
            </div>
            <div class="goods-money clear-wrap">
            	<span class="left orange">
            	<c:if test="${goodvo.payment !=2 }"> <!-- 现金消费 -->
	            	<i class="money-tag">¥</i>
		            <i class="money-num">
		             ${goodvo.nowPriceCash} 
		    		</i>
	    		</c:if>
	    		<c:if test="${goodvo.payment ==2 }"> <!-- 积分消费 -->
	            	<em class="money-num">积分:<i>${goodvo.nowPriceIntegral}</i></em>
	            </c:if>
	    		<c:if test="${goodvo.payment==3 }">
	            	<img src="${pageContext.request.contextPath}/imageMall/icon06.png" width="8" />
	            	<em class="ing-num">积分兑换:<i>${goodvo.nowPriceIntegral}</i></em>
	            </c:if>
            	</span>
                <div class="right">
	                <c:if test="${goodvo.goodsStatusId==2 }">
	                 <a target="_self" href="${pageContext.request.contextPath}/weixin/${brandId }/buyGood.do?goodCode=${goodvo.id}&weixinId=${weixinId }&showwxpaytitle=1" class="shop-btn">立即购买</a>
	                </c:if>
	                <c:if test="${goodvo.goodsStatusId !=2 }">
	                 <a target="_self" href="javascript:void(0);" class="gray-shop-btn">已售罄</a>
	                </c:if>
                 </div>
            </div>
        </section>
        <section class="goods-message">
        	<h1>${goodvo.name}</h1>
            <div class="message-content">
            <span style="color:#0B0B0B">【商品介绍】</span>
             	<pre style="word-wrap:break-word;white-space:normal;">${goodvo.intro}</pre>
            </div>
            <div class="message-content">
           <span style="color:#0B0B0B">【 商品规则】</span>
             	<pre style="word-wrap:break-word;white-space:normal;">${goodvo.rules}</pre>
            </div>
            <p class="orange">
            <c:if test="${goodvo.buyLimitIs ==1 }">
            	<c:if test="${goodvo.buyLimitType ==1 }">
            	 每天可购买${goodvo.buyLimitNum}个
            	</c:if>
            	<c:if test="${goodvo.buyLimitType !=1 }">
            	累计可购买${goodvo.buyLimitNum}个
            	</c:if>
            </c:if>
            <c:if test="${goodvo.buyLimitIs !=1 }">
            	不限购
            </c:if>
            </p>
        </section>
    </div>
</div>

	
<script type="text/javascript">
//当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
document.addEventListener('WeixinJSBridgeReady', function onBridgeReady(){
	WeixinJSBridge.call('showOptionMenu');
	});
	var dataForWeixin={
		       
	        appId:"",
	        MsgImg:'${sharePic}',

	        TLImg:'${sharePic}',
	        
	        url:"https://open.weixin.qq.com/connect/oauth2/authorize?appid=${dict.appId}&redirect_uri=<%=base_Path%>/weixin/${brandId}/goodInfoPage.do?goodCode=${goodvo.id }&showwxpaytitle=1&response_type=code&scope=snsapi_base&state=111#wechat_redirect",
	     
	        title:"${goodvo.name}",
	     
	        desc:"${goodvo.name}，快来抢购吧！",
	     
	        fakeid:"",
	     
	        callback:function(){}
	     
	     };
	</script>
</body>
</html>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/weixin.js"></script>