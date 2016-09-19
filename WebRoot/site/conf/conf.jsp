<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>阈值分析系统参数配置</title>

<!-- date time picker -->
<link rel="stylesheet" media="screen" href="css/jquery.datetimepicker.css">

<!-- slider -->
<link rel="stylesheet" href="css/bootstrap-slider.css">

<!-- Bootstrap Core CSS -->
<link href="../bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="../bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="../dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="../bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<style>
#errorTable th {
	border-bottom: 2px solid #439ABB;
	font-size: 13px;
}

#errorTable td {
	font-family: 'Lucida sans', 'trebuchet MS', 'Consolas';
	font-size: 12px;
	border-bottom: 1px solid #6FB1D8;
}

.well {
	border: 1px solid #439ABB;
	border-radius: 0px;
	margin-top: 15px;
	margin-bottom: 5px;
	background-color: white;
}
.slider-selection {
	background: #C6E2FF;
}
</style>

</head>

<body>
	<input type="hidden" id="_strTime" value="" />
	<div id="wrapper">
		<!-- Navigation -->
		<%@include file="../incoming/navigation.jsp"%>
		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-4" id='index_conf'>
							<div class="panel panel-default">
								<div class="panel-heading">
									<a><i class="fa fa-home fa-fw"></i>&nbsp;首页参数</a> <a
										class="collapse-link" style="float:right"> <i
										class="fa fa-chevron-up"></i>
									</a>
									
								</div>
								<!-- /.panel-heading -->
								<div class="panel-body">
									<div class="dataTable_wrapper">
										<div class="form-group">
											<label>异常点误差率：<span id="err_val"></span>%
											</label>
											<div style="width: 222px;border: 1px solid #6FB1D8;border-radius:6px;margin-bottom:5px">
												<input id="_index_errrate" data-slider-id='index_errrate'
													type="text" data-slider-min="0" data-slider-max="50"
													data-slider-step="5" />
											</div>
										</div>
										<div class="form-group">
											<label>异常点显示日期：<span id="err_dt"></span>
											</label>
											<div style="width: 108px;">
											<input type="text" id="errdate" 
													style="background-color:#FFF" class="form-control" readonly>
											</div>
										</div>
										<button id="conf_index_btn"
											class="btn btn-primary btn-outline btn-sm">配置</button>
									</div>
									<!-- /.panel-body -->
								</div>
								<!-- /.panel -->
							</div>
						</div>
						<div class="col-lg-4">
							<div class="panel panel-default">
								<div class="panel-heading">
									<a><i class="fa fa-history fa-fw"></i>&nbsp;历史特征值参数</a> <a
										class="collapse-link" style="float:right"> <i
										class="fa fa-chevron-up"></i>
									</a>
								</div>
								<!-- /.panel-heading -->
								<div class="panel-body">
									<div class="dataTable_wrapper">
										<div style="width:200">
											<div class="form-group">
												<label>计算频度：<span id="his_step_val"></span>天</label> 
												<!-- <input id="step" class="form-control" placeholder="step" /> -->
												<div style="width: 222px;border: 1px solid #6FB1D8;border-radius:6px;margin-bottom:5px">
													<input id="step" data-slider-id='_step'
													type="text" data-slider-min="1" data-slider-max="14" data-slider-value='7'
													data-slider-step="1"/>
												</div>
											</div>
											<div class="form-group">
												<label>下一个日期：<span id="his_nextdate_val"></span></label>
												<div style="width: 108px;">
												<input type="text" id="nextdate"
													style="background-color:#FFF" class="form-control" readonly>
												</div>
											</div>
											<button id="conf_his_btn" class="btn btn-primary btn-outline btn-sm">配置</button>
										</div>
									</div>
									<!-- /.table-responsive -->
								</div>
							</div>
						</div>
						<div class="col-lg-4">
							<div class="panel panel-default">
								<div class="panel-heading">
									<a><i class="fa fa-delicious fa-fw"></i>&nbsp;模型计算参数</a> <a
										class="collapse-link" style="float:right"> <i
										class="fa fa-chevron-up"></i>
									</a>
								</div>
								<!-- /.panel-heading -->
								<div class="panel-body">
									<div class="dataTable_wrapper">
										<div style="width:200">
											<div class="form-group">
												<label>sigama：<span id="model_sigma_val"></span></label> 
												<div style="width: 222px;border: 1px solid #6FB1D8;border-radius:6px;margin-bottom:5px">
													<input id="_conf_model" type="text"  value="" data-slider-min="0" data-slider-max="100" data-slider-step="5" data-slider-value="[20,80]"/>
												</div>
											</div>
											<button id="conf_model_btn" class="btn btn-primary btn-outline btn-sm">配置</button>
										</div>
									</div>
									<!-- /.table-responsive -->
								</div>
							</div>
						</div>
						<div class="col-lg-12" style="display:none">
							<div class="panel panel-default">
								<div class="panel-heading">
									<a><i class="fa fa-dot-circle-o fa-fw"></i>&nbsp;测点Test</a> <a
										class="collapse-link" style="float:right"> <i
										class="fa fa-chevron-up"></i>
									</a>
								</div>
								<!-- /.panel-heading -->
								<div class="panel-body">
									<table cellspacing="2px">
										<tr>
										<td>
										<label>基本部位</label>
										<select class="form-control"id="basic_sel">
											<option value='' selected>---请选择---</option>
										</select>
										</td>
										<td>&nbsp;&nbsp;</td>
										<td>	
										<label>监测项目</label>
										<select class="form-control"id="monitor_sel">
											<option value='' selected>---请选择---</option>
										</select>
										</td>
										<td>&nbsp;&nbsp;</td>
										<td>
										<label>仪器类型</label>
										<select class="form-control"id="instrType_sel">
											<option value='' selected>---请选择---</option>
										</select>
										</td>
										<td>&nbsp;&nbsp;</td>
										<td>
										<label>测点</label>
										<div>
											<select class="form-control"id="point_sel">
												<option value='' selected>---请选择---</option>
											</select>
										</div>
										</td>
										<td>&nbsp;&nbsp;</td>
										<td>
										<label>分量</label>
										<div>
											<select class="form-control"id="comp_sel">
												<option value='' selected>---请选择---</option>
											</select>
										</div>
										</td>
										</tr>
									</table>
									<!-- /.table-responsive -->
								</div>
							</div>
						</div>
						<!-- page content end -->
					</div>
				</div>
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
	<script
		src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
	<script
		src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="../dist/js/sb-admin-2.js"></script>

	<!-- ***JS*** -->
	<%-- 	<script type="text/javascript" src="js/highcharts.js"></script>
	<script type="text/javascript" src="js/exporting.js"></script> --%>
	
    <script type="text/javascript"src="js/jquery.datetimepicker.js"></script>
    <script type="text/javascript"src="js/bootstrap-slider.js"></script>
	<script type="text/javascript" src="../myjs/point.js"></script>
	<script type="text/javascript" src="js/conf.js"></script>
	<script type="text/javascript" src="../myjs/bootstrap-multiselect.js"></script>
</body>

</html>
