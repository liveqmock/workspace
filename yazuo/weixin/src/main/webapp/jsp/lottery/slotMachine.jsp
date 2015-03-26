<%@ include file="/common/meta.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class='redBg'>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />


<title>老虎机</title>
</head>
<body>
<form id="form1">

<!-- 抽奖信息 -->
<input id="brandId" name="brandId" type="hidden" value="${memberAward.brandId}"/>
<input id="membershipId" name="membershipId" type="hidden" value="${memberAward.membershipId}"/>
<input id="weixinId" name="weixinId" type="hidden" value="${memberAward.weixinId}"/>
<input id="phoneNo" name="phoneNo" type="hidden" value="${memberAward.phoneNo}"/>
<input id="type" name="type" type="hidden" value="${memberAward.type}"/>

<section class='containerWheel'> 
   	<div class='tigerBg'><p class='tigerTopBg'></p>
   		<div class='tigerGameBg clear'>
   			<div class='picViewer' id="JS_picViewerFir">
	   			<ul>
	   			<c:forEach items="${lottery.list}" var="list" >
	   				<c:set var="prefixUrl" value=""></c:set>
	   				<c:choose>
			      		<c:when test="${fn:startsWith(list.lottery_item_pic,'M00/') }">
			      			<c:set var="prefixUrl" value="${lottery.newPictureUrl }"></c:set>		
			      		</c:when>
			      		<c:otherwise>
			      			<c:set var="prefixUrl" value="${lottery.lottery_pic }"></c:set>
			      		</c:otherwise>
        			</c:choose>
	   				<li><a href='javascript:;' class='picPosition'><img align="absmiddle" src='${prefixUrl}${list.lottery_item_pic}' /></a></li>
	   			</c:forEach>
	   				<li><a href='javascript:;' class='picPosition'><img align="absmiddle" src='<%=basePath%>/lotteryImages/tigerDefault.png' /></a></li>
	   			</ul>
   			</div>
   			<div class='picViewer' id="JS_picViewerSec">
	   			<ul>
	   				<li><a href='javascript:;' class='picPosition'><img align="absmiddle" src='<%=basePath%>/lotteryImages/tigerDefault.png' /></a></li>
	   			<c:forEach items="${lottery.list}" var="list" >
	   				<c:set var="prefixUrl" value=""></c:set>
	   				<c:choose>
			      		<c:when test="${fn:startsWith(list.lottery_item_pic,'M00/') }">
			      			<c:set var="prefixUrl" value="${lottery.newPictureUrl }"></c:set>		
			      		</c:when>
			      		<c:otherwise>
			      			<c:set var="prefixUrl" value="${lottery.lottery_pic }"></c:set>
			      		</c:otherwise>
        			</c:choose>
	   				<li><a href='javascript:;' class='picPosition'><img align="absmiddle" src='${prefixUrl}${list.lottery_item_pic}' /></a></li>
	   			</c:forEach>
	   			</ul>   			
   			</div>
   			<div class='picViewer' id="JS_picViewerThi">
	   			<ul>
	   			<c:forEach items="${lottery.list}" var="list" >
	   				<c:set var="prefixUrl" value=""></c:set>
	   				<c:choose>
			      		<c:when test="${fn:startsWith(list.lottery_item_pic,'M00/') }">
			      			<c:set var="prefixUrl" value="${lottery.newPictureUrl }"></c:set>		
			      		</c:when>
			      		<c:otherwise>
			      			<c:set var="prefixUrl" value="${lottery.lottery_pic }"></c:set>
			      		</c:otherwise>
        			</c:choose>
	   				<li><a href='javascript:;' class='picPosition'><img align="absmiddle" src='${prefixUrl}${list.lottery_item_pic}' /></a></li>
	   			</c:forEach>
	   				<li><a href='javascript:;' class='picPosition'><img align="absmiddle" src='<%=basePath%>/lotteryImages/tigerDefault.png' /></a></li>
	   			</ul>   			
   			</div>
   		</div>
   		<div class='tigerGameBtnBg'>
   			<a href="javascript:;" id='JS_tigerGameBtn' class='tigerGameBtn'>我要抽奖</a>
   			<span id='JS_tigerGameSpan' class='tigerGameBtn'>抽奖中...</span>
   		</div>
   	</div> 	
	<p class='wheelPlayRulesBg'></p>
	<c:if test="${lottery.winflag == 4}">
		<div class='wheelPlayRules' id ='luck'>			
			<ul class="playRulesInfo">
	        	<li><b>活动日期：</b>${lottery.begin_time} — ${lottery.end_time}</li>
	            <li><b>游戏规则：</b>每位会员 <c:if test="${lottery.time_unit == 1}">总共</c:if><c:if test="${lottery.time_unit == 2}">每天</c:if>有【<em id='JS_lotteryChanceNum'>${lottery.frequency}</em>】次抽奖机会，抽奖之前洗洗手，手气全来...</li>
	            <li><b>您还剩余：</b><em id='pafrequency'>${lottery.number}</em>次机会</li>
	            <li>中奖信息可在会员页面看到奖品情况！</li>	            
	        </ul>		
		</div>
	</c:if>
	<c:if test="${lottery.winflag != 4}">
		<div class='wheelPlayRules'>
			<ul class="playRulesInfo">
	            <li>${error}</li>
	            <li><b>活动日期：</b>${lottery.begin_time} — ${lottery.end_time}</li>
	            <li><b>游戏规则：</b>每位会员 <c:if test="${lottery.time_unit == 1}">总共</c:if><c:if test="${lottery.time_unit == 2}">每天</c:if>有【<em>${lottery.frequency}</em>】次抽奖机会，抽奖之前洗洗手，手气全来...</li>
	            <li><b>您还剩余：</b><em> 0 </em>次机会</li>
	            <li>中奖信息可在会员页面看到奖品情况！</li>
	        </ul>	
	        <script>
	        	$("#JS_tigerGameSpan").text("没机会了");
	        	$("#JS_tigerGameSpan").show();
	        	$("#JS_tigerGameBtn").hide();
	        </script>	
		</div>
	</c:if>
	<div class='wheelPlayRules' id='luckerror' style="display:none">
		<ul class="playRulesInfo">
            <li id ='error'>${error}</li>
            <li><b>活动日期：</b>${lottery.begin_time} — ${lottery.end_time}</li>
            <li><b>游戏规则：</b>每位会员 <c:if test="${lottery.time_unit == 1}">总共</c:if><c:if test="${lottery.time_unit == 2}">每天</c:if>有【<em>${lottery.frequency}</em>】次抽奖机会，抽奖之前洗洗手，手气全来...</li>
            <li><b>您还剩余：</b><em> 0 </em>次机会</li>
            <li>中奖信息可在会员页面看到奖品情况！</li>
        </ul>		
	</div>
