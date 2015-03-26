$(function(){
	$("#pop-cancle").click(function(){
		popClose("cancle");	
	});
});

function popPassword(){
	var topH = ($(document).scrollTop()+(window.innerHeight-$("#card-password").height())/2)+"px",
		leftL = ($(document).width() - 262)/2+"px";
	$(".card-password").css({
			top:topH,
			left:leftL
	});
	$("#pop-bg").css("height", $(document).height());
	$("#pop-bg").show();
	$("#card-password").show();
	$("#userPwd").val("");
	$("#errorPwd").text("");
}
function popClose(flag){
	$("#pop-bg").hide();
	$("#card-password").hide();	
	if("cancle"==flag){
		$("#tip").text("支付取消,请稍后重试！");
		$('#zhifu').show();
	}
}