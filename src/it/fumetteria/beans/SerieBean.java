package it.fumetteria.beans;

import java.io.Serializable;

public class SerieBean implements Serializable {
	private static final long serialVersionUID = -1604898755139259013L;

	private String nome;
	private String periodicita;
	
	public SerieBean() {
		nome = "";
		periodicita = "";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPeriodicita() {
		return periodicita;
	}

	public void setPeriodicita(String periodicita) {
		this.periodicita = periodicita;
	}
	
}
