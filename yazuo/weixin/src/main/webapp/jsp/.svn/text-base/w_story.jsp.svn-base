<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	BusinessVO business = ((BusinessVO) request
	.getAttribute("business"));
	Integer brandId = business.getBrandId();
	String title = business.getTitle();
	String weixinId=(String)request.getAttribute("weixinId");
	Boolean hasImage=(Boolean)request.getAttribute("hasImage");
%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/crm.css" />
<title><%=title%></title>
</head>
<body>
<div class="w">
	<section class="w-top">
    	<div class="top-bg"></div>
        <section class="company-logo">
        	<%if(hasImage||business.getCompanyWeiboId().equals("1111111111")){%>
        <img src="<%=basePath%>/user-upload-image/${business.brandId}/${business.smallimageName}" width="75" height="75" />
        <%}else{%>
        <img src="http://tp4.sinaimg.cn/<%=business.getCompanyWeiboId() %>/180/1122/9" width="75" height="75" />
        <%} %>
        </section>
        <section class="company-name">
        	<h1><%=title%></h1>
        </section>
        <section class="top-menu">
        <a href="<%=path%>/weixin/phonePage/membershipCard.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">会员</a>
        <a	href="<%=path%>/weixin/phonePage/preference.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">优惠</a>
        <a href="<%=path%>/weixin/newFun/subbranch.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">门店</a>
		<a href="<%=path%>/weixin/newFun/dishes.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>&r=<%=Math.random()%>">菜品</a>
		<!-- <a href="<%=path%>/weixin/phonePage/subbranch.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">门店</a>
		<a href="<%=path%>/weixin/phonePage/dishes.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">菜品</a>-->
        </section>
    </section>
    <!-- 清除浮动 -->
    <section class="clear_wrap"></section>
    <!-- 中部 -->
    <section class="pinpai_title">品牌故事</section>
    <!-- 清除浮动 -->
    <section class="clear_wrap"></section>
    <!-- 下部 -->
    <section class="pinpai_msg">
        <section class="pinpai_logo"><img src="<%=basePath%>/images/image0031.png"></section>
        <section class="pinpai_text">2000年5月20日，俏江南第一家品牌餐厅于北京国贸中心开业，由此正式向中式正餐市场进军<br>
        2002年1月16日，俏江南上海时代广场店开业，标志着俏江南跨区域经营的开端<br>
		2007年3月，俏江南正式成为2008年北京奥运会8大竞赛场馆的官方餐饮服务商<br>
		2007年4月，俏江南联手法航，荷航推出全新机上中餐服务<br>
		2007年11月，俏江南被正式认定为“中国驰名商标”，获得了中国唯一在全球范围内得到国际法律保护的商标标识<br>
		2009年4月，俏江南正式签约上海世博会，为参加世博会的中外宾客提供餐饮服务<br>
		2010年7月，俏江南更名为俏江南股份有限公司<br>
		2012年1月，俏江南在台湾开设第一家分店 <br></section>
    </section>


</div>
</body>
</html>
