<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/common/meta.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class='redBg'>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0"/>
<title>刮刮乐</title>
<script type="text/javascript" language="javascript">
	var ctx = "<%=basePath%>";
</script>
<script src="<%=basePath %>js/prize-list.js"></script>
</head>

<body>
	<!-- 抽奖信息 -->
<input id="brandId" name="brandId" type="hidden" value="${memberAward.brandId}"/>
<input id="membershipId" name="membershipId" type="hidden" value="${memberAward.membershipId}"/>
<input id="weixinId" name="weixinId" type="hidden" value="${memberAward.weixinId}"/>
<input id="phoneNo" name="phoneNo" type="hidden" value="${memberAward.phoneNo}"/>
<input id="type" name="type" type="hidden" value="${memberAward.type}"/>
    
    
   <section class='containerLotto'>
		<div class='starBg'>
			<div class='lottoBg clear'>
				<h3 class='lottoTitle'>刮奖区</h3>
				<div id="JS_wScratchPad" class='wScratchPad'></div>
			</div>
		</div>
		
			<div class='wheelPlayRules'  id='card' style="display:none">
				<ul class="playRulesInfo">
	        	<li><b>活动日期：</b>${lottery.award.begin_time} — ${lottery.award.end_time}</li>
	            <li><b>您还剩余：</b><em id='pafrequency'>${lottery.award.remainCount+1}</em>次机会</li>
	            <li>中奖信息可在中奖记录菜单中看到奖项情况！</li>
	        </ul>	
			</div>
	
		
			<div class='wheelPlayRules' id='carderror' style="display:none">
				<ul class="playRulesInfo">
		            <li id ='error'>${error}</li>
		            <li><b>活动日期：</b>${lottery.award.begin_time} — ${lottery.award.end_time}</li>
		            <li><b>您还剩余：</b><em>0</em>次机会</li>
		            <li>中奖信息可在中奖记录菜单中看到奖项情况！</li>
		        </ul>		
			</div>
		<!-- 不能抽奖了活动到期或者结束 -->
		<div class='wheelPlayRules' id='carderrorend' style="display:none">
			<ul class="playRulesInfo">
	            <li id ='errorend'></li>
	            <li><b>活动日期：</b>${lottery.award.begin_time} — ${lottery.award.end_time}</li>
	            <li><b>您还剩余：</b><em>0</em>次机会</li>
	            <li>中奖信息可在中奖记录菜单中看到奖项情况！</li>	            
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
        <div class="popBtns"><a href="javascript:luckyDraw();" class="grayBtn2" id='goLotteryNo'>关 闭</a></div>
        
            <div class="popAd">
        <c:if test="${lottery.award.lottery_rule_title_pic !=''&& lottery.award.lottery_rule_title_pic !='null'}">
       		<c:choose>
	      		<c:when test="${fn:startsWith(lottery.award.lottery_rule_title_pic,'M00/') }">
	      			<img src="${lottery.newPictureUrl}${lottery.award.lottery_rule_title_pic}" />
	      		</c:when>
	      		<c:otherwise>
	               <img src="${lottery.lottery_pic}${lottery.award.lottery_rule_title_pic}" />
	      		</c:otherwise>
        	</c:choose>
        </c:if>
        	   <p>${lottery.award.lottery_rule_title}</p>
            </div>
    </div>
</div>   

<div class="popUp" id="popUp" style='display:none'>
	<div class="popInfo">
    	<h3 class="winTip">恭喜您获得<em id='lottery'></em><label id="lotteryUnit">一张</label>！</h3>
        <div class="giftCoupon">
        	<img src="<%=basePath%>/lotteryImages/tigerDefault.png"  id ='winning'/>
        	<c:if test="${lottery.award.lottery_item_type==1 }"> <!-- 中奖的是劵 -->
        		<p>优惠券已发到您的会员卡中</p>
        	</c:if>
        </div>
        <div class="popBtns">
        	<a href="javascript:nowDraw();" class="btn2" id='goLottery'>立即领取</a>
        		<a href="javascript:luckyDraw();"  class="grayBtn2" id='btn_close'>关闭</a>
        </div>
    	<h3 class="popBorderTitle">
    		<p class='headBorder'></p>
    		<p class='headText'>您还有<em id='frequency'>0</em>次机会，加油！</p>
    	</h3>
      <div class="popAd">
        <c:if test="${lottery.award.lottery_rule_title_pic !=''&& lottery.award.lottery_rule_title_pic !='null'}">
        	<c:choose>
	      		<c:when test="${fn:startsWith(lottery.award.lottery_rule_title_pic,'M00/') }">
	      			<img src="${lottery.newPictureUrl}${lottery.award.lottery_rule_title_pic}" />
	      		</c:when>
	      		<c:otherwise>
	               <img src="${lottery.lottery_pic}${lottery.award.lottery_rule_title_pic}" />
	      		</c:otherwise>
        	</c:choose>
        </c:if>
        	   <p>${lottery.award.lottery_rule_title}</p>
     </div>
        
    </div>
</div> 
<%@include file="/jsp/coffee/lottery/add_address.jsp" %>

<script type="text/javascript">

var cardflag=0; //是否中奖
var flag = 0;
var sp = null;
function luckyDraw() {

	$("#buy_address_div").hide();
	
	document.getElementById("popUp").style.display='none';
	document.getElementById("noPopUp").style.display='none';
	
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
        url:'<%=path%>/caffe/cardLottery/ajaxLottery.do',
        //dataType:"String",
        data:strs,
        success: success,
       error: error,
       cache: false
	});
    
}

