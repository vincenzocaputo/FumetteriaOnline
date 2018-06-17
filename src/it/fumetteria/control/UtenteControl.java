package it.fumetteria.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.fumetteria.beans.UtenteBean;
import it.fumetteria.model.UtenteModel;
import it.fumetteria.model.UtenteModelDS;

/**
 * Servlet implementation class UtenteServlet
 */
public class UtenteControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static UtenteModel model;
	
	
	public void init() {
		DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
		model = new UtenteModelDS(ds);
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UtenteControl() {
        super();
        
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		
		if(action!=null && action.equals("logout")) {
			HttpSession session = request.getSession();
			if(session.getAttribute("email")!=null)
				session.removeAttribute("email");
			if(session.getAttribute("admin")!=null)
				session.removeAttribute("admin");
			
			response.sendRedirect("index.jsp");
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti o errati.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		
		
		try {
			
			if(action!=null && action.equals("logon")) {
				String redirectPage;
				UtenteBean utente = new UtenteBean();
				boolean error = false;
				String email = request.getParameter("email");
				String cognome = request.getParameter("cognome");
				String nome = request.getParameter("nome");
				String citta = request.getParameter("citta");
				String provincia = request.getParameter("provincia");
				String via = request.getParameter("via");
				String civico = request.getParameter("civico");
				String cap = request.getParameter("cap");
				String password = request.getParameter("password");
				
				if(email!=null && !email.equals("") &&
				   cognome!=null && !cognome.equals("") &&
				   nome!=null && !nome.equals("") &&
				   citta!=null && !citta.equals("") &&
				   provincia!=null && !provincia.equals("") &&
				   via!=null && !via.equals("") &&
				   civico!=null && !civico.equals("") &&
				   cap!=null && !cap.equals("") &&
				   password!=null && !password.equals("")) {
					
					utente.setEmail(email);

					utente.setCognome(cognome);
					utente.setNome(nome);
					utente.setCitta(citta);
					utente.setProvincia(provincia);
					utente.setVia(via);
					utente.setCivico(civico);
					utente.setCap(cap);
					utente.setPassword(password);
					utente.setRuolo("user");
				} else {
					error = true;
				}
				if(!error) {
					if(model.doRetrieveByKey(email)==null)  {//L'utente non è registrato
						model.doSave(utente);
						request.setAttribute("statusLogon", 1);
						redirectPage = "/login.jsp";
					}
					else {
						request.setAttribute("statusLogon", 0);
						redirectPage = "/logon.jsp";
					}
				} else {
					request.setAttribute("statusLogon", -1);
					redirectPage = "/logon.jsp";
				}
				getServletContext().getRequestDispatcher(redirectPage).forward(request, response);
			} else if(action!=null && action.equals("login")) {
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				
				HttpSession session = request.getSession();
				UtenteBean utente = model.doRetrieveByCond(email, password);
				if(utente.getEmail().equals("")) { //Dati errati o non presenti
					request.setAttribute("statusLogin", false);
					getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
				}
				else {
					request.getSession().setAttribute("email", utente.getEmail());
					if(utente.getRuolo().equals("admin")) {
						session.removeAttribute("cart");
						session.setAttribute("admin", new Boolean(true));
					} else {
						session.setAttribute("admin", new Boolean(false));
					}
					response.sendRedirect("index.jsp");
				}
				
			} else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti o errati.");
			}			
			
		} catch(SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Un'eccezione SQL è stata lanciata.");
		}
	}

}
