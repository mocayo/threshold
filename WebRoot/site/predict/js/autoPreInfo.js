/**
 * @author qihai
 * @time 2016年5月19日11:13:52
 * 初始化. 自动预测查询
 */
var pageindex = 1;
var pagesize = 10;
var pagemax = 0;
var exportUrl = 'http://121.248.200.5:8000/highcharts-export';

$(function(){
	
	/*$(".collapse-link").click(function(event){
		var obj = $(this).children("i");
		if(obj.hasClass("fa-chevron-up")){
			$(this).parent().siblings(".panel-body").hide();
			obj.removeClass("fa-chevron-up").addClass("fa-chevron-down");
		}else{
			$(this).parent().siblings(".panel-body").show();
			obj.removeClass("fa-chevron-down").addClass("fa-chevron-up");
		}
	});*/
	
	/*var $dec = $("#dec_Sel");
	var $dam = $("#dam_Sel");
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
	
	// 获取监测项
	$.getJSON("GetMonitorItems",function(result){
		$dec.html("");
		$dec.append("<option value='' selected>---请选择---</option>");
		for(var i = 0; i < result.length; i++)
		{
			$dec.append("<option value='"+result[i].type+","+result[i].table+"'>"+result[i].describe+"</option>"); 
		}
	});
//	loadPage1("","","",$stime.val(),$etime.val());
	
	//检测仪器 变化，坝段变化
	$dec.on('change',function(){
		// 检测仪器 变化，坝段变化
		var monitorItem = $.trim($(this).val().split(",")[1]);
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
			$("#dam_Sel").html(row.toString());
			showNoSelect();
		});
//		if ($dam.val()!=""){
//			getInstrNo($dec.val().split(",")[0],$dam.val());
//		}
	});
	$dam.change(function(){
		//getInstrNo($dec.val().split(",")[0],$dam.val()+"");
		showNoSelect();
	});
	*/
	initPointSel();
	
	//autoTest();
	
	$("#search_button").click(function(){
		if($("#point_sel").val()==''){
			alert("请选择测点编号！");
			return;
		}
		var _points = $("#point_sel").val();
		console.log(_points);
		var table = _points.split("#")[0];
		var point = _points.split("#")[1];
//		var point = [];
//		var table = [];
//		$.each(_points,function(index,element){
//			var tmp = element.split('#');
//			table.push(tmp[0]);
//			point.push(tmp[1]);
//		});
		console.log(table);
		console.log(point);
		$.getJSON('sinPreAutoByPage',{
			/*point: 'C4-A22-PL-01',
			table: 'T_ZB_PL',
			start: '2013-04-01',
			end: '2013-05-20'*/
			point: point.toString(),
			table: table.toString(),
			start: $("#start_time").val(),
			end: $("#end_time").val()
		},function(result){
			console.log(result);
			drawPic(result);
		});
		
	});
	
});

