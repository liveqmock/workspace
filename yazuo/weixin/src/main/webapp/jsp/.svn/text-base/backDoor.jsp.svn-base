<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信后台管理系统</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>

<script>
	$(document).ready(function() {

		$("#businessListDiv").load("<%=path%>/weixin/businessList.do");
	});
</script>
</head>
<body >
	<table>
		<tr>
			<td></td>
			<td></td>
		</tr>
	</table>
	<div id="businessListDiv"  style="height:300px;width:75%;overflow-x: auto; overflow-y: auto; background:#EEEEEE;"></div>
	<br>
	<br>
	<div id="businessModifyDiv" style="height:300px;width:75%;overflow-x: auto; overflow-y: auto; background:#EEEEEE;display:none;"></div>
	<br>
	<br>
	<div id="dishesModifyDiv"></div>
	<br>
	<br>
	<div id="subbranchModifyDiv"></div>
	<br>
	<br>
	<div id="preferenceModifyDiv"></div>

	<div id="showDiv"></div>
</body>
</html>