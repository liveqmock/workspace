var flag = 0;
$(function(){
	// 进入门店页面，选中门店导航，其他导航不选中
	$(".footer-on").removeClass("footer-on");
	$(".footer-icon-shop").parent().parent().parent().addClass("footer-on");
	
	$(".search-store input").click(function(){
		$(".area-wrap").hide();
		$(".search-area").removeClass("area-on");
	});
	
	$(".search-area").on("click",function(){//搜索地区
		var disp = $(".area-wrap").css("display"),
			area = $(".area-wrap");
		if(disp == "none"){
			area.show();
			$(this).addClass("area-on");
		}else{
			area.hide();
			$(this).removeClass("area-on");
		}
	});
	$(".sheng-on").next().css({
		"border-top":"none"
	});
	//点击行政区域 加载详细地区
	$("body").on("click","#JS_mainAreaList li",function(){
		if($(this).attr("id") == 'JS_allArea'){
			return;	
		}	
		var pAreaId=$(this).attr("data-areaCode");
		var parentName=$(this).text();
		var _this=$(this);
		$.ajax({
			 type: "get",
			 url:  ctx + '/weixin/newFun/loadChildArea.do?pAreaId=' + pAreaId+"&brandId="+$("#brandId").val(),
			 dataType:"json",
			 success: function(data){
				if (data ==null || data.length==0) { // 没有二级菜单
					$("#JS_mainAreaList li").removeClass("sheng-on");
					_this.addClass("sheng-on");
					_this.addClass("show-page-loading-msg");
					_this.attr("data-prefetch='ture'");
					updatePageInArea(pAreaId,parentName);
				} else {
					var i=0,resultHtml='<li data-areaCode="'+pAreaId+'" class="show-page-loading-msg" >全部'+parentName+'</li>';
					for(i=0;i<data.length;i++){
						var nowData=data[i];
						resultHtml+='<li data-areaCode="'+nowData.areaId+'" class="show-page-loading-msg" >'+nowData.name+'</li>';				
					}
					$("#JS_mainAreaList li").removeClass("sheng-on");
					_this.addClass("sheng-on");
					$(".sheng-on").css({
						"border-top":"1px solid #cfcfcf",
						"border-bottom":"1px solid #cfcfcf"
					});
					$(".sheng-on").next().css({
						"border-top":"none",
						"border-bottom":"none"
					});
					$(".sheng-on").prev().css({
						"border-top":"1px solid #ededed",
						"border-bottom":"none"
					});
					$("#JS_partAreaList").html(resultHtml);
					$("#JS_partAreaList").show();	
				}
			 }
		});	
	});
	
	//点击区县列表，刷新页面
	$("body").on("click","#JS_partAreaList li",function(){
		var areaId=$(this).attr("data-areaCode");
		var areaName=$(this).text();
		updatePageInArea(areaId,areaName);	
	});
	
	//判断是否支持html5，支持即获取经纬度
	 if(navigator.geolocation) {
		 var options = {timeout:60000};
         //document.getElementById("div_tip").innerHTML = "HTML5 Geolocation is supported in your browser.";
		 navigator.geolocation.getCurrentPosition(updateLocation,errorHandler,options); // 先获取位置，在调用updateLoction方法
	 } else {
		updatePageInArea(0, '');
	 } 

	 $.ajax({
		 type: "get",
		 url:  ctx + '/weixin/newFun/initArea.do?brandId='+$("#brandId").val(),
		 dataType:"json",
		 success: function(reStr){
			if (reStr==null || reStr.length==0) {
				$("#JS_areaSelect").hide();
			} else {
				$("#JS_areaSelect").show();
				$("#JS_showMainAreaBtn").fadeIn(1000);
				
				var i=0,resultHtml='<li id="JS_allArea" class=\"sheng-on show-page-loading-msg\" onclick="javascript:updatePageInArea(0,\'全部地区\')" data-areaCode="0" >全部地区<i></i></li>';
				for(i=0;i<reStr.length;i++){
					var array = reStr[i].split(",");
					var thisStr= array[1];//reStr[i].areaId;		
					var resultArr=array[0];//reStr[i].name;
					var visibleArr = array[2];
					resultHtml+='<li class=\"sheng-info\" data-areaCode="'+thisStr+'">'+resultArr+'';
					if (visibleArr=='true') {
						resultHtml +='<i></i>';
					}
					resultHtml +='</li>';
				}
				$("#JS_mainAreaList").html(resultHtml);
				$("#JS_allArea").next().css({
					"border-top":"none"
				});
				//$("#JS_mainAreaList").show();
				//$("#JS_mainAreaList").parent().show(); 
			}
		 }
	});
});


