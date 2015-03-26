<%@page import="com.yazuo.weixin.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<script src="<%=path%>/js/jquery/1.8.3/jquery-1.8.3.min.js"></script>

<script>
	function save() {
		var loginName = $("#loginName").val();
		var userName = $("#userName").val();
		var isEnable = $("#isEnable").val();
		var id = $("#id").val();
		var mobile =  $("#mobile").val();
		var password = $("#password").val();
		if (loginName==null || loginName=="") {
			alert("请输入登录名称");
			return;
		} else if (userName==null || userName=="") {
			alert("请输入用户名");
			return;
		} else if (isEnable==null || isEnable=="") {
			alert("请选择是否有效");
			return;
		} else {
			var vreg = /[^\x00-\x80]/;
			if(vreg.test(loginName)){
				alert("输入的登录名不能为汉字，只能为字母、数字组合");
				return;
			}
			if ((mobile !=null && mobile!="") && !vad_sub(mobile)) { // 验证手机号
				alert("输入的手机有误");
				return;
			}
		}
		var requestData = {};
		requestData.id = id;
		requestData.loginUser = loginName;
		requestData.userName = userName;
		requestData.mobile = mobile;
 		requestData.tempEnable = isEnable.trim();
 		if (password !=null && password!="") {
 			requestData.password = password;
 		}
		$.ajax({
			url:"<%=path%>/user/manager/saveUser.do",
			type:"post",
			data:requestData,
			dataType:"json",
			success:function(data){
				alert(data.message);
				if (data.flag) {
					$("#businessModifyDiv").show();
					$("#businessModifyDiv").load("<%=path%>/user/manager/listUser.do");
				}
			}
		});
	}
	
	function vad_sub(text) {
		var reg = /^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\d{8}$/;// 手机号码验证
		if (!reg.test(text)) {// 为空或错误时提示错误信
			return false;
		} else {
			return true;
		}
	}
</script>
</head>
<body class="easyui-layout" style="text-align: left">
	<table>
		<tr>
			<td><span>登录名:</span>
				<c:choose>
					<c:when test="${empty user }">
						<input type="text" name="loginName" id="loginName" value="${user.loginUser }">	
					</c:when>
					<c:otherwise>
						<input type="hidden" name="loginName" id="loginName" value="${user.loginUser }">
						<span>${user.loginUser }</span>
					</c:otherwise>
				</c:choose>
				<span style="color:red;">(注:登录名创建后不能再次修改,初始密码为6个0)</span>
			</td>
		</tr>
		<tr>
			<td><span>姓名:</span><input type="text" name="userName" id="userName" value="${user.userName }"></td>
		</tr>
		<tr>
			<td><span>手机号:</span><input type="text" name="mobile" id="mobile" value="${user.mobile }"></td>
		</tr>
		<c:if test="${not empty user && not empty user.id }">
			<tr>
				<td><span>密码:</span><input type="password" name="password" id="password"></td>
			</tr>
		</c:if>
		<tr>
			<td><span>是否有效:</span>
				<select name="isEnable" id="isEnable">
					<option value="1" <c:if test="${empty user || user.isEnable }">selected</c:if>>是</option>
					<option value="0" <c:if test="${not empty user && !user.isEnable }">selected</c:if>>否</option>
				</select>
			</td>
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