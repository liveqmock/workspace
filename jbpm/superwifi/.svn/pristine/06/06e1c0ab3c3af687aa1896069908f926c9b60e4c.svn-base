$(function(){
	$(document).on('click','.htb-sort',function(){//排序点击效果
		var sort_icon = $(this).children(".sort-icon");
		if(sort_icon.hasClass("htb-arrow-bottom")){
			$(this).children(".sort-word").text("按时间升序");
			sort_icon.removeClass("htb-arrow-bottom").addClass("htb-arrow-top");
			//排序标示，1降序，2升序
			sortBy(2);//排序
		}else{
			$(this).children(".sort-word").text("按时间降序");
			sort_icon.removeClass("htb-arrow-top").addClass("htb-arrow-bottom");
			sortBy(1);//排序
		}
	});
	
	
	$(document).on('click','.form-icon-search',function(){
		//window.location.href="/controller/merchant/formManager.do?searchKey="+$("#searchKey").val()+"&operatorMobileNumber="+operatorMobileNumber+"&operatorId="+operatorId;
		sortBy(1);
	});
	
	function sortBy(sortBynumber){
		$.ajax({
			type : "POST",
			url :  '/controller/merchant/formManagerAjax.do',
			cache : false,
			//contentType : "application/json; charset=utf-8",
			data :"searchKey="+$("#searchKey").val()+"&operatorMobileNumber="+operatorMobileNumber+"&operatorId="+operatorId+"&timeSortBy="+sortBynumber,
			dataType : "json",
			success : function(retObj) {
				if(!retObj.flag){
					alert("查询失败，请咨询客服人员");
					return;
				}
				$(".merCount").text(retObj.data.merchantList.length);
				var strHtml = "";
				for(var i =0;i<retObj.data.merchantList.length;i++){
					var mer = retObj.data.merchantList[i];
					var insertTime = new Date(mer.insertTime).format("yyyy-MM-dd hh:mm:ss");
					
					strHtml +="<li><a href='/controller/merchant/updateWifiInfoForERP.do?operatorMobileNumber="+mer.operatorMobileNumber+"&operatorId="+mer.operatorId+"&merchantId="+mer.merchantId+"&brandId="+mer.brandId+"' target='_self'><div>" +
							"<span class='htb-sh-name'>"+mer.merchantName+"</span>" +
							"<span class='htb-cu-name'>人名：<span>"+mer.bossName+"</span></span>" +
							"</div><p class='htb-form-time'>"+insertTime+"</p><span class='htb-arrow-right'></span>" +
							"</a></li>";
				}
				$(".merchants").html(strHtml);
			},
			error : function(XMLHttpRequest) {
				console.log("failed.");
			}
		}); 
	}
	
	sortBy(1);
});