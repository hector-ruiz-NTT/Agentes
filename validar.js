function esEmailValido(valor) {
	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(valor)) {
		//alert("La direcci�n de email " + valor + " es correcta.")
		return (true);
	} else {
		//alert("La direcci\xf3n de email es incorrecta.");
		return (false);
	}
}

function ComprobarDNI(){
	
		var i;
		var valorCampo = "";
		
		valorCampo = document.forms[0].dniTitular.value;
		var primeraLetra=valorCampo.charAt(0);
		if((primeraLetra=='X') ||(primeraLetra=='x')){
		    valorCampo=valorCampo.substring(1, valorCampo.length);
		}
		if((primeraLetra=='Y') ||(primeraLetra=='y')){
			return true;
		}
		var letraDni   = valorCampo.charAt(valorCampo.length-1); 
		var numeroDni;
		var letraCorrectaMayuscula;
		var letraCorrectaMinuscula;
		var minusculaMayuscula;

		for(i=0;i<valorCampo.length-1;++i){
			if (i == 0){
				numeroDni = valorCampo.charAt(i);
			}
			else{
				numeroDni = numeroDni + valorCampo.charAt(i);
			}
		}
		if ((letraDni.charCodeAt(0) >= '97') && (letraDni.charCodeAt(0) <= '122')){
			letraCorrectaMinuscula = DNIMinuscula(numeroDni);
			minusculaMayuscula = 'm';
		}
		if ((letraDni.charCodeAt(0) >= '65') && (letraDni.charCodeAt(0) <= '90')){
			letraCorrectaMayuscula = DNIMayuscula(numeroDni);
			minusculaMayuscula = 'M';			
		}
		if ((valorCampo.length > 9) || (valorCampo.length < 8)){
			alert('El D.N.I. introducido no es v�lido');
			return false;
		}
		else if(!comprobarLetra(letraDni)){
			alert('Introduzca la letra al final del NIF');
			return false;
		}
        valorCampo = valorCampo.substring(0, valorCampo.length-1) + DNIMayuscula(numeroDni);
      //	document.forms[0].dniTitular.value = valorCampo;
    	if (minusculaMayuscula == 'm'){
			if (letraCorrectaMinuscula != letraDni){
				alert('La letra del NIF introducida no era correcta.') ;
				return false;
			}
		}else	 if (minusculaMayuscula == 'M'){
			if (letraCorrectaMayuscula != letraDni){
				alert('La letra del NIF introducida no era correcta.');
				return false;						
			}		
		}

    
    return true;
}

function DNIMayuscula(n){
	var d =(n/23)
	d = Math.floor(d);
	var e=d*23
	var r=n-e
	
	if (r==0)
		return "T"
	if (r==1)
		return "R"
	if (r==2)
		return "W"
	if (r==3)
		return "A"
	if (r==4)
		return "G"
	if (r==5)
		return "M"
	if (r==6)
		return "Y"
	if (r==7)
		return "F"
	if (r==8)
		return "P"
	if (r==9)
		return "D"
	if (r==10)
		return "X"
	if (r==11)
		return "B"
	if (r==12)
		return "N"
	if (r==13)
		return "J"
	if (r==14)
		return "Z"
	if (r==15)
		return "S"
	if (r==16)
		return "Q"
	if (r==17)
		return "V"
	if (r==18)
		return "H"
	if (r==19)
		return "L"
	if (r==20)
		return "C"
	if (r==21)
		return "K"
	if (r==22)
		return "E"
}

///Calcula la letra del dni introducido por el usuario, 
///en el caso de que esta sea minuscula.

