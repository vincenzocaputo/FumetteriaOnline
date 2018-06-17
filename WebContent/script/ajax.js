function getXmlHttpRequest() {
	var xhr = false;
	var activeXoptions = new Array("Microsoft.XmlHttp", "MSXML4.XmlHttp", "MSXML3.XmlHttp", "MSXML2.XmlHttp", "MSXML.XmlHttp");

	try {
		xhr = new XMLHttpRequest();
		console.log("Get XML http request");
	} catch (e) {
	}

	if (!xhr) {
		var created = false;
		for (var i = 0; i < activeXoptions.length && !created; i++) {
			try {
				xhr = new ActiveXObject(activeXoptions[i]);
				created = true;
				console.log("Get ActiveXObject XML http request");
			} catch (e) {
			}
		}
	}
	return xhr;
}

function ajaxCall(url, handler) {
	var req = getXmlHttpRequest();
	try {
		wait(true);
		req.onreadystatechange = getReadyStateHandler(req, handler, 2000)
		
		req.open('GET', url, true);
		req.setRequestHeader("connection",false);
		req.send(null);
		return false;
	} catch(e) {
		return false;
	}
}

function getReadyStateHandler(req, responseJSONHandler, timeout) {
	return function() {
		if(timeout > 0) {
			handleWait(req, new Date().getTime(), timeout);
		}
		
		if (req.readyState == 1) {
			console.log("Server connection");
		} else if ( req.readyState == 2 ) {
			console.log("Send request");
		} else if ( req.readyState == 3 ) {
				console.log("Receive response");
				wait(false);
		} else if (req.readyState == 4) {
			console.log("Request finished and response is ready");
			if (req.status == 200 || req.status == 304) {
				wait(false);
				responseJSONHandler(req.responseText);
			} else if(req.status == 401) {
				wait(false);
				showPopupMsg(true, "Devi effettuare l'accesso.",5000);
			} else {
				console.log("Response error "+ req.status + " " + req.statusText);
			}
		} else {
			wait(false);
		}
	}
}

function wait(state) {
	if (state == true) {
		showPopupMsg(false, "Attendi...",0);				   
	} else {
		hidePopupMsg();
	}
}

function handleWait(req, start, maxTime) {
	checkTime = function() {

		if ( req.readyState == 4 ) {
			wait(false);
			return;
		}

		if((new Date().getTime() - start) > maxTime) {
			req.onreadystatechange = function(){
				showPopupMsg(true, "Errore", 5000);
				return;
			};
			req.abort();
			wait(false);
		} 
		else {
			setTimeout(checkTime, 100);
		}
	}
	checkTime();	
}
