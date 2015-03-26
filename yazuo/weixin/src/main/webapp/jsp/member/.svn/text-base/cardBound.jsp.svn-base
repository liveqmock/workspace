<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.util.StringUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="org.apache.activemq.openwire.DataStreamMarshaller"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	MemberVO member = (MemberVO) request.getAttribute("member");
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
<title>${business.title }</title>
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/a.min.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/jquery.mobile.icons.min.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/jquery.mobile.structure-1.4.5.min.css" />
<link rel="stylesheet" href="<%=path %>/css/new_member/base.css" />
<link rel="stylesheet" href="<%=path %>/css/new_member/member.css" />
<script type="text/javascript">
	var ctx = "<%=path%>";
	var cyear = "${year}";
	var cmonth = "${month}";
	var cday = "${day}";
	var isAllowWeixinMember='${isAllowWeixinMember}';//是否输入验证码
</script>
<script type="text/javascript" src="<%=path%>/js/jquery/jquery.js"></script>
<script src="<%=path %>/js/jquery.mobile/jquery.mobile-1.4.5.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/new_member/choose_date.js"></script>
<script type="text/javascript" src="<%=path%>/js/new_member/card_bound.js"></script>
</head>
<body>
<input type="hidden" name="isNeedPwd" id="isNeedPwd"/>
<input type="hidden" name="isRequireSuccess" id="isRequireSuccess"/>
<input type="hidden" name="errorMessageTip" id="errorMessageTip"/>
<input type="hidden" name="brandId" id="brandId" value="${business.brandId }"/>
<input type="hidden" name="weixinId" id="weixinId" value="${weixinId }"/>
<input type="hidden" name="title" id="title" value="${business.title }"/>
<input type="hidden" name="memberMobile" id="memberMobile" value="${member.phoneNo}"/>

<div data-role="page" data-theme="a">
	<div data-role="header" data-position="inline">
		<a target="_self" href="javascript:window.history.go(-1)" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left m-icon-back"></a>
        <h1>会员中心</h1>        
		<a target="_self" href="<%=path %>/weixin/phonePage/fensiCard.do?brandId=${business.brandId }&weixinId=${weixinId}" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-right  m-icon-home"></a>
    </div>
    <div data-role="content" data-theme="a" class="m-wrapper">
    	<section class="member-fans-wrap">
        	<h1>实体卡绑定</h1>
            <section class="weixin-input">
                <div class="weixin-input-cont clear-wrap"><label class="text-r">姓&nbsp;&nbsp;&nbsp;名：</label>
                	<input type="text" placeholder="点击这里输入" name="name" id="uname" value="${not empty member.name ? member.name:''}"/>
                </div>
                 <div class="weixin-input-cont clear-wrap">
                    <label>性   别：</label>
					<fieldset data-role="controlgroup">
                    	<input type="radio" name="gender" id="male" value="1" <c:if test="${empty member || empty member.gender || member.gender =='1' }">checked</c:if>/><label for="male" class="sex-style">男</label>
						<input type="radio" name="gender" id="female" value="2" <c:if test="${member.gender =='2' }">checked</c:if>/><label for="female" class="sex-style">女</label>
                    </fieldset>
                </div>
                <div class="weixin-input-cont clear-wrap">    
                	<label>生   日：</label>
					<fieldset data-role="controlgroup"> 
                    	<input type="radio" id="yangLi" name="birthType" value="1" <c:if test="${empty member || empty member.birthType || member.birthType==1 }">checked</c:if>/><label for="yangLi" class="sex-style">阳历</label>
						<input type="radio" id="yinLi" name="birthType" value="2" <c:if test="${member.birthType==2 }">checked</c:if>/><label for="yinLi" class="sex-style">阴历</label>
                    </fieldset>
                </div>
                <div class="weixin-input-cont clear-wrap"> 
                	<select id="year" name="year" class="selset-year">
                		<option>年</option>
                    </select>
                	<select id="month" name="month" class="selset-month">
                		<option>月</option>
                    </select>
                	<select id="day" name="day" class="selset-day">
                		<option>日</option>
                    </select>
                </div>
        	</section>
            <h1>填写会员卡信息</h1>
            <section class="weixin-input">
                <div class="weixin-input-cont clear-wrap"><label class="text-r">卡&nbsp;&nbsp;&nbsp;号：</label>
                	<input type="text"  type="tel" placeholder="请输入您的16位会员卡号" class="text" name="cardNum" id="cardNum" onchange="isNeedWritePassword()"  />
                </div>
                <div class="weixin-input-cont clear-wrap"><label class="text-r">安全码：</label>
                	<input type="text" placeholder="安全码是16位卡号后面的三位数字" name="secCode" id="secCode"/>
                </div>
                <div class="weixin-input-cont clear-wrap password"><label class="text-r">密&nbsp;&nbsp;&nbsp;码：</label>
                	<input type="password" placeholder="为会员卡设置6位数字密码" name="userPwd" id="userPwd" />
                </div>
                <div class="weixin-input-cont clear-wrap password"><label class="text-r">确认密码：</label>
                	<input type="password" placeholder="请再次输入6位数字密码"  name="userPwd2" id="userPwd2"/>
                </div>
        	</section>
        	<h1>验证消息</h1>
        	 <section class="weixin-input">
        	 	<div class="weixin-input-cont clear-wrap"><label>手机号：</label><input type="text" name="phoneNo" id="phoneNo" value="${not empty param.phoneNo ? param.phoneNo :'' }" placeholder="请输入您的新手机号" /></div>
                <c:if test="${isAllowWeixinMember }">
                <div class="weixin-input-cont yzm-input-cont clear-wrap">
                    <label>验证码：</label><input type="text" class="mem-vadm" placeholder="请输入验证码" id="identifyNo"/>
                    <!--<span class="yzm-btn">获取验证码</span>-->
                    <span class="yzm-btn reset-yzm" id="getIdentifyNO">获取验证码</span>
                </div>
                </c:if>
	        </section>
    		<div class="mesg-error"><div class="mesg-error-tips"></div></div>
        <section class="sucess-btn-cont">
        	<button class="m-btn3" onclick="javascript:saveMember();">提交</button>
        </section>
    </div>
</div>
</body>
</html>

