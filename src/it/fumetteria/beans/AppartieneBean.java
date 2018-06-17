package it.fumetteria.beans;

import java.io.Serializable;

public class AppartieneBean implements Serializable {
	private static final long serialVersionUID = 8817558633970998989L;

	private int fumetto;
	private String serie;
	private int numero;
	
	public AppartieneBean() {
		fumetto = 0;
		serie = "";
		numero = 0;
	}

	public int getFumetto() {
		return fumetto;
	}

	public void setFumetto(int fumetto) {
		this.fumetto = fumetto;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	
}
