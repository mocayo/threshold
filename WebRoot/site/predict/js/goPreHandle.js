/**
 * 初始化. 继续处理
 */
var preId = "";
var tableTag = "";
var sjTypes = "1";
$(function(){
	
	//预处理信息
	var url = window.location.href;
	if (url.indexOf("?") != -1) {
		var str = url.split("?");
		var strs = str[1].split("&");
		tableTag = strs[0].split("=")[1];
		preId = strs[1].split("=")[1];
		
		var heading = '';
		if(tableTag == "1"){
			heading = '<a><i class="fa fa-play"></i>&nbsp; 继续预处理</a>';
		}
		else{
			heading = '<a><i class="fa fa-eye"></i>&nbsp; 查看预处理</a>';
			$("title").html("查看预处理");
		}
		$("#head").html(heading);
		
	    $("#tableTag").val(tableTag);
	    $("#preId").val(preId);
	    showInfo(preId);
	}
	
	//显示继续处理和查看处理的表格
	var $preProcess=$("#preProcess"), $preResult=$("#preResult");
	if (tableTag == "1"){
		$preResult.hide();
		$preProcess.show(function(){
			loadProcPage("");
		});
		
	} else if (tableTag == "2"){
		$preProcess.hide();
		$("#confirm").hide();
		$preResult.show(function(){
			loadResPage("");
		});
	}
	
	$("#allData").click(function(){
		sjTypes = "1";
		if (tableTag == "1")
			loadProcPage("");
		else 
			loadResPage("");
	});
	$("#normData").click(function(){
		sjTypes = "2";
		if (tableTag == "1")
			loadProcPage("");
		else 
			loadResPage("");
	});
	$("#errorData").click(function(){
		sjTypes = "3";
		if (tableTag == "1")
			loadProcPage("");
		else 
			loadResPage("");
	});
	
	$("#confirm").click(function(){
		processConfirm();
	});
});

//显示预处理信息
function showInfo(resultId){
	$.getJSON("getPreHandleInfoByBatchId",{
		batchId:resultId
	},function(result){
		$("#resultTime").html("预处理时间:&nbsp;"+result.dt);
		$("#resultDes").html("自动"+result.resultdiscript);
	  
	});
}

//确认预处理
function processConfirm() {
	if(confirm("是否确认预处理？")){
		//触发模态框并显示进度条
//		$("#active_Modal").trigger('click');
//		progressbar();
		//显示进度条
		$.getJSON("confirmPreHandleBatch", {
			batchId : preId,
		}, function(result) {
			console.log(result == "1");
			if (result) {
//				$("#progress1").html('<div class="progress-bar progress-bar-success" style="width: 40%">');
				window.location.href = 'pretreat.jsp';
			} else {
				alert("确认失败");
			}
		});
	}
}

