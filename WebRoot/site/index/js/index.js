/**
 * @author qihai
 * @time 2016年5月11日13:27:34
 * 首页显示
 */
var pageindex = 1;
var pagemax = 0;
var total = 0;
var period = -6;
var exportUrl = 'http://10.65.20.15:8000/highcharts-export';
var thresholdDay = "";
$(function() {
	$.ajax({
		type: "GET",
		url: "getThresholdDay",
		async: false,
		success: function(result){
			console.log('thresholdDay = ' + result);
			thresholdDay = result;
			$("#_strTime").val(thresholdDay);
		},
		failure: function(){
			alert("加载阈值计算日期出错，请刷新页面重试。")
		}
	});
//	$.getJSON('getMaxDate',function(result){
//		var _stime = dealDate(result[0]);
//		out(_stime);
//		$("#_strTime").val(_stime);
//		
//	});
	/*initBarTime('timepage',function(){
		pageindex = 1;
		showError();
	});
	showError();*/
	initBarTime('timepage',function(){
		pageindex = 1;
		showError2();
	});
	showError2();
	
	$("#_time").on('change',function(){
		alert("haha");
		pageindex = 1;
		showError2();
	});
	
	/*$("#pweek").click(function(){
		$(this).attr("class","active");
		$(this).siblings().attr("class","");
		showKeyChart('pweek');
	});
	$("#pmonth").click(function(){
		$(this).attr("class","active");
		$(this).siblings().attr("class","");
		showKeyChart('pmonth');
	});
	
	showKeyChart('pweek');*/
	showKeyChart2();
	
});

var tab_content = [];
var page_ctrl = [];
function addlevel(level,val){
	var err_rate = parseFloat(val);
	if(err_rate<=0.025)level[0]++;
	else if(err_rate<=0.05)level[1]++;
	else if(err_rate<=0.1)level[2]++;
	else if(err_rate<=0.2)level[3]++;
	else if(err_rate<=0.3)level[4]++;
	else if(err_rate<=0.4)level[5]++;
	else if(err_rate<=0.5)level[6]++;
	else if(err_rate<=0.6)level[7]++;
	else if(err_rate<=0.7)level[8]++;
	else if(err_rate<=0.8)level[9]++;
	else if(err_rate<=0.9)level[10]++;
	else if(err_rate<=1.0)level[11]++;
	else level[12]++;
}

