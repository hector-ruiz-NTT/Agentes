function genListaGenerica() {
	var arg = arguments;
	var cont = 1;
	var tabla = arg[0]; // se recoge el numero de tabla

	var nombreForm = arg[1];
	var theForm = document.forms[0];

	for (var i = 0; i < document.forms.length; i++) {
		if (document.forms[i].name == nombreForm) {
			theForm = document.forms[i];
		}
	}

	for (var i = 0; i < document.forms.length; i++) {
		for (var j = 0; j < document.forms[i].elements.length; j++) {
			if (document.forms[i].elements[j].name == arg[j + 2]) {
				theForm = document.forms[i];
				nombreForm = document.forms[i].name;
			}
		}
	}

	var devuelve = ""; // Genera una cadena con los parámetros que hay que
	// devolver
	var window;

	for (; cont < arg.length; cont++) {
		for (i = 0; i < theForm.elements.length; i++) {
			if (theForm.elements[i].name == arg[cont]) {
				devuelve += i + ",";
			}
		}
	}

	devuelve = devuelve.substring(0, devuelve.length - 1);

	var cadenaLlamada = "ListaValoresJSP.icm" + "?NOMBRE=" + escape(tabla)
			+ "&DEVUELVE=" + escape(devuelve);
	if (nombreForm != '') {
		cadenaLlamada += "&NOMBRE_FORMULARIO=" + nombreForm;
	}

	window = open(cadenaLlamada, "NuevaPagina",
			"Scrollbars=1, resizable=1, width=400, height=400, top=100, left=100");

}

/*******************************************************************************
 * function genCalendario(x) función que llama al cgi generador del calendario,
 * en "x" se pasa nombre de la caja de texto donde se quiere pasar el resultado
 * de la busqueda. "p_devolver" es un campo oculto del calendario.
 ******************************************************************************/
function genCalendario(idInput, idForm) {
	if (document.forms.length < 1)
		alert('La página no contiene ningún formulario, para utilizar el calendario debe definir uno. Pongase en contacto con el responsable de la aplicación.');
	//Si recibimos el identificador del formulario lo cargamos directamente.
	var form = document.getElementById(idForm);	
	//Si no recibimos el identificador del formulario asumimos que es el primer formulario.
	if (idForm == null) {
		idForm = document.forms[0].name;
		form = document.forms[0];
	}
	//Aunque el formulario no tenga name, el form debe estar definido.
	if (idForm == null) idForm = document.forms[0].id;
	//Comprobamos que tenemos el identificador del formulario cuando es el único de la página.
	if (idForm == null && document.forms.length == 1) {
		alert('La página contiene un único formulario y este no tiene definido ni nombre ni identificador. Pongase en contacto con el responsable de la aplicación.');
	}
	//Si el objeto form no se ha cargado es porque se ha recibido el nombre del formulario en lugar del id
	if (form == null || typeof form === "undefined") {
		for (aux_form in document.forms) {
			if (aux_form.id == idForm || aux_form.name == idForm) {
				form = aux_form;
				break;
			}
		}
	}

	var input = document.getElementById(idInput);
	if (input == null || typeof input === "undefined") {
		for (aux_input in form.elements) {
			if (aux_input.id == idInput || aux_input.name == idInput) {
				input = aux_input;
				break;
			}
		}
	}
	

	var cadenaCalendario = "CalendarioJSP.icm?" + "annoInicio=" + escape(1990)
			+ "&annoFin=" + escape(2050) + "&idOpenerForm=" + idForm
			+ "&idOpenerField=" + idInput;

	var win = open(cadenaCalendario, "Calendario",
			"status,Scrollbars=1,resizable=1,width=340,height=275,top=100,left=100");
	win.opener = self;
	win.focus();
}

/** ******************************************************* */
/* Javascript del Componente Calendario */
/** ******************************************************* */

function ayuda(value) {
	document.getElementById("diaFestivo").value = value;
}

function salirayuda() {
	document.getElementById("diaFestivo").value = "";
}

