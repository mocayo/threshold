<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>继续预处理</title>
<style type="text/css">
table th {
	font-size: 18px;
	background-color: #FEFEFE;
	border-bottom: 2px solid #439ABB;
}
table td {
	font-size: 16px;
	border-bottom: 1px solid #6FB1D8;
}
</style>

<!-- Bootstrap Core CSS -->
<link href="../bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- date time picker -->
<link rel="stylesheet" media="screen"
	href="css/jquery.datetimepicker.css">
<link rel="stylesheet" media="screen" href="css/page.css">

<!-- progress bar -->
<link href="progressbar/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css"/>
<link href="progressbar/css/main.css" rel="stylesheet" type="text/css" />

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
						<h1 class="page-header"></h1>
					</div>
					/.col-lg-12
				</div> -->
				<!-- /.row -->

				<!-- add page content here -->
				<div class="panel-body">
				<!-- /.row -->
				<div class="row">
					<div class="col-lg-12">
	                    <div class="panel panel-info">
	                        <div class="panel-heading" id="head"></div>
	                        <div class="panel-body">
	                        	<input type="hidden" id="totalpage"/>
	                        	<p style="font-weight:bold" id="resultTime">预处理时间:&nbsp;&nbsp;&nbsp;</p>
				    			<p style="font-weight:bold" id="resultDes">自动处理测点&nbsp;个，处理记录&nbsp;条，正常记录&nbsp;条、异常记录&nbsp;条。</p>
								<ul class="nav nav-tabs">
	                                <li class="active" id="allData"><a href="#home-pills" data-toggle="tab">全部数据</a>
	                                </li>
	                                <li id="normData"><a href="#profile-pills" data-toggle="tab">正常数据</a>
	                                </li>
	                                <li id="errorData"><a href="#messages-pills" data-toggle="tab">异常数据</a>
	                                </li>
	                                <!-- <li id="confirm"><a href="#settings-pills" data-toggle="tab">确认预处理</a>
	                                </li> -->
                            		<li style="float:right" onclick="window.location.href = 'pretreat.jsp'"><a title="返回"><i class="fa fa-reply"></i></a></li>
                            		<li style='float:right;'><button class='btn btn-info btn-outline' id='confirm'>确认预处理</button></li>
                            	</ul>
								<div style="margin-top: 10px">
									<table id="preProcess" class="table table-striped table-hover" width="99%" >
									</table>
									<table id="preResult" class="table table-striped table-hover" width="99%" style="display:none;">
									</table>
								</div>
								<div id="pagespan">
							    </div>
	                        </div>
	                        <!-- <div class="panel-footer">
	                            Panel Footer
	                        </div> -->
	                    </div>
	                </div>
	                
					<%-- <div class="col-lg-12" style="display:none;">
						<div class="panel panel-default">
							<div class="panel-heading" style="background-color:#D1EEEE;">
								<h3></h3>
								<span>
									<button class="btn btn-info btn-outline" id="allData">全部数据</button>
									<button class="btn btn-info btn-outline" id="normData">正常数据</button>
									<button class="btn btn-info btn-outline" id="errorData">异常数据</button>
									<button class="btn btn-info btn-outline" id="confirm">确认预处理</button>
								</span>
							</div>
							<!-- /.panel-heading -->
							<div class="panel-body">
								<ul class="nav nav-tabs">
	                                <li class="active" id="allData"><a href="#home-pills" data-toggle="tab">全部数据</a>
	                                </li>
	                                <li id="normData"><a href="#profile-pills" data-toggle="tab">正常数据</a>
	                                </li>
	                                <li id="errorData"><a href="#messages-pills" data-toggle="tab">异常数据</a>
	                                </li>
	                                <li id="confirm"><a href="#settings-pills" data-toggle="tab">确认预处理</a>
	                                </li>
                            	</ul>
								<div style="margin-top: 10px">
									<div id="preProcess" style='width:99%;margin:5px;'>
									</div>
									<div id="preResult" style="display:none;width:99%;margin:5px;">
									</div>
								</div>
								<!-- <div class="pagin">
							    	<div class="message">共<i class="blue" id="totalitem"></i>条记录，当前显示第&nbsp;<i class="blue" id="page_info">&nbsp;</i>页</div>
							        <ul class="paginList" id="pagespan">
							        </ul>
							    </div> -->
							</div>
							
							<!-- Modal -->
							<!-- <button id="active_Modal" style="display:none;" data-toggle="modal" data-target="#myModal"></button>
                            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="myModalLabel">预处理进度</h4>
                                        </div>
                                        <div class="modal-body">
                                        	<div id="progress1">
										        <div class="percent"></div>
										        <div class="pbar"></div>
										        <div class="elapsed"></div>
										    </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-primary">确认</button>
                                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                        </div>
                                    </div>
                                    /.modal-content
                                </div>
                                /.modal-dialog
                            </div> -->
                            <!-- /.modal -->
                            
							<!-- /.panel-body -->
							<!-- /.panel -->
						</div>
						<!-- /.col-lg-12 -->
					</div>
					<!-- /.row -->
				 --%>
				</div>
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /#page-wrapper -->

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

	<!-- Select js -->
	<script type="text/javascript" src="../js/collapse-link.js"></script>
	<script type="text/javascript" src="js/goPreHandle.js"></script>
	<script type="text/javascript" src="../myjs/jStringBuffer.js"></script>
	
</body>

</html>
