package it.fumetteria.search;

public class ArticoloRicercaBean {

	@Override
	public String toString() {
		return "ArticoloRicercaBean [nome=" + nome + ", categoria=" + categoria + ", genere=" + genere + ", interni="
				+ interni + ", disponibile=" + disponibile + ", scontato=" + scontato + ", prezzoMin=" + prezzoMin
				+ ", prezzoMax=" + prezzoMax + ", inSerie=" + inSerie + "]";
	}

	private String nome;
	private String categoria;
	private String genere;
	private String interni;
	private boolean disponibile;
	private boolean scontato;
	private double prezzoMin;
	private double prezzoMax;
	private boolean inSerie;
	private String ordinamento;
	
	public ArticoloRicercaBean() {
		nome = "";
		categoria = "";
		genere = "";
		interni = "";
		disponibile = false;
		scontato = false;
		prezzoMin = 0;
		prezzoMax = 0;
		inSerie = false;
		ordinamento = "";
	}

	public String getOrdinamento() {
		return ordinamento;
	}

	public void setOrdinamento(String ordinamento) {
		this.ordinamento = ordinamento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
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

	public boolean isDisponibile() {
		return disponibile;
	}

	public void setDisponibile(boolean disponibile) {
		this.disponibile = disponibile;
	}

	public boolean isScontato() {
		return scontato;
	}

	public void setScontato(boolean scontato) {
		this.scontato = scontato;
	}

	public double getPrezzoMin() {
		return prezzoMin;
	}

	public void setPrezzoMin(double prezzoMin) {
		this.prezzoMin = prezzoMin;
	}

	public double getPrezzoMax() {
		return prezzoMax;
	}

	public void setPrezzoMax(double prezzoMax) {
		this.prezzoMax = prezzoMax;
	}

	public boolean isInSerie() {
		return inSerie;
	}

	public void setInSerie(boolean inSerie) {
		this.inSerie = inSerie;
	}
}
