<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.yazuo.weixin.util.JudgeMenuShow"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.*"%>
<%@page import="com.yazuo.weixin.vo.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	BusinessVO business = ((BusinessVO) request.getAttribute("business"));
	Integer brandId = business.getBrandId();
	String title = business.getTitle();
	String weixinId=(String)request.getAttribute("weixinId");
	MemberVO member=(MemberVO)request.getAttribute("member");
	Integer membershipId=member.getMembershipId();
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/crm.css" />
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/common.css" />
<script src="<%=basePath%>js/jquery/1.8.3/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        // 生日特效
        $(".brith_button img").click(function(){
            $(".w_birth_one").slideUp("slow");
            $(".w_birth_two").slideDown("slow");

        });
        $("#birthday").focus(function(){
            // var abc=$(this).val();
            $(this).val('');
            $(".brith_jinggao").css("display",'none');
        	$(this).addClass("putong");
        	$(this).removeClass("jinggao");
        });
		
        $("input[type='radio']").click(function(){
        	$(".brith_jinggao").css("display",'none');
        	$("#birthday").addClass("putong");
        	$("#birthday").removeClass("jinggao");
        });
		
        $(".w_birth_two img").click(function(){
        	var birthday=$("#birthday").val();
        	var birthType =$("input[name='birthButton']:checked").val();
        	$.ajax({
			type : "post",
			url : '<%=path%>/weixin/phonePage/modifyMemberInfo.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>&birthday='+ birthday+'&birthType='+birthType,
			cache : false,
			dataType : "text",
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					alert('错误', '提交失败', 'error');
				}
			},
			success : function(json) {
			
				if (json == "5") {
					$("#birthdayInfo").html("生日："+birthday);
					setTimeout(function(){
		                $(".w_birth").slideUp("slow");
		            }, 2000);
				}else if("2"==json){
					$(".brith_jinggao").html("");
					$(".brith_jinggao").html("修改失败，请稍后再试！");
					$(".brith_jinggao").css("display",'block');
		        	$("#birthday").removeClass("putong");
		        	$("#birthday").addClass("jinggao");
				}else if("3"==json){
		        	$(".brith_jinggao").html("");
					$(".brith_jinggao").html("你还不是会员！");
					$(".brith_jinggao").css("display",'block');
		        	$("#birthday").removeClass("putong");
		        	$("#birthday").addClass("jinggao");
				}else if("4"==json){
					$(".brith_jinggao").html("");
					$(".brith_jinggao").html("格式不正确！");
					$(".brith_jinggao").css("display",'block');
		        	$("#birthday").removeClass("putong");
		        	$("#birthday").addClass("jinggao");
				}else{
					$(".brith_jinggao").css("display",'block');
		        	$("#birthday").removeClass("putong");
		        	$("#birthday").addClass("jinggao");
				}

			}
		});
            
        });
    });
	

    var backimage = { 'url':'<%=basePath%>/images/a.png', 'img': null };
    var canvas = {'temp': null, 'draw': null}; 
    var mouseDown = false;  
	var a=false;
	var guakai=false;
    	
    var currentpage = 0;//当前页数
    var loadcount = 0;//载入更多条数
    
    //微信会员卡交易查询功能
    function queryTransactionRecord(){
    	loadcount = loadcount + 15;
        currentpage = currentpage + 1;//当前页数
        var pagesize = 15;//每页条数
        
        var brandId=<%=brandId%>;
        var membershipId=<%=membershipId%>;
        var cardno = '${cardNo}';
        var beginTime=null;
        var endTime=null;
        var strs = "cardno="+cardno+"&merchantId="+brandId+"&membershipId="+membershipId+"&beginTime="+beginTime+"&endTime="+endTime+"&pagesize="+
        				pagesize+"&pageindex="+currentpage;
        //var strs = "cardno=6201300008977188&merchantId=21&membershipId="+membershipId+"&beginTime="+beginTime+"&endTime="+endTime+"&pagesize="+
        //                pagesize+"&pageindex="+currentpage;
        jQuery.ajax({
	        type:"POST",
	        url:'<%=path%>/weixin/transactionRecord/queryTransactionRecord.do',
	        //dataType:"String",
	        data:strs,
	        success: success,
	        error: error,
	        cache: false
         });
    }; 
    
    //微信会员卡交易查询成功时
    var success = function(returnMap){
        var recordList = document.getElementById("JS_recordList");
        var transactionRecordList=returnMap.list;
        var page=returnMap.page;
     	var allpage = page.allpage;
     	var pageindex = page.pageindex;
     	for ( var i = 0; i < transactionRecordList.length; i++) {
     		var master_id = transactionRecordList[i].id;
     		var membership_id = transactionRecordList[i].membershipId;
     		var trans_merchant = transactionRecordList[i].transMerchant;
     		var merchant_name = transactionRecordList[i].transMerchantName;
     		//var merchant_name = transactionRecordList[i].merchant_name;
     		var trans_time = transactionRecordList[i].transTime;
     		//var trans_time = transactionRecordList[i].trans_time;
     		var total = transactionRecordList[i].total;
     		var cash = transactionRecordList[i].cash;
     		var integral = transactionRecordList[i].integral;
     		var store = transactionRecordList[i].store;
     		var coupon = transactionRecordList[i].coupon;
     		var evaluateId = transactionRecordList[i].evaluateId;
     		var score = transactionRecordList[i].score;
     		var content = transactionRecordList[i].content;
     		var out =  transactionRecordList[i].out ;
     		var strTemp='';
			strTemp += '<li>';
			strTemp += '<p><label>交易金额：</label><b>' + checkValue(total) + '</b>元</p>';	
			strTemp += '<p>';
			strTemp += '<label>交易门店：</label>' + merchant_name;
			strTemp += '</p>';
			strTemp += '<p class="listExtra">';
			strTemp += '<span><label> 券&nbsp; 消&nbsp; 费：</label>' + checkValue(coupon) + '元</span>';
			strTemp += '<span><label>积分消费：</label>' + checkValue(integral) + '元</span>';
			strTemp += '</p>';
			strTemp += '<p class="listExtra">';
			strTemp += '<span><label>储值消费：</label>' + checkValue(store) + '元</span>';
			strTemp += '<span><label>现金消费：</label>' + checkValue(cash) + '元</span>';
			strTemp += '</p>';
			strTemp += '<p class="listExtra"><label>交易时间：</label>' + trans_time + '</p>';
			strTemp += '<div class="consumeComment">';
			strTemp+='<input type="hidden" value="'+master_id+'" name="transwater_id">'; //transwater_id
			strTemp+='<input type="hidden" value="'+evaluateId+'" name="evaluate_id">'; //evaluate_id
			strTemp += '	<label>消费评价：</label>';
			if(out){
				if(content.length==0){
					strTemp += '<p class="commentInfo">您没有留下任何评论</p>';
					strTemp += '<p class="commentTips">(只能对一个月内的交易进行评价)</p>';
				}else{
					strTemp += '<p class="commentShow"><span class="starScoreShow">';
					var cstar=parseInt(score);
					
					for(var j=0; j<5; j++){
						var sClass= (cstar > j)?'starIco votedStar':'starIco';
						strTemp+='<i class="'+sClass+'"></i>';
					}
					strTemp += '</span>';
					strTemp += '<span class="commentTxt">'+content+'</span></p>';
				 }
			}else{
				if(content.length==0){
					strTemp += '<p class="commentInfo">您还没有评论本次消费';
					strTemp += '<a href="javascript:;" class="JS_addConsumeCommentBtn orangeBtn">消费评价</a>';
					strTemp += '<a href="javascript:;" class="JS_cancelConsumeCommentBtn">取 消</a></p>';
				}else{
					strTemp += '<p class="commentShow"><span class="starScoreShow">';
					var cstar=parseInt(score);
					for(var k=0; k<5; k++){
						var sClass= (cstar > k)?'starIco votedStar':'starIco';
						strTemp+='<i class="'+sClass+'"></i>';
					}
					strTemp+='<input type="hidden" value="'+cstar+'" class="JS_nowStarScore">';
					strTemp += '</span>';
					strTemp += '<span class="commentTxt">'+content+'</span><a href="javascript:;" class="JS_editCommentFormBtn orangeBtn">修改</a></p>';
				}
			}
			strTemp += '</div>';	   			
			strTemp += '</li>';
			recordList.innerHTML += strTemp;
     	}
     	
        if (allpage > pageindex) { //总页数大于当前页
            document.getElementById("zrg").style.display = '';
            document.getElementById("noMore").style.display = 'none';
    	} else {
    		document.getElementById("zrg").style.display = 'none';
    		document.getElementById("noMore").style.display = '';
    	} 
        window.location.hash="#JS_recordTitle";
    };
    
    //微信会员卡交易查询失败时
    var error =  function()  {
        alert("请求失败!");
        document.getElementById("zrg").style.display = '';
        document.getElementById("noMore").style.display = 'none';
    };
    
	//检查金额是否为空，如为空则赋值0
	function checkValue(value){
		if(value == undefined || value == null){  
		    return 0;
		}else{
			return value;
		}
	}
    	
    $(function(){
    	$(".yh_list li").toggle(function(){
    		$(this).children(".yh_detail").show();
    		$(this).children("a").children("i").removeClass("jtx").addClass("jts");
    	},function(){
    		$(this).children(".yh_detail").hide();
    		$(this).children("a").children("i").removeClass("jts").addClass("jtx");
    	})
    });
    
    var recordClickCount = 0;//交易记录点击次数
    
	//加载微信会员卡交易信息
 	$("#JS_recordTitle").live("click",function(){
 		$(this).find(".viewIco").toggleClass("viewMoreIco");
 		$("#JS_recordListWrap").toggle();
 		recordClickCount++;//点击次数加1
 		if(1 == recordClickCount){//只在第一次点击时加载，后续加载由"查看更多"触发
 	 		queryTransactionRecord(); 			
 		}
 		
 	    //滚动条定位至交易记录
 		if(recordClickCount > 1){
 			if(recordClickCount%2 == 1){//交易记录展开
 	        	window.location.hash="#JS_recordTitle";
 	        }else{//交易记录关闭
 	        	window.location.hash="#000"; 	        
 	 		};
 		}
 	
	});
    // 查看推送广告
    function lookAdviser(brandId, weixinId) {
    	window.location.href = "<%=path%>/merchantWifi/pushPage.do?brandId="+brandId+"&weixinId="+weixinId;
    }
	// 修改密码初始化页面
    function redirectUpdatePwd() {
    	$("#is_reset_div").hide();
		window.location.href = "<%=path%>/weixin/member/initUpdatePwdPage.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>&cardNo=${cardNo}";
	}
	// 关闭弹框
	function closeDiv(divId){
		$("#"+divId).hide();
	}
    // 重置密码
    function resetPwd() {
    	$("#is_reset_div").hide();
   		$.ajax({
       		url:"<%=path%>/weixin/member/resetPwd.do?brandId=${business.brandId}&cardNo=${cardNo}",
       		type:"post",
       		dataType:"json",
       		success:function(data){
       			if (data.flag) { // 重置密码成功
       				$("#reset_success_div").show();
       			} else { // 重置失败
       				$("#reset_fail_div").find(".popInfo").find("span").html(data.message);
       				$("#reset_fail_div").show();
       			}
       		}
       	});	
    } 
    function isResetPwd() {
    	$("#is_reset_div").show();
    }
    </script>


