<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- <%@ include file="../mystyle/style.jsp" %> --%>
<!-- check form -->
<script type="text/javascript">
	function checkForm() {
		if (document.getElementById("modelname").value == "") {
			alert("ModelName不能为空！");
			return false;
		} else {
			/* var check1 = document.getElementById("method1");
			var check2 = document.getElementById("method2");
			var check3 = document.getElementById("method3");
			if (check1.checked || check2.checked || check3.checked) {
			} else {
				alert("请选择ModelMethod！");
			return false;
			} */
			if(document.getElementById("method").value==""){
				alert("Method不能为空");
				return false;
			}
		}
		return true;
	}
</script>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>添加预测模型pro</title>

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
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">添加预测模型pro</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
				
				 <!-- add page content here -->
				 <div class="panel-body">
                 <div class="row">
                 <div class="col-lg-6">
				 <form role="form" action="addpremodel" method="post" onsubmit="return checkForm()">
					<div class="form-group">
						<label>ModelName:</label> 
						<input class="form-control" placeholder="请输入ModelName" size="10" name="model.modelname" id="modelname">
					</div>
					<div class="form-group">
						<label>ModelType:</label>
						<input type="hidden" name="model.modeltype" value="1">
						<p class="form-control-static">预测模型</p>
					</div>
					<div class="form-group">
						<label>Method:</label>
						<input class="form-control" placeholder="请输入ModelMethod" size="10" name="model.method" id="method">
						<!-- <div class="checkbox">
							<label> <input type="checkbox" value="method1" name="model.method" id="method1">
								method1
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" value="method2" name="model.method" id="method2">
								method2
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" value="method3" name="model.method" id="method3">
								method3
							</label>
						</div> -->
					</div>
					<button type="submit" class="btn btn-default">添加</button>
                    <button type="reset" class="btn btn-default">重置</button>
				</form>
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
    <script src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>

</body>

</html>
