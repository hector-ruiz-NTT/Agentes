/**
 * <p>Paquete: agto_web.acciones</p>
 * <p>Clase: AccionAbrirTribunal</p>
 * <p>Descripcion: Esta funcionalidad permitirá desbloquear un tipo de acceso de un tribunal que ha
 *  sido bloqueado previamente porque dicho Tribunal ya ha emitido el Acta de Evaluación Final 
 *  para un determinado tipo de acceso.</p>
 * @author Software Everis
 * @version 1.0
 */
package agto_web.acciones;

import sistemas.framework.acciones.ClaseAccion;
import sistemas.framework.excepciones.AccionException;
import sistemas.util.Trazas;
import agto_web.bean.AyudaBean;
import agto_web.bean.CabeceraCalificaBean;
import agto_web.bean.HitoBean;
import agto_web.modelo.ActaFinalDAO;
import agto_web.modelo.AyudaDao;
import agto_web.modelo.TribunalHitosDAO;
import agto_web.modelo.dao.PublicacionDAO;
import agto_web.util.AvisoGeneral;
import agto_web.util.Constantes;
import agto_web.util.Util;

/**
 * Clase que da de abre un tribunal para una oposicion teniendo en cuenta las
 * especificaciones detalladas en la documentacion de la aplicacion
 */
public class AccionAbrirTribunal extends ClaseAccion {

