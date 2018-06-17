package it.fumetteria.cart;

import java.util.ArrayList;
import java.util.List;

import it.fumetteria.beans.ArticoloBean;

public class Cart {
	private List<ArticoloInCarrello> articoli;
	
	public Cart() {
		articoli = new ArrayList<ArticoloInCarrello>();
	}
	
	public boolean addArticolo(ArticoloBean articolo, int numArt) {
		for(ArticoloInCarrello art:articoli) {
			if(art.getArticolo().getCodice() == articolo.getCodice()) {
				updateArticolo(articolo.getCodice(),numArt);
				return false;
			}
		}
		articoli.add(new ArticoloInCarrello(articolo,numArt));
		return true;
	}
	
	public void deleteArticolo(int codice) {
		for(ArticoloInCarrello art : articoli) {
			if(art.getArticolo().getCodice() == codice) {
				articoli.remove(art);
				break;
			}
		}
 	}

	public void updateArticolo(int codice, int numArt) {
		for(ArticoloInCarrello art : articoli) {
			if(art.getArticolo().getCodice() == codice) {
				art.setNumArt(numArt);
				break;
			}
		}
	}
	
	public double getTotale() {
		double totale = 0;
		for(ArticoloInCarrello art : articoli) {
			totale += art.getArticolo().getPrezzoScontato()*art.getNumArt();
		}
		return totale;
	}
	
	public List<ArticoloInCarrello> getArticoli() {
		return articoli;
	}
}

