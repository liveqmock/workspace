<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%@page import="java.net.URLEncoder"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	List<ServiceConfigVO> serviceConfigList=(List<ServiceConfigVO>)request.getAttribute("serviceConfigList");
	List<ParamConfigVO> paramConfigList=(List<ParamConfigVO>)request.getAttribute("paramConfigList");
	Integer brandId=Integer.parseInt(request.getParameter("brandId"));
	ServiceConfigVO serviceConfig=null;
	if(serviceConfigList!=null&&serviceConfigList.size()>0){
		serviceConfig=serviceConfigList.get(0);		
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>一键跳转配置</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>

<script>
	//添加参数列表的可编辑行
	function addParamConfigInput() {
		$("#paramListTable")
				.append(
						"<tr>"
								+ "<td><input name='param_name' type='text' style='width: 120px; height: 18px' /></td>"
								+ "<td><input name='param_type' type='text' style='width: 120px; height: 18px' /></td>"
								+ "<td><input name='param_description' type='text' style='width: 120px; height: 18px' /></td>"
								+ "<td><select name='is_default' style='width: 50px; height: 24px'><option value='0'>否</option><option value='1'>是</option></select></td>"
								+ "<td><input name='param_value' type='text' style='width: 120px; height: 18px' /></td>"
								+ "<td><input type='button' value='删除' class='deleteParamConfigInputBtn' /></td>"
								+ "</tr>");
		eachId();
	}

	//对参数列表行id重新赋值
	function eachId() {
		$("#paramListTable tr").each(function(i) {
			this.id = "p_" + i;
		});
	}

	//点击class=deleteParamConfigInputBtn的按钮，删除本行
	$(".deleteParamConfigInputBtn").live("click", function() {
		var sId = $(this).parents("tr").attr("id");
		$("#" + sId).remove();
	});
	
	//删除跳转服务
	function deleteServiceConfig(brandId){
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/serviceConfig/deleteServiceConfig.do?',
			cache : false,
			dataType : "text",
			data : "brandId=" + brandId,
			error : function(XMLHttpRequest) {
				alert('删除失败');
			},
			success : function(json) {
				if (json == "1") {
					alert('删除成功');
					$("#businessModifyDiv").load("<%=path%>/weixin/serviceConfig/serviceConfig.do?brandId="+brandId);
				} else {
					alert('删除失败');
				}
			}
		});
	}
	
	//检查入参是否为空
	function checkParam(value,name){
		if(value == undefined || value == "" || value == null){  
		     alert("请输入"+name); 
		     return true;
		}  
	}
	
	//添加跳转服务
	function saveServiceConfig(insertOrUpdateFlag){
		
		var brandId = $("#brandId").val();//商户ID
		var serviceId = $("#serviceId").val();//服务ID
		
		//服务配置
		var url = $("#url").val();//URL
		var content1 = $("#content1").val();//内容1
		var content2 = $("#content2").val();//内容2
		var content3 = $("#content3").val();//内容3
		
		if(checkParam(url,"URL")||checkParam(content2,"内容2")){
			return;
		}
		
		var serviceConfig={
			url      : url,
			brandId  : brandId,
			content1 : content1,
			content2 : content2,
			content3 : content3
		};
		
		//参数配置
		var paramConfigs = new Array();
		var flag=false;
		$("#paramListTable tr").each(function(i) {
			
			//第0行为标题行，不进行处理
			if(i>0){
				var param_name = $(this).find("input:eq(0)").val();
				var param_type = $(this).find("input:eq(1)").val();
				var param_description = $(this).find("input:eq(2)").val();
				var is_default = $(this).find("select option:selected").val();
				var param_value = $(this).find("input:eq(3)").val();
				
				// 校验入参是否为空
				if(checkParam(param_name,"参数名称") || checkParam(param_type,"参数类型") 
						|| checkParam(param_description,"参数描述")	|| checkParam(is_default,"是否为空")){
					flag = true;
					return;
				}
				
				// 校验默认值是否为空
				if("1" == is_default && checkParam(param_value,"默认值")){
					flag = true;
					return;
				}
				
				// 组织参数配置列表
				paramConfigs[i-1]={
					param_name:param_name,
					param_type:param_type,
					param_description:param_description,
					is_default:is_default,
					param_value:param_value
				};
			}
		});

		if(flag){
			return;
		}
		
		var parameters = [serviceConfig,paramConfigs];
		
		if("0"==insertOrUpdateFlag){//添加
			$.ajax({
				type : "POST",
				url : '<%=path%>/weixin/serviceConfig/addServiceConfig.do',
				cache : false,
				data : "params="+JSON.stringify(parameters),
				dataType : "json",
				success : function(json) {
					if (json == "1") {
						alert('保存成功');
						$("#businessModifyDiv").load("<%=path%>/weixin/serviceConfig/serviceConfig.do?brandId="+brandId);
					} else {
						alert('保存失败');
					}
				},
				error : function(XMLHttpRequest) {
					alert('保存失败');
				}
			});
		}else if("1"==insertOrUpdateFlag){//修改
			$.ajax({
				type : "POST",
				url : '<%=path%>/weixin/serviceConfig/updateServiceConfig.do',
				cache : false,
				data : "params="+JSON.stringify(parameters)+"&serviceId="+serviceId,
				dataType : "json",
				success : function(json) {
					if (json == "1") {
						alert('保存成功');
						$("#businessModifyDiv").load("<%=path%>/weixin/serviceConfig/serviceConfig.do?brandId="+brandId);
					} else {
						alert('保存失败');
					}
				},
				error : function(XMLHttpRequest) {
					alert('保存失败');
				}
			});
		}
	}
