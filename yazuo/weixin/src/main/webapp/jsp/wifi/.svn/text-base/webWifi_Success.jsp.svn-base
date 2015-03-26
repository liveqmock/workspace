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
<link type="text/css" rel="stylesheet" href="<%=basePath %>css/wx_card.css" />
<script type="text/javascript">
	//查看会员信息
	function lookCardDetial (brandId, weixinId) {
		window.location.href = ctx + "/weixin/phonePage/fensiCard.do?brandId="+brandId+"&weixinId="+weixinId+"&random="+Math.random();
	}
	
	// 查看推送页信息
	function lookPushPage(brandId, weixinId) {
		window.location.href = ctx + "/merchantWifi/pushPage.do?brandId="+brandId+"&weixinId="+weixinId+"&random="+Math.random();
	}
</script>
<title>${map.business.title }</title>
</head>
<body>
<div data-role="page">
    <div data-role="content" class="card-content">
    	<section class="buy-success">
        	<div class="success-word"><img src="<%=basePath %>imageCoffee/icon05.png" width="30" />
      
	       	   <c:if test="${map.flag == 0}">
	       	   		<span>服务器繁忙，请稍后再试！</span>
	       	   </c:if>
        	</div>
	  <c:if test="${map.flag == 1}">
	       	 	<ul class="buy-list">
						<li><span class="gray999">
						<c:choose>
			        		<c:when test="${flag}">
			        			<span>您加入过我们的会员，现在可以正常使用WIFI了~</span>
			        		</c:when>
			        		<c:otherwise>
			        			<span>您现在可以正常使用WIFI了~</span>
			        		</c:otherwise>
			        	</c:choose>
						</span></li>
						<c:if test="${!empty map.coupons}">
							<c:forEach var="o" items="${map.coupons }">
								<li><span class="gray999">
									您获得了#${o.name }#一张,<br />
									有效期：#${o.startDate }至${o.expiredDate }#
								</span></li>
							</c:forEach>
						</c:if>
				</ul>
	       	</c:if>
        </section>
    </div>
<c:if test="${map.flag == 1}">
	<div class="sub-div" id="userSubmit">
		<c:if test="${not empty map.isPush && map.isPush }"><!-- 推送页 -->
			<a href="javascript:lookPushPage(${map.brand_id},'${map.weixin_id}');" class="mem-sub mem-submit">查看会员中心</a>		
		</c:if>
		<c:if test="${empty map.isPush || !map.isPush}"><!-- 普通会员中心页面 -->
			<a href="javascript:lookCardDetial(${map.brand_id},'${map.weixin_id}');" class="mem-sub mem-submit">查看会员中心</a>
		</c:if>
	</div>
</c:if>
</div> 
</body>
</html>
