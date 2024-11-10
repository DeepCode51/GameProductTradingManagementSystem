package com.shine.game.filter;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class FilterLogin
 */
@WebFilter(filterName="FilterLogin",urlPatterns="/jsp/admin/*",
			initParams={@WebInitParam(name="allowPath",value="login.jsp;LoginServlet;images;css")})
public class FilterLogin implements Filter {
	private String allowPath;
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest  httpRequest=(HttpServletRequest) request;
		HttpServletResponse httpResponse=(HttpServletResponse) response;
		String urlPath=httpRequest.getServletPath();
		if(httpRequest.getSession().getAttribute("adminUser")!=null){
			chain.doFilter(request, response);
			return;
		}

		String[] urls=this.allowPath.split(";");
		for(String url:urls){
			if(urlPath.indexOf(url)>0){
				chain.doFilter(request, response);
				return;
			}
		}
		String noPath=httpRequest.getScheme()+"://"+httpRequest.getServerName()+":"+httpRequest.getServerPort()+httpRequest.getContextPath()+"/jsp/admin/login.jsp";
		//httpResponse.sendRedirect("login.jsp");
		//'http://localhost:8080/bookshop/login.jsp'
		PrintWriter pw=httpResponse.getWriter();
		//解决iframe框架未登录顶层窗口跳转地址问题
		pw.println("<script>top.location.href='"+noPath+"'</script>");
//		pw.write("<script>top.location.href='"+noPath+"'</script>");
//		pw.flush();

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.allowPath=fConfig.getInitParameter("allowPath");
	}

}
