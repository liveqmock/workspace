<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.BusinessVO"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	BusinessVO business = ((BusinessVO) request.getAttribute("business"));
	Integer brandId = business.getBrandId();
%> 
<!doctype html>
<html class="no-js" lang="">
<head>
<meta charset="utf-8">
<meta name="description" content="">
<meta name="HandheldFriendly" content="True">
<meta name="MobileOptimized" content="320">
<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no"> 
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/a.min.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/jquery.mobile.icons.min.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/jquery.mobile.structure-1.4.5.min.css" />
<link rel="stylesheet" href="<%=path %>/css/new_member/base.css" />
<link rel="stylesheet" href="<%=path %>/css/new_member/member.css" />
<script type="text/javascript">
	var ctx = "<%=path%>";
	//弹窗消失
	 setTimeout(function(){
		$(".member-card-tips").animate({
			opacity: 'hide'
		},300);				
	},5000);    
</script>
<script type="text/javascript" src="<%=path%>/js/jquery/jquery.js"></script>
<script src="<%=path %>/js/jquery.mobile/jquery.mobile-1.4.5.min.js"></script>
<title>${business.title }</title>
</head>
<body>
<div data-role="page" data-theme="a">
	<div data-role="header" data-position="inline">
		<a target="_self" href="<%=path %>/weixin/phonePage/fensiCard.do?brandId=${business.brandId }&weixinId=${weixinId}" class="ui-btn ui-shadow ui-corner-all ui-icon-home ui-btn-icon-left"></a>
        <h1>${business.title }</h1>
    </div>
    <div data-role="content" data-theme="a" class="m-wrapper">
    	 <c:if test="${not empty map.couponArray && fn:length(map.couponArray)>0}">
	       	<section class="member-card-tips">
		    	<p class="success-img"><img src="<%=path %>/images/member/card-success-icon.png" /></p>
				<p class="card-tips-word">恭喜，您已成功连接 ${business.title } WIFI，享受WIFI会员待遇。
					您已获赠 
					<c:forEach var="c" items="${map.couponArray }">
						${c.name }一张;
					</c:forEach>
				</p>
			</section>
		</c:if>
    	<section class="member-card-message card-VIP">
    		${customAdvter }
        </section>
    </div>
   <%@ include file="/jsp/common/common_top.jsp" %>
  </div>
</body>
</html>
