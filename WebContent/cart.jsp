<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8" 
    import="it.fumetteria.cart.*, it.fumetteria.beans.ArticoloBean, java.text.NumberFormat" %>

<%
	NumberFormat nf = NumberFormat.getInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
	Cart cart = (Cart) session.getAttribute("cart");
	boolean doOrder = true;
%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="shortcut icon" type="image/png" href="img/favicon.png"/>
		<link type="text/css" rel="stylesheet" href="style/nav.css">
		<link type="text/css" rel="stylesheet" href="style/main.css">
		<link type="text/css" rel="stylesheet" href="style/cart.css">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="Contenuto carrello">
		<meta name="author" content="Vincenzo Caputo, Andrea Lorenzo">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Carrello</title>
		
		<script>
			function validateNumArt() {
				if(document.numArtForm.numArt<=0) {
					return false;
				} else {
					return true;
				}
			}
		</script>
	</head>
	<body>
		<%@include file="header.jsp" %>
		<main>
			<section id="cartSection">
				<h2>Il mio carrello</h2>
				<%if(request.getAttribute("aggiornato")!=null) {
					if((boolean)request.getAttribute("aggiornato")) { %>
						<p>Modifica effettuata</p>
					<%} else { %>
						<p>La quantità selezionata non è ammessa</p>
					<%}
				}%>
				<% 
					if(cart!=null) {
				%>
					<div id="tableCart">
						<table>
							<thead>
								<tr>
									<th colspan="2">Articolo</th>
									<th>Prezzo</th>
									<th>Quantità</th>
									<th>Importo</th>
									<th>Elimina</th>
								</tr>
								
							</thead>
							<%
								for(ArticoloInCarrello art:cart.getArticoli()) {
									ArticoloBean articolo = art.getArticolo();
							%>
									<tr>
										<td class="artImage"><img src="articolo?action=getImg&id=<%=articolo.getCodice()%>" alt="Immagine articolo"></td>
										<td><%=articolo.getNome() %></td>
										<td><%=nf.format(articolo.getPrezzoScontato())%>&euro;</td>
										<td>
											<form action="articolo" method="get" onsubmit="return validateNumArt()" name="numArtForm">
											    <input type="hidden" name="action" value="updateArticoloInCart"> 
											    <input type="hidden" name="id" value="<%=articolo.getCodice() %>">
												<input class="inputNumArt" type="number" name="numArt" value="<%=art.getNumArt() %>" min="1" max="<%=articolo.getGiacenza()%>">
												
												<input class="submitNumArt" type="submit" value=" ">
											</form>
											<span class="errorGiacenza">
												<%
													if(articolo.getGiacenza()<=0) {
														doOrder = false;
												%>
														L'articolo non è più disponibile!
												<%} %>
											</span>
										</td>
										<td><%=nf.format(articolo.getPrezzoScontato()*art.getNumArt())%>&euro;</td>
										<td><a href="articolo?action=delArticoloInCart&id=<%=articolo.getCodice()%>"><img src="img/delete-icon.png" alt="delete"></a></td>
									</tr>
							<%	} %>
						</table>
					</div>
					<div id="total">
						Totale: <%=nf.format(cart.getTotale()) %>&euro;
					</div>
					<%if(doOrder) {%>
						<a href="ordine?action=readOrdineDetails" id="order">Procedi all'ordine</a>
					<%} else { %>
						<p id="order">Impossibile procedere</p>
					<%} %>
				<%
					} else {
				%>
					<p>Carrello vuoto</p>
				<%} %>
			</section>
		</main>
		<%@include file="footer.html" %>
	</body>
</html>
   