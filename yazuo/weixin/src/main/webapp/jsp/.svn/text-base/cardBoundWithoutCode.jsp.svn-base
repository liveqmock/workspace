<%@page import="org.apache.activemq.openwire.DataStreamMarshaller"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	BusinessVO business = ((BusinessVO) request
			.getAttribute("business"));
	MemberVO member = (MemberVO) request.getAttribute("member");
	String weixinId = (String) request.getAttribute("weixinId");
	String information = "";
	if (member.getIsMember()) {
		information = "完善会员资料";
	} else {
		information = "加入粉丝";
	}
	Date date = new Date();
	int year = date.getYear() + 1900;
	boolean flag = true;
	String[] dateStrArr = new String[3];
	if (member.getIsMember()) {
		if (member.getBirthday() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = sdf.format(member.getBirthday());//转换会员生日
			dateStrArr = dateStr.split("-");//时间日期
			if (!dateStrArr[0].startsWith("19")
					&& !dateStrArr[0].startsWith("20")) {
				flag = false;
			}
		}

	}
	Integer brandId = business.getBrandId();
	String title = business.getTitle();
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<title></title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/crm.css" />
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>js/jquery/jquery.form.js"></script>
<script language="javascript">
	$(document).ready(
			function() {//页面初始化
				// 1 3 5 7 8 10 12 31天  2 28天 4 6 9 11 30天
				var date = new Date;
				var year = date.getFullYear();
				var months = [ '01', '02', '03', '04', '05', '06', '07', '08',
						'09', '10', '11', '12' ];
				for ( var i = parseInt(year); i >= 1900; i--) {
					$('#year').append(
							"<option value='"+i+"'>" + i + "</option>");
				}
				;
				for ( var i = 0; i < months.length; i++) {
					$('#month').append(
							"<option value='"+months[i]+"'>" + months[i]
									+ "</option>");
				}
				var reg =/^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\d{8}$/;// 手机号码验证
				init();
			});
	var days = [ '01', '02', '03', '04', '05', '06', '07', '08', '09', '10',
			'11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21',
			'22', '23', '24', '25', '26', '27', '28', '29', '30', '31' ];
	$('#month').live(
			'change',
			function() {
				//2 月  28天
				var month = $(this).attr("value");
				if (month == 2) {
					$('#day').empty();
					$('#day').append("<option value='day'>日</option>");
					for ( var i = 0; i < days.length - 3; i++) {
						$('#day').append(
								"<option value='"+days[i]+"'>" + days[i]
										+ "</option>");
					}
				}
				//4 6 9 11 30天
				if (parseInt(month) == 4 || parseInt(month) == 6
						|| parseInt(month) == 9 || parseInt(month) == 11) {
					$('#day').empty();
					$('#day').append("<option value='day'>日</option>");
					for ( var i = 0; i < days.length - 1; i++) {
						$('#day').append(
								"<option value='"+days[i]+"'>" + days[i]
										+ "</option>");
					}
				} else {
					$('#day').empty();
					$('#day').append("<option value='day'>日</option>");
					for ( var i = 0; i < days.length; i++) {
						$('#day').append(
								"<option value='"+days[i]+"'>" + days[i]
										+ "</option>");
					}
				}

			});

	//会员卡信息 提交前的表单验证
	function identityUserInfo() { 
		if ($(".mem-name").val().trim() == ''
				|| /^[0-9_]+$/.test($(".mem-name").val().trim())) {//姓名
			$('.error-mesg').text('');
			$(".error-mesg").text("名字填写有误");
			return false;
		} else if (!verifyCardInfo()) {
			return false;
		} else if (!(vad_sub('.mem-phone'))) {//手机
			$('.error-mesg').text('');
			$(".error-mesg").text("手机号输入错误");
			return false;
		} else {
			$(".error-mesg").text("");
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
		if ($("#cardNum").val().trim() == '' || (!/^\d{16}$/.test($("#cardNum").val().trim()) && !/^\d{19}$/.test($("#cardNum").val().trim()))) {//会员卡卡号
			$('.error-mesg').text('');
			$(".error-mesg").text("卡号填写有误");
			return false;
		}else if ($("#secCode").val().trim() == '' || !/^\d{3}$/.test($("#secCode").val().trim())) {//安全码
			$('.error-mesg').text('');
			$(".error-mesg").text("安全码填写有误");
			return false;
		} else {
			if (isNeedPassword=="true") { // 需要输入密码，验证密码
				if ($("#userPwd").val().trim() == '' || !/^\d{6}$/.test($("#userPwd").val().trim())) {//密码
					$('.error-mesg').text('');
					$(".error-mesg").text("密码必须是6位数字");
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
						$('.error-mesg').text('');
						$(".error-mesg").text("密码不能是卡后6位");
						return false;
					} else if (equalCount == pwd.length-1) { // 密码不能输入6个相同
						$('.error-mesg').text('');
						$(".error-mesg").text("密码不能是6个相同的数字");
						return false;
					}else if ($("#userPwd2").val().trim() == '' || !/^\d{6}$/.test($("#userPwd2").val().trim())) {//确认密码
						$('.error-mesg').text('');
						$(".error-mesg").text("确认密码必须是6位数字");
						return false;
					}else if ($.trim($("#userPwd").val()) !== $.trim($("#userPwd2").val())) { //密码
						$('.error-mesg').text('');
						$(".error-mesg").text("两次密码输入不一致");
						return false;
					} else {
						$(".error-mesg").text("");
						return true;
					}
				}
			} else {
				if (isRequireSuccess=="false") {
					$('.error-mesg').text('');
					$(".error-mesg").text(errorMessageTip);
					return false;
				}
			}
			return true;
		}
	}
	
	//提交用户信息
	function saveMember(){
		
		var year =$('#year').attr("value");
		var month = $('#month').attr("value"); 
		var day = $('#day').attr("value");
		var birthday =""+year+month+day;

		if(isNaN(year)||isNaN(month)||isNaN(day)){
			birthday="";
		}
		if(!identityUserInfo()){
			$('.mem-submit').show();
			$('#getIdentifyNO').show();
			return false;
		};
		$('.mem-submit').hide();//点击提交后隐藏起来，防止重复提交
		$('#getIdentifyNO').hide();
		$('.error-mesg').text('');
		$('.error-mesg').text('提交中请稍后.....');
		
		$.ajax({
			type:"post",
			url:'<%=path%>/weixin/phonePage/cardBound.do',
			dataType:"json",
			data:"&name="+$('#uname').val().trim()+"&gender="+$(':radio[checked="checked"]').val()+"&birthday="+birthday+"&cardNum="+$('#cardNum').val().trim()
			+"&secCode="+$('#secCode').val().trim()+"&userPwd="+$('#userPwd').val().trim()+"&phoneNo="+$('#phoneNo').val().trim()+"&brandId="+"<%=brandId%>"+"&weixinId="+"<%=weixinId%>"+"&random="+Math.random(),
			success:function(data){
				var json = data.cardBoundStatus;
				if(json.trim()=="success"){
					$('#registerInforamtion').hide();
					//$('#submitInformation').show();
					//document.getElementById('submitSuccess').href="<%=path%>/weixin/phonePage/fensiCard.do?"+"brandId="+"<%=brandId%>"+"&weixinId="+"<%=weixinId%>"+"&random="+Math.random();
					if (data.luckSuccess) { // 绑定卡成功送抽奖机会或赠劵成功
						window.location.href = "<%=path%>/caffe/cardLottery/boundSuccess.do?couponIdStr="+data.couponIdStr+"&luckCount="+data.lunckCount+"&brandId=<%=brandId%>&weixinId=<%=weixinId%>&random="+Math.random();					
					}else {
						window.location.href="<%=path%>/weixin/phonePage/fensiCard.do?"+"brandId=<%=brandId%>&weixinId=<%=weixinId%>&random="+Math.random();
					}
				}else if(json.trim()=="error"){//商家不允许电子注册会员
					$('.error-mesg').text('');
					$('.mem-submit').show();
					$('#getIdentifyNO').show();
					$('.error-mesg').text('请到实体店办理');
				}else if(json.trim()=="3"){
					$('.error-mesg').text('');
					$('.mem-submit').show();
					$('#getIdentifyNO').show();
					$('.error-mesg').text('信息格式错误');
				}else if(json.trim()=="4"){
					$('.error-mesg').text('');
					$('.mem-submit').show();
					$('#getIdentifyNO').show();
					$('.error-mesg').text('您已经是粉丝会员');
				}else if(json.trim()=="registerError"){//调用接口失败
					$('.error-mesg').text('');
					$('.mem-submit').show();
					$('#getIdentifyNO').show();
					$('.error-mesg').text('注册失败,稍候重试');
				}else if(json.trim()=="saveFailure"){//更新用户到crm库里时失败
					$('.error-mesg').text('');
					$('.mem-submit').show();
					$('#getIdentifyNO').show();
					$('.error-mesg').text('系统有误,稍候....');
				}else{
					$('.error-mesg').text('');
					$('.mem-submit').show();
					$('#getIdentifyNO').show();
					$('.error-mesg').text(json);
				}
			},
			error:function(XMLHttpRequest){
				$('.error-mesg').text('');
				$('.mem-submit').show();
				$('#getIdentifyNO').show();
				$('.error-mesg').text('提交失败~~~');
			}
		});
		
	}
	
	window.setInterval(function() {
		if ($('.error-mesg').text() != '') {
			window.setTimeout(function() {
				$('.error-mesg').text('');
			}, 3000);
		}
	}, 3000);
	
	//add by sundongfeng 2014-07-16
	function init(){
		var name = '${member.name}';
		var gender = '${member.gender}';
		var year = '${year}';
		var month='${month}';
		var day='${day}';
		$("#uname").val(name);
		if(year!=''){
			$("select[name='year'] option[value='"+year+"']").attr("selected","selected");
			$("select[name='month'] option[value='"+month+"']").attr("selected","selected");
			$('#day').empty();
			$('#day').append("<option value='"+day+"'>"+day+"</option>");
			$("select[name='day'] option[value='"+day+"']").attr("selected","selected");
		}
		if(gender=='2'){
			$("#female").attr("checked",true);
		}else{
			$("#male").attr("checked",true);
		}
	}
	// 根据卡类型是否要输入密码
	function isNeedWritePassword() {
		var cardNo = $("#cardNum").val().trim();
		if (cardNo == '' || (!/^\d{16}$/.test(cardNo) && !/^\d{19}$/.test(cardNo))) {//会员卡卡号
			$('.error-mesg').text('');
			$(".error-mesg").text("卡号填写有误");
			return false;
		}
		$.ajax({
			url:"<%=path%>/weixin/phonePage/judgeNeedPwd.do?brandId=<%=brandId%>&cardNo="+cardNo,
			type:"post",
			dataType:"json",
			success:function(data){
				$("#isNeedPwd").val(data.flag);
				$("#isRequireSuccess").val(data.isRequireSuccess);
				$("#errorMessageTip").val(data.message);
				if (data.flag) { // 需要输入密码
					$("#div_pass").show();
				} else {
					$("#div_pass").hide();
				}
			}
		});
	}