//需要继续处理的
function loadProcPage(cpage){
	var resultId = preId;
	$.getJSON("getPreHandleResByType",{
		sjTypes : sjTypes,
		resultId : resultId,
		cpage : cpage
	},function(result){
		var pc = result[0];
		$("#totalitem").html(pc.totalitem.toString());
		$("#page_info").html(pc.cpage.toString() + "/" + pc.totalpage.toString());
//		$("#cpage").html("");
//		$("#cpage").append(pc.cpage.toString());
//		$("#totalpage").html("");
		$("#totalpage").val(pc.totalpage.toString());
//		$("#totalpageHID").val(pc.totalpage.toString());
//		$("#previouspage").val(pc.previouspage.toString());
//		$("#nextpage").val(pc.nextpage.toString());
		var pagedate = new jStringBuffer();
		var dealdata = new jStringBuffer();
		/*var obj = eval(result);*/
		var colorStr="";
		var color_r1="";var result_r1;//保存r1现实的颜色和中文解释(正常，异常，未处理)
		var color_r2="";var result_r2;
		var color_r3="";var result_r3;
//		var start = 0;
//		var start = (pc.cpage - 1)* pc.pagesize;
		
//		var maxShowPageNum = 6;
//		var pageIndex = pc.cpage;
//		var pageNum = pc.totalpage;
//		var startPage = 0;
//		var endPage = 0;
//		if ( pageIndex <= maxShowPageNum / 2 ) {
//    		// 如果当前页码小于显示最大页码数的一半
//    		startPage = 1;
//    		if (pageNum <= maxShowPageNum) {
//    			endPage = pageNum+1;
//    		} else {
//    			endPage = maxShowPageNum;
//    		}
//    	} else if ( pageIndex > (pageNum - maxShowPageNum / 2) ) {
//    		// 如果当前页面大于最大页数-显示最大页码数的一半
//    		startPage = pageNum - maxShowPageNum;
//    		endPage = pageNum;
//    	} else {
//    		// 否则，保持当前页码在输出页码数的中间
//    		startPage = pageIndex - maxShowPageNum / 2 + 1;
//    		endPage = pageIndex + maxShowPageNum / 2;
//    	}
//    	if (startPage < 1) startPage = 1;
//    	if (endPage > (pageNum+1)) endPage = (pageNum+1);
//    	
//    	for (var i=startPage; i<endPage; i++) {
//    		if (i == (pageIndex)) {
//    			pagedate.append("<li class='paginItem current'><a href='javascript:(0);' >").append(i).append("&nbsp;</a></li>");
//    		} else {
//    			pagedate.append("<li class='paginItem'><a href='javascript:(0);' onclick='goNextPage(").append(i).append(")'>").append(i).append("&nbsp;</a></li>");
//    		}
//    	}
    	var itemType=result[1];//获取该表项类型
    	var num=0;             //获取该表项数量
    	var table_th=new jStringBuffer();
    	var table_th1= new jStringBuffer();//存放表格的表头
    	var table_th2= new jStringBuffer();//存放表格的表头
    	table_th1.append("<tr>");
    	if(itemType.r1!=="0"){
    		num++;
    		table_th1.append("<th>").append(itemType.r1).append("</th>");
    		table_th2.append("<th>").append(itemType.r1).append("</th>");
    	}
    	if(itemType.r2!=="0"){
    		num++;
    		table_th1.append("<th>").append(itemType.r2).append("</th>");
    		table_th2.append("<th>").append(itemType.r2).append("</th>");
    	}
    	if(itemType.r3!=="0"){
    		num++;
    		table_th1.append("<th>").append(itemType.r3).append("</th>");
    		table_th2.append("<th>").append(itemType.r3).append("</th>");
    	}
    	table_th2.append("</tr>");
    	table_th.append("<tr><th width='8%' style='vertical-align:middle;' rowspan='2'>测点编号</th><th style='vertical-align:middle;' width='6%' rowspan='2'>采集时间</th>")
			.append("<th width='10%'  colspan='").append(num)
			.append("'>实测值（双击修改）</th><th width='10%' colspan='").append(num)
			.append("'>人工干预结果</th></tr>");
    	table_th.append(table_th1).append(table_th2);
    	var temp1 = new jStringBuffer();  //存放与处理结果的td
		var temp2 = new jStringBuffer(); //存放人工干预结果的td
		for(var i = 2; i < result.length; i++){
			temp1.clear();temp2.clear();
			var value_result;                //保存改点该分量处理结果
//			colorStr=(i%2==0)?"#FBFEFF":"#EEF9FF";			    
//			dealdata.append("<tr bgcolor='").append(colorStr)
			dealdata.append("<tr")
				.append("><td>").append($.trim(result[i].instr_no)).append("</td>")
				.append("<td>").append(result[i].dt.substring(0,10)).append("</td>");
			if(itemType.r1!=="0"){
				if(result[i].handle_r1_result==-1){//没有数据
					temp1.append("<td>--</td>");
					temp2.append("<td>--</td>");
				}
				else{
					if(result[i].people_r1_handle!==-1){
						value_result=result[i].people_r1_handle;
						result[i].people_r1_handle==0?color_r1="#FF0000":color_r1="#00CC00";
						result[i].people_r1_handle==0?result_r1="异常":result_r1="正常";
					}
					else{
						value_result=result[i].handle_r1_result;
						result[i].handle_r1_result==0?color_r1="#FF0000":color_r1="#00CC00";
						result_r1="未处理";
					}
					temp1.append("<td").append("><div style='color:"+color_r1+";' ondblclick=\"changeStatus('")
						.append("1','").append($.trim(result[i].instr_no)).append("','").append(result[i].dt)
						.append("','").append(value_result).append("','").append(num)
						.append("','").append(itemType.table_name)
						.append("',this)\">").append($.trim(result[i].r1)).append("</div></td>");
					temp2.append("<td>").append(result_r1).append("</td>");
				}
	    	}
	    	if(itemType.r2!=="0"){
	    		if(result[i].handle_r2_result==-1){//没有数据
					temp1.append("<td>--</td>");
					temp2.append("<td>--</td>");
				}
	    		else{
	    			if(result[i].people_r2_handle!==-1){
		    			value_result=result[i].people_r2_handle;
		    			result[i].people_r2_handle==0?color_r2="#FF0000":color_r2="#00CC00";
		    			result[i].people_r2_handle==0?result_r2="异常":result_r2="正常";
		    		}
					else{
						value_result=result[i].handle_r2_result;
						result[i].handle_r2_result==0?color_r2="#FF0000":color_r2="#00CC00";
						result_r2="未处理";
					}
		    		temp1.append("<td").append("><div style='color:"+color_r2+";' ondblclick=\"changeStatus('")
		    			.append("2','").append($.trim(result[i].instr_no)).append("','").append(result[i].dt)
		    			.append("','").append(value_result).append("','").append(num)
		    			.append("','").append(itemType.table_name)
		    			.append("',this)\">").append($.trim(result[i].r2)).append("</div></td>");
		    		temp2.append("<td>").append(result_r2).append("</td>");
	    		}
	    	}
	    	if(itemType.r3!=="0"){
	    		if(result[i].handle_r3_result==-1){//没有数据
					temp1.append("<td>--</td>");
					temp2.append("<td>--</td>");
				}
	    		else{
	    			if(result[i].people_r3_handle!==-1){
		    			value_result=result[i].people_r3_handle;
		    			result[i].people_r3_handle==0?color_r3="#FF0000":color_r3="#00CC00";
		    			result[i].people_r3_handle==0?result_r3="异常":result_r3="正常";
		    		}
					else{
						value_result=result[i].handle_r3_result;
						result[i].handle_r3_result==0?color_r3="#FF0000":color_r3="#00CC00";
						result_r3="未处理";
					}
		    		temp1.append("<td").append("><div style='color:"+color_r3+";' ondblclick=\"changeStatus('")
		    			.append("3','").append($.trim(result[i].instr_no)).append("','").append(result[i].dt)
		    			.append("','").append(value_result).append("','").append(num)
		    			.append("','").append(itemType.table_name)
		    			.append("',this)\">").append($.trim(result[i].r3)).append("</div></td>");
		    		temp2.append("<td>").append(result_r3).append("</td>");
	    		}
	    	}
	    	dealdata.append(temp1).append(temp2).append("</tr>");
		}
		var $page = "";
		$page += "<span style='display:block;margin-top:10px;float:right'>";
		$page += "<strong>第"+ pc.cpage.toString() + "/" + pc.totalpage.toString() + "页，共"+ pc.totalitem+"条记录</strong>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='prepage(2)'>首页</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='prepage("+ pc.cpage.toString() +")'>上一页</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='nextpage("+ pc.cpage.toString() +")'>下一页</botton>";
		$page += "&nbsp;<input type='text' id='goto-page'style='height:20px;width:50px;valign:bottom;line-height=23px;text-indent:3px;font-size:10px;'/>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='gotopage()'>GO</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='goNextPage("+pc.totalpage.toString()+")'>末页</botton>&nbsp;</span>";

		$("#preProcess").html("");
		$("#preProcess").html((table_th.append(dealdata)).toString());
		$("#pagespan").html($page);
	});
}

