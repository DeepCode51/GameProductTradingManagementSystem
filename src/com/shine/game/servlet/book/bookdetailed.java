package com.shine.game.servlet.book;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shine.game.bean.Comment;
import com.shine.game.bean.User;
import com.shine.game.dao.BookDao;
import com.shine.game.dao.impl.BookDaoImpl;
import com.shine.game.dao.impl.CommentDaoImpl;

/**
 * Servlet implementation class bookdetailed
 */
@WebServlet("/bookdetail")
public class bookdetailed extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DETAIL_PATH="jsp/book/bookdetails.jsp";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		//获得及生成一些需要的对象和数据
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("landing");
		BookDao bd = new BookDaoImpl();
		CommentDaoImpl commentDao=new CommentDaoImpl();
		if(user!=null){
		System.out.println("用户id"+user.getUserId()+"---id："+bookId);
			//查询改用户是否评价过
			boolean commentByUid = commentDao.findCommentByUid(bookId, user.getUserId());
			//查询该用户是否购买过当前商品

			System.out.println("改用户是否评价过："+commentByUid);
		    request.setAttribute("isComment",commentByUid);
		}else{
			request.setAttribute("isComment",null);
		}
		//是否登录
		request.setAttribute("islogin",user!=null?true:false);
		//查询该商品最新前10的评价
		List<Comment> comments = commentDao.CommentList(bookId);
		System.out.println("是否有评论");
		System.out.println(comments.toString());
		request.setAttribute("bookInfo", bd.findBookById(bookId));
		request.setAttribute("comments", comments);
		request.getRequestDispatcher(DETAIL_PATH).forward(request, response);
	}
}
