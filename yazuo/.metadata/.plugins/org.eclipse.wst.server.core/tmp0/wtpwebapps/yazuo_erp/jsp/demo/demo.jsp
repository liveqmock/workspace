<%@ include file="/common/meta.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />

<script>

var getEvaluate = function(score) {
    jQuery("#score").val(score);
    if(score == 4) {
        document.getElementById("form1").action = "<%=basePath%>/statistical/evaluateDaily/goodEvaluateRate.do";
        document.getElementById("form1").submit();
    } else {
        document.getElementById("form1").action = "<%=basePath%>/statistical/evaluateDaily/evaluateRate.do";
        document.getElementById("form1").submit();
    }
};
var getStored = function(flag) {
    if(flag == 1) {//新增储值金额
        document.getElementById("form1").action = "<%=basePath%>/statistical/reportDataDaily/storePayMerchant.do";
        document.getElementById("form1").submit();
    } else if (flag == 5) {//营销收益
        document.getElementById("form1").action = "<%=basePath%>/statistical/reportDataDaily/marketingIncomeMerchant.do";
        document.getElementById("form1").submit();
    }
};
var getMemberType = function(type,typeName) {
    jQuery("#memberType").val(type);
    jQuery("#typeName").val(typeName);
    document.getElementById("form1").action = "<%=basePath%>/statistical/member/memberType.do";
    document.getElementById("form1").submit();
};

var getDaily = function(flag) {
    jQuery("#flag").val(flag);
    document.getElementById("form1").action = "<%=basePath%>/statistical/statement/daily.do";
    document.getElementById("form1").submit();

};
var getMonthly = function(flag) {
    jQuery("#flag").val(flag);
    if(flag == 0) {
        document.getElementById("form1").action = "<%=basePath%>/statistical/statement/otherMonth.do";
        document.getElementById("form1").submit();
    } else {
        document.getElementById("form1").action = "<%=basePath%>/statistical/statement/monthly.do";
        document.getElementById("form1").submit();
    }

};


</script>

<title>运营日报</title>
</head>
<body>
<form id="form1">

<!-- 常量信息 -->
<input id="brandId" name="brandId" type="hidden" value="${statdata.brandId }"/>
<input id="starttime" name="starttime" type="hidden" value="${statdata.starttime }"/>
<input id="endtime" name="endtime" type="hidden" value="${statdata.endtime }"/>
<input id="flag" name="flag" type="hidden" value="${statdata.flag }"/>

<!-- 评论级别 -->
<input id="score" name="score" type="hidden" />
<!-- 会员类型 -->
<input id="memberType" name="memberType" type="hidden" />
<!-- 类型名称 -->
<input id="typeName" name="typeName" type="hidden" />

<div class="wx">
    <section class="wx-top">
        <span class="crm_logo left"></span>
        <div class="wx_menu left">
        <c:if test="${statdata.flag == 0}">
            <a href="javascript:getDaily(0);" class="menu_sel">昨日</a>
            <a href="javascript:getDaily(1);" >今日</a>
        </c:if>
        <c:if test="${statdata.flag == 1}">
            <a href="javascript:getDaily(0);" >昨日</a>
            <a href="javascript:getDaily(1);" class="menu_sel">今日</a>
        </c:if>
            <a href="javascript:getMonthly(1);">本月</a>
            <a href="javascript:getMonthly(0)">往月</a>
        </div>
    </section>
    <section class="wx-monthly">
        <div class="mon_tit">运营日报(${statdata.starttime})<i></i></div>

        <section class="day_list">
            <ul id="ul_1">
              <li>
                  <c:if test="${rate.sum == 0}">
                        全部点评：<span class="green">${rate.sum}</span>
                  </c:if>

                 <c:if test="${rate.sum != 0}">
                    <a  href="javascript:getEvaluate(0);" class="block">全部点评：<span class="green">${rate.sum}</span><i class="right">&gt;</i></a>
                </c:if>
             </li>

             <li>
                   <c:if test="${rate.goodSum == 0}">
                        好       评：<span class="green">${rate.goodSum}</span>
                  </c:if>
                  <c:if test="${rate.goodSum != 0}">
                    <a  href="javascript:getEvaluate(1);" class="block">好       评：<span class="green">${rate.goodSum}</span><i class="right">&gt;</i></a>
                  </c:if>
             </li>

             <li>
                  <c:if test="${rate.generalSum == 0}">
                        中       评：<span class="green">${rate.generalSum}</span>
                  </c:if>
                 <c:if test="${rate.generalSum != 0}">
                    <a  href="javascript:getEvaluate(2);" class="block">中       评：<span class="green">${rate.generalSum}</span><i class="right">&gt;</i></a>
                 </c:if>
             </li>

             <li>
                   <c:if test="${rate.badSum == 0}">
                        差       评：<span class="green">${rate.badSum}</span>
                  </c:if>
                  <c:if test="${rate.badSum != 0}">
                    <a  href="javascript:getEvaluate(3);" class="block">差       评：<span class="green">${rate.badSum}</span><i class="right">&gt;</i></a>
                   </c:if>
             </li>

             <li>
             <c:if test="${rate.goodRate == 0}">
                    平  均  分：<span class="green">${rate.goodRate}</span>
             </c:if>
             <c:if test="${rate.goodRate != 0}">
                <a  href="javascript:getEvaluate(4);" class="block">平  均  分：<span class="green">${rate.goodRate}</span><i class="right">&gt;</i></a>
             </c:if>
             </li>
            </ul>
        </section>

<c:if test="${statdata.flag == 0}">
             <div class="mon_bread"><span>新增数据</span></div>
         <section class="day_list">
            <ul>
             <li>
             <c:if test="${rate.storePay == '0' || rate.storePay == '               0'}">
                    新增储值金额：<span class="green">0元</span>
             </c:if>
             <c:if test="${rate.storePay != '0' && rate.storePay != '               0'}">
                <a  href="javascript:getStored(1);" class="block">新增储值金额：<span class="green">${rate.storePay}元</span><i class="right">&gt;</i></a>
             </c:if>
             </li>

                <!-- 会员部分 -->
                <c:forEach items="${rate.newMemberType}" var="members">
                <li>
                 <c:if test="${members.memberSum == 0}">
                        ${members.typeName}：<span class="green">${members.memberSum}</span>
                 </c:if>
                 <c:if test="${members.memberSum != 0}">
                    <a  href="javascript:getMemberType('${members.type}','${members.typeName}');" class="block">${members.typeName}：<span class="green">${members.memberSum}</span><i class="right">&gt;</i></a>
                 </c:if>
                </li>
                </c:forEach>

            </ul>
         </section>
            <div class="mon_bread"><span>营销数据</span></div>
         <section class="day_list">
            <ul>
             <li>
             <c:if test="${rate.marketingIncome == '0.00' || rate.marketingIncome == '                .00'}">
                    营销收益：<span class="green">0.00元</span>
             </c:if>
             <c:if test="${rate.marketingIncome != '0.00' && rate.marketingIncome != '                .00'}">
                <a  href="javascript:getStored(5);" class="block">营销收益：<span class="green">${rate.marketingIncome}元</span><i class="right">&gt;</i></a>
             </c:if>
            </li>
            </ul>
         </section>
 </c:if>
    </section>
    <div style="">
</div>
</div>
</form>







</body>
</html>
