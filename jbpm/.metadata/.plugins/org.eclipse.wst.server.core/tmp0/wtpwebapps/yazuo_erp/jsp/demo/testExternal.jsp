<!DOCTYPE html>
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
<body class="page-add-project">
	<div>
		<button type="button" class="getDailyMerchantSummary btn-link-1">商户日报</button><br>
	</div>
	
	<script>
        jQuery(function($){
        	$('.getDailyMerchantSummary').on('click', function(){
        		 var mydata = JSON.stringify({ 
        			 merchantId:9355,
        			 date:1415030400000
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>report/getDailyMerchantSummary.do',
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

