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
<title>优惠管理</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>js/jquery/jquery.form.js"></script>
<script>
	function modifyPreference(id,title,content,isNew,isRecomment, sortNumber) {
		$("#preferenceEditid").val(id);
		$("#preferenceEdit\\.title").val(title);
		$("#preferenceEdit\\.content").val(content.replace(/\^\~\^/g,"\n"));
		$("#preferenceEdit\\.isNew").val(isNew);
		$("#preferenceEdit\\.isRecommend").val(isRecomment);
		$("#sortNumber").val(sortNumber);
		$("#preferenceAddDiv").hide();
		$("#preferenceEditDiv").show();
	}
	function addPreference(brandId) {
		$("#preferenceAddBrandId").val(brandId);
		$("#preferenceEditDiv").hide();
		$("#preferenceAddDiv").show();
	}
	function modifyPreferenceSubmit(id) {
		var title = document.getElementById("preferenceEdit.title").value;
		var content = document.getElementById("preferenceEdit.content").value;
		var sortNumber = $("#sortNumber").val();
		if (title == "" || content == "") {
			alert("不可为空");
			return false;
		}else if (isNaN(sortNumber)) {
			alert("输入的顺序必须为数字");
			return false;
		}
		
		if (content.indexOf("%")>-1) {
			content = content.replace("%","@");
		}
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/modifyPreference.do',
			cache : false,
			dataType : "text",
			data : 'preferenceEdit.title=' + $("#preferenceEdit\\.title").val()
					+ '&preferenceEdit.content='
					+ content
					+ '&preferenceEdit.isNew='
					+ $("#preferenceEdit\\.isNew").val()
					+ '&preferenceEdit.isRecommend='
					+ $("#preferenceEdit\\.isRecommend").val()
					+ '&preferenceEditid='
					+ $("#preferenceEditid").val()+'&preferenceEdit.sortNumber='+sortNumber,
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					alert('错误', '提交失败', 'error');
				}
			},
			success : function(json) {
				if (json == "1") {
					alert('成功');
					$("#businessModifyDiv").load("<%=path%>/weixin/preferenceList.do?id=<%=business.getId()%>");
				} else {
					alert('失败');
				}

			}
		});
	}

	function addPreferenceSubmit(id) {
		var title = document.getElementById("preferenceAdd.title").value;
		var content = document.getElementById("preferenceAdd.content").value;
		var sortNumber = $("#addSortNumber").val();
		
		if (title == "" || content == "") {
			alert("不可为空");
			return false;
		} else if (isNaN(sortNumber)) {
			alert("输入的顺序必须为数字");
			return false;
		}
		
		if (content.indexOf("%")>-1) {
			content = content.replace("%","@");
		}
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/addPreference.do',
			cache : false,
			dataType : "text",
			data : 'preferenceAdd.title=' + $("#preferenceAdd\\.title").val()
					+ '&preferenceAdd.content='
					+ content
					+ '&preferenceAdd.isNew='
					+ $("#preferenceAdd\\.isNew").val()
					+ '&preferenceAdd.isRecommend='
					+ $("#preferenceAdd\\.isRecommend").val()
					+ '&preferenceAddBrandId='
					+ $("#preferenceAddBrandId").val()+'&preferenceAdd.sortNumber='+sortNumber,
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					alert('错误', '提交失败', 'error');
				}
			},
			success : function(json) {
				if (json == "1") {
					alert('成功');
					$("#businessModifyDiv").load("<%=path%>/weixin/preferenceList.do?id=<%=business.getId()%>");
				} else {
					alert('失败');
				}

			}
		});
	}
	function deletePreference(id) {
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/deletePreference.do?',
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
					$("#businessModifyDiv").load("<%=path%>/weixin/preferenceList.do?id=<%=business.getId()%>");
				} else {
					alert('失败');
				}

			}
		});
	}
