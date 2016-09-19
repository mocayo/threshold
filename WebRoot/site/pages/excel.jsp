<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>从文件中上传测点</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
</head>

<style>
.file {
	position: relative;
	display: inline-block;
	background: #D0EEFF;
	border: 1px solid #99D3F5;
	border-radius: 4px;
	padding: 4px 12px;
	overflow: hidden;
	color: #1E88C7;
	text-decoration: none;
	text-indent: 0;
	line-height: 20px;
}

.file input {
	position: absolute;
	font-size: 100px;
	right: 0;
	top: 0;
	opacity: 0;
}

.file:hover {
	background: #AADFFD;
	border-color: #78C3F3;
	color: #004974;
	text-decoration: none;
}

</style>

<body>
	<!-- <form action="modelAdmin/pages/addpreexcel" method="post" enctype="multipart/form-data">
	<input type="file" name="pointExcel" /><br>
	<input type="submit" value="submit"/>
  </form> -->
	<form action="modelAdmin/pages/addpreexcel" method="post" enctype="multipart/form-data">
	<a href="javascript:;" class="file"> 选择文件 
	<input type="file"	name="" id="">
	</a>
	<br><br>
	<button type="button" class="btn btn-info">Info</button>
  </form>

</body>
</html>
