package it.fumetteria.cart;

import it.fumetteria.beans.ArticoloBean;

public class ArticoloInCarrello {

	private ArticoloBean articolo;
	private int numArt;
	
	public ArticoloInCarrello() {
		articolo = null;
		numArt = 0;
	}
	
	public ArticoloInCarrello(ArticoloBean articolo, int numArt) {
		this.articolo = articolo;
		this.numArt = numArt;
	}

	public ArticoloBean getArticolo() {
		return articolo;
	}

	public void setArticolo(ArticoloBean articolo) {
		this.articolo = articolo;
	}

	public int getNumArt() {
		return numArt;
	}

	public void setNumArt(int numArt) {
		this.numArt = numArt;
	}
}
