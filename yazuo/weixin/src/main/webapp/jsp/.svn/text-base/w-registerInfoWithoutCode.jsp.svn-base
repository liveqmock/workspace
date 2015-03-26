<%@page import="org.apache.activemq.openwire.DataStreamMarshaller"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
      if(member.getIsMember()){
    	  if(member.getBirthday()!=null){
    		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    			String dateStr =sdf.format(member.getBirthday());//转换会员生日
    			dateStrArr = dateStr.split("-");//时间日期
    			if (!dateStrArr[0].startsWith("19") && !dateStrArr[0].startsWith("20")) {
    				  flag = false;
    			}
    	  }
		
      }
	Integer brandId = business.getBrandId();
	String title = business.getTitle();
%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<title><%=title%></title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/crm.css" />
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>js/jquery/jquery.form.js"></script>
<script language="javascript">
$(document).ready(function(){//页面初始化
// 1 3 5 7 8 10 12 31天  2 28天 4 6 9 11 30天
var year ="<%=year%>";
var months=['01','02','03','04','05','06','07','08','09','10','11','12'];

for(var i=parseInt(year);i>=1900;i--){
	$('#year').append("<option value='"+i+"'>"+i+"</option>");
};
for(var i=0;i<months.length;i++){
	$('#month').append("<option value='"+months[i]+"'>"+months[i]+"</option>");
}	

var reg = /^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\d{8}$/;// 手机号码验证
	
  if(<%=member.getIsMember()%>){//是会员时
	$('#userIndentity').hide();//验证码隐藏
	if(<%=member.getName()==null%>||<%=member.getName()!=null&&member.getName().trim().equals("")%>){
		 $('#userName').show();
	}else{
		 $('#userName').hide();//姓名隐藏
		 $('#uname').val('<%=member.getName()%>');//植入值
	}
	
	  if(<%=member.getGender()==null%>){//性别为空
   	   $('#userGender').show();
     }else{
   	    $('#userGender').hide();//性别隐藏
   	    var genderArray = $('input[type="radio"]');
   	    for(var i=0;i<genderArray.length;i++){//性别,
		 if(<%=member.getGender()%>==genderArray[i].value){
			 genderArray[i].checked=true;
		 }
			
		 }
   	
   }
	  if(<%=member.getPhoneNo()==null%>||<%=member.getPhoneNo()!=null%>&&!reg.test(<%=member.getPhoneNo()%>)){
	    	 $('#userPhone').show();
	    }else{
	    	 $('#userPhone').hide();//手机号隐藏
	    	 $('#phoneNo').val('<%=member.getPhoneNo()%>');
	    }
	  
	  if(<%=member.getBirthday()==null%>||<%=!flag%>){//生日不是以19和20开始的
		  $("#yinUserBirthday").show();
	    	$('#userBirthday').show();//生日信息显示
	    }else{
	    	 $("#yinUserBirthday").hide();
	    	$('#userBirthday').hide();
	    	$('#year').empty();
	    	$('#year').append("<option value='"+<%=dateStrArr[0]%>+"'>" +<%=dateStrArr[0]%>+"</option>");
	    	$('#month').empty();
	    	var month = '<%=dateStrArr[1]%>';
	    	$('#month').append("<option value='"+month+"'>" +month+"</option>");
	    	$('#day').empty();
	    	var day ='<%=dateStrArr[2]%>';
	    	$('#day').append("<option value='"+day+"'>" +day+"</option>");
	    }

	
	
	
}

});
   var days = ['01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31'];
    $('#month').live('change',function(){
	 
	
	 //2 月  28天
	 var month = $(this).attr("value");
	 if(month==2){
		$('#day').empty();
		$('#day').append("<option value='day'>日</option>");
		for(var i=0;i<days.length-3;i++){
		$('#day').append("<option value='"+days[i]+"'>"+days[i]+"</option>");
		}
	 }
	 //4 6 9 11 30天
	 if(parseInt(month)==4||parseInt(month)==6||parseInt(month)==9||parseInt(month)==11){
         $('#day').empty();
         $('#day').append("<option value='day'>日</option>");
		 for(var i=0;i<days.length-1;i++){
			 $('#day').append("<option value='"+days[i]+"'>"+days[i]+"</option>");
		 }
	 }else{
         $('#day').empty();
         $('#day').append("<option value='day'>日</option>");
		 for(var i=0;i<days.length;i++){
			 $('#day').append("<option value='"+days[i]+"'>"+days[i]+"</option>");
		 }
	 }
	 
 });
    
