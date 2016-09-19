/**
 * 数据预处理
 */
var pageindex = 1;
var pagemax = 0;
var pagesize = 10;
var total = 0;
$(function(){
	var $dec = $("#dec_Sel");
	var $dam = $("#dam_Sel");
	
	/*// 获取监测项
	$.getJSON("GetMonitorItems", function(result) {
		var row = new jStringBuffer();
		row.append("<option value='' selected>--请选择--</option>");
		for ( var i = 0,len = result.length; i < len; i++) {
			row.append("<option value='").append(result[i].type).append(",")
			   .append($.trim(result[i].table)).append("'>").append($.trim(result[i].describe)).append("</option>");
		}
		$dec.html(row.toString());
		//show测点编号
		showNoSelect();
	});

	//检测仪器 变化，坝段变化
	$dec.on("change",function() {
		var monitorItem =  $(this).val().split(",")[1],$dam = $("#dam_Sel");
		$.get("GetIsWhereByMonitorItem",{monitorItem:monitorItem},function(result) {
			var list=result.substring(1,result.length-1).split(",");
			var row = new jStringBuffer();
			row.append("<option value='' selected>--请选择--</option>");
			for ( var i = 0; i < list.length; i++) {
				if(list[i]!=="null")
					row.append("<option value='").append(list[i]).append("'>")
						.append(list[i]).append("</option>");
			}
			$dam.html(row.toString());
			showNoSelect();
		});
	});
	
	$dam.on("change",function() {
		showNoSelect();
	});
	*/
	
	/*//时间
	var $stime = $("#start_time"), $etime = $("#end_time");
	$stime.datetimepicker({
		lang : 'zh',
		format : 'Y-m-d',
		value: getDate(0),
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
	});*/
	initPointSel();
	
	//获取预处理批次记录总数
	$.getJSON('getPreHandleCount',function(result){
		total = parseInt(result[0].totalrecord);
		pagemax = parseInt((total-1) / pagesize) + 1;
		predata(pageindex);
	});
	
	$("#handle_button").click(function(){
		var points = $("#point_sel").val();
		if(points.length==0){
			alert("请选择测点编号！");
			return;
		}
		var the_point = [];
		var the_table = [];
		$.each(points,function(index,element){
			var tmp = element.split('#');
			the_point.push(tmp[1]);
			the_table.push(tmp[0]);
		});
		console.log(points);
		console.log(the_point.join(','));
		console.log(the_table.join(','));
//		if($("#dec_Sel").val()===""){
//			alert("请选择监测仪器！");
//			return;
//		}
//		if($("#dam_Sel").val()===""){
//			alert("请选择坝段！");
//			return;
//		}
//		if($("#point_Sel").val()===null){
//			alert("请选择测点编号！");
//			return;
//		}
//		if($("#ptm_Sel").val()===""){
//			alert("请选择预测模型！");
//			return;
//		}
		var info = new jStringBuffer();
//		info.append("监测仪器：" + $("#dec_Sel option:selected").text());
//		info.append("\n坝段：" + $("#dam_Sel option:selected").text());
//		info.append("\n测点编号：" + $("#point_Sel option:selected").text());
		info.append("测点编号：" + the_point.toString());
		info.append("\n表名：" + the_table.toString());
		info.append("\n预处理模型：" + $("#ptm_Sel option:selected").text());
		info.append("\n开始时间：" + $("#start_time").val());
		info.append("\n至：" + $("#end_time").val());
		if(!confirm(info)){
			return;
		}
		//$(this).attr("disabled","disabled");
		
		//开始预处理
//		var $starttime = $("#start_time"), $endtime = $("#end_time"), $point_no = $('#point_Sel option:selected'),
//		$monitoritem_select=$("#dec_Sel"),$dam_select=$("#dam_Sel");
//		var url = "commonPreHandleAction?startTime=" + $starttime.val() + "&endTime=" + $endtime.val() +"&title="+$monitoritem_select.val().split(",")[1]
//		+ "&dam=" + $dam_select.val() + "&monitoritemtype="+$monitoritem_select.val().split(",")[0]+"&type=1&pointIdString=";
//		if($point_no.length === 0){
//			url += "null,";
//		}else{
//			$point_no.each(function(){
//				url += $(this).val() + ",";
//			});
//		}
		//显示模态框
		showModal('pretreat_batch');
//		console.log(url);
		//预处理跳转页面
		$.getJSON('commonPreHandleAction',{
			startTime:$("#start_time").val()+'',
			endTime:$("#end_time").val()+'',
			type: 1,
			pointIdString: the_point.join(','),
			title: the_table.join(','),
			monitoritemtype: 'monitoritemtype'
		},function(result,status){
			$('#myModal').modal('hide');
			if(status=='success'){
				window.location.href = "goPreHandle.jsp?tableTag=1&" + result.res;
			}else{
				alert('预处理出错，请重试！');
			}
		});
		
		/*$.getJSON(url.substring(0,url.length-1), function(result){
			$('#myModal').modal('hide');
			window.location.href = "goPreHandle.jsp?tableTag=1&" + result.res;
		});*/
	});
	
});

