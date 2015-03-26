var mac=null;
initMacs();

$(function(){
	$(document).on('input','.wifi-mac-search',function(){//mac搜索匹配
		var val,new_val,temp,new_mac;
		val = $(this).val();
		new_val = val.substring(0,2)+"-"+val.substring(2,4);
		$(".form-wifi-search-result ul").html('');
		for(i=0;i<mac.length;i++){
			new_mac = mac[i].substring(mac[i].length-5,mac[i].length);
			if(new_mac.toLowerCase()==new_val.toLowerCase()){
				$(".form-wifi-search-result").show();
				temp = '<li>'+mac[i]+'</li>';
				$(".form-wifi-search-result ul").append(temp);
			}
		}
	});
	$(document).on('tap','.form-wifi-search-result li',function(){
		var text = $(this).text(),html;
		$(this).parents(".form-wifi-search-result").hide();
		html = '<div class="form-wifi-input search-result"><span class="result-cont addWifiSapn">'+text+'</span><span class="htb-arrow-remove wifi-remove"></span></div>';
		$(this).parents(".form-module").append(html);
		$(".erroy-message").hide();
	});
	$(document).on('tap','.wifi-remove',function(){
		$(this).parents('.search-result').remove();
	});
	$(document).on('tap','.wifi-password-remove',function(){
		$(this).parents('.wifi-password').hide();
	});
	$(document).on('tap','.htb-arrow-lock',function(){//点击锁图标显示输入wifi密码框效果
		$(this).parent().siblings(".wifi-password").show();
	});
	$(document).on('tap','.htb-btn1',function(){
		$(this).parent().siblings(".wifi-password").show();
	});
	/*$(document).on('tap','#save',function(){
		submit();
	});*/
	$("body").on("click",".lock-style",function(){
		if($(this).hasClass('lock-on')){
			$(this).removeClass('lock-on');
			$(this).addClass('lock-off');
			$(this).data("value","false");
		}else{
			$(this).removeClass('lock-off');
			$(this).addClass('lock-on');
			$(this).data("value","true");
		}
	});
});

function initMacs(){
	var url='/controller/device/getMacList.do';
	$.ajax({
		type : "post",
		url : url,
		data: "merchantId="+merchantId,
		cache : false,
		dataType : "json",
		error : function(XMLHttpRequest) {// 请求失败时调用函数
			alert('错误', '提交失败', 'error');
		},
		success : function(data) {
			mac=data.data;
		}
	});
}

function submit(){
//	if(!validateInput()){
//		return;
//	}
	$.mobile.loading('show');//loading
	var macs='';
	var macTemp=$(".search-result .result-cont");
	for(var i=0;i<macTemp.length;i++){
		if(i==macTemp.length-1){
			macs+=$(macTemp[i]).text();
		}else{
			macs+=$(macTemp[i]).text()+",";
		}
	}
	var merchantName=$("#merchantName").val();
	
	var ssid1=$("#ssid1").val();
	var ssid2=$("#ssid2").val();
	var ssid3=$("#ssid3").val(); 
	var password1=$("#password1").val(); 
	var password2=$("#password2").val(); 
	var password3=$("#password3").val();
	var userName=$("#userName").val(); 
	var mobileNumber=$("#userMobile").val(); 
	var isPassWordCheck=$("#isPassWordCheck").data("value");
	//var merchantType=$("#merchantType").val();
	
	var url;
	var data;
//	if(true==isUpdate){
//		url='/controller/merchant/addOrUpdateWifiProductForOldBrand.do';
//		data='merchantName='+merchantName+'&macs='+macs+'&ssid1='+ssid1+'&ssid2='+ssid2+'&ssid3='+ssid3+
//	'&password1='+password1+'&password2='+password2+'&password3='+password3+'&userName='+userName+'&mobileNumber='+mobileNumber
	//+'&operatorId='+operatorId+'&operatorMobileNumber='+operatorMobileNumber+'&brandId='+brandId+'&merchantId='+merchantId;
//	}else{
		url='/controller/merchant/addOrUpdateWifiProductForNewBrand.do';
		data='merchantId='+merchantId+'&merchantName='+merchantName+'&macs='+macs+'&ssid1='+ssid1+'&ssid2='+ssid2+'&ssid3='+ssid3
		+'&password1='+password1+'&password2='+password2+'&password3='+password3+'&userName='+userName+'&mobileNumber='+mobileNumber
		+'&operatorId='+operatorId+'&operatorMobileNumber='+operatorMobileNumber+'&isPassWordCheck='+isPassWordCheck;//+'&merchantType='+merchantType;
//	}
	$.ajax({
		type : "post",
		url : url,
		data: data,
		cache : false,
		dataType : "json",
		error : function(XMLHttpRequest) {// 请求失败时调用函数
			alert('错误，稍后重试');
			$.mobile.loading('hide');
		},
		success : function(data) {
			if(data.flag!=null&&data.flag==1){
				$("#save_step3").click();//页面跳转
			}else{
				alert(data.message);
				$.mobile.loading('hide');
			}
		}
	});
}

$(function(){
	$(".erroy-message").hide();
	
	//非空验证
	$(document).on('blur','.noEmpty',function(){
		if($(this).val()==""||$(this).val()==null){
			$(".erroy-message").show();
		}else{
			$(".erroy-message").hide();
		}
		
	});
});

function validateInput(n){
		var flag = true;
		var inputs = $('#form-step'+n).find('.noEmpty');
		for( var i = 0; i < inputs.length; i++ )  { 
			 if(!($('#form-step'+n).find('.noEmpty').eq(i).val()||$('#form-step'+n).find('.noEmpty').eq(i).text())){
				  flag = false;
			 }
        } 
		
		if(flag){
			if(n == 2){
				 submit();
			}else if (n==1) {
				 if($('#form-step'+n).find('.addWifiSapn').length){

					//如果联系人姓名超过是一个字符提示
					 if($("#userName").val().length>10){
						 $("#form-step"+n+" .erroy-message").html("老板/店长姓名不能超过10个字符");
						 $(".erroy-message").show();
						 return false;
					 }
					 //验证手机号
					 if(!isPhoneNum($("#userMobile").val())){
						 $("#form-step1 .erroy-message").html("您输入的手机号不正确，请输入正确的手机号");
						 $(".erroy-message").show();
						 return false;
					 }
					 $("#form-step"+n+" .erroy-message").html("文本不能为空");
					 return true;
				 }else {
					 $(".erroy-message").show();
					 return false;
				 }
			}
		}else{
			$(".erroy-message").show();
			return false;
		}
}