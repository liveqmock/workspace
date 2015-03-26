<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.util.JudgeMenuShow"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.*"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://epms.tiros.com.cn/restricttag" prefix="restrict"%>
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
<link type="text/css" rel="stylesheet" href="<%=path %>/js/1.8.5/aristo/phone.jquery.ui.all.css" />
<link rel="stylesheet" href="<%=path %>/css/new_member/base.css" />
<link rel="stylesheet" href="<%=path %>/css/new_member/member.css" />
<script src="${pageContext.request.contextPath}/js/MapClass.js"></script>
<script src="<%=path %>/js/1.8.5/jquery.js"></script>
<script src="<%=path %>/js/jquery.mobile/jquery.mobile-1.4.5.min.js"></script>
<script src="<%=path%>/js/1.8.5/jquery-ui-1.8.5.min.js"></script>
<script src="<%=path%>/js/new_member/choose_date.js"></script>
<script type="text/javascript">
	var ctx = "<%=path%>";
	var cyear = '${year}';
	var cmonth = '${month}';
	var cday ='${day}';
	
	var familyArray = new Array();//JSON.stringify("${relationList }");
	
	<c:forEach var="k" items="${relationList }">
	   var obj=new MapClass();
		obj.put('id','${k.id}');
		obj.put('name','${k.name}');
		familyArray.push(obj);
	</c:forEach>
</script>
<script src="<%=path%>/js/new_member/member-information-edit.js"></script>

<title>个人信息编辑</title>
</head>
<body>
	<input type="hidden" name="brandId" id="brandId" value="${member.brandId }"/>
	<input type="hidden" name="weixinId" id="weixinId" value="${member.weixinId }"/>
	<input type="hidden" name="title" id="title" value="${title }"/>
	<input type="hidden" name="membershipId" id="membershipId" value="${member.membershipId }">
	
