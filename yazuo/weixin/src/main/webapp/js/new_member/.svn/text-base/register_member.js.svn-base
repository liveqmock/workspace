$(function(){
  //获取验证码
    $('#getIdentifyNO').on('click',function(){
    	if(!vad_sub('#phoneNo')){
    		$(".mesg-error").show();
    		 $(".mesg-error .mesg-error-tips").html('');
    		 $(".mesg-error .mesg-error-tips").html("手机号输入错误");
    	     return false;
    	}
    	 var count = 60;//每间隔六十秒获取验证码
    	 var timeId = "";
    	 function countSecond(){
    		if(count==0){
    		window.clearInterval(timeId);
    		count=60;
    		$('#getIdentifyNO').html('获取验证码');
    		document.getElementById('getIdentifyNO').disabled = false;
    		return false;
    		}
    		$('#getIdentifyNO').html(count+'秒后获取');
    		document.getElementById('getIdentifyNO').disabled = true;
    		count--;
    	}
    	  timeId =window.setInterval(countSecond,1000);

       //获取验证码之前先对用户基本信息验证
    	var requestData = {};
    	requestData.brandId = $("#brandId").val();;
		requestData.weixinId = $("#weixinId").val();
    	requestData.title = $("#title").val();
    	requestData.phoneNo = $('#phoneNo').val().trim();
    	$.ajax({
    	 type: "post",
    	 url: ctx + '/weixin/phonePage/registerIdentifyingCode.do?random='+Math.random(),
    	 cache: false,
    	 dataType : "text",
    	 data: requestData,
    	 success: function(json){
    		 $(".mesg-error").show();
    		 if(json.trim().length==3){//成功获取验证码
    			 $(".mesg-error .mesg-error-tips").html('');
    			 $(".mesg-error .mesg-error-tips").html('验证码已通过短信的形式发送到您的手机上');
    		 }else if(json.trim()=="-1"){
    			 $(".mesg-error .mesg-error-tips").html('');
    			 $(".mesg-error .mesg-error-tips").html('抱歉，微信端暂时无法注册会员，请到门店办理');
    		 }else{
    			 $(".mesg-error .mesg-error-tips").html('');
    			 $(".mesg-error .mesg-error-tips").html('请重新获取');
    		 }
    	 },
    	 error: function(XMLHttpRequest){
    		 if (processCommErr(XMLHttpRequest)) {
    			 $(".mesg-error").show();
    			 $(".mesg-error .mesg-error-tips").html('');
    		     $(".mesg-error .mesg-error-tips").html('获取失败请重新获取');
    		 }
    	 }
    	});
    });
	
});

function identityUserInfo(){ //用户基本信息验证
	$(".mesg-error .mesg-error-tips").html('');
	$(".mesg-error").show();
	if($("#uname").val()==null || $("#uname").val()==''||/^[0-9_]+$/.test($("#uname").val())){//姓名
		 $(".mesg-error .mesg-error-tips").html("名字填写有误");
	     return false;
	}else if(!(vad_sub('#phoneNo'))){//手机
		 $(".mesg-error .mesg-error-tips").html("手机号输入错误");
	     return false;
	}else if(isAllowWeixinMember=='true' && $(".mem-vadm").val()==''){//验证码
		$(".mesg-error .mesg-error-tips").html("验证码输入错误");
	    return false;
	}else{
		$(".mesg-error").hide();
		return true;
	}

 }
function vad_sub(id) {
	var reg = /^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\d{8}$/;// 手机号码验证
	if (!reg.test($(id).val()) || $(id).val().trim() == '') {// 为空或错误时提示错误信
		return false;
	} else {
		return true;
	}
}

