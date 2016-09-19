/**
 * Created by qihai on 2016/1/17.
 * 添加预测模型
 */
$(function(){
	$.getJSON("getPremodelToAdd",function(result){
		var data = "";
		for(var i=0;i<result.length;i++){
			data += "<tr><td>";
			data += (i+1) + "</td><td>";
			data += result[i].modelname + "</td><td>"; 
			data += result[i].method + "</td><td>";
			data += "<button onclick='addmodel(" + result[i].modelid + ")' class='btn btn-outline btn-primary btn-xs'>添加</button></td><td>";
		}
		$("#the_tbody").html("");
		$("#the_tbody").html(data);
	});
	/*var array = [];
	$.getJSON("getPremodelToAdd",function(result){
		var options = "<option selected value=''>--请选择--</option>";
		for(var i=0;i<result.length;i++){
			options += "<option value='" + result[i].modelid + "," +result[i].modelname + "'>" + result[i].modelname + "</option>";
			array[i] = result[i];
		}
		$("#name_Sel").html("");
		$("#name_Sel").html(options);
	});
	
	$("#name_Sel").on('change',function(){
		var selindex = $(this).prop('selectedIndex');
		var options = "";
		var method = (array[selindex-1].method + "").split(",");
		for(var i=0;i<method.length;i++){
			options += "<option value='" + method[i] + "'>" + method[i] + "</option>";
		}
		$("#method_Sel").html("");
		$("#method_Sel").html(options);
		$('#method_Sel').multiselect('rebuild');
		$('#method_Sel').multiselect({
			buttonWidth: '150px',
			onChange:function(option, checked){
				var selectedOptions = $('#method_Sel option:selected');
				if (selectedOptions.length >= 20) {
                    var nonSelectedOptions = $('#method_Sel option').filter(function() {
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
	
	$("#add_btn").click(function(){
		var info = "确认添加";
		var modelid = $("#name_Sel").val().split(",")[0];
		var modelname = $("#name_Sel").val().split(",")[1];
		var method = $("#method_Sel").val() + "";
		info += "\n模型名称：" + modelname;
		info += "\n模型方法：" + method;
		if(!confirm(info))
			return;
		
		//添加模型
		$.getJSON('updateAddPremodel',{
			modelid : modelid,
			modelname : modelname,
			method : method
		},function(result){
			if(result[0].added == 1){
				alert("添加成功 ( •̀ ω •́ )y");
				window.location.href = "addpremodel.jsp";
			}else{
				alert("添加失败 (✖╭╮✖)");
				return;
			}
		});
	});*/
});

//添加模型
function addmodel(id){
//	alert(id);
	$.getJSON('updateAddPremodel',{
		modelid : id,
	},function(result){
		if(result[0].added == 1){
			alert("添加成功 ( •̀ ω •́ )y");
			window.location.href = "addpremodel.jsp";
		}else{
			alert("添加失败 (✖╭╮✖)");
			return;
		}
	});
}
