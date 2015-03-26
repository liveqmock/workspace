<%@page import="com.yazuo.weixin.util.JudgeMenuShow"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	BusinessVO business=((BusinessVO) request.getAttribute("business"));
	List<PreferenceVO> preferenceList = business.getPreferenceList();
	Integer brandId=business.getBrandId();
	//String title=business.getTitle();
	//String weixinId=(String)request.getAttribute("weixinId");
	//Boolean hasImage=(Boolean)request.getAttribute("hasImage");
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
<script type="text/javascript">
	$(function(){
		// 进入优惠页面，选中优惠导航，其他导航不选中
		$(".footer-on").removeClass("footer-on");
		$(".footer-icon-coupon").parent().parent().parent().addClass("footer-on");
	});
</script>
</head>
<body>


	<div data-role="page" data-theme="a">
		<div data-role="header" data-position="inline">
			<a target="_self" href="<%=path %>/weixin/phonePage/fensiCard.do?brandId=${business.brandId }&weixinId=${weixinId}" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left m-icon-home"></a>
	        <h1>优惠</h1>        
	    </div>
	    <div data-role="content" data-theme="a" class="m-wrapper">
	   		 <c:choose>
	    		<c:when test="${empty business.preferenceList || fn:length(business.preferenceList)==0 }">
	    			<div class="search-result-error">
	                	<p><img src="../../images/member/icon54.png" /></p>
	                    <p>暂无优惠信息，要密切关注呦~</p>
	                </div>
	    		</c:when>
	    		<c:otherwise>
			    	<section class="coupon-wrap">
			        	<ul class="coupon-list">
			        		<c:forEach var="p" items="${business.preferenceList }" varStatus="i">
				            	<li><span class="coupon-list-num">${i.index+1 }</span>
				            		<a href="<%=path%>/weixin/phonePage/preferenceDetail.do?id=${p.id }&weixinId=${weixinId}" target="_self">${p.title }
				            		<c:if test="${p.isNew }"><i></i></c:if>
				            		</a>
				            	</li>
			        		</c:forEach>
			            </ul>
			        </section>
	        </c:otherwise>
	      </c:choose>
	    </div>
	     <%@ include file="/jsp/common/common_top.jsp" %>
    </div>
</body>
</html>