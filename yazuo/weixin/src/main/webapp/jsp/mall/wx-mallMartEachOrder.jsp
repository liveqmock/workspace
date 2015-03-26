<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>

<html>
<head>

<title>订单详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0, maximum-scale=1.0"/> 
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
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.mobile/jquery.mobile.js"></script>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script src="${pageContext.request.contextPath}/js/order.js"></script>
<script src="${pageContext.request.contextPath}/js/MapClass.js"></script>
 <script type="text/javascript">
    var orderMap=new MapClass();//<outtradeno,orderInfo>
    $(function(){
	    var itm = new Array(8);
		itm[0]="${weixinId}";
		itm[1]="${item.integral}";
		itm[2]="${item.product_info}";
		itm[3]="${item.goods_id}";
		itm[4]="${item.buy_num}";
		itm[5]="${item.total_fee}";
		itm[6]="${item.out_trade_no}";
		itm[7]="${item.nonce_tr}";
		orderMap.put("${item.out_trade_no}",itm);
    });
 	/* *  发起退款审核  * */
	function refundMethod(outTradeNo,orderState){
		if(orderState!=1 && orderState!=2){
			$(this).cofirm({
					type:2,
					title:'退款申请',
					text:'不可申请退款！' 
			});
			return ;
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/weixin/${brandId}/queryRemainCoupon.do",
			type:"post",    //数据发送方式  
    		dataType:"json",   //接受数据格式  
    		data:"outTradeNo="+outTradeNo,   //要传递的数据；就是上面序列化的值  
    		success: function(data) {
    			$("#JS_cardType").empty();
    			if(data.size>0){
	    			for(var i=1;i<=data.size;i++){
	    				$("#JS_cardType").append("<option value='"+i+"'>"+i+"</option>");
	    			}
    			}else{
    				$("#JS_cardType").append("<option value='0'>0</option>");
    			}
    		}
		});
		popRefund();//弹出退款框
		$("#pop-sure").click(function(){
			var that=this;
			var refundMessage = $("#refundMessage").val();
			var	noUseNum = $("#JS_cardType option:selected").val();
			if(refundMessage==null ||refundMessage==''|| refundMessage.length==0){
				$("#errorTip").text("请输入退款原因");
			}else if(refundMessage.length<10){
				$("#errorTip").text("退款原因不得少于10个字");
			}else if(noUseNum<1){
				$("#errorTip").text("没有可退的商品");
			}else{
				popCloseHide();
				var requestStr="refundDesc="+refundMessage+"&outTradeNo="+outTradeNo+"&remainNum="+noUseNum;
				$.ajax({
					url:"${pageContext.request.contextPath}/weixin/${brandId}/updateRefundDesc.do",
					type:"post",    //数据发送方式  
		    		dataType:"json",   //接受数据格式  
		    		data:requestStr,   //要传递的数据；就是上面序列化的值  
		    		success: function(data) {
		    			$(that).cofirm({
							type:1,
							title:'退款申请',
							text:data.message,
							callBack: function(){//回调事件
								if(data.flag){
									location.reload();
								}
							} 
						});
		    		}
				});
			}
		});
	}
 	
	/*继续发起支付*/
	function xianjinxiaofei(orderNo){
		var arr = orderMap.get(orderNo);
		var requestStr = "weixinId="+arr[0]+"&integral="+arr[1]+"&goodInfo="+arr[2]+"&goodId="+arr[3]+"&num="+arr[4]+"&totalFee="+arr[5]+"&outTradeNo="+arr[6]+"&nonceTr="+arr[7];
		$.ajax({
			url:"${pageContext.request.contextPath}/weixin/${brandId}/queryProductInfo.do",
		    type:"post",    //数据发送方式  
		    dataType:"json",   //接受数据格式  
		    async: false, //设为false就是同步请求
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
    				}, function(res) {
    					if (res.err_msg == "get_brand_wcpay_request:ok") {
    						window.location.href="${pageContext.request.contextPath}/weixin/${brandId}/seeOrderInfo.do?weixinId=${weixinId}&outTradeNo=${item.out_trade_no}&random="+Math.random();
    					}
    				});
    			}else if(v2v3flag=='3'){
						WeixinJSBridge.invoke('getBrandWCPayRequest', {
							"appId" : data.order.appId, //公众号名称，由商户传入
							"timeStamp" :data.order.timeStamp, //时间戳
							"nonceStr" : data.order.nonceStr, //随机串
							"package" : data.order.package,//扩展包
							"signType" :"MD5", //微信签名方式v3 MD5
							"paySign" :data.order.paySign
						}, function(res) {
							if (res.err_msg == "get_brand_wcpay_request:ok") {
								window.location.href="${pageContext.request.contextPath}/weixin/${brandId}/seeOrderInfo.do?weixinId=${weixinId}&outTradeNo=${item.out_trade_no}&random="+Math.random();
							}
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
    	<a target="_self" href="${pageContext.request.contextPath}/weixin/${brandId }/orderHistory.do?weixinId=${weixinId}" data-role="button"><img src="${pageContext.request.contextPath}/imageMall/back.png" width="16" />返回</a>
    	<h1>订单详情</h1>
    </div>
    
    <div data-role="content" class="shop-content">
        <section class="shop-item">
            <div class="shop-item-list">
            	<ul>
                	<li class="clear-wrap">
                    	<div class="left item-img">
                    		<c:if test="${!empty item.picurl }">
								<c:choose>
					    			<c:when test="${fn:startsWith(item.picurl, 'M00/')}">
										<img src="${newPictureUrl}${item.picurl}" width="100" />
					    			</c:when>
					    			<c:otherwise>
										<img src="${caFileIp}${item.picurl}" width="100" />
					    			</c:otherwise>
					    		</c:choose>
							</c:if>
                    	</div>
                        <div class="left item-content">
                        	<p><span class="item-menu-name">${item.product_info}</span></p>
                            <p class="item-money-group">
                            	<span class="item-money">
                            	<c:if test="${item.pay_type != 2 }"> <!-- 现金消费 -->
					    			<em class="grey98">￥</em><i class="num-red">${item.total_fee}</i>
						    	</c:if>
						    	<c:if test="${item.pay_type == 2}"> <!-- 积分消费 -->
						    		 <i class="num-red">${item.integral}</i><em class="grey98">积分</em>
						    	</c:if>
						    	<c:if test="${item.pay_type == 3}"> <!-- 积分消费 -->
						    		<em class="grey98"> + ${item.integral} </em>积分
						    	</c:if></span>
                            </p>
                            <p class="item-time grey98">
								<c:if test="${item.expire_type == 1 }" ><!-- 固定使用期限 -->
						          	  有效期:<fmt:formatDate value="${item.expire_begin}" pattern="yyyy-MM-dd"/>至 <fmt:formatDate value="${item.expire_end}" pattern="yyyy-MM-dd"/>
						        </c:if>
					        	<c:if test='${item.expire_type == 2 }'> <!-- 相对期限 -->
						                                    使用期:自购买之日起第${item.expire_days_begin}天至第${item.expire_days}天
					        	</c:if></p>
                        </div>
                        <div class="order-details">
                        	<div class="details-word"> ${item.rules}</div>
	                        <div class="details-button">
							<c:if test="${item.order_state eq 1 || item.order_state eq 2}">
	                            	<a target="_self" href="javascript:void(0);" onclick="refundMethod('${item.out_trade_no}','${item.order_state }')" class="refund-btn shop-btn">申请退款</a>
                            </c:if>
                            <c:if test="${item.jifen_state and item.order_state eq 11}" >
									<a target="_self" href="javascript:void(0);" onclick="xianjinxiaofei('${item.out_trade_no}')" class="refund-btn shop-btn">继续支付</a>
                           	</c:if>
                            </div>
                        </div>
                    </li>
                    <li>
                    	<div class="order-sm-list">
                            <p>订单状态：${item.order_state_name }</p>
                            <p>订单号：${item.out_trade_no }</p>
                            <p>下单时间：${fn:substring(item.time_begin, 0, 19)}</p>
                            <p>购买数量：${item.buy_num }</p>
                            <c:if test="${item.total_fee  gt 0 }">
			            		<p>商品总价：${item.total_fee }元</p>
			            	</c:if>
                            <c:if test="${item.integral  gt 0 }">
	                            <p>消费积分：${item.integral}</p>
			            	</c:if>
                        </div>
                    </li>
                    <c:if test="${!empty refundList }">
                    	<li><!-- 退款记录 -->
					    <div class="order-refund-list clear-wrap">
					        <span class="refund-tit">退款记录：</span>
					        <section class="refund-cont">
					        <c:forEach items="${refundList }" var="item">
					         	<div class="refund-module">
					                <p>退款时间：<fmt:formatDate value="${item.deal_time }" pattern="yyyy-MM-dd HH:mm:ss"/></p>
					                <p>退款数量：${item.refund_num}</p>
					                <p>退款原因：${item.description }</p>
					               	<c:choose>
					                <c:when test="${item.deal_state eq 6 }">
					                	<p>退款状态：已完成</p>
					                </c:when>
					                <c:otherwise>
					                	<p>退款状态：未完成</p>
					                </c:otherwise>
					                </c:choose>
					            </div>
				    		</c:forEach>
					        </section>
					    </div>
					</li>
					</c:if>
                </ul>
            </div>
        </section>
    </div>
    
</div> 
<div class="mobile_popBg" id="pop-bg" style="display:none;"></div>
<div class="pop-div hide" id="refund">
	<div class="refund-content">
    	<h1>退款申请</h1>
    	<div class="refund-select-num">
        	<p>请选择退款数量：</p>
           <select name="cardType" class="ui-select" id="JS_cardType" ></select>
        </div>
        <div class="refund-cause">
        	<p>请说明您要退款的原因：</p>
            <p><textarea id="refundMessage"></textarea></p>
           <div class="error-message" id="errorTip"></div>
        </div>
        <div class="pop_sub">
            <button id="pop-cancle" class="shop-btn3 marR5">取消</button>
            <button id="pop-sure" class="shop-btn2">确定</button>
        </div>
    </div>
</div>
	
	<script type="text/javascript">
	//当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady(){
		WeixinJSBridge.call('hideOptionMenu');
	});
	</script>
</body>
</html>
