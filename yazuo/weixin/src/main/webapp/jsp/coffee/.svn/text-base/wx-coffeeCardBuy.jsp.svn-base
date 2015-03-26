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
<title>购买信息</title>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/wx_card.css" />
<script src="${pageContext.request.contextPath}/js/md5.js"></script>
<script src="${pageContext.request.contextPath}/js/sha1.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.mobile/jquery.mobile.js"></script>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script type="text/javascript">
	var brandId='${brandId}';
	var appid= '${dict.appId}';
 
	function getAddressSign()
	{
	    var url = "<%=base_Path %>/weixin/${brandId}/buyCard.do?goodCode=${goodvo.id}";
	    var time_stamp = "1384841012";
	    var nonce_str = "1234561111";
		var accesstoken=$("#accesstoken").val();
		var code=$("#code").val();
		var state=$("#state").val();
		url = url+"&code="+code+"&state="+state;
	    //第一步，对所有需要传入的参数加上appkey作一次key＝value字典序的排序
	    var keyvaluestring = "accesstoken=" + accesstoken + "&appid=" + appid + "&noncestr=" + nonce_str+ "&timestamp=" + time_stamp + "&url=" + url;
	    var sign = CryptoJS.SHA1(keyvaluestring).toString();
	    return sign;
	}
	
	function editAddress(){
		WeixinJSBridge.invoke('editAddress',{
			"appId" : appid,
			"scope" : "jsapi_address",
			"signType" : "sha1",
			"addrSign" : getAddressSign()+"",
			"timeStamp" : "1384841012",
			"nonceStr" : "1234561111"
			},function(res){
				if(res.err_msg=="edit_address:ok"){
							
					//若res 中所带的返回值不为空，则表示用户选择该返回值作为收货地
					//址。否则若返回空，则表示用户取消了这一次编辑收货地址。
					
					document.getElementById("first").value=res.proviceFirstStageName;
					document.getElementById("second").value=res.addressCitySecondStageName;
					
					document.getElementById("third").value=res.addressCountiesThirdStageName;
				
					document.getElementById("detail").value=res.addressDetailInfo;
			
					document.getElementById("phone").value=res.telNumber;
				
					document.getElementById("receiver").value=res.userName;
				
					document.getElementById("zipcode").value=res.addressPostalCode;
					document.getElementById("zhifu").removeAttribute('disabled');
					$("#tip").text("");
					$("#changeAddress").html("送至："+res.proviceFirstStageName+res.addressCitySecondStageName+res.addressCountiesThirdStageName+res.addressDetailInfo+"<br/>"+res.userName+","+res.telNumber);
				}else{	
						$("#changeAddress").html("请选择收货地址");
						document.getElementById("zhifu").disabled=true;
						$("#addressInfo").val("");
						document.getElementById("tip").style.display = "";
				}
			});
	}
	/**
	* 获取微信地址编辑
	*/
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady(){
		WeixinJSBridge.call('hideOptionMenu');
		$("#changeAddress").click(function(){
			editAddress();
			});
		});
	function trim(s){return s.replace(/^\s+|\s+$/g,"");};
	/**
	 * 支付方法
	 */
	function pay(){
		$("#zhifu").hide();//点击提交后隐藏起来，防止重复提交
		var receiver = document.getElementById("receiver").value;
		var	firstStageName = document.getElementById("first").value;
		var	secondstageName = document.getElementById("second").value;
		var	thirdStageName = document.getElementById("third").value;
		var	addressDetail = document.getElementById("detail").value;
		var	mobile = document.getElementById("phone").value;
		var	zipcode = document.getElementById("zipcode").value;
		var openId = document.getElementById("openId").value;
		
		var totalFee = trim($("#JS_totalPrice").text());//$("#JS_totalPrice").text(); 
		//微信禁止金额为0.01分，最低1分钱
		var goodInfo = $("#itemShort").val();
		var num = $("#JS_totalNum").val();
		var productType = $("#productType").val();
		var batchId = $("#batchId").val();
		var cardTypeId = $("#cardTypeId").val();
		if(productType==2){
			num=1;
			mobile=$("#buyerPhone").val();
		}
		if(!validateMobile(mobile)&&productType==2){
			$("#buyerPhone").val("请输入您的手机号");
			$('#zhifu').show();//点击提交后隐藏起来，防止重复提交
			return ;//校验手机号
		}
		
		
		var goodId = $("#goodId").val();
		var buy_limit_is = $("#buy_limit_is").val();
		var buy_limit_type = $("#buy_limit_type").val();
		var buy_limit_num = $("#buy_limit_num").val();
		
		var requestStr = "receiver="+receiver+"&firstStageName="+firstStageName+"&secondstageName="+secondstageName
			+"&thirdStageName="+thirdStageName+"&addressDetail="+addressDetail+"&mobile="+mobile+"&zipcode="+zipcode
			+"&openId="+openId+"&totalFee="+totalFee+"&goodInfo="+goodInfo+"&goodId="+goodId+"&num="+num
			+"&productType="+productType+"&batchId="+batchId+"&cardTypeId="+cardTypeId;
		
		var checkStr = "weixinId="+openId+"&goodId="+goodId+"&num="+num+"&buy_limit_is="+buy_limit_is+"&buy_limit_type="+buy_limit_type+"&buy_limit_num="+buy_limit_num;
		
		if(productType!=2 && receiver=="" && mobile=="" && addressDetail==""){
			$("#tip").text("请选择收货地址");
			$('#zhifu').show();//点击提交后隐藏起来，防止重复提交
			return;
		}
		
		/**
		* 判断购买规则,1.判断库存，判断购买限制
		**/
		$.ajax({
			url:"${pageContext.request.contextPath}/weixin/"+brandId+"/checkBeforeBuy.do",
		    type:"post",    //数据发送方式  
		    dataType:"json",   //接受数据格式  
		    async: false, //设为false就是同步请求
		    data:checkStr,   //要传递的数据；就是上面序列化的值  
		    success: function(data) {
		    	var flag = data.flag;
		    	if(flag=='1'){//flag==1 校验不通过，提示用户
		    		$("#tip").text(data.message);
		    		$('#zhifu').show();//点击提交后隐藏起来，防止重复提交
		    	}else{
		    		$("#tip").text("提交中请稍后.....");
					beginPay(requestStr,brandId,openId);//开始支付
		    	}
		    }
		});
	}
	
	function beginPay(requestStr,brandId,weixinId){
		$.ajax({
   			url:"${pageContext.request.contextPath}/weixin/"+brandId+"/obtainCardInfo.do",
   		    type:"post",    //数据发送方式  
   		    dataType:"json",   //接受数据格式  
   		    data:requestStr,   //要传递的数据；就是上面序列化的值  
   		    success: function(data) {
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
   						var red_url ="${pageContext.request.contextPath}/weixin/"+brandId+"/"+weixinId+"/"+data.outTradeNo+"/goCardSuccessPage.do?random="+Math.random()+"&showwxpaytitle=1";
   						window.location.href=red_url;
   						//window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+red_url+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
   					}else{
   						$("#tip").text("支付失败,请稍后重试！");
	    				$('#zhifu').show();//点击提交后隐藏起来，防止重复提交
   					}
   				});
   			}
   		});
	}
