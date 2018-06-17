package it.fumetteria.beans;

import java.io.Serializable;

public class UtenteBean implements Serializable {
	private static final long serialVersionUID = -6302534851525759749L;
	
	private String cognome;
	private String nome;
	private String email;
	private String via;
	private String civico;
	private String cap;
	private String citta;
	private String provincia;
	private String password;
	private String ruolo;
	
	public UtenteBean() {
		cognome = "";
		nome = "";
		email = "";
		via = "";
		civico = "";
		cap = "";
		citta = "";
		provincia = "";
		password = "";
		ruolo = "";
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
