// JavaScript Document
/*
*配合jquery
*返回json数据必须包含id和value两个属性
*/
define(["avalon","text!./autocomplete.html","text!./autocomplete.css"],function(avalon,tepHtml,tepCss){
    var win = this,
        erp = win.erp;
	var widget=erp.module['autocomplete']=function(element,data,vmodels){
		element.innerHTML='';
		var model = avalon.define(data.autocompleteId,function(vm) {
			avalon.mix(vm,data.autocompleteOptions);
			vm.$init=function(){
				var styleTag=document.getElementsByTagName('style');
				var headTag=document.getElementsByTagName("head")[0];
				if(styleTag && styleTag[0]){//添加css
					styleTag[0].innerHTML+=tepCss;
				}else{
					var styleTag=document.createElement('style');
					styleTag.innerHTML=tepCss;
					headTag.appendChild(styleTag);	
				}
				element.innerHTML = tepHtml;//模板替换
				avalon.scan(element,[model].concat(vmodels));
			};
			
			vm.dataAll='';//全部数据
			vm.matchData=[];//匹配到的数据
			
			vm.getThinkAll=function(){//获取全部结果
				this.onkeyup=vm.getKeyValue;//获得按键值
				vm.isInputChange();//输入框有内容时进行匹配
				$(document).on('click',vm.closeThink);//这里可能污染全局
			};
			
			vm.getKeyValue=function(e){//获得按键值
				e=e||window.event;
				var keyValue= e.keyCode;
				switch (keyValue){
					case 38:
						vm.setKeyValue('prev');//上下箭头设置input内容
						break;
					case 40:
						vm.setKeyValue('next');//上下箭头设置input内容
						break;
					default :
						vm.isInputChange();//判断输入框是否被改变
				}
			};
			
			vm.isInputChange=function(){//判断输入框是否被改变
				var inputValue=vm.searchValue;
				inputValue=inputValue.replace(/(^\s+)|(\s+$)/g,'');
				if(inputValue.length>0){
					vm.getMatchData(inputValue);
				}else{
                    vm.showHide="none";
                    vm.matchData=[];
                }
			};
			
			vm.getMatchData=function(value){//显示匹配到的数据
				vm.matchData=[];
				for(var i=0,len=vm.dataAll.length;i<len;i++){//匹配数据
					for(var k in vm.dataAll[i]){
						if(k == 'value'){
							if(vm.dataAll[i][k].indexOf(value)!=-1){
								vm.matchData.push(vm.dataAll[i]);	
							}
						}
					}
				}
				if(vm.matchData.length){//显示数据
					vm.showHide='block';
				}else{//隐藏数据
					vm.showHide = 'none';
					vm.matchData = [];
				}
			};
			
			vm.setKeyValue=function(select){//上下箭头设置input内容
				if(typeof vm.num=='undefined'){vm.num=-1;}
				var oDivs=$('.ui_thinkSearch_search')[0].getElementsByTagName('div');
				var len=oDivs.length;
				if(select=='prev'){
					vm.num--;
					if(vm.num<0){vm.num=len-1;}
				}else if(select=='next'){
					vm.num++;
					if(vm.num>=len){vm.num=0;}
				}
				for(var i=0;i<len;i++){
					oDivs[i].className='ui_thinkSearch_search_list';
				}
				oDivs[vm.num].className+=' ui_thinkSearch_search_cur';
				vm.searchValue=oDivs[vm.num].innerHTML;
				vm.searchId=oDivs[vm.num].getAttribute('data-id');
			};
			
			vm.getValue=function(){//获取click结果值并隐藏列表
				vm.searchId=this.getAttribute('data-id');
				vm.searchValue=this.innerHTML;
				vm.showHide='none';
			};
			
			vm.closeThink=function(e){//输入框失去焦点
				e=e||window.event;
				var target=e.target||e.srcElement;
				if(!$('.ui_thinkSearch').has(target).length){
					vm.showHide="none";
					vm.matchData=[];
					$(document).off('click',vm.closeThink)
				}
			};
		});		
		return model;
	};
	widget.defaults={
		searchValue:'',//data的value
		showHide:"none",//控制联想框的显示隐藏
		searchId:'',//data的id
        getDataAll: avalon.noop
	};
	return avalon;
});