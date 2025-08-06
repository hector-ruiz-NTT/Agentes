package agto_web.acciones;

import java.util.List;
import java.util.Vector;

import sistemas.framework.acciones.ClaseAccion;
import sistemas.framework.excepciones.AccionException;
import sistemas.framework.excepciones.DAOException;
import agto_web.bean.AyudaBean;
import agto_web.bean.CabeceraCalificaBean;
import agto_web.bean.UbicacionTribunalBean;
import agto_web.modelo.AyudaDao;
import agto_web.modelo.SesionesPorTribunalDAO;
import agto_web.util.AvisoGeneral;
import agto_web.util.Constantes;
import agto_web.util.Globales;
import agto_web.util.Util;

/**
 * Administra festivos
 */
public class AccionAdminFestivos extends ClaseAccion {

    /** valor de la clase */
    private String nombreVista = "AdminFestivos";

    /**
     * proceso de Administra festivos
     * 
     * @return String
     * @throws AccionException si se produce algun error
     */
    public String procesar() throws AccionException {
    	
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
        
        // -------------------------------------------------------------------------------
        Vector parametros = new Vector();
        parametros.add("operacion");
        Util.tracearListaParametros(request, parametros);
        // -------------------------------------------------------------------------------
        CabeceraCalificaBean ccb = null;
        if (Globales.TP_ORGANO_COMISION.equals(request.getParameter("organo"))) {
            ccb = new CabeceraCalificaBean(Globales.TP_ORGANO_COMISION);
        } else {
            ccb = new CabeceraCalificaBean(Globales.TP_ORGANO_TRIBUNAL);
        }
        String usuario = Util.retornarUsuario(request);
        UbicacionTribunalBean ubicacion = null;
        SesionesPorTribunalDAO sesionesPorTribunalDAO = new SesionesPorTribunalDAO();
        //String perfil = "";
        String operacion = "";
        String fechaAlta = "";
        String fechaBaja = "";
        String descripcion = "";
        String fechaModificacion = "";
        Boolean checkAutTribunal = false;
		Boolean checkAut = false;
		Boolean checkAutCuerpo = false;
		Boolean checkAutConvocatoria = false;
        List autorizados = null;

        // LEO EL PERFIL
        perfil = Util.leerPerfil(con, this.request);
        request.setAttribute("perfil", perfil);
        
        // GUARDO/LEO LA SESIÓN.
        ccb = Util.guardarSesion(this.con, request.getSession().getId(), this.request, ccb);
        if (Globales.TP_ORGANO_COMISION.equals(request.getParameter("organo"))) {
            ccb.setTribunal("1");
            ccb.setOrgano(Globales.TP_ORGANO_COMISION);
        }
        ccb.tracear("tras grabar");

        operacion = (request.getParameter("operacion") == null) ? "" : request
                .getParameter("operacion");
        fechaAlta = (request.getParameter("fecha") == null) ? "" : request.getParameter("fecha");
        fechaBaja = (request.getParameter("fecha_baja") == null) ? "" : request
                .getParameter("fecha_baja");
        descripcion = (request.getParameter("descripcion") == null) ? "" : request
                .getParameter("descripcion");
        fechaModificacion = (request.getParameter("fecha_modificacion") == null) ? "" : request
                .getParameter("fecha_modificacion");

		String selectedOption = request.getParameter("checkAut");

		if (selectedOption != null) {
			if (selectedOption.equals("checkAut")) {
				checkAut = true;
			} else if (selectedOption.equals("checkAutCuerpo")) {
				checkAutCuerpo = true;
			} else if (selectedOption.equals("checkAutConvocatoria")) {
				checkAutConvocatoria = true;
			} else if (selectedOption.equals("checkAutTribunal")){
				checkAutTribunal = true;
			}
		}
	

        try {
            ubicacion = sesionesPorTribunalDAO.consultarUbicacionTribunal(con, ccb);

            if (operacion.equalsIgnoreCase("INICIO")) { // -----------------------------------
                                                        // MUESTRA LA JSP
                //
                if (ubicacion.getCdDAT() == null) {
                    if (Globales.TP_ORGANO_COMISION.equals(ccb.getOrgano())) {
                        request.setAttribute("mensaje", "No existe ninguna Comision");
                    } else {
                        request.setAttribute("mensaje", "No existe ningún Tribunal");
                    }
                    nombreVista = "avisogeneral";
                    return nombreVista;
                }
         } else if (operacion.equalsIgnoreCase("ALTA")) { // ---------------------------------
																// ACTUALIZA
																// DATOS
				if (sesionesPorTribunalDAO.esFinDeSemana(con, fechaAlta)
						|| sesionesPorTribunalDAO.esFestivo(con, ubicacion,
								fechaAlta)) {
					sesionesPorTribunalDAO.borraFestivosAutorizados(con, ccb,
							fechaAlta, ubicacion.getCdDAT(), checkAut);
					// si se ha marcado, se recupera la lista de tribunales
					// de esa especialidad
					// y se inserta un registro para cada uno
					if (checkAut) {
						List<CabeceraCalificaBean> listaTrib = sesionesPorTribunalDAO
								.recuperaListaTribunales(con, ccb);
						for (CabeceraCalificaBean trib : listaTrib) {
							sesionesPorTribunalDAO
									.insertaFestivosAutorizadosTrib(con, ccb,
											fechaAlta, descripcion,
											ubicacion.getCdDAT(), usuario,
											trib.getTribunal());
						}
					} else if (checkAutCuerpo) {
						// Inserta autorizar todos los tribunales de todas las
						// especialidades del cuerpo.
			
				                List<CabeceraCalificaBean> listaTrib = sesionesPorTribunalDAO.recuperaListaTribunalesCuerpo(con, ccb);
				                for (CabeceraCalificaBean trib : listaTrib) {
				                    sesionesPorTribunalDAO.insertaFestivosAutorizadosTribCuerpo(con, ccb, fechaAlta, descripcion, ubicacion.getCdDAT(), usuario, trib.getTribunal(), trib.getEspecialidad());
				                }
				            //}

					} else if (checkAutConvocatoria) {
						// Inserta autorizar todos los tribunales de todas las
						// especialidades de todos los cuerpos de la
						// convocatoria
				                    List<CabeceraCalificaBean> listaTrib = sesionesPorTribunalDAO.recuperaListaTribunalesConvocatoria(con, ccb);
				                    for (CabeceraCalificaBean trib : listaTrib) {
				                        sesionesPorTribunalDAO.insertaFestivosAutorizadosTribConvocatoria(con, ccb, fechaAlta, descripcion, ubicacion.getCdDAT(), usuario, trib.getTribunal(), trib.getEspecialidad(), trib.getCuerpo());
				                    }
				          
					} else if (checkAutTribunal){
						// 994546 - si no se ha marcado el check se filtra por
						// el tribunal
						sesionesPorTribunalDAO.insertaFestivosAutorizados(con,
								ccb, fechaAlta, descripcion,
								ubicacion.getCdDAT(), usuario);
					}
					// 994546 FIN
				} else {
                    this.request.setAttribute("msg",
                            "Sólo se pueden autorizar los días festivos o de fin de semana.");
                }
            } else if (operacion.equalsIgnoreCase("BAJA")) { // ----------------------------------
                                                            // BORRA DATOS
                if (!sesionesPorTribunalDAO.haySesionesEnFestivo(con, ccb, fechaBaja, checkAut)) {
                    sesionesPorTribunalDAO.borraFestivosAutorizados(con, ccb, fechaBaja,
                            ubicacion.getCdDAT(), checkAut);
                } else {
                    this.request.setAttribute("msg",
                            "No se puede desautorizar porque hay SESIONES celebradas en este día.");
                }
             } else if (operacion.equalsIgnoreCase("RECUPERAR")) {
                 // Comprobamos si la fecha que se va a modificar, se puede modificar
                 if (sesionesPorTribunalDAO.haySesionesEnFestivo(con, ccb, fechaModificacion, checkAut)) {
                    request.setAttribute("msg",
                            "No se puede modificar porque hay SESIONES celebradas en este día.");
                } else {
                    request.setAttribute("fechaModificacion", fechaModificacion);
                }
            } else if (operacion.equalsIgnoreCase("MODIFICACION")) {
                // Modificación de la fecha
                if (sesionesPorTribunalDAO.esFinDeSemana(con, fechaAlta)
                        || sesionesPorTribunalDAO.esFestivo(con, ubicacion, fechaAlta)) {
                    sesionesPorTribunalDAO.borraFestivosAutorizados(con, ccb, fechaModificacion,
                            ubicacion.getCdDAT(), checkAut);
                    sesionesPorTribunalDAO.borraFestivosAutorizados(con, ccb, fechaAlta,
                            ubicacion.getCdDAT(), checkAut);
                    // 994546 INI - si no se ha marcado el check se filtra por el tribunal
                    if(!checkAut){
                    	sesionesPorTribunalDAO.insertaFestivosAutorizados(con, ccb, fechaAlta,
                            descripcion, ubicacion.getCdDAT(), usuario);
                    }else{
                    	//si se ha marcado, se recupera la lista de tribunales de esa especialidad
                    	// y se inserta un registro para cada uno
                    	List<CabeceraCalificaBean> listaTrib = sesionesPorTribunalDAO.recuperaListaTribunales(con, ccb);
                    	for (CabeceraCalificaBean trib : listaTrib) {
                        	sesionesPorTribunalDAO.insertaFestivosAutorizadosTrib(con, ccb, fechaAlta,
                                    descripcion, ubicacion.getCdDAT(), usuario, trib.getTribunal());
						}

                    }
                    // 994546 FIN
                } else {
                    request.setAttribute("msg",
                            "Sólo se pueden autorizar los días festivos o de fin de semana.");
                }

            } // fin if operacion.

            AyudaDao ayDao = new AyudaDao();
            AyudaBean ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_ADMIN_FESTIVOS);
            request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_ADMIN_FESTIVOS);
            request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);

            // consulto los días autorizados y los paso a la JSP.
            autorizados = sesionesPorTribunalDAO.busquedaFestivosAutorizados(con, ccb);
            request.setAttribute("autorizados", autorizados);

        } catch (DAOException e) {
            throw new AccionException(e);
        }

        return nombreVista;
    } // fin procesar
} // FIN DE CLASE.