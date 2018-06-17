<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8" 
    import="it.fumetteria.beans.ArticoloBean, it.fumetteria.beans.FumettoBean, it.fumetteria.beans.AppartieneBean, java.text.NumberFormat,
    	it.fumetteria.cart.*" %>

<%
	NumberFormat nf = NumberFormat.getInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
	
	ArticoloBean articolo = (ArticoloBean) request.getAttribute("articolo");
	AppartieneBean serie = (AppartieneBean) request.getAttribute("serie");
%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="shortcut icon" type="image/png" href="img/favicon.png"/>
		<link type="text/css" rel="stylesheet" href="style/nav.css">
		<link type="text/css" rel="stylesheet" href="style/main.css">
		<link type="text/css" rel="stylesheet" href="style/articolo.css">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="Dettagli articolo">
		<meta name="author" content="Vincenzo Caputo, Andrea Lorenzo">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Scheda articolo</title>
		<script src="/Fumetteria/script/ajax.js"></script>
		<script src="/Fumetteria/script/articoloView.js" charset="UTF-8"></script>
		<script>
			function confirmDialog() { 
				return confirm("Vuoi eliminare l'articolo?"); 
			}
		</script>
	</head>
	<body>
		<%@include file="header.jsp" %>
		<main>
			
			<%
				if(articolo!=null && articolo.getCodice()>0) {
			%>
				<h1>
					<%=articolo.getNome()%> 
					<%if(serie!=null && serie.getNumero()!=0) { %>
						<%=serie.getNumero() %>
					<%} %>
				</h1>
				<div id="artImg">
					<img src="articolo?action=getImg&id=<%=articolo.getCodice()%>" alt="immagine articolo">
				</div>
				<div id="artDetails">
					<div id="artTable">
						<div id="priceCell">
							<%
								if(articolo.getSconto()!=0) {
							%>
									<div id="oldPrice"><%=nf.format(articolo.getPrezzo()) %>&euro;</div>
									<div id="price"><%=nf.format(articolo.getPrezzoScontato())%>&euro;</div>
							<%
								} else {
							%>
									<div id="price"><%=nf.format(articolo.getPrezzo()) %>&euro;</div>
							<%
								}
							%>
						</div>
						<div id="cartCell">
							<%if(session.getAttribute("admin")!=null && ((boolean)(session.getAttribute("admin"))==true)) {
									String path = "";
									if(articolo.isFumetto()) {
										path = "fumetto?action=readArticolo&id="+articolo.getCodice()+"&update=true";
									} else {
										path = "articolo?action=readArticolo&id="+articolo.getCodice()+"&update=true";
									}
							%>
							   <a href="<%=path%>" id="modificaButton">Modifica</a>
							   <a href="articolo?action=delArticolo&id=<%=articolo.getCodice() %>" id="eliminaButton" onclick="return confirmDialog()">Elimina</a>
							<%} else { %>
								<% if(articolo.getGiacenza()>0) { %>
									<div id="stock">
										<span class="title">Disponibilità: </span>
										<span class="value"><%=articolo.getGiacenza()%></span>
									</div>
									<form  onsubmit="return ajaxCall(generateURL(),addToCartHandler)" name="addToCartForm">
										<div id="quantity">
											<label for="number">Qtà</label>
											<input type="number" name="numArt" min="1" max="<%=articolo.getGiacenza()%>" id="number" value="1">
										</div>	
										<input type="hidden" name="id" value="<%=articolo.getCodice() %>">
										<input type="hidden" name="action" value="addArticoloToCart">
										<input type="submit" value=" " id="addToCart">
									</form>
								<% } else  {%>
									<div id="stock">Non disponibile</div>
								<%} 
							}%>
						</div>
						<div id="serieCell">
						
							<% if(serie!=null && serie.getNumero()!=0) { %>
							
								<div id="serieName">
									<span class="title">Serie: </span>
									<span class="value"><%=serie.getSerie() %></span>
								</div>
								<div id="serieNum">
									<span class="title">Numero: </span>
									<span class="value"><%=serie.getNumero() %></span></div>
								<%if(session.getAttribute("admin")==null || ((boolean)(session.getAttribute("admin"))==false)) {%>
									<div id="addPref">
									<% 
										if(request.getAttribute("isPreferito")!=null) {	
											boolean isPreferito = (boolean) request.getAttribute("isPreferito");
											if(isPreferito) { %>
												<a onclick="ajaxCall('preferiti?action=addPreferiti&serie=<%=serie.getSerie()%>',preferiti)" id="addPreferito" style="display: none;"><img src="img/whiteStar.png" alt="whiteStar">Aggiungi ai preferiti</a>
												<a onclick="ajaxCall('preferiti?action=delPreferitiAJAX&serie=<%=serie.getSerie()%>',preferiti)" id="delPreferito" style="display: block;"><img src="img/yellowStar.png" alt="yellowStar">Rimuovi dai preferiti</a>
									<%		} else {%>
												<a onclick="ajaxCall('preferiti?action=addPreferiti&serie=<%=serie.getSerie()%>',preferiti)" id="addPreferito" style="display: block;"><img src="img/whiteStar.png" alt="whiteStar">Aggiungi ai preferiti</a>
												<a onclick="ajaxCall('preferiti?action=delPreferitiAJAX&serie=<%=serie.getSerie()%>',preferiti)" id="delPreferito" style="display: none;"><img src="img/yellowStar.png" alt="yellowStar">Rimuovi dai preferiti</a>
									<%		}
										}
									%>
									
									</div>
								<% } 
							}%>
						</div>
					</div>
					
					
					<%
						if(articolo.isFumetto()) {
							FumettoBean fumetto = ((FumettoBean) articolo); 
					%>
							<div id="genere">
								<span class="title">Genere</span>
								<span class="value"><%=fumetto.getGenere() %></span>
							</div>
							<div id="interni">
								<span class="title">Interni</span>
								<span class="value"><%=fumetto.getInterni() %></span>
							</div>
							<div id="numPag">
								<span class="title">Numero Pagine</span>
								<span class="value"><%=fumetto.getNumeroPagine() %></span>
							</div>
							<div id="formato">
								<span class="title">Formato</span>
								<span class="value"><%=fumetto.getFormato() %></span>
							</div>
							
					<%
						}
					%>
				</div>
			<%
				} else {
			%>
					<h2>Articolo non presente</h2>
			<%
				}
			%>
			
			<div id="description">
				<hr>
				<span class="title">Descrizione</span><br>
				<span class="value">
				<%=articolo.getDescrizione() %></span>
			</div>
		</main>
		<%@include file="footer.html" %>
	</body>
</html>