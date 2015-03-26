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
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/a.min.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/jquery.mobile.icons.min.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery.mobile/jquery.mobile.structure-1.4.5.min.css" />
<link rel="stylesheet" href="<%=path %>/css/new_member/base.css" />
<script type="text/javascript">
	var ctx = "<%=path%>";
</script>
<script type="text/javascript" src="<%=path%>/js/jquery/jquery.js"></script>
<script src="<%=path %>/js/jquery.mobile/jquery.mobile-1.4.5.min.js"></script>
<title><%=title %></title>
</head>
<body>
<div data-role="page" data-theme="a">
	<div data-role="header" data-position="inline">
		<a target="_self" href="javascript:window.history.go(-1)" class="ui-btn ui-shadow ui-corner-all ui-icon-arrow-l ui-btn-icon-left"></a>
        <h1>会员中心</h1>        
		<a target="_self" href="#" class="ui-btn ui-shadow ui-corner-all ui-icon-home ui-btn-icon-right"></a>
    </div>
    <div data-role="content" data-theme="a" class="m-wrapper">
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
            	<p><img src="<%=path%>/images/m09_test.png" width="220" /></p>
            </div>
            <div class="w-member-way">
            	<p><img src="<%=path%>/images/m10.png" width="280" /></p>
            	<p><img src="<%=path%>/images/m11.png" width="280" /></p>
            	<p><img src="<%=path%>/images/m12.png" width="280" /></p>
            	<p><img src="<%=path%>/images/m13_test.png" width="228" /></p>
            </div>
            <div class="w-member-ts"><img src="<%=path%>/images/m14.png" width="250" /></div>
        </div>
    </section>
   </div>
    <%@ include file="/jsp/common/common_top.jsp" %>
  </div>
</body>
</html>
