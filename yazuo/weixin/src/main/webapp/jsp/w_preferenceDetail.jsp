<%@page import="com.yazuo.weixin.util.JudgeMenuShow"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	PreferenceVO preference = (PreferenceVO) (request
			.getAttribute("preference"));
	BusinessVO business=((BusinessVO) request.getAttribute("business"));
	Integer brandId=business.getBrandId();
	String title = business.getTitle();
	String weixinId=(String)request.getAttribute("weixinId");
	Boolean hasImage=(Boolean)request.getAttribute("hasImage");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/crm.css" />
<title><%=title%></title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>

<script>
	
</script>
</head>
<body>
	<div class="w">
		<section class="w-top">
		<div class="top-bg"></div>
		<section class="company-logo">  <%if(hasImage||business.getCompanyWeiboId().equals("1111111111")){%>
        <img src="<%=basePath%>/user-upload-image/${business.brandId}/${business.smallimageName}" width="75" height="75" />
        <%}else{%>
        <img src="http://tp4.sinaimg.cn/<%=business.getCompanyWeiboId() %>/180/1122/9" width="75" height="75" />
        <%} %></section> <section class="company-name">
		<h1><%=title%></h1>
		</section>
		 <section class="top-menu"> 
			<a href="<%=path%>/weixin/phonePage/membershipCard.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">会员</a>
			<a href="<%=path%>/weixin/phonePage/preference.do?brandId=<%=preference.getBrandId()%>&weixinId=<%=weixinId%>"class="sel">优惠</a>
			<a href="<%=path%>/weixin/newFun/subbranch.do?brandId=<%=preference.getBrandId()%>&weixinId=<%=weixinId%>&r=<%=Math.random()%>">门店</a>
			<%if (JudgeMenuShow.judgeMenu(brandId)) { %>
				<a href="<%=path%>/weixin/newFun/showStory.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">陪你</a>
			<%} else {%>
				<a href="<%=path%>/weixin/newFun/dishes.do?brandId=<%=preference.getBrandId()%>&weixinId=<%=weixinId%>&r=<%=Math.random()%>">菜品</a>
			<%} %>
			<!-- 
			<a href="<%=path%>/weixin/phonePage/subbranch.do?brandId=<%=preference.getBrandId()%>&weixinId=<%=weixinId%>">门店</a>
			<a href="<%=path%>/weixin/phonePage/dishes.do?brandId=<%=preference.getBrandId()%>&weixinId=<%=weixinId%>">菜品</a>
			-->
		</section> 
		</section>
		<section class="w-main">
		<div class="w-coupon-detail">
			<h1><%=preference.getTitle()%></h1>
			<p><%=preference.getContent()%></p>
		</div>
		</section>
	</div>
</body>
</html>