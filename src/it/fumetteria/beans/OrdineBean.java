package it.fumetteria.beans;

import java.io.Serializable;
import java.util.Date;

public class OrdineBean implements Serializable {
	private static final long serialVersionUID = 979826894555195910L;
	
	private int numero;
	private Date dataEmissione;
	private Date dataConsegna;
	private double totale;
	private String utente;
	
	public OrdineBean() {
		numero = -1;
		dataEmissione = new Date();
		dataConsegna = new Date();
		totale = 0.0;
		utente = null;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Date getDataEmissione() {
		return dataEmissione;
	}

	public void setDataEmissione(Date dataEmissione) {
		this.dataEmissione = dataEmissione;
	}

	public Date getDataConsegna() {
		return dataConsegna;
	}

	public void setDataConsegna(Date dataConsegna) {
		this.dataConsegna = dataConsegna;
	}

	public double getTotale() {
		return totale;
	}

	public void setTotale(double totale) {
		this.totale = totale;
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}
	
}
