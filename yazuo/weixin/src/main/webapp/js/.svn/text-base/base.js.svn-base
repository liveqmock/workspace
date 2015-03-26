/* 动画库 */
function getStyle(obj, attr)
{
	var result='';
	if(obj.currentStyle){
		result=obj.currentStyle[attr];
	}else{
		result=getComputedStyle(obj, false)[attr];
	}
	
	if(attr=='opacity'){
		return Math.round(parseFloat(result)*100);
	}else{
		return parseInt(result);
	}
}

function startMove(obj, json, fnCallBack){
	clearInterval(obj.timer);
	obj.timer=setInterval(function (){
		var attr;
		
		var bStop=true;		
		for(attr in json){
			var iCur=getStyle(obj, attr);
			var iSpeed=(json[attr]-iCur)/5;
			iSpeed=iSpeed>0?Math.ceil(iSpeed):Math.floor(iSpeed);
			
			if(iCur!=json[attr]){bStop=false;}
			
			if(attr=='opacity'){
				obj.style.filter='alpha(opacity:'+(iCur+iSpeed)+')';
				obj.style.opacity=(iCur+iSpeed)/100;
			}else{
				obj.style[attr]=iCur+iSpeed+'px';
			}
		}
		
		if(bStop){
			clearInterval(obj.timer);
			if(fnCallBack){fnCallBack();}
		}
	}, 30);
}

function myAddEvent(obj, sEventName, fnHandler){
	if(obj.attachEvent){
		obj.attachEvent('on'+sEventName, fnHandler);
	}else{
		obj.addEventListener(sEventName, fnHandler, false);
	}
};

function getByClass(oParent, sClass)
{
	oParent=oParent||document;
	var aEle=oParent.getElementsByTagName('*');
	var re=new RegExp('(^|\\s)'+sClass+'(\\s|$)', 'i');
	var result=[];
	for(var i=0;i<aEle.length;i++){
		if(re.test(aEle[i].className))
		{
			result.push(aEle[i]);
		}
	}
	return result;
};

var setFlag=true;
function channelFocus(id,iSpeed,isShow){

	var oCFWrap=document.getElementById(id);		
		var oUl=oCFWrap.getElementsByTagName('ul')[0];
		var aLi=oUl.getElementsByTagName('li');	
		
		var i=0;
		var iNow=0;
		
		if(aLi.length>1){
            oCFWrap.timerSwitch = 0;

            var oLi=aLi[0].cloneNode(true);
			oUl.appendChild(oLi);

            oCFWrap.timer1=setInterval(function(){
				focusTab();
                oCFWrap.timerSwitch+=iSpeed;
			},iSpeed);
			function focusTab(){
				iNow++;
				if(iNow==aLi.length){
					oUl.style.top=0+'px';iNow=1;
				}
				
				if(oCFWrap.timerSwitch<2000){				
					startMove(oUl, {top:-aLi[0].offsetHeight*iNow}, function(){
                        oUl.style.top=0+'px';//回到初始位置
                        if(winflag == 4){
                        	$(oUl).find('img').eq(0).attr('src',lottery_pic+lottery_item_pic);//设置返回来的奖品
                        }else{
							//没中奖时随机出各个列表的最终图片路径
							$(oUl).find('img').eq(0).attr('src',getStepImg(id));
						}
                    });
				}
				else{
					clearInterval(oCFWrap.timer1);	
					
					//document.getElementById("JS_tigerGameBtn").style.display='block';
					//document.getElementById("JS_tigerGameSpan").style.display='none';
				}						
			};				
		};	
}

function showPopUp(sInfo,confirm){
	if($("#JS_popUp").length>0){
		$("#JS_popUp").remove();
	}

	var oBody=document.getElementsByTagName('body')[0];
	var oPopUp=document.createElement('div');
	
	oPopUp.id='JS_popUp';
	oPopUp.className='popUp';
	if(confirm){
		oPopUp.innerHTML="<p class='popInfo'>"+sInfo+"</p><p class='popBtns'><a class='popConfirmBtn sureBtn' href='javascript:;'>确定</a><a class='popConfirmBtn closeBtn' href='javascript:;'>取消</a></p>";
	}else{
		oPopUp.innerHTML="<p class='popInfo'>"+sInfo+"</p><p class='popBtns'><a href='javascript:;' class='popBtn'>确定</a></p>";
	}
	oBody.appendChild(oPopUp);
	
}

var isSure;
$(".popBtn").live("click",function(){
	$(this).parents(".popUp").hide();
});

$(".sureBtn").live("click",function(){
	$(this).parents(".popUp").hide();
	
});
$(".closeBtn").live("click",function(){
	$(this).parents(".popUp").hide();
});

//获取某一个列表的随机img的src
var allSrc=[];  //没中奖时随机出的三个不同src数组
var diffSrc = '';    //如果前两个相同，则递归出的第三个不同的src
function getStepImg(divId){
	var listImg=document.getElementById(divId).getElementsByTagName('img');
	var arrayImg=[];
	for(var i=0;i<listImg.length;i++){
		arrayImg.push(listImg[i].getAttribute('src'));	
	}
	var oneSrc;
	if(allSrc.length == 2) {
		oneSrc = arrayImg[GetRandomNum(0,arrayImg.length-1)];
		while(allSrc[0] == allSrc[1] && allSrc[1] == oneSrc) {
			oneSrc = arrayImg[GetRandomNum(0,arrayImg.length-1)];
		}
		
	} else {
		oneSrc=arrayImg[GetRandomNum(0,arrayImg.length-1)];
	}
	allSrc.push(oneSrc);	
	return oneSrc;
}

//产生随机数
function GetRandomNum(Min,Max){   
	var Range = Max - Min;   
	var Rand = Math.random();   
	return(Min + Math.round(Rand * Range));   
}

//抽奖没中时的提示语
var arrOnlyChance=['爆搓晕了吧，奖品都迷路了','爆搓晕了吧，奖品都迷路了','一定是你刮奖的心态不够虔诚'];
var arrMoreChance=['心里默拜灶王爷，再试一次运气足','在吃货的道路上，永远都有下一次','让肠胃麻利消化，给运气留点空间'];
function getResults(iChance){
	var resultIndex=GetRandomNum(0,2);
	if(iChance > 1){
		return arrMoreChance[resultIndex];
	}else{
		return arrOnlyChance[resultIndex];
	}
};

