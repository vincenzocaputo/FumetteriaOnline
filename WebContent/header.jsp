<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8" 
    import="it.fumetteria.cart.*, it.fumetteria.search.ArticoloRicercaBean" %>

<%
	Cart cartHeader = (Cart) session.getAttribute("cart");

	ArticoloRicercaBean datoCercato = (ArticoloRicercaBean) request.getAttribute("datiRicerca");
%>

<script src="/Fumetteria/script/header.js"></script>

<header>
	<div id="popupMsg"></div>
	<nav>
		<div id="menuCell">
			<button id="sandwichMenu" onclick="showSandwichMenu()"><img src="/Fumetteria/img/menu-icon.png" alt="menu"></button>
			<ul id="listMenu">				
				<%
					String email = (String) session.getAttribute("email");
					if(email==null ) {
				%>
					<li class="accountSandwich"><a href="/Fumetteria/login.jsp">Login</a></li>
				<%} else { %>
					<%
						boolean admin = (Boolean)session.getAttribute("admin");
						if(admin) {
					%>
							<li class="accountSandwich"><a href="/Fumetteria/admin/addArticolo.jsp">Nuovo articolo</a></li>
							<li class="accountSandwich"><a href="/Fumetteria/ordine?action=readAllOrdini">Ordini</a></li>
							<li class="accountSandwich"><a href="/Fumetteria/utente?action=logout">Logout</a></li>
					<%} else { %>
							<li class="accountSandwich"><a href="/Fumetteria/ordine?action=readOrdini">I miei ordini</a></li>
							<li class="accountSandwich"><a href="/Fumetteria/preferiti?action=readPreferiti">Preferiti</a></li>
							<li class="accountSandwich"><a href="/Fumetteria/utente?action=logout">Logout</a></li>
							
					<%} %>
					
				<%} %>
				<li class="accountSandwich" id="cartSandwich">
					<a href="/Fumetteria/cart.jsp">Carrello</a>
					<% if(cartHeader!=null) {%>
						<div id="numArtInCart"><%=cartHeader.getArticoli().size() %></div>
					<%} else {%>
						<div id="numArtInCart">0</div>
					<%} %>
				</li>
				<li><a href="/Fumetteria/articolo?action=readAllArticoli&categoria=Comics">Comics</a></li>
				<li><a href="/Fumetteria/articolo?action=readAllArticoli&categoria=Manga">Manga</a></li>
				<li><a href="/Fumetteria/articolo?action=readAllArticoli&categoria=Gadgets">Gadgets</a></li>
				<li><a href="/Fumetteria/articolo?action=readAllArticoli&categoria=Accessori">Accessori</a></li>
			</ul>
		</div>
		<div id="searchCell">
			<form action="articolo" method="POST" name="searchForm">
				<%if(datoCercato!=null && datoCercato.getNome()!=null) {%>
					<input type="text" placeholder="cerca" id="searchField" name="nome" value="<%=datoCercato.getNome()%>">
				<%} else { %>
					<input type="text" placeholder="cerca" id="searchField" name="nome">
				<%} %>
				<input type="hidden" name="action" value="searchArticoli">
				<input type="hidden" name="ordinamento" value="codice">
				<input type="hidden" name="categoria" value="seleziona">
				<input type="hidden" name="interni" value="seleziona">
			</form>
		</div>
		<div id="userCell">
			<div id="userCellTable">
				<div id="accountCell">
				<%
					email = (String) session.getAttribute("email");
					if(email==null ) {
				%>
						<div class="accountButton"><a href="/Fumetteria/login.jsp">Login</a></div>
				<%	} else { %>
						<div class="accountButton" onclick="showMenu()" onmouseleave="hideMenu()">
							<div id="userMenuCell">
								<img src="/Fumetteria/img/account.png" alt="account">
								<span id="userMenuLink">Menu &#9660;</span>
								<div id="userMenu">
									<ul>
										<%
										boolean admin = (Boolean)session.getAttribute("admin");
										if(!admin) {
										%>
											<li><a href="ordine?action=readOrdini">I miei ordini</a></li>
											<li><a href="preferiti?action=readPreferiti">Preferiti</a></li>
										<%} else { %>
											<li><a href="/Fumetteria/admin/addArticolo.jsp">Nuovo articolo</a></li>
											<li><a href="/Fumetteria/ordine?action=readAllOrdini">Ordini</a></li>
										<%} %>
										<li><a href="/Fumetteria/utente?action=logout">Logout</a></li>
									</ul>
								</div>
							</div>
						</div>							
				<% }%>
				</div>
				<div id="cart">
					<a href="/Fumetteria/cart.jsp"><img src="/Fumetteria/img/cart.png" id="cartImg" alt="cart"/></a>
					<% if(cartHeader!=null) {%>
						<span id="numArt"><%=cartHeader.getArticoli().size() %></span>
					<%} else {%>
						<span id="numArt">0</span>
					<%} %>
				</div>
			</div>
		</div>
	</nav>
	<div id="logo">
		<a href="/Fumetteria/index.jsp"><img src="/Fumetteria/img/logo.png" alt="logo"></a>
	</div>
</header>
