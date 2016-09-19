<%@ page pageEncoding="utf-8"%>
<style>
body{font-family:'微软雅黑';}
table th{font-family:'微软雅黑';}
/* .panel-body {background-color:#DCDCDC;}
.panel-body .panel-heading{background-color:#DCDCDC;}
.panel-body .panel-body{background-color:#DCDCDC;} */
</style>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<nav class="navbar navbar-default navbar-static-top"
	style="margin-bottom:0;background-image:url('../css/img/header-profile.png');" id="side_bar">
	<div class="navbar-header" style="height:77px;">
		<img src="../css/img/logo.png" alt="小湾电站" height="47px" style="margin-top: 10px;float: left;margin-left: 20px;"></img>
		<a class="navbar-brand" style="margin-top:10px">基于实测资料的水工安全阈值分析系统 v2.0</a>
	</div>
	<!-- /.navbar-header -->

	<div class="navbar-default sidebar" id="sidebar" style="margin-top:80px;">
		<div class="sidebar-nav navbar-collapse">
			<ul class="nav" id="side-menu">
				<li><a href="../index/index.jsp"><i
						class="fa fa-home fa-fw"></i> 阈值首页</a></li>
				<!-- sidebar -->
				<li><a href="#"><i class="fa fa-file-text fa-fw"></i> 数据预处理<span
						class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
						<li><a href="../predict/pretreat.jsp">手动预处理</a></li>
						<li><a href="../predict/ptmTimeInfo.jsp">手动预处理查询</a></li>
						<li><a href="../predict/ptmPointInfo.jsp">自动预处理查询</a></li>
						<!-- <li><a href="../predict/autoPtmInfo.jsp">自动预处理对比</a></li> -->
					</ul></li>

				<li><a href="#"><i class="fa fa-hand-o-right fa-fw"></i>
						单点预测<span class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
						<li><a href="../predict/autoPreInfo.jsp">自动预测</a></li>
						<li><a href="../predict/singlePre.jsp" title="专家模式">手动预测</a></li>
					</ul></li>
					
				<li><a href="#"><i class="fa fa-history fa-fw"></i> 历史特征值<span
						class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
						<!-- <li><a href="../predict/getConfHis">参数配置</a></li> -->
						<li><a href="../predict/hisValInfo.jsp">历史特征值查询</a></li>
						<li><a href="../predict/hisValGraph.jsp">历史特征图</a></li>
						<!-- <li><a href="../predict/hisValGraph2.jsp">历史特征图2</a></li> -->
					</ul></li>
				
				<li><a href="../predict/errorAnalysis.jsp"><i
						class="fa fa-star-o fa-fw"></i> 误差分析</a></li>
						
				<li><a href="../conf/conf.jsp"><i
						class="fa fa-gears fa-fw"></i> 阈值参数配置</a></li>

				<li><a href="#"><i class="fa fa-desktop fa-fw"></i> 预处理模型配置<span
						class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
						<li><a href="../pages/ptmmodelinfo">所有模型</a></li>
						<li><a href="../pages/addptmmodel.jsp">添加模型</a></li>
					</ul></li>
				<li><a href="#"><i class="fa fa-gear fa-fw"></i> 预处理测点配置<span
						class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
						<li><a href="../pages/showptmpoint">所有测点</a></li>
						<li><a href="../pages/addptmpointInput">添加测点</a></li>
					</ul></li>

				<li><a href="#"><i class="fa fa-desktop fa-fw"></i> 预测模型配置<span
						class="fa arrow"></span></a>
					<ul class="nav nav-third-level">
						<li><a href="../pages/premodelinfo">所有模型</a></li>
						<li><a href="../pages/addpremodel.jsp">添加模型</a></li>
					</ul></li>
				<li><a href="#"><i class="fa fa-gear fa-fw"></i> 预测测点配置<span
						class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
						<li><a href="../pages/showprepoint">所有测点</a></li>
						<li><a href="../pages/addprepointInput">添加测点</a></li>
					</ul></li>
			</ul>
		</div>
		<!-- /.sidebar-collapse -->
	</div>
	<!-- /.navbar-static-side -->
</nav>
