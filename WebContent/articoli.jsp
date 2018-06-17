<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8" import="java.util.*, it.fumetteria.beans.ArticoloBean, it.fumetteria.cart.*, java.text.NumberFormat, 
    it.fumetteria.search.*,it.fumetteria.beans.AppartieneBean, it.fumetteria.search.ArticoloRicercaBean" %>
<%
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(2);
    nf.setMinimumFractionDigits(2);

	Collection<?> articoli = (Collection<?>) request.getAttribute("articoli");
	ArticoloRicercaBean datiRicerca = (ArticoloRicercaBean) request.getAttribute("datiRicerca");
%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="shortcut icon" type="image/png" href="img/favicon.png"/>
		<link type="text/css" rel="stylesheet" href="style/nav.css">
		<link type="text/css" rel="stylesheet" href="style/main.css">
		<link type="text/css" rel="stylesheet" href="style/articoli.css">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="Lista articoli in vendita">
		<meta name="author" content="Vincenzo Caputo, Andrea Lorenzo">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script src="/Fumetteria/script/ajax.js"></script>
		<script src="/Fumetteria/script/articoli.js" charset="UTF-8"></script>
		<title>Articoli</title>
	</head>
	<body>
		<%@include file="header.jsp" %>
		
		<button id="filtriOpen" onclick="showRicercaAvanzata()">Ricerca avanzata</button>
		<main>
			<aside>
				<h2>Filtri</h2>
				<hr>
				<form action="articolo" method="post" name="ricercaAvanzata" onsubmit="return validateSearch();">
					<div id="ordinaForm">
						<label for="ordinamentoSelect">Ordina per</label>
						
						<select name="ordinamento" id="ordinamentoSelect">
							<%if(datiRicerca!=null && datiRicerca.getOrdinamento().equals("codice")) {%>
								<option value="codice" selected>Data inserimento</option>
							<%} else { %>
								<option value="codice">Data inserimento</option>
							<%} if(datiRicerca!=null && datiRicerca.getOrdinamento().equals("prezzoC")) {%>
								<option value="prezzoC" selected>Prezzo (crescente)</option>
							<%} else { %>
								<option value="prezzoC">Prezzo (crescente)</option>
							<%} if(datiRicerca!=null && datiRicerca.getOrdinamento().equals("prezzoD")) { %>
						  		<option value="prezzoD" selected>Prezzo (decrescente)</option>
						  	<%} else { %>
						  		<option value="prezzoD">Prezzo (decrescente)</option>
						  	<%} %>
						</select>
					</div>
					<div id="categoriaForm">
						<label for="categoriaSelect">Categoria</label>
						<select name="categoria" id="categoriaSelect">
						<%if(datiRicerca!=null && datiRicerca.getCategoria().equals("seleziona")) {%>
							<option value="seleziona" selected>Seleziona</option>
						<%}else{ %>
						    <option value="seleziona">Seleziona</option>
						 <%} %>	
						 <%if(datiRicerca!=null && datiRicerca.getCategoria().equals("comics")) {%>
							<option value="comics" selected>Comics</option>
						<%} else { %>
							<option value="comics">Comics</option>
						<%} %>	
						 <%if(datiRicerca!=null && datiRicerca.getCategoria().equals("manga")) {%>	
						  	<option value="manga" selected>Manga</option>
						 <%}else{ %>
						    <option value="manga">Manga</option>
						 <%} %>   
						 <%if(datiRicerca!=null && datiRicerca.getCategoria().equals("gadgets")) {%> 	
						 	<option value="gadgets" selected>Gadgets</option>
						 <%}else{ %>
						    <option value="gadgets">Gadgets</option>
						 <%} %>   
						 <%if(datiRicerca!=null && datiRicerca.getCategoria().equals("accessori")) {%>	
						  	<option value="accessori" selected>Accessori</option>
						 <%}else{ %> 
						    <option value="accessori">Accessori</option>
						 <%} %>    	
						</select>
					</div>
					<div id="genereForm">
						<label for="genereInput">Genere</label>
						<%if(datiRicerca!=null && datiRicerca.getGenere()!=null) {%>
							<input type="text" name="genere" id="genereInput" value="<%=datiRicerca.getGenere()%>">
						<%} else { %>
							<input type="text" name="genere" id="genereInput">
						<%} %>
						<div class="errorSearch"></div>
					</div>
					<div id="interniForm">
						<label for="interniSelect">Interni</label>
						<select name="interni" id="interniSelect">
							<%if(datiRicerca!=null && datiRicerca.getInterni().equals("seleziona")) { %>
								<option value="seleziona" selected>Seleziona</option>
							<%} else { %>
								<option value="seleziona">Seleziona</option>
							<%} if(datiRicerca!=null && datiRicerca.getInterni().equals("b/n")) { %>
								<option value="b/n" selected>b/n</option>
							<%} else { %>
								<option value="b/n">b/n</option>
							<%} if(datiRicerca!=null && datiRicerca.getInterni().equals("col.")) { %>
						  		<option value="col." selected>col.</option>
						  	<%} else { %>
						  		<option value="col.">col.</option>
						  	<%} %>
						</select>
					</div>
					
					<div class="checkbox">
						<%if(datiRicerca!=null && datiRicerca.isScontato()==true) { %>
							<input type="checkbox" name="scontato" value="true" id="scontatoCheckbox" checked/>
						<%} else { %>
							<input type="checkbox" name="scontato" value="true" id="scontatoCheckbox"/>
						<%} %>
						<label for="scontatoCheckbox">In Promozione</label>
					</div>
					<div class="checkbox">
						<%if(datiRicerca!=null && datiRicerca.isDisponibile()==true) { %>
							<input type="checkbox" name="disponibile" value="true" id="disponibileCheckbox" checked/>
						<%} else { %>
							<input type="checkbox" name="disponibile" value="true" id="disponibileCheckbox"/>
						<%} %>
						<label for="disponibileCheckbox">Disponibile</label>
					</div>
					<div class="checkbox">
						<%if(datiRicerca!=null && datiRicerca.isInSerie()==true) { %>
							<input type="checkbox" name="inSerie" value="true" id="inSerieCheckbox" checked/>
						<%} else { %>
							<input type="checkbox" name="inSerie" value="true" id="inSerieCheckbox"/>
						<%} %>
       					<label for="inSerieCheckbox">In Serie</label>
					</div>
					<div id="priceForm">
						<label for="price-min">Prezzo Minimo:</label>
						<%if(datiRicerca!=null && datiRicerca.getPrezzoMin()>=0) {%>
							<input type="number" name="prezzoMin" id="price-min" value="<%=datiRicerca.getPrezzoMin()%>" min="0" step="0.1">
						<% } else { %>
							<input type="number" name="prezzoMin" id="price-min" value="0" min="0" step="0.1">
						<%} %>
						<div class="errorSearch"></div>
        				<label for="price-max">Prezzo Massimo:</label>
        				<%if(datiRicerca!=null && datiRicerca.getPrezzoMax()>=0) {%>
        					<input type="number" name="prezzoMax" id="price-max" value="<%=datiRicerca.getPrezzoMax() %>" min="0" step="0.1">
        				<%} else { %>
        					<input type="number" name="prezzoMax" id="price-max" value="" min="0" step="0.1">
        				<%} %>
        				<div class="errorSearch"></div>
       				</div>
       				<input type="hidden" name="action" value="searchArticoli">
       				<input type="hidden" value="" name="nome">
       				<input type="hidden" name="page" value="0">
       				<div id="submitSearch">
       					<input type="submit" value="Cerca" >
       				</div>
				</form>
			</aside>
			
			
			<section id="artView">
				<%
					if(articoli!=null && articoli.size()>0) {
						Iterator<?> it = articoli.iterator();
						while (it.hasNext()) {
							RicercaBean bean = (RicercaBean) it.next();
							ArticoloBean articolo = bean.getArticolo();
							AppartieneBean appartiene = bean.getSerie();
							%>
								<article class="art">
									<div class="artImg">
										<img src="articolo?action=getImg&id=<%=articolo.getCodice()%>" alt="Immagine articolo"/>

									</div>
									<div class="artDetails">
										<h2>
											<%=articolo.getNome() %>
											<% if(appartiene.getNumero()!=0) { %>
												<%=appartiene.getNumero() %>
											<% } %>
										</h2>
										<div class="category">
											<%=articolo.getCategoria() %>
											<% if(appartiene.getSerie()!=null) { %>
												- <%=appartiene.getSerie()%>
											<%} %>
										</div>
										
										
										<div class="stock">Disponibilità: <%=articolo.getGiacenza() %></div>
										<div class="date"><%=articolo.getDataInserimento() %></div>
										
									</div>
									<div class="artCart">
										
										<div class="price"><h3>Prezzo <%=nf.format(articolo.getPrezzoScontato())%>&euro;</h3></div>
										<% if(articolo.getSconto()>0) {%>
											<div class="oldPrice"><%=nf.format(articolo.getPrezzo()) %>&euro;</div>
										<% } %>
										
										<%
										if(session.getAttribute("email")==null || ((boolean)(session.getAttribute("admin"))==false)) {%>
											<% 	if(articolo.getGiacenza()>0) { %>
												<a  onclick="ajaxCall('articolo?action=addArticoloToCart&id=<%=articolo.getCodice()%>&numArt=1',addToCartHandler)" class="addToCart">Aggiungi al carrello</a>
											<% 	} else  { %>
												<div class="notAvailable">Non disponibile</div>
										<%		
												} 
										}
										String servlet;
										if(articolo.isFumetto()) { 
											servlet = "fumetto";
										} else  {
											servlet = "articolo";
										}
										%>
										<a href="<%=servlet%>?action=readArticolo&id=<%=articolo.getCodice()%>" class="article">Scheda articolo</a>
					
									</div>
								</article>
								<hr>
							<% 
						}
					} else {
					%>
						<h2>Nessun articolo trovato</h2>
						
				<%  } %>
			</section>
		</main>
		<%@include file="footer.html" %>
	</body>
</html>