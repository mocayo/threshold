/*添加测点*/

$(function(){
	$("input [type='checkbox']").eq(0).attr('checked',true);
	initPointSel();
	
	$("#_add").click(function(){
		var checks = $(":checkbox[name='model_methods']:checked");
		var comps = $("#comp_sel").val();
		if(comps == '' || comps.length==0){
			alert("请选择测点(✖╭╮✖)");
			return;
		}
		
		if(checks==null || checks.length == 0){
			alert("至少选择一个模型(✖╭╮✖)");
			return;
		}
//		var points = $("#point_sel").val();
//		if(points.length==0){
//			alert("请选择测点编号！");
//			return;
//		}
//		var the_point = [];
//		var the_table = [];
//		$.each(points,function(index,element){
//			var tmp = element.split('#');
//			the_point.push(tmp[1]);
//			the_table.push(tmp[0]);
//		});
//		console.log(points);
		console.log($("form").serialize());
//		return;
		$("form").submit();
	});
});

//初始化测点选择
function initPointSel(){
	getBasicItem();
	basic.on('change',getMonitor);
	monitor.on('change',getInstrType);
	instrType.on('change',getObservePoints);
	point.on('change',getComponent);
}


/*function getInstrNo(table_type,dam_no){
	$.getJSON("GetPointByDamAndTableName",{
		title : table_type,
		dam : dam_no.trim(),
	},function(result){
		$("#instr_Sel").html("");
//		$("#instr_Sel").append("<option value='' selected>---请选择---</option>");
		for(var i = 0; i < result.length; i++)
		{
			$("#instr_Sel").append("<option value='"+result[i].code+"'>"+result[i].code+"</option>"); 
		}
		$('#instr_Sel').multiselect('rebuild');
		$('#instr_Sel').multiselect({
			buttonWidth: '150px',
			onChange:function(option, checked){
				var selectedOptions = $('#instr_Sel option:selected');
				if (selectedOptions.length >= 20) {
                    var nonSelectedOptions = $('#instr_Sel option').filter(function() {
                        return !$(this).is(':selected');
                    });
 
                    var dropdown = $('#instr_Sel').siblings('.multiselect-container');
                    nonSelectedOptions.each(function() {
                        var input = $('input[value="' + $(this).val() + '"]');
                        input.prop('disabled', true);
                        input.parent('li').addClass('disabled');
                    });
                }
			}
		});
		$(".multiselect-container").css({
            'max-height': 210+'px',
            'overflow-y': 'auto',
            'overflow-x': 'hidden'
        });
	});
}

function getDam() {
	var monitorItem =  $("#decType_Sel").val().split(",")[1],$dam_select = $("#dam_Sel");
	$.get("GetIsWhereByMonitorItem",{monitorItem:monitorItem},function(result) {
		var list=result.substring(1,result.length-1).split(",");
		var row = new jStringBuffer();
		row.append("<option value='' selected>请选择</option>");
		for ( var i = 0; i < list.length; i++) {
			if(list[i]!=="null")
				row.append("<option value='").append(list[i]).append("'>")
					.append(list[i]).append("</option>");
		}
		$dam_select.html(row.toString());
	});
}

function getMonitorItem(table_type){
	var $point_no = $('#instr_Sel option:selected');
	var instr = "";
	var instr_Sel = new Array();
	if($point_no.length === 0){
	}else{
		$point_no.each(function(){
			instr += "," + $(this).val() ;
		});
		var instrsels = instr.split(",");
		for(var k = 0;k<instrsels.length;k++){
			if(instrsels[k] != ''){
				instr_Sel.push(instrsels[k]);
			}
		}
	}
	
	$.getJSON("GetMonitorItemType",{
		table_type : table_type,
	},function(result){
		$("#component_Sel").html("");
//		$("#component_Sel").append("<option value='' selected>---请选择---</option>");
		var obj = eval(result);
		if (instr_Sel.length != 0){
			for (var j = 0; j< instr_Sel.length; j++){
				if(obj.r1 != 0){
					$("#component_Sel").append("<option value='"+instr_Sel[j]+",r1'>"+instr_Sel[j]+":"+result.r1+"</option>");
				}
				if(obj.r2 != 0){
					$("#component_Sel").append("<option value='"+instr_Sel[j]+",r2'>"+instr_Sel[j]+":"+result.r2+"</option>");
				}
				if(obj.r3 != 0){
					$("#component_Sel").append("<option value='"+instr_Sel[j]+",r3'>"+instr_Sel[j]+":"+result.r3+"</option>");
				}
			}
		} else {
				if(obj.r1 != 0){
					$("#component_Sel").append("<option value='r1'>"+obj.r1+"</option>");
				}
				if(obj.r2 != 0){
					$("#component_Sel").append("<option value='r2'>"+obj.r2+"</option>");
				}
				if(obj.r3 != 0){
					$("#component_Sel").append("<option value='r3'>"+obj.r3+"</option>");
				}
				
		}
		
		$('#component_Sel').multiselect('rebuild');
		$('#component_Sel').multiselect({
			buttonWidth: '150px',
			onChange:function(option, checked){
				var selectedOptions = $('#component_Sel option:selected');
				if (selectedOptions.length >= 20) {
                    var nonSelectedOptions = $('#component_Sel option').filter(function() {
                        return !$(this).is(':selected');
                    });
 
                    var dropdown = $('#component_Sel').siblings('.multiselect-container');
                    nonSelectedOptions.each(function() {
                        var input = $('input[value="' + $(this).val() + '"]');
                        input.prop('disabled', true);
                        input.parent('li').addClass('disabled');
                    });
                }
			}
		});
		$(".multiselect-container").css({
            'max-height': 210+'px',
            'overflow-y': 'auto',
            'overflow-x': 'hidden'
        });
	});
}

function compSel(){
	var instr_no = $("#point_sel").val();
	var preId = $("#pred_id").val();
	var $compsel = $("#comp_sel");
	$compsel.html("");
	$compsel.append("<option value='' selected>---请选择---</option>");
	$.getJSON("compSelect",{
		preId : preId,
		instr_no : instr_no
	},function(result){
		var obj = eval(result);
		for (var i = 0; i < obj.length; i++) {
			if (obj[i].r1 != 0) {
				$compsel.append("<option value='r1'>" + obj[i].r1 + "</option>");
			}
			if (obj[i].r2 != 0) {
				$compsel.append("<option value='r2'>" + obj[i].r2 + "</option>");
			}
			if (obj[i].r3 != 0) {
				$compsel.append("<option value='r3'>" + obj[i].r3 + "</option>");
			}
		}
	});
}
*/