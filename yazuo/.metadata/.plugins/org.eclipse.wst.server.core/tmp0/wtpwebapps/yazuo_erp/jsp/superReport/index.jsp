<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
    <%@ include file="/common/meta.jsp"%>
    <link href="<%=basePath%>css/jquery-ui-1.10.4.custom.min.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=basePath%>js/jquery/jquery-ui-1.10.4.custom.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jquery/jquery.ui.datepicker-zh-CN.min.js"></script>
    <script src="<%=basePath%>js/highcharts/highcharts.js"></script>
    <script src="<%=basePath%>js/highcharts/modules/no-data-to-display.js"></script>
    <script src="<%=basePath%>js/moment.min.js"></script>
</head>
<body class="page-super-report">
    <div class="page">
        <div class="page-hd">
            <h3 class="page-title">综合数据</h3>
        </div>
        <div class="page-bd">
            <div class="page-bd-tbar">
                <jsp:useBean id="now" class="java.util.Date" />
                <span class="report-meta">数据统计截止至：<fmt:formatDate value='${now}' type="date" pattern="yyyy-MM-dd HH:mm:ss" /> </span>
            </div>
            <div class="page-bd-inner">
                <div class="report-box user-brand-box">
                    <div class="report-title">
                        <h4>用户和商户数据统计&nbsp;&nbsp;</h4>
                    </div>
                    <div class="report-box-inner">
                        <ul class="report-nav fn-clear">
                            <li class="nav-item fn-left">
                                <ul class="sub-nav-list fn-clear">
                                    <li class="sub-nav-item fn-left">
                                        <p class="label-title">总用户</p>
                                        <p class="num-field">${userData.total['总用户']}人</p>
                                    </li>
                                    <li class="sub-nav-item last-sub-nav-item fn-left">
                                        <p class="label-title">今天新增</p>
                                        <p class="num-field">${userData.total['今日新增']}人</p>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item fn-left">
                                <ul class="sub-nav-list fn-clear">
                                    <li class="sub-nav-item fn-left">
                                        <p class="label-title">总品牌</p>
                                        <p class="num-field">${userData.total['总品牌']}家</p>
                                    </li>
                                    <li class="sub-nav-item sub-separator fn-left">
                                        <p class="label-title">今天新增</p>
                                        <p class="num-field">${userData.total['新增品牌']}家</p>
                                    </li>
                                    <li class="sub-nav-item fn-left">
                                        <p class="label-title">总门店</p>
                                        <p class="num-field">${userData.total['总门店']}家</p>
                                    </li>
                                    <li class="sub-nav-item last-sub-nav-item fn-left">
                                        <p class="label-title">今天新增</p>
                                        <p class="num-field">${userData.total['新增门店']}家</p>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                        <ul class="report-panel">
                            <li class="panel-item">
                                <div class="report-grid-wrapper">
                                    <table class="user-grid" cellpadding="0" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>&nbsp;</th>
                                                <th>总用户</th>
                                                <th>新增用户</th>
                                                <th>登录人数</th>
                                                <th>登录人次</th>
                                                <th>活跃用户比</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:set var="row" value="${userData.statistics['今天']}"/>
                                            <tr class="odd">
                                              <td>今天</td>
                                              <td><fmt:formatNumber type="number" value="${row.totalUserCount}" maxFractionDigits="0"/></td>
                                              <td><fmt:formatNumber type="number" value="${row.newUserCount}" maxFractionDigits="0"/></td>
                                              <td><fmt:formatNumber type="number" value="${row.loginUserCount}" maxFractionDigits="0"/></td>
                                              <td><fmt:formatNumber type="number" value="${row.loginTimes}" maxFractionDigits="0"/></td>
                                              <td><fmt:formatNumber type="number" value="${row.activeUserProportion*100}" maxFractionDigits="2"/>%</td>
                                          </tr>
                                            <c:set var="row" value="${userData.statistics['昨天']}"/>
                                          <tr class="even">
                                              <td>昨天</td>
                                              <td><fmt:formatNumber type="number" value="${row.totalUserCount}" maxFractionDigits="0"/></td>
                                              <td><fmt:formatNumber type="number" value="${row.newUserCount}" maxFractionDigits="0"/></td>
                                              <td><fmt:formatNumber type="number" value="${row.loginUserCount}" maxFractionDigits="0"/></td>
                                              <td><fmt:formatNumber type="number" value="${row.loginTimes}" maxFractionDigits="0"/></td>
                                              <td><fmt:formatNumber type="number" value="${row.activeUserProportion*100}" maxFractionDigits="2"/>%</td>
                                          </tr>
                                          <c:set var="row" value="${userData.statistics['近90天平均']}"/>
                                          <tr class="odd">
                                              <td>近90天平均</td>
                                              <td> - </td>
                                              <td><fmt:formatNumber type="number" value="${row.newUserCount}" maxFractionDigits="2"/></td>
                                              <td><fmt:formatNumber type="number" value="${row.loginUserCount}" maxFractionDigits="2"/></td>
                                              <td><fmt:formatNumber type="number" value="${row.loginTimes}" maxFractionDigits="2"/></td>
                                              <td> - </td>
                                          </tr>
                                          <c:set var="row" value="${userData.statistics['历史最高']}"/>
                                          <tr class="even">
                                              <td>历史最高</td>
                                              <td> - </td>
                                              <td><fmt:formatNumber type="number" value="${row.newUserCount}" maxFractionDigits="2"/><br/>${row.newUserCountDate}</td>
                                              <td><fmt:formatNumber type="number" value="${row.loginUserCount}" maxFractionDigits="2"/><br/>${row.loginUserCountDate}</td>
                                              <td><fmt:formatNumber type="number" value="${row.loginTimes}" maxFractionDigits="2"/><br/>${row.loginTimesDate}</td>
                                              <td> - </td>
                                          </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="report-chart-wrapper">
                                    <div class="report-chart-title">用户分布图表</div>
                                    <div class="report-chart-tbar">
                                        <select class="user-list cate-list">
                                            <option value="1" selected="selected">总用户</option>
                                            <option value="2">新增用户</option>
                                        </select>&nbsp;&nbsp;<select class="date-list">
                                            <option value="0" selected="selected">今天</option>
                                            <option value="-1">昨天</option>
                                            <option value="-10000">指定日期</option>
                                        </select><span class="custom-date-wrapper fn-hide">&nbsp;&nbsp;<input type="text" class="date-input text-field" readonly="readonly"/></span>
                                    </div>
                                    <div id="user-chart" class="report-chart-box">

                                    </div>
                                </div>
                            </li>
                            <li class="panel-item">
                                <div class="report-grid-wrapper">
                                    <table class="brand-grid" cellpadding="0" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>&nbsp;</th>
                                                <th>总品牌</th>
                                                <th>新增品牌</th>
                                                <th>活跃品牌数</th>
                                                <th>活跃品牌占比</th>
                                                <th>总门店</th>
                                                <th>新增门店</th>
                                                <th>活跃门店数</th>
                                                <th>活跃门店占比</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                           <tr class="odd">
                                                <c:set var="row" value="${brandData.stat['今天']}"/>
                                                <td>今天</td>
                                                <td><fmt:formatNumber type="number" value="${row.totalBrandCount}" maxFractionDigits="0"/></td>
                                                <td><fmt:formatNumber type="number" value="${row.newBrandCount}" maxFractionDigits="0"/></td>
                                                <td><fmt:formatNumber type="number" value="${row.activeBrandCount}" maxFractionDigits="0"/></td>
                                                <td><fmt:formatNumber type="number" value="${row.activeBrandProportion * 100}" maxFractionDigits="2"/>%</td>
                                                <td><fmt:formatNumber type="number" value="${row.totalFaceShopCount}" maxFractionDigits="0"/></td>
                                                <td><fmt:formatNumber type="number" value="${row.newFaceShopCount}" maxFractionDigits="0"/></td>
                                                <td><fmt:formatNumber type="number" value="${row.activeFaceShopCount}" maxFractionDigits="0"/></td>
                                                <td><fmt:formatNumber type="number" value="${row.activeFaceShopProportion * 100}" maxFractionDigits="2"/>%</td>
                                            </tr>
                                            <tr class="even">
                                                <c:set var="row" value="${brandData.stat['昨天']}"/>
                                                <td>昨天</td>
                                                <td><fmt:formatNumber type="number" value="${row.totalBrandCount}" maxFractionDigits="0"/></td>
                                                <td><fmt:formatNumber type="number" value="${row.newBrandCount}" maxFractionDigits="0"/></td>
                                                <td><fmt:formatNumber type="number" value="${row.activeBrandCount}" maxFractionDigits="0"/></td>
                                                <td><fmt:formatNumber type="number" value="${row.activeBrandProportion * 100}" maxFractionDigits="2"/>%</td>
                                                <td><fmt:formatNumber type="number" value="${row.totalFaceShopCount}" maxFractionDigits="0"/></td>
                                                <td><fmt:formatNumber type="number" value="${row.newFaceShopCount}" maxFractionDigits="0"/></td>
                                                <td><fmt:formatNumber type="number" value="${row.activeFaceShopCount}" maxFractionDigits="0"/></td>
                                                <td><fmt:formatNumber type="number" value="${row.activeFaceShopProportion * 100}" maxFractionDigits="2"/>%</td>
                                            </tr>
                                            <tr class="odd">
                                                <c:set var="row" value="${brandData.stat['近90天平均']}"/>
                                                <td>近90天平均</td>
                                                <td> - </td>
                                                <td><fmt:formatNumber type="number" value="${row.newBrandCount}" maxFractionDigits="0"/></td>
                                                <td> - </td>
                                                <td> - </td>
                                                <td> - </td>
                                                <td><fmt:formatNumber type="number" value="${row.newFaceShopCount}" maxFractionDigits="0"/></td>
                                                <td> - </td>
                                                <td> - </td>
                                            </tr>
                                            <tr class="even">
                                                <c:set var="row" value="${brandData.stat['历史最高']}"/>
                                                <td>历史最高</td>
                                                <td> - </td>
                                                <td><fmt:formatNumber type="number" value="${row.newBrandCount}" maxFractionDigits="0"/><br/>${row.newBrandCountDate}</td>
                                                <td><fmt:formatNumber type="number" value="${row.activeBrandCount}" maxFractionDigits="0"/><br/>${row.activeBrandCountDate}</td>
                                                <td> - </td>
                                                <td> - </td>
                                                <td><fmt:formatNumber type="number" value="${row.newFaceShopCount}" maxFractionDigits="0"/><br/>${row.newFaceShopCountDate}</td>
                                                <td><fmt:formatNumber type="number" value="${row.activeFaceShopCount}" maxFractionDigits="0"/><br/>${row.activeFaceShopCountDate}</td>
                                                <td> - </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="report-chart-wrapper">
                                    <div class="report-chart-title">品牌分布图表</div>
                                    <div class="report-chart-tbar">
                                        <select class="brand-list cate-list">
                                            <option value="1" selected="selected">总品牌</option>
                                            <option value="2">新增品牌</option>
                                        </select>&nbsp;&nbsp;<select class="date-list">
                                            <option value="0" selected="selected">今天</option>
                                            <option value="-1">昨天</option>
                                            <option value="-10000">指定日期</option>
                                        </select><span class="custom-date-wrapper fn-hide">&nbsp;&nbsp;<input type="text" class="date-input text-field" readonly="readonly"/></span>
                                    </div>
                                    <div id="brand-chart" class="report-chart-box">

                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <!-- 功能使用统计 -->
                <div class="report-box action-box">
                    <div class="report-title">
                        <h4>功能使用统计&nbsp;&nbsp;</h4>
                    </div>
                    <c:set var="nameStr" value="SEND_REPORT,VIEW_REPORT,VIEW_STATISTICS,SEND_COMMEND,VIEW_COMMEND,VIEW_NOTICE,VIEW_MARK"/>
                    <c:set var="names" value="${fn:split(nameStr,',')}"/>
                    <div class="report-box-inner">
                        <ul class="report-nav fn-clear">
                            <li class="user-num-nav nav-item fn-left">
                                <p>人数</p>
                            </li>
                            <li class="count-num-nav nav-item fn-left">
                                <p>次数</p>
                            </li>
                        </ul>
                        <ul class="report-panel">
                            <li class="panel-item">
                                <div class="report-grid-wrapper">
                                    <table class="user-num-grid" cellpadding="0" cellspacing="0">
                                        <thead>
                                        <tr>
                                            <th>&nbsp;</th>
                                            <th>发送日报</th>
                                            <th>查看日报</th>
                                            <th>数据统计</th>
                                            <th>发送评论</th>
                                            <th>查看评论</th>
                                            <th>查看通知</th>
                                            <th>标注</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                         <tr class="odd">
                                            <td>今天</td>
                                            <c:forEach items="${names}" var="name">
                                               <td>
                                                <c:forEach items="${userCountData['今天']}" var="item">
                                                   <c:if test='${item.name eq name}'>
                                                     <fmt:formatNumber type="number" value="${item.count}" maxFractionDigits="0"/>
                                                   </c:if>
                                                </c:forEach>
                                               </td>
                                            </c:forEach>
                                        </tr>
                                         <tr class="even">
                                            <td>昨天</td>
                                            <c:forEach items="${names}" var="name">
                                               <td>
                                                <c:forEach items="${userCountData['昨天']}" var="item">
                                                   <c:if test='${item.name eq name}'>
                                                     <fmt:formatNumber type="number" value="${item.count}" maxFractionDigits="0"/>
                                                   </c:if>
                                                </c:forEach>
                                               </td>
                                            </c:forEach>
                                        </tr>
                                         <tr class="odd">
                                            <td>近90天平均</td>
                                            <c:forEach items="${names}" var="name">
                                               <td>
                                                <c:forEach items="${userCountData['近90天平均']}" var="item">
                                                   <c:if test='${item.name eq name}'>
                                                     <fmt:formatNumber type="number" value="${item.count}" maxFractionDigits="1"/>
                                                   </c:if>
                                                </c:forEach>
                                               </td>
                                            </c:forEach>
                                        </tr>
                                         <tr class="even">
                                            <td>历史最高</td>
                                            <c:forEach items="${names}" var="name">
                                               <td>
                                                <c:forEach items="${userCountData['历史最高']}" var="item">
                                                   <c:if test='${item.name eq name}'>
                                                     <fmt:formatNumber type="number" value="${item.count}" maxFractionDigits="0"/>
                                                      <br/>
                                                      ${item.date}
                                                   </c:if>
                                                </c:forEach>
                                               </td>
                                            </c:forEach>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="report-chart-wrapper">
                                    <div class="report-chart-title">功能使用占比</div>
                                    <div class="report-chart-tbar">
                                        <select class="cate-list">
                                            <option value="1" selected="selected">全部功能</option>
                                            <option value="SEND_REPORT">发送日报</option>
                                            <option value="VIEW_REPORT">查看日报</option>
                                            <option value="VIEW_STATISTICS">数据统计</option>
                                            <option value="SEND_COMMEND">发送评论</option>
                                            <option value="VIEW_COMMEND">查看评论</option>
                                            <option value="VIEW_NOTICE">查看通知</option>
                                            <option value="VIEW_MARK">标注</option>
                                        </select>&nbsp;&nbsp;<select class="date-list">
                                        <option value="0" selected="selected">今天</option>
                                        <option value="-1">昨天</option>
                                        <option value="-10000">指定日期</option>
                                    </select><span class="custom-date-wrapper fn-hide">&nbsp;&nbsp;<input type="text" class="date-input text-field" readonly="readonly"/></span><!--&nbsp;&nbsp;<select class="display-type-list">
                                        <option value="1">按次数显示</option>
                                        <option value="2" selected="selected">按人数显示</option>
                                    </select>-->
                                    </div>
                                    <div id="user-num-chart" class="report-chart-box">

                                    </div>
                                </div>
                            </li>
                            <li class="panel-item">
                                <div class="report-grid-wrapper">
                                    <table class="count-num-grid" cellpadding="0" cellspacing="0">
                                        <thead>
                                        <tr>
                                            <th>&nbsp;</th>
                                            <th>发送日报</th>
                                            <th>查看日报</th>
                                            <th>数据统计</th>
                                            <th>发送评论</th>
                                            <th>查看评论</th>
                                            <th>查看通知</th>
                                            <th>标注</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                          <tr class="odd">
                                            <td>今天</td>
                                            <c:forEach items="${names}" var="name">
                                               <td>
                                                <c:forEach items="${userTimeData['今天']}" var="item">
                                                   <c:if test='${item.name eq name}'>
                                                     <fmt:formatNumber type="number" value="${item.count}" maxFractionDigits="0"/>
                                                   </c:if>
                                                </c:forEach>
                                               </td>
                                            </c:forEach>
                                        </tr>
                                         <tr class="even">
                                            <td>昨天</td>
                                            <c:forEach items="${names}" var="name">
                                               <td>
                                                <c:forEach items="${userTimeData['昨天']}" var="item">
                                                   <c:if test='${item.name eq name}'>
                                                     <fmt:formatNumber type="number" value="${item.count}" maxFractionDigits="0"/>
                                                   </c:if>
                                                </c:forEach>
                                               </td>
                                            </c:forEach>
                                        </tr>
                                         <tr class="odd">
                                            <td>近90天平均</td>
                                            <c:forEach items="${names}" var="name">
                                               <td>
                                                <c:forEach items="${userTimeData['近90天平均']}" var="item">
                                                   <c:if test='${item.name eq name}'>
                                                   <fmt:formatNumber type="number" value="${item.count}" maxFractionDigits="1"/>
                                                   </c:if>
                                                </c:forEach>
                                               </td>
                                            </c:forEach>
                                        </tr>
                                         <tr class="even">
                                            <td>历史最高</td>
                                            <c:forEach items="${names}" var="name">
                                               <td>
                                                <c:forEach items="${userTimeData['历史最高']}" var="item">
                                                   <c:if test='${item.name eq name}'>
                                                     <fmt:formatNumber type="number" value="${item.count}" maxFractionDigits="0"/>
                                                     <br/>
                                                     ${item.date}
                                                   </c:if>
                                                </c:forEach>
                                               </td>
                                            </c:forEach>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="report-chart-wrapper">
                                    <div class="report-chart-title">功能使用占比</div>
                                    <div class="report-chart-tbar">
                                        <select class="cate-list">
                                            <option value="1" selected="selected">全部功能</option>
                                            <option value="SEND_REPORT">发送日报</option>
                                            <option value="VIEW_REPORT">查看日报</option>
                                            <option value="VIEW_STATISTICS">数据统计</option>
                                            <option value="SEND_COMMEND">发送评论</option>
                                            <option value="VIEW_COMMEND">查看评论</option>
                                            <option value="VIEW_NOTICE">查看通知</option>
                                            <option value="VIEW_MARK">标注</option>
                                        </select>&nbsp;&nbsp;<select class="date-list">
                                        <option value="0" selected="selected">今天</option>
                                        <option value="-1">昨天</option>
                                        <option value="-10000">指定日期</option>
                                    </select><span class="custom-date-wrapper fn-hide">&nbsp;&nbsp;<input type="text" class="date-input text-field" readonly="readonly"/></span><!--&nbsp;&nbsp;<select class="display-type-list">
                                        <option value="1" selected="selected">按次数显示</option>
                                        <option value="2">按人数显示</option>
                                    </select>-->
                                    </div>
                                    <div id="count-num-chart" class="report-chart-box">

                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        jQuery(function($){
            var bodyEl=$('body'),
                userBrandBoxEl=$('.user-brand-box',bodyEl),
                actionBoxEl=$('.action-box',bodyEl);
            //tabs功能
            bodyEl.on('click','.report-nav .nav-item',function(){
                var navItemEl=$(this),
                    boxEl=navItemEl.closest('.report-box'),
                    panelWEl=$('.report-panel',boxEl),
                    currentPanelEl=$('.panel-item',panelWEl).eq(navItemEl.index());
                if(!navItemEl.hasClass('nav-active')){
                    navItemEl.addClass('nav-active').siblings().removeClass('nav-active');
                    $('.panel-item',panelWEl).removeClass('panel-active');
                    currentPanelEl.addClass('panel-active');
                    //触发自定义事件switched
                    boxEl.trigger('switched',currentPanelEl);
                }
            });

            //用户和商户统计初始化
            userBrandBoxEl.on('switched',function(evt,panelEl){
                //初始化charts
                var chartBoxWEl=$('.report-chart-wrapper',panelEl),
                    chartBoxEl=$('.report-chart-box',panelEl),
                    chart,
                    chartContainerId,
                    requestUrl;
                panelEl=$(panelEl);
                if(!panelEl.data('isInit')){
                    chartContainerId=chartBoxEl.attr('id');
                    if(chartContainerId=="brand-chart"){    //品牌图表
                        requestUrl="<c:url value='/superReport/getBrandInfo.do'/>";
                        //requestUrl="#";
                        chart = new Highcharts.Chart({
                            chart: {
                                renderTo: chartContainerId,
                                type: 'column'
                            },lang: {
                                noData:'没有找到您要的数据'
                            },
                            title: {
                                text: '近7日 总品牌数量 对比'
                            },
                            colors: ['#e0769b', '#e0ad76', '#dae076', '#66ccb5', '#76cae0','#76a9e0', '#7678e0'],
                            xAxis: {
                                categories: ['品牌','分公司','门店']
                            },
                            credits:{
                                enabled:false
                            },
                            yAxis: {
                                min: 0,
                                title: {
                                    text: ''
                                }
                            },
                            tooltip: {
                                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                                        '<td style="padding:0"><b>{point.y}</b></td></tr>',
                                footerFormat: '</table>',
                                shared: true,
                                useHTML: true
                            },plotOptions: {
                                column: {
                                    pointPadding: 0.2,
                                    borderWidth: 0
                                }
                            },
                            series: [{
                                name: '',
                                data: []

                            }, {
                                name: '',
                                data: []

                            }, {
                                name: '',
                                data: []

                            }, {
                                name: '',
                                data: []

                            }, {
                                name: '',
                                data: []

                            }, {
                                name: '',
                                data: []

                            }, {
                                name: '',
                                data: []

                            }]
                        });
                    }else{
                        requestUrl="<c:url value='/superReport/getUserInfo.do'/>";
                        chart = new Highcharts.Chart({
                            chart: {
                                renderTo: chartContainerId,
                                plotBackgroundColor: null,
                                plotBorderWidth: null,
                                plotShadow: false
                            },
                            title: {
                                text: '2014-04-21 总用户数量'
                            },lang: {
                                noData:'没有找到您要的数据'
                            },
                            tooltip: {
                                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                            },
                            credits:{
                                enabled:false
                            },legend:{
                              borderWidth:1,
                              backgroundColor: '#FAFAFA',
                              itemMarginTop:5,
                              itemMarginBottom:5,
                              verticalAlign:'middle',
                              align:'right',
                              layout:'vertical',
                              symbolPadding:5
                            },
                            colors: ['#e0ad76', '#76cae0', '#76a9e0', '#7678e0', '#dae076','#66ccb5'],
                            plotOptions: {
                                pie: {
                                    showInLegend:true,
                                    allowPointSelect: true,
                                    cursor: 'pointer',
                                    dataLabels: {
                                        enabled: true,
                                        format: '<span style="color:{series.color}"><b>{point.name}</b>:{point.y}人<br/> 占{point.percentage:.1f}%</span>',
                                        style: {
                                            color: '#666666'
                                        }
                                    }
                                }
                            },
                            series: [{
                                type: 'pie',
                                name: '用户',
                                data: [
                                    /*['40人',   45.0],
                                    ['80人',26.8],
                                    {
                                        name: 'Chrome',
                                        y: 12.8,
                                        sliced: true,
                                        selected: true
                                    },
                                    ['160人',8.5],
                                    ['150人',6.2]*/
                                ]
                            }]
                        });
                    }

                    var cateListEl=$('.cate-list',chartBoxWEl),
                        dateListEl=$('.date-list',chartBoxWEl);
                    //初始化自定义时间组件
                    var dateInputWEl=$('.custom-date-wrapper',chartBoxWEl),
                        dateInputEl=$('.date-input',dateInputWEl);
                    dateInputEl.datepicker({
                        maxDate: ' +0d',
                        dateFormat: "yy-mm-dd",
                        onSelect:function(selectedDate){
                            var meEl=$(this),
                                cateValue=cateListEl.val();
                            redrawChart({
                                "url":requestUrl,
                                "data":{
                                    "isNew":getBooleanFromCate(cateValue),
                                    "date":selectedDate
                                }
                            },chart,function(){
                                if(chartContainerId=="user-chart"){
                                    this.setTitle({
                                        text: selectedDate+' '+$('option[value="'+cateValue+'"]',cateListEl).text()+'数量'
                                    },{
                                        text:''
                                    },false);
                                }
                            });
                        }
                    });

                    chartBoxWEl.on('change','.cate-list,.date-list',function(){
                        var meEl=$(this),
                            cateValue=cateListEl.val(),
                            dateValue=dateListEl.val(),
                            customDateValue,
                            ajaxOpts={};
                        //拼接请求数据
                        ajaxOpts["cate"]=cateValue;
                        //控制datepicker显示与否
                        if(meEl.hasClass('date-list')){
                            if(dateValue=="-10000"){ //自定义时间
                                dateInputWEl.show();
                                dateInputEl.focus();
                            }else{
                                dateInputWEl.hide();
                                dateInputEl.val("");
                            }
                        }
                        customDateValue=dateInputEl.val();
                        //当自定义时间不为空时，重画chart
                        if(dateValue!="-10000"||(dateValue=="-10000"&&customDateValue.length>0)){
                            //拼接ajax参数
                            _.extend(ajaxOpts,{
                                "url":requestUrl,
                                "data":{
                                    "isNew":getBooleanFromCate(cateValue),
                                    "date":customDateValue.length>0?customDateValue:getRealDate(dateValue)
                                }
                            });
                            redrawChart(ajaxOpts,chart,function(){
                                if(chartContainerId=="user-chart"){
                                    this.setTitle({
                                        text: ajaxOpts.data.date+' '+$('option[value="'+cateValue+'"]',cateListEl).text()+'数量'
                                    },{
                                        text:''
                                    },false);
                                }
                            });
                        }

                    });
                    //触发date list的change事件
                    $('.date-list',chartBoxWEl).change();
                    //保证自适应宽度
                    setTimeout(function(){
                        $(window).resize();
                    },300);
                    panelEl.data('isInit',true);
                }
            });
            //功能使用统计初始化
            actionBoxEl.on('switched',function(evt,panelEl){
                //初始化charts
                var chartBoxWEl=$('.report-chart-wrapper',panelEl),
                    chartBoxEl=$('.report-chart-box',panelEl),
                    chart,
                    chartContainerId,
                    requestUrl;
                panelEl=$(panelEl);
                if(!panelEl.data('isInit')){
                    chartContainerId=chartBoxEl.attr('id');
                    if(chartContainerId=="user-num-chart"){    //按人数图表
                        requestUrl="<c:url value='/superReport/getUserCountByFunc.do'/>";
                        chart = new Highcharts.Chart({
                            chart: {
                                renderTo: chartContainerId,
                                plotBackgroundColor: null,
                                plotBorderWidth: null,
                                plotShadow: false
                            },lang: {
                                noData:'没有找到您要的数据'
                            },
                            title: {
                                text: ''
                            },
                            tooltip: {
                                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                            },
                            credits:{
                                enabled:false
                            },legend:{
                                borderWidth:1,
                                backgroundColor: '#FAFAFA',
                                itemMarginTop:5,
                                itemMarginBottom:5,
                                verticalAlign:'middle',
                                align:'right',
                                layout:'vertical',
                                symbolPadding:5
                            },
                            colors: ['#e0ad76', '#76cae0', '#76a9e0', '#7678e0', '#dae076','#66ccb5'],
                            plotOptions: {
                                pie: {
                                    showInLegend:true,
                                    allowPointSelect: true,
                                    cursor: 'pointer',
                                    dataLabels: {
                                        enabled: true,
                                        format: '<span style="color:{series.color}"><b>{point.name}</b>:{point.y}人<br/> 占{point.percentage:.1f}%</span>',
                                        style: {
                                            color: '#666666'
                                        }
                                    }
                                }
                            },
                            series: [{
                                type: 'pie',
                                name: '用户占比',
                                data: [
                                    /*['40人',45.0],
                                    ['80人',26.8],
                                    {
                                        name: 'Chrome',
                                        y: 12.8,
                                        sliced: true,
                                        selected: true
                                    },
                                    ['160人',8.5],
                                    ['150人',6.2]*/
                                ]
                            }]
                        });
                    }else{
                        requestUrl="<c:url value='/superReport/getUserTimesByFunc.do'/>";
                        chart = new Highcharts.Chart({
                            chart: {
                                renderTo: chartContainerId,
                                plotBackgroundColor: null,
                                plotBorderWidth: null,
                                plotShadow: false
                            },lang: {
                                noData:'没有找到您要的数据'
                            }, title: {
                                text: ''
                            },
                            tooltip: {
                                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                            },
                            credits:{
                                enabled:false
                            },legend:{
                                borderWidth:1,
                                backgroundColor: '#FAFAFA',
                                itemMarginTop:5,
                                itemMarginBottom:5,
                                verticalAlign:'middle',
                                align:'right',
                                layout:'vertical',
                                symbolPadding:5
                            },
                            colors: ['#e0ad76', '#76cae0', '#76a9e0', '#7678e0', '#dae076','#66ccb5'],
                            plotOptions: {
                                pie: {
                                    showInLegend:true,
                                    allowPointSelect: true,
                                    cursor: 'pointer',
                                    dataLabels: {
                                        enabled: true,
                                        format: '<span style="color:{series.color}"><b>{point.name}</b>:{point.y}人<br/> 占{point.percentage:.1f}%</span>',
                                        style: {
                                            color: '#666666'
                                        }
                                    }
                                }
                            },
                            series: [{
                                type: 'pie',
                                name: '用户占比',
                                data: [
                                    /*['40人',45.0],
                                    ['80人',26.8],
                                    {
                                        name: 'Chrome',
                                        y: 12.8,
                                        sliced: true,
                                        selected: true
                                    },
                                    ['160人',8.5],
                                    ['150人',6.2]*/
                                ]
                            }]
                        });
                    }

                    var cateListEl=$('.cate-list',chartBoxWEl),
                        dateListEl=$('.date-list',chartBoxWEl);
                        //displayTypeEl=$('.display-type-list',chartBoxWEl);
                    //初始化自定义时间组件
                    var dateInputWEl=$('.custom-date-wrapper',chartBoxWEl),
                        dateInputEl=$('.date-input',dateInputWEl);
                    dateInputEl.datepicker({
                        maxDate: ' +0d',
                        dateFormat: "yy-mm-dd",
                        onSelect:function(selectedDate){
                            var meEl=$(this),
                                cateValue=cateListEl.val();
                            redrawChart({
                                "url":requestUrl,
                                "data":{
                                    "funcName":cateValue,
                                    "date":selectedDate
                                }
                            },chart,function(){
                                this.setTitle({
                                    text: selectedDate+' '+$('option[value="'+cateValue+'"]',cateListEl).text()+' 使用'+(chartContainerId=="user-num-chart"?"人数":"次数")
                                },{
                                    text:''
                                },false);
                            });
                        }
                    });

                    chartBoxWEl.on('change','.cate-list,.date-list',function(){
                        var meEl=$(this),
                                cateValue=cateListEl.val(),
                                dateValue=dateListEl.val(),
                                customDateValue,
                                ajaxOpts={};
                        //拼接请求数据
                        ajaxOpts["cate"]=cateValue;
                        //控制datepicker显示与否
                        if(meEl.hasClass('date-list')){
                            if(dateValue=="-10000"){ //自定义时间
                                dateInputWEl.show();
                                dateInputEl.focus();
                            }else{
                                dateInputWEl.hide();
                                dateInputEl.val("");
                            }
                        }
                        customDateValue=dateInputEl.val();
                        //当自定义时间不为空时，重画chart
                        if(dateValue!="-10000"||(dateValue=="-10000"&&customDateValue.length>0)){
                            //拼接ajax参数
                            _.extend(ajaxOpts,{
                                "url":requestUrl,
                                "data":{
                                    "funcName":cateValue,
                                    "date":customDateValue.length>0?customDateValue:getRealDate(dateValue)
                                }
                            });
                            redrawChart(ajaxOpts,chart,function(){
                                this.setTitle({
                                    text: ajaxOpts.data.date+' '+$('option[value="'+cateValue+'"]',cateListEl).text()+' 使用'+(chartContainerId=="user-num-chart"?"人数":"次数")
                                },{
                                    text:''
                                },false);
                            });
                        }
                    });
                    //触发date list的change事件
                    $('.date-list',chartBoxWEl).change();
                    //按人数和按次数切换
                    /*displayTypeEl.on('change',function(){
                        var displayType=displayTypeEl.val();
                        if(displayType=="1"){   //按次数显示
                            $('.count-num-nav',actionBoxEl).click();
                            displayTypeEl.val("2");
                        }else{
                            $('.user-num-nav',actionBoxEl).click();
                            displayTypeEl.val("1");
                        }
                    });*/
                    //保证自适应宽度
                    setTimeout(function(){
                        $(window).resize();
                    },300);
                    panelEl.data('isInit',true);
                }
            });
            //激活第一个tab
            $('.report-nav .nav-item',userBrandBoxEl).eq(0).click();
            $('.report-nav .nav-item',actionBoxEl).eq(0).click();

            //私有函数放下面
            function redrawChart(ajaxOpts,chart,interceptor){
                var series=chart.series;
                $.ajax(_.extend({
                    "type":"get",
                    "dataType":"json",
                    "success":function(responseData){
                        var data,
                            seriesData;
                        if(responseData.success){
                            data=responseData.data;
                            seriesData=data.series;
                            //设置对应的series
                            _.each(series,function(seriesItem,i){
                                //seriesItem.setData(seriesStore[i].data.slice(val,val+maxShowCateNum),false);
                                seriesItem.update(seriesData[i],false);
                            });
                            interceptor&&interceptor.call(chart,data);
                            //重画
                            chart.redraw();
                        }
                    }
                },ajaxOpts));
            }

            /**
             * 根据dayFlag返回实际日期字符串
             * @param dayFlag
             */
            function getRealDate(dayFlag){
                return moment().add('days', dayFlag/1).format('YYYY-MM-DD');
            }

            /**
             * 用户和商户数据统计类别翻译
             * @param cateValue
             */
            function getBooleanFromCate(cateValue){
                return cateValue==1?false:true;
            }
        });
    </script>
</body>
</html>