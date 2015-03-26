<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>商户与key关联关系</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>js/jquery/jquery.form.js"></script>
<script>
	function submitPhoto(){
		if($('#key').val()==""){
			alert("key值不能为空");
			return;
		}
		document.addForm.submit();
	}
</script>
</head>
<body>
	<div id="photofile">
	<form id="addForm" name="addForm" action="<%=basePath%>/weixin/brandAndKey/saveBrandKeyRelative.do" method="post">
	<input type="hidden" name="brandId" id="brandId" value="${empty param.brandId ? relative.brandId : param.brandId }">
	<input type="hidden" name="id" id="id" value="${relative.id}">
	<table>
		<tr>
		<td>key值</td>
		<td><input type="text" id="key" name="key" value="${relative.tokenKey }" /></td>
		</tr>
		<tr>
			<td>是否有效：</td>
			<td> 
				<select name="isDelete" id="isDelete">
					<option value="true" <c:if test="${empty relative || relative.status}">selected</c:if>>是</option>
					<option value="false" <c:if test="${not empty relative && !relative.status}">selected</c:if> >否</option>
				</select>
			</td>
		</tr>	
	</table>
	 	<input type="button" value="提交"  onclick= "submitPhoto()"> <input type="button" value="取消"  onclick= "cancel()">
	</form>
	</div>
</body>
</html>