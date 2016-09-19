/**
 * @author qihai
 * @time 2016年5月19日11:14:24
 * 误差分析
 */
$(function(){
//	test();
	// 时间
	initPointSel();
	$("#view_err").click(function(){
		var table = point.val().split('#')[0];
		var instr_no = point.val().split('#')[1];
		var _comp = comp.val().split('#')[1];
//		var info = "";
//		info += '测点：' + instr_no + '\n';
//		info += '分量：' + $("#comp_sel option:selected").text();
		
		var data = {
			instr_no: instr_no,
			table: table,
			comp: _comp
		}
		
//		console.log(data);
//		if (!confirm(info)) {
//			return;
//		}
		$("#err_ana_result").show('slow',function(){
			$("#container").html('');
			create(data);
		});
	});
});

/*var basic = $("#basic_sel");
var monitor = $("#monitor_sel");
var instrType = $("#instrType_sel");
var point = $("#point_sel");
var comp = $("#comp_sel");*/

//初始化测点选择
function initPointSel(){
	getBasicItem();
	basic.on('change',getMonitor);
	monitor.on('change',getInstrType);
	instrType.on('change',getObservePoints);
	point.on('change',getComponent);
	getComponent();
//	var $stime = $("#start_time"), $etime = $("#end_time");
//	$stime.datetimepicker({
//		lang : 'zh',
//		format : 'Y-m-d',
//		value : getDate(0),
//		onShow : function(ct) {
//			if ($etime.val())
//				string = $etime.val().split("-");
//			else
//				string = false;
//			this.setOptions({
//				maxDate : string ? string[0] + "/" + string[1] + "/"
//						+ string[2] : false
//			});
//		},
//		onChangeDateTime : function(dp, $input) { // 时间变化
//			stime = $input.val();
//		},
//		timepicker : false
//	});
//	$etime.datetimepicker({
//		lang : 'zh',
//		format : 'Y-m-d',
//		value : getDate(0),
//		onShow : function(ct) {
//			string = $stime.val().split("-");
//			this.setOptions({
//				minDate : $stime.val() ? string[0] + "/" + string[1] + "/"
//						+ string[2] : false
//			});
//		},
//		onChangeDateTime : function(dp, $input) { // 时间变化
//			etime = $input.val();
//		},
//		timepicker : false
//	});
}

