<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	String weixinId=(String)request.getAttribute("weixinId");
	String brandId=((Integer)request.getAttribute("brandId")).toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/crm.css" />
<title></title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#test").click(function() {
			alert("");
		});
		if (window.navigator.geolocation) {
	         var options = {
	             enableHighAccuracy: true,
	         };
	         window.navigator.geolocation.getCurrentPosition(handleSuccess, handleError, options);
	     } else {
	         alert("浏览器不支持html5来获取地理位置信息");
	     }
	});
	 
     function handleSuccess(position){
         // 获取到当前位置经纬度  本例中是chrome浏览器取到的是google地图中的经纬度
         var lng = position.coords.longitude;
         var lat = position.coords.latitude;
         window.location='<%=path%>/weixin/phonePage/subbranch.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>&pointX='+lng+'&pointY='+lat;
       }
     
      function handleError(error){
    	  switch(error.code){ 
    	  case error.TIMEOUT : 
    	  alert("连接超时，请重试 "); 
    	  break; 
    	  case error.PERMISSION_DENIED : 
    	  alert("您拒绝了使用位置共享服务，查询已取消 "); 
    	  window.location='<%=path%>/weixin/phonePage/subbranch.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>&pointX=&pointY=';
    	  break; 
    	  case error.POSITION_UNAVAILABLE : 
    	  alert("抱歉，暂时无法为您所在的星球提供位置服务 "); 
    	  break; 
    	  } 
       }
	</script>
</head>
<body>
</body>
</html>