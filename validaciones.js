
	// funcion para saber si el registro esta seleccionado
	// los campos check deben de llamarse (check_+i)
        // debemos crear un campo hidden name="contador"
	// input tupe hidden del c?digo name="codigo"

		function chek()
		{
			var reg = new RegExp("^chk");
			numero=document.forms[0].length;
			var nombre="";
			var cnt=0;
			for(i=0; i<numero; i++)
			{
				nombre=document.forms[0].elements[i].name;
				if(nombre.search(reg)==0)
				{
				   if (document.forms[0].elements[i].checked)
				   {
				       cnt++;
				   }
			}
                  if(cnt > 0){
                        alert ('desea Actualizar '+cnt+'registros'); 
                        return true;
                       // document.forms[0].contador.value=cnt;
			 }else{
                       return false;  
                   } 
		}

		
}	
		
		