</section>
<div class="popUp" id="noPopUp" style="display:none">
	<div class="popInfo">
    	<h3 class="popBorderTitle">
    		<p class='headBorder'></p>
    		<p class='headText'>没抽中，<span id='JS_randomText'></span>！<br />您还有<em id='nofrequency'>0</em>次机会，加油！</p>
    	</h3>
        <div class="giftCoupon">
        	<%-- <img src="<%=basePath%>/lotteryImages/tigerDefault.png"  id ='noWinning'/> --%>
        </div>
        <div class="popBtns"><a href="javascript:luckyDraw();" class="btn" id='goLotteryNo'>关 闭</a></div>
        
            <div class="popAd">
        <c:if test="${lottery.title_pic !=''&& lottery.title_pic !='null'}">
        	<c:choose>
        		<c:when test="${fn:startsWith(lottery.title_pic,'M00/') }">
        			<img src="${lottery.newPictureUrl}${lottery.title_pic}" />
        		</c:when>
        		<c:otherwise>
	               <img src="${lottery.lottery_pic}${lottery.title_pic}" />
        		</c:otherwise>
        	</c:choose>
        </c:if>
        	   <p>${lottery.title}</p>
            </div>
        
    </div>
</div>   

<div class="popUp" id="popUp" style='display:none'>
	<div class="popInfo">
    	<h3 class="winTip">恭喜您获得<em id='lottery'></em>一张！</h3>
        <div class="giftCoupon">
        	<img src="<%=basePath%>/lotteryImages/tigerDefault.png"  id ='winning'/>
        	<p>优惠券已发到您的会员卡中</p>
        </div>
        <div class="popBtns"><a href="javascript:luckyDraw();" class="btn" id='goLottery'>关 闭</a></div>
    	<h3 class="popBorderTitle">
    		<p class='headBorder'></p>
    		<p class='headText'>您还有<em id='frequency'>0</em>次机会</p>
    	</h3>
      <div class="popAd">
        <c:if test="${lottery.title_pic !=''&& lottery.title_pic !='null'}">
        	<c:choose>
        		<c:when test="${fn:startsWith(lottery.title_pic,'M00/') }">
        			<img src="${lottery.newPictureUrl}${lottery.title_pic}" />
        		</c:when>
        		<c:otherwise>
	               <img src="${lottery.lottery_pic}${lottery.title_pic}" />
        		</c:otherwise>
        	</c:choose>
        </c:if>
        	   <p>${lottery.title}</p>
     </div>
        
    </div>
</div> 
</form>


<script type="text/javascript">

