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
<%-- <%@ include file="../mystyle/style.jsp" %> --%>
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>单点预测</title>

<style type="text/css">
.styled-select select {
	width: 150px;
	height: 34px;
	overflow: hidden;
	border-radius: 4px;
	background: url(css/img/arrow.png) no-repeat right;
	-webkit-appearance: none; /*for chrome*/
}

#pre_table table th {
	font-size: 15px;
	border-bottom: 2px solid #439ABB;
}

#pre_table table td {
	font-size: 13px;
	border-bottom: 1px solid #6FB1D8;
}

#showstatistic table th {
	font-size: 15px;
	border-bottom: 2px solid #439ABB;
}

#showstatistic table td {
	font-size: 13px;
	font-family: 'Lucida sans', 'trebuchet MS', 'Consolas';
	border-bottom: 1px solid #6FB1D8;
	vertical-align: middle;
}

#showdetailedinfo table th {
	font-size: 14px;
	border-bottom: 2px solid #439ABB;
}

#showdetailedinfo table td {
	font-size: 12px;
	border-bottom: 1px solid #6FB1D8;
}
</style>

<!-- date time picker -->
<link rel="stylesheet" media="screen"
	href="css/jquery.datetimepicker.css">
<link rel="stylesheet" media="screen"
	href="../../media/css/dropkick.css">

<!-- css file form css dir -->
<link rel="stylesheet" href="css/bordered.css">

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

</head>

