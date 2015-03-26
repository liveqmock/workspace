<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.*"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	BusinessVO business=(BusinessVO)request.getAttribute("business");
	List<BusinessManagerVO> businessManagerList=business.getBusinessManagerList();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商家管理者</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>js/jquery/jquery.form.js"></script>
<script>
	function addBusinessManager(brandId) {
		$("#businessManagerAddBrandId").val(brandId);
		$("#businessManagerAddDiv").show();
	}

	function addBusinessManagerSubmit(id) {
		var phoneNo = document.getElementById("businessManagerAdd.phoneNo").value;
		if (phoneNo == "") {
			alert("不可为空");
			return false;
		}
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/addBoss.do',
			cache : false,
			data : 'businessManagerAdd.phoneNo=' + $("#businessManagerAdd\\.phoneNo").val()
					+ '&businessManagerAddBrandId=' + $("#businessManagerAddBrandId").val(),
			dataType : "text",
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					alert('错误', '提交失败', 'error');
				}
			},
			success : function(json) {
				if (json == "1") {
					alert('成功');
					$("#businessModifyDiv").load("<%=path%>/weixin/bossList.do?id=<%=business.getId()%>");
				} else {
					alert('手机号已存在');
				}

			}
		});
	}
	function deleteBusinessManager(id) {
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/deleteBoss.do',
			cache : false,
			dataType : "text",
			data : "id=" + id,
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					alert('错误', '提交失败', 'error');
				}
			},
			success : function(json) {
				if (json == "1") {
					alert('成功');
					$("#businessModifyDiv").load("<%=path%>/weixin/bossList.do?id=<%=business.getId()%>");
				} else {
					alert('失败');
				}

			}
		});
	}
</script>
</head>
<body>
	<table title="管理者">
		<tr>
			<td>商家ID（brandId）</td>
			<td>电话号码</td>
			<td>绑定时间</td>
			<td>创建时间</td>
			<td>操作 <a href="javascript:void(0)"
				onclick="addBusinessManager('<%=business.getBrandId().toString()%>')">新增</a></td>
		</tr>
		<%
			for (BusinessManagerVO businessManagerVO : businessManagerList) {
				if(!businessManagerVO.getIsDelete()){
		%>
		<tr>
			<td><%=businessManagerVO.getBrandId()%></td>
			<td><%=businessManagerVO.getPhoneNo()%></td>
			<td><%=businessManagerVO.getLastUpdateTime()%></td>
			<td><%=businessManagerVO.getCreateTime()%></td>
			<td> <a
				href="javascript:void(0)"
				onclick="deleteBusinessManager('<%=businessManagerVO.getId()%>')">删除</a></td>
		</tr>
		<%
				}}
		%>
	</table>
	<div id="businessManagerAddDiv"
		style="position: absolute; top: 300px; right: 10px; display: none;">
		<form action="<%=path%>/weixin/addBoss.do" id="businessManagerAddForm">
			<table>
				<tr>
					<td>手机号码</td>
					<td><input type="text" id="businessManagerAdd.phoneNo"
						name="businessManagerAdd.name"></td>
				</tr>
			</table>
			<input type="hidden" id="businessManagerAddBrandId" name="businessManagerAddBrandId">
			<a href="javascript:void(0)" onclick="addBusinessManagerSubmit()">提交</a>
		</form>
	</div>
</body>
</html>