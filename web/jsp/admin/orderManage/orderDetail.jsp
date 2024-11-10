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
	<h2 class="text-center">订单详情</h2>
	<div class="container">
		<div class="row">
			<div class="col-md-2 text-right">订单号</div>
			<div class="col-md-10">${order.orderNum}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">客户id</div>
			<div class="col-md-10">${order.userId}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">客户姓名</div>
			<div class="col-md-10">${order.user.name}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">寄送地址</div>
			<div class="col-md-10">${order.user.address}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">联系方式</div>
			<div class="col-md-10">${order.user.tell}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">订单状态</div>
			<div class="col-md-10">
				<c:if test="${order.orderStatus eq 1 }"><span style="background:red;color:#fff;">已提交</span></c:if>
				<c:if test="${order.orderStatus eq 2 }"><span style="background:green;color:#fff;">已发货</span></c:if>
				<c:if test="${order.orderStatus eq 3 }"><span >已完成</span></c:if>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 text-left">订单商品信息</div>
			<div class="col-md-12">
				<table class="table table-bordered">
					<tr class="info">
						<th class="col-md-6">&nbsp;</th>
						<th class="col-md-2">单价</th>
						<th class="col-md-2">数量</th>
						<th class="col-md-2">金额</th>
					</tr>
					<c:forEach items="${order.oItem}" var="i" varStatus="vs">
						<tr class="pro-list">
							<td><img width="50px" class="img-responsive col-md-2"
								src="${i.book.upLoadImg.imgSrc }" alt="" />
								<div class="col-md-8">
									<p>${i.book.bookName}</p>
									<p>${i.book.author}</p>
									<p>${i.book.press}</p>
								</div>
							</td>
							<td>￥<i>${i.book.price}</i></td>
							<td>${i.quantity}</td>
							<c:choose>
								<c:when test="${vs.first}">
									<td style="border:0;border-left:1px solid #ddd;">￥${order.money }</td>
								</c:when>
								<c:otherwise>
									<td style="border:0;border-left:1px solid #ddd;">&nbsp;</td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</table>

			</div>
		</div>



	</div>
</body>
</html>