function DNIMinuscula(n){
	var d =(n/23)
	d = Math.floor(d);
	var e=d*23
	var r=n-e
	
	if (r==0)
		return "t"
	if (r==1)
		return "r"
	if (r==2)
		return "w"
	if (r==3)
		return "a"
	if (r==4)
		return "g"
	if (r==5)
		return "m"
	if (r==6)
		return "y"
	if (r==7)
		return "f"
	if (r==8)
		return "p"
	if (r==9)
		return "d"
	if (r==10)
		return "x"
	if (r==11)
		return "b"
	if (r==12)
		return "n"
	if (r==13)
		return "j"
	if (r==14)
		return "z"
	if (r==15)
		return "s"
	if (r==16)
		return "q"
	if (r==17)
		return "v"
	if (r==18)
		return "h"
	if (r==19)
		return "l"
	if (r==20)
		return "c"
	if (r==21)
		return "k"
	if (r==22)
		return "e"
}

//Comprueba si lo que ha introducido el usuario es una letra.
function comprobarLetra(letra){

	if ((letra.charCodeAt(0) >= '65') && (letra.charCodeAt(0) <= '90')){
		return true;
	}
	else if ((letra.charCodeAt(0) >= '97') && (letra.charCodeAt(0) <= '122')){
		return true;
	}
	else{
		return false;
	}
}

function ComprobarBanco()
{
var retorno;
var banco=document.forms[0].banco.value;
var sucu=document.forms[0].sucu.value;
var dc=document.forms[0].dc.value;
var cuenta=document.forms[0].nCuenta.value;

    if (   !(obtenerDigito("00" + banco + sucu) == parseInt(dc.charAt(0))) 
    	|| !(obtenerDigito(cuenta) == parseInt(dc.charAt(1)))){
		alert("Datos bancarios incorrectos");
		retorno=false;
	}else{
	  	retorno=true;
	}
	return retorno;
}
//this: ComprobarBanco
function obtenerDigito(valor)
{
var valores = new Array(1, 2, 4, 8, 5, 10, 9, 7, 3, 6);
var control = 0;
	for (i=0; i<=9; i++) {
		control += parseInt(valor.charAt(i)) * valores[i];
	}

	control = 11 - (control % 11);
	if (control == 11) control = 0;
	else if (control == 10) control = 1;
	return control;
}

function validaFecha()
				{
				var arg = arguments;
				var fecha = arg[0];
								
				

				   	mascara =/[0-9]{2}\/[0-9]{2}\/[0-9]{4}/;
					mascara2=/[0-9]{2}\/[0-9]{1}\/[0-9]{4}/;
					dia = fecha.charAt(0)+fecha.charAt(1);
					mes = fecha.charAt(3)+fecha.charAt(4);
					anno = fecha.charAt(6)+fecha.charAt(7)+fecha.charAt(8)+fecha.charAt(9);

					if(document.forms[0].fecha!='')
					{
						if(!mascara.test(fecha) && !mascara2.test(fecha))
						{
						 	alert('Valor no v�lido, introduzca un formato de fecha v�lido \'dd/mm/aaaa\'');
							return false;

						}
						else if(mes<01 || mes>12)
						{
							alert('El mes debe estar comprendido entre \'1\' y \'12\'');
							return false;
						}
						else if(dia<01 || dia>31)
						{
							if (mes == 01 || mes == 03 || mes == 05 || mes == 07 || mes == 8 || mes == 10 || mes == 12)
							{
								alert('El d�a debe estar comprendido entre \'1\' y \'31\'');
								return false;
							}
							else if (mes == 04 || mes == 06 || mes == 9 || mes == 11)
							{
								alert('El d�a debe estar comprendido entre \'1\' y \'30\'');
								return false;
							}
							else
							{
								alert('El d�a debe estar comprendido entre \'1\' y \'28\'');
								return false;
							}
						}
						else
						{
						  return true;
						}
					}
					else
					{
					    alert('Debe rellenar el campo para poder Grabar');

					}
				}
