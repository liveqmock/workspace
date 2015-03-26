<%@page import="java.util.List"%>
<%@page import="java.text.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.*"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	BusinessVO business=(BusinessVO)request.getAttribute("business");
	List<ActivityConfigVO> activityConfigList=business.getActivityConfigList();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动管理</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>js/jquery/jquery.form.js"></script>
<script>
	function modifyActivityConfig(brandId,id,name,probability,startTime,endTime,activityId,timeUnit,frequency,countLimit,replyContent) {
		$("#activityConfigEditBrandId").val(brandId);
		$("#activityConfigEditid").val(id);
		$("#activityConfigEdit\\.name").val(name);
		$("#activityConfigEdit\\.probability").val(probability);
		$("#activityConfigEdit\\.startTime").val(startTime);
		$("#activityConfigEdit\\.endTime").val(endTime);
		$("#activityConfigEdit\\.activityId").val(activityId);
		$("#activityConfigEdit\\.timeUnit").val(timeUnit);
		$("#activityConfigEdit\\.frequency").val(frequency);
		$("#activityConfigEdit\\.countLimit").val(countLimit);
		$("#activityConfigEdit\\.replyContent").val(replyContent);
		$("#activityConfigAddDiv").hide();
		$("#activityConfigEditDiv").show();
	}
	function addActivityConfig(brandId) {
		$("#activityConfigAddBrandId").val(brandId);
		$("#activityConfigEditDiv").hide();
		$("#activityConfigAddDiv").show();
	}
	function modifyActivityConfigSubmit() {
		var probability=$("#activityConfigEdit\\.probability").val();
		var startTime=$("#activityConfigEdit\\.startTime").val();
		var endTime=$("#activityConfigEdit\\.endTime").val();
		var name=$("#activityConfigEdit\\.name").val();
		var id=$("#activityConfigEditid").val();
		var brandId=$("#activityConfigEditBrandId").val();
		var activityId=$("#activityConfigEdit\\.activityId").val();
		var timeUnit=$("#activityConfigEdit\\.timeUnit").val();
		var frequency=$("#activityConfigEdit\\.frequency").val();
		var countLimit=$("#activityConfigEdit\\.countLimit").val();
		var replyContent=$('#activityConfigEdit\\.replyContent').val();
		if (name == "" ||endTime==""||startTime=="") {
			alert("不可为空");
			return false;
		}
		if (!(/^[0-9_]+$/.test(startTime)&&startTime.length==8&&/^[0-9_]+$/.test(endTime)&&endTime.length==8)) {
			alert("日期格式不正确!20130101");
			return false;
		}
		if(parseInt(endTime)<parseInt(startTime)){
			alert("起始时间必须小于结束时间");
			return false;
		}
		if (!(/^[0-9_]+$/.test(probability)&&parseInt(probability)>-1&&parseInt(probability)<101)) {
			alert("0-100间的整数");
			return false;
		}
		if (!(/^[0-9_]+$/.test(activityId))) {
			alert("活动ID必须为数字");
			return false;
		}
		if (!(/^[0-9_]+$/.test(frequency))) {
			alert("频度必须为数字");
			return false;
		}
		if (!(/^[0-9_]+$/.test(countLimit))) {
			alert("总中奖数量必须为数字");
			return false;
		}
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/modifyActivityConfig.do',
			cache : false,
			dataType : "text",
			data : 'activityConfigEdit.name=' + name
			+'&activityConfigEdit.probability=' + probability
			+'&activityConfigEdit.startTime=' + startTime
			+'&activityConfigEdit.endTime=' + endTime
			+'&activityConfigEdit.id=' + id
			+'&activityConfigEdit.brandId=' + brandId
			+'&activityConfigEdit.activityId=' + activityId
			+'&activityConfigEdit.frequency=' + frequency
			+'&activityConfigEdit.countLimit=' + countLimit
			+'&activityConfigEdit.timeUnit=' + timeUnit
			+'&activityConfigEdit.replyContent=' + replyContent,
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					alert('错误', '提交失败', 'error');
				}
			},
			success : function(json) {
				if (json == "1") {
					alert('成功');
					$("#businessModifyDiv").load("<%=path%>/weixin/activityConfigList.do?id=<%=business.getId()%>");
				} else {
					alert(json);
				}

			}
		});
	}

	function addActivityConfigSubmit(id) {
		var probability=$("#activityConfigAdd\\.probability").val();
		var startTime=$("#activityConfigAdd\\.startTime").val();
		var endTime=$("#activityConfigAdd\\.endTime").val();
		var name=$("#activityConfigAdd\\.name").val();
		var activityId=$("#activityConfigAdd\\.activityId").val();
		var timeUnit=$("#activityConfigAdd\\.timeUnit").val();
		var frequency=$("#activityConfigAdd\\.frequency").val();
		var countLimit=$("#activityConfigAdd\\.countLimit").val();
		var replyContent=$('#activityConfigAdd\\.replyContent').val();;
		if (name == "" ||endTime==""||startTime==""||activityId=="") {
			alert("不可为空");
			return false;
		}
		if (!(/^[0-9_]+$/.test(startTime)&&startTime.length==8&&/^[0-9_]+$/.test(endTime)&&endTime.length==8)) {
			alert("日期格式不正确!20130101");
			return false;
		}
		if(parseInt(endTime)<parseInt(startTime)){
			alert("起始时间必须小于结束时间");
			return false;
		}
		if (!(/^[0-9_]+$/.test(probability)&&parseInt(probability)>-1&&parseInt(probability)<101)) {
			alert("0-100间的整数");
			return false;
		}
		if (!(/^[0-9_]+$/.test(activityId))) {
			alert("活动ID必须为数字");
			return false;
		}
		if (!(/^[0-9_]+$/.test(frequency))) {
			alert("频度必须为数字");
			return false;
		}
		if (!(/^[0-9_]+$/.test(countLimit))) {
			alert("总中奖数量必须为数字");
			return false;
		}
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/addActivityConfig.do',
			cache : false,
			data : 'activityConfigAdd.name=' + name
					+ '&activityConfigAddBrandId=' + $("#activityConfigAddBrandId").val()
					+'&activityConfigAdd.probability=' + probability
					+'&activityConfigAdd.startTime=' + startTime
					+'&activityConfigAdd.endTime=' + endTime
					+'&activityConfigAdd.activityId=' + activityId
					+'&activityConfigAdd.frequency=' + frequency
					+'&activityConfigAdd.countLimit=' + countLimit
					+'&activityConfigAdd.timeUnit=' + timeUnit
					+'&activityConfigAdd.replyContent='+ replyContent,
			dataType : "text",
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					alert('错误', '提交失败', 'error');
				}
			},
			success : function(json) {
				if (json == "1") {
					alert('成功');
					$("#businessModifyDiv").load("<%=path%>/weixin/activityConfigList.do?id=<%=business.getId()%>");
				} else {
					alert(json);
				}

			}
		});
	}
	function deleteActivityConfig(id) {
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/deleteActivityConfig.do',
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
					$("#businessModifyDiv").load("<%=path%>/weixin/activityConfigList.do?id=<%=business.getId()%>");
				} else {
					alert('失败');
				}

			}
		});
	}
