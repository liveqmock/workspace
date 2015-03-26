<%@page import="com.yazuo.weixin.util.StringUtil"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.*"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	BusinessVO business=(BusinessVO)request.getAttribute("business");
	List<AutoreplyVO> autoreplyList=business.getAutoreplyList();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自动回复管理</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>js/jquery/jquery.form.js"></script>
<script>
	function addAutoreply(brandId) {
		$("#autoreplyAddBrandId").val(brandId);
		$('#photofile').hide();
		$("#autoreplyDivNotKeyWord").hide();
		$("#settingAutoreply").hide();
		$("#autoreplyDiv").show();
		$("#autoreplyForm").find(".keywordType").attr("disabled", false);
		$("#autoreplyForm").find(".keyword").attr("disabled", false);
		$("#autoreplyForm").find(".replyType").attr("disabled", false);
		$("#autoreplyForm").find(".eventType").attr("disabled", false);
		$("#autoreplyForm").find(".keyword").val("");
		$("#autoreplyForm").find("#replyId").val("");
		if($('#autoreplyAdd\\.replyType').val()=="award"){
			$('#autoreplyAdd\\.replyContent').val('[]');
		}else{
			$('#autoreplyAdd\\.replyContent').val('');
		}
		$("#autoreplyForm").find("#specificType").val("");
		
	}
	function addAutoreplyNotKeyWord(brandId) {
		$("#notKeyAutoreplyAddBrandId").val(brandId);
		$("#autoreplyDivNotKeyWord").find("input[name='replyId']").val('');
		$('#photofile').hide();
		$("#autoreplyDiv").hide();
		$("#settingAutoreply").hide();
		$("#autoreplyDivNotKeyWord").show();
		$("#autoreplyDivNotKeyWord").find(".replyContent").html('');
	}
	
	function settingAutoreply (brandId) {
		$("#brandId").val(brandId);
		$('#photofile').hide();
		$("#autoreplyDivNotKeyWord").hide();
		$("#autoreplyDiv").hide();	
		$("#settingAutoreply").show();
		$("input[name='setting_autoreply_check']").each(function(){
			$(this).attr("disabled", false);
			$(this).attr("checked", false);
		});
		$("#settingAutoreplyBtn").show();
	}

	function addDishesSubmit(id) {
		/* if ($("#autoreplyAdd\\.keyword").val() =="1"||$("#autoreplyAdd\\.keyword").val() =="2"||$("#autoreplyAdd\\.keyword").val() =="3"||$("#autoreplyAdd\\.keyword").val() =="4"||$("#autoreplyAdd\\.keyword").val() =="5") {
			alert("请勿输入系统默认快捷回复编号!");
			return false;
		} */
		if (/^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\d{8}$/.test($("#autoreplyAdd\\.keyword").val())) {
			alert("请勿输入手机号码!");
			return false;
		}
		if (/^[0-9_]+$/.test($("#autoreplyAdd\\.keyword").val())&&$("#autoreplyAdd\\.keyword").val().length==6) {
			alert("请勿输入6位数字!");
			return false;
		}
		if (/^[0-9_]+$/.test($("#autoreplyAdd\\.keyword").val())&&$("#autoreplyAdd\\.keyword").val().length==3) {
			alert("请勿输入3位数字!");
			return false;
		}
	
// 		if ($("#autoreplyAdd\\.replyType").val() == "news" &&( $("#autoreplyAdd\\.pic").val() == ""||$("#autoreplyAdd\\.replyTitle").val() == "")) {
// 			alert("图文模式必须配图,如果URL为空则默认链接商户页");
// 			return false;
// 		}
		if ($("#autoreplyAdd\\.keyword").val() == "" || $("#autoreplyAdd\\.replyContent").val() == "" ) {
			alert("内容不可为空");
			return false;
		}
		if($('#autoreplyAdd\\.replyType').val()=="award"){
			var replyContext = $('#autoreplyAdd\\.replyContent').val().trim();
			if(replyContext.substring(0,1)!="["||replyContext.substring(replyContext.length-1)!="]"){
				alert("刮奖模式的回复内容必需以'['开始，以']'结尾");
				return false;
			}
		}
		var replyId = $("#autoreplyForm").find("input[name='replyId']").val();
		if (replyId !=null && replyId !="") { // 修改的连接
			$("#autoreplyForm").attr("action", "<%=path%>/weixin/updateAutoreply.do");
		}
		$("#autoreplyForm").submit();
		var t=setTimeout("alert('成功');$('#businessModifyDiv').load('<%=path%>/weixin/autoreplyList.do?id=<%=business.getId()%>');",1000);
	}
	function addDishesNotKeyWordSubmit() {
		if (  $("#autoreplyDivNotKeyWord\\.replyContent").val() == "" ) {
			alert("内容不可为空");
			return false;
		}
		var replyId = $("#autoreplyDivNotKeyWord").find("input[name='replyId']").val();
		if (replyId !=null && replyId !="") { // 修改的连接
			$("#autoreplyNotKeyWordForm").attr("action", "<%=path%>/weixin/updateAutoreply.do");
		}
		var brandId=$("#notKeyAutoreplyAddBrandId").val();
		$.ajax({
			type:'post',
			url:'<%=path%>/weixin/checkNotKeyAutoreply.do',
			dataType : "text",
			data : "brandId="+brandId,
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					alert('错误', '提交失败', 'error');
				}
			},
			success : function(json) {
				if (json == "1" &&(replyId.length==0)) {
					alert('已经有非关键字回复了。');
				}else{
					$("#autoreplyNotKeyWordForm").submit();
					var t=setTimeout("alert('成功');$('#businessModifyDiv').load('<%=path%>/weixin/autoreplyList.do?id=<%=business.getId()%>');",1000);
				}
			}
		});
	}
	
	function settingAutoreplySubmit() {
		var array = new Array();
		$("input[name='setting_autoreply_check']:checked").each(function(){
			array.push($(this).val());
		});
		var requestData = {};
		requestData.brandId = $("#brandId").val();
		requestData.keywords = array;
		$.ajax({
			type:'post',
			url:'<%=path%>/weixin/settingAutoreply.do',
			dataType : "json",
			data : requestData,
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					alert('错误', '提交失败', 'error');
				}
			},
			success : function(json) {
				alert(json.message);
				if (json.flag == '1') {
					var t=setTimeout("$('#businessModifyDiv').load('<%=path%>/weixin/autoreplyList.do?id=<%=business.getId()%>');",1000);
				}
			}
		});
	}
	
	function deleteAutoreply(id,brandId,keyword,keywordType,replyType) {
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/deleteAutoreply.do',
			cache : false,
			dataType : "text",
			data : "autoreplyConifgId=" + id
			     +"&autoreplyAddBrandId="+brandId
			     +"&autoreplyAdd.keywordType="+keywordType
			     +"&autoreplyAdd.keyword="+keyword
			     +"&autoreplyAdd.replyType="+replyType,
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					alert('错误', '提交失败', 'error');
				}
			},
			success : function(json) {
				if (json == "1") {
					alert('成功');
					$("#businessModifyDiv").load("<%=path%>/weixin/autoreplyList.do?id=<%=business.getId()%>");
						} else {
							alert('失败');
						}

					}
				});
	}
	function clickFileName(upload_field) {
		var re_text = /\JPG|\jpg|\gif|\PNG/i; //这名比较关键,定义上传的文件类型,允许上传的就写上.
		var filename = upload_field.value; //这个是得到文件域的值
		var newFileName = filename.split('.'); //这是将文件名以点分开,因为后缀肯定是以点什么结尾的.
		newFileName = newFileName[newFileName.length - 1]; //这个是得到文件后缀,因为split后是一个数组所以最后的那个数组就是文件的后缀名.
		/* Checking file type */
		if (newFileName.search(re_text) == -1) { //search如果没有刚返回-1.这个如果newFileName在re_text里没有,则为不允许上传的类型. .
			alert("只支持jpg或者png格式的图片");
			upload_field.form.reset();
			return false;
		}
	}
	function selectMode(model){
		if (model.value=='text') {
			$("#div_tip").show();
		} else {
			$("#div_tip").hide();
		}
		
		if(model.value=="award"){
			$('#autoreplyAdd\\.replyContent').val('[]');
		}else{
			$('#autoreplyAdd\\.replyContent').val('');
		}
	}
	
	function uploadPhoto(id,brandId){
		if(document.getElementById('photofile').style.display=="none"){
			$("#autoreplyDiv").hide();
			$("#autoreplyDivNotKeyWord").hide();
			$('#autoreplyIdspan').empty();
			$('#imageConfigAdd\\.replyTitle').val('');
			$('#imageConfigAdd\\.replyUrl').val('');
			$('#imageConfigAdd\\.descripation').val('');
			$('#imageConfigAdd\\.pic').val('');
			
			$.ajax({
				type : "post",
				url : '<%=path%>/weixin/imageConfigList.do',
				cache : false,
				dataType : "text",
				data : "autoreplyConifgId=" + id,
				     
				error : function(XMLHttpRequest) {// 请求失败时调用函数
					if (processCommErr(XMLHttpRequest)) {
						alert('暂时无法上传图片');
						return false;
					}
				},
				success : function(json) {
					$('#uploadedCount').html(json);
						}
					});
			$('#photofile').show();
		    $('#autoreplyIdspan').html(id);
		    $('#autoreplyConfigId').val(id);
			$('#autoreplyAddBrandId2').val(brandId);
		}else{
			$('#imageConfigAdd\\.replyTitle').val('');
			$('#imageConfigAdd\\.replyUrl').val('');
			$('#imageConfigAdd\\.descripation').val('');
			$('#imageConfigAdd\\.pic').val('');
			$.ajax({
				type : "post",
				url : '<%=path%>/weixin/imageConfigList.do',
				cache : false,
				dataType : "text",
				data : "autoreplyConifgId=" + id,
				     
				error : function(XMLHttpRequest) {// 请求失败时调用函数
					if (processCommErr(XMLHttpRequest)) {
						alert('暂时无法上传图片');
						return false;
					}
				},
				success : function(json) {
					$('#uploadedCount').html(json);
						}
					});
			
			$('#photofile').show();
		    $('#autoreplyIdspan').html(id);
		    $('#autoreplyConfigId').val(id);
			$('#autoreplyAddBrandId2').val(brandId);
		}
	}
	
	function cancel(){
		$('#photofile').hide();
	}
	function titleLeave(){
		if($('#imageConfigAdd\\.replyTitle').val()==""){
			alert("标题为空");
		}
	}
	function urlLeave(){
		if($('#imageConfigAdd\\.replyUrl').val()==""){
			alert("URL为空");
		}
	}
	
	
	function submitPhoto(){
		var count = 10;
		if($('#imageConfigAdd\\.pic').val()==""){
			alert("图片不能为空");
			return false;
		}
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/imageConfigList.do',
			cache : false,
			dataType : "text",
			data : "autoreplyConifgId=" + $('#autoreplyIdspan').html(),
			     
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					alert('暂时无法上传图片');
					return false;
				}
			},
			success : function(json) {
				if(json.trim()==count){
					alert("不能再上传图片了");
					$('#photofile').hide();
					return;
					}else{
						titleLeave();
						urlLeave();
						$('#uploadfile').submit();
						$('#photofile').hide();
						var t=setTimeout("alert('成功');$('#businessModifyDiv').load('<%=path%>/weixin/autoreplyList.do?id=<%=business.getId()%>');",1000);
						
					}
				}
				});
		
		
	}
	
	function deletePhoto(autoreplyId){
		$('#photofile').hide();
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/delImage.do',
			cache : false,
			dataType : "text",
			data : "autoreplyConifgId=" + autoreplyId,
			     
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					alert('删除失败');
					return false;
				}
			},
			success : function(json) {
				if(json.trim()=="1"){
					alert("成功删除图片");
					return;
					}
				}
				});
	}
	
	function updateAutoreply (id) {
		$('#photofile').hide();
		$.ajax({
			url:"<%=path%>/weixin/getAutoreply.do?id="+id,
			type:"post",
			cache : false,
			dataType:"json",
			success:function(responseData) {
				var reply = responseData.data;
				var keyword = reply.keyword;
				if (reply.specificType!=null && reply.specificType==1) { // 菜单动态设置
					$("#autoreplyDivNotKeyWord").hide();
					$("#autoreplyDiv").hide();
					$("#settingAutoreply").show();
					$("#settingAutoreplyBtn").hide();
					$("input[name='setting_autoreply_check']").each(function(){
						$(this).attr("disabled", true);
						$(this).attr("checked", false);
						if($(this).val()==keyword){
							$(this).attr("checked", true);
						}
					});
				} else {
					if(keyword!=null &&keyword.length>0){
						$("#autoreplyDivNotKeyWord").hide();
						$("#autoreplyDiv").show();
						$("#autoreplyDiv").find(".keywordType").val(reply.keywordType);
						$("#autoreplyDiv").find(".keyword").val(reply.keyword);
						$("#autoreplyDiv").find("input[name='replyId']").val(reply.id);
						$("#autoreplyDiv").find(".replyType").val(reply.replyType);
						$("#autoreplyDiv").find(".eventType").val(reply.eventType);
						$("#autoreplyDiv").find(".replyContent").html(reply.replyContent);
						$("#autoreplyDiv").find("#autoreplyAddBrandId").val(reply.brandId);
						$("#autoreplyDiv").find(".keywordType").attr("disabled", true);
						$("#autoreplyDiv").find(".keyword").attr("disabled", true);
						$("#autoreplyDiv").find(".replyType").attr("disabled", true);
						$("#autoreplyDiv").find(".eventType").attr("disabled", true);
						$("#autoreplyDiv").find("#specificType").val(reply.specificType);
					}else{
						$("#autoreplyDiv").hide();
						$("#autoreplyDivNotKeyWord").find("input[name='replyId']").val(reply.id);
						$("#autoreplyDivNotKeyWord").find(".replyType").val(reply.replyType);
						$("#autoreplyDivNotKeyWord").find(".eventType").val(reply.eventType);
						$("#autoreplyDivNotKeyWord").find(".replyContent").html(reply.replyContent);
						$("#autoreplyDivNotKeyWord").find(".eventType").attr("disabled", true);
						$("#autoreplyDivNotKeyWord").show();
					}
				}
				
			}
		});
	}
	