//处理完成的
function loadResPage(cpage){
	var resultId = preId;
	$.getJSON("getPreHandleResByType",{
		sjTypes : sjTypes,
		resultId : resultId,
		cpage : cpage
	},function(result){
//		console.log(JSON.stringify(result));
		var pc = result[0];
//		$("#totalitem").html(pc.totalitem.toString());
//		$("#page_info").html(pc.cpage.toString() + "/" + pc.totalpage.toString());
		$("#totalpage").val(pc.totalpage.toString());
//		var pagedate = new jStringBuffer();
		var dealdata = new jStringBuffer();
		/*var obj = eval(result);*/
		var colorStr="";
		var color_r1="";var result_r1;//保存r1现实的颜色和中文解释(正常，异常，未处理)
		var color_r2="";var result_r2;
		var color_r3="";var result_r3;
//		var start = 0;
//		var start = (pc.cpage - 1)* pc.pagesize;
		
//		var maxShowPageNum = 6;
//		var pageIndex = pc.cpage;
//		var pageNum = pc.totalpage;
//		var startPage = 0;
//		var endPage = 0;
//		if ( pageIndex <= maxShowPageNum / 2 ) {
//    		// 如果当前页码小于显示最大页码数的一半
//    		startPage = 1;
//    		if (pageNum <= maxShowPageNum) {
//    			endPage = pageNum+1;
//    		} else {
//    			endPage = maxShowPageNum;
//    		}
//    	} else if ( pageIndex > (pageNum - maxShowPageNum / 2) ) {
//    		// 如果当前页面大于最大页数-显示最大页码数的一半
//    		startPage = pageNum - maxShowPageNum;
//    		endPage = pageNum;
//    	} else {
//    		// 否则，保持当前页码在输出页码数的中间
//    		startPage = pageIndex - maxShowPageNum / 2 + 1;
//    		endPage = pageIndex + maxShowPageNum / 2;
//    	}
//    	if (startPage < 1) startPage = 1;
//    	if (endPage > (pageNum+1)) endPage = (pageNum+1);
//    	
//    	for (var i=startPage; i<endPage; i++) {
//    		if (i == (pageIndex)) {
//    			pagedate.append("<li class='paginItem current'><a href='javascript:(0);' >").append(i).append("&nbsp;</a></li>");
//    		} else {
//    			pagedate.append("<li class='paginItem'><a href='javascript:(0);' onclick='goNextPage(").append(i).append(")'>").append(i).append("&nbsp;</a></li>");
//    		}
//    	}
    	var itemType=result[1];//获取该表项类型
    	var num=0;             //获取该表项数量
    	var table_th=new jStringBuffer();
    	var table_th1= new jStringBuffer();//存放表格的表头
    	var table_th2= new jStringBuffer();//存放表格的表头
    	table_th1.append("<tr>");
    	if(itemType.r1!=="0"){
    		num++;
    		table_th1.append("<th>").append(itemType.r1).append("</th>");
    		table_th2.append("<th>").append(itemType.r1).append("</th>");
    	}
    	if(itemType.r2!=="0"){
    		num++;
    		table_th1.append("<th>").append(itemType.r2).append("</th>");
    		table_th2.append("<th>").append(itemType.r2).append("</th>");
    	}
    	if(itemType.r3!=="0"){
    		num++;
    		table_th1.append("<th>").append(itemType.r3).append("</th>");
    		table_th2.append("<th>").append(itemType.r3).append("</th>");
    	}
    	table_th2.append("</tr>");
    	table_th.append("<tr><th width='8%' rowspan=2 style='vertical-align:middle;'>测点编号</th><th style='vertical-align:middle;' width='6%' rowspan='2'>采集时间</th>")
			.append("<th width='10%' colspan='").append(num)
			.append("'>实测值</th><th width='8%' colspan='").append(num)
			.append("'>人工干预结果</th></tr>");
    	table_th.append(table_th1).append(table_th2);
    	var temp1 = new jStringBuffer();  //存放与处理结果的td
		var temp2 = new jStringBuffer(); //存放人工干预结果的td
		for(var i = 2; i < result.length; i++){
			temp1.clear();temp2.clear();
			var value_result;                //保存改点该分量处理结果
//			colorStr=(i%2==0)?"#FBFEFF":"#EEF9FF";			    
//			dealdata.append("<tr bgcolor='").append(colorStr)
			dealdata.append("<tr")
				.append("><td>").append($.trim(result[i].instr_no)).append("</td>")
				.append("<td>").append(result[i].dt.substring(0,10)).append("</td>");
			if(itemType.r1!=="0"){
				if(result[i].handle_r1_result==-1){//没有数据
					temp1.append("<td>--</td>");
					temp2.append("<td>--</td>");
				}
				else{
					if(result[i].people_r1_handle!==-1){
						value_result=result[i].people_r1_handle;
						result[i].people_r1_handle==0?color_r1="#FF0000":color_r1="#00CC00";
						result[i].people_r1_handle==0?result_r1="异常":result_r1="正常";
					}
					else{
						value_result=result[i].handle_r1_result;
						result[i].handle_r1_result==0?color_r1="#FF0000":color_r1="#00CC00";
						result_r1="未处理";
					}
					temp1.append("<td").append("><div style='color:"+color_r1+";'>").
						append($.trim(result[i].r1)).append("</div></td>");
					temp2.append("<td>").append(result_r1).append("</td>");
				}
				
	    	}
	    	if(itemType.r2!=="0"){
	    		if(result[i].handle_r2_result==-1){//没有数据
					temp1.append("<td>--</td>");
					temp2.append("<td>--</td>");
				}
	    		else{
	    			if(result[i].people_r2_handle!==-1){
		    			value_result=result[i].people_r2_handle;
		    			result[i].people_r2_handle==0?color_r2="#FF0000":color_r2="#00CC00";
		    			result[i].people_r2_handle==0?result_r2="异常":result_r2="正常";
		    		}
					else{
						value_result=result[i].handle_r2_result;
						result[i].handle_r2_result==0?color_r2="#FF0000":color_r2="#00CC00";
						result_r2="未处理";
					}
//		    		temp1.append("<td").append("><div style='color:"+color_r2+";' >").
//		    			append($.trim(result[i].r2)).append("</div></td>");
//		    		temp2.append("<td>").append(result_r2).append("</td>");
	    		}
//	    		if(result[i].people_r2_handle!==-1){
//	    			value_result=result[i].people_r2_handle;
//	    			result[i].people_r2_handle==0?color_r2="#FF0000":color_r2="#00CC00";
//	    			result[i].people_r2_handle==0?result_r2="异常":result_r2="正常";
//	    		}
//				else{
//					value_result=result[i].handle_r2_result;
//					result[i].handle_r2_result==0?color_r2="#FF0000":color_r2="#00CC00";
//					result_r2="未处理";
//				}
	    		temp1.append("<td").append("><div style='color:"+color_r2+";' >").
	    			append($.trim(result[i].r2)).append("</div></td>");
	    		temp2.append("<td>").append(result_r2).append("</td>");
	    	}
	    	if(itemType.r3!=="0"){
	    		if(result[i].handle_r3_result==-1){//没有数据
					temp1.append("<td>--</td>");
					temp2.append("<td>--</td>");
				}
	    		else{
	    			if(result[i].people_r3_handle!==-1){
		    			value_result=result[i].people_r3_handle;
		    			result[i].people_r3_handle==0?color_r3="#FF0000":color_r3="#00CC00";
		    			result[i].people_r3_handle==0?result_r3="异常":result_r3="正常";
		    		}
					else{
						value_result=result[i].handle_r3_result;
						result[i].handle_r3_result==0?color_r3="#FF0000":color_r3="#00CC00";
						result_r3="未处理";
					}
		    		temp1.append("<td").append("><div style='color:"+color_r3+";'>").
		    			append($.trim(result[i].r3)).append("</div></td>");
		    		temp2.append("<td>").append(result_r3).append("</td>");
	    		}
	    	}
	    	dealdata.append(temp1).append(temp2).append("</tr>");
		}
		$("#preResult").show().html("").html((table_th.append(dealdata)).toString());
		$("#preProcess").hide();
		var $page = "";
		$page += "<span style='display:block;margin-top:10px;float:right'>";
		$page += "<strong>第"+ pc.cpage.toString() + "/" + pc.totalpage.toString() + "页，共"+ pc.totalitem+"条记录</strong>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='prepage(2)'>首页</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='prepage("+ pc.cpage.toString() +")'>上一页</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='nextpage("+ pc.cpage.toString() +")'>下一页</botton>";
		$page += "&nbsp;<input type='text' id='goto-page'style='height:20px;width:50px;valign:bottom;line-height=23px;text-indent:3px;font-size:10px;'/>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='gotopage()'>GO</botton>&nbsp;";
		$page += "<botton class='btn btn-outline btn-info btn-xs' onclick='goNextPage("+pc.totalpage.toString()+")'>末页</botton>&nbsp;</span>";
		$("#pagespan").html($page);
	});
}

