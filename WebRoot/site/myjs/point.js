/**
 * @author qihai
 * 2016年4月19日
 * 生成选择测点
 */

var basic = $("#basic_sel");
var monitor = $("#monitor_sel");
var instrType = $("#instrType_sel");
var point = $("#point_sel");
var comp = $("#comp_sel");

//基本部位
function getBasicItem(){
	$.getJSON('getBasicItem',function(result){
		//console.log(result);
		var option = '<option value="" selected>---请选择---</option>';
		$.each(result,function(index,element){
			//console.log(element);
			var tmp = element.split('#');
			option += '<option value="' + tmp[0] + '">' + tmp[1] + '</option>';
		});
		basic.html(option);
	});
}

//监测项目
function getMonitor(){
	var basicId = basic.val() + '';
	if(basicId == '')return;
	$.getJSON('getMonitor',{basicId:basicId},function(result){
		//console.log(result);
		var option = '<option value="" selected>---请选择---</option>';
		$.each(result,function(index,element){
			//console.log(element);
			var tmp = element.split('#');
			option += '<option value="' + tmp[0] + '">' + tmp[1] + '</option>';
		});
		monitor.html(option);
	});
}

//仪器类型
function getInstrType(){
	var basicId = basic.val() + '';
	var monitorItem = monitor.val() + '';
	if(basicId == '')return;
	if(monitorItem == '')return;
	$.getJSON('getInstrType',{basicId:basicId,monitorItem:monitorItem},function(result){
		//console.log(result);
		var option = '<option value="" selected>---请选择---</option>';
		$.each(result,function(index,element){
			//console.log(element);
			var tmp = element.split('#');
			option += '<option value="' + tmp[0] + '">' + tmp[1] + '</option>';
		});
		instrType.html(option);
	});
}

//测点
function getObservePoints(){
	var basicId = basic.val() + '';
	var monitorItem = monitor.val() + '';
	var instr_type = instrType.val() + '';
	if(basicId == '')return;
	if(monitorItem == '')return;
	if(instr_type == '')return;
	console.log(basicId+'#' + monitorItem + '#' + instr_type);
	point.attr('multiple','multiple');
	$.getJSON('getObservePoints',{basicId:basicId,monitorItem:monitorItem,instr_type:instr_type},function(result){
		//console.log(result);
		//var option = '<option value="" selected>---请选择---</option>';
		var option = '';
		$.each(result,function(index,element){
			var tmp = element.split("#");
			option += '<option value="' + $.trim(tmp[1])+ '#' + $.trim(tmp[0]) + '">' + $.trim(tmp[0]) + '</option>';
		});
		point.html(option);
		point.multiselect('rebuild');
		point.multiselect({
			buttonWidth : '150px',
			onChange : function(option, checked) {
				var selectedOptions = $('#point_sel option:selected');
				if (selectedOptions.length >= 20) {
					var nonSelectedOptions = $('#point_sel option').filter(function() {
						return !$(this).is(':selected');
					});
					nonSelectedOptions.each(function() {
						var input = $('input[value="' + $(this).val() + '"]');
						input.prop('disabled', true);
						input.parent('li').addClass('disabled');
					});
				}
			}
		});
	});
}

//测点分量
function getComponent(){
	var the_point = point.val();
	var points = [];
	$.each(the_point,function(index,element){
		points.push(element.split('#')[1]);
	});
	if(the_point=='')return;
	console.log(the_point);
	comp.attr('multiple','multiple');
	$.getJSON('getComponent',{point:points.toString()},function(result){
		//console.log(result);
//		var option = '<option value="" selected>---请选择---</option>';
		var option = '';
		$.each(result,function(index,element){
			//console.log(element);
			var _point = element['point'];
			//console.log(element['comps']);
			var _comps = element['comps'].split("#");
			for(var i=0;i<_comps.length;i++){
				if(_comps[i] !== '0')
					option += '<option value="'+_point + '#R' + (i+1) + '">'+_point + '：' + _comps[i] + '</option>';
			}
		});
		comp.html(option);
		comp.multiselect('rebuild');
		comp.multiselect({
			buttonWidth : '150px',
			onChange : function(option, checked) {
				var selectedOptions = $('#comp_sel option:selected');
				if (selectedOptions.length >= 20) {
					var nonSelectedOptions = $('#comp_sel option').filter(function() {
						return !$(this).is(':selected');
					});
					nonSelectedOptions.each(function() {
						var input = $('input[value="' + $(this).val()
								+ '"]');
						input.prop('disabled', true);
						input.parent('li').addClass('disabled');
					});
				}
			}
		});
	});
}