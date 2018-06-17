<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8" 
    import="it.fumetteria.cart.*, it.fumetteria.beans.UtenteBean, it.fumetteria.beans.ArticoloBean, java.text.NumberFormat, 
    java.util.*,it.fumetteria.beans.SiRiferisceBean, it.fumetteria.beans.OrdineBean" %>

<%
	NumberFormat nf = NumberFormat.getInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
	
	Cart cart = (Cart) session.getAttribute("cart");
	UtenteBean utente = (UtenteBean) request.getAttribute("userInfo");
	OrdineBean ordine = (OrdineBean) request.getAttribute("ordine");
	Collection<?> siriferisce = (Collection<?>) request.getAttribute("siriferisce");
	boolean doOrdine = true;
	double prezzo = 0;
%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="shortcut icon" type="image/png" href="img/favicon.png"/>
		<link type="text/css" rel="stylesheet" href="style/nav.css">
		<link type="text/css" rel="stylesheet" href="style/main.css">
		<link type="text/css" rel="stylesheet" href="style/order.css">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="Contenuto carrello">
		<meta name="author" content="Vincenzo Caputo, Andrea Lorenzo">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Dettagli ordine</title>
		
		<script>
			function validateData()
			{
				try {
					var data = document.getElementById("dataOrdine");
					var today = new Date();
					var consegna = new Date(data.value);
					if(data.value=="") {
						data.style.borderColor = "red";
						document.getElementById("dateError").innerHTML = "Campo obbligatorio";
						return false;
					} else if(today.getTime()>=consegna.getTime()) {
						data.style.borderColor = "red";
						document.getElementById("dateError").innerHTML = "Data non valida.";
						return false;
					} else {
						data.style.borderColor = "white";
						document.getElementById("dateError").innerHTML = "";
						return true;
					}
				 
				}catch(e) {
					console.log(e);
				}
			} 
		</script>
	</head>
	<body>
		<%@include file="header.jsp" %>
		<main>
		<%if(utente==null) {%>
			<h2>Risorsa non disponibile</h2>
		<%} else { %>
			<!-- Titolo pagina -->
				<% if(ordine==null)  {
					%> 
					<h1>Conferma ordine</h1>
				<%} else { %>
					<h1>Dettagli ordine</h1>
					<div id="ordineID">Ordine ID: <%=ordine.getNumero() %></div>
				<%} %>
			<!-- Tabella resoconto ordine -->
				<div id="orderDetailsTable">
					<table>
						<thead>
							<tr>
								<th>Articolo</th>
								<th>Importo</th>
								<th>Quantità</th>
								<%if(ordine!=null) {%>
									<th></th>
								<%} %>
							</tr>
							
						</thead>
						<%if(ordine==null) { %>
							<%
								prezzo=0;
								if(cart!=null) {
									for(ArticoloInCarrello art:cart.getArticoli()) {
										ArticoloBean articolo = art.getArticolo();
								%>
										<tr>
											<td><%=articolo.getNome() %></td>
											<td><%=nf.format(articolo.getPrezzoScontato()*art.getNumArt())%>&euro;</td>
											<td><%=art.getNumArt() %></td>
										</tr>
								<%
										if(art.getArticolo().getGiacenza()<=0) {
											doOrdine = false;
										}
										prezzo = prezzo + art.getArticolo().getPrezzoScontato()*art.getNumArt();
									}
								}
							%>
						<%} else { %>
							<%
								Iterator<?> it = siriferisce.iterator();
								while(it.hasNext()) {
									SiRiferisceBean bean = (SiRiferisceBean)it.next();
							%>
									<tr>
										<td><%=bean.getArticolo().getNome() %></td>
										<td><%=nf.format(bean.getCosto())%>&euro;</td>
										<td><%=bean.getQuantita() %></td>
										<td class="dettagli">
										    <%String servlet; 
										    if(bean.getArticolo().isFumetto())
										    	servlet="fumetto";
										      else
										    	servlet="articolo";%>
											<a href="<%=servlet%>?action=readArticolo&id=<%=bean.getArticolo().getCodice()%>">Visualizza articolo</a>
										</td>
									</tr>
							<% }
						} %>
						
					</table>
				</div>
				<!-- Totale ordine -->
				<div id="total">
					<%if(ordine==null) {%>
						Totale: <%=nf.format(prezzo) %>&euro;
					<%} else { %>
						Totale: <%=nf.format(ordine.getTotale()) %>&euro;
					<%} %>
				</div>
				<hr>
				<!-- Dati indirizzo di spedizione -->
				<div id="ordineAddr">
					<%if(ordine==null || ordine.getDataConsegna()==null) {%>
						<h4>Da spedire a:</h4>
					<%} else { %>
						<h4>Spedito a:</h4>
					<%} %>
					<p>
						<%if(utente!=null)  {%>
							<%=utente.getCognome()%> <%=utente.getNome() %><br>
							<%=utente.getVia() %>,<%=utente.getCivico() %><br>
							<%=utente.getCitta() %> (<%=utente.getProvincia() %>)<br>
							CAP: <%=utente.getCap() %>
						<%} %>
					</p>
				</div>
				<!-- Bottone conferma ordine -->
				<%if(ordine==null) {%>
					<%if(doOrdine) {%>
						<a href="ordine?action=addOrdine" id="order">Conferma</a>
					<%} else {%>
						<h4>Impossibile proseguire, l'articolo non è più disponibile.</h4>
					<%} %>
				<%} else {%>
				<!-- Informazioni data emissione, data consegna, stato dell'ordine -->
					<div id="ordineDate">
						<h4>Data Emissione: </h4>
						<%=ordine.getDataEmissione() %>
						<!-- Form per confermare l'ordine (lato amministratore) -->
						<%if((request.getSession().getAttribute("admin")!=null && ((boolean)(request.getSession().getAttribute("admin")))==true)
							  && ordine.getDataConsegna()==null){ %>
							<form action="ordine" method="POST" name="modificaOrdineForm" onsubmit="return validateData()">
								<label for="dataOrdine">Data consegna:</label>
								<input type="date" name="dataOrdine" id="dataOrdine"><br>
								<span id="dateError"></span>
								<input type="hidden" name="action" value="confermaOrdine">
								<input type="hidden" name="id" value="<%=ordine.getNumero() %>">
								<input type="submit" name="modificaOrdineButton" id="modificaOrdineButton" value="Modifica">
							</form>
						<%}else{ %>	
							<h4>Stato:</h4>
							<%if(ordine.getDataConsegna()==null) { %>
								In elaborazione
							<%} else {%>
								Spedito.
								<h4>Data consegna stimata: </h4>
								<%=ordine.getDataConsegna() %>
							<%} %>
						<%} %>
					</div>
				<%} %>
			<%} %>
		</main>
		<%@include file="footer.html" %>
	</body>
</html>