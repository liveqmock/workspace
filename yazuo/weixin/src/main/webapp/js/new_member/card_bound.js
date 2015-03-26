$(function(){
	// 初始化信息
	init();
	//获取验证码
	$('#getIdentifyNO').on('click', function() {
		if (!vad_sub('#phoneNo')) {
			$(".mesg-error").show();
			$(".mesg-error .mesg-error-tips").html("");
			$(".mesg-error .mesg-error-tips").html("手机号输入错误");
			return false;
		}
		var count = 60;//每间隔六十秒获取验证码
		var timeId = "";
		function countSecond() {
			if (count == 0) {
				window.clearInterval(timeId);
				count = 60;
				$('#getIdentifyNO').html('获取验证码');
				document.getElementById('getIdentifyNO').disabled = false;
				return false;
			}
			$('#getIdentifyNO').html(count + '秒后获取');
			document.getElementById('getIdentifyNO').disabled = true;
			count--;
		}
		timeId = window.setInterval(countSecond, 1000);
		var requestData = {};
		requestData.brandId = $("#brandId").val();
		requestData.weixinId =$("#weixinId").val();
		requestData.phoneNo = $('#phoneNo').val().trim();
		requestData.title = $("#title").val();
		//获取验证码
		$.ajax({
		 type : "post",
		 url : ctx+'/weixin/phonePage/registerIdentifyingCode.do?random='+Math.random(),
		 cache : false,
		 dataType : "text",
		 data : requestData,
		 success: function(json){
			 $(".mesg-error .mesg-error-tips").html("");
			 $(".mesg-error").show();
			 if(json.trim().length==3){//成功获取验证码
				 $(".mesg-error .mesg-error-tips").html('验证码已通过短信的形式发送到您的手机上');
			 }else if(json.trim()=="-1"){
				 $(".mesg-error .mesg-error-tips").html('抱歉，微信端暂时无法注册会员，请到门店办理');
			 }else{
				 $(".mesg-error .mesg-error-tips").html('请重新获取');
			 }
		 },
		 error: function(XMLHttpRequest){
			 if (processCommErr(XMLHttpRequest)) {
				 $(".mesg-error .mesg-error-tips").html('获取失败请重新获取');
			 }
		 }
		});
	});
});

//会员卡信息 提交前的表单验证
function identityUserInfo() { 
	$(".mesg-error .mesg-error-tips").html("");
	$(".mesg-error").show();
	if ($("#uname").val() == null || $("#uname").val().trim() == '' || /^[0-9_]+$/.test($("#uname").val().trim())) {//姓名
		$(".mesg-error .mesg-error-tips").html("名字填写有误");
		return false;
	} else if (!verifyCardInfo()) {
		return false;
	} else if (!(vad_sub('#phoneNo'))) {//手机
		$(".mesg-error .mesg-error-tips").html("手机号输入错误");
		return false;
	} else if ($(".mem-vadm").val() == '') {//验证码
		$(".mesg-error .mesg-error-tips").html("验证码输入错误");
	    return false;
	} else {
		return true;
	}
}

// 手机号码验证
function vad_sub(id) {
	var reg = /^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\d{8}$/;
	if (!reg.test($(id).val()) || $(id).val().trim() == '') {// 为空或错误时提示错误信息
		return false;
	} else {
		return true;
	}
}