//初始化测点选择
function initPointSel(){
	getBasicItem();
	basic.on('change',getMonitor);
	monitor.on('change',getInstrType);
	instrType.on('change',getObservePoints);
	point.on('change',getComponent);
	//预处理模型名称
	$.getJSON("getPtmModel", function(data){
		var options = new jStringBuffer();
		options.append("<option value='' selected>--请选择--</option>");
		for ( var i = 0, len = data.length; i < len; i++) {
			if(i==0)
				options.append("<option selected value='").append(data[i].modelid).append("'>").append(data[i].modelname).append("</option>");
			else
				options.append("<option value='").append(data[i].modelid).append("'>").append(data[i].modelname).append("</option>");
		}
		$("#ptm_Sel").html(options.toString());
	});
	var $stime = $("#start_time"), $etime = $("#end_time");
	$stime.datetimepicker({
		lang : 'zh',
		format : 'Y-m-d',
		value : getDate(0),
		onShow : function(ct) {
			if ($etime.val())
				string = $etime.val().split("-");
			else
				string = false;
			this.setOptions({
				maxDate : string ? string[0] + "/" + string[1] + "/"
						+ string[2] : false
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
		value : getDate(0),
		onShow : function(ct) {
			string = $stime.val().split("-");
			this.setOptions({
				minDate : $stime.val() ? string[0] + "/" + string[1] + "/"
						+ string[2] : false
			});
		},
		onChangeDateTime : function(dp, $input) { // 时间变化
			etime = $input.val();
		},
		timepicker : false
	});
}

//显示测点编号
function showNoSelect(){
	if($("#dec_Sel").val()==="")
		return;
	if($("#dam_Sel").val()==="")
		return;
	var dam= $("#dam_Sel").val().trim();
	var title=($("#dec_Sel").val().split(",")[1]);
	$.get("GetPointByDamAndTableName",{dam:dam,title:title}, function(result){
		var data = JSON.parse(result);
//		console.log(data);
		var options = new jStringBuffer();
//		options.append("<option value='' selected>--请选择--</option>");
		for ( var i = 0, len = data.length; i < len; i++) {
			options.append("<option value='" + data[i].code + "'>" + data[i].code + "</option>");
		}
		$("#point_Sel").html(options.toString());
		$('#point_Sel').multiselect('rebuild');
		$('#point_Sel').multiselect({
			buttonWidth: '150px',
			onChange:function(option, checked){
				var selectedOptions = $('#point_Sel option:selected');
				if (selectedOptions.length >= 20) {
                    var nonSelectedOptions = $('#point_Sel option').filter(function() {
                        return !$(this).is(':selected');
                    });
 
                    //var dropdown = $('#point_Sel').siblings('.multiselect-container');
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

//显示预处理批次表
function predata(pageindex){
//	alert(pageindex);
//	var pageindex = 3;
	$.getJSON("getAllPreHandleBatch",{pageindex:pageindex,pagesize:pagesize},function(result){
		var datatable = new jStringBuffer();
		datatable.append("<table id='pretable' class='table table-striped table-hover' style='font-size:12px;'>");
		datatable.append("<thead><tr><th>#</th><th style='text-align:center;'>处理时间</th><th>描述</th><th style='text-align:center;'>状态</th><th colspan=2 style='text-align:center;'>操作</th></tr></thead>");
		datatable.append("<tbody>");
		var i = 0;
		for(;i<result.length;i++) {
			datatable.append("<tr><td>");
			datatable.append((pageindex-1)*pagesize + i+1).append("</td><td>");
			datatable.append(dealDate(result[i].dt)).append("</td><td>");
			datatable.append(result[i].resultdiscript).append("</td><td>");
			datatable.append(result[i].statu == 1 ? "处理完成</td><td><div><a title='查看' href='goPreHandle.jsp?tableTag=2&preId="
					+ result[i].id+"'>查看" : "正在处理</td><td><div><a title='继续处理' href='goPreHandle.jsp?tableTag=1&preId="+result[i].id+"'>继续处理");
			datatable.append("</a></div></td><td><div><a title='删除' href='javascript:void(0);' onclick=delpredata(this,'").append(result[i].id).append("')>删除</a></div></td></tr>");
		}
		datatable.append("</tbody></table>");
		
		var $page = "";
		$page += "<span style='display:block;margin-top:10px;float:right'>";
		$page += "<strong>第"+ pageindex + "/" + pagemax + "页，共"+ total + "条记录</strong>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='firstpage()'>首页</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='prepage()'>上一页</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='nextpage()'>下一页</botton>";
		$page += "&nbsp;<input type='text' id='topage'style='height:20px;width:35px;valign:bottom;line-height=23px;text-indent:3px;font-size:10px;'/>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='gotopage()'>GO</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='lastpage()'>末页</botton></span>";
		datatable.append($page);
		$("#predata").html("");
		$("#predata").html(datatable.toString());
	});
}

function dealDate(date){
	return date.split(":")[0] + ":" + date.split(":")[1];
}

//删除预处理批次
function delpredata(obj,id){
    if (confirm("确认删除？", "您确定要删除吗？")) {
    	$.get("deletePreHandleResByBatchId",{id:id}, function(result){
    		if(result=="0"){
    			$(obj).parent().parent().parent().remove();
    			alert("删除成功！");
    		}
    	});
    }
}

//显示第一页
function firstpage(){
	pageindex = 1;
	predata(pageindex);
}

//显示最后一页
function lastpage(){
	pageindex = pagemax;
	predata(pageindex);
}

//显示上一页
function prepage(){
	pageindex --;
	if(pageindex <=0){
//		alert('已经是第一页！');
//		pageindex = 1;
		return;
	}
	predata(pageindex);
}

//显示下一页
function nextpage(){
	pageindex ++;
	if(pageindex >pagemax){
//		alert('这是最后一页！');
//		pageindex = pagemax;
		return;
	}
	predata(pageindex);
}

//goto页面
function gotopage(){
	pageindex = parseInt($("#topage").val());
	if(pageindex>pagemax){
//		alert("不能超过最大页数！");
		$("#topage").val(pagemax);
		return;
	}
	predata(pageindex);
}
//function changePage(){
//	pageindex = $('#page_Sel').val();
//	predata(pageindex);
//}

//显示模态框
function showModal(id){
	var modal = '<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">';
	modal += '<div class="modal-dialog">';
    modal += '<div class="modal-content">';
    modal += '<div class="modal-header">';   
    modal += '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>';
    modal += '<h4 class="modal-title" id="myModalLabel">数据预处理</h4>';
    modal += '</div>';
    modal += '<div class="modal-body">系统处理数据中，请稍候。。。</div>';
    modal += '<div class="modal-footer">';
    modal += '<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>';
    modal += '<button type="button" class="btn btn-primary">确定</button>';
    modal += '</div>';
    modal += '</div>';
    modal += '</div>';
    modal += '</div>';
    $('#' + id).append(modal);
    $('#myModal').modal();
}
