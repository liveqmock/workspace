<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.util.StringUtil"%>
<%@page import="org.apache.activemq.openwire.DataStreamMarshaller"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	var year ="<%=year%>";
	var cyear ="<%=StringUtil.isNullOrEmpty(dateStrArr[0]) ? "" : dateStrArr[0]%>";
	var cmonth ="<%=StringUtil.isNullOrEmpty(dateStrArr[1]) ? "" : dateStrArr[1]%>";
	var cday ="<%=StringUtil.isNullOrEmpty(dateStrArr[2]) ? "" : dateStrArr[2]%>";
	var isAllowWeixinMember='${isAllowWeixinMember}';//是否输入验证码
</script>
<script type="text/javascript" src="<%=path%>/js/jquery/jquery.js"></script>
<script src="<%=path %>/js/jquery.mobile/jquery.mobile-1.4.5.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/new_member/choose_date.js"></script>
<script type="text/javascript" src="<%=path%>/js/new_member/register_member.js"></script>
</head>
<body>
<input type="hidden" name="brandId" id="brandId" value="${business.brandId }"/>
<input type="hidden" name="weixinId" id="weixinId" value="${weixinId }"/>
<input type="hidden" name="dataSource" id="dataSource" value="${member.dataSource}"/>
<input type="hidden" name="title" id="title" value="${business.title}"/>
<input type="hidden" name="isMember" id="isMember" value="${member.isMember }"/>
<input type="hidden" name="toUrl" id="toUrl" value="${toUrl}"/>

<div data-role="page" data-theme="a">
	<div data-role="header" data-position="inline">
		<a target="_self" href="javascript:window.history.go(-1)" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left m-icon-back"></a>
        <h1>会员中心</h1>        
		<%-- <a target="_self" href="<%=path %>/weixin/phonePage/fensiCard.do?brandId=${business.brandId }&weixinId=${weixinId}" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-right m-icon-home"></a> --%>
    </div>
    <div data-role="content" data-theme="a" class="m-wrapper">
    	<section class="member-fans-wrap">
        	<h1>加入粉丝</h1>
            <section class="weixin-input">
                <div class="weixin-input-cont clear-wrap"><label>姓   名：</label><input type="text" name="name" id="uname" placeholder="点击这里输入" /></div>
                <div class="weixin-input-cont clear-wrap">
                    <label>性   别：</label>
					<fieldset data-role="controlgroup">
                    	<input type="radio" name="gender" id="male" value="1" /><label for="male" class="sex-style">男</label>
                    	<input type="radio" name="gender" id="female" value="2" checked="checked"/><label for="female" class="sex-style">女</label>
                    </fieldset>
                </div>
                <div class="weixin-input-cont clear-wrap">
                    <label>生   日：</label>
					<fieldset data-role="controlgroup">
                    	<input type="radio" name="birthButton" id="yangLi" value="1" checked="checked"/><label for="yangLi" class="sex-style">阳历</label>
						<input type="radio" name="birthButton" id="yinLi" value="2" /><label for="yinLi" class="sex-style">阴历</label>
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
                <div class="weixin-input-cont clear-wrap"><label>手机号：</label><input type="text" name="phoneNo" id="phoneNo" value="${not empty param.phoneNo ? param.phoneNo :'' }" placeholder="请输入您的手机号" /></div>
              	<c:if test="${isAllowWeixinMember }">
	                <div class="weixin-input-cont yzm-input-cont clear-wrap">
	                    <label>验证码：</label><input type="text" class="mem-vadm" placeholder="请输入验证码" id="identifyNo"/>
	                    <!--<span class="yzm-btn">获取验证码</span>-->
	                    <span class="yzm-btn reset-yzm" id="getIdentifyNO">获取验证码</span>
	                </div>        
                </c:if>
        	</section>
    			<div class="mesg-error"><div class="mesg-error-tips"></div></div>
        </section>
        <section class="sucess-btn-cont">
        	<button class="m-btn3" onclick="javascript:saveMember();">提交</button>
        </section>
    </div>
</div>
</body>
</html>
