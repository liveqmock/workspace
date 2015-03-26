<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path ;
%>

<html>
<head>

	<meta charset="utf-8">
	<title>后台系统跳转</title>
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
	<meta content="telephone=no" name="format-detection" />
	<meta content="email=no" name="format-detection" />
	
</head>
<body >
	  	<%
	  		String requestPageUrl =(String) request.getAttribute("tourl");
	  		response.sendRedirect(requestPageUrl);
	  	%>
</body>
</html>