<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html class="no-js" lang="">
<head>
<meta charset="utf-8">
<title>回头宝上线表单</title>
<meta name="description" content="">
<meta name="HandheldFriendly" content="True">
<meta name="MobileOptimized" content="320">
<meta name="viewport" content="width=device-width, initial-scale=1 ,user-scalable=no">
<meta http-equiv="cleartype" content="on">
<link rel="stylesheet" href="/jsp/mobile/css/themes/a.min.css" />
<link rel="stylesheet" href="/jsp/mobile/css/themes/jquery.mobile.icons.min.css" />
<link rel="stylesheet" href="/jsp/mobile/css/themes/jquery.mobile.structure-1.4.5.min.css" />
<link type="text/css" rel="stylesheet" href="/jsp/mobile/script/1.8.5/aristo/phone.jquery.ui.all.css" />
<link rel="stylesheet" href="/jsp/mobile/css/htb.css" />
<script src="/jsp/mobile/script/jquery1.11.js"></script>
<script src="/jsp/mobile/script/jquery.mobile-1.4.5.min.js"></script>
<script src="/js/base.js"></script>
</head>
<body>
<div data-role="page" data-theme="a">
	<div data-role="header" data-position="inline">
		<!-- <a target="_self" href="#" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left ui-icon-arrow-l"></a> -->
        <h1>回头宝上线表单</h1>
		<a target="_self" href="/controller/merchant/addWifiInfoForERP.do?operatorMobileNumber=${operatorMobileNumber}&operatorId=${operatorId}" class="ui-btn-right ui-btn ui-shadow ui-corner-all ui-btn-icon-right h-right">新增商户</a>
    </div>
    <div data-role="content" data-theme="a" class="m-wrapper">
    	<section class="htb-tit">
        	<div class="htb-sort"><span class="sort-word">按时间降序</span><span class="sort-icon htb-arrow-bottom"></span></div>
            <div class="htb-form-search"><input type="text" placeholder="商户名称/联系人姓名/手机号" id="searchKey"/><span class="form-icon-search"></span></div>
        </section>
        <section class="htb-form-manager">
        	<h1>共<span class="merCount">10</span>个商户</h1>
            <ul class="merchants">
            	<%-- <c:forEach var="item" items="${merchantList}"> 
            		<li>
                	<a href="/controller/merchant/updateWifiInfoForERP.do?operatorMobileNumber=${operatorMobileNumber}&operatorId=${operatorId}&merchantId=${item.merchantId}&brandId=${item.brandId}" target="_self">
                        <div>
                            <span class="htb-sh-name"></span>
                            <span class="htb-cu-name">人名：<span class ="linkName"></span></span>
                        </div>
                        <p class="htb-form-time"><fmt:formatDate value="${item.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                        <p class="htb-form-time"></p>
                        <span class="htb-arrow-right"></span>
                    </a>
                </li>
            	</c:forEach> --%>
            </ul>
        </section>
    </div>
</div>
<script src="/jsp/mobile/script/form-manager.js"></script>
<script type="text/javascript">
var operatorMobileNumber='${operatorMobileNumber}';
var operatorId='${operatorId}';
</script>
</body>
</html>