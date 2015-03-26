function manageImage(id) {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/weixin/imageList.do?id="+id);
}

function manageSubbranch(id) {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/weixin/subbranchList.do?id="+id);
}
function managePreference(id) {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/weixin/preferenceList.do?id="+id);
}
function manageDishes(id) {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/weixin/dishesList.do?id="+id);
}
function manageAutoreply(id) {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/weixin/autoreplyList.do?id="+id);
}
function manageActivityConfig(id) {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/weixin/activityConfigList.do?id="+id);
}
function newBusiness() {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/jsp/businessAdd.jsp");
}

function modifyBusiness(voteStatus,id,weixinId,token,title,birthdayMessage,brandId,companyWeiboId,salutatory,tagName,tagId,isAllowWeixinMember) {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/jsp/businessModify.jsp?isAllowWeixinMember="+isAllowWeixinMember+"&voteStatus="+voteStatus+"&id="+id+"&weixinId="+weixinId+"&token="+token+"&title="+title+
				"&brandId="+brandId+"&companyWeiboId="+companyWeiboId+"&salutatory="+salutatory+"&birthdayMessage="+birthdayMessage+"&tagName="+tagName+"&tagId="+tagId);
}
function manageBoss(id) {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/weixin/bossList.do?id="+id);
}
function deleteBusiness(id) {
	if(confirm("确认删除此商户，请注意你的行为！")){
	$.ajax({
		type : "post",
		url : ctx+'/weixin/deleteBusiness.do?',
		cache : false,
		dataType : "text",
		data : "id=" + id,
		error : function(XMLHttpRequest) {// 请求失败时调用函数
			if (processCommErr(XMLHttpRequest)) {
				alert('错误', '提交失败', 'error');
			}
		},
		success : function(json) {
			if (json == "1") {
				alert('成功');
				$("#businessListDiv").load(ctx+"/weixin/businessList.do");
			} else {
				alert('失败');
			}

		}
	});
	}
}
function importSubbranch(brandId,companyWeiboId) {
	$.ajax({ 
        type: "get", 
        url: ctx+"/weixin/importSubbranch.do?brandId="+brandId+"&companyWeiboId="+companyWeiboId, 
        dataType: "text", 
        success: function (data) { 
        	alert("本次操作成功导入数据"+data+"条");
        }, 
        error: function (XMLHttpRequest, textStatus, errorThrown) { 
                alert(errorThrown); 
        } 
});
	
}
//优惠券描述信息
function manageCouponDescribe(merchantId,flag) {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/weixin/newDescribe/describeList.do?merchantId="+merchantId+"&flag="+flag);
}
//跳转服务配置
function manageServiceConfig(brandId){
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/weixin/serviceConfig/serviceConfig.do?brandId="+brandId);
}

function createMenu(brandId) {
	window.open(ctx+"/weixin/createButton/initMenuList.do?brandId="+brandId);
}
// 添加会员权限或修改
function mangerMerberRight(businessId, memberRights) {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/jsp/addMerberRight.jsp?businessId="+businessId+"&memberRights="+memberRights);
}

function addStory(brandId) {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/weixin/remoteInterface/getWeixinStory.do?brandId="+brandId);
}

//设置模版
function managerTemplate(brandId) {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/weixin/template/queryTemplate.do?brandId="+brandId);
}
//设置商户参数
function managerParams(brandId) {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/weixin/queryBrandParam.do?brandId="+brandId);
}
//设置accesstokentime
function managerAccesstokenTime(brandId) {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/weixin/queryAccessTokenTime.do?brandId="+brandId);
}
//删除Accesstoken
function deleteAccessToken(brandId) {
	$.ajax({ 
        type: "get", 
        url: ctx+"/weixin/deleteAccessToken.do?brandId="+brandId, 
        dataType: "text", 
        success: function (data) { 
        	alert(data);
        }, 
        error: function (XMLHttpRequest, textStatus, errorThrown) { 
                alert(errorThrown); 
        } 
});
	
}
function addKey(brandId) {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/weixin/brandAndKey/editRelative.do?brandId="+brandId);
}

function createResource() {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/setting/resource/listResource.do");
}

function settingResource(brandId) {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/setting/resource/initMerchantResource.do?brandId="+brandId);
}

function settingCardRecommend(brandId) {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/setting/cardRecommend/loadCardType.do?brandId="+brandId);
}

//列所有用户或添加用户
function listUser () {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/user/manager/listUser.do");
}

// 修改个人信息
function editUser(userId) {
	$("#businessModifyDiv").show();
	$("#businessModifyDiv").load(ctx+"/user/manager/modfiyInfo.do?userId="+userId);
}

// 搜索商户
function search() {
	var title = $("#searchTitle").val();
	var brandId = $("#searchBrandId").val();
	var weixinId = $("#searchWeixinId").val();
	var isCertification = $("#isCertification").val();
	var isOpenPayment = $("#isOpenPayment").val();
	
	var param = "?title="+title+"&brandId="+brandId+"&weixinId="+weixinId+"&isCertification="+isCertification+"&isOpenPayment="+isOpenPayment;
	$("#businessListDiv").load(ctx+"/weixin/businessList.do"+param);
}

// 上一页
function prePage() {
	var currentPage = parseInt($("#currentPage").val());
	if ((currentPage-1)<=0) {
		alert("当前页是第一页");
		return;
	} else {
		$("#currentPage").val(currentPage-1);
		
		var title = $("#searchTitle").val();
		var brandId = $("#searchBrandId").val();
		var weixinId = $("#searchWeixinId").val();
		var isCertification = $("#isCertification").val();
		var isOpenPayment = $("#isOpenPayment").val();
		var currentPage = $("#currentPage").val();
		
		var param = "?title="+title+"&brandId="+brandId+"&weixinId="+weixinId+"&isCertification="+isCertification+"&isOpenPayment="+isOpenPayment+"&currentPage="+currentPage;
		$("#businessListDiv").load(ctx+"/weixin/businessList.do"+param);
	}
}

// 下一页
function nextPage() {
	var total = parseInt($("#totalPage").val());
	var currentPage = parseInt($("#currentPage").val());
	if ((currentPage+1)>=total) {
		alert("当前是最后一页");
		return;
	} else {
		$("#currentPage").val(currentPage+1);
		
		var title = $("#searchTitle").val();
		var brandId = $("#searchBrandId").val();
		var weixinId = $("#searchWeixinId").val();
		var isCertification = $("#isCertification").val();
		var isOpenPayment = $("#isOpenPayment").val();
		var currentPage = $("#currentPage").val();
		
		var param = "?title="+title+"&brandId="+brandId+"&weixinId="+weixinId+"&isCertification="+isCertification+"&isOpenPayment="+isOpenPayment+"&currentPage="+currentPage;
		$("#businessListDiv").load(ctx+"/weixin/businessList.do"+param);
	}
}