<title>${business.title }</title>
</head>
<body>
<div class="w">
	<section class="w-top">
    	<div class="top-bg"></div>
        <section class="company-logo">
        <c:choose>
	        <c:when test="${hasImage ||'1111111111' eq business.companyWeiboId }">
	        <img src="<%=basePath%>/user-upload-image/${business.brandId}/${business.smallimageName}" width="75" height="75" />
	         </c:when>
		       	<c:otherwise>
	        <img src="http://tp4.sinaimg.cn/${business.companyWeiboId}/180/1122/9" width="75" height="75" />
	        </c:otherwise>
	    </c:choose>
        </section>
        <section class="company-name">
        	<h1>${business.title }</h1>
        </section>
        <section class="top-menu">
	        <a href="#" class="sel">会员</a> 
	        <a	href="<%=path%>/weixin/phonePage/preference.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">优惠</a>
	        <a href="<%=path%>/weixin/newFun/subbranch.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">门店</a>
			<%if (JudgeMenuShow.judgeMenu(brandId)) { %>
				<a href="<%=path%>/weixin/newFun/showStory.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>">陪你</a>
			<%} else {%>
				<a href="<%=path%>/weixin/newFun/dishes.do?brandId=<%=brandId%>&weixinId=<%=weixinId%>&r=<%=Math.random()%>">菜品</a>
			<%} %>
        </section>
    </section>
    <!-- 清除浮动 -->
    <section class="clear_wrap"></section>
    <!-- 上部 -->
    <c:if test="${empty member.birthday }">
     <section class="w_birth">
        <section class="w_birth_one">
            <section class="brith_title">登记生日可能会有惊喜哦！</section>
            <section class="brith_button"><img src="<%=basePath%>/images/image0022.png"></section>
        </section>
        <section class="w_birth_two">
        	<section class="v_birth_box">
	            <div class="brith_title_two">请填写生日(格式：19830809)</div>
	            <input type="radio" class="checkbox" name="birthButton" id="yangLi" value="1" checked="checked"/>阳历
				<input type="radio" class="checkbox" name="birthButton" id="yinLi" value="2" />阴历
	            <div style="display:none" class="brith_jinggao">格式不正确</div>
            </section>
            <input class="putong" id="birthday" type="text" name="" value="19880808"/>
            <section class="brith_button_two"><img src="<%=basePath%>/images/tijiao.png"></section>
        </section>
        <section class="w_birth_three">
            <section class="w_img_three"><img src="<%=basePath%>/images/guajiang3.png"></section>
            <section class="w_title_three">我们会在您生日前夕送上惊喜~！ 注意查收微信哦~</section>
        </section>  
    </section>
    <!-- 清除浮动 -->
    <section class="clear_wrap"></section>
    </c:if>
    <!-- 目前就 水木锦堂 商户有此功能8748 -->
    <c:if test="${existsPushPage}">
   		<h3 onclick="javascript:lookAdviser(<%=brandId%>, '<%=weixinId%>');" class="adItemTitle" >办理会员卡，专享会员优惠  <i class="rightLinkIco"></i></h3>
   </c:if>
    <!-- 中部 -->
    <section class="w-main2">
    		  <div class="cardList">
	    		<div class="cardItem">
		        	<div class="cardBgItem">
		        		<c:if test="${empty icon}">
		                <img src="<%=basePath %>/images/card1.png" class="cardImg" />
		                <span class="cardLogo">
		                 <c:choose>
					        <c:when test="${hasImage ||'1111111111' eq business.companyWeiboId }">
					         <img src="<%=basePath%>/user-upload-image/${business.brandId}/${business.smallimageName}" />
					         </c:when>
						       	<c:otherwise>
					        <img src="http://tp4.sinaimg.cn/${business.companyWeiboId }/180/1122/9" />
					        </c:otherwise>
					    </c:choose>
		                </span>
		                <span class="cardName">${business.title }</span>
		                <span class="cardType difColor">${cardType }</span> <span class="cardBirthday difColor" id="birthdayInfo">生日：<%=member.getBirthday()==null?"未登记":sdf1.format(member.getBirthday()) %></span>
		                </c:if>
		                <c:if test="${!empty icon}">
		                	 <img src="${icon}" class="cardImg" />
		                </c:if>
		            </div>
		            <div class="cardMoney">
			           <c:if test="${pwdIf}">
			            	<p class="pwdLine"><a href="javascript:redirectUpdatePwd();" class="editPwdLink">修改密码</a> | <a href="javascript:isResetPwd();" class="resetPwdLink">重置密码</a></p>
			           </c:if>
			           	<p>
			           		<label>手机号：</label>
			           		<strong class="cardNum2">${member.phoneNo}</strong>
			           	</p>
			           	<p>
			           		<label>会员卡号：</label>
			           		<strong class="cardNum2">${cardNo}</strong>
			           	</p>
		            	<p><label>储值余额：</label>
		            		<strong class="red">
		            			<c:choose>
		                			<c:when test="${empty storeBalance}">0</c:when>
		                			<c:otherwise>${storeBalance}</c:otherwise>
		                		</c:choose>
		            		</strong>
		            		<em>RMB</em>
		            	</p>
		                <p><label>积分余额：</label>
		                	<strong class="green">
		                		<c:choose>
		                			<c:when test="${empty integralAvailable}">0</c:when>
		                			<c:otherwise>${integralAvailable}</c:otherwise>
		                		</c:choose>
		                	</strong>
		                </p>
		            </div>
    			</div>
    		</div>

        </section>
    </section>
    <!-- 清除浮动 -->
    <section class="clear_wrap"></section>
    <!-- 优惠券详情信息 -->
    <!-- 底部    -->
    
