package it.fumetteria.control;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

import it.fumetteria.beans.AppartieneBean;
import it.fumetteria.beans.ArticoloBean;
import it.fumetteria.search.ArticoloRicercaBean;
import it.fumetteria.beans.FumettoBean;
import it.fumetteria.beans.SerieBean;
import it.fumetteria.cart.Cart;
import it.fumetteria.model.AppartieneModel;
import it.fumetteria.model.AppartieneModelDS;
import it.fumetteria.model.ArticoloModel;
import it.fumetteria.model.ArticoloModelDS;
import it.fumetteria.model.FumettoModel;
import it.fumetteria.model.FumettoModelDS;
import it.fumetteria.model.SerieModel;
import it.fumetteria.model.SerieModelDS;

/**
 * Servlet implementation class UtenteServlet
 */
public class ArticoloControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String saveDir;
    
	static ArticoloModel articoloModel;
	static FumettoModel fumettoModel;
	static SerieModel serieModel;
	static AppartieneModel appartieneModel;
	
	public void init() {
		// Get the file location where it would be stored
		saveDir = getServletConfig().getInitParameter("image-upload")+File.separator+getServletConfig().getInitParameter("item-folder");
		DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
		articoloModel = new ArticoloModelDS(ds);
		fumettoModel = new FumettoModelDS(ds);
		appartieneModel = new AppartieneModelDS(ds);
		serieModel = new SerieModelDS(ds);
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArticoloControl() {
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
				if(action.equals("readArticoliByCond")) {
					request.removeAttribute("articoliNovita");
					request.setAttribute("articoliNovita", articoloModel.doRetrieveByCond("novita"));
					request.removeAttribute("articoliPromozioni");
					request.setAttribute("articoliPromozioni", articoloModel.doRetrieveByCond("promozioni"));
					request.removeAttribute("articoliVenduti");
					request.setAttribute("articoliVenduti", articoloModel.doRetrieveByCond("venduti"));
				} else if(action.equals("getImg")) {
					response.setContentType("image/jpeg");
					if(request.getParameter("id")!=null) {
						try {
							int codice = Integer.parseInt(request.getParameter("id"));
							OutputStream out = response.getOutputStream();
							BufferedImage image = null;
							String imgPath = getServletContext().getRealPath("/")+"img/articoli";
							File file = new File(imgPath+"/"+codice+".jpg");
							if(file.exists()) {	
								try {
									image = ImageIO.read(file);
									ImageIO.write(image, "jpg", out);
								} catch(IOException e) {}
							} else {
								try {
									File defaultFile = new File(imgPath+"/"+"noImg.jpg");
									image = ImageIO.read(defaultFile);
									ImageIO.write(image, "jpg", out);
								} catch(IOException e) {}
							}
						} catch(NumberFormatException e) {
							response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Un'eccezione Number Format � stata lanciata.");
						}
					} else {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti.");
					}
				} 
				/*Restituisci informazioni di un articolo*/
				else if(action.equals("readArticolo")) {
					if(request.getParameter("id")!=null) {
						try {
							int codice = Integer.parseInt(request.getParameter("id"));
							
							request.removeAttribute("articolo");
							request.setAttribute("articolo", articoloModel.doRetrieveByKey(codice));
							
							String redirect = "/articoloView.jsp";
							
							if(request.getSession().getAttribute("admin")!=null && 
									((boolean)request.getSession().getAttribute("admin"))==true && 
									request.getParameter("update")!=null) {
								redirect ="/admin/addArticolo.jsp";
							} 
							RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(redirect);
							dispatcher.forward(request, response);
						}catch(NumberFormatException e) {
							response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Un'eccezione Number Format � stata lanciata.");
						}
					} else {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti.");
					}
				} 
				/*Restituisci tutti gli articoli di una determinata categoria*/
				else if(action.equals("readAllArticoli")) {
					String categoria = request.getParameter("categoria");
					if(categoria!=null && !categoria.equals("")) {
						request.removeAttribute("articoli");
						request.setAttribute("articoli", articoloModel.doRetrieveByCond(categoria));
						
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/articoli.jsp");
						dispatcher.forward(request, response);
					} else {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti.");
					}
					
				} 
				/*Aggiungi un articolo al carrello*/
				else if(action.equals("addArticoloToCart")) {
					response.setContentType("application/json");
					JSONObject jsonObj = new JSONObject();
					Cart cart = (Cart) request.getSession().getAttribute("cart");
					if(cart==null) {
						cart = new Cart();
					}
					if(request.getParameter("id")!=null && request.getParameter("numArt")!=null) {
						try {
							
							int codice = Integer.parseInt(request.getParameter("id"));
							int numArt = Integer.parseInt(request.getParameter("numArt")); 
							ArticoloBean bean = articoloModel.doRetrieveByKey(codice);
							boolean status;
							if(numArt<=bean.getGiacenza() && numArt>0) {
								status = cart.addArticolo(bean, numArt);
							}else {
								status = false;
							}
							request.getSession().setAttribute("cart", cart);
							try {
								jsonObj.append("status", status);
								jsonObj.append("numArt", cart.getArticoli().size());
							} catch(JSONException e) {
								
							}
							
							response.getWriter().print(jsonObj.toString());
						} catch(NumberFormatException e) {
							response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Un'eccezione Number Format � stata lanciata.");
						}
					} else {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti.");
					}
				} 
				/*Elimina articolo dal carrello*/
				else if(action.equals("delArticoloInCart")) {
					Cart cart = (Cart) request.getSession().getAttribute("cart");
					if(cart==null) {
						cart = new Cart();
					} else {
						if(request.getParameter("id")!=null) {
							try {
								int codice = Integer.parseInt(request.getParameter("id"));
								cart.deleteArticolo(codice);
								
								if(cart.getArticoli().isEmpty())
									request.getSession().removeAttribute("cart");
								else
									request.getSession().setAttribute("cart", cart);
								
								response.sendRedirect("cart.jsp");
							} catch(NumberFormatException e){
								response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Un'eccezione Number Format � stata lanciata.");
							}
						} else {
							response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti.");
						}
					}
				} 
				/*Aggiorna dati carrello*/
				else if(action.equals("updateArticoloInCart")) {
					Cart cart = (Cart) request.getSession().getAttribute("cart");
					if(cart==null) {
						cart = new Cart();
					}
					if(request.getParameter("id")!=null && request.getParameter("numArt")!=null) {
						try {
							int codice = Integer.parseInt(request.getParameter("id"));
							int numArt = Integer.parseInt(request.getParameter("numArt"));
							ArticoloBean bean = articoloModel.doRetrieveByKey(codice);
							if(numArt<=bean.getGiacenza() && numArt>0) {
								cart.updateArticolo(codice, numArt);
								request.setAttribute("aggiornato", true);
							} else {
								request.setAttribute("aggiornato", false);
							}
							request.getSession().setAttribute("cart", cart);
							
							
							getServletContext().getRequestDispatcher("/cart.jsp").forward(request,response);
						} catch(NumberFormatException e) {
							response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Un'eccezione Number Format � stata lanciata.");
						}
					} else {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti.");
					}
				} /*Cancella un articolo*/
				else if(action.equals("delArticolo")) {
					if(request.getParameter("id")!=null) {
						try {
							int codice = Integer.parseInt(request.getParameter("id"));
							articoloModel.doDelete(codice);
							
							String imgPath = getServletContext().getRealPath("/")+"img/articoli";
							File file = new File(imgPath+"/"+codice+".jpg");
							if(file.exists()) {
								file.delete();
							}
							response.sendRedirect("index.jsp");
						} catch(NumberFormatException e) {
							response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Un'eccezione Number Format � stata lanciata.");
						}
					} else {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti.");
					}
				} else if(action.equals("readArticoliPref")) {
					String serie = request.getParameter("serie");
					if(serie!=null && !serie.equals("")) {
					
						request.removeAttribute("articoli");
						request.setAttribute("articoli", articoloModel.doRetrieveBySerie(serie));
						
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/articoli.jsp");
						dispatcher.forward(request, response);
					} else {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti.");
					}
				} else {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Errore nei parametri: il parametro 'action' non � valido.");
				}
			} else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti.");
			}
			
		} catch(SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Un'eccezione SQL � stata lanciata.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		try {
			if(action!=null) {
				/*Aggiungi articolo*/
				if(action.equals("addArticolo")) {
					try {
						String redirectPath = "articolo";
						ArticoloBean bean = new ArticoloBean();
						boolean error = false;
						
						String nome = request.getParameter("nome");
						if(nome!=null && !nome.equals("")) {
							bean.setNome(nome);
						} else {
							error = true;
						}
						
						String categoria = request.getParameter("categoria");
						if(categoria!=null && !categoria.equals("")) {
							bean.setCategoria(categoria);
						} else {
							error = true;
						}
						
						String descrizione = request.getParameter("descrizione");
						if(descrizione==null || descrizione.equals("")) {
							descrizione = "Nessuna descrizione.";
						}
						bean.setDescrizione(descrizione);
						
						String giacenza = request.getParameter("giacenza");
						if(giacenza==null || giacenza.equals("")) {
							giacenza = "0";
						}
						bean.setGiacenza(Integer.parseInt(giacenza));
						
						String isFumetto = request.getParameter("isFumetto");
						if(isFumetto==null || isFumetto.equals("")) {
							isFumetto = "false";
						}
						bean.setIsFumetto(Boolean.parseBoolean(isFumetto));
						
						String prezzo = request.getParameter("prezzo");
						if(prezzo==null || prezzo.equals("")) {
							prezzo = "0";
						}
						bean.setPrezzo(Double.parseDouble(prezzo));
						
						String sconto = request.getParameter("sconto");
						if(sconto==null || sconto.equals("")) {
							sconto = "0";
						}
						bean.setSconto(Integer.parseInt(sconto));
						
						int codice=0;
						if(!error) {
							codice = articoloModel.doSave(bean);
							caricaImmagine(request, codice);
						}
						if(!error && bean.isFumetto()) {
							redirectPath = "fumetto";
							FumettoBean fBean = new FumettoBean();
							
							fBean.setCodice(codice);
							String formato = request.getParameter("formato");
							if(formato!=null && !formato.equals("")) {
								fBean.setFormato(formato);
							} else {
								error = true;
							}
							String genere = request.getParameter("genere");
							if(genere!=null && !genere.equals("")) {
								fBean.setGenere(genere);
							} else {
								error = true;
							}
							String interni = request.getParameter("interni");
							if(interni!=null && !interni.equals("")) {
								fBean.setInterni(interni);
							} else {
								error = true;
							}
							String numPagine = request.getParameter("numPagine");
							if(numPagine==null || numPagine.equals("")) {
								numPagine = "0";
							}	
							fBean.setNumeroPagine(Integer.parseInt(numPagine));
							if(!error) {
								fumettoModel.doSave(fBean);
							}
							String inSerie = request.getParameter("inSerie");
							if(inSerie==null || inSerie.equals("")) {
								inSerie = "false";
							}
							if(!error && Boolean.parseBoolean(inSerie)) {
								SerieBean sBean = new SerieBean();
								
								String nomeSerie = request.getParameter("nomeSerie");
								if(nomeSerie!=null && !nomeSerie.equals("")) {
									sBean.setNome(nomeSerie);
								} else {
									error = true;
								}
								String periodicita = request.getParameter("periodicita");
								if(periodicita!=null && !periodicita.equals("")) {
									sBean.setPeriodicita(periodicita);
								} else {
									error = true;
								}
								
								if(!error) {
									AppartieneBean aBean = new AppartieneBean();
									aBean.setSerie(sBean.getNome());
									aBean.setFumetto(fBean.getCodice());
									String numero = request.getParameter("numero");
									if(numero==null || numero.equals("")) {
										numero = "0";
									}
									aBean.setNumero(Integer.parseInt(numero));
									if(serieModel.doRetrieveByKey(sBean.getNome())==null) //Se la serie non esiste, la creo
										serieModel.doSave(sBean);
									appartieneModel.doSave(aBean); //aggiungo associazione fumetto-serie
								}
							}
						} 
						if(!error) {
							response.sendRedirect(redirectPath+"?action=readArticolo&id="+codice);
						} else {
							request.setAttribute("status", false);
							getServletContext().getRequestDispatcher("/admin/addArticolo.jsp").forward(request, response);
						}
					}catch(NumberFormatException e) {
						response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Un'eccezione Number Format � stata lanciata.");
					}
				}
				
				/*Aggiorna dati articolo*/
				else if(action.equals("updateArticolo")) {
					try {
						String redirectPath = "articolo";
						ArticoloBean bean = new ArticoloBean();
						boolean error = false;
						
						String id = request.getParameter("id");
						if(id!=null && !id.equals("")) {
							bean.setCodice(Integer.parseInt(id));
						} else {
							error = true;
						}
						
						String nome = request.getParameter("nome");
						if(nome!=null && !nome.equals("")) {
							bean.setNome(nome);
						} else {
							error = true;
						}
						
						String descrizione = request.getParameter("descrizione");
						if(descrizione==null || descrizione.equals("")) {
							descrizione = "Nessuna descrizione.";
						}
						bean.setDescrizione(descrizione);
						
						String giacenza = request.getParameter("giacenza");
						if(giacenza==null || giacenza.equals("")) {
							giacenza = "0";
						}
						bean.setGiacenza(Integer.parseInt(giacenza));
						
						String isFumetto = request.getParameter("isFumetto");
						if(isFumetto==null || isFumetto.equals("")) {
							isFumetto = "false";
						}
						bean.setIsFumetto(Boolean.parseBoolean(isFumetto));
						
						String prezzo = request.getParameter("prezzo");
						if(prezzo==null || prezzo.equals("")) {
							prezzo = "0";
						}
						bean.setPrezzo(Double.parseDouble(prezzo));
						
						String sconto = request.getParameter("sconto");
						if(sconto==null || sconto.equals("")) {
							sconto = "0";
						}
						bean.setSconto(Integer.parseInt(sconto));
						
						if(!error) {
							articoloModel.doUpdate(bean);
						}
						if(!error && bean.isFumetto()) {
							redirectPath = "fumetto";
							FumettoBean fBean = new FumettoBean();
							
							fBean.setCodice(Integer.parseInt(id));
							String formato = request.getParameter("formato");
							if(formato!=null && !formato.equals("")) {
								fBean.setFormato(formato);
							} else {
								error = true;
							}
							String genere = request.getParameter("genere");
							if(genere!=null && !genere.equals("")) {
								fBean.setGenere(genere);
							} else {
								error = true;
							}
							String interni = request.getParameter("interni");
							if(interni!=null && !interni.equals("")) {
								fBean.setInterni(interni);
							} else {
								error = true;
							}
							String numPagine = request.getParameter("numPagine");
							if(numPagine==null || numPagine.equals("")) {
								numPagine = "0";
							}	
							fBean.setNumeroPagine(Integer.parseInt(numPagine));
							if(!error) {
								fumettoModel.doUpdate(fBean);
							}
							String inSerie = request.getParameter("inSerie");
							if(inSerie==null || inSerie.equals("")) {
								inSerie = "false";
							}
							if(!error && Boolean.parseBoolean(inSerie)) {
								SerieBean sBean = new SerieBean();
								
								String nomeSerie = request.getParameter("nomeSerie");
								if(nomeSerie!=null && !nomeSerie.equals("")) {
									sBean.setNome(nomeSerie);
								} else {
									error = true;
								}
								String periodicita = request.getParameter("periodicita");
								if(periodicita!=null && !periodicita.equals("")) {
									sBean.setPeriodicita(periodicita);
								} else {
									error = true;
								}
								
								if(!error) {
									AppartieneBean aBean = new AppartieneBean();
									aBean.setSerie(sBean.getNome());
									aBean.setFumetto(fBean.getCodice());
									String numero = request.getParameter("numero");
									if(numero==null || numero.equals("")) {
										numero = "0";
									}
									aBean.setNumero(Integer.parseInt(numero));
									if(serieModel.doRetrieveByKey(sBean.getNome())!=null) { //la serie esiste
										serieModel.doUpdate(sBean);
									} else {
										serieModel.doSave(sBean);
									}
									
									if(appartieneModel.doRetrieveByKey(fBean.getCodice()).getFumetto()!=0) { //il fumetto appartiene gi� alla serie
										appartieneModel.doUpdate(aBean);
									} else { //Il fumetto non appartiene a nessuna serie
										appartieneModel.doSave(aBean); 
									}
								}
							}
						}
						if(!error) {
							response.sendRedirect(redirectPath+"?action=readArticolo&id="+id);
						} else {
							request.setAttribute("status", false);
							getServletContext().getRequestDispatcher("/admin/addArticolo.jsp").forward(request, response);
						}
					}catch(NumberFormatException e) {
						response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Un'eccezione Number Format � stata lanciata.");
					}
				} 
				
				/*Ricerca avanzata articolo*/
				else if(action.equals("searchArticoli")){
					try {
						String nome, genere, categoria, interni, ordinamento;
						if(request.getParameter("nome")!=null && !request.getParameter("nome").trim().equals(""))
							nome = request.getParameter("nome");
						else 
							nome = null;
						
						if(request.getParameter("categoria")!=null && !request.getParameter("categoria").trim().equals(""))
							categoria = request.getParameter("categoria");
						else
							categoria = "seleziona";
						
						if(request.getParameter("genere")!=null && !request.getParameter("genere").trim().equals(""))
							genere = request.getParameter("genere");
						else
							genere = null;
						
						if(request.getParameter("interni")!=null && !request.getParameter("interni").trim().equals(""))
							interni = request.getParameter("interni");
						else
							interni = "seleziona";
						
						boolean disponibile = Boolean.parseBoolean(request.getParameter("disponibile"));
						boolean scontato = Boolean.parseBoolean(request.getParameter("scontato"));
						boolean inSerie = Boolean.parseBoolean(request.getParameter("inSerie"));
						double prezzoMin;
						if(request.getParameter("prezzoMin")!=null && !request.getParameter("prezzoMin").equals(""))
							prezzoMin = Double.parseDouble(request.getParameter("prezzoMin"));
						else
							prezzoMin = -1;
						double prezzoMax;
						if(request.getParameter("prezzoMax")!=null && !request.getParameter("prezzoMax").equals(""))
							prezzoMax = Double.parseDouble(request.getParameter("prezzoMax"));
						else
							prezzoMax = -1;
						
						if(request.getParameter("ordinamento")!=null && !request.getParameter("ordinamento").equals(""))
							ordinamento = request.getParameter("ordinamento");
						else
							ordinamento = "codice";
						
						ArticoloRicercaBean bean = new ArticoloRicercaBean();
						bean.setNome(nome);
						bean.setCategoria(categoria);
						bean.setGenere(genere);
						bean.setInterni(interni);
						bean.setDisponibile(disponibile);
						bean.setScontato(scontato);
						bean.setInSerie(inSerie);
						bean.setPrezzoMin(prezzoMin);
						bean.setPrezzoMax(prezzoMax);
						bean.setOrdinamento(ordinamento);
						
						request.removeAttribute("articoli");
						request.setAttribute("articoli", articoloModel.doSearch(bean));
						
						request.removeAttribute("datiRicerca");
						request.setAttribute("datiRicerca", bean);
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/articoli.jsp");
						dispatcher.forward(request, response);
					} catch(NumberFormatException e) {
						response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Un'eccezione Number Format � stata lanciata.");
					}
				} else {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Errore nei parametri: il parametro 'action' non � valido.");
				}

			} else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti.");
			}
		} catch(SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Un'eccezione SQL � stata lanciata.");
		}
	}
	
	private String caricaImmagine(HttpServletRequest request, long codice) throws IOException, ServletException {
		String savePath = request.getServletContext().getRealPath("/") + saveDir;

		File fileSaveDir = new File(savePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}

		String fileName = "";
		Part part = request.getPart("immagine");
		if(part != null && part.getSize()!=0) {
			fileName = codice + ".jpg";
			if (fileName != null && !fileName.equals("")) {
				part.write(savePath + File.separator + fileName);
			}
		} 
		
		return fileName;
	}
}
