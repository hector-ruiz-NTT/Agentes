package agto_web.acciones;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import sistemas.framework.acciones.ClaseAccion;
import sistemas.framework.excepciones.AccionException;
import sistemas.framework.excepciones.DAOException;
import agto_web.bean.CabeceraCalificaBean;
import agto_web.bean.MiembroTribunalRolBean;
import agto_web.bean.OpositoresBean;
import agto_web.bean.RecuperaSesionesBean;
import agto_web.modelo.ComisionDAO;
import agto_web.modelo.MiembroTribunalRolDAO;
import agto_web.modelo.SesionesPorTribunalDAO;
import agto_web.modelo.SustitucionDAO;
import agto_web.util.Globales;
import agto_web.util.Util;
import agto_web.util.UtilesICM;

/* E_ASB48 371500 28/05/2013 INICIO
 * REQ005 - CU020 Sustitución del presidente por un vocal
 * Incluimos validación sobre los cambios de rol de los miembros del tribunal.
 */
/**
 * Proceso de una alta de comisión
 */
public class AccionAltaModificacionComision extends ClaseAccion {

    /**
     * mensaje
     */
    private String msg = "";

    /**
     * alta de comisión
     * 
     * @return String
     * @throws AccionException
     */
    public String procesar() throws AccionException {
        // -------------------------------------------------------------------------------
        Vector parametros = new Vector();
        parametros.add("secre");
        parametros.add("fecha_compo");
        parametros.add("hora_compo_ini");
        parametros.add("hora_compo_fin");
        parametros.add("nCMiembros");
        Util.tracearListaParametros(request, parametros);
        // -------------------------------------------------------------------------------
        String NOMBRE_VISTA = "";
        CabeceraCalificaBean ccb = new CabeceraCalificaBean();
        RecuperaSesionesBean recuperaSesiones = new RecuperaSesionesBean();
        boolean correcto = false;

        // GUARDO/LEO LA SESIÓN.
        ccb = Util.guardarSesion(this.con, request.getSession().getId(), this.request, ccb);
        ccb.tracear("tras entrar");
        // ES OPERATIVA DE COMISIÓN.
        ccb.setOrgano(Globales.TP_ORGANO_COMISION);
        ccb.setTribunal("1");

        String dni = "";
        String msg = "";
        String nmTribunalOrigen = "";
        String nmOrden = "";

        List dniNuevos = new ArrayList();
        List dniAntiguos = new ArrayList();
        int presidente = 0;
        int acumula = 1;
        int secretario = 0;
        int numMiembrosComision = 0;
        int nmSecuencia = 0;
        String modo = (request.getParameter("modo") != null ? request.getParameter("modo") : "");
        String strNumMiembrosComision = (request.getParameter("nCMiembros") != null ? request
                .getParameter("nCMiembros") : "");
        boolean estaConstituida = false;
        boolean fechaComposicionNula = false;
        boolean fcComisionMayorSesiones = false;
        ComisionDAO comDao = new ComisionDAO();
        SesionesPorTribunalDAO sesionesDAO = new SesionesPorTribunalDAO();
        SustitucionDAO sustitucionDAO = new SustitucionDAO();
        new RecuperaSesionesBean();
        new RecuperaSesionesBean();

        MiembroTribunalRolDAO miembroRolDao = new MiembroTribunalRolDAO();
        MiembroTribunalRolBean filtroRolBean = new MiembroTribunalRolBean();
        MiembroTribunalRolBean resultadoRolBean = null;

        try {

            recuperaSesiones.setFcCalificacion(request.getParameter("fecha_compo"));
            recuperaSesiones.setTlHoraInicio(request.getParameter("hora_compo_ini"));
            recuperaSesiones.setTlHoraFin(request.getParameter("hora_compo_fin"));
            recuperaSesiones.setNmSecuencia("1");

            // Modificación 13/05/2008 Carlos Suárez (everis)
            String fcSesion = request.getParameter("fecha_compo");

            if (!strNumMiembrosComision.equals("")) {
                numMiembrosComision = Integer.parseInt(strNumMiembrosComision);
            }

            // boolean existenSesiones = false;
            //
            // for (int i=0; i<numMiembrosComision ; i++){
            // List listaSesiones = new ArrayList();
            // nombreCampoNifJSP = "DNI" + Integer.toString(i);
            // dni = request.getParameter(nombreCampoNifJSP);
            // listaSesiones = sesionesDAO.buscarSesionesMiembro(con, dni,
            // fcSesion);
            // sesion.setCdConvocatoria(ccb.getConvocatoria());
            // sesion.setCdCuerpo(ccb.getCuerpo());
            // sesion.setCdEspecialidad(ccb.getEspecialidad());
            // sesion.setTpOrgano(ccb.getOrgano());
            // sesion.setNmTribunal(ccb.getTribunal());
            // sesion.setTpSesion(Globales.TP_SESION_CONSTITUCION);
            // sesion.setNmSecuencia("1");
            //
            // //boolean devuelve =
            // sesionesDAO.descartarSesionActual(listaSesiones, sesion);
            //
            // if(listaSesiones.size() > 0) {
            // recuSesiones = (RecuperaSesionesBean) listaSesiones.get(0);
            // existenSesiones = true;
            // break;
            // }
            // }
            //
            // if (!existenSesiones) {
            // Fin Modificación 13/05/2008 Carlos Suárez (everis)

//            correcto = this.validarSesion(ccb, recuperaSesiones, Globales.TP_SESION_CONSTITUCION);
            correcto = this.validarSesion(ccb, recuperaSesiones);
            if (correcto) {

                // //////////////////////////////////
                // ////////////MIEMBROS//////////////
                // //////////////////////////////////

                // Recogemos los miembros que están en el JSP y guardamos sus
                // DNI en una lista
                // para que sea más fácil de buscar un miembro
                List nuevos = new ArrayList();
                secretario = Integer.parseInt(request.getParameter("secretario"));
                presidente = Integer.parseInt(request.getParameter("presidente"));

                // Filtro para la consulta sobre los cambios de rol
                filtroRolBean.setCdConvocatoria(ccb.getConvocatoria());
                filtroRolBean.setCdEspecialidad(ccb.getEspecialidad());
                filtroRolBean.setCdCuerpo(ccb.getCuerpo());
                filtroRolBean.setTpOrgano(Globales.TP_ORGANO_TRIBUNAL);

                ccb.setOrgano(Globales.TP_ORGANO_TRIBUNAL);
                for (int i = 0; i < numMiembrosComision; i++) {
                    dni = request.getParameter("DNI" + i);

                    nmTribunalOrigen = request.getParameter("nmTribunalOrigen" + i);
                    nmOrden = request.getParameter("nmOrden" + i);

                    OpositoresBean opositoresBean = new OpositoresBean();
                    opositoresBean.setDni(dni);
                    opositoresBean.setNmTribunalOrigen(nmTribunalOrigen);
                    opositoresBean.setNumOrden(nmOrden);

                    // Inicio modificación SECO 120946 Juan Núñez (everis
                    // centers) 19/06/2009
                    if (comDao.ComprobarSesionMiembro(con, opositoresBean, ccb, fcSesion)) {
                        correcto = false;
                        msg = "El miembro con dni: " + opositoresBean.getDni()
                                + " ya tiene una sesión asginada para la fecha " + fcSesion;
                        request.setAttribute("texto", msg);
                        request.setAttribute("url", "javascript:window.history.back();");
                        return "informativa";
                    }
                    // Fin modificación SECO 120946 Juan Núñez (everis centers)
                    // 19/06/2009

                    // E_ASB48 371500 28/05/2013 INICIO
                    // REQ005 - CU020 Sustitución del presidente por un vocal
                    // En este encargo habilitamos la sustitución de presidentes
                    // de un tribunal.
                    // Esta sustitución se realiza en el mismo momento que se
                    // ejecuta el proceso,
                    // pero puede tener como fecha de efecto una fecha futura.
                    // Esto provoca que tengamos que controlar que cargo tiene
                    // cada miembro en la fecha
                    // de constitución del tribunal o en la fecha de cualquier
                    // sesión.
                    if (sustitucionDAO
                            .esSustitutoFechaPosterior(con, opositoresBean, ccb, fcSesion)) {
                        correcto = false;
                        msg = "El miembro con dni: " + opositoresBean.getDni()
                                + " es un miembro sustituto con fecha posterior a " + fcSesion
                                + ". Debe seleccionar otro miembro de tribunal.";
                        request.setAttribute("texto", msg);
                        request.setAttribute("url", "javascript:window.history.back();");
                        return "informativa";
                    }

                    // Consultamos el rol para la fecha de sesión definida
                    // Si no hay resultados es porque no se han realizado
                    // cambios en el rol en la fecha dada
                    // por lo que el rol será el que tiene actualmente
                    filtroRolBean.setDocIdentificativo(opositoresBean.getDni());
                    filtroRolBean.setNmTribunal(Integer.valueOf(nmTribunalOrigen));
                    resultadoRolBean = miembroRolDao.consultaRolFecha(con, filtroRolBean, fcSesion);
                    if ((resultadoRolBean != null)
                            && !"PT".equals(resultadoRolBean.getCdTipoPuesto())) {
                        correcto = false;
                        msg = "El miembro con dni: "
                                + opositoresBean.getDni()
                                + " no es presidente de su tribunal en la fecha de sesión indicada "
                                + fcSesion + ".";
                        request.setAttribute("texto", msg);
                        request.setAttribute("url", "javascript:window.history.back();");
                        return "informativa";
                    }
                    // E_ASB48 371500 28/05/2013 FIN

                    if (presidente == i) {
                        opositoresBean.setCargo("PT");
                        opositoresBean.setSecretario("N");
                    } else if (secretario == i) {
                        opositoresBean.setCargo("VT");
                        opositoresBean.setSecretario("S");
                    } else {
                        opositoresBean.setCargo("VT");
                        opositoresBean.setSecretario("N");
                    }
                    nuevos.add(opositoresBean);
                    dniNuevos.add(opositoresBean.getDni());
                }
                // E_ASB48 371500 28/05/2013 INICIO
                ccb.setOrgano(Globales.TP_ORGANO_COMISION);
                // E_ASB48 371500 28/05/2013 FIN

                List antNuevos = new ArrayList();

                // Recuperamos los miembros que ya están en la BD
                List antiguos = comDao.busquedaMiembrosComision(con, ccb);
                if (antiguos.size() > 0) {
                    acumula = antiguos.size();
                }
                Iterator itAntiguos = antiguos.iterator();
                while (itAntiguos.hasNext()) {
                    OpositoresBean miembro = (OpositoresBean) itAntiguos.next();

                    // Guardamos los miembros, que estaban ya en la BD y NO
                    // están en los miembros del JSP, en una lista
                    if (!dniNuevos.contains(miembro.getDni())) {
                        antNuevos.add(miembro);
                    }
                    dniAntiguos.add(miembro.getDni());
                }

                // Comprobamos que un miembro (que ya estaba en la BD y NO en el
                // JSP) no haya participado
                // en otra sesión. En caso afirmativo mostramos un alert y no
                // hacemos nada
                Iterator itAntNuevos = antNuevos.iterator();

                while (itAntNuevos.hasNext()) {
                    OpositoresBean miembro = (OpositoresBean) itAntNuevos.next();

                    if (sesionesDAO.buscarMiembroTribunalEnSesion(con, ccb, miembro.getDni()) != 0) {
                        correcto = false;
                        msg = "No se puede modificar un miembro que ya ha participado en alguna sesión.";
                        request.setAttribute("texto", msg);
                        request.setAttribute("url", "javascript:window.history.back();");
                        NOMBRE_VISTA = "informativa";
                    }
                }

                if (correcto) {

                    // Borramos en AGTO_MIEMBROS_TRIBUNAL Y
                    // AGTO_MIEMBROS_SESION, los miembros que no
                    // sean ninguno de los que hemos recogido del JSP
                    comDao.BorrarMiembrosComisionTribunal(con, ccb, dniNuevos);
                    comDao.BorrarMiembrosComisionSesion(con, ccb, dniNuevos);

                    // Iteramos sobre los miembros que hemos recogido del JSP
                    Iterator itNuevos = nuevos.iterator();
                    // obtenemos el número de secuencia
                    nmSecuencia = 1;
                    List sesionesConstucion = sesionesDAO.busquedaSesiones(con, ccb,
                            Globales.TP_SESION_CONSTITUCION);
                    if (sesionesConstucion.size() > 0) {
                        Iterator it = sesionesConstucion.iterator();
                        while (it.hasNext()) {
                            RecuperaSesionesBean sesionConstitucion = (RecuperaSesionesBean) it
                                    .next();
                            nmSecuencia = Integer.parseInt(sesionConstitucion.getNmSecuencia());
                        }
                    }
                    while (itNuevos.hasNext()) {
                        OpositoresBean miembro = (OpositoresBean) itNuevos.next();

                        // si existe en la lista de los miembros de la BD, lo
                        // actualizamos en ambas tablas
                        if (dniAntiguos.contains(miembro.getDni())) {
                            correcto = comDao.actualizaMiembroComision(con, ccb, miembro.getDni(),
                                    Integer.parseInt(miembro.getNumOrden()),
                                    miembro.getSecretario(), miembro.getCargo(),
                                    miembro.getNmTribunalOrigen());
                            correcto = sesionesDAO.actualizaMiembroSesion(con, ccb,
                                    miembro.getDni(), nmSecuencia, Globales.TP_SESION_CONSTITUCION,
                                    miembro.getSecretario(), miembro.getCargo(),
                                    miembro.getNmTribunalOrigen());
                        } else { // sino lo insertamos en AGTO_MIEMBROS_TRIBUNAL
                                 // y en AGTO_MIEMBROS_SESION
                            correcto = comDao.altaMiembroComision(con, ccb, miembro.getDni(),
                                    ++acumula, miembro.getSecretario(), miembro.getCargo(),
                                    miembro.getNmTribunalOrigen());
                            correcto = sesionesDAO.altaMiembroSesion(con, ccb,
                                    miembro.getNmTribunalOrigen(), miembro.getCargo(),
                                    miembro.getSecretario(), Globales.TP_SESION_CONSTITUCION,
                                    nmSecuencia, miembro.getDni());
                        }
                    }

                    if (correcto) {

                        // //////////////////////////////////
                        // /////////////FECHAS///////////////
                        // //////////////////////////////////

                        RecuperaSesionesBean recuSesion = sesionesDAO.buscarFechaSesionesComision(
                                con, ccb);
                        if (!recuSesion.getFcCalificacion().equals("")) {
                            if (sustitucionDAO.comparaFechas(con, fcSesion,
                                    recuSesion.getFcCalificacion()) >=0) { // fc1
                                                                            // >
                                                                            // fc2
                                fcComisionMayorSesiones = true;
                            }
                        }

                        // recogemos la fecha/hora.
                        String fecha_compo = request.getParameter("fecha_compo");
                        String hora_compo_ini = request.getParameter("hora_compo_ini");
                        String hora_compo_fin = request.getParameter("hora_compo_fin");
                        fecha_compo = fecha_compo + " " + hora_compo_ini;

                        // comprobamos si está creada. Si lo está borra los
                        // miembros
                        estaConstituida = comDao.VerSiExiste(con, ccb);
                        // si esta creada la comision esta creada en
                        // Agto-ubiccacion
                        // else debemos de comprobar que esta en Agto-ubicacion
                        // si no existe grabamos
                        if (estaConstituida) {
                            // comDao.BorrarMiembrosComision(con, ccb);
                            try {
                                if (!fcComisionMayorSesiones) {
                                    comDao.AltaComisionUbicacionUpdate(con, ccb, fecha_compo); // ubicación
                                } else {
                                    correcto = false;
                                    msg = "No se puede componer una comisión con fecha igual o posterior a la de alguna de sus sesiones.";
                                    request.setAttribute("texto", msg);
                                    request.setAttribute("url", "javascript:window.history.back();");
                                    NOMBRE_VISTA = "informativa";
                                }
                            } catch (DAOException e) {
                                throw new AccionException(e);
                            }
                        } else {
                            try {
                                fechaComposicionNula = comDao.ComprobarComisionUbicacion(con, ccb);
                                // DEBE haber el registro de la comision en
                                // ubicacion aunque sin fecha hasta que se
                                // constituya.
                                // fechaComposicionNula == true -- > no está
                                // constituida.
                                // fechaComposicionNula == false --> está
                                // constituida: no debería entrar por aquí.
                                if (fechaComposicionNula) {
                                    List fecha = sesionesDAO.devuelveFechaSesion(con, ccb);
                                    Iterator itFecha = fecha.iterator();

                                    while (itFecha.hasNext()) {
                                        recuSesion = (RecuperaSesionesBean) itFecha.next();
                                        if (sustitucionDAO.comparaFechas(con,
                                                recuSesion.getFcCalificacion(), fecha_compo) == 1) {
                                            fechaComposicionNula = comDao
                                                    .AltaComisionUbicacionUpdate(con, ccb,
                                                            fecha_compo);
                                        } else if (sustitucionDAO.comparaFechas(con,
                                                recuSesion.getFcCalificacion(), fecha_compo) == 0) {
                                            if (UtilesICM.comparaHoras(
                                                    recuSesion.getTlHoraInicio(), hora_compo_fin) > 0) {
                                                fechaComposicionNula = comDao
                                                        .AltaComisionUbicacionUpdate(con, ccb,
                                                                fecha_compo);
                                            } else {
                                                correcto = false;
                                                msg = "Ya existe una sesión programada para la hora introducida.";
                                                request.setAttribute("texto", msg);
                                                request.setAttribute("url",
                                                        "javascript:window.history.back();");
                                                NOMBRE_VISTA = "informativa";
                                            }
                                        } else {
                                            correcto = false;
                                            msg = "Ya existe una sesión programada para la fecha introducida.";
                                            request.setAttribute("texto", msg);
                                            request.setAttribute("url",
                                                    "javascript:window.history.back();");
                                            NOMBRE_VISTA = "informativa";
                                        }
                                    }
                                } else {
                                    correcto = comDao.AltaComisionUbicacion(con, ccb, fecha_compo);
                                }
                            } catch (DAOException e1) {
                                throw new AccionException(e1);
                            }
                        }

                        if (modo.equals("ALTA") && (correcto == true)) {
                            correcto = sesionesDAO.insertarSesion(con, ccb,
                                    Globales.TP_SESION_CONSTITUCION, recuperaSesiones);
                            request.setAttribute("modo", "ALTA"); // Para
                                                                  // recoger el
                                                                  // valor en la
                                                                  // pantalla
                                                                  // informativa
                        } else if (modo.equals("MODIFICACION") && (correcto == true)) {
                            correcto = sesionesDAO.modificarSesion(con, ccb, recuperaSesiones,
                                    Globales.TP_SESION_CONSTITUCION);
                            request.setAttribute("modo", "MODIFICACION"); // Para
                                                                          // recoger
                                                                          // el
                                                                          // valor
                                                                          // en
                                                                          // la
                                                                          // pantalla
                                                                          // informativa
                        }
                    } else {
                        correcto = false;
                        msg = "Ha habido un error al agregar o modificar un miembro.";
                        request.setAttribute("texto", msg);
                        request.setAttribute("url", "javascript:window.history.back();");
                        NOMBRE_VISTA = "informativa";
                    }
                }

            }// fin if validarSesion
            if (!correcto) {
                // Se recarga la comisión y muestra el error.
                AccionComision accionComision = new AccionComision();
                accionComision.IniAccionTribunal(this.con, this.request, this.response,
                        NOMBRE_VISTA);
                return accionComision.procesarRellamada(msg);
            }

            NOMBRE_VISTA = "actualizacion"; // informa que ha ido bien.

            // Modificación 13/05/2008 Carlos Suárez (everis)
            // }
            //
            // else {
            // nombre = recuSesiones.getNombre();
            // dni = recuSesiones.getDni();
            // //fcSesion = recuSesiones.getFcCalificacion();
            // msg = "Atención: el miembro con NIF/NIE " + dni + " (" + nombre +
            // ") ya tiene una sesión programada para la fecha " + fcSesion +
            // ". PULSE ACEPTAR.";
            // request.setAttribute("texto",msg);
            // request.setAttribute("url","javascript:window.history.back();");
            // NOMBRE_VISTA = "informativa";
            // }
            // Fin Modificación 13/05/2008 Carlos Suárez (everis)

        } catch (DAOException e) {
            throw new AccionException(e);
        }

        return NOMBRE_VISTA;
    }// fin procesar

