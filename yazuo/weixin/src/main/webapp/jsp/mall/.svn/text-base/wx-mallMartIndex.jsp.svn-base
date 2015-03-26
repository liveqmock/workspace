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

	<meta charset="utf-8">
	<title>${business.title }</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
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

<body class="index-bg">
<div data-role="page">
    <div data-role="header" class="shop-header">
    	<a target="_self" href="${pageContext.request.contextPath}/weixin/${brandId }/orderHistory.do?weixinId=${openid}" data-role="button" class="ui-btn-right" >我的订单</a>
    	<span>粉丝特卖会</span>
    </div>
    <div data-role="content" class="shop-content">
    	<section class="shop-ad">
   			<c:if test="${!empty dict.pagePicurl }" >
   				<c:choose>
	    			<c:when test="${fn:startsWith(dict.pagePicurl, 'M00/')}">
	    				<img src="${newPictureUrl}${dict.pagePicurl}" width="320" />
	    			</c:when>
	    			<c:otherwise>
	    				<img src="${caFileIp}${dict.pagePicurl}" width="320" />
	    			</c:otherwise>
	    		</c:choose>
   			</c:if>
    	</section>
		<c:forEach items="${bigList}" varStatus="i" var="item">
			<section class="shop-item">
			<h1>${item.bigName}</h1>
				<c:forEach items = "${item.subTreeList }" var="subTree" varStatus="j">
						<div class="shop-item-name">${subTree.subName }</div>
							<div class="shop-item-list">
            				<ul>
							<c:forEach items = "${subTree.goodList }" var="goodItem" varStatus="k">
								<li class="clear-wrap">
									<div class="left item-img">
										<c:if test="${!empty goodItem.picurl }">
											<c:choose>
								    			<c:when test="${fn:startsWith(goodItem.picurl, 'M00/')}">
								    				<img src="${newPictureUrl}${goodItem.picurl}" width="100" />
								    			</c:when>
								    			<c:otherwise>
													<img src="${caFileIp}${goodItem.picurl}" width="100" />
								    			</c:otherwise>
								    		</c:choose>
											<c:if test="${goodItem.goodsStatusId!=2 }">
												<p class="soldOutBg"></p>
												<img src="${pageContext.request.contextPath}/images/soldOutIco.png" class="soldOutIco" />	
											</c:if>										
										</c:if>
									</div>
			                        <div class="left item-content">
										<a target="_self"  href="${pageContext.request.contextPath}/weixin/${brandId}/goodInfoPage.do?goodCode=${goodItem.id }&weixinId=${openid}&showwxpaytitle=1">
			                        	<p>
			                        		<span class="item-menu-name">
			                        		${goodItem.name }
				                        		<c:if test="${goodItem.payment == 2 }"> <!-- 积分消费 -->
				                        			<span class="integral-icon">积</span>
										    	</c:if>
									    	</span>
			                        	</p>
			                            <p class="item-money-group">
			                            	<span class="item-money">
			                            	<c:if test="${goodItem.nowPriceCash gt 0}">
			                            		<c:if test="${goodItem.dealsTypeId ne 6}">${goodItem.dealName }</c:if>
			                            		<em class="grey98">￥</em><i class="num-red">${goodItem.nowPriceCash }</i>
			                            	</c:if>
			                            	<em class="grey98">
				                            	<c:if test="${goodItem.payment==3 }">
				                            	+
				                            	</c:if>
				                            	<c:if test="${goodItem.payment!=1 }">
					                            	<c:choose>
						                            	<c:when test="${goodItem.payment==2 }">
						                            		<i class="num-red">${goodItem.nowPriceIntegral}</i>积分
						                            	</c:when>
						                            	<c:otherwise>
						                            	 	${goodItem.nowPriceIntegral}积分
						                            	</c:otherwise>
					                            	</c:choose>
				                            	</c:if>
			                            	 </em>
			                            	<c:if test="${goodItem.originalPriceShow eq 1}">	
			                            		<em class="old-money grey98">${goodItem.originalPrice}</em>
			                            	</c:if>
			                            	</span>
			                            </p>
			                            <p class="item-time grey98">
			                            	<c:if test="${goodItem.expireType == 1 }" ><!-- 固定使用期限 -->
			                            		有效期:<fmt:formatDate value="${goodItem.expireBegin}" pattern="yyyy-MM-dd"/>至
									            <fmt:formatDate value="${goodItem.expireEnd}" pattern="yyyy-MM-dd"/>
									        </c:if>
								        	<c:if test='${goodItem.expireType == 2 }'> <!-- 相对期限 -->
									           	 使用期:自购买之日起第${goodItem.expireDaysBegin}天至第${goodItem.expireDays}天
								        	</c:if>
			                            </p>
										</a>
			                        </div>
								</li>
							</c:forEach>
							</ul>
							</div>
				</c:forEach>
			</section>
		</c:forEach>
		
    </div>
</div> 

<script type="text/javascript">
//当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
document.addEventListener('WeixinJSBridgeReady', function onBridgeReady(){
	WeixinJSBridge.call('showOptionMenu');
	});

	var dataForWeixin={
	        appId:"",
	        MsgImg:'<%=base_Path%>/user-upload-image/${brandId}/${business.smallimageName}',

	        TLImg:'<%=base_Path%>/user-upload-image/${brandId}/${business.smallimageName}',
	        
	        url:"https://open.weixin.qq.com/connect/oauth2/authorize?appid=${dict.appId}&redirect_uri=<%=base_Path%>/weixin/${brandId}/mallMartIndex.do?showwxpaytitle=1&response_type=code&scope=snsapi_base&state=1.01#wechat_redirect",
	     
	        title:"${business.title}",
	     
	        desc:"快来抢购吧！",
	     
	        fakeid:"",
	     
	        callback:function(){}
	     
	     };
	</script>
</body>
</html>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/weixin.js"></script>