function showError2(){
	var day = $("#_time").val();
	if(day>thresholdDay){
		$("#_time").val(thresholdDay);
		alert(day + "日尚未计算阈值。。")
		window.location.reload();
		return;
	}
	var level = [];
	var cate = ['2.5%','5%','10%','20%','30%','40%','50%','60%','70%','80%','90%','100%','>100%'];
	for(var i=0;i<cate.length;i++)level.push(0);
	if(pageindex !=1 && tab_content.length!=0){
		$("#errorTable tbody").html(tab_content[pageindex-1]);
		$("#pagectrl").html(page_ctrl[pageindex-1].replace('page%index',pageindex));
		if(pageindex == pagemax){
			$("#_next").unbind('click');
			$("#_last").unbind('click');
		}
		return;
	}
	$.getJSON('getErrorPoints2',{day:day},function(results){
		tab_content = [];
		page_ctrl = [];
		if(results.length==0)alert(stime + "数据异常，请刷新页面重试...");
		$("#calyearp").html($("#_time").val() +  '&nbsp;水位:' + numformat(results[0][0][0].WL,2) + 'm');
//		wl_info += '水位：' + numformat(results[0][0][0].WL, 2) + 'm';
		pagemax = results[0].length;
		total = results.pop();
		if(pageindex !=1)return;
		$.each(results[0],function(index,result){
			var data = '';
			for(var i=0;i<result.length;i++){
				var abs_error = 0;
				addlevel(level,result[i].err_rate);
				
				data += "<tr>";
				data += "<td>" + ((pageindex-1) * 10 + i+1) + "</td>";
				data += "<td>" + result[i].INSTR_NO + "</td>";
				
				var unit = '';
				if(result[i].STATICS != ''){
					var comps = result[i].STATICS.split('unit')[0].split("#");
					var units = result[i].STATICS.split('unit')[1].split("#");
					if(result[i].component =='r1'){
						data += "<td>" + comps[0] + "</td>";
						unit = units[0];
					}else if(result[i].component =='r2'){
						data += "<td>" + comps[1] + "</td>";
						unit = units[1];
					}else{
						data += "<td>" + comps.pop() + "</td>";
						unit = units.pop();
					}
				}else{
					if(result[i].INSTR_NO == 'C4-A18Y-P-02'){
						data += "<td>压力</td>";
						unit = 'KN';
					}else{
						data += "<td>" + result[i].component + "</td>";
					}
				}
				
				if(result[i].realVal=="" || result[i].preVal==""){
					data += "<td style='text-align:right'> 0.0000" + unit + "</td>";
					data += "<td style='text-align:right'>0.0000" + unit + "</td>";
					data += "<td style='text-align:right'>0.0000" + unit + "</td>";
				}else{
					data += "<td style='text-align:right'>" + result[i].realVal  + unit + "</td>";
					data += "<td style='text-align:right'>" + numformat(result[i].preVal,4) + unit + "</td>";
					abs_error = Math.abs(parseFloat(result[i].preVal)-parseFloat(result[i].realVal));
					data += "<td style='text-align:right'>" + numformat(abs_error,4) + unit + "</td>";
				}
				data += "<td style='text-align:center;'>" + numpercent(result[i].err_rate) + "</td>";
				data += "</tr>";
			}
			
			tab_content.push(data);
			
			var $page = "";
			$page += "<span style='display:block;margin-top:10px;float:right'>";
			$page += "<strong>第page%index/" + pagemax + "页，共"+ total + "条记录</strong>&nbsp;";
			$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='firstpage()' id='_first'>首页</botton>&nbsp;";
			$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='prepage()' id='_pre'>上一页</botton>&nbsp;";
			$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='nextpage()' id='_next'>下一页</botton>";
			$page += "&nbsp;<input type='text' id='topage'style='height:23px;width:35px;valign:bottom;line-height=23px;text-indent:3px;font-size:10px;'/>&nbsp;";
			$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='gotopage()'>GO</botton>&nbsp;";
			$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='lastpage()' id='last'>末页</botton></span>";
			page_ctrl.push($page);
		});
		
		$("#errorTable tbody").html(tab_content[pageindex-1]);
		$("#pagectrl").html(page_ctrl[pageindex-1].replace('page%index',pageindex));
		if(pageindex == 1){
			$("#_first").unbind('click');
			$("#_pre").unbind('click');
		}
		//开始画图
		if(pageindex!=1)return;
		var piedata = [];
		var bardata = [];
		var allbarcate = ['0~2.5%','2.5%~5%','5%~10%','10%~20%','20%~30%','30%~40%','40%~50%','50%~60%','60%~70%','70%~80%','80%~90%','90%~100%','>100%'];
		var barcate = [];
		for(var i=0;i<level.length;i++){
			if(level[i] == 0)continue;
			barcate.push(allbarcate[i]);
			/*if(i==1){
				piedata.push({
					name: cate[i],
					y: level[i],
					sliced: true,
					selected: true
				});
			}else{*/
				piedata.push({
					name: cate[i],
					y: level[i]
				});
//			}
		}
		console.log(piedata);
		
		var bardata = $.grep(level,function(element){
			return element>0;
		});
		
		$('#_errorbar').highcharts({
			credits : {
				enabled: false
			},
			title : {
				useHTML : true,
				text : '<p style="font-size:12px;font-weight:bold;">误差统计柱状图</p>'
			},
			xAxis: {
				categories: barcate,
				labels:{
                    rotation: -45
                }
			},
			yAxis : {
                title: {
                    text: '',
                }
			},
			legend: {
	            enabled: false
	        },
			series: [{
				type: 'column',
				name: '误差',
				data: bardata,
				dataLabels: {
					enabled : true,
					style: {
                    	fontSize: '8px',
                    	fontWeight: 'bold',
                        color: 'black'
                    }
				}
			},],
			exporting:{
	        	url: exportUrl,
	        	buttons: {
	        		contextButton: {
	        			text: '',
	        			menuItems: [{
	        				text: '导出PNG',
	        				onclick: function () {
	        					this.exportChart({
	        						type: 'image/png',
	        			            filename: 'errbar-png'
	        					});
	        				}
	        			},{
	        				text: '导出JPEG',
	        				onclick: function () {
	        					this.exportChart({
	        						type: 'image/jpeg',
	        			            filename: 'errbar-jpeg'
	        					});
	        				}
	        			},{
	        				text: '导出PDF',
	        				onclick: function () {
	        					this.exportChart({
	        						type: 'application/pdf',
	        			            filename: 'errbar-pdf'
	        					});
	        				}
	        			}]
	        		}
	        	}
			}
		});
		$('#_errorpie').highcharts({
			credits : {
				enabled: false
			},
			title : {
				useHTML : true,
				text : '<p style="font-size:12px;font-weight:bold;">误差统计饼图</p>'
			},
			xAxis: {
				categories: cate,
				labels:{
                    rotation: -45
                }
			},
			yAxis : {
                title: {
                    text: '',
                }
			},
			plotOptions : {
				pie : {
					allowPointSelect : true
				}
			},
			legend: {
	            enabled: false
	        },
			series: [
			{
				type: 'pie',
				name: '误差统计',
				data: piedata,
				center: ['50%', '75%'],
				size: 100,
				showInLegend: false,
				dataLabels: {
					enabled: true,
                    format: '<b>{point.name}</b>:{point.percentage:.1f} %',
                    style: {
                    	fontSize: '8px',
                        color: 'black'
                    }
				}
			}],
			exporting:{
	        	url: exportUrl,
	        	buttons: {
	        		contextButton: {
	        			text: '',
	        			menuItems: [{
	        				text: '导出PNG',
	        				onclick: function () {
	        					this.exportChart({
	        						type: 'image/png',
	        			            filename: 'errpie-png'
	        					});
	        				}
	        			},{
	        				text: '导出JPEG',
	        				onclick: function () {
	        					this.exportChart({
	        						type: 'image/jpeg',
	        			            filename: 'errpie-jpeg'
	        					});
	        				}
	        			},{
	        				text: '导出PDF',
	        				onclick: function () {
	        					this.exportChart({
	        						type: 'application/pdf',
	        			            filename: 'errpie-pdf'
	        					});
	        				}
	        			}]
	        		}
	        	}
			}
		});
		
	});
}

