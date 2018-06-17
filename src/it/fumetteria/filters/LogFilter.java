package it.fumetteria.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		HttpSession session = httpRequest.getSession();
		String action = request.getParameter("action");
		boolean pass = true;
		int httpStatus = HttpServletResponse.SC_OK;
		String httpMessage = null;
		
		if(action!=null && !action.equals("")) {
			if( action.equals("readOrdineDetails") || action.equals("addOrdine") || action.equals("readOrdini") || action.equals("addPreferiti") || action.equals("delPreferiti") 
					|| action.equals("readPreferiti") || action.equals("logout") || action.equals("readArticoliPref")) {
				String email = (String) session.getAttribute("email");
				if(email!=null && !email.equals("")) {
					pass = true;
				} else {
					pass = false;
					httpStatus = HttpServletResponse.SC_UNAUTHORIZED;
					httpMessage = "Effettua la login per continuare.";
				}
				
			} else if(action.equals("addArticolo") || action.equals("delArticolo") || action.equals("updateArticolo") || action.equals("confermaOrdine")
					|| action.equals("readAllOrdini") || action.equals("getAllSerie")) {
				String email = (String) session.getAttribute("email");
				if(email!=null && session.getAttribute("admin")!=null && 
						!email.equals("") && (boolean)session.getAttribute("admin")) {
					pass = true;
				} else {
					pass = false;
					httpStatus = HttpServletResponse.SC_FORBIDDEN;
					httpMessage = "Operazione non consentita.";
				}
			} 
		}
		if(httpRequest.getRequestURI().contains("admin")) {
			String email = (String) session.getAttribute("email");
			if(email!=null && session.getAttribute("admin")!=null && 
					!email.equals("") && (boolean)session.getAttribute("admin")) {
				pass = true;
			} else {
				pass = false;
				httpStatus = HttpServletResponse.SC_FORBIDDEN;
				httpMessage = "Operazione non consentita.";
			}
		}
		if(pass)
			chain.doFilter(request, response);
		else {
			httpResponse.sendError(httpStatus, httpMessage);
		}
	}

}
