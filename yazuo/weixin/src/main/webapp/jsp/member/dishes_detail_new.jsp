<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.yazuo.weixin.vo.BusinessVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page isELIgnored="false" %>
<%
	String path = request.getContextPath();
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
<title>菜品详情</title>
</head>
<body>
<div data-role="page" data-theme="a">
	<div data-role="header" data-position="inline">
		<a target="_self" href="<%=path%>/weixin/newFun/dishes.do?brandId=${param.brandId }&weixinId=${param.weixinId }" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left m-icon-back"></a>
        <h1>菜品</h1>        
		<a target="_self" href="<%=path %>/weixin/phonePage/fensiCard.do?brandId=${param.brandId }&weixinId=${param.weixinId}" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-right m-icon-home"></a>
    </div>
    <div data-role="content" data-theme="a" class="m-wrapper">
    	<section class="menu-detail-wrap" data-dishesId="${dishesDetail.id }" data-merchantId="${dishesDetail.merchantId }">
        	<input type="hidden" name="weixinId" id="weixinId" value="${param.weixinId }">
        	<div class="menu-img">
        		<c:if test="${dishesDetail.dishesIsNew == 1 }">
        			<span class="m-icon-new-big"></span>
        		</c:if>
        		<c:choose>
	        		<c:when test="${not empty dishesDetail.dishesPic && dishesDetail.dishesPic!='' }">
		        		<img src="${dishesDetail.dishesPic }"/>
	        		</c:when>
	        		<c:otherwise>
	        			<img src="<%=path %>/images/member/010.jpg"/>
	        		</c:otherwise>
	        	</c:choose>
        	</div>
            <div class="menu-detail">
            	<div class="menu-detail-name">${dishesDetail.dishesName }</div>
               	<c:set var="choose" value=""></c:set>
               	<c:if test="${dishesDetail.pariseStatus==1 }">
               		<c:set var="choose" value="num-on"></c:set>
               	</c:if>
                <span class="menu-good-num ${choose }"><i></i><em>${dishesDetail.dishesPraise }</em></span>
                <div class="menu-detail-money">
                	<span class="menu-money-def"><i>￥</i>${dishesDetail.dishesPrice }</span>
                	<c:if test="${dishesDetail.dishesPriceMember > 0 }">
                		<span class="menu-money-member"><span class="word-color-def">会员：</span><i>￥</i>${dishesDetail.dishesPriceMember}</span>
                	</c:if>
                </div>
                <div class="menu-detail-word">${dishesDetail.dishesDesc }</div>
            </div>
        </section>
    </div>
   </div>


</body>
</html>
