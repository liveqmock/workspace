<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	BusinessVO business=((BusinessVO) request.getAttribute("business"));
	List<SubbranchVO> subbranchList = business.getSubbranchList();
	Integer brandId=business.getBrandId();
	String title=business.getTitle();
	String weixinId=(String)request.getAttribute("weixinId");
	Boolean hasImage=(Boolean)request.getAttribute("hasImage");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
 <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=3fad19499e3dd462f217cdb46b3e0d3c"></script>
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/crm.css" />
<title><%=title %></title>
<style type="text/css">  
        .error-tip {
        	color: #ff0000;
        	font-size: 1.0em;
        }
</style>  
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	var ctx = "<%=basePath %>";
	function checkAndCall(phoneNo,id){
		//alert(phoneNo);
		if(phoneNo != null && phoneNo.trim() != ''){
			var phoneNoList = phoneNo.split(',');
			window.location.href="tel:"+phoneNoList[0];
		}else{
			$('#'+(id-1)+' .error-tip').text("暂无商家电话！");
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
        	<h1><%=title %></h1>
        </section>
        <section class="top-menu">
        <a href="<%=path%>/weixin/phonePage/membershipCard.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">会员</a>
        <a href="<%=path%>/weixin/phonePage/preference.do?brandId=<%=brandId %>&weixinId=<%=weixinId%>">优惠</a>
		<a href="<%=path%>/weixin/newFun/subbranch.do?brandId=<%=brandId %>&weixinId=<%=weixinId%>&r=<%=Math.random()%>"class="sel">门店</a>
		<a href="<%=path%>/weixin/newFun/dishes.do?brandId=<%=brandId %>&weixinId=<%=weixinId%>">菜品</a> 
		<!--  
		<a href="<%=path%>/weixin/phonePage/subbranch.do?brandId=<%=brandId %>&weixinId=<%=weixinId%>"class="sel">门店</a>
		<a href="<%=path%>/weixin/phonePage/dishes.do?brandId=<%=brandId %>&weixinId=<%=weixinId%>">菜品</a>
		--> 
        </section>
    </section>
   <section class="w-main">
    	<ul class="w-subb">
    	    <%
				int i = 0;
    	    	int m = 0;
				for (SubbranchVO subbranch : subbranchList) {
					if(!subbranch.getIsDelete()){
			%>
        	<li id="<%=m++ %>">
            	<h1><%=subbranch.getName() %></h1>
                <p><%=subbranch.getAddress() %><br />电话： <%=subbranch.getPhoneNo() %><span class="error-tip"></span></p>
                 <div style="padding-top : 10px ; margin  : 0px ; width : 280px ; overflow : hidden ;">
                <a href="javascript:checkAndCall('<%=subbranch.getPhoneNo() %>','<%=m %>')" class="yd-btn simple-btn"><i class="icon"></i>&nbsp;预订</a>
                <a href="http://api.map.baidu.com/marker?location=<%=subbranch.getPointY() %>,<%=subbranch.getPointX()%>&title=<%=subbranch.getName()%>&name=<%=subbranch.getName()%>&content=<%=subbranch.getAddress()%>&output=html&src=weiba|weiweb" class="nav-btn simple-btn"><i class="icon"></i>&nbsp;导航</a>
                <br/>
                </div> 
            </li>
             <%
				}}
			%>
        </ul>
    </section>
</div>
</body>
</html>