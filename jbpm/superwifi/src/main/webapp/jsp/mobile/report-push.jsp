<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/jsp/comm/baseJs.jsp"%>

 <!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>会员发展情况</title> 
<head>
<meta http-equiv="Cache-Control" content="Expires: 36000" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0"/>
<meta name="description" content="">
<meta name="HandheldFriendly" content="True">
<meta name="MobileOptimized" content="320">
<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
<meta http-equiv="cleartype" content="on">
<link rel="stylesheet" href="css/themes/a.min.css" />
<link rel="stylesheet" href="css/themes/jquery.mobile.icons.min.css" />
<link rel="stylesheet" href="css/themes/jquery.mobile.structure-1.4.5.min.css" />
<link type="text/css" rel="stylesheet" href="script/1.8.5/aristo/phone.jquery.ui.all.css" />
<link rel="stylesheet" href="css/htb.css" />
<script src="script/1.8.5/jquery.js"></script>
<script src="script/jquery.mobile-1.4.5.min.js"></script>
<script src="script/1.8.5/jquery-ui-1.8.5.min.js"></script>
<script src="script/report-push.js"></script>
</head>
<body>
<div data-role="page" data-theme="a">
	<div data-role="header" data-position="inline">
		<!-- <a target="_self" href="#" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left ui-icon-arrow-l"></a> -->
        <h1>会员发展情况</h1>
		<!-- <a target="_self" href="#" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-right h-right">新增商户</a> -->
    </div>
    <div data-role="content" data-theme="a" class="m-wrapper">
    	<section class="htb-tit">
        	<div class="htb-tit-left lastDay"><span class="htb-arrow-left"></span><span>前一天</span></div>
        	<div class="htb-tit-time"><input type="text" class="datePicker" placeholder="选择日期"/><span class="htb-arrow-bottom"></span></div>
        	<!-- <div class="htb-tit-right htb-tit-right-disbled"><span class="afterDay">后一天</span><span class="htb-arrow-right"></span></div> -->
        	<div class="htb-tit-right afterDay"><span>后一天</span><span class="htb-arrow-right"></span></div>
        </section>
        <section class="htb-report-push">
        	<ul>
            	<li><span class="htb-store-name merchantName">水木锦堂国贸店</span></li>
                <li>
                	<p class="link-wifi-member">本周共有 <span class="htb-nums-def  loginCount">40</span> 人连接了WIFI</p>
                    <p class="add-wifi-member">其中有 <span class="htb-nums-clor totalCount">30</span> 位新增WIFI会员</p>
                    <p class="htb-tj-time">统计周期：<span class="startTime">2014-12-15</span> 至 <span class="endTime">2014-12-19</span></p>
                </li>
            </ul>
            <div class="htb-member-chart">
            	<h1>每天会员发展情况环比图：</h1>
                <!-- <p><img src="images/t01.png" width="100%"/></p> -->
                <div id="container"></div>
            </div>
        </section>
    </div>
</div>
<c:set var="merchantId" value="${param.merchantId}"></c:set>
<c:set var="brandId" value="${param.brandId}"></c:set>
</body>
<script language="javascript" src="/client/origin/asset/third/highcharts/js/highcharts.js"></script>
<script language="javascript" src="/js/base.js"></script>
<script>
 var merchantId = '${merchantId}'==null?0:'${merchantId}';
 var brandId = '${brandId}'==null?0:'${brandId}';

var currentDateStr = $(".datePicker").val();
$(".datePicker").live("change",function(){
	currentDateStr=$(this).val();
	load();
});
//前一天计算
$(document).on("tap",".lastDay",function(){
	currentDateStr=getPrevNextDay(currentDateStr, -1);
	$('.datePicker').val(currentDateStr);
	load();
});
//后一天计算
$(document).on("tap",".afterDay",function(){
	currentDateStr=getPrevNextDay(currentDateStr, 1);
	$('.datePicker').val(currentDateStr);
	load();
});

load();
function load(){
	var startDate = getPrevNextDay(currentDateStr, -6);
	var startTime =new Date(startDate).getTime() ;
	var endTime = new Date(currentDateStr).getTime();
	var data = {
	    merchantId:merchantId,
	    brandId:brandId,
	    startTime:startTime,
        endTime:endTime
	};
	$.ajax({
		type : "POST",
		url :  '/controller/member/memberAddStatic.do',
		cache : false,
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify(data),
		dataType : "json",
		success : function(retObj) {
			$(".merchantName").text(retObj.data.merchantName);
			$(".loginCount").text(retObj.data.loginCount);
			$(".totalCount").text(retObj.data.totalCount);
			$(".startTime").text(retObj.data.startTime);
			$(".endTime").text(retObj.data.endTime);
			
			//画图
			newlineChart(retObj.data.dateList,retObj.data.countList);
		},
		error : function(XMLHttpRequest) {
			console.log("failed.");
		}
	});
}	
	//画图
	function newlineChart(dateList,dataList){
		$('#container').highcharts({
	        chart: {
	            type: 'line'
	        },
	        title: {
	        	style:{
	        		fontSize: "14px"
	        	},
	        	text: "",
	        },
	        xAxis: {
	            categories: dateList
	        },
	        yAxis: {
		    	 title: {
		             text: "新增会员"
		         },
	        },
	        tooltip: {
	            enabled: false,
	            formatter: function() {
	                return '<b>'+ this.series.name +'</b><br/>'+this.x +': '+ this.y;
	            }
	        },
	        plotOptions: {
	            line: {
	                dataLabels: {
	                    enabled: true
	                },
	                enableMouseTracking: false
	            }
	        },
	        legend: {
                //layout: 'vertical',
                itemStyle: {
                    "fontSize": "13px"
                },
                align: 'bottom',
                //verticalAlign: 'middle',
                borderWidth: 0,
                enabled: false
            },
            credits:{
            	enabled: false
            },
	        series:[{"data":dataList}]
	    });
	}
	
</script>
</html>