</script>
</head>
<body>

	<%
		if (serviceConfig != null) {//修改
	%>
	<form action="#">
		<!-- 跳转服务配置 -->
		<table width="100%" border="0" cellspacing="1" cellpadding="0">
			<tr>
				<td width="20%" style="text-align: right">URL：</td>
				<td width="80%"><input id="url" name="url" type="text" value="<%=serviceConfig.getUrl()%>" style="width: 400px; height: 18px" /></td>
			</tr>
			<tr>
				<td width="20%" style="text-align: right">内容1：</td>
				<td><input id="content1" name="content1" type="text" value="<%=serviceConfig.getContent1()%>" style="width: 400px; height: 18px" /></td>
			</tr>
			<tr>
				<td width="20%" style="text-align: right">内容2：</td>
				<td><input id="content2" name="content2" type="text" value="<%=serviceConfig.getContent2()%>" style="width: 400px; height: 18px" /></td>
			</tr>
			<tr>
				<td width="20%" style="text-align: right">内容3：</td>
				<td><input id="content3" name="content3" type="text" value="<%=serviceConfig.getContent3()%>" style="width: 400px; height: 18px" /></td>
			</tr>
			<tr>
				<td width="20%" style="text-align: right"></td>
				<td>
					<div>
						<p></p>
						<input type="button" value="添加参数" onclick="addParamConfigInput()" />
					</div> <!-- 服务参数配置 -->
					<table id="paramListTable" width="700px" border="0" cellspacing="1" cellpadding="0" align="left">
						<tr id="p_0">
							<td width="18%">参数名称</td>
							<td width="18%">参数类型</td>
							<td width="18%">参数描述</td>
							<td width="10%">是否默认</td>
							<td width="18%">默认值</td>
							<td width="18%">操作</td>
						</tr>
						<%
							if (paramConfigList != null && paramConfigList.size() > 0) {
									for (int i = 0; i < paramConfigList.size(); i++) {
										ParamConfigVO paramConfig = paramConfigList.get(i);
										String temp = "p_" + (i + 1);
						%>
						<tr id="<%=temp%>">
							<td><input name="param_name" type="text" value="<%=paramConfig.getParamName()%>" style="width: 120px; height: 18px" /></td>
							<td><input name="param_type" type="text" value="<%=paramConfig.getParamType()%>" style="width: 120px; height: 18px" /></td>
							<td><input name="param_description" type="text" value="<%=paramConfig.getParamDescription()%>" style="width: 120px; height: 18px" /></td>
							<td><select name="is_default" style="width: 50px; height: 24px">
									<%
										String isDefault = paramConfig.getIsDefault();
													if ("0".equals(isDefault)) {
									%>
									<option value="0" selected="selected">否</option>
									<option value="1">是</option>
									<%
										} else if ("1".equals(isDefault)) {
									%>
									<option value="0">否</option>
									<option value="1" selected="selected">是</option>
									<%
										}
									%>
							</select></td>
							<td><input name="param_value" type="text" value="<%=paramConfig.getParamValue()%>" style="width: 120px; height: 18px" /></td>
							<td><input type="button" value="删除" class="deleteParamConfigInputBtn" /></td>
						</tr>
						<%
							}
								}
						%>
					</table>
				</td>
			</tr>
			<tr>
				<td width="20%" height="25px" style="text-align: right"></td>
				<td></td>
			</tr>
			<tr>
				<td width="20%" style="text-align: right"></td>
				<td><input id="saveBtn" type="button" value="保存" onclick="saveServiceConfig(1)" /> <input type="button" value="删除服务"
					onclick="deleteServiceConfig(<%=serviceConfig.getBrandId()%>)" /></td>
			</tr>
			<input id="brandId" name="brandId" type="hidden" value="<%=brandId%>" />
			<input id="serviceId" name="serviceId" type="hidden" value="<%=serviceConfig.getServiceId()%>" />
		</table>
	</form>
	<%
		} else {//创建
	%>
	<form action="#">
		<!-- 跳转服务配置 -->
		<table width="100%" border="0" cellspacing="1" cellpadding="0">
			<tr>
				<td width="20%" style="text-align: right">URL：</td>
				<td width="80%"><input id="url" name="url" type="text" style="width: 400px; height: 18px" /></td>
			</tr>
			<tr>
				<td width="20%" style="text-align: right">内容1：</td>
				<td><input id="content1" name="content1" type="text" style="width: 400px; height: 18px" /></td>
			</tr>
			<tr>
				<td width="20%" style="text-align: right">内容2：</td>
				<td><input id="content2" name="content2" type="text" style="width: 400px; height: 18px" /></td>
			</tr>
			<tr>
				<td width="20%" style="text-align: right">内容3：</td>
				<td><input id="content3" name="content3" type="text" style="width: 400px; height: 18px" /></td>
			</tr>
			<tr>
				<td width="20%" style="text-align: right"></td>
				<td>
					<div>
						<p></p>
						<input type="button" value="添加参数" onclick="addParamConfigInput()" />
					</div> <!-- 服务参数配置 -->
					<table id="paramListTable" width="700px" border="0" cellspacing="1" cellpadding="0" align="left">
						<tr id="p_0">
							<td width="18%">参数名称</td>
							<td width="18%">参数类型</td>
							<td width="18%">参数描述</td>
							<td width="10%">是否默认</td>
							<td width="18%">默认值</td>
							<td width="18%">操作</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width="20%" height="25px" style="text-align: right"></td>
				<td></td>
			</tr>
			<tr>
				<td width="20%" style="text-align: right"></td>
				<td><input id="saveBtn" type="button" value="保存" onclick="saveServiceConfig(0)" /></td>
			</tr>
			<input id="brandId" name="brandId" type="hidden" value="<%=brandId%>" />
		</table>
	</form>
	<%
		}
	%>
</body>
</html>