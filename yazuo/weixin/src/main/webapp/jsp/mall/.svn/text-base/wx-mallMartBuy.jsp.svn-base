<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
String base_Path = request.getScheme() + "://"+ request.getServerName() + path;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0, maximum-scale=1.0"/> 
<title>购买套餐</title>
	<c:choose>
		<c:when test="${dict.pageColor eq '#FEC500'  or dict.pageColor eq '#fec500'}">
			<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/wx_shop_yellow.css" />
		</c:when>
		<c:when test="${dict.pageColor eq '#56B072'  or dict.pageColor eq '#56b072'}">
			<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/wx_shop_green.css" />
		</c:when>
		<c:otherwise>
			<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/wx_shop_orange.css" />
		</c:otherwise>
	</c:choose>
<script src="${pageContext.request.contextPath}/js/MapClass.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.mobile/jquery.mobile.js"></script>
<script src="${pageContext.request.contextPath}/js/md5.js"></script>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script src="${pageContext.request.contextPath}/js/buy-group.js"></script>
<script type="text/javascript">
	var brandId='${brandId}';
	var cardMap=new MapClass();//<id,cardInfo>
	var out_trade_no ;
	var nonceTr;
	//当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady(){
		WeixinJSBridge.call('hideOptionMenu');
	});
	//选卡，显示积分
	function onSelectCardAuto(){
		$("#eachIntegral").text("");
		var	cardNo = $("#JS_cardType option:selected").val();
		if(cardNo==""){
			$("#eachIntegral").text("0");
			return ;
		};
		var selectArray = cardMap.get(cardNo);
		$("#eachIntegral").text(selectArray[2]);
		$("#tip").text("");
	}
	var password_1;
	var totalIntegral_1;
	var cardNo_1;
	var payType_1;
	var weixinId_1;
	var requestStr_1;
	var totalFee_1;
	/**
	 * 支付方法
	 */
	function pay(){
		$('#zhifu').hide();//点击提交后隐藏起来，防止重复提交
		$('#tip').text('');
		$('#tip').text('提交中请稍后.....');
		var weixinId = $("#weixinId").val();
		var totalFee =  (parseFloat($("#JS_totalPrice").text())).toFixed(2);
		//$("#JS_totalPrice").text();
		var goodInfo = $("#itemShort").val();
		var num = $("#JS_totalNum").val();
		var payType=$("#payType").val();
		var goodId = $("#goodId").val();
		var buy_limit_is = $("#buy_limit_is").val();
		var buy_limit_type = $("#buy_limit_type").val();
		var buy_limit_num = $("#buy_limit_num").val();
		var integral = $("#integral").val();
		var totalIntegral = parseInt(integral)*num;
		$("#payJiFen").text(totalIntegral+"积分");
		var requestStr = "weixinId="+weixinId+"&integral="+totalIntegral+"&goodInfo="+goodInfo+"&goodId="+goodId+"&num="+num+"&payType="+payType;
		var checkStr = "weixinId="+weixinId+"&goodId="+goodId+"&num="+num+"&payType="+payType+"&buy_limit_is="+buy_limit_is+"&buy_limit_type="+buy_limit_type+"&buy_limit_num="+buy_limit_num;
		//var flag ='';
		
		/**  判断购买规则,1.判断库存，判断购买限制 **/
		if(!checkGuize(checkStr)){
			$('#zhifu').show();//点击提交后隐藏起来，防止重复提交
			return;
		}
		
		/** 积分支付，判断积分是否足够 */
		if(payType!=1&&!checkIntegral()){
			$('#zhifu').show();//点击提交后隐藏起来，防止重复提交
			return;
		}
		/**
		* 判断密码是否正确
		*/
		//("" + CryptoJS.MD5(signString))
		/*
		 * 非现金支付,弹出密码框，获取密码，校验密码是否正确
		 */
		if(payType!=1){
			var	cardNo = $("#JS_cardType option:selected").val();
			var selectArray = cardMap.get(cardNo);
			var password = selectArray[1];
			requestStr+="&cardNo="+cardNo+"&pwd="+password;
			var userPwd="";
			var pwdIf = false;
			if(!checkCardType(cardNo)){
				pwdIf=true;
			}
			password_1=password;
			totalIntegral_1=totalIntegral;
			cardNo_1=cardNo;
			payType_1=payType;
			weixinId_1=weixinId;
			requestStr_1=requestStr;
			totalFee_1=totalFee;
			if(!(pwdIf||(password==("" + CryptoJS.MD5("000000")).toUpperCase()) || password==("" + CryptoJS.MD5(cardNo.substring(cardNo.length-6,cardNo.length))).toUpperCase() )){ //("" + CryptoJS.MD5(signString)).toUpperCase()
				popPassword();//密码框
				
				$("#pop-sure").click(function(){
					var userPwd = $("#userPwd").val();
					if(userPwd==""){
						$("#errorPwd").text("请输入6位密码");
					}
					if(password_1!=("" + CryptoJS.MD5(userPwd)).toUpperCase()){
						$("#errorPwd").text("密码错误");
					}else{
						popClose();
						if(checkTradeFun(totalIntegral_1,cardNo_1,userPwd)){
							if(payType_1==2){//2是积分消费
								beginPay(payType_1,weixinId_1,requestStr_1,totalFee_1);//开始支付
							}
							if(payType_1==3){//3是现金+积分消费
								requestStr_1+="&totalFee="+totalFee_1;
								if(jifenxiaofei(requestStr_1)){
									requestStr_1+="&outTradeNo="+out_trade_no+"&nonceTr="+nonceTr;
									xinjinxiaofei(requestStr_1);
								}else{
									$("#tip").text("积分交易失败，请稍后重试！");
									$('#zhifu').show();//点击提交后隐藏起来，防止重复提交
								}
							}
						}
					}
				});
			}else{
				if(password==("" + CryptoJS.MD5("000000")).toUpperCase()){
					userPwd="000000";
				}else{
					userPwd=cardNo.substring(cardNo.length-6,cardNo.length);
				}
				if(checkTradeFun(totalIntegral,cardNo,userPwd)){
					if(payType==2){//2是积分消费
						beginPay(payType,weixinId,requestStr,totalFee);//开始支付
					}
					if(payType==3){//3是现金+积分消费
						requestStr+="&totalFee="+totalFee;
						if(jifenxiaofei(requestStr)){
							requestStr+="&outTradeNo="+out_trade_no+"&nonceTr="+nonceTr;
							xinjinxiaofei(requestStr);
						}else{
							$("#tip").text("积分交易失败，请稍后重试！");
							$('#zhifu').show();//点击提交后隐藏起来，防止重复提交
						}
					}
				}
			}
		}else{
			beginPay(payType,weixinId,requestStr,totalFee);//开始支付
		}
	}
	
	function checkGuize(checkStr){
		var ff =true;
		$.ajax({
			url:"${pageContext.request.contextPath}/weixin/"+brandId+"/checkBuy.do",
		    type:"post",    //数据发送方式  
		    dataType:"json",   //接受数据格式  
		    async: false, //设为false就是同步请求
		    data:checkStr,   //要传递的数据；就是上面序列化的值  
		    success: function(data) {
		    	if(data.flag=='1'){//flag==1 校验不通过，提示用户
		    		ff=false;
		    		$("#tip").text(data.message);
		    		$("#JS_totalNum").val("1");
		    		$("#JS_totalPrice").html( (parseFloat($("#JS_unitPrice").text())).toFixed(2) * 1);
		    		$("#JS_totalJiFen").html(parseInt($("#JS_unitJiFen").text()) * 1);
		    	}
		    }
		});
		return ff;
	}
	
	function checkCardType(cardNo){
		var flag ='';
		var checkCardTypeStr = "cardNo="+cardNo;
		/**校验是否有交易限制**/
		$.ajax({
			url:"${pageContext.request.contextPath}/weixin/"+brandId+"/checkCardType.do",
		    type:"post",    //数据发送方式  
		    dataType:"json",   //接受数据格式  
		    async: false, //设为false就是同步请求
		    data:checkCardTypeStr,   //要传递的数据；就是上面序列化的值  
		    success: function(data) {
		    	flag = data.flag;
		    }
		});
		if(flag=='1'){
			return false;
		}else{
			return true;
		}
	}
	//校验交易限制
	function checkTradeFun(totalIntegral,cardNo,password){
		var checkCardTradeStr = "integral="+totalIntegral+"&cardNo="+cardNo+"&pwd="+password;
		var flag ='';
		/**校验是否有交易限制**/
		$.ajax({
			url:"${pageContext.request.contextPath}/weixin/"+brandId+"/checkCardTrade.do",
		    type:"post",    //数据发送方式  
		    dataType:"json",   //接受数据格式  
		    async: false, //设为false就是同步请求
		    data:checkCardTradeStr,   //要传递的数据；就是上面序列化的值  
		    success: function(data) {
		    	flag = data.flag;
		    	if(flag=='1'){//flag==1 校验不通过，提示用户
		    		$("#tip").text(data.message);
		    		$('#zhifu').show();//点击提交后隐藏起来，防止重复提交
		    	}
		    }
		});
		if(flag=='1'){
			return false;
		}else{
			return true;
		}
	}
	
	function beginPay(payType,weixinId,requestStr,totalFee){
		var appid='${dict.appId}';
			/**
			* 1. 现金消费
			* 2. 纯积分消费
			* 3. 现金+积分消费
			*
			*/
			if(payType==2){
				
				// 如果是纯积分消费，单独走销分，生券接口
				$.ajax({
					url:"${pageContext.request.contextPath}/weixin/"+brandId+"/integralPay.do",
					type:"post",    //数据发送方式  
		    		dataType:"json",   //接受数据格式  
		    		data:requestStr,   //要传递的数据；就是上面序列化的值  
		    		success: function(data) {
		    			if(data.flag=='success'){
		    				var red_url ="<%=base_Path %>/weixin/"+brandId+"/"+weixinId+"/"+data.outTradeNo+"/goMallSuccessPage.do?showwxpaytitle=1";
		    				window.location.href=red_url;
    						//window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+red_url+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
		    			}else{
		    				$("#tip").text("支付失败,请稍后重试！");
		    				$('#zhifu').show();//点击提交后隐藏起来，防止重复提交
		    				//window.location.href="${pageContext.request.contextPath}/weixin/"+brandId+"/"+weixinId+"/goMallErrorPage.do?showwxpaytitle=1";
		    			}
		    		}
				});
			}else{
				requestStr+="&totalFee="+totalFee;
				$.ajax({
	    			url:"${pageContext.request.contextPath}/weixin/"+brandId+"/obtainGoodInfo.do",
	    		    type:"post",    //数据发送方式  
	    		    dataType:"json",   //接受数据格式  
	    		    data:requestStr,   //要传递的数据；就是上面序列化的值  
	    		    success: function(data) {
	    		    	var v2v3flag = data.v2v3flag;
	    		    	if(v2v3flag=='2'){
		    				WeixinJSBridge.invoke('getBrandWCPayRequest', {
		    					"appId" : data.order.appId, //公众号名称，由商户传入
		    					"timeStamp" :data.order.timestamp, //时间戳
		    					"nonceStr" :data.order.noncestr, //随机串
		    					"package" : data.order.package,//扩展包
		    					"signType" :"sha1", //微信签名方式:1.sha1
		    					"paySign" :data.order.paySign
		    				//微信签名
		    				}, function(res) {
		    					
		    					if (res.err_msg == "get_brand_wcpay_request:ok") {
		    						var red_url ="<%=base_Path %>/weixin/"+brandId+"/"+weixinId+"/"+data.outTradeNo+"/goMallSuccessPage.do?showwxpaytitle=1";
		    						//window.location.href=red_url;
		    						window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+red_url+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
		    					}else{
		    						$("#tip").text("支付失败,请稍后重试！");
				    				$('#zhifu').show();//点击提交后隐藏起来，防止重复提交
		    					}
		    					// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
		    					//因此微信团队建议，当收到ok返回时，向商户后台询问是否收到交易成功的通知，若收到通知，前端展示交易成功的界面；若此时未收到通知，商户后台主动调用查询订单接口，查询订单的当前状态，并反馈给前端展示相应的界面。
		    				});
		    			}else if(v2v3flag=='3'){
	    						WeixinJSBridge.invoke('getBrandWCPayRequest', {
	    							"appId" : data.order.appId, //公众号名称，由商户传入
	    							"timeStamp" :data.order.timeStamp, //时间戳
	    							"nonceStr" : data.order.nonceStr, //随机串
	    							"package" : data.order.package,//扩展包
	    							"signType" :"MD5", //微信签名方式v3 MD5
	    							"paySign" :data.order.paySign
	    						//微信签名
	    						}, function(res) {
	    							
	    							if (res.err_msg == "get_brand_wcpay_request:ok") {
			    						var red_url ="<%=base_Path %>/weixin/"+brandId+"/"+weixinId+"/"+data.outTradeNo+"/goMallSuccessPage.do?showwxpaytitle=1";
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
	    		    }
	    		});
			}
	}
	
	/*积分消费*/
	function jifenxiaofei(requestStr){
		var flag =false;
		/**校验是否有交易限制**/
		$.ajax({
			url:"${pageContext.request.contextPath}/weixin/${brandId}/jiFenXiaoFei.do",
		    type:"post",    //数据发送方式  
		    dataType:"json",   //接受数据格式  
		    async: false, //设为false就是同步请求
		    data:requestStr,   //要传递的数据；就是上面序列化的值  
		    success: function(data) {
		    	flag = data.flag;
		    	if(flag){
		    		out_trade_no=data.outTradeNo;
		    		nonceTr=data.nonceTr;
		    	}
		    }
		});
		return flag;
	}
	/*现金消费*/
	function xinjinxiaofei(requestStr){
		$.ajax({
			url:"${pageContext.request.contextPath}/weixin/${brandId}/queryProductInfo.do",
		    type:"post",    //数据发送方式  
		    dataType:"json",   //接受数据格式  
		    data:requestStr,   //要传递的数据；就是上面序列化的值  
		    success: function(data) {
		    	var v2v3flag = data.v2v3flag;
		    	if(v2v3flag=='2'){
    				WeixinJSBridge.invoke('getBrandWCPayRequest', {
    					"appId" : data.order.appId, //公众号名称，由商户传入
    					"timeStamp" :data.order.timestamp, //时间戳
    					"nonceStr" :data.order.noncestr, //随机串
    					"package" : data.order.package,//扩展包
    					"signType" :"sha1", //微信签名方式:1.sha1
    					"paySign" :data.order.paySign
    				//微信签名
    				}, function(res) {
    					
    					if (res.err_msg == "get_brand_wcpay_request:ok") {
    						var red_url ="<%=base_Path %>/weixin/"+brandId+"/"+weixinId+"/"+data.outTradeNo+"/goMallSuccessPage.do?showwxpaytitle=1";
    						//window.location.href=red_url;
    						window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+red_url+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
    					}else{
    						$("#tip").text("支付失败,请稍后重试！");
		    				$('#zhifu').show();//点击提交后隐藏起来，防止重复提交
    					}
    					// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
    					//因此微信团队建议，当收到ok返回时，向商户后台询问是否收到交易成功的通知，若收到通知，前端展示交易成功的界面；若此时未收到通知，商户后台主动调用查询订单接口，查询订单的当前状态，并反馈给前端展示相应的界面。
    				});
    			}else if(v2v3flag=='3'){
						WeixinJSBridge.invoke('getBrandWCPayRequest', {
							"appId" : data.order.appId, //公众号名称，由商户传入
							"timeStamp" :data.order.timeStamp, //时间戳
							"nonceStr" : data.order.nonceStr, //随机串
							"package" : data.order.package,//扩展包
							"signType" :"MD5", //微信签名方式v3 MD5
							"paySign" :data.order.paySign
						//微信签名
						}, function(res) {
							
							if (res.err_msg == "get_brand_wcpay_request:ok") {
	    						var red_url ="<%=base_Path %>/weixin/"+brandId+"/"+weixinId+"/"+data.outTradeNo+"/goMallSuccessPage.do?showwxpaytitle=1";
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
			}
		});
	}
</script> 
</head>
<body>
<div data-role="page">
    <div data-role="header" class="shop-header">
    	<a target="_self" href="${pageContext.request.contextPath}/weixin/${brandId}/goodInfoPage.do?goodCode=${goodvo.id}&weixinId=${weixinId }&showwxpaytitle=1" data-role="button"><img src="${pageContext.request.contextPath}/imageMall/back.png" width="16" />返回</a>
    	<h1>购买商品</h1>
    </div>
        <div data-role="content" class="shop-content">
    	<section class="buy-module">
        	<h1>套餐名称：${goodvo.name}</h1>
            <ul>
            	<li class="clear-wrap">
	            	<span class="left">单价：</span>
	            	<span class="right orange">
			    		<c:if test="${goodvo.payment != 2 }"> <!-- 现金消费 -->
			    			￥<span id="JS_unitPrice" >
				    			${goodvo.nowPriceCash}
				    		</span>
				    	</c:if>
				    	<c:if test="${goodvo.payment == 2}"> <!-- 积分消费 -->
				    		 <span id="JS_unitJiFen" >${goodvo.nowPriceIntegral}</span>积分
				    	</c:if>
				    	<c:if test="${goodvo.payment == 3}"> <!-- 积分消费 -->
				    		+<span id="JS_unitJiFen" >${goodvo.nowPriceIntegral}</span>积分
				    	</c:if>
				    </span>
            	</li>
            	<li class="clear-wrap">
            		<span class="left">请选择购买数量：</span>
            		<span class="right">
	            		<em class="shop-buy-num reduce-icon" onclick="setTotalNum(-1)"></em>
	            		<input type="text" class="shop-buy-num-input" value="1" id="JS_totalNum" onblur="validateNum(this.value)"/>
	            		<em class="shop-buy-num add-icon" onclick="setTotalNum(1)"></em>
            		</span>
            	</li>
            	<li class="clear-wrap">
            		<span class="left">总价：</span>
            		<span class="right orange">
            		
            			<c:if test="${goodvo.payment != 2 }"> <!-- 现金消费 -->
			    			￥<span id="JS_totalPrice">
				    			${goodvo.nowPriceCash}
				    		</span>
				    	</c:if>
				    	<c:if test="${goodvo.payment == 2}"> <!-- 积分消费 -->
				    		<span id="JS_totalJiFen" >${goodvo.nowPriceIntegral}</span>积分
				    	</c:if>
				    	<c:if test="${goodvo.payment == 3}"> <!-- 积分消费 -->
				    		+<span id="JS_totalJiFen" >${goodvo.nowPriceIntegral}</span>积分
				    	</c:if>
			    	</span>
            	</li>
            </ul>
        </section>
         <c:if test="${goodvo.payment != 1 }"> <!-- 积分消费 -->
	    	<section class="buy-module">
	        	<h1>支付方式：<span class="buy-sm">（现金部分将直接通过微信支付）</span></h1>
	            <ul>
	            	<li><span>卡号：</span>
		            	<span class="orange">
			            	<select name="cardType" class="fadeSelect" id="JS_cardType" onchange="onSelectCardAuto()">
					        	<!-- <option value="-1">--请选择卡--</option> -->
					        	<c:forEach items="${cardList }" var="item" varStatus="i">
					        		<option value="${item.cardNo }">${item.cardNo }</option>
					        		<script type="text/javascript">
					        			var itm = new Array(3);
					        			itm[0]="${item.cardNo}";
					        			itm[1]="${item.consumePwd}";
					        			itm[2]="${item.integralAvailable}";
					        			cardMap.put("${item.cardNo}",itm);
					        		</script>
					        	</c:forEach>
					        </select> 
					    </span>
			        </li>
	            	<li class="buy-balance clear-wrap">
	            	<span class="left">当前积分余额：</span>
	            	<span class="right orange" id="eachIntegral">
						<c:choose>
							<c:when test="${empty cardList}">
							0
							</c:when>
							<c:otherwise>
								${cardList[0].integralAvailable}
							</c:otherwise>
						</c:choose>
					</span></li>
	            </ul>
        	</section>
    </c:if>
	        <div class="error-message" id="tip"></div>
        
            <button class="shop-btn" id="zhifu" onClick="pay()">确认支付</button>
    </div>
</div>
    
<div class="mobile_popBg" id="pop-bg" style="display:none;"></div>
<div class="pop-div card-password hide" id="card-password">
    <h1>请输入积分卡密码</h1>
    <div class="pay-ingNum">
        <p>您需支付</p>
        <p class="pay-num" id="payJiFen">3000积分</p>
    </div>
    <div class="paw-input"><input type="password" id="userPwd" name="userPwd" maxlength="6"/></div>
    <div class="error-message" id="errorPwd"></div>
    <div class="pop_sub">
    	<a href="javascript:void(0);" id="pop-cancle" class="shop-btn3 marR5">取消</a>
    	<a href="javascript:void(0);" id="pop-sure" class="shop-btn2">确定</a>
    </div>
</div>
    
    
 	<input type="hidden" id="itemShort" name="itemShort" value="${goodvo.name}"><!-- 商品 -->
	<input type="hidden" id="goodId" value="${goodvo.id }"/><!-- 商品id -->
	<input type="hidden" id="payType" value="${goodvo.payment }"/><!-- 商品支付类型 -->
	<input type="hidden" id="weixinId" value="${weixinId }" />
	<input type="hidden" id="buy_limit_is" value="${goodvo.buyLimitIs }" />
	<input type="hidden" id="buy_limit_type" value="${goodvo.buyLimitType }" />
	<input type="hidden" id="buy_limit_num" value="${goodvo.buyLimitNum }" />
	<input type="hidden" id="integral" value="${goodvo.nowPriceIntegral }" />
	
	
<script type="text/javascript">
function checkIntegral(){
	var cardIntegral = parseInt( $("#eachIntegral").text());
	var num = $("#JS_totalNum").val();
	var integral = $("#integral").val();
	var totalIntegral = parseInt(integral)*num;
	if(totalIntegral>cardIntegral){
		$("#tip").text("积分余额不足，无法支付。");
		return false;
	}
	return true;
}


var exp   = /^[0-9]*[1-9][0-9]*$/;
function validateNum(num){
	$("#tip").text("");
	if(isNaN(num)){ 
		/* alert("请输入数字"); */
		$("#tip").text("请输入数字！");
		$("#JS_totalNum").val("1");
		$("#JS_totalPrice").html( (parseFloat($("#JS_unitPrice").text())).toFixed(2) * 1);
		$("#JS_totalJiFen").html(parseInt($("#JS_unitJiFen").text()) * 1);
		return false;
	}else if(num<=0){
		/* alert("数量为正整数"); */
		$("#tip").text("数量为正整数！");
		$("#JS_totalNum").val("1");
		$("#JS_totalPrice").html( (parseFloat($("#JS_unitPrice").text())).toFixed(2) * 1);
		$("#JS_totalJiFen").html(parseInt($("#JS_unitJiFen").text()) * 1);
		return false;
	} else if(!exp.test(num)){
		/* alert("数量为正整数"); */
		$("#tip").text("数量为正整数！");
		$("#JS_totalNum").val("1");
		$("#JS_totalPrice").html( (parseFloat($("#JS_unitPrice").text())).toFixed(2) * 1);
		$("#JS_totalJiFen").html(parseInt($("#JS_unitJiFen").text()) * 1);
		return false;
	}
	$("#JS_totalPrice").html( (parseFloat($("#JS_unitPrice").text())* num).toFixed(2));
	$("#JS_totalJiFen").html(parseInt($("#JS_unitJiFen").text()) * num);
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
		$("#JS_totalPrice").html( (parseFloat($("#JS_unitPrice").text())* newNum).toFixed(2) );
		$("#JS_totalJiFen").html(parseInt($("#JS_unitJiFen").text()) * newNum);
		
	}else{ //不是数字
	  /*  alert("购买数量必须为数字！"); */
		$("#tip").text("购买数量必须为数字！");
	}	 
}
</script>
</body>
</html>
<!--<script src="${pageContext.request.contextPath}/js/jquery/1.8.3/jquery-1.8.3.min.js"></script>  -->
