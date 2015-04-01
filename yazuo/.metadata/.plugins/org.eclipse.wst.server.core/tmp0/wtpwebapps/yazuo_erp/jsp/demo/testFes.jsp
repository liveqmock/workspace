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
		<button type="button" class="saveFesPlan btn-link-1">工作计划添加</button><br>
		<button type="button" class="updateFesPlan btn-link-1">工作计划修改</button><br>
		<button type="button" class="getSynMerchantByUserId btn-link-1">查询前端咨询人员管理商户列表</button><br>
		<button type="button" class="queryFesPlanList btn-link-1">由开始时间和结束时间查询工作计划</button><br>
		<button type="button" class="queryDailyFesPlanList btn-link-1">查询一天的工作计划</button><br>
		<button type="button" class="queryFesPlanById btn-link-1">根据工作计划ID查询详细信息</button><br>
		<button type="button" class="updateAbandonFesPlanById btn-link-1">放弃工作计划</button><br>
		<button type="button" class="updateDelayFesPlanById btn-link-1">延期工作计划</button><br>
		<button type="button" class="updateCompleteFesPlanById btn-link-1">完成工作计划</button><br>
		<button type="button" class="updateRemindFesPlanById btn-link-1">工作计划提醒设置</button><br>
		<button type="button" class="downloadFiles btn-link-1">下载</button><br>
		<br>
		<button type="button" class="saveFesPlanOfExternal btn-link-1">工作计划添加（外部接口）</button><br>
		<button type="button" class="updateFesPlanOfExternal btn-link-1">工作计划修改（外部接口）</button><br>
		<button type="button" class="updateAbandonFesPlanByIdOfExternal btn-link-1">放弃工作计划（外部接口）</button><br>
		<button type="button" class="updateDelayFesPlanByIdOfExternal btn-link-1">延期工作计划（外部接口）</button><br>
		<br>
		<button type="button" class="exportReport btn-link-1">导出运营报告</button><br>
		<br>
		<button type="button" class="exportPlanReport btn-link-1">导出工作计划</button><br>
	</div>
	<script>
        jQuery(function($){
        	$('.saveFesPlan').on('click', function(){
        		 var mydata = JSON.stringify({
        			 userId:1,
        			 merchantId:15,
        			 title:'0730工作计划添加测试',
        			 planItemType:'1',
        			 communicationFormType:'1',
        			 isRemind:false,
        			 isSendSms:false,
        			 plansSource:'1',
        			 description:'0730工作计划添加测试',
        			 explanation:'',
        			 sponsor:999999,
        			 remark:'',
        			 startTime:1406601475187,
        			 endTime:0,
        			 remindTime:0,
        			 attachmentList:[
						{
							originalFileName:'1.txt',
							attachmentName:'011c832d-39fe-4417-a934-4fc747fd45eb.txt',
							attachmentSize:'10',
							attachmentSuffix:'txt'
						},
						{
							originalFileName:'2.txt',
							attachmentName:'021c832d-39fe-4417-a934-4fc747fd45eb.txt',
							attachmentSize:'20',
							attachmentSuffix:'txt'
						}
					],
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>fesPlan/saveFesPlan.do',
                    data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    }
                });
        	});
        });
    </script>
    
    <script>
        jQuery(function($){
        	$('.updateFesPlan').on('click', function(){
        		 var mydata = JSON.stringify({
        			 userId:1,
        			 planId:1,
        			 merchantId:15,
        			 title:'0730工作计划修改测试',
        			 planItemType:'2',
        			 communicationFormType:'1',
        			 isRemind:false,
        			 isSendSms:false,
        			 plansSource:'1',
        			 description:'0730工作计划修改测试',
        			 explanation:'',
        			 sponsor:999999,
        			 remark:'',
        			 startTime:1406601475187,
        			 endTime:1406601475187,
        			 remindTime:1406601475187,
        			 attachmentList:[
						{
							attachmentId:3,
							originalFileName:'1.txt',
							attachmentName:'011c832d-39fe-4417-a934-4fc747fd45eb.txt',
							attachmentSize:'10',
							attachmentSuffix:'txt'
						},
						{
							originalFileName:'3.txt',
							attachmentName:'021c832d-39fe-4417-a934-4fc747fd45eb.txt',
							attachmentSize:'30',
							attachmentSuffix:'txt'
						}
					],
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>fesPlan/updateFesPlan.do',
                    data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    }
                });
        	});
        });
    </script>
    
    <script>
        jQuery(function($){
        	$('.saveFesPlanOfExternal').on('click', function(){
        		 var mydata = JSON.stringify({ 
        			 userId:1,
        			 merchantId:15,
        			 title:'0730工作计划添加测试',
        			 description:'0730工作计划添加测试',
        			 startTime:1406601475187,
        			 endTime:1406601475187,
        			 explanation:'',
        			 isRemind:false,
        			 remindTime:1406601475187,
        			 isSendSms:false,
        			 sponsor:999999,
        			 plansSource:'1',
        			 remark:''
        		});
                $.ajax({
                    type: "POST",
                    //contentType : 'application/json',
                    url:'<%=basePath%>external/fesPlan/saveFesPlan.do?key=001&ciphertext='+mydata,
                    //data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    }
                });
        	});
        });
    </script>
    
    <script>
        jQuery(function($){
        	$('.updateFesPlanOfExternal').on('click', function(){
        		 var mydata = JSON.stringify({ 
        			 userId:1,
        			 planId:11,
        			 merchantId:15,
        			 title:'0731工作计划修改测试',
        			 isRemind:false,
        			 isSendSms:false,
        			 plansSource:'1',
        			 description:'0731工作计划修改测试',
        			 explanation:'',
        			 sponsor:999999,
        			 remark:'',
        			 startTime:1406601475187,
        			 endTime:1406601475187,
        			 remindTime:1406601475187,
        			 status:'1'
        		});
                $.ajax({
                    type: "POST",
                    //contentType : 'application/json',
                    url:'<%=basePath%>external/fesPlan/updateFesPlan.do?key=001&ciphertext='+mydata,
                    //data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    }
                });
        	});
        });
    </script>
    
    <script>
        jQuery(function($){
        	$('.getSynMerchantByUserId').on('click', function(){
        		var mydata = 1;
        		// var mydata = JSON.stringify({ 
        		//	 userId:1
        		// });
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>synMerchant/getSynMerchantByUserId.do?userId='+mydata,
                    //data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    }
                });
        	});
        });
    </script>
    
    <script>
        jQuery(function($){
        	$('.queryFesPlanList').on('click', function(){
        		 var mydata = JSON.stringify({ 
        			 userId:1,
        			 startTime:1006736000000, //1406736000000,
        			 endTime:2406822399000, //,1406822399000
        			 merchantId:738
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>fesPlan/queryFesPlanList.do',
                    data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    }
                });
        	});
        });
    </script>
    
    <script>
        jQuery(function($){
        	$('.queryDailyFesPlanList').on('click', function(){
        		 var mydata = JSON.stringify({ 
        			 userId:1,
        			 startTime:1006736000000,//1406736000000,
        			 endTime:2406822399000//,1406822399000
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>fesPlan/queryDailyFesPlanList.do',
                    data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    }
                });
        	});
        });
    </script>
    
    <script>
        jQuery(function($){
        	$('.queryFesPlanById').on('click', function(){
        		 var mydata = JSON.stringify({ 
        			 planId:44
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>fesPlan/queryFesPlanById.do',
                    data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    }
                });
        	});
        });
    </script>
    
    <script>
        jQuery(function($){
        	$('.updateAbandonFesPlanById').on('click', function(){
        		 var mydata = JSON.stringify({ 
        			 planId:11,
        			 explanation:'放弃测试'
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>fesPlan/abandonFesPlanById.do',
                    data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    }
                });
        	});
        });
    </script>
    
    <script>
        jQuery(function($){
        	$('.updateDelayFesPlanById').on('click', function(){
        		 var mydata = JSON.stringify({ 
        			 planId:19,
        			 explanation:'延期测试',
        			 startTime:1406563200000
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>fesPlan/delayFesPlanById.do',
                    data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert(JSON.stringify(data));
                    }
                });
        	});
        });
    </script>
    
    <script>
        jQuery(function($){
        	$('.updateAbandonFesPlanByIdOfExternal').on('click', function(){
        		 var mydata = JSON.stringify({ 
        			 userId:1,
        			 planId:43,
        			 explanation:'放弃测试'
        		});
                $.ajax({
                    type: "POST",
                    //contentType : 'application/json',
                    url:'<%=basePath%>external/fesPlan/updateAbandonFesPlanById.do?key=001&ciphertext='+mydata,
                    //data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    }
                });
        	});
        });
    </script>
    
    <script>
        jQuery(function($){
        	$('.updateDelayFesPlanByIdOfExternal').on('click', function(){
        		 var mydata = JSON.stringify({ 
        			 userId:1,
        			 planId:19,
        			 explanation:'延期测试',
        			 startTime:1406563200000
        		});
                $.ajax({
                    type: "POST",
                    //contentType : 'application/json',
                    url:'<%=basePath%>external/fesPlan/updateDelayFesPlanById.do?key=001&ciphertext='+mydata,
                    //data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    }
                });
        	});
        });
    </script>
    
    <script>
        jQuery(function($){
        	$('.updateCompleteFesPlanById').on('click', function(){
        		 var mydata = JSON.stringify({ 
        			 planId:44
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>fesPlan/updateCompleteFesPlanById.do?',
                    data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    }
                });
        	});
        });
    </script>
    
    <script>
        jQuery(function($){
        	$('.updateRemindFesPlanById').on('click', function(){
        		 var mydata = JSON.stringify({ 
        			 planId:44,
        			 isRemind:true,
        			 isSendSms:true,
        			 remindTime:1406563200000
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>fesPlan/updateRemindFesPlanById.do?',
                    data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    }
                });
        	});
        });
    </script>
    
    <script>
        jQuery(function($){
        	$('.downloadFiles').on('click', function(){
        		 var mydata = JSON.stringify({ 
        			 fileName:'<%=basePath%>'
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>fesPlan/downloadFiles.do?',
                    data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    }
                });
        	});
        });
    </script>
    
    <script>
        jQuery(function($){
        	$('.exportReport').on('click', function(){
                window.location = '<%=basePath%>fesReport/exportReport.do?startTime='+ 1404144000000+'&endTime=1404230400000';
        	});
        });
    </script>
    
    <script>
        jQuery(function($){
        	$('.exportPlanReport').on('click', function(){
                window.location = '<%=basePath%>fesPlanReport/exportPlanReport.do?startTime='+ 1388505600000+'&endTime='+1421155198000;
        	});
        });
    </script>
</body>
</html>

