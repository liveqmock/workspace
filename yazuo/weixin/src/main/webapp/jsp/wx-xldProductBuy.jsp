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
<title>套餐抢购</title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/crm-wx.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/packagePayment.css" />
<script src="<%=basePath%>/js/md5.js"></script>
	<script src="<%=basePath%>/js/sha1.js"></script>
	<style>
		.state-disabled {
			color:white ! important;
			background: gray ! important;
			cursor: default ! important;
		}
	</style>
<script type="text/javascript">

	var oldTimeStamp ;//记住timestamp，避免签名时的timestamp与传入的timestamp时不一致
	var oldNonceStr ; //记住nonceStr,避免签名时的nonceStr与传入的nonceStr不一致
	var oldPackageString;
function getPackage() {
	var outTradeNo = document.getElementById("outTradeNo").value;
	var partnerId = document.getElementById("partnerId").value;
	var paternerKey = document.getElementById("paternerKey").value;
	var itemShort = document.getElementById("itemShort").value;
	var totalFee = $("#JS_totalPrice").text();
	var num = $("#JS_totalNum").val();
	var mobile = $("#phone").val();
	var cardId=$("#cardId").val();
	var banktype = "WX";
	var body =itemShort;//商品名称信息，这里由测试网页填入
	var fee_type = "1";//费用类型，这里1为默认的人民币
	var input_charset = "UTF-8";//字符集，这里将统一使用utf-8
	var notify_url ="<%=basePath%>weixin/1119/"+num+"/"+mobile+"/payResult.do?cardId="+cardId;//支付成功后将通知该地址
	var out_trade_no =outTradeNo;//订单号，商户需要保证该字段对于本商户的唯一性
	var partner = partnerId;//测试商户号
	var spbill_create_ip = getIp();
	var total_fee =totalFee*100;//totalFee 总金额,后续要调整为100分制，totalFee*100
	var partnerKey = paternerKey;
	//首先第一步：对原串进行签名，注意这里不要对任何字段进行编码。这里是将参数按照key=value进行字典排序后组成下面的字符串,在这个字符串最后拼接上key=XXXX。由于这里的字段固定，因此只需要按照这个顺序进行排序即可。
	var signString = "bank_type=" + banktype + "&body=" + body
			+ "&fee_type=" + fee_type + "&input_charset=" + input_charset
			+ "&notify_url=" + notify_url + "&out_trade_no=" + out_trade_no
			+ "&partner=" + partner + "&spbill_create_ip="
			+ spbill_create_ip + "&total_fee=" + total_fee + "&key="
			+ partnerKey;	
	var md5SignValue = ("" + CryptoJS.MD5(signString)).toUpperCase();
	//然后第二步，对每个参数进行url转码，如果您的程序是用js，那么需要使用encodeURIComponent函数进行编码。

	banktype = encodeURIComponent(banktype);
	body = encodeURIComponent(body);
	fee_type = encodeURIComponent(fee_type);
	input_charset = encodeURIComponent(input_charset);
	notify_url = encodeURIComponent(notify_url);
	out_trade_no = encodeURIComponent(out_trade_no);
	partner = encodeURIComponent(partner);
	spbill_create_ip = encodeURIComponent(spbill_create_ip);
	total_fee = encodeURIComponent(total_fee);

	//然后进行最后一步，这里按照key＝value除了sign外进行字典序排序后组成下列的字符串,最后再串接sign=value
	var completeString = "bank_type=" + banktype + "&body=" + body
			+ "&fee_type=" + fee_type + "&input_charset=" + input_charset
			+ "&notify_url=" + notify_url + "&out_trade_no=" + out_trade_no
			+ "&partner=" + partner + "&spbill_create_ip="
			+ spbill_create_ip + "&total_fee=" + total_fee;
	
	completeString = completeString + "&sign=" + md5SignValue;
	oldPackageString = completeString;//记住package，方便最后进行整体签名时取用
	return completeString;
	}

