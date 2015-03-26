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
	//进入粉丝会员添加页面
	function addMember (brandId, weixinId) {
		window.location.href = ctx + "/weixin/phonePage/registerPage.do?brandId=" +brandId + "&weixinId=" + weixinId+"&phoneNo=${phoneNo}&random="+Math.random();
	}
</script>
<title>在线办卡</title>
</head>
<body>
<div data-role="page">
    <div data-role="content" class="card-content">
    	<section class="buy-success">
        	<div class="success-word-bind"><img src="<%=basePath %>imageCoffee/fail.png" width="30" /><span> 抱歉，您还不是我们的会员</span></div>
        </section>
    </div>
	<div class="sub-div" id="userSubmit">
		<a href="javascript:addMember(${brandId },'${weixinId }');" class="mem-sub mem-submit">点击加入粉丝会员</a>
	</div>
    
</div> 
</body>
</html>
