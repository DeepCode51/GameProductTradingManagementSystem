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
	<title>用户修改</title>
	<link rel="stylesheet" href="bs/css/bootstrap.css">
	<script type="text/javascript" src="bs/js/jquery.min.js"></script>
	<script type="text/javascript" src="bs/js/bootstrap.js"></script>
	<link rel="stylesheet" type="text/css" href="bs/validform/style.css">
	<script type="text/javascript" src="bs/validform/Validform_v5.3.2_min.js"></script>
	<script type="text/javascript" src="js/admin/userManage/userEdit.js"></script>
</head>
<body>
	<c:if test="${!empty userMessage}">
		<h3 class="text-center">${userMessage}</h3>
	</c:if>
	<div class="container">
		<h2 class="text-center">用户修改</h2>
		<form id="myForm" action="jsp/admin/CommentManageServlet?action=update" method="post" class="form-horizontal">
			<input type="hidden" name="id" value="${comment.id}">
			<div class="modal-body">
				<div class="input-group">
					<input id="dateId" name="dateId"  type="hidden">
					<input id="itemId" name="itemId"  type="hidden">
					<span class="input-group-addon" id="basic-addon4">评级：</span>
					<select name="start"  class="form-control">
						<option value="★★★★★" <c:if test="${comment.start eq '★★★★★'}">selected="selected"</c:if>>★★★★★</option>
						<option value="★★★★" <c:if test="${comment.start eq '★★★★'}">selected="selected"</c:if>>★★★★</option>
						<option value="★★★" <c:if test="${comment.start eq '★★★'}">selected="selected"</c:if>>★★★</option>
						<option value="★★" <c:if test="${comment.start eq '★★'}">selected="selected"</c:if>>★★</option>
						<option value="★" <c:if test="${comment.start eq '★'}">selected="selected"</c:if>>★</option>
						<select>
				</div>
			</div>

			<div class="modal-body">
				<div class="input-group">
					<span class="input-group-addon" id="basic-addon7">可信度：</span>
					<select name="credible"  class="form-control">
						<option value="100%" <c:if test="${comment.credible eq '100%'}">selected="selected"</c:if>>100%</option>
						<option value="80%" <c:if test="${comment.credible eq '80%'}">selected="selected"</c:if>>80%</option>
						<option value="50%" <c:if test="${comment.credible eq '50%'}">selected="selected"</c:if>>50%</option>
						<option value="20%" <c:if test="${comment.credible eq '20%'}">selected="selected"</c:if>>20%</option>
						<option value="0%" <c:if test="${comment.credible eq '0%'}">selected="selected"</c:if>>0%</option>
						<select>
				</div>
			</div>
			<div class="modal-body">
				<div class="input-group">
					<span class="input-group-addon" id="basic-addon6">发货速度：</span>
					<select name="send"  class="form-control">
						<option value="快" <c:if test="${comment.send eq '快'}">selected="selected"</c:if>>快</option>
						<option value="中" <c:if test="${comment.send eq '中'}">selected="selected"</c:if>>中</option>
						<option value="慢" <c:if test="${comment.send eq '慢'}">selected="selected"</c:if>>慢</option>
						<select>
				</div>
			</div>
			<div class="modal-body">
				<div class="input-group">
					<span class="input-group-addon" id="basic-addon3">内容：</span>
					<textarea rows="5" name="comment" cols="50"  class="form-control" >${comment.comment}</textarea>
				</div>
			</div>

			<div class="form-group">
				<label class="col-md-2  control-label col-md-offset-2">
					<input class="btn btn-success btn-block" type="submit" value="更改">
				</label>
				<label class="col-md-2 control-label">
					<input class="btn btn-warning btn-block" type="reset" value="重置">
				</label>
			</div>
		</form>
	</div>
</body>
</html>
