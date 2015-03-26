<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0, maximum-scale=1.0"/> 
<title>${cardInfo.couponName}</title>
	<link type="text/css" rel="stylesheet" href="<%=basePath%>css/crm-wx.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/packagePayment.css" />
</head>

<body>
	<header><a href="<%=basePath%>weixin/1119/goRecharge.do?showwxpaytitle=1" class="goBack"><i class="pIco goBackIco"></i>返回</a> ${cardInfo.couponName }</header>
 	
    <div class="picWrap">
    	<c:if test="${cardCode eq '17364' }">
    	<img src="<%=basePath%>images/wx-xld-taocan_1.jpg" class="packagePic" />
    	</c:if>
    	<c:if test="${cardCode eq '17365' }">
    	<img src="<%=basePath%>images/wx-xld-taocan_4.jpg" class="packagePic" />
    	</c:if>
    	<c:if test="${cardCode eq '17366' }">
    	<img src="<%=basePath%>images/wx-xld-taocan_3.jpg" class="packagePic" />
    	</c:if>
    	<c:if test="${cardCode eq '17367' }">
    	<img src="<%=basePath%>images/wx-xld-taocan_2.jpg" class="packagePic" />
    	</c:if>
    	<c:if test="${cardCode eq '18077' }">
    	<img src="<%=basePath%>images/wx-xld-taocan_1.jpg" class="packagePic" />
    	</c:if>
    	<c:if test="${cardCode eq '18078' }">
    	<img src="<%=basePath%>images/wx-xld-taocan_4.jpg" class="packagePic" />
    	</c:if>
    	<c:if test="${cardCode eq '18079' }">
    	<img src="<%=basePath%>images/wx-xld-taocan_3.jpg" class="packagePic" />
    	</c:if>
    	<c:if test="${cardCode eq '18080' }">
    	<img src="<%=basePath%>images/wx-xld-taocan_2.jpg" class="packagePic" />
    	</c:if>
        <p>使用范围：全国直营门店<font color="red">（加盟店不参加本活动）</font></p>
    </div>  
    
    <div class="buyNow clear_wrap">
    	<span class="bugMoney"><em>￥</em>${cardInfo.couponAmount }</span>
        <a href="<%=basePath%>weixin/1119/goEachPayPage.do?cardCode=${cardCode}&showwxpaytitle=1" class="btn bugNowBtn" >立即抢购</a>
    </div> 
    
    <div class="packageInfo">
    	<h3 class="packageTitle">${cardInfo.couponName}&nbsp;&nbsp;${descs[0]}套餐</h3>
        
        <div class="infoPart">
        	${descs[1]}。     
            <p><i class="pIco clockIco"></i>预售期：2014-06-25至2014-07-16</p>
            <p><i class="pIco clockIco"></i>使用期：${cardInfo.expireBegin }至${cardInfo.expireEnd}</p>
        </div>
        
        <div class="packageDetails">
        	<h3><i></i> 套餐</h3>
            <table class="packageDetailTab">
            	<thead><tr><th class="packageInclude">套餐包括</th><th class="packageNum">数量</th></tr></thead>
                <tbody>
                	<tr><td> ${descs[0]}</td><td class="numTd">1斤</td></tr>
                    <tr><td>蔬菜吧 近20种蔬菜</td><td class="numTd">2份</td></tr>
                    <tr><td>虾滑、手工鲜虾丸、灌汤蟹粉丸、爆浆肉丸</td><td class="numTd">1份<br />（四选一）</td></tr>
                    <tr><td>瘦身海藻、香辣杏鲍菇、川味香肠、冰藻脆鱼皮</td><td class="numTd">1份 <br />（四选一）</td></tr>
                    <tr><td>可尔必思乳酸饮、徐妈凉茶、冬瓜仙草露、土贡梅煎</td><td class="numTd">1扎<br />（四选一）</td></tr>
                    <tr><td colspan="2" class="numTd">小时代限量版 明星杯1个、纪念册1本</td></tr>
                </tbody>
            </table>
        </div>
        <div class="packageExplain">
        	<h3><i class="pointIco"></i>【注意事项】</h3>
            <p>买任意套餐得明星杯1个、纪念册1个</p>
            <p>现在成功购买任意套餐，还有机会获得以下福利：</p>
            <p>福利一：2人电影兑换券（北京）共12张</p>
            <p>福利二：价值100元新辣道鱼火锅代金券1张共10张</p>
            <p>福利三：6月25日起，前1000名参与微信预售成功购买者（非维权用户）将获得3-10元不等快的打车返券</p>
            
            <h3><i class="pointIco"></i>【福利及中奖说明】请仔细阅读</h3> 
            <p>1、福利一（电影兑换券）与福利二（价值100元新辣道代金券）福利不同享</p>
            <p>2、中奖用户接受并使用所获福利，将不再享受任何套餐购买维权申请；若中奖用户放弃所获福利，并且不影响该福利的二次使用情况下，中奖用户可重获维权申请</p>
            <p>3、快的中奖用户接受并使用所获福利，将不再享受任何套餐购买维权申请；若中奖用户放弃所获福利，并且不影响该福利的二次使用情况下，中奖用户可重获维权申请</p>
            <p>4、快的打车返券，将由新辣道委托快的打车，于活动结束后，统一向6月25日起，参与微信预售购买成功（非维权用户）的前1000名购买者手机中，随机发送3-10元不等快的返券，使用方式请参见快的打车相关说明</p>
            <p>5、电影兑换券由北京万达影城提供，使用方式将依照北京万达影城统一规定。奖品将由新辣道官方于活动结束后统一快递发送</p>
            <p>6、价值100元代金券由新辣道提供，使用方式将依照新辣道统一规定。奖品将由新辣道官方于活动结束后统一快递发送</p>
            <p>7、中奖用户将由新辣道官方在7月17日前于微信公众后台随机抽取，并做及时官方公布</p>
            <p>8、若活动或因其他外力因素导致必须提前终止，新辣道将正式在官方平台（微博微信）进行统一信息公布，请随时关注我们的信息动态</p>
            
            <p class="explainExtra">*请仔细阅读以上【福利及中奖说明】或参见导航"小时代套餐"-"购买须知"，一旦您成功完成购买交易，将默认您已对以上条款与新辣道官方达成共识，并表示同意。奖品以实物为准。活动最终解释权归北京新辣道餐饮管理有限公司所有。</p>
        </div>
    </div>
<script type="text/javascript">
//当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
document.addEventListener('WeixinJSBridgeReady', function onBridgeReady(){
	WeixinJSBridge.call('showOptionMenu');
	});
	var dataForWeixin={
		       
	        appId:"",
	        MsgImg:'<%=basePath%>images/xinladao.png',

	        TLImg:'<%=basePath%>images/xinladao.png',
	   	     
	        url:"<%=basePath%>weixin/1119/goEachPage.do?cardCode=${cardCode}&showwxpaytitle=1",
	     
	        title:"${cardInfo.couponName}",
	     
	        desc:"每个人都有小时代，快来抢购你的小时代套餐吧！",
	     
	        fakeid:"",
	     
	        callback:function(){}
	     
	     };
	</script>
</body>
</html>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/weixin.js"></script>