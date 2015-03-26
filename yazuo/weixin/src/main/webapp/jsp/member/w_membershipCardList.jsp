<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.yazuo.weixin.util.JudgeMenuShow"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<!doctype html>
<html class="no-js" lang="">
<head>
<meta charset="utf-8">
<meta name="description" content="">
<meta name="HandheldFriendly" content="True">
<meta name="MobileOptimized" content="320">
<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no"> 
<meta http-equiv="cleartype" content="on">
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/a.min.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/jquery.mobile.icons.min.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/jquery.mobile.structure-1.4.5.min.css" />
<link rel="stylesheet" href="<%=path %>/css/new_member/base.css" />
<link rel="stylesheet" href="<%=path %>/css/new_member/member.css" />
<script type="text/javascript">
	var ctx = "<%=path%>";
</script>
<script type="text/javascript" src="<%=path%>/js/jquery/jquery.js"></script>
<script src="<%=path %>/js/jquery.mobile/jquery.mobile-1.4.5.min.js"></script>
<script src="<%=path %>/js/new_member/member.js"></script>

<title>${business.title }</title>
</head>
<body>
	<div data-role="page" data-theme="a">
		<div data-role="header" data-position="inline">
			<!-- <a target="_self" href="#" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left m-icon-home"></a> -->
	        <h1>会员中心</h1>
	    </div>
	    <div data-role="content" data-theme="a" class="m-wrapper">
	    	<%@ include file="/jsp/common/member_top.jsp" %>
	    	
	    	<section class="member-card-content">
	        	<h1><span class="tiy">.</span>我的会员卡<span class="tiy">.</span></h1>
	        	<div class="member-card-show">
	                <ul class="clear-wrap">
	                	<c:choose>
							<c:when  test="${!empty cardList}">
							<c:forEach items="${cardList}" varStatus="i" var="item">
								<li <c:if test="${i.index==0}">class='img-big'</c:if>>
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
											<%-- <span class="cardType small">${item.cardtype}</span> --%>
										</c:when>
										<c:otherwise>
											<img src="${pageContext.request.contextPath }/images/card1.png" class="cardDefaultBg" />
											<c:choose>
									        	<c:when test="${hasImage ||'1111111111' eq business.companyWeiboId }">
									        		<c:choose>
														<c:when test="${fn:startsWith(business.smallimageName,'group1/M00/') }">
															<img alt="" src="${pictureUrl }${business.smallimageName}"  class="cardDefaultLogo"/>
														</c:when>
														<c:otherwise>
									       	 				<img src="<%=basePath%>/user-upload-image/${brandId}/${business.smallimageName}" class="cardDefaultLogo"/>
									       	 			</c:otherwise>
									       	 		</c:choose>
									        	</c:when>
								       			<%-- <c:when test="${!hasImage && dataSource == 14 && empty business.id}">
										        	<!-- 从wifi进入的会员中心且在weixin数据库中没有次商户数据 -->
								       				<img src="<%=basePath%>images/member/default_brand_logo.png" class="cardDefaultLogo" />
								       			</c:when> --%>
									       		<c:otherwise>
									       			<img src="<%=basePath%>images/member/default_brand_logo.png" class="cardDefaultLogo" />
													<%-- <img src="http://tp4.sinaimg.cn/${business.companyWeiboId}/180/1122/9" class="cardDefaultLogo"/> --%>
									            </c:otherwise>
									    	</c:choose>
											<span class="cardType small">${item.cardtype}</span>
										</c:otherwise>
									</c:choose>
								</a>
								</form>
									
								</li>
								
							</c:forEach>
							</c:when>
							<c:otherwise>
								没找到相应的会员卡
							</c:otherwise>
						</c:choose>
	                </ul>
	                <span class="click-left"><i></i></span>
					<span class="click-right" style="display:none;"><i></i></span>
	            </div>
	        </section>
        	<%@ include file="/jsp/common/common_top.jsp" %>
	    </div>
    </div>
</body>
</html>
