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
	/* String title = business.getTitle();
	String weixinId=(String)request.getAttribute("weixinId");
	Boolean hasImage=(Boolean)request.getAttribute("hasImage"); */
	
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
<link rel="stylesheet" href="<%=path %>/css/new_member/coupon.css" />
<script type="text/javascript" src="<%=basePath%>js/jquery/jquery.js"></script>
<script src="<%=path %>/js/jquery.mobile/jquery.mobile-1.4.5.min.js"></script>
<title>${business.title }</title>
</head>
<body>
  <div data-role="page" data-theme="a">
		<div data-role="header" data-position="inline">
			<a target="_self" href="<%=path%>/weixin/phonePage/preference.do?brandId=${business.brandId }&weixinId=${weixinId }" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left m-icon-back"></a>
	        <h1>优惠</h1>        
			<a target="_self" href="<%=path %>/weixin/phonePage/fensiCard.do?brandId=${business.brandId }&weixinId=${weixinId}" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-right m-icon-home"></a>
	    </div>
	    <div data-role="content" data-theme="a" class="m-wrapper">
	    	<section class="coupon-wrap">
	        	<div class="coupon-detail">
	            	<h1>${preference.title }</h1>
	                <p>${preference.content }</p>
	            </div>
	        </section>
	    </div>
  </div>
</body>
</html>