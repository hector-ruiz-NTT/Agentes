function validarRepetidosHuecos(arr, cadena, huecos) {
  var datoOk = true;
  if (cadena != "") {
    if (huecos) {
      datoOk = false;
    }
    else {
      for (i=0; i<arr.length; i++) {
        if (arr[i] == cadena) {
          datoOk = false;
          break;
        }
      }
      if (datoOk) {
        arr[arr.length] = cadena;
      }
    }
    huecos = false;
  }
  return datoOk;
}

// Valida que un campo solo contiene caracteres que estan en cadena
function validarCampoCaracteres(campo, literal, navegar, cadena) {
	// Compruebo los caracteres validos
  var checkOK = cadena;
  var checkStr = campo.value;
  var allValid = true;
  
  for (i = 0;  i < checkStr.length;  i++) {
    ch = checkStr.charAt(i);
    for (j = 0;  j < checkOK.length;  j++) {
    	if (ch == checkOK.charAt(j))
    		break;
    }
    if (j == checkOK.length) {
    	allValid = false;
    	break;
    }
  }
  if (!allValid) {
    alert("Rellene el campo \"" + literal + "\" con caracteres y sin acentos.");
    if (navegar) {
      campo.focus();
    }
    return (false);
  }
  else
    return true;
}

// Devuelve false si el item no esta relleno, true en caso contrario
function validarCampoObligatorio(campo, literal, navegar) {

  var aux = campo.value;
  var ch = '';
  var numblancos = 0;
  var encontradoBlanco = false;
  var blanco = ' ';
  
  if (aux == "") {
    alert("Introduzca un valor para el campo \""+ literal + "\"");
    if (navegar) {
      campo.focus();
    }
    return false;
  }
  else {
    for (i = 0;  i < aux.length;  i++) {
      ch = aux.charAt(i);
      if (ch == blanco.charAt(0)) {
	++numblancos;
      	encontradoBlanco = true;
      }
    }  

    if (numblancos == aux.length) {
    	alert("Introduzca un valor para el campo \""+ literal + "\" que no esté vacío");
    	if (navegar) {
    	  campo.focus();
    	}
    	return (false);
    }
    else {
    	return (true);
    }
    //return encontradoBlanco;
  }
  //return true;
}

// Valida la longitud de un campo
function validarCampoLongitud(campo, literal, navegar, total) {
  if (parseInt(campo.value.length) != parseInt(total)) {
    alert("Introduzca " + total + " caracteres para el campo \""+ literal + "\"");
    if (navegar) {
      campo.focus();
    }
    return false;
  }
  return true;
}

// Valida que un campo contenga un numero
function validarCampoNumerico(campo, literal, navegar) {    
  var checkStr = campo.value;
  var checkOK = "0123456789";  
  var allValid = true;
  for (i = 0;  i < checkStr.length;  i++) {
    ch = checkStr.charAt(i);
    for (j = 0;  j < checkOK.length;  j++) {
      if (ch == checkOK.charAt(j))
        break;
    }
    if (j == checkOK.length) {
      allValid = false;
      break;
    }
  }
  if (!allValid) {
    alert("Introduzca sólo dígitos en el campo \""+ literal + "\"");
    if (navegar) {      
      campo.focus();
    }
    return false;
  }
  return true;
}

// Valida que un dato sea numerico
function validarNumerico(dato) {    
  var checkStr = dato;
  var checkOK = "0123456789";  
  var allValid = true;
  for (i = 0;  i < checkStr.length;  i++) {
    ch = checkStr.charAt(i);
    for (j = 0;  j < checkOK.length;  j++) {
      if (ch == checkOK.charAt(j))
        break;
    }
    if (j == checkOK.length) {
      allValid = false;
      break;
    }
  }
  if (!allValid) {
    return false;
  }
  return true;
}

