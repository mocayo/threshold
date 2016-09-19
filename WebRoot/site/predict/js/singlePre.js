/**
 * 初始化. 单点预测
 */
// 存放查看的id对应测点和分量
var idpointInfo = {};
// 存放开始结束时间preId和instr_no,startTime,endTime
var periodInfo = {};
// 存放统计信息preId和statisticsData的map
var statisticInfo = {};
// 存放详细信息preId和dateStr1,dataStr2,dataStr3的map
//var detailedResult = {};
//详细信息页码
var detailindex = 1;
var detailpagemax = 1;

var premodelname = {};

//var exportUrl = 'http://10.65.20.16:8080/highcharts-export';
var exportUrl = 'http://121.248.200.5:8000/highcharts-export';

$(function() {
	showSinTable();
	initPointSel();
	/*var $dec = $("#dec_Sel");
	var $dam = $("#dam_Sel");
	var $no = $("#point_Sel");

	// 获取监测项
	$.getJSON("GetMonitorItems", function(result) {
		var row = new jStringBuffer();
		row.append("<option value='' selected>--请选择--</option>");
		for (var i = 0, len = result.length; i < len; i++) {
			row.append("<option value='").append(result[i].type).append(",")
					.append($.trim(result[i].table)).append("'>").append(
							$.trim(result[i].describe)).append("</option>");
		}
		$dec.html(row.toString());
		// show测点编号
//		showNoSelect();
	});

	// 检测仪器 变化，坝段变化
	$dec.on("change", function() {
		var monitorItem = $(this).val().split(",")[1], $dam = $("#dam_Sel");
		$.get("GetIsWhereByMonitorItem", {
			monitorItem : monitorItem
		}, function(result) {
			var list = result.substring(1, result.length - 1).split(",");
			var row = new jStringBuffer();
			row.append("<option value='' selected>--请选择--</option>");
			for (var i = 0; i < list.length; i++) {
				if (list[i] !== "null")
					row.append("<option value='").append(list[i]).append("'>")
							.append(list[i]).append("</option>");
			}
			$dam.html(row.toString());
			showNoSelect();
		});
	});

	$dam.on("change", function() {
		showNoSelect();
	});

	$no.on("change", function() {
		showComponent();
	});
	// 预测模型名称
	$.getJSON("getPreModel", function(data) {
		var options = new jStringBuffer();
		options.append("<option value='' selected>--请选择--</option>");
		for (var i = 0, len = data.length; i < len; i++) {
//			if(i==0)
//				options.append("<option selected value='").append(data[i].method).append("'>").append(data[i].modelname).append("</option>");
//			else
				options.append("<option value='").append(data[i].method).append("'>").append(data[i].modelname).append("</option>");
			premodelname["model" + data[i].modelid] = data[i].modelname;
		}
		$("#premodel_Sel").html(options.toString());
	});
	// 时间
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
	});*/

	$("#predict_button").click(function() {
		 predict();
//		testpre();
	});

});