    /**
     * da de alta
     * 
     * @param ccb
     * @param tipo
     * @throws DAOException
     */
//    private boolean validarSesion(CabeceraCalificaBean ccb, RecuperaSesionesBean recuperaSesiones,
//            String tipo) throws DAOException {
    private boolean validarSesion(CabeceraCalificaBean ccb, RecuperaSesionesBean recuperaSesiones
            ) throws DAOException {

        SesionesPorTribunalDAO sesionesPorTribunalDAO = new SesionesPorTribunalDAO();
        boolean correcto = true;

        int resultado = sesionesPorTribunalDAO.estaAutorizado(con, ccb,
                recuperaSesiones.getFcCalificacion());
        if (resultado == 0) {
            correcto = false;
        } else if (resultado == 1) {
            recuperaSesiones.setItFestivo(Globales.ITFESTIVO_SI);
            correcto = true;
        } else if (resultado == -1) {
            recuperaSesiones.setItFestivo(Globales.ITFESTIVO_NO);
            correcto = true;
        }

        if (!correcto) {
            this.msg = "ERROR: Al tratarse de un día festivo debe estar autorizado";
        }

        request.setAttribute("msg", this.msg);
        request.setAttribute("cabecera", ccb); // en estaAutorizado se cargan
                                               // datos de la ubicación

        return correcto;
    } //

}// FIN DE CLASE.
