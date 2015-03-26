<%@page import="com.yazuo.weixin.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String id = request.getParameter("id");
	String weixinId = URLDecoder.decode(URLDecoder.decode(request.getParameter("weixinId"), "UTF-8"), "UTF-8");
	String token = URLDecoder.decode(URLDecoder.decode(request.getParameter("token"), "UTF-8"),"UTF-8");
	String title = URLDecoder.decode(URLDecoder.decode(request.getParameter("title"), "UTF-8"),"UTF-8");
	String salutatory = URLDecoder.decode(URLDecoder.decode(request.getParameter("salutatory"), "UTF-8"), "UTF-8");
	String birthdayMessage = URLDecoder.decode(URLDecoder.decode(request.getParameter("birthdayMessage"), "UTF-8"), "UTF-8");
	String brandId = request.getParameter("brandId");
	int voteStatus = new Integer(request.getParameter("voteStatus")).intValue();
	String isAllowWeixinMember = String.valueOf(request.getParameter("isAllowWeixinMember"));  
	String companyWeiboId = request.getParameter("companyWeiboId");
	String tagName = URLDecoder.decode(URLDecoder.decode(request.getParameter("tagName"), "UTF-8"),"UTF-8");
	String tagId = request.getParameter("tagId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>

<script>
	function check() {
		var weixinId = document.getElementById("bussiness.weixinId").value;
		var token = document.getElementById("bussiness.token").value;
		var title = document.getElementById("bussiness.title").value;
		var tagId = document.getElementById("bussiness.tagId").value;
		var tagName = document.getElementById("bussiness.tagName").value;
		var brandId = document.getElementById("bussiness.brandId").value;
		var birthdayMessage = document
				.getElementById("bussiness.birthdayMessage").value;
		var companyWeiboId = document
				.getElementById("bussiness.companyWeiboId").value;
		if (weixinId == "" || token == "" || title == "" || tagName == "" || brandId == ""
				|| companyWeiboId == "" || birthdayMessage == "") {
			alert("不能为空");
			return false;
		}
		if (!/^[0-9]*$/.test(brandId)) {
			alert("品牌ID请输入数字!");
			return false;
		}
		return true;
	}
</script>
</head>
<body class="easyui-layout" style="text-align: left">

	<table>
		<tr>
			<td></td>
			<td></td>
		</tr>
	</table>

	<form action="<%=path%>/weixin/modifyBusiness.do" method="post" encType="multipart/form-data">
		<table>
			<tr>
				<td>商家微信:</td><td><input type="text" name="bussiness.weixinId" value="<%=weixinId%>" id="bussiness.weixinId"></td>
			</tr>
			<tr>
				<td>商家token:</td><td><input type="text" name="bussiness.token" value="<%=token%>" id="bussiness.token"></td>
			</tr>
			<tr>
				<td>商家标题:</td><td><input type="text" name="bussiness.title" value="<%=title%>" id="bussiness.title"></td>
			</tr>
			<tr>
				<td>商家微信公共账号:
				</td><td>
					<input type="text" name="bussiness.tagName" value="<%=tagName%>" id="bussiness.tagName">
					<input type="hidden" name="bussiness.tagId" value="<%=tagId%>" id="bussiness.tagId">
				</td>
			</tr>
			<tr>
				<td>品牌ID(brandId)：</td><td><input type="text" name="bussiness.brandId" value="<%=brandId%>" id="bussiness.brandId"></td>
			</tr>
			<tr>
				<td>微薄ID：</td><td><input type="text" name="bussiness.companyWeiboId" value="<%=companyWeiboId%>" id="bussiness.companyWeiboId"></td>
			</tr>
			<tr>
				<td>自定义欢迎语：</td><td><input type="text" name="bussiness.salutatory" value="<%=salutatory%>" id="bussiness.salutatory"></td>
			</tr>
			<tr>
				<td>完善生日资料欢迎语：</td><td><input type="text" name="bussiness.birthdayMessage" value="<%=birthdayMessage%>" id="bussiness.birthdayMessage"></td>
			</tr>
			<tr>
				<td>商户状态</td>
				<td><select name="bussiness.voteStatus" id="bussiness.voteStatus">
						<%
							if (voteStatus == 1) {
						%>
						<option value="2">新商户（只投票）</option>
						<option value="1" selected="selected">老商户（投票并加入会员）</option>
						<%
							} else {
						%>
						<option value="2" selected="selected">新商户（只投票）</option>
						<option value="1">老商户（投票并加入会员）</option>
						<%
							}
						%>
				</select></td>
			</tr>
			<tr>
				<td>注册时是否发送验证码：</td>
				<td><select name="bussiness.isAllowWeixinMember" id="bussiness.isAllowWeixinMember">
						<%
							if ("true".equals(isAllowWeixinMember)) { 
						%>
						<option value="1"  selected="selected">是</option>
						<option value="0">否</option>
						<%
							} else if("false".equals(isAllowWeixinMember)){
						%>
						<option value="1">是</option>
						<option value="0" selected="selected">否</option>
						<%
							}
						%>
				</select></td>
			</tr>
			
			<tr>
				<td colspan="2"><input type="hidden" id="id" name="id" value="<%=id%>"> <input type="submit" value="提交" onclick="return check();"></td>
			</tr>
		</table>
	</form>
</body>
</html>