<div data-role="page" data-theme="a" id="modify_member_div">
	<div data-role="header" data-position="inline">
		<a target="_self" href="<%=path %>/weixin/phonePage/fensiCard.do?brandId=${member.brandId }&weixinId=${member.weixinId}" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left m-icon-back"></a>
        <h1>个人资料编辑</h1>        
		<a target="_self" href="javascript:saveMemberInfo();" class="ui-btn ui-shadow ui-corner-all m-save ui-btn-icon-right">保存</a>
    </div>
    <div data-role="content" data-theme="a" class="m-wrapper">
    	<div class="mesg-error"><div class="mesg-error-tips"></div></div>
    	<section class="member-information-wrap">
        	<h1>个人信息</h1>
            <ul class="information-edit">
            	<li class="clear-wrap"><label class="mesg-word6">姓   名：</label><input type="text" name="name" id="name" value="${member.name }" placeholder="点击这里输入" /></li>
            	<li class="clear-wrap">
                    <label class="mesg-word6">性   别：</label> 
					<fieldset data-role="controlgroup">
                    	<input name="gender" type="radio" value="1" id="male" <c:if test="${member.gender==1 }">checked</c:if>/><label for="male" class="sex-style">男</label>
                    	<input name="gender" type="radio" value="2" id="female" <c:if test="${member.gender==2 }">checked</c:if>/><label for="female" class="sex-style">女</label>
                    </fieldset>
                </li>
            	<li class="clear-wrap">
                    <label class="mesg-word6">生   日：</label>
                    <!-- 生日非空时，在修改个人信息时不能修改生日 -->
					<fieldset data-role="controlgroup">
                    	<input name="birthday" id="birth1" type="radio" value="1" <c:if test="${member.birthType==1 }">checked</c:if> />
                    	<label for="birth1" class="sex-style">阳历</label>
                    	<input name="birthday" id="birth2" type="radio" value="2" <c:if test="${member.birthType==2 }">checked</c:if> />
                    	<label for="birth2" class="sex-style">阴历</label>
                    </fieldset>
                </li>
            	<li class="clear-wrap">
                	<select id="year" name="year" class="selset-year" <c:if test="${not empty year }">disabled</c:if>>
                    	<option>年</option>
                    </select>
                	<select id="month" name="month" class="selset-month" <c:if test="${not empty month }">disabled</c:if>>
                    	<option>月</option>
                    </select>
                	<select id="day" name="day" class="selset-day" <c:if test="${not empty day }">disabled</c:if>>
                    	<option>日</option>
                    </select>
                </li>
                <li class="clear-wrap"><span class="mesg-word6">手   机：</span><span class="mesg-word-color">${member.phoneNo }</span><span class="mesg-icon-arrow"><a href="#modify_phone_div" id="modify_mobile_btn" target="_self">修改</a></span></li>
            </ul>    
        </section>
    	 <!-- 判断该商户是否有家人生日功能 -->
	     <c:if test="${existsMemberBirth }">
	    	<section class="member-information-wrap">
	        	<h1>家人生日（最多可设置5人）
	       		  <c:choose>
	               	<c:when test="${not empty familyList && fn:length(familyList)>=5 }">
	               		<span class="mesg-icon-arrow relation-add-disabled"><i class="m-icon-add" style="display: none;"></i></span>
	               	</c:when>
	               	<c:otherwise>
	                 <span class="mesg-icon-arrow relation-add"><i class="m-icon-add" ></i></span>
	               	</c:otherwise>
	               </c:choose>
	        	</h1>
	            <ul>
	            	<li>
	            		<span class="mesg-relation-tit"><span class="relation-name-tit">称谓</span></span>
	                    <span class="mesg-relation-tit">阴/阳历<i></i></span>
	                	<span class="mesg-relation-tit relation-time">日期</span>                    
	                </li> 
	                <c:choose>
	                	<c:when test="${not empty familyList && fn:length(familyList)>0 }">
	                	<input type="hidden" name="familyCount" id="familyCount" value="${fn:length(familyList) }"/>
	                	 <c:forEach var="t" items="${familyList }">
			            	 <li class="relation-cont clear-wrap">
			                    <select name="family" class="relation-cont-select" disabled>
			                    	<c:forEach var="k" items="${relationMap }">
			                    		<option id="${k.value }" <c:if test="${t.dictFamilyRelationId==k.value }">selected</c:if>>${k.key }</option>
			                    	</c:forEach>
			                    </select>
			                     <select name="birthType" class="relation-cont-rl" disabled>
				                  	<option id="2" <c:if test="${t.birthdayType==2 }">selected</c:if>>阴历</option> 
				                  	<option id="1" <c:if test="${t.birthdayType==1 }">selected</c:if>>阳历</option>
				                  </select>
			                    <input type="text" class="datePicker" name="familyBirthday" disabled readonly value="${t.birthdayFamilyDate }"/>
			                </li>  
		                </c:forEach>
	                	</c:when>
	                	<c:otherwise>
	                		 <li class="relation-cont clear-wrap">
			                    <select name="family" class="relation-cont-select">
			                    	<c:forEach var="k" items="${relationMap }">
			                    		<option id="${k.value }">${k.key }</option>
			                    	</c:forEach>
			                    </select>
			                     <select name="birthType" class="relation-cont-rl">
				                  	<option id="2" >阴历</option> 
				                  	<option id="1" >阳历</option>
				                  </select>
			                    <input type="text" class="datePicker" name="familyBirthday" readonly placeholder="选择日期" value="${t.birthdayFamilyDate }"/>
			                    <span class="mesg-icon-arrow"><i class="m-icon-remove" onclick="javascript:relation(this);"></i></span>
			                </li>  
	                	</c:otherwise>
	                </c:choose>
	          </ul>
	            <a href="#popupDialog" class="relation-pop" data-rel="popup" data-position-to="window" data-transition="pop"></a>
				<div data-role="popup" id="popupDialog" data-overlay-theme="b" data-theme="b" data-dismissible="false">
					<div data-role="header" data-theme="a">
					<h1>提示</h1>
					</div>
					<div role="main" class="ui-content">
						<h3 class="ui-title">家人生日名额已满</h3>
	                    <p>每位用户最多可添加5位人</p>
						<a href="#" class="ui-btn m-btn5" data-rel="back">确定</a>
					</div>
				</div>
	        </section> 
        </c:if>
    </div>
</div>
<!--  -->
<a href="#modify_member_div" id="modify_back" style="display:none;"></a>
<div data-role="page" data-theme="a" id="modify_phone_div">
	<div data-role="header" data-position="inline">
		<a target="_self" href="#modify_member_div" class="ui-btn ui-shadow ui-corner-all m-icon-back ui-btn-icon-left"></a>
        <h1>会员中心</h1>        
		<a target="_self" href="#" class="ui-btn ui-shadow ui-corner-all m-icon-home ui-btn-icon-right"></a>
    </div>
    <div data-role="content" data-theme="a" class="m-wrapper">
    	<section class="memeber-tips-wrap">
        	<span><i class="m-icon-tips"></i>会员手机号修改</span>
            <span class="m-tips-arrow"></span>
        </section>
        <section class="weixin-input">
            <div class="weixin-input-cont clear-wrap"><label>手机号：</label><input type="text" name="phoneNo" id="phoneNo" placeholder="请输入您的新手机号" /></div>
            <div class="weixin-input-cont yzm-input-cont clear-wrap">
            	<label>验证码：</label><input type="text" placeholder="请输入验证码" id="identifyNo"/>
                <!--<span class="yzm-btn">获取验证码</span>-->
            	<span class="yzm-btn reset-yzm" id="getIdentifyNO">获取验证码</span>
            </div>        
            <div class="mesg-error"><div class="coupon-tips">验证码已通过短信的形式发送到您的手机上</div></div>
        </section>
        <section class="sucess-btn-cont">
        	<button class="m-btn3" onclick="javascript:verifyMember();">提交</button>
        </section>
    </div>
</div>
</body>
</html>
