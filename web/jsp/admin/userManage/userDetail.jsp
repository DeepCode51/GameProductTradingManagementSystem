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
	<meta charset="UTF-8">
	<title>饰品详情页</title>
	<link rel="stylesheet" href="bs/css/bootstrap.css">
	<style type="text/css">
		body{
			margin:0;
			padding:0;
			background:#eee;
		}

		.container .row{
			line-height: 30px;
			htight:30px;
		}

	</style>
</head>
<body>
	<h2 class="text-center">用户详情</h2>
	<div class="container">
		<div class="row">
			<div class="col-md-2 text-right">用户编号</div>
			<div class="col-md-10">${userInfo.userId}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">用户名</div>
			<div class="col-md-10">${userInfo.userName}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">用户密码</div>
			<div class="col-md-10">${userInfo.userPassWord}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">姓名</div>
			<div class="col-md-10">${userInfo.name}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">性别</div>
			<div class="col-md-10">${userInfo.sex}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">年龄</div>
			<div class="col-md-10">￥${userInfo.age}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">电话</div>
			<div class="col-md-10">${userInfo.tell}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">地址</div>
			<div class="col-md-10">${userInfo.address}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">启用状态</div>
			<div class="col-md-10">
				<c:choose>
					<c:when test="${i.enabled eq 'y'}">
						<span style="background:green;color:#fff">启用</span>
					</c:when>
					<c:otherwise>
						<span style="background:red;color:#fff">禁用</span>
					</c:otherwise>
				</c:choose>
			</div>
		</div>


	</div>
</body>
</html>
