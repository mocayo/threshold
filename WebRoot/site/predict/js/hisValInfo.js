/**
 * 历史特征值配置
 */
var pageindex = 1;
var pagesize = 10;
var pagemax = 0;
var total = 0;
var lsinfo = [];

var exportUrl = 'http://http://121.248.200.5:8000/highcharts-export';

$(function() {
	$.getJSON("getLSTZZInfoCount",function(result,status){
		if(status=="success"){
			total = Number(result);
			pagemax = parseInt((total-1)/pagesize) + 1;
		}
	});

	showLS();
	
	$("#tabs a").eq(0).click(function(e){
		e.preventDefault();
		$("#tabs li").attr("class", "");
        $("#tabs li").eq(0).attr("class", "active");
//        $("#tab_head").append();
        $("#lsinfo").show();
        $("#lschart").hide();
	});
	$("#tabs a").eq(1).click(function(e){
		e.preventDefault();
		$("#tabs li").attr("class", "");
		$("#tabs li").eq(1).attr("class", "active");
		$("#lsinfo").hide();
		$("#lschart").show(function(){
			boxplot();
		});
	});
	
});

function showLS(){
	$.getJSON("getLSTZZInfo",{pageindex:pageindex},function(result,status){
		if(status=="success"){
			var data = "";
			for(var i=0;i<result.length;i++){
				lsinfo[i] = result[i];
				data += "<tr>";
				data += "<td>" + ((pageindex-1) * pagesize + i+1) + "</td>";
				data += "<td>" + result[i].instr_no + "</td>";
				data += "<td style='text-align:right;'>" + numFix(result[i].maxV,6) + "</td>";
				data += "<td style='text-align:right;'>" + numFix(result[i].minV,6) + "</td>";
				data += "<td style='text-align:right;'>" + numFix(result[i].avgV,6) + "</td>";
				data += "<td style='text-align:right;'>" + numFix(result[i].maxAV,6) + "</td>";
				data += "<td style='text-align:right;'>" + numFix(result[i].minAV,6) + "</td>";
				data += "</tr>";
			}
			$("#ls_table").html("");
			$("#ls_table").html(data);
			
			//分页按钮
			var $page = "";
			$page += "<span style='display:block;margin-top:10px;float:right'>";
			$page += "<strong>第"+ pageindex + "/" + pagemax + "页，共"+ total + "条记录</strong>&nbsp;";
			$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='firstpage()'>首页</botton>&nbsp;";
			$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='prepage()'>上一页</botton>&nbsp;";
			$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='nextpage()'>下一页</botton>";
			$page += "&nbsp;<input type='text' id='topage'style='height:20px;width:35px;valign:bottom;line-height=23px;text-indent:3px;font-size:10px;'/>&nbsp;";
			$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='gotopage()'>GO</botton>&nbsp;";
			$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='lastpage()'>末页</botton></span>";
			$("#page_ctrl").html($page);
		}else{
			$("#ls_table").html("sorry...暂无数据");
		}
	});
}

//第一页
function firstpage(){
	pageindex = 1;
	showLS();
}

//末页
function lastpage(){
	pageindex = pagemax;
	showLS();
}

//上一页
function prepage(){
	if(pageindex==1){
//		alert("已经是第一页！");
		return;
	}
	pageindex --;
	showLS();
}

//下一页
function nextpage(){
	pageindex ++;
	if(pageindex > pagemax){
		pageindex = pagemax;
	}
	showLS();
}

//跳转到
function gotopage(){
	var $topage = parseInt($("#topage").val());
	if($topage>pagemax){
		$("#topage").val(pagemax);
	}
	pageindex = $topage;
	showLS();
}

//n位小数
function numFix(num,n){
	return Number(num).toFixed(n);
}

//箱线图
function boxplot(){
	var chardata = [];
	var cate = [];
	for(var i=0;i<lsinfo.length;i++){
		var nums = [Number(lsinfo[i].minV),Number(lsinfo[i].minAV),
		            Number(lsinfo[i].avgV),Number(lsinfo[i].maxAV),
		            Number(lsinfo[i].maxV)];
		chardata[i] = nums;
		cate[i] = $.trim(lsinfo[i].instr_no);
	}
//	console.log(JSON.stringify(chardata));
	 $('#lschart #container').highcharts({

	        chart: {
	            type: 'boxplot'
	        },
	        
			credits : {
				text : '',
				href : '#'
			},
	        
	        title: {
	            text: '历史特征值'
	        },

	        legend: {
	            enabled: false
	        },

	        xAxis: {
//	        	categories: ['1', '2', '3', '4', '5'],
	            categories: cate,
	            title: {
	                text: '测点'
	            }
	        },

	        yAxis: {
	            title: {
	                text: '特征值'
	            },
//	            plotLines: [{
//	                value: 932,
//	                color: 'red',
//	                width: 1,
//	                label: {
//	                    text: 'Theoretical mean: 932',
//	                    align: 'center',
//	                    style: {
//	                        color: 'gray'
//	                    }
//	                }
//	            }]
	        },
	        tooltip : {
				crosshairs : true,
	            formatter : function(){
	            	console.log(this.points);
	            	var s = '';
	            	s += '<b>' + this.points[0].key + "</b><br>";
	            	s += '最大值：' +  this.points[0].point.high + '<br>';
	            	s += '大值平均值：' +  this.points[0].point.q3 + '<br>';
	            	s += '平均值：' +  this.points[0].point.median + '<br>';
	            	s += '小值平均值：' +  this.points[0].point.q1 + '<br>';
	            	s += '最小值：' +  this.points[0].point.low + '<br>';
	                return s;
	            },
				shared : true
			},
	        series: [{
	            name: '特征值',
//	            data: [
//	                [760, 801, 848, 895, 965],
//	                [733, 853, 939, 980, 1080],
//	                [714, 762, 817, 870, 918],
//	                [724, 802, 806, 871, 950],
//	                [834, 836, 864, 882, 910]
//	            ],
	            data: chardata,
	            tooltip: {
	                headerFormat: '<b>{point.key}</b><br/>'
	            }
	        },
//	        {
//	            name: 'Outlier',
//	            color: Highcharts.getOptions().colors[0],
//	            type: 'scatter',
//	            data: [ // x, y positions where 0 is the first category
//	                [0, 644],
//	                [1, 718],
//	                [2, 951],
//	                [3, 969],
//	                [4, 969]
//	            ],
//	            marker: {
//	                fillColor: 'white',
//	                lineWidth: 1,
//	                lineColor: Highcharts.getOptions().colors[0]
//	            },
//	            tooltip: {
//	                pointFormat: 'Observation: {point.y}'
//	            }
//	        }
	        ],
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
	        			            filename: 'ls-png'
	        					});
	        				}
	        			},{
	        				text: '导出JPEG',
	        				onclick: function () {
	        					this.exportChart({
	        						type: 'image/jpeg',
	        			            filename: 'ls-jpeg'
	        					});
	        				}
	        			},{
	        				text: '导出PDF',
	        				onclick: function () {
	        					this.exportChart({
	        						type: 'application/pdf',
	        			            filename: 'ls-pdf'
	        					});
	        				}
	        			},]
	        		}
	        	}
	        }
	    });
}