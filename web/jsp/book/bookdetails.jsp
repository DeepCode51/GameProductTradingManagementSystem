<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <base href="${basePath}">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>CSGO游戏交易</title>
    <link rel="stylesheet" href="bs/css/bootstrap.css">
    <script type="text/javascript" src="bs/js/jquery.min.js"></script>
    <script type="text/javascript" src="bs/js/bootstrap.js"></script>
    <script type="text/javascript" src="js/book/landing.js"></script>
    <link href="css/book/head_footer.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="js/book/addcart.js"></script>
    <style type="text/css">
        .wrapper .pro_info {
            border-bottom: 1px #ccc solid;
            line-height: 34px;
            margin-top: 20px;
        }

        .wrapper .pro_info tr td:first-child {
            font-weight: bold;

        }

        .wrapper .pro_info i {
            color: red;
            font-size: 22px;
        }

        .wrapper .buy_pro {
            margin-top: 20px;

        }

        .wrapper .pro_desc {
            margin: 10px;
        }

        .wrapper .pro_desc h3 {
            border-bottom: 1px #ccc solid;
            padding: 10px;
        }

        .wrapper .pro_desc div {
            text-indent: 2em;
            line-height: 2em;
        }

    </style>
</head>
<body>

<div class="container-fullid">
    <%@include file="header.jsp" %>
    <div class="wrapper">
        <!-- main start -->
        <div class="main container">
            <div class="sub-nav">
                <ol class="breadcrumb">
                    <li><a href="jsp/book/index.jsp">主页</a></li>
                    <li><a href="#">${bookInfo.catalog.catalogName}</a></li>
                    <li class="active">${bookInfo.bookName}</li>
                </ol>
            </div>
            <div class="row">
                <div class="col-md-5">
                    <img class="img-responsive" src="${bookInfo.upLoadImg.imgSrc}"/>
                </div>
                <div class="col-md-7">
                    <table class="pro_info">
                        <tr>
                            <input name="bookId" id="bookId" type="hidden"  value="${bookInfo.bookId}" >
                            <td colspan="2"><h2>${bookInfo.bookName}</h2></td>
                        </tr>
                        <tr>
                            <td>价格：</td>
                            <td><i>￥${bookInfo.price}</i></td>
                        </tr>
                        <tr>
                            <td>饰品编号：</td>
                            <td>${bookInfo.bookId}</td>
                        </tr>
                        <tr>
                            <td>饰品分类：</td>
                            <td>${bookInfo.catalog.catalogName}</td>
                        </tr>
                        <tr>
                            <td>品质：</td>
                            <td>${bookInfo.author}</td>
                        </tr>
                        <tr>
                            <td>类别：</td>
                            <td>${bookInfo.press}</td>
                        </tr>
                        <tr>
                            <td>类型 ：</td>
                            <td>${bookInfo.description}</td>
                        </tr>
                        <tr>
                            <td>上架日期：</td>
                            <td>${bookInfo.addTime}</td>
                        </tr>

                    </table>
                    <p class="buy_pro">
                        <%--                        <a class="btn btn-info"  id="tosettle" href="#">立即购买</a>--%>
                        <button class="btn btn-info" id="tosettle" href="#">立即购买</button>
                        <button type="button" class="btn btn-danger" onclick="addToCart(${bookInfo.bookId})" data-toggle="modal" data-target=".bs-example-modal-sm">加入购物车
                        </button>
                    </p>
                    <%--					<p>温馨提示：支持7天无理由退货</p>--%>
                </div>
            </div>
        </div>
        <div style="width: 900px;float:right;margin-bottom:40px; ">
            <h4 style="text-align:left;margin-left: 60px;">评价列表</h4>
            <table style="margin-top: 8px; word-break: break-all" bordercolor=#e3e3e3 cellspacing=0
                   bordercolordark=white
                   cellpadding=0 rules=all width="95%" bordercolorlight=#e0e0e0 border=1 class="main">
                <c:choose>
                    <c:when test="${!empty comments}">
                        <c:forEach items="${comments}" var="i" varStatus="n">
                            <tr bgcolor=#f0f0f0 height=30>
                                <td align="center" width="12%" style="font-size: 11px;">${n.count}
                                </td>
                                <td style="font-size: 11px;">
                                    <span style="float:left">【星级】 ${i.start}</span>
                                </td>

                                <td style="font-size: 11px;">
                                    <span style="float:left">【用户】${i.user.userName}</span>
                                </td>
                                <td style="font-size: 11px;">
                                    <span style="float:left">【发货速度】${i.send}</span>
                                </td>
                                <td style="font-size: 11px;">
                                    <span style="float:left">【可信度】${i.credible}</span>
                                </td>
                                <td style="font-size: 11px;">
                                    <span style="float:left">【评价时间】${i.time}</span>
                                </td>
                            </tr>
                            <tr height=20>
                                <td align="center">【内容】</td>
                                <td colspan="5"  style="padding-right: 6px; padding-left: 6px; padding-bottom: 6px; padding-top: 6px"  valign=top style="font-size: 11px;">
                                        ${i.comment}
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="8"><h4 class="text-center">暂无评论</h4></td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
        </div>
    </div>

    <%@include file="footer.jsp" %>
