<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.*"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	List<CouponNewDescribeVO> coupondescribes=(List<CouponNewDescribeVO>)request.getAttribute("couponDescribes");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>优惠券描述管理</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>js/jquery/jquery.form.js"></script>
<script>
function addSubbranch(name,batch_no,brandid,flag) {
	$("#subbranchAddName").val(name);
	$("#subbranchAddBrandId").val(brandid);
	$("#subbranchAddFlag").val(flag);
	$("#subbranchAddBatchNo").val(batch_no);
	$("#subbranchEditDiv").hide();
	$("#subbranchAddDiv").show();
}
function modifySubbranch(name,batch_no,brandid,flag,description) {
	$("#subbranchEditName").val(name);
	$("#subbranchEditBrandId").val(brandid);
	$("#subbranchEditFlag").val(flag);
	$("#subbranchEditBatchNo").val(batch_no);
	$("#subbranchEditDescription").val(description);
	$("#subbranchEditDiv").show();
	$("#subbranchAddDiv").hide();
}

function addSubbranchSubmit() {
	var name = $("#subbranchAddName").val();
	var brandid = $("#subbranchAddBrandId").val();
	var flag = $("#subbranchAddFlag").val();
	var description = $("#subbranchAddDescription").val();
	var batchNo = $("#subbranchAddBatchNo").val();
	if (name == "" || brandid == ""
			|| flag == "" || description=="") {
		alert("不可为空");
		return;
	}
	$.ajax({
		type : "post",
		url : '<%=path%>/weixin/newDescribe/addDescribe.do',
		cache : false,
		dataType : "text",
		data : 'coupon_name=' + name
		+ '&merchant_id='+ brandid 
		+ '&flag='+ flag
		+ '&description='+ description+'&batch_no='+ batchNo,
		error : function(XMLHttpRequest) {// 请求失败时调用函数
			if (processCommErr(XMLHttpRequest)) {
				alert('错误', '提交失败', 'error');
			}
		},
		success : function(json) {
			if (json == "1") {
				alert('成功');
				$("#businessModifyDiv").load("<%=path%>/weixin/newDescribe/describeList.do?merchantId="+brandid+"&flag="+flag);
			} else {
				alert('失败');
			}

		}
	});
}

function modifySubbranchSubmit() {
	var name = $("#subbranchEditName").val();
	var description = $("#subbranchEditDescription").val();
	var brandid = $("#subbranchEditBrandId").val();
	var flag = $("#subbranchEditFlag").val();
	var batchNo = $("#subbranchEditBatchNo").val();
	$.ajax({
		type : "post",
		url : '<%=path%>/weixin/newDescribe/editDescribe.do',
		cache : false,
		dataType : "text",
		data : 'coupon_name=' + name
		+ '&merchant_id='+ brandid 
		+ '&flag='+ flag
		+ '&description='+ description+ '&batch_no='+ batchNo,
		error : function(XMLHttpRequest) {// 请求失败时调用函数
			if (processCommErr(XMLHttpRequest)) {
				alert('错误', '提交失败', 'error');
			}
		},
		success : function(json) {
			if (json == "1") {
				alert('成功');
				$("#businessModifyDiv").load("<%=path%>/weixin/newDescribe/describeList.do?merchantId="+brandid+"&flag="+flag);
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
			<td>商家ID（merchantId）</td>
			<td>优惠券名称</td>
			<td>优惠券批次号</td>
		</tr>
		<%
			for (CouponNewDescribeVO coupondescribe : coupondescribes) {
				
		%>
		<tr>
			<td><%=coupondescribe.getMerchant_id()%></td>
			<td><%=coupondescribe.getCoupon_name()%></td>
			<td><%=coupondescribe.getBatch_no()%></td>
	    
			
			<td>
			<%if (coupondescribe.getDescription() == null || coupondescribe.getDescription().equals("")) { %>
			<a href="javascript:void(0)"
				onclick="addSubbranch('<%=coupondescribe.getCoupon_name()%>','<%=coupondescribe.getBatch_no()%>','<%=coupondescribe.getMerchant_id()%>','1')">添加详情</a> 
			<%
			 } else {
			%>
			<a href="javascript:void(0)"
				onclick="modifySubbranch('<%=coupondescribe.getCoupon_name()%>','<%=coupondescribe.getBatch_no()%>','<%=coupondescribe.getMerchant_id()%>','1','<%=coupondescribe.getDescription()%>')">修改详情</a> 
			<%
			 }
			%>
			</td>
		</tr>
		<%
				}
		%>
	</table>
	<div id="subbranchEditDiv"
		style="position: absolute; top: 300px; right: 10px; display: none;">
		<form action="<%=path%>/weixin/modifySubbranch.do" id="subbranchEditForm">
			<table>
				<tr>
					<td>优惠券名称</td>
					<td><input type="text" readonly="readonly"  id="subbranchEditName"
						name="subbranchEditName" ></td>
				</tr>
				<tr>
					<td>描述</td>
					<td>
					<textarea  id="subbranchEditDescription" name="subbranchEditDescription" cols="30" rows="4" ></textarea>
				</tr>

			</table>
			<input type="hidden" id="subbranchEditName" name="subbranchEditName">
			<input type="hidden" id="subbranchEditBrandId" name="subbranchEditBrandId">
			<input type="hidden" id="subbranchEditFlag" name="subbranchEditFlag">
			<input type="hidden" id="subbranchEditBatchNo" name="subbranchEditBatchNo">
			<a href="javascript:void(0)" onclick="modifySubbranchSubmit()">确认修改</a>
		</form>
	</div>

	<div id="subbranchAddDiv" style="position: absolute; top: 300px; right: 10px; display: none;">
		<form action="<%=path%>/weixin/addSubbranch.do" id="subbranchAddForm">
			<table>
				<tr>
					<td>优惠券名称</td>
					<td><input type="text" readonly="readonly"  id="subbranchAddName"
						name="subbranchAddName" ></td>
				</tr>
				<tr>
					<td>描述</td>
					<td>
					<textarea  id="subbranchAddDescription" name="subbranchAddDescription" cols="30" rows="4" ></textarea>
				</tr>

	
			</table>
			
			<input type="hidden" id="subbranchAddName" name="subbranchAddName">
			<input type="hidden" id="subbranchAddBrandId" name="subbranchAddBrandId">
			<input type="hidden" id="subbranchAddFlag" name="subbranchAddFlag">
			<input type="hidden" id="subbranchAddBatchNo" name="subbranchAddBatchNo">
			<a href="javascript:void(0)" onclick="addSubbranchSubmit()">提交</a>
		</form>
	</div>
</body>
</html>