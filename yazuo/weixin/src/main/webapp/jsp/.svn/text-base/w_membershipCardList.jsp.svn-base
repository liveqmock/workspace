<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.yazuo.weixin.util.JudgeMenuShow"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	BusinessVO business = ((BusinessVO) request.getAttribute("business"));
	Integer brandId = business.getBrandId();
	%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/crm.css" />
<script src="${pageContext.request.contextPath}/js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
    function mysubmit(formid){
    	 document.getElementById(formid).submit();
    }
</script>


<title>会员卡列表页</title>
</head>
<body>
<div class="w">
<section class="w-top">
    	<div class="top-bg"></div>
        <section class="company-logo">
        <c:choose>
	        <c:when test="${hasImage ||'1111111111' eq business.companyWeiboId }">
	        	<img src="<%=basePath%>/user-upload-image/${business.brandId}/${business.smallimageName}" width="75" height="75" />
	        </c:when>
	       	<c:otherwise>
	        	<img src="http://tp4.sinaimg.cn/${business.companyWeiboId }/180/1122/9" width="75" height="75" />
	        </c:otherwise>
	    </c:choose>
        </section>
        <section class="company-name">
        	<h1>${business.title }</h1>
        </section>
        <section class="top-menu">
	        <a href="#" class="sel">会员</a> 
	        <a	href="${pageContext.request.contextPath}/weixin/phonePage/preference.do?brandId=${brandId }&weixinId=${weixinId}">优惠</a>
	        <a href="${pageContext.request.contextPath}/weixin/newFun/subbranch.do?brandId=${brandId }&weixinId=${weixinId}">门店</a>
			<%if (JudgeMenuShow.judgeMenu(brandId)) { %>
				<a href="${pageContext.request.contextPath}/weixin/newFun/showStory.do?brandId=${brandId }&weixinId=${weixinId}">陪你</a>
			<%} else {%>
				<a href="${pageContext.request.contextPath}/weixin/newFun/dishes.do?brandId=${brandId }&weixinId=${weixinId}">菜品</a>
			<%} %>
        </section>
    </section>
    <!-- 清除浮动-->
    <section class="clear_wrap"></section>
    <div class="cardCenter">
	<h3>请选择您想查看的会员卡：</h3>
	
	<ul class="hasCardList">
	<c:choose>
	<c:when  test="${!empty cardList}">
	<c:forEach items="${cardList}" varStatus="i" var="item">
		<li>
		<form action="${pageContext.request.contextPath }/weixin/phonePage/membershipCoupon.do" method="post" id="form${i.index }">
			<input type="hidden" name="brandId" value="${brandId }" />
			<input type="hidden" name="cardNo" value="${item.cardNo }" />
			<input type="hidden" name="mobile" value="${member.phoneNo }" />
			<input type="hidden" name="weixinId" value="${weixinId}" />
			<input type="hidden" name="cardType" value="${item.cardtype}" />
			<input type="hidden" name="pwdIf" value="${item.isTradePassword}" />
			<input type="hidden" name="storeBalance" value="${item.storeBalance}" />
			<input type="hidden" name="icon" value="${item.icon}" />
			<input type="hidden" name="integralAvailable" value="${item.integralAvailable}" />
			<a href="javascript:mysubmit('form${i.index}');">
			<c:choose>
				<c:when  test="${!empty item.icon}"><!--卡图片路径 -->
					<img src="${item.icon}" class="cardDefaultBg" />
					<span class="cardType">${item.cardtype}</span>
				</c:when>
				<c:otherwise>
					<img src="${pageContext.request.contextPath }/images/card1.png" class="cardDefaultBg" />
					<c:choose>
			        	<c:when test="${hasImage ||'1111111111' eq business.companyWeiboId }">
			       	 		<img src="<%=basePath%>/user-upload-image/${brandId}/${business.smallimageName}" class="cardDefaultLogo"/>
			        	</c:when>
			       		<c:otherwise>
							<img src="http://tp4.sinaimg.cn/${business.companyWeiboId}/180/1122/9" class="cardDefaultLogo"/>
			            </c:otherwise>
			    	</c:choose>
					<span class="cardType">${item.cardtype}</span>
				</c:otherwise>
			</c:choose>
		</a>
		</form>
			<p class="borderShadow"></p>
		</li>
		
	</c:forEach>
	</c:when>
	<c:otherwise>
		网络出现异常，请稍后重试。
	</c:otherwise>
	</c:choose>
	</ul>
	</div>
	</div> 
</body>
</html>
