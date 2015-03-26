<%@page import="com.yazuo.weixin.util.StringUtil"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%@page import="java.net.URLEncoder"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	List<BusinessVO> businessList=(List<BusinessVO>)request.getAttribute("businessList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CRM-OPENAPI</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script>
	var ctx ="<%=path%>";
</script>
<script type="text/javascript" src="<%=path%>/js/business.js"></script>
</head>
<body>
	<div style="float:left;">
		<input type="hidden" name="currentPage" id="currentPage" value="${currentPage }"/>
		<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }"/>
		<input type="text" name="searchTitle" id="searchTitle" placeHolder="输入商户名称" value="${param.title }"/>
		<input type="text" name="searchBrandId" id="searchBrandId" placeHolder="输入商户id" value="${param.brandId}"/>
		<input type="text" name="searchWeixinId" id="searchWeixinId" placeHolder="输入weixinID" value="${param.weixinId }"/>
		<select name="isCertification" id="isCertification">
			<option value="">是否认证</option>
			<option value="true" <c:if test="${not empty param.isCertification && param.isCertification }">selected</c:if>>是</option>
			<option value="false" <c:if test="${not empty param.isCertification && !param.isCertification }">selected</c:if>>否</option>
		</select>
		<select name="isOpenPayment" id="isOpenPayment">
			<option value="">是否开通支付</option>
			<option value="true" <c:if test="${not empty param.isOpenPayment && param.isOpenPayment }">selected</c:if>>是</option>
			<option value="false" <c:if test="${not empty param.isOpenPayment &&!param.isOpenPayment }">selected</c:if>>否</option>
		</select>
		<input type="button" name="btn_search" value="搜索" onclick="javascript:search();"/>
	</div>
	<div style="margin-right: 30px;text-align: right;margin-bottom: 5px;">
		<c:if test="${not empty businessList && fn:length(businessList)>0 && totalPage>1}">
			<button onclick="javascript:prePage();">上一页</button><button onclick="javascript:nextPage();">下一页</button> 
		</c:if>
	</div>
	<table bordercolor="#ddd" border="0" style="border-collapse: collapse;">
		<tr>
			<td width="10%">微信ID</td>
			<td width="10%">token</td>
			<td width="10%">商家名称</td>
			<td width="10%">商家Id<br/>（brandId）</td>
			<td width="10%">微薄Id</td>
			<td width="10%">商家状态</td>
			<td width="10%">是否认证</td>
			<td width="10%">是否开通支付</td>
			<td nowrap="nowrap">操作 
				<a href="javascript:void(0)" onclick="newBusiness()">新建商家</a> 
				<a href="javascript:void(0)" onclick="createResource()">创建资源</a>
				<c:if test="${sessionScope.USER.supperUser==1 }">
					<a href="javascript:void(0)" onclick="listUser();">用户</a>
				</c:if>
				<a href="javascript:void(0)" onclick="editUser(${sessionScope.USER.userId});">个人信息</a>
			</td>
		</tr>
		<%
		if (businessList !=null && businessList.size()>0) {
			for (BusinessVO business : businessList) {
		%>
		<tr>
			<td><%=business.getWeixinId()%></td>
			<td><%=business.getToken()%></td>
			<td><%=business.getTitle()%></td>
			<td><%=business.getBrandId()%></td>
			<td><%=business.getCompanyWeiboId()%></td>
			<td>
				<%
					if (business.getVoteStatus() == null) {
						out.print("异常");
					} else if (business.getVoteStatus().intValue() == 1) {
						out.print("老商户");
					} else {
						out.print("新商户");
					}
				%>
			</td>
			<td>
				<%
					if (business.getIsCertification()!=null && business.getIsCertification()) {
						out.print("已认证");
					} else {
						out.print("未认证");
					}
				%>
			</td>
			<td><%
				if (business.getIsOpenPayment()!=null &&business.getIsOpenPayment()) {
							out.print("开通支付");
						} else {
							out.print("未开通");
						}
				%>
			</td>
			<td style="padding-bottom: 10px;">
			<input type="button" value="管理者" onclick="manageBoss('<%=business.getId()%>')"> 
<%-- 			<input type="button" value="分店管理" onclick="manageSubbranch('<%=business.getId()%>')">  --%>
<%-- 			<input type="button" value="菜品管理" onclick="manageDishes('<%=business.getId()%>')">  --%>
			<input type="button" value="优惠管理" onclick="managePreference('<%=business.getId()%>')"> 
			<input type="button" value="图片管理" onclick="manageImage('<%=business.getId()%>')"> 
			<input type="button" value="自动回复管理 " onclick="manageAutoreply('<%=business.getId()%>')"> 
			<%-- <input type="button" value="活动管理 " onclick="manageActivityConfig('<%=business.getId()%>')"> 
			<input type="button" value="导入分店信息" onclick="importSubbranch('<%=business.getBrandId()%>','<%=business.getCompanyWeiboId()%>')"> --%> 
			<input type="button" value="删除商家" onclick="deleteBusiness('<%=business.getId()%>')"> 
			<input type="button" value="修改商家" onclick="modifyBusiness('<%=business.getVoteStatus()%>','<%=business.getId()%>','<%=URLEncoder.encode(URLEncoder.encode(business.getWeixinId(), "UTF-8"), "UTF-8")%>','<%=URLEncoder.encode(URLEncoder.encode(business.getToken(), "UTF-8"), "UTF-8")%>','<%=URLEncoder.encode(URLEncoder.encode(business.getTitle(), "UTF-8"), "UTF-8")%>','<%=URLEncoder.encode(URLEncoder.encode(business.getBirthdayMessage(), "UTF-8"), "UTF-8")%>','<%=business.getBrandId()%>','<%=business.getCompanyWeiboId()%>','<%=business.getSalutatory() == null ? "" : URLEncoder.encode(
							URLEncoder.encode(business.getSalutatory(), "UTF-8"), "UTF-8")%>','<%=business.getTagName() == null ? "" : URLEncoder.encode(URLEncoder.encode(business.getTagName(), "UTF-8"), "UTF-8")%>','<%=business.getTagId()%>','<%=business.getIsAllowWeixinMember()%>')">
			<%
				String memberRights = "";
				if (!StringUtil.isNullOrEmpty(business.getMemberRights())) {
					memberRights = URLEncoder.encode(URLEncoder.encode(business.getMemberRights(), "UTF-8"), "UTF-8");
				}
			%>
			<input type="button" value="会员权益" onclick="mangerMerberRight('<%=business.getId()%>','<%=memberRights%>')"> 
			<%-- <input type="button" value="优惠券描述管理" onclick="manageCouponDescribe('<%=business.getBrandId()%>',1)">  --%>
			<%-- <input type="button" value="电子优惠券描述管理" onclick="manageCouponDescribe('<%=business.getBrandId()%>',2)">  --%>
			<%-- <input type="button" value="一键跳转" onclick="manageServiceConfig('<%=business.getBrandId()%>')"> --%>
			<input type="button" value="设置菜单" onclick="createMenu(<%=business.getBrandId()%>)">

			<input type="button" value="品牌故事" onclick="addStory(<%=business.getBrandId()%>)">
			<input type="button" value="接口key值" onclick="addKey(<%=business.getBrandId()%>)">
			<input type="button" value="设置模版" onclick="managerTemplate(<%=business.getBrandId()%>)">
			<input type="button" value="设置商户参数" onclick="managerParams(<%=business.getBrandId()%>)">
			<input type="button" value="更新tokenTime" onclick="managerAccesstokenTime('<%=business.getBrandId()%>')">
			<input type="button" value="设置资源" onclick="settingResource('<%=business.getBrandId()%>')"> 
			<input type="button" value="设置卡推荐" onclick="settingCardRecommend('<%=business.getBrandId()%>')"> 
			</td>
		</tr>
		<%
			}
		}
		%>
	</table>
</body>
</html>