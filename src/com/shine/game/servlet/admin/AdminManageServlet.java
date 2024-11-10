package com.shine.game.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shine.game.bean.Admin;
import com.shine.game.bean.PageBean;
import com.shine.game.dao.AdminDao;
import com.shine.game.dao.impl.AdminDaoImpl;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/jsp/admin/AdminManageServlet")
public class AdminManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ADMINLIST_PATH="adminManage/adminList.jsp";//用户列表页面地址
	private static final String ADMINADD_PATH="adminManage/adminAdd.jsp";//用户增加页面地址
	private static final String ADMINEDIT_PATH="adminManage/adminEdit.jsp";//用户修改页面地址

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getParameter("action");
		switch(action){
		case "list":
			adminList(request,response);
			break;
		case "add":
			adminAdd(request,response);
			break;
		case "update":
			adminUpdate(request,response);
			break;
		case "edit":
			adminEdit(request,response);
			break;
		case "del":
			adminDel(request,response);
			break;
		case "batDel":
			adminBatDel(request,response);
			break;
		case "find":
			adminFind(request,response);
			break;
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	//查询用户列表
	private void adminList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		AdminDao ad=new AdminDaoImpl();
		int curPage=1;
		String page=request.getParameter("page");
		if(page!=null){
			curPage=Integer.parseInt(page);
		}
		//获取xml中设置的每页显示大小参数
		int maxSize=Integer.parseInt(request.getServletContext().getInitParameter("maxPageSize"));

		PageBean pageBean=new PageBean(curPage,maxSize,ad.bookReadCount());

		request.setAttribute("adminList", ad.userList(pageBean));
		request.setAttribute("pageBean", pageBean);

		request.getRequestDispatcher(AdminManageServlet.ADMINLIST_PATH).forward(request, response);

	}

	//增加用户
	private void adminAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		AdminDao ad=new AdminDaoImpl();
		Admin admin=new Admin(request.getParameter("userName"),request.getParameter("passWord"),
				request.getParameter("name"));
		//添加之前判断用户名是否在库中存在
		if(new AdminDaoImpl().findUser(admin.getUserName())){
			request.setAttribute("adminMessage", "用户添加失败！用户名已存在");
			request.getRequestDispatcher(AdminManageServlet.ADMINADD_PATH).forward(request, response);
		}else{
			//执行dao层添加操作
			if(ad.userAdd(admin)){
				request.setAttribute("adminMessage", "用户添加成功！");
				adminList(request, response);//通过servlet中listUser跳到用户列表
			}else{
				request.setAttribute("adminMessage", "用户添加失败！");
				request.getRequestDispatcher(AdminManageServlet.ADMINADD_PATH).forward(request, response);
			}
		}

	}
	//更新用户信息
	private void adminUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Admin admin=new Admin(Integer.parseInt(request.getParameter("id")),
				request.getParameter("passWord"),
				request.getParameter("name")
				);
		AdminDao ad=new AdminDaoImpl();
		if(ad.userUpdate(admin)) {
			request.setAttribute("adminMessage", "用户更新成功");
			adminList(request, response);//通过servlet中listUser跳到用户列表
		}else {
			//更新失败跳转到修改页面
			request.setAttribute("adminMessage", "用户更新失败");
			request.setAttribute("adminInfo", ad.findUser(Integer.valueOf(admin.getId())));//这里回去是Admin对象
			request.getRequestDispatcher(AdminManageServlet.ADMINEDIT_PATH).forward(request, response);
		}

	}

	//修改用户
	private void adminEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");
		AdminDao ad=new AdminDaoImpl();
		request.setAttribute("adminInfo",ad.findUser(Integer.valueOf(id)));//这里回去是Admin对象
		request.getRequestDispatcher(AdminManageServlet.ADMINEDIT_PATH).forward(request, response);
	}

	//删除用户
	private void adminDel(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int id=Integer.parseInt(request.getParameter("id"));
		AdminDao ad=new AdminDaoImpl();
		if(ad.delUser(id)) {
			request.setAttribute("adminMessage", "用户已删除");
		}else {
			request.setAttribute("adminMessage", "用户删除失败");
		}
		//用户删除成功失败都跳转到用户列表页面
		adminList(request, response);//通过servlet中listUser跳到用户列表
	}

	//批量删除
	private void adminBatDel(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String ids=request.getParameter("ids");
		AdminDao ad=new AdminDaoImpl();

		if(ad.batDelUser(ids)) {
			request.setAttribute("adminMessage", "用户已批量删除");
		}else {
			request.setAttribute("adminMessage", "用户批量删除失败");
		}
		//用户删除成功失败都跳转到用户列表页面
		adminList(request, response);//通过servlet中listUser跳到用户列表

	}

	//ajax判断用户名(param为接收的用户名）
	private void adminFind(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userName=request.getParameter("param");

		AdminDao ad=new AdminDaoImpl();
		//这里实例化json对象需要导入6个jar包（
		//commons-lang-2.4.jar ,commons-collections-3.2.1.jar,commons-beanutils-1.8.3.jar
		//json-lib-2.4-jdk15.jar ,ezmorph-1.0.6.jar ,commons-logging-1.1.3.jar）
		JSONObject json=new JSONObject();
		if(ad.findUser(userName)){
			json.put("info", "用户名已存在");
			json.put("status", "n");
		}else{
			json.put("info", "用户名可以使用");
			json.put("status", "y");
		}
		response.getWriter().write(json.toString());
	}

}
