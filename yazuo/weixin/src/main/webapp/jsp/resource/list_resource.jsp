<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>CRM-OPENAPI</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>

<script>
	function addResource() {
		$("#businessModifyDiv").show();
		$("#businessModifyDiv").load("<%=path%>/jsp/resource/add_resource.jsp");
	}
	
	function updateResource(id) {
		$("#businessModifyDiv").show();
		$("#businessModifyDiv").load("<%=path%>/setting/resource/editResource.do?id="+id);
	}
</script>
</head>
<body class="easyui-layout" style="text-align: left">
	<div style="float: right;margin:10px;">
				<a href="javascript:addResource();">添加</a>
	</div>
	<table border="1" bordercolor="black" style="border:1 solid black;border-collapse: collapse;width: 100%;text-align: center;">
		<tr>
			<td nowrap="nowrap">名称</td>
			<td nowrap="nowrap">类型</td>
			<td nowrap="nowrap">是否有效</td>
			<td>跳转url</td>
			<td width="30%">描述</td>
			<td nowrap="nowrap">操作</td>
		</tr>
		<c:forEach var="r" items="${list }">
			<tr>
				<td nowrap="nowrap">${r.name }</td>
				<td nowrap="nowrap">${r.type }</td>
				<td nowrap="nowrap">${r.isEnable ? "是":"否" }</td>
				<td>${r.resourceUrl }</td>
				<td>${r.description }</td>
				<td nowrap="nowrap"><a href="javascript:updateResource(${r.id })">修改</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>