/**
 * 初始化.
 */
var p = 0;
var resJson = null;
$(function() {
	var $decType_Sel = $("#decType_Sel"), $dam_Sel = $("#dam_Sel"), $instr_Sel = $("#instr_Sel"), $component_Sel = $("#component_Sel"), $stime = $("#start_time"), $etime = $("#end_time");
	$.getJSON("GetMonitorItems",function(result){
		$decType_Sel.html("");
		$decType_Sel.append("<option value='' selected>---请选择---</option>");
		for(var i = 0; i < result.length; i++)
		{
			$decType_Sel.append("<option value='"+result[i].type+","+result[i].table+"'>"+result[i].describe+"</option>"); 
		}
	});
	$decType_Sel.change(function(){
		getDam();
		getMonitorItem($decType_Sel.val().split(",")[1]);
		if ($dam_Sel.val()!=""){
			getInstrNo($decType_Sel.val().split(",")[1],$dam_Sel.val());
		}
	} );
	$dam_Sel.change(function(){
		if ($decType_Sel.val()!=""){
			getInstrNo($decType_Sel.val().split(",")[1],$dam_Sel.val());
		}
	});
	$instr_Sel.change(function(){
		getMonitorItem($decType_Sel.val().split(",")[1]);
	});
	
	$("#point_sel").change(function(){
		compSel();
	});
	
//	var current_li="static";
	$("#charTab").click(function(){
		var instr_Sel = $("#instr_Sel").val()[0];
		if(instr_Sel!=null&&instr_Sel!=""){
			var instrs = instr_Sel.split(",");
			var point_sel = $("#point_sel");
			point_sel.html("");
			point_sel.append("<option value='' selected>---请选择---</option>");
			for (var i=0;i<instrs.length;i++){
				point_sel.append("<option value='"+instrs[i]+"'>"+instrs[i]+"</option>");
			}
		}/* else {
			point_sel.html("");
			point_sel.append("<option value='' selected>---请选择---</option>");
			point_sel.append("<option value='").append(instr_Sel).append("'>").append(instr_Sel).append("</option>");
		}*/
		$("#pointSec").modal("show");
	});
	
	$("#pointConf").click(function(){
		var point_sel = $("#point_sel").val();
		if(point_sel==""){
			alert("请选择测点");
		}else {
			alert(point_sel);
			var preId = $("#pred_id").val();
			$.getJSON("sfResult",{
				preId : preId,
				instr_no : point_sel
			},function(result){
				var obj = eval(result);
				drawpic(obj);
			});
		}
		$("#pointSec").modal("hide");
	});
});

function getInstrNo(table_type,dam_no){
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
