/**
 * 阈值系统参数配置
 */
$(function(){
	conf_index();
	
	conf_his();
	
	conf_model();
	
	initPointSel();
	
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
/*
var basic = $("#basic_sel");
var monitor = $("#monitor_sel");
var instrType = $("#instrType_sel");
var point = $("#point_sel");
var comp = $("#comp_sel");*/
function initPointSel(){
	getBasicItem();
	basic.on('change',getMonitor);
	monitor.on('change',getInstrType);
	instrType.on('change',getObservePoints);
	point.on('change',getComponent);
}

//配置模型参数
function conf_model(){
	$("#_conf_model").slider({
		formatter: function(value) {
			return value[0] + "%~" + value[1] + "%";
		}
	}).slider('setValue',[20,80]);
	$("#model_sigma_val").text("20%~80%");
	$("#_conf_model").on('change',function(evt){
		$("#model_sigma_val").text(evt.value.newValue[0] + "%~" + evt.value.newValue[1] + "%");
	});
}

//配置首页参数
function conf_index(){
	$.getJSON('getThresoldConf',{item:'index'},function(result){
//		console.log(result);
		conf_index_errrate(result[0].err_rate); 
		conf_index_errdt(result[0].err_dt); 
	});
	
	$('#conf_index_btn').click(function(){
		var names = 'err_rate,err_dt';
		var values = $("#err_val").text() + ',' + $("#err_dt").text();
//		alert("item = index,names = " + names + ',values = ' + values);
		var conf_info = "确认配置\r";
		conf_info += "异常点误差率：" + $("#err_val").text()  + "%\r";
		conf_info += "异常点显示日期：" + $("#err_dt").text();
		if(!confirm(conf_info))
			return;
		$.getJSON('updateThresholdConf',{
			item: 'index',
			names: names,
			values: values
		},function(result){
			result += '';
			if(result === '11'){alert("配置成功");}
			else if(result === '10'){alert("异常点日期配置失败");}
			else{alert("异常点误差率配置失败");}
		});
	});
}

//配置首页显示异常点的误差率
function conf_index_errrate(rate){
	$("#err_val").text(rate);
	$('#_index_errrate').slider({
		formatter: function(value) {
			return '误差: ' + value + "%";
		}
	}).slider('setValue',Number(rate));
	
	$('#_index_errrate').on('change',function(evt){
		$("#err_val").text(evt.value.newValue);
	});
//	$('#_index_errrate').on('slide',function(slideEvt){
//		$("#err_val").text(slideEvt.value);
//	});
}

//配置首页显示异常点的日期	
function conf_index_errdt(dt){
	$("#err_dt").html(dt);
	var $errdate = $("#errdate");
	$errdate.datetimepicker({
		lang : 'zh',
		format : 'Y-m-d',
		value : $("#err_dt").html(),
		onChangeDateTime : function(dp, $input) { 
			nextdate = $input.val();
			$("#err_dt").text($input.val());
		},
		timepicker : false
	});
}

//配置历史特征值参数
function conf_his() {
	var $step = $('#step');
	$step.slider({
		setValue: 13,
		formatter: function(value) {
			return '计算频度: ' + value + "天";
		}
	});
	var $nextdate = $('#nextdate');
	$.getJSON("getConfInfo",function(result,status){
		if(status!='success'){}
		$("#his_step_val").text(result[0].step);
		$("#his_nextdate_val").text(dealDate(result[0].nextDate));
		$step.slider('setValue',result[0].step);
		$nextdate.datetimepicker({
			lang : 'zh',
			format : 'Y-m-d',
			value : dealDate(result[0].nextDate),
			onChangeDateTime : function(dp, $input) { // 时间变化
				nextdate = $input.val();
				$("#his_nextdate_val").text($input.val());
			},
			timepicker : false
		});
	});
	
	$step.on('change',function(evt){
		$("#his_step_val").text(evt.value.newValue);
	});
	
//	$step.on('slide',function(slideEvt){
//		$("#his_step_val").text(slideEvt.value);
//	});
	
	//配置参数
	$('#conf_his_btn').click(function(){
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
			console.log(result);
			if(result[0].ok == 1){
				alert("配置成功 ( •̀ ω •́ )y");
				window.location.href = "conf.jsp";
			}else{
				alert("配置失败 (✖╭╮✖)");
			}
		});
	});
}

//截断日期字符串
function dealDate(date){
	return date.substring(0,10);
}

//检查是否整数
function isInt(a)
{
    var reg = /^-?d+$/;
    return reg.test(a);
}