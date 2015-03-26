<%@page import="org.apache.activemq.openwire.DataStreamMarshaller"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%@ page isELIgnored="false" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!doctype html>
<html class="no-js" lang="">
<head>
<meta charset="utf-8">
<meta name="description" content="">
<meta name="HandheldFriendly" content="True">
<meta name="MobileOptimized" content="320">
<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no"> 
<meta http-equiv="cleartype" content="on">
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/a.min.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/jquery.mobile.icons.min.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/jquery.mobile.structure-1.4.5.min.css" />
<link rel="stylesheet" href="<%=path %>/css/new_member/base.css" />
<link rel="stylesheet" href="<%=path %>/css/new_member/member.css" />
<script type="text/javascript">
	var ctx = "<%=path%>";
</script>
<script type="text/javascript" src="<%=path%>/js/jquery/jquery.js"></script>
<script src="<%=path %>/js/jquery.mobile/jquery.mobile-1.4.5.min.js"></script>

<title>修改密码</title>

<script language="javascript">
	
    // 修改密码
    function updatePwd() {
    	var newPwd = $("#newPwd").val();
    	var pwd = $("#pwd").val();
    	var newConfirmPwd = $("#newConfirmPwd").val();
    	if(pwd==null || pwd=="") {
    		$(".mesg-error").show();
    		$(".mesg-error .mesg-error-tips").html("请输入原始密码");
    		return;
    	} else if (newPwd ==null || newPwd =="") {
    		$(".mesg-error").show();
    		$(".mesg-error .mesg-error-tips").html("请输入新密码");
    		return;
    	} else if (newConfirmPwd ==null || newConfirmPwd =="") {
    		$(".mesg-error").show();
    		$(".mesg-error .mesg-error-tips").html("请输入确认密码");
    		return;
    	} else {
    		if(newPwd != newConfirmPwd){
    			$(".mesg-error").show();
    			$(".mesg-error .mesg-error-tips").html("输入的新密码与确认密码不一致");
        		return;
    		}
    	}
    	var requestData ={};
    	requestData.brandId="${brandId}";
    	requestData.cardNo="${cardNo}";
    	requestData.pwd= pwd;
    	requestData.newPwd=newPwd;
    	$.ajax({
    		url:"<%=basePath%>/weixin/member/updatePwd.do",
    		type:"post",
    		data:requestData,
    		dataType:"json",
    		success:function(data){
    			$(".mesg-error").show();
    			$(".mesg-error .mesg-error-tips").html(data.message);
    			if (data.flag) { // 修改成功之后跳转
    				$(".sucess-btn-cont .m-btn3").hide();
    				window.setTimeout(function(){
	    				window.location.href = "<%=basePath%>/weixin/phonePage/fensiCard.do?brandId=${brandId}&weixinId=${weixinId}";
    				},2000);
    			}
    		}
    	});
    }
</script>
</head>
<body>
	<div data-role="page" data-theme="a">
	<div data-role="header" data-position="inline">
		<a target="_self" href="<%=path %>/weixin/phonePage/fensiCard.do?brandId=${brandId }&weixinId=${weixinId}" class="ui-btn ui-shadow ui-corner-all  m-icon-back ui-btn-icon-left"></a>
        <h1>会员中心</h1>        
		<a target="_self" href="<%=path %>/weixin/phonePage/fensiCard.do?brandId=${brandId }&weixinId=${weixinId}" class="ui-btn ui-shadow ui-corner-all  m-icon-home ui-btn-icon-right"></a>
    </div>
    <div data-role="content" data-theme="a" class="m-wrapper">
    	<section class="memeber-tips-wrap">
        	<span><i class="m-icon-tips"></i>您正在修改您的会员卡密码</span>
            <span class="m-tips-arrow"></span>
        </section>
        <section class="weixin-input">
            <div class="weixin-input-cont clear-wrap"><label class="text-r">原始密码：</label><input type="password" name="pwd" id="pwd" placeholder="请输入您现在的密码" /></div>
            <div class="weixin-input-cont clear-wrap"><label class="text-r">新密码：</label><input type="password" name="newPwd" id="newPwd" placeholder="请输入您的新密码" /></div>
            <div class="weixin-input-cont clear-wrap"><label class="text-r">密码确认：</label><input type="password" name="newConfirmPwd" id="newConfirmPwd" placeholder="请再次输入您的新密码" /></div>
            <div class="mesg-error"><div class="mesg-error-tips">密码错误，请重新输入</div></div>
        </section>
        <section class="sucess-btn-cont">
        	<button class="m-btn3" onclick="javascript:updatePwd();">提交</button>
        </section>
    </div>
</div>
</body>
</html>
