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

    <title>自动预处理</title>
    
    <style>
		#predataResult_cd th{
			border-bottom: 2px solid #439ABB;
		}
		#predataResult_cd td{
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
                <!-- <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">按测点查询</h1>
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
                        <div class="panel-heading"><a><i class="fa fa-dot-circle-o"></i>&nbsp;自动预处理查询</a></div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                            	<%-- <table style="">
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
									</select>
								</div>
								</td>
								<td>&nbsp;</td>
								<td width="120">
								<div class="form-group">
									<label>测点编号</label> 
									<select class="form-control" id="point_Sel" multiple="multiple" size="1">
										<option value=''>--请选择--</option>
									</select>
								</div>
								</td>
								<td>&nbsp;&nbsp;</td>
								<td valign="bottom">
								<div class="form-group" >
									<button id="query_point" class="btn btn-primary btn-outline">查询</button>
								</div>
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
											<td>&nbsp;&nbsp;</td>
											<td valign="bottom">
											<div>
												<button id="query_point" class="btn btn-primary btn-outline">查询</button>
											</div>
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
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading"><a><i class="fa fa-tags"></i>&nbsp;预处理对比分析</a></div>
                        <div class="panel-body" style="height:auto">
                        	<!-- 按测点查询 -->
                        	<div id="container"></div>
							<!-- <div id="predataResult_cd" style="margin-top: 5px;"></div>
							<div>
								<div id="container_r1" style="margin-top: 5px;border:1px solid white;">
									<div id="detail-container_r1" style="width:99%"></div>
									<div id="master-container_r1" style="margin-top: 320px; height: 120px; width:100%"></div>
								</div>
							</div>
							<div style="border-bottom:1.5px dashed #6FB1D8;"></div>
							<div>
								<div id="container_r2" style="margin-top: 5px;border:1px solid white/* #439ABB */;">
									<div id="detail-container_r2" style="width:99%"></div>
									<div id="master-container_r2" style="margin-top: 320px; height: 120px; width: 100%"></div>
								</div>
							</div> -->
            			</div>
            		</div>
            	</div>
            </div>
            </div>
            </div>
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
	
	<!-- Select js -->
	<script type="text/javascript" src="../js/collapse-link.js"></script>
	<script type="text/javascript" src="js/highcharts.js"></script>
	<script type="text/javascript" src="js/exporting.js"></script>
    <script type="text/javascript" src="../myjs/point-single.js"></script>
    <script type="text/javascript" src="js/autoPtmInfo.js"></script>
    <script type="text/javascript" src="../myjs/jStringBuffer.js"></script>
    <script type="text/javascript" src="../myjs/bootstrap-multiselect.js"></script>
</body>

</html>