function getSign()
{
    var app_id = document.getElementById("appId").value;
    var app_key = document.getElementById("appKey").value;
    var nonce_str = oldNonceStr;
    var package_string = oldPackageString;
    var time_stamp = oldTimeStamp;
    //第一步，对所有需要传入的参数加上appkey作一次key＝value字典序的排序
    var keyvaluestring = "appid="+app_id+"&appkey="+app_key+"&noncestr="+nonce_str+"&package="+package_string+"&timestamp="+time_stamp;
    sign = CryptoJS.SHA1(keyvaluestring).toString();
    return sign;
}

//获取订单生成Ip
function getIp(){
	
	var str_tmp =  document.getElementById("spbillCreateIp").value ;
	return str_tmp;
}

function getTimeStamp()
{
    var timestamp=new Date().getTime();
    var timestampstring = timestamp.toString();//一定要转换字符串
    oldTimeStamp = timestampstring;
    return timestampstring;
}


function getNonceStr() {
	var $chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
	var maxPos = $chars.length;
	var noceStr = "";
	for (i = 0; i < 32; i++) {
		noceStr += $chars.charAt(Math.floor(Math.random() * maxPos));
	}
	oldNonceStr = noceStr;
	return noceStr;
}

//当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
document.addEventListener('WeixinJSBridgeReady', function onBridgeReady(){
	WeixinJSBridge.call('hideOptionMenu');
	$('#zhifu').removeAttr("disabled");
	});
	
function pay(){
	if ($('#zhifu').data('isDisabled')) {
		return;
	}
	$("#tip").text("");
	var	mobile = document.getElementById("phone").value;
	var num = $("#JS_totalNum").val();
	if(!validateNum(num)){
		return;
	}
	if(!validateMobile(mobile)){
		$("#phone").val("手机号为兑换唯一凭证");
		return ;//校验手机号
	}
	
	var itemShort = document.getElementById("itemShort").value;
	var totalFee = $("#JS_totalPrice").text();
	var nonceTr=getNonceStr();
	var cardId=$("#cardId").val();
	var requestStr = "mobile="+mobile+"&productInfo="+itemShort+"&totalFee="+totalFee+"&nonceTr="+nonceTr+"&cardId="+cardId+"&num="+num;
	$.ajax({  
		url:"<%=basePath%>weixin/1119/checkAndGet.do",
	    type:"post",    //数据发送方式  
	    dataType:"json",   //接受数据格式  
	    data:requestStr,   //要传递的数据；就是上面序列化的值  
	    success: function(data) {
	    	document.getElementById("outTradeNo").value=data.outTradeNo;
	    	document.getElementById("partnerId").value=data.partnerId;
	    	document.getElementById("paternerKey").value=data.paternerKey;
	        document.getElementById("appId").value=data.appId;
	        document.getElementById("appKey").value=data.appKey;
	        document.getElementById("spbillCreateIp").value=data.spbillCreateIp;
	        var appid = data.appId+"";
			WeixinJSBridge.invoke('getBrandWCPayRequest', {
				"appId" : appid, //公众号名称，由商户传入
				"timeStamp" :getTimeStamp(), //时间戳
				"nonceStr" :nonceTr, //随机串
				"package" : getPackage(),//扩展包
				"signType" :"sha1", //微信签名方式:1.sha1
				"paySign" :getSign()
		//微信签名
		}, function(res) {
			if (res.err_msg == "get_brand_wcpay_request:ok") {
				var outTradeNo = $("#outTradeNo").val();
				var appid=$("#appId").val();
				var red_url ="<%=base_Path%>weixin/1119/"+num+"/"+totalFee+"/"+cardId+"/"+outTradeNo+"/"+mobile+"/goPaySuccessPage.do?showwxpaytitle=1";
				 window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+red_url+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
			}
			// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
			//因此微信团队建议，当收到ok返回时，向商户后台询问是否收到交易成功的通知，若收到通知，前端展示交易成功的界面；若此时未收到通知，商户后台主动调用查询订单接口，查询订单的当前状态，并反馈给前端展示相应的界面。
		});
		//$('#zhifu').addClass('state-disabled').data('isDisabled','true');//添加disabled属性
	}
});
}