function luckyDraw() {
	
	var pafrequency = jQuery("#pafrequency").html();
	if(pafrequency > 0) {//可以抽奖
		document.getElementById("JS_tigerGameBtn").style.display='block';
		document.getElementById("JS_tigerGameSpan").style.display='none';
	} else {
		document.getElementById("JS_tigerGameBtn").style.display='none';
		document.getElementById("JS_tigerGameSpan").style.display='block';
	}
	document.getElementById("popUp").style.display='none';
	document.getElementById("noPopUp").style.display='none';
}


var result = "";//反回结果
var prize =3;
var winflag=0; //是否中奖
var lottery_item_pic = '';// 奖品图片
var lottery_pic = '';// 图片地址

document.getElementById('JS_tigerGameBtn').onclick=function(){
	var wiflag = '${lottery.winflag}';
	
	if (wiflag!=4) {
		return false;
	} 
 

	this.style.display='none';
	document.getElementById("JS_tigerGameSpan").style.display='block';
	
	var randomTime = new Date().getTime();
    var brandId = '${memberAward.brandId}';
    var membershipId = '${memberAward.membershipId}';
    var weixinId = '${memberAward.weixinId}';
    var phoneNo = '${memberAward.phoneNo}';
    var type = '${memberAward.type}';
    var strs = "brandId="+brandId+"&membershipId="+membershipId+"&weixinId="+weixinId+
                    "&phoneNo="+phoneNo+"&type="+type+"&randomTime="+randomTime;
	
    jQuery.ajax({
        type:"POST",
        url:'<%=path%>/weixin/consumerLottery/ajaxLottery.do',
        //dataType:"String",
        data:strs,
        success: success,
       error: error,
       cache: false
	});
};


var success = function(retObj){
	
	winflag = retObj.map.winflag;//中奖状态
	var error = retObj.error;
	var remainCount = retObj.map.award.remainCount;//剩余抽奖次数
	var lottery_item_name = retObj.map.award.lottery_item_name;// 奖品名称
	var lottery_item_seq = retObj.map.award.lottery_item_seq;// 奖品等级
	var lasttime = retObj.map.award.lasttime;// 是否是最后一次
	var lottery_coupon_name = retObj.map.award.lottery_coupon_name; //奖项名称
	
	var lotteryStr = '【'+lottery_item_name+'】';
	if (lottery_coupon_name!=null && lottery_coupon_name!="" && lottery_coupon_name!='null') {
		lotteryStr += '【'+lottery_coupon_name+'】';
	}
	
	lottery_item_pic = retObj.map.award.lottery_item_pic;// 奖品图片
	lottery_pic = retObj.map.lottery_pic;// 图片地址
	
	if(lottery_item_pic!=null && lottery_item_pic.substr(0,4) == 'M00/') {// 是否为新图片
		lottery_pic = retObj.map.newPictureUrl;    		
	}
	
	//winflag=2

	if(winflag <4) {//不能抽奖了
		document.getElementById("luckerror").style.display='';
        document.getElementById("luck").style.display='none';
        jQuery("#JS_tigerGameSpan").html("没机会了");
        jQuery("#error").html(error);
		return false;
	} 
	
	//清空没中奖时 图片随机路径
	allSrc=[];  
	diffSrc='';
	
	channelFocus('JS_picViewerFir',50,true);
	channelFocus('JS_picViewerSec',100,true);
	channelFocus('JS_picViewerThi',60,true);
	
	setTimeout(function(){
		if(winflag == 4) {//中奖了
			document.getElementById("popUp").style.display='';
			$("#winning").attr("src",lottery_pic+lottery_item_pic);
			jQuery("#frequency").html(remainCount);
			
			jQuery("#lottery").html(lotteryStr);
			if(remainCount==0) {
				jQuery("#JS_tigerGameSpan").html("没机会了");
				//$("#goLottery").text('确定');
			}
		
		} else {
			document.getElementById("noPopUp").style.display='';
			jQuery("#nofrequency").html(remainCount);
			if(remainCount==0) {
				jQuery("#JS_tigerGameSpan").html("没机会了");
				//$("#goLotteryNo").text('确定');
			}
			//==================================
			var iChance=$("#JS_lotteryChanceNum").text();	
			$("#JS_randomText").text(getResults(iChance));
		}
		jQuery("#pafrequency").html(remainCount);
		
		if(remainCount==0 || lasttime == 1) {
			document.getElementById("luckerror").style.display='';
	        document.getElementById("luck").style.display='none';
	        jQuery("#JS_tigerGameSpan").html("没机会了");
	        if(lasttime == 1) {
	        	jQuery("#error").html("奖品已经送完，活动结束了，感谢您的参与！");
	        } else {
	        	jQuery("#error").html("您已没有抽奖机会了，感谢您的参与！");
	        }
		} 
		
	},4000);
     
};
var error = function() {
	 alert("请求失败!");
};


</script>


</body>
</html>