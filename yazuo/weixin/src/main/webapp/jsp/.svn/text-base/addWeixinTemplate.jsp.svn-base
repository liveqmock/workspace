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
	 var textArry = $("#addTemplateForm").find("input[type='text']");;
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
	<form action="<%=path%>/weixin/template/addOrUpdateTemplate.do" method="post" id="addTemplateForm">
		<input type="hidden" name="brandId" value="${brandId }"/>
		<table>
			<c:choose>
			<c:when  test="${!empty list}">
				<c:forEach items="${list }" var="item">
				<tr>
					<td>
						${item.name }:</td><td><input type="text" name="temp${item.type }" id="temp${item.type }" value="${item.template_id }" />
					</td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td>
						会员消费通知:</td><td><input type="text" name="temp1" id="temp1" value="" />
					</td>
				</tr>
				<tr>
					<td>
						到期提醒通知:</td><td><input type="text" name="temp2" id="temp2" value="" />
					</td>
				</tr>
				<tr>
					<td>
						优惠券到账通知:</td><td><input type="text" name="temp3" id="temp3" value="" />
					</td>
				<tr>
			</c:otherwise>
			</c:choose>
			<tr>
				<td colspan="2"><input type="submit" value="提交" onclick="return buttonajx();"></td>
			</tr>
		</table>
	</form>
</body>
</html>