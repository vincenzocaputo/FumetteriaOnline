function preferiti(listJSON) {
	var json = JSON.parse(listJSON);
	
	var status = json.status;
	var popupMsg = document.getElementById("popupMsg");
	var elAdd = document.getElementById("addPreferito");
	var elDel = document.getElementById("delPreferito");
	if(status == 1) { //è stato aggiunto ai preferiti
		elAdd.style.display = "none";
		elDel.style.display = "block";
		showPopupMsg(false, "Aggiunto ai preferiti",5000);
	} else {
		elAdd.style.display = "block";
		elDel.style.display = "none";
		showPopupMsg(false, "Rimosso dai preferiti",5000);
	}
}

function addToCartHandler(listJSON) {
	var json = JSON.parse(listJSON);
	var status = json.status[0];
	var numArt = json.numArt[0];
	if(status) {
		document.getElementById("numArt").innerHTML = numArt;
		document.getElementById("numArtInCart").innerHTML = numArt;
		showPopupMsg(false, "Aggiunto al carrello",5000);
	} else {
		showPopupMsg(true, "Quantità aggiornata",5000);
	}
}

function generateURL() {
	var servlet = "articolo";
	var par1 = document.addToCartForm.numArt.value;
	var par2 = document.addToCartForm.id.value;
	var par3 = document.addToCartForm.action.value;
	
	var url = servlet+"?action="+par3+"&id="+par2+"&numArt="+par1;
	return url;
	
}