<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>  
<!doctype html>
<html class="no-js" lang="">
<head>
<meta charset="utf-8">
<meta name="description" content="">
<meta name="HandheldFriendly" content="True">
<meta name="MobileOptimized" content="320">
<meta name="viewport" content="width=device-width, initial-scale=1">
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
<script src="<%=basePath %>js/prize-list.js"></script>
<title>在线办卡</title>
</head>
<body>
<div data-role="page" data-theme="a">
	<div data-role="header" data-position="inline">
		<a target="_self" href="javascript:window.history.go(-1)" class="ui-btn ui-shadow ui-corner-all ui-icon-arrow-l ui-btn-icon-left"></a>
        <h1>会员中心</h1>        
		<!-- <a target="_self" href="#" class="ui-btn ui-shadow ui-corner-all ui-icon-home ui-btn-icon-right"></a> -->
    </div>
    <div data-role="content" data-theme="a" class="m-wrapper">
        <section class="member-success-content">
        	<div class="success-cont">
            	<div class="success-icon">
                	<p><img src="<%=path %>/images/member/icon21.png" /></p>
                    <p>恭喜您，绑定成功！</p>
                </div>
                <div class="success-mesg">
                	<p class="text-center">
                	<span class="mesg-word">
                		<c:if test="${!isMemberBindWeixin }">
                			您的会员卡可以正式使用了~
                		</c:if>
                	</span></p>
                </div>
            </div>
	            <div class="coupon-tips" style="display: block;">
	                <span class="coupon-tips-arrow"></span>
		            <c:if test="${isGiveLuckCount }">
			            <p>您获得了<span class="tips-word-color">${luckCount }次</span>幸运抽奖机会，快去试手气吧~</p>
		            </c:if>
                	<c:if test="${!isGiveLuckCount }">
	                	<c:forEach var="o" items="${couponList }">
							<p>	
								您获得了${o.name }一张,<br />
								有效期：${o.startDate }至${o.expiredDate }
							</p>							
						</c:forEach>
                	</c:if>
	            </div>
        </section>
        <section class="sucess-btn-cont">
        	<c:if test="${isGiveLuckCount }">
				<a href="javascript:nowLuck(${brandId },'${weixinId }');"  target="_self" class="m-btn3">马上参加幸运抽奖</a>
			</c:if>
			<c:if test="${!isGiveLuckCount }">
				<a href="javascript:lookCardDetial(${brandId },'${weixinId }');"  target="_self" class="m-btn3">查看会员卡</a>
			</c:if>
        </section>
    </div>
</div>
</body>
</html>