// Valida un numero decimal
function validarCampoNumericoDecimal(campo, literal, navegar, indicadorDecimal) {    
	var checkOK = "0123456789-" + indicadorDecimal;
  var checkStr = campo.value;
  var allValid = true;
  var validGroups = true;
  var decPoints = 0;
  var allNum = "";
  for (i = 0;  i < checkStr.length;  i++) {
    ch = checkStr.charAt(i);
    for (j = 0;  j < checkOK.length;  j++) {
      if (ch == checkOK.charAt(j))
        break;
    }
    if (j == checkOK.length) {
      allValid = false;
      break;
    }
    if (ch == indicadorDecimal) {
      allNum += ",";
      decPoints++;
    }
    else
      allNum += ch;
  }
  if (!allValid) {
    if (navegar) {
      campo.focus();
    }
    alert("Escriba solo numeros y use el caracter \"" + indicadorDecimal + "\" para decimales en el campo \"" + literal + "\"");    
    return (false);
  }

  if (decPoints > 1 || !validGroups) {
    alert("Escriba un numero valido en el campo \"" + literal + "\"");
    if (navegar) {
      campo.focus();
    }
    return (false);
  }
  return (true);
}

function validarCampoFecha(campo, literal, navegar) {
  if (campo.value.length != 10) {
    alert("Introduzca un valor para el campo \"" + literal + "\" con el formato dd/mm/yyyy.");
    if (navegar) {
      campo.focus();
    }
    return (false);
  }
  // Compruebo el formato de la fecha de nacimiento  	
  var checkOK = "0123456789";
  var checkStr = campo.value;
  var allValid = true;
  for (i = 0;  i < checkStr.length;  i++) {
    ch = checkStr.charAt(i);
    if ((i==2) || (i==5)) { // Las barras
      if (ch != '/') {
        allValid = false;
  	    break;
   	  }
    }
    else {
      for (j = 0;  j < checkOK.length;  j++) {
        if (ch == checkOK.charAt(j))
          break;
   	  }
   	  if (j == checkOK.length) {
     	  allValid = false;
     	  break;
   	  }
    }
  }
  if (!allValid) {
    alert("Introduzca un valor para el campo \"" + literal + "\" con el formato dd/mm/yyyy.");
    if (navegar) {
    	campo.focus();
    }
    return (false);
  }
  return true;
}

function validarMinimoMaximo(campo, literal, navegar, minimo, maximo) {
  if (minimo != null) {
    if (parseInt(campo.value) < minimo) {
      if (navegar) {
        campo.focus();
      }
  	  alert("El valor mínimo del campo " + literal + " es: " + minimo);
  	  return false;
    }
  }
  if (maximo != null) {
    if (parseInt(campo.value) > maximo) {
      if (navegar) {
        campo.focus();
      }
  		alert("El valor máximo del campo " + literal + " es: " + maximo);
  	 	return false;
    }
  }
  return true;
}

function validarMinimo(campo, literal, navegar, minimo) {
  if (minimo != null) {
    if (parseInt(campo.value) < minimo) {
      if (navegar) {
        campo.focus();
      }
  	  alert("El valor mínimo del campo " + literal + " es: " + minimo);
  	  return false;
    }
  }
  return true;
}

// Pasa un campo a mayusculas
void function pasarAMayusculas(campo) {
  campo.value = campo.value.toUpperCase();
}

// Pasa a mayusculas todos los campos de texto de un formulario
void function pasarTextoAMayusculas(vForm) {    
  var vItem;
  for (i=0; i<vForm.elements.length; i++) {
    vItem = vForm.elements[i];
    if (vItem.type == "text") {
      vItem.value = vItem.value.toUpperCase();
    }
  }  
}

// Funcion que saca un mensaje con el formato del campo DNI
void function mensajeFormatoDNI(x) {
  alert(x + "\nFormatos:\n"
    + "1. DNI: 9 digitos (complete con 0's por la izquierda).\n"
	  + "2. Tarjeta Residencia: Caracter + 7 digitos (complete con 0's por la izquierda) + Caracter.");
}

