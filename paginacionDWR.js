/* Las funciones init() y errors() gestionan los errores */
	function init(){
       DWREngine.setErrorHandler(errors);
       dwr.util.setEscapeHtml(false);
     }
     function errors(message) {
        alert("Errores " + message);
     }
     
    /* Funciones para las columnas de la tabla */
    //Función para la columna Codigo
	function getCod(aplicacion) { 
		return "<p>" + aplicacion.codigo + "</p>" 
	};
	//Función para la columna Descripción
	function getDS(aplicacion) { 
		return "<p>" + aplicacion.nombre + "</p>"
	};
	
	estilos_tabla = {
		cellCreator:function(options) {
		    var td = document.createElement("td");
			if(options.rowIndex%2 != 0){
			    td.style.backgroundColor = "#CCCCCC";
			}
		    return td;
		  }
	}
	
	//Recupera la informacion de la paginacion incluido el listado de registros	     
    function pinta_listado(pagina_actual){
    	DWRUtil.setValue("PAGINA_ACTUAL",pagina_actual);
    	var filtro = DWRUtil.getValue("FILTRO");
		ListadoPaginadoAplicaciones.procesar(pagina_actual,filtro,procesa_pinta_listado);
     }
	 
	//Pinta la informacion de la paginacion en la vista
	function procesa_pinta_listado(PaginacionDWRBean) {
		//Se quitan las filas de la tabla
		DWRUtil.removeAllRows("listado");
		//Se añaden las nuevas filas
		DWRUtil.addRows("listado", PaginacionDWRBean.listadoRegistros, [ getCod, getDS ],estilos_tabla);
		//Se pinta la barra de navegacion de la paginacion
		DWRUtil.setValue("lista_pag",PaginacionDWRBean.listaPaginas);
		DWRUtil.setValue("anterior",PaginacionDWRBean.anterior);
		DWRUtil.setValue("siguiente",PaginacionDWRBean.siguiente);
		DWRUtil.setValue("inf_registros",PaginacionDWRBean.regMostrados);
	}  
	
	//Ir a pagina anterior
	function pag_anterior(){
     	var pagina_actual = DWRUtil.getValue("PAGINA_ACTUAL");
     	var pagina_ant = pagina_actual - 1;
     	pinta_listado(pagina_ant);
     }
	 
	//Ir a pagina siguiente
     function pag_siguiente(){
     	var pagina_actual = DWRUtil.getValue("PAGINA_ACTUAL");
     	var pagina_sig = Math.round(pagina_actual) + 1;
     	pinta_listado(pagina_sig);
     }