$(function(){
	// 进入菜品页面，选中菜品导航，其他导航不选中
	$(".footer-on").removeClass("footer-on");
	$(".footer-icon-menu").parent().parent().parent().addClass("footer-on");
	
	$(".menu-show-icon").on("tap",function(){
		$(".menu-show-detail").css("height",$(".ui-page").height());
		var right = $(this).parents(".menu-show-detail").css("right"),
		    par = $(this).parents(".menu-show-detail");
		var temp = '<div class="m-layer-bg"></div>';
		if(right=='0px'){
			par.animate({right:'-245px'}, { duration: "slow" });
			$(".m-layer-bg").remove();
		}else{
			par.animate({right:'0px'}, { duration: "slow" });
			$("body").append(temp);
			$(".m-layer-bg").css("height",$(".ui-page").height());
		}
	});
	
	$(".menu-good-num").on("tap", function(){
		if ($(this).hasClass("num-on")) {
			return false;
		}
		var disheId=$(this).parent().parent().attr("data-dishesId");
		var merchantId=$(this).parent().parent().attr("data-merchantId");
		var oVote=$(this).find("em")[0];
		var iVoteNum=parseInt(oVote.innerHTML);
		var voteClass = $(this);
		$.ajax({
			 type: "get",
			 url: ctx+'/weixin/newFun/dishesParise.do?dishesId='+ disheId +'&merchantId='+merchantId+"&weixinId="+$("#weixinId").val(),
			 cache: false,
			 dataType : "json",
			 success: function(data){
				 if(data.flag){
					 oVote.innerHTML=(iVoteNum + 1);
					 voteClass.addClass("num-on");
				}
			 }
		});
		return false;
	});
	
	$("body").on("click", ".m-wrapper .menu-module-content ul li", function(){
		var dishesId = $(this).attr("data-dishesId");
		var merchantId = $(this).attr("data-merchantId");
		var weixinId = $(this).attr("data-weixinId");
		
		window.location.href=ctx+"/weixin/newFun/detailDishes.do?dishesId="+dishesId+"&brandId="+merchantId+"&weixinId="+weixinId;
	});
	
});