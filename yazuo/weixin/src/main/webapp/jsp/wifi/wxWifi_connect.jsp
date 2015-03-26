<%@page import="com.yazuo.weixin.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp"%>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<script type="text/javascript" language="javascript">
	var ctx = "<%=basePath%>";
</script>
<%
	String reportUrl = "http://report.yazuo.com"+request.getContextPath();
	pageContext.setAttribute("reportUrl", reportUrl);
%>
<link type="text/css" rel="stylesheet" href="<%=basePath %>css/wx_card.css" />
<script type="text/javascript">
	//查看会员信息
	function lookCardDetial (brandId, weixinId) {
		window.location.href =  "${reportUrl}/weixin/phonePage/fensiCard.do?brandId="+brandId+"&weixinId="+weixinId+"&random="+Math.random();
	}
	// 添加会员
	function addMember(brandId, weixinId) {
		window.location.href=ctx + "/weixin/phonePage/registerPage.do?brandId=" + brandId + "&weixinId=" + weixinId + "&source=<%=Constant.MEMBERSOURCE_13%>";
	}
	
	// 查看推送页信息
	function lookPushPage(brandId, weixinId) {
		window.location.href = "${reportUrl}/merchantWifi/pushPage.do?brandId="+brandId+"&weixinId="+weixinId+"&random="+Math.random();
	}

</script>
<title>${business.title }</title>
</head>
<body>
<div data-role="page">
    <div data-role="content" class="card-content">
    	<section class="buy-success">
        	<div class="success-word">
	        	<p style="margin-bottom:10px;">
	        		<img src="<%=basePath %>imageCoffee/icon05.png" width="30" />
	        		<!-- <a href="http://report.yazuo.com/controller/security/goUpdateMerchantStatus.do">上网请点击这里</a><br /> -->
	        	<c:choose>
	        		<c:when test="${isMember}">
	        			<span>您加入过我们的会员，现在可以正常使用WIFI了~</span></p>
	        			  <%-- <img src="http://report.yazuo.com/image/failed.png?r=<%=Math.random() %>" width="90%;" style="display:block; margin:0px auto;" /> --%>
	        		</c:when>
	        		<c:otherwise>
	        			<span>您还不是我们的会员，请先加入会员才能使用wifi~</span></p>
	        		</c:otherwise>
	        	</c:choose>
        	
        	</div>
        </section>
    </div>
	<div class="sub-div" id="userSubmit">
		<c:choose>
       		<c:when test="${isMember}">
       			<c:if test="${not empty isPush && isPush }">
       				<a href="javascript:lookPushPage(${brandId},'${weixinId}');" class="mem-sub mem-submit">查看会员中心</a>
       			</c:if>
       			<c:if test="${empty isPush || !isPush }">
	       			<a href="javascript:lookCardDetial(${brandId},'${weixinId }');" class="mem-sub mem-submit">查看会员中心</a>
       			</c:if>
       		</c:when>
       		<c:otherwise>
       			<a href="javascript:addMember(${brandId },'${weixinId }');" class="mem-sub mem-submit">加入会员</a>
       		</c:otherwise>
       	</c:choose>
	</div>
</div> 
</body>
</html>
