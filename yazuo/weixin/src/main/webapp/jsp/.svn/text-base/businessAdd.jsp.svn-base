<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
<title>CRM-OPENAPI</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>

<script>
	function check() {
		var weixinId = document.getElementById("bussiness.weixinId").value;
		var token = document.getElementById("bussiness.token").value;
		var title = document.getElementById("bussiness.title").value;
		var tagName = document.getElementById("bussiness.tagName").value;
		var brandId = document.getElementById("bussiness.brandId").value;
		var companyWeiboId = document
				.getElementById("bussiness.companyWeiboId").value;
		var pic1 = document.getElementById("bussiness.pic1").value;
		var pic2 = document.getElementById("bussiness.pic2").value;
		var birthdayMessage = document
				.getElementById("bussiness.birthdayMessage").value;

		if (weixinId == "" || token == "" || title == "" || tagName == ""
				|| brandId == "" || companyWeiboId == ""
				|| birthdayMessage == "") {
			alert("不能为空");
			return false;
		}
		if (!/^[0-9]*$/.test(brandId)) {
			alert("请输入数字!");
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
<body class="easyui-layout" style="text-align: left">

	<table>
		<tr>
			<td></td>
			<td></td>
		</tr>
	</table>

	<form action="<%=path%>/weixin/saveBusiness.do" method="post" encType="multipart/form-data">
		<table>
			<tr>
				<td>商家微信:<input type="text" name="bussiness.weixinId" id="bussiness.weixinId"></td>
			</tr>
			<tr>
				<td>商家token:<input type="text" name="bussiness.token" id="bussiness.token"></td>
			</tr>
			<tr>
				<td>商家标题:<input type="text" name="bussiness.title" id="bussiness.title"></td>
			</tr>
			<tr>
				<td>商家微信公共账号:<input type="text" name="bussiness.tagName" id="bussiness.tagName"></td>
			</tr>
			<tr>
				<td>品牌ID(brandId)：<input type="text" name="bussiness.brandId" id="bussiness.brandId"></td>
			</tr>
			<tr>
				<td>微薄ID：<input type="text" name="bussiness.companyWeiboId" id="bussiness.companyWeiboId"></td>
			</tr>
			<tr>
				<td>商户状态：<select name="bussiness.voteStatus" id="bussiness.voteStatus">
						<option value="1">老商户（投票并加入会员）</option>
						<option value="2">新商户（只投票）</option>
				</select></td>
			</tr>
			<tr>
				<td>注册时是否发送验证码：<select name="bussiness.isAllowWeixinMember" id="bussiness.isAllowWeixinMember">
						<option value="1">是</option>
						<option value="0">否</option>
				</select></td>
			</tr>
			<tr>
				<td>自定义欢迎语：<input type="text" name="bussiness.salutatory" id="bussiness.salutatory"></td>
			</tr>
			<tr>
				<td>完善生日资料欢迎语：<input type="text" name="bussiness.birthdayMessage" id="bussiness.birthdayMessage" value="登记生日可能有惊喜"></td>
			</tr>
			<tr>
				<td>商家大图：<input type="file" name="bussiness.pic1" id="bussiness.pic1" onChange="clickFileName(this)"></td>
			</tr>
			<tr>
				<td>商家小图：<input type="file" name="bussiness.pic2" id="bussiness.pic2" onChange="clickFileName(this)"></td>
			</tr>
			<tr>
				<td><input type="submit" value="提交" onclick="return check();"></td>
			</tr>
		</table>





	</form>
</body>
</html>