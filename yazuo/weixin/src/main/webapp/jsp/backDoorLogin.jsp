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
	<title>后台系统登陆</title>
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
	<meta content="telephone=no" name="format-detection" />
	<meta content="email=no" name="format-detection" />
	<style type="text/css">
		table {
	  max-width: 50%;
	  border-collapse: collapse;
	  border-spacing: 0;
	  background-color: transparent;
	}
	.table {
	  width: 50%;
	  text-align:center;
	  margin-bottom: 18px;
	}
	.table th,
	.table td {
	  padding: 8px;
	  line-height: 18px;
	  text-align: left;
	  vertical-align: top;
	  border-top: 1px solid #dddddd;
	}
	.table th {
	  font-weight: bold;
	}
	.table-bordered {
	  border: 1px solid #dddddd;
	  border-left: 0;
	  border-collapse: separate;
	  *border-collapse: collapsed;
	  -webkit-border-radius: 4px;
	  -moz-border-radius: 4px;
	  border-radius: 4px;
	}
	.table-bordered th,
	.table-bordered td {
	  border-left: 1px solid #dddddd;
	}
	</style>
</head>
<body >
	<div style="margin-top:30px;width:100%" align="center">
		
 	<span style="color:red" id="errorTip">${error }</span>
		
	</div>
	<div align="center">
		<form id="form1" action="" method="post" name="form1">
		<table class="table table-bordered">
			<tr>
				<td>用户名：</td>
				<td><input type="text" id="username" name="username" /></td>
			</tr>
			<tr>
				<td>密码：</td>
				<td><input type="password" id="password" name="password" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="button" value="登录" onclick="login()"/>
				<input type="reset" value="重置" /></td>
			</tr>
		</table>
		</form>
	</div>

<script src="<%=basePath%>/js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	function login(){
		var name = $("#username").val();
		var pwd = $("#password").val();
		if(name=='' || pwd==''){
			$("#errorTip").html("");
			$("#errorTip").html("请输入用户名或密码再登录！");
			return ;
		}else{
			document.form1.action="<%=basePath%>/login.do";
			document.form1.submit();
		}
	}
</script>
</body>
</html>