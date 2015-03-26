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
<script src="/jsp/mobile/script/common.js"></script>
<script src="/js/base.js"></script>
</head>
<body>
<div data-role="page" data-theme="a" id="form-step1">
	<div data-role="header" data-position="inline">
		<a target="_self" href="/controller/merchant/formManager.do?operatorMobileNumber=${operatorMobileNumber }&operatorId=${operatorId }" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left ui-icon-arrow-l"></a>
        <h1>回头宝上线表单</h1>
		<a target="_self"  onclick="return validateInput(1);" href="#form-step2" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-right h-right">下一步</a>
    </div>
    <div data-role="content" data-theme="a" class="m-wrapper">
    	<section class="form-tit">1.请填写下面的基础信息</section>
        <section class="htb-online-form" >
        	<div class="form-module">
            	<h1>门店信息：</h1>
            	<c:if test="${merchantInfo==null}">
            		<input type="text" placeholder="请输入门店名称。如：水木锦堂国贸店" id="merchantName" class="noEmpty" />
	                <input type="text" placeholder="请输入老板/店长姓名" id="userName" class="noEmpty" />
	                <input type="text" placeholder="请输入老板/店长手机号" id="userMobile" class="noEmpty" />
	                
            	</c:if>
            	<c:if test="${merchantInfo!=null}">
            		<input type="text" placeholder="请输入门店名称。如：水木锦堂国贸店" id="merchantName" value="${merchantInfo.merchantName}" class="noEmpty" />
	                <input type="text" placeholder="请输入老板/店长姓名" id="userName"  value="${merchantInfo.bossName}" class="noEmpty" />
	                <input type="text" placeholder="请输入老板/店长手机号" id="userMobile"  value="${merchantInfo.bossMobile}" class="noEmpty" />
            		
	            </c:if>
            </div>
            <div class="form-module">
            	<c:if test="${merchantInfo==null}">
            		<p class="lock-tips">当连接WIFI时，通过发送含有验证码的短信验证用户填写的手机号码。</p>
	                <div class="lock-box">短信验证码
						<div class="lock-style lock-off"  id="isPassWordCheck"  data-value="false">
							<span class="lock-icon"></span>
						</div>
	                </div>
            	</c:if>
            	<c:if test="${merchantInfo!=null}">
            		<p class="lock-tips">当连接WIFI时，通过发送含有验证码的短信验证用户填写的手机号码。</p>
            		<div class="lock-box">短信验证码
	                		<c:choose>
   								<c:when test="${merchantInfo.isPassWordCheck eq true}">
    								<div class="lock-style lock-on"  id="isPassWordCheck"  data-value="true">
										<span class="lock-icon"></span>
									</div>
   								</c:when>
   								<c:otherwise>
   									<div class="lock-style lock-off"  id="isPassWordCheck"  data-value="false">
										<span class="lock-icon"></span>
									</div>
   								</c:otherwise>
    						</c:choose>
	                </div>
            	</c:if>
            </div>
        	<div class="form-module">
            	<h1>设备MAC码：</h1>
                <div class="form-wifi-input"><input type="text" class="wifi-mac-search" placeholder="可输入MAC后四位进行搜索"   /></div>
                <div class="form-wifi-search-result">
                	<ul></ul>
                </div>
                <c:if test="${merchantInfo==null}">
            			
            	</c:if>
            	<c:if test="${merchantInfo!=null}">
            		<c:forEach var="item" items="${merchantInfo.deviceInfoList}">
            			<div class="form-wifi-input search-result">
	            			<span class="result-cont  addWifiSapn" >${item.mac}</span>
	            			<span class="htb-arrow-remove wifi-remove"></span>
            			</div>
            		</c:forEach>
            	</c:if>
            </div>
        </section>
        <div class="erroy-message">文本不能为空</div>
        <!-- <section class="htb-btn"><button class="htb-btn1">增加设备</button></section> -->
    </div>
