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
<script type="text/javascript" src="../upload/js/plupload.full.min.js"></script>

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>添加预测模型测点</title>

<style>
#mtable th {
	border-bottom: 2px solid #439ABB;
}

#mtable td {
	border-bottom: 1px solid #6FB1D8;
}
</style>

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
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-9">
							<div class="panel panel-default">
								<div class="panel-heading">
									<a><i class="fa fa-pencil"></i>&nbsp;添加测点(预测)</a>
								</div>
								<div class="panel-body">
									<form action="addpremodelpoint" method="post">
										<div class="seachform" style="margin-top:5px;">
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
															<select class="form-control" id="point_sel"
																name="modelpointrecord.Instr_no">
																<option value='' selected>---请选择---</option>
															</select>
														</div></td>
													<td>&nbsp;&nbsp;</td>
													<td><label>分量</label>
														<div>
															<select class="form-control" id="comp_sel"
																name="modelpointrecord.R">
																<option value='' selected>---请选择---</option>
															</select>
														</div></td>
												</tr>
											</table>
										</div>
										<br> <label>为当前选择点批量配置模型：</label>
										<s:property value="#request.point_model_select" escape="false" />
									</form>
									<div style="magin-top:5px;float:right">
										<button id="_add" class="btn btn-primary btn-outline">添加</button>
									</div>
								</div>
							</div>
						</div>
						<div class="col-lg-3">
							<div class="panel panel-default">
								<div class="panel-heading">
									<a><i class="fa fa-file-excel-o"></i></a>
								</div>
								<div class="panel-body">
									<%@include file="../incoming/preupload.jsp"%>
								</div>
							</div>
						</div>
					</div>
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
	<script
		src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
	<script
		src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="../dist/js/sb-admin-2.js"></script>

	<!-- ******* -->
	<script type="text/javascript" src="js/addpoint.js"></script>
	<script type="text/javascript" src="../myjs/point.js"></script>
	<script type="text/javascript" src="../myjs/jStringBuffer.js"></script>
	<script type="text/javascript" src="../myjs/bootstrap-multiselect.js"></script>

</body>

</html>
