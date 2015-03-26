<%@page import="org.apache.activemq.openwire.DataStreamMarshaller"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	BusinessVO business = ((BusinessVO) request.getAttribute("business"));
	MemberVO member = (MemberVO)request.getAttribute("member");
	String weixinId = (String)request.getAttribute("weixinId");
	Integer brandId = business.getBrandId();
	String title = business.getTitle();
%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<title><%=title%></title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/crm.css" />
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>js/jquery/jquery.form.js"></script>
<script language="javascript">

</script>
</head>
<body>
<div class="w">
    <%-- <section class="mem-main" style="display:block" id="submitInformation">
    	<div class="mem-succ" ><img src="<%=path%>/images/suc1.png" width="40" /><span>您已经是粉丝了</span></div>
        <div class="sub-div" >
        	<a href="<%=path%>/weixin/phonePage/fensiCard.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>&random=<%=Math.random()%>" class="mem-sub" id="submit">确&nbsp;&nbsp;&nbsp;定</a>
        </div>
    </section> --%>
	<script language="javascript">       	
	var url = "${toUrl}";
	if (url!=null && url!="") {
		window.location.href="<%=path%>" + url;
	} else {	
		window.location.href="<%=path%>/weixin/phonePage/fensiCard.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>&random=<%=Math.random()%>";
	}
	</script>
</div>
</body>
</html>