function validaFecha2()
				{
				var arg = arguments;
				var fecha = arg[0];
				
			   	mascara =/[0-9]{2}\/[0-9]{2}\/[0-9]{4}/;
				
				if(document.forms[0].fecha!='')
					{
						if(!mascara.test(fecha) && !mascara2.test(fecha))
						{
						 	alert('Valor no v�lido, introduzca un formato de fecha v�lido \'dd/mm/aaaa\'');
							return false;

						}
						else
						{
				
							
							var aux = fecha.split('/');
							
						
							dia = aux[0];
							mes = aux[1];
							anno = aux[2];
							
							var annoNum;
							var esBisiesto;
							
							annoNum = parseInt(anno, 10)
							
							esBisiesto =(((parseInt(annoNum%4, 10))==0 && (parseInt(annoNum%100, 10))!=0)||(parseInt(annoNum%400, 10))==0);
							
	
							if((parseInt(dia.length,10)!=2))
							{
							 	alert('Valor no v�lido, introduzca un formato de fecha v�lido \'dd/mm/aaaa\'');
								return false;
	
							}
							else if ((parseInt(mes.length,10)!=2) || (parseInt(anno.length, 10)!=4))
							{
							 	alert('Valor no v�lido, introduzca un formato de fecha v�lido \'dd/mm/aaaa\'');
								return false;
	
							}
							else if (esBisiesto && mes == '02' && dia>'29')
							{
								alert('El d�a debe estar comprendido entre \'1\' y \'29\'');
								return false;
							}
							else if (!esBisiesto && mes == '02' && dia>'28')
							{
								alert('El d�a debe estar comprendido entre \'1\' y \'28\'');
								return false;
							}
							else if (dia=='31' && (mes == '04' || mes == '06' || mes == '09' || mes == '11'))
							{
								//alert('chungui');
								alert('El d�a debe estar comprendido entre \'1\' y \'30\'');
								return false;
							}
							else if(mes<'01' || mes>'12')
							{
								alert('El mes debe estar comprendido entre \'1\' y \'12\'');
								return false;
							}
							else if(dia<'01' || dia>'31')
							{
								if (mes == '01' || mes == '03' || mes == '05' || mes == '07' || mes == '08' || mes == '10' || mes == '12')
								{
									alert('El d�a debe estar comprendido entre \'1\' y \'31\'');
									return false;
								}
								else if (mes == '04' || mes == '06' || mes == '09' || mes == '11')
								{
									alert('El d�a debe estar comprendido entre \'1\' y \'30\'');
									return false;
								}
								else if (esBisiesto)
								{
									alert('El d�a debe estar comprendido entre \'1\' y \'29\'');
									return false;
								}
								else
								{
									alert('El d�a debe estar comprendido entre \'1\' y \'28\'');
									return false;
								}
							}
							else
							{
							  return true;
							}
					}
					}
					else
					{
					    alert('Debe rellenar el campo para poder Grabar');

					}
				}
function formateaMesFecha()
				{
				var arg = arguments;
				var fecha = arg[0];
								
				

				   	var mascara =/[0-9]{2}\/[0-9]{2}\/[0-9]{4}/;
					var mascara2=/[0-9]{2}\/[0-9]{1}\/[0-9]{4}/;

					if(document.forms[0].fecha!='')
					{
						if(!mascara.test(fecha) && !mascara2.test(fecha))
						{
						 	alert('Valor no v�lido, introduzca un formato de fecha v�lido \'dd/mm/aaaa\'');
							return '';

						}
						else
						{	
				
							var aux = fecha.split('/');					
							
							dia = aux[0];
							mes = aux[1];
							anno = aux[2];
							
							var mes2;
							
			
								if(document.forms[0].fecha!='')
								{
									if(mes.length == 1)
									{
									 	mes2= ('0'+mes);
									 	return (dia+'/'+mes2+'/'+anno);
			
									}						
									else
									{
									  return fecha;
									}
								}	
								
						}
				}
				else
				{
				    alert('Debe rellenar el campo para poder Grabar');

				}
			}
function comprobarLongitud(){
	if (document.forms[0].observaciones.value.length > 70){
		alert("El texto introducido no debe exceder el tama�o de la caja");
		document.forms[0].observaciones.focus();
		document.forms[0].observaciones.select();
	}
}

