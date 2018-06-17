<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="UTF-8" import="it.fumetteria.beans.SerieBean, java.util.*, it.fumetteria.beans.ArticoloBean, it.fumetteria.beans.FumettoBean, it.fumetteria.beans.AppartieneBean, java.text.NumberFormat"%>

<%
	Collection<?> serie = (Collection<?>)request.getAttribute("serieAll");

	NumberFormat nf = NumberFormat.getInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
	
	Boolean status = (Boolean) request.getAttribute("status");
	ArticoloBean articolo = (ArticoloBean) request.getAttribute("articolo");
	AppartieneBean appartiene = (AppartieneBean) request.getAttribute("serie");
	SerieBean serieInfo = (SerieBean) request.getAttribute("serieInfo");
	
	FumettoBean fumetto = new FumettoBean();
	String action = "updateArticolo";
	if(articolo==null){
		action="addArticolo";
		articolo = new ArticoloBean();
	} else {
		if(articolo.isFumetto()) {
			fumetto = (FumettoBean)articolo;
		}
	}
	
	if(appartiene==null) {
		appartiene = new AppartieneBean();
	}
	
	if(serieInfo==null) {
		serieInfo = new SerieBean();
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="shortcut icon" type="image/png" href="/Fumetteria/img/favicon.png"/>
		<link type="text/css" rel="stylesheet" href="/Fumetteria/style/nav.css">
		<link type="text/css" rel="stylesheet" href="/Fumetteria/style/main.css">
		<link type="text/css" rel="stylesheet" href="/Fumetteria/style/addArticolo.css">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="Lista articoli in vednita">
		<meta name="author" content="Vincenzo Caputo, Andrea Lorenzo">
		<script src="/Fumetteria/script/addArticolo.js"></script>
		<title>Gestione articolo</title>
		<script src="/Fumetteria/script/ajax.js"></script>
		<script>
			window.onload = function() {
				enableFumetto(document.addArticoloForm.categoria);
				serieEnable(document.addArticoloForm.inSerieCheck.checked);
			};
			
			function serieHandler(responseText) {
				try {
					var json = JSON.parse(responseText);
					var serie = json.serie;
					var list = "";
					document.getElementById("nomeSerieList").innerHTML = "";
					if(serie==null) {
						document.getElementById("periodicitaField").readOnly = false;
					} else {
						document.getElementById("periodicitaField").readOnly = true;
						for(var i=0; i<serie.length; i++) {
							var node = document.createElement("option");
							node.value = serie[i].nome;
							document.getElementById("nomeSerieList").appendChild(node);
							document.getElementById("periodicitaField").value = serie[i].periodicita;
						}
					}
				}
				catch(e) {
				}
			}
		</script>
	</head>
	<body>
		<%@include file="../header.jsp" %>
		<main>
			<h1>Gestione articolo</h1>
			<% if(status!=null && !status) {%>
				<h4>Parametri mancanti o errati</h4>
			<%} %>
			<form action="/Fumetteria/articolo" method="post" id="addArticoloForm" name="addArticoloForm" enctype="multipart/form-data" onsubmit="return validateForm();">
				<div id="articoloForm">
					<%if(articolo.getCodice()>0) {%>
						<span id="artCode">Codice: <%=articolo.getCodice() %></span>
					<%} %>
					<label for="nomeField">Nome</label>
					<input type="text" name="nome" id="nomeField" value="<%=articolo.getNome()%>"/>
					<div class="errorString"></div>
					
					<%if(!articolo.getCategoria().equals("")) { %>
						<input type="hidden" name="categoria" id="categoriaSelect" value="<%=articolo.getCategoria() %>">
					<%} else {%>
						<label for="categoriaSelect">Categoria</label>
						<select name="categoria" id="categoriaSelect" onchange="enableFumetto(this)">
							<option value="Comics">Comics</option>
							<option value="Manga">Manga</option>
							<option value="Gadgets">Gadgets</option>
							<option value="Accessori">Accesori</option>
						</select>
						<div class="errorString"></div>
					<%} %>
					
					<label for="giacenzaField">Giacenza</label>
					<input type="number" name="giacenza" min="0" id="giacenzaField" value="<%=articolo.getGiacenza()%>">

					<div class="errorString"></div>
					<label for="prezzoField">Prezzo</label>
					<input type="number" name="prezzo" id="prezzoField" step="0.01" value="<%=articolo.getPrezzo()%>">
					<div class="errorString"></div>
					<label for="scontoField">Sconto</label>
					<input type="number" name="sconto" min="0" id="scontoField" value="<%=articolo.getSconto()%>">
					<div class="errorString"></div>
					<label for="descrizioneArea">Descrizione</label>
					<textarea name="descrizione" id="descrizioneArea" rows="5"><%=articolo.getDescrizione()%></textarea>
					<div class="errorString"></div>
					
					<%if(articolo.getCodice()<1) {%>
						<label for="imgField">Immagine</label>
						<input type="file" name="immagine" id="imgField" accept=".jpg" value="<%=articolo.getCodice()%>.jpg">
					<%} %>
					<div class="errorString"></div>
					
				</div>
				<div id="fumettoForm">
							
					
					<label for="formatoField">Formato</label>
					<input type="text" name="formato" id="formatoField" disabled value="<%=fumetto.getFormato()%>">
					<div class="errorString"></div>
					<label for="genereField">Genere</label>
					<input type="text" name="genere" id="genereField" disabled value="<%=fumetto.getGenere()%>">
					<div class="errorString"></div>
					<label for="interniSelect">Interni</label>
					<select name="interni" id="interniSelect" disabled>
						<%if(fumetto.getInterni().equals("b/n")) {%>
							<option value="b/n" selected>b/n</option>
						<%} else { %>
							<option value="b/n">b/n</option>
						<%} if(fumetto.getInterni().equals("col.")) {%>
							<option value="col." selected>col.</option>
						<%} else { %>
							<option value="col.">col.</option>
						<%} %>
					</select>
					<div class="errorString"></div>
					<label for="numPagineField">Numero pagine</label>
					<input type="number" name="numPagine" id="numPagineField" disabled value="<%=fumetto.getNumeroPagine()%>" min="1">
					<div class="errorString"></div>
				</div>
				<div id="serieForm">
					<div id="inSerieCheckDiv">
						<%if(appartiene.getFumetto()!=0) { %>
							<input type="hidden" name="inSerie" value="true" id="inSerieCheck" onclick="serieEnable(this.checked)" disabled checked>
						<%} else { %>
							<input type="checkbox" name="inSerie" value="true" id="inSerieCheck" onclick="serieEnable(this.checked)" disabled>
							<label for="inSerieCheck">Appartiene ad una serie</label>
						<%} %>
						
					</div>
					<div class="errorString"></div>
					<label for="nomeSerieField">Nome serie</label>
					<%
						if(appartiene.getSerie()!=null && !appartiene.getSerie().trim().equals("")) {%>
							<input type="text" name="nomeSerie" id="nomeSerieField" value="<%=appartiene.getSerie()%>" readonly="readonly">	
					<%} else { %>
							<input type="text" name="nomeSerie" id="nomeSerieField" list="nomeSerieList" onblur="changePeriodicita()" onkeyup="ajaxCall('/Fumetteria/serie?action=getAllSerie&nome='+this.value,serieHandler)" value="">
					<%} %>
					<div class="errorString"></div>
					<datalist id="nomeSerieList">
				  	</datalist>
					<label for="periodicitaField">Periodicità</label>
					<input type="text" name="periodicita" id="periodicitaField" disabled value="<%=serieInfo.getPeriodicita()%>">
					<div class="errorString"></div>
					<label for="numeroField">Numero fumetto</label>
					<input type="number" name="numero" id="numeroField" disabled value="<%=appartiene.getNumero()%>">
					<div class="errorString"></div>
				</div>
				
				<input type="hidden" name="action" value="<%=action%>">
				<input type="hidden" name="isFumetto">
				<input type="hidden" name="id" value="<%=articolo.getCodice() %>">
				<input type="submit" id="addButton" name="Inserisci" value="Conferma">
			</form>
		</main>
		<%@include file="../footer.html" %>
	</body>
</html>