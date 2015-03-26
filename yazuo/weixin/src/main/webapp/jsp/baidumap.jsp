<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	String x = request.getParameter("x");
	String y = request.getParameter("y");
	String name = request.getParameter("name");
	String address = request.getParameter("address");
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
    <style type="text/css">  
        html,body{  
            width:100%;  
            height:100%;  
            margin:0;  
            overflow:hidden;  
        }  
        #gotobaiduform {
        	font-size: 1.3em;
        }
        #gotobaiduform .outset {
        	width: 180px;
        }
        .error-tip {
        	color: #ff0000;
        	font-size: 0.6em;
        }
    </style>  
    </head>  
    <body>  
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=3fad19499e3dd462f217cdb46b3e0d3c"></script>
	<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
    <div style="width:100%;height:100%;border:1px solid gray" id="container">  
    </div>  
    </body>  
</html>  
 <script type="text/javascript">  
    var map = new BMap.Map("container");  
    map.centerAndZoom(new BMap.Point(<%=x%>,<%=y%>), 16);  
    map.enableScrollWheelZoom();  
    var marker=new BMap.Marker(new BMap.Point(<%=x%>,<%=y%>));  
    map.addOverlay(marker);  
    var licontent="<b>终点：<%=name%></b><br>";  
    licontent+="<span><strong>地址：</strong><%=address%></span><br>";  
    //licontent+="<span><strong>电话：</strong>(010)63095718,(010)63095630</span><br>";  
    licontent+="<span class=\"input\"><strong>起点：</strong><input class=\"outset\" type=\"text\" name=\"origin\" value=\"\"/><br /><input class=\"outset-but\" type=\"button\" value=\"公交\" onclick=\"gotobaidu(1)\" /><input class=\"outset-but\" type=\"button\" value=\"驾车\"  onclick=\"gotobaidu(2)\"/>&nbsp;<span class=\"error-tip\"></span><a class=\"gotob\" href=\"url=\"http://api.map.baidu.com/direction?destination=latlng:"+marker.getPosition().lat+","+marker.getPosition().lng+"|name:<%=name%>"+"®ion="+"&output=html\" target=\"_blank\"></a></span>";  
    var hiddeninput="<input type=\"hidden\" value=\""+'北京'+"\" name=\"region\" /><input type=\"hidden\" value=\"html\" name=\"output\" /><input type=\"hidden\" value=\"driving\" name=\"mode\" /><input type=\"hidden\" value=\"latlng:"+marker.getPosition().lat+","+marker.getPosition().lng+"|name:<%=name%>"+"\" name=\"destination\" />";  
    var content1 ="<form id=\"gotobaiduform\" action=\"http://api.map.baidu.com/direction\" target=\"_blank\" method=\"get\">" + licontent +hiddeninput+"</form>";   
    var opts1 = { width: 300 };  
    
    var  infoWindow = new BMap.InfoWindow(content1, opts1);  
 	marker.openInfoWindow(infoWindow);  
 	marker.addEventListener('click',function(){ marker.openInfoWindow(infoWindow);});  
  
function gotobaidu(type)  
{  
    if($.trim($("input[name=origin]").val())=="")  
    {  
        //alert("请输入起点！");  
        $('.error-tip').text("请输入起点！");
        return;  
    }else{  
    	$('.error-tip').empty();
        if(type==1)  
        {  
            $("input[name=mode]").val("transit");  
            $("#gotobaiduform")[0].submit();  
        }else if(type==2)  
        {      
            $("input[name=mode]").val("driving");          
            $("#gotobaiduform")[0].submit();  
        }  
    }  
}     
</script>  