function comprobarPrueba(){
 	if (formActasSesionDiariasPrimerPrueba1.prueba.value == ""){
 		alert("Antes de seleccionar un ejercicio debe seleccionar una prueba");
 	}
 	else{
		 generadorValores(10,formActasSesionDiariasPrimerPrueba1,'ejercicio','dEjercicio');
	}
}
function validarCamposComisionLCD3(){
	var arg = arguments;
	var nombreForm	= arg[0];
	
	if (nombreForm == formLCD3){
		if (nombreForm.observaciones.value == ''){
			alert('Debe introducir las observaciones para poder continuar');
			nombreForm.observaciones.focus();
		}
		else if ((nombreForm.localSesiones.value == ' ')||(nombreForm.localSesiones.value == '')){
			alert('Debe introducir un valor en el campo local de sesiones');
			nombreForm.localSesiones.focus();
		}
		else if (nombreForm.dia.value == ''){
			alert('Para poder continuar debe introducir el d�a');
			nombreForm.dia.focus();
		}
		else{
			nombreForm.submit();
		}
	}
}

function validarCamposComisionLCD4(){
	var arg = arguments;
	var nombreForm	= document.getElementById(arg[0]);

	if (nombreForm == document.getElementById('FORMPDFLC_D4')){
		if ((nombreForm.local.value == '')||(nombreForm.local.value == ' ')){
			alert('Debe introducir un valor en el campo local de sesiones');
			nombreForm.local.focus();
		}
		else if (nombreForm.fecha.value == ''){
			alert('Debe introducir la fecha para poder continuar');
			nombreForm.fecha.focus();
		}
		else{
			var fecha = nombreForm.fecha.value;
			if (deshaceFecha(fecha,'FORMPDFLC_D4','fecha')){
				nombreForm.submit();
			}		
		}
	}
}
function validarCamposComisionLCD5(){
	var arg = arguments;
	var nombreForm	= document.getElementById(arg[0]);

	if (nombreForm == document.getElementById('FORMPDFLC_D5')){
		if ((nombreForm.local.value == '')||(nombreForm.local.value == ' ')){
			alert('Por favor introduzca el local de sesiones');
			nombreForm.local.focus();
		}
		else if (nombreForm.fecha.value == ''){
			alert('Por favor introduzca la fecha');
			nombreForm.fecha.focus();
		}
		else{
			var fecha = FORMPDFLC_D5.fecha.value;
			if (deshaceFecha(fecha,'FORMPDFLC_D5','fecha')){
				nombreForm.submit();
			}		
		}		
	}
}

function validarCamposComisionLCD6(){
	var arg = arguments;
	var nombreForm	= document.getElementById(arg[0]);

	if (nombreForm == document.getElementById('FORMPDFLC_D6')){
		if ((nombreForm.localidad.value == '')||(nombreForm.localidad.value == ' ')){
			alert('Para continuar debe introducir la localidad');
			nombreForm.localidad.focus();
		}
		else if (nombreForm.fecha.value == ''){
			alert('Para poder continuar debe introducir la fecha');
			nombreForm.fecha.focus();
		}
		else{
			var fecha = document.getElementById('FORMPDFLC_D6').fecha.value;
			if (deshaceFecha(fecha,'FORMPDFLC_D6','fecha')){
				nombreForm.submit();
			}		
		}		
	}
}
function validarCamposComisionLCD7(){
	var arg = arguments;
	var nombreForm	= document.getElementById(arg[0]);

	if (nombreForm == document.getElementById('FORMPDFLC_D7')){
		if ((nombreForm.localidad.value == '')||(nombreForm.localidad.value == ' ')){
			alert('Para poder continuar debe introducir la localidad');
			nombreForm.localidad.focus();
		}
		else if (nombreForm.fecha.value == ''){
			alert('Para poder continuar debe introducir la fecha');
			nombreForm.fecha.focus();
		}
		else{
			var fecha = document.getElementById('FORMPDFLC_D7').fecha.value;
			if (deshaceFecha(fecha,'FORMPDFLC_D7','fecha')){
				nombreForm.submit();
			}		
		}		
	}
}