function updatePageInArea(areaId,areaName){
	var pageIndex = 1;
	$("#latitude").val("");
	$("#longitude").val("");
	$("#province").val("");
	$("#merchantName").val("");
	var brandName = $("#brandName").val();
	var requestData = {};
	requestData.brandId = $("#brandId").val();
	requestData.weixinId = $("#weixinId").val();
	requestData.areaId = areaId;
	requestData.areaName = areaName;
	requestData.pageIndex = pageIndex;
	requestData.brandName = brandName;
	$.ajax({
		url:ctx + "/weixin/newFun/subbranchSortArea.do",
		type:"get",
		data:requestData,
		dataType:"json",
		success:function(responseData){
			$(".area-wrap").hide();
			$(".ui-icon-loading").hide();
			$("#merchantUl").siblings("h1").hide();
			$("#merchantUl").siblings("h1").html("");
			$("#merchantUl").html("");
			appendMerchatMsg(responseData);
		}
	});
	//调用刷新页面方法
	//window.location.href = ctx + "/weixin/newFun/subbranchSortArea.do?brandId="+$("#brandId").val()+"&weixinId="+$("#weixinId").val()+"&areaId="
		//+areaId+"&areaName="+areaName+"&pageIndex="+pageIndex+"&flag="+$("#isLoad").val()+"&brandName="+brandName;
}

//分页，下一页
function loadMoreRecord () {
	var pageIndex = parseInt($("#currentIndex").val())+1;
	var areaId = $("#areaId").val();
	var latitude =$("#latitude").val();
	var longitude =$("#longitude").val();
	var province = $("#province").val();
	var merchantName = $("#merchantName").val();
	var resquestData = "brandId="+$("#brandId").val()+"&pageIndex="+pageIndex+"&areaId="+areaId +
		"&latitude="+latitude+"&province="+province+"&longitude="+longitude+"&merchantName="+merchantName;
	if (merchantName !=null && merchantName!="") {
		resquestData +="&source=search";
	}
	$.ajax({
		url:ctx + "/weixin/newFun/loadMoreSubbranch.do",
		type:"get",
		data:resquestData,
		dataType:"json",
		success:function(responseData){
			appendMerchatMsg(responseData);
		}
	});
}
// hmtl5获取经纬度失败
function errorHandler(err) {
/*  if(err.code == 1) {
    alert("Error: Access is denied!");
  }else if( err.code == 2) {
    alert("Error: Position is unavailable!");
  }*/
  //if ((isLoad==null || isLoad=="" || isLoad=="undefinde") && flag ==0) {
		//$("#isLoad").val(1);
		updatePageInArea(0, '');
	//} 
}

// 判断是否支持html5，获取当前坐标
function updateLocation(position) {
	falg =1;
    var latitude = position.coords.latitude;
    var longitude = position.coords.longitude;
    // 等待调用百度api获取完省之后再调用下面的方法
    gpsxy(longitude,latitude,function(province){
    	var index = $("#currentIndex").val();
    	if (index == null || index =="" || index =="undefined") {
    		index = 1;
    	}
    	var requestData = "brandId=" + $("#brandId").val() + "&latitude="+latitude+"&longitude="+longitude+"&province="+province+"&pageIndex=" + index+"&flag=1";
    	
    	$("#latitude").val(latitude);
    	$("#longitude").val(longitude);
    	$("#province").val(province);
    	//按距离搜出门店且排序
    	$.ajax({
    		url:ctx + "/weixin/newFun/subbranchSort.do",
    		type:"get",
    		data:requestData,
    		dataType:"json",
    		success:function(data){
    			var list = data.merchantList;
    			if (list!=null && list.length>0) { // 按距离搜索门店
    				$("#span_area").html(data.areaName);
    				$("#merchantUl").siblings("h1").show();
    				$("#merchantUl").siblings("h1").html("您附近的门店：");
    				appendMerchatMsg(data);
    			} else { // 列所有门店
    				updatePageInArea(0, '');
    			}
    		}
    	});
    });
}

