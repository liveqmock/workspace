<%@ include file="/common/meta.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class='redBg'>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<script type="text/javascript" language="javascript">
	var ctx = "<%=basePath%>";
</script>
<script src="<%=basePath %>js/prize-list.js"></script>

<title>幸运转盘</title>
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
	<div class='wheelInfo'>

        <c:if test="${lottery.length == 1}">
        	<div id='disk' class='wheelBg wheelBg1'></div>
        </c:if>
        <c:if test="${lottery.length == 2}">
        	<div id='disk' class='wheelBg wheelBg2'></div>
        </c:if>
        <c:if test="${lottery.length == 3}">
        	<div id='disk' class='wheelBg wheelBg3'></div>
        </c:if>
        <c:if test="${lottery.length == null}">
        	<div id='disk' class='wheelBg wheelBg1'></div>
        </c:if>
		<div id="start">
			<img id="startbtn" src="<%=basePath%>/lotteryImages/wheelPoint.png" style="transform: rotate(0deg);" />
		</div>
	</div>
	<p class='wheelPlayRulesBg'></p>
	
	<!-- 没机会时，在大转盘上提示 -->
	<div id="JS_noChanceTips">您已没有抽奖机会了，感谢您的参与！</div>
	<!-- 没机会时，在大转盘上提示  end-->
	
	<c:if test="${lottery.winflag == 4}">
		<div class='wheelPlayRules' id ='luck'>
			<ul class="playRulesInfo">
	        	<li><b>活动日期：</b>${lottery.begin_time} — ${lottery.end_time}</li>
	            <li><b>您还剩余：</b><em id='pafrequency'>${lottery.number}</em>次机会</li>
	            <li>中奖信息可在中奖记录菜单中看到中奖情况！<!-- <a href="javascript:getCard();">点击查看</a> --></li>
	        </ul>
	        <script>
	        	$("#JS_noChanceTips").hide();
	        </script>		
		</div> 
	</c:if>
	<c:if test="${lottery.winflag != 4}">
		<div class='wheelPlayRules'>
			<ul class="playRulesInfo">
	            <li>${error}</li>
	            <li><b>活动日期：</b>${lottery.begin_time} — ${lottery.end_time}</li>
	            <li><b>您还剩余：</b><em> 0 </em>次机会</li>
	            <li>中奖信息可在中奖记录菜单中看到中奖情况！</li>	            
	        </ul>	
	        <script>
	        	$("#JS_noChanceTips").show();
	        </script>	
		</div>
	</c:if>
	<div class='wheelPlayRules' id='luckerror' style="display:none">
		<ul class="playRulesInfo">
            <li id ='error'>${error}</li>
            <li><b>活动日期：</b>${lottery.begin_time} — ${lottery.end_time}</li>
            <li><b>您还剩余：</b><em id="remainNumberOfOne"> 0 </em>次机会</li>
            <li>中奖信息可在中奖记录菜单中看到中奖情况！</li>
        </ul>		
	</div>
	<p class='wheelPageBg'></p>
</section>

<div class="popUp" id="noPopUp" style="display:none">
	<div class="popInfo">
    	<h3 class="popBorderTitle">
    		<p class='headBorder'></p>
    		<p class='headText'>没抽中，<span id='JS_randomText'></span>！<br />您还有<em id='nofrequency'>0</em>次机会，加油！</p></h3>
        <div class="giftCoupon">
        	<%-- <img src="<%=basePath%>/lotteryImages/tigerDefault.png"  id ='noWinning'/> --%>
        </div>
        <div class="popBtns"><a href="javascript:luckyDraw();" class="grayBtn2" id='goLotteryNo'>关 闭</a></div>
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
    		<p class='headText'>您还有<em id='frequency'>0</em>次机会</p></h3>
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
<%@include file="/jsp/coffee/lottery/add_address.jsp" %>
</form>
<script type="text/javascript">
var jsflag =2;
function luckyDraw() {
	$("#buy_address_div").hide();
	var pafrequency = jQuery("#pafrequency").html();
	if(pafrequency == 0) {
		jsflag =1;
	} else {
		jsflag=2;
	}
	document.getElementById("popUp").style.display='none';
	document.getElementById("noPopUp").style.display='none';
	/*
	$("#startbtn").rotate({
	 	duration:0,
	 	angle: 0, 
		animateTo:0,
		easing: $.easing.easeOutSine,
		
	});
	*/
}

