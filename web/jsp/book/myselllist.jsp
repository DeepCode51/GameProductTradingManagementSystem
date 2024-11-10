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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<base href="${basePath}">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的出售</title>
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
<c:if test="${!empty bookMessage}">
	<h3 class="text-center">${bookMessage}</h3>
</c:if>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="regForm" action="CommentServlet?action=add" method="post" class="form-horizontal">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						对此商品评价
					</h4>
				</div>
				<div class="modal-body">
					<div class="input-group">
						<input id="dateId" name="dateId"  type="hidden">
						<input id="itemId" name="itemId"  type="hidden">
						<span class="input-group-addon" id="basic-addon4">评级：</span>
						<select name="start"  class="form-control">
							<option value="★★★★★">★★★★★</option>
							<option value="★★★★">★★★★</option>
							<option value="★★★">★★★</option>
							<option value="★★">★★</option>
							<option value="★">★</option><select>
					</div>
				</div>

				<div class="modal-body">
					<div class="input-group">
						<span class="input-group-addon" id="basic-addon7">可信度：</span>
						<select name="credible"  class="form-control">
							<option value="100%">100%</option>
							<option value="80%">80%</option>
							<option value="50%">50%</option>
							<option value="20%">20%</option>
							<option value="0%">0%</option><select>
					</div>
				</div>
				<div class="modal-body">
					<div class="input-group">
						<span class="input-group-addon" id="basic-addon6">发货速度：</span>
						<select name="send"  class="form-control">
							<option value="快">快</option>
							<option value="中">中</option>
							<option value="慢">慢</option>
							<select>
					</div>
				</div>
				<div class="modal-body">
					<div class="input-group">
						<span class="input-group-addon" id="basic-addon3">内容：</span>
						<textarea rows="5" name="comment" cols="50"  class="form-control" ></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-lg btn-default" data-dismiss="modal">关闭
					</button>
					<input class="btn btn-lg btn-success pull-right" type="submit" value="保存">
				</div>
			</form>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

	<div class="container-fullid">
		<%@include file="header.jsp" %>
		<div class="wrapper">
			<div class="main container">
				<h2 class="text-center">饰品列表</h2>

				<div class="funbtn">
					<a id="batDel" class="btn btn-danger" href="javascript:void(0)" >批量删除</a>
					<a class="btn btn-info" href="BookList?action=addReq">增加饰品</a>
				</div>
				<table class="table table-striped table-hover">
					<tr class="success">
						<th>
							<input type="checkbox" id="batDelChoice">
							<label for="batDelChoice"> 全/反选</label>
						</th>
						<th>饰品名称</th>
						<th>图片</th>
						<th>饰品分类</th>
						<th>品质</th>
						<th>类别</th>
						<th>价格</th>
						<th>上架日期</th>
						<th width="140px;">操作</th>
					</tr>
					<c:choose>
						<c:when test="${!empty bookList}">
							<c:forEach items="${bookList}" var="i" varStatus="n">
								<tr>
									<td class="noClick"><input type="checkbox" name="choice" value="${i.bookId}">
									<td  >${i.bookName}</td>
									<td width="80px">
										<img class="img-responsive" src="${i.upLoadImg.imgSrc}" alt="" />
									</td>
									<td>${i.catalog.catalogName}</td>
									<td>${i.author}</td>
									<td>${i.press}</td>
									<td>${i.price}</td>
									<td>	<fmt:formatDate value="${i.addTime}" pattern="yyyy-MM-dd" /></td>
									<td class="noClick">
										<a class="btn btn-info btn-xs" href="BookList?action=edit&id=${i.bookId}">修改</a>
										<a class="btn btn-danger btn-xs" href="BookList?action=del&id=${i.bookId}" onclick="javascript:return confirm('确定要删除吗？');">删除</a>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="8"><h4 class="text-center">当前无更多饰品信息</h4></td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
				<!-- 分页按钮 -->
				<ul class="pager row">
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
	$(function(){
		$('#myModal').modal("hide");
	});
	function values(ID,itemId){
		$('#dateId').val(ID);
		$('#itemId').val(itemId);
	}
</script>
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
		window.location="${bsePath}BookList?action=mySellList&page=1";
	})
	$(".prePage").click(function(){
		window.location="${basePath}BookList?action=mySellList&page=${pageBean.prePage}";
	})
	$(".nextPage").click(function(){
		window.location="${basePath}BookList?action=mySellList&page=${pageBean.nextPage}";
	})
	$(".lastPage").click(function(){
		window.location="${basePath}BookList?action=mySellList&page=${pageBean.pageCount}";
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
			window.location="${basePath}BookList?action=mySellList&page="+page;
		}
	})



	//批量选中
	$("#batDelChoice").change(function(){
		if(!$("input[name='choice']").prop("checked")){
			$(this).prop("checked",true);
			$("input[name='choice']").prop("checked",true);

		}else{
			$(this).removeProp("checked");
			$("input[name='choice']").removeProp("checked");
		}
	})


	//批量删除
	$("#batDel").click(function(){
		var choices=$("input:checked[name='choice']");
		var ids="";
		for(i=0;i<choices.length;i++){
			if(i!=choices.length-1){
				ids+=choices.eq(i).val()+",";
			}else{
				ids+=choices.eq(i).val();
			}
		}
		if(ids==""){
			alert("请勾选要删除的内容！");
			return;

		}
		if(confirm("你确定要删除"+choices.length+"条用户吗？")){

			window.location = "${basePath}BookList?action=batDel&ids="+ids;

		}
	})


</script>
</body>
</html>
