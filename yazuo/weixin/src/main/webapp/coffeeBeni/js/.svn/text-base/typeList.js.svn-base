// JavaScript Document

$(".buyBtn").bind("click",function(){
	if($("#popUpTip").length > 0){
		$("#popUpTip").show();	
	}else{
		var ponHtml='<div id="popUpTip">'
						+ '<div class="popBg"></div>'
						+ '<div class="popInfo">在线购买功能即将开通，敬请期待！<a href="javascript:;" class="popConfirmBtn">确定</a></div>'    
					 +'</div>';
		
		$("body").append(ponHtml);
		$("#popUpTip").show();	
	}	
});

$(".popConfirmBtn").live("click",function(){
	$("#popUpTip").hide();	
})