function nuevaVentana()
{
		var arg = arguments;
	var nombreForm	= arg[0];
	
  var cadenaCalendario = "/html/web/PDFLC_D1.icm"
  

  	
  
  w = open(cadenaCalendario ,
              "NuevaPagina",
              "status,Scrollbars=1,resizable=1,width=340,height=275,top=100,left=100");
             
    w.opener = self;
    w.focus()

}

//se le pasa el valor el nombre del formulario y del campo para que haga los select().
	function compruebaNif(v,form_name,camp_name)
		{
			var valor_devuelto="";
			//SE COMENTA LA LOGICA QUE HACE LA VALIDACI�N DEL NIF.DE MOMENTO.
					/*var nif = v;
					theForm = document.getElementById(form_name);
					if (nif.length != 9){
								alert('Ese NIF no es v�lido');
								theForm.elements[camp_name].select();
								valor_devuelto=false;
					}
					else if (!IsChar(nif.substring(8, 9))){
								alert('Ese NIF no es v�lido');
								theForm.elements[camp_name].select();
								valor_devuelto=false;
					}
					else
					{
						var ControlValue = 0
						var NIFCharIndex = 0
		
						var NIFChars = new Array('T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E');
						var NIFNumber = nif.substring(0, 8)
						var NIFChar = nif.substring(8, 9) 
						NIFChar = NIFChar.toUpperCase()
		
						ControlValue = NIFNumber / NIFChars.length
						ControlValue = Math.floor(ControlValue);
						ControlValue = ControlValue * NIFChars.length
						NIFCharIndex = NIFNumber - ControlValue
						if (NIFChar == NIFChars[NIFCharIndex])
							valor_devuelto=true;
						else{
								alert('La letra del NIF no es correcta');
								theForm.elements[camp_name].select();
								valor_devuelto=false;
		
						}
					}*/
	 valor_devuelto='true';
	 return valor_devuelto;
	}

//se le pasa como parametros el valor,el nombre del fomulario y del campo
	function validaHora(v,form_name,camp_name)
	{
		var hora=v;
		var valor_devuelto=true;
		theForm = document.getElementById(form_name);
		a = hora.charAt(0);
		b = hora.charAt(1);
		c = hora.charAt(2);
		d = hora.charAt(3);
		e = hora.charAt(4);
		if (hora.length!=5 || c!=':') {
			alert("Por favor introduzca un formato correcto para la hora (HH:MM)");
			
		//	theForm.elements[camp_name].select();
			valor_devuelto=false;
			return valor_devuelto;
		}
		else{
			if (a>2) {
				alert("La hora introducida no es v�lida");
				alert("Por favor introduzca un formato correcto para la hora (HH:MM)");				
			//	theForm.elements[camp_name].select();
				valor_devuelto=false;
				return valor_devuelto;
			}
			if (a==2 && b>3) {
				alert("La hora introducida no es v�lida");
				alert("Por favor introduzca un formato correcto para la hora (HH:MM)");				
			//	theForm.elements[camp_name].select();
				valor_devuelto=false;
				return valor_devuelto;
			}
			if (d>5) {
				alert("Los minutos introducidos no son v�lidos");
				alert("Por favor introduzca un formato correcto para la hora (HH:MM)");				
			//	theForm.elements[camp_name].select();					
				valor_devuelto=false;
				return valor_devuelto;
			}
			if (a!='0' && a!='1' && a!='2' && a!='3' && a!='4' && a!='5' && a!='6' && a!='7' && a!='8' && a!='9') {
				alert("La hora introducida no es v�lida");
				alert("Por favor introduzca un formato correcto para la hora (HH:MM)");				
			//	theForm.elements[camp_name].select();					
				valor_devuelto=false;
				return valor_devuelto;
			}
			if (b!='0' && b!='1' && b!='2' && b!='3' && b!='4' && b!='5' && b!='6' && b!='7' && b!='8' && b!='9') {
				alert("La hora introducida no es v�lida");
				alert("Por favor introduzca un formato correcto para la hora (HH:MM)");				
			//	theForm.elements[camp_name].select();					
				valor_devuelto=false;
				return valor_devuelto;
			}
			if (d!='0' && d!='1' && d!='2' && d!='3' && d!='4' && d!='5' && d!='6' && d!='7' && d!='8' && d!='9') {
				alert("Los minutos introducidos no son v�lidos");
				alert("Por favor introduzca un formato correcto para la hora (HH:MM)");				
			//	theForm.elements[camp_name].select();					
				valor_devuelto=false;
				return valor_devuelto;
			}
			if (e!='0' && e!='1' && e!='2' && e!='3' && e!='4' && e!='5' && e!='6' && e!='7' && e!='8' && e!='9') {
				alert("Los minutos introducidos no son v�lidos");
				alert("Por favor introduzca un formato correcto para la hora (HH:MM)");				
			//	theForm.elements[camp_name].select();					
				valor_devuelto=false;
				return valor_devuelto;
			}
		}
		return valor_devuelto;
	}
	
		function IsChar(YourChar)
		{
			var Template = /^[a-z]$/i //Formato de letra
			return (Template.test(YourChar)) ? 1 : 0 //Compara \"YourChar\" con el formato \"Template\" y si coincidevuelve verdadero si no devuelve falso
		}
		