//会员卡信息验证
function verifyCardInfo() {
	var isNeedPassword = $("#isNeedPwd").val();//$("#div_pass").is(":visible"); // 是否显示密码框
	var isRequireSuccess = $("#isRequireSuccess").val(); // 请求是否成功
	var errorMessageTip = $("#errorMessageTip").val(); // 错误信息
	$(".mesg-error .mesg-error-tips").html("");
	$(".mesg-error").show();
	if ($("#cardNum").val().trim() == '' || (!/^\d{16}$/.test($("#cardNum").val().trim()) && !/^\d{19}$/.test($("#cardNum").val().trim()))) {//会员卡卡号
		$(".mesg-error .mesg-error-tips").html("卡号填写有误");
		return false;
	}else if ($("#secCode").val().trim() == '' || !/^\d{3}$/.test($("#secCode").val().trim())) {//安全码
		$(".mesg-error .mesg-error-tips").html("安全码填写有误");
		return false;
	} else {
		if (isNeedPassword=="true") { // 需要输入密码，验证密码
			if ($("#userPwd").val().trim() == '' || !/^\d{6}$/.test($("#userPwd").val().trim())) {//密码
				$(".mesg-error .mesg-error-tips").html("密码必须是6位数字");
				return false;
			}else {
				var pwd = $("#userPwd").val().trim();
				var first = pwd.charAt(0);
				var equalCount = 0;
				for (var i=1; i<pwd.length;i++) {
					if (first == pwd.charAt(i)) {
						equalCount++;	
					}
				}
				var cardNo = $("#cardNum").val().trim();
				// 取卡号后六位
				var sixCard = cardNo.length >16 ? cardNo.substring(cardNo.length-3-6, cardNo.length-3) : cardNo.substring(cardNo.length-6);
				if (sixCard== pwd) {
					$(".mesg-error .mesg-error-tips").html("密码不能是卡后6位");
					return false;
				} else if (equalCount == pwd.length-1) { // 密码不能输入6个相同
					$(".mesg-error .mesg-error-tips").html("密码不能是6个相同的数字");
					return false;
				}else if ($("#userPwd2").val().trim() == '' || !/^\d{6}$/.test($("#userPwd2").val().trim())) {//确认密码
					$(".mesg-error .mesg-error-tips").html("确认密码必须是6位数字");
					return false;
				}else if ($.trim($("#userPwd").val()) !== $.trim($("#userPwd2").val())) { //密码
					$(".mesg-error .mesg-error-tips").html("两次密码输入不一致");
					return false;
				} else {
					return true;
				}
			}
		} else {
			if (isRequireSuccess=="false") {
				$(".mesg-error .mesg-error-tips").html(errorMessageTip);
				return false;
			}
		}
		return true;
	}
}

