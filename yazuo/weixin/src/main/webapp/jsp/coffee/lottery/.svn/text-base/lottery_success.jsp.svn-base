<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class='redBg'>
<head>
<%@ include file="/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<script type="text/javascript" language="javascript">
	var ctx = "<%=basePath%>";
</script>
<link type="text/css" rel="stylesheet" href="<%=basePath %>css/wx_card.css" />
<script src="<%=basePath %>js/prize-list.js"></script>
<script src="<%=basePath %>js/jquery.mobile/jquery.mobile.js"></script>
<title>在线办卡</title>
</head>
<body>
<div data-role="page">
    <div data-role="header" class="shop-header">
    	<h1>申请会员卡</h1>
    </div>
    <div data-role="content" class="card-content">
    	<section class="buy-success">
        	<div class="success-word"><img src="<%=basePath %>imageCoffee/icon05.png" width="30" /><span> 恭喜您，领取成功！</span></div>
            <ul class="buy-list">
            	<li>奖品名称:${mallOrder.productInfo }</li>
                <li>数量：1</li>
            	<li>
                    <p>送至：${mallOrder.detailAddr }</p>
                </li>
            	<li><span class="gray999">奖品很快会为您寄出，你可在微信公众账号"中奖记录"菜单查询奖品详情。</span></li>
            </ul>
        </section>
         <div><button class="shop-btn" onclick="lookLotteryRecord(${brandId },'${mallOrder.openId}');">查看中奖记录</button></div>
    </div>
    
</div> 
</body>
</html>