</div>
<!--弹窗盒子start -->
<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-body" style="color:green;font-size:24px;">
                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>&nbsp商品已成功加入购物车！
            </div>
            <div class="modal-footer">
                <a href="javascript:void(0)" type="button" class="btn btn-default" data-dismiss="modal">返回继续购物</a>
                <a href="jsp/book/cart.jsp" type="button" class="btn btn-success">立即去结算</a>
            </div>
        </div>
    </div>
</div>
<!--弹窗盒子end -->

<!-- 弹窗 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">登陆</h4>
            </div>
            <div class="modal-body">
                <!-- //登陆表单主体 -->
                <p class="info" style="text-align: center;color:red"></p>
                <div id="tab_login" class="tab-pane fade in active">
                    <form id="loginForm" name="loginForm" method="post" class="form-horizontal">
                        <div class="form-group">
                            <label for="l_userName" class="col-md-4 control-label">用户名：</label>
                            <div class="col-md-6">
                                <input name="userName" id="l_userName" type="text" class="form-control">
                                <span class="Validform_checktip">&nbsp</span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="l_passWord" class="col-md-4  control-label">密码：</label>
                            <div class="col-md-6">
                                <input type="password" name="passWord" id="l_passWord" class="form-control">
                                <span class="Validform_checktip">&nbsp</span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="ck_code" class="col-md-4  control-label">验证码：</label>
                            <div class="col-md-3">
                                <input class="form-control" type="text" name="code" id="ck_code">
                                <span class="Validform_checktip">&nbsp</span>
                            </div>

                            <div class="col-md-4" style="padding:0;">
                                <img class="col-md-8" id="imgCode" src="CodeServlet?action=code" alt="" style="padding:0;width:100px;height:38px;"/>
                                <span onclick="reCode()" class="col-md-4 glyphicon glyphicon-refresh "  aria-hidden="true" style="padding:0 0 0 5px;font-size: 24px;"></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label col-md-offset-4">
                                <button class="btn btn-success btn-block" type="submit">登录</button>
                            </label>
                            <label class="col-md-2 control-label">
                                <button class="btn btn-warning btn-block" type="reset">重置</button>
                            </label>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //去结算处理
    $("#tosettle").click(function () {
        $.get("UserServlet?action=landstatus", function (data) {
            if (data.status == "y") {
                toConorder();
            } else {
                $("#myModal").modal("show");
            }
        }, "json")
        return;
    })
    //登陆处理
    $("#loginForm").submit(function () {
        $.post("UserServlet?action=ajlogin", $('#loginForm').serialize(), function (data) {
            if ("y" == data.status) {
                toConorder();
                <%--window.location.href = "${basePath}jsp/book/conorder.jsp";--%>
            } else {
                $("#myModal .info").html(data.info);
            }
        }, "json")
        return false;
    })

    //验证码
    function reCode() {
        $("#imgCode").prop("src", "CodeServlet?action=code&" + new Date().getTime());
    }

    function  toConorder() {

        var bookid=$("#bookId").val();
        //alert(bookid)

        $.ajax({
            url:"CartServlet?action=add",
            dataType:"json",
            async:true,
            data:{"bookId":bookid},
            type:"POST",
            success:function(data){
                //$("#cart .num").html(data);
                window.location.href = "${basePath}jsp/book/conorder.jsp";
            }
        })

    }
</script>
</body>
</html>