//提交用户信息
function saveMember(){
	
	var birthday = $("#year").siblings("span").text().replace("年",'').trim()+$("#month").siblings("span").text().replace("月",'').trim()
	+$("#day").siblings("span").text().replace("日",'').trim();
	var identifyNo="";
	if(isAllowWeixinMember=='true'){
		identifyNo=$('#identifyNo').val().trim();
	}
	/*if(isNaN(year)||isNaN(month)||isNaN(day)){
		birthday="";
	}*/
	if(!identityUserInfo()){
		$('.m-btn3').show();
//		$('#getIdentifyNO').show();
		return false;
	};
	$(".mesg-error .mesg-error-tips").html("");
	$(".mesg-error").show();
	$('.m-btn3').hide();//点击提交后隐藏起来，防止重复提交
//	$('#getIdentifyNO').hide();
	$(".mesg-error .mesg-error-tips").html('提交中请稍后.....');
	var brandId = $("#brandId").val();
	var weixinId = $("#weixinId").val();;
	var requestData = {};
	requestData.name = $('#uname').val().trim();
	requestData.gender = $('input[name="gender"]:checked').val();
	requestData.birthday = birthday;
	requestData.cardNum = $('#cardNum').val().trim();
	requestData.secCode = $('#secCode').val().trim();
	requestData.userPwd = $('#userPwd').val().trim();
	requestData.phoneNo = $('#phoneNo').val().trim();
	requestData.identifyCode = identifyNo;
	requestData.brandId = brandId;
	requestData.weixinId =weixinId;
	requestData.birthType = $("input[name='birthType']:checked").val();
	requestData.random = Math.random();
	$.ajax({
		type:"post",
		url:ctx+'/weixin/phonePage/cardBound.do',
		dataType:"json",
		data: requestData,
		success:function(data){
			var json = data.cardBoundStatus;
			if(json.trim()=="success"){
				//$('#submitInformation').show();
				//document.getElementById('submitSuccess').href=ctx+"/weixin/phonePage/fensiCard.do?"+"brandId="+"<%=brandId%>"+"&weixinId="+"<%=weixinId%>"+"&random="+Math.random();
				
				if (data.luckSuccess) { // 绑定卡成功送抽奖机会或赠劵成功
					window.location.href = ctx+"/caffe/cardLottery/boundSuccess.do?couponIdStr="+data.couponIdStr+"&luckCount="+data.lunckCount+"&brandId="+brandId+"&weixinId="+weixinId+"&random="+Math.random();					
				}else {
					window.location.href=ctx+"/weixin/phonePage/fensiCard.do?brandId="+brandId+"&weixinId="+weixinId+"&random="+Math.random();
				}
			}else if(json.trim()=="error"){//商家不允许电子注册会员
				$('.m-btn3').show();
//				$('#getIdentifyNO').show();
				$(".mesg-error .mesg-error-tips").html('请到实体店办理');
			}else if(json.trim()=="identifyError"){
				$('.m-btn3').show();
//				$('#getIdentifyNO').show();
				$(".mesg-error .mesg-error-tips").html('验证码错误');
			}else if(json.trim()=="identifyExpire"){
				$('.m-btn3').show();
//				$('#getIdentifyNO').show();
				$(".mesg-error .mesg-error-tips").html('验证码过期,重新获取');
			}else if(json.trim()=="3"){
				$('.m-btn3').show();
//				$('#getIdentifyNO').show();
				$(".mesg-error .mesg-error-tips").html('信息格式错误');
			}else if(json.trim()=="4"){
				$('.m-btn3').show();
//				$('#getIdentifyNO').show();
				$(".mesg-error .mesg-error-tips").html('您已经是粉丝会员');
			}else if(json.trim()=="registerError"){//调用接口失败
				$('.m-btn3').show();
//				$('#getIdentifyNO').show();
				$(".mesg-error .mesg-error-tips").html('注册失败,稍候重试');
			}else if(json.trim()=="saveFailure"){//更新用户到crm库里时失败
				$('.m-btn3').show();
//				$('#getIdentifyNO').show();
				$(".mesg-error .mesg-error-tips").html('系统有误,稍候....');
			}else if (json.trim()=="1") {
				$('.m-btn3').show();
//				$('#getIdentifyNO').show();
				$(".mesg-error .mesg-error-tips").html("生日格式错误");
			}else{
				$('.m-btn3').show();
//				$('#getIdentifyNO').show();
				$(".mesg-error .mesg-error-tips").html("信息填写有误，稍后重试");
			}
		},
		error:function(XMLHttpRequest){
			$('.m-btn3').show();
//			$('#getIdentifyNO').show();
			$(".mesg-error .mesg-error-tips").html('提交失败~~~');
		}
	});
	
}

window.setInterval(function() {
	if ($(".mesg-error .mesg-error-tips").html() != '') {
		window.setTimeout(function() {
			$(".mesg-error .mesg-error-tips").html('');
			$(".mesg-error").hide();
		}, 3000);
	}
}, 3000);

//add by sundongfeng 2014-07-16
function init(){
	var reg = /^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\d{8}$/;// 手机号码验证
	var phoneNo = $("#memberMobile").val();
	if(phoneNo!=''&&reg.test(phoneNo)){
		$("#phoneNo").val(phoneNo);
		$('input[name=phoneNo]').attr("readonly","readonly");
	}
}

// 根据卡类型是否要输入密码
function isNeedWritePassword() {
	var cardNo = $("#cardNum").val().trim();
	if (cardNo == '' || (!/^\d{16}$/.test(cardNo) && !/^\d{19}$/.test(cardNo))) {//会员卡卡号
		$(".mesg-error").show();
		$(".mesg-error .mesg-error-tips").html('');
		$(".mesg-error .mesg-error-tips").html("卡号填写有误");
		return false;
	}
	$.ajax({
		url:ctx+"/weixin/phonePage/judgeNeedPwd.do?brandId="+$("#brandId").val()+"&cardNo="+cardNo,
		type:"post",
		dataType:"json",
		success:function(data){
			$("#isNeedPwd").val(data.flag);
			$("#isRequireSuccess").val(data.isRequireSuccess);
			$("#errorMessageTip").val(data.message);
			if (data.flag) { // 需要输入密码
				$(".password").show();
			} else {
				$(".password").hide();
			}
		}
	});
}