//初始化测点选择
function initPointSel(){
	getBasicItem();
	basic.on('change',getMonitor);
	monitor.on('change',getInstrType);
	instrType.on('change',getObservePoints);
	point.on('change',getComponent);
	// 预测模型名称
	$.getJSON("getPreModel", function(data) {
		var options = new jStringBuffer();
		options.append("<option value='' selected>--请选择--</option>");
		for (var i = 0, len = data.length; i < len; i++) {
//			if(i==0)
//				options.append("<option selected value='").append(data[i].method).append("'>").append(data[i].modelname).append("</option>");
//			else
				options.append("<option value='").append(data[i].method).append("'>").append(data[i].modelname).append("</option>");
			premodelname["model" + data[i].modelid] = data[i].modelname;
		}
		$("#premodel_sel").html(options.toString());
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

// 这边需要修改，将isCalculated不为1 的过滤
// 显示测点
function showNoSelect() {
	if ($("#dec_Sel").val() === "")
		return;
	if ($("#dam_Sel").val() === "")
		return;
	var dam = $("#dam_Sel").val().trim();
	var title = ($("#dec_Sel").val().split(",")[1]);
	$.get("GetPointByDamAndTableName", {
		dam : dam,
		title : title
	}, function(result) {
		var data = JSON.parse(result);
		// console.log(data);
		var options = new jStringBuffer();
		// options.append("<option value='' selected>--请选择--</option>");
		for (var i = 0, len = data.length; i < len; i++) {
			options.append("<option value='" + data[i].code + "'>"
					+ data[i].code + "</option>");
		}
		$("#point_Sel").html(options.toString());
		$('#point_Sel').multiselect('rebuild');
		$('#point_Sel').multiselect({
			buttonWidth : '150px',
			onChange : function(option, checked) {
				var selectedOptions = $('#point_Sel option:selected');
				if (selectedOptions.length >= 20) {
					var nonSelectedOptions = $('#point_Sel option')
					.filter(function() {
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

// 显示测点分量
function showComponent() {
	var $point_no = $('#point_Sel option:selected');
	var instr = "";
	var instr_Sel = new Array();
	if ($point_no.length === 0) {
	} else {
		$point_no.each(function() {
			instr += "," + $(this).val();
		});
		var instrsels = instr.split(",");
		for (var k = 0; k < instrsels.length; k++) {
			if (instrsels[k] != '') {
				instr_Sel.push(instrsels[k]);
			}
		}
	}
	var thedec = $("#dec_Sel").val().split(",")[1];
	$.getJSON("GetMonitorItemType", {
		table_type : thedec
	}, function(result) {
		$("#comp_Sel").html("");
		// $("#comp_Sel").append("<option value=''
		// selected>---请选择---</option>");
		var obj = eval(result);
		if (instr_Sel.length != 0) {
			for (var j = 0; j < instr_Sel.length; j++) {
				if (obj.r1 != 0) {
					$("#comp_Sel").append(
							"<option value='" + instr_Sel[j] + ",r1'>"
									+ instr_Sel[j] + ":" + result.r1
									+ "</option>");
				}
				if (obj.r2 != 0) {
					$("#comp_Sel").append(
							"<option value='" + instr_Sel[j] + ",r2'>"
									+ instr_Sel[j] + ":" + result.r2
									+ "</option>");
				}
				if (obj.r3 != 0) {
					$("#comp_Sel").append(
							"<option value='" + instr_Sel[j] + ",r3'>"
									+ instr_Sel[j] + ":" + result.r3
									+ "</option>");
				}
			}
		} else {
			if (obj.r1 != 0) {
				$("#comp_Sel").append(
						"<option value='r1'>" + obj.r1 + "</option>");
			}
			if (obj.r2 != 0) {
				$("#comp_Sel").append(
						"<option value='r2'>" + obj.r2 + "</option>");
			}
			if (obj.r3 != 0) {
				$("#comp_Sel").append(
						"<option value='r3'>" + obj.r3 + "</option>");
			}
		}

		$('#comp_Sel').multiselect('rebuild');
		$('#comp_Sel').multiselect(
				{
					buttonWidth : '150px',
					onChange : function(option, checked) {
						var selectedOptions = $('#comp_Sel option:selected');
						if (selectedOptions.length >= 20) {
							var nonSelectedOptions = $('#comp_Sel option')
									.filter(function() {
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

// 开始预测
function predict() {
	if($("#premodel_sel").val() === ''){
		alert("请选择预测模型！");
		return;
	}
	if($("#point_sel").val() === null){
		alert("请选择测点！");
		return;
	}
	
	/*if ($("#dec_Sel").val() === "") {
		alert("请选择监测仪器！");
		return;
	}
	// 坝段
	if ($("#dam_Sel").val() === "") {
		alert("请选择坝段！");
		return;
	}
	// 测点编号
	if ($("#point_Sel").val() === null) {
		alert("请选择测点编号！");
		return;
	}
	// 预测分量
	if ($("#comp_Sel").val() === null) {
		alert("请选择预测分量！");
		return;
	}
	if ($("#premodel_Sel").val() === "") {
		alert("请选择预测模型！");
		return;
	}
*/
	/*var decType_Sel = $("#dec_Sel").val().split(",")[0];
	var title = $("#dec_Sel").val().split(",")[1];
	var dam_Sel = $("#dam_Sel").val();
	var instr_Sel = $("#point_Sel").val();
	
	var point = instr_Sel[0];
	for (var i = 1; i < instr_Sel.length; i++) {
		point += "," + instr_Sel[i];
	}
	
	var comp_Sel = $("#comp_Sel").val();
	var points = point.split(',');
	var comps = point.split(',');
	for(var i = 0;i<points.length;i++){
		for(var j=0;j<comp_Sel.length;j++){
			if(comp_Sel[j].includes(points[i])){
				comps[i] += "," + comp_Sel[j].split(",")[1];
			}
		}
	}

	var comp = '';
	$.each(comps,function(index,value){
		comp += value + ";"
	});
	comp = comp.substr(0,comp.length-1);
	var premodel = $("#premodel_Sel").val() + "";
	var sTime = $("#start_time").val();
	var eTime = $("#end_time").val();
	console.log(decType_Sel);
	console.log(title);
	console.log(dam_Sel);
	console.log("测点：" + point);
	console.log("分量：" + comp);
	console.log(premodel);
	console.log(sTime);
	console.log(eTime);*/
	var premodel = $("#premodel_sel").val() + "";
	var point = [];
	var comp = [];
	var table = [];
	var dectype = 'dectype';
	var dam = encodeURI($("#basic_sel option:selected").text()+'');
	
	var sTime = $("#start_time").val();
	var eTime = $("#end_time").val();
	
	$.each($("#point_sel").val(),function(index,element){
		var tmp = element.split("#");
		table.push(tmp[0]);
		point.push(tmp[1]);
		comp.push(tmp[1]);
	});
	
	console.log($("#comp_sel").val());
	var comps_sel = $("#comp_sel").val();
	for(var i=0;i<comp.length;i++){
		for(var j=0;j<comps_sel.length;j++){
			if(comps_sel[j].includes(point[i])){
				comp[i] += "," + comps_sel[j].split("#")[1].toLowerCase();
			}
		}
	}
	
//	$.each($("#comp_sel").val(),function(index,element){
//		var tmp1 = element.split("#");
//		console.log(index + '-->' + element);
//		console.log('pindex = ' + pindex);
//		var pindex = comp.indexOf(tmp1[0]);
//		if(pindex!=-1){
//			comp[pindex] = comp[pindex] + ',' + tmp1[1].toLowerCase();
//		}
//	});

	console.log(premodel);
	console.log(sTime);
	console.log(eTime);
	console.log(table.join(','));
	console.log(dectype);
	console.log(dam);
	console.log(point.join(','));
	console.log(comp.join(';'));
	var info = "";
	info += "测点：" + point.join(',');
	info += "\n分量：" + $("#comp_sel option:selected").text();
	info += "\n预测模型：" + $("#premodel_sel option:selected").text();
	info += "\n开始时间：" + $("#start_time").val();
	info += "\n至：" + $("#end_time").val();
	/*info += "监测仪器：" + $("#dec_Sel option:selected").text();
	info += "\n坝段：" + $("#dam_Sel option:selected").text();
	info += "\n测点编号：" + $("#point_Sel option:selected").text();
	info += "\n预测分量：" + $("#comp_Sel option:selected").text();
	info += "\n预测模型：" + $("#premodel_Sel option:selected").text();
	info += "\n开始时间：" + $("#start_time").val();
	info += "\n至：" + $("#end_time").val();*/
	console.log(info);
	if (!confirm(info)) {
		return;
	}
	showModal('total_pre_info');
	$.getJSON('singlePre', {
		/*premodel : 'method2',
		sTime : '2013-04-01',
		eTime : '2013-04-05',
		title : 'T_ZB_PL',
		dectype : 'dectype',
		dam_Sel : 'A22',
		instr_Sel : 'C4-A22-PL-01',
		component_Sel : 'C4-A22-PL-01,r1,r2'*/
		premodel : premodel,
		sTime : sTime,
		eTime : eTime,
		title : table.join(','),
		decType_Sel : dectype,
		dam_Sel : dam,
		instr_Sel : point.join(','),
		component_Sel : comp.join(';')
	}, function(result) {
		$('#myModal').modal('hide');
		showSinTable();
	});

}

//显示模态框
function showModal(id){
	var modal = '<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">';
	modal += '<div class="modal-dialog">';
    modal += '<div class="modal-content">';
    modal += '<div class="modal-header">';   
    modal += '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>';
    modal += '<h4 class="modal-title" id="myModalLabel">单点预测</h4>';
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


// 删除预测批次
function delPrediction(obj, preId) {
	if (!confirm("确认删除？"))
		return;
	$.getJSON("delPrediction", {
		preId : preId
	}, function(result) {
		if (result.delresult) {
			//$(obj).parent().parent().remove();
			sindex = 1;
			showSinTable();
		} else {
			alert("删除失败");
		}
	});
}

// 查看预测批次
function detailed(preId) {
	$("#_view_preId").val(preId);
	$("#total_pre_info").hide();
    var $detail_Sel = $("#detail_infoSel");
    $detail_Sel.show(function () {
    	//根据表里的数据选择
        var pointinfo = idpointInfo[preId].split(";");
        var points = pointinfo[0].split(",");
        var comps = pointinfo[1].split(",");
        var times = pointinfo[2];
        var tab = pointinfo[3];
        var opt1 = "";
        var opt2 = "";
        for (var i = 0; i < points.length; i++) {
            opt1 += "<option value='" + points[i] + "'>" + points[i] + "</option>";
        }
        $("#detail_point").html(opt1);
        //if(comps[0]!==''){
        	for (var i = 0; i < comps.length; i++) {
        		opt2 += "<option value='" + ('r' + (i+1)) + "'>" + comps[i] + "</option>";
        	}
//        }else{
//        	$.getJSON('getComponentByPoint',{},function(result){
//        		
//        	});
//        	opt2 += "<option value='r1'>分量1</option>";
//        	opt2 += "<option value='r2'>分量2</option>";
//        }
        $("#detail_comp").html(opt2);
        $("#_the_time").val(times);
        $("#_the_tab").val(tab);
    });
    //选择测点和编号之后画图
    $("#detail_point").on('change',function(){
    	$('#container').html("");
    	draw(preId);
    });
    $("#detail_comp").on('change',function(){
    	$('#container').html("");
    	draw(preId);
    });
    var $preinfo = $('#pre_info');
    $preinfo.show(function () {
        $("#someTable_Sel").hide();
        $("#pre_table").hide();
        $("#tab_head").show(function () {
            $("#tab_head").append("<div id='container' style='width:99%'></div>");
            draw(preId);

            $("#tabs a").eq(0).click(function (e) {
                e.preventDefault();
                $detail_Sel.show();
                $("#tabs li").attr("class", "");
                $("#tabs li").eq(0).attr("class", "active");
                $("#tab_head div").remove();
                $("#tab_head").append("<div id='container' style='width:99%'></div>");
                draw(preId);
                
            });
            $("#tabs a").eq(1).click(function (e) {
                e.preventDefault();
                $detail_Sel.hide();
                $("#tab_head div").remove();
                $("#tabs li").attr("class", "");
                $("#tabs li").eq(1).attr("class", "active");
                $("#tab_head").append("<div id='showstatistic' style='width:99%'></div>");
                showStatistics(preId);
            });
            $("#tabs a").eq(2).click(function (e) {
                e.preventDefault();
                $detail_Sel.hide();
                $("#tab_head div").remove();
                $("#tabs li").attr("class", "");
                $("#tabs li").eq(2).attr("class", "active");
                $("#tab_head").append("<div id='showdetailedinfo' style='width:99%'></div>");
                showDetailedInfo();
            });
        });
    });
}


//预测表分页
var sinIndex = 1;
var sinSize = 5;

function sin_firstpage(){
	if(sinIndex<=1)return;
	sinIndex = 1;
	showSinTable();
}

function sin_lastpage(max){
	if(sinIndex == max)return;
	sinIndex = max;
	showSinTable();
}

function sin_prepage(){
	if(sinIndex<=1)return;
	sinIndex = sinIndex -1;
	showSinTable();
}

function sin_nextpage(max){
	if(sinIndex>=max)return;
	sinIndex = sinIndex + 1;
	showSinTable();
}

function sin_gotopage(max){
	var $sindex = Number($("#sin_topage").val());
	if($sindex > max){
		$("#sin_topage").val(max);
		return;
	}
	sinIndex = $sindex;
	showSinTable();
}

// 显示预测表
function showSinTable() {
	var sinMax = 0;
	var sinTotal = 0;
    $.getJSON("getSinglePre", {pageindex:sinIndex,pagesize:sinSize},function (result) {
    	//console.log(JSON.stringify(result));
    	sinTotal = result.pop();
    	sinMax = parseInt((sinTotal-1)/sinSize) + 1;
        var datatable = new jStringBuffer();
        datatable.append("<table class='table table-striped table-hover' style='font-size:12px;'>");
        datatable.append("<thead><tr><th>#</th><th>预测时间</th><th>监测仪器</th><th>坝段</th><th>测点编号</th><th>预测分量</th><th colspan='2'>操作</th></tr></thead>");
        for (var i = 0, len = result.length; i < len; i++) {
            idpointInfo[result[i].id] = result[i].obser_no + ";" + result[i].pre_component + ";" + result[i].startTime + "," + result[i].endTime + ";" + $.trim(result[i].det_instr);
            periodInfo[result[i].id] = $.trim(result[i].det_instr) + "#" + result[i].obser_no + "#" + result[i].startTime + "," + result[i].endTime;
            statisticInfo[result[i].id] = result[i].staticsData;
            var detailBtn = "<button type='button' class='btn btn-outline btn-info btn-xs' onclick=\"detailed('" + result[i].id + "')\">查看</button>";
            var delBtn = "<button type='button'  class='btn btn-outline btn-danger btn-xs' onclick=\"delPrediction(this,'" + result[i].id + "')\">删除</button>";
            datatable.append("<tr><td>")
            .append((sinIndex - 1)*sinSize + i + 1).append("</td><td>")
            .append(result[i].dt).append("</td><td>")
            .append(result[i].det_instr).append("</td><td>")
            .append(decodeURI(result[i].dam_no)).append("</td><td>")
            .append(result[i].obser_no).append("</td><td>")
            .append(result[i].pre_component).append("</td><td>")
            .append(detailBtn + "&nbsp;" + delBtn).append("</td><td>").append("</td></tr>");
        }
        datatable.append("</table>");
        //分页按钮
		var $page = "";
		$page += "<span style='display:block;margin-top:10px;float:right'>";
		$page += "<strong>第"+ sinIndex + "/" + sinMax + "页，共"+ sinTotal + "条记录</strong>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='sin_firstpage()' id='_first'>首页</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='sin_prepage()' id='_pre'>上一页</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='sin_nextpage(" + sinMax + ")' id='_next'>下一页</botton>";
		$page += "&nbsp;<input type='text' id='sin_topage'style='height:23px;width:35px;valign:bottom;line-height=23px;text-indent:3px;font-size:10px;'/>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='sin_gotopage(" + sinMax + ")'>GO</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='sin_lastpage(" + sinMax + ")' id='last'>末页</botton></span>";
		datatable.append($page);
        $('#pre_table').html("");
        $('#pre_table').html(datatable.toString());
    });
}

// 画图
function draw(preId) {
	// 画图需要的参数
	var tableName = $("#_the_tab").val();	
	// 测点编号
	 var instr_no = $("#detail_point").val() + "";
	// 分量
	 var component = $("#detail_comp").val() + "";
	// 开始以及结束时间
	 var startTime = $("#_the_time").val().split(",").sort()[0];
	 var endTime = $("#_the_time").val().split(",").sort()[1];

	$.getJSON('getChartData', {
		preId : preId,
		tableName : tableName,
		instr_no : instr_no,
		stime : startTime,
		etime : endTime,
		comp : component
	}, function(result) {
		console.log(result); 
		if(result==0){
			$('#container').html("暂无数据，请重新选择...");
			return;
		}
		
		var realVal = [];
		//图标题以及副标题
		var charttitle = "测点：" + instr_no + " 分量：" + $("#detail_comp").find("option:selected").text();
		var chartsubtitle = startTime + "~" + endTime;
		
		//开始时间处理
		var $stime = Date.parse(dealDate(result[0].dt));
		
		//定义画图的series数组
		var theSeries = [];
		
		//x轴时间
		var xtime = [];
		
		//实测值
		var realVal = [];
		var preVal = [];
		var wl = [];
		
		for(var i=0;i<result.length;i++){
			realVal.push(result[i].realVal);
			preVal.push(result[i].preVal);
			wl.push(result[i].wl);
		}
		
		theSeries.push({
			name: '水位',
            type: 'areaspline',
            yAxis: 1,
            data: wl,
            tooltip: {
                valueSuffix: ' m'
            }
        });
		theSeries.push({
            name: '实测值',
            type: 'spline',
            data: realVal
        });
		theSeries.push({
            name: '预测值',
            type: 'spline',
            data: preVal
        });
		
//		console.log(charttitle + "..." + chartsubtitle + "..." + Date.parse(startTime));
//		console.log(JSON.stringify(result));
//		console.log(JSON.stringify(theSeries));
        
		 $('#container').highcharts({
            chart: {
                zoomType: 'x'
            },
            title: {
                text: charttitle
            },
            subtitle: {
                text: chartsubtitle
            },
            credits: {
                text: '',
                url: '#'
            },
            plotOptions: {
                series: {
                    pointStart: $stime,
                    pointInterval: 24 * 3600 * 1000,
                    /*marker: {
                        radius: 3
                    }*/
                },
                areaspline: {
                	marker:{
                		radius: 0
                	}
                },
                spline: {
                	marker:{
                		radius: 3
                	}
                }
            },
            xAxis: {
                type: 'datetime',
                /*labels: {
                	formatter: function() {
                		var day = new Date(this.value);
                		return day.getFullYear() + '-' + (day.getMonth()+1) + '-' + day.getDate();
                	}
                }*/
                dateTimeLabelFormats: {
                    day: "%Y-%m-%e"
                },
                tickInterval: 24 * 3600 * 1000
            },
            yAxis: [{ // Primary yAxis
                labels: {
                    format: '{value}',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                },
                title: {
                    text: '测值',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                }
            }, { // Secondary yAxis
                title: {
                    text: '水位',
                    style: {
                        color: Highcharts.getOptions().colors[0]
                    }
                },
                labels: {
                    format: '{value} m',
                    style: {
                        color: Highcharts.getOptions().colors[0]
                    }
                },
                min: 1150,
                max: 1250,
                opposite: true
            }],
            tooltip: {
                shared: true,
                crosshairs: true,
                dateTimeLabelFormats: {
                    day: '%Y-%m-%e'
                }
            },
            legend: {
                align: 'center',
                verticalAlign: 'bottom',
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
            },
            exporting: {
                url: exportUrl,
                buttons: {
                    contextButton: {
                        text: '导出',
                        menuItems: [{
                            text: '导出PNG',
                            onclick: function () {
                                this.exportChart({
                                    type: 'image/png',
                                    filename: 'point-png'
                                });
                            }
                        },{
                            text: '导出JPEG',
                            onclick: function () {
                                this.exportChart({
                                    type: 'image/jpeg',
                                    filename: 'point-jpeg'
                                });
                            }
                        },{
                            text: '导出PDF',
                            onclick: function () {
                                this.exportChart({
                                    type: 'application/pdf',
                                    filename: 'point-pdf'
                                });
                            }
                        }]
                    }
                }
            },
            series: theSeries
        });
		return;
	});
}

//显示统计信息
function showStatistics(preId){
	//	console.log("showStatistics : preId = " + preId);
	//	preId = c03a1b72-b1505181654;
	var modeltab = {"m0":"插值","m1":"拟合","m2":"二次多项式"};
	var the_info = statisticInfo[preId];
//	console.log(JSON.stringify(the_info));
	var dataTable = "<table class='table table-striped table-hover' style='margin-top:20px;' id=''>";
	dataTable += "<thead><tr><th rowspan='2' colspan='1' style='vertical-align:middle;'>#</th><th rowspan='2' colspan='1' style='vertical-align:middle;'>监测仪器</th>";
	dataTable += "<th rowspan='2' colspan='1' style='vertical-align:middle;'>预测分量</th><th rowspan='2' colspan='1' style='vertical-align:middle;'>模型</th>";
	dataTable += "<th rowspan='1' colspan='2'>一级预警</th><th rowspan='1' colspan='2'>二级预警</th>";
	dataTable += "<th rowspan='1' colspan='2'>三级预警</th><th rowspan='1' colspan='1'>正常</th></tr>";
	dataTable += "<tr><th rowspan='1' colspan='1'>阈值</th><th rowspan='1' colspan='1'>数量</th>";
	dataTable += "<th rowspan='1' colspan='1'>阈值</th><th rowspan='1' colspan='1'>数量</th>";
	dataTable += "<th rowspan='1' colspan='1'>阈值</th><th rowspan='1' colspan='1'>数量</th>";
	dataTable += "<th rowspan='1' colspan='1'>数量</th></tr></thead><tbody>";
	
	var rowindex = 0;
	$.each(the_info,function(key1,val1){
		//key1对应测点
		var rowinfo = "";
		rowinfo += key1;
		$.each(val1,function(key2,val2){
			//key2对应分量
			var key2_comp = $("#detail_comp option[value='"+ key2 + "']").text();
			rowinfo += "," + key2_comp;
			$.each(val2,function(key3,val3){
				//key3对应模型
				var key3_model = modeltab[key3];
				dataTable += "<tr><td>";
				dataTable += (rowindex+1) + "</td><td>";
				dataTable += key1 + "</td><td>";
				dataTable += key2_comp + "</td><td>";
				dataTable += key3_model + "</td><td>";
				dataTable += ">=" + val3.oneData + "</td><td>";
				dataTable += val3.countOneData + "</td><td>";
				dataTable += val3.twoData + "<br>~<br>" + val3.oneData + "</td><td>";
				dataTable += val3.countTwoData + "</td><td>";
				dataTable += val3.threeData + "<br>~<br>" + val3.twoData + "</td><td>";
				dataTable += val3.countThreeData + "</td><td>";
				dataTable += val3.countNomalData + "</td><tr>";
				rowindex ++;
			});
		});
	});
	
	dataTable += "<tbody></table>";
	$("#tab_head #showstatistic").html("");
	$("#tab_head #showstatistic").html(dataTable);
}


//显示详细信息
function showDetailedInfo(){
	var $preId = $("#_view_preId").val();
	var $info = periodInfo[$preId].split("#");
	var $tableName = $.trim($info[0]);
	var $instr_no = $info[1];
	var $time = $info[2];
	var detailtotal = 0;
//	var $comp = $("#detail_comp").val() + "";
	var comps =  $("#detail_comp option");
	var $comp = "'" + $(comps[0]).val() + "'";
	for(var i=1;i<comps.length;i++){
		$comp += ",'" + $(comps[i]).val() + "'";
	}
	$.getJSON('getDetailInfoDataCount',{
		preId : $preId,
		instr_no : $instr_no,
		comp : $comp,
		time: $time,
		tableName : $tableName
	},function(result,status){
		if(status == 'success'){
			detailtotal = Number(result);
			detailpagemax = parseInt((detailtotal-1) / 10) + 1;
		}
//		console.log(detailtotal);
//		console.log(detailpagemax);
	});
	var dataTable = "<table class='table table-striped table-hover' style='margin-top:20px;'>";
	dataTable += "<thead><tr><th rowspan='2' colspan='1' style='vertical-align:middle;'>#</th><th rowspan='2' colspan='1' style='vertical-align:middle;'>采集时间</th>";
	dataTable += "<th rowspan='2' colspan='1' style='vertical-align:middle;'>水位</th><th rowspan='2' colspan='1' style='vertical-align:middle;'>测点编号</th>";
	dataTable += "<th rowspan='2' colspan='1' style='vertical-align:middle;'>预测分量</th><th rowspan='2' colspan='1' style='vertical-align:middle;'>实测值</th>";
	$.getJSON('getDetailInfoData',{
		preId : $preId,
		instr_no : $instr_no,
		comp : $comp,
		tableName : $tableName,
		time: $time,
		pageindex : detailindex},
		function(result){
//		console.log(JSON.stringify(result));
		var modelcount = result[result.length-1].modelcount;
		var th1 = "";var th2 = "";
		for(var i=1;i<modelcount;i++){
			th1 += "<th rowspan='1' colspan='3'  align='center'>其他模型" + i + "</th>";
			th2 += "<th rowspan='1' colspan='1'>名称</th><th rowspan='1' colspan='1'>预测值</th><th rowspan='1' colspan='1'>相对误差</th>";
		}
		dataTable += "<th rowspan='1' colspan='3' align='center'>最优模型</th>" + th1;
		dataTable += "<tr><th rowspan='1' colspan='1'>名称</th><th rowspan='1' colspan='1'>预测值</th><th rowspan='1' colspan='1'>相对误差</th>";
		dataTable += th2 + "</tr></thead><tbody>";
		var count = 1;
		for(var j=0;j<result.length-modelcount;j+=modelcount){
			var line = "<tr>";
			line +=  "<td>" + ((detailindex-1) * 10 + count++) + "</td>";
			line +=  "<td>" + result[j].DT.substring(0,10) + "</td>";
			line +=  "<td>" + result[j].WL + "</td>";
			line +=  "<td>" + result[j].INSTR_NO + "</td>";
			
			var comps = result[j].STATICS.split("#");
			if(result[j].component =='r1'){
				line += "<td>" + comps[0] + "</td>";
			}else if(result[j].component =='r2'){
				line += "<td>" + comps[1] + "</td>";
			}else{
				line += "<td>" + comps.pop() + "</td>";
			}
			line +=  "<td>" + result[j].realVal + "</td>";
			var best = "";
			var other = "";
			for(var k=0;k<modelcount;k++){
				if(result[j+k].isBest==1){
					best += "<td>" + premodelname["model" + result[j+k].modelId] + "</td>";
					best += "<td>" + numformat(result[j+k].preVal,4) + "</td>";
					best += "<td>" + numpercent(result[j+k].err_rate) + "</td>";
				}else{
					other += "<td>" + premodelname["model" + result[j+k].modelId] + "</td>";
					other += "<td>" + numformat(result[j+k].preVal,4) + "</td>";
					other += "<td>" + numpercent(result[j+k].err_rate) + "</td>";
				}
			}
			line += best + other + "</tr>";
			dataTable += line ;
		}
		
		dataTable += "</tbody></table>";
		var $page = "";
		$page += "<span style='display:block;margin-top:10px;float:right'>";
		$page += "<strong>第"+ detailindex + "/" + detailpagemax + "页，共"+ detailtotal + "条记录</strong>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='firstpage()'>首页</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='prepage()'>上一页</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='nextpage()'>下一页</botton>";
		$page += "&nbsp;<input type='text' id='todetailpage'style='height:23px;width:35px;valign:bottom;line-height=23px;text-indent:3px;font-size:10px;'/>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='gotopage()'>GO</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='lastpage()'>末页</botton></span>";
		
		dataTable += $page;
		$("#tab_head #showdetailedinfo").html("");
//		console.log(dataTable);
		$("#tab_head #showdetailedinfo").html(dataTable);
	});
	
}

//详细信息分页
function firstpage(){
	detailindex = 1;
	showDetailedInfo();
}

function lastpage(){
	detailindex = detailpagemax;
	showDetailedInfo();
}

function gotopage(){
	$index = Number($("#todetailpage").val());
	if($index > detailpagemax){
		$("#todetailpage").val(detailpagemax)
		return;
	}
	detailindex = $index;
	showDetailedInfo();
}

function prepage(){
	if(detailindex==1){
		return;
	}
	detailindex --;
	showDetailedInfo();
}
function nextpage(){
	if(detailindex == detailpagemax){
		return;
	}
	detailindex ++;
	showDetailedInfo();
}

//截断日期字符串
function dealDate(date){
	return date.substring(0,10);
}

//预测值规范化，保留小数点后6位数字
function numformat(num,n){
	return Number(num).toFixed(n);
}

//误差百分化，保留小数点后4位数字
function numpercent(err_rate){
	return (Number(err_rate)*100).toFixed(4) + "%";
}

//日期加一天
function addOneDay(date){
	date = date.replace(/-/g,'/'); 
	var day = new Date(date);
	day.setDate(day.getDate()+1);
	return day.getFullYear() + '-' + (day.getMonth()+1) + "-" + day.getDate();
}

//日期格式化
function dtformat(date){
	var day = new Date(date);
	return day.getFullYear() + '-' + (day.getMonth()+1) + "-" + day.getDate();
}

//控制台打印 @debug
function out(obj){
	console.log(JSON.stringify(obj));
}