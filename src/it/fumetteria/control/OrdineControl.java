package it.fumetteria.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.fumetteria.beans.ArticoloBean;
import it.fumetteria.beans.OrdineBean;
import it.fumetteria.beans.SiRiferisceBean;
import it.fumetteria.cart.ArticoloInCarrello;
import it.fumetteria.cart.Cart;
import it.fumetteria.model.ArticoloModel;
import it.fumetteria.model.ArticoloModelDS;
import it.fumetteria.model.OrdineModel;
import it.fumetteria.model.OrdineModelDS;
import it.fumetteria.model.SiRiferisceModel;
import it.fumetteria.model.SiRiferisceModelDS;
import it.fumetteria.model.UtenteModel;
import it.fumetteria.model.UtenteModelDS;

/**
 * Servlet implementation class OrdineServlet
 */

public class OrdineControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	static OrdineModel ordineModel;
	static ArticoloModel articoloModel;
	static SiRiferisceModel siriferisceModel;
	static UtenteModel utenteModel;
	
	public void init() {
		DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
		ordineModel = new OrdineModelDS(ds);
		articoloModel = new ArticoloModelDS(ds);
		siriferisceModel = new SiRiferisceModelDS(ds);
		utenteModel = new UtenteModelDS(ds);
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrdineControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		
		try {
			if(action!=null) {
				if(action.equals("addOrdine")) {
					Cart cart = (Cart) request.getSession().getAttribute("cart");
					if(cart!=null) {
						boolean ordineOk = true;
						OrdineBean bean = new OrdineBean();
						
						
						ArticoloBean articolo;
						for(ArticoloInCarrello art:cart.getArticoli()) {
							articolo = articoloModel.doRetrieveByKey(art.getArticolo().getCodice());
							int giacenza = articolo.getGiacenza();
							art.setArticolo(articolo);
							if(art.getNumArt()<=giacenza)
								art.getArticolo().setGiacenza(art.getArticolo().getGiacenza()-art.getNumArt());
							else {
								ordineOk = false;
							}
							articoloModel.doUpdate(art.getArticolo());
						}
						bean.setTotale(cart.getTotale());
						bean.setUtente((String)request.getSession().getAttribute("email"));
						int codice = 0;
						if(ordineOk) {
							codice = ordineModel.doSave(bean);
							for(ArticoloInCarrello art: cart.getArticoli()) {
								SiRiferisceBean siriferisceBean = new SiRiferisceBean();
								siriferisceBean.setArticolo(art.getArticolo());
								siriferisceBean.setCosto(art.getArticolo().getPrezzoScontato()*art.getNumArt());
								siriferisceBean.setQuantita(art.getNumArt());
								siriferisceModel.doSave(siriferisceBean);
							}
							request.getSession().removeAttribute("cart");
						}
						
						
						response.sendRedirect("ordine?action=readOrdineDetails&id="+codice);
					}
					
				} else if(action.equals("readOrdineDetails")) {
					String emailSessione = (String) request.getSession().getAttribute("email");
					String emailOrdine = "";
					if(request.getParameter("id")!=null) {
						
						try {
							int codice = Integer.parseInt(request.getParameter("id"));
							if(codice!=0) {
								request.removeAttribute("siriferisce");
								request.setAttribute("siriferisce", siriferisceModel.doRetrieveByCond(codice));
								
								OrdineBean ordine = ordineModel.doRetrieveByKey(codice);
								request.removeAttribute("ordine");
								request.setAttribute("ordine", ordine);
								emailOrdine = ordine.getUtente();
							}
						} catch(NumberFormatException e) {
							response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Number Format Exception was thrown");
						}
						
						
					}
					if(emailOrdine.equals("")) {
						request.removeAttribute("userInfo");
						request.setAttribute("userInfo", utenteModel.doRetrieveByKey(emailSessione));
						getServletContext().getRequestDispatcher("/order.jsp").forward(request, response);
					} else if(emailSessione.equals(emailOrdine) || (request.getSession().getAttribute("admin")!=null && ((boolean)(request.getSession().getAttribute("admin")))==true)){
						request.removeAttribute("userInfo");
						request.setAttribute("userInfo", utenteModel.doRetrieveByKey(emailOrdine));
						getServletContext().getRequestDispatcher("/order.jsp").forward(request, response);
					} else {
						response.sendError(HttpServletResponse.SC_FORBIDDEN,"Operazione non consentita");
					}
				} else if(action.equals("readOrdini")) {
					String utente = (String) request.getSession().getAttribute("email");
					request.removeAttribute("ordini");
					request.setAttribute("ordini", ordineModel.doRetrieveByCond(utente));
					
					getServletContext().getRequestDispatcher("/myorders.jsp").forward(request, response);
				} else if(action.equals("readAllOrdini")) {
					request.removeAttribute("ordini");
					request.setAttribute("ordini", ordineModel.doRetrieveAll());
					
					getServletContext().getRequestDispatcher("/admin/orders.jsp").forward(request, response);
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
		
		String action = request.getParameter("action");
		
		try{
			if(action!=null && action.equals("confermaOrdine"))
			{
			   String dataConsegna = request.getParameter("dataOrdine");
			   int id = Integer.parseInt(request.getParameter("id"));
			   
			   ordineModel.doUpdate(id, dataConsegna);
			   
			   response.sendRedirect("ordine?action=readOrdineDetails&id="+id);
			} else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti o errati");
			}
		}catch(SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Un'eccezione SQL è stata lanciata.");
		}
	}

}