<div class="wx">
    <section class="wx-monthly">
    	<c:if test="${!empty cardCouponList }">
    	
	        <section class="couponList">
        	<h3 class="partTitle">卡券</h3>
        	<ul> 
				    <c:forEach items="${cardCouponList}" varStatus="i" var="item">
	                   
	                <li>
	            	  	<h3 class="couponTitle greenTitle">${item.name}&nbsp;&nbsp;${item.availableQuantity }张
	            	  	<%-- <a href="${pageContext.request.contextPath}/weixin/${business.brandId}/toSendFriend.do?fromUserId=${member.membershipId}&couponId=${item.id}&couponName=${item.name}&couponNum=${item.availableQuantity }&beginTime=${item.startDate}&endTime=${item.expiredDate }&weixinId=${weixinId}">赠送</a> --%>
	            	  	</h3>
                		<p class="couponDate">有效期：<em class="green">${item.startDate }</em> 至 <em class="green">${item.expiredDate }</em></p>
                    	<div class="couponDetailBox">
	                		<p><i class="showDetailLinkIco" onclick="showDesc('${item.batchNo}',this)"></i></p>
	                		<p class="couponDetailInfo"></p>
	                		<p><i class="hideDetailLinkIco"></i></p>
	                	</div>
	                </li> 
	                   
				    </c:forEach>
            </ul>
	        </section>
	     </c:if>
	     <c:if test="${!empty phoneCouponList }">
	        <section class="couponList">
        	<h3 class="partTitle">通用券  <span>|&nbsp;&nbsp;&nbsp;跟手机号绑定，所有卡内都可见哦~</span></h3>
        	<ul> 
				     <c:forEach items="${phoneCouponList}" varStatus="i" var="item">

	                    <li>
	            	  	<h3 class="couponTitle blueTitle">${item.name}&nbsp;&nbsp;${item.availableQuantity }张
	            	  	 <%-- <a href="${pageContext.request.contextPath}/weixin/${business.brandId}/toSendFriend.do?fromUserId=${member.membershipId}&couponId=${item.id}&couponName=${item.name}&couponNum=${item.availableQuantity }&beginTime=${item.startDate}&endTime=${item.expiredDate }&weixinId=${weixinId}">赠送</a> --%> 
	            	  	</h3>
                		<p class="couponDate">有效期：<em class="blue">${item.startDate }</em> 至 <em class="blue">${item.expiredDate }</em></p>
                    	<div class="couponDetailBox">
	                		<p><i class="showDetailLinkIco" onclick="showDesc('${item.batchNo}',this)"></i></p>
	                		<p class="couponDetailInfo"></p>
	                		<p><i class="hideDetailLinkIco"></i></p>
	                	</div>
	               		</li> 
				     </c:forEach>
	         </ul>
	        </section>
	     </c:if>
    </section>
