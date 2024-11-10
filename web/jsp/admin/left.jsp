<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>在此处插入标题</title>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<link rel="stylesheet" href="css/admin/left.css">
</head>
<body>
	<div class="nowTime"></div>
	<div class="left">
		<ul>
			<li class="list">
				<h3>用 户 管 理</h3>
				<ul>
					<li><a href="jsp/admin/AdminManageServlet?action=list" target="rFrame">管理员管理</a></li>
					<li><a href="jsp/admin/UserManageServlet?action=list" target="rFrame">用户管理</a></li>
					<li><a href="jsp/admin/CommentManageServlet?action=list" target="rFrame">评价管理</a></li>
				</ul>
			</li>
			<li class="list">
				<h3>饰 品 管 理</h3>
				<ul>
					<li><a href="jsp/admin/BookManageServlet?action=list" target="rFrame">饰品列表</a></li>
					<li><a href="jsp/admin/CatalogServlet?action=list" target="rFrame">分类管理</a></li>
				</ul>
			</li>

			<li class="list">
				<h3>订 单 管 理</h3>
				<ul>
					<li><a href="jsp/admin/OrderManageServlet?action=list" target="rFrame">订单列表</a></li>
					<%--<li><a href="jsp/admin/OrderManageServlet?action=processing" target="rFrame">订单处理</a></li>--%>
				</ul>
			</li>

		</ul>
	</div>
	<script type="text/javascript">
	/* 菜单切换展开 */
		$(".list h3").next().slideUp(300);
		$(".list h3").click(function(){
			$(".list h3").css("color","#fff");
			$(".list h3").next().slideUp(300);
			if($(this).next().css("display")=="none"){
				$(this).css("color","#bc0a6b");
				$(this).next().slideDown(300);
			}else{
				$(this).next().slideUp(300);
			}
		})

		$(".list ul a").click(function(){
			$(".list ul a").css("color","#000");
			$(this).css("color","#bc0a6b");
		})
		/* 计时器 */
		function showTime() {
					var now = new Date();
					var time = now.toLocaleDateString() + " " + now.toLocaleTimeString();
					document.getElementsByClassName('nowTime')[0].innerHTML = time;
		}
		setInterval(showTime, 1000);

	</script>
</body>
</html>
