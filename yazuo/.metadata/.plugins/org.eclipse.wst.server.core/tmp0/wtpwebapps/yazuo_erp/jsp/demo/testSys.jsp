<!DOCTYPE html>
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
<body class="page-add-project">
	<div>
		<button type="button" class="saveSysCustomerComplaint btn-link-1">客户投诉添加</button><br>
		<button type="button" class="updateSysCustomerComplaint btn-link-1">客户投诉修改</button><br>
		<button type="button" class="getSysCustomerComplaintById btn-link-1">根据ID查询客户投诉信息</button><br>
		<button type="button" class="querySysCustomerComplaintList btn-link-1">分页查询客户投诉信息</button><br>
		<button type="button" class="querySysDictionaryByTypeList btn-link-1">根据类型列表查询有效的数据字典</button><br>
		<button type="button" class="listSysQuestions btn-link-1">查询填单题目集</button><br>
		<button type="button" class="saveSysDocument btn-link-1">保存填单</button><br>
		<button type="button" class="updateSysDocument btn-link-1">修改填单</button><br>
		<button type="button" class="querySysDocumentById btn-link-1">根据ID查询填单信息</button><br>
		<button type="button" class="queryContact btn-link-1">根据商户ID查询通讯录</button><br>
		<button type="button" class="isExistSysUserMerchant btn-link-1">根据用户ID和商户ID查询前端顾问和商户的关系</button><br>
		<br>
		<button type="button" class="saveSysDatabase btn-link-1">添加资料库</button><br>
		<button type="button" class="updateSysDatabase btn-link-1">修改资料库</button><br>
		<button type="button" class="deleteSysDatabase btn-link-1">删除资料库</button><br>
		<button type="button" class="querySysDatabase btn-link-1">查询资料库列表</button><br>
		<button type="button" class="querySysDatabaseById btn-link-1">按照ID查询资料</button><br>
		<button type="button" class="saveSysAttachmentUser btn-link-1">保存附件-用户关系表（下载记录表）</button><br>
		<a href="<%=basePath%>sysDatabase/download.do?relPath=user-upload-img/sys/databaseFilePath/real/27/37a31c8f-7c36-4d27-a39d-331b0e9d46a1.xlsx&userId=1000000&attachmentId=1000000" class="btn-link-1" target="_blank">下载</a><br>
		<div>
		<br/>
		<%
		 ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(new SysProductOperationVO());
			out.println(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		%>
		</div>
	</div>
	<script>
        jQuery(function($){
        	$('.saveSysCustomerComplaint').on('click', function(){
        		 var mydata = JSON.stringify({
        			  "merchantId" : 0,
        			  "customerComplaintType" : [ "1", "2" ],
        			  "complaintTitle" : "1",
        			  "complaintContent" : "1",
        			  "complaintTime" : 1407321948000,
        			  "sysAttachment" : {
							originalFileName:'1.txt',
							attachmentName:'011c832d-39fe-4417-a934-4fc747fd45eb.txt',
							attachmentSize:'10',
							attachmentSuffix:'txt'
					  }
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>sysCustomerComplaint/saveSysCustomerComplaint.do',
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
        	$('.updateSysCustomerComplaint').on('click', function(){
        		 var mydata = JSON.stringify({
        			  "id" : 21,
        			  "handledBy" : 1,
        			  "handledTime" : 1407321948000,
        			  "handledDescription" : "已解决",
        			  "customerComplaintStatus" : "0"
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>sysCustomerComplaint/updateSysCustomerComplaint.do',
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
        	$('.getSysCustomerComplaintById').on('click', function(){
        		 var mydata = JSON.stringify({
        			  "id" : 3
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>sysCustomerComplaint/getSysCustomerComplaintById.do',
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
        	$('.querySysCustomerComplaintList').on('click', function(){
        		 var mydata = JSON.stringify({
        			 // "id" : 3,
        			  "pageSize" : 10,
        			  "pageNumber" : 1,
        			  //"customerComplaintStatus" : "0",
        			  //"merchantName" : "雅"
        			  "merchantId":2611
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>sysCustomerComplaint/querySysCustomerComplaintList.do',
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
        	$('.querySysDictionaryByTypeList').on('click', function(){
        		 var mydata = JSON.stringify(['00000001','00000002','00000003']);
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>dictionary/querySysDictionaryByTypeList.do',
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
        	$('.listSysQuestions').on('click', function(){
        		 var mydata = JSON.stringify({
        			 documentType : "1"
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>sysQuestion/listSysQuestions.do',
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
        	$('.saveSysDocument').on('click', function(){
        		 var mydata = JSON.stringify({
        				"merchantId": 0, //"商户ID"
        				"documentType":1, //"填单类型"
        				"sysAttachment":{
							originalFileName:'1.txt',
							attachmentName:'011c832d-39fe-4417-a934-4fc747fd45eb.txt',
							attachmentSize:'10',
							attachmentSuffix:'txt',
							attachmentType:'2',
							moduleType:'fes'
					  	},
        				"sysDocumentDtlList":[
        					{
        						"questionId":1, //"题目ID"
        						"questionType":'1', //"问题类型"
        						"title":'请问负责贵品牌的客户服务经理是否有到店进行上线培训？', //"题干"
        						"tip":'', //"提示语"
        						"comment":'', //"意见"
        						"sysDocumentDtlOptionList":[
        							{
        								"optionContent":'是', //"选项内容"
        								"isSelected":'1',//"是否选择"
        								"isOpenTextarea":'0',//是否开启意见框
        								"tip":'',//提示语
        								"comment":''//意见
        							},
        							{
        								"optionContent":'否', //"选项内容"
        								"isSelected":'0',//"是否选择"
            							"isOpenTextarea":'0',//是否开启意见框
            							"tip":'',//提示语
            							"comment":''//意见
        							}
        						]
        					},
        					{
        						"questionId":2, //"题目ID"
        						"questionType":'1', //"问题类型"
        						"title":'请问贵品牌参与上线培训的工作人员有哪些？', //"题干"
        						"tip":'', //"提示语"
        						"comment":'不吃葡萄偏吐葡萄皮', //"意见"
        						"sysDocumentDtlOptionList":[
        							{
        								"optionContent":'店长', //"选项内容"
        								"isSelected":'1',//"是否选择"
            							"isOpenTextarea":'0',//是否开启意见框
            							"tip":'',//提示语
            							"comment":''//意见
        							},
        							{
        								"optionContent":'财务', //"选项内容"
        								"isSelected":'1',//"是否选择"
            							"isOpenTextarea":'0',//是否开启意见框
            							"tip":'',//提示语
            							"comment":''//意见
        							},
        							{
        								"optionContent":'收银', //"选项内容"
        								"isSelected":'1',//"是否选择"
            							"isOpenTextarea":'0',//是否开启意见框
            							"tip":'',//提示语
            							"comment":''//意见
        							},
        							{
        								"optionContent":'其他', //"选项内容"
        								"isSelected":'0',//"是否选择"
            							"isOpenTextarea":'1',//是否开启意见框
            							"tip":'提示语',//提示语
            							"comment":'aaaaaaaaaaaaa'//意见
        							}
        						]
        					}
        				]
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>sysDocument/saveSysDocument.do',
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
        	$('.updateSysDocument').on('click', function(){
       		 var mydata = JSON.stringify({
       			"id":1,//"ID"
 				"merchantId": 0, //"商户ID"
 				"documentType":1, //"填单类型"
 				"sysAttachment":{
						originalFileName:'1.txt',
						attachmentName:'011c832d-39fe-4417-a934-4fc747fd45eb.txt',
						attachmentSize:'10',
						attachmentSuffix:'txt',
						attachmentType:'2',
						moduleType:'fes'
				  	},
 				"sysDocumentDtlList":[
 					{
 						"questionId":1, //"题目ID"
 						"questionType":'1', //"问题类型"
 						"title":'请问负责贵品牌的客户服务经理是否有到店进行上线培训？', //"题干"
 						"tip":'', //"提示语"
 						"comment":'', //"意见"
 						"sysDocumentDtlOptionList":[
 							{
 								"optionContent":'是', //"选项内容"
 								"isSelected":'0',//"是否选择"
 								"isOpenTextarea":'0',//是否开启意见框
 								"tip":'',//提示语
 								"comment":''//意见
 							},
 							{
 								"optionContent":'否', //"选项内容"
 								"isSelected":'1',//"是否选择"
     							"isOpenTextarea":'0',//是否开启意见框
     							"tip":'',//提示语
     							"comment":''//意见
 							}
 						]
 					},
 					{
 						"questionId":2, //"题目ID"
 						"questionType":'1', //"问题类型"
 						"title":'请问贵品牌参与上线培训的工作人员有哪些？', //"题干"
 						"tip":'', //"提示语"
 						"comment":'我爱吃葡萄', //"意见"
 						"sysDocumentDtlOptionList":[
 							{
 								"optionContent":'店长', //"选项内容"
 								"isSelected":'1',//"是否选择"
     							"isOpenTextarea":'0',//是否开启意见框
     							"tip":'',//提示语
     							"comment":''//意见
 							},
 							{
 								"optionContent":'财务', //"选项内容"
 								"isSelected":'1',//"是否选择"
     							"isOpenTextarea":'0',//是否开启意见框
     							"tip":'',//提示语
     							"comment":''//意见
 							},
 							{
 								"optionContent":'收银', //"选项内容"
 								"isSelected":'1',//"是否选择"
     							"isOpenTextarea":'0',//是否开启意见框
     							"tip":'',//提示语
     							"comment":''//意见
 							},
 							{
 								"optionContent":'其他', //"选项内容"
 								"isSelected":'0',//"是否选择"
     							"isOpenTextarea":'1',//是否开启意见框
     							"tip":'提示语',//提示语
     							"comment":'gggggggggg'//意见
 							}
 						]
 					}
 				]
		 		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>sysDocument/updateSysDocument.do',
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
        	$('.querySysDocumentById').on('click', function(){
        		var mydata = JSON.stringify({
        			  "id" : 13
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>sysDocument/querySysDocumentById.do',
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
        	$('.queryContact').on('click', function(){
        		 var mydata = JSON.stringify({
        			  "merchantId" : 243
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>contact/queryContact.do',
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
        	$('.isExistSysUserMerchant').on('click', function(){
        		 var mydata = JSON.stringify({
        			  "userId" : 1,
        			  "merchantId" : 881
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>user/isExistSysUserMerchant.do',
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
        	$('.saveSysDatabase').on('click', function(){
        		 var mydata = JSON.stringify({
        			  "title" : "标题",//标题
        			  "description" : "描述",//描述
        			  "applyCrowdType" : [ "1", "2" ],
        			  "dataType": "9",
        			  "sysAttachment" : {
							originalFileName:'1.txt',
							attachmentName:'011c832d-39fe-4417-a934-4fc747fd4511.txt',
							attachmentSize:'10',
							attachmentSuffix:'txt'
					  }
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>sysDatabase/saveSysDatabase.do',
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
        	$('.updateSysDatabase').on('click', function(){
        		 var mydata = JSON.stringify({
        			  "id" : 5,//ID
        			  "title" : "标题标题",//标题
        			  "description" : "描述描述",//描述
        			  "applyCrowdType" : [ "1", "2" ],
        			  "dataType": "8",
        			  "sysAttachment" : {
							originalFileName:'2.txt',
							attachmentName:'011c832d-39fe-4417-a934-4fc747fd4522.txt',
							attachmentSize:'10',
							attachmentSuffix:'txt'
					  }
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>sysDatabase/updateSysDatabase.do',
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
        	$('.deleteSysDatabase').on('click', function(){
        		 var mydata = JSON.stringify({
        			  "id" : 4//ID
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>sysDatabase/deleteSysDatabase.do',
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
        	$('.querySysDatabase').on('click', function(){
        		 var mydata = JSON.stringify({
        			  "pageSize" : 2,
        			  "pageNumber":1,
        			  "attachmentSuffix":"txt",
        			  "applyCrowdType":"1",
        			  "title":""
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>sysDatabase/querySysDatabase.do',
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
        	$('.querySysDatabaseById').on('click', function(){
        		 var mydata = JSON.stringify({
        			  "pageSize" : 2,
        			  "pageNumber":1,
        			  "id":6
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>sysDatabase/querySysDatabaseById.do',
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
        	$('.saveSysAttachmentUser').on('click', function(){
        		 var mydata = JSON.stringify({
        			 "userId":1,
        			 "attachmentId":1484
        		});
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    url:'<%=basePath%>sysAttachmentUser/saveSysAttachmentUser.do',
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

