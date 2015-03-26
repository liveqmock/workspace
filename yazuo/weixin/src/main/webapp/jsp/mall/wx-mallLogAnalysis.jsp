<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path ;
%>

<html>
<head>

	<meta charset="utf-8">
	<title>访问日志查询</title>
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
	<meta content="telephone=no" name="format-detection" />
	<meta content="email=no" name="format-detection" />
	<script type='text/javascript' src='${pageContext.request.contextPath}/js/datepicker/WdatePicker.js'></script>
	<style type="text/css">
		table {
	  max-width: 100%;
	  border-collapse: collapse;
	  border-spacing: 0;
	  background-color: transparent;
	}
	.table {
	  width: 100%;
	  margin-bottom: 18px;
	}
	.table th,
	.table td {
	  padding: 8px;
	  line-height: 18px;
	  text-align: left;
	  vertical-align: top;
	  border-top: 1px solid #dddddd;
	}
	.table th {
	  font-weight: bold;
	}
	.table-bordered {
	  border: 1px solid #dddddd;
	  border-left: 0;
	  border-collapse: separate;
	  *border-collapse: collapsed;
	  -webkit-border-radius: 4px;
	  -moz-border-radius: 4px;
	  border-radius: 4px;
	}
	.table-bordered th,
	.table-bordered td {
	  border-left: 1px solid #dddddd;
	}
	</style>
</head>
<body >
	<div style="margin-top:30px;width:100%" align="center">
		<form id="form1" action="" method="post" name="form1">
			开始日期:
			 <input type="text" class="Wdate input-medium" id="beginDate" name="beginDate" value="${beginDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'2010-01-01',maxDate:'2099-12-31'})"/>
			结束日期:
			 <input type="text" class="Wdate input-medium" id="endDate" name="endDate" value="${endDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'2010-01-01',maxDate:'2099-12-31'})"/>
			选择商户:
			<select name="brandId" class="fadeSelect" id="brandId">
				<c:forEach items="${brandList }" var="item" varStatus="i">
	        		<option value="${item.brandId }">${item.title }</option>
	        	</c:forEach>
			</select>
			<input type="button" value="查询" onclick="return search();">
		</form>
	</div>
	<div align="center">
		<table class="table table-bordered">
			<tr>
				<td>日期</td>
				<td>首页 </td>
				<td>商品详情页 </td>
				<td>购买页 </td>
				<td>发起支付次数</td>
				<td>现金支付 </td>
				<td>积分支付 </td>
				<td>成功页面</td>
			</tr>
			<c:forEach items="${logList}" varStatus="i" var="item">
			<tr><td>${item.logdate }</td>
				<td>${item.mallMartIndex}</td>
				<td>${item.goodInfoPage}</td>
				<td>${item.buyGood}</td>
				<td>${item.obtainGoodInfo}</td>
				<td>${item.mallPayResult}</td>
				<td>${item.integralPay}</td>
				<td>${item.goMallSuccessPage}</td>
			</tr>
			</c:forEach>
		</table>
	</div>

<script src="<%=basePath%>/js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	function search(){
		var startDate = $("#beginDate").val();
		var endDate = $("#endDate").val();
		if(startDate==null || startDate==''){
			alert("开始时间必选！");
			return false;
		}
		if(endDate==null || endDate==''){
			alert("结束时间必选！");
			return false;
		}
		if(startDate=='' && endDate==''){
			alert("时间必选");
			return false;
		}
		document.form1.action="<%=basePath%>/analysis/queryMallLog.do";
		document.form1.submit();
	}
	
	function exportExcel(){
		var url="<%=basePath%>/analysis/1119/exportExcel.do";
		document.form1.action=url;
		document.form1.submit();
	}
	$(function(){
		 $("#brandId").val('${brandId}');
	});
</script>
</body>
</html>