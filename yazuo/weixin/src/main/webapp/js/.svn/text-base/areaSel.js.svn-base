var flag = 0;
$(function(){
	//判断是否支持html5，支持即获取经纬度
	var isLoad = $("#isLoad").val();
	 if(navigator.geolocation && isLoad==0) {
         //document.getElementById("div_tip").innerHTML = "HTML5 Geolocation is supported in your browser.";
		 navigator.geolocation.getCurrentPosition(updateLocation,function(){
				if ((isLoad==null || isLoad=="" || isLoad=="undefinde") && flag ==0) {
					$("#isLoad").val(1);
					updatePageInArea(0, '');
				} 
			}); // 先获取位置，在调用updateLoction方法
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
				
				var i=0,resultHtml='<li id="JS_allArea"><a href="javascript:updatePageInArea(0,\'全部地区\')" data-areaCode="0">全部地区</a><i class="areaIco showPartIco"></i></li>';
				for(i=0;i<reStr.length;i++){
					var array = reStr[i].split(",");
					var thisStr= array[1];//reStr[i].areaId;		
					var resultArr=array[0];//reStr[i].name;
					var visibleArr = array[2];
					resultHtml+='<li><a href="javascript:;" data-areaCode="'+thisStr+'">'+resultArr+'</a>';
					if (visibleArr=='true') {
						resultHtml +='<i class="areaIco showPartIco"></i>';
					}
					resultHtml +='</li>';
				}
				$("#JS_mainAreaList").html(resultHtml);
				//$("#JS_mainAreaList").show();
				//$("#JS_mainAreaList").parent().show(); 
			}
		 }
	});
});

//点击选择地区 加载行政区
/*$("#JS_showMainAreaBtn").on("click",function(){
	var searchBox=$(this).parent().find(".mainArea");
	if(searchBox.css("display") === 'block'){
		searchBox.hide();	
		$(this).find(".areaIco").removeClass("allAreaUpIco").addClass("allAreaIco");
		return;
	}
	$(this).find(".areaIco").removeClass("allAreaIco").addClass("allAreaUpIco");		
	$("#JS_mainAreaList").show();
	$("#JS_mainAreaList").parent().show(); 
});*/

function h3SearchClick(obj){
	var searchBox=$(obj).parent().find(".mainArea");
	if(searchBox.css("display") === 'block'){
		searchBox.hide();	
		$(obj).find(".areaIco").removeClass("allAreaUpIco").addClass("allAreaIco");
		return;
	}
	$(obj).find(".areaIco").removeClass("allAreaIco").addClass("allAreaUpIco");		
	$("#JS_mainAreaList").show();
	$("#JS_mainAreaList").parent().show(); 
}

//点击行政区域 加载详细地区
$("#JS_mainAreaList a").live("click",function(){
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
				$("#JS_mainAreaList a").removeClass("active");
				_this.addClass("active");
				updatePageInArea(pAreaId,parentName);
			} else {
				var i=0,resultHtml='<li><a href="javascript:;" data-areaCode="'+pAreaId+'">全部'+parentName+'</a></li>';
				for(i=0;i<data.length;i++){
					var nowData=data[i];
					resultHtml+='<li><a href="javascript:;" data-areaCode="'+nowData.areaId+'">'+nowData.name+'</a></li>';				
				}
				$("#JS_mainAreaList a").removeClass("active");
				_this.addClass("active");
				$("#JS_partAreaList").html(resultHtml);
				$("#JS_partAreaList").show();	
			}
		 }
	});	
});

//点击区县列表，刷新页面
$("#JS_partAreaList a").live("click",function(){
	var areaId=$(this).attr("data-areaCode");
	var areaName=$(this).text();
	updatePageInArea(areaId,areaName);	
});

function updatePageInArea(areaId,areaName){
	var pageIndex = 1;
	$("#latitude").val("");
	$("#longitude").val("");
	$("#province").val("");
	//调用刷新页面方法
	window.location.href = ctx + "/weixin/newFun/subbranchSortArea.do?brandId="+$("#brandId").val()+"&weixinId="+$("#weixinId").val()+"&areaId="
		+areaId+"&areaName="+areaName+"&pageIndex="+pageIndex+"&flag="+$("#isLoad").val();
}

//分页，下一页
function loadMoreRecord () {
	var pageIndex = parseInt($("#currentIndex").val())+1;
	var areaId = $("#areaId").val();
	var latitude =$("#latitude").val();
	var longitude =$("#longitude").val();
	var province = $("#province").val();
	var resquestData = "brandId="+$("#brandId").val()+"&pageIndex="+pageIndex+"&areaId="+areaId +
		"&latitude="+latitude+"&province="+province+"&longitude="+longitude;
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
    			$("#isLoad").val(data.flag);
    			if (list!=null && list.length>0) { // 按距离搜索门店
    				$("#span_area").html(data.areaName);
    				$("#div_tip").html("您附近的门店：");
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
	var ul = document.getElementById("merchantUl");
	var data = responseData.merchantList;
	for (var i=0;i<data.length; i++) {
		var temp = data[i];
		var address = temp.address!=null && temp.address!="null" ? temp.address : "";
		var phone = temp.officePhone!=null && temp.officePhone!="null" ? temp.officePhone : "";
		var strhtml ="<li id='"+(i+1)+"'><h1>"+temp.merchantName+"</h1><p>地址："+address+"<br />" +
			"电话："+phone +"<span class='error-tip'></span></p>" +
			"<div style='padding-top : 10px ; margin  : 0px ; width : 280px ; overflow : hidden ;'>"
			+"<a href='javascript:checkAndCall(\""+temp.officePhone +"\",\""+(i+1)+"\");' class='yd-btn simple-btn'><i class='icon'></i>&nbsp;预订</a>" +
			" <a href='http://api.map.baidu.com/marker?location="+temp.latitude+","+temp.longitude+"&title="+temp.merchantName+"&name="+temp.merchantName+"" +
				"&content="+temp.address+"&output=html&src=weiba|weiweb' " +
			"class='nav-btn simple-btn'><i class='icon'></i>&nbsp;导航</a><br/> </div></li>";
		ul.innerHTML +=strhtml;
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
	var pageIndex = 1;
	window.location.href = ctx + "/weixin/newFun/subbranchSortArea.do?brandId="+$("#brandId").val()+"&weixinId="+$("#weixinId").val()
	+"&pageIndex="+pageIndex+"&flag="+$("#isLoad").val()+"&merchantName="+subbranchName+"&source=search";
}

