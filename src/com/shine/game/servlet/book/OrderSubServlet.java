package com.shine.game.servlet.book;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shine.game.bean.*;
import com.shine.game.dao.BookDao;
import com.shine.game.dao.OrderDao;
import com.shine.game.dao.OrderItemDao;
import com.shine.game.dao.UserDao;
import com.shine.game.dao.impl.BookDaoImpl;
import com.shine.game.dao.impl.OrderDaoImpl;
import com.shine.game.dao.impl.OrderItemDaoImpl;
import com.shine.game.dao.impl.UserDaoImpl;
import com.shine.game.util.DateUtil;
import com.shine.game.util.RanUtil;

/**
 * 订单前台一些请求
 * Servlet implementation class OrderSubServlet
 */
@WebServlet(name = "OrderServlet", urlPatterns = {"/OrderServlet"})
public class OrderSubServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int MAX_LIST_SIZE = 5;
    private static final String CART_PATH = "jsp/book/cart.jsp";
    private static final String ORDER_PAY_PATH = "jsp/book/ordersuccess.jsp";

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
            case "subOrder":
                subOrder(request, response);
                break;
            case "list":
                myOrderList(request, response);
                break;
            case "ship":
                orderShip(request, response);
                break;
            case "processing":
                orderProcessing(request, response);
                break;
            case "detail":
                orderDetail(request, response);
                break;
            case "shipDeal":
                orderShipDeal(request, response);
                break;
        }

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
        request.getRequestDispatcher("jsp/book/orderDetail.jsp").forward(request, response);
    }

    //发货
    private void orderShipDeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("id");
        OrderDao orderDao = new OrderDaoImpl();

        if (orderDao.orderStatus(Integer.parseInt(orderId), 2)) {
            request.setAttribute("orderMessage", "一个订单操作成功");
        } else {
            request.setAttribute("orderMessage", "一个订单操作失败");
        }
        orderProcessing(request, response);
    }

    private void orderShip(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String orderId = request.getParameter("id");
        OrderDao orderDao = new OrderDaoImpl();

        if (orderDao.orderStatus(Integer.parseInt(orderId), 3)) {
            request.setAttribute("orderMessage", "一个订单操作成功");
        } else {
            request.setAttribute("orderMessage", "一个订单操作失败");
        }
        myOrderList(request, response);
    }

    //我的订单列表请求
    private void myOrderList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User) request.getSession().getAttribute("landing");
        if (user == null) {
            response.sendRedirect("jsp/book/reg.jsp?type=login");
        } else {
            OrderDao orderDao = new OrderDaoImpl();
            OrderItemDao oItem = new OrderItemDaoImpl();
            BookDao bookDao = new BookDaoImpl();
            int curPage = 1;
            String page = request.getParameter("page");
            if (page != null) {
                curPage = Integer.parseInt(page);
            }
            System.out.println("------1");
            PageBean pb = new PageBean(curPage, MAX_LIST_SIZE, orderDao.orderReadCount(user.getUserId()));
            List<Order> orderList = orderDao.orderList(pb, user.getUserId());
            System.out.println("------2");
            for (Order order : orderList) {
                //通过订单编号查询订单项集合
                order.setoItem(oItem.findItemByOrderId(order.getOrderId()));
                for (OrderItem oi : order.getoItem()) {
                    //通过饰品id获取饰品对象
                    oi.setBook(bookDao.findBookById(oi.getBookId()));
                }
            }
            request.setAttribute("pageBean", pb);
            request.setAttribute("orderList", orderList);
            request.getRequestDispatcher("jsp/book/myorderlist.jsp").forward(request, response);
        }
    }

    //订单提交处理，生成订单号并存入数据库（这里订单状态未1;未付款），
    private void subOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //支付密码
        String payPwd = request.getParameter("payPwd");
        System.out.println("支付密码：" + payPwd);
        //获得及生成一些需要的对象和数据
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("shopCart");
        User user = (User) session.getAttribute("landing");
        System.out.println("当前登录者密码：" + user.getPayPwd());
        //判断密码是否正确
        if (!payPwd.equalsIgnoreCase(user.getPayPwd())) {
            System.out.println("支付密码错误");
            request.setAttribute("suberr", "支付密码错误，请重新提交输入正确密码");
            request.getRequestDispatcher(CART_PATH).forward(request, response);
        } else {
            String orderNum = RanUtil.getOrderNum();//生成的订单号
            String orderDate = DateUtil.show();//生成订单日期
            Order order = new Order();
            OrderDao orderDao = new OrderDaoImpl();
            OrderItemDao oItemDao = new OrderItemDaoImpl();
            BookDao bookDao = new BookDaoImpl();
            Book bookById = bookDao.findBookById(cart.getBookId());
            //给订单对象属性赋值
            order.setOrderNum(orderNum);
            order.setOrderDate(orderDate);
            order.setMoney(cart.getTotPrice());
            order.setOrderStatus(1);
            order.setUserId(user.getUserId());
            //设置出售者
            order.setSellUserId(bookById.getSellUserId());
            if (orderDao.orderAdd(order)) {
                //订单保存成功通过订单号获取订单编号，订单项留用
                order.setOrderId(orderDao.findOrderIdByOrderNum(orderNum));
                //
                for (Map.Entry<Integer, CartItem> meic : cart.getMap().entrySet()) {
                    OrderItem oi = new OrderItem();
                    oi.setBookId(meic.getKey());
                    oi.setQuantity(meic.getValue().getQuantity());
                    oi.setOrderId(order.getOrderId());
                    oItemDao.orderAdd(oi);
                }
                //订单项保存结束清空购物车，返回订单提交成功
                session.removeAttribute("shopCart");
                request.setAttribute("orderNum", order.getOrderNum());
                request.setAttribute("money", order.getMoney());
                request.getRequestDispatcher(ORDER_PAY_PATH).forward(request, response);
            } else {
                request.setAttribute("suberr", "订单提交失败，请重新提交");
                request.getRequestDispatcher(CART_PATH).forward(request, response);
            }
        }
    }
    //待处理订单列表
    private void orderProcessing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("订单处理");
        User user = (User) request.getSession().getAttribute("landing");
        if (user == null) {
            response.sendRedirect("jsp/book/reg.jsp?type=login");
        } else {
            int curPage = 1;
            String page = request.getParameter("page");
            if (page != null) {
                curPage = Integer.parseInt(page);
            }
            int maxSize = Integer.parseInt(request.getServletContext().getInitParameter("maxPageSize"));
            OrderDao orderDao = new OrderDaoImpl();
            PageBean pb = new PageBean(curPage, maxSize, orderDao.orderReadCountByStatusUserId(1, user.getUserId()));
            List<Order> orders = orderDao.orderListByStatusUserId(pb, 1, user.getUserId());
            for (Order o : orders
            ) {
                System.out.println(o.toString());
            }
            request.setAttribute("pageBean", pb);
            request.setAttribute("orderList", orderDao.orderListByStatusUserId(pb, 1, user.getUserId()));
            request.getRequestDispatcher("jsp/book/orderOp.jsp").forward(request, response);
        }
    }

}