</script> 
</head>
<body>
	<input type="hidden" id="itemShort" name="itemShort" value="${goodvo.name}"><!-- 商品 -->
	<input type="hidden" id="goodId" value="${goodvo.id }"/><!-- 商品id -->
	<input type="hidden" id="addressInfo" value="请选择收货地址">
	<input type="hidden" id="openId" name="openId" value="${openid }">
	<input type="hidden" id="code" value="${code}">
	<input type="hidden" id="state" value="${state }">
	<input type="hidden" id="accesstoken" value="${accesstoken}">
	<input type="hidden" id="first" name="firstStageName" value=""/>
	<input type="hidden" id="second" name="secondstageName"  value=""/>
	<input type="hidden" id="third" name="thirdStageName"  value=""/>
	<input type=hidden   id="detail" name="addressDetail" value="" ><!-- 详细地址 -->
	<input type="hidden" id="phone" name="phone" value=""/><!-- 电话号 -->
	<input type="hidden" id="receiver" name="receiver" value=""/><!--收件人姓名 -->
	<input type="hidden" id="zipcode" name="zipcode" value=""/><!-- 邮编 -->
	<input type="hidden" id="buy_limit_is" value="${goodvo.buyLimitIs }" />
	<input type="hidden" id="buy_limit_type" value="${goodvo.buyLimitType }" />
	<input type="hidden" id="buy_limit_num" value="${goodvo.buyLimitNum }" />
	<input type="hidden" id="productType" value="${goodvo.productType }" />
	<input type="hidden" id="batchId" value="${goodvo.batchId }" />
	<input type="hidden" id="cardTypeId" value="${goodvo.cardTypeId }" />
