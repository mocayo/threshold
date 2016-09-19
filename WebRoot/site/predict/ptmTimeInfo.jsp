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

    <title>手动预处理查询</title>
	
	<style>
		#predataResult_time th{
			border-bottom: 2px solid #439ABB;
		}
		#predataResult_time td{
			border-bottom: 1px solid #6FB1D8;
		}
	</style>	
	
    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- date time picker -->
    <link rel="stylesheet" media="screen" href="css/jquery.datetimepicker.css">
    <link rel="stylesheet" media="screen" href="css/sel.css">

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
                <!-- <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">按时间查询</h1>
                    </div>
                    /.col-lg-12
                </div> -->
                <!-- /.row -->
				
				 <!-- add page content here -->

				  <!-- /.row -->
			<div class="panel-body">
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading"><a><i class="fa fa-calendar"></i>&nbsp;手动预处理查询</a></div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                             <div class="dataTable_wrapper">
                            	<table style="">
                            	<tr>
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
								</td>
								<td>&nbsp;&nbsp;</td>
								<td valign="bottom">
								<div class="form-group" >
									<button id="query_time" class="btn btn-primary btn-outline">查询</button>
								</div>
								</td>
								</tr>
								</table>
							</div>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.panel-body -->
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            <div class="row" id="query_time_result" style="display:none">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading"><a><i class="fa fa-tags"></i>&nbsp;预处理批次</a></div>
                        <div class="panel-body">
                        	<div class='dataTable_wrapper'>
                        		<!-- 按时间查询 -->
								<div id="predataResult_time" style="margin-top: 5px;">
                        	</div>
                        </div>
            			</div>
            		</div>
            	</div>
            </div>
            </div>
            <!-- /.row -->
            </div>
			</div>            
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
    <script src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
    
    <!-- Select js -->
    <script type="text/javascript" src="../js/collapse-link.js"></script>
    <script type="text/javascript" src="js/query_time.js"></script>
	<script type="text/javascript"src="js/jquery.datetimepicker.js"></script>
    <script type="text/javascript" src="../myjs/date-util.js"></script>
    <script type="text/javascript" src="../myjs/jStringBuffer.js"></script>
    <script type="text/javascript" src="../myjs/bootstrap-multiselect.js"></script>

</body>

</html>
