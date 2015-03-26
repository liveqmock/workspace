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
<title>${map.business.title }</title>
</head>
<body>
<div data-role="page">
	<c:if test="${map.flag == 0}">
    <div data-role="content" class="card-content">
    	<section class="buy-success">
        	<div class="success-word"><img src="<%=basePath %>imageCoffee/icon05.png" width="30" />
      
	       	   		<span>服务器繁忙，请稍后再试！</span>
        	</div>
        </section>
    </div>
	</c:if>
		<c:if test="${map.flag == 1}">
      	 	    <script language="javascript">       	
				window.location.href=ctx+"/weixin/phonePage/fensiCard.do?brandId=${map.brand_id}&weixinId=${map.weixin_id}&random=<%=Math.random()%>";
				</script>
      	</c:if>

</div> 
</body>
</html>
