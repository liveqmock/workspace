<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>

	<meta charset="utf-8">
	<title>待发货接口</title>
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
	<meta content="telephone=no" name="format-detection" />
	<meta content="email=no" name="format-detection" />
	<link type="text/css" rel="stylesheet" href="<%=basePath%>css/crm-wx.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/packagePayment.css" />

</head>
<body>
	<div class="package-header">待发货总数:<font color="red">${size }</font></div>
	<div class="package-content">
		<c:forEach items="${list}" varStatus="i" var="item">
			<div class="item">
				<div class="text">
					<p>手机号：${item.mobile }&nbsp;&nbsp;&nbsp; 订单号:${item.out_trade_no }&nbsp;&nbsp;
					appid:${item.app_id }</p>
				</div>
			</div>
		</c:forEach>
	</div>
	<div class="package-footer">
		<form action="<%=basePath%>weixin/1119/deliverNotify.do" method="post" id="form1" name="form1">
		<input type="submit" value="一键发货" />
		</form>
	</div>
</body>
</html>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>