function  deshaceFecha(v,form_name,camp_name){
	
				   	var mascara =/[0-9]{2}\/[0-9]{2}\/[0-9]{4}/;
					var mascara2=/[0-9]{2}\/[0-9]{1}\/[0-9]{4}/;

					var fecha = v;
					return (mascara.test(fecha) || mascara2.test(fecha))
}



function Valida_Fecha_Partida(dia, mes, anno){
	// Esta funcion chequea si la fecha tiene un formato correcto
	// alert("Funcion de validacion de la fecha (tres parametros):"+dia+"/"+mes+"/"+anno);

	var valido=true;
	var valor_devuelto=true;
	sError="";

	for(i=0;i<dia.length;i++){
		var ch=dia.substring(i,i+1);
		if(!(ch >= "0" && ch <= "9")){
			valido=false;
		}
		if(!valido){
			valor_devuelto=false;
		}
	}

	for(i=0;i<mes.length;i++){
		var ch=mes.substring(i,i+1);
		if(!(ch >= "0" && ch <= "9")){
			valido=false;
		}
		if(!valido){
			valor_devuelto=false;
		}
	}

	for(i=0;i<anno.length;i++){
		var ch=anno.substring(i,i+1);
		if(!(ch >= "0" && ch <= "9")){
			valido=false;
		}
		if(!valido){
			valor_devuelto=false;
		}
	}

	// Hasta aqui parece que todo va bien en cuanto a escritura.
	// Ahora tenemos que comprobar la fecha

	var arrayDias="312831303130313130313031";
	var arraydia=arrayDias.substring((mes-1)*2,mes*2);
	var limite=arraydia*1;
	var bisiesto=0;

	mes=mes*1;
	dia=dia*1;
	anno=anno*1;

	if(((anno%4) == 0) && ((anno%100) != 0)){
		bisiesto=1;
	}
	if(anno==2000) bisiesto=1;
	if (mes==2)
		limite=limite+bisiesto;

	if((mes < 1) || (mes > 12)){
		valor_devuelto=false;
	}
	if(mes < 10){
		mes="0"+mes;
	}

	if((anno < 1900) || (anno > 9999)){
		valor_devuelto=false;
	}

	if((dia < 1) || (dia > limite)){
		valor_devuelto=false;
	}
	if(dia < 10){
		dia="0"+dia;
    }
	return valor_devuelto;
}


function protegeLectura(n,form_name,camp_name){
theForm = document.getElementById(form_name);
				if(n=='0'){
					theForm.elements[camp_name].readOnly='readOnly';
					theForm.elements[camp_name].style.background='#f0f8ff';					

				}else{
					theForm.elements[camp_name].readOnly='';
					theForm.elements[camp_name].style.background='white';
					theForm.elements[camp_name].focus();

				}			
}

