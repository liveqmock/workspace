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
<title>商户与卡类型设置</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>

<script>
	function saveMres() {
		var cardTypeIds = new Array();
		$("input[name='cardTypeId']:checked").each(function(){
			cardTypeIds.push($(this).val());
		});
		var brandId = $("#brandId").val();
		var requestData = {};
		requestData.brandId = brandId;
		requestData.cardTypeIds = cardTypeIds;
		$.ajax({
			url:"<%=basePath%>/setting/cardRecommend/saveCardType.do",
			type:"post",
			data:requestData, 
			dataType:"json",
			success:function(data){
				alert(data.message);
				if (data.flag) {
					$("#businessModifyDiv").show();
					$("#businessModifyDiv").load("<%=basePath%>/setting/cardRecommend/loadCardType.do?brandId="+brandId);
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
				<c:if test="${empty cardTypeList || fn:length(cardTypeList)==0 }">
					商户未设置卡类型,请先设置...
				</c:if>
				<c:forEach var="c" items="${cardTypeList }" varStatus="i">
					<c:set var="isChecked" value="false"></c:set>
					<c:forEach var="cl" items="${chooseCardList }">
						<c:if test="${c.id == cl }">
							<c:set var="isChecked" value="true"></c:set>	
						</c:if>
					</c:forEach>
					<input type="checkbox" name="cardTypeId" id="cardTypeId${i.index }" value="${c.id }" <c:if test="${isChecked }">checked</c:if>/>
					${c.cardtype }
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td>
				<input type="hidden" name="brandId" id="brandId" value="${brandId }">
				<c:if test="${not empty cardTypeList && fn:length(cardTypeList)>0}">
					<input type="button" value="保存" onclick="javascript:saveMres();">
				</c:if>
			</td>
		</tr>
	</table>
</body>
</html>