</div>
<a id="save_step3" href="#form-step3" style="display:none;"></a>
<div data-role="page" data-theme="a" id="form-step2">
	<div data-role="header" data-position="inline">
		<a href="#form-step1" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left ui-icon-arrow-l"></a>
        <h1>回头宝上线表单</h1>
		<a href="javascript:validateInput(2);" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-right h-right"  >保存</a>
    </div>
    <div data-role="content" data-theme="a" class="m-wrapper">
    	<section class="form-tit">2.设置WIFI名称</section>
        <section class="htb-online-form">
        	<c:if test="${merchantInfo==null}">
	            <div class="form-module ssid-module">
	            	<h1>WIFI名称1(建议顾客使用)：</h1>
	                <div class="form-wifi-input"><input type="text" placeholder="请输入WIFI名称" id="ssid1" class="noEmpty" /><span class="htb-arrow-lock"></span></div>
	                <div class="form-wifi-input wifi-password"><input type="password" placeholder="请输入连接WIFI的密码" id="password1"/><span class="htb-arrow-remove wifi-password-remove"></span></div>
	            </div>
	        	<div class="form-module ssid-module">
	            	<h1>WIFI名称2(建议员工使用)：</h1>
	                <div class="form-wifi-input"><input type="text" placeholder="请输入WIFI名称" id="ssid2"/><span class="htb-arrow-lock"></span></div>
	                <div class="form-wifi-input wifi-password"><input type="password" placeholder="请输入连接WIFI的密码" id="password2"/><span class="htb-arrow-remove wifi-password-remove"></span></div>
	            </div>
	        	<div class="form-module ssid-module">
	            	<h1>WIFI名称3：</h1>
	                <div class="form-wifi-input"><input type="text" placeholder="请输入WIFI名称" id="ssid3"/><span class="htb-arrow-lock"></span></div>
	                <div class="form-wifi-input wifi-password"><input type="password" placeholder="请输入连接WIFI的密码" id="password3"/><span class="htb-arrow-remove wifi-password-remove"></span></div>
	            </div>
            </c:if>
            <c:if test="${merchantInfo!=null}">
            	<div class="form-module ssid-module">
	            	<h1>WIFI名称1(建议顾客使用)：</h1>
	                <div class="form-wifi-input"><input type="text" placeholder="请输入WIFI名称" id="ssid1" value="${merchantInfo.deviceInfoList[0].devSSID[0].ssid}" class="noEmpty"/><span class="htb-arrow-lock"></span></div>
	                <div class="form-wifi-input wifi-password"><input type="password" placeholder="请输入连接WIFI的密码" id="password1"/><span class="htb-arrow-remove wifi-password-remove"></span></div>
	            </div>
	        	<div class="form-module ssid-module">
	            	<h1>WIFI名称2(建议员工使用)：</h1>
	                <div class="form-wifi-input"><input type="text" placeholder="请输入WIFI名称" id="ssid2" value="${merchantInfo.deviceInfoList[0].devSSID[1].ssid}"/><span class="htb-arrow-lock"></span></div>
	                <div class="form-wifi-input wifi-password"><input type="password" placeholder="请输入连接WIFI的密码" id="password2"/><span class="htb-arrow-remove wifi-password-remove"></span></div>
	            </div>
	        	<div class="form-module ssid-module">
	            	<h1>WIFI名称3：</h1>
	                <div class="form-wifi-input"><input type="text" placeholder="请输入WIFI名称" id="ssid3" value="${merchantInfo.deviceInfoList[0].devSSID[2].ssid}"/><span class="htb-arrow-lock"></span></div>
	                <div class="form-wifi-input wifi-password"><input type="password" placeholder="请输入连接WIFI的密码" id="password3"/><span class="htb-arrow-remove wifi-password-remove"></span></div>
	            </div>
            </c:if>
        	
        </section>
        <div class="erroy-message">请输入WIFI名称</div>
    </div>
</div>
<div data-role="page" data-theme="a" id="form-step3">
	<div data-role="header" data-position="inline">
        <h1>回头宝上线表单</h1>
    </div>
    <div data-role="content" data-theme="a" class="m-wrapper">
    	<section class="form-tit">3.完成</section>
        <section class="htb-online-form form-success">
        	<div class="online-form-success">
            	<p><img src="/jsp/mobile/images/icon10.png" width="100"/></p>
                <p class="success-word">设置成功！</p>
            </div>
            <p>可让商户登陆回头宝，修改广告设置、管理用户等</p>
        </section>
        <section class="htb-btn"><a target="_self" href="/controller/merchant/formManager.do?operatorMobileNumber=${operatorMobileNumber }&operatorId=${operatorId }" class="htb-btn1">完成</a></section>
    </div>
</div>
<script type="text/javascript">
var operatorMobileNumber='${operatorMobileNumber}';
var operatorId='${operatorId}';
var isUpdate=${isUpdate};
var merchantId='${merchantInfo.merchantId}';
var brandId='${merchantInfo.brandId}';
</script>
<script src="/jsp/mobile/script/online-form.js"></script>
</body>
</html>