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
		<button type="button" class="queryCoursewareList btn-link-1">老学员学习管理列表</button><br>
		<button type="button" class="queryInfo btn-link-1">发起学习-查询</button><br>
		<button type="button" class="executeTermBegin btn-link-1">发起学习</button><br>
		<button type="button" class="queryOldStaffList btn-link-1">学员管理-列表</button><br>
		<button type="button" class="executeRemove btn-link-1">移除学员</button><br>
		<button type="button" class="executeDelay btn-link-1">延时学习</button><br>
		<br>
		<button type="button" class="queryMemberList btn-link-1">组员管理列表</button><br>
		<button type="button" class="queryLearningProcessList btn-link-1">查看学习情况</button><br>
		<button type="button" class="queryToDoListByLeaderId btn-link-1">根据领导ID查询待办事项</button><br>
		<button type="button" class="queryToDoListCountByLeaderId btn-link-1">根据领导ID查询待办事项数量</button><br>
		<br>
		<button type="button" class="getAllCoursewares btn-link-1">学生-查询课件列表</button><br>
		<br>
		<button type="button" class="startExam btn-link-1">开始考试</button><br>
		<button type="button" class="startPptExam btn-link-1">开始考试</button><br>
		<button type="button" class="getPracticeQuestionByCoursewareId btn-link-1">根据课件ID查询实操题</button><br>
	</div>
	
	<script>
        jQuery(function($){
        	$('.queryCoursewareList').on('click', function(){
        		var mydata = JSON.stringify({ 
        			pageSize:10,
        			pageNumber:1,
        			coursewareName : '课件'
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>oldStaffManagement/queryCoursewareList.do',
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
        	$('.queryInfo').on('click', function(){
        		var mydata = JSON.stringify({ 
        			pageSize:1000,
        			pageNumber:1,
        			queryType : '3',
        			flag : '0',
        			coursewareId : 204
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>oldStaffManagement/queryInfo.do',
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
        	$('.executeTermBegin').on('click', function(){
        		var mydata = JSON.stringify({ 
        			conditionType : '1',// 1-按照部门，2-按照职位，3-按照员工
        			idList : [108,109],// id列表（组ID/职位ID/员工ID），具体类型根据conditionType判定
        			flag : '0',// 是否排除已学习过的学员标志（0-不排除，1-排除）
        			coursewareId : 235//课件ID
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>oldStaffManagement/executeTermBegin.do',
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
        	$('.queryOldStaffList').on('click', function(){
        		var mydata = JSON.stringify({ 
        			pageSize:2,
        			pageNumber:1,
        			coursewareId : 235,
        			courseStatus:'',
        			userName:'',
        			tel:''
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>oldStaffManagement/queryOldStaffList.do',
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
        	$('.executeRemove').on('click', function(){
        		var mydata = JSON.stringify({ 
        			learningProgressIdList : [115,99999999999]
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>oldStaffManagement/executeRemove.do',
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
        	$('.executeDelay').on('click', function(){
        		var mydata = JSON.stringify({ 
        			learningProgressId : 115,
        			endDate : 1413043200
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>oldStaffManagement/executeDelay.do',
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
        	$('.queryMemberList').on('click', function(){
        		var mydata = JSON.stringify({ 
        			pageSize:10,
        			pageNumber:1,
        			baseUserId : 1,
        			subUserName:''
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>teamMember/queryMemberList.do',
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
        	$('.queryLearningProcessList').on('click', function(){
        		var mydata = JSON.stringify({ 
        			pageSize:10,
        			pageNumber:1,
        			studentId:1
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>teamMember/queryLearningProcessList.do',
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
        	$('.getAllCoursewares').on('click', function(){
                $.ajax({
                    type: "get",
                    contentType : 'application/json',
                    url:'<%=basePath%>/train/student/getAllCoursewares.do?isOldStaff=1',
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
        	$('.queryToDoListByLeaderId').on('click', function(){
        		var mydata = JSON.stringify({ 
        			pageSize:10,
        			pageNumber:1,
        			baseUserId:173
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>oldStaffManagement/queryToDoListByLeaderId.do',
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
        	$('.queryToDoListCountByLeaderId').on('click', function(){
        		var mydata = JSON.stringify({ 
        			pageSize:10,
        			pageNumber:1,
        			baseUserId:173
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>oldStaffManagement/queryToDoListCountByLeaderId.do',
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
        	$('.startExam').on('click', function(){
                $.ajax({
                    type: "get",
                    contentType : 'application/json',
                    url:'<%=basePath%>train/exam/startExam.do?examPaperId=577',
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
        	var mydata = JSON.stringify({ 
        		examPaperId:847,
        		sessionId:'FC9E01065D7A99D1FD10D4D5D4CDDC58'
    		});
        	$('.startPptExam').on('click', function(){
                $.ajax({
                    type: "post",
                    contentType : 'application/json',
                    url:'<%=basePath%>train/exam/startPptExam.do',
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
        	$('.getPracticeQuestionByCoursewareId').on('click', function(){
        		var mydata = JSON.stringify({ 
        			coursewareId:246,
        			pageNumber:1,
        			pageSize:10000,
        			question:""
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>rule/getPracticeQuestionByCoursewareId.do',
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

