<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://epms.tiros.com.cn/restricttag" prefix="restrict"%>
<%@ page isELIgnored="false" %>
<%
String path1 = request.getContextPath();
String basePath1 = request.getScheme() + "://"+ request.getServerName() + path1;
%>
<script type="text/javascript">
	function navLocation(url) {
		var url = url.replace("{path}", "<%=basePath1%>").replace("{brandId}", "${brandId}").replace("{weixinId}", "${weixinId}").replace("{appId}", "${appId}");
		window.location.href = url;
	}
	function editMemberInfo() {
		$("#head_form").attr("action", ctx + "/member/birth/queryMemberInfo.do");
    	$("#head_form")[0].submit();
	}
	// 查看推送广告
    function lookAdviser(brandId, weixinId) {
    	window.location.href = ctx+"/merchantWifi/pushPage.do?brandId="+brandId+"&weixinId="+weixinId;
    }
	
</script>
  <c:if test="${not empty couponArray && fn:length(couponArray)>0}">
       	<section class="member-card-tips">
	    	<p class="success-img"><img src="<%=path1 %>/images/member/card-success-icon.png" /></p>
			<p class="card-tips-word">恭喜，您已成功连接 ${business.title } WIFI，享受WIFI会员待遇。
				您已获赠 
				<c:forEach var="c" items="${couponArray }">
					${c.name }一张;
				</c:forEach>
			</p>
		</section>
	</c:if>
	<form id="head_form" action="" method="post">
	  <input type="hidden" name="membershipId" id="membershipId" value="${member.membershipId }">
	  <input type="hidden" name="gender" id="gender" value="${member.gender }">
	  <input type="hidden" name="birthType" id="birthType" value="${member.birthType }">
	  <input type="hidden" name="name" id="name" value="${member.name }">
	  <input type="hidden" name="birthDay" id="birthDay" value="<fmt:formatDate value='${member.birthday }' pattern='yyyy-MM-dd'/>">
	  <input type="hidden" name="phoneNo" id="phoneNo" value="${member.phoneNo }">
	  <input type="hidden" name="brandId" id="brandId" value="${brandId }">
	  <input type="hidden" name="weixinId" id="weixinId" value="${weixinId }">
	  <input type="hidden" name="title" id="title" value="${business.title }">
	 </form>
	  <section class="member-message-wrap">
      	<ul class="member-message-list">
          	<li onclick="editMemberInfo();">
              	<h1><span class="memeber-name">${member.name }</span>
              		<c:if test="${member.gender ==1 }">
              			<span class="sex icon-male"></span>
              		</c:if>
              		<c:if test="${member.gender ==2 }">
              			<span class="sex icon-remale"></span>
              		</c:if>
              	</h1>
                  <p class="mesg-info"><span class="mesg-word">生日：</span><span class="mesg-word-color"><fmt:formatDate value="${member.birthday }" pattern="yyyy-MM-dd"/></span></p>
	                 <!-- 判断该商户是否有家人生日功能 -->
	                 <c:if test="${existsMemberBirth }">
	                  	<p class="mesg-info"><span class="mesg-word">已添加 <span class="mesg-word-color">
                  		<c:choose>
                  			<c:when test="${not empty familyCount }">${familyCount }</c:when>
                  			<c:otherwise>0</c:otherwise>
                  		</c:choose>
                  		</span> 个家人生日</span></p>
	                </c:if>
                  <p class="mesg-info"><span class="mesg-word">手机：</span><span class="mesg-word-color">${member.phoneNo }</span></p>
                  <span class="icon-arrow-right"></span>
              </li>
              <restrict:operation url="memberRight" brandId="${brandId }">
	              <!-- 目前就 水木锦堂 商户有此功能8748 -->
	              	<li onclick="javascript:lookAdviser(${brandId }, '${weixinId }');">
	              		<span class="member-set-one">
	              			<c:if test="${empty resourceList || fn:length(resourceList)<=1 }">
	              				<img src="../../images/member/icon51.png" />
	              			</c:if>
	              			<span>
	              			会员权益介绍</span>
	              		</span><span class="icon-arrow-right"></span>
	              	</li>
              </restrict:operation>
   			
              <c:choose>
              	<c:when test="${fn:length(resourceList)==1 }">
              		 <li>
              		 	<c:forEach var="r" items="${resourceList }">
	              		 	<span class="member-set-one" onclick="javascript:navLocation('${r.resource_url}')">
	              		 		<img src="../../images/member/icon0${r.type }.png" /><span>${r.name }</span></span><span class="icon-arrow-right">
	              		 	</span>
              		 	</c:forEach>
              		 </li>
              	</c:when>
              	<c:when test="${fn:length(resourceList)==2 }">
              		 <li class="clear-wrap">
              		 	<c:forEach var="r" items="${resourceList }">
              		 		<div class="member-set member-set-two left" onclick="javascript:navLocation('${r.resource_url}')">
		                    	<p><img src="../../images/member/icon0${r.type }.png" /></p>
		                        <p>${r.name }</p>
		                    </div>
              		 	</c:forEach>
	                </li>
              	</c:when>
              	<c:when test="${fn:length(resourceList)==3 }">
              		 <li class="clear-wrap">
              		 	<c:forEach var="r" items="${resourceList }">
              		 		<div class="member-set member-set-three left" onclick="javascript:navLocation('${r.resource_url}')">
		                    	<p><img src="../../images/member/icon0${r.type }.png" /></p>
		                        <p>${r.name }</p>
		                    </div>
              		 	</c:forEach>
	                </li>
              	</c:when>
              	<c:otherwise>
              		<li class="clear-wrap">
              			<c:forEach var="r" items="${resourceList }">
			              	<div class="member-set left" onclick="javascript:navLocation('${r.resource_url}')">
			                  	<p><img src="../../images/member/icon0${r.type }.png" /></p>
			                      <p>${r.name }</p>
		                 	</div>
		                 </c:forEach> 
		             </li>
              	</c:otherwise>
              </c:choose>
          </ul>
      </section>
