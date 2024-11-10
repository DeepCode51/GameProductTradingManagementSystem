<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html>
<head>
    <base href="${basePath}">
    <meta charset="UTF-8">
    <title>评价管理</title>
    <link rel="stylesheet" href="bs/css/bootstrap.css">
    <script type="text/javascript" src="bs/js/jquery.min.js"></script>
    <script type="text/javascript" src="bs/js/bootstrap.js"></script>
    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            background: #eee;
        }

        .content {
            padding: 20px 40px 0 40px;
        }

        .page-div {
            display: inline-block;
            width: 110px;
        }

        .homePage, .prePage, .page-go, .nextPage, .lastPage {
            border-radius: 15px;
            color: #337ab7;
        }

        .pager {
            padding: 0 20px;
        }

        #page-input {
            display: inline-block;
            width: 60px;
            border-radius: 10px;
        }

        .bookImg {
            width: 50px;
        }

        .funbtn {
            margin: 10px 0;
        }

        .funbtn a {
            margin-right: 10px;
        }

        /* .table #desc{
            display:inline-block;
            width:300px;
            word-break:keep-all;
            white-space:nowrap;
            overflow:hidden;
            text-overflow:ellipsis;
        } */
    </style>

</head>
<body>
<c:if test="${!empty commentMessage}">
    <h3 class="text-center">${commentMessage}</h3>
</c:if>


<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form id="regForm" action="${basePath}/jsp/admin/CommentManageServlet?action=editUser" method="post" class="form-horizontal">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        对此卖家信用评价
                    </h4>
                </div>
                <input id="userId" name="userId" type="hidden">
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon" id="basic-addon4">信用等级：</span>
                        <select name="start" class="form-control">
                            <option value="★★★★★">★★★★★</option>
                            <option value="★★★★">★★★★</option>
                            <option value="★★★">★★★</option>
                            <option value="★★">★★</option>
                            <option value="★">★</option>
                        </select>
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

<h2 class="text-center">评价列表</h2>
<div class="container-fulid content">
    <div class="funbtn">
        <a id="batDel" class="btn btn-danger" href="javascript:void(0)">批量删除</a>
    </div>
    <table class="table table-striped table-hover">
        <tr class="success">
            <th>
                <input type="checkbox" id="batDelChoice">
                <label for="batDelChoice"> 全/反选</label>
            </th>
            <th>饰品名称</th>
            <th>卖家用户名</th>
            <th>买家用户名</th>
            <th>评级</th>
            <th>可信度</th>
            <th>发货速度</th>
            <th>内容</th>
            <th width="160px;">操作</th>
        </tr>
        <c:choose>
            <c:when test="${!empty commentList}">
                <c:forEach items="${commentList}" var="i" varStatus="n">
                    <tr>
                        <td class="noClick"><input type="checkbox" name="choice" value="${i.id}">
                        <td>${i.book.bookName}</td>
                        <td>${i.book.sellUserName}</td>
                        <td>${i.user.name}</td>
                        <td>${i.start}</td>
                        <td>${i.credible}</td>
                        <td>${i.send}</td>
                        <td>${i.comment}</td>
                        <td class="noClick">
                            <a data-toggle="modal" data-target="#myModal" onclick="values('${i.book.sellUserName}', ${i.book.sellUserId})">卖家信用
                            </a>
                            <a class="btn btn-info btn-xs" href="jsp/admin/CommentManageServlet?action=edit&id=${i.id}">修改</a>
                            <a class="btn btn-danger btn-xs" href="jsp/admin/CommentManageServlet?action=del&id=${i.id}"
                               onclick="javascript:return confirm('确定要删除吗？');">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="8"><h4 class="text-center">当前无更多评价信息</h4></td>
                </tr>
            </c:otherwise>
        </c:choose>
    </table>

    <ul class="pager">
        <li>
            <button class="homePage btn btn-default btn-sm">首页</button>
        </li>
        <li>
            <button class="prePage btn btn-sm btn-default">上一页</button>
        </li>
        <li>总共 ${pageBean.pageCount} 页 | 当前 ${pageBean.curPage} 页</li>
        <li>
            跳转到
            <div class="input-group form-group page-div">
                <input id="page-input" class="form-control input-sm" type="text" name="page"/>
                <span>
					<button class="page-go btn btn-sm btn-default">GO</button>
				</span>
            </div>
        </li>
        <li>
            <button class="nextPage btn btn-sm btn-default">下一页</button>
        </li>
        <li>
            <button class="lastPage btn btn-sm btn-default">末页</button>
        </li>
    </ul>
</div>

<script type="text/javascript">
    $(function () {
        $('#myModal').modal("hide");
    });

    function values(sellUserName,ID) {
        console.log(ID)
        $('#userId').val(ID);
        $("#myModalLabel").html("对此[ "+sellUserName+" ]卖家信用评价")
    }
</script>
<script type="text/javascript">
    //按钮禁用限制
    if ("${pageBean.curPage}" == 1) {
        $(".homePage").attr("disabled", "disabled");
        $(".prePage").attr("disabled", "disabled");
    }
    if ("${pageBean.curPage}" == "${pageBean.pageCount}") {
        $(".page-go").attr("disabled", "disabled");
        $(".nextPage").attr("disabled", "disabled");
        $(".lastPage").attr("disabled", "disabled");
    }
    //按钮事件
    $(".homePage").click(function () {
        window.location = "${bsePath}jsp/admin/CommentManageServlet?action=list&page=1";
    })
    $(".prePage").click(function () {
        window.location = "${basePath}jsp/admin/CommentManageServlet?action=list&page=${pageBean.prePage}";
    })
    $(".nextPage").click(function () {

        window.location = "${basePath}jsp/admin/CommentManageServlet?action=list&page=${pageBean.nextPage}";
    })
    $(".lastPage").click(function () {

        window.location = "${basePath}jsp/admin/CommentManageServlet?action=list&page=${pageBean.pageCount}";
    })
    //控制页面跳转范围
    $(".page-go").click(function () {
        var page = $("#page-input").val();
        var pageCount =${pageBean.pageCount};
        if (isNaN(page) || page.length <= 0) {
            $("#page-input").focus();
            alert("请输入数字页码");
            return;
        }
        if (page > pageCount || page < 1) {
            alert("输入的页码超出范围！");
            $("#page-input").focus();
        } else {
            window.location = "${basePath}jsp/admin/CommentManageServlet?action=list&page=" + page;
        }
    })

    //批量选中
    $("#batDelChoice").change(function () {
        if (!$("input[name='choice']").prop("checked")) {
            $(this).prop("checked", true);
            $("input[name='choice']").prop("checked", true);

        } else {
            $(this).removeProp("checked");
            $("input[name='choice']").removeProp("checked");
        }
    })

    //批量删除
    $("#batDel").click(function () {
        var choices = $("input:checked[name='choice']");
        var ids = "";
        for (i = 0; i < choices.length; i++) {
            if (i != choices.length - 1) {
                ids += choices.eq(i).val() + ",";
            } else {
                ids += choices.eq(i).val();
            }
        }
        if (ids == "") {
            alert("请勾选要删除的内容！");
            return;
        }
        if (confirm("你确定要删除" + choices.length + "条评论吗？")) {
            window.location = "${basePath}jsp/admin/CommentManageServlet?action=batDel&ids=" + ids;
        }
    })


</script>
</body>
</html>
