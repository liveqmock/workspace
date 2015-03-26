<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户与资源关系</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>

<script>
	function saveMres() {
		var resourceId = new Array();
		$("input[name='resourceId']:checked").each(function(){
			resourceId.push($(this).val());
		});
		var brandId = $("#brandId").val();
		var requestData = {};
		requestData.brandId = brandId;
		requestData.resourceArray = resourceId;
		$.ajax({
			url:"<%=basePath%>/setting/resource/saveMerchantRes.do",
			type:"post",
			contentType:"application/json",
			data:JSON.stringify(requestData), 
			dataType:"json",
			success:function(data){
				alert(data.message);
				if (data.flag) {
					$("#businessModifyDiv").show();
					$("#businessModifyDiv").load("<%=basePath%>/setting/resource/initMerchantResource.do?brandId="+brandId);
				}
			}
		});
	}
</script>
</head>
<body class="easyui-layout" style="text-align: left">
	<table>
		<tr>
			<td>
				<c:forEach var="r" items="${resourceList }" varStatus="i">
					<c:set var="isChecked" value="false"></c:set>
					<c:forEach var="mr" items="${merchantResList }">
						<c:if test="${r.id == mr.resource_id }">
							<c:set var="isChecked" value="true"></c:set>	
						</c:if>
					</c:forEach>
					<input type="checkbox" name="resourceId" id="resourceId${i.index }" value="${r.id }" <c:if test="${isChecked }">checked</c:if>/>
					${r.name }
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td>
				<input type="hidden" name="brandId" id="brandId" value="${brandId }">
				<c:if test="${not empty resourceList && fn:length(resourceList)>0}">
					<input type="button" value="保存" onclick="javascript:saveMres();">
				</c:if>
			</td>
		</tr>
	</table>
</body>
</html>