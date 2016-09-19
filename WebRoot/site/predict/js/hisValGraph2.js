/**
 * @author qihai
 * @time 2016年5月6日01:46:51
 * 历史特征图2
 */
var exportUrl = 'http://121.248.200.5:8000/highcharts-export';

$(function() {
	initPointSel();
	
	demo();
	
	$("#query_lstzz").click(function() {
//		var table = point.val().split('#')[0];
//		var instr_no = point.val().split('#')[1];
//		if(instr_no == '' || instr_no==null){
//			alert("请选择测点编号！");
//			return;
//		}
		var wl_date = $("#wl_date").val();
		$.getJSON('getLSTZZ_RES_5', {
			table : 'T_ZB_PL',
			instr_no : 'C4-A22-PL-01',
			wl_date : wl_date
		}, function(result, status) {
			if (status == 'success') {
				var r1_0 = [];
				var r1_1 = [];
				var r2_0 = [];
				var r2_1 = [];
				console.log(result);
				$.each(result, function(index, element) {
					if (element.comp == 'r1') {
						if (element.isRising == 0) {
							r1_0.push(element);
						} else {
							r1_1.push(element);
						}
					} else {
						if (element.isRising == 0) {
							r2_0.push(element);
						} else {
							r2_1.push(element);
						}
					}
				});
				console.log(r1_0);
				console.log(r1_1);
				console.log(r2_0);
				console.log(r2_1);
				drawPic(r1_0,'_r1_0',0, '上升水位');
				drawPic(r1_1,'_r1_1',0, '下降水位');
				drawPic(r2_0,'_r2_0',1, '上升水位');
				drawPic(r2_1,'_r2_1',1, '下降水位');
			} else {
				alert("与服务器连接失败，请重试...");
			}
		});
	});
});

function drawPic(data, id,comp_index,rise) {
	$('#container' + id).html('');
	var title = '';
	var name_comp = data[0].name_comp.split('unit');
	var the_comp = name_comp[0].split('#')[comp_index];
	var unit = name_comp[1].split('#')[comp_index];
	title = data[0].pno + ' ' + the_comp + ' ' + rise;
	var ranges = [];
	var averages = [];
	$.each(data, function(index, element) {
		var year = element.wateryear.split('~')[0];
		ranges.push([ Date.parse(year), Number(element.minV),
				Number(element.maxV) ]);
		averages.push([ Date.parse(year), Number(element.avgV) ]);
	});
	ranges.sort();
	averages.sort();

	$('#container' + id).highcharts(
			{
				credits : {
					enabled : false
				},
				title : {
					useHTML: true,
					text : '<b style="font-size:15px">' + title + '</b>'
				},

				xAxis : {
					type : 'datetime',
					labels : {
						formatter : function() {
							var day = new Date(this.value);
							return day.getFullYear() + '年';
							/*return day.getFullYear() + '-'
									+ (day.getMonth() + 1) + '-'
									+ day.getDate();*/
						}
					}
				},

				yAxis : {
					title : {
						text : null
					}
				},

				tooltip : {
					crosshairs : true,
					shared : true,
					formatter : function() {
						var day = new Date(this.x);
						var s = '<b>' + day.getFullYear() /*+ '-'
								+ (day.getMonth() + 1) + '-' + day.getDate()*/
								+ '年</b>';
						s += '<br>';
						s += the_comp + ' ' + Highcharts.numberFormat(this.points[0].y,2) + unit;
						s += '<br>';
						s += '范围：' + Highcharts.numberFormat(this.points[1].point.low,2) + unit + ' ~'
								+ Highcharts.numberFormat(this.points[1].point.high,2) + unit;
						return s;
					},
				},

				legend : {
					enabled : false
				},

				series : [ {
					name : 'Temperature',
					data : averages,
					type : 'spline',
					zIndex : 1,
					marker : {
						fillColor : 'white',
						lineWidth : 2,
						lineColor : Highcharts.getOptions().colors[0]
					}
				}, {
					name : 'Range',
					data : ranges,
					type : 'areasplinerange',
					lineWidth : 0,
					linkedTo : ':previous',
					color : /*Highcharts.getOptions().colors[0]*/'orange',
					fillOpacity : 0.5,
					zIndex : 0
				} ],

				exporting : {
					url : exportUrl,
					buttons : {
						contextButton : {
							text : '导出',
							menuItems : [ {
								text : '导出PNG',
								onclick : function() {
									this.exportChart({
										type : 'image/png',
										filename : 'point-png'
									});
								}
							}, {
								text : '导出JPEG',
								onclick : function() {
									this.exportChart({
										type : 'image/jpeg',
										filename : 'point-jpeg'
									});
								}
							}, {
								text : '导出PDF',
								onclick : function() {
									this.exportChart({
										type : 'application/pdf',
										filename : 'point-pdf'
									});
								}
							} ]
						}
					}
				}

			});
}

// 初始化测点选择
function initPointSel() {
	getBasicItem();
	basic.on('change', getMonitor);
	monitor.on('change', getInstrType);
	instrType.on('change', getObservePoints);
	point.on('change', getComponent);
	var $wl_date = $("#wl_date");
	$wl_date.datetimepicker({
		lang : 'zh',
		format : 'Y-m-d',
		value : '2016-01-29',
		onChangeDateTime : function(dp, $input) { 
			nextdate = $input.val();
			$("#wl_date").text($input.val());
		},
		timepicker : false
	});
}

