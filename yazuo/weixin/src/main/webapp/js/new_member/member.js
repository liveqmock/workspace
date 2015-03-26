$(function(){
	// 进入菜品页面，选中菜品导航，其他导航不选中
	$(".footer-on").removeClass("footer-on");
	$(".footer-icon-member").parent().parent().parent().addClass("footer-on");
	//弹窗消失
	setTimeout(function(){
		$(".member-card-tips").animate({
			opacity: 'hide'
		},300);				
	},5000); 
	
	//多张卡滑动
	$(document).on("swipeleft",".member-card-show li", function(){//向左滑动
		swipLeft(this);
	});
	$(document).on("swiperight",".member-card-show li", function(){//向右滑动
		swipRight(this);
	});		                  
	$(document).on("click",".click-left", function(){//向左滑动
		var obj = $(".member-card-show .img-big");
		swipLeft(obj);
	});
	$(document).on("click",".click-right", function(){//向右滑动
		var obj = $(".member-card-show .img-big");
		swipRight(obj);
	});
		
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
			url : ctx+"/weixin/phonePage/modifyMemberInfo.do?brandId="+$("#brandId").val()+"&weixinId="+$("#weixinId").val()+"&birthday="+ birthday+"&birthType="+birthType,
			cache : false,
			dataType : "text",
			error : function(XMLHttpRequest) {// 请求失败时调用函数
				if (processCommErr(XMLHttpRequest)) {
					alert('错误', '提交失败', 'error');
				}
			},
			success : function(json) {
			
				if (json == "1") {
					$("#birthdayInfo").html("生日："+birthday);
					setTimeout(function(){
		                $(".w_birth").slideUp("slow");
		            }, 2000);
				}else if("3"==json){
					$(".brith_jinggao").html("");
					$(".brith_jinggao").html("已经超过修改次数！");
					$(".brith_jinggao").css("display",'block');
		        	$("#birthday").removeClass("putong");
		        	$("#birthday").addClass("jinggao");
				}else if("2"==json){
					$(".brith_jinggao").html("");
					$(".brith_jinggao").html("修改失败，请稍后再试！");
					$(".brith_jinggao").css("display",'block');
		        	$("#birthday").removeClass("putong");
		        	$("#birthday").addClass("jinggao");
				}else {
					$(".brith_jinggao").css("display",'block');
		        	$("#birthday").removeClass("putong");
		        	$("#birthday").addClass("jinggao");
				}

			}
		});
            
        });
     
       var recordClickCount = 0;//交易记录点击次数
      //加载微信会员卡交易信息
      $("#JS_recordTitle").on("click",function(){
    	  var brandId = $("#brandId").val();
    	  var membershipId = $("#membershipId").val();
	      	$(this).toggleClass("viewMoreIco");
	      	$("#JS_recordList").toggle();
	      	$("#noMore").toggle();
	      	recordClickCount++;//点击次数加1
	      	if(1 == recordClickCount){//只在第一次点击时加载，后续加载由"查看更多"触发
	       		queryTransactionRecord(brandId, membershipId); 			
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
     // -------------------------消费评价--------------------------------------------------
      /*交易记录 添加消费评价*/
      $("body").on("click",".JS_addConsumeCommentBtn", function(){
		  	$(this).hide();
		  	$(this).parent().find(".JS_cancelConsumeCommentBtn").show();
		  	var text="";
		  	commentFormAppend($(this).parents(".consumeComment"),0,'');
      });
    //点击修改消费评价
      $("body").on("click",".JS_editCommentFormBtn",function(){
    	commentStar=$(this).parents(".commentShow").find(".JS_nowStarScore").val();
      	commentTxt=$(this).parents(".commentShow").find(".commentTxt").text();
      	
      	var defaultHtml='<p class="commentInfo commentCont"><span class="commentWord">您正在修改本次消费评价 '
      					+'<restrict:operation url="commentKey">'
      					+'<a href="javascript:;" class="JS_addConsumeCommentBtn orangeBtn" style="display:none">消费评价</a>'
      				    +'<a href="javascript:;" class="JS_cancelConsumeCommentBtn" style="display:inline-block">取 消</a>'
      				    +'</:restrict:operation></span>'
      				    +'</p>';
      	$(this).parents(".consumeComment").append(defaultHtml);
      	commentFormAppend($(this).parents(".consumeComment"),commentStar,commentTxt);
      	
      	$(this).parents(".consumeComment").find(".JS_commentFormBtn").show();
      	$(this).parents(".consumeComment").find(".JS_commentFormspan").hide();
      	
      	$(this).parent().remove();
      	
      	//clearDefault();
      });
      
    //确定按钮，提交评价
      $("body").on("click", ".JS_commentFormBtn", function(){
      	commentTxt=$(this).parents(".consumeCommentForm").find(".commentFormTxt").val();
      	commentTxt=replaceBr(commentTxt);
        commentStar=$(this).parents(".starVoteBtns").find(".JS_starScoreNum").val();
      	var twid = $(this).parents(".consumeComment").find("input[name='transwater_id']").val();
      	var evaluateId = $(this).parents(".consumeComment").find("input[name='evaluate_id']").val();
      	var requestStr = "commentStar="+commentStar+"&commentTxt="+commentTxt;
      	var requestUrl = ctx+"/weixin/transactionRecord/";
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
      							+'<span class="commentTxt">'+commentTxt+'</span><restrict:operation url="commentKey"><a href="javascript:;" class="JS_editCommentFormBtn orangeBtn modifyBtn">修改</a></restrict:operation></p>';
      		//$(this).parents(".consumeCommentForm").siblings(".commentShow").html(commentShowHtml);
      		$(this).parents(".consumeCommentForm").siblings(".commentShow").remove();
      		$(this).parents(".consumeComment").append(commentShowHtml);
      		$(this).parents(".consumeCommentForm").remove();
      		clearDefault();		
      	}
      		
      });
      
    //输入的同时对字数计数
      $("body").on("keyup",".commentFormTxt",function(){
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
      $(".commentFormTxt").on('input propertychange',function (){  
      	setTxtNum(this);
      });

      
    //取消按钮
      $("body").on("click",".JS_cancelConsumeCommentBtn",function(){
    	  if(commentStar !== 0 && 10<commentTxt.length<100){
	    	  var commentShowHtml='<restrict:operation url="commentKey"><a href="javascript:;" class="JS_editCommentFormBtn orangeBtn">修改</a></restrict:operation></p>';
	  		  $(this).parents(".commentInfo").siblings(".commentShow").append(commentShowHtml);
	    	  $(this).parents(".consumeComment").find(".consumeCommentForm").remove();
	    	  $(this).parents(".commentInfo").remove();
    	  }else{
    			$(this).hide();
    			$(this).parent().find(".JS_addConsumeCommentBtn").show();
    			$(this).parents(".consumeComment").find(".consumeCommentForm").remove();
    		}
      	  clearDefault();
      });
});

//--------------------卡滑动 begin----------------
function swipLeft(obj){
	var index = $(obj).index()+1,
		num = 210*index,//图片所在容器向左移动的距离
		total = $(".member-card-show li").length;//图片个数
	if(index==1){
		if(index==(total-1)){
			$(".click-left").hide();
			$(".click-right").show();
			var num = 245*($(obj).index())+176;		
			$(".member-card-show ul").animate({
				marginLeft: "-"+num+"px"
			},500,function(){
				$( ".member-card-show li" ).removeClass();
				$( ".member-card-show li" ).eq(index).addClass("img-big");
			});
		}else {
		$(".click-right").show();
		//$(".click-left").show();
		$(".member-card-show ul").animate({
			marginLeft: "-"+num+"px"
		},500,function(){
			$( ".member-card-show li" ).removeClass();
			$( ".member-card-show li" ).eq(index).addClass("img-big");
		});
		}
	}else if(index!=total){//如果不是最后一张图片则向左滑动
		if(index==(total-1)){
			$(".click-left").hide();
			$(".click-right").show();
			var num = 245*($(obj).index())+176;		
			$(".member-card-show ul").animate({
				marginLeft: "-"+num+"px"
			},500,function(){
				$( ".member-card-show li" ).removeClass();
				$( ".member-card-show li" ).eq(index).addClass("img-big");
			});
		}else {
			$(".click-right").show();
			$(".click-left").show();
			var num = 245*($(obj).index())+210;		
			$(".member-card-show ul").animate({
				marginLeft: "-"+num+"px"
			},500,function(){
				$( ".member-card-show li" ).removeClass();
				$( ".member-card-show li" ).eq(index).addClass("img-big");
			});
		}
	}
	
}
function swipRight(obj){
	var index = $(obj).index()-1,
		num = 228*index,//u图片所在容器向右移动的距离
		total = $(".member-card-show li").length;
	if(index>=0){//如果当前图片是第一张则不能右滑动
		if(index==0){
			$(".click-right").hide();			
		}
		if((index+2)==total){
			if(index==0){
				$(".click-right").hide();
			}else{
		$(".click-right").show();
			}
		$(".click-left").show();
		$(".member-card-show ul").animate({
			marginLeft: "-"+num+"px"
		},500,function(){
			$( ".member-card-show li" ).removeClass();
			$( ".member-card-show li" ).eq(index).addClass("img-big");
		});
		}else{
			var num = 210*index;
			$(".member-card-show ul").animate({
				marginLeft: "-"+num+"px"
			},500,function(){
				$( ".member-card-show li" ).removeClass();
				$( ".member-card-show li" ).eq(index).addClass("img-big");
			});
		}
	}
}
//--------------------卡滑动 end----------------
//---------------------------------------消费评价开始--------------------------------------------------
//textarea 输入的回车 显示时替换为换行
function replaceBr(sInfo){
	return sInfo.replace(/\n/g,"<br/>");
}

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
					+'<a href="javascript:;" class="sureBtn JS_commentFormBtn" style="display:none">确 定</a><em class="JS_commentFormspan">确 定</em>'
				+'</p></div>';
	$(oParent).append(commentHtml);			
}


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

var backimage = { 'url':ctx+'/images/a.png', 'img': null };
var canvas = {'temp': null, 'draw': null}; 
var mouseDown = false;  
var a=false;
var guakai=false;

//微信会员卡交易查询功能
function queryTransactionRecord(brandId, membershipId){
	var currentpage = parseInt($("#currentpage").val());//当前页数
	var loadcount = parseInt($("#loadcount").val());//载入更多条数
	loadcount = loadcount + 15;
    currentpage = currentpage + 1;//当前页数
    $("#currentpage").val(currentpage);
    $("#loadcount").val(loadcount);
    
    var pagesize = 15;//每页条数
    var cardno = $("#cardNo").val();;
    var beginTime=null;
    var endTime=null;
    var strs = "cardno="+cardno+"&merchantId="+brandId+"&membershipId="+membershipId+"&beginTime="+beginTime+"&endTime="+endTime+"&pagesize="+
    				pagesize+"&pageindex="+currentpage;
    //var strs = "cardno=6201300008977188&merchantId=21&membershipId="+membershipId+"&beginTime="+beginTime+"&endTime="+endTime+"&pagesize="+
    //                pagesize+"&pageindex="+currentpage;
    jQuery.ajax({
        type:"POST",
        url:ctx+'/weixin/transactionRecord/queryTransactionRecord.do',
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
 	var pagesize = page.pagesize;
 	var listlen = transactionRecordList.length;
 	var i =0;
 	var endLength =0;
 	if(pageindex==1){//第一页
	 	endLength=listlen>pagesize?pagesize:listlen;
 	}else if(pageindex>1){//第二页以上
 		i=(pageindex-1)*pagesize-1;
 		endLength=(listlen>(pageindex*pagesize))?(pageindex*pagesize):(listlen);
 	}
 	for ( ; i < endLength; i++) {
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
 		if(evaluateId==null|| evaluateId==''||evaluateId=='null'){
 			evaluateId='';
 		}
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
			if(content==null|| content.length==0){
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
			if(content==null|| content.length==0){
				strTemp += '<p class="commentInfo">您还没有评论本次消费';
					strTemp += '<restrict:operation url="commentKey"><a href="javascript:;" class="JS_addConsumeCommentBtn orangeBtn">消费评价</a>';
					strTemp += '<a href="javascript:;" class="JS_cancelConsumeCommentBtn">取 消</a></restrict:operation></p>';
			}else{
				strTemp += '<p class="commentShow"><span class="starScoreShow">';
				var cstar=parseInt(score);
				for(var k=0; k<5; k++){
					var sClass= (cstar > k)?'starIco votedStar':'starIco';
					strTemp+='<i class="'+sClass+'"></i>';
				}
				strTemp+='<input type="hidden" value="'+cstar+'" class="JS_nowStarScore">';
				strTemp += '</span>';
				strTemp += '<span class="commentTxt">'+content+'</span><restrict:operation url="commentKey"><a href="javascript:;" class="JS_editCommentFormBtn orangeBtn modifyBtn">修改</a></restrict:operation</p>';
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

//---------------------------------------消费评价结束--------------------------------------------------
// 修改密码初始化页面
function redirectUpdatePwd() {
	$("#is_reset_div").hide();
	window.location.href = ctx+"/weixin/member/initUpdatePwdPage.do?brandId="+$("#brandId").val()+"&weixinId="+$("#weixinId").val()+"&cardNo="+$("#cardNo").val();
}
// 重置密码
function resetPwd() {
	$.ajax({
		url:ctx+"/weixin/member/resetPwd.do?brandId="+$("#brandId").val()+"&cardNo="+$("#cardNo").val(),
		type:"post",
		dataType:"json",
		success:function(data){
			if (data.flag) { // 重置密码成功
				var t = '<p>新密码已发送到您的手机，请查收。建议您修改成自己的常用密码，点击会员中心卡面下方“修改密码”就可以修改哦~</p>'
			    	+'<a href="#" onclick="javascript:redirectUpdatePwd();" class="ui-btn m-btn5" data-rel="back">确定</a>';
				$("#popupDialog").find(".ui-content").html(t);
			} else { // 重置失败
				var t = '<p>'+data.message+'</p>'
			    	+'<a href="#" class="ui-btn m-btn5" data-rel="back">确定</a>';
				$("#popupDialog").find(".ui-content").html(t);
			}
		}
   	});	
} 
function isResetPwd() {
	$(".relation-pop").click();
}
//-----------------------------------------------------------------------
function showDesc(batchNo,_this){
	if ($(_this).hasClass("icon-arrow-green")) {
		$(_this).parent().find(".list-content-wrap").hide();
		$(_this).removeClass("icon-arrow-green");
    	$(_this).addClass("icon-arrow-gray");
		return;
	}
	$.ajax({
		url:ctx+"/weixin/phonePage/getCouponDesc.do",
	    type:"post",    //数据发送方式  
	    data:"batchNo="+batchNo,   //要传递的数据；就是上面序列化的值  
	    success: function(data) {
	    	$(_this).parent().find(".list-content-wrap").show();
	    	$(_this).removeClass("icon-arrow-gray");
	    	$(_this).addClass("icon-arrow-green");
	    	if(data==null || data==''){
	    		data="没有描述信息";
	    	}
	    	$(_this).parent().find(".list-content-wrap").html(data);
	    }
	});
}

function mysubmit(formid){
	 document.getElementById(formid).submit();
}