//显示异常点
function showError(){
	var stime = $("#_time").val();
//	var stime = '2016-04-05';
	var etime = stime;
	var level;
	$.getJSON('getErrorPoints',{
		pageindex:pageindex,
		starttime:stime,
		endtime:etime},function(result){
//		console.log(JSON.stringify(result));
		total = result.pop();
		level = result.pop();
		console.log(level);
		pagemax = parseInt((Number(total)-1) / 10) + 1;
//		console.log(JSON.stringify(level));
		var data = "";
		var wl = numformat(result[0].WL,2);
		$("#calyearp").html($("#_time").val() +  '&nbsp;水位:' + wl + 'm');
		for(var i=0;i<result.length;i++){
			var abs_error = 0;
			
			data += "<tr>";
			data += "<td>" + ((pageindex-1) * 10 + i+1) + "</td>";
			data += "<td>" + result[i].INSTR_NO + "</td>";
			var comps = result[i].STATICS.split('unit')[0].split("#");
			var units = result[i].STATICS.split('unit')[1].split("#");
			var unit = '';
			if(result[i].component =='r1'){
				data += "<td>" + comps[0] + "</td>";
				unit = units[0];
			}else if(result[i].component =='r2'){
				data += "<td>" + comps[1] + "</td>";
				unit = units[1];
			}else{
				data += "<td>" + comps.pop() + "</td>";
				unit = units.pop();
			}
			if(result[i].realVal=="" || result[i].preVal==""){
				data += "<td style='text-align:right'>0.0000" + unit + "</td>";
				data += "<td style='text-align:right'>0.0000" + unit + "</td>";
				data += "<td style='text-align:right'>0.0000" + unit + "</td>";
			}else{
				data += "<td style='text-align:right'>" + result[i].realVal  + unit + "</td>";
				data += "<td style='text-align:right'>" + numformat(result[i].preVal,4) + unit + "</td>";
				abs_error = Math.abs(parseFloat(result[i].preVal)-parseFloat(result[i].realVal));
				data += "<td style='text-align:right'>" + numformat(abs_error,4) + unit + "</td>";
			}
			data += "<td style='text-align:center;'>" + numpercent(result[i].err_rate) + "</td>";
			data += "</tr>";
		}
		//分页按钮
		var $page = "";
		$page += "<span style='display:block;margin-top:10px;float:right'>";
		$page += "<strong>第"+ pageindex + "/" + pagemax + "页，共"+ total + "条记录</strong>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='firstpage()' id='_first'>首页</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='prepage()' id='_pre'>上一页</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='nextpage()' id='_next'>下一页</botton>";
		$page += "&nbsp;<input type='text' id='topage'style='height:23px;width:35px;valign:bottom;line-height=23px;text-indent:3px;font-size:10px;'/>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='gotopage()'>GO</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='lastpage()' id='last'>末页</botton></span>";
		$("#errorTable tbody").html("");
		$("#errorTable tbody").html(data);
		$("#pagectrl").html($page);
		if(pageindex == 1){
			$("#_first").unbind('click');
			$("#_pre").unbind('click');
		}
		if(pageindex == pagemax){
			$("#_next").unbind('click');
			$("#_last").unbind('click');
		}
		
		if(pageindex !=1)return;
		//误差统计图
		level.shift();
		var cate = [/*'0~10%',*/'10%~20%','20%~30%','30%~40%','40%~50%','50%~60%','60%~70%','70%~80%','80%~90%','90%~100%','>100%'];
		var piedata = [];
		for(var i=0;i<level.length;i++){
			if(level[i] == 0)continue;
			if(i==1){
				piedata.push({
					name: cate[i],
					y: level[i],
					sliced: true,
					selected: true
				});
			}else{
				piedata.push({
					name: cate[i],
					y: level[i]
				});
			}
//			piedata.push([cate[i],level[i]]);
		}
		var bardata = $.grep(level,function(element){
			return element>0;
		});
//		console.log(cate);
		$('#_errorbar').highcharts({
			credits : {
				enabled: false
			},
			title : {
				useHTML : true,
				text : '<p style="font-size:12px;font-weight:bold;">误差统计柱状图</p>'
			},
			xAxis: {
				categories: cate,
				labels:{
                    rotation: -45
                }
			},
			yAxis : {
                title: {
                    text: '',
                }
			},
			legend: {
	            enabled: false
	        },
			series: [{
				type: 'column',
				name: '误差',
				data: bardata,
				dataLabels: {
					enabled : true,
					style: {
                    	fontSize: '8px',
                    	fontWeight: 'bold',
                        color: 'black'
                    }
				}
			},
//			{
//				type: 'spline',
//				name: '误差',
//				data: level,
//				marker: {
//					lineWidth: 2,
//					lineColor: Highcharts.getOptions().colors[3],
//					fillColor: 'white'
//				}
//			},
			],
			exporting:{
	        	url: exportUrl,
	        	buttons: {
	        		contextButton: {
	        			text: '',
	        			menuItems: [{
	        				text: '导出PNG',
	        				onclick: function () {
	        					this.exportChart({
	        						type: 'image/png',
	        			            filename: 'errbar-png'
	        					});
	        				}
	        			},{
	        				text: '导出JPEG',
	        				onclick: function () {
	        					this.exportChart({
	        						type: 'image/jpeg',
	        			            filename: 'errbar-jpeg'
	        					});
	        				}
	        			},{
	        				text: '导出PDF',
	        				onclick: function () {
	        					this.exportChart({
	        						type: 'application/pdf',
	        			            filename: 'errbar-pdf'
	        					});
	        				}
	        			}]
	        		}
	        	}
			}
		});
		$('#_errorpie').highcharts({
			credits : {
				enabled: false
			},
			title : {
				useHTML : true,
				text : '<p style="font-size:12px;font-weight:bold;">误差统计饼图</p>'
			},
			xAxis: {
				categories: cate,
				labels:{
                    rotation: -45
                }
			},
			yAxis : {
                title: {
                    text: '',
                }
			},
			plotOptions : {
				pie : {
					allowPointSelect : true
				}
			},
			legend: {
	            enabled: false
	        },
			series: [
			{
				type: 'pie',
				name: '误差统计',
				data: piedata,
				center: ['50%', '75%'],
				size: 100,
				showInLegend: false,
				dataLabels: {
					enabled: true,
                    format: '<b>{point.name}</b>:{point.percentage:.1f} %',
                    style: {
                    	fontSize: '8px',
                        color: 'black'
                    }
				}
			}],
			exporting:{
	        	url: exportUrl,
	        	buttons: {
	        		contextButton: {
	        			text: '',
	        			menuItems: [{
	        				text: '导出PNG',
	        				onclick: function () {
	        					this.exportChart({
	        						type: 'image/png',
	        			            filename: 'errpie-png'
	        					});
	        				}
	        			},{
	        				text: '导出JPEG',
	        				onclick: function () {
	        					this.exportChart({
	        						type: 'image/jpeg',
	        			            filename: 'errpie-jpeg'
	        					});
	        				}
	        			},{
	        				text: '导出PDF',
	        				onclick: function () {
	        					this.exportChart({
	        						type: 'application/pdf',
	        			            filename: 'errpie-pdf'
	        					});
	        				}
	        			}]
	        		}
	        	}
			}
		});
		
	});
}

