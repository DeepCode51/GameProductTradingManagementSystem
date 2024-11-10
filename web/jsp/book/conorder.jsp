<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
	pageContext.setAttribute("basePath", basePath);
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<base href="${basePath}">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>确认订单</title>
<link rel="stylesheet" href="bs/css/bootstrap.css">
<script type="text/javascript" src="bs/js/jquery.min.js"></script>
<script type="text/javascript" src="bs/js/bootstrap.js"></script>
<script type="text/javascript" src="js/book/landing.js"></script>
<link href="css/book/head_footer.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/book/addcart.js"></script>
<style type="text/css">
	.wrapper .main h3{
		border-bottom: 1px #ccc solid;
		padding:5px 0;
		text-align: center;
	}
	.wrapper .main .info,.wrapper .main .payMethod,.wrapper .main .pro,.wrapper .main .settle{
		margin:25px 0;
	}
	.wrapper .main .row>h4{
		font-size: 22px;
		font-weight: bold;
	}
	.wrapper .main .info .default {
		border:1px #ccc solid;
	}
	.wrapper .main .pro .prolist td{
		height:50px;
		line-height: 50px;
	}
	.wrapper .main .settle .settle-info{
		border:1px #ccc solid;
		padding:20px ;
		font-size:16px;
		margin:0;
	}
	.wrapper .main .settle .settle-info .settle-li>div{
		line-height: 40px;
	}
	.wrapper .main .settle .settle-info span{
		display:inline-block;
		width:120px;
		text-align:right;
	}
	.wrapper .main .settle .settle-info .totprice {
		padding:10px 0;
		font-size: 28px;
	}
	.wrapper .main .settle .settle-info .totprice b{
		color:red;
	}

}
</style>

</head>
<body>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="regForm" action="OrderServlet?action=subOrder" method="post" class="form-horizontal">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					我的收银台
				</h4>
			</div>
			<div class="modal-body">
				<%--支付密码：<input type="password" name="payPwd" id="payPwd" class="form-control">--%>
				<div class="input-group">
					<span class="input-group-addon" id="basic-addon3">支付密码：</span>
					<input type="password" name="payPwd" id="payPwd" class="form-control">
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-lg btn-default" data-dismiss="modal">关闭
				</button>
				<input class="btn btn-lg btn-success pull-right" type="submit" value="支付">
				<%--<a href="OrderServlet?action=subOrder" onclick="subOrder()" class="btn btn-lg btn-success pull-right">提交</a>--%>
				<%--<button type="button" class="btn btn-primary">
					提交
				</button>--%>
			</div>
			</form>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

	<div class="container-fullid">
		<%@include file="header.jsp" %>
		<div class="wrapper">
			<div class="main container">
				<h3>
					<span>确认订单</span>
				</h3>
				<div class="container">
					<div class="info row">
						<h4 class="col-md-12">收货信息:</h4>
						<div class="col-md-3 default">
							<b>姓名：${landing.name }</b>
							<%--<p>${landing.address }</p>--%>
							<p>账号：${landing.tell }</p>
						</div>
					</div>
					<div class="payMethod row">
						<h4 class="col-md-12">支付方式:</h4>
						<label class="radio-inline">
							<input type="radio" name="paymeth" id="inlineRadio1" checked="checked" value="option1">货到付款
						</label>
						<!-- <label class="radio-inline">
						  	<input type="radio" name="paymeth" id="inlineRadio3" value="option3"> 货到付款
						</label> -->
					</div>

					<div class="pro row">
						<h4 class="col-md-12">商品信息</h4>
						<table class="table table-bordered">
							<tr class="info">
								<th class="col-md-6">&nbsp</th>
								<th class="col-md-2">单价</th>
								<th class="col-md-2">数量</th>
								<th class="col-md-2">小计</th>
							</tr>
							<c:forEach items="${shopCart.map}" var="i">
								<tr class="pro-list">
									<td><img width="50px" class="img-responsive col-md-2"
										src="${i.value.book.upLoadImg.imgSrc }" alt="" />
										<div class="col-md-8">
											<a href="bookdetail?bookId=${i.key}">${i.value.book.bookName}</a>
											<p>${i.value.book.author}</p>
											<p>${i.value.book.press}</p>
										</div>
									</td>
									<td>￥<i>${i.value.book.price}</i></td>
									<td>${i.value.quantity}</td>
									<td><b>￥<i>${i.value.subtotal}</i></b></td>
								</tr>
							</c:forEach>
						</table>
					</div>

					<div class="row settle">
						<h4 class="">结算信息</h4>
						<div class=" settle-info row">
							<div class="col-md-4 col-md-offset-8 settle-li">
								<div class="">
									<span>${shopCart.totQuan}件商品总价:</span>
									<span>￥<i>${shopCart.totPrice}</i></span>
								</div>
								<div>
									<span>运费:</span>
									<span>￥0.00</span>
								</div>
								<div>
									<span>优惠:</span>
									<span>-￥0.00</span>
								</div>
								<div class="totprice">
									<span>应付金额:</span>
									<span><b>￥<i>${shopCart.totPrice}</i></b></span>
								</div>
								<div>
									<!-- 按钮触发模态框 -->
									<button class="btn btn-primary btn-lg pull-right" data-toggle="modal" data-target="#myModal">
										提交订单
									</button>
									<%--<a href="OrderServlet?action=subOrder" onclick="subOrder()" class="btn btn-lg btn-success pull-right">提交订单</a>--%>
								</div>
							</div>
						</div>
					</div>


				</div>
			</div>
		</div>

		<%@include file="footer.jsp" %>
	</div>


<script type="text/javascript">
	/* function subOrder(){
		$.get("OrderServlet?action=subOrder",function(data){
			if("ok"==data.status){
				window.location.href="${basePath}jsp/book/ordersuccess.jsp";
			}else{
				alert("订单提交失败，请重新提交")
			}
		},"json")
	} */
</script>
</body>
</html>
