<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	pageContext.setAttribute("basePath", basePath);
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<base href="${basePath}">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单提交成功</title>
<link rel="stylesheet" href="bs/css/bootstrap.css">
<script type="text/javascript" src="bs/js/jquery.min.js"></script>
<script type="text/javascript" src="bs/js/bootstrap.js"></script>		
<script type="text/javascript" src="js/book/landing.js"></script>
<link href="css/book/head_footer.css" rel="stylesheet" type="text/css">
<style type="text/css">
	.wrapper{
		min-height:500px;
	}
	.wrapper .main .info{
		width:900px;
		margin:70px auto 0;
		padding:50px;
		border:1px #ccc solid;
		font-size: 24px;
	}
	.wrapper .main .info i{
		color:red;
		
	}
	.wrapper .main .info .op{
		width: 300px;
		margin:20px auto 0;
	}
	
}
</style>

</head>
<body>
	<div class="container-fullid">
		<%@include file="header.jsp" %>
		<div class="wrapper">
			<div class="main container">
				<div class="info">
					<div class="row">
						<div class="col-md-4">
							<img src="images/book/corr.png" alt="" />
						</div>
						<div class="col-md-8">
							<p>订单<i>${orderNum}</i>已成功生成，我们会尽快送达！</p>
							<p>订单金额：<i>￥${money}</i></p>
							<p>支付方式：货到付款</p>
						</div>
					</div>
					<div class="op">
						<a class="btn btn-success " href="OrderServlet?action=list">查看我的订单</a>
						<a class="btn btn-default" href="jsp/book/index.jsp">返回首页</a>
					</div>
				</div>
			</div>
		</div>

		<%@include file="footer.jsp" %>
	</div>


<script type="text/javascript">
	
</script>
</body>
</html>