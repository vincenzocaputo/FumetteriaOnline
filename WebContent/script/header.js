function showMenu() {
	var display = document.getElementById("userMenu").style.display;
	if(display == "") {
		document.getElementById("userMenu").style.display = "block";
		document.getElementById("userMenuCell").style.height = "auto";
	}
}

function hideMenu() {
	var display = document.getElementById("userMenu").style.display;
	if(display == "block") {
		document.getElementById("userMenu").style.display = "";
		document.getElementById("userMenuCell").style.height = "100%";
	}
}

function showSandwichMenu() {
	var listMenu = document.getElementById("menuCell").getElementsByTagName("ul")[0];
	if(listMenu.id=="listMenu") {
		listMenu.id = "sandwichListMenu";
	} else {
		listMenu.id = "listMenu";
	}
}

function showPopupMsg(error, message, timeout) {
	var popupMsg = document.getElementById("popupMsg");
	popupMsg.innerHTML = message;
	popupMsg.style.display="block";
	if(error)
		popupMsg.style.background = "rgba(255,0,0,0.5)";
	else
		popupMsg.style.background = "rgba(30,144,255,0.5)";
	
	if(timeout!=0) {
		setTimeout(function() {
				popupMsg.innerHTML = "";
				popupMsg.style.display="none";
			}, timeout);
	}
}

function hidePopupMsg() {
	if(popupMsg.innerHTML == "Attendi...") {
		popupMsg.innerHTML = "";
		popupMsg.style.display="none";
	}
}
window.onresize = function(event) {
	var listMenu = document.getElementById("menuCell").getElementsByTagName("ul")[0];
	if(listMenu.id = "sandwichListMenu"){
		listMenu.id = "listMenu";
	}
};