function identityUserInfo(){ //用户基本信息验证
		if($(".mem-name").val().trim()==''||/^[0-9_]+$/.test($(".mem-name").val().trim())){//姓名
			 $('.error-mesg').text('');
			 $(".error-mesg").text("名字填写有误");
		     return false;
		}else if(!(vad_sub('.mem-phone'))){//手机
			 $('.error-mesg').text('');
			 $(".error-mesg").text("手机号输入错误");
		     return false;
		}else{
			$(".error-mesg").text("");
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
	var year =$('#year').attr("value");
	var month = $('#month').attr("value"); 
	var day = $('#day').attr("value");
	var birthday =""+year+month+day;
	if("<%=information%>"=="完善会员资料"){
		if(isNaN(year)||isNaN(month)||isNaN(day)){
			 $('.error-mesg').text('');
			 $('.error-mesg').text('完善会员资料信息必需完整');
			 $('.mem-submit').show();
			 $('#getIdentifyNO').show();
			 
			 return false;
		}
	}
	if(<%=member.getIsMember()%>){//是会员时
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
		url:'<%=path%>/weixin/phonePage/modifyUserInfo.do',
		dataType:"text",
		data:"brandId="+"<%=business.getBrandId()%>"+"&weixinId="+"<%=weixinId%>"
		+"&phoneNo="+$('#phoneNo').val().trim()+"&gender="+gender+"&birthType="+birthType
		+"&name="+$('#uname').val().trim()+"&birthday="+birthday+"&random="+Math.random(),
		success:function(json){
			var url = "${toUrl}";
			if(json.trim()=="success"){//修改成功
				$('#cccc').text('');
				$('#cccc').text('修改成功');
				$('#registerInforamtion').hide();
				if (url!=null && url!="") {
						window.location.href="<%=path%>" + url;
				} else {
					window.location.href="<%=path%>/weixin/phonePage/fensiCard.do?"+"brandId="+"<%=brandId%>"+"&weixinId="+"<%=weixinId%>"+"&random="+Math.random();
				}
			}else if(json.trim()=="error"){
				$('.error-mesg').text("");
				$('.mem-submit').show();
				$('#getIdentifyNO').show();
				$('.error-mesg').text("修改失败");
			}else if(json.trim()=="beyond"){
				$('.error-mesg').text("");
				$('.mem-submit').show();
				$('#getIdentifyNO').show();
				$('.error-mesg').text("超过修改次数,不能在修改");
			}else if(json.trim()=="modifyFailure"){//调用接口失败
				$('.error-mesg').text("");
				$('.mem-submit').show();
				$('#getIdentifyNO').show();
				$('.error-mesg').text("稍后重试");
			}else{
				$('.error-mesg').text("");
				$('.mem-submit').show();
				$('#getIdentifyNO').show();
				$('.error-mesg').text("异常");//
			}
		},
		 error: function(XMLHttpRequest){
			 if (processCommErr(XMLHttpRequest)) {
				 $('.error-mesg').text('');
			     $('.error-mesg').text('修改失败~~');
			 }
		 }
		});
	}else{//新用户注册时
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
			url:'<%=path%>/weixin/phonePage/saveUser.do',
			dataType:"json",
			data:"brandId="+"<%=business.getBrandId()%>"+"&weixinId="+"<%=weixinId%>"
			+"&phoneNo="+$('#phoneNo').val().trim()+"&gender="+gender+"&birthType="+birthType
			+"&name="+$('#uname').val().trim()+"&birthday="+birthday+"&random="+Math.random()+"&source=${member.dataSource}",
			success:function(data){
				var json = ""+data.status;
				var url = "${toUrl}";
				if(json.trim()=="success"){
					$('#registerInforamtion').hide();
					if (url!=null && url!="") {
						window.location.href="<%=path%>" + url;
					} else if('${member.dataSource}'=='13'){ //数据来源13微信wifi会员，
						window.location.href = "<%=path%>/weixin/cardLottery/wifiBindSuccess.do?couponIdStr="+data.couponIdStr+"&luckCount="+data.lunckCount+
								"&brandId=<%=brandId%>&weixinId=<%=weixinId%>&flag="+data.oldMemberFlag+"&membershipId="+data.membershipId+"&phoneNo="+data.phoneNo+"&random="+Math.random();
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
	
	 
}
window.setInterval(function(){
	if($('.error-mesg').text()!=''){
		window.setTimeout(function(){$('.error-mesg').text('');},3000);
	}
},3000);

</script>
</head>
<body>
	<div class="w">
		<section class="mem-main" id="registerInforamtion">
		<h1><%=information%></h1>
		<form method="post" id="userInfo">
			<p id="userName">
				<label>姓 名：</label><span><input type="text"
					class="text mem-name" name="name" id="uname" /></span>
			</p>
			<p id="userGender">
				<label>性 别：</label><span class="padR20"><input type="radio"
					class="checkbox" name="gender" id="male" value="1" />男</span><span><input
					type="radio" class="checkbox" name="gender" id="female" value="2" checked="checked"/>女</span>
			</p>
			<p id="yinUserBirthday">
				<label>生 日：</label> 
				<span class="padR20"> <input type="radio" class="checkbox" name="birthButton" id="yangLi" value="1" checked="checked"/>阳历</span>
				<span> <input type="radio" class="checkbox" name="birthButton" id="yinLi" value="2" />阴历</span>
			</p>
			<p id="userBirthday">
				<select name="year" id="year" class="select">
					<option value="year">年</option>
				</select> <select name="month" id="month" class="select">
					<option value="month">月</option>
				</select> 
				<select name="day" id="day" class="select">
					<option value="day">日</option>
				</select>
			</p>
			<p id="userPhone">
				<label>手 机：</label><span><input type="tel"
					class="text mem-phone" name="phoneNo" id="phoneNo" value="${not empty param.phoneNo ? param.phoneNo :'' }"/></span>
			</p>
		</form>
		<div class="clear_wrap" id="userIndentity">
			<p class="yzm left">
				<label>验证码：</label><span><input type="text"
					class="text mem-vadm" placeholder="请输入验证码" id="identifyNo" /></span>
					
			</p>
			<a href="javascript:saveMember();" class="yz_btn right" id="getIdentifyNO">获取验证码</a>
		</div>
		<div class="sub-div" id="userSubmit">
			<span class="error-mesg"></span> <a href="javascript:saveMember();"
				class="mem-sub mem-submit">提&nbsp;&nbsp;&nbsp;交</a>
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