function test(){
	$("#container").hide();
	$('#test').highcharts({
        title: {
            text: 'Monthly Average Temperature',
            x: -20 //center
        },
        subtitle: {
            text: 'Source: WorldClimate.com',
            x: -20
        },
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
        yAxis: {
            title: {
                text: 'Temperature (°C)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '°C'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: 'Tokyo',
            data: [7.0, 6.9, 9.5, {y:14.5,marker: {
                symbol: 'url(../css/img/smer.png)'
            }}, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
        }/*, {
            name: 'New York',
            data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
        }, {
            name: 'Berlin',
            data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
        }, {
            name: 'London',
            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
        }*/]
    });
}

//测点所有实测值
var detailChart;
var dt = {};
var exportUrl = 'http://121.248.200.5:8000/highcharts-export';
var losts = [];
var the_dt = [];
var err_sum = {'level1':0,'level2':0};
//dt['detailStart'] = Date.parse('2015-05-17');
//dt['masterFrom'] = Date.parse('2013-09-17');
//dt['masterTo'] = Date.parse('2015-06-13');

function create(the_data){
    $(document).ready(function () {

        // make the container smaller and add a second container for the master chart
        var $container = $('#container')
            .css('position', 'relative');

        $('<div id="detail-container">')
            .appendTo($container);

        $('<div id="master-container">')
            .css({ position: 'absolute', top: 320, height: 120, width: '100%' })
            .appendTo($container);

//        extremeTest();
        $.getJSON('getErrateByPoint', the_data,function (result){
            console.log(result);
            var data = [];
            initErrSum();
            the_dt.push(result[0].DT);
            data.push(Number(result[0].err_rate));
            for (var i = 1; i < result.length; i++) {
                var delta = diffDate(result[i-1].DT,result[i].DT);
                if(delta==0){
                    //日期已经存在，pass
                }
                else if(delta==1){
                    //日期刚好，go
//                	data.push({
//                		y:Number(result[i].err_rate)*100,
//                		marker: 'triangle'
//                	});
                	var tmp = Number(result[i].err_rate);
                	//if(tmp>1.2)tmp = 1.2;
                    data.push(tmp);
                    errSum(tmp);
                    the_dt.push(result[i].DT);
                }
                else{
                    //只考虑日期升序的情况
                    //日期相差超过1，补齐缺失数据，0代替
                    for(var j=0;j<delta;j++){
                    	//console.log('losts');
                    	//console.log(Date.parse(addDay(result[i].DT,j+1-delta)));
                        data.push(0);
                        losts.push(Date.parse(addDay(result[i].DT,j+1-delta)));
                        the_dt.push(addDay(result[i].DT,j+1-delta));
                    }
                }
            }
          dt['masterFrom'] = Date.parse(the_dt[0]);
          dt['masterTo'] = Date.parse(the_dt[the_dt.length-1]);
          dt['detailStart'] = Date.parse(addDay(dt['masterTo'], -30));
          //console.log(data);
            // create master and in its callback, create the detail chart
            createMaster(data);
            createErrSumChart();
            errCDF();
        });

    });
}

// create the detail chart
function createDetail(masterChart) {
    // prepare the detail chart
    var detailData = [];
        //detailStart = Date.UTC(2008, 7, 1);
    var detailStart = dt['detailStart'];
    console.log(new Date(detailStart));
    var masterData = masterChart.series[0].data;
    //console.log(masterChart);
    var i = 0;
    $.each(masterData, function (index) {
        if (this.x >= detailStart) {
        	detailData.push({x:Date.parse(addDay(detailStart,i)),y:this.y});
        	i++;
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
            text: '测点 ' + point.val().split('#')[1] + ' 分量 ' + $("#comp_sel option:selected").text()
        },
        subtitle: {
            text: '拖动下方区域查看详细信息'
        },
        xAxis: {
           type: 'datetime',
            /*tickInterval: 7*24*3600*1000,*/
            labels:{
                formatter: function(){
                    var day = new Date(this.value);
                    console.log(day);
                    return Highcharts.dateFormat("%Y-%m-%d",this.value);
//                    return day.getFullYear()+ '-' + (day.getMonth()+1) + '-' + day.getDate();
                },
                step: 1
            }
        },
        yAxis: {
            title: {
                text: null
            },
            maxZoom: 0.1,
            min: 0,
            labels:{
                formatter: function(){
                    return this.value*100 + '%';
                }
            }
        },
        tooltip: {
            formatter: function () {
                var point = this.points[0];
                return '<b>' + point.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%Y-%m-%d', this.x) + ':<br/>' +
                    Highcharts.numberFormat(point.y*100, 2) + "%";
            },
            shared: true
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                marker: {
                    enabled: true,
                    radius: 1,
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
            name: '相对误差',
            //pointInterval: 24 * 3600 * 1000,
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
                                filename: 'err-png'
                            });
                        }
                    },{
                        text: '导出JPEG',
                        onclick: function () {
                            this.exportChart({
                                type: 'image/jpeg',
                                filename: 'err-jpeg'
                            });
                        }
                    },{
                        text: '导出PDF',
                        onclick: function () {
                            this.exportChart({
                                type: 'application/pdf',
                                filename: 'err-pdf'
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
                    $.each(this.series[0].data, function (index) {
                        if (this.x > min && this.x < max) {
                            if(losts.indexOf(this.x)>=0){
                            	detailData.push({x:this.x,y:this.y,/*color:'red'*/marker:{symbol: 'url(../css/img/smer.png)'}});
                            }
                            else{
                            	detailData.push([this.x, this.y]);
                            }
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
            name: '误差全序列',
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
    var day = new Date(date);
    day.setDate(day.getDate()+n);
    var result = day.getFullYear() + '-';
    //return  + '-' + (day.getMonth()+1) + "-" +
    //确保格式为0000-00-00
    if(day.getMonth()<9)
        result += '0';
    result += (day.getMonth()+1) + "-";

    if(day.getDate()<10)
        result += '0';
    result += day.getDate();
    return result;
}

//误差统计
var err_series = {};
function initErrSum(){
	err_sum = {};
	for(var i=0;i<13;i++){
		err_sum['level' + i] = 0;
	}
	/*err_series['level0'] = '0~10%';err_series['level1'] = '10%~20%';
	err_series['level2'] = '20%~30%';err_series['level3'] = '30%~40%';
	err_series['level4'] = '40%~50%';err_series['level5'] = '50%~60%';
	err_series['level6'] = '60%~70%';err_series['level7'] = '70%~80%';
	err_series['level8'] = '80%~90%';err_series['level9'] = '90%~100%';
	err_series['level10'] = '>100%';*/
	err_series['level0'] = '0~2.5%';err_series['level1'] = '2.5%~5%';
	err_series['level2'] = '5%~10%';err_series['level3'] = '10%~20%';
	err_series['level4'] = '20%~30%';err_series['level5'] = '30%~40%';
	err_series['level6'] = '40%~50%';err_series['level7'] = '50%~60%';
	err_series['level8'] = '60%~70%';err_series['level9'] = '70%~80%';
	err_series['level10'] = '80%~90%';err_series['level11'] = '90%~100%';
	err_series['level12'] = '>100%';
}
function errSum(e){
	var err = e * 100;
	/*if(err<10)err_sum['level0']++;
	else if(err<20)err_sum['level1']++;
	else if(err<30)err_sum['level2']++;
	else if(err<40)err_sum['level3']++;
	else if(err<50)err_sum['level4']++;
	else if(err<60)err_sum['level5']++;
	else if(err<70)err_sum['level6']++;
	else if(err<80)err_sum['level7']++;
	else if(err<90)err_sum['level8']++;
	else if(err<100)err_sum['level9']++;
	else err_sum['level10']++;*/
	if(err<2.5)err_sum['level0']++;
	else if(err<5)err_sum['level1']++;
	else if(err<10)err_sum['level2']++;
	else if(err<20)err_sum['level3']++;
	else if(err<30)err_sum['level4']++;
	else if(err<40)err_sum['level5']++;
	else if(err<50)err_sum['level6']++;
	else if(err<60)err_sum['level7']++;
	else if(err<70)err_sum['level8']++;
	else if(err<80)err_sum['level9']++;
	else if(err<90)err_sum['level10']++;
	else if(err<100)err_sum['level11']++;
	else err_sum['level12']++;
}

var err_cdf = [];
var err_cdf_cate = ['0','2.5%','5%','10%','20%','30%','40%','50%','60%','70%','80%','90%','100%','>100%'];
function errCDF(){
	var tmp = [];
	$.each(err_sum,function(key,val){
		tmp.push(val);
	});
	err_cdf[0] = 0;
	err_cdf[1] = tmp[0];
	for(var i=1;i<tmp.length;i++){
		err_cdf[i+1] = err_cdf[i] + tmp[i];
	}
	var total = err_cdf[err_cdf.length-1];
	var _cdf = 	$.map(err_cdf,function(ele){return ele/total});
	console.log(_cdf);
	createErrCDFChart(_cdf);
}

//误差累积分布图
function createErrCDFChart(err_cdf){
	$("#err_cdf").highcharts({
		chart: {
            type: 'areaspline'
        },
        title: {
        	useHTML : true,
			text : '<b style="font-size:15px">误差累积分布图</b>'
        },
        credits: {
        	enabled: false
        },
        legend: {
        	enabled: false
        },
        xAxis : {
        	title: {
                text: '相对误差',
            },
        	categories:err_cdf_cate
		},
		yAxis : {
			labels: {
                formatter : function(){
	            	return Highcharts.numberFormat((this.value)*100,0) + "%";
	            }
            },
			title: {
                text: '百分比',
            },
            max: 1
            /*plotLines: [{
                color: Highcharts.getOptions().colors[0],
                width: 2,
                value: 1,
                dashStyle: 'ShortDash',
                label: {
                	align: 'right',
                	y: 16,
                    style: {
                        color: Highcharts.getOptions().colors[0],
                        fontWeight: 'bold'
                    }
                }
            }],*/
		},
		series: [{
            name: '累积分布',
            data: err_cdf,
            marker:{
            	radius:2
            }
        }],
        tooltip:{
        	formatter : function(){
        		var yVal = Highcharts.numberFormat(this.y*100,2) + '%';
//        		return yVal + "的计算结果相对误差小于" + this.x;
        		return "相对误差小于" + this.x + "的计算结果占" + yVal;
            },
        },
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
}

//误差统计图
function createErrSumChart(){
	var bardata = [];
	var cate = [];
	var piedata = [];
	for(var i=0;i<11;i++){
		if(err_sum['level' + i]==0)continue;
		bardata.push(err_sum['level' + i]);
		cate.push(err_series['level' + i]);
		if(i==1){
			piedata.push({
				name: err_series['level' + i],
				y: err_sum['level' + i],
				sliced: true,
				selected: true
			});
		}else{
			piedata.push({
				name: err_series['level' + i],
				y: err_sum['level' + i]
			});
		}
	}
	$("#err_sum_bar").highcharts({
		credits : {
			enabled: false
		},
		title : {
			useHTML : true,
			text : '<b style="font-size:15px;">误差统计柱状图</b>'
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
	
	$("#err_sum_pie").highcharts({
		credits : {
			enabled: false
		},
		title : {
			useHTML : true,
			text : '<b style="font-size:15px;">误差统计饼图</b>'
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
//			center: ['50%', '50%'],
			size: 180,
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
}