$(function(){
	$("#pop-cancle").click(function(){
		popCloseHide();	
	});
});

function popRefund(){
	var topH = ($(document).scrollTop()+(window.innerHeight-$("#refund").height())/2)+"px",
	leftL = ($(document).width() - 260)/2+"px";
	$("#refund").css({
			top:topH,
			left:leftL
	});
	$("#pop-bg").css("height", $(document).height());
	$("#refundMessage").val("");
	$("#errorTip").html("");
	$("#pop-bg").show();
	$("#refund").show();
}
function popCloseHide(){
	$("#pop-bg").hide();
	$("#refund").hide();	
}