    /**
     * Proceso de abrir un tribunal
     * 
     * @return String
     * @throws AccionException
     */
    public String procesar() throws AccionException {
        CabeceraCalificaBean ccb = new CabeceraCalificaBean();
        
        //CONTROL DE ACCESO - INI
        // LEO EL PERFIL
        String perfil = Util.leerPerfil(con, this.request);
        request.setAttribute("perfil", perfil);
        if (!"9".equals(perfil)) {
            throw new AccionException(AvisoGeneral.COD_ERROR, "Acceso Denegado"
                    + AvisoGeneral.SEPARADOR
                    + "No tiene permisos para acceder a la opción de menú seleccionada");
        }
        //CONTROL DE ACCESO - FIN
        

        String nombreVista = "";
        //String perfil = "";
        String operacion = "";
        String convocatoria = "";
        String cuerpo = "";
        String especialidad = "";
        String organo = "";
        String tribunal = "";
        String acceso = "";
        // LEO EL PERFIL
        perfil = Util.leerPerfil(con, this.request);
        request.setAttribute("perfil", perfil);
        // GUARDO/LEO LA SESIÓN.
        ccb = Util.guardarSesion(this.con, request.getSession().getId(), this.request, ccb);
        ccb.tracear("tras entrar");

        operacion = request.getParameter("operacion");
        convocatoria = request.getParameter("convocatoria");
        cuerpo = request.getParameter("cuerpo");
        especialidad = request.getParameter("especialidad");
        organo = request.getParameter("organo");
        tribunal = request.getParameter("tribunal");
        acceso = request.getParameter("acceso");
        try {
            AyudaDao ayDao = new AyudaDao();
            AyudaBean ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_ABRIR_TRIBUNAL);
            request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_ABRIR_TRIBUNAL);
            request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);
            // E_VMF17 689085 INICIO
            request.setAttribute("avisoListado", null);
            request.setAttribute("confirmarEliminar", null);
            // E_VMF17 689085 FIN
            if ("abrir".equals(operacion)) {
                nombreVista = "abrirTribunal";
                ActaFinalDAO baja = new ActaFinalDAO();
                baja.bajaActa(con, convocatoria, cuerpo, especialidad, organo, tribunal, acceso);
                request.setAttribute("avisoListado", "Se ha realizado la apertura del tribunal");

            } else if ("abrirL".equals(operacion)) {
            	// E_VMF17 689085 INICIO
                nombreVista = "abrirTribunal";
                //Se fija el acceso 1 por defecto
                if ("".equals(acceso)){
                	acceso = Constantes.STRING_ACCESO_1;
                }
                
                TribunalHitosDAO hitos = new TribunalHitosDAO();         
                //Se comprueba si existen hitos a borrar
                if (hitos.existeHito(con,ccb, Constantes.HITO_LIST_ASP_SUPER_PRU_2) || hitos.existeHito(con,ccb, Constantes.HITO_LIST_DEF_NOTAS_PARTE_2)){
                	//eliminarhito
                    HitoBean consHitoLS2 = hitos.consultaOrdenHito(con, 
                    		Constantes.HITO_LIST_ASP_SUPER_PRU_2, acceso, convocatoria);
                    HitoBean consHitoLD2 = hitos.consultaOrdenHito(con, 
                    		Constantes.HITO_LIST_DEF_NOTAS_PARTE_2, acceso, convocatoria);
                    if (consHitoLS2!=null){
	                	if (hitos.consultaTribOrdenHitoSiguiente(con, acceso, ccb, consHitoLS2.getNmOrden())){
	                		//error	                	
	                		 request.setAttribute("avisoListado", "Existen hitos posteriores al " +  consHitoLS2.getDescHito());	 
	                	}else{
	                		 request.setAttribute("avisoListado", "Se van a eliminar los siguientes listados: " +  consHitoLS2.getDescHito() +" ; "  + consHitoLD2.getDescHito());
	                		 request.setAttribute("confirmarEliminar", "L2");	                		
	                	}                    
                	}                
                }else if ((hitos.existeHito(con,ccb, Constantes.HITO_LIST_ASP_SUPER_PRU_1) || hitos.existeHito(con,ccb, Constantes.HITO_LIST_DEF_NOTAS_PARTE_1))){
                    HitoBean consHitoLA1 = hitos.consultaOrdenHito(con, 
                    		Constantes.HITO_LIST_ASP_SUPER_PRU_1, acceso, convocatoria);
                    HitoBean consHitoLD1 = hitos.consultaOrdenHito(con, 
                    		Constantes.HITO_LIST_DEF_NOTAS_PARTE_1, acceso, convocatoria);
                    if (consHitoLA1!=null){
	                	if (hitos.consultaTribOrdenHitoSiguiente(con, acceso, ccb, consHitoLA1.getNmOrden())){
	                		//error
	                		 request.setAttribute("avisoListado", "Existen hitos posteriores al " +  consHitoLA1.getDescHito());
	                	}else{
	                		
	                		 request.setAttribute("avisoListado", "Se van a eliminar los siguientes listados: " +  consHitoLA1.getDescHito() +" ; "  + consHitoLD1.getDescHito());
	                		 request.setAttribute("confirmarEliminar", "L1");
	                		
	                	}
                	}
                	
                }else{
                	request.setAttribute("avisoListado", "No existen listados definitivos");
                }
                //no 
                //existe ld parte1

            }else if("confirmaEliminar".equals(operacion)){
            	//Se realiza la eliminación de los listados
            	  if ("".equals(acceso)){
                  	acceso = Constantes.STRING_ACCESO_1;
                  }
            	String listado=request.getParameter("eliminar");
            	 TribunalHitosDAO hitos = new TribunalHitosDAO();  
            	 PublicacionDAO publicacion = new PublicacionDAO();
				if ("L2".equals(listado)) {
					
					if (hitos.existeHito(con,ccb, Constantes.HITO_LIST_ASP_SUPER_PRU_2)) {
						hitos.eliminartaHito(con, ccb, Constantes.HITO_LIST_ASP_SUPER_PRU_2);
						publicacion.eliminaPublicacionLD(con, ccb, acceso, "2", Constantes.LISTADO_ASPIRANTES_SUPERADO_PRUEBA);
						request.setAttribute("avisoListado", "Se ha realizado la apertura de los listados definitivos");
					}
					if (hitos.existeHito(con,ccb, Constantes.HITO_LIST_DEF_NOTAS_PARTE_2)) {
						hitos.eliminartaHito(con, ccb, Constantes.HITO_LIST_DEF_NOTAS_PARTE_2);
						publicacion.eliminaPublicacionLD(con, ccb, acceso,"2", Constantes.LISTADO_PARAMETRIZADO_NOTAS);
						request.setAttribute("avisoListado", "Se ha realizado la apertura de los listados definitivos");
					}       		
            	}else if ("L1".equals(listado)){
                    
					if (hitos.existeHito(con,ccb, Constantes.HITO_LIST_ASP_SUPER_PRU_1)) {
						hitos.eliminartaHito(con, ccb, Constantes.HITO_LIST_ASP_SUPER_PRU_1);
						publicacion.eliminaPublicacionLD(con, ccb, acceso,"1", Constantes.LISTADO_ASPIRANTES_SUPERADO_PRUEBA);
					  	request.setAttribute("avisoListado", "Se ha realizado la apertura de los listados definitivos");
						
					}
					
					if (hitos.existeHito(con,ccb, Constantes.HITO_LIST_DEF_NOTAS_PARTE_1)) {
						hitos.eliminartaHito(con, ccb, Constantes.HITO_LIST_DEF_NOTAS_PARTE_1);
						publicacion.eliminaPublicacionLD(con, ccb, acceso, "1",Constantes.LISTADO_PARAMETRIZADO_NOTAS );
						request.setAttribute("avisoListado", "Se ha realizado la apertura de los listados definitivos");
					}     
            	}
				nombreVista = "abrirTribunal";
            }else if ("INICIO".equals(operacion)) {
                nombreVista = "abrirTribunal";
                // E_VMF17 689085 FIN
            } else if ("cancelar".equals(operacion)) {
                nombreVista = "inicio";
            } else {
                nombreVista = "error";
            }

        } catch (Exception e1) {
            Trazas.imprimeErrorExtendido(e1);
            throw new AccionException(e1);
        }

        return nombreVista;
    }

}
