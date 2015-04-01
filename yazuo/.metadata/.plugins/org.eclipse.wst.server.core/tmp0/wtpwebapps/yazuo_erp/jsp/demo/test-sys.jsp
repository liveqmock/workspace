<!DOCTYPE html>
<%@page import="com.yazuo.erp.system.vo.SysUserVO"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.yazuo.erp.filter.SessionUserList"%>
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
	<form action="<%=basePath%>train/exam/saveAudio.do" method="POST" enctype="multipart/form-data">
	    examPaperId:<input type="text" value="30" name="examPaperId"><br/>
	    pptId:<input type="text" value="1" name="pptId"/>
	    hours:<input type="text" value="1" name="hours"/>
	    pptfile:<input type="file" name="pptfile"/>
	    <input type="submit" value="submit">
	</form>
  <hr/>
  <br/>
  <script>
	  function compare(a,b){
	           if(a.stepId > b.stepId){
	               return 1;
	           }else{
	        	   return -1;
	           } 
	       }
	  var listSteps = [];
	  for(var i=0; i<5; i++){
		  var a = 7;
		  if(i==1)a = 2;
		  if(i==2)a = 1;
		  if(i==3)a = 10;
		  if(i==4)a = 9;
	        var step= {};
//	        step.stepId = (9-i);
	        step.stepId = a;
	        listSteps.push(step);
	   }
	  console.log(listSteps );
	  listSteps.sort(compare);
	  console.log(listSteps );
	  var result = "";
	  for(var i=0; i<listSteps.length; i++){
		  result +=(listSteps[i].stepId + " ");
	  }
	  alert(result);
  </script>
  测试2014-8-29 16:04:10
  上传方案洽谈，实体卡制作卡样文件等对应的接口测试
  <form action="<%=basePath%>fesOnlineProcess/uploadFiles.do" method="POST" enctype="multipart/form-data">
	    processId:<input type="text" value="25" name="processId"/>
	    myfiles:<input type="file" name="myfile"/>
	    <input type="submit" value="submit">
	</form>
	  <br/>
  <div>
	  <div>添加用户MAC地址</div>
	  <label>输入用户手机号：</label>
	  <input type="text" name="tel" class="input-tel" />
	  <label>输入MAC地址：</label>
	  <input type="text" name="mac" style="width: 300px;" class="input-mac" />
  	  <input type="button" class="addMac" value="添加"/>
  	  <br>
    <%

		Enumeration<Object> enums = SessionUserList.getInstance().getUserList();
	    while(enums.hasMoreElements()){
	    	SysUserVO user = (SysUserVO)enums.nextElement();
	    	out.println("<br/>测试: "+user.getSessionId()+" "+user.getTel()+"<br/>");
	    }
    %>
    <br>
  </div>
  <hr/>
	<div>
     <button type="button" class="find-sub btn-link-1">查找下属</button>
     <button type="button" class="save-train btn-link-1">保存培训计划</button>
     <button type="button" class="save-resource btn-link-1">保存资源</button>
     <button type="button" class="search-resource btn-link-1">查询资源</button>
     <hr/>
     
     <button type="button" class="save-group btn-link-1">保存公司组织架构 </button>
     <button type="button" class="search-group btn-link-1">查询公司组织架构 </button>
     <hr/>
     <button type="button" style="visibility: show;" class="verifyMac btn-link-1">mac地址测试</button>
     <hr/>
     <button type="button" style="visibility: show;" class="userPrevilege btn-link-1">测试用户拥有的资源</button>
     <hr/>
     <button type="button" style="visibility: show;" class="test btn-link-1">测试session</button>
	</div>
	<br>
     <button type="button" style="visibility: show;" class="logout btn-link-1">退出</button><span class='text-log-out'/>
	
        <!-- 嵌入插件获得本机MAC地址 -->
        <embed type="application/npsoftpos" width=0 height=0 id="SoftPos"/>
        <div class='append'>+++ </div>
    <script>
    /*
       var softPos = document.getElementById('SoftPos');
	    for(i=0; i < 150; i++){
	    	var b;
	    	if(i<10){
	    		b =i ;
	    	}
	        if (softPos.VerifyMAC(i) !=0) {
	        	$('.append').append(softPos.VerifyMAC(i)+'@');
	        }
	    }
	   alert(5);
	   */
		//alert(a);
        jQuery(function($){
        	$('.save-train').on('click', function(){
                var mydata = JSON.stringify({ "address": "111啊时代发生地方",
						                	"beginTime": 1405937603000,
						                	"content": "111阿桑地方",
						                	"endTime": 1405937603000,
						                	"id": 1,
						                	"processId":31, 
						                	"trainer": "1阿桑地方"
                                             });
                $.ajax({
                    type: "post",
                    contentType : 'application/json',
                    url:'<%=basePath%>fesOnlineProcess/saveFesTrainPlan.do',
                    data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert(data);
                    }
                });
        	});
        	$('.save-resource').on('click', function(){
                var mydata = JSON.stringify({ parentId: 0,
                                              treeCode: "ddddddddd",
                                              parentTreeCode: "ddddddddd",
                                              resourceType: "2",
                                              resourceName: "ddddddddd",
                                              resourceUrl: "url",
                                              sort: 2,
                                              insertTime: "2010-6-6",
                                              insertBy: 1,
                                              updateBy: 1,
                                              updateTime: "2010-6-6",
                                              remark: "ddddddddd"
                                             }              
                                         );
                $.ajax({
                    type: "post",
                    contentType : 'application/json',
                    url:'<%=basePath%>resource/saveSysResource.do',
                    data:mydata,
                    dataType:"json",
                    success:function(data){
                    }
                });
        	});
        	$('.find-sub').on('click', function(){
                var mydata =  JSON.stringify({baseUserId: 1});
                $.ajax({
                    type: "post",
                    contentType : 'application/json',
                    url:'<%=basePath%>group/getSubordinateEmployees.do',
                    data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    }
                });
        	});
        	$('.search-resource').on('click', function(){
                var mydata =  JSON.stringify({level: "1", userId: 1});
                $.ajax({
                    type: "post",
                    contentType : 'application/json',
                    url:'<%=basePath%>resource/getSysResourcesByLevel.do',
                    data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    }
                });
        	});

        	$('.save-group').on('click', function(){
                var mydata = JSON.stringify({ parentId: 0,
                                              treeCode: "ddddddddd",
                                              parentTreeCode: "ddddddddd",
                                              groupName: "ddddddddd",
                                              isEnable: "0",
                                              insertTime: "2010-6-6",
                                              insertBy: 1,
                                              updateBy: 1,
                                              updateTime: "2010-6-6",
                                              remark: "ddddddddd"
                                             }              
                                         );
                $.ajax({
                    type: "post",
                    contentType : 'application/json',
                    url:'<%=basePath%>group/saveSysGroup.do',
                    data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert(JSON.stringify(data));
                    }
                });
        	});
        	$('.search-group').on('click', function(){
                var mydata =  JSON.stringify({parentId: 0});
                $.ajax({
                    type: "post",
                    contentType : 'application/json',
                    url:'<%=basePath%>group/getSysGroupsByParentId.do',
                    data:mydata,
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    }
                });
        	});
        	$('.verifyMac').on('click', function(){
                var mydata =  JSON.stringify({
                		tel: "test"//,
                		//mac: '54-35-30-0D-88-88'
                	});
                $.ajax({
                    type: "post",
                    contentType : 'application/json',
                    url:'<%=basePath%>login/verifyMac.do',
                    data:mydata,
                    dataType:"json",
                    success:function(responseData){
                    	for(var i=0;i<responseData.data.length;i++){
                    		//alert( responseData.data[i].mac)
                    	}
                    	//alert( JSON.stringify(responseData));
                    }
                });
        	});
        	$('.test').on('click', function(){
                var mydata =  JSON.stringify({
                		test : '2012-3-3'
                	});
                $.ajax({
                    type: "post",
                    contentType : 'application/json',
                    url:'<%=basePath%>login/getSessionUser.do',
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    	
                    }
                });
        	});
        	$('.userPrevilege').on('click', function(){
                $.ajax({
                    type: "post",
                    contentType : 'application/json',
                    url:'<%=basePath%>login/getAllNodeForCurrentUser.do',
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    	
                    }
                });
        	});
        	$('.addMac').on('click', function(){
        		var tel = $.trim($('.input-tel').val());
        		var mac = $.trim($('.input-mac').val());
        		var macs = mac.split(',');
                $.ajax({
                    type: "post",
                    contentType : 'application/json',
                    url:'<%=basePath%>login/addMacToUser/'+tel+'/'+macs+'.do',
                    dataType:"json",
                    success:function(data){
                    	alert( JSON.stringify(data));
                    }
                });
        	});
        	$('.logout').on('click', function(){
                $.ajax({
                    type: "post",
                    contentType : 'application/json',
                    url:'<%=basePath%>login/removeSessionUser.do',
                    dataType:"json",
                    success:function(data){
                    	$('.text-log-out').text('退出成功');
                    }
                });
        	});
        	//$('.btn-link-1').hide();
        });//end jquery ready.
    </script>
</body>
</html>

