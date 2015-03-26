$(function(){
	
	$("body").on('click','.ui-select .selset-year',function(){
		var val = $(this).val();
		if(val == '年'){
			$(this).siblings("span").text(val);
		}else{
			$(this).siblings("span").text(val+'年');
		}
	});
	$("body").on('click','.ui-select .selset-month',function(){
		var val = $(this).val();
		if(val == '月'){
			$(this).siblings("span").text(val);
		}else{
			$(this).siblings("span").text(val+'月');
		}
	});
	$("body").on('click','.ui-select .selset-day',function(){
		var val = $(this).val();
		if(val==null)val="日";
		if(val == '日'){
			$(this).siblings("span").text(val);
		}else{
			$(this).siblings("span").text(val+'日');
		}
	});
	// 初始化年、月、日
	$("#year").siblings("span").text(cyear+"年");
	$("#month").siblings("span").text(cmonth+"月");
	$("#day").siblings("span").text(cday+"日");
	if ((cyear!=null && cyear!="") && (cmonth!=null && cmonth!="") && (cday && cday!="")!=null) {
		$("input[name='birthday']").parent().addClass("ui-state-disabled");
	}
	
	var time = new Date();
	var year = time.getFullYear();
	var months=['01','02','03','04','05','06','07','08','09','10','11','12'];
	for(var i=parseInt(year);i>=1900;i--){
		$('#year').append("<option value='"+i+"'>"+i+"</option>");
	};
	for(var i=0;i<months.length;i++){
		$('#month').append("<option value='"+months[i]+"'>"+months[i]+"</option>");
	}
	var days = ['01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31'];
	$('#month').on('change',function(){
		//2 月  28天
		var month = $(this).val();
		if(parseInt(month)==2){
			$('#day').empty();
//			$('#day').append("<option value='day'>日</option>");
			for(var i=0;i<days.length-3;i++){
				$('#day').append("<option value='"+days[i]+"'>"+days[i]+"</option>");
			}
		} else if(parseInt(month)==4||parseInt(month)==6||parseInt(month)==9||parseInt(month)==11){//4 6 9 11 30天
			$('#day').empty();
//			$('#day').append("<option value='day'>日</option>");
			for(var i=0;i<days.length-1;i++){
				$('#day').append("<option value='"+days[i]+"'>"+days[i]+"</option>");
			}
		}else{
			$('#day').empty();
//			$('#day').append("<option value='day'>日</option>");
			for(var i=0;i<days.length;i++){
				$('#day').append("<option value='"+days[i]+"'>"+days[i]+"</option>");
			}
		}
	});
	
	
});
