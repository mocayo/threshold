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

    <title>历史特征值查询</title>
    
	<!-- css file form css dir -->
	<link rel="stylesheet" href="css/bordered.css">
	
	 <style>
	 	#lsinfo table{
	 		margin-top: 5px;
	 	}
		#lsinfo table th{
			border-bottom: 2px solid #439ABB;
		}
		#lsinfo table td{
			font-family: 'Lucida sans', 'trebuchet MS', 'Consolas';
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
                        <div class="panel-heading"><a><i class="fa fa-clock-o"></i>&nbsp;历史特征值查询</a></div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
								<div id="tab_head">
									<ul class="nav nav-tabs" id="tabs">
										<li class="active"><a href="#" title="历史特征值">历史特征值</a></li>
										<li><a href="#" title="箱线图">箱线图</a></li>
									</ul>
								</div>
								<div id="lschart">
									<div id="container"></div>
								</div>
								<div id="lsinfo">
								<table class="table table-striped table-hover" style="width:95%;margin:15px;">
									<thead>
										<tr>
										<th>#</th>
										<th >测点编号</th>
										<th style='text-align:right;'>最大值</th>
										<th style='text-align:right;'>最小值</th>
										<th style='text-align:right;'>平均值</th>
										<th style='text-align:right;'>大值平均值</th>
										<th style='text-align:right;'>小值平均值</th>
										</tr>
									</thead>
									<tbody id="ls_table"></tbody>
								</table>
								<div id="page_ctrl" style="float:right"></div>
                            	</div>
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
			<!-- page content end -->
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
    <script src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
    
    <!-- select js -->
    <script type="text/javascript" src="js/hisValInfo.js"></script>
    <script type="text/javascript" src="js/highcharts.js"></script>
    <script type="text/javascript" src="js/highcharts-more.js"></script>
	<script type="text/javascript" src="js/exporting.js"></script>

</body>

</html>
