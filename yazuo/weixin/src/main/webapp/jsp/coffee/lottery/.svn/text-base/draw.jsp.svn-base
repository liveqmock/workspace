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
<script src="<%=basePath %>js/common.js"></script>
<title>在线办卡</title>
</head>
<body>
<input type="hidden" name="membershipId" id="membershipId" value="${membershipId }"/>
<input type="hidden" name="lotterName" id="lotterName" value="${lotteryName }"/>
<input type="hidden" name="weixinId" id="weixinId" value="${weixinId }"/>
<input type="hidden" name="brandId" id="brandId" value="${brandId }"/>
<input type="hidden" name="orderId" id="orderId" value="${orderId }"/>

<div data-role="page">
    <div data-role="header" class="shop-header">
    	<a target="_self" href="javascript:window.history.go(-1);" data-role="button"><img src="<%=basePath %>imageCoffee/back.png" width="16" />返回</a>
    	<h1>办卡抽奖活动</h1>
    </div>
    
    <div data-role="content" class="shop-content">
    	<section class="buy-module">
        	<h1>中奖信息</h1>
            <ul>
            	<li>${lotteryItemName }&nbsp;&nbsp;${lotteryName }<!--  【奖项名称】【奖品名称】--></li>
                <li>数量：1</li>
            </ul>
        </section>
        <section class="buy-module">
        	<h1>收货地址</h1>
            <ul>
            	<li><p class="clear-wrap"><span class="left">收&nbsp;&nbsp;货&nbsp;&nbsp;人：</span>
            		<span class="left"><input type="text" class="customer-add-input"  name="receiver" id="receiver" value="" /></span></p>
            	</li>
                <li><p class="clear-wrap"><span class="left">联系方式：</span>
                	<span class="left"><input type="text" class="customer-add-input" name="mobile" id="mobile" value="" /></span></p>
                </li>
                <li><p class="clear-wrap"><span class="left">详细地址：</span>
                	<span class="left"><input type="text" class="customer-add-input" name="detail_address" id="detail_address" value="" /></span></p>
                </li>
            </ul>
        </section>
        <span id="span_error"></span>
        <button class="shop-btn draw-submit" id="btn_shop_submit" onclick="recordSaveAddress();">提交</button>
    </div>
    
</div>
<div class="popUp" id="comfirm_address_div" style='display:none;top:20px;'>
	<div class="popInfo pop-cont">
		<h1 class="pop-h1">领奖确认</h1>
		<p class="pop-tit">确认要提交收货信息吗？</p>
		<p class="gray999">收货信息十分重要，我们将按照信息中的地址发送奖品。收货信息提交后无法再修改。</p>
	</div>
    <div class="popBtns">
	   <a class="btn2 draw-submit" id="btn_ok" href="javascript:submitAddress();">确定</a>
	   <a href="javascript:btnCancle();"  class="grayBtn2" id='btn_close'>取消</a>
	</div>
 
 </div>
</body>
</html>
