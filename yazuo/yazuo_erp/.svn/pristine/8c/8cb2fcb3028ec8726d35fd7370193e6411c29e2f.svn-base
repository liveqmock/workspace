<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
    <%@ include file="/common/meta.jsp"%>
    <link href="<%=basePath%>css/jquery-ui-1.10.4.custom.min.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=basePath%>js/jquery/jquery-ui-1.10.4.custom.min.js"></script>
    <script src="<%=basePath%>js/jquery.pagination.js"></script>
</head>
<body class="page-add-project">
	<div>
     <button type="button" class="search btn-link-1">查询</button>
	</div>
    <script>
        jQuery(function($){
        	
        	$('.search').on('click', function(){
        		 var mydata = JSON.stringify({ pageNum: 1,
                     pageSize: 5
                    }              
                );
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>course/getCourseInfo.do',
                    data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    }
                });
        	});
        });
    </script>
</body>
</html>

