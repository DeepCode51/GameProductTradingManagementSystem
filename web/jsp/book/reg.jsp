<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	pageContext.setAttribute("basePath", basePath);
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<base href="${basePath}">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>CSGO游戏交易</title>
	<link rel="stylesheet" href="bs/css/bootstrap.css">
	<script type="text/javascript" src="bs/js/jquery.min.js"></script>
	<script type="text/javascript" src="bs/js/bootstrap.js"></script>
	<link rel="stylesheet" type="text/css" href="bs/validform/style.css">
	<script type="text/javascript" src="bs/validform/Validform_v5.3.2_min.js"></script>
	<script type="text/javascript" src="js/book/user_reg_login.js"></script>
	<script type="text/javascript" src="js/book/landing.js"></script>
	<link href="css/book/head_footer.css" rel="stylesheet" type="text/css">
	<link href="css/book/user_reg_login.css" rel="stylesheet" type="text/css">

</head>
<body>
<c:if test="${!empty infoList}">
	<c:forEach items="${infoList}" var="i">
		<script type="text/javascript">
			alert("${i}")
		</script>
	</c:forEach>
</c:if>
	<div class="container-fullid">
		<%@include file="header.jsp" %>
		<div class="wrapper">
			<!-- main start -->
			<div class="main container">
				<div class="title">
					<ul class="nav nav-tabs" id="myTab">
						<li role="presentation" class="active"><a href="#tab_login" data-toggle="tab">登&nbsp&nbsp录</a></li>
					  	<li role="presentation" ><a href="#tab_reg" data-toggle="tab">新 用 户 点 此 注 册</a></li>

					</ul>
				</div>
				<!-- 注册表单 -->
				<div id="myTabContent" class="tab-content">
					<div id="tab_reg" class="tab-pane fade">
						<form id="regForm" action="UserServlet?action=reg" method="post" class="form-horizontal">
							<div class="form-group">
								<label for="userName" class="col-md-2  control-label">用户名：</label>
								<div class="col-md-6">
									<input name="userName" id="userName" type="text" class="form-control" >
								</div>
								<div class="col-md-4">
									<span class="Validform_checktip">*用户名以字母开头，4-8个字符</span>
								</div>
							</div>
							<div class="form-group">
								<label for="passWord" class="col-md-2 control-label">密码：</label>
								<div class="col-md-6">
									<input type="password" name="passWord" id="passWord" class="form-control">
								</div>
								<div class="col-md-4">
									<span class="Validform_checktip">*密码为4~8位字符</span>
								</div>
							</div>
							<div class="form-group">
								<label for="c_passWord" class="col-md-2 control-label">确认密码：</label>
								<div class="col-md-6">
									<input type="password" name="c_passWord" id="c_passWord" class="form-control">
								</div>
								<div class="col-md-4">
									<span class="Validform_checktip"></span>
								</div>
							</div>
							<div class="form-group">
								<label for="passWord" class="col-md-2 control-label">支付密码：</label>
								<div class="col-md-6">
									<input type="password" name="payPwd" id="payPwd" class="form-control">
								</div>
								<div class="col-md-4">
									<span class="Validform_checktip">*密码为4~8位字符</span>
								</div>
							</div>
							<div class="form-group">
								<label for="name" class="col-md-2 control-label">姓名：</label>
								<div class="col-md-6">
									<input type="text" id="name" name="name" class="form-control">
								</div>
								<div class="col-md-4">
									<span class="Validform_checktip">*姓名为2~8位字符</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label">性别：</label>
								<div class="col-md-6 ">
									<label class="radio-inline">
										<input type="radio" name="sex" id="sex" class="pr1" value="男">男
									</label>
									<label class="radio-inline">
										<input type="radio" name="sex"  class="pr1"  value="女">女
									</label>
								</div>
								<div class="col-md-4">
									<label>
										<span class="Validform_checktip">*必选</span>
									</label>
								</div>
							</div>
							<div class="form-group">
								<label for="age" class="col-md-2 control-label">年龄：</label>
								<div class="col-md-6">
									<input type="text" id="age" name="age" class="form-control">
								</div>
								<div class="col-md-4">
									<span class="Validform_checktip">*必填</span>
								</div>
							</div>
							<div class="form-group">
								<label for="tell" class="col-md-2 control-label">电话：</label>
								<div class="col-md-6">
									<input type="text" id="tell" name="tell" class="form-control">
								</div>
								<div class="col-md-4">
									<span class="Validform_checktip">*必填</span>
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-md-2 control-label">地址：</label>
								<div class="col-md-6">
									<input type="text" id="address" name="address" class="form-control">
								</div>
								<div class="col-md-4">
									<span class="Validform_checktip">*必填</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2  control-label col-md-offset-2">
									<input class="btn btn-success btn-block" type="submit" value="注册">
								</label>
								<label class="col-md-2 control-label">
									<input class="btn btn-warning btn-block" type="reset" value="重置">
								</label>
							</div>
						</form>
					</div>
					<!-- 登录表单 -->
					<div id="tab_login" class="tab-pane fade in active">
						<form id="loginForm" action="UserServlet?action=login" method="post" class="form-horizontal">
							<div class="form-group">
								<label for="l_userName" class="col-md-4 control-label">用户名：</label>
								<div class="col-md-6">
									<input name="userName" id="l_userName" type="text" class="form-control" >
									<span class="Validform_checktip">&nbsp</span>
								</div>

							</div>
							<div class="form-group">
								<label for="l_passWord" class="col-md-4  control-label">密码：</label>
								<div class="col-md-6">
									<input type="password" name="passWord" id="l_passWord" class="form-control">
									<span class="Validform_checktip">&nbsp</span>
								</div>

							</div>

							<div class="form-group">
								<label for="ck_code" class="col-md-4  control-label">验证码：</label>
								<div class="col-md-3" >
									<input class="form-control" type="text" name="code" id="ck_code" >
									<span class="Validform_checktip">&nbsp</span>
								</div>

								<div class="col-md-4" style="padding:0;">
									<img class="col-md-8" id="imgCode" src="CodeServlet?action=code" alt="" style="padding:0;width:100px;height:38px;" />
									<span onclick="reCode()" class="col-md-4 glyphicon glyphicon-refresh " aria-hidden="true" style="padding:0 0 0 5px;font-size: 24px;"></span>
								</div>

							</div>
							<div class="form-group">
								<label class="col-md-2 control-label col-md-offset-4">
									<input class="btn btn-success btn-block" type="submit" value="登录">
								</label>
								<label class="col-md-2 control-label">
									<input class="btn btn-warning btn-block" type="reset" value="重置">
								</label>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>

		<%@include file="footer.jsp" %>
	</div>


<script type="text/javascript">
/* tab标签显示控制通过url参数 */
	var ur=location.href;
	var para=ur.split('?')[1];
	var type="login";//默认
	if(para!=null){
		type=para.split("type=")[1];
	}
	switch (type){
	   case 'reg':
	       $('#myTab a[href="#tab_reg"]').tab('show')
	       break;
	   case 'login':
	       $('#myTab a[href="#tab_login"]').tab('show')
	       break;
	}

	//验证码
	function reCode(){
		$("#imgCode").prop("src","CodeServlet?action=code&"+new Date().getTime());
	}

</script>
</body>
</html>
