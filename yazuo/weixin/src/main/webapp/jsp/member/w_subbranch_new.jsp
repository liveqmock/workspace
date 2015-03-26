<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.yazuo.weixin.util.StringUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%@page isELIgnored="false" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	BusinessVO business= request.getAttribute("business")!=null ? ((BusinessVO) request.getAttribute("business")) : new BusinessVO();
	//String title=business!=null ? business.getTitle() : "";
	//String weixinId=(String)request.getAttribute("weixinId");
	Integer brandId= Integer.parseInt(request.getParameter("brandId"));
	//Boolean hasImage= StringUtil.isNullOrEmpty(String.valueOf(request.getAttribute("hasImage"))) ? true : (Boolean)request.getAttribute("hasImage");
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
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=3fad19499e3dd462f217cdb46b3e0d3c"></script>
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/a.min.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/jquery.mobile.icons.min.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/jquery.mobile.structure-1.4.5.min.css" />
<link rel="stylesheet" href="<%=path %>/css/new_member/base.css" />
<link rel="stylesheet" href="<%=path %>/css/new_member/store.css" />
<script type="text/javascript" src="<%=basePath%>js/jquery/jquery.js"></script>
<script src="<%=path %>/js/jquery.mobile/jquery.mobile-1.4.5.min.js"></script>
<script src="<%=path %>/js/new_member/store.js"></script>

<title>${business.title }</title>
<style type="text/css">  
    .error-tip {
    	color: #ff0000;
    	font-size: 1.0em;
    }
</style>  
<script type="text/javascript">
	var ctx = "<%=basePath %>";
</script>
</head>
<body>
<input type="hidden" name="weixinId" id="weixinId" value="${weixinId}">
<input type="hidden" name="brandId" id="brandId" value="${business.brandId }">
<input type="hidden" name="isLoad" id="isLoad" value="${flag }">
<input type="hidden" name="latitude" id="latitude" value="${param.latitude }">
<input type="hidden" name="longitude" id="longitude" value="${param.longitude }">
<input type="hidden" name="province" id="province" value="${param.province }">
<input type="hidden" name="brandName" id="brandName" value="${business.title }">
<div data-role="page" data-theme="a">
	<div data-role="header" data-position="inline">
		<a target="_self" href="<%=path %>/weixin/phonePage/fensiCard.do?brandId=${business.brandId }&weixinId=${weixinId}" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left m-icon-home"></a>
        <h1>门店</h1>
    </div>
    <div  data-role="content" data-theme="a" class="m-wrapper">
    	<section class="store-wrap">
        	<div class="store-search-header clear-wrap" id="JS_areaSelect">
            	<span class="search-area"><span id="span_area"  class="s">${areaName }</span><i></i></span>
                <div class="search-store">
                	<input type="text" name="merchantName" id="merchantName" placeholder="搜索门店"  value="${param.merchantName }"/>
                    <span class="search-btn show-page-loading-msg" onclick="javascript:searchSubbranch();" ><i></i></span>
                </div>
            </div> 
            <div class="area-wrap clear-wrap">
            	<div class="area-sheng">
                	<ul id="JS_mainAreaList">
                    	<li class="sheng-on"><a href="javascript:;">全部省/市/自治区</a><i></i></li>
                    </ul>
                </div>
                <div class="area-qu">
                    <ul  id="JS_partAreaList">
                        <li>全部市（区/县）</li>
                    </ul>
                </div>
            </div>           
             <div class="search-result">
           		  <h1 style="display: none;"></h1> 
		          <ul id="merchantUl">
				   	<c:forEach var="m" items="${merchantAllList }" varStatus="i">
				   		<c:if test="${m.merchantName !='虚拟门店' }">
				    		<li id="${i.index+1 }">
				            	<h2>${m.merchantName }</h2>
				                <p class="store-address">地址：${not empty m.address && m.address!='null' ? m.address:""}</p>
								<p class="store-address"><span style="color:#888;border:none;">电话：${not empty m.officePhone && m.officePhone !='null' ? m.officePhone :"" }</span><span class="error-tip"></span></p>
				                <p class="store-btn">
					                <a href="javascript:checkAndCall('${m.officePhone }','${i.index+1 }')" class="store-phone"><i></i>&nbsp;预订</a>
					                <a href="http://api.map.baidu.com/marker?location=${m.latitude},${m.longitude}&title=${m.merchantName}&name=${m.merchantName }&content=${m.address }&output=html&src=weiba|weiweb" class="store-navigation"><i></i>&nbsp;导航</a>
				                </p>
				            </li>
				           </c:if>
				   	
				   	</c:forEach>
		        </ul>
		        <input type="hidden" name="currentIndex" id="currentIndex" value="${page }"/>
		        <input type="hidden" name="pageTotalCount" id="pageTotalCount" value="${totalPageCount }"/>
		        <input type="hidden" name="areaId" id="areaId" value="${areaId }"/>
		        <c:if test="${not empty merchantAllList && totalPageCount > page }">
				    <a id="loadMoreBnt" href="javascript:loadMoreRecord();" class="viewMoreLinks">加载更多 <i class="viewMoreIco"></i></a>
		        </c:if>
		        <a id="loadMoreBnt" style="display: none" href="javascript:loadMoreRecord();" class="viewMoreLinks">加载更多 <i class="viewMoreIco"></i></a>
           			
           		<c:if test="${empty merchantAllList || fn:length(merchantAllList)==0}">
           			<div class="search-result-error" <c:if test="${empty param.merchantName }">style="display:none;"</c:if>>
                	<p><img src="../../images/member/icon54.png" /></p>
                    <p>没有找到匹配的门店</p>
                    <p>您可以更改其它的搜索关键词试一试</p>
                </div>
		        </c:if>
		    </div>
        </section>
    </div>
     <%@ include file="/jsp/common/common_top.jsp" %>
</div>

</body>
</html>