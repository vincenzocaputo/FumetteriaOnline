package it.fumetteria.beans;

import java.io.Serializable;

public class FumettoBean extends ArticoloBean implements Serializable {
	private static final long serialVersionUID = 5329766009673946778L;
	
	private String genere;
	private String interni;
	private int numeroPagine;
	private String formato;
	
	public FumettoBean() {
		super();
		genere = "";
		interni = "";
		numeroPagine = 0;
		formato = "";
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public String getInterni() {
		return interni;
	}

	public void setInterni(String interni) {
		this.interni = interni;
	}

	public int getNumeroPagine() {
		return numeroPagine;
	}

	public void setNumeroPagine(int numeroPagine) {
		this.numeroPagine = numeroPagine;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}
	
	
}
