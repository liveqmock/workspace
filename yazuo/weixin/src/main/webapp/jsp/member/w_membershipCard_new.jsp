
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.util.JudgeMenuShow"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.*"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://epms.tiros.com.cn/restricttag" prefix="restrict"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	BusinessVO business = ((BusinessVO) request.getAttribute("business"));
	Integer brandId = business.getBrandId();
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
	
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
    /*全局变量 星星票数、输入的评价内容、是否满足提交条件 */
	var commentStar=0,commentTxt='',isAllowSubmit=false;
</script>
<script type="text/javascript" src="<%=path%>/js/jquery/jquery.js"></script>
<script src="<%=path %>/js/jquery.mobile/jquery.mobile-1.4.5.min.js"></script>
<script src="<%=path %>/js/new_member/member.js"></script>
<title>${business.title }</title>
</head>
<body>
<input type="hidden" name="membershipId" id="membershipId" value="${member.membershipId }" />
<input type="hidden" name="brandId" id="brandId" value="${business.brandId }" />
<input type="hidden" name="cardNo" id="cardNo" value="${cardNo }" />
<input type="hidden" name="weixinId" id="weixinId" value="${weixinId }" />
<input type="hidden" name="currentpage" id="currentpage" value="0" />
<input type="hidden" name="loadcount" id="loadcount" value="0" />


