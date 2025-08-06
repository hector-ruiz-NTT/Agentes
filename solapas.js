/* INICIO: Funciones para gestionar las pestanyas */

function selectTab(panelID,numDiv,n) {
	var panelDiv;
	var tabDiv;
	
	for(var i=0; i<numDiv; i++) {
		if (document.layers) {
			panelDiv=document.layers[panelID+'panel'+i];
			tabDiv=document.layers[panelID+'tab'+i]; }
		else if (document.all) {
			panelDiv=document.all[panelID+'panel'+i];
			tabDiv=document.all[panelID+'tab'+i]; }
		else if (document.getElementById) {
			panelDiv=document.getElementById(panelID+'panel'+i);
			tabDiv=document.getElementById(panelID+'tab'+i); 
		}
		if (i==n) panelDiv.style.display = 'block';
		else panelDiv.style.display = 'none';
		if (i==n) tabDiv.className="tabselected";
		else      tabDiv.className="tab";
	}
	setTabCookie(panelID, n);
}

function getCookieContextPath() {
	if (window.contextPath) {
		return "; path=" + window.contextPath;
	} else {
		return "";
	}
}

function getCookie(name, suffix) {
	var prefix = name + suffix + "=";
	var cookieStartIndex = document.cookie.indexOf(prefix);
	if (cookieStartIndex == -1) return "???";
	var cookieEndIndex = document.cookie.indexOf(";", cookieStartIndex + prefix.length);
	if (cookieEndIndex == -1) cookieEndIndex = document.cookie.length;
	return unescape(document.cookie.substring(cookieStartIndex + prefix.length, cookieEndIndex));
}

function setTabCookie(name, value) {
	var cookie = getCookie("selectedTab", "");	

	var start;
	var end;
	if (cookie=="undefined") cookie = "";
	if (cookie==null) cookie = "";
	if (cookie=="???") cookie = "";	
	start = cookie.indexOf(name + "=");
	if (start==-1) {
		cookie = cookie + name + "=" + value + ";"
	} else {
		end = cookie.substring(start).indexOf(";");
		cookie = cookie.substring(0, start) + name + "=" + value + ";" + cookie.substring(end);
	}
	document.cookie = "selectedTab=" + escape(cookie) + getCookieContextPath();
}

/* FIN: Funciones para gestionar las pestanyas */

