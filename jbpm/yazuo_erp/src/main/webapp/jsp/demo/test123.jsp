<!DOCTYPE html>
<%@page import="org.apache.commons.lang.math.RandomUtils"%>
<%@page import="java.util.Random"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
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
<style type="text/css">
  .input-sql {
    width:300px;
    height:130px;
  }
</style>
<body class="page-add-project">
	<div>
	  <button type="button" class="runscript btn-link-1">run</button><br>
	  <textarea class="input-sql">
	  	select * from sys.sys_user
	  </textarea>
	  <br/>
	 	 执行结果： <br/>
	  <div class="response-data">
	  
	  </div>
	<%
		//System.exit(0);
	%>
	</div>
	<script>
        jQuery(function($){
        	$('.runscript').on('click', function(){
        		var mydata = $('.input-sql').val();
                $.ajax({
                    type: "POST",
                    url:'<%=basePath%>tools/runScript.do',
                    data:JSON.stringify(mydata),
                    contentType : 'application/json',
                    dataType:"json",
                    success:function(data){
                    	//alert( JSON.stringify(data));
                    	$('.response-data').html(JSON.stringify(data));
                    }
                });
        	});
        });
    </script>
</body>
</html>