// Funcion que recibe un elemento y valida si su valor cumple el formato del DNI
function validarCampoDNI(dni) {	
  // Paso a mayusculas
  dni.value = dni.value.toUpperCase();    
  
  if (dni.value == "") {      
    mensajeFormatoDNI("Introduzca un valor para el campo \"DNI\"");
    dni.focus();
    return (false);
  }
    
  if (!(dni.value.length == 9)) {  
    mensajeFormatoDNI("Formato incorrecto del campo \"DNI\"");
    dni.focus();
    return (false);
  }

  var checkOK = "0123456789";
  var checkStrOK = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";  	
  var checkStr = dni.value;
  // Cojo el primer caracter
  var caracter = checkStr.charAt(0);
  var esDNI = false;
  	  	
  for (i = 0;  i < checkOK.length;  i++) {
    if (caracter == checkOK.charAt(i)) {
      esDNI = true;
      break;
    }
  }
    
  if (esDNI) {
    var allValid = true;
    for (i = 0;  i < checkStr.length;  i++) {
      ch = checkStr.charAt(i);
      for (j = 0;  j < checkOK.length;  j++) {
        if (ch == checkOK.charAt(j))
          break;
      }
      if (j == checkOK.length) {
        allValid = false;
        break;
      }
  	}
  	if (!allValid) {    
      mensajeFormatoDNI("Formato incorrecto del campo \"DNI\".");
      dni.focus();
      return (false);
  	}
  }
  else {
    var allValid = false;
    // El primer caracter es una letra. Ahora veo el ultimo
    var caracter = checkStr.charAt(8);
    for (i = 0;  i < checkStrOK.length;  i++) {
      if (caracter == checkStrOK.charAt(i)) {    	
        allValid = true;
        break;
      }
    }    	
    if (!allValid) {      
      mensajeFormatoDNI("Formato incorrecto del campo \"DNI\".");
      dni.focus();
      return (false);
    }
    	
    for (i = 1;  i < 8;  i++) {
      var caracter = checkStr.charAt(i);
      for (j = 0;  j < checkOK.length;  j++) {
        if (caracter == checkOK.charAt(j))
          break;
      }
      if (j == checkOK.length) {
        allValid = false;
        break;
      }
  	}
  	if (!allValid) {      
      mensajeFormatoDNI("Formato incorrecto del campo \"DNI\".");
      dni.focus();
      return (false);
  	}
  }
  return true;
}


function validarPositivo (campo, literal, navegar){
  var allValid = true;

  if (campo.value < 0)
    allValid=false;
  if (!allValid) {
    alert("Introduzca números positivos en el campo \""+ literal + "\"");
    if (navegar) {      
      campo.focus();
    }
    return false;
  }
  return true;
}