</div>

<!-- 交易信息 start-->
	<div class="tradeRecord">
   		<a class="recordTitle" id="JS_recordTitle" href="javascript:;">交易记录<i class="viewIco"></i></a>
   		<div  id="JS_recordListWrap" style="display:none">
	   			<ul class="recordList" id="JS_recordList">	   		
	   				
	   			</ul>
	   		<a id="zrg" style="display: none" href="javascript:queryTransactionRecord();" class="viewMoreLinks">查询更多 <i class="viewMoreIco"></i></a>
			<i id="noMore" style="display: none" class="viewMoreLinks">没有更多</i>
   		</div>
	</div>
<!-- 交易信息 end-->
<!-- 重置密码提示 -->
<div class="popWin" id="reset_success_div" style="display:none;">
	<div class="popBg"></div>
	<div class="popInfo"> <a href="javascript:closeDiv('reset_success_div');" id="closePopWinBtn">×</a>
    	新密码已发送到您的手机，请查收。建议您修改成自己的常用密码，点击会员中心卡面下方“修改密码”就可以修改哦~
		<a href="javascript:redirectUpdatePwd();" class="popGreenBtn">确定</a>
    </div>
</div> 
<div class="popWin" id="reset_fail_div" style="display:none;">
	<div class="popBg"></div>
	<div class="popInfo">
		<span></span>
		<a href="javascript:closeDiv('reset_fail_div');" class="popGreenBtn">确定</a>
    </div>
