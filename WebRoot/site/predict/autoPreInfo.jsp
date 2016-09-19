<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- <%@ include file="../mystyle/style.jsp" %> --%>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>自动预测查询</title>
    
	<!-- date time picker -->
    <link rel="stylesheet" media="screen" href="css/jquery.datetimepicker.css">
    
    <!-- table css -->
    <link rel="stylesheet" media="screen" href="css/bordered.css">
    
    <style>
		#autoPreTable th{
			border-bottom: 2px solid #439ABB;
		}
		#autoPreTable td{
			border-bottom: 1px solid #6FB1D8;
		}
	</style>	
	
    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
		<%@include file="../incoming/navigation.jsp" %>
		
       <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
            <div class="panel-body">
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                        	<a><i class="fa fa-search"></i>&nbsp;自动预测查询</a>
                        	<!-- <a class="collapse-link" style="float:right"><i class="fa fa-chevron-up"></i></a> -->
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                            	<%-- autoPreInfo here --%>
								<%-- <table style="display:none">
                            	<tr>
                            	<td width="120">
								<div class="form-group">
									<label>监测仪器</label> 
									<select class="form-control" id="dec_Sel">
										<option value='' selected>---请选择---</option>
									</select>
								</div>
								</td>
								<td>&nbsp;</td>
								<td width="120">
								<div class="form-group">
									<label>坝段</label> 
									<select class="form-control" id="dam_Sel">
										<option value='' selected>--请选择--</option>
										<!-- <option value="A22">A22</option>
										<option value="A15">A15</option> -->
									</select>
								</div>
								</td>
								<td>&nbsp;</td>
								<td>
								<div class="form-group">
									<label>测点编号</label>
									<div class="form-group dk-select"style="width: 138px;margin-bottom:0px;">
											<select id="point_Sel" class="form-control" multiple="multiple" size="1">
													<option value=''>---请选择---</option>
											</select>
									</div>
									<label>测点编号</label> 
									<select class="form-control" id="point_Sel" size="1">
										<option value=''>--请选择--</option>
									</select>
								</div>
								</td>
								<td>&nbsp;</td>
								<td width="120">
								<div class="form-group">
									<label>安全等级</label> 
									<select id="safe_Sel" class="form-control">
										<option value='' selected>---请选择---</option>
										<option value='6'>全部</option>
										<option value='0'>异常</option>
										<option value='1'>一级预警</option>
										<option value='2'>二级预警</option>
										<option value='3'>三级预警</option>
										<option value='4'>原数据异常</option>
										<option value='5'>正常</option>
									</select>
								</div>
								</td>
								<!-- <td>&nbsp;</td>
								<td width="120">
								<div class="form-group">
									<label>时间段</label> 
									<input id="start_time" class="form-control" type="text" style="background-color:#FFF" readonly />
								</div>
								</td>
								<td>&nbsp;</td>
								<td width="120">
								<div class="form-group">
									<label>至</label> 
									<input id="end_time" class="form-control" type="text" style="background-color:#FFF" readonly />
								</div>
								</td> -->
								<td>&nbsp;&nbsp;</td>
								<td valign="bottom">
								<div class="form-group" >
									<button id="search_button" class="btn btn-primary btn-outline">查询</button>
								</div>
								</td>
								</tr>
								</table>
                            	 --%>
                            	<%-- autoPreInfo here --%>
                            	<table>
										<tr>
											<td><label>基本部位</label> <select class="form-control"
												id="basic_sel">
													<option value='' selected>---请选择---</option>
											</select></td>
											<td>&nbsp;&nbsp;</td>
											<td><label>监测项目</label> <select class="form-control"
												id="monitor_sel">
													<option value='' selected>---请选择---</option>
											</select></td>
											<td>&nbsp;&nbsp;</td>
											<td><label>仪器类型</label> <select class="form-control"
												id="instrType_sel">
													<option value='' selected>---请选择---</option>
											</select></td>
											<td>&nbsp;&nbsp;</td>
											<td><label>测点</label>
												<div>
													<select class="form-control" id="point_sel">
														<option value='' selected>---请选择---</option>
													</select>
												</div></td>
											<%-- <td>&nbsp;&nbsp;</td>
											<td><label>分量</label>
												<div>
													<select class="form-control" id="comp_sel">
														<option value='' selected>---请选择---</option>
													</select>
												</div></td> --%>
											<td>&nbsp;</td>
											<td width="120px" >
												<div>
													<label>时间段</label> <input id="start_time"
														class="form-control" type="text"
														style="background-color:#FFF" readonly />
												</div>
											</td>
											<td>&nbsp;</td>
											<td width="120px">
												<div>
													<label>至</label> <input id="end_time" class="form-control"
														type="text" style="background-color:#FFF" readonly />
												</div>
											</td>
											<td>&nbsp;&nbsp;</td>
											<td valign="bottom">
												<button id="search_button" class="btn btn-primary btn-outline">查询</button>
											</td>
										</tr>
									</table>
                            </div>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row" id="autopre_result" style="display:none">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                        	<a><i class="fa fa-flag"></i>&nbsp;自动预测</a>
                        	<!-- <a class="collapse-link" style="float:right"><i class="fa fa-chevron-up"></i></a> -->
                        </div>
                        <div class="panel-body">
                        	<div class='dataTable_wrapper'>
                        		<!-- 自动预测查询结果 -->
                        		<div id="autoPreTable">
                            	<table class="table table-striped table-hover" style="font-size:18px;">
	                            	<tr>
										<th>#</th>
										<th>采集时间</th>
										<th>测点编号</th>
										<th>水位</th>
										<th>模型</th>
										<th>预测分量</th>
										<th>实测值</th>
										<th>预测值</th>
										<th>相对误差</th>
										<th>安全等级</th>
										<!-- <th style="width:5%;">序号</th>
										<th style="width:10%">采集时间</th>
										<th style="width:10%">测点编号</th>
										<th style="width:15%">水位</th>
										<th style="width:15%">模型</th>
										<th style="width:10%">预测分量</th>
										<th style="width:15%">实测值</th>
										<th style="width:15%">预测值</th>
										<th style="width:10%">安全等级</th> -->
									</tr>
									<tbody id="autoResTable" style="font-size:13px;"></tbody>
                            	</table>
                            	<div id="page_ctrl" style="float:right;"></div>
                        	</div>
                        </div>
            			</div>
            		</div>
            	</div>
            </div>
            <div class="row" id="autopre_chart">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                        	<a><i class="fa fa-flag"></i>&nbsp;测点详细图</a>
                        	<!-- <a class="collapse-link" style="float:right"><i class="fa fa-chevron-up"></i></a> -->
                        </div>
                        <div class="panel-body" style="height:auto;">
	                        <div id="container"></div>
	                        <div id="container_r1" style="border-bottom: 1.5px dashed #6FB1D8;"></div>
	                        <div id="container_r2"></div>
                        </div>
            		</div>
            	</div>
            </div>
            <div class="row" id="test" style="display:none">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                        	<a><i class="fa fa-flag"></i>&nbsp;自动预测对比图</a>
                        </div>
                        <div class="panel-body" style="height:auto;">
	                        <div id="test_container"></div>
                        </div>
            		</div>
            	</div>
            </div>
            <!-- /.row -->
			</div>
			<!-- page content end -->
            </div>
            <!-- /.container-fluid -->
	   </div>
        <!-- /#page-wrapper -->
	</div>				
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- DataTables JavaScript -->
    <script src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
    
    <!-- select js -->
	<script type="text/javascript" src="../js/collapse-link.js"></script>
	<script type="text/javascript" src="js/highcharts.js"></script>
	<script type="text/javascript" src="js/exporting.js"></script>
    <script type="text/javascript" src="js/autoPreInfo.js"></script>
    <script type="text/javascript"src="js/jquery.datetimepicker.js"></script>
    <script type="text/javascript" src="../myjs/point-single.js"></script>
    <script type="text/javascript" src="../myjs/date-util.js"></script>
    <script type="text/javascript" src="../myjs/jStringBuffer.js"></script>
    <script type="text/javascript" src="../myjs/bootstrap-multiselect.js"></script>

</body>

</html>
