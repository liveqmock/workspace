$(function(){
	$(document).on("tap","#modify_mobile_btn",function(){
		window.clearInterval(timeId);
		$(".sucess-btn-cont .m-btn3").show();
		$("#phoneNo").val('');
		$("#identifyNo").val('');
		$("#getIdentifyNO").html("获取验证码");
		$(".mesg-error").hide();
	});
	$(".relation-cont-select[name='family']").each(function(){
		var id = $(this).find("option:selected").attr("id");
		$(this).siblings("span").attr("relationid",id);
	});
	$(".relation-cont-rl[name='birthType']").each(function(){
		var id = $(this).find("option:selected").attr("id");
		$(this).siblings("span").attr("birthtype",id);
	});
	$(".relation-add").click(function(){
		var len = $(".relation-cont").length;
		if (len >=5) { // 大于5个时就不能再添加了
			// 提示框
			$(".relation-pop").click();
			return;
		}
		var temp = '<li class="relation-cont clear-wrap" id="">'+
                    '<div class="ui-select"><div id="select-13-button" class="ui-btn ui-icon-carat-d ui-btn-icon-right ui-corner-all ui-shadow"><span relationId="9">其他</span><select class="relation-cont-select" name="family">';
		for (var i=0; i < familyArray.length; i++) {
			var selected = i==0 ? "selected" : "";
			temp +="<option id='"+familyArray[i].get('id')+"' "+selected+">"+familyArray[i].get('name')+"</option>";
		}
        temp += '</select></div></div>'+
		        '<div class="ui-select"><div class="ui-btn ui-icon-carat-d ui-btn-icon-right ui-corner-all ui-shadow"><span class="relation-cont-rl" birthtype="2">阴历</span><select class="relation-cont-rl" name="birthType">'+
		    	'<option id="2" selected>阴历</option>'+
		    	'<option id="1">阳历</option>'+
		    	'</select></div></div>'+
                    '<div class="ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset"><input type="text" placeholder="选择日期" name="familyBirthday" readonly="" class="datePicker" ></div>'+
                    '<span class="mesg-icon-arrow"><i class="m-icon-remove" onclick="javascript:relation(this);"></i></span></li>';
        $(this).parent().siblings('ul').append(temp);
	});
	
	// 选择家人生日
	$("body").on('change','.relation-cont-select[name="family"]',function(){
		var val = $(this).val();
		$(this).siblings("span").text(val);
		var a = $(this).find("option:selected").attr("id");
		$(this).siblings("span").attr("relationId", a);
	});
	// 选择生日类型
	$(document).on('change','.relation-cont-rl[name="birthType"]',function(){
		var val = $(this).val();
		$(this).siblings("span").text(val);
		var a = $(this).find("option:selected").attr("id");
		$(this).siblings("span").attr("birthtype", a);
	});
	
	var currentTime = new Date();
	var yearRange ="1900:"+ currentTime.getFullYear();
	$("body").on('focus','.relation-cont .datePicker',function(){//时间控件
		$(this).datepicker({
			changeMonth:true,
			changeYear:true,
			yearRange:yearRange
		});
		$("#ui-datepicker-div").css({
			'left':'4px'
		});
	});
	// 获取验证码代码----------------------------------------------------------
	 var timeId = "";
    $('#getIdentifyNO').on('click',function(){
    	if(!vad_sub('#phoneNo')){
    		$('.mesg-error').show();
    		$('.mesg-error').find(".coupon-tips").html("手机号输入错误");
    	     return false;
    	}
    	 var count = 60;//每间隔六十秒获取验证码
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
		
    	var requestData = {};
    	requestData.brandId = $("#brandId").val();
    	requestData.weixinId = $("#weixinId").val();
    	requestData.title = $("#title").val();
    	requestData.phoneNo = $('#phoneNo').val().trim();
       //获取验证码之前先对用户基本信息验证
    	$.ajax({
    	 type: "post",
    	 url:  ctx+"/bindOldMember/registerIdentifyingCode.do?r="+Math.random(),
    	 cache: false,
    	 dataType : "text",
    	 data: requestData,
    	 success: function(json){
    		 $('.mesg-error').show();
    		 if(json.trim().length==3){//成功获取验证码
    			 $('.mesg-error').find(".coupon-tips").html('验证码已通过短信的形式发送到您的手机上');
    		 }else if(json.trim()=="-1"){
    			 $('.mesg-error').find(".coupon-tips").html('抱歉，微信端暂时无法注册会员，请到门店办理');
    		 }else{
    			 $('.mesg-error').find(".coupon-tips").html('请重新获取');
    		 }
    	 },
    	 error: function(XMLHttpRequest){
    		 if (processCommErr(XMLHttpRequest)) {
    			 $('.mesg-error').find(".coupon-tips").html('获取失败请重新获取');
    		 }
    	 }
    	});
    });
	
});