<div data-role="page">
    <div data-role="header" class="shop-header">
    	<a target="_self" href="<%=base_Path %>/weixin/${brandId}/cardInfoPage.do?goodCode=${goodvo.id}&showwxpaytitle=1" data-role="button"><img src="${pageContext.request.contextPath}/imageMall/back.png" width="16" />返回</a>
    	<h1>购买信息</h1>
    </div>
        <div data-role="content" class="shop-content">
    	<section class="buy-module">
        	<h1>名称：${goodvo.name}</h1>
            <ul>
            <c:choose>
            	<c:when test="${goodvo.productType ne 2 }">	
	            	<li class="clear-wrap">
		            	<span class="left">单价：</span>
		            	<span class="right orange">
			    			￥<span id="JS_unitPrice" >
				    			${goodvo.nowPriceCash}
				    		</span>
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
            	</c:when>
            	<c:otherwise>
            		<li class="clear-wrap">
            		<span class="left">数量：</span>
            		<span class="right orange" id="JS_totalNum" >
	            		1
            		</span>
            	</li>
            	</c:otherwise>
            </c:choose>
            	<li class="clear-wrap">
            		<span class="left">总价：</span>
            		<span class="right orange">
		    			￥<span id="JS_totalPrice">
			    			${goodvo.nowPriceCash}
			    		</span>
			    	</span>
            	</li>
            </ul>
        </section>
         <c:choose>
            <c:when test="${goodvo.productType ne 2 }">	
				<section class="select-address">
		        	<span class="icon-arrow-r"></span>
		        	<div class="add-detail">
		            	<p  id="changeAddress">
						   请选择收货地址
						</p>
		            </div>
		        </section>
	        </c:when>
	        <c:otherwise>
	        <section class="buy-module">
	        	<h1>请输入办卡人手机号</h1>
	            <ul>
	            	<li class="clear-wrap card-phone">
	            	<span class="left">手机号：</span>
	            	<span class="left">
	            		<input type="text" id="buyerPhone" name="buyerPhone" onfocus="javascript:this.value=''" value="请输入您的手机号"/>
	            	</span>
	            	</li>
	            </ul>
	        </section>
	        </c:otherwise>
        </c:choose>
	        <div class="error-message" id="tip"></div>
        
            <button class="shop-btn" id="zhifu" onClick="pay()">确认支付</button>
    </div>
</div>
    
    
<script type="text/javascript">
var reg =/^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\d{8}$/;// 手机号码验证
function validateMobile(mobile){
	$("#tip").text("");
	if(mobile==""){
		$("#tip").text("请输入手机号！");
		return false;
	}
	if(!reg.test(mobile)){
		$("#tip").text("手机号不合法！");
		return false;
	}
	return true;
}
var exp   = /^[0-9]*[1-9][0-9]*$/;
function validateNum(num){
	$("#tip").text("");
	if(isNaN(num)){ 
		$("#tip").text("请输入数字！");
		$("#JS_totalNum").val("1");
		$("#JS_totalPrice").html((parseFloat($("#JS_unitPrice").text())).toFixed(2) * 1);
		return false;
	}else if(num<=0){
		$("#tip").text("数量为正整数！");
		$("#JS_totalNum").val("1");
		$("#JS_totalPrice").html((parseFloat($("#JS_unitPrice").text())).toFixed(2) * 1);
		return false;
	} else if(!exp.test(num)){
		$("#tip").text("数量为正整数！");
		$("#JS_totalNum").val("1");
		$("#JS_totalPrice").html((parseFloat($("#JS_unitPrice").text())).toFixed(2) * 1);
		return false;
	}
	$("#JS_totalPrice").html((parseFloat($("#JS_unitPrice").text())* num).toFixed(2) );
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
		$("#JS_totalPrice").html((parseFloat($("#JS_unitPrice").text())* newNum).toFixed(2) );
		
	}else{ //不是数字
		$("#tip").text("购买数量必须为数字！");
	}	 
}
</script>
</body>
</html>