</script>
</head>
<body>
	<div class="w">
		<input type="hidden" name="isNeedPwd" id="isNeedPwd"/>
		<input type="hidden" name="isRequireSuccess" id="isRequireSuccess"/>
		<input type="hidden" name="errorMessageTip" id="errorMessageTip"/>
		<div class="header">会员卡绑定</div>
		<section class="mem-main" id="registerInforamtion">

		<form method="post" id="userInfo">
			<h1 class="formItemTitle">输入基本信息：</h1>
			<p>
				<label>姓 名：</label><span><input type="text" class="text mem-name" name="name" id="uname" /></span>
			</p>
			<p>
				<label>性 别：</label><span class="padR20"><input type="radio" class="checkbox" name="gender" id="male" value="1" />男</span><span><input type="radio"
					class="checkbox" name="gender" id="female" value="2" checked="checked" />女</span>
			</p>
			<p>
				<label>生 日：</label> <select name="year" id="year" class="select">
					<option value="year">年</option>
				</select> <select name="month" id="month" class="select">
					<option value="month">月</option>

				</select> <select name="day" id="day" class="select">
					<option value="day">日</option>
				</select>
			</p>

			<h1 class="formItemTitle">填写会员卡信息：</h1>
			<p>
				<label>&nbsp;卡&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</label><span><input type="tel" placeholder="请输入您的16位会员卡号" class="text" name="cardNum" id="cardNum" onchange="isNeedWritePassword()" /></span>
			</p>
			<p>
				<label>&nbsp;安&nbsp;全&nbsp;码：</label><span><input type="text" placeholder="16位卡号后面的三位数字" class="text" name="secCode" id="secCode" /></span>
			</p>
			<div id = "div_pass" style="display: none;">
				<p>
					<label>&nbsp;密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</label><span><input type="password" placeholder="为会员卡设置6位数密码" class="text" name="userPwd" id="userPwd" /></span>
				</p>
				<p>
					<label>确认密码：</label><span><input type="password" placeholder="请再次输入6位数密码" class="text" name="userPwd2" id="userPwd2" /></span>
				</p>
			</div>
			<h1 class="formItemTitle">验证信息：</h1>
			<p id="userPhone">
				<label>手 机：</label><span><input type="tel" placeholder="请输入您的手机号" class="text mem-phone" name="phoneNo" id="phoneNo" /></span>
			</p>
		</form>
		<div class="clear_wrap" id="userIndentity">
			<p class="yzm left">
				<label>验证码：</label><span><input type="text" class="text mem-vadm" placeholder="请输入验证码" id="identifyNo" /></span>
			</p>
			<a href="javascript:saveMember();" class="yz_btn right" id="getIdentifyNO">获取验证码</a>
		</div>
		<div class="sub-div" id="userSubmit">
			<span class="error-mesg"></span> <a href="javascript:saveMember();" class="mem-sub mem-submit">提&nbsp;&nbsp;&nbsp;交</a>
		</div>
		</section>
		<!--提交成功-->
		<section class="mem-main" style="display:none" id="submitInformation">
		<div class="mem-succ">
			<img src="<%=path%>/images/suc1.png" width="40" /><span id="cccc">您已成功成为粉丝</span>
		</div>
		<div class="sub-div">
			<a href="#" class="mem-sub" id="submitSuccess">确&nbsp;&nbsp;&nbsp;定</a>
		</div>
		</section>
	</div>
</body>
</html>