//保留测值后小数点后n位小数
function numformat(num,n){
	return (Number(num).toFixed(n));
}

//下一页
function nextpage(str){
	var index = parseInt(str)+1;
	var total = parseInt($("#totalpage").val());
	if(index>total){
		return;
	}
	goNextPage(index);
}

//上一页
function prepage(str){
	var index = parseInt(str)-1;
	if(index==0){
		return;
	}
	goNextPage(index);
}

//goto页
function gotopage(){
	var index = $("#goto-page").val();
	var total = parseInt($("#totalpage").val());
	if(index > total){
		$("#goto-page").val(total);
		return;
	}
	goNextPage(index);
}

function goNextPage(str) {
//	var tableTag = tableTag;
//	if (str == "ToPage") {
//		var toPage = $("#textfield").val();
//		var totalpage = $("#totalpage").val();
//		if (toPage > totalpage) {
//			toPage = totalpage;
//		}
//		if (tableTag == "1") {
//			loadProcPage(toPage);
//		} else if (tableTag == "2") {
//			loadResPage(toPage);
//		}
//	} else if (str == parseInt(str)) {
		if (tableTag == "1") {
			loadProcPage(str);
		} else if (tableTag == "2") {
			loadResPage(str);
		}
//	} else {
//		var page = $("#" + str).val();
//		if (tableTag == "1") {
//			loadProcPage(page);
//		} else if (tableTag == "2") {
//			loadResPage(page);
//		}
//	}
}

