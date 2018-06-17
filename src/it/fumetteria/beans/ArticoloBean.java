package it.fumetteria.beans;

import java.io.Serializable;
import java.util.Date;

public class ArticoloBean implements Serializable {
	private static final long serialVersionUID = -2938807228565282153L;
	
	private int codice;
	private String nome;
	private boolean isFumetto;
	private double prezzo;
	private int sconto;
	private String categoria;
	private int giacenza;
	private String descrizione;
	private Date dataInserimento;
	
	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public void setFumetto(boolean isFumetto) {//rivedere questo metodo, esiste gi� isFumetto
		this.isFumetto = isFumetto;
	}

	public ArticoloBean() {
		codice = -1;
		nome = "";
		isFumetto = false;
		prezzo = 0;
		sconto = 0;
		categoria = "";
		giacenza = 0;
		descrizione = "";
	}

	public int getCodice() {
		return codice;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isFumetto() {
		return isFumetto;
	}

	public void setIsFumetto(boolean isFumetto) {
		this.isFumetto = isFumetto;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public int getSconto() {
		return sconto;
	}

	public void setSconto(int sconto) {
		this.sconto = sconto;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getGiacenza() {
		return giacenza;
	}

	public void setGiacenza(int giacenza) {
		this.giacenza = giacenza;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public double getPrezzoScontato() {
		return prezzo*(100-sconto)/100;
	}
}
