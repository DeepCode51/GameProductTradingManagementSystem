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
	<title>饰品增加</title>
	<link rel="stylesheet" type="text/css" href="bs/css/bootstrap.css">
	<script type="text/javascript" src="bs/js/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="bs/validform/style.css">
	<script type="text/javascript" src="bs/validform/Validform_v5.3.2_min.js"></script>
	<script type="text/javascript" src="js/admin/bookManage/bookAdd.js"></script>
	<style type="text/css">
		body{
			margin:0;
			padding:0;
			background:#eee;
		}
	</style>
</head>
<body>
	<div class="container">
		<c:if test="${!empty bookMessage}">
			<h3 class="text-center">${bookMessage}</h3>
		</c:if>
		<h2 class="text-center">饰品增加</h2>
		<form id="bookAddForm" class="form-horizontal" action="jsp/admin/BookManageServlet?action=add" method="post" enctype="multipart/form-data">
			<div class="form-group">
				<label for="bookName" class="col-sm-2 col-sm-offset-2 control-label">饰品名称</label>
				<div class="col-sm-4 ">
					<input type="text" name="bookName" id="bookName" class="form-control"/>
				</div>
				<div class="col-sm-4 Validform_checktip"></div>
			</div>
			<div class="form-group">
				<label for="catalog" class="col-sm-2 col-sm-offset-2 control-label">饰品类型</label>
				<div class="col-sm-4">
					<select name="catalog" id="catalog" class="form-control">
						<option value="">==请选择饰品类型==</option>
						<c:if test="${!empty catalog}">
							<c:forEach items="${catalog}" var="i" >
								<option value="${i.catalogId}">${i.catalogName}</option>
							</c:forEach>
						</c:if>
					</select>
				</div>
				<div class="col-sm-4 Validform_checktip"></div>
			</div>
			<div class="form-group">
				<label for="author" class="col-sm-2 col-sm-offset-2 control-label">饰品品质</label>
				<div class="col-sm-4 ">
					<input type="text" name="author" id="author" class="form-control"/>
				</div>
				<div class="col-sm-4 Validform_checktip"></div>
			</div>
			<div class="form-group">
				<label for="press" class="col-sm-2 col-sm-offset-2 control-label">饰品类别</label>
				<div class="col-sm-4 ">
					<input type="text" name="press" id="press" class="form-control"/>
				</div>
				<div class="col-sm-4 Validform_checktip"></div>
			</div>
			<div class="form-group">
				<label for="price" class="col-sm-2 col-sm-offset-2 control-label">价格</label>
				<div class="col-sm-4">
					<input type="text" name="price" id="price" class="form-control">
				</div>
				<div class="col-sm-4 Validform_checktip"></div>
			</div>
			<div class="form-group">
				<label for="bookImg" class="col-sm-2 col-sm-offset-2 control-label">图片上传</label>
				<div class="col-sm-4">
					<input type="file" name="bookImg" id="bookImg">
				</div>
				<div class="col-sm-4 Validform_checktip"></div>
			</div>

			<div class="form-group">
				<label for="desc" class="col-sm-2 col-sm-offset-2 control-label">备注</label>
				<div class="col-sm-4">
					<textarea rows="3" name="desc" id="desc" class="form-control"></textarea>
				</div>
				<div class="col-sm-4 Validform_checktip">*选填</div>
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
