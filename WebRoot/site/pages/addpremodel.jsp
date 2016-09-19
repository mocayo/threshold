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

<title>添加预测模型</title>

<style>
#addtable th {
	border-bottom: 2px solid #439ABB;
}

#addtable td {
	border-bottom: 1px solid #6FB1D8;
}
</style>

<!-- ***CSS*** -->
<link rel="stylesheet" media="screen"
	href="../../media/css/dropkick.css">

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
						<div class="col-lg-6">
							<div class="panel panel-default">
								<div class="panel-heading">
									<a><i class="fa fa-pencil"></i>&nbsp;添加预测模型</a>
								</div>
								<div class="panel-body">
									<div class="table-responsive">
										<table class="table" id="addtable">
											<thead>
												<tr>
													<th>#</th>
													<th>模型名称</th>
													<th>模型方法</th>
													<th><i class="fa fa-pencil-square-o"></i></th>
												</tr>
											</thead>
											<tbody id="the_tbody">
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
						<%-- <div class="col-lg-6">
							<div class="panel panel-default">
								<div class="panel-heading">
									<p class="fa fa-pencil"></p>
								</div>
								<div class="panel-body">
									<label>模型名称</label>
									<div class="form-group" style="width:150px">
										<select id="name_Sel" class="form-control">
										</select>
									</div>
									<label>模型方法</label>
									<div class="form-group" style="width:150px">
										<select id="method_Sel" class="form-control"
											multiple="multiple" size="1">
											<option selected value=''>--请选择--</option>
										</select>
									</div>
									<button id="add_btn" class="btn btn-primary btn-outline">添加</button>
								</div>
							</div>
						</div>
						 --%>
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

	<!-- ***JS*** -->
	<script type="text/javascript" src="js/addpremodel.js"></script>
	<script type="text/javascript" src="../myjs/bootstrap-multiselect.js"></script>

</body>

</html>
