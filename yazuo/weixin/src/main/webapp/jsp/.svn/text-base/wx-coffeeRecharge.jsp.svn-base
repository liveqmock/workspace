<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
String base_Path = request.getScheme() + "://"+ request.getServerName() + path;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0, maximum-scale=1.0"/> 
<title>充值中心</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath %>/css/recharge.css" />
	<script src="<%=basePath %>/js/md5.js"></script>
	<script src="<%=basePath %>/js/sha1.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>/js/recharge.js"></script>
<script type="text/javascript">
	var brandId='${brandId}';
	var oldTimeStamp ;//记住timestamp，避免签名时的timestamp与传入的timestamp时不一致
	var oldNonceStr ; //记住nonceStr,避免签名时的nonceStr与传入的nonceStr不一致
	var oldPackageString;
function getPackage() {
	var	card = $("#JS_cardType option:selected").val();
	var outTradeNo = document.getElementById("outTradeNo").value;
	var partnerId = document.getElementById("partnerId").value;
	var paternerKey = document.getElementById("paternerKey").value;
	var totalFee = $("#JS_moneyType option:selected").val();
	var mobile = $("#mobile").val();
	var banktype = "WX";
	var body =totalFee+"充值卡";//商品名称信息，这里由测试网页填入
	var fee_type = "1";//费用类型，这里1为默认的人民币
	var input_charset = "UTF-8";//字符集，这里将统一使用utf-8
	var notify_url ="<%=basePath%>/weixin/"+brandId+"/"+card+"/"+mobile+"/coffeePayResult.do";//支付成功后将通知该地址
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
	});
function changeFee(){
	$("#tip").text("");
}	
function pay(){
	$('#zhifu').hide();
	$('#tip').text('提交中请稍后.....');
	var	card = $("#JS_cardType option:selected").val();
	var mobile =$("#mobile").val();
	var totalFee = $("#JS_moneyType option:selected").val();
	var nonceTr=getNonceStr();
	var requestStr = "card="+card+"&mobile="+mobile+"&productInfo="+totalFee+"充值卡&totalFee="+totalFee+"&nonceTr="+nonceTr;
	var flag ='1';
	var checkStr = "card="+card+"&totalFee="+totalFee+"&mobile="+mobile;
	/**
	* 判断购买规则,1.判断库存，判断购买限制
	**/
	$.ajax({
		url:"${pageContext.request.contextPath}/weixin/"+brandId+"/checkStoreCard.do",
	    type:"post",    //数据发送方式  
	    dataType:"json",   //接受数据格式  
	    async: false, //设为false就是同步请求
	    data:checkStr,   //要传递的数据；就是上面序列化的值  
	    success: function(data) {
	    	flag = data.flag;
	    	if(flag=='1'){//flag==1 校验不通过，提示用户
	    		$("#tip").text(data.message);
	    		$("#tip").show();
	    		$('#zhifu').show();
	    	}
	    }
	});
	
	if(flag!='1'){
		$.ajax({  
			url:"<%=basePath%>/weixin/"+brandId+"/getProductInfo.do",
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
					var red_url ="<%=base_Path%>/weixin/"+brandId+"/"+card+"/"+totalFee+"/"+outTradeNo+"/goCoffeeSuccessPage.do?showwxpaytitle=1";
					//window.location.href=red_url;
					window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+red_url+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
				}else{
					$("#tip").text("支付失败,请稍后重试！");
    				$('#zhifu').show();//点击提交后隐藏起来，防止重复提交
				}
				// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
				//因此微信团队建议，当收到ok返回时，向商户后台询问是否收到交易成功的通知，若收到通知，前端展示交易成功的界面；若此时未收到通知，商户后台主动调用查询订单接口，查询订单的当前状态，并反馈给前端展示相应的界面。
			});
			}
		});
	}
}
/*获取储值卡信息*/
function queryCardInfo(){
	//判断手机号输入
	var userPhone=$.trim($("#phone").val());
		var reg = /^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\d{8}$/;
		if(userPhone === ''){
			$("#JS_errorTip").text("请输入手机号");
			$("#JS_errorTip").show();
			return;	
		}else if(!reg.test(userPhone)){
			$("#JS_errorTip").text("输入的手机号码有误");
			$("#JS_errorTip").show();
			return;		
		}else{
			//验证通过  
			$.ajax({
			 type: "post",
			 url:  "<%=basePath%>/weixin/"+brandId+"/goObtainCardInfo.do",
			 cache: false,
			 dataType : "json",
			 data: 'mobile='+userPhone,
			 success: function(restr){
				 var htm = '';
				 if("无储值卡信息"!=restr.key){
					 $.each(restr.key,function(i,val){
						htm +="<option value='"+val+"'>"+val+"</option>";
					 });
					 $("#JS_cardType").append(htm);
					 $("#mobile").val(userPhone);
					 $("#JS_phoneNumForm").hide();
					 $("#JS_selectTypePart").show();	
			 	 }else{
			 		$("#JS_errorTip").text(restr.key);
					$("#JS_errorTip").show();
					return;		
				 }
			}
	  	});
	}
}

