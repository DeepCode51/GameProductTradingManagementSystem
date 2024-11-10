<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();  
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>用户增加</title>
	<link rel="stylesheet" href="bs/css/bootstrap.css">
	<script type="text/javascript" src="bs/js/jquery.min.js"></script>
	<script type="text/javascript" src="bs/js/bootstrap.js"></script>
	<link rel="stylesheet" type="text/css" href="bs/validform/style.css">
	<script type="text/javascript" src="bs/validform/Validform_v5.3.2_min.js"></script> 
	<script type="text/javascript" src="js/admin/adminManage/adminAdd.js"></script>
	<style type="text/css">
		body{
			margin:0;
			padding:0;
			background:#eee;
		}
	</style>
</head>
<body>
	<c:if test="${!empty adminMessage}">
		<h3 class="text-center">${adminMessage}</h3>
	</c:if>
	<div class="container">
		
		<h2 class="text-center">用户增加</h2>
		<form id="myForm" action="jsp/admin/AdminManageServlet?action=add" method="post" class="form-horizontal"">
				<div class="form-group">
					<label for="userName" class="col-sm-2 col-sm-offset-2 control-label">用户名：</label>
					<div class="col-sm-4">
						<input name="userName" id="userName" type="text" class="form-control" >
					</div>
					<div class="col-sm-4">
						<span class="Validform_checktip">用户名以字母开头，4-8个字符</span>
					</div>
				</div>
				<div class="form-group">
					<label for="passWord" class="col-sm-2 col-sm-offset-2 control-label">密码：</label>
					<div class="col-sm-4">
						<input type="password" name="passWord" id="passWord" class="form-control">
					</div>
					<div class="col-sm-4">
						<span class="Validform_checktip">密码为4~8位字符</span>
					</div>
				</div>
				<div class="form-group">
					<label for="c_passWord" class="col-sm-2 col-sm-offset-2 control-label">确认密码：</label>
					<div class="col-sm-4">
						<input type="password" name="c_passWord" id="c_passWord" class="form-control">
					</div>
					<div class="col-sm-4">
						<span class="Validform_checktip"></span>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 col-sm-offset-2 control-label">姓名：</label>
					<div class="col-sm-4">
						<input type="text" id="name" name="name" class="form-control">
					</div>
					<div class="col-sm-4">
						<span class="Validform_checktip">姓名为2~8位字符</span>
					</div>
				</div>
			
				<div class="form-group">
					<label class="col-sm-2 col-sm-offset-2 control-label">
						<input class="btn btn-success btn-block" type="submit" value="提交">
					</label>
					<label class="col-sm-2 control-label">
						<input class="btn btn-warning btn-block" type="reset" value="重置">
					</label>
					
				</div>
		</form>
	</div>
</body>
</html>