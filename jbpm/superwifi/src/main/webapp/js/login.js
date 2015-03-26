
/*优惠券收起展开效果*/
$(".couponItem dt").bind("click",function(){
	$(this).find("i").toggleClass("foldIco");
	$(this).parent().find("dd").toggle();
});


/*手机号验证*/
function isPhoneNum(phoneNum){
	var filter=/^1[3|4|5|7|8]\d{9}$/; 
	var filter2=/^9\d{10}$/; 
	newPhoneNum=phoneNum.replace(/-/g,'');
	return filter.test(newPhoneNum)||filter2.test(newPhoneNum); 
}

var inputEvtName = "keyup";
if ('oninput' in document.createElement('input')) {
	inputEvtName = "input";
}
$("#JS_loginPhoneNum").bind(inputEvtName, function(){
	var phoneNumFlag=isPhoneNum($(this).val());
	
	if(phoneNumFlag){
		$("#JS_sendVerifyCodeSpan").hide();
		$("#JS_sendVerifyBtn").show();
	}else{
		$("#JS_sendVerifyCodeSpan").show();
		$("#JS_sendVerifyBtn").hide();
	}
});

function fillZero(num, digit){
	var str=''+num;	
	while(str.length<digit){
		str='0'+str;
	}	
	return str;
}
/*点击 发送验证码*/
jQuery(function($) {
	$('#JS_sendVerifyBtn').on('click',function(){
		$("#JS_sendVerifyBtn").hide();
		$("#JS_sendVerifyCodeSpan").html("重新发送(<em id='secondsCount'>59</em>秒)");
		$("#JS_sendVerifyCodeSpan").show();
		
		//发送验证码方法
		requestPassword($("#JS_loginPhoneNum").val(),deviceMac,userMac);
		
		var secondsTimer=setInterval(function(){
			var nowSeconds=Number($("#secondsCount").text());   //转化成数字
			if(nowSeconds > 1){
				var thisSec=fillZero((nowSeconds-1), 2);
				$("#secondsCount").text(thisSec);
			}else{
				$("#JS_sendVerifyCodeSpan").text("获取验证码");			
				clearTimeout(secondsTimer);
				if(isPhoneNum($("#JS_loginPhoneNum").val())){
					$("#JS_sendVerifyCodeSpan").hide();
					$("#JS_sendVerifyBtn").show();
				}else{
					$("#JS_sendVerifyCodeSpan").show();
					$("#JS_sendVerifyBtn").hide();
				}			
			};		
		},1000);
	});
	xy_check();
$(document).on("click","#xy-check",function(){
	xy_check();
})
	
});
function xy_check(){
	var xy_check = $("#xy-check").attr("checked");
	if(xy_check=="checked"||xy_check==true){
		$("#JS_wifiLoginBtn").show();
		$("#JS_wifiLoginBtnDisabled").hide();	
	}else{
		$("#JS_wifiLoginBtn").hide();
		$("#JS_wifiLoginBtnDisabled").show();	
	}
}
function requestPassword(mobileNumber,deviceMac,userMac,deviceSSID){
	if (!isPassWordCheck) {
		$.mobile.loading('show');
		$("#JS_wifiLoginBtn").hide();
		$.ajax({
			type : "post",
			url :  '/controller/login/requestPassword.do',
			cache : false,
			dataType : "json",
			data : 'mobileNumber=' + mobileNumber+'&deviceMac='+deviceMac+'&userMac='+userMac+'&deviceSSID='+deviceSSID,
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				$.mobile.loading('show');
			},
			success : function(a) {
				if(!isPassWordCheck){
					window.location.href=a.data.redirectUrl;
				}else{
					$.mobile.loading('hide');
					$("#JS_wifiLoginBtn").show();
				}
			}
		});
	} else {
		$.ajax({
			type : "post",
			url :  '/controller/login/requestPassword.do',
			cache : false,
			dataType : "json",
			data : 'mobileNumber=' + mobileNumber+'&deviceMac='+deviceMac+'&userMac='+userMac+'&deviceSSID='+deviceSSID,
			error : function(XMLHttpRequest) {// 请求失败时调用函数
			},
			success : function(a) {
			}
		});
	}

	
		
	 
}

function login(mobileNumber,password,deviceMac,userMac,deviceSSID){
	 var data='mobileNumber=' + mobileNumber+'&password='+password+'&deviceMac='+deviceMac+'&userMac='+userMac+'&deviceSSID='+deviceSSID;
	 $("#JS_wifiLoginBtn").hide();
	 $.ajax({
			type : "post",
			url :  '/controller/login/login.do',
			cache : false,
			dataType : "json",
			data : data,
			error : function(XMLHttpRequest) {// 请求失败时调用函数
			},
			success : function(a) {
				if(a!=null){
					if(a.status=='success'){
						window.location.href=a.url;
					}else{
						$("#JS_wifiLoginBtn").show();
						$("#JS_errorTip").text(a.data);
						$("#JS_errorTip").show();
					}
					
				}else{
					$("#JS_wifiLoginBtn").show();
					$("#JS_errorTip").show();
				}
			}
		});
}

$("#JS_wifiLoginBtn").bind("click",function(){
	
	var mobileNumber=$("#JS_loginPhoneNum").val();
	var password=$("#JS_verifyCodeTxt").val();
	
	if(mobileNumber == '' || !isPhoneNum(mobileNumber) || password == ''){
		$("#JS_errorTip").show();
		return;
	}
	//$(this).fadeOut().delay(10000).fadeIn(200);
	login(mobileNumber,password,deviceMac,userMac,deviceSSID);
	//验证通过，执行登陆操作
	
});

$(".wifirenzheng").bind("click",function(){
	$("#div2").show();
	$("#div1").hide();
});

$(".xy-back").bind("click",function(){
	$("#div1").show();
	$("#div2").hide();
});