// Funcion que recibe un elemento y valida si su valor cumple el formato del NIF
function validarCampoNIF(nif) {	
  // Paso a mayusculas
  nif.value = nif.value.toUpperCase();    
  
  if (nif.value == "") {      
    mensajeFormatoNIF("Introduzca un valor para el campo \"NIF\"");
    nif.focus();
    return (false);
  }
    
  if (!(nif.value.length == 9)) {  
    mensajeFormatoNIF("Formato incorrecto del campo \"NIF\"");
    nif.focus();
    return (false);
  }

  var checkOK = "0123456789";
  var checkStrOK = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";  	
  var checkStr = nif.value;
  
  // Cojo el primer caracter
  var caracter = checkStr.charAt(0);
  var esNIF = false;
  	  	
  for (i = 0;  i < checkOK.length;  i++) {
    if (caracter == checkOK.charAt(i)) {
      esNIF = true;
      break;
    }
  }
    
  if (esNIF) {
    var allValid = false;
    caracter = checkStr.charAt(8);// cojo el último carácter.
    // El último carácter debe ser letra.
    for (i = 0;  i < checkStrOK.length;  i++) {
      if (caracter == checkStrOK.charAt(i)) {    	
        allValid = true;
        break;
      }
    }    	
    if (!allValid) {      
      mensajeFormatoNIF("Formato incorrecto del campo \"NIF\".");
      nif.focus();
      return (false);
    }


    // el resto de caracteres deben ser numeros.
    allValid = true;
    for (i = 0;  i < (checkStr.length-1);  i++) {// para cada caracter del nif QUITANDO LA LETRA FINAL.
      ch = checkStr.charAt(i);
      for (j = 0;  j < checkOK.length;  j++) {// miro si es numero
        if (ch == checkOK.charAt(j))
          break;
      }
      if (j == checkOK.length) {// si no es un numero j llega hasta el final
        allValid = false;
        break;
      }
    }
    if (!allValid) {    
      mensajeFormatoNIF("Formato incorrecto del campo \"NIF\".");
      nif.focus();
      return (false);
    }
  }
  else {
    var allValid = false;
    // El primer caracter es una letra. Ahora veo el ultimo
    var caracter = checkStr.charAt(8);
    for (i = 0;  i < checkStrOK.length;  i++) {
      if (caracter == checkStrOK.charAt(i)) {    	
        allValid = true;
        break;
      }
    }    	
    if (!allValid) {      
      mensajeFormatoNIF("Formato incorrecto del campo \"NIF\".");
      nif.focus();
      return (false);
    }
    	
    for (i = 1;  i < 8;  i++) {
      var caracter = checkStr.charAt(i);
      for (j = 0;  j < checkOK.length;  j++) {
        if (caracter == checkOK.charAt(j))
          break;
      }
      if (j == checkOK.length) {
        allValid = false;
        break;
      }
  	}
  	if (!allValid) {      
      	  mensajeFormatoNIF("Formato incorrecto del campo \"NIF\".");
          nif.focus();
          return (false);
  	}
  }
  return true;
}


// Funcion que saca un mensaje con el formato del campo NIF
void function mensajeFormatoNIF(x) {
  alert(x + "\nFormatos:\n"
    + "1. NIF: 8 dígitos (complete con 0's por la izquierda) + Carácter.\n"
    + "2. Tarjeta Residencia: Carácter + 7 dígitos (complete con 0's por la izquierda) + Carácter.");
}


// Devuelve una cadena sin los espacios del principio
function LTrim(s){
	var i=0;
	var j=0;
	
	// Busca el primer caracter de un espacio
	for (i=0; i<s.length; i++) {
		if(s.substring(i,i+1) != ' '){
			j=i;
			break;
	    }
	}

	if ( (j<i) && (j==0) ) {
		return "";
	}
	else {
		return s.substring(j, s.length + j);
	}
}

// Quita los espacios en blanco del final de la cadena
function RTrim(s){
	var j=0;
		
	// Busca el último caracter de un espacio
	for(var i=s.length-1; i>=0; i--) {
		if(s.substring(i,i+1) != ' '){
			j=i;
			break;
		}
	}

	return s.substring(0, j+1);
}

// Quita los espacios del principio y del final
function Trim(s){
	return LTrim(RTrim(s));
}

//Valida que los campos no esten vacios o con espacios en blanco unicamente.
function validaEspacios(valor, mensaje) {
	if (valor == ""){
		alert("Cumplimente el campo obligatorio " + mensaje + ".");
		return false;
	} else {
		var regExp = new RegExp("^\\s+$");
		if (regExp.test(valor)) {
			alert("Cumplimente correctamente el campo " + mensaje + ".");
			return false;
		}
	}
	return true;
}  

function soloNumeros(event) 
{ 
	var key = (document.all) ? event.keyCode : event.which;
			
	if (key < 48 || key > 57) 
	{			
		if(document.all)
			event.keyCode = 0;
		else 
		{
			event.preventDefault();
			event.stopPropagation();
		}	
	}
}
