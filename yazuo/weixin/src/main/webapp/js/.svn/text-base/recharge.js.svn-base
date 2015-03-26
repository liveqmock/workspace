/* 考核预测 */

function vad_sub(id) {
	var reg = /^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9]))\d{8}$/;// 手机号码验证
	if (!reg.test($(id).val())) {// 为空或错误时提示错误信
		return false;
	} else {
		return true;
	}
}

function queryCardInfo(cxt){
	//判断手机号输入
	$("#phone").bind("focus",function(){ $("#JS_errorTip").hide(); });
	$("#JS_submitPhoneNumBtn").bind("click",function(){
			var userPhone=$.trim($("#phone").val());
			var reg = /^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9]))\d{8}$/;
			if(userPhone === ''){
				$("#JS_errorTip").text("请输入手机号");
				$("#JS_errorTip").show();
				return;	
			}else if(!reg.test(userPhone)){
				$("#JS_errorTip").text("输入的手机号码有误");
				$("#JS_errorTip").show();
				return;		
			}else{
				//验证通过  
				$.ajax({
				 type: "post",
				 url:  cxt+'/weixin/1120/goObtainCardInfo.do',
				 cache: false,
				 dataType : "json",
				 data: 'mobile='+userPhone,
				 success: function(reStr){
					 $.each(restr.key,function(i,n){
						 alert(n);
					 });
				 }
	    	});
			
				//success 后设置
				$("#JS_phoneNumForm").hide();
				$("#JS_selectTypePart").show();	
			}
	});
}


//储值卡及金额提交
$("#JS_kindSubmitBtn").bind("click",function(){
	var cardNum=$("#JS_cardType").val();
	var moneyNum=$("#JS_moneyType").val();
	
	$.ajax({
		 url:  ctx+'/',
		 cache: false,
		 type: "post",
		 dataType : "json",
		 data: {},
		 success: function(reStr){
			 
		 }
	});
	
	//success后设置
	$("#JS_selectTypePart").hide();
	$("#JS_successTipPart").show();
	
		
});

