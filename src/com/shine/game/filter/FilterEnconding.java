package com.shine.game.filter;

import java.io.IOException;
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
 * Servlet Filter implementation class FilterEnconding
 */
@WebFilter(filterName="FilterEnconding",
			urlPatterns="/*",
			initParams= {@WebInitParam(name="enconding",value="utf-8")})
public class FilterEnconding implements Filter {
	private String enconding;


	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest=(HttpServletRequest) request;
		HttpServletResponse httpResponse=(HttpServletResponse) response;

		httpRequest.setCharacterEncoding(this.enconding);
		httpResponse.setCharacterEncoding(this.enconding);

		//System.out.println(DateUtil.show()+">>设置enconding"+this.enconding);

		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.enconding=fConfig.getInitParameter("enconding");
	}

	@Override
	public void destroy() {

	}

}
