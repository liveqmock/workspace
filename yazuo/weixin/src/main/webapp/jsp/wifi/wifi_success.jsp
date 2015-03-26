<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class='redBg'>
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
		window.location.href ="${reportUrl}/weixin/phonePage/fensiCard.do?brandId="+brandId+"&weixinId="+weixinId+"&random="+Math.random();
	}
	// 查看推送页信息
	function lookPushPage(brandId, weixinId) {
		window.location.href ="${reportUrl}/merchantWifi/pushPage.do?brandId="+brandId+"&weixinId="+weixinId+"&random="+Math.random();
	}
</script>
<title>${not empty business ? business.title : "" }</title>
</head>
<body>
<div data-role="page">
    <div data-role="content" class="card-content">
    	<section class="buy-success">
        	<div class="success-word">
       			<img src="<%=basePath %>imageCoffee/icon05.png" width="30" />
	        	<c:choose>
	        		<c:when test="${flag}">
	        			<span>会员绑定成功！</span>
	        		</c:when>
	        		<c:otherwise>
	        			<span>成功加入会员！</span>
	        		</c:otherwise>
	        	</c:choose>
        	</div>
				 <ul class="buy-list">
					<li><span class="gray999">
		        			<p style="margin-bottom:10px;">	<span>您现在可以正常使用WIFI了~</span></p>
		        			<%-- <span><img src="http://report.yazuo.com/image/failed.png?r=<%=Math.random() %>" width="90%;" style="display:block; margin:0px auto;"  /></span> --%>
					</span></li>
					<c:if test="${not empty luckCount && luckCount>0 }">
						<li><span class="gray999">您获得了#${luckCount }#次幸运抽奖奖机会，快去试试手气吧~</span></li>
					</c:if>
					<c:if test="${!empty couponList }">
						<c:forEach var="o" items="${couponList }">
							<li><span class="gray999">
								您获得了#${o.name }#一张,<br />
								有效期：#${o.startDate }至${o.expiredDate }#
							</span></li>
						</c:forEach>
					</c:if>
				</ul>
        </section>
    </div>
	<div class="sub-div" id="userSubmit">
		<c:if test="${not empty isPush && isPush }"><!-- 跳转推送页面 -->
			<a href="javascript:lookPushPage(${brandId },'${weixinId }');" class="mem-sub mem-submit">查看会员中心</a>
		</c:if>
		<c:if test="${empty isPush || !isPush }"><!-- 跳转普通的会员中心页面-->
			<a href="javascript:lookCardDetial(${brandId },'${weixinId }');" class="mem-sub mem-submit">查看会员中心</a>
		</c:if>
	</div>
    
</div> 
</body>
</html>