<body>

	<div id="wrapper">

		<!-- Navigation -->
		<%@include file="../incoming/navigation.jsp"%>

		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<!-- <div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">单点预测</h1>
					</div>
					/.col-lg-12
				</div> -->
				<!-- /.row -->

				<!-- add page content here -->

				<!-- /.row -->
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-13">
							<div class="panel panel-default">
								<div class="panel-heading">
									<a><i class="fa fa-hand-o-up"></i>&nbsp;手动预测</a>
								</div>
								<!-- /.panel-heading -->
								<div class="panel-body">
									<div class="dataTable_wrapper">
										<div id="someTable_Sel">
											<%-- <table>
												<tr>
													<td><label>监测仪器</label>
														<div class="form-group dk-select"
															style="width: 125px;margin-bottom:0px;">
															<select id="dec_Sel" class="form-control">
																<option value='' selected>---请选择---</option>
															</select>
														</div></td>
													<td>&nbsp;&nbsp;</td>
													<td><label>坝段</label>
														<div class="form-group dk-select"
															style="width: 125px;margin-bottom:0px;">
															<select id="dam_Sel" class="form-control">
																<option value='' selected>---请选择---</option>
															</select>
														</div></td>
													<td>&nbsp;&nbsp;</td>
													<td><label>测点编号</label>
														<div class="form-group dk-select"
															style="width: 138px;margin-bottom:0px;">
															<select id="point_Sel" class="form-control"
																multiple="multiple" size="1">
																<option value=''>---请选择---</option>
															</select>
														</div></td>
													<td>&nbsp;&nbsp;</td>
													<td><label>预测分量</label>
														<div class="form-group dk-select"
															style="width: 138px;margin-bottom:0px;">
															<select id="comp_Sel" class="form-control"
																multiple="multiple" size="1">
																<option value='' selected>---请选择---</option>
															</select>
														</div></td>
													<td>&nbsp;&nbsp;</td>
													<td><label>预测模型</label>
														<div class="form-group dk-select"
															style="width: 125px;margin-bottom:0px;">
															<select id="premodel_Sel" class="form-control">
																<option value='' selected>---请选择---</option>
															</select>
														</div></td>
													<td>&nbsp;&nbsp;</td>
													<td><label>时间段</label>
														<div class="form-group dk-select"
															style="width: 105px;margin-bottom:0px;">
															<input id="start_time" class="form-control" type="text"
																style="background-color:#FFF" readonly />
														</div></td>
													<td>&nbsp;&nbsp;</td>
													<td><label>至</label>
														<div class="form-group dk-select"
															style="width: 105px;margin-bottom:0px;">
															<input id="end_time" class="form-control" type="text"
																style="background-color:#FFF" readonly />
														</div></td>
													<td>&nbsp;&nbsp;</td>
													<td valign="bottom">
														<button id="predict_button"
															class="btn btn-primary btn-outline">开始预测</button>
													</td>
												</tr>
											</table>
											  --%>
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
											<td>&nbsp;&nbsp;</td>
											<td><label>分量</label>
												<div>
													<select class="form-control" id="comp_sel">
														<option value='' selected>---请选择---</option>
													</select>
												</div></td>
											<td>&nbsp;&nbsp;</td>
											<td><label>预测模型</label>
												<div >
													<select id="premodel_sel" class="form-control">
														<option value='' selected>---请选择---</option>
													</select>
											</div></td>
											<td>&nbsp;</td>
											<td width="105" >
												<div>
													<label>时间段</label> <input id="start_time"
														class="form-control" type="text"
														style="background-color:#FFF" readonly />
												</div>
											</td>
											<td>&nbsp;</td>
											<td width="105">
												<div>
													<label>至</label> <input id="end_time" class="form-control"
														type="text" style="background-color:#FFF" readonly />
												</div>
											</td>
											<td>&nbsp;&nbsp;</td>
											<td valign="bottom">
												<button id="predict_button" class="btn btn-primary btn-outline">开始预测</button>
											</td>
										</tr>
									</table> 
											<input type="hidden" id="_view_preId" />
										</div>
										<!-- 查看信息 -->
										<div id="pre_info" style="display:none;">

											<div id="tab_head" style="display:none;valign:bottom;">
												<ul class="nav nav-tabs" id="tabs">
													<li class="active"><a href="#" title="折线图">折线图</a></li>
													<li><a href="#" title="统计信息">统计信息</a></li>
													<li><a href="#" title="详细信息">详细信息</a></li>
													<li><span class="styled-select" id="detail_infoSel"
														style="display:none;"> 测点： <select
															id="detail_point">
														</select> 分量： <select id="detail_comp">
																<!-- <option value='r1' selected>&nbsp;横河向位移</option>
														<option value='r2'>&nbsp;顺河向位移</option> -->
														</select> <!-- 存放开始到结束时间 --> <input type="hidden" id="_the_time" />
															<!-- 存放表名 --> <input type="hidden" id="_the_tab" />
													</span></li>
													<li style="float:right"
														onclick="window.location.href = 'singlePre.jsp'"><a
														title="返回"><i class="fa fa-reply"></i></a></li>
												</ul>
											</div>
										</div>
									</div>
									<!-- /.panel-body -->
								</div>

								<!-- /.panel -->
								<!-- /.col-lg-12 -->
							</div>
							<!-- /.row -->
							<!-- page content end -->
						</div>
						<!-- /.container-fluid -->
					</div>

					<div class="row" id="total_pre_info">
						<div class="col-lg-13">
							<div class="panel panel-default">
								<div class='panel-heading'>
									<a><i class='fa fa-columns'></i>&nbsp;单点预测信息</a>
								</div>
								<div class='panel-body'>
									<div class='dataTable_wrapper'>
										<!-- 单点预测表 -->
										<div id="pre_table"></div>
									</div>
								</div>
							</div>
						</div>
					</div>

				</div>
				<!-- /#page-wrapper -->
			</div>
		</div>
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

	<!-- select js -->
	<script type="text/javascript" src="../js/collapse-link.js"></script>
	<script type="text/javascript" src="js/highcharts.js"></script>
	<script type="text/javascript" src="js/exporting.js"></script>
	<script type="text/javascript" src="js/jquery.datetimepicker.js"></script>
	<script type="text/javascript" src="js/singlePre.js"></script>
	<script type="text/javascript" src="../myjs/point.js"></script>
	<script type="text/javascript" src="../myjs/date-util.js"></script>
	<script type="text/javascript" src="../myjs/jStringBuffer.js"></script>
	<script type="text/javascript" src="../myjs/bootstrap-multiselect.js"></script>

</body>

</html>
