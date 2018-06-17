package it.fumetteria.search;

import it.fumetteria.beans.AppartieneBean;
import it.fumetteria.beans.ArticoloBean;

public class RicercaBean{

	private ArticoloBean articolo;
	private AppartieneBean serie;
	
	public RicercaBean(){
		articolo = new ArticoloBean();
		serie = new AppartieneBean();	
	}
	
	public ArticoloBean getArticolo() {
		return articolo;
	}

	public void setArticolo(ArticoloBean articolo) {
		this.articolo = articolo;
	}
	
	public AppartieneBean getSerie() {
		return serie;
	}

	public void setSerie(AppartieneBean serie) {
		this.serie = serie;
	}
}