function passBack(day, mes, anno) {
	var idOpenerForm = document.getElementById("openerForm").value;
	var openerFormObj = window.opener.document.getElementById(idOpenerForm);
	if (openerFormObj == null || typeof openerFormObj === "undefined") {
		openerFormObj = window.opener.document[idOpenerForm];
	}
	var idOpenerField = document.getElementById("openerField").value;
	var openerFieldObj = window.opener.document.getElementById(idOpenerField);
	if (openerFieldObj == null || typeof openerFieldObj === "undefined")
		openerFieldObj = openerFormObj.elements[idOpenerField];
	if (openerFieldObj == null || typeof openerFieldObj === "undefined")
		openerFieldObj = window.opener.document[idOpenerForm].elements[idOpenerField];
	if (openerFieldObj == null || typeof openerFieldObj === "undefined") {
		for (aux_input in openerFormObj.elements) {
			if (aux_input == idOpenerField ) {
				openerFieldObj = aux_input;
				break;
			}
		}
	}
	if (openerFieldObj == null || typeof openerFieldObj === "undefined") 
		alert("No se ha encontrado el elemento '" + idOpenerField + "' en la página.");
	openerFieldObj.value = day + "/" + mes + "/" + anno;
	if (openerFieldObj.readOnly != true) openerFieldObj.focus();
	window.close();
}

function irASeleccion() {
	document.getElementById("calendarForm").submit();
}

function setPreviousYear() {
	var anno = document.getElementById("ANNO");

	var anoActual = anno.options[anno.selectedIndex].value;
	var anoAnterior = anoActual - 1;
	var indiceAnnoAnterior = -1;

	for (var i = 0; i <= anno.options.length && indiceAnnoAnterior == -1
			&& anno.options != undefined; i++) {
		if (anno.options[i].value == anoAnterior) {
			indiceAnnoAnterior = i;
		}
	}

	if (indiceAnnoAnterior != -1) {
		anno.selectedIndex = indiceAnnoAnterior;
		irASeleccion();
	}
}

function setPreviousMonth() {
	var mes = document.getElementById("MES");

	var mesActual = mes.options[mes.selectedIndex].value;
	var mesAnterior = mesActual - 1;
	var cambioAnyo = false;

	if (mesActual == 1) {
		mesAnterior = 12;
		cambioAnyo = true;
	}

	var indiceMesAnterior = -1;
	for (var i = 0; i <= mes.options.length && indiceMesAnterior == -1
			&& mes.options != undefined; i++) {
		if (mes.options[i].value == mesAnterior) {
			indiceMesAnterior = i;
		}
	}

	if (indiceMesAnterior != -1) {
		mes.selectedIndex = indiceMesAnterior;
		if (!cambioAnyo) {
			irASeleccion();
		} else {
			setPreviousYear();
		}
	}
}

function setToday(anno, mes) {
	var mesObj = document.getElementById("MES");
	var annoObj = document.getElementById("ANNO");

	var indiceMesHoy = -1;
	for (var i = 0; i <= mesObj.options.length && indiceMesHoy == -1
			&& mesObj.options != undefined; i++) {
		if (mesObj.options[i].value == mes) {
			indiceMesHoy = i;
		}
	}

	var indiceAnnoHoy = -1;
	for (var i = 0; i <= annoObj.options.length && indiceAnnoHoy == -1
			&& annoObj.options != undefined; i++) {
		if (annoObj.options[i].value == anno) {
			indiceAnnoHoy = i;
		}
	}

	annoObj.selectedIndex = indiceAnnoHoy;
	mesObj.selectedIndex = indiceMesHoy;
	irASeleccion();
}

function setNextMonth() {
	var mesObj = document.getElementById("MES");

	var mesActual = mesObj.options[mesObj.selectedIndex].value;
	var mesAnterior = parseInt(mesActual) + 1;
	var cambioAnyo = false;

	if (mesActual == 12) {
		mesAnterior = 1;
		cambioAnyo = true;
	}

	var indiceMesAnterior = -1;
	for (var i = 0; i <= mesObj.options.length && indiceMesAnterior == -1
			&& mesObj.options != undefined; i++) {
		if (mesObj.options[i].value == mesAnterior) {
			indiceMesAnterior = i;
		}
	}

	if (indiceMesAnterior != -1) {
		mesObj.selectedIndex = indiceMesAnterior;
		if (!cambioAnyo) {
			irASeleccion();
		} else {
			setNextYear();
		}
	}
}

function setNextYear() {
	var annoObj = document.getElementById("ANNO");
	var anoActual = annoObj.options[annoObj.selectedIndex].value;
	var anoAnterior = parseInt(anoActual) + 1;
	var indiceAnnoAnterior = -1;

	for (var i = 0; i <= annoObj.options.length && indiceAnnoAnterior == -1
			&& annoObj.options != undefined; i++) {
		if (annoObj.options[i].value == anoAnterior) {
			indiceAnnoAnterior = i;
		}
	}

	if (indiceAnnoAnterior != -1) {
		annoObj.selectedIndex = indiceAnnoAnterior;
		irASeleccion();
	}
}

/** ******************************************************* */

window.Sistemas = {
	cerrarSesion : function() {
		document.location.href = 'FinalizarSesion.icm';
	}
};