//自动预测绘图test
function autoTest(){
	$("#test").show();
	$.getJSON('getTestInfo',function(result){
		var m1 = [];
		var m2 = [];
		var m3 = [];
		var real = [];
		var best = [];
		var m1name = '',m2name = '',m3name = '';
		//var modelname = '';
		$.each(result,function(index,element){
			if(element.abs_err == '1')
				best.push([Date.parse(element.DT),Number(element.preVal)]);
			if(index<61){
				m1.push([Date.parse(element.DT),Number(element.preVal)]);
				real.push([Date.parse(element.DT),Number(element.realVal)]);
				m1name = element.modelName;
			}else if(index<122){
				m2.push([Date.parse(element.DT),Number(element.preVal)]);
				m2name = element.modelName;
			}else{
				m3.push([Date.parse(element.DT),Number(element.preVal)]);
				m3name = element.modelName;
			}
		});
		best = best.sort();
		console.log(m1);
		console.log(m2);
		console.log(m3);
		console.log(real.sort());
		console.log(best);
		var theSeries = [];
		theSeries.push({name: '最优',data: best,marker:{enabled:true,radius:4}});
		theSeries.push({name: '实测值',data: real,marker:{enabled:true,radius:4}});
		theSeries.push({name: m1name,data: m1.sort(),marker:{enabled:true,radius:4}});
		theSeries.push({name: m2name,data: m2.sort(),marker:{enabled:true,radius:4}});
		theSeries.push({name: m3name,data: m3.sort(),marker:{enabled:true,radius:4}});
		$("#test_container").highcharts({
			chart: {
	            type: 'spline',
	            zoomType: 'x',
	            resetZoomButton: {
	                position: {
	                    // align: 'right', // by default
	                    // verticalAlign: 'top', // by default
	                    x: -10,
	                    y: 10
	                },
	                relativeTo: 'chart'
	            }
	        },
	        title: {
	        	/*text: 'C4-A22-S9-03-2 应变'*/
	        	text: 'C4-A22-PL-01 顺河向位移'
	        },
	        credits: {
	        	enabled: false
	        },
	        xAxis : {
				type : 'datetime',
				dateTimeLabelFormats : {
					 day: '%Y-%m-%e'
	            },
	            /*tickInterval: 7*24*3600*1000,*/
	            labels:{
	                rotation: -45,
	                formatter: function(){
	                	var day = new Date(this.value);
	                	return day.getFullYear() + '-' + (day.getMonth()+1) + '-' + day.getDate();
	                }
	            }
	            
			},
			yAxis : {//测值轴
				labels: {
	                format: '{value}',
	                style: {
	                    color: Highcharts.getOptions().colors[1]
	                }
	            },
	            title: {
	                text: '测值 ',
	                style: {
	                    color: Highcharts.getOptions().colors[1]
	                }
	            },
	          /*  min: 60,
	            max: 82*/
			},
			tooltip : {
				crosshairs : true,
				followPointer : true,
	            formatter : function(){
	            	var day = new Date(this.x);
	            	var s = '<b>' + day.getFullYear() + '-' + (day.getMonth()+1) + '-' + day.getDate() + '</b>';
	                $.each(this.points, function () {
	                	s += '<br/>' + this.series.name + ': ' + Highcharts.numberFormat(this.y,4);
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

//初始化测点选择
function initPointSel(){
	getBasicItem();
	basic.on('change',getMonitor);
	monitor.on('change',getInstrType);
	instrType.on('change',getObservePoints);
//	point.on('change',getComponent);
	var $stime = $("#start_time"), $etime = $("#end_time");
	$stime.datetimepicker({
		lang : 'zh',
		format : 'Y-m-d',
		value : addDay(getDate(0),-30)/*getDate(0)*/,
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

//绘图绘图r1 & r2
function drawPic(result){
	var pre_r1 = [];
	var real_r1 = [];
	var err_rate_r1 = [];
	var abs_err_r1 = [];
	var name_r1 = [];
	var pre_r2 = [];
	var real_r2 = [];
	var err_rate_r2 = [];
	var abs_err_r2 = [];
	var name_r2 = [];
	$.each(result,function(index,element){
		if(element.component == 'r1'){
			pre_r1.push([Date.parse(element.DT),Number(element.preVal)]);
			real_r1.push([Date.parse(element.DT),Number(element.realVal)]);
			err_rate_r1.push([Date.parse(element.DT),Number(element.err_rate)]);
			if(name_r1.length==0){
				var tmp = element.rs.split(";");
				name_r1.push(element.INSTR_NO + ' ' + tmp[0].split("#")[0]);
				name_r1.push(tmp[1].split("#")[0]);
			}
		}else if(element.component == 'r2'){
			pre_r2.push([Date.parse(element.DT),Number(element.preVal)]);
			real_r2.push([Date.parse(element.DT),Number(element.realVal)]);
			err_rate_r2.push([Date.parse(element.DT),Number(element.err_rate)]);
			if(name_r2.length==0){
				var tmp = element.rs.split(";");
				name_r2.push(element.INSTR_NO + ' ' + tmp[0].split("#")[1]);
				name_r2.push(tmp[1].split("#")[1]);
			}
			/*if(name_r2==''){
				name_r2 = element.INSTR_NO + '：' +element.rs.split("#")[1];
			}*/
		}
	});
	spline("container_r1",name_r1,pre_r1,real_r1,err_rate_r1);
	spline("container_r2",name_r2,pre_r2,real_r2,err_rate_r2);
	console.log(pre_r1);
	console.log(pre_r2);
	console.log(name_r1);
	console.log(name_r2);
}

//绘制折线
function spline(id,title,pre,real,err_rate){
	var theSeries = [];
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
	console.log(err_rate.sort());
	theSeries.push({name: '预测值',data: pre,marker:{enabled:true,radius:1}});
	theSeries.push({name: '实测值',data: real,marker:{enabled:true,radius:1}});
	var _step = parseInt(pre.length/60) + 1;
	$("#"+id).highcharts({
		chart: {
            type: 'spline',
//            zoomType: 'x'
        },
        title: {
        	text: title[0]
        },
        credits: {
        	enabled: false
        },
        xAxis : {
			type : 'datetime',
			dateTimeLabelFormats : {
				 day: '%Y-%m-%e'
            },
            tickInterval: 7*24*3600*1000,
            labels:{
                /*rotation: -45,*/
                step: _step,
                formatter: function(){
                	var day = new Date(this.value);
                	return day.getFullYear() + '-' + (day.getMonth()+1) + '-' + day.getDate();
                }
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
                text: '测值  单位('+ title[1] + ')',
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
                		s += '<br/>' + this.series.name + ': ' + this.y + title[1];
                	}
                });

                return s;
            },
			shared : true
		},
/*		plotOptions : {
			spline : {
				marker : {
					radius : 4,
					lineColor : '#666666',
					lineWidth : 1
				}
			},
		},
*/		exporting: {
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

//误差百分化，保留小数点后4位数字
function numpercent(err_rate){
	return (Number(err_rate)*100).toFixed(4) + "%";
}

//第一页
function firstpage(){
	pageindex = 1;
	$("#autoResTable").html("");
	$("#search_button").click();	
}

//末页
function lastpage(){
	pageindex = pagemax;
	$("#autoResTable").html("");
	$("#search_button").click();
}

//上一页
function prepage(){
	if(pageindex==1){
//		alert("已经是第一页！");
		return;
	}
	pageindex --;
	$("#autoResTable").html("");
	$("#search_button").click();	
}

//下一页
function nextpage(){
	if(pageindex==pagemax){
		return;
	}
	pageindex ++;
	$("#autoResTable").html("");
	$("#search_button").click();	
}

//跳转
function gotopage(){
	var $index = parseInt($("#topage").val());
	console.log($index);
	if($index > pagemax){
		$("#topage").val(pagemax);
		return;
	}
	pageindex = $index;
	$("#autoResTable").html("");
	$("#search_button").click();	
}

//保留测值后小数点后n位小数
function numformat(num,n){
	return (Number(num).toFixed(n));
}

//测点所有实测值
var detailChart;
var dt = {};
//dt['detailStart'] = Date.parse('2015-05-17');
//dt['masterFrom'] = Date.parse('2013-09-17');
//dt['masterTo'] = Date.parse('2015-06-13');

function create(){
    $(document).ready(function () {
    	console.log('jjjjjj');
        // make the container smaller and add a second container for the master chart
        var $container = $('#container')
            .css('position', 'relative');

        $('<div id="detail-container">')
            .appendTo($container);

        $('<div id="master-container">')
            .css({ position: 'absolute', top: 320, height: 120, width: '100%' })
            .appendTo($container);

//        extremeTest();
        $.getJSON('getAllSinPreAuto', function (result){
            console.log(result.length);
            var the_dt = [];
            var data = [];
            the_dt.push(result[0].dt);
            data.push(Number(result[0].realVal));
            for (var i = 1; i < result.length; i++) {
                var delta = diffDate(result[i-1].dt,result[i].dt);
                if(delta==0){
                    //日期已经存在，pass
                }
                else if(delta==1){
                    //日期刚好，go
                    data.push(Number(result[i].realVal));
                    the_dt.push(result[i].dt);
                }
                else{
                    //只考虑日期升序的情况
                    //日期相差超过1，补齐缺失数据，0代替
                    for(var j=0;j<delta;j++){
                        data.push(0);
                        the_dt.push(addDay(result[i].dt,j+1-delta));
                    }
                }
            }
          dt['masterFrom'] = Date.parse(the_dt[0]);
          dt['masterTo'] = Date.parse(the_dt[the_dt.length-1]);
          dt['detailStart'] = Date.parse(addDay(the_dt[the_dt.length-1], -30));
//          console.log(dt);
            // create master and in its callback, create the detail chart
            createMaster(data);
        });

    });
}

// create the detail chart
function createDetail(masterChart) {

    // prepare the detail chart
    var detailData = [],
        //detailStart = Date.UTC(2008, 7, 1);
        detailStart = dt['detailStart'];
    $.each(masterChart.series[0].data, function () {
        if (this.x >= detailStart) {
            detailData.push(this.y);
        }
    });

    // create a detail chart referenced by a global variable
    detailChart = $('#detail-container').highcharts({
        chart: {
            marginBottom: 120,
            reflow: false,
            marginLeft: 50,
            marginRight: 20,
            style: {
                position: 'absolute'
            },
            type: 'spline'
        },
        credits: {
            enabled: false
        },
        title: {
            text: '测点'
        },
        subtitle: {
            text: '拖动下方区域查看详细信息'
        },
        xAxis: {
            type: 'datetime',
            labels:{
                formatter: function(){
                    var day = new Date(this.value);
                    return day.getFullYear()+ '-' + (day.getMonth()+1) + '-' + day.getDate();
                }
            }
        },
        yAxis: {
            title: {
                text: null
            },
            maxZoom: 0.1
        },
        tooltip: {
            formatter: function () {
                var point = this.points[0];
                return '<b>' + point.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%Y-%m-%d', this.x) + ':<br/>' +
                    Highcharts.numberFormat(point.y, 2);
            },
            shared: true
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                marker: {
                    enabled: false,
                    states: {
                        hover: {
                            enabled: true,
                            radius: 3
                        }
                    }
                }
            }
        },
        series: [{
            name: '实测值',
            pointStart: detailStart,
            pointInterval: 24 * 3600 * 1000,
            data: detailData
        }],

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
        }

    }).highcharts(); // return chart
}

// create the master chart
function createMaster(data) {
    $('#master-container').highcharts({
        chart: {
            reflow: false,
            borderWidth: 0,
            backgroundColor: null,
            marginLeft: 50,
            marginRight: 20,
            zoomType: 'x',
            events: {

                // listen to the selection event on the master chart to update the
                // extremes of the detail chart
                selection: function (event) {
                    var extremesObject = event.xAxis[0],
                        min = extremesObject.min,
                        max = extremesObject.max,
                        detailData = [],
                        xAxis = this.xAxis[0];

                    // reverse engineer the last part of the data
                    $.each(this.series[0].data, function () {
                        if (this.x > min && this.x < max) {
                            detailData.push([this.x, this.y]);
                        }
                    });

                    // move the plot bands to reflect the new detail span
                    xAxis.removePlotBand('mask-before');
                    xAxis.addPlotBand({
                        id: 'mask-before',
                        //from: Date.UTC(2006, 0, 1),
                        from: dt['masterFrom'],
                        to: min,
                        color: 'rgba(0, 0, 0, 0.2)'
                    });

                    xAxis.removePlotBand('mask-after');
                    xAxis.addPlotBand({
                        id: 'mask-after',
                        from: max,
                        //to: Date.UTC(2008, 11, 31),
                        to: dt['masterTo'],
                        color: 'rgba(0, 0, 0, 0.2)'
                    });


                    detailChart.series[0].setData(detailData);

                    return false;
                }
            }
        },
        title: {
            text: null
        },
        xAxis: {
            type: 'datetime',
            showLastTickLabel: true,
            labels:{
                formatter: function(){
                    var day = new Date(this.value);
                    return day.getFullYear()+ '-' + (day.getMonth()+1) + '-' + day.getDate();
                }
            },
            maxZoom: 14 * 24 * 3600000, // fourteen days
            plotBands: [{
                id: 'mask-before',
                //from: Date.UTC(2006, 0, 1),
                //to: Date.UTC(2008, 7, 1),
                from: dt['masterFrom'],
                to: dt['detailStart'],
                color: 'rgba(0, 0, 0, 0.2)'
            }],
            title: {
                text: null
            }
        },
        yAxis: {
            gridLineWidth: 0,
            labels: {
                enabled: false
            },
            title: {
                text: null
            },
            //min: 0.6,
            showFirstLabel: false
        },
        tooltip: {
            formatter: function () {
                return false;
            }
        },
        legend: {
            enabled: false
        },
        credits: {
            enabled: false
        },
        plotOptions: {
            series: {
                fillColor: {
                    linearGradient: [0, 0, 0, 70],
                    stops: [
                        [0, Highcharts.getOptions().colors[0]],
                        [1, 'rgba(255,255,255,0)']
                    ]
                },
                lineWidth: 1,
                marker: {
                    enabled: false
                },
                shadow: false,
                states: {
                    hover: {
                        lineWidth: 1
                    }
                },
                enableMouseTracking: false
            }
        },

        series: [{
            type: 'area',
            name: 'USD to EUR',
            pointInterval: 24 * 3600 * 1000,
            //pointStart: Date.UTC(2006, 0, 1),
            pointStart: dt['masterFrom'],
            data: data
        }],

        exporting: {enabled: false}

    }, function (masterChart) {
        createDetail(masterChart);
    })
        .highcharts(); // return chart instance
}

//日期相减并返回天数
function diffDate(date1,date2){
    var d1 = Date.parse(date1);
    var d2 = Date.parse(date2);
    return (d2-d1)/(24*3600*1000);
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

//补全时间序列
function test(){
    var result = [];
    var the_dt = [];
    var dtSeries = [];
    result.push('2015-01-01');
    result.push('2015-01-02');
    result.push('2015-01-03');
    result.push('2015-01-03');
    result.push('2015-01-05');
    result.push('2015-01-07');
    result.push('2015-01-08');
    result.push('2015-01-11');
    console.log(result);
    result.sort();
    dtSeries.push(result[0]);
    for (var i = 1; i < result.length; i++) {
        var delta = diffDate(result[i-1],result[i]);
        console.log(result[i] + '-' + result[i-1] + '=' + delta);
        if(delta==0){}
        else if(delta==1){
            the_dt.push(1);
            dtSeries.push(result[i]);
        }
        else{
            console.log('loop' + delta);
            for(var j=0;j<delta;j++){
                the_dt.push(0);
                dtSeries.push(addDay(result[i],j+1-delta));
            }
        }
        //if(the_dt.length==0){
        //    the_dt.push(result[i]);
        //}else{
        //    console.log('current -> ' + JSON.stringify(the_dt));
        //    var tmp_dt = the_dt[the_dt.length-1];
        //    var delta = diffDate(tmp_dt,result[i]);
        //    console.log('compare' + tmp_dt + '&&' + result[i])
        //    if(delta==0){
        //        //日期已经存在，pass
        //    }else if(delta==1){
        //        //日期刚好，go
        //        the_dt.push(result[i]);
        //    }else{
        //        //只考虑日期升序的情况
        //        //日期相差超过1，补齐缺失数据，0代替
        //        for(var j=0;j<delta;j++){
        //            console.log('loop -> ' + j);
        //
        //            the_dt.push(addDay(tmp_dt,j));
        //            //console.log('push ' + addDay(tmp_dt,j));
        //            console.log('after push ' + the_dt.length);
        //        }
        //    }
        //    console.log(tmp_dt + '-' + result[i] + '='  + delta);
        //}
    }
    console.log(the_dt);
    //dtSeries.push(result[0]);
    //for(var i=0;i<the_dt.length;i++){
    //    if(the_dt[i]==0){
    //
    //    }else{
    //
    //    }
    //    dtSeries.push(addDay(result[0],i+1));
    //}
    console.log(dtSeries);
}

//生成随机整数
function fRandomBy(under, over){
    switch(arguments.length){
        case 1: return parseInt(Math.random()*under+1);
        case 2: return parseInt(Math.random()*(over-under+1) + under);
        default: return 0;
    }
}

//极限数据测试
function extremeTest(){
    var data = [];
    dt['detailStart'] = Date.parse('2043-05-17');
    dt['masterFrom'] = Date.parse('2013-09-17');
    dt['masterTo'] = Date.parse('2043-09-10');
    for(var i=0;i<10950;/*30 years*/i++){
        data.push(fRandomBy(-20,20));
    }
    createMaster(data);
}