//demo
function demo() {
	var real_val = [[1246406400000, 20]];
	var ranges = [
		[1246406400000, 14.3, 27.7],
		[1246492800000, 14.5, 27.8],
		[1246579200000, 15.5, 29.6],
		[1246665600000, 16.7, 30.7],
		[1246752000000, 16.5, 25.0],
		[1246838400000, 17.8, 25.7],
		[1246924800000, 13.5, 24.8],
		[1247011200000, 10.5, 21.4],
		[1247097600000, 9.2, 23.8],
		[1247184000000, 11.6, 21.8],
		[1247270400000, 10.7, 23.7],
		[1247356800000, 11.0, 23.3],
		[1247443200000, 11.6, 23.7],
		[1247529600000, 11.8, 20.7],
		[1247616000000, 12.6, 22.4],
		[1247702400000, 13.6, 19.6],
		[1247788800000, 11.4, 22.6],
		[1247875200000, 13.2, 25.0],
		[1247961600000, 14.2, 21.6],
		[1248048000000, 13.1, 17.1],
		[1248134400000, 12.2, 15.5],
		[1248220800000, 12.0, 20.8],
		[1248307200000, 12.0, 17.1],
		[1248393600000, 12.7, 18.3],
		[1248480000000, 12.4, 19.4],
		[1248566400000, 12.6, 19.9],
		[1248652800000, 11.9, 20.2],
		[1248739200000, 11.0, 19.3],
		[1248825600000, 10.8, 17.8],
		[1248912000000, 11.8, 18.5],
		[1248998400000, 10.8, 16.1]
	],
	averages = [
		[1246406400000, 21.5],
		[1246492800000, 22.1],
		[1246579200000, 23],
		[1246665600000, 23.8],
		[1246752000000, 21.4],
		[1246838400000, 21.3],
		[1246924800000, 18.3],
		[1247011200000, 15.4],
		[1247097600000, 16.4],
		[1247184000000, 17.7],
		[1247270400000, 17.5],
		[1247356800000, 17.6],
		[1247443200000, 17.7],
		[1247529600000, 16.8],
		[1247616000000, 17.7],
		[1247702400000, 16.3],
		[1247788800000, 17.8],
		[1247875200000, 18.1],
		[1247961600000, 17.2],
		[1248048000000, 14.4],
		[1248134400000, 13.7],
		[1248220800000, 15.7],
		[1248307200000, 14.6],
		[1248393600000, 15.3],
		[1248480000000, 15.3],
		[1248566400000, 15.8],
		[1248652800000, 15.2],
		[1248739200000, 14.8],
		[1248825600000, 14.4],
		[1248912000000, 15],
		[1248998400000, 13.6]
	];
//	var ranges2 = [];
//	$.each(ranges,function(index,element){
//		var tmp = element;
//		tmp[1] -= 3;
//		tmp[2] += 2.5;
//		ranges2.push(tmp);
//	});
	$('#container').highcharts({
		credits: {
			enabled: false
		},
		
		title: {
			text: 'Demo - July temperatures'
		},
		
		xAxis : {
			type : 'datetime',
			labels : {
				formatter : function() {
					var day = new Date(this.value);
					return day.getFullYear() + '-'
							+ (day.getMonth() + 1) + '-'
							+ day.getDate();
				},
				step: 2
			},
		},
		
		yAxis: {
			title: {
				text: null
			}
		},
		
		tooltip : {
			crosshairs : true,
			shared : true,
			valueSuffix : '°C',
			formatter : function() {
				var day = new Date(this.x);
				var s = '<b>' + day.getFullYear() + '-'
						+ (day.getMonth() + 1) + '-' + day.getDate()
						+ '</b>';
				s += '<br>';
				s += '温度：' + this.points[0].y + '°C';
				s += '<br>';
				s += '范围：' + this.points[1].point.low + '°C ~'
						+ this.points[1].point.high + '°C';
				return s;
			},
		},
		
		legend: {
			enabled: false
		},
		
		series: [{
			name: 'Temperature',
			type: 'spline',
			data: averages,
			zIndex: 1,
			marker: {
				fillColor: 'white',
				lineWidth: 2,
				lineColor: Highcharts.getOptions().colors[0]
			},
		}, {
			name: 'Range',
			data: ranges,
			type: 'areasplinerange',
			lineWidth: 0,
			linkedTo: ':previous',
			color: Highcharts.getOptions().colors[0],
			fillOpacity: 0.5,
			zIndex: 0,
		},{
			name:'实测值',
			type:'scatter',
			data:real_val,
			color: 'rgba(255, 0, 0, .8)'
		}/*, {
		
			name: 'Range2',.
			
			data: ranges2,
			type: 'areasplinerange',
			lineWidth: 0,
			//linkedTo: ':previous',
			color: 'orange',
			fillOpacity: 0.3,
			zIndex: 0,
		}*/],
		
		exporting : {
			enabled: false
		}
	
	});
    
}