</script>
</head>
<body>
<body>
	<table title="优惠">
		<tr>
			<td>商家ID（brandId）</td>
			<td>标题</td>
			<td>内容</td>
			<td>是否为新优惠</td>
			<td>是否为推荐优惠</td>
			<td>操作 <a href="javascript:void(0)"
				onclick="addPreference('<%=business.getBrandId().toString()%>')">新增优惠</a></td>
		</tr>
		<%
			for (PreferenceVO preference : preferenceList) {
				if(!preference.getIsDelete()){
		%>
		<tr>
			<td><%=preference.getBrandId()%></td>
			<td><%=preference.getTitle()%></td>
			<td><%=preference.getContent()%></td>
			<td><%=preference.getIsNew()%></td>
			<td><%=preference.getIsRecommend()%></td>
			<td nowrap="nowrap"><a href="javascript:void(0)"
				onclick="modifyPreference('<%=preference.getId()%>','<%=preference.getTitle()%>','<%=preference.getContent().replace("<br>", "^~^")%>','<%=preference.getIsNew()%>','<%=preference.getIsRecommend()%>',<%=preference.getSortNumber() %>)">修改优惠</a> <a
				href="javascript:void(0)"
				onclick="deletePreference('<%=preference.getId()%>')">删除优惠</a></td>
		</tr>
		<%
			}}
		%>
	</table>
	<div id="preferenceEditDiv"
		style="position: absolute; top: 300px; right: 10px; display: none;">
		<form action="<%=path%>/weixin/modifyPreference.do"
			id="preferenceEditForm">
			<table>
				<tr>
					<td>标题</td>
					<td><input type="text" id="preferenceEdit.title"
						name="preferenceEdit.title"></td>
				</tr>
				<tr>
					<td>内容</td>
					<td><textarea name="preferenceEdit.content" id="preferenceEdit.content" cols="20" rows="5"></textarea></td>
				</tr>
				<tr>
					<td>是否为新优惠</td>
					<td><select name="preferenceEdit.isNew"
						id="preferenceEdit.isNew" >
							<option value="false">否</option>
							<option value="true">是</option>
					</select></td>
				</tr>
				<tr>
					<td>是否为推荐优惠</td>
					<td><select name="preferenceEdit.isRecommend"
						id="preferenceEdit.isRecommend" >
							<option value="false">否</option>
							<option value="true">是</option>
					</select></td>
				</tr>
				<tr>
					<td>顺序</td>
					<td><input type="text" name="preferenceEdit.sortNumber" id="sortNumber"></td>
				</tr>
			</table>
			<input type="hidden" id="preferenceEditid" name="preferenceEditid">
			<a href="javascript:void(0)" onclick="modifyPreferenceSubmit()">确认修改</a>
		</form>
	</div>

	<div id="preferenceAddDiv"
		style="position: absolute; top: 300px; right: 10px; display: none;">
		<form action="<%=path%>/weixin/addPreference.do"
			id="preferenceAddForm">
			<table>
				<tr>
					<td>标题</td>
					<td><input type="text" id="preferenceAdd.title"
						name="preferenceAdd.title"></td>
				</tr>
				<tr>
					<td>内容</td>
					<td><textarea name="preferenceAdd.content" id="preferenceAdd.content" cols="20" rows="5"></textarea></td>
				</tr>
				<tr>
					<td>是否为新优惠</td>
					<td><select name="preferenceAdd.isNew"
						id="preferenceAdd.isNew" >
							<option value="false">否</option>
							<option value="true">是</option>
					</select></td>
				</tr>
				<tr>
					<td>是否为推荐优惠</td>
					<td><select name="preferenceAdd.isRecommend"
						id="preferenceAdd.isRecommend" >
							<option value="false">否</option>
							<option value="true">是</option>
					</select></td>
				</tr>
				<tr>
					<td>顺序</td>
					<td><input type="text" name="preferenceAdd.sortNumber" id="addSortNumber"></td>
				</tr>
			</table>
			<input type="hidden" id="preferenceAddBrandId"
				name="preferenceAddBrandId"> <a href="javascript:void(0)"
				onclick="addPreferenceSubmit()">提交</a>
		</form>
	</div>
</body>
</body>
</html>