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
<title>品牌故事</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>js/jquery/jquery.form.js"></script>
<script>
	function clickFileName(upload_field) {
		var re_text = /\JPG|\jpg|\gif|\PNG/i; //这名比较关键,定义上传的文件类型,允许上传的就写上.
		var filename = upload_field.value; //这个是得到文件域的值
		var newFileName = filename.split('.'); //这是将文件名以点分开,因为后缀肯定是以点什么结尾的.
		newFileName = newFileName[newFileName.length - 1]; //这个是得到文件后缀,因为split后是一个数组所以最后的那个数组就是文件的后缀名.
		/* Checking file type */
		if (newFileName.search(re_text) == -1) { //search如果没有刚返回-1.这个如果newFileName在re_text里没有,则为不允许上传的类型. .
			alert("只支持jpg或者png格式的图片");
			upload_field.form.reset();
			return false;
		}
	}
	function titleLeave(){
		if($('#story_title').val()==""){
			alert("标题不能为空");
		}
	}
	function contentLeave(){
		if($('#story_content').val()==""){
			alert("描述不能为空");
		}
	}
	
	
	function submitPhoto(){
		titleLeave();
		contentLeave();
		$('#uploadfile').submit();
		//var t=setTimeout("alert('成功');$('#businessListDiv').load('<%=path%>/weixin/businessList.do');",1000);
	}
</script>
</head>
<body>
	<div id="photofile">
	<form id="uploadfile" action="<%=path%>/weixin/remoteInterface/weixinSaveStory.do" encType="multipart/form-data" method="post">
	<input type="hidden" name="brandId" id="brandId" value="${empty param.brandId ? story.brandId : param.brandId }">
	<input type="hidden" name="id" id="id" value="${story.id}">
	<table>
		<tr>
			<td>标题</td>
			<td><input type="text" id="story_title" name="title" value="${story.title }"  onblur="titleLeave()"></td>
		</tr>
		<tr>
		<td>描述</td>
		<td><textarea name="content" id="story_content" cols="20" rows="5" onblur="contentLeave">${story.content }</textarea></td>
		</tr>
		<tr>
			<td>图片：</td>
			<td><input type="file" name="image" id="image" onChange="clickFileName(this)"></td>
		</tr> 
		<tr>
			<td>是否删除：</td>
			<td> 
				<select name="isDelete" id="isDelete">
					<option value="0"  <c:if test="${!story.delete}">selected</c:if>>否</option>
					<option value="1" <c:if test="${story.delete}">selected</c:if>>是</option>
				</select>
			</td>
		</tr>	
	</table>
	 	<input type="button" value="提交"  onclick= "submitPhoto()"> <input type="button" value="取消"  onclick= "cancel()">
	</form>
	</div>
</body>
</html>