<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	List<SummaryDetail> summaryDetailList=((List<SummaryDetail>) request.getAttribute("summaryDetailList"));
	String[] unit=new String[]{"人","张","元","元","元","张","元","元","元","元","元"};
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/crm.css" />
<title></title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>

</head>
<body>
<div class="w">
	<section class="w-top">
    	<div class="top-bg"></div>
        <section class="company-logo">
        	<img src="<%=basePath%>/user-upload-image/0/0_small.jpg" width="90" height="90" />
        </section>
        <section class="company-name">
        	<h1>雅座CRM</h1>
        </section>
         <section class="top-menu">
        </section>
    </section>
   <section class="w-main">
    	<ul class="w-subb">
    		<%
				for (SummaryDetail summaryDetail : summaryDetailList) {
			%>
        	<li>
            	<h1><%=summaryDetail.getName() %> <%=summaryDetail.getValue() +unit[summaryDetail.getUnit().intValue()] %></h1>
            </li>
            <%
				}
			%>
        </ul>
    </section>

</div>
</body>
</html>