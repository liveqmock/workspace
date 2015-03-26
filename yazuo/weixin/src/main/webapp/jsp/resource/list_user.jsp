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
<title>用户列表</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>

<script>
	function modfiyUser(id) {
		$("#businessModifyDiv").show();
		$("#businessModifyDiv").load("<%=path%>/user/manager/editUser.do?userId="+id);
	}
	
	function addUser () {
		$("#businessModifyDiv").show();
		$("#businessModifyDiv").load("<%=path%>/jsp/resource/add_edit.jsp");
	}
	
	function searchUser() {
		$("#businessModifyDiv").show();
		$("#businessModifyDiv").load("<%=path%>/user/manager/listUser.do?userName="+$("#userName").val()+"&isEnble="+$("#isEnble").val()+"&isSearch=true");
	}
</script>
</head>
<body class="easyui-layout" style="text-align: left">
	<div>
		<div style="float: left;">
			<input type="text" name="userName" id="userName" placeholder="输入姓名" value="${param.userName }"/>
			<select name="isEnble" id="isEnble">
				<option value="">是否有效</option>
				<option value="true"  <c:if test="${not empty param.isEnble && param.isEnble }">selected</c:if>>是</option>
				<option value="false"  <c:if test="${not empty param.isEnble && !param.isEnble }">selected</c:if>>否</option>
			</select>
			<input type="button" name="btn_submit" value="搜索" onclick="searchUser();" />
		</div>
		<div style="float: right;margin-bottom: 10px;">
			<input type="button" value="添加" name="btn_add" id="btn_add" onclick="addUser();">
		</div>
	</div>
	<table border="1" bordercolor="black" style="border:1 solid black;border-collapse: collapse;width: 100%;text-align: center;">
		<tr>
			<td nowrap="nowrap">登录名</td>
			<td nowrap="nowrap">姓名</td>
			<td>是否超级用户</td>
			<td nowrap="nowrap">是否可用</td>
			<td nowrap="nowrap">操作</td>
		</tr>
		<c:forEach var="r" items="${list }">
			<tr>
				<td nowrap="nowrap">${r.loginUser }</td>
				<td nowrap="nowrap">${r.userName }</td>
				<td>${r.supperUser==1 ? "是":"否" }</td>
				<td nowrap="nowrap">${r.isEnable ? "是":"否" }</td>
				<td nowrap="nowrap">
					<a href="javascript:modfiyUser(${r.id })">修改</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>