//显示重点测点画图
function showKeyChart2(){
	var tday = thresholdDay;
	initTimePicker(tday);
	autoKeyPointsChart('C4-A22-PL-01', 'r2','cpl1r2',addDay(tday, -31),tday);
	autoKeyPointsChart('C4-A22-PL-02', 'r2','cpl2r2',addDay(tday, -31),tday);
	
	$("#show_key_chart").click(function(){
		var start = $("#start_time").val();
		var end = $("#end_time").val();
		autoKeyPointsChart('C4-A22-PL-01', 'r2', 'cpl1r2', start, end);
		autoKeyPointsChart('C4-A22-PL-02', 'r2','cpl2r2', start, end);
		console.log(start);
		console.log(end);
	});
	
}

function initTimePicker(tday){
	var $stime = $("#start_time"), $etime = $("#end_time");
	$stime.datetimepicker({
		lang : 'zh',
		format : 'Y-m-d',
		value : addDay(tday, -31),
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
		timepicker : false,
		closeOnDateSelect : true
	});
	$etime.datetimepicker({
		lang : 'zh',
		format : 'Y-m-d',
		value : tday,
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
		timepicker : false,
		closeOnDateSelect : true
	});
}

function showKeyChart(tabid){
	if(tabid=='pweek'){
		period = -6;
		$(".well").attr('class','col-lg-6 well');
		autoKeyPointsChart('C4-A22-PL-01', 'r1','c1r1');
		autoKeyPointsChart('C4-A22-PL-01', 'r2','c1r2');
		autoKeyPointsChart('C4-A22-PL-02', 'r1','c2r1');
		autoKeyPointsChart('C4-A22-PL-02', 'r2','c2r2');
//		c4pl('c1r1',1);
//		c4pl('c1r2',2);
//		c4pl('c2r1',3);
//		c4pl('c2r2',4);
	}else if(tabid=='pmonth'){
		period = -29;
		$(".well").attr('class','col-lg-12 well');
		autoKeyPointsChart('C4-A22-PL-01', 'r1','c1r1');
		autoKeyPointsChart('C4-A22-PL-01', 'r2','c1r2');
		autoKeyPointsChart('C4-A22-PL-02', 'r1','c2r1');
		autoKeyPointsChart('C4-A22-PL-02', 'r2','c2r2');
//		c4pl('c1r1',1);
//		c4pl('c1r2',2);
//		c4pl('c2r1',3);
//		c4pl('c2r2',4);
	}else{
		return;
	}
}

