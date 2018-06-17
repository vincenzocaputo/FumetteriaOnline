function validateLogon()
{
	try {
		var v = [validateEmail(), validateCognome(), validateNome(), validateCitta(), validateIndirizzo(), validatePassword()];
	
		for(var i=0; i<v.length; i++) {
			if(v[i] == false) {
				return false;
			}
		}
		return true;
	} catch(e) {
		console.log(e);
		return false;
	}
}

function validateLogin()
{
	var v = [validateEmail(), validatePassword()];

	for(var i=0; i<v.length; i++) {
		if(v[i] == false) {
			return false;
		}
	}
	return true;
}

function validateEmail()
{
	
	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	
	var email = document.getElementById("email");
	
	if(email.value.match(mailformat)) 
	{
		email.nextElementSibling.innerHTML = "";
		email.style.borderColor = "white";
		return true;
	} 
	else 
	{ 
		email.style.borderColor = "red";
		email.nextElementSibling.innerHTML = "Email errata";
		return false;
	} 
}

function validateCognome()
{
	var cognome = document.getElementById("cognome");
	if(cognome.value.trim() == "")
	{	 
		cognome.style.borderColor = "red";
		cognome.nextElementSibling.innerHTML = "Campo obbligatorio";
		return false;
	} else {
		cognome.nextElementSibling.innerHTML = "";
		cognome.style.borderColor = "white";
		return true;
	}
}

function validateNome() {
	var nome = document.getElementById("nome");
	
	if(nome.value.trim() == "")
	{	 
		nome.style.borderColor = "red";
		nome.nextElementSibling.innerHTML = "Campo obbligatorio";
		return false;
	} else
	  {
		nome.nextElementSibling.innerHTML = "";
		nome.style.borderColor = "white";
		return true;
	  }
}

function validateCitta()
{
	var citta = document.getElementById("citta");
	var provincia = document.getElementById("provincia");
	provincia.value=provincia.value.toUpperCase();
	var t=0;
	var errorString = "";
	  
	if(citta.value.trim() == "")
	{
		citta.style.borderColor = "red";
		errorString = "Campo obbligatorio<br>";
		t = 1;
	}	
	if(t!=1) 
		citta.style.borderColor = "white";
		
	if(provincia.value.trim() == "")
	{	 
		provincia.style.borderColor = "red";
		errorString = "Campo obbligatorio<br>";
		t = 2;
	} else if(provincia.value.length!=2) {
		provincia.style.borderColor = "red";
		errorString += "La provincia deve avere 2 caratteri<br>";
		t = 2;
	} else if(!provincia.value.match(/^[a-zA-Z]+$/)) {
		provincia.style.borderColor = "red";
		errorString += "La provincia deve contenere solo lettere<br>";
		t = 2;
	}
	if(t!=2) 
		provincia.style.borderColor = "white";
	  
	if(t==0) {
		document.getElementById("cittaTable").nextElementSibling.innerHTML = "";
		return true;
	}
	else {
		document.getElementById("cittaTable").nextElementSibling.innerHTML = errorString;
		return false;
	}
}

function validateIndirizzo() {
	var via = document.getElementById("via");
	var civico = document.getElementById("civico");
	var cap = document.getElementById("cap");
	var t = 0;
	var errorString = "";
	
	if(via.value.trim() == "")
	{	 
		via.style.borderColor = "red";
		errorString = "Campo obbligatorio<br>";
		t = 1;
	}
	
	if(t!=1) 
		via.style.borderColor = "white";
	
	if(civico.value.trim() == "")
	{	 
		civico.style.borderColor = "red";
		errorString = "Campo obbligatorio<br>";
		t = 2;
	} else if(civico.value.length>3) {
		civico.style.borderColor = "red";
		errorString += "Il CAP deve avere al massimo 3 caratteri<br>";
		t = 2;
	} 
	if(t!=2) 
		civico.style.borderColor = "white";
	
	if(cap.value.trim() == "")
	{	 
		cap.style.borderColor = "red";
		errorString = "Campo obbligatorio";
		t = 3;
	} else if(cap.value.length!=5) {
		cap.style.borderColor = "red";
		errorString  += "Il CAP deve avere esattamente 5 caratteri<br>";
		t = 3;
	} else if(isNaN(cap.value)==true) {
		cap.style.borderColor = "red";
		errorString += "Il CAP deve avere caratteri numerici<br>";
		t = 3;
	}
	if(t!=3) 
		cap.style.borderColor = "white";

	if(t==0) {
		document.getElementById("indirizzo").nextElementSibling.innerHTML = "";
		return true;
	}
	else {
		document.getElementById("indirizzo").nextElementSibling.innerHTML = errorString;
		return false;
	}
}

function validatePassword() {
	var password = document.getElementById("password");
	
	if(password.value.trim() == "")
	{	 
		password.style.borderColor = "red";
		password.nextElementSibling.innerHTML = "Campo obbligatorio";
		return false;
	} else if(password.value.length < 8) {
		password.style.borderColor = "red";
		password.nextElementSibling.innerHTML = "La password deve avere almeno 8 caratteri";
		return false;
	} else {
		password.style.borderColor = "white";	
		password.nextElementSibling.innerHTML = "";
		return true;
	}

}