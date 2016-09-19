/**
 * 显示预测模型测点
 */
var pageindex = 1;
var total = 0;
var pagemax = 0;
$(function(){
 	showPoints();
});

function showPoints(){
	var model = 'ptmmodel';
 	$.getJSON('getPoints',{
 		pageindex : pageindex,
 		model : model
 	},function(result){
 		total = result.pop();
 		pagemax = parseInt((total-1)/10) + 1;
 		var content = "";
 		var confurl = 'conf' + model + 'point?&type=' + model;
 		content += "<table class='table table-striped  table-hover' id='ptable'>";
		content += "<thead><th>#</th><th>测点编号</th><th>分量R1</th><th>分量R2</th><th>分量R3</th><th>监测仪器</th><th>配置</th></thead>";
		content += "<tbody>";
		for(var i=0;i<result.length;i++){
			var url = confurl + "&no=" + result[i].instr_no;
			content += "<tr>";
			content += "<td>" + ((pageindex-1) * 10 + i + 1) + "</td>";
			content += "<td>" + result[i].instr_no + "</td>";
			content += "<td>" + result[i].r1 + "</td>";
			content += "<td>" + result[i].r2 + "</td>";
			content += "<td>" + result[i].r3 + "</td>";
			content += "<td>" + result[i].monitorItem + "</td>";
			content += "<td><a href='"+ url +"'><i class='fa fa-cog'></a></td>";
			content += "</tr>";
		}
		content += "</tbody></table>";
		var $page = "";
		$page += "<span style='display:block;margin-top:10px;float:right'>";
		$page += "<strong>第"+ pageindex + "/" + pagemax + "页，共"+ total + "条记录</strong>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='firstpage()' id='_first'>首页</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='prepage()' id='_pre'>上一页</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='nextpage()' id='_next'>下一页</botton>";
		$page += "&nbsp;<input type='text' id='topage'style='height:23px;width:35px;valign:bottom;line-height=23px;text-indent:3px;font-size:10px;'/>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='gotopage()'>GO</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='lastpage()' id='last'>末页</botton></span>";
		$("#_content").html(content + $page);
 	});	
}
 
 //第一页
function firstpage(){
	if(pageindex == 1)return;
	pageindex = 1;
	showPoints();
}

//末页
function lastpage(){
	if(pageindex == pagemax)return;
	pageindex = pagemax;
	showPoints();
}

//上一页
function prepage(){
	if(pageindex==1){
//		alert("已经是第一页！");
		return;
	}
	pageindex --;
	showPoints();
}

//下一页
function nextpage(){
	pageindex ++;
	if(pageindex > pagemax){
//		alert('这是最后一页！');
//		pageindex = pagemax;
		return;
	}
	showPoints();
}

function gotopage(){
//	if($("#topage").val() == '')return;
	pageindex = parseInt($("#topage").val());
	if(pageindex>pagemax){
		$("#topage").val(pagemax);
		return;
	}
	showPoints();
}
 