gpsxy = function (longitude,latitude,callback){//添加一个参数作为回调函数，该函数可以获取addre参数 
	var point = new BMap.Point(longitude, latitude);
	var gc = new BMap.Geocoder(); 
	gc.getLocation(point, function(rs){
		var addComp = rs.addressComponents;
		//addre = addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber;
		if(callback) callback(addComp.province);//把addre传递到回调函数中，这样就可以在函数外部使用返回值了
	}); 
};

// ajax请求，绑定返回的门店数据
function appendMerchatMsg (responseData) {
	var merchantUl = $("#merchantUl");
//	var ul = document.getElementById("merchantUl");
	var data = responseData.merchantList;
	for (var i=0;i<data.length; i++) {
		var temp = data[i];
		if (temp.merchantName !="虚拟门店") {
			var address = temp.address!=null && temp.address!="null" ? temp.address : "";
			var phone = temp.officePhone!=null && temp.officePhone!="null" ? temp.officePhone : "";
			var strhtml ="<li id='"+(i+1)+"'><h2>"+temp.merchantName+"</h2><p class=\"store-address\">地址："+address+"</p>" +
				"<p class=\"store-address\"><span style=\"color:#888;border:none;\">电话："+phone +"</span><span class='error-tip'></span></p>" 
				+"<p class=\"store-btn\"><a href='javascript:checkAndCall(\""+temp.officePhone +"\",\""+(i+1)+"\");' class='store-phone'><i></i>&nbsp;预订</a>" +
				" <a href='http://api.map.baidu.com/marker?location="+temp.latitude+","+temp.longitude+"&title="+temp.merchantName+"&name="+temp.merchantName+"" +
					"&content="+temp.address+"&output=html&src=weiba|weiweb' " +
				"class='store-navigation'><i></i>&nbsp;导航</a></p></li>";
			merchantUl.append(strhtml);
//			ul.innerHTML +=strhtml;
		}
	}
	$("#areaId").val(responseData.areaId);
	var total = responseData.totalPageCount;
	var index = responseData.page;
	$("#currentIndex").val(index);
	if (total > index) {
		$("#loadMoreBnt").show();
	} else {
		$("#loadMoreBnt").hide();
	}
}

function searchSubbranch() {
	var subbranchName = $("#merchantName").val();
	var brandName = $("#brandName").val();
	var pageIndex = 1;
	
	var resquestData = "brandId="+$("#brandId").val()+"&weixinId="+$("#weixinId").val()
		+"&pageIndex="+pageIndex+"&merchantName="+subbranchName+"&source=search&brandName="+brandName;
	$.ajax({
		url:ctx + "/weixin/newFun/subbranchSortArea.do",
		type:"get",
		data:resquestData,
		dataType:"json",
		success:function(responseData){
			$(".ui-icon-loading").hide();
			$("#merchantUl").html("");
			appendMerchatMsg(responseData);
		}
	});
	
	
//	window.location.href = ctx + "/weixin/newFun/subbranchSortArea.do?brandId="+$("#brandId").val()+"&weixinId="+$("#weixinId").val()
//	+"&pageIndex="+pageIndex+"&merchantName="+subbranchName+"&source=search&brandName="+brandName;
}

function checkAndCall(phoneNo,id){
	alert(phoneNo);
	if(phoneNo != null && phoneNo.trim() != ''){
		var phoneNoList = phoneNo.split('/');
		window.location.href="tel:"+phoneNoList[0];
	}else{
		$('#'+(id)+' .error-tip').text("暂无商家电话！");
		return;
	}
}
function getLocation(x,y,name,address) {
	var str = ctx+"jsp/baidumap.jsp?x="+x+"&y="+y+"&name="+name+"&address="+address;	
	window.location.href=str;
}

