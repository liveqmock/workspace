<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.util.JudgeMenuShow"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.*"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!doctype html>
<html class="no-js" lang="">
<head>
<meta charset="utf-8">
<meta name="description" content="">
<meta name="HandheldFriendly" content="True">
<meta name="MobileOptimized" content="320">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="cleartype" content="on">
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/a.min.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/jquery.mobile.icons.min.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/jquery.mobile.structure-1.4.5.min.css" />
<link type="text/css" rel="stylesheet" href="<%=path %>/js/1.8.5/aristo/phone.jquery.ui.all.css" />
<link rel="stylesheet" href="<%=path %>/css/new_member/base.css" />
<link rel="stylesheet" href="<%=path %>/css/new_member/coupon.css" />
<script src="<%=path %>/js/1.8.5/jquery.js"></script>
<script src="<%=path %>/js/jquery.mobile/jquery.mobile-1.4.5.min.js"></script>
<script src="${pageContext.request.contextPath}/js/MapClass.js"></script>
<script src="<%=path%>/js/1.8.5/jquery-ui-1.8.5.min.js"></script>
<script type="text/javascript">
	$(function(){
		var familyArray = new Array();
		<c:forEach var="k" items="${resultList }">
		   var obj=new MapClass();
			obj.put('url','${k.url}');
			obj.put('name','${k.name}');
			obj.put('type','${k.type}');
			familyArray.push(obj);
		</c:forEach>
		if (familyArray.length == 1) {
			window.location.href = familyArray[0].get("url");
		}
	});
</script>

<title>抽奖</title>
</head>
<body>
	<input type="hidden" name="brandId" id="brandId" value="${member.brandId }"/>
	<input type="hidden" name="weixinId" id="weixinId" value="${member.weixinId }"/>
	<input type="hidden" name="title" id="title" value="${title }"/>
	
<c:if test="${not empty resultList && fn:length(resultList)>1 }">
	<div data-role="page" data-theme="a" id="modify_member_div">
			<div data-role="header" data-position="inline">
				<a target="_self" href="<%=path %>/weixin/phonePage/fensiCard.do?brandId=${member.brandId }&weixinId=${member.weixinId}" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left  m-icon-back"></a>
		        <h1>${title }</h1>        
		    </div>
	    
	    
	     <div data-role="content" data-theme="a" class="m-wrapper">
	    	<section class="coupon-wrap">
	        	<ul class="coupon-list">
		        	<c:if test="${not empty resultList && fn:length(resultList)>1 }">
			    		<c:forEach var="r" items="${resultList }" varStatus="i">
			            	<li><span class="coupon-list-num">${i.index+1 }</span>
			            		<a href="${r.url }" target="_self">${r.title }
			            		</a>
			            	</li>
	        			</c:forEach>
		            </c:if>
	            </ul>
	        </section>
	    </div>
	</div>
</c:if>
</body>
</html>
