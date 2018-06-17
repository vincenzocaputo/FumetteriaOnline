package it.fumetteria.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

import it.fumetteria.beans.SegueBean;
import it.fumetteria.beans.SerieBean;
import it.fumetteria.model.SegueModel;
import it.fumetteria.model.SegueModelDS;

/**
 * Servlet implementation class PreferitiServlet
 */
public class SegueControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	static SegueModel segueModel;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SegueControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String action = request.getParameter("action");
		
		
		DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
		segueModel = new SegueModelDS(ds);
		try {
			if(action!=null) {
				HttpSession session = request.getSession();
				if(action.equals("addPreferiti")) {
					response.setContentType("application/json");
					String nomeSerie = request.getParameter("serie");
					if(nomeSerie!=null && !nomeSerie.equals("")) {
						JSONObject jsonObj = new JSONObject();
						SegueBean bean = new SegueBean();
						
						bean.setUtente((String)session.getAttribute("email")); 
						SerieBean serie = new SerieBean();
						serie.setNome(nomeSerie);
						bean.setSerie(serie);
						
						segueModel.doSave(bean);
						
						try {
							jsonObj.put("status", 1);
						} catch (JSONException e) {
							
						}
						response.getWriter().print(jsonObj.toString());
					}
				} else if(action.equals("delPreferitiAJAX")) {
					response.setContentType("application/json");
					String serie = request.getParameter("serie");
					if(serie!=null && !serie.equals("")) {
						JSONObject jsonObj = new JSONObject();
						String utente = (String)session.getAttribute("email"); 
						
						
						segueModel.doDelete(utente, serie);
						
						try {
							jsonObj.put("status", 0);
						} catch (JSONException e) {
							
						}
						response.getWriter().print(jsonObj.toString());
					} else {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti.");
					}
				} else if(action.equals("delPreferiti")) { //Rimozione preferito dalla pagina myPreferiti
					String serie = request.getParameter("serie");
					if(serie!=null && !serie.equals("")) {
						String utente = (String)session.getAttribute("email"); 
						
						
						segueModel.doDelete(utente, serie);
						
						response.sendRedirect("preferiti?action=readPreferiti");
					} else {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti.");
					}
				}
				else if(action.equals("readPreferiti")) { 
					String email = (String)session.getAttribute("email");
					
					request.removeAttribute("serie");
					request.setAttribute("serie", segueModel.doRetrieveByCond(email));

					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/myPreferiti.jsp");
					dispatcher.forward(request, response);
				} else {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Errore nei parametri: il parametro 'action' non è valido.");
				}
			} else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti.");
			}
		} catch(SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Un'eccezione SQL è stata lanciata.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
