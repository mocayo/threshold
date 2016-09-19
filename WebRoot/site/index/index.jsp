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

<title>阈值分析系统首页</title>

<!-- css file form css dir -->
<link rel="stylesheet" href="../css/table-style.css">

<link rel="stylesheet" media="screen" href="css/jquery.datetimepicker.css">

<!-- timebar -->
<link rel="stylesheet" type="text/css" href="css/common.css">

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
	font-family: 'Microsoft Yahei', 'Consolas';
	font-size: 12px;
	border-bottom: 1px solid #6FB1D8;
}

.well {
	border-bottom: 1px dashed #439ABB;
	/* border: 1px solid #439ABB;
	border-radius: 0px; */
	margin-top: 5px;
	margin-bottom: 2px; 
	background-color: white;
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
						<div class="col-lg-12" id='_errorpoints'>
							<div class="panel panel-default">
								<div class="panel-heading">
									<a><i class="fa fa-comments"></i>&nbsp;关注点</a>
								</div>
								<!-- /.panel-heading -->
								<div class="panel-body">
									<div class="col-lg-7">
										<div id="timepage" style='width:99%;font-size:12px;'></div>
										<hr style=" margin: 0; border-top: 1.5px dashed #6FB1D8;">
										<input type='hidden' id='_time' value="" />
										<table class="table table-striped table-hover" id="errorTable">
											<thead>
												<tr>
													<th>#</th>
													<th>测点编号</th>
													<th>分量</th>
													<th style='text-align:right'>实测值</th>
													<th style='text-align:right'>预测值</th>
													<th style='text-align:right'>绝对误差</th>
													<th style='text-align:center'>相对误差</th>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
										<div id="pagectrl"></div>
									</div>
									<div class="col-lg-5" id="_errorpie" style="height:220px;border-bottom: 1.5px dashed #6FB1D8;border-left:1.5px dashed #6FB1D8;"></div>
									<div class="col-lg-5" id="_errorbar" style="border-left:1.5px dashed #6FB1D8;"></div>
									<!-- /.panel-body -->
								</div>
								<!-- /.panel -->
							</div>
						
						</div>
						<div class="col-lg-12" id='_keypoints' style="display:none">
							<div class="panel panel-default">
								<div class="panel-heading">
									<a><i class="fa fa-thumb-tack"></i>&nbsp;重点关注测点</a>
								</div>
								<!-- /.panel-heading -->
								<div id="tab_head">
									<ul class="nav nav-tabs" id="tabs">
										<li class="active" id='pweek'><a href="javascript:;"
											title="周" style='font-size:16px'>7天</a></li>
										<li id='pmonth'><a href="javascript:;" title="月"
											style='font-size:16px'>30天</a></li>
									</ul>
								</div>
								<div class="panel-body">
									<div class="col-lg-6 well">
										<div id="c2r1"></div>
									</div>
									<div class="col-lg-6 well">
										<div id="c2r2"></div>
									</div>
									<div class="col-lg-6 well">
										<div id="c1r1"></div>
									</div>
									<div class="col-lg-6 well">
										<div id="c1r2"></div>
									</div>
								</div>
							</div>
						</div>
						
						<div class="col-lg-12" id='_keypoints2'>
							<div class="panel panel-default">
								<div class="panel-heading">
									<a><i class="fa fa-thumb-tack"></i>&nbsp;重点关注测点</a>
								</div>
								<!-- /.panel-heading -->
								<!-- <div id="tab_head">
									<ul class="nav nav-tabs" id="tabs">
										<li class="active" id='pweek'><a href="javascript:;"
											title="周" style='font-size:16px'>7天</a></li>
										<li id='pmonth'><a href="javascript:;" title="月"
											style='font-size:16px'>30天</a></li>
									</ul>
								</div> -->
								<div class="panel-body">
									<div class="col-lg-12 well">
										<table>
										<tr class="col-lg-8">
											<td width="130">
												<label>从</label>
											</td>
											<td>
												<div style="width:120px">
													<input id="start_time" class="form-control" type="text" style="background-color: #FFF" readonly />
												</div>
											</td>
											<td>&nbsp;&nbsp;&nbsp;</td>
											<td width="130">
												<label>至</label>
											</td>
											<td>
												<div style="width:120px">
													<input id="end_time" class="form-control" type="text" style="background-color: #FFF" readonly />
												</div>
											</td>
											<td>
												<button id="show_key_chart" class="btn btn-primary btn-outline" style="margin-left:5px">计算</button>
											</td>
										</tr>
									</table>
									</div>
									
									<div class="col-lg-12 well">
										<div id="cpl1r2"></div>
									</div>
									<div class="col-lg-12 well">
										<div id="cpl2r2"></div>
									</div>
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
	<script type="text/javascript" src="../myjs/date-util.js"></script>
	<script type="text/javascript" src="js/jquery.datetimepicker.js"></script>
	<script type="text/javascript" src="js/highcharts.js"></script>
	<script type="text/javascript" src="js/exporting.js"></script>
	<script type="text/javascript" src="js/timepage.js"></script>
	<script type="text/javascript" src="../js/collapse-link.js"></script>
	<script type="text/javascript" src="js/index.js"></script>
	<%-- <script type="text/javascript" src="../myjs/themes/dark-blue.js"></script> --%>
	
</body>

</html>