//截断日期字符串

function dealDate(date){
	return date.substring(0,10);
}

//保留测值后小数点后n位小数
function numformat(num,n){
	return (Number(num).toFixed(n));
}

//误差百分化，保留小数点后4位数字
function numpercent(err_rate){
	return (Number(err_rate)*100).toFixed(4) + "%";
}

//第一页
function firstpage(){
	if(pageindex == 1)return;
	pageindex = 1;
//	showError();
	showError2();
}

//末页
function lastpage(){
	if(pageindex == pagemax)return;
	pageindex = pagemax;
//	showError();
	showError2();
}

//上一页
function prepage(){
	if(pageindex==1){
//		alert("已经是第一页！");
		return;
	}
	pageindex --;
//	showError();
	showError2();
}

//下一页
function nextpage(){
	pageindex ++;
	if(pageindex > pagemax){
//		alert('这是最后一页！');
//		pageindex = pagemax;
		return;
	}
//	showError();
	showError2();
}

function gotopage(){
//	if($("#topage").val() == '')return;
	pageindex = parseInt($("#topage").val());
	if(pageindex>pagemax){
		$("#topage").val(pagemax);
		return;
	}
//	showError();
	showError2();
}

//重要测点数据
function autoKeyPointsChart(instr_no,comp,id,start,end){
	var title = "测点：" + instr_no;
	if(comp=='r1')title += " 分量：横河向位移";
	else title += " 分量：顺河向位移";
	$.getJSON('getKeyPoints',{
		instr_no : instr_no,
		comp: comp,
		start: start,
		end: end
		/*period: period*/
	},function(result,status){
		if(status != 'success'){
			$("#container").html('请求数据失败(✖╭╮✖)，请刷新页面重试。。。');
			return;
		}
		var theSeries = [];		//存放画图参数
		var bestVal = [];		//最优值
		var realVal = [];		//实测值
		var err_rate = [];		//相对误差
		var steps = parseInt(result.length/10);
		
		$.each(result,function(index,element){
			var pre = Number(element.preVal);
			var real = Number(element.realVal);
			var err = 0;
			if(real!=0)err = Math.abs((pre-real)/real);
			bestVal.push([Date.parse(dealDate(element.DT)),pre]);
			realVal.push([Date.parse(dealDate(element.DT)),real]);
			err_rate.push([Date.parse(dealDate(element.DT)),err]);
		});
		
		theSeries.push({name: '预测值',data: bestVal,marker:{enabled:false}});
		theSeries.push({name: '实测值',data: realVal,marker:{enabled:false}});
		theSeries.push({
			name: '相对误差',
			type:'column',
			tooltip: {
				formatter: function () {
	                return numpercent(this.value);
	            }
			},
			yAxis: 1,
			data: err_rate.sort()});
		theSeries.reverse();
		$('#'+id).highcharts({
			credits : {
				enabled: false
			},
			legend: {
	            align: 'center',
	            verticalAlign: 'bottom',
	        },
			chart : {
				type : 'spline'
			},
			title : {
				useHTML : true,
				text : '<p style="font-size:12px;font-weight:bold;">' + title + '</p>'
			},
			xAxis : {
				type : 'datetime',
				dateTimeLabelFormats : {
					 day: '%Y-%m-%e'
                },
                tickInterval: 24*3600*1000,
                labels:{
                    rotation: -45,
                    step: steps
                }
			},
			yAxis : [{//测值轴
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
			},{//相对误差轴
				labels: {
                    formatter : function(){
    	            	return (Number(this.value)*100).toFixed(2) + "%";
    	            },
                    style: {
                        color: Highcharts.getOptions().colors[0]
                    }
                },
                title: {
                    text: '相对误差',
                    style: {
                        color: Highcharts.getOptions().colors[0]
                    }
                },
                /*plotLines: [{
                    color: '#FF4500',
                    width: 2,
                    value: 0.2,
                    dashStyle: 'ShortDash',
                    label: {
                    	text: '20%',
                    	align: 'right',
                    	y: 16,
                        style: {
                            color: 'red',
                            fontWeight: 'bold'
                        }
                    }
                }],*/
                max: 1,
				opposite: true
			}],
			tooltip : {
				crosshairs : true,
				followPointer : true,
	            formatter : function(){
	            	var day = new Date(this.x);
	            	var s = '<b>' + day.getFullYear() + '-' + (day.getMonth()+1) + '-' + day.getDate() + '</b>';
	                $.each(this.points, function () {
	                	if(this.series.name == '相对误差'){
	                		s += '<br/>' + this.series.name + ': ' + numpercent(this.y);
	                	}else{
	                		s += '<br/>' + this.series.name + ': ' + this.y;
	                	}
	                });

	                return s;
	            },
				shared : true
			},
			exporting: {
	        	url: exportUrl,
	        	buttons: {
	        		contextButton: {
	        			text: '',
	        			menuItems: [{
	        				text: '导出PNG',
	        				onclick: function () {
	        					this.exportChart({
	        						type: 'image/png',
	        			            filename: 'chart-png'
	        					});
	        				}
	        			},{
	        				text: '导出JPEG',
	        				onclick: function () {
	        					this.exportChart({
	        						type: 'image/jpeg',
	        			            filename: 'chart-jpeg'
	        					});
	        				}
	        			},{
	        				text: '导出PDF',
	        				onclick: function () {
	        					this.exportChart({
	        						type: 'application/pdf',
	        			            filename: 'chart-pdf'
	        					});
	        				}
	        			}]
	        		}
	        	}
	        },
			series : theSeries
		});
	});
}

