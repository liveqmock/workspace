$(function(){
	$(".select-option").click(function(){//下拉
		var dis = $(this).siblings("ul").css("display");
		if(dis=="none"){
			$(this).siblings("ul").show();
		}else{
			$(this).siblings("ul").hide();
		}
	});
	$(".prize-select li").click(function(){
		var value = $(this).text();
		$(this).parent().hide();
		$(".prize-zt").text(value);
	});
	
	$(".prize-list li").click(function(){
		$(".list-detail").hide();
		$(".icon-arrow-b").removeClass("icon-arrow-t");
		$(this).children(".icon-arrow-b").addClass("icon-arrow-t");
		$(this).next(".list-detail").show();
	});
	
	$(".draw-submit").on('click',function(){
		var detailAddress = $("#detail_address").val();
		var receiver = $("#receiver").val();
		var mobile = $("#mobile").val();
		 if (judgeEmpty(receiver)) {
				$("#span_error").html("<font color='red'>收货人不能为空!</font>");
				return;
		}else if (judgeEmpty(mobile)) {
			$("#span_error").html("<font color='red'>收货人手机号不能为空!</font>");
			return;
		}else if (judgeEmpty(detailAddress)) { // 判断收货地址是否为空
			$("#span_error").html("<font color='red'>收货地址不能为空!</font>");
			return;
		} else {
			if(!vad_sub(mobile)){
				$("#span_error").html("<font color='red'>手机号输入有误!</font>");
				return;
			}
			$("#span_error").html("");
		}
		$("#buy_address_div").hide();
		$("#comfirm_address_div").show();
	});
});

function submitAddress () {
	saveAddress();
}
function recordSaveAddress() {
	var detailAddress = $("#detail_address").val();
	var receiver = $("#receiver").val();
	var mobile = $("#mobile").val();
	 if (judgeEmpty(receiver)) {
			$("#span_error").html("<font color='red'>收货人不能为空!</font>");
			return;
	}else if (judgeEmpty(mobile)) {
		$("#span_error").html("<font color='red'>收货人手机号不能为空!</font>");
		return;
	}else if (judgeEmpty(detailAddress)) { // 判断收货地址是否为空
		$("#span_error").html("<font color='red'>收货地址不能为空!</font>");
		return;
	} else {
		if(!vad_sub(mobile)){
			$("#span_error").html("<font color='red'>手机号输入有误!</font>");
			return;
		}
		$("#span_error").html("");
	}
	 $("#comfirm_address_div").show();
}

// 抽奖信息提交
function saveAddress(){
	var detailAddress = $("#detail_address").val();
	var lotterName = $("#lotterName").val();
	var receiver = $("#receiver").val();
	var weixinId = $("#weixinId").val();
	var mobile = $("#mobile").val();
    var cardMoblie = $("#phoneNo").val();
    if (cardMoblie==null || cardMoblie=="") {
    	cardMoblie = mobile;
    }
    var str = receiver+","+mobile+","+detailAddress;
	var requestData = "detailAddr="+str+"&productInfo="+lotterName+"&receiver="+receiver+"&mobile="+cardMoblie+
		"&openId="+weixinId+"&orderId="+$("#orderId").val()+"&brandId="+$("#brandId").val();
	window.location.href = ctx + "/caffe/cardLottery/saveLotteryMessage.do?" + requestData;
}

// 提示确认收货地址点取消
function btnCancle () {
	$("#comfirm_address_div").hide();
	$("#buy_address_div").show();
	$("#popUp").hide();
}

// 立即领取
function nowDraw() {
	$("#receiver").val("");
	//$("#mobile").val("");
	$("#detail_address").val("");
	$("#buy_address_div").show();
}

function judgeEmpty (str) {
	if (str == null || str =="") {
		return true;
	} else {
		return false;
	}
}

// 手机号码验证
function vad_sub(valStr) {
	var reg = /^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9]))\d{8}$/;
	if (!reg.test(valStr)) {// 为空或错误时提示错误信息
		return false;
	} else {
		return true;
	}
}

// 立即抽奖跳转到抽奖页面
function nowLuck(brandId, weixinId) {
	$.ajax({
		url:ctx+"/caffe/cardLottery/nowLuckEvent.do?brandId="+brandId+"&weixinId="+weixinId,
		type:"post",
		dataType:"json",
		success:function(responseData) {
			if (responseData.flag ==1) {
				window.location.href = responseData.data;
			}
		}
	});
}

// 查看中奖记录
function lookLotteryRecord (brandId, weixinId) {
	window.location.href = ctx + "/caffe/cardLottery/lookLotteryRecord.do?weixinId="+weixinId+"&brandId="+brandId;
}

// 查看会员信息
function lookCardDetial (brandId, weixinId) {
	window.location.href = ctx + "/weixin/phonePage/fensiCard.do?brandId="+brandId+"&weixinId="+weixinId+"&random="+Math.random();
}

// 未填写地址时，点请填写地址进入地址填写页面
function redirectAddAddr (lotteryItemName, lotteryName, orderId, weixinId, membershipId, brandId) {
	window.location.href = ctx+"/caffe/cardLottery/redirectRervierPage.do?membershipId="+membershipId+"&lotteryItemName="+lotteryItemName+"&lotteryName="+lotteryName+"&weixinId="+weixinId+"&orderId="+orderId+"&brandId="+brandId;
}