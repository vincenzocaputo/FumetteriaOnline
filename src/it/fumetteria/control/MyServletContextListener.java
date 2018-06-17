package it.fumetteria.control;

import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;

public class MyServletContextListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();

		DataSource ds = null;
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/fumetteria");

		} catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}		
		context.setAttribute("DataSource", ds);
		System.out.println("DataSource creation...."+ds.toString());
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

}
