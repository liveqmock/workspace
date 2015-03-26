<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/common/meta.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0"/>
<title></title>
	
	
<script>
$(function(){
	var flag='${map.flag}';
	if(flag == 0) {
		jQuery("#flag").html("WIFI连接失败！");
	} else {
		jQuery("#flag").html("");
		var brandId='${map.brand_id}';
		var weixinId='${map.weixin_id}';
		window.location.href = "<%=basePath%>/weixin/phonePage/fensiCard.do?brandId="+brandId+"&weixinId="+weixinId+"&random="+Math.random();
	}
});
</script>
	
</head>	
<body>
			
	
	<section class='container'><i class='leftBg'></i> <i class='footBg'></i>
		<p class='errorMsg'> <i class='errorIco'></i>
			<span id="flag"></span>
		</p>	
	</section>

	
	


</body>
</html>