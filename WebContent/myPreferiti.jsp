<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"  import="it.fumetteria.beans.SegueBean, java.util.*"%>

<%
	Collection<?> serie = (Collection<?>) request.getAttribute("serie");
%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="shortcut icon" type="image/png" href="img/favicon.png"/>
		<link type="text/css" rel="stylesheet" href="style/nav.css">
		<link type="text/css" rel="stylesheet" href="style/main.css">
		<link type="text/css" rel="stylesheet" href="style/myorders.css">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="Dettagli articolo">
		<meta name="author" content="Vincenzo Caputo, Andrea Lorenzo">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Scheda articolo</title>
	</head>
	<body>
		<%@include file="header.jsp" %>
		<main>
			<h1>Le mie serie preferite</h1>
			<%if(serie!=null && serie.size()>0) {%>
				<div id="myPreferitiTable">
					<table>
						<thead>
							<tr>
								<th>Nome</th>
								<th>Periodicità</th>
								<th>Elenco articoli</th>
								<th>Elimina</th>
							</tr>
						</thead>
						<%
							Iterator<?> it = serie.iterator();
							while(it.hasNext()) {
								SegueBean bean = (SegueBean) it.next();
						%>
								<tr>
									<td><%=bean.getSerie().getNome() %></td>
									<td><%=bean.getSerie().getPeriodicita() %></td>
									<td><a href="articolo?action=readArticoliPref&serie=<%=bean.getSerie().getNome()%>">Articoli</a></td>
									<td><a href="preferiti?action=delPreferiti&serie=<%=bean.getSerie().getNome()%>"><img src="img/delete-icon.png" alt="delete"></a></td>
								</tr>
						<%
							}
						%>
					</table>
				</div>
			<%} else { 
					if(session.getAttribute("email")!=null && serie!=null) {
					%>
						<p>Nessuna serie tra i preferiti</p>
					<%} else {%>
						<p>Risorsa non disponibile.</p>
					<%} 
			}%>
		</main>
		<%@include file="footer.html"%>
	</body>
</html>