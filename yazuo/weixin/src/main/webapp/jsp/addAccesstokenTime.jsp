<%@page import="com.yazuo.weixin.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<title></title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
 
<script>
 function buttonajx(){
	 var textArry = $("#setAccessTokenForm").find("input[type='text']");;
 	var flag = true;
 	textArry.each( function(){
 		var input=$(this).val();
 		if(input==''){
 			alert("文本框为空");
 			flag = false;
 		}
 	});
 	if(flag){
 		return true;
 	}else{
 		return false;
 	}
 }
</script>
</head>
<body class="easyui-layout" style="text-align: left">
	<form action="<%=path%>/weixin/updateAccessTokenTime.do" method="post" id="setAccessTokenForm">
		<input type="hidden" name="brandId" value="${brandId }"/>
		<table>
			
				<tr>
					<td>
						过期时间设置:</td><td><input type="text" name="expirestime" id="expirestime" value="" />
					</td>
				</tr>
			
			<tr>
				<td colspan="2"><input type="submit" value="提交" onclick="return buttonajx();"></td>
			</tr>
		</table>
	</form>
</body>
</html>