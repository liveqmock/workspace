<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信后台管理系统</title>
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>

<script>
	function a(json) {
		if ($("#aa").val() != '' && $("#ab").val() != '') {
			json = json + "{\"key\":\"" + $("#aa").val() + "\",\"name\":\""
					+ $("#ab").val() + "\",\"type\":\"click\"}";
		} else {
			return json;
		}
		if ($("#ac").val() != '' && $("#ad").val() != '') {
			json = json + ",{\"key\":\"" + $("#ac").val() + "\",\"name\":\""
					+ $("#ad").val() + "\",\"type\":\"click\"}";
		} else {
			return json;
		}
		if ($("#ae").val() != '' && $("#af").val() != '') {
			json = json + ",{\"key\":\"" + $("#ae").val() + "\",\"name\":\""
					+ $("#af").val() + "\",\"type\":\"click\"}";
		} else {
			return json;
		}
		if ($("#ag").val() != '' && $("#ah").val() != '') {
			json = json + ",{\"key\":\"" + $("#ag").val() + "\",\"name\":\""
					+ $("#ah").val() + "\",\"type\":\"click\"}";
		} else {
			return json;
		}
		if ($("#ai").val() != '' && $("#aj").val() != '') {
			json = json + ",{\"key\":\"" + $("#ai").val() + "\",\"name\":\""
					+ $("#aj").val() + "\",\"type\":\"click\"}";
			return json;
		} else {
			return json;
		}
	}
	function b(json) {
		if ($("#ba").val() != '' && $("#bb").val() != '') {
			json = json + "{\"key\":\"" + $("#ba").val() + "\",\"name\":\""
					+ $("#bb").val() + "\",\"type\":\"click\"}";
		} else {
			return json;
		}
		if ($("#bc").val() != '' && $("#bd").val() != '') {
			json = json + ",{\"key\":\"" + $("#bc").val() + "\",\"name\":\""
					+ $("#bd").val() + "\",\"type\":\"click\"}";
		} else {
			return json;
		}
		if ($("#be").val() != '' && $("#bf").val() != '') {
			json = json + ",{\"key\":\"" + $("#be").val() + "\",\"name\":\""
					+ $("#bf").val() + "\",\"type\":\"click\"}";
		} else {
			return json;
		}
		if ($("#bg").val() != '' && $("#bh").val() != '') {
			json = json + ",{\"key\":\"" + $("#bg").val() + "\",\"name\":\""
					+ $("#bh").val() + "\",\"type\":\"click\"}";
		} else {
			return json;
		}
		if ($("#bi").val() != '' && $("#bj").val() != '') {
			json = json + ",{\"key\":\"" + $("#bi").val() + "\",\"name\":\""
					+ $("#bj").val() + "\",\"type\":\"click\"}";
			return json;
		} else {
			return json;
		}
	}
	function c(json) {
		if ($("#ca").val() != '' && $("#cb").val() != '') {
			json = json + "{\"key\":\"" + $("#ca").val() + "\",\"name\":\""
					+ $("#cb").val() + "\",\"type\":\"click\"}";
		} else {
			return json;
		}
		if ($("#cc").val() != '' && $("#cd").val() != '') {
			json = json + ",{\"key\":\"" + $("#cc").val() + "\",\"name\":\""
					+ $("#cd").val() + "\",\"type\":\"click\"}";
		} else {
			return json;
		}
		if ($("#ce").val() != '' && $("#cf").val() != '') {
			json = json + ",{\"key\":\"" + $("#ce").val() + "\",\"name\":\""
					+ $("#cf").val() + "\",\"type\":\"click\"}";
		} else {
			return json;
		}
		if ($("#cg").val() != '' && $("#ch").val() != '') {
			json = json + ",{\"key\":\"" + $("#cg").val() + "\",\"name\":\""
					+ $("#ch").val() + "\",\"type\":\"click\"}";
		} else {
			return json;
		}
		if ($("#ci").val() != '' && $("#cj").val() != '') {
			json = json + ",{\"key\":\"" + $("#ci").val() + "\",\"name\":\""
					+ $("#cj").val() + "\",\"type\":\"click\"}";
			return json;
		} else {
			return json;
		}
	}
	function toJson() {
		var json = "{\"button\":";
		if ($("#a").val() != '') {
			if ($("#aaa").val() != ''){
				json=json+"[{\"key\":\"" + $("#aaa").val()+ "\",\"name\":\"" + $("#a").val()+ "\",\"type\":\"click\"}";
			}else{
				json = json + "[{\"name\":\"" + $("#a").val()+ "\",\"sub_button\":[";
				json = a(json);
				json = json+"]}";
			}
			
		} else {
			return null;
		}
		if ($("#b").val() != '') {
			if ($("#bbb").val() != ''){
				json=json+",{\"key\":\"" + $("#bbb").val()+ "\",\"name\":\"" + $("#b").val()+ "\",\"type\":\"click\"}";
			}else{
			json = json + ",{\"name\":\"" + $("#b").val()
					+ "\",\"sub_button\":[";
			json = b(json);
			json = json+"]}";}
		} else {
			json = json + "]" + "}";
			alert(json);
			return json;
		}
		if ($("#c").val() != '') {
			if ($("#ccc").val() != ''){
				json=json+",{\"key\":\"" + $("#ccc").val()+ "\",\"name\":\"" + $("#c").val()+ "\",\"type\":\"click\"}";
			}else{
			json = json + ",{\"name\":\"" + $("#c").val()
					+ "\",\"sub_button\":[";

			json = c(json);
			json = json+"]}";}
		} else {
		//	json = json + "]" + "}";
		}
		json=json+ "]" + "}";
		console.log(json);
		return json;
	}
	
	function submit(){
		var json=toJson();
		console.log(json);
		$.ajax({
			type : "post",
			url : '<%=path%>/weixin/createButton.do',
			cache : false,
			dataType : "text",
			data : 'json=' + json + '&appId=' + $("#AppId").val()
					+ '&appSecret=' + $("#AppSecret").val(),
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					alert('错误', '提交失败', 'error');
				}
			},
			success : function(a) {
				alert(a);
			}
		});

	}
