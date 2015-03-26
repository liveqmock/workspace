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
	List<PreferenceVO> preferenceList=business.getPreferenceList();
	List<SubbranchVO> subbranchList=business.getSubbranchList();
	List<DishesVO> dishesList=business.getDishesList();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分店管理</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>js/jquery/jquery.form.js"></script>
<script>
	function modifySubbranch(id,name,address,phoneNo,pointX,pointY,isNew,isRecommend,orderId) {
		$("#subbranchEdit\\.name").val(name);
		$("#subbranchEdit\\.address").val(address);
		$("#subbranchEdit\\.phoneNo").val(phoneNo);
		$("#subbranchEdit\\.pointX").val(pointX);
		$("#subbranchEdit\\.pointY").val(pointY);
		$("#subbranchEdit\\.isNew").val(isNew);
		$("#subbranchEdit\\.isRecommend").val(isRecommend);
		$("#subbranchEdit\\.orderId").val(orderId);
		$("#subbranchEditid").val(id);
		$("#subbranchAddDiv").hide();
		$("#subbranchEditDiv").show();
	}
	function addSubbranch(brandId,companyWeiboId ) {
		$("#subbranchAddBrandId").val(brandId);
		$("#subbranchAddCompanyWeiboId").val(companyWeiboId);
		$("#subbranchEditDiv").hide();
		$("#subbranchAddDiv").show();
	}
	function modifySubbranchSubmit(id) {
		var name = document.getElementById("subbranchEdit.name").value;
		var address = document.getElementById("subbranchEdit.address").value;
		var phoneNo = document.getElementById("subbranchEdit.phoneNo").value;
		var pointX = document.getElementById("subbranchEdit.pointX").value;
		var pointY = document.getElementById("subbranchEdit.pointY").value;
		var orderId = document.getElementById("subbranchEdit.orderId").value;
		if (name == "" || address == "" || phoneNo == ""
				|| pointX == "" || pointY == "") {
			alert("不可为空");
			return false;
		}
		if (!/^[0-9]*$/.test(orderId)) {
			alert("请输入数字!");
			return false;
		}
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/modifySubbranch.do',
			cache : false,
			dataType : "text",
			data : 'subbranchEdit.name=' + $("#subbranchEdit\\.name").val()
			+ '&subbranchEdit.address='
			+ $("#subbranchEdit\\.address").val() + '&subbranchEdit.phoneNo='
			+ $("#subbranchEdit\\.phoneNo").val() + '&subbranchEdit.pointX='
			+ $("#subbranchEdit\\.pointX").val() + '&subbranchEdit.pointY='
			+ $("#subbranchEdit\\.pointY").val() + '&subbranchEdit.isNew='
			+ $("#subbranchEdit\\.isNew").val() + '&subbranchEdit.isRecommend='
			+ $("#subbranchEdit\\.isRecommend").val()
			+ '&subbranchEditid='
			+ $("#subbranchEditid").val()+'&subbranchEdit.orderId='+$("#subbranchEdit\\.orderId").val(),
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					alert('错误', '提交失败', 'error');
				}
			},
			success : function(json) {
				if (json == "1") {
					alert('成功');
					$("#businessModifyDiv").load("<%=path%>/weixin/subbranchList.do?id=<%=business.getId()%>");
				} else {
					alert('失败');
				}

			}
		});
	}
	
	function addSubbranchSubmit(id) {
		var name = document.getElementById("subbranchAdd.name").value;
		var address = document.getElementById("subbranchAdd.address").value;
		var phoneNo = document.getElementById("subbranchAdd.phoneNo").value;
		var pointX = document.getElementById("subbranchAdd.pointX").value;
		var pointY = document.getElementById("subbranchAdd.pointY").value;
		if (name == "" || address == "" || phoneNo == ""
				|| pointX == "" || pointY == "") {
			alert("不可为空");
			return false;
		}
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/addSubbranch.do',
			cache : false,
			dataType : "text",
			data : 'subbranchAdd.name=' + $("#subbranchAdd\\.name").val()
			+ '&subbranchAdd.address='
			+ $("#subbranchAdd\\.address").val() + '&subbranchAdd.phoneNo='
			+ $("#subbranchAdd\\.phoneNo").val() + '&subbranchAdd.pointX='
			+ $("#subbranchAdd\\.pointX").val() + '&subbranchAdd.pointY='
			+ $("#subbranchAdd\\.pointY").val() + '&subbranchAdd.isNew='
			+ $("#subbranchAdd\\.isNew").val() + '&subbranchAdd.isRecommend='
			+ $("#subbranchAdd\\.isRecommend").val()
			+ '&subbranchAddCompanyWeiboId='
			+ $("#subbranchAddCompanyWeiboId").val()
			+ '&subbranchAddBrandId=' + $("#subbranchAddBrandId").val(),
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					alert('错误', '提交失败', 'error');
				}
			},
			success : function(json) {
				if (json == "1") {
					alert('成功');
					$("#businessModifyDiv").load("<%=path%>/weixin/subbranchList.do?id=<%=business.getId()%>");
				} else {
					alert('失败');
				}

			}
		});
	}
	function deleteSubbranch(id) {
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/deleteSubbranch.do',
			cache : false,
			dataType : "text",
			data : "id="+id,
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					alert('错误', '提交失败', 'error');
				}
			},
			success : function(json) {
				if (json == "1") {
					alert('成功');
					$("#businessModifyDiv").load("<%=path%>/weixin/subbranchList.do?id=<%=business.getId()%>");
				} else {
					alert('失败');
				}

			}
		});
	}
	