</div>
<div class="popWin" id="is_reset_div" style="display:none;">
	<div class="popBg"></div>
	<div class="popInfo"> <a href="javascript:closeDiv('is_reset_div');" id="closePopWinBtn">×</a>
    	您确定要重置密码吗?
		<a href="javascript:resetPwd();" class="popGreenBtn">确定</a>
    </div>
</div>
</div>
<script type="text/javascript">
//textarea 输入的回车 显示时替换为换行
function replaceBr(sInfo){
	return sInfo.replace(/\n/g,"<br/>");
}

/*全局变量 星星票数、输入的评价内容、是否满足提交条件 */
var commentStar=0,commentTxt='',isAllowSubmit=false;

/*交易记录 添加消费评价*/
$(".JS_addConsumeCommentBtn").live("click",function(){
	$(this).hide();
	$(this).parent().find(".JS_cancelConsumeCommentBtn").show();
	var text="";
	commentFormAppend($(this).parents(".consumeComment"),0,'');
});
//点击消费评价或修改时，列表插入form表单
function commentFormAppend(oParent,commStar,commTxt){
	var textLen=commentTxt.length;
	var wordsCountHtml='';
	if(textLen < 10){
		wordsCountHtml="您还需输入<em id='JS_wordsNumCount'>"+(10-textLen)+"</em>个字";
	}else{
		wordsCountHtml="您还可输入<em id='JS_wordsNumCount'>"+(100-textLen)+"</em>个字";
	}
	var starHtml="";
	for(var i=0; i<5; i++){
		if(i<commStar){
			starHtml+='<i class="starIco votedStar" onclick="starVote(this)"></i>';
		}else{
			starHtml+='<i class="starIco" onclick="starVote(this)"></i>';
		}
	}	
	var commentHtml='<div class="consumeCommentForm">'
					+'<textarea class="commentFormTxt">'+commTxt+'</textarea>'
					+'<p class="wordsNumCount">'+wordsCountHtml+'</p>'
					+'<p class="starVoteBtns">'
					+'<span class="starScores">'+starHtml+'</span><input type="hidden" value="'+commentStar+'" class="JS_starScoreNum" />'   
					+'<a href="javascript:;" class="orangeBtn JS_commentFormBtn" style="display:none">确 定</a><em class="JS_commentFormspan">确 定</em>'
				+'</p></div>';
	$(oParent).append(commentHtml);			
}
//取消按钮
$(".JS_cancelConsumeCommentBtn").live("click",function(){
	
	if(commentStar !== 0 && 10<commentTxt.length<100){  //在修改时点击的取消按钮，需要把之前的默认值带回
		var starHtml="";
		for(var i=0;i<5;i++){
			if(i<commentStar){
				starHtml+='<i class="starIco votedStar"  onclick="starVote(this)"></i>';
			}else{
				starHtml+='<i class="starIco"  onclick="starVote(this)"></i>';
			}
		}	
		var commentShowHtml='<p class="commentShow">'
				+'<span class="starScoreShow">'+starHtml+'</span>' 
				+'<input type="hidden" value="'+commentStar+'" class="JS_nowStarScore" />'
				+'<span class="commentTxt">'+commentTxt+'</span><a href="javascript:;" class="JS_editCommentFormBtn orangeBtn">修改</a></p>';
		$(this).parents(".consumeComment").append(commentShowHtml);			
		$(this).parents(".consumeComment").find(".consumeCommentForm").remove();
		$(this).parent().remove();
	}else{
		$(this).hide();
		$(this).parent().find(".JS_addConsumeCommentBtn").show();
		$(this).parents(".consumeComment").find(".consumeCommentForm").remove();
	}
	clearDefault();
});