//提交用户信息
var saveMember = function(){ //
	var birthType =$("input[name='birthButton']:checked").val();
	var gender = $("input[name='gender']:checked").val();
	var birthday = $("#year").siblings("span").text().replace("年",'').trim()+$("#month").siblings("span").text().replace("月",'').trim()
	+$("#day").siblings("span").text().replace("日",'').trim();
	/*var year =$('#year').attr("value");
	var month = $('#month').attr("value"); 
	var day = $('#day').attr("value");
	var birthday =""+year+month+day;*/
	var brandId = $("#brandId").val();
	var weixinId =  $("#weixinId").val();
	
	var identifyNo="";
	if(isAllowWeixinMember=='true'){
		identifyNo=$('#identifyNo').val().trim();//获取验证码
	}
	var isMember = $("#isMember").val();
	if(isMember=="true"){//是会员时
		 if(!identityUserInfo()){
			 $('.m-btn3').show();
			// $('#getIdentifyNO').show();
		    	return false;
		    };
		$('.m-btn3').hide();//点击提交后隐藏起来，防止重复提交
		$('#getIdentifyNO').hide();
		$(".mesg-error .mesg-error-tips").html('');
		$(".mesg-error").show();
		$(".mesg-error .mesg-error-tips").html('提交中请稍后.....');
		var requestData = {};
		requestData.brandId = brandId;
		requestData.weixinId =weixinId;
		requestData.phoneNo = $('#phoneNo').val().trim();
		requestData.gender = gender;
		requestData.birthType = birthType;
		requestData.name = $('#uname').val().trim();
		requestData.birthday = birthday;
		$.ajax({
		type:"post",
		url:ctx + '/weixin/phonePage/modifyUserInfo.do?random='+Math.random(),
		dataType:"text",
		data:requestData,
		success:function(json){
			var url = $("#toUrl").val();;
			if(json.trim()=="success"){//修改成功
				$(".mesg-error .mesg-error-tips").html('修改成功');
				if (url!=null && url!="") {
					window.location.href=ctx + url;
				} else {
					window.location.href=ctx+"/weixin/phonePage/fensiCard.do?brandId="+brandId+"&weixinId="+weixinId+"&random="+Math.random();
				}
			}else if(json.trim()=="error"){
				$('.m-btn3').show();
//				$('#getIdentifyNO').show();
				$(".mesg-error .mesg-error-tips").html("修改失败");
			}else if(json.trim()=="beyond"){
				$('.m-btn3').show();
//				$('#getIdentifyNO').show();
				$(".mesg-error .mesg-error-tips").html("超过修改次数,不能在修改");
			}else if(json.trim()=="modifyFailure"){//调用接口失败
				$('.m-btn3').show();
//				$('#getIdentifyNO').show();
				$(".mesg-error .mesg-error-tips").html("稍后重试");
			}else{
				$('.m-btn3').show();
//				$('#getIdentifyNO').show();
				$(".mesg-error .mesg-error-tips").html("系统异常，稍候重试");//
			}
		},
		 error: function(XMLHttpRequest){
			 if (processCommErr(XMLHttpRequest)) {
			     $(".mesg-error .mesg-error-tips").html('修改失败~~');
			 }
		 }
		});
	}else{//新用户注册时
		/*if(isNaN(year)||isNaN(month)||isNaN(day)){
			birthday="";
		}*/
		 if(!identityUserInfo()){
			 $('.m-btn3').show();
//			 $('#getIdentifyNO').show();
		    	return false;
		 };
		$('.m-btn3').hide();//点击提交后隐藏起来，防止重复提交
		$('#getIdentifyNO').hide();
		$(".mesg-error .mesg-error-tips").html('');
		$(".mesg-error").show();
		$(".mesg-error .mesg-error-tips").html('提交中请稍后.....');
		var requestData = {};
		requestData.brandId = brandId;
		requestData.weixinId =weixinId;
		requestData.phoneNo = $('#phoneNo').val().trim();
		requestData.gender = gender;
		requestData.birthType = birthType;
		requestData.name = $('#uname').val().trim();
		requestData.birthday = birthday;
		requestData.identifyCode = identifyNo;
		requestData.source = $("#dataSource").val();
			
		$.ajax({
			type:"post",
			url:ctx+'/weixin/phonePage/saveUser.do?random='+Math.random(),
			dataType:"json",
			data: requestData,
			success:function(data){
				var json = data.status;
				var url = $("#toUrl").val();
				if(json.trim()=="success"){
					$(".mesg-error .mesg-error-tips").html("您已成功成为粉丝");
					if (url!=null && url!="") {
						window.location.href=ctx + url;
					} else if('${member.dataSource}'=='13'){ //数据来源13微信wifi会员，
						window.location.href = ctx +"/weixin/cardLottery/wifiBindSuccess.do?couponIdStr="+data.couponIdStr+"&luckCount="+data.lunckCount
								+"&brandId="+brandId+"&weixinId="+weixinId+"&flag="+data.oldMemberFlag+"&membershipId="+data.membershipId+"&phoneNo="+data.phoneNo+"&random="+Math.random();
					}else {
//						window.location.href=ctx+"/weixin/phonePage/fensiCard.do?"+"brandId="+brandId+"&weixinId="+weixinId+"&random="+Math.random();
						window.location.href=ctx+"/weixin/phonePage/membershipCenter.do?"+"brandId="+brandId+"&weixinId="+weixinId+"&mobile="+data.phoneNo
						+"&cardNo="+data.cardNo+"&cardtypeId="+data.cardtypeId+"&storeBalance="+data.storeBalance+"&integralAvailable="+data.integralAvailable+"&random="+Math.random();
					}
				}else if(json.trim()=="error"){//商家不允许电子注册会员
					$('.m-btn3').show();
//					$('#getIdentifyNO').show();
					$(".mesg-error .mesg-error-tips").html('请到实体店办理');
				}else if(json.trim()=="identifyError"){
					$('.m-btn3').show();
//					$('#getIdentifyNO').show();
					$(".mesg-error .mesg-error-tips").html('验证码错误');
				}else if(json.trim()=="identifyExpire"){
					$('.m-btn3').show();
//					$('#getIdentifyNO').show();
					$(".mesg-error .mesg-error-tips").html('验证码过期,重新获取');
				}else if(json.trim()=="3"){
					$('.m-btn3').show();
//					$('#getIdentifyNO').show();
					$(".mesg-error .mesg-error-tips").html('信息格式错误');
				}else if(json.trim()=="4"){
					$('.m-btn3').show();
//					$('#getIdentifyNO').show();
					$(".mesg-error .mesg-error-tips").html('您已经是粉丝会员');
				}else if(json.trim()=="registerError"){//调用接口失败
					$('.m-btn3').show();
//					$('#getIdentifyNO').show();
					$(".mesg-error .mesg-error-tips").html('注册失败,稍候重试');
				}else if(json.trim()=="NoIdentify"){//验证码没有申请，就提交了注册信息
					$('.m-btn3').show();
//					$('#getIdentifyNO').show();
					$(".mesg-error .mesg-error-tips").html('验证码错误~~');
				}else if(json.trim()=="saveFailure"){//更新用户到crm库里时失败
					$('.m-btn3').show();
//					$('#getIdentifyNO').show();
					$(".mesg-error .mesg-error-tips").html('系统有误,稍候....');
				}else if (json.trim()=="1") {
					$('.m-btn3').show();
					//$('#getIdentifyNO').show();
					$(".mesg-error .mesg-error-tips").html("生日输入错误");
				} else {
					$('.m-btn3').show();
//					$('#getIdentifyNO').show();
					$(".mesg-error .mesg-error-tips").html("系统异常，稍候重试");
				}
				
			},
			error:function(XMLHttpRequest){
				 $('.m-btn3').show();
//				 $('#getIdentifyNO').show();
			    $(".mesg-error .mesg-error-tips").html('提交失败~~~');
			}
		});
	}
};

window.setInterval(function(){
if($(".mesg-error .mesg-error-tips").html()!=''){
	window.setTimeout(function(){
		$(".mesg-error .mesg-error-tips").html('');
		$(".mesg-error").hide();
	},3000);
}
},3000);