<div data-role="page" data-theme="a">
	<div data-role="header" data-position="inline">
		<c:if test="${not empty pageSource && pageSource=='singleCard' }"> <!-- 多张卡 -->
			<a target="_self" href="javascript:window.history.go(-1)" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left m-icon-back"></a>
		</c:if>
        <h1>会员卡名称</h1>
        <c:if test="${not empty pageSource && pageSource=='singleCard' }"> <!-- 多张卡 -->        
			<a target="_self" href="<%=path %>/weixin/phonePage/fensiCard.do?brandId=${business.brandId }&weixinId=${weixinId}" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-right m-icon-home"></a>
		</c:if>
    </div>
    <div data-role="content" data-theme="a" class="m-wrapper">
    	<c:if test="${empty pageSource}">
    		<%@ include file="/jsp/common/member_top.jsp" %>
    	</c:if>
    	<div class="member-card-contain-pad">
    	<section class="member-card-message">
   			<div class="member-card-img cardBgItem">
   				<c:if test="${empty icon}">
   					<img src="<%=basePath %>/images/card1.png" class="cardImg" />
	                <span class="cardLogo">
	                 <c:choose>
				        <c:when test="${hasImage ||'1111111111' eq business.companyWeiboId }">
				        	<c:choose>
								<c:when test="${fn:startsWith(business.smallimageName,'group1/M00/') }">
									<img alt="" src="${pictureUrl }${business.smallimageName}"  class="cardDefaultLogo"/>
								</c:when>
							<c:otherwise>
				         		<img src="<%=basePath%>/user-upload-image/${business.brandId}/${business.smallimageName}" />
				         	</c:otherwise>
				         </c:choose>
				         </c:when>
		       			<%-- <c:when test="${!hasImage && dataSource == 14 && empty business.id}">
		       				<!--  从wifi进入的会员中心且在weixin数据库中没有次商户数据 -->
		       				<img src="<%=basePath%>images/member/default_brand_logo.png" class="cardDefaultLogo" />
		       			</c:when> --%>
					     <c:otherwise>
					     	<img src="<%=basePath%>images/member/default_brand_logo.png" class="cardDefaultLogo" />
				        	<%-- <img src="http://tp4.sinaimg.cn/${business.companyWeiboId }/180/1122/9" /> --%>
				        </c:otherwise>
				    </c:choose>
	                </span>
	                <span class="cardName">${business.title }</span>
	                <span class="cardType">${cardType }</span>
   				</c:if>
   				<c:if test="${!empty icon}">
                	 <img src="${icon}" class="cardImg" />
                </c:if>
   		   </div>
           <div class="member-card-contain">
           	<div class="member-card-number">
               	<p class="member-card-num-info"><span class="member-nums">手机号：</span><span class="card-phone-nums">${member.phoneNo}</span></p>
               	<p class="member-card-num-info"><span class="member-nums">会员卡号：</span><span>${cardNo }</span></p>
                 <c:if test="${pwdIf}">
                	<p class="member-card-set"><a class="member-card-modPaw" href="javascript:redirectUpdatePwd();" target="_self"><i></i>修改密码</a><a class="member-card-rePaw" href="javascript:isResetPwd();" target="_self"><i></i>重置卡密</a></p>
                </c:if>
             </div>
               <div class="member-card-money">
	               	<p><span class="member-nums">储值余额：</span>
	               		<span class="money-num">
							<c:choose>
	                			<c:when test="${empty storeBalance}">0</c:when>
	                			<c:otherwise>${storeBalance}</c:otherwise>
	                		</c:choose>	
						</span>
						<span class="money-style">RMB</span>
						<restrict:operation brandId="${business.brandId }" url="/weixin/{brandId}/goCoffeeRecharge.do?showwxpaytitle=1">
							<a href="<%=basePath %>weixin/${business.brandId }/goCoffeeRecharge.do?showwxpaytitle=1" class="m-btn1 m-online" target="_self">在线充值</a>
						</restrict:operation>
					</p>
                   <p><span class="member-nums">积分余额：</span>
                   		<span class="jf-num">
                   		<c:choose>
                			<c:when test="${empty integralAvailable}">0</c:when>
                			<c:otherwise>${fn:trim(integralAvailable)}</c:otherwise>
                		</c:choose>
                	</span></p>
               </div>
           </div>
        </section>
       	  <section class="member-list-module">
	        <c:if test="${!empty cardCouponList }">
       			<h2>卡券</h2>
	       		 <c:forEach items="${cardCouponList}" varStatus="i" var="item">
	      		 	<section class="member-list-wrap">
		                <div class="list-header clear-wrap">
		                    <span class="list-header-name left">${item.name}&nbsp;&nbsp;${item.availableQuantity }张</span>
		                    <!-- 判断是否有赠送功能 -->
		                    <restrict:operation brandId="${business.brandId }" url="/weixin/{brandId}/toSendFriend.do ">
			                    <a href="${pageContext.request.contextPath}/weixin/${business.brandId}/toSendFriend.do?fromUserId=${member.membershipId}&couponId=${item.id}&couponName=${item.name}&couponNum=${item.availableQuantity }&beginTime=${item.startDate}&endTime=${item.expiredDate }&weixinId=${weixinId}" target="_self" class="m-btn2 right">送朋友</a>
			                </restrict:operation>
		                </div>
		                <div class="list-content">
		                    <c:choose>
								<c:when test="${business.brandId eq 1631 }">
			                    	<h3>活动有效期：<span class="list-time">${item.startDate }</span> 至  <span class="list-time">${item.expiredDate }</span></h3>
			                    </c:when>
			                    <c:otherwise>
			                    	<h3>有效期：<span class="list-time">${item.startDate }</span> 至  <span class="list-time">${item.expiredDate }</span></h3>
			                    </c:otherwise>
		                    </c:choose>
		                    <div class="list-content-wrap"></div>
		                    <span class="icon-arrow-gray" onclick="showDesc('${item.batchNo}',this)"><i></i></span>
		                </div>
		            </section>
	       		 </c:forEach>
	        </c:if>
	        <c:if test="${!empty phoneCouponList }">
	        	<h2>通用券<span class="member-tit-sm">跟手机号绑定，所有卡内都可见哦~</span></h2>
	        	 <c:forEach items="${phoneCouponList}" varStatus="i" var="item">
		            <section class="member-list-wrap">
		                <div class="list-header list-header-blue clear-wrap">
		                    <span class="list-header-name left">${item.name}&nbsp;&nbsp;${item.availableQuantity }张</span>
		                    <restrict:operation brandId="${business.brandId }" url="/weixin/{brandId}/toSendFriend.do ">
			                    <a href="${pageContext.request.contextPath}/weixin/${business.brandId}/toSendFriend.do?fromUserId=${member.membershipId}&couponId=${item.id}&couponName=${item.name}&couponNum=${item.availableQuantity }&beginTime=${item.startDate}&endTime=${item.expiredDate }&weixinId=${weixinId}" target="_self" class="m-btn2 right">送朋友</a>
			                </restrict:operation>
		                </div>
		                    <div class="list-content">
		                        <c:choose>
								<c:when test="${business.brandId eq 1631 }">
		                        	<h3>活动有效期：<span class="list-time">${item.startDate }</span> 至 <span class="list-time">${item.expiredDate }</span></h3>
		                         </c:when>
			                    <c:otherwise>
		                        	<h3>有效期：<span class="list-time">${item.startDate }</span> 至 <span class="list-time">${item.expiredDate }</span></h3>
		                       </c:otherwise>
		                     </c:choose>
		                        <div class="list-content-wrap"></div>
		                        <span class="icon-arrow-gray" onclick="showDesc('${item.batchNo}',this)"><i></i></span>
		                    </div>
		            </section>
	            </c:forEach>
	        </c:if>
	        <!-- 交易信息 start-->
			<div class="tradeRecord  member-trade-list-wrap">
				<h3></h3>
		   		<!--<span class="recordTitle" id="JS_recordTitle" href="javascript:;">交易记录<i class="viewIco"></i></span>  -->
		   		<div  class="member-trade-list-content">
			   		<div  class="member-trade-list"  id="JS_recordListWrap">
			   		 <p class="m-bg-tit"></p>
	                    <p class="trade-list-tit">交易记录</p>
				   			<ul class="recordList trade-list-content" id="JS_recordList" style="display:none">	   		
				   				
				   			</ul>
				   		<div class="comment-more" id="zrg" style="display: none"><a href="javascript:queryTransactionRecord(${business.brandId }, ${member.membershipId });" class="viewMoreLinks">查询更多 <i class="viewMoreIco"></i></a></div>
						<div class="comment-more" id="noMore" style="display: none"><i class="viewMoreLinks">没有更多</i></div>
						<span class="viewIco" id="JS_recordTitle"><i></i></span>
			   		</div>
			   		<span class="list-bottom-bg"></span>
		   		</div>
			</div>
			<!-- 交易信息 end-->
			<!-- 重置密码提示 -->
			 <a href="#popupDialog" class="relation-pop" data-rel="popup" data-position-to="window" data-transition="pop"></a>
			<div data-role="popup" id="popupDialog" data-overlay-theme="b" data-theme="b" data-dismissible="false">
				<div data-role="header" data-theme="a">
				<h1>提示</h1>
				</div>
				<div role="main" class="ui-content">
					<p>您确定要重置密码吗?</p>
					<a href="#" onclick="javascript:resetPwd();" class="ui-btn m-btn5">确定</a>
					<a href="#" class="ui-btn m-btn5" data-rel="back">取消</a>
				</div>
			</div>
        </section>
        </div>
    </div>
    <c:if test="${empty pageSource}">
    	<%@ include file="/jsp/common/common_top.jsp" %>
    </c:if>
</div>
</body>
</html>