$(function(){
	var trade_state = '${trade_state}';
	if($.trim(trade_state)=='0'){
		$("#JS_phoneNumForm").hide();
		$("#JS_selectTypePart").hide();	
		$("#JS_successTipPart").show();
	}else if(trade_state=='1'){
		$("#JS_phoneNumForm").hide();
		$("#JS_selectTypePart").hide();	
		$("#JS_failTipPart").show();
	}
	
});
</script> 
</head>

<body>
    <header>在线充值</header>
    
    <div class="rechargeParts" id="JS_phoneNumForm" style="display:block">
    	<h3>请输入您的手机号：</h3>
        <p class="formItem"><label for="userPhone">手 机 号：</label><input type="tel" name="phone" class="formTxt" id="phone" onfocus="javascript:this.value=''"/></p>
        <p class="errorTip" id="JS_errorTip"></p>
        <a href="javascript:;" class="rechargeBtn" id="JS_submitPhoneNumBtn" onClick="queryCardInfo()">提交</a>
    </div>
    
    <div class="rechargeParts" id="JS_selectTypePart">
    	<h3>选择您要充值的储值卡</h3>
        <select name="cardType" class="fadeSelect" id="JS_cardType">     
        </select>  
        	
        <h3 class="moneyKind">选择您的充值金额</h3> 
         <select name="moneyType" class="fadeSelect" id="JS_moneyType" onchange="changeFee()">     
            <option selected="selected" value="300">300元</option>     
            <option value="600">600元</option>     
            <option value="900">900元</option> 
        </select> 
        <p class="errorTip" id="tip"></p>
        <a href="javascript:;" class="rechargeBtn" id="zhifu" onClick="pay()">微信支付</a>
    </div>
    
    <div class="rechargeParts" id="JS_successTipPart">
    	<p class="successTip"><span> <i class="rechargeIco successIco"></i> 恭喜您，充值成功！ </span></p>
        
        <div class="successInfo">
        	<p>储值卡号：${card}</p>
            <p>充值金额：${totalFee}元</p>
        </div>
        <a href="<%=basePath %>/weixin/phonePage/registerPage.do?brandId=${brandId}&&weixinId=${openid}" class="rechargeBtn">查看余额</a>
    </div>
    <div class="rechargeParts" id="JS_failTipPart">
    	<p class="successTip"><span> <i class="rechargeIco successIco"></i> 充值错误！ </span></p>
        
        <div class="successInfo">
            <p>请您致电客服人员进行人工充值。客服电话：4008827456。</p>
        </div>
    </div>
    
 	<input type="hidden" id="outTradeNo" name="outTradeNo" value=""/>
	<input type="hidden" id="appId" name="appId" value="" />
	<input type="hidden" id="openId" name="openId" value=""/>
	<input type="hidden" id="spbillCreateIp" name="spbillCreateIp"><!-- ip地址 -->
	<input type="hidden" id="partnerId" />
	<input type="hidden" id="appKey" />
	<input type="hidden" id="paternerKey" />
	<input type="hidden" id="mobile"/>
</body>
</html>
