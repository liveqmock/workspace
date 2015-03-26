<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.yazuo.weixin.vo.BusinessVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta.jsp"%>
<%@page isELIgnored="false" %>
<%
	BusinessVO business=request.getAttribute("business")!=null ? ((BusinessVO) request.getAttribute("business")) : new BusinessVO();
	Integer brandId=business.getBrandId();
	String title=business.getTitle();
	String weixinId=(String)request.getAttribute("weixinId");
	Boolean hasImage=StringUtils.isNotEmpty(String.valueOf(request.getAttribute("hasImage"))) ? (Boolean)request.getAttribute("hasImage") : false;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0, maximum-scale=1.0"/> 
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/crm.css" />
<title><%=title%></title>
<link href="<%=basePath%>/css/dishes.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var ctx = "<%=basePath%>";
</script>
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
        <a href="<%=path%>/weixin/phonePage/preference.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">优惠</a>
        <a href="<%=path%>/weixin/newFun/subbranch.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>&r=<%=Math.random()%>">门店</a>
		<a href="<%=path%>/weixin/newFun/dishes.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>&r=<%=Math.random()%>" class="sel">菜品</a>
        <!--  
		<a href="<%=path%>/weixin/phonePage/subbranch.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">门店</a>
		<a href="<%=path%>/weixin/phonePage/dishes.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>"class="sel">菜品</a>
		--> 
        </section>
    </section>
<input type="hidden" name="weixinId" id="weixinId" value="${param.weixinId }">
<div class="dishesList" id="JS_dishesList">
	<c:set var="keyCount" value="0"></c:set>
	<c:forEach var="list" items="${dishesList }" varStatus="i">
		<c:if test="${not empty list.key && list.key !='null' && list.key !=''}">
			<c:set var="keyCount" value="${keyCount+1 }"></c:set>
		</c:if>
	</c:forEach>
	<c:forEach var="list" items="${dishesList }" varStatus="i">
		<h3>
			<c:if test="${(empty list.key || list.key =='null') && keyCount > 0}">
				其它
			</c:if>
			<c:if test="${not empty list.key && list.key !='null'}">
				${list.key }	
			</c:if>
		</h3>
		<c:forEach var="t" items="${dishesList[list.key] }">
			 <ul>
		    	<li class="clear" data-dishesId='${t.id }' data-merchantId='${t.merchantId }'>
		    		<a href="#" onclick="showDetail(${t.id},${t.merchantId},'${param.weixinId}');">
			        	<c:if test="${not empty t.dishesPic && t.dishesPic!='' }">
			        		<img src="${t.dishesPic}" class="listAvatar" /> 
			        	</c:if>
			        	<p>
			        	<c:if test="${not empty t.dishesIsNew && t.dishesIsNew == 1 }">
				        	<img src="<%=basePath%>/images/dishe_new.png" height="16" width="16" style="position:relative;top:-1px;"/>
			        	</c:if>
			        	${t.dishesName }
			        		<span>	 
			        			<c:if test="${t.dishesPrice >0 }">
				        			<c:choose>
				        				<c:when test="${t.dishesPriceMember>0 }">
				        					<del>￥${t.dishesPrice }</del>
				        				</c:when>
				        				<c:otherwise>
				        					￥${t.dishesPrice }
				        				</c:otherwise>
				        			</c:choose>
			        			</c:if>
		        				<c:if test="${not empty t.dishesPriceMember && t.dishesPriceMember>0 }">
		        					<em>会员价：<label>￥${t.dishesPriceMember }</label></em>
		        				</c:if>
	        				</span>
			        	</p>
		        	 </a>
		        	<c:if test="${t.pariseStatus ==0 }">
		        		<a href="javascript:;" class="dishesVote"><i class="dishesIco"></i><em>${t.dishesPraise }</em></a>
		        	</c:if>
		        	<c:if test="${t.pariseStatus == 1 }">
		        		<a href="javascript:;" class="dishesVote voted"><i class="dishesIco"></i><em>${t.dishesPraise }</em></a>
		        	</c:if>
		        </li>
		    </ul>
		</c:forEach>
	</c:forEach>
</div>    
</div>
<script language="javascript" src="<%=basePath %>js/dishes.js"></script>
</body>
</html>
