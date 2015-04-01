<!DOCTYPE html>
<%@page import="com.yazuo.erp.system.vo.SysUserRequirementVO"%>
<%@page import="com.yazuo.erp.system.vo.SysProductOperationVO"%>
<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<%@ include file="/common/meta.jsp"%>
<link href="<%=basePath%>css/jquery-ui-1.10.4.custom.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/jquery/jquery-ui-1.10.4.custom.min.js"></script>
<script src="<%=basePath%>js/jquery.pagination.js"></script>
</head>
<body class="page-add-project"> [[[]]] 2014-11-7 09:17:53
	<div>
		<div>
			<br/>
			<%
			out.println(System.getProperties());
			out.println("");
			out.println("");
			out.println("");
			 ObjectMapper mapper = new ObjectMapper();
			try {
				String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(new SysUserRequirementVO());
				out.println(jsonString);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			%>
		</div>
			
			<br/>
			<br/>
		</div>
	</div>
	<script>
    <script>
        	jQuery('.sendEmail').on('click', function(){
        		var mydata = JSON.stringify(
						{
							"sendAddress": "hasdfasd",
							"subject": "subject",
							"context": "context",
							"to": ["songfuyu@yazuo.com"]
						 }
					);
        		jQuery.ajax({
				    type: "POST",
				    contentType : 'application/json',
				    url: '<%=basePath%>fesOnlineProcess/sendEmail.do',
				    data: mydata,
				    dataType:"json",
				    success:function(data){
				    	alert(JSON.stringify(data));
				    	console.log( JSON.stringify(data));
				    }
				});
        	});
    </script>
</body>
</html>