function getCard() {
    document.getElementById("form1").action = "<%=basePath%>/weixin/phonePage/fensiCard.do";
    document.getElementById("form1").submit();
    
}



$(function(){
	$("#startbtn").rotate({
		bind:{
			click:function(){
				var wiflag = '${lottery.winflag}';
				if (jsflag==1 || wiflag<4) {
					return false;
				}
				jsflag=1;
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
			             cache: false,
			             //dataType:"String",
			             data:strs,
			             success : function(retObj) {
			            	
			            	var winflag = retObj.map.winflag;//中奖状态
			            	var degrees = retObj.map.award.degrees;//角度
			            	var remainCount = retObj.map.award.remainCount;//剩余抽奖次数
			            	var lottery_item_name = retObj.map.award.lottery_item_name;// 奖品名称
			            	var lottery_item_seq = retObj.map.award.lottery_item_seq;// 奖品等级
			            	var lottery_item_pic = retObj.map.award.lottery_item_pic;// 奖品图片
			            	var lottery_pic = retObj.map.lottery_pic;// 图片地址
			            	
			            	if(lottery_item_pic!=null && lottery_item_pic.substr(0,4) == 'M00/') {// 是否为新图片
								lottery_pic = retObj.map.newPictureUrl;    		
			            	}
			            	
			            	var lasttime = retObj.map.award.lasttime;// 是否是最后一次
			            	var lottery_coupon_name = retObj.map.award.lottery_coupon_name; //奖项名称
			            	var error = retObj.error;
			            	var lottery_item_type = retObj.map.award.lottery_item_type; // 奖品类别
			            	
			            	$("#lotterName").val(retObj.map.award.lottery_coupon_name);
			            	$("#orderId").val(retObj.map.award.orderId);
			            	if (lottery_item_type ==1) { //劵
			            		$("#goLottery").hide();
			            		$("#btn_close").show();
			            	}else{
			            		$("#goLottery").show();
			            		$("#btn_close").show();
			            	}
			        		
			            	if(winflag <4) {//不能抽奖了
								document.getElementById("luckerror").style.display='';
						        document.getElementById("luck").style.display='none';
						        jQuery("#error").html(error);
								return false;
							} 
			            	
							$("#startbtn").css('transform','rotate(0deg)');
							 $("#startbtn").rotate({
								 	duration:3000,
								 	angle: 0, 
			            			animateTo:1440+degrees,
									easing: $.easing.easeOutSine,
									callback: function(){
										if(winflag == 4) {//中奖了
											document.getElementById("popUp").style.display='';
											$("#winning").attr("src",lottery_pic+lottery_item_pic);
											jQuery("#frequency").html(remainCount);
											var lotteryStr = '【'+lottery_item_name+'】'; 
											if (lottery_coupon_name!=null && lottery_coupon_name!="" && lottery_coupon_name!='null') {
												lotteryStr += '【'+lottery_coupon_name+'】';
											}
											jQuery("#lottery").html(lotteryStr);
											var str = "";

											if (lottery_item_type==1) { //劵
												str = "1张";
												if(remainCount==0) { // 最后一次
													$("#btn_close").text('确定');
												}
											} else { // 实物
												str = "1个";														
											}
											$("#lotteryUnit").html(str);
											$("#address_show_lottery").html(lotteryStr+str); // 填写地址页面显示
										} else {
											document.getElementById("noPopUp").style.display='';
											jQuery("#nofrequency").html(remainCount);
											//==================================
											var iChance=$("#JS_lotteryChanceNum").text();	
											$("#JS_randomText").text(getResults(iChance));
											//if(remainCount==0) {
												//$("#goLotteryNo").text('确定');
											//}
										}
										if(remainCount==0 || lasttime == 1) {
											document.getElementById("luckerror").style.display='';
									        document.getElementById("luck").style.display='none';
									        $("#remainNumberOfOne").html(remainCount); // 规则的剩余次数为1时，要是还有机会将机会数显示出来
									        if(lasttime == 1) {
									        	jQuery("#error").html("奖品已经送完，活动结束了，感谢您的参与！");
									        } else {
									        	jQuery("#error").html("您已没有抽奖机会了，感谢您的参与！");
									        	$("#JS_noChanceTips").show();
									        }
										}
										jQuery("#pafrequency").html(remainCount);
										
									}
							 });
			             },
			             error : function(XMLHttpRequest) {
			            	 	jsflag=2;
								alert("解析请求失败");
						 }
			     });
			}
		}
	});
});





</script>



</body>
</html>