//确定按钮，提交评价
$(".JS_commentFormBtn").live("click",function(){
	commentTxt=$(this).parents(".consumeCommentForm").find(".commentFormTxt").val();
	commentTxt=replaceBr(commentTxt);
    commentStar=$(this).parents(".starVoteBtns").find(".JS_starScoreNum").val();
	var twid = $(this).parents(".consumeComment").find("input[name='transwater_id']").val();
	var evaluateId = $(this).parents(".consumeComment").find("input[name='evaluate_id']").val();
	var requestStr = "commentStar="+commentStar+"&commentTxt="+commentTxt;
	var requestUrl = "<%=basePath%>weixin/transactionRecord/";
	if(evaluateId==null||evaluateId==""){
		requestStr+="&twid="+twid;
		requestUrl+="saveTransactionRecord.do";
	}else{
		requestStr+="&evaluateId="+evaluateId;
		requestUrl+="updateTransactionRecord.do";
	}
	var successflag = false;
	//ajax 数据提交后台
	$.ajax({
		url:requestUrl,
	    type:"post",    //数据发送方式  
	    data:requestStr,   //要传递的数据；就是上面序列化的值
	    dataType:"json",
	    async: false, //设为false就是同步请求
        cache: false,
		success: function(reStr){
			successflag=reStr.success;
			if(evaluateId==null||evaluateId==""){
				evaluateId=reStr.id;
			}
		 }
	});
	if(successflag){
		//success 后的操作
		$(this).parents(".consumeComment").find(".commentInfo").remove();
		$(this).parents(".consumeComment").find("input[name='evaluate_id']").val(evaluateId); //把评价id加上
		var starHtml="";
		for(var i=0;i<5;i++){
			if(i<commentStar){
				starHtml+='<i class="starIco votedStar"  onclick="starVote(this)"></i>';
			}else{
				starHtml+='<i class="starIco"  onclick="starVote(this)"></i>';
			}
		}	
		var commentShowHtml='<p class="commentShow">'
		 					+'<span class="starScoreShow">'+starHtml+'</span>' 
							+'<input type="hidden" value="'+commentStar+'" class="JS_nowStarScore" />'
							+'<span class="commentTxt">'+commentTxt+'</span><a href="javascript:;" class="JS_editCommentFormBtn orangeBtn">修改</a></p>';
		$(this).parents(".consumeComment").append(commentShowHtml);
		$(this).parents(".consumeCommentForm").remove();
		
		clearDefault();		
	}
		
});
//点击星星投票
function starVote(obj){
	var thisIndex=$(obj).index();
	$(obj).parents(".starVoteBtns").find(".JS_starScoreNum").val(thisIndex+1);
	$(obj).parent().find(".starIco").removeClass("votedStar");
	for(var i=0;i<(thisIndex+1);i++){
		$(obj).parent().find(".starIco").eq(i).addClass("votedStar");
	}
	var nowTxtLen=$(obj).parents(".consumeCommentForm").find(".commentFormTxt").val().length;
	if(9<nowTxtLen && nowTxtLen<101 && $(obj).parents(".starVoteBtns").find(".JS_starScoreNum").val()>0){
		isAllowSubmit=true;
	}else{
		isAllowSubmit=false;
	}	
	changeSubBtnStat($(obj).parents(".consumeCommentForm"));  //判断是否允许提交，切换按钮状态
};



