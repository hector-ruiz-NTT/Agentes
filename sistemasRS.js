function rellenaCombo(cadena) {
	/* Se sacan las opciones, que vienen en una cadena separadas por '~'. La estructura de la cadena de entrada
	es :
		id_campo|valor1,valor2,...,valorn
	*/

	var salir = 0;
	var elemento,id;
	var nueva_opcion;
	var i = 0;

	/* Se vacía la lista */
	var nombre_control = cadena.substring(0,cadena.indexOf("|"));
	cadena = cadena.substring(cadena.indexOf("|") + 1,cadena.length);
    	var selectc = document.getElementById(nombre_control);
	for (i=selectc.options.length-1; i>=0; i--) {
		selectc.options[i] = null;
	}

	var limite = cadena.length;
	while (!salir) {

		id = cadena.substring(0, cadena.indexOf("¨"));
		cadena = cadena.substring(cadena.indexOf("¨") + 1, cadena.length);
		elemento = cadena.substring(0, cadena.indexOf("~"));
		cadena = cadena.substring(cadena.indexOf("~") + 1, cadena.length);

		if (cadena.indexOf("~") <= 0) {
			salir = 1;
		}

		nueva_opcion = document.createElement("OPTION");
		nueva_opcion.text=elemento
		nueva_opcion.value=id;
		selectc.add(nueva_opcion);			
	}
}

function rellenaCampoDerivado(cadena) {
	/* Se escribe el valor de un campo a partir del valor que se escribe en otro. Para funcionar, se utiliza
	el evento de salida del campo
	*/

	var nombre_control = cadena.substring(0,cadena.indexOf("|"));
	cadena = cadena.substring(cadena.indexOf("|") + 1,cadena.length);
	var control = document.getElementById(nombre_control);
	control.value = cadena;
}

function creaRadios(cadena) {
	/* Crea una lista de radio. Llega como parámetro el nombre del radio, el nombre del control div en el
	que se pinta y los valores (separados por comas)
	*/

	var salir = 0;
	var elemento;
	var elementos = "";
	var nombre_control = cadena.substring(0,cadena.indexOf("|"));
	cadena = cadena.substring(cadena.indexOf("|") + 1,cadena.length);
	var nombre_espacio = cadena.substring(0,cadena.indexOf("|"));
	cadena = cadena.substring(cadena.indexOf("|") + 1,cadena.length);

	while (!salir) {
		elemento = cadena.substring(0, cadena.indexOf("~"));
		cadena = cadena.substring(cadena.indexOf("~") + 1,cadena.length);

		if (cadena.indexOf("~") <= 0) {
			salir = 1;
		}

		elementos += "<input type='radio' name='" + nombre_control + "' value='" + elemento + "'>" + elemento + "<br>";
	}

	document.getElementById(nombre_espacio).innerHTML = elementos;
}

function creaLista(cadena) {
	/* Funciona como creaRadios, salvo que no es necesario indicar el nombre del control (pero sí el del
	espacio).
	*/
	var salir = 0;
	var elementos = "";
	var elemento;
	var nombre_espacio = cadena.substring(0,cadena.indexOf("|"));
	cadena = cadena.substring(cadena.indexOf("|") + 1,cadena.length);

	elementos += "<ol>";
	while (!salir) {
		elemento = cadena.substring(0, cadena.indexOf("~"));
		cadena = cadena.substring(cadena.indexOf("~") + 1,cadena.length);

		if (cadena.indexOf("~") <= 0) {
			salir = 1;
		}


		elementos += "<li>" + elemento + "</li>";
	}
	elementos += "</ol>";

	document.getElementById(nombre_espacio).innerHTML = elementos;
}

function creaTabla(cadena) {
	/* Esta función pinta una tabla a partir de los datos de entrada. Primero entra el nombre del control
	div donde se pinta la tabla; después, vienen los valores; cada valor va por comas, y se separa cada
	columna por el control |
	*/

	var salir = 0;
	var tabla = "<table border='1'><tr>";
	var elemento;
	var titulo = 1;
	var nombre_espacio = cadena.substring(0,cadena.indexOf("|"));
	cadena = cadena.substring(cadena.indexOf("|") + 1,cadena.length);

	while (!salir) {
		if (cadena.indexOf("~") <= cadena.indexOf("|") || cadena.indexOf("|") == -1) {
			elemento = cadena.substring(0, cadena.indexOf("~"));
			cadena = cadena.substring(cadena.indexOf("~") + 1,cadena.length);
			if (titulo == 0)
				tabla += "<td>" + elemento + "</td>";
			else
				tabla += "<td align='center'><b>" + elemento + "</b></td>";
		}
		else {
			elemento = cadena.substring(0, cadena.indexOf("|"));
			cadena = cadena.substring(cadena.indexOf("|") + 1,cadena.length);
			if (titulo == 0)
				tabla += "<td>" + elemento + "</td></tr>";
			else
				tabla += "<td align='center'><b>" + elemento + "</b></td></tr>";

			titulo = 0;
		}

		if (cadena.indexOf("~") <= 0) {
			salir = 1;
		}
	}

	tabla += "<td>" + cadena + "</td>";
	tabla += "</tr></table>";
	document.getElementById(nombre_espacio).innerHTML = tabla;
}

function insertaHTML(cadena) {

	/* Esta función escribe directamente lo que viene en el parámetro al div indicado
	*/
			
	var nombre_espacio = cadena.substring(0,cadena.indexOf("|"));
	cadena = cadena.substring(cadena.indexOf("|") + 1,cadena.length);
	document.getElementById(nombre_espacio).innerHTML = cadena;
}


/* Funciones a las que se debe llamar desde los programas */


function ComboRS(accion, parametro, nombre_campo){
    	jsrsExecute(accion,rellenaCombo,'ejecutarRS',Array(nombre_campo,parametro));
}

function TextoDerivadoRS(accion, parametro, nombre_campo) {
	jsrsExecute(accion,rellenaCampoDerivado,'ejecutarRS',Array(nombre_campo,parametro));
}

function RadioButtonRS(accion, nombre_control, nombre_espacio) {
	jsrsExecute(accion,creaRadios,'ejecutarRS',Array(nombre_control,nombre_espacio));
}

function ListaRS(accion, nombre_espacio) {
	jsrsExecute(accion,creaLista,'ejecutarRS',nombre_espacio);
}

function TablaRS(accion, nombre_espacio) {
	jsrsExecute(accion,creaTabla,'ejecutarRS',nombre_espacio);
}

function HTMLRS(accion, nombre_espacio,valor) {
	jsrsExecute(accion,insertaHTML,'ejecutarRS',Array(nombre_espacio,valor));
}

function ExpandirArbol(espacio,texto_padre,codigo,ultima) {
	jsrsExecute('ExpandirNodo.icm',insertaHTML,'ejecutarRS',Array(espacio,texto_padre,codigo,ultima));
}
	
function ContraerArbol(espacio,texto_padre,codigo,ultima) {
	jsrsExecute('ContraerNodo.icm',insertaHTML,'ejecutarRS',Array(espacio,texto_padre,codigo,ultima));
}


/* Fin de las funciones a las que se debe llamar desde los programas */