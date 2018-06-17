package it.fumetteria.beans;

import java.io.Serializable;

public class SiRiferisceBean implements Serializable{
	private static final long serialVersionUID = 1218268272618605946L;
	
	private int ordine;
	private ArticoloBean articolo;
	private double costo;
	private int quantita;
	
	public SiRiferisceBean() {
		ordine = 0;
		articolo = new ArticoloBean();
		costo = 0;
		quantita = 0;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public int getOrdine() {
		return ordine;
	}

	public void setOrdine(int ordine) {
		this.ordine = ordine;
	}

	public ArticoloBean getArticolo() {
		return articolo;
	}

	public void setArticolo(ArticoloBean articolo) {
		this.articolo = articolo;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	
	
	
}
