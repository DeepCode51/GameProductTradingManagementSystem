package com.shine.game.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.shine.game.bean.Admin;
import com.shine.game.util.DateUtil;

/**
 * Application Lifecycle Listener implementation class ListenerCount
 *
 */
@WebListener
public class ListenerCount implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {
	ServletContext application;

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  {
    	//记录系统访问的人数；
        Integer vCount=(Integer) this.application.getAttribute("vCount");
         vCount++;
         this.application.setAttribute("vCount", vCount);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  {
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  {

    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent se)  {
    	List<String> olUser=(List<String>) this.application.getAttribute("olUser");
          String name=se.getName();
          if("userInfo".equals(name)){
        	  Admin user=(Admin) se.getValue();
        	  olUser.add(user.getUserName());
        	  this.application.setAttribute("olUser", olUser);
        	  System.out.println(DateUtil.show()+">>> 用户："+user.getUserName()+"登录");
          }

    }

	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent se)  {
    	List<String> olUser=(List<String>) this.application.getAttribute("olUser");
    	String name=se.getName();
    	if("userInfo".equals(name)){
    		Admin user=(Admin) se.getValue();
    		if(olUser.contains(user.getUserName())){
    			olUser.remove(user.getUserName());
    			this.application.setAttribute("olUser", olUser);
    		}
    		System.out.println(DateUtil.show()+">>> 用户："+user.getUserName()+"退出登录");
    	}

    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent se)  {
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  {
    	this.application=sce.getServletContext();
        this.application.setAttribute("vCount", 0);
        this.application.setAttribute("olUser", new ArrayList<String>());

    }

}
