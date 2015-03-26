<%@page import="com.yazuo.weixin.util.JudgeMenuShow"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.yazuo.weixin.vo.*"%>
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
<link rel="stylesheet" href="<%=path %>/css/new_member/menu.css" />
<script type="text/javascript">
	var ctx = "<%=path%>";
</script>
<script type="text/javascript" src="<%=path%>/js/jquery/jquery.js"></script>
<script src="<%=path %>/js/jquery.mobile/jquery.mobile-1.4.5.min.js"></script>
<script src="<%=path %>/js/new_member/menu-show.js"></script>
<title>${business.title }</title>

</head>
<body>
	<div data-role="page" data-theme="a">
		<div data-role="header" data-position="inline">
			<a target="_self" href="<%=path %>/weixin/phonePage/fensiCard.do?brandId=${business.brandId }&weixinId=${weixinId}" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left m-icon-home"></a>
	        <h1>陪你</h1>
	    </div>
	    <div data-role="content" data-theme="a" class="m-wrapper">
    		<c:choose>
	    		<c:when test="${empty story}">
	    			<div class="search-result-error">
	                	<p><img src="../../images/member/icon54.png" /></p>
	                    <p>暂无信息~</p>
	                </div>
	    		</c:when>
	    		<c:otherwise>
	    			<section class="pinpai_msg">
				        <section class="pinpai_logo"><strong>${story.title }</strong></section>
				        <section class="pinpai_text">
				        	${story.content }
				        </section>
				    </section>
	    		</c:otherwise>
    		</c:choose>
		</div>
		 <%@ include file="/jsp/common/common_top.jsp" %>
	</div>
</body>
</html>
