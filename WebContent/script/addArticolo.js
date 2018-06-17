

function enableFumetto(sel) {
	if(sel.value=="Comics" || sel.value=="Manga") {
		fumettoEnable(true);
		document.addArticoloForm.isFumetto.value=true;
	} else {
		fumettoEnable(false);
		document.addArticoloForm.isFumetto.value=false;
	}
}

function fumettoEnable(enable) {
	
	var v = document.getElementById("fumettoForm").childNodes;
	for(var i=0; i<v.length; i++) {
		if(enable) {
			v[i].disabled = false;
		} else if(!enable) {
			v[i].disabled = true;
		}
	}
	
	if(enable) {
		document.getElementById("inSerieCheck").disabled=false;
	} else {
		if(document.getElementById("inSerieCheck").checked==true) {
			serieEnable(false);
			document.getElementById("inSerieCheck").checked=false;
		}
		document.getElementById("inSerieCheck").disabled=true;
	}
}


function serieEnable(enable) {
	var v = document.getElementById("serieForm").childNodes;
	for(var i=0; i<v.length; i++) {
		if(enable) {
			v[i].disabled = false;
		} else if(!enable) {
			v[i].disabled = true;
		}
	}
}

function changePeriodicita() {
	var val = document.addArticoloForm.nomeSerieField.value;
    var opts = document.getElementById("nomeSerieList").childNodes;
    for (var i = 0; i < opts.length; i++) {
		if (opts[i].value == val) {
		  	return;
		}
    }
    document.addArticoloForm.periodicitaField.value = "";
	document.addArticoloForm.periodicitaField.readOnly = false;
    
}
function validateForm() {
	var v = [validateNome(), validateCategoria(), validateGiacenza(), validatePrezzo(), validateSconto(), validateDescrizione(), validateImg(), validateFormato(), validateGenere(), validateInterni(), validateNumPagine(), validateNomeSerie(), validatePeriodicita(), validateNumFumetto()];

	for(var i=0; i<v.length; i++) {
		if(v[i] == false) {
			return false;
		}
	}
	
	return true;
}

function validateNome() {
	var nome = document.getElementById("nomeField");
	
	if(nome.value.trim() == "") {	 
		nome.style.borderColor = "red";
		nome.nextElementSibling.innerHTML = "Campo obbligatorio!";
		return false;
	} else if(nome.value.length>=50) {
		nome.style.borderColor = "red";
		nome.nextElementSibling.innerHTML = "Il nome deve contenere al massimo 50 caratteri!";
		return false;
	}else {
		nome.nextElementSibling.innerHTML = "";
		nome.style.borderColor = "white";
		return true;
	}
}

function validateCategoria(){
	var categoria = document.getElementById("categoriaSelect");
	
	if(categoria.value == "Comics" || categoria.value == "Manga" || categoria.value == "Accessori" || categoria.value == "Gadgets")
	 {
	    categoria.nextElementSibling.innerHTML = "";
		categoria.style.borderColor = "initial";
		return true;	
	 } else{
		categoria.style.borderColor = "red";
		categoria.nextElementSibling.innerHTML = "Valore non consentito!";
		return false; 
	 }
}

function validateGiacenza(){
	var giacenza = document.getElementById("giacenzaField");
	
	if(giacenza.value < 0 || isNaN(giacenza.value)==true || giacenza.value.trim()=="" )
	{
		giacenza.nextElementSibling.innerHTML = "Valore non valido!";
		giacenza.style.borderColor = "red";
		return false;
	} else{
		giacenza.nextElementSibling.innerHTML = "";
		giacenza.style.borderColor = "white";
		return true;
	}
}

function validatePrezzo(){
	var prezzo = document.getElementById("prezzoField");
	
	if(prezzo.value <= 0 || isNaN(prezzo.value)==true || prezzo.value.trim()=="")
	{
		prezzo.nextElementSibling.innerHTML = "Valore non valido!";
		prezzo.style.borderColor = "red";
		return false;
	} else{
		prezzo.nextElementSibling.innerHTML = "";
		prezzo.style.borderColor = "white";
		return true;
	}	
}

function validateSconto(){
	var sconto = document.getElementById("scontoField");
	
	if(sconto.value <0 || isNaN(sconto.value)==true || sconto.value.trim()=="")
	{
		sconto.nextElementSibling.innerHTML = "Valore non valido!";
		sconto.style.borderColor = "red";
		return false;	
	} else{
		sconto.nextElementSibling.innerHTML = "";
		sconto.style.borderColor = "white";
		return true;
	}	
}