//点击修改消费评价
$(".JS_editCommentFormBtn").live("click",function(){
	commentStar=$(this).parents(".commentShow").find(".JS_nowStarScore").val();
	commentTxt=$(this).parent().find(".commentTxt").text();
	
	var defaultHtml='<p class="commentInfo">您正在修改本次消费评价 '
					+'<a href="javascript:;" class="JS_addConsumeCommentBtn orangeBtn" style="display:none">消费评价</a>'
				    +'<a href="javascript:;" class="JS_cancelConsumeCommentBtn" style="display:inline-block">取 消</a></p>';
	$(this).parents(".consumeComment").append(defaultHtml);
	commentFormAppend($(this).parents(".consumeComment"),commentStar,commentTxt);
	
	$(this).parents(".consumeComment").find(".JS_commentFormBtn").show();
	$(this).parents(".consumeComment").find(".JS_commentFormspan").hide();
	
	$(this).parent().remove();
	
	//clearDefault();
});

//输入的同时对字数计数
$(".commentFormTxt").live("keyup",function(){
	var nowTxtLen=$(this).val().length;
	var wordsCountHtml='';
	if(nowTxtLen < 10){
		wordsCountHtml="您还需输入<em id='JS_wordsNumCount'>"+(10-nowTxtLen)+"</em>个字";
	}else{
		wordsCountHtml="您还可输入<em id='JS_wordsNumCount'>"+(100-nowTxtLen)+"</em>个字";
	}
	if(9<nowTxtLen && nowTxtLen<101 && $(this).parents(".consumeCommentForm").find(".JS_starScoreNum").val()>0){
		isAllowSubmit=true;
	}else{
		isAllowSubmit=false;
	}
	changeSubBtnStat($(this).parents(".consumeCommentForm"));  //判断是否允许提交，切换按钮状态
	$(this).parent().find(".wordsNumCount").html(wordsCountHtml);
});
$(".commentFormTxt").live('input propertychange',function (){  
	setTxtNum(this);
});

