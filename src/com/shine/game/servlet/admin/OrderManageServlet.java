package com.shine.game.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shine.game.bean.Order;
import com.shine.game.bean.OrderItem;
import com.shine.game.bean.PageBean;
import com.shine.game.dao.BookDao;
import com.shine.game.dao.OrderDao;
import com.shine.game.dao.OrderItemDao;
import com.shine.game.dao.UserDao;
import com.shine.game.dao.impl.BookDaoImpl;
import com.shine.game.dao.impl.OrderDaoImpl;
import com.shine.game.dao.impl.OrderItemDaoImpl;
import com.shine.game.dao.impl.UserDaoImpl;

/**
 * Servlet implementation class OrderManageServlet
 */
@WebServlet("/jsp/admin/OrderManageServlet")
public class OrderManageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String ORDERLIST_PATH = "orderManage/orderlist.jsp";
    private static final String ORDERDETAIL_PATH = "orderManage/orderDetail.jsp";
    private static final String ORDEROP_PATH = "orderManage/orderOp.jsp";

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
        String action = request.getParameter("action");
        switch (action) {
            case "list":
                orderList(request, response);
                break;
            case "detail":
                orderDetail(request, response);
                break;
            case "processing":
                orderProcessing(request, response);
                break;
            case "ship":
                orderShip(request, response);
                break;
        }

    }

    private void orderShip(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("id");
        OrderDao orderDao = new OrderDaoImpl();

        if (orderDao.orderStatus(Integer.parseInt(orderId), 2)) {
            request.setAttribute("orderMessage", "一个订单操作成功");
        } else {
            request.setAttribute("orderMessage", "一个订单操作失败");
        }
        orderProcessing(request, response);
    }

    private void orderProcessing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null) {
            curPage = Integer.parseInt(page);
        }
        int maxSize = Integer.parseInt(request.getServletContext().getInitParameter("maxPageSize"));
        OrderDao orderDao = new OrderDaoImpl();
        PageBean pb = new PageBean(curPage, maxSize, orderDao.orderReadCountByStatus(1));

        request.setAttribute("pageBean", pb);
        request.setAttribute("orderList", orderDao.orderListByStatus(pb, 1));
        request.getRequestDispatcher(ORDEROP_PATH).forward(request, response);

    }

    private void orderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("id"));
        OrderDao orderDao = new OrderDaoImpl();
        OrderItemDao oItemDao = new OrderItemDaoImpl();
        UserDao userDao = new UserDaoImpl();
        BookDao bookDao = new BookDaoImpl();

        Order order = orderDao.findOrderByOrderId(orderId);
        order.setUser(userDao.findUser(order.getUserId()));
        order.setoItem(oItemDao.findItemByOrderId(order.getOrderId()));
        for (OrderItem oItem : order.getoItem()) {
            //通过饰品id获取饰品对象
            oItem.setBook(bookDao.findBookById(oItem.getBookId()));
        }
        request.setAttribute("order", order);
        request.getRequestDispatcher(ORDERDETAIL_PATH).forward(request, response);

    }

    private void orderList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null) {
            curPage = Integer.parseInt(page);
        }
        int maxSize = Integer.parseInt(request.getServletContext().getInitParameter("maxPageSize"));
        OrderDao orderDao = new OrderDaoImpl();
        PageBean pb = new PageBean(curPage, maxSize, orderDao.orderReadCount());
        request.setAttribute("pageBean", pb);
        request.setAttribute("orderList", orderDao.orderList(pb));
        request.getRequestDispatcher(ORDERLIST_PATH).forward(request, response);

    }

}
