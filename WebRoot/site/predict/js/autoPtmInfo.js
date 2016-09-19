/**
 * @author qihai
 * @time 2016年5月19日11:13:19
 * 自动预处理查询
 */

$(function(){
	initPointSel();
	//按钮事件
	
	$("#query_point").click(function(){
		if($("#point_sel").val().length==0){
			alert("请选择测点！");
			return;
		}
		//showcdData();
	});
	
	showAutoPtmInfo();
});

//初始化测点选择
function initPointSel(){
	getBasicItem();
	basic.on('change',getMonitor);
	monitor.on('change',getInstrType);
	instrType.on('change',getObservePoints);
	point.on('change',getComponent);
}

//对比图
function showAutoPtmInfo(){
	$.getJSON("getPreHandleResult2",function(result){
		var original = [];
		var preHandle = [];
		$.each(result,function(index,element){
			original.push([Date.parse(element.DT),Number(element.original)]);
			preHandle.push([Date.parse(element.DT),Number(element.preHandle)]);
		});
		console.log(original);
		console.log(preHandle);
		spline(original,preHandle,'container');
	});
}

//绘制spline
function spline(data1,data2,id){
	var theSeries = [];
	/*theSeries.push({
		name: '相对误差',
		type:'column',
		tooltip: {
			formatter: function () {
                return numpercent(this.value);
            }
		},
		yAxis: 1,
		data: err_rate.sort()});
	console.log(err_rate.sort());*/
	theSeries.push({name: '原始值',data: data1,marker:{enabled:true,radius:1}});
	theSeries.push({name: '预处理值',data: data2,marker:{enabled:true,radius:1}});
	console.log(theSeries);
	Highcharts.setOptions({
		lang: {
	        resetZoom: '重置',
	        resetZoomTitle:"重置缩放比例"
		}
	});
	$("#"+id).highcharts({
		chart: {
            type: 'spline',
            zoomType: 'x'
        },
        title: {
        	text: 'C4-A22-PL-01 横河向位移'//title[0]
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
                //rotation: -45,
                formatter: function(){
                	var day = new Date(this.value);
                	return day.getFullYear() + '-' + (day.getMonth()+1) + '-' + day.getDate();
                }
            }
            
		},
		yAxis : {
			labels: {
                format: '{value}',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            title: {
                text: '测值 ',//单位('+ title[1] + ')
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            }
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
}

var dt = {};
var losts = {};
//var the_dt = [];
var detailChart = {};
var exportUrl = 'http://121.248.200.5:8000/highcharts-export';
//目前仅支持单侧点的查询
function showcdData(){
	var _point_sel = $("#point_sel").val();
	console.log(_point_sel);
//	var tb = '';
	var tables = _point_sel.split('#')[0];
	var points = _point_sel.split('#')[1];
//	$.each(_point_sel,function(index,element){
//		var tmp = element.split('#');
//		if(tb==tmp[0]){
//			//points[tb] += ',' + "'" + tmp[1] + "'";
//			var po = points.pop();
//			points.push(po + ',' + "'" + tmp[1] + "'");
//		}else{
//			tb = tmp[0];
//			tables.push(tb);
//			//points[tb] = "'" + tmp[1] + "'";
//			points.push("'" + tmp[1] + "'");
//		} 
//	});
	console.log(tables);
	console.log(points);
//	$no_select = $("#point_Sel");
//	var tableName = null;
//	if($("#dec_Sel").val().indexOf(",")>=0)
//		tableName = $("#dec_Sel").val().split(",")[1];
//	else
//		tableName = $("#dec_Sel").val() + "";
//	console.log("tableName = " + tableName);
//	console.log("point = " + $no_select.val());
//	$no_select.val()的类型并不是字符串，所以需要进行转型
//	alert(typeof $no_select.val());
	$("#query_point_result").show();
	
	$.getJSON("getPreHandleResultByPoint",{point:points,tableName:tables}, function(result){
//		$.getJSON("getPreHandleResultByPoint",{point:points.join('#'),tableName:tables.join('#')}, function(result){
//		console.log(JSON.stringify(result));
		if(result.length==0){
			$("#predataResult_cd").html("<strong style='font-size:18px;margin-left:18px;'>当前所选测点没有数据，请重新选择！<strong>");
			return;
		}
		$.each(result[0],function(key,val){
			console.log(key);
		    var rval = eval(val);
		    var the_dt = [];
		    var data = [];
		    var lost = [];
	        the_dt.push(rval[0].DT);
	        data.push(Number(rval[0].preHandle));
		    for(var i=1;i<rval.length;i++){
		    	var delta = diffDate(rval[i-1].DT,rval[i].DT);
	            if(delta==0){
	                //日期已经存在，pass
	            }
	            else if(delta==1){
	                //日期刚好，go
	            	var tmp = Number(rval[i].preHandle);
	                data.push(tmp);
	                the_dt.push(rval[i].DT);
	            }
	            else{
	                //只考虑日期升序的情况
	                //日期相差超过1，补齐缺失数据，0代替
	                for(var j=0;j<delta;j++){
	                    data.push(0);
	                    lost.push(Date.parse(addDay(rval[i].DT,j+1-delta)));
	                    the_dt.push(addDay(rval[i].DT,j+1-delta));
	                }
	            }
		    }
		    losts[key] = lost;
		    dt['masterFrom_' + key] = Date.parse(the_dt[0]);
		    dt['masterTo_'+ key] = Date.parse(the_dt[the_dt.length-1]);
		    dt['detailStart_'+ key] = Date.parse(addDay(the_dt[the_dt.length-1], -30));
		    console.log(data);
		    console.log(dt);
		    createMaster(data,key);
		});
		return;
		/*var data = [];
        the_dt.push(result[0].DT);
        data.push(Number(result[0].preHandle));
        for (var i = 1; i < result.length; i++) {
            var delta = diffDate(result[i-1].DT,result[i].DT);
            if(delta==0){
                //日期已经存在，pass
            }
            else if(delta==1){
                //日期刚好，go
            	var tmp = Number(result[i].preHandle);
                data.push(tmp);
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
      dt['detailStart'] = Date.parse(addDay(the_dt[the_dt.length-1], -30));
      console.log(data);
      console.log(dt);
      //create master and in its callback, create the detail chart
      createMaster(data,'R1');
        */
		
		/*var datatable = new jStringBuffer();
		datatable.append("<table class='table table-striped table-hover' style='font-size:12px;'>");
		datatable.append("<thead><tr><th>序号</th><th>时间</th><th>描述</th><th>状态</th><th>操作</th></tr></thead>");
		for(var i=0;i<result.length;i++){
			datatable.append("<tr><td id='").append(result[i].id).append("'>").append((i+1)).append("</td><td>").append(result[i].dt).append("</td><td>")
			.append(result[i].resultdiscript).append("</td><td>").append(result[i].statu == 1 ? "处理完成</td><td><div><a title='查看' href='goPreHandle.jsp?tableTag=2&preId="+result[i].id+"'>查看" : "正在处理</td><td><div><a title='继续处理' href='goPreHandle.jsp?tableTag=1&preId="+result[i].id+"'>继续处理").
			append("</td></tr>");
		}
		datatable.append('</table>');
		$("#predataResult_cd").html("");
		$("#predataResult_cd").html(datatable.toString());*/
		
	});
};

//create the detail chart
function createDetail(masterChart,comp) {
    // prepare the detail chart
    var detailData = [],
        detailStart = dt['detailStart_'+comp];
    var masterData = masterChart.series[0].data;
    $.each(masterData, function () {
        if (this.x >= detailStart) {
        	detailData.push(this.y);
        }
    });

    // create a detail chart referenced by a global variable
    detailChart[comp] = $('#detail-container_' + comp).highcharts({
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
            text: '测点：' + ($("#point_sel").val().toString()).split("#")[1] + ' 分量：'+comp
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
            maxZoom: 0.1,
            labels:{
                formatter: function(){
                    return this.value;
                }
            }
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
            name: '预处理值',
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
function createMaster(data,comp) {
    $('#master-container_'+comp).highcharts({
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
                            if(losts[comp].indexOf(this.x)>=0){
                            	detailData.push({x:this.x,y:this.y,marker:{symbol: 'url(../css/img/smer.png)'}});
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
                        from: dt['masterFrom_'+comp],
                        to: min,
                        color: 'rgba(0, 0, 0, 0.2)'
                    });

                    xAxis.removePlotBand('mask-after');
                    xAxis.addPlotBand({
                        id: 'mask-after',
                        from: max,
                        //to: Date.UTC(2008, 11, 31),
                        to: dt['masterTo_'+comp],
                        color: 'rgba(0, 0, 0, 0.2)'
                    });


                    detailChart[comp].series[0].setData(detailData);
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
                from: dt['masterFrom_'+comp],
                to: dt['detailStart_'+comp],
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
            name: '预处理值',
            pointInterval: 24 * 3600 * 1000,
            //pointStart: Date.UTC(2006, 0, 1),
            pointStart: dt['masterFrom_'+comp],
            data: data
        }],

        exporting: {enabled: false}

    }, function (masterChart) {
        createDetail(masterChart,comp);
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

    if(day.getDate()<9)
        result += '0';
    result += day.getDate();
    return result;
}