function relation(obj){
	$(obj).parents("li").remove();
}
function saveMemberInfo() {
	var phoneNo = $(".mesg-word-color").text();
	if ($("#name").val()==null || $("#name").val()=="") {
		$(".mesg-error").show();
		$(".mesg-error").find(".mesg-error-tips").html("请填写名称");
		return;
	} else if (phoneNo ==null || phoneNo=="") {
		$(".mesg-error").show();
		$(".mesg-error").find(".mesg-error-tips").html("请填写手机号");
		return;
	}
	var len = $(".relation-cont").length;
	if (len >5) { // 大于5个时就不能再添加了
		// 提示框
		$(".relation-pop").click();
		return;
	}
	// 家人生日
	var familyHtml="";
	var fArray = new Array();
	var dArray = new Array();
	$(".relation-cont-select[name='family']").each(function(i){
		var birthday = $(this).parents(".ui-select").siblings(".ui-input-text").children("input").val();
		var relationId = $(this).siblings("span").attr("relationId");
		var birthTypeVal = $(this).parents(".ui-select").siblings(".ui-select").find("span").text();
		fArray.push(relationId);
		if(birthday!=null && birthday!=""){
			dArray.push(birthday);
			familyHtml+="<p>"+$(this).siblings("span").text()+":"+birthTypeVal+"&nbsp;"+birthday+"</p>";
		}
	});
	var isComplete = false;
	if (fArray.length>0 || dArray.length > 0) {
		isComplete = fArray.length!=dArray.length; // 说明其中有未填项
	}
	if (isComplete) {
		$(".mesg-error").show();
		$(".mesg-error .mesg-error-tips").html("添加的家人生日必须填写完整");
		return;
	}
	var familyCount = $("#familyCount").val(); // 已添加的生日数量
	if (dArray.length == familyCount) { // 说明未添加新的家人生日,即不保存家人生日
		familyHtml="";
	}
	$(".mesg-error").hide();
	if (familyHtml!=null && familyHtml!="") { // 填写家人生日提示
		var fthml = "<h1>请确认您的家人生日信息</h1><p>保存后将无法自行修改生日信息呦~</p>"+familyHtml + '<a href="#" onclick="javascript:confirmSaveMemberInfo();" class="ui-btn m-btn5" data-rel="back">确定</a>'
		+'&nbsp;&nbsp;<a href="#" class="ui-btn m-btn5" data-rel="back">取消</a>';
		$("#popupDialog").find(".ui-content").html(fthml);
		$(".relation-pop").click();
	} else { // 未填写家人生日直接保存基本信息
		confirmSaveMemberInfo();
	}
}

