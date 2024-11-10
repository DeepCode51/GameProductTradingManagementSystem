<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        .wrapper {
            min-height: 500px;
        }

        /* 分页样式 */
        .wrapper .pager {
            border-top: 1px #eee solid;
            padding-top: 15px;
        }

        .wrapper .pager .page-div {
            display: inline-block;
            width: 110px;
        }

        .wrapper .homePage, .wrapper .prePage, .wrapper .page-go, .wrapper .nextPage, .lastPage {
            border-radius: 15px;
            color: #d7006d;
        }


        .wrapper #page-input {
            display: inline-block;
            width: 60px;
            border-radius: 10px;
        }

        .wrapper .bookImg {
            width: 50px;
        }

        .wrapper .funbtn {
            margin: 10px 0;
        }

        .wrapper .funbtn a {
            margin-right: 10px;
        }


    </style>

</head>
<body>
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
                        <input id="dateId" name="dateId" type="hidden">
                        <input id="itemId" name="itemId" type="hidden">
                        <span class="input-group-addon" id="basic-addon4">评级：</span>
                        <select name="start" class="form-control">
                            <option value="★★★★★">★★★★★</option>
                            <option value="★★★★">★★★★</option>
                            <option value="★★★">★★★</option>
                            <option value="★★">★★</option>
                            <option value="★">★</option>
                        </select>
                    </div>
                </div>

                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon" id="basic-addon7">可信度：</span>
                        <select name="credible" class="form-control">
                            <option value="100%">100%</option>
                            <option value="80%">80%</option>
                            <option value="50%">50%</option>
                            <option value="20%">20%</option>
                            <option value="0%">0%</option>
                        </select>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon" id="basic-addon6">发货速度：</span>
                        <select name="send" class="form-control">
                            <option value="快">快</option>
                            <option value="中">中</option>
                            <option value="慢">慢</option>
                        </select>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon" id="basic-addon3">内容：</span>
                        <textarea rows="5" name="comment" cols="50" class="form-control"></textarea>
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
            <h2>全部订单</h2>
            <table class="table table-bordered">
                <thead>
                <tr class="info">
                    <th>商品</th>
                    <th>单价</th>
                    <th>数量</th>
                    <th>商品操作</th>
                    <th>实付款</th>
                    <th>交易状态</th>
                </tr>
                </thead>
                <c:choose>
                    <c:when test="${!empty orderList }">
                        <c:forEach items="${orderList}" var="order" varStatus="varstatus">
                            <c:if test="${!varstatus.first}">
                                <tbody>
                                <tr>
                                    <td colspan="6">&nbsp;</td>
                                </tr>
                                </tbody>
                            </c:if>
                            <tbody style="margin-top: 10px;">
                            <tr class="active">
                                <td>订单日期：${order.orderDate}</td>
                                <td colspan="5">订单号：${order.orderNum }</td>
                            </tr>
                            <c:forEach items="${order.oItem}" var="oit" varStatus="vs">
                                <tr>
                                    <td>
                                        <img class="img-responsive col-md-2" src="${oit.book.upLoadImg.imgSrc}" alt=""/>
                                        <span class="col-md-10">${oit.book.bookName}</span>
                                    </td>
                                    <td>￥${oit.book.price}</td>
                                    <td>${oit.quantity}</td>
                                    <td>
                                        <c:if test="${oit.isComment eq 0}">
                                            <a data-toggle="modal" data-target="#myModal"
                                               onclick="values(${oit.book.bookId},${oit.itemId})">
                                                评价
                                            </a>
                                        </c:if>
                                        <c:if test="${oit.isComment eq 1}">
                                            已评价
                                        </c:if>
                                    </td>
                                    <c:choose>
                                        <c:when test="${vs.first}">
                                            <td style="border:0;border-left:1px solid #ddd;">￥${order.money }</td>
                                            <td style="border:0;border-left:1px solid #ddd;">
                                                <c:if test="${order.orderStatus eq 1}"><p>订单已提交</p>
                                                    <p>(等待商家发货)</p></c:if>
                                                <c:if test="${order.orderStatus eq 2}">
                                                    <p>已发货</p>
                                                    <a href="OrderServlet?action=ship&id=${order.orderId}"
                                                       class="btn btn-sm btn-success"
                                                       onclick="confirm('确认收货了么？')">确认收货</a>
                                                </c:if>
                                                <c:if test="${order.orderStatus eq 3}">订单已完成</c:if>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td style="border:0;border-left:1px solid #ddd;">&nbsp</td>
                                            <td style="border:0;border-left:1px solid #ddd;">&nbsp</td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                            </c:forEach>
                            </tbody>

                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tbody>
                        <tr>
                            <td colspan="6"><h2 class="text-center">还莫有订单呢！</h2></td>
                        </tr>
                        </tbody>
                    </c:otherwise>
                </c:choose>
            </table>
            <!-- 分页按钮 -->
            <ul class="pager row">
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
    </div>

    <%@include file="footer.jsp" %>
</div>

<script type="text/javascript">
    $(function () {
        $('#myModal').modal("hide");
    });

    function values(ID, itemId) {
        $('#dateId').val(ID);
        $('#itemId').val(itemId);
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
        window.location = "${bsePath}OrderServlet?action=list&page=1";
    })
    $(".prePage").click(function () {
        window.location = "${basePath}OrderServlet?action=list&page=${pageBean.prePage}";
    })
    $(".nextPage").click(function () {

        window.location = "${basePath}OrderServlet?action=list&page=${pageBean.nextPage}";
    })
    $(".lastPage").click(function () {
        window.location = "${basePath}OrderServlet?action=list&page=${pageBean.pageCount}";
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
            window.location = "${basePath}OrderServlet?action=list&page=" + page;
        }
    })


</script>
</body>
</html>
