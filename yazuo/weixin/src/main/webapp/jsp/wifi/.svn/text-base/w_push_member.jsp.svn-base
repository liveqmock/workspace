<%@page import="com.yazuo.weixin.util.JudgeMenuShow"%>
<%@page import="com.yazuo.weixin.vo.BusinessVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	BusinessVO business = ((BusinessVO) request.getAttribute("business"));
	Integer brandId = business.getBrandId();
	String title = business.getTitle();
	Boolean hasImage=(Boolean)request.getAttribute("hasImage");
	String weixinId=(String)request.getAttribute("weixinId");
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0, maximum-scale=1.0"/> 
<script type="text/javascript" language="javascript">
	var ctx = "<%=basePath%>";
</script>
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/crm.css" />
<link href="<%=basePath%>/css/common.css" rel="stylesheet" type="text/css" />
<title><%=title %></title>
</head>
<body>
<div class="w">
<section class="w-top">
    	<div class="top-bg"></div>
        <section class="company-logo">
         <%if(hasImage||business.getCompanyWeiboId().equals("1111111111")){%>
        <img src="<%=basePath%>/user-upload-image/${business.brandId}/${business.smallimageName}" width="75" height="75" />
        <%}else{%>
        <img src="http://tp4.sinaimg.cn/<%=business.getCompanyWeiboId() %>/180/1122/9" width="75" height="75" />
        <%} %>
        </section>
        <section class="company-name">
        	<h1><%=title%></h1>
        </section>
        <section class="top-menu">
         	<a class="sel" href="<%=path%>/weixin/phonePage/membershipCard.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">  会员</a> 
	        <a	href="<%=path%>/weixin/phonePage/preference.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">优惠</a>
	        <a href="<%=path%>/weixin/newFun/subbranch.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">门店</a>
			<%if (JudgeMenuShow.judgeMenu(brandId)) { %>
				<a href="<%=path%>/weixin/newFun/showStory.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">陪你</a>
			<%} else {%>
				<a href="<%=path%>/weixin/newFun/dishes.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>&r=<%=Math.random()%>">菜品</a>
			<%} %>
        </section>
    </section>
	<section class="w-main">
    	<div class="w-member">
        	<div class="w-member-tit"><img src="<%=path%>/images/m01.png" width="260" /></div>
            <div class="w-member-goods clear_wrap">
            	<div class="left">
                	<ul>
                    	<li>
                        	<p><img src="<%=path%>/images/g1.jpg" width="145" height="125" /></p>
                            <p class="goods-name">阿根廷红虾</p>
                        </li>
                    	<li>
                        	<p><img src="<%=path%>/images/g9.jpg" width="145" height="162" /></p>
                            <p class="goods-name">法式香煎鹅肝</p>
                        </li>
                    	<li>
                        	<p><img src="<%=path%>/images/g10.jpg" width="145" height="162" /></p>
                            <p class="goods-name">摩西多</p>
                        </li>
                    </ul>
                </div>
                <div class="right">
                	<ul>
                    	<li>
                        	<p><img src="<%=path%>/images/g6.jpg" width="145" height="162" /></p>
                            <p class="goods-name">鹅肝鱼籽蒸蛋</p>
                        </li>
                    	<li>
                        	<p><img src="<%=path%>/images/g8.jpg" width="145" height="145" /></p>
                            <p class="goods-name">小米辽参</p>
                        </li>
                    	<li>
                        	<p><img src="<%=path%>/images/g5.jpg" width="145" height="142" /></p>
                            <p class="goods-name">冰酷树莓</p>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="w-member-tit"><img src="<%=path%>/images/m02.png" width="250" /></div>
            <div class="w-member-mode">
            	<p><img src="<%=path%>/images/m03.png" width="280" /></p>
            	<p><img src="<%=path%>/images/m04.png" width="280" /></p>
            	<p><img src="<%=path%>/images/m05.png" width="280" /></p>
            	<p><img src="<%=path%>/images/m06.png" width="280" /></p>
            	<p><img src="<%=path%>/images/m07.png" width="280" /></p>
            	<p><img src="<%=path%>/images/m08.png" width="280" /></p>
            	<p><img src="<%=path%>/images/m09.png" width="220" /></p>
            </div>
            <div class="w-member-way">
            	<p><img src="<%=path%>/images/m10.png" width="280" /></p>
            	<p><img src="<%=path%>/images/m11.png" width="280" /></p>
            	<p><img src="<%=path%>/images/m12.png" width="280" /></p>
            	<p><img src="<%=path%>/images/m13.png" width="228" /></p>
            </div>
            <div class="w-member-ts"><img src="<%=path%>/images/m14.png" width="250" /></div>
        </div>
    </section>
   </div>
</body>
</html>