</script>
</head>
<body>
	<table title="分店">
		<tr>
			<td>商家ID（brandId）</td>
			<td>名称</td>
			<td>地址</td>
			<td>电话</td>
			<td>是否为新店</td>
			<td>是否为推荐店面</td>
			<td>排序</td>
			<td>操作 <a href="javascript:void(0)"
				onclick="addSubbranch('<%=business.getBrandId().toString()%>','<%=business.getCompanyWeiboId()%>')">新增分店</a></td>
		</tr>
		<%
			for (SubbranchVO subbranch : subbranchList) {
				if(!subbranch.getIsDelete()){
		%>
		<tr>
			<td><%=subbranch.getBrandId()%></td>
			<td><%=subbranch.getName()%></td>
			<td><%=subbranch.getAddress()%></td>
			<td><%=subbranch.getPhoneNo()%></td>
			<td><%=subbranch.getIsNew()%></td>
			<td><%=subbranch.getIsRecommend()%></td>
			<td><%=subbranch.getOrderId()%></td>
			<td><a href="javascript:void(0)"
				onclick="modifySubbranch('<%=subbranch.getId()%>','<%=subbranch.getName()%>','<%=subbranch.getAddress()%>','<%=subbranch.getPhoneNo()%>','<%=subbranch.getPointX().toString()%>','<%=subbranch.getPointY().toString()%>','<%=subbranch.getIsNew()%>','<%=subbranch.getIsRecommend()%>','<%=subbranch.getOrderId()%>')">修改分店</a> <a
				href="javascript:void(0)"
				onclick="deleteSubbranch('<%=subbranch.getId()%>')">删除分店</a></td>
		</tr>
		<%
				}}
		%>
	</table>
	<div id="subbranchEditDiv"
		style="position: absolute; top: 300px; right: 10px; display: none;">
		<form action="<%=path%>/weixin/modifySubbranch.do" id="subbranchEditForm">
			<table>
				<tr>
					<td>名称</td>
					<td><input type="text" id="subbranchEdit.name"
						name="subbranchEdit.name"></td>
				</tr>
				<tr>
					<td>地址</td>
					<td><input type="text" id="subbranchEdit.address"
						name="subbranchEdit.address"></td>
				</tr>
				<tr>
					<td>电话</td>
					<td><input type="text" id="subbranchEdit.phoneNo"
						name="subbranchEdit.phoneNo"></td>
				</tr>
				<tr>
					<td>X坐标</td>
					<td><input type="text" id="subbranchEdit.pointX"
						name="subbranchEdit.pointX"></td>
				</tr>
				<tr>
					<td>Y坐标</td>
					<td><input type="text" id="subbranchEdit.pointY"
						name="subbranchEdit.pointY"></td>
				</tr>
				<tr>
					<td>是否为新店</td>
					<td><select name="subbranchEdit.isNew"
						id="subbranchEdit.isNew" >
							<option value="false">否</option>
							<option value="true">是</option>
					</select></td>
				</tr>
				<tr>
					<td>是否为推荐店面</td>
					<td><select name="subbranchEdit.isRecommend"
						id="subbranchEdit.isRecommend" >
							<option value="false">否</option>
							<option value="true">是</option>
					</select></td>
				</tr>
				<tr>
					<td>排序</td>
					<td><input type="text" id="subbranchEdit.orderId"
						name="subbranchEdit.orderId"></td>
				</tr>
			</table>
			<input type="hidden" id="subbranchEditid" name="subbranchEditid">
			<a href="javascript:void(0)" onclick="modifySubbranchSubmit()">确认修改</a>
		</form>
	</div>

	<div id="subbranchAddDiv" style="position: absolute; top: 300px; right: 10px; display: none;">
		<form action="<%=path%>/weixin/addSubbranch.do" id="subbranchAddForm">
			<table>
				<tr>
					<td>名称</td>
					<td><input type="text" id="subbranchAdd.name"
						name="subbranchAdd.name"></td>
				</tr>
				<tr>
					<td>地址</td>
					<td><input type="text" id="subbranchAdd.address"
						name="subbranchAdd.address"></td>
				</tr>
				<tr>
					<td>电话</td>
					<td><input type="text" id="subbranchAdd.phoneNo"
						name="subbranchAdd.phoneNo"></td>
				</tr>
				<tr>
					<td>X坐标</td>
					<td><input type="text" id="subbranchAdd.pointX"
						name="subbranchAdd.pointX"></td>
				</tr>
				<tr>
					<td>Y坐标</td>
					<td><input type="text" id="subbranchAdd.pointY"
						name="subbranchAdd.pointY"></td>
				</tr>
				<tr>
					<td>是否为新店</td>
					<td><select name="subbranchAdd.isNew"
						id="subbranchAdd.isNew" >
							<option value="false">否</option>
							<option value="true">是</option>
					</select></td>
				</tr>
				<tr>
					<td>是否为推荐店面</td>
					<td><select name="subbranchAdd.isRecommend"
						id="subbranchAdd.isRecommend" >
							<option value="false">否</option>
							<option value="true">是</option>
					</select></td>
				</tr>
			</table>
			<input type="hidden" id="subbranchAddBrandId" name="subbranchAddBrandId">
			<input type="hidden" id="subbranchAddCompanyWeiboId" name="subbranchAddCompanyWeiboId">
			<a href="javascript:void(0)" onclick="addSubbranchSubmit()">提交</a>
		</form>
	</div>
</body>
</html>