function setTxtNum(_this){
	var nowTxtLen=$(_this).val().length;
	var wordsCountHtml='';
	if(nowTxtLen < 10){
		wordsCountHtml="您还需输入<em id='JS_wordsNumCount'>"+(10-nowTxtLen)+"</em>个字";
	}else{
		wordsCountHtml="您还可输入<em id='JS_wordsNumCount'>"+(100-nowTxtLen)+"</em>个字";
	}
	if(9<nowTxtLen && nowTxtLen<101 && $(_this).parents(".consumeCommentForm").find(".JS_starScoreNum").val()>0){
		isAllowSubmit=true;
	}else{
		isAllowSubmit=false;
	}
	changeSubBtnStat($(_this).parents(".consumeCommentForm"));  //判断是否允许提交，切换按钮状态
	$(_this).parent().find(".wordsNumCount").html(wordsCountHtml);
}

//切换表单提交按钮
function changeSubBtnStat(oParent){
	if(isAllowSubmit){
		$(oParent).find(".JS_commentFormspan").hide();
		$(oParent).find(".JS_commentFormBtn").show();
	}else{
		$(oParent).find(".JS_commentFormspan").show();
		$(oParent).find(".JS_commentFormBtn").hide();
	}
}

//清除全局默认值
function clearDefault(){
	commentStar=0;
	commentTxt='';
}

function showDesc(batchNo,_this){
	$.ajax({
		url:"${pageContext.request.contextPath}/weixin/phonePage/getCouponDesc.do",
	    type:"post",    //数据发送方式  
	    data:"batchNo="+batchNo,   //要传递的数据；就是上面序列化的值  
	    success: function(data) {
	    	if(data==''){
	    		data="没有描述信息";
	    	}
	    	$(_this).parents(".couponDetailBox").find(".couponDetailInfo").html(data);
   			$(_this).hide();
   			$(_this).parents(".couponDetailBox").find(".couponDetailInfo").fadeIn(500);
    		$(_this).parents(".couponDetailBox").find(".hideDetailLinkIco").css("display",'block');
	    }
	});
	
}

//点击展开箭头，展示券详细信息
/* $(".showDetailLinkIco").bind("click",function(){
	$(this).hide();
	$(this).parents(".couponDetailBox").find(".couponDetailInfo").fadeIn(500);
	$(this).parents(".couponDetailBox").find(".hideDetailLinkIco").css("display",'block');
}); */
$(".hideDetailLinkIco").bind("click",function(){
	$(this).hide();
	$(this).parents(".couponDetailBox").find(".couponDetailInfo").fadeOut(500);
	$(this).parents(".couponDetailBox").find(".showDetailLinkIco").css("display",'block');
});

</script>
</body>
</html>
