<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.yazuo.weixin.vo.BusinessVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page isELIgnored="false" %>
<%
	String path = request.getContextPath();
	BusinessVO business=request.getAttribute("business")!=null ? ((BusinessVO) request.getAttribute("business")) : new BusinessVO();
	Integer brandId=business.getBrandId();
	//String title=business.getTitle();
	//String weixinId=(String)request.getAttribute("weixinId");
	//Boolean hasImage=StringUtils.isNotEmpty(String.valueOf(request.getAttribute("hasImage"))) ? (Boolean)request.getAttribute("hasImage") : false;
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
<input type="hidden" name="weixinId" id="weixinId" value="${param.weixinId }">
	<div data-role="page" data-theme="a">
		<div data-role="header" data-position="inline">
			<a target="_self" href="<%=path %>/weixin/phonePage/fensiCard.do?brandId=${business.brandId }&weixinId=${param.weixinId}" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left m-icon-home"></a>
	        <h1>菜品</h1>
	    </div>
	    <div data-role="content" data-theme="a" class="m-wrapper">
	    	<c:choose>
	    		<c:when test="${empty dishesList || fn:length(dishesList)==0 }">
	    			<div class="search-result-error">
	                	<p><img src="../../images/member/icon54.png" /></p>
	                    <p>暂无菜品信息，欢迎到店品尝~</p>
	                </div>
	    		</c:when>
	    		<c:otherwise>
				    <c:set var="keyCount" value="0"></c:set>
					<c:forEach var="list" items="${dishesList }" varStatus="i">
						<c:if test="${not empty list.key && list.key !='null' && list.key !=''}">
							<c:set var="keyCount" value="${keyCount+1 }"></c:set>
						</c:if>
					</c:forEach>
					<c:forEach var="list" items="${dishesList }" varStatus="i">
						<section class="menu-module-content">
				        	<h1>
								<c:if test="${(empty list.key || list.key =='null') && keyCount > 0}">
									其它
								</c:if>
								<c:if test="${not empty list.key && list.key !='null'}">
									${list.key }	
								</c:if>
							</h1>
							<ul>
								<c:forEach var="t" items="${dishesList[list.key] }">
									<li data-dishesId='${t.id }' data-merchantId='${t.merchantId }' data-weixinId='${param.weixinId }'>
							        	<c:choose>
							        		<c:when test="${not empty t.dishesPic && t.dishesPic!='' }">
							        			<img src="${t.dishesPic}" width="90" /> 
							        		</c:when>
							        		<c:otherwise>
							        			<img src="<%=path %>/images/member/009.jpg" width="90" />
							        		</c:otherwise>
							        	</c:choose>
							        	<c:if test="${not empty t.dishesIsNew && t.dishesIsNew == 1 }">
								        	<span class="m-icon-new"></span>
							        	</c:if>
							        	 <span class="menu-name">
								        	${t.dishesName }
							        	 </span>
							        	 <span class="menu-money-content">
						        			<span class="menu-money-def">
							        			<c:if test="${t.dishesPrice >0 }">
								        			<c:choose>
								        				<c:when test="${t.dishesPriceMember>0 }">
								        					<i>￥</i>${t.dishesPrice }
								        				</c:when>
								        				<c:otherwise>
								        					<i>￥</i>${t.dishesPrice }
								        				</c:otherwise>
								        			</c:choose>
							        			</c:if>
						        			</span>
					        				<c:if test="${not empty t.dishesPriceMember && t.dishesPriceMember>0 }">
					        					 <span class="menu-money-member"><span class="word-color-def">会员价：</span><i>￥</i>${t.dishesPriceMember }</span>
					        				</c:if>
					        				<c:set var="choose" value=""></c:set>
							               	<c:if test="${t.pariseStatus eq '1' }">
							               		<c:set var="choose" value="num-on"></c:set>
							               	</c:if>
							               	<span class="menu-good-num ${choose }" ><i></i><em>${t.dishesPraise }</em></span>
							        	</span>
									</li>
					    		</c:forEach>
				    		</ul>
				    	</section>
				    </c:forEach>
				</c:otherwise>
	    	</c:choose>
		</div>
		 <%@ include file="/jsp/common/common_top.jsp" %>
	</div>


</body>
</html>
