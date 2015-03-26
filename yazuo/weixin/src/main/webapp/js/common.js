$.fn.extend({
	cofirm: function(param){
		var  dialogBg = '<div class="mobile_popBg" id="pop_bg" style="display:none;"></div>',
			dialogHtml = '<div class="pop-div hide" id="pop_div">'+
							'<h1>'+param.title+'</h1>'+
							'<div class="pop-text">'+param.text+'</div>'+
							'<div class="pop_sub" id="pop_btn"></div>'+
						'</div>';
		$("body").append(dialogBg);
		$("body").append(dialogHtml);
		var topH = $(document).scrollTop()+(window.innerHeight-$("#pop_div").height())/2+"px",
		leftL = ($(document).width() - 260)/2+"px";
		$("#pop_div").css({
				top:topH,
				left:leftL
			});
		$("#pop_bg").css("height", $(document).height());
		$("#pop_bg").show();
		$("#pop_div").show();
		var temp;
		if(param.type==1){//一个按钮
			temp = '<button class="shop-btn2" id="cofirm_close">确认</button>';
		}else if(param.type==2){//两个按钮
			temp = '<button class="left shop-btn3" id="cofirm_cancle">取消</button><button class="right shop-btn2" id="cofirm_sure">确认</button>';
		}
		$("#pop_btn").append(temp);
		$("#cofirm_close").click(function(){
			if(param.callBack){//如果有回调则调用回调，没有回调则直接关闭窗口
				param.callBack();
				popClose();
			}else{
				popClose();
			}
		});		
		$("#cofirm_cancle").click(function(){
			popClose();
		});		
		$("#cofirm_sure").click(function(){
			if(param.callBack){//如果有回调则调用回调，没有回调则直接关闭窗口
				popClose();
				param.callBack();
			}else{
				popClose();
			}
		});
						
	}
});
function popClose(){
	$("#pop_bg").remove();
	$("#pop_div").remove();
}