<%@page import="com.yazuo.weixin.util.JudgeMenuShow"%>
<%@page import="com.yazuo.weixin.util.StringUtil"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%@ include file="/common/meta.jsp"%>
<%@page isELIgnored="false" %>
<%
BusinessVO business= request.getAttribute("business")!=null ? ((BusinessVO) request.getAttribute("business")) : new BusinessVO();
String title=business!=null ? business.getTitle() : "";
String weixinId=(String)request.getAttribute("weixinId");
Integer brandId= Integer.parseInt(request.getParameter("brandId"));
Boolean hasImage= StringUtil.isNullOrEmpty(String.valueOf(request.getAttribute("hasImage"))) ? true : (Boolean)request.getAttribute("hasImage");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
 <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=3fad19499e3dd462f217cdb46b3e0d3c"></script>
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/crm.css" />
<link href="<%=basePath%>css/areaSel.css" rel="stylesheet" type="text/css" />
<title>门店</title>
<style type="text/css">  
    .error-tip {
    	color: #ff0000;
    	font-size: 1.0em;
    }
</style>  
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script language="javascript" src="<%=basePath %>js/areaSel.js"></script>
<script type="text/javascript">
	var ctx = "<%=basePath %>";
	function checkAndCall(phoneNo,id){
		//alert(phoneNo);
		if(phoneNo != null && phoneNo.trim() != ''){
			var phoneNoList = phoneNo.split('/');
			window.location.href="tel:"+phoneNoList[0];
		}else{
			$('#'+(id)+' .error-tip').text("暂无商家电话！");
			return;
		}
	}
	function getLocation(x,y,name,address) {
		var str = "<%=basePath%>jsp/baidumap.jsp?x="+x+"&y="+y+"&name="+name+"&address="+address;	
		window.location.href=str;
	}
</script>
</head>
<body>
<input type="hidden" name="weixinId" id="weixinId" value="<%=weixinId%>">
<input type="hidden" name="brandId" id="brandId" value="<%=brandId%>">
<input type="hidden" name="isLoad" id="isLoad" value="${flag }">
<input type="hidden" name="latitude" id="latitude" value="${param.latitude }">
<input type="hidden" name="longitude" id="longitude" value="${param.longitude }">
<input type="hidden" name="province" id="province" value="${param.province }">
<div class="w">
	<section class="w-top">
    	<div class="top-bg"></div>
        <section class="company-logo">
        	 <%if(hasImage ||(business !=null && business.getCompanyWeiboId().equals("1111111111"))){%>
        <img src="<%=basePath%>/user-upload-image/${business.brandId}/${business.smallimageName}" width="75" height="75" />
        <%}else{%>
        <img src="http://tp4.sinaimg.cn/<%=business.getCompanyWeiboId() %>/180/1122/9" width="75" height="75" />
        <%} %>
        </section>
        <section class="company-name">
        	<h1><%=StringUtil.isNullOrEmpty(title) ? "" : title %></h1>
        </section>
        <section class="top-menu">
	        <a href="<%=path%>/weixin/phonePage/membershipCard.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">会员</a>
	        <a href="<%=path%>/weixin/phonePage/preference.do?brandId=<%=brandId %>&weixinId=<%=weixinId%>">优惠</a>
			<a href="<%=path%>/weixin/newFun/subbranch.do?brandId=<%=brandId %>&weixinId=<%=weixinId%>&r=<%=Math.random()%>"class="sel">门店</a>
			<%if (JudgeMenuShow.judgeMenu(brandId)) { %>
				<a href="<%=path%>/weixin/newFun/showStory.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">陪你</a>
			<%} else {%>
				<a href="<%=path%>/weixin/newFun/dishes.do?brandId=<%=brandId %>&weixinId=<%=weixinId%>&r=<%=Math.random()%>">菜品</a> 
			<%} %>
			<!--  
			<a href="<%=path%>/weixin/phonePage/subbranch.do?brandId=<%=brandId %>&weixinId=<%=weixinId%>"class="sel">门店</a>
			<a href="<%=path%>/weixin/phonePage/dishes.do?brandId=<%=brandId %>&weixinId=<%=weixinId%>">菜品</a>
			--> 
        </section>
        
    </section>
     <!--新增加的 区域选择部分-->
    <div id="JS_areaSelect">
        <div class="searchBox clear_wrap">
        	<h3 class="areaSelTitle" id="JS_showMainAreaBtn" onclick="h3SearchClick(this)">
	        	<i class="areaIco allAreaIco"></i> <span id="span_area">${areaName }</span> 
	        </h3>
            <div class="storeSearchBox">
            	<input type="text" placeHolder="搜索门店" class="storeKeywordsTxt" name="merchantName" id="merchantName" value="${param.merchantName }"/><a href="javascript:searchSubbranch();" class="storeSearchBtn"></a>
            </div>
        </div>  
        
        
        <div class="mainArea clear">
            <ul class="mainAreaList" id="JS_mainAreaList">
                <li><a href="javascript:;">北京市</a><i class="areaIco showPartIco"></i></li>           
            </ul>
            
            <ul class="partAreaList" id="JS_partAreaList">
                <li><a href="javascript:;">海淀区</a></li>            
            </ul>
        </div>
    </div>
    <div id="div_tip"></div>
    <!--新增加的 区域选择部分 end-->
   <section class="w-main">
    	<ul class="w-subb" id="merchantUl">
    	<c:forEach var="m" items="${merchantAllList }" varStatus="i">
    		<c:if test="${m.merchantName !='虚拟门店' }">
	    		<li id="${i.index+1 }">
	            	<h1>${m.merchantName }</h1>
	                <p>地址：${not empty m.address && m.address!='null' ? m.address:""}<br />电话：${not empty m.officePhone && m.officePhone !='null' ? m.officePhone :"" }<span class="error-tip"></span></p>
	                 <div style="padding-top : 10px ; margin  : 0px ; width : 280px ; overflow : hidden ;">
	                <a href="javascript:checkAndCall('${m.officePhone }','${i.index+1 }')" class="yd-btn simple-btn"><i class="icon"></i>&nbsp;预订</a>
	                <a href="http://api.map.baidu.com/marker?location=${m.latitude},${m.longitude}&title=${m.merchantName}&name=${m.merchantName }&content=${m.address }&output=html&src=weiba|weiweb" class="nav-btn simple-btn"><i class="icon"></i>&nbsp;导航</a>
	                <br/>
	                </div> 
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
    </section>
</div>
</body>
</html>