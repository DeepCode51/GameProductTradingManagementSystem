<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网上书店登录</title>
<link rel="stylesheet" type="text/css" href="css/login/login.css" />
<script type="text/javascript">
	function checkForm(){
		var userName=document.getElementById("userName");
		var passWord=document.getElementById("passWord");
		if(userName.value.length<=0){
			alert("请输入用户名！");
			userName.focus();
			return false;
		}
		if(passWord.value.length<=0){
			alert("请输入密码！");
			passWord.focus();
			return false;
		}
		return true;
	}
</script>
</head>
<body>
<c:if test="${!empty infoList}">
	<c:forEach items="${infoList}" var="i">
		<script type="text/javascript">
			alert("${i}")
		</script>
	</c:forEach>
</c:if>


	<h1 id="title">
		CSGO游戏交易后台管理系统&nbsp;<sup style="color: red;font-size:14px;"><%=application.getInitParameter("ver")%></sup>
	</h1>
	<div id="login">
		<form action="jsp/admin/LoginServlet" method="post"
			onsubmit="javascript:return checkForm()">
			<p>
				<input type="text" name="userName" id="userName" placeholder="用户名">
			</p>
			<p>
				<input type="password" name="passWord" id="passWord"
					placeholder="密码">
			</p>
			<p>
				<input type="submit" id="submit" value="登 录">
			</p>
		</form>
	</div>
</body>
</html>
