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
		<button type="button" class="queryPosition btn-link-1">查询有效的职位信息</button><br>
		<button type="button" class="saveTraPositionCourse btn-link-1">课程关联职位</button><br>
		<button type="button" class="queryStudentListToBeAllocated btn-link-1">查询未分配老师的学生列表</button><br>
		<button type="button" class="saveTraTeacherStudent btn-link-1">添加老师-学生关系</button><br>
		<button type="button" class="updateRecord btn-link-1">更新进度ID</button><br>
		<button type="button" class="updateLearningProcess btn-link-1">更新学习进度表课程状态</button><br>
		
	</div>
	<script>
        jQuery(function($){
        	$('.queryPosition').on('click', function(){
        		var mydata = JSON.stringify({ 
        			 courseId : 118
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>studentManagement/queryPosition.do',
					data : mydata,
					dataType : "json",
					success : function(data) {
						alert(JSON.stringify(data));
					}
				});
			});
		});
	</script>
	
	<script>
        jQuery(function($){
        	$('.saveTraPositionCourse').on('click', function(){
        		var mydata = JSON.stringify([{
        			"positionId":30,
        			"courseId":118
        		},{
        			"positionId":9999998,
        			"courseId":9999999
        		}]);
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>traPositionCourse/saveTraPositionCourse.do',
					data : mydata,
					dataType : "json",
					success : function(data) {
						alert(JSON.stringify(data));
					}
				});
			});
		});
	</script>
	
	<script>
        jQuery(function($){
        	$('.queryStudentListToBeAllocated').on('click', function(){
        		var mydata = JSON.stringify({
        			pageSize:10,
        			pageNumber:1,
        			userName:null,
        			tel:null
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>studentManagement/queryStudentListToBeAllocated.do',
					data : mydata,
					dataType : "json",
					success : function(data) {
						alert(JSON.stringify(data));
					}
				});
			});
		});
	</script>
	
	<script>
        jQuery(function($){
        	$('.saveTraTeacherStudent').on('click', function(){
        		var mydata = JSON.stringify({
        			studentIdList:[999999999,999999998],
        			teacherId:999999997
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>studentManagement/saveTraTeacherStudent.do',
					data : mydata,
					dataType : "json",
					success : function(data) {
						alert(JSON.stringify(data));
					}
				});
			});
		});
	</script>
	
	<script>
        jQuery(function($){
        	$('.updateRecord').on('click', function(){
        		var mydata = JSON.stringify({ 
        			 
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>studentManagement/updateRecord.do',
					data : mydata,
					dataType : "json",
					success : function(data) {
						alert(JSON.stringify(data));
					}
				});
			});
		});
	</script>
	
	<script>
        jQuery(function($){
        	$('.updateLearningProcess').on('click', function(){
        		var mydata = JSON.stringify({ 
        			 
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>studentManagement/updateLearningProcess.do',
					data : mydata,
					dataType : "json",
					success : function(data) {
						alert(JSON.stringify(data));
					}
				});
			});
		});
	</script>
</body>
</html>

