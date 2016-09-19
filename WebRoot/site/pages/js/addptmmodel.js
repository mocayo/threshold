/**
 * Created by qihai on 2016/1/17.
 * 添加预测模型
 */
$(function(){
	$.getJSON("getPtmmodelToAdd",function(result){
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