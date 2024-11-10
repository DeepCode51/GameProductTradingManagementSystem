package com.shine.game.servlet.admin;

import com.shine.game.bean.*;
import com.shine.game.dao.*;
import com.shine.game.dao.impl.*;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Servlet implementation class UserManage
 */
@WebServlet("/jsp/admin/CommentManageServlet")
public class CommentManageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        System.out.println("评价请求类");
        String action = request.getParameter("action");
        System.out.println(action);
        switch (action) {
            case "list":
                commentList(request, response);
                break;
            case "add":
                add(request, response);
                break;
            case "update":
                update(request, response);
                break;
            case "edit":
                toCommentEdit(request, response);
                break;
            case "del":
                commentDel(request, response);
                break;
            case "batDel":
                commentBatDel(request, response);
                break;
            case "editUser"://修改卖家信用
                editUser(request, response);
                break;
        }
    }

    //饰品批量删除
    private void commentBatDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ids = request.getParameter("ids");
        CommentDao bd = new CommentDaoImpl();
        if (bd.batDelComment(ids)) {
            request.setAttribute("commentMessage", "评论批量删除成功");
        } else {
            request.setAttribute("commentMessage", "评论批量删除失败");
        }
        //用户删除成功失败都跳转到用户列表页面
        commentList(request, response);//通过servlet中listUser跳到用户列表
    }

    //饰品删除
    private void commentDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        File contextPath = new File(request.getServletContext().getRealPath("/"));
        CommentDao bd = new CommentDaoImpl();

        Comment book = bd.findComment(id);
        //这里先删除数据库饰品信息，再删除饰品图片及本地硬盘图片信息
        if (bd.delComment(id)) {
            request.setAttribute("commentMessage", "评论已删除");
        } else {
            request.setAttribute("commentMessage", "评论删除失败");
        }
        //用户删除成功失败都跳转到用户列表页面
        commentList(request, response);//通过servlet中listUser跳到用户列表
    }


    private void commentList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null) {
            curPage = Integer.parseInt(page);
        }
        int maxSize = Integer.parseInt(request.getServletContext().getInitParameter("maxPageSize"));
        CommentDao commentDao = new CommentDaoImpl();
        PageBean pb = new PageBean(curPage, maxSize, commentDao.commentReadCount());
        System.out.println(commentDao.commentList(pb).size());
        request.setAttribute("pageBean", pb);
        request.setAttribute("commentList", commentDao.commentList(pb));
        request.getRequestDispatcher("commentManage/commentList.jsp").forward(request, response);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommentDaoImpl ad = new CommentDaoImpl();
        //商品id
        Integer dateId = Integer.parseInt(request.getParameter("dateId"));
        //订单明细id
        Integer itemId = Integer.parseInt(request.getParameter("itemId"));
        //当前登录者
        User user = (User) request.getSession().getAttribute("landing");
        Comment comment = new Comment(
                request.getParameter("comment"),
                request.getParameter("send"),
                request.getParameter("credible"),
                request.getParameter("start"),
                new Date(),
                user.getUserId(), dateId);
        //修改订单明细的评价状态
        OrderItemDaoImpl orderItemDao = new OrderItemDaoImpl();
        // itemId 订单明细id  state 是否评价(0:未评价,1:已评价)
        //orderItemDao.updateIsComment(itemId, 1);
        //执行dao层添加操作
        if (ad.commentAdd(comment) && orderItemDao.updateIsComment(itemId, 1)) {
            response.getWriter().println("<script  type='text/javascript'>alert('评价成功')</script>");
            response.getWriter().println("<script>window.location.href='./OrderServlet?action=list'</script>");
        } else {
            response.getWriter().println("<script  type='text/javascript'>alert('评价失败')</script>");
            response.getWriter().println("<script>window.location.href='./OrderServlet?action=list'</script>");
        }
    }

    private void toCommentEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        CommentDao commentDao = new CommentDaoImpl();
        request.setAttribute("comment", commentDao.findComment(Integer.valueOf(id)));//这里回去是User对象
        request.getRequestDispatcher("commentManage/commentEdit.jsp").forward(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommentDaoImpl ad = new CommentDaoImpl();
        int id = Integer.valueOf(request.getParameter("id"));
        Comment comment = new Comment(
                Integer.parseInt(request.getParameter("id")),
                request.getParameter("comment"),
                request.getParameter("send"),
                request.getParameter("credible"),
                request.getParameter("start")
        );
        if (ad.updateComment(comment)) {
            request.setAttribute("userMessage", "用户更新成功");
            commentList(request, response);//通过servlet中listUser跳到用户列表
        } else {
            //更新失败跳转到修改页面
            request.setAttribute("userMessage", "用户更新失败");
            request.setAttribute("comment", ad.findComment(id));//这里回去是User对象
            request.getRequestDispatcher("commentManage/commentEdit.jsp").forward(request, response);
        }
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---------------------------------");
        UserDao ad = new UserDaoImpl();
        User user = new User();
        user.setUserId(Integer.parseInt(request.getParameter("userId")));
        user.setGrade(request.getParameter("start"));
        System.out.println(user);
        //执行dao层添加操作
        if (ad.userUpdateGrade(user)) {
            request.setAttribute("commentMessage", "卖家信用设置成功");
            commentList(request, response);//通过servlet中listUser跳到用户列表
        } else {
            request.setAttribute("commentMessage", "卖家信用设置失败");
            commentList(request, response);//通过servlet中listUser跳到用户列表
        }
    }

}
