<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta.jsp"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<title>创建菜单</title>
<script>
   function saveData() {
	   var appId = $("#appId").val();
	   var appSecret = $("#appSecret").val();
	   if (appId==null || appId == "") {
		   alert("请输入appId!");
		   return;
	   } else if (appSecret==null || appSecret=="") {
		   alert("请输入appSecret!");
		   return;
	   }
	   var formData=$("form").serialize();
	   $.ajax({
	    type: "POST",
	    url: "<%=path %>/weixin/createButton/createButton.do",
	    processData:true,
	    dataType:"text",
	    data:formData,
	    success: function(data){
		    alert("返回结果：" + data);
	    	//$("#backMsgDiv").html("<font color='red'>返回结果：" + data + "</font>");
		   }
	   });
   }
</script>
</head>
<body>

	<center>
<form id="form1" method="post" action="">
	<input type="hidden" name="brandId" id="brandId" value="${param.brandId }">
	<a href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%BF%94%E5%9B%9E%E7%A0%81%E8%AF%B4%E6%98%8E"">错误码说明</a>
	<br>
	目前可用KEY有：pinpaigushi、youhui、mendian、caipin、jiaruhuiyuan、huiyuanquanyi、wodezhanghu、huiyuanziliao、wodeyouhui、shengrilibao、guaguaka、laohuji、dazhuanpan、yijianshangwang、bangdingshitika、bindweixinmember、zhongjiangjilu、bankachoujiang
	<br> 菜单名可以自己定义
	<br> 同级别菜单不应该有间隔（比如第一栏的二级菜单，只填写第三，第五条），从上到下填写
	<br>
	<br>
	创建自定义菜单后，由于微信客户端缓存，需要24小时微信客户端才会展现出来。建议测试时可以尝试取消关注公众账号后，再次关注，则可以看到创建后的效果。
	<br>
	<br> 正确的Json返回结果:{"errcode":0,"errmsg":"ok"}
	<br>
	<br>
	<span style="color:red">
	配置说明：
		1.如果菜单url是http或者https开头，生成的菜单为链接事件（跟浏览器访问地址一样），其他为按钮单击事件。
	</span>
	<br>
	<br> AppId:
	<input id="appId" name ="appId" value="${appId }"/>&nbsp;<font color="red">*</font>
	 AppSecret:<input id="appSecret" name ="appSecret" value="${appSecret }"/>&nbsp;<font color="red">*</font>
	<div id="backMsgDiv"></div>
	<table>
		<tr>
			<td></td>
			<td align="center">url(key)</td>
			<td align="center">菜单名</td>
		</tr>
		<c:if test="${not empty list && fn:length(list)>0 }"> 
			<c:forEach var="m" items="${list }" varStatus="i">
				<c:set var="inputHaflName" value=""></c:set>
				<table>
				<tr>
					<td colspan="3">
						第<c:if test="${i.index+1 ==1 }">一<c:set var="inputHaflName" value="one"></c:set></c:if>
						<c:if test="${i.index+1 ==2 }">二<c:set var="inputHaflName" value="two"></c:set></c:if>
						<c:if test="${i.index+1 ==3 }">三<c:set var="inputHaflName" value="three"></c:set></c:if>栏的菜单
					</td>
				</tr>
				<tr>
					<td>一级菜单</td>
					<td><input id="${inputHaflName }url" name="${inputHaflName }url" value="${m.menuName }"></td>
					<td><input id="${inputHaflName }name" name="${inputHaflName }name" value="${m.menuValue }"></td>
				</tr>
				<c:if test="${not empty m && not empty m.childrenList && fn:length(m.childrenList)>0 }">
					<c:forEach var="c" items="${m.childrenList }" varStatus="i">
						<tr>
							<td>二级菜单</td>
							<td><input id="${inputHaflName }${i.index+1 }url" name="${inputHaflName }${i.index+1 }url" value="${c.menuName }"></td>
							<td><input id="${inputHaflName }${i.index+1 }name" name="${inputHaflName }${i.index+1 }name" value="${c.menuValue }"></td>
						</tr>
					</c:forEach>
					<c:forEach var="i" begin="${fn:length(m.childrenList)+1}" end="5" step="1">
						<tr>
							<td>二级菜单</td>
							<td><input id="${inputHaflName }${i }url" name="${inputHaflName }${i }url"></td>
							<td><input id="${inputHaflName }${i }name" name="${inputHaflName }${i }name" value=""></td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty m || empty m.childrenList || fn:length(m.childrenList)==0 }">
					<c:forEach var="i" begin="1" end="5" step="1">
						<tr>
							<td>二级菜单</td>
							<td><input id="${inputHaflName }${i }url" name="${inputHaflName }${i }url"></td>
							<td><input id="${inputHaflName }${i }name" name="${inputHaflName }${i }name" value=""></td>
						</tr>
					</c:forEach>
				</c:if>
				</table>
			</c:forEach>
			<c:forEach var="m" begin="${fn:length(list)+1 }" end="3" step="1">
				<c:set var="inpName" value=""></c:set>
				<tr>
					<td colspan="3">
						第<c:if test="${m ==1 }">一<c:set var="inpName" value="one"></c:set></c:if>
						<c:if test="${m ==2 }">二<c:set var="inpName" value="two"></c:set></c:if>
						<c:if test="${m ==3 }">三<c:set var="inpName" value="three"></c:set></c:if>栏的菜单
					</td>
				</tr>
				<table>
					<tr>
						<td>一级菜单</td>
						<td><input id="${inpName }url" name="${inpName }url" value=""></td>
						<td><input id="${inpName }name" name="${inpName }name" value=""></td>
					</tr>
					<c:forEach var="n" begin="1" end="5" step="1">
						<tr>
							<td>二级菜单</td>
							<td><input id="${inpName }${n }url" name="${inpName }${n }url"></td>
							<td><input id="${inpName }${n }name" name="${inpName }${n }name" value=""></td>
						</tr>
					</c:forEach>
				</table>
			</c:forEach>
		</table>
		</c:if>
		<c:if test="${empty list || fn:length(list)==0 }">
			<tr>
				<td>第一栏的菜单</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>一级菜单</td>
				<td><input id="oneurl" name="oneurl" value=""></td>
				<td><input id="onename" name="onename" value=""></td>
			</tr>
			<tr>
				<td>二级菜单</td>
				<td><input id="one1url" name="one1url"></td>
				<td><input id="one1name" name="one1name" value=""></td>
			</tr>
			<tr>
				<td>二级菜单</td>
				<td><input id="one2url" name="one2url"></td>
				<td><input id="one2name" name="one2name" value=""></td>
			</tr>
			<tr>
				<td>二级菜单</td>
				<td><input id="one3url" name="one3url"></td>
				<td><input id="one3name" name="one3name" value=""></td>
			</tr>
			<tr>
				<td>二级菜单</td>
				<td><input id="one4url" name="one4url"></td>
				<td><input id="one4name" name="one4name" value=""></td>
			</tr>
			<tr>
				<td>二级菜单</td>
				<td><input id="one5url" name="one5url"></td>
				<td><input id="one5name" name="one5name" value=""></td>
			</tr>
		</table>
			第二栏的菜单
			<table>
			<tr>
				<td>一级菜单</td>
				<td><input id="twourl" name="twourl" value=""></td>
				<td><input id="twoname" name="twoname" value=""></td>
			</tr>
			<tr>
				<td>二级菜单</td>
				<td><input id="two1url" name="two1url"></td>
				<td><input id="two1name" name="two1name" value=""></td>
			</tr>
			<tr>
				<td>二级菜单</td>
				<td><input id="two2url" name="two2url"></td>
				<td><input id="two2name" name="two2name" value=""></td>
			</tr>
			<tr>
				<td>二级菜单</td>
				<td><input id="two3url" name="two3url"></td>
				<td><input id="two3name" name="two3name" value=""></td>
			</tr>
			<tr>
				<td>二级菜单</td>
				<td><input id="two4url" name="two4url"></td>
				<td><input id="two4name" name="two4name"  value=""></td>
			</tr>
			<tr>
				<td>二级菜单</td>
				<td><input id="two5url" name="two5url"></td>
				<td><input id="two5name" name="two5name" value=""></td>
			</tr>
			</table>
			第三栏的菜单
			<table>
			<tr>
				<td>一级菜单</td>
				<td><input id="threeurl" name="threeurl" value=""></td>
				<td><input id="threename" name="threename" value=""></td>
			</tr>
			<tr>
				<td>二级菜单</td>
				<td><input id="three1url" name="three1url"></td>
				<td><input id="three1name" name="three1name" value=""></td>
			</tr>
			<tr>
				<td>二级菜单</td>
				<td><input id="three2url" name="three2url"></td>
				<td><input id="three2name" name="three2name" value=""></td>
			</tr>
			<tr>
				<td>二级菜单</td>
				<td><input id="three3url" name="three3url"></td>
				<td><input id="three3name" name="three3name" value=""></td>
			</tr>
			<tr>
				<td>二级菜单</td>
				<td><input id="three4url" name="three4url"></td>
				<td><input id="three4name" name="three4name" value=""></td>
			</tr>
			<tr>
				<td>二级菜单</td>
				<td><input id="three5url" name="three5url"></td>
				<td><input id="three5name" name="three5name" value=""></td>
			</tr>
		</table>
</c:if>
	<input type="button" value="创建" style="width:80px;height: 60px;" onclick="saveData();"/>
</form>
	</center>
</body>
</html>