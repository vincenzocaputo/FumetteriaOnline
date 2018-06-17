<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8" import="it.fumetteria.beans.OrdineBean, java.util.*, java.text.NumberFormat"%>
    
<%
	NumberFormat nf = NumberFormat.getInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
	Collection<?> ordini = (Collection<?>) request.getAttribute("ordini");
%>

<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/png" href="/Fumetteria/img/favicon.png"/>
		<link type="text/css" rel="stylesheet" href="/Fumetteria/style/nav.css">
		<link type="text/css" rel="stylesheet" href="/Fumetteria/style/main.css">
		<link type="text/css" rel="stylesheet" href="/Fumetteria/style/myorders.css">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="Fumetteria e-commerce">
		<meta name="author" content="Vincenzo Caputo, Andrea Lorenzo">
<title>Ordini</title>
</head>

<body>
	<%@include file="../header.jsp" %>
	
	<main>
			<h1>Lista ordini</h1>
			<%if(ordini==null){%>
			 <p>Nessun ordine presente</p>
			 <%}else{ %>
			<div id="allOrdersTable">
				<table>
					<thead>
						<tr>
							<th>ID</th>
							<th>Data Emissione</th>
							<th>Totale</th>
							<th>Stato</th>
							<th> </th>
						</tr>		
					</thead>
					
					<%
						Iterator<?> it = ordini.iterator();
						while(it.hasNext()) {
							OrdineBean bean = (OrdineBean) it.next();
					%>
					
							<tr>	
								<td><%=bean.getNumero() %></td>
								<td><%=bean.getDataEmissione() %></td>
								<td><%=nf.format(bean.getTotale()) %>&euro;</td>
								<td>
									<%if(bean.getDataConsegna()==null) {%>
										In elaborazione
									<%} else { %>
										Spedito
									<%} %>
								</td>
								<td><a href="ordine?action=readOrdineDetails&id=<%=bean.getNumero() %>">Dettagli</a></td>
							</tr>
					<%
						}
				       }	
					%>
				</table>
			</div>
		</main>
	
	
	<%@include file="../footer.html"%>

</body>
</html>