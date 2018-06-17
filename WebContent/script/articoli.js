function addToCartHandler(listJSON, HTTPstatus) {
	var json = JSON.parse(listJSON);
	var status = json.status[0];
	var numArt = json.numArt[0];
	
	if(status) {
		document.getElementById("numArt").innerHTML = numArt;
		document.getElementById("numArtInCart").innerHTML = numArt;
		
		showPopupMsg(false, "Articolo aggiunto al carrello",5000);

	} else {
		showPopupMsg(true, "L'articolo è già nel carrello",5000);
	}
}
function showRicercaAvanzata() {
	var aside = document.getElementsByTagName("aside")[0];
	if(aside.style.display=="block"){
		aside.style.display = "none";
	} else {
		aside.style.display = "block";
	}
}

function validateSearch() {
	var form = document.ricercaAvanzata;
	form.genere.style.borderColor="initial";
	form.prezzoMin.style.borderColor="initial";
	form.prezzoMax.style.borderColor="initial";
	form.prezzoMin.nextElementSibling.innerHTML = "";
	form.prezzoMax.nextElementSibling.innerHTML = "";
	form.genere.nextElementSibling.innerHTML = "";
	
	if(form.prezzoMin.value<0) {
		form.prezzoMin.style.borderColor="red";
		form.prezzoMin.nextElementSibling.innerHTML = "Deve essere un valore positivo!";
		return false;
	} 
	if(form.prezzoMax.value<0) {
		form.prezzoMax.style.borderColor="red";
		form.prezzoMax.nextElementSibling.innerHTML = "Deve essere un valore positivo!";
		return false;
	} 
	
	if(form.prezzoMax.value!="" && parseFloat(form.prezzoMax.value)<parseFloat(form.prezzoMin.value)) {
		form.prezzoMin.style.borderColor="red";
		form.prezzoMax.style.borderColor="red";
		form.prezzoMax.nextElementSibling.innerHTML = "Il prezzo max deve essere maggiore del prezzo min!";
		return false;
	} 
	if(form.genere.value.length>50) {
		form.genere.style.borderColor = "red";
		form.genere.nextElementSibling.innerHTML = "Non puoi superare i 50 caratteri!";
		return false;
	}
	document.ricercaAvanzata.nome.value = document.searchForm.nome.value;
	return true;

}