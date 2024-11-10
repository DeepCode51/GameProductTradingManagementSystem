<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()  + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <base href="${basePath}">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>饰品详情页</title>
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

<div class="container-fullid">
    <%@include file="header.jsp" %>
    <div class="wrapper">
        <div class="main container">
            <h2>订单详情</h2>
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
                        <c:if test="${order.orderStatus eq 1 }"><span
                                style="background:red;color:#fff;">已提交</span></c:if>
                        <c:if test="${order.orderStatus eq 2 }"><span
                                style="background:green;color:#fff;">已发货</span></c:if>
                        <c:if test="${order.orderStatus eq 3 }"><span>已完成</span></c:if>
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
                                             src="${i.book.upLoadImg.imgSrc }" alt=""/>
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
        </div>
    </div>
    <%@include file="footer.jsp" %>
</div>
<script type="text/javascript">
</script>
</body>
</html>
