/**
 * 初始化. 按时间查询
 */
var pageindex = 1;
var pagemax = 0;
var pagesize = 8;
var total = 0;

$(function(){
	
	//时间
	var $stime = $("#start_time"), $etime = $("#end_time");
	$stime.datetimepicker({
		lang : 'zh',
		format : 'Y-m-d',
		value:getDate(0),
		onShow : function(ct) {
			if($etime.val())
				string = $etime.val().split("-");
			else string=false;
			this.setOptions({
				maxDate : string?string[0] + "/" + string[1] + "/" + string[2]:false
			});
		},
		onChangeDateTime : function(dp, $input) { // 时间变化
			stime = $input.val();
		},
		timepicker : false 
	});
	
	$etime.datetimepicker({
		lang : 'zh',
		format : 'Y-m-d',
		value:getDate(0),
		onShow : function(ct) {
			string = $stime.val().split("-");
			this.setOptions({
				minDate : $stime.val() ? string[0] + "/" + string[1] + "/" + string[2] : false
			});
		},
		onChangeDateTime : function(dp, $input) { // 时间变化
			etime = $input.val();
		},
		timepicker : false
	});
	
	$("#query_time").click(function(){
		$.getJSON('getPreHandleTimeCount',{StartTime:$stime.val()+"",EndTime:$etime.val()+""},function(result){
			total = result[0].totalrecord_time;
			pagemax = parseInt((total-1) / pagesize) + 1;
		});
		showsjData();
	});
	
});


//显示按时间查询
function showsjData(){
//	var stime = $("#start_time"), etime = $("#end_time");
//	if(stime==0)
	$("#query_time_result").show();
	$("#predataResult_time").html("正在查询...");
	var stime= $("#start_time").val();
//	if(etime==0)
	var	etime= $("#end_time").val();
	$.getJSON("getPreHandleResultByTime",{StartTime:stime,EndTime:etime,pageindex:pageindex}, function(result){
		if(result[0]==null){
			$("#predataResult_time").html("<strong style='font-size:16px;margin:18px;'>当前所选时间段没有数据，请重新选择！<strong>");
			return;
		}
		
		//分页按钮
		var pageSel = "";
		pageSel += "<select class='pagesel' id='page_Sel' onchange='changePage()'>";
		for(var j=1;j<=pagemax;j++){
			if(j==pageindex){
				pageSel += "<option selected value='" + j + "'>" + j + "</option>";
			}else{
				pageSel += "<option value='" + j + "'>" + j + "</option>";
			}
		}
		pageSel += "</select>";
//		console.log(pageSel);
		
		var pageBtn = "";
		pageBtn += "<div style='float:right;'><strong>第" + pageSel +
				"页</strong>&nbsp;&nbsp;<button class='btn btn-outline btn-info btn-xs' onclick='prepage()'>上一页</button>";
//		pageBtn += "&nbsp;<button  class='btn btn-info btn-xs' disable>"+pageindex+"</button>";
		pageBtn += "&nbsp;<button  class='btn btn-outline btn-info btn-xs' onclick='nextpage()'>下一页</button></div>";
		
		var datatable = new jStringBuffer();
		datatable.append("<table class='table table-striped table-hover' style='font-size:12px;'>");
		datatable.append("<thead><tr><th>序号</th><th>时间</th><th>描述</th><th>状态</th><th>操作</th></tr></thead>");
		for(var i=0;i<result.length;i++){
			datatable.append("<tr><td id='").append(result[i].id).append("'>").append(i+1).append("</td><td>").append(result[i].dt).append("</td><td>")
			.append(result[i].resultdiscript).append("</td><td>").append(result[i].statu == 1 ? "处理完成</td><td><div><a title='查看' href='goPreHandle.jsp?tableTag=2&preId="+result[i].id+"'>查看" : "正在处理</td><td><div><a title='继续处理' href='goPreHandle.jsp?tableTag=1&preId="+result[i].id+"'>继续处理").
			append("</td></tr>");
		}
		datatable.append("</table>");
		var $page = "";
		$page += "<span style='display:block;margin-top:10px;float:right'>";
		$page += "<strong>第"+ pageindex + "/" + pagemax + "页，共"+ total + "条记录</strong>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='firstpage()'>首页</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='prepage()'>上一页</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='nextpage()'>下一页</botton>";
		$page += "&nbsp;<input type='text' id='topage'style='height:23px;width:35px;valign:bottom;line-height=23px;text-indent:3px;font-size:10px;'/>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='gotopage()'>GO</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='lastpage()'>末页</botton></span>";
		datatable.append($page);
		$("#predataResult_time").html("");
		$("#predataResult_time").html(datatable.toString());
	});
}

//显示第一页
function firstpage(){
	pageindex = 1;
	showsjData();
}

function lastpage(){
	pageindex = pagemax;
	showsjData();
}

//显示上一页
function prepage(){
	if(pageindex ==1){
		return;
	}
	pageindex --;
	showsjData();
}

//显示下一页
function nextpage(){
	if(pageindex ==pagemax){
		return;
	}
	pageindex ++;
	showsjData();
}

//goto页面
function gotopage(){
	$index = Number($("#topage").val());
	if($index > pagemax){
		$("#topage").val(pagemax);
		return;
	}
	pageindex = $index;
	showsjData();
}

