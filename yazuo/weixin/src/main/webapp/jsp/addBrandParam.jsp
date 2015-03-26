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
 	var textArry = $("#brandParamForm").find("input[type='text']");
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
	<form action="<%=path%>/weixin/addOrUpdateBrandParam.do" method="post" id="brandParamForm">
		<input type="hidden" name="brandId" value="${brandId }"/>
		<table>
			<c:choose>
			<c:when  test="${!empty dict}">
				<tr>
					<td>appId:</td>
					<td><input type="text" name="appId" id="appId" value="${dict.appId }" />
					</td>
				</tr>
				<tr>
					<td>appSecret:</td>
					<td><input type="text" name="appSecret" id="appSecret" value="${dict.appSecret }" style="width:500px"/>
					</td>
				</tr>
				<tr>
					<td>公众号登录名:</td>
					<td><input type="text" name="loginName" id="loginName" value="${dict.loginName }" />
					</td>
				</tr>
				<tr>
					<td>公众号密码:</td>
					<td><input type="text" name="loginPwd" id="loginPwd" value="${dict.loginPwd }" />
					</td>
				</tr>
				<tr>
					<td>是否通过微信认证：</td>
					<td> 
						<select name="isCertification" id="isCertification">
							<option value="0"  <c:if test="${!dict.isCertification}">selected</c:if>>否</option>
							<option value="1" <c:if test="${dict.isCertification}">selected</c:if>>是</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>是否开通微信支付:</td>
					<td> 
						<select name="isOpenPayment" id="isOpenPayment">
							<option value="0"  <c:if test="${!dict.isOpenPayment}">selected</c:if>>否</option>
							<option value="1" <c:if test="${dict.isOpenPayment}">selected</c:if>>是</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>支付版本:</td>
					<td> 
						<select name="v2_v3" id="v2_v3">
							<option value="2" <c:if test="${dict.v2_v3 eq 2}">selected</c:if>>2014-9-10前申请</option>
							<option value="3" <c:if test="${dict.v2_v3 eq 3}">selected</c:if>>2014-9-10后申请</option>
						</select>
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<tr>
					<td>appId:</td>
					<td><input type="text" name="appId" id="appId" value="" />
					</td>
				</tr>
				<tr>
					<td>appSecret:</td>
					<td><input type="text" name="appSecret" id="appSecret" value="" style="width:500px"/>
					</td>
				</tr>
				<tr>
					<td>公众号登录名:</td>
					<td><input type="text" name="loginName" id="loginName"  />
					</td>
				</tr>
				<tr>
					<td>公众号密码:</td>
					<td><input type="text" name="loginPwd" id="loginPwd"  />
					</td>
				</tr>
				<tr>
					<td>是否通过微信认证：</td>
					<td> 
						<select name="isCertification" id="isCertification">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>是否开通微信支付:</td>
					<td> 
						<select name="isOpenPayment" id="isOpenPayment">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>支付版本:</td>
					<td> 
						<select name="v2_v3" id="v2_v3">
							<option value="2" >2014-9-10前申请</option>
							<option value="3" selected>2014-9-10后申请</option>
						</select>
					</td>
				</tr>
			</c:otherwise>
			</c:choose>
			<tr>
				<td colspan="2"><input type="submit" value="提交" onclick="return buttonajx();"></td>
			</tr>
		</table>
	</form>
</body>
</html>