// 修改会员信息
function confirmSaveMemberInfo() {
	var sex = $("input[name='gender']:checked").val();
	var phoneNo = $(".mesg-word-color").text();
	// 家人生日
	var fArray = new Array();
	var dArray = new Array();
	var btArray = new Array();
	$(".relation-cont-select[name='family']").each(function(){
		var bday = $(this).parents(".ui-select").siblings(".ui-input-text").children("input").val();
		var relationId = $(this).siblings("span").attr("relationId");
		var birthType = $(this).parents(".ui-select").siblings(".ui-select").find("span").attr("birthtype");
		fArray.push(relationId);
		dArray.push(bday);
		btArray.push(birthType);
	});
	var familyCount = $("#familyCount").val(); // 已添加的生日数量
	if (dArray.length == familyCount) { // 说明未添加新的家人生日,即不保存家人生日
		fArray.length = 0;
		dArray.length = 0;
	}
	var requestData = {};
	var brandId = $("#brandId").val();
	var weixinId = $("#weixinId").val();
	requestData.name = $("#name").val();
	requestData.gender = sex;
	requestData.birthType = $("input[name='birthday']:checked").val();
	var btemp = $("#year").siblings("span").text().replace("年",'').trim()+$("#month").siblings("span").text().replace("月",'').trim()
	+$("#day").siblings("span").text().replace("日",'').trim();
	// 修改完信息返回主页将生日带过去
	var birthday =  $("#year").siblings("span").text().replace("年",'-').trim()+$("#month").siblings("span").text().replace("月",'-').trim()
	+$("#day").siblings("span").text().replace("日",'').trim();
	requestData.birthDay = btemp;
	requestData.phoneNo = phoneNo;
	requestData.brandId = brandId;
	requestData.weixinId = weixinId;
	requestData.membershipId= $("#membershipId").val();
	requestData.family = fArray;
	requestData.familyBirthDay = dArray;
	requestData.birthTypes = btArray;
	$(".mesg-error").show();
	$(".mesg-error .mesg-error-tips").html("提交中请稍等...");
	$(".m-save").hide();
	$.ajax({
		url:ctx + "/member/birth/modifyUserFamily.do",
		type:"post",
		data:requestData,
		dataType:"json",
		success:function(data){
			var json = data.backStatus;
			if(json.trim()=="success"){//修改成功
				if(data.message!=null && data.message!=""){
					$(".mesg-error").find(".mesg-error-tips").html(data.message);
				} else {
					$(".mesg-error").find(".mesg-error-tips").html('修改成功');
				}
				window.location.href=ctx+"/weixin/phonePage/fensiCard.do?brandId="+brandId+"&weixinId="+weixinId+"&birthday="+birthday+"&random="+Math.random();
			}else if(json.trim()=="error"){
				$(".m-save").show();
				$(".mesg-error").find(".mesg-error-tips").html("修改失败");
			}else if(json.trim()=="modifyFailure"){//调用接口失败
				$(".m-save").show();
				$(".mesg-error").find(".mesg-error-tips").html("稍后重试");
			}else if (json.trim()=="parseBirError") {
				$(".m-save").show();
				$(".mesg-error").find(".mesg-error-tips").html("生日输入错误");
			}else{
				$(".m-save").show();
				$(".mesg-error").find(".mesg-error-tips").html("异常");//
			}
		},
		 error: function(XMLHttpRequest){
			 if (processCommErr(XMLHttpRequest)) {
				 $(".mesg-error").show();
				 $(".m-save").show();
				 $(".mesg-error").find(".mesg-error-tips").html('修改失败~~');
			 }
		 }
	});
}

// 验证手机号是否合法
function vad_sub(id) {
	var reg = /^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\d{8}$/;// 手机号码验证
	if (!reg.test($(id).val()) || $(id).val().trim() == '') {// 为空或错误时提示错误信
		return false;
	} else {
		return true;
	}
}
// 验证输入的验证码是否正确
function verifyMember () {
	var identifyNo = $("#identifyNo").val().trim();
	var phoneNo = $('#phoneNo').val().trim();
	if (!vad_sub("#phoneNo")) {
		$('.mesg-error').show();
		$('.mesg-error').find(".coupon-tips").html('输入手机号不合规则');
		 return;
	} else if (identifyNo==null || identifyNo=="") {
		$('.mesg-error').show();
		$('.mesg-error').find(".coupon-tips").html('请输入验证码');
		return;
	}
	var requestData = {};
	requestData.brandId = $("#brandId").val();
	requestData.weixinId = $("#weixinId").val();
	requestData.identifyCode = identifyNo;
	$(".sucess-btn-cont .m-btn3").hide();
	$.ajax({
		url:ctx+"/member/birth/verifyIdenfiyCode.do?random="+Math.random(),
		type:"post",
		data:requestData,
		dataType:"json",
		success:function(data) {
			if (data.flag==1) {
				$(".mesg-error").hide();
				$("#modify_back").click();
				//alert(0);
				//$("#modify_phone_div").hide();
				//$("#modify_member_div").show();
				$(".mesg-word-color").html(phoneNo);
			} else {
				$(".sucess-btn-cont .m-btn3").show();
				$('.mesg-error').show();
				$('.mesg-error').find(".coupon-tips").text(data.msg);
			}		
		}
	});
}
