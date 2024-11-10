<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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
	<h2 class="text-center">饰品详情</h2>
	<div class="container">
		<div class="row">
			<div class="col-md-2 text-right">饰品id</div>
			<div class="col-md-10">${bookInfo.bookId}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">饰品名称</div>
			<div class="col-md-10">${bookInfo.bookName}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">饰品分类</div>
			<div class="col-md-10">${bookInfo.catalog.catalogName}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">饰品品质</div>
			<div class="col-md-10">${bookInfo.author}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">类别</div>
			<div class="col-md-10">${bookInfo.press}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">饰品价格</div>
			<div class="col-md-10">￥${bookInfo.price}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">上架日期</div>
			<div class="col-md-10">
				<fmt:formatDate value="${bookInfo.addTime}" pattern="yyyy-MM-dd HH:mm:ss" />
				</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">饰品简介</div>
			<div class="col-md-10">${bookInfo.description}</div>
		</div>
		<div>
			<img class="col-md-6 col-md-offset-2" alt="" src="${bookInfo.upLoadImg.imgSrc}">
		</div>

	</div>
</body>
</html>
