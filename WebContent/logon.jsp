<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
   
 <%
 	Integer statusLogon = (Integer) request.getAttribute("statusLogon");
 %>
<!DOCTYPE html>
<html>
	<head>
		<link rel="shortcut icon" type="image/png" href="img/favicon.png"/>
	   	
		<link type="text/css" rel="stylesheet" href="style/main.css">
		<link type="text/css" rel="stylesheet" href="style/login.css">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="Pagina per effettuare la registrazione">
		<meta name="author" content="Vincenzo Caputo, Andrea Lorenzo">
		<script src="script/formValidator.js"></script>
		<title>Logon</title>
	</head>
	<body>
		<header>
			<div id="logo">
				<a href="index.jsp"><img src="img/logo.png" alt="logo"></a>
			</div>
		</header>
		
		<section id="logon">
			<h1>Registrazione</h1>
			<hr>
			<form action="utente" method="POST" onsubmit="return validateLogon()">
				<label for="email">Email</label> 
				<input type="email" name="email" placeholder="Email" id="email" maxlength="255">
				<div class="error">
				<%
					if(statusLogon != null && statusLogon==0) {
						%>
							Email già esistente!
						<%
					} else if(statusLogon != null && statusLogon==-1) {
						%>
							Dati mancanti o non corretti.
						<%
					}
				%>
				</div>
				<label for="cognome">Cognome</label> 
				<input type="text" name="cognome" placeholder="Cognome" id="cognome" maxlength="25">
				<div class="error"></div>
				<label for="nome">Nome</label> 
				<input type="text" name="nome" placeholder="Nome" id="nome" maxlength="25">
				<div class="error"></div>
				<table id="cittaTable">
				     <tr>
						<td class="cittaCell"><label for="citta" id="cittalabel">Città</label></td> 
						<td class="provinciaCell"><label for="provincia" id="provincialabel">Provincia</label></td>
					</tr>
					<tr> 
						<td class="cittaCell"><input type="text" name="citta" placeholder="Città" id="citta" maxlength="40"></td>
						<td class="provinciaCell"><input type="text" name="provincia" placeholder="Provincia" id="provincia" maxlength="2"></td>
					</tr>	
				</table>
				<div class="error"></div>
				<table id="indirizzo">
					<tr>
						<td class="viaCell"><label for="via" id="vialabel">Via</label></td> 
						<td class="civCell"><label for="civico" id="civicolabel">Civico</label></td>
						<td class="capCell"><label for="cap" id="caplabel">CAP</label> </td>
					</tr>
					<tr> 
						<td class="viaCell"><input type="text" name="via" placeholder="Via" id="via" maxlength="40"></td>
						<td class="civCell"><input type="text" name="civico" placeholder="Civico" id="civico" maxlength="3"></td>
						<td class="capCell"><input type="text" name="cap" placeholder="CAP" id="cap" maxlength="5"></td>
					</tr>	
				</table>
				<div class="error"></div>
				
				
				<label for="password">Password</label> 
				<input type="password" name="password" placeholder="Password" id="password" maxlength="255">
				<div class="error"></div>
				<input type="hidden" name="action" value="logon">
				<br>
				<input type="submit" class="submit" value="Registrati">
				<input type="reset" class="reset" value="Reset">
			</form>
		</section>
	</body>
</html>