<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8" import="java.util.*, it.fumetteria.search.RicercaBean, it.fumetteria.beans.AppartieneBean,
    it.fumetteria.beans.ArticoloBean, java.text.NumberFormat"%>
    
<jsp:include page="/articolo">
	<jsp:param name="action" value="readArticoliByCond"/>
</jsp:include>

<%
	NumberFormat nf = NumberFormat.getInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
	
	Collection<?> articoliNovita = (Collection<?>)request.getAttribute("articoliNovita");
	Collection<?> articoliPromozioni = (Collection<?>)request.getAttribute("articoliPromozioni");
	Collection<?> articoliVenduti = (Collection<?>)request.getAttribute("articoliVenduti");
%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="shortcut icon" type="image/png" href="img/favicon.png"/>
		<link type="text/css" rel="stylesheet" href="style/nav.css">
		<link type="text/css" rel="stylesheet" href="style/main.css">
		<link type="text/css" rel="stylesheet" href="style/index.css">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="Fumetteria e-commerce">
		<meta name="author" content="Vincenzo Caputo, Andrea Lorenzo">
		<title>Fumetteria</title>

	</head>
	<body>
		
		<%@include file="header.jsp" %>		
		<main>
			<section class="artGroup">
				<header><h2>Novità</h2></header>
				<hr>
				<div class="artGroupContent">
					<%
						if(articoliNovita!=null && articoliNovita.size()>0) {
							Iterator<?> it = articoliNovita.iterator();
							int i=0;
							while(it.hasNext()) {
								RicercaBean bean = (RicercaBean) it.next();
								ArticoloBean articolo = bean.getArticolo();
								AppartieneBean appartiene = bean.getSerie();
								String path="articolo";
								if(articolo.isFumetto()) path="fumetto";
								%>
									<div class="article">
										<div class="artImg">
											<a href="<%=path%>?action=readArticolo&id=<%=articolo.getCodice()%>">
												<img src="articolo?action=getImg&id=<%=articolo.getCodice() %>" alt="Immagine articolo"/>
											</a>
										</div>
										<hr>
										<span class="price"><%=nf.format(articolo.getPrezzoScontato())%>&euro;</span>
										<%if(articolo.getSconto()!=0) {%>
											<span class="oldPrice"><%=nf.format(articolo.getPrezzo()) %>&euro;</span>
										<%} %>
										
										<hr>
										<div class="title">
											<%=articolo.getNome()%>
											<%if(appartiene.getNumero()>0) {%>
												<%=appartiene.getNumero() %>
											<%} %>
										</div>												
									</div>
								<%
							}
						}
					%>
				</div>
			</section>
			<section class="artGroup">
				<header><h2>Promozioni</h2></header>
				<hr>
				<div class="artGroupContent">
				<%
					if(articoliPromozioni!=null && articoliPromozioni.size()>0) {
						Iterator<?> it = articoliPromozioni.iterator();
						while(it.hasNext()) {
							RicercaBean bean = (RicercaBean) it.next();
							ArticoloBean articolo = bean.getArticolo();
							AppartieneBean appartiene = bean.getSerie();
							String path="articolo";
							if(articolo.isFumetto()) path="fumetto";
							%>
								<div class="article">
									<div class="artImg">
										<a href="<%=path%>?action=readArticolo&id=<%=articolo.getCodice()%>">
											<img src="articolo?action=getImg&id=<%=articolo.getCodice() %>" alt="Immagine articolo"/>
										</a>
									</div>
									<hr>
									<span class="price"><%=nf.format(articolo.getPrezzoScontato())%>&euro;</span>
									<%if(articolo.getSconto()!=0) {%>
										<span class="oldPrice"><%=nf.format(articolo.getPrezzo()) %>&euro;</span>
									<%} %>
									<hr>
									<div class="title">
										<%=articolo.getNome()%>
										<%if(appartiene.getNumero()>0) {%>
											<%=appartiene.getNumero() %>
										<%} %>
									</div>												
								</div>
							<%
						}
					}
				%>
				</div>
			</section>
			<section class="artGroup">
				<header><h2>Più venduti</h2></header>
				<hr>
				<div class="artGroupContent">
				<%
					if(articoliVenduti!=null && articoliVenduti.size()>0) {
						Iterator<?> it = articoliVenduti.iterator();
						while(it.hasNext()) {
							RicercaBean bean = (RicercaBean) it.next();
							ArticoloBean articolo = bean.getArticolo();
							AppartieneBean appartiene = bean.getSerie();
							String path="articolo";
							if(articolo.isFumetto()) path="fumetto";
							%>
								<div class="article">
									<div class="artImg">
										<a href="<%=path%>?action=readArticolo&id=<%=articolo.getCodice()%>">
											<img src="articolo?action=getImg&id=<%=articolo.getCodice() %>" alt="Immagine articolo"/>
										</a>
									</div>
									<hr>
									<span class="price"><%=nf.format(articolo.getPrezzoScontato())%>&euro;</span>
									<%if(articolo.getSconto()!=0) {%>
										<span class="oldPrice"><%=nf.format(articolo.getPrezzo()) %>&euro;</span>
									<%} %>
									<hr>
									<div class="title">
										<%=articolo.getNome()%>
										<%if(appartiene.getNumero()>0) {%>
											<%=appartiene.getNumero() %>
										<%} %>
									</div>												
								</div>
							<%
						}
					}
				%>
				</div>
			</section>
		</main>
		
		<%@ include file="footer.html" %>
	</body>
</html>