</script>
</head>
<body>

	<a
		href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%BF%94%E5%9B%9E%E7%A0%81%E8%AF%B4%E6%98%8E"">错误码说明</a>
	<br>
	目前可用KEY有：pinpaigushi、youhui、mendian、caipin、jiaruhuiyuan、huiyuanquanyi、wodezhanghu、huiyuanziliao、wodeyouhui、shengrilibao、guaguaka、laohuji、dazhuanpan、yijianshangwang、bangdingshitika
	<br> 菜单名可以自己定义
	<br> 同级别菜单不应该有间隔（比如第一栏的二级菜单，只填写第三，第五条），从上到下填写
	<br>
	<br>

	创建自定义菜单后，由于微信客户端缓存，需要24小时微信客户端才会展现出来。建议测试时可以尝试取消关注公众账号后，再次关注，则可以看到创建后的效果。
	<br>
	<br> 正确的Json返回结果:{"errcode":0,"errmsg":"ok"}
	<br>
	<br> AppId:
	<input id="AppId"> AppSecret:
	<input id="AppSecret">
	<a href="javascript:void(0)" onclick="submit();">提交</a><br><br>
	第一栏的菜单	
	<table>
		<tr>
			<td>级菜单别</td>
			<td>key</td>
			<td>菜单名</td>
		</tr>
		<tr>
			<td>一级菜单</td>
			<td><input id="aaa" value="pinpaigushi"></td>
			<td><input id="a" value="品牌故事"></td>
		</tr>
		<tr>
			<td>二级菜单</td>
			<td><input id="aa"></td>
			<td><input id="ab"></td>
		</tr>
		<tr>
			<td>二级菜单</td>
			<td><input id="ac"></td>
			<td><input id="ad"></td>
		</tr>
		<tr>
			<td>二级菜单</td>
			<td><input id="ae"></td>
			<td><input id="af"></td>
		</tr>
		<tr>
			<td>二级菜单</td>
			<td><input id="ag"></td>
			<td><input id="ah"></td>
		</tr>
		<tr>
			<td>二级菜单</td>
			<td><input id="ai"></td>
			<td><input id="aj"></td>
		</tr>
		</table><br><br><br>
		第二栏的菜单
		<table>
		<tr>
			<td>一级菜单</td>
			<td><input id="bbb"></td>
			<td><input id="b" value="粉丝会员"></td>
		</tr>
		<tr>
			<td>二级菜单</td>
			<td><input id="ba" value="jiaruhuiyuan"></td>
			<td><input id="bb" value="加入会员"></td>
		</tr>
		<tr>
			<td>二级菜单</td>
			<td><input id="bc" value="huiyuanquanyi"></td>
			<td><input id="bd" value="会员权益"></td>
		</tr>
		<tr>
			<td>二级菜单</td>
			<td><input id="be" value="wodezhanghu"></td>
			<td><input id="bf" value="我的账户"></td>
		</tr>
		<tr>
			<td>二级菜单</td>
			<td><input id="bg" value="shengrilibao"></td>
			<td><input id="bh" value="生日优惠"></td>
		</tr>
		<tr>
			<td>二级菜单</td>
			<td><input id="bi"></td>
			<td><input id="bj"></td>
		</tr>
		</table><br><br><br>
		第三栏的菜单
		<table>
		<tr>
			<td>一级菜单</td>
			<td><input id="ccc"></td>
			<td><input id="c"></td>
		</tr>
		<tr>
			<td>二级菜单</td>
			<td><input id="ca"></td>
			<td><input id="cb"></td>
		</tr>
		<tr>
			<td>二级菜单</td>
			<td><input id="cc"></td>
			<td><input id="cd"></td>
		</tr>
		<tr>
			<td>二级菜单</td>
			<td><input id="ce"></td>
			<td><input id="cf"></td>
		</tr>
		<tr>
			<td>二级菜单</td>
			<td><input id="cg"></td>
			<td><input id="ch"></td>
		</tr>
		<tr>
			<td>二级菜单</td>
			<td><input id="ci"></td>
			<td><input id="cj"></td>
		</tr>
	</table>

</body>
</html>