</script>
</head>
<body>
	<table title="自动回复" >
		<tr>
		    <td width="5%">自动回复编号</td>
			<td width="5%">商家ID<br>（brandId）</td>
			<td width="10%">关键词</td>
			<td width="5%">匹配模式</td>
			<td width="40%">回复内容</td>
			<td width="5%">回复类型</td>
			<td width="5%">上传图片</td>
			<td width="5%">删除图片</td>
			<td nowrap="nowrap">
				<a href="javascript:void(0)" onclick="addAutoreply('<%=business.getBrandId().toString()%>')">新增自动回复</a>
				<a href="javascript:void(0)" onclick="addAutoreplyNotKeyWord('<%=business.getBrandId().toString()%>')">非关键词自动回复</a>
				<a href="javascript:void(0)" onclick="settingAutoreply('<%=business.getBrandId().toString()%>')">菜单动态设置</a>
			</td>
		</tr>
		<%
			for (AutoreplyVO autoreply : autoreplyList) {
				if (!autoreply.getIsDelete()) {
		%>
		<tr>
		    <td><%=autoreply.getId() %></td>
			<td><%=autoreply.getBrandId()%></td>
			<td><%=autoreply.getKeyword()%></td>
			<td><%=autoreply.getKeywordType()%></td>
			<td><%=autoreply.getReplyContent()%></td>
			<td><%=autoreply.getReplyType()%></td>
			<td>
			<%
			if(!StringUtil.isNullOrEmpty(autoreply.getReplyType()) && autoreply.getReplyType().trim().equals("news")){
			%>
			<a href='javascript:void(0)'
						onclick="uploadPhoto('<%=autoreply.getId()%>','<%=autoreply.getBrandId()%>')">上传图片</a>
			<% }%>
			</td>
			<td>
			<%
			if(!StringUtil.isNullOrEmpty(autoreply.getReplyType()) && autoreply.getReplyType().trim().equals("news")){
			%>
			<a href='javascript:void(0)'
						onclick="deletePhoto('<%=autoreply.getId()%>')">删除图片</a>
			<% }%>
			</td>
			<td>
				<a href="javascript:void(0)" onclick="deleteAutoreply('<%=autoreply.getId()%>','<%=autoreply.getBrandId()%>','<%=autoreply.getKeyword()%>','<%=autoreply.getKeywordType()%>','<%=autoreply.getReplyType()%>')">删除</a>
				<a href="javascript:void(0)" onclick="updateAutoreply('<%=autoreply.getId()%>')">修改</a>
			</td>
		</tr>
		<%
			}
			}
		%>
	</table>
	<div id="autoreplyDiv"
		style="position: absolute; top: 300px; right: 10px; display: none; width:23%;">
		<form action="<%=path%>/weixin/addAutoreply.do" id="autoreplyForm"
			encType="multipart/form-data" target="xxx" method="post">
			<input type="hidden" name="replyId" id="replyId" value="">
			<table>
				<tr><!-- openId参数必须是最后一位，且整个url在{}中，openId后台会自动赋值 -->
					<td id="div_tip" colspan="2" style="color: red;">回复内容中需要带openId的url,格式如下:{http://[ip]:[端口号]/访问路径.do?所需参数&openId=}</td>
				</tr>
				<tr>
					<td>匹配模式</td>
					<td><select name="autoreplyAdd.keywordType" id="autoreplyAdd.keywordType" class="keywordType">
							<option value="like">相似</option>
							<option value="equals">相等</option>
					</select></td>
				</tr>
				<tr>
					<td>匹配内容</td>
					<td><textarea name="autoreplyAdd.keyword" class="keyword"
							id="autoreplyAdd.keyword" cols="20" rows="5"></textarea></td>
				</tr>
				<tr>
					<td>回复模式</td>
					<td><select name="autoreplyAdd.replyType" class="replyType"
						id="autoreplyAdd.replyType" onchange="selectMode(this)">
						    <option value="text">文本模式</option>
						    <option value="award">刮奖模式</option>
							<option value="news">图文模式</option>
					</select></td>
				</tr>
				<tr>
					<td>事件类型</td>
					<td>
						<select name="autoreplyAdd.eventType" id="autoreplyAdd.eventType" class="eventType">
							<option value="2">其它</option>
							<option value="1">订阅</option>
						</select>
					<!--点阅标示关注时下发的图文/文字，其他标示菜单下发的图文/文字-->
					</td>
				</tr>
				<tr>
					<td colspan="2"  style="font-size:12px;color:red;">
						<span style="display:block; width:100%;">订阅是关注时下发的图文/文字，其它是菜单下发的图文/文字</span>
					</td>
				</tr>
				<tr>
					<td>是否限制会员</td>
					<td>
						<select name="specificType" id="specificType">
							<option value="2">否</option>
							<option value="3">是</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>回复内容</td>
					<td><textarea name="autoreplyAdd.replyContent" class="replyContent"
							id="autoreplyAdd.replyContent" cols="20" rows="5"></textarea></td>
				</tr>
			
				<tr>
				<td><a href="javascript:void(0)" onclick="addDishesSubmit()">提交</a>
				</td>
			</tr>
			</table>
			<input type="hidden" id="autoreplyAddBrandId"
				name="autoreplyAddBrandId"> 
		</form>
	</div>
	
	<div id="autoreplyDivNotKeyWord" style="position: absolute; top: 300px; right: 10px; display: none; width:23%;">
		<form action="<%=path%>/weixin/addNotKeyAutoreply.do" id="autoreplyNotKeyWordForm"
			encType="multipart/form-data" target="xxx" method="post">
			<input type="hidden" name="replyId" id="replyId" value="">
			<table>
				<tr>
					<td>回复模式</td>
					<td><select name="autoreplyAdd.replyType" class="replyType"
						id="autoreplyAdd.replyType">
						    <option value="text">文本模式</option>
							<option value="news">图文模式</option>
					</select></td>
				</tr>
				<tr>
					<td>事件类型</td>
					<td>
						<select name="autoreplyAdd.eventType" id="autoreplyAdd.eventType" class="eventType">
							<option value="2">其它</option>
						</select>
					<!--点阅标示关注时下发的图文/文字，其他标示菜单下发的图文/文字-->
					</td>
				</tr>
				<tr>
					<td>回复内容</td>
					<td><textarea name="autoreplyAdd.replyContent" class="replyContent"
							id="autoreplyAdd.replyContent" cols="20" rows="5"></textarea></td>
				</tr>
			
				<tr>
				<td><a href="javascript:void(0)" onclick="addDishesNotKeyWordSubmit()">提交</a>
				</td>
			</tr>
			</table>
			<input type="hidden" id="notKeyAutoreplyAddBrandId"
				name="notKeyAutoreplyAddBrandId"> 
		</form>
	</div>
	
	<div id="settingAutoreply" style="position: absolute; top: 300px; right: 10px; display: none; width:23%;">
		<form action="<%=path %>/weixin/settingAutoreply.do" method="post"  target="xxx">
			<table>
				<tr>
					<td nowrap="nowrap">匹配菜单key</td>
				</tr>
				<tr>
					<td>
						<input type="hidden" name="brandId" id="brandId" value="">
						<input type="checkbox" name="setting_autoreply_check" id="check1" value="wodezhanghu">我的账户
						<input type="checkbox" name="setting_autoreply_check" id="check2" value="wodeyouhui">我的优惠
						<input type="checkbox" name="setting_autoreply_check" id="check3" value="huiyuanquanyi">会员权益
						<input type="checkbox" name="setting_autoreply_check" id="check4" value="guaguaka">刮刮卡
						<input type="checkbox" name="setting_autoreply_check" id="check5" value="laohuji">老虎机
						<input type="checkbox" name="setting_autoreply_check" id="check6" value="dazhuanpan">大转盘
						<input type="checkbox" name="setting_autoreply_check" id="check7" value="mendian">门店
					</td>
				</tr>
				<tr>
				<td><a href="javascript:void(0)" id="settingAutoreplyBtn" onclick="settingAutoreplySubmit()">提交</a>
				</td>
			</table>
		</form>
	</div>
	
	<div  style="position: absolute; top: 300px; right: 10px; display: none; width:23%;" id="photofile">
	<form id="uploadfile"  target="xxx" action="<%=path%>/weixin/addImageConfig.do" encType="multipart/form-data" target="xxx" method="post">
	
	<table>
		<tr>
			<td colspan="2" style="color: red">url需要带openId时,格式如下:{http://[ip]:[端口号]/访问路径.do?所需参数&openId=}</td>
		</tr>
	          <tr>
	          <td nowrap="nowrap">回复编号</td>
	          <td><span id="autoreplyIdspan"></span></td>
	          </tr>
	          <tr>
	          <td>可上传张数</td>
	          <td>10</td>
	          </tr>
	          <tr>
	          <td>当前已上传</td>
	          <td><span id="uploadedCount"></span></td>
	          </tr>
		     <tr>
					<td>标题</td>
					<td><input type="text" id="imageConfigAdd.replyTitle"
						name="imageConfigAdd.replyTitle"  onblur="titleLeave()"></td>
				</tr>
				<tr>
					<td>URL</td>
					<td><input type="text" id="imageConfigAdd.replyUrl"
						name="imageConfigAdd.replyUrl" onblur="urlLeave()"></td>
				</tr>
				<tr>
				<td>图文描述</td>
				<td><textarea name="imageConfigAdd.descripation"
							id="imageConfigAdd.descripation" cols="20" rows="5"></textarea></td>
				</tr>
				<tr></tr>
				<tr>
					<td>图文模式配图：</td>
					<td><input type="file" name="imageConfigAdd.pic"
						id="imageConfigAdd.pic" onChange="clickFileName(this)"></td>
				</tr> 
	</table>
		<input type="hidden" name="autoreplyAddBrandId" id="autoreplyAddBrandId2"> 
		<input type="hidden" name="autoreplyConfigId" id="autoreplyConfigId">	
	 	<input type="button" value="提交"  onclick= "submitPhoto()"> <input type="button" value="取消"  onclick= "cancel()">
				
	</form>
	</div>
	<iframe name="xxx" style="display: none"></iframe>
</body>
</html>