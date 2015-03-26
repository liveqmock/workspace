
//日期获取到一位数时， 补零操作
function fillZero(num, digit){
	var str=''+num;	
	while(str.length<digit){
		str='0'+str;
	}	
	return str;
}



/*获取前一天、后一天
*  scaleStep = 1  当前日期的后 |scaleStep| 天  
*  scaleStep = -1 当前日期的前 |scaleStep| 天 
*/
function getPrevNextDay(currentDate, scaleStep) {   
	var dArr = currentDate.split('-');
	var aNowDate = new Date(dArr[0],dArr[1]-1,dArr[2]);

	var yesterday_milliseconds=aNowDate.getTime()+(1000*60*60*24)*scaleStep;

    var yesterday = new Date(yesterday_milliseconds);       
        
    var strYear = yesterday.getFullYear();    
    var strDay = yesterday.getDate();    
    var strMonth = yesterday.getMonth()+1;  
    sResultDate = strYear+"-"+fillZero(strMonth, 2)+"-"+fillZero(strDay, 2);  
    return sResultDate;  
}  

//日期控件初始化、绑定事件
if($('.datePicker').length >= 1){
	var sNowDate=(new Date()).getFullYear()+"-"+fillZero(((new Date()).getMonth()+1), 2)+"-"+fillZero((new Date()).getDate(), 2);
	sNowDate=getPrevNextDay(sNowDate,-1);//会员统计默认显示前一天
	$(".datePicker").val(sNowDate);
	$('.datePicker').datepicker({showOtherMonths:true,maxDate: new Date()});
	
/*	//设置datePicker 左右位置
	var iViewWidth=document.documentElement.clientWidth || document.body.clientWidth;
	$(".datePicker").live("click",function(){
		var oPickerDiv=$("#ui-datepicker-div");
		var iPickerWidth=document.getElementById('ui-datepicker-div').offsetWidth;
		var lastLeft=parseInt((iViewWidth-iPickerWidth)/2);
		$(oPickerDiv).css("left",lastLeft+'px');
		$(oPickerDiv).css("zIndex",'99999');		
	});*/
}


//判断一个值是否是标准的手机号格式或者是前端号码9开头且9位数
//如果是标准手机号返回true，不是手机号则返回 false;
function isPhoneNum(phoneNum){
	var filter=/^1[3|4|5|7|8]\d{9}$/; 
	//var filter2=/^9\d{10}$/; 
	newPhoneNum=phoneNum.replace(/-/g,'');
	return filter.test(newPhoneNum);//||filter2.test(newPhoneNum); 
}


/*
*  为指定元素子元素最后插入新元素
*  parentObj   父元素对象
*  sTagName    要创建的新节点的tagName
*  sObjClass   新元素的class名称
*  sInners     新元素的innerHTML 
*/
function appendChildBehind(parentObj,sTagName,sObjClass,sInners){
	var oNewDiv=document.createElement(sTagName);
	oNewDiv.className=sObjClass;
	oNewDiv.innerHTML=sInners;

    $(parentObj).append(oNewDiv);
}


Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
};


//textarea内容显示可换行替换
function replaceBr(sInfo){
	return sInfo.replace(/\n/g,"<br/>");
}
//列表加载等待提示========================
function loadingTip(oParent){
	var oTip=document.createElement("div");
	oTip.id="loadingTip";
	oTip.innerHTML="<i class='loadingIco'></i>";
	$(oParent).append(oTip);
}
function removeLoading(oLi){
	$(oLi).remove();
}
/**
 * 查询数组内是否存在某个值
 * @param value
 * @param array
 */
function isValueExists(value,array){
	if(null==value||null==array||0==array.length){
		return false;
	}
	for(var i=0;i<array.length;i++){
		if(value==array[i]||(array[i].roleName!=null&&value==array[i].roleName)) return true;
	}
	return false;
}