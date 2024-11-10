package com.shine.game.servlet.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shine.game.bean.Book;
import com.shine.game.dao.BookDao;
import com.shine.game.dao.impl.BookDaoImpl;

import net.sf.json.JSONObject;

/**
 * 系统加载首页
 */
@WebServlet("/ShopIndex")
public class ShopIndex extends HttpServlet {
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
        response.setContentType("text/json");
        JSONObject json = new JSONObject();
        BookDao bd = new BookDaoImpl();
        List<Book> recBooks = bd.bookList(4);
        json.put("recBooks", recBooks);
        List<Book> newBooks = bd.newBooks(4);
        json.put("newBooks", newBooks);
        PrintWriter pw = response.getWriter();
        pw.print(json);
        pw.flush();
    }

}
