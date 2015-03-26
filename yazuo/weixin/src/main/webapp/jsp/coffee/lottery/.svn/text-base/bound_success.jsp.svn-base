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
<script src="<%=basePath %>js/prize-list.js"></script>
<script src="<%=basePath %>js/jquery.mobile/jquery.mobile.js"></script>
<title>在线办卡</title>
</head>
<body>
<div data-role="page">
    <div data-role="content" class="card-content">
    	<section class="buy-success">
        	<div class="success-word"><img src="<%=basePath %>imageCoffee/icon05.png" width="30" /><span> 恭喜您，绑定成功！</span></div>
				 <ul class="buy-list">
					<li><span class="gray999">
						<!-- 非会员绑定微信显示 -->
						<c:if test="${!isMemberBindWeixin }">
							您的会员卡可以正常使用了~
						</c:if>
						</span></li>
					<c:if test="${isGiveLuckCount }">
						<li><span class="gray999">您获得了${luckCount }次幸运抽奖奖机会，快去试试手气吧~</span></li>
					</c:if>
					<c:if test="${!isGiveLuckCount }">
						<c:forEach var="o" items="${couponList }">
							<li><span class="gray999">
								您获得了${o.name }一张,<br />
								有效期：${o.startDate }至${o.expiredDate }
							</span></li>
						</c:forEach>
					</c:if>
				</ul>
        </section>
    </div>
	<div class="sub-div" id="userSubmit">
	<c:if test="${isGiveLuckCount }">
		<a href="javascript:nowLuck(${brandId },'${weixinId }');" class="mem-sub mem-submit">马上参加幸运抽奖</a>
	</c:if>
	<c:if test="${!isGiveLuckCount }">
		<a href="javascript:lookCardDetial(${brandId },'${weixinId }');" class="mem-sub mem-submit">查看会员卡</a>
	</c:if>
	</div>
    
</div> 
</body>
</html>
