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
<title>菜品管理</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>js/jquery/jquery.form.js"></script>
<script>
	function modifyDishes(id,name,description,isNew,isRecomment,recommentLevel) {
		$("#dishesEditid").val(id);
		$("#dishesEdit\\.name").val(name);
		$("#dishesEdit\\.description").val(description.replace(/\^\~\^/g,"\n"));
		$("#dishesEdit\\.isNew").val(isNew);
		$("#dishesEdit\\.isRecommend").val(isRecomment);
		$("#dishesEdit\\.recommendLevel").val(recommentLevel);
		$("#dishesAddDiv").hide();
		$("#dishesEditDiv").show();
	}
	function addDishes(brandId) {
		$("#dishesAddBrandId").val(brandId);
		$("#dishesEditDiv").hide();
		$("#dishesAddDiv").show();
	}
	function modifyDishesSubmit(id) {
		var name = document.getElementById("dishesEdit.name").value;
		var description = document.getElementById("dishesEdit.description").value;
		if (name == "" || description == "") {
			alert("不可为空");
			return false;
		}
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/modifyDishes.do',
			cache : false,
			dataType : "text",
			data : 'dishesEdit.name=' + $("#dishesEdit\\.name").val()
			+ '&dishesEdit.description='
			+ $("#dishesEdit\\.description").val() + '&dishesEdit.isNew='
			+ $("#dishesEdit\\.isNew").val() + '&dishesEdit.isRecommend='
			+ $("#dishesEdit\\.isRecommend").val()
			+ '&dishesEdit.recommendLevel='
			+ $("#dishesEdit\\.recommendLevel").val()
			+ '&dishesEditid=' + $("#dishesEditid").val(),
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					alert('错误', '提交失败', 'error');
				}
			},
			success : function(json) {
				if (json == "1") {
					alert('成功');
					$("#businessModifyDiv").load("<%=path%>/weixin/dishesList.do?id=<%=business.getId()%>");
				} else {
					alert('失败');
				}

			}
		});
	}

	function addDishesSubmit(id) {
		var name = document.getElementById("dishesAdd.name").value;
		var description = document.getElementById("dishesAdd.description").value;
		if (name == "" || description == "") {
			alert("不可为空");
			return false;
		}
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/addDishes.do',
			cache : false,
			data : 'dishesAdd.name=' + $("#dishesAdd\\.name").val()
					+ '&dishesAdd.description='
					+ $("#dishesAdd\\.description").val() + '&dishesAdd.isNew='
					+ $("#dishesAdd\\.isNew").val() + '&dishesAdd.isRecommend='
					+ $("#dishesAdd\\.isRecommend").val()
					+ '&dishesAdd.recommendLevel='
					+ $("#dishesAdd\\.recommendLevel").val()
					+ '&dishesAddBrandId=' + $("#dishesAddBrandId").val(),
			dataType : "text",
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					alert('错误', '提交失败', 'error');
				}
			},
			success : function(json) {
				if (json == "1") {
					alert('成功');
					$("#businessModifyDiv").load("<%=path%>/weixin/dishesList.do?id=<%=business.getId()%>");
				} else {
					alert('失败');
				}

			}
		});
	}
	function deleteDishes(id) {
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/deleteDishes.do',
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
					$("#businessModifyDiv").load("<%=path%>/weixin/dishesList.do?id=<%=business.getId()%>");
				} else {
					alert('失败');
				}

			}
		});
	}
</script>
</head>
<body>
	<table title="菜品">
		<tr>
			<td>商家ID（brandId）</td>
			<td>名称</td>
			<td>是否为新菜品</td>
			<td>是否为推荐菜品</td>
			<td>描述</td>
			<td>推荐等级</td>
			<td>操作 <a href="javascript:void(0)"
				onclick="addDishes('<%=business.getBrandId().toString()%>')">新增菜品</a></td>
		</tr>
		<%
			for (DishesVO dishes : dishesList) {
				if(!dishes.getIsDelete()){
		%>
		<tr>
			<td><%=dishes.getBrandId()%></td>
			<td><%=dishes.getName()%></td>
			<td><%=dishes.getIsNew()%></td>
			<td><%=dishes.getIsRecommend()%></td>
			<td><%=dishes.getDescription()%></td>
			<td><%=dishes.getRecommendLevel()%></td>
			<td><a href="javascript:void(0)"
				onclick="modifyDishes('<%=dishes.getId()%>','<%=dishes.getName()%>','<%=dishes.getDescription().replace("<br>", "^~^")%>','<%=dishes.getIsNew()%>','<%=dishes.getIsRecommend()%>','<%=dishes.getRecommendLevel()%>')">修改菜品</a> <a
				href="javascript:void(0)"
				onclick="deleteDishes('<%=dishes.getId()%>')">删除菜品</a></td>
		</tr>
		<%
				}}
		%>
	</table>
	<div id="dishesEditDiv"
		style="position: absolute; top: 300px; right: 10px; display: none;">
		<form action="<%=path%>/weixin/modifyDishes.do"
			id="dishesEditForm">
			<table>
				<tr>
					<td>名称</td>
					<td><input type="text" id="dishesEdit.name"
						name="dishesEdit.name"></td>
				</tr>
				<tr>
					<td>介绍</td>
					<td><textarea name="dishesEdit.description" id="dishesEdit.description" cols="20" rows="5"></textarea></td>
				</tr>
				<tr>
					<td>是否为新菜品</td>
					<td><select name="dishesEdit.isNew" id="dishesEdit.isNew"
						>
							<option value="false">否</option>
							<option value="true">是</option>
					</select></td>
				</tr>
				<tr>
					<td>是否为推荐菜品</td>
					<td><select name="dishesEdit.isRecommend"
						id="dishesEdit.isRecommend" >
							<option value="false">否</option>
							<option value="true">是</option>
					</select></td>
				</tr>
				<tr>
					<td>推荐等级</td>
					<td><select name="dishesEdit.recommendLevel"
						id="dishesEdit.recommendLevel" >
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
					</select></td>
				</tr>
			</table>
			<input type="hidden" id="dishesEditid" name="dishesEditid"> <a
				href="javascript:void(0)" onclick="modifyDishesSubmit()">确认修改</a>
		</form>
	</div>

	<div id="dishesAddDiv"
		style="position: absolute; top: 300px; right: 10px; display: none;">
		<form action="<%=path%>/weixin/addDishes.do" id="dishesAddForm">
			<table>
				<tr>
					<td>名称</td>
					<td><input type="text" id="dishesAdd.name"
						name="dishesAdd.name"></td>
				</tr>
				<tr>
					<td>介绍</td>
					<td><textarea name="dishesAdd.description" id="dishesAdd.description" cols="20" rows="5"></textarea></td>
				</tr>
				<tr>
					<td>是否为新菜品</td>
					<td><select name="dishesAdd.isNew" id="dishesAdd.isNew"	>
							<option value="false">否</option>
							<option value="true">是</option>
					</select></td>
				</tr>
				<tr>
					<td>是否为推荐菜品</td>
					<td><select name="dishesAdd.isRecommend"
						id="dishesAdd.isRecommend" >
							<option value="false">否</option>
							<option value="true">是</option>
					</select></td>
				</tr>
				<tr>
					<td>推荐等级</td>
					<td><select name="dishesAdd.recommendLevel"
						id="dishesAdd.recommendLevel">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
					</select></td>
				</tr>
			</table>
			<input type="hidden" id="dishesAddBrandId" name="dishesAddBrandId">
			<a href="javascript:void(0)" onclick="addDishesSubmit()">提交</a>
		</form>
	</div>
</body>
</html>