function validateDescrizione(){
	var descrizione = document.getElementById("descrizioneArea");
	
	if(descrizione.value.length>=1000) {
		descrizione.nextElementSibling.innerHTML = "La descrizione deve contenere massimo 1000 caratteri";
		descrizione.style.borderColor = "red";
		return false;
	}else{
		descrizione.nextElementSibling.innerHTML = "";
		descrizione.style.borderColor = "initial";
		return true;
	}	
}

function validateImg(){
	var img = document.getElementById("imgField");
	if(img!=null) {
		var path = img.value.split(".");
		
		if(img.value.trim()!="" && path[path.length-1] != "jpg" && path[path.length-1] != "jpeg"){
			img.nextElementSibling.innerHTML = "L'immagine deve avere estensione .jpg!";
			img.style.borderColor = "red";
			return false;
		}else if(img.value.trim()!="" && img.files[0].size>10485760) {
			img.nextElementSibling.innerHTML = "La dimensione del file deve essere minore di 10,4 MB!";
			img.style.borderColor = "red";
			return false;
		}else {
			img.nextElementSibling.innerHTML = "";
			img.style.borderColor = "white";
			return true;
		}
	} else {
		return true;
	}
}

function validateFormato(){
	var formato = document.getElementById("formatoField");
	
	if(formato.disabled == true)
	 return true;	
	
	if(formato.value.trim()=="")
	{
		formato.nextElementSibling.innerHTML = "Valore non valido!";
		formato.style.borderColor = "red";
		return false;
	} else{
		formato.nextElementSibling.innerHTML = "";
		formato.style.borderColor = "white";
		return true;
	}	
}

function validateGenere(){
	var genere = document.getElementById("genereField");
	
	if(genere.disabled == true)
		return true;
	
	if(genere.value.trim()=="")
	{
		genere.nextElementSibling.innerHTML = "Valore non valido!";
		genere.style.borderColor = "red";
		return false;
	} else{
		genere.nextElementSibling.innerHTML = "";
		genere.style.borderColor = "white";
		return true;
	}	

}

function validateInterni(){

	var interni = document.getElementById("interniSelect");
	
	if(interni.disabled == true)
		return true;
	
	if(interni.value=="b/n" || interni.value=="col.")
	{
		interni.nextElementSibling.innerHTML = "";
		interni.style.borderColor = "initial";
		return true;
	} else{
		interni.nextElementSibling.innerHTML = "Valore non consentito!";
		interni.style.borderColor="red";
		return false;
	}	

}

function validateNumPagine(){
	numPagine = document.getElementById("numPagineField");
	
	if(numPagine.disabled == true)
		return true;
	
	if(numPagine.value.trim()=="" || isNaN(numPagine.value)==true || numPagine.value<=0)
	{
		numPagine.nextElementSibling.innerHTML = "Valore non valido!";
		numPagine.style.borderColor = "red";
		return false;
	} else{
		numPagine.nextElementSibling.innerHTML = "";
		numPagine.style.borderColor = "white";
		return true;
	}		
}

function validateNomeSerie(){
	nomeSerie = document.getElementById("nomeSerieField");
	
	if(nomeSerie.disabled == true)
		return true;
	
	if(nomeSerie.value.trim()=="")
	{
		nomeSerie.nextElementSibling.innerHTML = "Campo obbligatorio!";
		nomeSerie.style.borderColor = "red";
		return false;
	} else{
		numPagine.nextElementSibling.innerHTML = "";
		numPagine.style.borderColor = "white";
		return true;
	}		
}

function validatePeriodicita(){
	periodicita = document.getElementById("periodicitaField");
	
	if(periodicita.disabled == true)
		return true;
	
	if(periodicita.value.trim()=="")
	{
		periodicita.nextElementSibling.innerHTML = "Campo obbligatorio!";
		periodicita.style.borderColor = "red";
		return false;
	} else{
		periodicita.nextElementSibling.innerHTML = "";
		periodicita.style.borderColor = "white";
		return true;
	}		
}

function validateNumFumetto(){
	numFumetto = document.getElementById("numeroField");
	
	if(numFumetto.disabled == true)
		return true;
	
	if(numFumetto.value<0 || isNaN(numFumetto.value)==true || numFumetto.value.trim()=="")
	{
		numFumetto.nextElementSibling.innerHTML = "Valore non valido!";
		numFumetto.style.borderColor = "red";
		return false;
	} else{
		numFumetto.nextElementSibling.innerHTML = "";
		numFumetto.style.borderColor = "white";
		return true;
	}	
}