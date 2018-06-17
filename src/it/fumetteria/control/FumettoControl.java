package it.fumetteria.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.fumetteria.beans.AppartieneBean;
import it.fumetteria.model.AppartieneModel;
import it.fumetteria.model.AppartieneModelDS;
import it.fumetteria.model.FumettoModel;
import it.fumetteria.model.FumettoModelDS;
import it.fumetteria.model.SegueModel;
import it.fumetteria.model.SegueModelDS;
import it.fumetteria.model.SerieModel;
import it.fumetteria.model.SerieModelDS;

/**
 * Servlet implementation class FumettoServlet
 */
public class FumettoControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	static FumettoModel fumettoModel;
	static AppartieneModel appartieneModel;
	static SegueModel segueModel;
	static SerieModel serieModel;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FumettoControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");

		appartieneModel = new AppartieneModelDS(ds);
		fumettoModel = new FumettoModelDS(ds);
		segueModel = new SegueModelDS(ds);
		serieModel = new SerieModelDS(ds);
		try {
			if(action!=null && action.equals("readArticolo")) {
				if(request.getParameter("id")!=null) {
					try {
						int codice = Integer.parseInt(request.getParameter("id"));
					
						request.removeAttribute("articolo");
						request.setAttribute("articolo", fumettoModel.doRetrieveByKey(codice));
						request.removeAttribute("serie");
						AppartieneBean serie =  appartieneModel.doRetrieveByKey(codice);
						request.setAttribute("serie", appartieneModel.doRetrieveByKey(codice));
						
						String email;
						if((email=(String)request.getSession().getAttribute("email"))!=null && !email.equals("")) {
							request.setAttribute("isPreferito", segueModel.doRetrieveByKey(serie.getSerie(), email));
						} else {
							request.setAttribute("isPreferito", false);
						}
						
						String redirect = "/articoloView.jsp";
						
						if(request.getSession().getAttribute("admin")!=null && 
								((boolean)request.getSession().getAttribute("admin"))==true && 
								request.getParameter("update")!=null) {
							request.removeAttribute("serieInfo");
							request.setAttribute("serieInfo", serieModel.doRetrieveByKey(serie.getSerie()));
							redirect ="/admin/addArticolo.jsp";
						} 
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(redirect);
						dispatcher.forward(request, response);
					} catch(NumberFormatException e) {
						response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Un'eccezione Number Format è stata lanciata");
					}
				} else {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti");
				}
			} else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti o errati");
			}
		} catch(SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Un'eccezione SQL è stata lanciata.");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
