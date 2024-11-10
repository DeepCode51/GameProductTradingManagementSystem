package com.shine.game.servlet.book;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shine.game.bean.Book;
import com.shine.game.bean.Cart;
import com.shine.game.bean.CartItem;
import com.shine.game.dao.BookDao;
import com.shine.game.dao.impl.BookDaoImpl;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
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
        String action = request.getParameter("action");
        switch (action) {
            case "add":
                addTOCart(request, response);
                break;
            case "changeIn":
                changeIn(request, response);//更改购物车商品数量
                break;
            case "delItem":
                delItem(request, response);
                break;
            case "delAll":
                delAll(request, response);
                break;
        }
    }


    private void delAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("shopCart");
        response.sendRedirect("jsp/book/cart.jsp");
    }

    //购物项删除
    private void delItem(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int bookId = Integer.parseInt(request.getParameter("id"));
        Cart shopCart = (Cart) request.getSession().getAttribute("shopCart");
        if (shopCart.getMap().containsKey(bookId)) {
            shopCart.getMap().remove(bookId);
        }
        response.sendRedirect("jsp/book/cart.jsp");
    }

    //更改购物项数量
    private void changeIn(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int bookId = Integer.parseInt(request.getParameter("bookId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Cart shopCart = (Cart) request.getSession().getAttribute("shopCart");
        CartItem item = shopCart.getMap().get(bookId);
        item.setQuantity(quantity);
        JSONObject json = new JSONObject();
        json.put("subtotal", item.getSubtotal());
        json.put("totPrice", shopCart.getTotPrice());
        json.put("totQuan", shopCart.getTotQuan());
        json.put("quantity", item.getQuantity());
        response.getWriter().print(json.toString());


    }

    private void addTOCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String bookId = request.getParameter("bookId");
        BookDao bd = new BookDaoImpl();
        Book book = bd.findBookById(Integer.parseInt(bookId));

        Cart shopCart = (Cart) request.getSession().getAttribute("shopCart");

        if (shopCart == null) {
            shopCart = new Cart();
            request.getSession().setAttribute("shopCart", shopCart);
        }
        shopCart.addBook(book);
        response.getWriter().print(shopCart.getTotQuan());//返回现在购物车实时数量
    }

}
