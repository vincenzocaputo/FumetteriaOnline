<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
   
 <%
 	Boolean statusLogin = (Boolean) request.getAttribute("statusLogin");
 %>
<!DOCTYPE html>
<html>
	<head>
		<link rel="shortcut icon" type="image/png" href="img/favicon.png"/>
		
		<link type="text/css" rel="stylesheet" href="style/main.css">
		<link type="text/css" rel="stylesheet" href="style/login.css">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="Pagina per effettuare l'autenticazione">
		<meta name="author" content="Vincenzo Caputo, Andrea Lorenzo">
		<script src="script/formValidator.js"></script>
		<title>Login</title>
	</head>
	<body>
		<div id="logo">
			<a href="index.jsp"><img src="img/logo.png" alt="logo"></a>
		</div>
	
		<section id="login">
			<%if(response.getStatus()==401) {%>
				<h1>Effettua la login</h1>
			<%} else {%>
				<h1>Login</h1>
			<%} %>
			<hr>
			<form action="utente" method="POST" onsubmit="return validateLogin()">
				<label for="email">Email</label> 
				<input type="text" name="email" placeholder="Email" id="email">
				<div class="error"></div>
				<label for="password">Password</label>
				<input type="password" name="password" placeholder="Password" id="password">
				<div class="error"></div>
				<br>
				<input type="hidden" name="action" value="login">
				<input type="submit" class="submit" value="Login">
			</form>
			
			<%
				if(statusLogin!=null) {
					if(!statusLogin) {
						%>
						<div id="loginError">
							Login fallito: dati non corretti. 
						</div>
						<%
					}
				}
			%>
			
			<%if(request.getAttribute("statusLogon")!=null && ((Integer)request.getAttribute("statusLogon"))==1) {%>
				<div id="logonOk">Registrazione effettuata con successo.</div>
			<%} %>
			<div id="logonLink">
				<a href="logon.jsp">Crea un nuovo account</a>
			</div>
		</section>
	</body>
</html>