//去重
function uniqueArray(realVal){
	var n = {},r=[]; //n为hash表，r为临时数组
	for(var i = 0; i < realVal.length; i++) //遍历当前数组
	{
		if (!n[realVal[i]]) //如果hash表中没有当前项
		{
			n[realVal[i]] = true; //存入hash表
			r.push(realVal[i]); //把当前数组的当前项push到临时数组里面
		}
	}
	return r;
}

//控制台打印 @debug
function out(obj){
	console.log(JSON.stringify(obj));
}

//误差百分化，保留小数点后4位数字
function numpercent(err_rate){
	return (Number(err_rate)*100).toFixed(4) + "%";
}

//字符串倒序
function sreverse(str){
	if(str.length==0)return '';
	var str2 = '';
	for(var i=0;i<str.length;i++){
		str2 += str.charAt(str.length-i-1);
	}
	return str2;
}

//@test 
//C4-A22-PL-01横河向位移
//C4-A22-PL-01顺河向位移
function c4pl(id,type){
	var c4plr2_realVal = 
		[104.596,104.4753,104.125,106.0706,105.8737,105.4765,105.0919,104.9551,104.6138,104.3334,103.6459,
		 103.4042,103.1198,102.9908,102.723,108.6861,108.4178,108.4251,108.491,108.51,107.8862,107.6086,
	     107.3157,107.3488,106.8545,106.5711,106.1684,105.8835,105.9026,105.6825];
	var c4plr2_preVal = 
		[104.5561 ,104.4708 ,104.3141 ,106.1595 ,105.8049 ,105.4139 ,105.3882 ,104.9182 ,104.5362 ,104.3267 ,103.6781 ,
		 103.0985 ,103.1041 ,102.9936 ,102.7653 ,102.8049 , 109.2044 ,108.4724 ,108.0268 ,108.3658 ,107.9405 ,107.7096 ,
		 107.3177 ,107.2981 ,106.8028 ,106.5360 ,106.1726 , 105.8811 ,105.9157 ,105.6860 ];
	var c4plr2_errrate = 
		[0.00038162 ,0.00004330 ,0.00181576 ,0.00083769 ,0.00064992 ,0.00059357 ,0.00281904 ,0.00035158 ,0.00074215 ,0.00006414 ,
		 0.00031024 ,0.00295640 ,0.00015196 ,0.00002745 ,0.00041212 ,0.05411222 ,0.00725508 ,0.00043649 ,0.00427857 ,0.00132877 ,
		 0.00050339 ,0.00093828 ,0.00001822 ,0.00047228 ,0.00048381 ,0.00032902 ,0.00004000 ,0.00002256 ,0.00012374 ,0.00003267 ];
	var c4plr1_realVal = 
		[-0.5952,-0.4013,-0.3854,0.1984,0.1924,0.3798,0.2924,0.2302,0.3295,0.2691,0.2873,0.17,
		 0.1747,0.0901,0.124,0.1615,0.236,0.259,0.1894,0.0928,0.1735,-0.0056,0.0998,0.1645,-0.0121,
		 0.0936,0.0547,-0.0056,0.0144,-0.0403];
	var c4plr1_preVal = 
		[-0.4788 ,-0.4188 ,-0.4041 ,0.3031 ,0.1991 ,0.4169 ,0.3813 ,0.2157 ,0.3152 ,0.2662 ,0.2990 ,0.1859 ,0.1814 ,0.0955 ,0.1313 ,0.1404 ,
		 0.1991 , 0.2389 ,0.1894 ,0.0459 ,0.1768 ,0.0587 ,0.0927 ,0.1184 ,0.0333 ,0.0658 ,0.0499 ,-0.0028 ,0.0150 ,-0.0464 ];
	var c4plr1_errrate = 
		[0.19561576 ,0.04350854 ,0.04854504 ,0.52785077 ,0.03473866 ,0.09771062 ,0.30406987 ,0.06316297 ,0.04330927 ,0.01059807 ,0.04084264 ,
		 0.09369167 , 0.03828852 ,0.05970355 ,0.05864652 ,0.13044984 ,0.15619430 ,0.07745108 ,0.00005841 ,0.50506532 ,0.01909005 ,11.48017733 ,
		 0.07066869 ,0.28051772 ,3.75545838 ,0.29676386 ,0.08763794 ,0.49969980 ,0.03958202 ,0.15245307 ];
	var c4pl2r1_realVal = 
		[-1.1315,-1.0879,-1.031,-0.4541,-0.4259,-0.3888,-0.5513,-0.4427,-0.3844,-0.4927,-0.4472,
		 -0.4893,-0.4982,-0.5214,-0.508,-0.5182,-0.4711,-0.4822,-0.5587,-0.5664,-0.6019,-0.6513,
		 -0.5595,-0.5972,-0.5962,-0.5588,-0.6114,-0.6649,-0.6517,-0.6859];
	var c4pl2r1_preVal = 
		[-1.1126 ,-1.1131 ,-1.0046 ,-0.5087 ,-0.7036 ,-0.4406 ,-0.5264 ,-0.4706 ,-0.4757 ,-0.5136 ,-0.4383 ,-0.4710 ,-0.4946 ,
		 -0.5157 ,-0.5075 ,-0.5210 ,-0.5088 ,-0.4831 ,-0.5190 ,-0.5689 ,-0.6001 ,-0.6507 ,-0.6143 ,-0.6026 ,-0.5910 ,-0.5654 ,
		 -0.6128 ,-0.6356 ,-0.6528 ,-0.6861 ];
	var c4pl2r1_errrate = 
		[0.016674 ,0.023138 ,0.025564 ,0.120277 ,0.651970 ,0.133258 ,0.045110 ,0.062960 ,0.237616 ,0.042355 ,0.019966 ,0.037488 ,
		 0.007268 ,0.010971 ,0.000976 ,0.005473 ,0.080078 ,0.001918 ,0.071036 ,0.004471 ,0.003048 ,0.000882 ,0.097896 ,0.009108 ,
		 0.008756 ,0.011800 ,0.002251 ,0.044007 ,0.001635 ,0.000332 ];
	
	var c4pl2r2_realVal = 
		[88.2625,88.2091,87.9411,90.0139,89.7721,89.5242,89.2367,88.9804,88.8257,88.5528,88.1335,87.9588,87.7191,87.5603,87.4264,
		 93.4936,93.3441,93.2549,93.2762,93.1911,92.8125,92.572,92.3533,92.238,91.9366,91.6829,91.3617,91.2472,91.2144,90.9943,];
	var c4pl2r2_preVal = 
		[88.2166 ,88.2058 ,88.1645 ,89.9514 ,89.8058 ,89.3901 ,89.4747 ,89.0750 ,88.6898 ,88.5481 ,88.1177 ,87.7961 ,87.6746 ,87.5607 ,87.4240 ,
		 87.5958 ,93.9517 ,93.4480 ,93.0120 ,93.1563 ,92.9415 ,92.5453 ,92.3548 ,92.2556 ,91.9281 ,91.6992 ,91.3703 ,91.2428 ,91.2138 ,90.9889 ];
	var c4pl2r2_errrate = 
		[0.000520 ,0.000037 ,0.002540 ,0.000695 ,0.000376 ,0.001498 ,0.002667 ,0.001063 ,0.001530 ,0.000053 ,0.000179 ,0.001850 ,0.000507 ,0.000005 ,0.000028 ,
		 0.063082 ,0.006510 ,0.002071 ,0.002833 ,0.000373 ,0.001389 ,0.000289 ,0.000016 ,0.000191 ,0.000092 ,0.000178 ,0.000094 ,0.000048 ,0.000006 ,0.000060 ];
	
//	out(c4plr2_realVal.length);
//	out(c4plr2_preVal.length);
//	out(c4plr2_errrate.length);
//	out(c4plr1_realVal.length);
//	out(c4plr1_preVal.length);
//	out(c4plr1_errrate.length);
	
	var theSeries = [];		//存放画图参数
	var bestVal = [];		//最优值
	var realVal = [];		//实测值
	var err_rate = [];		//相对误差
	
	if(type == 1){
		for(var i=29 + period;i<30;i++){
			bestVal.push(c4plr1_preVal[i]);
			realVal.push(c4plr1_realVal[i]);
			err_rate.push(c4plr1_errrate[i]);
		}
		theSeries.push({
			name: '相对误差',
			type:'column',
			tooltip: {
				formatter: function () {
	                return numpercent(this.value);
	            }
			},
			yAxis: 1,
			data: err_rate});
		theSeries.push({name: '实测值',type:'spline',data: realVal});
		theSeries.push({name: '预测值',type:'spline',data: bestVal});
		drawPic(id,'测点：C4-A22-PL-01 分量：横河向位移',theSeries);
	}else if(type==2){
		for(var i=29 + period;i<30;i++){
			bestVal.push(c4plr2_preVal[i]);
			realVal.push(c4plr2_realVal[i]);
			err_rate.push(c4plr2_errrate[i]);
		}
		theSeries.push({
			name: '相对误差',
			type:'column',
			tooltip: {
				formatter: function () {
	                return numpercent(this.value);
	            }
			},
			yAxis: 1,
			data: err_rate});
		theSeries.push({name: '实测值',type:'spline',data: realVal});
		theSeries.push({name: '预测值',type:'spline',data: bestVal});
		drawPic(id,'测点：C4-A22-PL-01 分量：顺河向位移',theSeries);
	}else if(type==3){
		for(var i=29 + period;i<30;i++){
			bestVal.push(c4pl2r1_preVal[i]);
			realVal.push(c4pl2r1_realVal[i]);
			err_rate.push(c4pl2r1_errrate[i]);
		}
		theSeries.push({
			name: '相对误差',
			type:'column',
			tooltip: {
				formatter: function () {
	                return numpercent(this.value);
	            }
			},
			yAxis: 1,
			data: err_rate});
		theSeries.push({name: '实测值',type:'spline',data: realVal});
		theSeries.push({name: '预测值',type:'spline',data: bestVal});
		drawPic(id,'测点：C4-A22-PL-02 分量：横河向位移',theSeries);
	}else{
		for(var i=29 + period;i<30;i++){
			bestVal.push(c4pl2r2_preVal[i]);
			realVal.push(c4pl2r2_realVal[i]);
			err_rate.push(c4pl2r2_errrate[i]);
		}
		theSeries.push({
			name: '相对误差',
			type:'column',
			tooltip: {
				formatter: function () {
	                return numpercent(this.value);
	            }
			},
			yAxis: 1,
			data: err_rate});
		theSeries.push({name: '实测值',type:'spline',data: realVal});
		theSeries.push({name: '预测值',type:'spline',data: bestVal});
		drawPic(id,'测点：C4-A22-PL-02 分量：顺河向位移',theSeries);
	}
	
}

