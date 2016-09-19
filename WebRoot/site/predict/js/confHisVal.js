/**
 * 历史特征值配置
 */
$(function() {
	var $step = $('#step');
	var $nextdate = $('#nextdate');
	$nextdate.datetimepicker({
		lang : 'zh',
		format : 'Y-m-d 00:00:00.0',
		value : $("#cur_nextDate").html(),
		onChangeDateTime : function(dp, $input) { // 时间变化
			nextdate = $input.val();
		},
		timepicker : false
	});
	
	//配置参数
	$('#conf_btn').click(function(){
		var ex = /^\d+$/;
		if (!ex.test($step.val())) {
			alert('步长需要为整数！');
			return;
		}
		var conf_info = "确认配置\r";
		conf_info += "步长：" + $step.val() + "\r";
		conf_info += "下一个日期：" +$nextdate.val() + "\r";
		if(!confirm(conf_info))
			return;
		$.getJSON("updateConfHis",{step:$step.val()+"",nextDate:$nextdate.val()+""},function(result){
			if(result[0].ok == 1){
				alert("配置成功 ( •̀ ω •́ )y");
				window.location.href = "getConfHis";
			}else{
				alert("配置失败 (✖╭╮✖)");
			}
		});
	});
});

//检查是否整数
function isInt(a)
{
    var reg = /^-?d+$/;
    return reg.test(a);
}