//测点分量，测点ID，时间，测点分量对应预测结果，该类型分量个数，表名,this
function changeStatus(value_type,instr_no,dt,value_result,num,tableName,obj) {  
	var modify_result;
	if(value_result==="1")
		modify_result="0";
	else
		modify_result="1";
	var resultId = $("#preId").val();
	$.get("modifyPreHandleRes",{
		value_type:value_type,
		instr_no:instr_no,
		dt:dt,
		resultId:resultId,
		tableName:tableName,
		modify_result:modify_result
		},function(result){
		if(result){
			alert("设置成功");
			if(modify_result==="0"){
				$(obj).css("color","#FF0000");
				var chandgeString=$(obj).attr("ondblclick").split(",");
				$(obj).attr("ondblclick",
					chandgeString[0]+","+chandgeString[1]+","+chandgeString[2]+",'"+modify_result+"',"+chandgeString[4]+","+chandgeString[5]+","+chandgeString[6]);
				$(obj).parent().nextAll().eq(num-1).text("异常");
			}
			else{
				$(obj).css("color","#00CC00");
				var chandgeString=$(obj).attr("ondblclick").split(",");
				$(obj).attr("ondblclick",
					chandgeString[0]+","+chandgeString[1]+","+chandgeString[2]+",'"+modify_result+"',"+chandgeString[4]+","+chandgeString[5]+","+chandgeString[6]);
				$(obj).parent().nextAll().eq(num-1).text("正常");
			}
		}
		else
			alert("错误！");
	});
}

