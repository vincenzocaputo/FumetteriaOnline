<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
	<head></head>
		<link rel="shortcut icon" type="image/png" href="img/favicon.png"/>
		<link type="text/css" rel="stylesheet" href="/Fumetteria/style/nav.css">
		<link type="text/css" rel="stylesheet" href="/Fumetteria/style/main.css">
		<link type="text/css" rel="stylesheet" href="/Fumetteria/style/index.css">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="Pagina di errore">
		<meta name="author" content="Vincenzo Caputo, Andrea Lorenzo">
		<title>Errore</title>
	</head>
	<body>
		
		<%@include file="header.jsp" %>
		<main>
			
				<h1>Error <%=response.getStatus() %></h1>
				<%if(request.getAttribute("javax.servlet.error.message")!=null) %>
					<h2><%=request.getAttribute("javax.servlet.error.message")%></h2>
		</main>
		<%@ include file="footer.html" %>
	</body>
</html>