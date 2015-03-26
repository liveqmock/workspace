<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	BusinessVO business=(BusinessVO)request.getAttribute("business");
	List<PreferenceVO> preferenceList=business.getPreferenceList();
	List<SubbranchVO> subbranchList=business.getSubbranchList();
	List<DishesVO> dishesList=business.getDishesList();
	Boolean hasSmallImage=(Boolean)request.getAttribute("hasSmallImage");
	Boolean hasBigImage=(Boolean)request.getAttribute("hasBigImage");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片管理</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script>
function check() {
	var pic1 = document.getElementById("bussiness.pic1").value;
	var pic2 = document.getElementById("bussiness.pic2").value;
	if (pic1 == "" && pic2 == "") {
		alert("至少要选择一张图片");
		return false;
	}
	return true;
}
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
</script>
</head>
<body>
	<form action="<%=path%>/weixin/modifyImage.do" method="post"
		encType="multipart/form-data">

		<table>
			<tr>
				<%if(hasBigImage){%>
					<td>
						<c:choose>
							<c:when test="${fn:startsWith(business.bigimageName,'group1/M00/') }">
								<img alt="" src="${pictureUrl }${business.bigimageName}" />
							</c:when>
							<c:otherwise>
								<img alt="" src="<%=basePath%>/user-upload-image/${business.brandId}/${business.bigimageName}">
							</c:otherwise>
						</c:choose>
					</td>
				<%}else{
					out.print("无大图");
				} %>
				
				<%if(hasSmallImage){%>
					<td>
						<c:choose>
							<c:when test="${fn:startsWith(business.smallimageName,'group1/M00/') }">
								<img alt="" src="${pictureUrl }${business.smallimageName}" />
							</c:when>
							<c:otherwise>
								<img alt="" src="<%=basePath%>/user-upload-image/${business.brandId}/${business.smallimageName}">
							</c:otherwise>
						</c:choose>
					</td>
				<%}else{
					out.print("无小图");
				} %>
				
			</tr>
			<tr>

				<tr>
				<td>商家小图：<input type="file" name="bussiness.pic2"
					id="bussiness.pic2" onChange="clickFileName(this)"></td>
			</tr><td>商家大图：<input type="file" name="bussiness.pic1"
					id="bussiness.pic1" onChange="clickFileName(this)"></td>
			</tr>
			<tr>
			<td><input type="hidden" id="bussiness.brandId" name="bussiness.brandId" value="<%=business.getBrandId().toString() %>" >
				</td>
				<td><input type="submit" value="提交" onclick="return check();">
				</td>
			</tr>
		</table>

	</form>



</body>
</html>