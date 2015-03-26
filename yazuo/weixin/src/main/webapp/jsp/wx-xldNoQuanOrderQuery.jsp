<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>

	<meta charset="utf-8">
	<title>没发券订单查询</title>
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
	<meta content="telephone=no" name="format-detection" />
	<meta content="email=no" name="format-detection" />
	<link type="text/css" rel="stylesheet" href="<%=basePath%>css/crm-wx.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/packagePayment.css" />
</head>
<body>
	<div class="package-header">没发券订单总数:<font color="red">${size }</font></div>
	<div class="package-content">
		<c:forEach items="${orderList}" varStatus="i" var="item">
			<div class="item">
				<div class="text">
					<p>手机号：${item.mobile}订单号:${item.out_trade_no}
					微信id:${item.openId}<br/>券id:${item.cardId}
					银行订单号:${item.transaction_id}数量:${item.count}</p>
				</div>
			</div>
		</c:forEach>
	</div>
	<div class="package-footer">
		<form action="" method="post" id="form1" name="form1">
			手机号：<input type="text" id="mobile" name="mobile">
			订单号：<input type="text" id="outTradeNo" name="outTradeNo">
			微信id:<input type="text" id="openId" name="openId"><br/>
			券id：<input type="text" id="cardId" name="cardId">
			银行号：<input type="text" id="tranid" name="tranid">
			数量:<input type="text" id="num" name="num"><br/>
			<input type="button" value="补发券"  onclick="bufa()"/>
		</form>
	</div>
</body>
</html>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	function bufa(){
		var mobile =  $.trim($("#mobile").val());
		var outTradeNo = $.trim($("#outTradeNo").val());
		var openId=  $.trim($("#openId").val());
		var cardId = $.trim($("#cardId").val());
		var tranid = $.trim($("#tranid").val());
		var num = $.trim($("#num").val());
		var str = "mobile="+mobile+"&outTradeNo="+outTradeNo+"&openId="+openId+"&cardId="+cardId+"&tranid="+tranid+"&num="+num;
		$.ajax({  
			url:"<%=basePath%>weixin/1119/buQuan.do",
		    type:"post",    //数据发送方式  
		    dataType:"text",
		    data:str,   //要传递的数据；就是上面序列化的值  
		    success: function(data) {
		    	alert(data);
		    }
		    });
	}
</script>