//进度条
function progressbar() {
	jQuery.fn.anim_progressbar = function(aOptions) {
		// def values
		var iCms = 1000;
		var iMms = 60 * iCms;
		var iHms = 3600 * iCms;
		var iDms = 24 * 3600 * iCms;

		// def options
		var aDefOpts = {
			start : new Date(), // now
			finish : new Date().setTime(new Date().getTime() + 60 * iCms), // now
			// + 60
			// sec
			interval : 100
		};
		var aOpts = jQuery.extend(aDefOpts, aOptions);
		var vPb = this;

		// each progress bar
		return this
				.each(function() {
					var iDuration = aOpts.finish - aOpts.start;

					// calling original progressbar
					$(vPb).children('.pbar').progressbar();

					// looping process
					var vInterval = null;
					vInterval = setInterval(
							function() {
								var iLeftMs = aOpts.finish - new Date(); // left
								// time
								// in
								// MS
								var iElapsedMs = new Date() - aOpts.start, // elapsed
								// time
								// in
								// MS
								iDays = parseInt(iLeftMs / iDms), // elapsed
								// days
								iHours = parseInt((iLeftMs - (iDays * iDms))
										/ iHms), // elapsed hours
								iMin = parseInt((iLeftMs - (iDays * iDms) - (iHours * iHms))
										/ iMms), // elapsed minutes
								iSec = parseInt((iLeftMs - (iDays * iDms)
										- (iMin * iMms) - (iHours * iHms))
										/ iCms), // elapsed seconds
								iPerc = (iElapsedMs > 0) ? iElapsedMs
										/ iDuration * 100 : 0; // percentages

								// display current positions and progress
								$(vPb).children('.percent').html(
										'<b>' + iPerc.toFixed(1) + '%</b>');
								$(vPb).children('.elapsed').html(iHours + 'h:' + iMin
												+ 'm:' + iSec + 's</b>');
								$(vPb).children('.pbar').children(
										'.ui-progressbar-value').css('width',
										iPerc + '%');

								// in case of Finish
								if (iPerc >= 100) {
									clearInterval(vInterval);
									$(vPb).children('.percent').html(
											'<b>100%</b>');
									$(vPb).children('.elapsed')
											.html('Finished');
								}
							}, aOpts.interval);
				});
	};

	// default mode
	$('#progress1').anim_progressbar();

	// from second #5 till 15
	var iNow = new Date().setTime(new Date().getTime() + 5 * 1000); // now plus
	// 5 secs
	var iEnd = new Date().setTime(new Date().getTime() + 15 * 1000); // now
	// plus
	// 15
	// secs
	$('#progress2').anim_progressbar({
		start : iNow,
		finish : iEnd,
		interval : 100
	});

	// we will just set interval of updating to 1 sec
	$('#progress3').anim_progressbar({
		interval : 1000
	});
}
