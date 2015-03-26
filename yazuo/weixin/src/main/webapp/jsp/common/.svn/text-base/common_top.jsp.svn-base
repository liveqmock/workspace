<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.yazuo.weixin.util.JudgeMenuShow"%>
<script src="${pageContext.request.contextPath}/js/new_member/common.js"></script>
<script type="text/javascript">
$(function(){
	$("#footer li").click(function(){
		$("#footer li").removeClass("footer-on");
		$(this).addClass("footer-on");
	});
});
</script>
	  <div data-role="footer" id="footer" data-position="fixed" data-tap-toggle="false">
    	<ul class="clear-wrap">
        	<li class="footer-on">
        	    <c:set var="otherParam" value="&brandName=${business.title }"></c:set>
        		<c:choose>
        			<c:when test="${dataSource==14 }">
        				<a class="show-page-loading-msg" href="${pageContext.request.contextPath}/weixin/phonePage/fensiCard.do?brandId=${business.brandId }&weixinId=${weixinId }&dataSource=${dataSource}&isCrmMemberUser=${isCrmMemberUser}${otherParam}" target="_self">
		                    <p><span class="footer-icon-member"></span></p>
		                    <p>会员</p>
		                </a>
        			</c:when>
        			<c:otherwise>
        				<a class="show-page-loading-msg" href="${pageContext.request.contextPath}/weixin/phonePage/fensiCard.do?brandId=${business.brandId }&weixinId=${weixinId }${otherParam}" target="_self">
		                    <p><span class="footer-icon-member"></span></p>
		                    <p>会员</p>
		                </a>
        			</c:otherwise>
        		</c:choose>
            	
            </li>
            <!-- 来源为wifi网页，是crm会员显示4个菜单，非crm会员只显示会员标签 -->
            <c:if test="${(empty dataSource && empty isCrmMemberUser) || (dataSource==14 && isCrmMemberUser=='true') }">
	        	<li class="footer-icon-menu">
	            	<%if (JudgeMenuShow.judgeMenu(brandId)) { %>
						<a class="show-page-loading-msg" href="${pageContext.request.contextPath}/weixin/newFun/showStory.do?brandId=${business.brandId }&weixinId=${weixinId }${otherParam}" target="_self">
							<p><span class="footer-icon-menu"></span></p>
			                <p>陪你</p>
		                </a>
					<%} else {%>
		            	<a class="show-page-loading-msg" href="${pageContext.request.contextPath}/weixin/newFun/dishes.do?brandId=${business.brandId }&weixinId=${weixinId }${otherParam}" target="_self">
		                <p><span class="footer-icon-menu"></span></p>
		                <p>菜品</p>
		                </a>
	                <%} %>
	            </li>
	        	<li class="footer-icon-shop">
	            	<a  class="show-page-loading-msg" href="${pageContext.request.contextPath}/weixin/newFun/subbranch.do?brandId=${business.brandId }&weixinId=${weixinId }${otherParam}&r=<%=Math.random()%>" target="_self">
	                    <p><span class="footer-icon-shop"></span></p>
	                    <p>门店</p>
	                </a>
	            </li>
	        	<li class="footer-icon-coupon">
	            	<a class="show-page-loading-msg" href="${pageContext.request.contextPath}/weixin/phonePage/preference.do?brandId=${business.brandId }&weixinId=${weixinId }${otherParam}" target="_self">
	                    <p><span class="footer-icon-coupon"></span></p>
	                    <p>优惠</p>
	                </a>
	            </li>
            </c:if>
        </ul>
    </div>
</html>
