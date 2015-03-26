<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class='redBg'>
<head>
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
    <div data-role="header" class="shop-header">
    	<a target="_self" href="javascript:window.history.go(-1);" data-role="button"><img src="<%=basePath %>imageCoffee/back.png" width="16" />返回</a>
    	<h1>中奖情况查询</h1>
    </div>
    <div data-role="content" class="card-content">
    	<section class="prize-demand">
            <ul class="prize-list">
            <c:if test="${empty recordList || fn:length(recordList)==0 }">
            	<li><p>您还没有任何中奖记录</p></li>
            </c:if>
            <c:if test="${not empty recordList && fn:length(recordList)>0 }">
	        	<c:forEach var="r" items="${recordList }" varStatus="i">
	        		<li>
	                	 <c:if test="${r.lottery_item_type==2 }">
	                		<span class="icon-arrow-b"></span>
	                	</c:if>
	                	<p><span class="gray999">奖品名称：</span>
	                		<span>
	                			${not empty r.lottery_coupon_name ? r.lottery_coupon_name : r.lottery_item_name}
	                		</span>
	                	</p>
	                	<p><span class="gray999">中奖时间：</span><span><fmt:formatDate value="${r.addtime }" pattern="yyyy-MM-dd HH:mm:ss" /> </span></p>
	                	<p><span class="gray999">抽奖活动：</span>
	                	<span>
	                		<c:if test="${r.active_type==28 }">幸运大抽奖</c:if>
	                		<c:if test="${r.active_type==22 }">微信抽奖</c:if>
	                	</span></p>
	                </li>
	                <c:if test="${r.lottery_item_type==2 }">
		                 <div class="list-detail poisition hide">
		                    	<c:if test="${empty r.detail_addr }">
			                    	<span class="icon-arrow-r"></span>
			                    	<a href="javascript:void(0);" class="block"  onclick="redirectAddAddr('${r.lottery_item_name }','${r.lottery_coupon_name }',${r.orderId },'${r.weixin_id }', ${r.membership_id },${r.brand_id });">
			                    		<span class="icon-arrow-r"></span>
                            			<p>请填写收货地址</p>
			                    	</a>
		                    	</c:if>
		                    	<c:if test="${not empty r.detail_addr }">
			                        <p><span class="gray999">收货人：</span><span>${r.receiver }</span></p>
			                        <p><span class="gray999">联系方式：</span><span>${r.mobile }</span></p>
			                        <p><span class="gray999">详细地址：</span>
			                        	<span>
			                        		<c:set var="address" value="${r.detail_addr }"></c:set>
			                        		<c:if test="${not empty r.detail_addr }">
			                        			<c:set var="address" value="${fn:split(r.detail_addr,',')[2] }"></c:set>
			                        		</c:if>
			                        		${address }
			                        	</span>
			                        </p>
		                        </c:if>
		                    </div>
	                </c:if>
	        	</c:forEach>
	        </c:if>
            </ul>
        </section>
    </div>
    
</div> 
</body>
</html>
