/*********************************************************************************************************
Funcion que muestra valores en funci?n de condiciones 
**********************************************************************************************************/
function generadorValores() {
		var arg = arguments;
		var cont = 1;
		var tabla = arg[0]; //se recoge el numero de tabla
		var nombreForm = arg[1];
		var theForm = document.forms[0];
		var convocatoria = arg[4];
		for(i=0;i<document.forms.length; i++){
			for(j=0;j<document.forms[i].elements.length; j++){
				if (document.forms[i].elements[j].name == arg[1]){
					theForm = document.forms[i];
					nombreForm = document.forms[i].name;
				}
			}
		}
		
		var devuelve=""; //Genera una cadena con los par?metros que hay que devolver
		var window;
		
		for (; cont<arg.length; cont++) {
			for (i=0; i < theForm.elements.length;i++) {
				if (theForm.elements[i].name==arg[cont]) {
					devuelve += i + ",";
				}
			}
		}
		
		devuelve = devuelve.substring(0, devuelve.length - 1);
		if((tabla==1) || (tabla==15) //|| (tabla==16)
				|| (tabla==20) || (tabla==49)){ 
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve);
		
		}		  
		if(tabla==2) {
			var cadenaLlamada = "AccionLista.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve);
		}
		if(tabla==3){
			condicion = "CD_CONVOCATORIA = '" + 
			theForm.convocatoria.value + "'";
			condicion2= " AND NM_SECUENCIAL_ESTRU ='" +
			theForm.secuencial.value + "'";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2);  
		
		}
		
		if(tabla==4){
			condicion = "CD_CONVOCATORIA = '" + 
			formCabecera.convocatoria.value + "'";
			condicion2= " AND NM_SECUENCIAL_ESTRU ='" +
			formCabecera.secuencial.value + "'";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2);  
		
		
		}
		
		if(tabla==5){
		
			condicion = "CD_CONVOCATORIA = '" + 
			theForm.convocatoria.value + "'";
			condicion2 = " AND CD_ESPECIALIDAD ='" +
			theForm.especialidad.value + "'";
			condicion3 = " AND CD_CUERPO ='" +
			theForm.cuerpo.value + "'";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2) + escape(condicion3);  
		}
		if(tabla==6){
			condicion = "AM.CD_CONVOCATORIA = '" + 
			theForm.convocatoria.value + "'";
			condicion2 = " AND AM.CD_ESPECIALIDAD ='" +
			theForm.especialidad.value + "'";
			condicion3 = " AND AM.CD_CUERPO ='" +
			theForm.cuerpo.value + "'";
			condicion4 = " AND AM.CD_TIPO_PUESTO='VS' ";
			condicion5 = " AND AM.NM_TRIBUNAL='"+theForm.tribunal.value+"'";
			condicion6 = " AND AM.TP_ORGANO='01' ";
			condicion7 = " AND AM.IT_ASISTENCIA='N' ";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2) + escape(condicion3)+escape(condicion4)+escape(condicion5)+escape(condicion6)+escape(condicion7);  
		}
		if(tabla==12){
			condicion = "AM.CD_CONVOCATORIA = '" + 
			theForm.convocatoria.value + "'";
			condicion2 = " AND AM.CD_ESPECIALIDAD ='" +
			theForm.especialidad.value + "'";
			condicion3 = " AND AM.CD_CUERPO ='" +
			theForm.cuerpo.value + "'";
			condicion4 = " AND CD_TIPO_PUESTO='VT'";
			condicion5 = " AND AM.NM_TRIBUNAL='"+theForm.tribunal.value+"'";
			condicion6 = " AND AM.TP_ORGANO='01' ";
			condicion7 = " AND AM.IT_ASISTENCIA='S' ";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2) + escape(condicion3)+escape(condicion4)+escape(condicion5)+escape(condicion6)+escape(condicion7);  
		}
		
		if(tabla==7){
		
			condicion = "CD_CONVOCATORIA = '" + 
			theForm.convocatoria.value + "'";
			condicion2 = " AND CD_ESPECIALIDAD ='" +
			theForm.especialidad.value + "'";
			condicion3 = " AND CD_CUERPO ='" +
			theForm.cuerpo.value + "'";
			condicion4 = " AND IT_TIPO_PUESTO='VT'";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2) + escape(condicion3)+ escape(condicion4);  
		}
		if(tabla==8){
			condicion = "B.CD_CONVOCATORIA = '" + 
			theForm.convocatoria.value + "'";
			condicion2 = " AND B.CD_ESPECIALIDAD ='" + 
			theForm.especialidad.value + "'";
			condicion3 = " AND B.CD_CUERPO ='" +
			theForm.cuerpo.value + "'";
			condicion4 = " AND B.CD_TIPO_ACCESO ='" +
			theForm.acceso.value + "'";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2)  + escape(condicion3) + escape(condicion4);  
		
		}	
		if (tabla == 9){
			if(theForm.nif.value.length>0){
				condicion = "B.DNI like'%" + 
				theForm.nif.value + "%'" ;
				condicion2="AND B.CD_CONVOCATORIA='" +
				theForm.convocatoria.value +"'";
				condicion3 = " AND B.CD_ESPECIALIDAD ='" + 
				theForm.especialidad.value + "'";
				condicion4 = " AND B.CD_CUERPO ='" +
				theForm.cuerpo.value + "'";
				condicion5 = " AND C.CD_TIPO_ACCESO ='" +
				theForm.acceso.value + "'";	          
				var cadenaLlamada = "ListaValoresJSP.icm" +
				"?NOMBRE=" + escape(tabla) +
				"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) +escape(condicion2) +escape(condicion3)+escape(condicion4)+escape(condicion5);
			}else{
				condicion="B.CD_CONVOCATORIA='" +
				theForm.convocatoria.value +"'";
				condicion2="AND B.CD_CONVOCATORIA='" +
				theForm.convocatoria.value +"'";
				condicion3 = " AND B.CD_ESPECIALIDAD ='" + 
				theForm.especialidad.value + "'";
				condicion4 = " AND B.CD_CUERPO ='" +
				theForm.cuerpo.value + "'";
				condicion5 = " AND C.CD_TIPO_ACCESO ='" +
				theForm.acceso.value + "'";
				var cadenaLlamada = "ListaValoresJSP.icm" +
				"?NOMBRE=" + escape(tabla) +
				"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) +escape(condicion2)+escape(condicion3)+escape(condicion4)+escape(condicion5);
			}   		        
		}
		
		if (tabla == 10){
			condicion =  " B.CD_ORDEN_PRUEBA ='" +
			theForm.prueba.value + "'";
			condicion2 = " AND C.CD_CONVOCATORIA ='" + 
			theForm.convocatoria.value + "'";
			condicion3 = " AND C.CD_ESPECIALIDAD ='" + 
			theForm.especialidad.value + "'";   	        
			condicion4 = " AND C.CD_CUERPO ='" +
			theForm.cuerpo.value + "'";
			condicion5 = " AND C.CD_TIPO_ACCESO ='" +
			theForm.acceso.value + "'";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2) + escape(condicion3) + escape(condicion4) + escape(condicion5);         	 		
		}
		if(tabla==11){
		
			condicion = "A.CD_CONVOCATORIA = '" + 
			theForm.convocatoria.value + "'";
			condicion2 = " AND A.CD_ESPECIALIDAD ='" +
			theForm.especialidad.value + "'";
			condicion3 = " AND A.CD_CUERPO ='" +
			theForm.cuerpo.value + "'";
		
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2) + escape(condicion3);  
		
		}		  
		if (tabla ==13){
		
			if(theForm.nif.value.length>0){
				condicion = "B.DNI like'%" + 
				theForm.nif.value + "%'" ;
				condicion2="AND B.CD_CONVOCATORIA='" +
				theForm.convocatoria.value +"'";
				condicion3="AND B.NM_TRIBUNAL="  +
				theForm.tribunal.value  ;
		
				var cadenaLlamada = "ListaValoresJSP.icm" +
				"?NOMBRE=" + escape(tabla) +
				"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) +escape(condicion2) +escape(condicion3);;
			}else{
				condicion="B.CD_CONVOCATORIA='" +
				theForm.convocatoria.value +"'";
				condicion2="AND B.NM_TRIBUNAL="  +
				theForm.tribunal.value  ;
				var cadenaLlamada = "ListaValoresJSP.icm" +
				"?NOMBRE=" + escape(tabla) +
				"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) +escape(condicion2);
			}   		   
		
		} 
		if (tabla ==14){
		
			if(theForm.nif.value.length>0){
				condicion = "B.DNI like'%" + 
				theForm.nif.value + "%'" ;
				condicion2="AND B.CD_CONVOCATORIA='" +
				theForm.convocatoria.value +"'";
				condicion3="AND B.NM_TRIBUNAL="  +
				theForm.tribunal.value  ;
				var cadenaLlamada = "ListaValoresJSP.icm" +
				"?NOMBRE=" + escape(tabla) +
				"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) +escape(condicion2) +escape(condicion3);;
			}else{
				condicion="B.CD_CONVOCATORIA='" +
				theForm.convocatoria.value +"'";
				condicion2="AND B.NM_TRIBUNAL="  +
				theForm.tribunal.value  ;
				var cadenaLlamada = "ListaValoresJSP.icm" +
				"?NOMBRE=" + escape(tabla) +
				"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) +escape(condicion2);
			}   		   
		
		} 
		
		// <%-- E_ASB48 417873 27/03/2014 INICIO --%>
		if(tabla==16) {
			if (convocatoria == undefined) {
				convocatoria = theForm.cdConvoca.value;
			}
			condicion = "GDG.CD_CONVOCATORIA = '" +
			convocatoria + "'";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion);
		}
		// <%-- E_ASB48 417873 27/03/2014 FIN --%>

		if(tabla==17){
			if (convocatoria == undefined) {
				convocatoria = theForm.cdConvoca.value;
			}
			condicion = "AECE.CD_CUERPO = '" +
			theForm.cuerpo.value + "'";
			condicion2 = " AND AECE.CD_CONVOCATORIA= '" +
			convocatoria + "'";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2);
		}
		if(tabla==172){
			tabla=17;
			condicion = "AECE.CD_CUERPO = '" +
			arg[3] + "'";
			condicion2 = " AND AECE.CD_CONVOCATORIA= '" +
			convocatoria + "'";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2);
		}
		if(tabla==173){
			tabla=17;
			condicion = "AECE.CD_CUERPO = '" +
			theForm.txtCuerpo2.value + "'";
			condicion2 = " AND AECE.CD_CONVOCATORIA = '" +
			theForm.txtConvocatoria2.value + "'";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2);
		}
		if(tabla==18){
			condicion = "CD_CONVOCATORIA = '" + 
			theForm.convocatoria.value + "'";
			condicion2 = " AND CD_ESPECIALIDAD ='" +
			theForm.especialidad.value + "'";
			condicion3 = " AND CD_CUERPO ='" +
			theForm.cuerpo.value + "'";
		
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2) + escape(condicion3);  
		
		}	
		if(tabla==19){
		
			condicion =  " CD_CONVOCATORIA = '" + 
			theForm.convocatoria.value + "'";
			condicion2 = " AND CD_ESPECIALIDAD ='" +
			theForm.especialidad.value + "'";
			condicion3 = " AND CD_CUERPO ='" +
			theForm.cuerpo.value + "'"; 
			condicion4 = " AND TP_ORGANO ='" +
			theForm.organo.value + "'";
		
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2) + escape(condicion3) + escape(condicion4);  
		
		}
		if(tabla==21){  
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve);
		}		     	   
		if(tabla==22){  
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve);
		}	
		if(tabla==23){
			// condicion = "CDPROV = 28";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve); 
			// + "&WHERE=" + escape(condicion);  
		
		}
		if((tabla==24) || (tabla==44)){
		
			condicion =  " DOC_IDENTIFICATIVO = '" +  theForm.dniTitular.value + "'";       
			var cadenaLlamada = "ListaValoresJSP.icm" + "?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve);  
			if(theForm.dniTitular.value.length>0){
		
				var cadenaLlamada =cadenaLlamada + "&WHERE=" + escape(condicion); 
		
			}
		}
		if(tabla==25){
		
			condicion =  " CDBANC = '" +  theForm.banco.value + "'";   
		
			var cadenaLlamada = "ListaValoresJSP.icm" + "?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve);  
			if(theForm.banco.value.length>0){
		
				var cadenaLlamada =cadenaLlamada + "&WHERE=" + escape(condicion); 
		
			}
		
		}
		if(tabla==26){
			if(theForm.banco.value>0){
		
				condicion =  " CDBANC = '" +  theForm.banco.value + "'";       
				//   condicion1 =  " AND CDSUCU = '" +  theForm.dat_sucu_1.value + "'";
				var cadenaLlamada = "ListaValoresJSP.icm" +
				"?NOMBRE=" + escape(tabla) +
				"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion);  
		
				//  if(theForm.dat_sucu_1.value.length>0){
				//    	var cadenaLlamada =cadenaLlamada + escape(condicion); 
				//  }			  
			}else{
		
				alert('Debe Seleccionarse Primero un Banco.');
		
				return ;
		
			}
		}	     	   
		if(tabla==27){
		
			condicion =  " NM_SESIONES_ORDI IS NULL ";
		
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion);  
		
		}
		if(tabla==28){
		
		
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve)+ "&WHERE=";  
		
		}
		if(tabla==29){
		
		
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve)+ "&WHERE=";  
		
		}
		if(tabla==30){
		
		
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE="; 
		
		}
		if(tabla==31){
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE="; 
		
		}
		if( (tabla==32) || (tabla==35) || (tabla==351) ){ 
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE=" + escape(devuelve);
		}	
		if(tabla==36){
			condicion = "AM.CD_CONVOCATORIA = '" + 
			theForm.convocatoria.value + "'";
			condicion2 = " AND AM.CD_ESPECIALIDAD ='" +
			theForm.especialidad.value + "'";
			condicion3 = " AND AM.CD_CUERPO ='" +
			theForm.cuerpo.value + "'";
			condicion4 = " AND AM.NM_TRIBUNAL='"+theForm.tribunal.value+"'";
			condicion5 = " AND AM.TP_ORGANO='01' ";
			condicion6 = " AND (AM.IT_ASISTENCIA='S' OR (AM.IT_ASISTENCIA <> 'S' AND AM.IT_ASISTENCIA_ANTERIOR = 'S' AND AM.FC_SUSTITUCION IS NOT NULL)) ";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE=" + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2) + escape(condicion3)+escape(condicion4)+escape(condicion5)+escape(condicion6); 
		}
		
		if(tabla==361){
			condicion = "AM.CD_CONVOCATORIA = '" + 
			theForm.convocatoria.value + "'";
			condicion2 = " AND AM.CD_ESPECIALIDAD ='" +
			theForm.especialidad.value + "'";
			condicion3 = " AND AM.CD_CUERPO ='" +
			theForm.cuerpo.value + "'";
			
			//Modificaci?n Andres Acedo 12/05/2008 (everis)
			if(theForm.organo.value == '02'){
				condicion4 = " AND AM.NM_TRIBUNAL='1'";
			}
			else {
				condicion4 = " AND AM.NM_TRIBUNAL='"+theForm.tribunal.value+"'";
			}
		    //Fin Modificacin Andres Acedo 12/05/2008 (everis)
		    
			condicion5 = " AND AM.TP_ORGANO='"+theForm.organo.value+"'";
			condicion6 = " AND (AM.IT_ASISTENCIA='S' OR (AM.IT_ASISTENCIA <> 'S' AND AM.IT_ASISTENCIA_ANTERIOR = 'S' AND AM.FC_SUSTITUCION IS NOT NULL)) ";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE=" + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2) + escape(condicion3)+escape(condicion4)+escape(condicion5)+escape(condicion6); 
		}
		
		if(tabla==37){
			condicion = "AM.CD_CONVOCATORIA = '" + 
			theForm.convocatoria.value + "'";
			condicion2 = " AND AM.CD_ESPECIALIDAD ='" +
			theForm.especialidad.value + "'";
			condicion3 = " AND AM.CD_CUERPO ='" +
			theForm.cuerpo.value + "'";
			condicion4 = " AND AM.CD_TIPO_PUESTO='CO' ";
			condicion5 = " AND AM.NM_TRIBUNAL='" +
			theForm.tribunal.value + "' ";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE=" + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2) + escape(condicion3)+escape(condicion4)+escape(condicion5); 
		}
		
		if ((tabla ==38)||(tabla ==41) ||(tabla ==39)
				){
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve);
		}
		
		if(tabla==40){
			condicion = "AM.CD_CONVOCATORIA = '" + 
			theForm.convocatoria.value + "'";
			condicion2 = " AND AM.CD_ESPECIALIDAD ='" +
			theForm.especialidad.value + "'";
			condicion3 = " AND AM.CD_CUERPO ='" +
			theForm.cuerpo.value + "'";
			condicion4 = " AND AM.NM_TRIBUNAL='"+theForm.tribunal.value+"'";
			condicion5 = " AND AM.TP_ORGANO='01' ";
			condicion6 = " AND AM.IT_ASISTENCIA='S' ";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE=" + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2) + escape(condicion3)+escape(condicion4)+escape(condicion5)+escape(condicion6); 
		}
		if(tabla==42){
			condicion = "CD_CUERPO = '" +
			theForm.cuerpo.value + "'";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion);
		}  
		
		// <%-- E_ASB48 417873 27/03/2014 INICIO --%>
		if(tabla==43) {
			condicion = "B.CD_CONVOCATORIA = '" + theForm.cdConvoca.value + "'";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion);
		}
		// <%-- E_ASB48 417873 27/03/2014 FIN --%>

		if(tabla==46){
			condicion = "A.CD_CONVOCATORIA = '" + 
			theForm.convocatoria.value + "'";
			condicion2 = " AND A.CD_ESPECIALIDAD ='" +
			theForm.especialidad.value + "'";
			condicion3 = " AND A.CD_CUERPO ='" +
			theForm.cuerpo.value + "'";
			condicion4 = " AND A.TP_ORGANO='01'";
			condicion5 = " AND A.DOC_IDENTIFICATIVO = B.DOC_IDENTIFICATIVO";
			condicion6 = " AND A.CD_CONVOCATORIA = U.CD_CONVOCATORIA";
			condicion7 = " AND A.CD_CUERPO = U.CD_CUERPO";
			condicion8 = " AND A.CD_ESPECIALIDAD = U.CD_ESPECIALIDAD";
			condicion9 = " AND A.TP_ORGANO = U.TP_ORGANO";
			condicion10 = " AND A.NM_TRIBUNAL = U.NM_TRIBUNAL";
			condicion11 = " AND U.FC_COMPOSICION IS NOT NULL ";
			condicion12 = " AND A.CD_TIPO_PUESTO IN ('PT','PS')";
			condicion13 = " AND A.IT_ASISTENCIA = 'S'";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2) + escape(condicion3) + escape(condicion4) + escape(condicion5) + escape(condicion6) + escape(condicion7) + escape(condicion8) + escape(condicion9) + escape(condicion10) + escape(condicion11) + escape(condicion12) + escape(condicion13);
		} 
		
		if(tabla==47){
			condicion = "A.CD_CONVOCATORIA = '" + 
			theForm.convocatoria.value + "'";
			condicion2 = " AND A.CD_ESPECIALIDAD ='" +
			theForm.especialidad.value + "'";
			condicion3 = " AND A.CD_CUERPO ='" +
			theForm.cuerpo.value + "'";
			condicion4 = " AND A.TP_ORGANO='01'";
			condicion5 = " AND A.DOC_IDENTIFICATIVO = B.DOC_IDENTIFICATIVO";
			condicion6 = " AND A.CD_CONVOCATORIA = U.CD_CONVOCATORIA";
			condicion7 = " AND A.CD_CUERPO = U.CD_CUERPO";
			condicion8 = " AND A.CD_ESPECIALIDAD = U.CD_ESPECIALIDAD";
			condicion9 = " AND A.TP_ORGANO = U.TP_ORGANO";
			condicion10 = " AND A.NM_TRIBUNAL = U.NM_TRIBUNAL";
			condicion11 = " AND U.FC_COMPOSICION IS NOT NULL ";
			condicion12 = " AND A.CD_TIPO_PUESTO IN ('PT','VT','PS','VS')";
			condicion13 = " AND A.IT_ASISTENCIA = 'S'";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2) + escape(condicion3) + escape(condicion4) + escape(condicion5) + escape(condicion6) + escape(condicion7) + escape(condicion8) + escape(condicion9) + escape(condicion10) + escape(condicion11) + escape(condicion12) + escape(condicion13);
		}
		
		if(tabla==48){
			condicion = "AC.CD_CUERPO <> DECODE ((SELECT IT_GRUPO TG FROM AGTO_GC_DATOS_GENERALES AGDG WHERE CD_CONVOCATORIA = '" + convocatoria + "'), 'S', '0597', 'AC.CD_CUERPO'||'A')";
			condicion2 = " AND AC.CD_CUERPO = DECODE ((SELECT IT_GRUPO TG FROM AGTO_GC_DATOS_GENERALES AGDG WHERE CD_CONVOCATORIA = '" + convocatoria + "'), 'P', '0597', AC.CD_CUERPO) ";
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2);
		}
		
		if(tabla==50){
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE="  + escape(devuelve) + "&WHERE=" ;
		}
		
		if(tabla==51 || tabla==52){
			condicion = "MT.CD_CONVOCATORIA = '" + 
			theForm.convocatoria.value + "'";
			condicion2 = " AND MT.CD_ESPECIALIDAD ='" +
			theForm.especialidad.value + "'";
			condicion3 = " AND MT.CD_CUERPO ='" +
			theForm.cuerpo.value + "'";
			condicion4 = " AND MT.NM_TRIBUNAL='"+
			theForm.tribunal.value+"'";
		    
			var cadenaLlamada = "ListaValoresJSP.icm" +
			"?NOMBRE=" + escape(tabla) +
			"&DEVUELVE=" + escape(devuelve) + "&WHERE=" + escape(condicion) + escape(condicion2) + escape(condicion3)+escape(condicion4); 
		}
		
		
		if (nombreForm != ''){
			cadenaLlamada += "&NOMBRE_FORMULARIO="+nombreForm;
		
		}
		window = open( 	cadenaLlamada,
				"NuevaPagina",
		"Scrollbars=1, resizable=1, width=800, height=600");
}


		/*********************************************************************************************************
		Funciones pertenecientes al escenario L?mite de Sesiones Por Especialidad y Rango de Aspirantes 
													INICIO
		**********************************************************************************************************/


		//LLAMADAS: ModiEspeAspi, AltaEspeAspi
		function ValidaCuerpoYEspe(){
		
		var devuelve=true;
		var incorrecto=false;
		var min="";
		var max="";
		var cont=0;
		var cont2=0;
		var rompe=false;
		arr_rangos_fila= new Array();
		numero=document.forms[0].length;
		var regCuerpo =  new RegExp("^cuerpo");
		var regDescuerpo =  new RegExp("^descripcion_cuerpo");
		var regCodEspe =  new RegExp("^cod_espe");
		var regDesCuerpo =  new RegExp("^descripcion_espe");
		var regNumMin =  new RegExp("^num_min");
		var regNumMax =  new RegExp("^num_max");
		var regNmSesion =  new RegExp("^num_sesion");
		for(i=0;i<numero;i++){
			nombre=document.forms[0].elements[i].name;
			valor=document.forms[0].elements[i].value;
			if(nombre.search(regCuerpo)==0){
				if(valor==' '){incorrecto=true;}
			}else if(nombre.search(regDescuerpo)==0){
				if(valor==' '){incorrecto=true;}			    
			}else if(nombre.search(regCodEspe)==0){
				if(valor==' '){incorrecto=true;}			    
			}else if(nombre.search(regDesCuerpo)==0){
				if(valor==' '){incorrecto=true;}
			}else if(nombre.search(regNumMin)==0){
				if((valor=='0')||(isNaN(valor))){incorrecto=true;}
				if((cont>1)||(cont==0)){
					if(cont>1){
						cont=0;
						//alert('INtroduco el array elementos '+arr_elementos+' en el array Madre-arr_rangos_fila');
						arr_rangos_fila[cont2]=arr_elementos;
						cont2=cont2+1;	
					}
		
					arr_elementos= new Array(2);
				}
				min=valor;
		
			}else if(nombre.search(regNumMax)==0){
				if((valor=='0')||(isNaN(valor))){incorrecto=true;}
				max=valor;
		
			}else if(nombre.search(regNmSesion)==0){
				if((valor=='0')||(isNaN(valor))){incorrecto=true;}
			}
			if(incorrecto==true){
				mensaje="";
				if(valor=='0'){
					mensaje="Ning?n L?mite puede ser igual a cero.\nGracias.";
				}else if(isNaN(valor)){
					mensaje="Ha Introducido una Letra en Lugar de un Rango.\nGracias.";
				}else if((valor=='')||(valor==' ')){
					mensaje="No Puede Dejar La siguiente Casilla a Blanco.\nGracias.";
				}
				document.forms[0].elements[i].focus();
				document.forms[0].elements[i].select();
				devuelve=false;
				break;
		
			}else {
		
				if((nombre.search(regNumMin)==0)||(nombre.search(regNumMax)==0)){
					if(valor!=''){
						//alert('guardo el numero ->'+valor+' en el array');
						arr_elementos[cont]=parseInt(valor);
						cont=cont+1;
					}
				}
		
			}
		
		}
		var vueltas=arr_rangos_fila.length;
		
		
		
		for(o=0;o<vueltas;o++){
			//alert('valores->0-'+o+'**'+arr_rangos_fila[o][0]+'/'+arr_rangos_fila[o][1]);
			min_n=parseInt(arr_rangos_fila[o][0]);
			max_n=parseInt(arr_rangos_fila[o][1]);
			if((min_n==' ')||(min_n=='')||(isNaN(min_n))){break;};
			for(r=0;r<vueltas;r++){
				//cojo un elemento y veo se esta dentro de los rango, en cuanto sea mayor al min o menor 
				//al max ya se solapan
				if(devuelve==true){
					for(m=0;m<2;m++){
						aux_n=parseInt(arr_rangos_fila[r][m]);
						if((aux_n==' ')||(aux_n=='')||(isNaN(aux_n))){break;};
						//alert('min_n->'+min_n+'/max_n->'+max_n+'/Auxiliar a comparar->'+aux_n);
						if((min_n<aux_n)&&(max_n>aux_n)){
							alert('No Pueden Existir Rangos Solapados. El valor '+aux_n+' se Solapa con el Rango '+min_n+'-'+max_n+'.\nGracias.');
							devuelve=false;
							break;						
						}
						//alert('DEVUELVE ES IGUAL A->'+devuelve);
					}
				}else{
					//alert('SALGO ROMPIENDO EL IF PORQUE DEVUELVE ES IGUAL A->'+devuelve);
					devuelve=false;
					break;
				}					
			}
		}
		//alert('retorno el valor de devuelve->'+devuelve);
		return devuelve;
		
		}
		
		//editAspiranteEspe.jsp(
		function BajaEspeAspi(num_vueltas){
		
		var numero=document.forms[0].length;
		var reg =  new RegExp("^fila_rango");
		var flag=false;	
		filaespe=document.AltaBajaModiLimiteSesionesXEspeAspiForm.radio_espe.checked;
		if(filaespe==true){
		
			flag=confirm('Al Seleccionar la Especialidad se Eliminar? Esta y Sus L?mites Correspondientes.\n?Desea Continuar?');
			document.AltaBajaModiLimiteSesionesXEspeAspiForm.borrado_total.value="S";
		
		}else{
			for(t=0;t<numero;t++){
				nombre=document.forms[0].elements[t].name;
				if(nombre.search(reg)==0){
					if(document.forms[0].elements[t].checked==true){
						document.forms[0].elements[t].value=true;
					}
				}	
			}
		
			flag=true;
		
		}
		if(flag==true){
		
			document.forms[0].estado.value="BAJA"
				document.forms[0].action="AccionLimiteSesionesXEspeAspi.icm";
			document.forms[0].submit();
			//	document.AltaBajaModiLimiteSesionesXEspeAspiForm.submit();
			//	window.opener.document.LimiteSesionesXEspeAspiForm.submit();
			//	window.close();
		}
		
		}
		
		//editAspiranteEspe.jsp
		function ModiEspeAspi(vueltas){
		
		var sw= ValidaCuerpoYEspe();
		
		if(sw==true){
			//alert('MODI');
			//	document.forms[0].estado.value='MODI';
			//	document.AltaBajaModiLimiteSesionesXEspeAspiForm.action="AccionLimiteSesionesXEspeAspi.icm";
			//	document.AltaBajaModiLimiteSesionesXEspeAspiForm.submit();
			//	window.opener.document.LimiteSesionesXEspeAspiForm.submit();
			//	window.close();
			document.forms[0].estado.value='MODI';
			document.forms[0].action="AccionLimiteSesionesXEspeAspi.icm";
			document.forms[0].submit();
		
		}
		}

		//editAspiranteEspe.jsp
		function AltaEspeAspi(){
		
		var sw=ValidaCuerpoYEspe();
		if(sw==true){
			//	document.AltaBajaModiLimiteSesionesXEspeAspiForm.estado.value='ALTA';
			//	document.AltaBajaModiLimiteSesionesXEspeAspiForm.action="AccionLimiteSesionesXEspeAspi.icm";
			//	document.AltaBajaModiLimiteSesionesXEspeAspiForm.submit();
			//	window.opener.document.LimiteSesionesXEspeAspiForm.submit();
			//	window.opener.document.LimiteSesionesXEspeAspiForm.estado.value="ALTA";
			//	window.close();
			document.forms[0].estado.value="EA";
			document.forms[0].action="AccionLimiteSesionesXEspeAspi.icm";
			document.forms[0].submit();
		}
		}

		//LimiteSesionesXEspeAspi.jsp
		function BajaLimiEspeAspi(num_vueltas){
		
		var codCuerpo=document.LimiteSesionesXEspeAspiForm.param_cod_cuerpo.value;
		var descripCuerpo=document.LimiteSesionesXEspeAspiForm.param_des_cuerpo.value;
		var codEspe=document.LimiteSesionesXEspeAspiForm.param_cod_espe.value;
		var descripEspe=document.LimiteSesionesXEspeAspiForm.param_des_espe.value;
		var flag=false;
		var v=num_vueltas;
		var sw=false;
		var flag = false;
		
		if(v>1){
			for(i=0;i<v;i++){
				sw=document.LimiteSesionesXEspeAspiForm.fila[i].checked;
				if(sw == true){
					flag = true;
				}
			}
		}else{
			sw=document.LimiteSesionesXEspeAspiForm.fila.checked;
			if(sw == true){
				flag = true;
			}
		}
		if(flag==true){
		
			//window.open('AccionAltaBajaModiEspeAspi.icm?params=2;'+flag+';'+codCuerpo+';'+descripCuerpo+';'+codEspe+';'+descripEspe, 'popup' ,'width= 860 ,height=520 ');
			document.forms[0].estado.value='MODI';
			document.forms[0].sw.value="2";
			document.forms[0].action="AccionAltaBajaModiEspeAspi.icm";
			document.forms[0].submit();
		
		}else{
		
			alert('Debe de Seleccionar un Registro para Poder Modificarlo.\n Gracias');
		
		}
		}

		//LimiteSesionesXEspeAspi.jsp
		function ModiLimiEspeAspi(num_vueltas){
		
		var codCuerpo=document.LimiteSesionesXEspeAspiForm.param_cod_cuerpo.value;
		var descripCuerpo=document.LimiteSesionesXEspeAspiForm.param_des_cuerpo.value;
		var codEspe=document.LimiteSesionesXEspeAspiForm.param_cod_espe.value;
		var descripEspe=document.LimiteSesionesXEspeAspiForm.param_des_espe.value;
		var flag=false;
		var v=num_vueltas;
		var sw=false;
		var flag = false;
		
		if(v>1){
			for(i=0;i<v;i++){
				sw=document.LimiteSesionesXEspeAspiForm.fila[i].checked;
				if(sw == true){
					flag = true;
				}
			}
		}else{
			sw=document.LimiteSesionesXEspeAspiForm.fila.checked;
			if(sw == true){
				flag = true;
			}
		}
		
		if(flag==true){
		
			//	window.open('AccionAltaBajaModiEspeAspi.icm?params=3;'+flag+';'+codCuerpo+';'+descripCuerpo+';'+codEspe+';'+descripEspe, 'popup' ,'width= 860 ,height=520 ');
			document.forms[0].estado.value='MODI';
			document.forms[0].sw.value="3";
			document.forms[0].action="AccionAltaBajaModiEspeAspi.icm";
			document.forms[0].submit();
		
		
		
		}else{
		
			alert('Debe de Seleccionar un Registro para Poder Modificarlo.\n Gracias');
		
		}
		}
		//LimiteSesionesXEspeAspi.jsp
		function AltaLimiteSesionesXEspeAspi(num_vueltas){
		
		var codCuerpo=document.LimiteSesionesXEspeAspiForm.param_cod_cuerpo.value;
		var descripCuerpo=document.LimiteSesionesXEspeAspiForm.param_des_cuerpo.value;
		var codEspe=document.LimiteSesionesXEspeAspiForm.param_cod_espe.value;
		var descripEspe=document.LimiteSesionesXEspeAspiForm.param_des_espe.value;
		var flag=false;
		var v=num_vueltas;
		var sw=false;
		if(v>1){
			for(i=0;i<v;i++){
				sw=document.LimiteSesionesXEspeAspiForm.fila[i].checked;
				if(sw == true){
					flag = true;
				}else{
					sw="1";
				}
			}
		}else if(v==0){
		
			flag = false;
		
		}else {
			sw=document.LimiteSesionesXEspeAspiForm.fila.checked;
			if(sw == true){
				flag = true;
			}
		}
		
		if(flag==true){
		
			//	window.open('AccionAltaBajaModiEspeAspi.icm?params=1;'+flag+';'+codCuerpo+';'+descripCuerpo+';'+codEspe+';'+descripEspe, 'popup' ,'width= 860 ,height=520 ');
			document.forms[0].estado.value='ALTA';
			document.forms[0].action="AccionAltaBajaModiEspeAspi.icm";
			document.forms[0].flag.value='true';
			document.forms[0].sw.value="1";
			document.forms[0].submit();
		
		}else{
		
			//	window.open('AccionAltaBajaModiEspeAspi.icm?params=1;'+flag, 'popup' ,'width= 860 ,height=520 ');
			document.forms[0].estado.value='ALTA';
			document.forms[0].action="AccionAltaBajaModiEspeAspi.icm";
			document.forms[0].flag.value='false';
			document.forms[0].sw.value="1";
			document.forms[0].submit();
		
		}
		
		
		
		}
		//LimiteSesionesXEspeAspi.jsp
		function PasaParametrosLimiteSesionesXEspeAspi(num_vueltas){
		var v=num_vueltas;
		if(v>1){
			for(i=0;i<v;i++){
				var sw=document.LimiteSesionesXEspeAspiForm.fila[i].checked;
		
				if(sw == true){
					document.LimiteSesionesXEspeAspiForm.param_cod_cuerpo.value=document.LimiteSesionesXEspeAspiForm.cd_cuerpo[i].value;
					document.LimiteSesionesXEspeAspiForm.param_des_cuerpo.value=document.LimiteSesionesXEspeAspiForm.ds_cuerpo[i].value;
					document.LimiteSesionesXEspeAspiForm.param_cod_espe.value=document.LimiteSesionesXEspeAspiForm.cd_especialidad[i].value;
					document.LimiteSesionesXEspeAspiForm.param_des_espe.value=document.LimiteSesionesXEspeAspiForm.ds_especialidad[i].value;
					document.LimiteSesionesXEspeAspiForm.param_num_sesiones.value=document.LimiteSesionesXEspeAspiForm.nm_sesiones[i].value;
					document.LimiteSesionesXEspeAspiForm.param_nm_aspi_min.value=document.LimiteSesionesXEspeAspiForm.nm_aspirantes_min[i].value;
					document.LimiteSesionesXEspeAspiForm.param_nm_aspi_max.value=document.LimiteSesionesXEspeAspiForm.nm_aspirantes_max[i].value;
		
				}				
			}
		}else{
		
			document.LimiteSesionesXEspeAspiForm.param_cod_cuerpo.value=document.LimiteSesionesXEspeAspiForm.cd_cuerpo.value;
			document.LimiteSesionesXEspeAspiForm.param_des_cuerpo.value=document.LimiteSesionesXEspeAspiForm.ds_cuerpo.value;
			document.LimiteSesionesXEspeAspiForm.param_cod_espe.value=document.LimiteSesionesXEspeAspiForm.cd_especialidad.value;
			document.LimiteSesionesXEspeAspiForm.param_des_espe.value=document.LimiteSesionesXEspeAspiForm.ds_especialidad.value;
			document.LimiteSesionesXEspeAspiForm.param_num_sesiones.value=document.LimiteSesionesXEspeAspiForm.nm_sesiones.value;
			document.LimiteSesionesXEspeAspiForm.param_nm_aspi_min.value=document.LimiteSesionesXEspeAspiForm.nm_aspirantes_min.value;
			document.LimiteSesionesXEspeAspiForm.param_nm_aspi_max.value=document.LimiteSesionesXEspeAspiForm.nm_aspirantes_max.value;
		
		
		}
		}
		/*********************************************************************************************************
		Funciones pertenecientes al escenario L?mite de Sesiones Ordinarias Por Especialidad
		y Rango de Aspirantes                  FIN
		**********************************************************************************************************/
		
		/*********************************************************************************************************
					Funciones pertenecientes al escenario L?mite de Sesiones Preparatorias
													INICIO
		**********************************************************************************************************/
		/*********************************************************************************************************
					Funciones pertenecientes al escenario L?mite de Sesiones Preparatorias
													FIN
		**********************************************************************************************************/
		
		/**********************************************************************************************
		Funciones pertenecientes al escenario L?mite de Sesiones Por Cuerpo INICIO
		**********************************************************************************************/
		//LimiteSesionesXCuerpo.jsp
		function BajaLimteSesionesXCuerpo(){
		
		var cod=document.AltaBajaModiLimiteSesionesXCuerpoForm.cod.value;
		var descripcion=document.AltaBajaModiLimiteSesionesXCuerpoForm.descripcion.value;
		var nmsesiones=document.AltaBajaModiLimiteSesionesXCuerpoForm.nmsesiones.value;
		window.opener.document.LimiteSesionesXCuerpoForm.estado.value='BAJA';
		window.opener.document.LimiteSesionesXCuerpoForm.param_cd_cuerpo.value=cod;
		window.opener.document.LimiteSesionesXCuerpoForm.param_ds_cuerpo.value=descripcion;
		window.opener.document.LimiteSesionesXCuerpoForm.param_nm_sesiones.value=nmsesiones;
		window.opener.document.LimiteSesionesXCuerpoForm.action='AccionLimiteSesionesXCuerpo.icm';
		window.opener.document.LimiteSesionesXCuerpoForm.submit();
		window.close();
		
		
		
		
		}
		//LimiteSesionesXCuerpo.jsp
		function NavegaBajaLimteSesionesXCuerpo(num_vueltas){
		
		var flag=false;
		var v=num_vueltas;
		var sw=false;
		if(v>1){
			for(i=0;i<v;i++){
				sw=document.LimiteSesionesXCuerpoForm.fila[i].checked;
				if(sw == true){
					flag= true;
				}
			}
		}else{
			sw=document.LimiteSesionesXCuerpoForm.fila.checked;
			if(sw == true){
				flag= true;
			}
		}
		
		if(flag==true){
		
			var cod=document.LimiteSesionesXCuerpoForm.param_cd_cuerpo.value;
			var descripcion=document.LimiteSesionesXCuerpoForm.param_ds_cuerpo.value;
			var nmsesiones=document.LimiteSesionesXCuerpoForm.param_nm_sesiones.value;				
			window.open('AccionAltaBajaModiSesionesXCuerpo.icm?params=2;'+cod+';'+descripcion+';'+nmsesiones, 'popup' ,'width= 750 ,height= 230 ');
		
		}else{
			alert("Debe de selecionar un registro para poder darlo de baja. ");
		}
		}


		//LimiteSesionesXCuerpo.jsp
		function NavegaModiLimiteSesionesXCuerpo(num_vueltas){
		var flag=false;
		var v=num_vueltas;
		var sw=false;
		
		if(v>1){
			for(i=0;i<v;i++){
				sw=document.LimiteSesionesXCuerpoForm.fila[i].checked;
				if(sw == true){
					flag= true;
				}
			}
		}else{
			sw=document.LimiteSesionesXCuerpoForm.fila.checked;
			if(sw == true){
				flag= true;
			}
		}
		
		if(flag==true){
		
			var cod=document.LimiteSesionesXCuerpoForm.param_cd_cuerpo.value;
			var descripcion=document.LimiteSesionesXCuerpoForm.param_ds_cuerpo.value;
			var nmsesiones=document.LimiteSesionesXCuerpoForm.param_nm_sesiones.value;				
		
			window.open('AccionAltaBajaModiSesionesXCuerpo.icm?params=3;'+cod+';'+descripcion+';'+nmsesiones, 'popup' ,'width= 750 ,height= 230 ');
		
		}else{
		
			alert("Debe de selecionar un registro para poder darlo de baja. ");
		
		}
		}


		//LimiteSesionesXCuerpo.jsp
		function PasaParametrosLimiteSesionesXCuerpo(num_vueltas){
		var v=num_vueltas;
		if(v>1){
			for(i=0;i<v;i++){
				var sw=document.LimiteSesionesXCuerpoForm.fila[i].checked;
		
				if(sw == true){
					document.LimiteSesionesXCuerpoForm.param_cd_cuerpo.value=document.LimiteSesionesXCuerpoForm.cdcuerpo[i].value;
					document.LimiteSesionesXCuerpoForm.param_ds_cuerpo.value=document.LimiteSesionesXCuerpoForm.dscuerpo[i].value;
					document.LimiteSesionesXCuerpoForm.param_nm_sesiones.value=document.LimiteSesionesXCuerpoForm.nmsesiones[i].value;
		
				}				
			}
		}else{
		
			document.LimiteSesionesXCuerpoForm.param_cd_cuerpo.value=document.LimiteSesionesXCuerpoForm.cdcuerpo.value;
			document.LimiteSesionesXCuerpoForm.param_ds_cuerpo.value=document.LimiteSesionesXCuerpoForm.dscuerpo.value;
			document.LimiteSesionesXCuerpoForm.param_nm_sesiones.value=document.LimiteSesionesXCuerpoForm.nmsesiones.value;
		
		
		}
		}

		//AltaBajaModiSesionesXCuerpo.jsp
		function ModificaLimteSesionesXCuerpo(){
		
		var cod=document.AltaBajaModiLimiteSesionesXCuerpoForm.cod.value;
		var descripcion=document.AltaBajaModiLimiteSesionesXCuerpoForm.descripcion.value;
		var nmsesiones=document.AltaBajaModiLimiteSesionesXCuerpoForm.nmsesiones.value;
		
		if((nmsesiones=='')){
		
			alert('Debe de Introducir El N?mero de Sesiones.\n Gracias. ');
			document.AltaBajaModiLimiteSesionesXCuerpoForm.nmsesiones.focus();	
		
		}else{
		
			window.opener.document.LimiteSesionesXCuerpoForm.estado.value='MODI';
			window.opener.document.LimiteSesionesXCuerpoForm.param_cd_cuerpo.value=cod;
			window.opener.document.LimiteSesionesXCuerpoForm.param_ds_cuerpo.value=descripcion;
			window.opener.document.LimiteSesionesXCuerpoForm.param_nm_sesiones.value=nmsesiones;
			window.opener.document.LimiteSesionesXCuerpoForm.action='AccionLimiteSesionesXCuerpo.icm';
			window.opener.document.LimiteSesionesXCuerpoForm.submit();
			window.close();
		}
		}


		//AltaBajaModiSesionesXCuerpo.jsp
		function AltaLimteSesionesXCuerpo(){
		
		var cod=document.AltaBajaModiLimiteSesionesXCuerpoForm.cod.value;
		var descripcion=document.AltaBajaModiLimiteSesionesXCuerpoForm.descripcion.value;
		var nmsesiones=document.AltaBajaModiLimiteSesionesXCuerpoForm.nmsesiones.value;
		
		if((cod=='')||(nmsesiones=='')){
		
			alert('Dede Introducir Todos los Datos para Dar de Alta un Nuevo L?mite.\nGracias.');
			document.AltaBajaModiLimiteSesionesXCuerpoForm.cod.focus();
		}else if((cod=='')){
		
			alert('Debe de Introducir El C?digo del Cuerpo.\n Gracias. ');
			document.AltaBajaModiLimiteSesionesXCuerpoForm.cod.focus();	
		
		}else if((nmsesiones=='')){
		
			alert('Debe de Introducir El N?mero de Sesiones.\n Gracias. ');
			document.AltaBajaModiLimiteSesionesXCuerpoForm.nmsesiones.focus();	
		
		}else{
			window.opener.document.LimiteSesionesXCuerpoForm.estado.value='ALTA';
			window.opener.document.LimiteSesionesXCuerpoForm.param_cd_cuerpo.value=cod;
			window.opener.document.LimiteSesionesXCuerpoForm.param_ds_cuerpo.value=descripcion;
			window.opener.document.LimiteSesionesXCuerpoForm.param_nm_sesiones.value=nmsesiones;
			window.opener.document.LimiteSesionesXCuerpoForm.action='AccionLimiteSesionesXCuerpo.icm';
			window.opener.document.LimiteSesionesXCuerpoForm.submit();
			window.close();
		}
		}
		
		/**********************************************************************************************
		Funciones pertenecientes al escenario L?mite de Sesiones Por Cuerpo FIN
		**********************************************************************************************/
		

		//no se usa
		function baja_miembros_tribu(num_vueltas){
		
		var flag=false;
		var v=num_vueltas;
		var sw=false;
		if(v>1){
			for(i=0;i<v;i++){
				sw=document.formTribunal.fila[i].checked;
				if(sw == true){
					flag= true;
				}
			}
		}else{
			sw=document.formTribunal.fila.checked;
			if(sw == true){
				flag= true;
			}
		}
		
		if(flag==true){
		
			var dni=document.formTribunal.param_dni.value;
			var nom=document.formTribunal.param_nom.value;
			var ape1=document.formTribunal.param_ape1.value;
			var ape2=document.formTribunal.param_ape2.value;
			var ds_muni=document.formTribunal.param_ds_muni.value;					
			var cd_muni=document.formTribunal.param_cd_muni.value;
			var cargo=document.formTribunal.param_cargo.value;
			var categoria=document.formTribunal.param_categoria.value;
			var banco=document.formTribunal.param_banc.value;	
			var sucursal=document.formTribunal.param_suc.value;
			var digito=document.formTribunal.param_d_c.value;
			var cuenta=document.formTribunal.param_cuenta.value;
		
			window.open('AccionAltaBajaModiMiembrosTribu.icm?params=3;'+dni+';'+nom+';'+ape1+';'+ape2+';'+ds_muni+';'+cd_muni+';'+cargo+';'+categoria+';'+banco+';'+sucursal+';'+digito+';'+cuenta, 'popup' ,'width= 600 ,height=410');
		
		}else{
		
			alert("Debe de selecionar un registro para poder darlo de baja. ");
		
		}
		}

		//no se usa
		function modi_miembros_tribu(num_vueltas){
		var flag=false;
		var v=num_vueltas;
		var sw=false;
		if(v>1){
			for(i=0;i<v;i++){
				sw=document.formTribunal.fila[i].checked;
				if(sw == true){
					flag= true;
				}
			}
		}else{
			sw=document.formTribunal.fila.checked;
			if(sw == true){
				flag= true;
			}
		}
		
		if(flag==true){
			var dni=document.formTribunal.param_dni.value;
			var nom=document.formTribunal.param_nom.value;
			var ape1=document.formTribunal.param_ape1.value;
			var ape2=document.formTribunal.param_ape2.value;
			var ds_muni=document.formTribunal.param_ds_muni.value;					
			var cd_muni=document.formTribunal.param_cd_muni.value;
			var cargo=document.formTribunal.param_cargo.value;
			var categoria=document.formTribunal.param_categoria.value;
			var banco=document.formTribunal.param_banc.value;	
			var sucursal=document.formTribunal.param_suc.value;
			var digito=document.formTribunal.param_d_c.value;
			var cuenta=document.formTribunal.param_cuenta.value;
			window.open('AccionAltaBajaModiMiembrosTribu.icm?params=2;'+dni+';'+nom+';'+ape1+';'+ape2+';'+ds_muni+';'+cd_muni+';'+cargo+';'+categoria+';'+banco+';'+sucursal+';'+digito+';'+cuenta, 'popup' ,'width= 650 ,height=525');
		}else{
		
			alert("Debe de selecionar un registro para poder darlo de baja. ");
		
		}
		}
		
		
		//alta_baja_modi_compo_tribu.jsp
		function alta_miemb_tribu(){
		
		var dni=document.alta_baja_modi_miem_tribu.dni.value;
		var nom=document.alta_baja_modi_miem_tribu.nom.value;
		var ape1=document.alta_baja_modi_miem_tribu.ape1.value;
		var ape2=document.alta_baja_modi_miem_tribu.ape2.value;		
		var localidad=document.alta_baja_modi_miem_tribu.localidad.value;
		var tipo_cargo=document.alta_baja_modi_miem_tribu.tipo_cargo.value;
		var categoria=document.alta_baja_modi_miem_tribu.categoria.value;		
		var banco=document.alta_baja_modi_miem_tribu.dat_banco_1.value;
		var sucu=document.alta_baja_modi_miem_tribu.dat_sucu_1.value;
		var dc=document.alta_baja_modi_miem_tribu.dat_d_c.value;
		var cuenta=document.alta_baja_modi_miem_tribu.dat_cuenta.value;
		var result=false;
		if((dni=='')&&(nom=='')&&(ape1=='')&&(localidad=='')&&(tipo_cargo=='')){
		
			alert('Debe Rellenar Todos los Datos del Nuevo Miembro para Darlo de Alta.\n Gracias.');
			document.alta_baja_modi_miem_tribu.dni.focus();
			result=false;
		}else if(dni==''){
			document.alta_baja_modi_miem_tribu.dni.focus();
			alert('Debe Rellenar El Dni del Nuevo Miembro.');
			result=false;
		}else if(nom==''){
			alert('Debe Rellenar El Nombre del Nuevo Miembro.');
			document.alta_baja_modi_miem_tribu.nom.focus();
			result=false;		
		}else if(ape1==''){
			alert('Debe Rellenar El Primer Apellido del Nuevo Miembro.');
			document.alta_baja_modi_miem_tribu.ape1.focus();		
			result=false;
		}else if(localidad==''){
			alert('Debe Rellenar La Localidad.');
			document.alta_baja_modi_miem_tribu.cd_localidad.focus();
			result=false;
		}
		else{
			window.opener.document.formTribunal.param_estado.value="ALTA";
			window.opener.document.formTribunal.param_dni.value=document.alta_baja_modi_miem_tribu.dni.value;
			window.opener.document.formTribunal.param_nom.value=document.alta_baja_modi_miem_tribu.nom.value;
			window.opener.document.formTribunal.param_ape1.value=document.alta_baja_modi_miem_tribu.ape1.value;
			window.opener.document.formTribunal.param_ape2.value=document.alta_baja_modi_miem_tribu.ape2.value;
			window.opener.document.formTribunal.param_cd_muni.value=document.alta_baja_modi_miem_tribu.cd_localidad.value;
			window.opener.document.formTribunal.param_cargo.value=document.alta_baja_modi_miem_tribu.tipo_cargo.value;
			window.opener.document.formTribunal.param_categoria.value=document.alta_baja_modi_miem_tribu.categoria.value;
			window.opener.document.formTribunal.param_banc.value=document.alta_baja_modi_miem_tribu.dat_banco_1.value;
			window.opener.document.formTribunal.param_suc.value=document.alta_baja_modi_miem_tribu.dat_sucu_1.value;
			window.opener.document.formTribunal.param_d_c.value=document.alta_baja_modi_miem_tribu.dat_d_c.value;
			window.opener.document.formTribunal.param_cuenta.value=document.alta_baja_modi_miem_tribu.dat_cuenta.value;	
			window.opener.document.formTribunal.action="AccionTribunal.icm";
			result=true;
			window.opener.document.formTribunal.submit();
			window.close();
		}
		
		return result;
		}
		
		
		//alta_baja_modi_compo_tribu.jsp
		function modi_miemb_tribu(){
		
		
		var nom=document.alta_baja_modi_miem_tribu.nom_modi.value;
		var ape1=document.alta_baja_modi_miem_tribu.ape1_modi.value;
		var ape2=document.alta_baja_modi_miem_tribu.ape2_modi.value;		
		var localidad=document.alta_baja_modi_miem_tribu.localidad.value;
		var tipo_cargo=document.alta_baja_modi_miem_tribu.cargo.value;
		var categoria=document.alta_baja_modi_miem_tribu.categorias_modi.value;		
		var banco=document.alta_baja_modi_miem_tribu.dat_banco_1_modi.value;
		var sucu=document.alta_baja_modi_miem_tribu.dat_sucu_1_modi.value;
		var dc=document.alta_baja_modi_miem_tribu.dat_d_c_modi.value;
		var cuenta=document.alta_baja_modi_miem_tribu.dat_cuenta_modi.value;
		var result=false;
		if((nom=='')&&(ape1=='')&&(localidad=='')&&(tipo_cargo=='')){
		
			alert('Debe Rellenar Todos los Datos del Nuevo Miembro para Darlo de Alta.');
			document.alta_baja_modi_miem_tribu.nom_modi.focus();
			result=false;
		}else if(nom==''){
			alert('Debe Rellenar El Nombre del Nuevo Miembro.');
			document.alta_baja_modi_miem_tribu.nom_modi.focus();
			result=false;		
		}else if(ape1==''){
			alert('Debe Rellenar El Primer Apellido del Nuevo Miembro.');
			document.alta_baja_modi_miem_tribu.ape1_modi.focus();		
			result=false;
		}else if(localidad==''){
			alert('Debe Rellenar La Localidad.');
			document.alta_baja_modi_miem_tribu.localidad.focus();
			result=false;
		
		}else{
		
		
			window.opener.document.formTribunal.param_estado.value="MODI";
			window.opener.document.formTribunal.param_dni.value=document.alta_baja_modi_miem_tribu.dni_modi.value;
			window.opener.document.formTribunal.param_nom.value=document.alta_baja_modi_miem_tribu.nom_modi.value;
			window.opener.document.formTribunal.param_ape1.value=document.alta_baja_modi_miem_tribu.ape1_modi.value;
			window.opener.document.formTribunal.param_ape2.value=document.alta_baja_modi_miem_tribu.ape2_modi.value;
			window.opener.document.formTribunal.param_cd_muni.value=document.alta_baja_modi_miem_tribu.cd_localidad_modi.value;
			window.opener.document.formTribunal.param_cargo.value=document.alta_baja_modi_miem_tribu.cargo.value;
			window.opener.document.formTribunal.param_categoria.value=document.alta_baja_modi_miem_tribu.categorias_modi.value;
			window.opener.document.formTribunal.param_banc.value=document.alta_baja_modi_miem_tribu.dat_banco_1_modi.value;
			window.opener.document.formTribunal.param_suc.value=document.alta_baja_modi_miem_tribu.dat_sucu_1_modi.value;
			window.opener.document.formTribunal.param_d_c.value=document.alta_baja_modi_miem_tribu.dat_d_c_modi.value;
			window.opener.document.formTribunal.param_cuenta.value=document.alta_baja_modi_miem_tribu.dat_cuenta_modi.value;	
			window.opener.document.formTribunal.action="AccionTribunal.icm";
			result=true;
			window.opener.document.formTribunal.submit();
			window.close();
		}
		
		return result;
		}
		
		/**********************************************************************************************
		Funciones pertenecientes al escenario de Composici?n de un tribunal FIN
		**********************************************************************************************/
		/**********************************************************************************************
		Funciones pertenecientes al catalogo distancias entre municipios INICIO
		**********************************************************************************************/
		//alta_baja_modi_distan_muni
		function altaDistanEntreMuni(){
		
		var cdmuni1= document.alta_baja_modi_distan.cod_muni001.value;
		var dsmuni1= document.alta_baja_modi_distan.des_muni001.value;
		var cdmuni2= document.alta_baja_modi_distan.cod_muni002.value;
		var dsmuni2= document.alta_baja_modi_distan.des_muni002.value;
		var km_distancia= document.alta_baja_modi_distan.distancia.value;			
		var result=false;
		if((cdmuni1=='')&&(dsmuni1=='')&&(cdmuni2=='')&&(dsmuni2=='')&&(km_distancia=='')){
			alert('Debe Rellenar Todos los Datos Para dar de Alta un Nuevo Registro.\nGracias.');
			result=false;
		}else if(cdmuni1==''){
		
			alert('Debe Rellenar El C?digo del Municipio de Salida.\nGracias.');
			result=false;
		
		}else if(dsmuni1==''){
		
			alert('Debe Rellenar El la Descripci?n del Municipio de Salida.\nGracias.');
			result=false;
		
		}else if(cdmuni2==''){
		
			alert('Debe Rellenar El C?digo del Municipio de Llegada.\nGracias.');
			result=false;
		
		}else if(dsmuni2==''){
		
			alert('Debe Rellenar El la Descripci?n del Municipio de Llegada.\nGracias.');
			result=false;
		
		}else if(km_distancia==''){
		
			alert('Debe Rellenar El la Distancia Entre el Municipio de Salida y el Municipio de Llegada .\nGracias.');
			result=false;
		
		}else{
			window.opener.document.form_distan_entre_muni.estado.value='ALTA';
			window.opener.document.form_distan_entre_muni.alta_cod_muni001.value=document.alta_baja_modi_distan.cod_muni001.value;
			window.opener.document.form_distan_entre_muni.alta_ds_muni001.value=document.alta_baja_modi_distan.des_muni001.value;
			window.opener.document.form_distan_entre_muni.alta_cod_muni002.value=document.alta_baja_modi_distan.cod_muni002.value;
			window.opener.document.form_distan_entre_muni.alta_ds_muni002.value=document.alta_baja_modi_distan.des_muni002.value;
			window.opener.document.form_distan_entre_muni.alta_km.value=document.alta_baja_modi_distan.distancia.value;
			window.opener.document.form_distan_entre_muni.accion='AccionDistanciaEntreMuni.icm';
			result=true;
			window.opener.document.form_distan_entre_muni.submit();
			window.close();
		}
		return result;	      	
		}

		/**********************************************************************************************
		Funciones pertenecientes al catalogo distancias entre municipios FIN
		**********************************************************************************************/
		
		/**********************************************************************************************
		Funciones pertenecionetes al Catalogo Tipo de Gasto.INICIO
		**********************************************************************************************/
		
		function alta(){
		var cod= document.alta_modi.cod_tipo_gasto.value;
		var des= document.alta_modi.ds_tipo_gasto.value;
		var horas= document.alta_modi.num_horas.value;
		var result=false;
		
		
		
		if((cod=='')&(des=='')&(horas=='')){
		
			alert('Debe de Introducir Todos los Datos.\nGracias');
			document.alta_modi.cod_tipo_gasto.focus();
		
		
			result=false;
		}else if(cod==''){
		
			alert('Debe de Introducir Datos en el C?digo.\nGracias');
			document.alta_modi.cod_tipo_gasto.focus();
			result=false;
		
		
		}else if(des==''){
		
			alert('Debe de Introducir Datos en la Descripci?n.\nGracias');
			document.alta_modi.ds_tipo_gasto.focus();
			result=false;
		
		
		}else if(horas==''){
		
			alert('Debe de Introducir Datos en Las Horas.\nGracias');
			document.alta_modi.num_horas.focus();
			result=false;
		
		
		}else{
		
			window.opener.document.TipoGastos.estado.value='ALTA';
			window.opener.document.TipoGastos.alta_cod_tip_gasto.value=document.alta_modi.cod_tipo_gasto.value;
			window.opener.document.TipoGastos.alta_ds_tip_gasto.value=document.alta_modi.ds_tipo_gasto.value;
			window.opener.document.TipoGastos.alta_nm_horas.value=document.alta_modi.num_horas.value;
			window.opener.document.TipoGastos.accion='AccionTipoGastos.icm';
			result=true;
			window.opener.document.TipoGastos.submit();
			window.close();
		}
		return result;	      	
		}
		
		function modiGastos(){
		
		var des= document.alta_modi.ds_tipo_gasto_modi.value;
		var horas= document.alta_modi.num_horas_modi.value;
		var result=false;
		
		
		
		if((des=='')&(horas=='')){
		
			alert('Debe de introducir todos los datos');
			document.alta_modi.ds_tipo_gasto_modi.focus();
			result=false;
		}else if(des==''){
		
			alert('Debe de introducir datos en la Decripci?n');
			document.alta_modi.ds_tipo_gasto_modi.focus();
			result=false;
		
		
		}else if(horas==''){
		
			alert('Debe de introducir datos en las Horas');
			document.alta_modi.num_horas_modi.focus();
			result=false;
		
		
		}else{
		
			window.opener.document.TipoGastos.estado.value='MODI';
			window.opener.document.TipoGastos.modi_cod_tip_gasto.value=document.alta_modi.codigo_modi.value;
			window.opener.document.TipoGastos.modi_ds_tip_gasto.value=document.alta_modi.ds_tipo_gasto_modi.value;
			window.opener.document.TipoGastos.modi_nm_horas.value=document.alta_modi.num_horas_modi.value;
			window.opener.document.TipoGastos.accion='AccionTipoGastos.icm';
			result=true;
			window.opener.document.TipoGastos.submit();
			window.close();
		}
		return result;
		
		}
		
		
		
	/**********************************************************************************************
	Funciones pertenecionetes al Catalogo Tipo de Gasto.FIN
	**********************************************************************************************/
	/*Rober-->****************************************INICIO***************************************************************
	*Las dos funciones siquientes se forman parte de la funconalidad del la p?gina JSP calculonota
	*Son las responsables de recojer datos validarlos y relizar la l?gica perteneciente a conseguir la m?xima nota y la m?nima
	*adem?s de  la media de estas.
	*/
	//calculonota.jsp
	function calculaNot(num,min,max,ponde){
		
		var  not_mien= new Array();
		var cont;
		var acu;
		var auxacu=0;
		var error;
		var caderror;
		var med;
		var auxnum;
		var auxmed;
		var cont2;
		var may;
		var men;
		var Aux2;
		var difnotMayMen;
		
		for(cont=0;cont<num;cont++){
			not_mien[cont]=document.cal_not.elements[cont+1].value;
			acu=parseFloat(not_mien[cont]);
			var result=chequeaNotas(acu,min,max,ponde);
			var auxcont=cont+1;
			if(result=='true'){
				auxacu=auxacu+acu;
				error="false";
				document.cal_not.elements[cont+1].value=acu;
			}else{
				if(result=='false'){
					caderror='Formato incorrecto al introducir la  nota correspondiente del miembro n?'+auxcont;
					error="true";
					break;
				}
			}
		
		}
		
		if(error=="false"){
			if(num>=3){
				may=parseFloat(not_mien[0]);
				men=parseFloat(not_mien[0]);
				for(cont2=0;cont2<num;cont2++){
					Aux2=parseFloat(not_mien[cont2]);
					if(Aux2>may){
						may=Aux2;
					} 
					if(Aux2<men){
						men=Aux2;
					}
				}
			}
			difnotMayMen=may-men;
		
			if(num==3){
				//cuando los miembro son tres no se quitan las notas mayores ni menores
				med=auxacu/num;
				auxmed=String(med);
			}else{
				if(difnotMayMen>2){
					//Cuando los miembros son 4 o 5 se quitan la mayor y la menor nota adem?s de hacer la media con el n?mero de notas restantes	
					auxnum=num-2;
					auxacu=auxacu-men;
					auxacu=auxacu-may;
					med=auxacu/auxnum;
					auxmed=String(med);
				}else{
					med=auxacu/num;
					auxmed=String(med);
				}
			}
			document.cal_not.N_MIN.value=men;
			document.cal_not.N_MAY.value=may;
			auxmed=auxmed.substr(0,6);
			document.cal_not.N_MED.value=auxmed;
		}else{
			document.cal_not.elements[cont+1].focus();
			document.cal_not.elements[cont+1].select();
			document.cal_not.N_MIN.value='';
			document.cal_not.N_MAY.value='';
			document.cal_not.N_MED.value='';
		}
		}
		//this: calculaNot
		function chequeaNotas(num,max,min,ponde){
		
		if (ponde=="SI")
		{
			minimo=min;
			maximo=max;
		}
		else
		{
			minimo=0;
			maximo=10;
		}
		
		var devuelve="true";
		if(isNaN(num)){
			devuelve="false";
		}else{
			if((num < minimo)||(num > maximo)){
				devuelve="false";
			}
		}
		return devuelve;
		}
		/*Rober-->************************************************FIN*********************************************************/
		
		/*
		*
		*		GENERALES - GENERALES - GENERALES - GENERALES - GENERALES - GENERALES - GENERALES - 
		*
		*/
		
		
		function init(){
		capa = menu.style
		}
		
		function OcultarCaja(obj){
		obj.visibility = "hidden"
		}
		
		function mostrarOcultarCapa(obj) {
		if (obj.visibility == "hidden"){
			obj.visibility = "visible"
				document.forms[0].esPublicable.value='S';
		}
		else{
			obj.visibility = "hidden"
				document.forms[0].esPublicable.value='N';					
		}
		}		
		
		
		function establecerFoco() {
		var arg = arguments;
		var nombreForm = arg[0];
		if (nombreForm == loginForm){
			nombreForm.USUARIO.focus();
		}
		}
		
		function ayuda(v1,v2){
		
		if(v1==1){
		
			if(v2==1){
				ayuda_cargo.style.top=130;
				ayuda_cargo.style.left=660;	
				ayuda_cargo.style.visibility="visible";
		
		
			}else if(v2==0){
		
				ayuda_cargo.style.visibility="hidden";
		
		
			}
		
		}
		}
		
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
					alert("La hora introducida no es v?lida");
					alert("Por favor introduzca un formato correcto para la hora (HH:MM)");				
					//	theForm.elements[camp_name].select();
					valor_devuelto=false;
					return valor_devuelto;
				}
				if (a==2 && b>3) {
					alert("La hora introducida no es v?lida");
					alert("Por favor introduzca un formato correcto para la hora (HH:MM)");				
					//	theForm.elements[camp_name].select();
					valor_devuelto=false;
					return valor_devuelto;
				}
				if (d>5) {
					alert("Los minutos introducidos no son v?lidos");
					alert("Por favor introduzca un formato correcto para la hora (HH:MM)");				
					//	theForm.elements[camp_name].select();					
					valor_devuelto=false;
					return valor_devuelto;
				}
				if (a!='0' && a!='1' && a!='2' && a!='3' && a!='4' && a!='5' && a!='6' && a!='7' && a!='8' && a!='9') {
					alert("La hora introducida no es v?lida");
					alert("Por favor introduzca un formato correcto para la hora (HH:MM)");				
					//	theForm.elements[camp_name].select();					
					valor_devuelto=false;
					return valor_devuelto;
				}
				if (b!='0' && b!='1' && b!='2' && b!='3' && b!='4' && b!='5' && b!='6' && b!='7' && b!='8' && b!='9') {
					alert("La hora introducida no es v?lida");
					alert("Por favor introduzca un formato correcto para la hora (HH:MM)");				
					//	theForm.elements[camp_name].select();					
					valor_devuelto=false;
					return valor_devuelto;
				}
				if (d!='0' && d!='1' && d!='2' && d!='3' && d!='4' && d!='5' && d!='6' && d!='7' && d!='8' && d!='9') {
					alert("Los minutos introducidos no son v?lidos");
					alert("Por favor introduzca un formato correcto para la hora (HH:MM)");				
					//	theForm.elements[camp_name].select();					
					valor_devuelto=false;
					return valor_devuelto;
				}
				if (e!='0' && e!='1' && e!='2' && e!='3' && e!='4' && e!='5' && e!='6' && e!='7' && e!='8' && e!='9') {
					alert("Los minutos introducidos no son v?lidos");
					alert("Por favor introduzca un formato correcto para la hora (HH:MM)");				
					//	theForm.elements[camp_name].select();					
					valor_devuelto=false;
					return valor_devuelto;
				}
			}
			return valor_devuelto;
		}
		
		/*
		*	Compara dos horas y dice cual es mayor
		*/	
		function IsChar(YourChar)
		{
			var Template = /^[a-z]$/i; //Formato de letra
			return (Template.test(YourChar)) ? 1 : 0; //Compara \"YourChar\" con el formato \"Template\" y si coincidevuelve verdadero si no devuelve falso
		}


		/*
		*	Compara dos horas y dice cual es mayor
		*/	
		function validaHorasInicioFin(hora_ini, hora_fin, mensaje)
		{
		var hora_ini = hora_ini;
		var num_hora_ini = hora_ini.charAt(0) + hora_ini.charAt(1) + hora_ini.charAt(3) + hora_ini.charAt(4);
		//
		var hora_fin = hora_fin;
		var num_hora_fin = hora_fin.charAt(0) + hora_fin.charAt(1) + hora_fin.charAt(3) + hora_fin.charAt(4);

			if ( num_hora_ini >= num_hora_fin ) {
				alert("La hora de finalizacion " + mensaje + " debe ser mayor que la fecha de inicio");
				return false;
			}
			return true;
		}

		function imprimirExtracto(){

			const date = new Date();
		    var ejercMax = date.getFullYear();
		    var dni = document.editBaremo.dni.value;		 
		    var certificado = "N";
		    
		    var url = "ImprimirExtractoPDF.icm?ejercMax=" + ejercMax
		        + "&certificado=" + certificado
		        + "&dni="+dni;

		    window.open(url,"ImprimirExtractoPDF","width=800px,height=400px,scrollbars=yes, left=200, top=200,status=yes,resizable=1");
		   
		}