var reg =/^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\d{8}$/;// 手机号码验证
var exp   = /^[0-9]*[1-9][0-9]*$/;
function validateMobile(mobile){
	$("#tip").text("");
	if(mobile==""){
		/* alert("请输入手机号！"); */
		$("#tip").text("请输入手机号！");
		return false;
	}
	if(!reg.test(mobile)){
		/* alert("手机号不合法！"); */
		$("#tip").text("手机号不合法！");
		return false;
	}
	return true;
}
function validateNum(num){
	$("#tip").text("");
	if(isNaN(num)){ 
		/* alert("请输入数字"); */
		$("#tip").text("请输入数字！");
		$("#JS_totalNum").val("1");
		$("#JS_totalPrice").html(parseInt($("#JS_unitPrice").text()) * 1);
		return false;
	}else if(num<=0){
		/* alert("数量为正整数"); */
		$("#tip").text("数量为正整数！");
		$("#JS_totalNum").val("1");
		$("#JS_totalPrice").html(parseInt($("#JS_unitPrice").text()) * 1);
		return false;
	} else if(!exp.test(num)){
		/* alert("数量为正整数"); */
		$("#tip").text("数量为正整数！");
		$("#JS_totalNum").val("1");
		$("#JS_totalPrice").html(parseInt($("#JS_unitPrice").text()) * 1);
		return false;
	}
	$("#JS_totalPrice").html(parseInt($("#JS_unitPrice").text()) * num);
	return true;
	
}
function setTotalNum(step){
	var nowNum=parseInt($("#JS_totalNum").val());
	var newNum;
	if(!isNaN(nowNum)){ //是数字
	    if(nowNum === 1 && step === -1){
		    newNum=1;
		}else{
			newNum = (parseInt(nowNum) + step);
		}
		
		$("#JS_totalNum").val(newNum);
		$("#JS_totalPrice").html(parseInt($("#JS_unitPrice").text()) * newNum);
		
	}else{ //不是数字
	  /*  alert("购买数量必须为数字！"); */
		$("#tip").text("购买数量必须为数字！");
	}	 
}
</script> 
</head>

<body>
	<header><a href="<%=basePath%>weixin/1119/goEachPage.do?cardCode=${cardInfo.couponId}&showwxpaytitle=1" class="goBack"><i class="pIco goBackIco"></i>返回</a>小时代套餐抢购</header>
    
    <h3 class="payFormItemTitle">套餐名称：新辣道小时代套餐之${cardInfo.couponName}</h3>
    <div class="payFormItem">
    	<p><label>单  价：</label><span id="JS_unitPrice">${cardInfo.couponAmount }</span></p>
        <p><label>数  量：</label>
            <span>
            	<a href="javascript:;" class="pIco numReduceIco" onclick="setTotalNum(-1)"></a>
                <input type="text" class="totalNumTxt" style="margin-top:7px;" value="1" id="JS_totalNum" onblur="validateNum(this.value)"/>
                <a href="javascript:;" class="pIco numPlusIco" onclick="setTotalNum(1)"></a>
            </span></p>
        <p><label>总  价：</label><span class="totalPrice" id="JS_totalPrice">${cardInfo.couponAmount }</span></p>
    </div>
    
    <h3 class="payFormItemTitle">请输入您的手机号：</h3>
    <div class="payFormItem">
    	<p><label>手机号：</label><input type="tel" class="buyerPhone" id="phone" name="phone" value="手机号为兑换唯一凭证" onfocus="javascript:this.value=''"/></p>     
    </div>
    
     <a href="javascript:;" class="payBtn" id="zhifu" onClick="pay()">微信支付</a>
	<h3 class="payFormItemTitle"><span id="tip" style="color:red"></span></h3>
 	<input type="hidden" id="outTradeNo" name="outTradeNo" value=""/>
	<input type="hidden" id="appId" name="appId" value="" />
	<input type="hidden" id="openId" name="openId" value=""/>
	<input type="hidden" id="itemShort" name="itemShort" value="小时代套餐之${cardInfo.couponName}">
	<input type="hidden" id="spbillCreateIp" name="spbillCreateIp"><!-- ip地址 -->
	<input type="hidden" id="partnerId" />
	<input type="hidden" id="appKey" />
	<input type="hidden" id="paternerKey" />
	<input type="hidden" id="cardId" value="${cardCode }"/><!-- 券id -->
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>   
</body>
</html>
