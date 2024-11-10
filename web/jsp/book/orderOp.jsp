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
<title>我的订单</title>
<link rel="stylesheet" href="bs/css/bootstrap.css">
<script type="text/javascript" src="bs/js/jquery.min.js"></script>
<script type="text/javascript" src="bs/js/bootstrap.js"></script>
<script type="text/javascript" src="js/book/landing.js"></script>
<link href="css/book/head_footer.css" rel="stylesheet" type="text/css">
<style type="text/css">
	.wrapper{
		min-height:500px;
	}
	/* 分页样式 */
	.wrapper .pager{
		border-top:1px #eee solid;
		padding-top:15px;
	}
	.wrapper .pager .page-div{
		display: inline-block;
		width:110px;
	}
	.wrapper .homePage,.wrapper .prePage,.wrapper .page-go,.wrapper .nextPage,.lastPage{
		border-radius:15px;
		color:#d7006d;
	}


	.wrapper #page-input{
		display:inline-block;
		width:60px;
		border-radius: 10px;
	}
	.wrapper .bookImg{
		width:50px;
	}
	.wrapper .funbtn{
		margin:10px 0;
	}
	.wrapper .funbtn a{
		margin-right:10px;
	}


</style>

</head>
<body>

	<div class="container-fullid">
		<%@include file="header.jsp" %>
		<div class="wrapper">
			<div class="main container">
				<h2>待处理订单列表</h2>
				<table class="table table-striped table-hover">
					<tr class="success">
						<th>订单编号</th>
						<th>订单号</th>
						<th>用户id</th>
						<th>订单日期</th>
						<th>金额</th>
						<th>订单状态</th>
						<th>操作</th>
					</tr>
					<c:choose>
						<c:when  test="${!empty orderList}">
							<c:forEach items="${orderList}" var="i">
								<tr>

									<td>${i.orderId}</td>
									<td>${i.orderNum}</td>
									<td>${i.userId}</td>
									<td>${i.orderDate}</td>
									<td>${i.money}</td>
									<td>
										<c:if test="${i.orderStatus eq 1 }"><span style="background:red;color:#fff;">已提交</span></c:if>
										<c:if test="${i.orderStatus eq 2 }"><span style="background:green;color:#fff;">已发货</span></c:if>
										<c:if test="${i.orderStatus eq 3 }"><span >已完成</span></c:if>
									</td>
									<td>
										<a class="btn btn-default btn-sm" href="OrderServlet?action=detail&id=${i.orderId}">详情</a>
										<a class="btn btn-info btn-sm" href="OrderServlet?action=shipDeal&id=${i.orderId}" onclick="return confirm('确定要发货了么！')">发货</a>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="7"><h4 class="text-center">当前无更多订单信息</h4></td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>

				<ul class="pager">
					<li><button class="homePage btn btn-default btn-sm">首页</button></li>
					<li><button class="prePage btn btn-sm btn-default">上一页</button></li>
					<li>总共 ${pageBean.pageCount} 页 | 当前 ${pageBean.curPage} 页</li>
					<li>
						跳转到
						<div class="input-group form-group page-div">
							<input id="page-input" class="form-control input-sm" type="text" name="page"/>
							<span>
					<button  class="page-go btn btn-sm btn-default">GO</button>
				</span>
						</div>
					</li>
					<li><button class="nextPage btn btn-sm btn-default">下一页</button></li>
					<li><button class="lastPage btn btn-sm btn-default">末页</button></li>
				</ul>
			</div>
		</div>

		<%@include file="footer.jsp" %>
	</div>

<script type="text/javascript">

	//按钮禁用限制
	if("${pageBean.curPage}"==1){
		$(".homePage").attr("disabled","disabled");
		$(".prePage").attr("disabled","disabled");
	}
	if("${pageBean.curPage}"=="${pageBean.pageCount}"){
		$(".page-go").attr("disabled","disabled");
		$(".nextPage").attr("disabled","disabled");
		$(".lastPage").attr("disabled","disabled");
	}
	//按钮事件
	$(".homePage").click(function(){
		window.location="${basePath}OrderServlet?action=processing&page=1";
	})
	$(".prePage").click(function(){
		window.location="${basePath}OrderServlet?action=processing&page=${pageBean.prePage}";
	})
	$(".nextPage").click(function(){
		window.location="${basePath}OrderServlet?action=processing&page=${pageBean.nextPage}";
	})
	$(".lastPage").click(function(){
		window.location="${basePath}OrderServlet?action=processing&page=${pageBean.pageCount}";
	})
	//控制页面跳转范围
	$(".page-go").click(function(){
		var page=$("#page-input").val();
		var pageCount=${pageBean.pageCount};
		if(isNaN(page)||page.length<=0){
			$("#page-input").focus();
			alert("请输入数字页码");
			return;
		}
		if(page > pageCount || page < 1 ){
			alert("输入的页码超出范围！");
			$("#page-input").focus();
		}else{
			window.location="${basePath}OrderServlet?action=processing&page="+page;
		}
	})
</script>
</body>
</html>
