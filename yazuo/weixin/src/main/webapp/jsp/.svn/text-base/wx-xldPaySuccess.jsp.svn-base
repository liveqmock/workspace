<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String base_Path = request.getScheme() + "://"
		+ request.getServerName() 
		+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0, maximum-scale=1.0"/> 
<title>购买成功</title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/crm-wx.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/packagePayment.css" />
</head>

<body>
	<header>套餐抢购</header>
 	
    <h3 class="successTips"><span class="pIco">恭喜您，购买成功！</span></h3>
    
    <div style="text-align:center;margin-bottom:20px"><p><span>每个人都有小时代<br/>从新辣道鱼火锅开始</span></p></div>
    
    <ul class="successDetail">
    	<li><label>&nbsp;名&nbsp;&nbsp;&nbsp;&nbsp;称&nbsp;：</label>新辣道小时代套餐之${cardInfo.couponName}</li>
        <li><label>&nbsp;数&nbsp;&nbsp;&nbsp;&nbsp;量&nbsp;：</label>${num }</li>
        <li><label>&nbsp;总&nbsp;&nbsp;&nbsp;&nbsp;价&nbsp;：</label>${totalFee }</li>
        <li><label>有&nbsp;效&nbsp;期&nbsp;：</label>${cardInfo.expireBegin }至${cardInfo.expireEnd}</li>
        <li><label>使用范围：</label>全国直营门店<font color="red">(加盟店不参加本活动)</font></li>
        <li><label>兑换方式：</label>凭购买时录入的手机号</li>
    </ul>
   
   <div class="bugSuccessBtns clear_wrap">
   		<a href="<%=basePath %>weixin/phonePage/registerPage.do?brandId=${brandId}&&weixinId=${openid}" class="btn successBtn">立即查看套餐</a>
        <a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appid}&redirect_uri=<%=base_Path%>weixin/1119/goRecharge.do?showwxpaytitle=1&response_type=code&scope=snsapi_base&state=123#wechat_redirect" class="btn successBtn">购买其他套餐</a>
   </div>
<script type="text/javascript">
//当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
document.addEventListener('WeixinJSBridgeReady', function onBridgeReady(){
	WeixinJSBridge.call('hideOptionMenu');
	});
	</script>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>  
</body>
</html>
