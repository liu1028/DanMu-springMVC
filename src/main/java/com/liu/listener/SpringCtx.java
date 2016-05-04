package com.liu.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringCtx implements ServletContextListener {

	private static WebApplicationContext springContext;
	
	public SpringCtx() {
		// TODO Auto-generated constructor stub
	}

	public void contextInitialized(ServletContextEvent sce) {
		springContext=WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

	public static WebApplicationContext getWebAppContext(){
		return springContext;
	}
}
