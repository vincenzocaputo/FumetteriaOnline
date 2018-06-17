package it.fumetteria.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

import it.fumetteria.model.SerieModel;
import it.fumetteria.model.SerieModelDS;

/**
 * Servlet implementation class SerieControl
 */
public class SerieControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	static SerieModel model;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SerieControl() {
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
		model = new SerieModelDS(ds);
		
		if(action!=null && action.equals("getAllSerie")) {
			response.setContentType("application/json");
			String serieNome = request.getParameter("nome");
			if(serieNome!=null && !serieNome.equals("")) {
				try {
					JSONObject jsonObj = new JSONObject();
					Collection<?> serie = model.doRetrieveByCond(serieNome);
					
					try {
						jsonObj.put("serie", serie);
					} catch (JSONException e) {
						
					}
					response.getWriter().print(jsonObj.toString());
				} catch (SQLException e) {
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Un'eccezione SQL è stata lanciata.");
				}
			}
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Important parameter needed");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