//@test
//绘图
function drawPic(id,title,theSeries){
	var startTime;
	if(period==-6)startTime = '2015-12-9';
	else startTime = '2015-11-16';
	var cate = [];
	cate.push(startTime);
	for(var i=1;i<1-period;i++){
		startTime = addOneDay(startTime);
		cate.push(startTime);
	}
	console.log(startTime);
	$('#'+id).highcharts({
		credits : {
			enabled: false
		},
		legend: {
            align: 'center',
            verticalAlign: 'bottom',
        },
		chart : {
			type : 'spline'
		},
		title : {
			useHTML : true,
			text : '<p style="font-size:12px;font-weight:bold;">' + title + '</p>'
		},
		plotOptions: {
            series: {
                pointStart: startTime,
                pointInterval: 24 * 3600 * 1000
            }
        },
		xAxis : {
			categories : cate,
//			type : 'datetime',
//			dateTimeLabelFormats : {
//				 day: '%Y-%m-%e'
//            },
//            tickInterval: 24*3600*1000,
            labels:{
                rotation: -45
            },
		},
		yAxis : [{//测值轴
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
		},{//相对误差轴
			labels: {
                formatter : function(){
	            	return (Number(this.value)*100).toFixed(2) + "%";
	            },
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            title: {
                text: '相对误差',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            plotLines: [{
                color: '#FF4500',
                width: 2,
                value: 0.2,
                dashStyle: 'ShortDash',
                label: {
                	text: '20%',
                	align: 'right',
                	y: 16,
                    style: {
                        color: 'red',
                        fontWeight: 'bold'
                    }
                }
            }],
            max: 1,
			opposite: true
		}],
		tooltip : {
			crosshairs : true,
			followPointer : true,
            formatter : function(){
            	var day = new Date(this.x);
            	var s = '<b>' + day.getFullYear() + '-' + (day.getMonth()+1) + '-' + day.getDate() + '</b>';
                $.each(this.points, function () {
                	if(this.series.name == '相对误差'){
                		s += '<br/>' + this.series.name + ': ' + numpercent(this.y);
                	}else{
                		s += '<br/>' + this.series.name + ': ' + this.y;
                	}
                });

                return s;
            },
			shared : true
		},
		plotOptions : {
			spline : {
				marker : {
					radius : 4,
					lineColor : '#666666',
					lineWidth : 1
				}
			},
		},
		exporting: {
        	url: exportUrl,
        	buttons: {
        		contextButton: {
        			text: '',
        			menuItems: [{
        				text: '导出PNG',
        				onclick: function () {
        					this.exportChart({
        						type: 'image/png',
        			            filename: 'chart-png'
        					});
        				}
        			},{
        				text: '导出JPEG',
        				onclick: function () {
        					this.exportChart({
        						type: 'image/jpeg',
        			            filename: 'chart-jpeg'
        					});
        				}
        			},{
        				text: '导出PDF',
        				onclick: function () {
        					this.exportChart({
        						type: 'application/pdf',
        			            filename: 'chart-pdf'
        					});
        				}
        			}]
        		}
        	}
        },
		series : theSeries
	});
}

//日期加一天
function addOneDay(date){
	date = date.replace(/-/g,'/'); 
	var day = new Date(date);
	day.setDate(day.getDate()+1);
	return day.getFullYear() + '-' + (day.getMonth()+1) + "-" + day.getDate();
}

//日期加n天
function addDay(date,n){
    date = date.replace(/-/g,'/');
    var day = new Date(date);
    day.setDate(day.getDate()+n);
    var result = day.getFullYear() + '-';
    //return  + '-' + (day.getMonth()+1) + "-" +
    //确保格式为0000-00-00
    if(day.getMonth()<9)
        result += '0';
    result += (day.getMonth()+1) + "-";

    if(day.getDate()<9)
        result += '0';
    result += day.getDate();
    return result;
}