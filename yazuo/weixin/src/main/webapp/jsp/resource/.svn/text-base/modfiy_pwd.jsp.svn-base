<%@page import="com.yazuo.weixin.util.Constants"%>
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
<title>用户管理</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script>
	function save() {
		var userName = $("#user_name").val();
		var oldPwd = $("#oldPwd").val();
		var newPwd = $("#newPwd").val();
		var confirmPwd = $("#confirmPwd").val();
		var id = $("#id").val();
		if (userName==null || userName=="") {
			alert("请输入姓名");
			return;
		} else if (oldPwd==null || oldPwd=="") {
			alert("请输入原密码");
		}else if (newPwd==null || newPwd=="") {
			alert("请输入新密码");
			return;
		} else if (confirmPwd==null || confirmPwd=="") {
			alert("请输入确认密码");
			return;
		} else {
			if (newPwd != confirmPwd) {
				alert("输入的新密码与确认密码不一致");
				return;
			}
		}
		var requestData = {};
		requestData.id = id;
		requestData.userName = userName;
		requestData.oldPassword = oldPwd;
		requestData.password = newPwd;
		$.ajax({
			url:"<%=basePath%>/user/manager/modfiyPwd.do",
			type:"post",
			data:requestData,
			dataType:"json",
			success:function(data){
				alert(data.message);
				if (data.flag) {
					$("#businessModifyDiv").hide();
					//$("#businessModifyDiv").load("<%=basePath%>/user/manager/listUser.do");
				}
			}
		});
	}
</script>
</head>
<body class="easyui-layout" style="text-align: left">
	<table>
		<tr>
			<td><span>登录名:</span>${user.loginUser }</td>
		</tr>
		<tr>
			<td><span>姓名:</span><input type="text" name="user_name" id="user_name" value="${user.userName }"></td>
		</tr>
		<tr>
			<td><span>原始密码:</span><input type="password" name="oldPwd" id="oldPwd"></td>
		</tr>
		<tr>
			<td><span>新密码:</span><input type="password" name="newPwd" id="newPwd"></td>
		</tr>
		<tr>
			<td><span>确认密码:</span><input type="password" name="confirmPwd" id="confirmPwd"></td>
		</tr>
		<tr>
			<td>
				<input type="hidden" name="id" id="id" value="${user.id }">
				<input type="button" value="保存" onclick="javascript:save();">
			</td>
		</tr>
	</table>
</body>
</html>