</script>
</head>
<body>
	<table title="活动">
		<tr>
			<td>活动标示</td>
			<td>商家ID（brandId）</td>
			<td>名称</td>
			<td>中奖率(%)</td>
			<td>类型</td>
			<td>频度</td>
			<td>奖品总数</td>
			<td>已中奖</td>
			<td>活动ID</td>
			<td>起始时间</td>
			<td>结束时间</td>
			<td>回复内容</td>
			<td>操作 <a href="javascript:void(0)"
				onclick="addActivityConfig('<%=business.getBrandId().toString()%>')">新增活动</a></td>
		</tr>
		<%
			for (ActivityConfigVO activityConfig : activityConfigList) {
				if(!activityConfig.getIsDelete()){
		%>
		<tr>
			<td><%=activityConfig.getId()%></td>
			<td><%=activityConfig.getBrandId()%></td>
			<td><%=activityConfig.getName()%></td>
			<td><%=activityConfig.getProbability()%></td>
			<td><%switch (activityConfig.getTimeUnit()){
			case 1:out.print("日"); break ;
			case 2:out.print("周");break ;
			case 3:out.print("月");break ;
			case 4:out.print("年");break ;
			case 5:out.print("永久");break ;
			}%></td>
			<td><%=activityConfig.getFrequency()%></td>
			<td><%=activityConfig.getCountLimit()%></td>
			<td><%=activityConfig.getAwardCount()%></td>
			<td><%=activityConfig.getActivityId()%></td>
			<td><%=sdf.format(activityConfig.getStartTime())%></td>
			<td><%=sdf.format(activityConfig.getEndTime())%></td>
			<td><%=activityConfig.getReplyContent()%></td>
			<td><a href="javascript:void(0)"
				onclick="modifyActivityConfig('<%=activityConfig.getBrandId()%>','<%=activityConfig.getId()%>','<%=activityConfig.getName()%>','<%=activityConfig.getProbability()%>','<%=sdf2.format(activityConfig.getStartTime())%>','<%=sdf2.format(activityConfig.getEndTime())%>','<%=activityConfig.getActivityId()%>','<%=activityConfig.getTimeUnit()%>','<%=activityConfig.getFrequency()%>','<%=activityConfig.getCountLimit()%>','<%=activityConfig.getReplyContent()%>')">修改活动</a> <a
				href="javascript:void(0)"
				onclick="deleteActivityConfig('<%=activityConfig.getId()%>')">删除活动</a></td>
		</tr>
		<%
				}}
		%>
	</table>
	<div id="activityConfigEditDiv"
		style="position: absolute; top: 300px; right: 10px; display: none;">
		<form action="<%=path%>/weixin/modifyActivityConfig.do"
			id="activityConfigEditForm">
			<table>
				<tr>
					<td>名称</td>
					<td><input type="text" id="activityConfigEdit.name"
						name="activityConfigEdit.name"></td>
				</tr>
				<tr>
					<td>中奖率(%)</td>
					<td><input type="text" id="activityConfigEdit.probability"
						name="activityConfigEdit.probability"></td>
				</tr>
				<tr>
					<td>时间单位</td>
					<td><select name="activityConfigEdit.timeUnit"
						id="activityConfigEdit.timeUnit" >
							<option value="1">日</option>
							<option value="2">周</option>
							<option value="3">月</option>
							<option value="4">年</option>
							<option value="5">永久</option>
					</select></td>
				</tr>
				<tr>
					<td>频度</td>
					<td><input type="text" id="activityConfigEdit.frequency"
						name="activityConfigEdit.frequency"></td>
				</tr>
				<tr>
					<td>总中奖次数限制</td>
					<td><input type="text" id="activityConfigEdit.countLimit"
						name="activityConfigEdit.countLimit"></td>
				</tr>
				<tr>
					<td>活动ID</td>
					<td><input type="text" id="activityConfigEdit.activityId"
						name="activityConfigEdit.activityId"></td>
				</tr>
				<tr>
					<td>起始时间</td>
					<td><input type="text" id="activityConfigEdit.startTime"
						name="activityConfigEdit.startTime"></td>
				</tr>
				<tr>
					<td>结束时间</td>
					<td><input type="text" id="activityConfigEdit.endTime"
						name="activityConfigEdit.endTime"></td>
				</tr>
				<tr>
				     <td>回复内容</td>
				     <td><textarea rows="5" cols="20" id="activityConfigEdit.replyContent" name="activityConfigEdit.replyContent"></textarea></td>
				</tr>
			</table>
			<input type="hidden" id="activityConfigEditBrandId" name="activityConfigEditBrandId"> 
			<input type="hidden" id="activityConfigEditid" name="activityConfigEditid"> <a
				href="javascript:void(0)" onclick="modifyActivityConfigSubmit()">确认修改</a>
		</form>
	</div>

	<div id="activityConfigAddDiv"
		style="position: absolute; top: 300px; right: 10px; display: none;">
		<form action="<%=path%>/weixin/addActivityConfig.do" id="activityConfigAddForm">
			<table>
				<tr>
					<td>名称</td>
					<td><input type="text" id="activityConfigAdd.name"
						name="activityConfigAdd.name"></td>
				</tr>
				<tr>
					<td>中奖率</td>
					<td><input type="text" id="activityConfigAdd.probability"
						name="activityConfigAdd.probability"></td>
				</tr>
				<tr>
					<td>时间单位</td>
					<td><select name="activityConfigAdd.timeUnit"
						id="activityConfigAdd.timeUnit" >
							<option value="1">日</option>
							<option value="2">周</option>
							<option value="3">月</option>
							<option value="4">年</option>
							<option value="5">永久</option>
					</select></td>
				</tr>
				<tr>
					<td>频度</td>
					<td><input type="text" id="activityConfigAdd.frequency"
						name="activityConfigAdd.frequency"></td>
				</tr>
				<tr>
					<td>总中奖次数限制</td>
					<td><input type="text" id="activityConfigAdd.countLimit"
						name="activityConfigAdd.countLimit"></td>
				</tr>
				<tr>
					<td>活动ID</td>
					<td><input type="text" id="activityConfigAdd.activityId"
						name="activityConfigAdd.activityId"></td>
				</tr>
				<tr>
					<td>起始时间</td>
					<td><input type="text" id="activityConfigAdd.startTime"
						name="activityConfigAdd.startTime"></td>
				</tr>
				<tr>
					<td>结束时间</td>
					<td><input type="text" id="activityConfigAdd.endTime"
						name="activityConfigAdd.endTime"></td>
				</tr>
				 <tr>
				 <td>回复内容</td>
				 <td><textarea rows="5" cols="20" id="activityConfigAdd.replyContent" name="activityConfigAdd.replyContent"></textarea></td>
				 </tr>
			</table>
			<input type="hidden" id="activityConfigAddBrandId" name="activityConfigAddBrandId">
			<a href="javascript:void(0)" onclick="addActivityConfigSubmit()">提交</a>
		</form>
	</div>
</body>
</html>