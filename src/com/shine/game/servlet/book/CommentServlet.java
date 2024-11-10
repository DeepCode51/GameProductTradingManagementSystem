package com.shine.game.servlet.book;

import com.shine.game.bean.Comment;
import com.shine.game.bean.User;
import com.shine.game.dao.UserDao;
import com.shine.game.dao.impl.CommentDaoImpl;
import com.shine.game.dao.impl.OrderItemDaoImpl;
import com.shine.game.dao.impl.UserDaoImpl;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Servlet implementation class UserManage
 */
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String LOGIN_PATH = "jsp/book/reg.jsp?type=login";
    private static final String REG_PATH = "jsp/book/reg.jsp?type=reg";
    private static final String USERINFO_PATH = "jsp/book/userinfo.jsp?type=info";
    private static final String INDEX_PATH = "jsp/book/index.jsp";
    private static final String LANDING = "landing";//前台用户session标识

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
        String action = request.getParameter("action");
        switch (action) {
            case "login":
                login(request, response);
                break;
            case "off":
                offlogin(request, response);
                break;
            case "ajlogin":
                ajlogin(request, response);
                break;
            case "add":
                add(request, response);
                break;
            case "userInfo":
                userInfo(request, response);
                break;
            case "landstatus":
                landstatus(request, response);
                break;
        }
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

    //判断登陆状态
    private void landstatus(HttpServletRequest request, HttpServletResponse response) throws IOException {

        User user = (User) request.getSession().getAttribute(LANDING);

        PrintWriter pw = response.getWriter();
        JSONObject json = new JSONObject();

        if (user != null) {
            json.put("status", "y");
        } else {
            json.put("status", "n");
        }
        pw.print(json.toString());
        pw.flush();

    }

    //ajax登陆
    private void ajlogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");
        User user = new User(userName, passWord);
        PrintWriter pw = response.getWriter();
        JSONObject json = new JSONObject();
        UserDao ud = new UserDaoImpl();
        User user2 = ud.userLogin(user);

        if (user2 != null) {
            if ("y".equals(user2.getEnabled())) {
                request.getSession().setAttribute(LANDING, user2);
                json.put("status", "y");
            } else {
                json.put("info", "该用户已被禁用，请联系管理员");
            }
        } else {
            json.put("info", "用户名或密码错误，请重新登陆！");

        }
        pw.print(json.toString());
    }

    //我的资料
    private void userInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //当前登录者
        User user = (User) request.getSession().getAttribute("landing");
        UserDao userDao = new UserDaoImpl();
        User us = userDao.findUser(user.getUserId());
        request.setAttribute("user", us);
        System.out.println("个人信息");
        request.getRequestDispatcher(USERINFO_PATH).forward(request, response);
    }


    private void reg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao ad = new UserDaoImpl();
        User user = new User(
                request.getParameter("userName"),
                request.getParameter("passWord"),
                request.getParameter("name"),
                request.getParameter("sex"),
                Integer.parseInt(request.getParameter("age")),
                request.getParameter("tell"),
                request.getParameter("address"));
        user.setEnabled("y");//默认添加的用户启用
        user.setPayPwd(request.getParameter("payPwd"));
        //添加之前判断用户名是否在库中存在
        if (ad.findUser(user.getUserName())) {
            request.setAttribute("infoList", "用户添加失败！用户名已存在");
            request.getRequestDispatcher(REG_PATH).forward(request, response);
        } else {
            //执行dao层添加操作
            if (ad.userAdd(user)) {
                request.setAttribute("infoList", "注册成功！请登陆！");
                request.getRequestDispatcher(LOGIN_PATH).forward(request, response);
            } else {
                request.setAttribute("userMessage", "用户添加失败！");
                request.getRequestDispatcher(REG_PATH).forward(request, response);
            }
        }

    }

    private void offlogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute(LANDING);
        if (user != null) {
            request.getSession().removeAttribute(LANDING);
        }
        response.sendRedirect(INDEX_PATH);
    }


    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");
        User user = new User(userName, passWord);
        UserDao ud = new UserDaoImpl();
        User user2 = ud.userLogin(user);
        if (user2 != null) {
            if ("y".equals(user2.getEnabled())) {
                request.getSession().setAttribute(LANDING, user2);
                response.sendRedirect(INDEX_PATH);
            } else {
                request.setAttribute("infoList", "该用户已被禁用，请联系管理员");
                request.getRequestDispatcher(LOGIN_PATH).forward(request, response);
            }
        } else {
            request.setAttribute("infoList", "用户名或密码错误，请重新登陆！");
            request.getRequestDispatcher(LOGIN_PATH).forward(request, response);
        }
    }

}
