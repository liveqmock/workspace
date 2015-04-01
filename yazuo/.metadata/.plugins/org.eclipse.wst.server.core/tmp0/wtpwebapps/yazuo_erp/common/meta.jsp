<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<title></title>
<!-- 设定页面不缓存 -->
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>

<link href="<%=basePath%>/css/base.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>/css/page.css" rel="stylesheet" type="text/css"/>
<link rel="shortcut icon" href="<%=basePath%>images/yazuo.ico"/>
<script type="text/javascript" src="<%=basePath%>/js/underscore.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/underscore.string.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/uri.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery/jquery-1.10.2.js"></script>
<%-- <script type="text/javascript" src="<%=basePath%>/js/weixin/weixin.js"></script> --%>