var success = function(retObj){
	cardflag = retObj.map.winflag;//中奖状态
	var error = retObj.error;
	
	var remainCount = retObj.map.award.remainCount+1;
	
	if(cardflag <4) {//不能抽奖了
		document.getElementById("carderror").style.display='';
        document.getElementById("card").style.display='none';
        jQuery("#error").html(error);
		return false;
	} else {
		document.getElementById("carderror").style.display='none';
        document.getElementById("card").style.display='';
	}
	
	var img = '<%=basePath%>/lotteryImages/ggkDefault.png';

	if(cardflag == 4){//中奖了
		var lottery_item_pic = retObj.map.award.lottery_item_pic;// 奖品图片
		var lottery_pic = retObj.map.lottery_pic;// 图片地址
		
		if(lottery_item_pic!=null && lottery_item_pic.substr(0,4) == 'M00/') {// 是否为新图片
			lottery_pic = retObj.map.newPictureUrl;    		
    	}
		
		img =lottery_pic+lottery_item_pic;
		$("#winning").attr("src",img);
		
		var lottery_item_seq = retObj.map.award.lottery_item_seq;// 奖品图片
		var lottery_item_name = retObj.map.award.lottery_item_name;// 图片地址
		var lottery_coupon_name = retObj.map.award.lottery_coupon_name; //奖项名称
		var lottery_item_type = retObj.map.award.lottery_item_type; //奖项类别
		
		if (lottery_item_type ==1) { //劵
			$("#goLottery").hide();
			$("#btn_close").show();
		}else{
			$("#goLottery").show();
			$("#btn_close").show();
		}
		
		var lotteryStr = '【'+lottery_item_name+'】'; 
		if (lottery_coupon_name!=null && lottery_coupon_name!="" && lottery_coupon_name!='null') {
			lotteryStr += '【'+lottery_coupon_name+'】';
		}
		var str = "";
		if (lottery_item_type ==1) {
			str = "1张";
		} else {
			str = "1个";
		}
		$("#lotteryUnit").html(str);
		jQuery("#lottery").html(lotteryStr);
		
		$("#address_show_lottery").html(lotteryStr+str); // 填写地址页面显示
		
	}
/* 	if(remainCount==0) {
		$("#goLottery").text('确定');
		$("#goLotteryNo").text('确定');
	} */
	jQuery("#frequency").html(remainCount-1);
	jQuery("#nofrequency").html(remainCount-1);

	//jQuery("#pafrequency").html(remainCount);
	
	flag = 0;
	
	
	
    sp.wScratchPad('image', img);
    sp.wScratchPad('reset');
  
	
};
var error = function() {
	 alert("请求失败!");
	 flag = 0;
};


$(function(){
	
	cardflag = '${lottery.winflag}';//中奖状态

	if(cardflag <4) {//不能抽奖了
		document.getElementById("carderror").style.display='none';
        document.getElementById("card").style.display='none';
        document.getElementById("carderrorend").style.display='';
        jQuery("#errorend").html('${error}');
		return false;
	} else {
		document.getElementById("carderror").style.display='none';
        document.getElementById("card").style.display='';
	}
	
	
	var img = '<%=basePath%>/lotteryImages/ggkDefault.png';
	var remainCount = '${lottery.award.remainCount}';
	
	if(cardflag == 4){//中奖了
		var lottery_item_pic ='${lottery.award.lottery_item_pic}';// 奖品图片
		var lottery_pic ='${lottery.lottery_pic}';// 图片地址
		
		if(lottery_item_pic!=null && lottery_item_pic.substr(0,4) == 'M00/') {// 是否为新图片
			lottery_pic = '${lottery.newPictureUrl}';    		
    	}
	
		img =lottery_pic+lottery_item_pic;
		$("#winning").attr("src",img);
		
		var lottery_item_seq = '${lottery.award.lottery_item_seq}';
		var lottery_item_name = '${lottery.award.lottery_item_name}';
		var lottery_coupon_name = '${lottery.award.lottery_coupon_name}'; //奖项名称
		var lottery_item_type = '${lottery.award.lottery_item_type}'; //奖项类别
		
		var lotteryStr = '【'+lottery_item_name+'】'; 
		if (lottery_coupon_name!=null && lottery_coupon_name!="" && lottery_coupon_name!='null') {
			lotteryStr += '【'+lottery_coupon_name+'】';
		}
		jQuery("#lottery").html(lotteryStr);
		var str = "";
		if (lottery_item_type ==1) { // 如果奖品类型是劵将立即领取隐藏
			$("#goLottery").hide();
			str = "1张";
		} else {
			$("#goLottery").show();
			str = "1个";
		}
		$("#lotteryUnit").html(str);
		$("#address_show_lottery").html(lotteryStr+str); // 填写地址页面显示
		
	} 
/* 	if(remainCount==0) {
		$("#goLottery").text('确定');
		$("#goLotteryNo").text('确定');
	} */
	
	jQuery("#frequency").html(remainCount);
	jQuery("#nofrequency").html(remainCount);
	
	

	 sp = $("#JS_wScratchPad").wScratchPad({
		cursor:'',
		color:'#B6B4B6',
		image:img,	
		scratchMove: function(e, percent){
			 if(percent > 70)
			this.clear();
		}
	});

	/*
	function resetSP(){
		sp.wScratchPad('reset');
	}
	$('#JS_wScratchPadReset').bind('click',function(){
		resetSP();
	});
 */
});	

</script> 
</body>
</html>
