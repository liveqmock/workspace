$(function(){
	$(document).on('focus','.datePicker',function(){//时间控件
		$(this).datepicker();
		$("#ui-datepicker-div").css({
			'left':'10px'
		});
	});
})