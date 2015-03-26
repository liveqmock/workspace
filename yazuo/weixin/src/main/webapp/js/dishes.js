//点赞
$(".dishesVote").live("click",function(){
	if($(this).hasClass("voted")){
		return false;	
	}
	
	var disheId=$(this).parent().attr("data-dishesId");
	var merchantId=$(this).parent().attr("data-merchantId");
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
				 voteClass.addClass("voted");			 
			}
		 }
	});
		
});

function showDetail(id, merchantId, weixinId) {
	var t = "";
	var htmlContent = $("#JS_dishesList");
	$.ajax({
		url:ctx+"/weixin/newFun/detailDishes.do?dishesId="+id+"&merchantId="+merchantId+"&weixinId="+weixinId,
		type:"get",
		dataType:"json",
		success:function(detailDishes){
			t="<h3 class='dishDetailTitle'><a href='"+ctx+"/weixin/newFun/dishes.do?brandId="+merchantId+"&weixinId="+weixinId+"'>菜品</a><em>&gt;</em>"+detailDishes.dishesName+"</h3>";
			if (detailDishes.dishesPic !=null && detailDishes.dishesPic!="") {
				t +=  "<img src='"+detailDishes.dishesPic +"' class='bigDishesImg' />";
			}
			t +="<div class='dishesInfo'>"+
			"<h3 data-dishesId='"+detailDishes.id +"' data-merchantId='"+detailDishes.merchantId +"'>";
			if (detailDishes.dishesIsNew ==1) {
				t +="<img src='"+ctx+"/images/dishe_new.png' height='16' width='16' style='position:relative;top:-1px;'/>&nbsp;";
			}
			t += detailDishes.dishesName+"<p>";
			if (detailDishes.dishesPrice>0){
				if (detailDishes.dishesPriceMember>0) { // 会员价与原价相同即不显示
					t+="<del>￥"+detailDishes.dishesPrice+"</del>";
				}  else {
					t+="<em>￥"+detailDishes.dishesPrice+"</em>";
				}
			}
			if(detailDishes.dishesPriceMember>0){
				t+="&nbsp;&nbsp;&nbsp;&nbsp;<em>会员价：<label>￥"+detailDishes.dishesPriceMember+"</label></em>";
			}
			if (detailDishes.dishesUnitName && detailDishes.dishesUnitName !='') {
				t+="/"+detailDishes.dishesUnitName;
			}
			var str = "";
			if (detailDishes.pariseStatus==1) {
				str = "dishesVote voted";
			} else {
				str = "dishesVote";
			}
			t+="</p><a href='javascript:;' class='"+str+"'><i class='dishesIco'></i><em>"+detailDishes.dishesPraise +"</em></a>"+
				"</h3><p>"+detailDishes.dishesDesc +"</p></div>";
			htmlContent.html(t);
		}, error:function(detailDishes) {
			
		}
	});
}

