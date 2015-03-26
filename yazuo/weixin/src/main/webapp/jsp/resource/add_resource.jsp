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
	
	pageContext.setAttribute("resourceMap", Constants.RESOURCE_MAPS);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置资源</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>

<script>
	function save() {
		var name = $("#name").val();
		var type = $("#type").val();
		var isEnable = $("#isEnable").val();
		var description = $("#description").val();
		var id = $("#id").val();
		if (name==null || name=="") {
			alert("请输入名称");
			return;
		} else if (type==null || type=="") {
			alert("请输入类型");
			return;
		} else if (isEnable==null || isEnable=="") {
			alert("请选择是否有效");
			return;
		}
		var requestData = {};
		requestData.id = id;
		requestData.name = name;
		requestData.type = type;
 		requestData.tempEnable = isEnable.trim();
		requestData.description = description; 
		requestData.resourceSource = $("#resourceSource").val();
		requestData.resourceUrl = $("#resourceUrl").val();
		$.ajax({
			url:"<%=basePath%>/setting/resource/saveOrUpdateResource.do",
			type:"post",
			contentType:"application/json",
			data:JSON.stringify(requestData),
			dataType:"json",
			success:function(data){
				alert(data.message);
				if (data.flag) {
					$("#businessModifyDiv").show();
					$("#businessModifyDiv").load("<%=basePath%>/setting/resource/listResource.do");
				}
			}
		});
	}
</script>
</head>
<body class="easyui-layout" style="text-align: left">
	<table>
		<tr>
			<td>名称:<input type="text" name="name" id="name" value="${resource.name }"></td>
		</tr>
		<tr>
			<td>类型:<input type="text" name="type" id="type" value="${resource.type }"></td>
		</tr>
		<tr>
			<td>是否有效:
				<select name="isEnable" id="isEnable">
					<option value="1" <c:if test="${empty resource || resource.isEnable }">selected</c:if>>是</option>
					<option value="0" <c:if test="${not empty resource && !resource.isEnable }">selected</c:if>>否</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>跳转url:
				<input type="text" name="resourceUrl" id="resourceUrl" value="${resource.resourceUrl }">
			</td>
		</tr>
		<tr>
			<td>来源:
				<select id="resourceSource" name="resourceSource">
					<c:forEach var="r" items="${resourceMap }">
						<option value="${r.value }" <c:if test="${resource.resourceSource==r.value }">selected</c:if>>${r.key }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>描述:
				<textarea rows="5" cols="40" name="description" id="description">${resource.description }</textarea>
			</td>
		</tr>
		<tr>
			<td>
				<input type="hidden" name="id" id="id" value="${resource.id }">
				<input type="button" value="保存" onclick="javascript:save();">
			</td>
		</tr>
	</table>
</body>
</html>