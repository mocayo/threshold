//collapse-link.js
$(function(){
	var link = '<a class="collapse-link" style="float:right"><i class="fa fa-chevron-up"></i></a>';
	$(".panel-heading").append(link);
	$(".collapse-link").click(function(event){
		var obj = $(this).children("i");
		if(obj.hasClass("fa-chevron-up")){
			$(this).parent().siblings(".panel-body").hide();
			obj.removeClass("fa-chevron-up").addClass("fa-chevron-down");
		}else{
			$(this).parent().siblings(".panel-body").show();
			obj.removeClass("fa-chevron-down").addClass("fa-chevron-up");
		}
	});
});