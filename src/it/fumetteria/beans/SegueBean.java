package it.fumetteria.beans;

import java.io.Serializable;

public class SegueBean implements Serializable {
	private static final long serialVersionUID = 6219071781568379665L;
	
	private String utente;
	private SerieBean serie;
	
	public SegueBean() {
		utente = "";
		serie = new SerieBean();
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public SerieBean getSerie() {
		return serie;
	}

	public void setSerie(SerieBean serie) {
		this.serie = serie;
	}

}
