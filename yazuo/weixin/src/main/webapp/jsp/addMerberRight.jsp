<%@page import="com.yazuo.weixin.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Integer id = Integer.parseInt(request.getParameter("businessId"));
	String tempRight = request.getParameter("memberRights");
	String memberRights = StringUtil.isNullOrEmpty(tempRight) ? "" : URLDecoder.decode(tempRight, "UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>

<script>
</script>
</head>
<body class="easyui-layout" style="text-align: left">
	<form action="<%=path%>/weixin/updateMemberRights.do" method="post" encType="multipart/form-data">
		<input type="hidden" name="businessId" id="businessId" value="${param.businessId }">
		<table>
			<tr>
				<td>
					会员权益描述:</td><td><textarea cols="20" rows="5" name="memberRights"><%=memberRights %></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2"><input type="hidden" id="id" name="id" value="<%=id%>"> <input type="submit" value="提交"></td>
			</tr>
		</table>
	</form>
</body>
</html>