package agto_web.acciones;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import sistemas.framework.acciones.ClaseAccion;
import sistemas.framework.excepciones.AccionException;
import sistemas.framework.excepciones.DAOException;
import agto_web.bean.AyudaBean;
import agto_web.bean.CabeceraCalificaBean;
import agto_web.bean.MiembroTribunalBean;
import agto_web.bean.OpositoresBean;
import agto_web.bean.RecuperaSesionesBean;
import agto_web.bean.SesionesTribunalBean;
import agto_web.bean.UbicacionTribunalBean;
import agto_web.modelo.AyudaDao;
import agto_web.modelo.ComisionDAO;
import agto_web.modelo.SesionEvaluacionDAO;
import agto_web.modelo.SesionesPorTribunalDAO;
import agto_web.modelo.SustitucionDAO;
import agto_web.modelo.TribunalesAgrupadosDAO;
import agto_web.util.Constantes;
import agto_web.util.Globales;
import agto_web.util.Util;

/* E_ASB48 371500 03/06/2013
 * Actualización del proceso de inserción y consulta de sesiones 
 * para incluir el control sobre las sustituciones y cambios de rol
 * de los miembros del tribunal. 
 */

/**
 * Mantenimiento de sesiones
 */
public class AccionAltaModificaSesion extends ClaseAccion {

    /** valor de la clase */
    private static String nombreVista = "AltaBajaModiSesionesTribunal";

    /**
     * Proceso de una sesiones
     * 
     * @return String
     * @throws AccionException si se produce algún error
     */
    public String procesar() throws AccionException {
        // -------------------------------------------------------------------------------
        Vector parametros = new Vector();
        parametros.add("tipo");
        parametros.add("oper");
        parametros.add("nSesiones");
        parametros.add("secuenciaSelec");
        parametros.add("fcSesion");
        parametros.add("horaInicio");
        parametros.add("horaFin");
        Util.tracearListaParametros(request, parametros);
        // -------------------------------------------------------------------------------
        String tipo = "";
        String msg = "";
        String titulo = "";
        boolean correcto = true;
//        List recuperados = null;
        CabeceraCalificaBean ccb = new CabeceraCalificaBean(Globales.TP_ORGANO_TRIBUNAL);
        String nSesiones = "";
        SesionesPorTribunalDAO sesionesPorTribunalDAO = new SesionesPorTribunalDAO();
        String oper = "";

        if (request.getParameter("nSesiones") != null) {
            nSesiones = request.getParameter("nSesiones");
            request.setAttribute("nSesiones", nSesiones);
        }

        // GUARDO/LEO LA SESIÓN.
        ccb = Util.guardarSesion(this.con, request.getSession().getId(), this.request, ccb);
        ccb.tracear("tras entrar");

        int nmSecuencia = 0;

        if (request.getParameter("oper") != null) {
            oper = request.getParameter("oper");
        } else if (request.getAttribute("oper") != null
                        && !request.getAttribute("oper").equals("")) {
            oper = (String) request.getAttribute("oper");
        }

        if (tipo.equalsIgnoreCase("")) {
            if (request.getParameter("tipo") != null) {
                tipo = request.getParameter("tipo");
            } else if (request.getAttribute("tipo") != null
                            && !request.getAttribute("tipo").equals("")) {
                tipo = (String) request.getAttribute("tipo");
            }
        }

        if (oper == null) {
            oper = "";
        }

        try {
            if (oper.equals("A")) { // ---------
                // ALTA: INICIALIZACIÓN
                request.setAttribute("tipo", tipo);
                request.setAttribute("oper", oper);
                titulo = titulo + "Alta de Sesiones";
                AyudaDao ayDao = new AyudaDao();
                AyudaBean ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_GEST_SES_TRIB);
                request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_GEST_SES_TRIB);
                request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);
                nombreVista = "AltaBajaModiSesionesTribunal";
            } else if (oper.equals("M")) { // /--------------------------------------------------
                // MODIFICACIÓN: INICIALIZACIÓN
                request.setAttribute("tipo", tipo);
                request.setAttribute("oper", oper);
                request.setAttribute("secuenciaSelec", request.getParameter("secuenciaSelec"));
                titulo = titulo + "Modificacion de Sesiones";
                request.setAttribute("oper", oper);
                // Cargamos las cajas de Texto //
                request.setAttribute("fcSesion", request.getParameter("fechaS"));
                request.setAttribute("horaInicio", request.getParameter("horaI"));
                request.setAttribute("horaFin", request.getParameter("horaF"));
                request.setAttribute("itFestivo", request.getParameter("itFestivo"));

                AyudaDao ayDao = new AyudaDao();
                AyudaBean ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_GEST_SES_TRIB);
                request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_GEST_SES_TRIB);
                request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);

                nombreVista = "AltaBajaModiSesionesTribunal";
            } else if (oper.equals("EA")) {
                StringBuilder sbMsgOut = new StringBuilder();
                titulo = procesarAlta(tipo, titulo, correcto, ccb,
                                sesionesPorTribunalDAO, oper, sbMsgOut);
                msg = sbMsgOut.toString();
            } else if (oper.equals("MSRE")) { // --------
                titulo = procesarModificarSesionReunionEspecial(tipo,
                                msg, titulo, ccb, sesionesPorTribunalDAO, oper);
            } else if (oper.equals("MSFE")) { 
                tipo = procesarModificarSesionFormacionEspecial(tipo, msg, titulo, ccb,
                                sesionesPorTribunalDAO, oper);
            } else if (oper.equals("EM")) {
                StringBuilder sbMsgOut = new StringBuilder();
                titulo = procesarModificar(tipo, titulo, correcto, ccb, sesionesPorTribunalDAO,
                                nmSecuencia, oper, sbMsgOut);
                msg = sbMsgOut.toString();
            } // fin if oper.
        } catch (DAOException e) {
            throw new AccionException(e);
        } // fin try.

        request.setAttribute("msg", msg);
        request.setAttribute("cabecera", ccb);
        titulo += Util.getDsTipoSesion(tipo);
        request.setAttribute("titulo", titulo);

        return nombreVista;
    } // fin procesar.

    /**
     * @param tipo tipo
     * @param titulo título
     * @param correcto correcto
     * @param ccb ccb
     * @param sesionesPorTribunalDAO sesiones
     * @param nmSecuencia nmSecuencia
     * @param oper oper
     * @param sbMsgOut mensaje
     * @return tipo tipo
     * @throws DAOException si se produce algún error
     * @throws AccionException si se produce algún error
     */
    private String procesarModificar(String tipo, String titulo, boolean correcto,
                    CabeceraCalificaBean ccb, SesionesPorTribunalDAO sesionesPorTribunalDAO,
                    int nmSecuencia, String oper, StringBuilder sbMsgOut) throws DAOException, AccionException {
        List recuperados;
        // /------------
        // MODIFICACIÓN: EJECUCIÓN
        String msgTmp = "";
        RecuperaSesionesBean recuperaSesiones = new RecuperaSesionesBean();
        recuperaSesiones.setFcCalificacion(request.getParameter("fcSesion"));
        request.setAttribute("modoAuto", request.getParameter("modoAuto"));

        if (tipo.equalsIgnoreCase(Globales.TP_SESION_CONSTITUCION)) {
            msgTmp = procesarModificarSesionConstitucion(tipo, correcto, ccb,
                            sesionesPorTribunalDAO, nmSecuencia, msgTmp, recuperaSesiones);
        } else {
            if (tipo.equalsIgnoreCase(Globales.TP_SESION_ORDINARIA)
                            || tipo.equalsIgnoreCase(Globales.TP_SESION_CALIFICATORIA)
                            || tipo.equalsIgnoreCase(Globales.TP_SESION_PREPARATORIAS)
                            || tipo.equalsIgnoreCase(Globales.TP_SESION_FORMACION)
                            || tipo.equalsIgnoreCase(Globales.TP_SESION_REUNION)) {
                correcto = sesionesPorTribunalDAO.validarFechaAltaSesion(con, ccb,
                                recuperaSesiones);
            }

            if (!correcto) {
                msgTmp = "La fecha de alta debe ser Mayor que "
                                + "la fecha de Constitución del Tribunal: "
                                + recuperaSesiones.getFcAlta() + ". PULSE CANCELAR";
                correcto = false;
            } else {
                if (request.getParameter("secuenciaSelec") != null) {
                    nmSecuencia = Integer.parseInt(request.getParameter("secuenciaSelec"));
                    request.setAttribute("secuenciaSelec",
                                    request.getParameter("secuenciaSelec"));
                }
                recuperaSesiones.setNmSecuencia(Integer.toString(nmSecuencia));
                recuperaSesiones.setTlHoraInicio(request.getParameter("horaInicio"));
                recuperaSesiones.setTlHoraFin(request.getParameter("horaFin"));
                recuperaSesiones.setItFestivo(request.getParameter("itFestivo"));

                // buscamos si existe un una sesión ya creada //
                if (request.getParameter("fechaAnterior").equals(request.getParameter("fcSesion"))) {
                    correcto = true;
                } else {

                    // jcc83 - control fechas existentes sesiones resto
                    // de tribunales

                    /*
                     * "Un mismo tribunal puede evaluar distintas
                     * especialidades, en estos casos funcionará como
                     * 'n' tribunales en función de cada una de las
                     * especialidades, pero como uno a efectos de
                     * control de gastos, es decir se le aplicarán los
                     * controles de sesiones y gastos como uno."
                     */

                    // Ver tabla AGTO_TRIBUNALES_ASOCIADOS
                    MiembroTribunalBean tribunal = new MiembroTribunalBean();
                    tribunal.setCdConvocatoria(ccb.getConvocatoria());
                    tribunal.setCdCuerpo(ccb.getCuerpo());
                    tribunal.setCdEspecialidad(ccb.getEspecialidad());
                    tribunal.setNmTribunal(Integer.parseInt(ccb.getTribunal()));

                    // Comprueba si el tribunal funciona como 'n
                    // tribunales' y obtiene el id del grupo
                    
                    //E_OAR8 - INI - Los tribunales asociados ya no comparten fecha
          //          int nmGrupo = new TribunalesAgrupadosDAO().obtenerNmGrupoTribunal(con,
          //                          tribunal);

          //          if (nmGrupo == -1) {
                        correcto = sesionesPorTribunalDAO.busquedaSesionesFecha(con, ccb,
                                        tipo, recuperaSesiones.getFcCalificacion());
         /*           } else {
                        List tribunalesAgrupados = new TribunalesAgrupadosDAO().obtenerTribunalesAsociadosPorNmGrupo(
                                        con, nmGrupo, ccb);
                        correcto = true;
                        for (int i = 0; i < tribunalesAgrupados.size(); i++) {
                            MiembroTribunalBean tribunalAux = (MiembroTribunalBean) tribunalesAgrupados.get(i);

                            CabeceraCalificaBean ccbAux = new CabeceraCalificaBean();
                            ccbAux.setConvocatoria(tribunalAux.getCdConvocatoria());
                            ccbAux.setCuerpo(tribunalAux.getCdCuerpo());
                            ccbAux.setEspecialidad(tribunalAux.getCdEspecialidad());
                            ccbAux.setTribunal(String.valueOf(tribunalAux.getNmTribunal()));
                            ccbAux.setOrgano("01");

                            correcto = correcto && sesionesPorTribunalDAO.busquedaSesionesFecha(con, ccbAux, tipo,
                                            recuperaSesiones.getFcCalificacion());
                        }
                    }
          */         //E_OAR8 - FIN
                    // jcc83

                    if (correcto) {
                        int resultado = sesionesPorTribunalDAO.estaAutorizado(con, ccb,
                                        recuperaSesiones.getFcCalificacion());
                        if (resultado == 0) {
                            msgTmp = "Al tratarse de un día festivo debe estar autorizado."
                                            + " PULSE CANCELAR";
                            correcto = false;
                        } else if (resultado == 1) {
                            recuperaSesiones.setItFestivo(Globales.ITFESTIVO_SI);
                            correcto = true;
                        } else if (resultado == -1) {
                            recuperaSesiones.setItFestivo(Globales.ITFESTIVO_NO);
                            correcto = true;
                        }
                    } else {
                        msgTmp = "Existe ya una Sesión creada para esa fecha. PULSE CANCELAR";
                    }
                }
                // jcc83 - pto20 control sesiones miembros por fecha,
                // modificacion
                if (correcto) {
                    msgTmp = procesarModificarSesiones(tipo, ccb, sesionesPorTribunalDAO,
                                    msgTmp, recuperaSesiones);
                }
                //
                if (correcto) {
                    correcto = sesionesPorTribunalDAO.modificarSesion(con, ccb, recuperaSesiones, tipo);
                    if (correcto) {
                        msgTmp = "El registro ha sido modificado correctamente";
                    } else {
                        msgTmp = "No ha podido añadirse el registro. PULSE CANCELAR";
                    }
                }
            }

            if (correcto) {
                recuperados = sesionesPorTribunalDAO.busquedaSesiones(con, ccb, tipo);
                request.setAttribute("detalle", recuperados);
                request.setAttribute("tipo", tipo);
                AyudaDao ayDao = new AyudaDao();
                AyudaBean ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_DET_SES_TRIB);
                request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_DET_SES_TRIB);
                request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);
                nombreVista = "DetalleSesionesTribunal";
                if (request.getParameter("modoAuto").equals("S")) {
                    request.setAttribute("modoAuto", "S2");
                    ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_GEST_SES_TRIB);
                    request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_GEST_SES_TRIB);
                    request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);
                    nombreVista = "AltaBajaModiSesionesTribunal";
                }
            } else {
                request.setAttribute("tipo", tipo);
                request.setAttribute("oper", oper);
                request.setAttribute("secuenciaSelec",
                                request.getParameter("secuenciaSelec"));
                titulo = titulo + "Alta de Sesiones";
                request.setAttribute("fcSesion", request.getParameter("fechaS"));
                request.setAttribute("horaInicio", request.getParameter("horaI"));
                request.setAttribute("horaFin", request.getParameter("horaF"));
                request.setAttribute("itFestivo", request.getParameter("itFestivo"));
                AyudaDao ayDao = new AyudaDao();
                AyudaBean ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_GEST_SES_TRIB);
                request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_GEST_SES_TRIB);
                request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);
                nombreVista = "AltaBajaModiSesionesTribunal";
            }
        }

        if (sbMsgOut != null) {
            sbMsgOut.append(msgTmp);
        }

        return titulo;
    }

    /**
     * @param tipo tipo
     * @param ccb ccb
     * @param sesionesPorTribunalDAO sesiones
     * @param msgTmp mensjae
     * @param recuperaSesiones recuperar sesiones
     * @return mensaje
     * @throws DAOException si se produce algún error
     */
    private String procesarModificarSesiones(String tipo, CabeceraCalificaBean ccb,
                    SesionesPorTribunalDAO sesionesPorTribunalDAO, String msgTmp,
                    RecuperaSesionesBean recuperaSesiones) throws DAOException {
        boolean continuar = true;

        recuperaSesiones.setCdConvocatoria(ccb.getConvocatoria());
        recuperaSesiones.setCdCuerpo(ccb.getCuerpo());
        recuperaSesiones.setCdEspecialidad(ccb.getEspecialidad());
        recuperaSesiones.setTpOrgano(ccb.getOrgano());
        recuperaSesiones.setNmTribunal(ccb.getTribunal());
        recuperaSesiones.setTpSesion(tipo);

        // fecha candidata
        String fcSesion = recuperaSesiones.getFcCalificacion();
        String fcAntSesConst = request.getParameter("fechaAnterior");

        // dni miembros
        List miembros = null;
        if (tipo.equalsIgnoreCase(Globales.TP_SESION_ENTREGA)) {
            // E_ASB48 371500 03/06/2013 INICIO
            // Actualizamos la consulta de los miembros de
            // la sesión
            // de forma que se tiene en cuenta las
            // sustituciones y cambios de rol realizados.
            // miembros = new
            // SesionEvaluacionDAO().RecuperarMiembrosSesionEntregaDocumentos(con,
            // ccb,
            // Integer.parseInt(recuperaSesiones.getNmSecuencia()),
            // Globales.TP_SESION_ENTREGA); // recupera
            // lista miembros
            miembros = new SesionEvaluacionDAO().obtenerMiembrosSesionEntregaDocumentos(con, ccb,
                            Integer.parseInt(recuperaSesiones.getNmSecuencia()), tipo, fcSesion);
            // E_ASB48 371500 03/06/2013 FIN
        } else {
            // E_ASB48 371500 03/06/2013 INICIO
            // Actualizamos la consulta de los miembros de
            // la sesión
            // de forma que se tiene en cuenta las
            // sustituciones y cambios de rol realizados.
            // miembros = new
            // SesionEvaluacionDAO().RecuperarMiembrosSesion(con,
            // ccb,
            // Integer.parseInt(recuperaSesiones.getNmSecuencia()),
            // tipo); // recupera lista miembros
            miembros = new SesionEvaluacionDAO().obtenerMiembrosSesion(con, ccb,
                            Integer.parseInt(recuperaSesiones.getNmSecuencia()), tipo, fcSesion);
            // E_ASB48 371500 03/06/2013 FIN
        }
        for (int i = 0; (i < miembros.size()) && continuar; i++) {
            OpositoresBean ob = (OpositoresBean) miembros.get(i);

            // no se valida para asesores y colaboradores
            if (!"C".equals(ob.getItPuesto()) && !"A".equals(ob.getItPuesto())) {
                String dni = ob.getDni(); // recupera el dni del miembro
                String cdEspecialidad = ccb.getEspecialidad();
                List lSesionesMiembro = sesionesPorTribunalDAO.buscarSesionesMiembro(con, dni, fcSesion, cdEspecialidad);

                if (!lSesionesMiembro.isEmpty()) {
                    sesionesPorTribunalDAO.descartarSesionActual(lSesionesMiembro, recuperaSesiones);
                }

                if (!lSesionesMiembro.isEmpty()) {
                    continuar = false;
                    String nombre = ((RecuperaSesionesBean) lSesionesMiembro.get(0)).getNombre();
                    msgTmp = "Atención: el miembro con DNI " + dni + " (" + nombre
                            + ")  ya tiene una sesión programada para la fecha " + fcSesion + ".   PULSE CANCELAR.";
                }
            }
        }
        // E_AGM206 - 371500 - 05/06/2013 - INICIO
        if (continuar) {
            // Obtenemos los miembros de la sesion originaria
            List < OpositoresBean > miembrosOri = new SesionEvaluacionDAO().obtenerMiembrosSesion(
                            con, ccb, Integer.parseInt(recuperaSesiones.getNmSecuencia()), tipo, fcAntSesConst);
//            new HashMap < String, OpositoresBean > ();

            // Comprobamos que los miembros de la sesión
            // original puedan formar parte de la nueva
            // sesión
            for (OpositoresBean miembro : miembrosOri) {
                // Sólo comprobamos los asistentes
                if ("S".equals(miembro.getAsistencia())) {
                    OpositoresBean datosMiembroNuevaSesion = new SesionEvaluacionDAO().obtenerMiembroSesion(
                                    con, ccb, miembro.getDni(), Integer.parseInt(recuperaSesiones.getNmSecuencia()),
                                    tipo, fcSesion);

                    if (datosMiembroNuevaSesion == null) {
                        // Ha sido sustituido
                        continuar = false;
                        msgTmp = "Atención: el miembro con DNI " + miembro.getDni() + " (" + miembro.getNombre()
                              + ")  no forma parte del tribunal para la fecha " + fcSesion + " .   PULSE CANCELAR.";
                        break;
                    }

                    // Comprobamos si tiene el cargo (rol)
                    // en la sesión anterior y la nueva
                    // sesión
                    // Esta validación sólo es necesaria
                    // para las sesiones de Formación,
                    // Presidentes
                    // y Entrega de documentación.
                    if (continuar) {
                        if (tipo.equalsIgnoreCase(Globales.TP_SESION_FORMACION)
                                        || tipo.equalsIgnoreCase(Globales.TP_SESION_REUNION)
                                        || tipo.equalsIgnoreCase(Globales.TP_SESION_ENTREGA)) {
                            if (!miembro.getCargo().equals(datosMiembroNuevaSesion.getCargo())
                                            || !miembro.getSecretario().equals(
                                                            datosMiembroNuevaSesion.getSecretario())) {
                                // Ha sufrido un cambio de rol
                                continuar = false;
                                msgTmp = "Atención: el miembro con DNI " + miembro.getDni() + " ("
                                        + miembro.getNombre() + ")  tiene un" + " cargo distinto para la fecha "
                                        + fcSesion + ".   PULSE CANCELAR.";
                                break;
                            }
                        }
                    }
                }
            }
        }
        // E_AGM206 - 371500 - 05/06/2013 - FIN
        return msgTmp;
    }

    /**
     * @param tipo tipo
     * @param correcto correcto
     * @param ccb ccb
     * @param sesionesPorTribunalDAO dao
     * @param nmSecuencia nmSecuencia
     * @param msgTmp mensaje
     * @param recuperaSesiones sesiones
     * @return mensaje
     * @throws DAOException si se produce algún error
     */
    private String procesarModificarSesionConstitucion(String tipo, boolean correcto,
                    CabeceraCalificaBean ccb, SesionesPorTribunalDAO sesionesPorTribunalDAO,
                    int nmSecuencia, String msgTmp, RecuperaSesionesBean recuperaSesiones)
                                    throws DAOException {
        List recuperados;
        SustitucionDAO sustitucionDAO = new SustitucionDAO();
        String fcAntSesConst = request.getParameter("fechaAnterior");
        String fcNueSesConst = request.getParameter("fcSesion");
        String hNueIniSesConst = request.getParameter("horaInicio");
        String hNueFinSesConst = request.getParameter("horaFin");
        if (request.getParameter("secuenciaSelec") != null) {
            nmSecuencia = Integer.parseInt(request.getParameter("secuenciaSelec"));
            request.setAttribute("secuenciaSelec", request.getParameter("secuenciaSelec"));
        }

        ArrayList listaSesiones = new ArrayList();
        recuperaSesiones.setTpSesion(tipo);
        String tipcomparacion = "<>";
        listaSesiones = sesionesPorTribunalDAO.obtenerFechaSesionMenor(con, ccb, recuperaSesiones, tipcomparacion);

        Iterator iterador = listaSesiones.iterator();
        RecuperaSesionesBean recuperaSesionesBean = new RecuperaSesionesBean();

        if (listaSesiones.size() == 0) {
            UbicacionTribunalBean ubicacion = sesionesPorTribunalDAO.consultarUbicacionTribunal(con, ccb);
            recuperaSesiones.setItFestivo("N");
            recuperaSesiones.setFcCalificacion(fcNueSesConst);
            recuperaSesiones.setTlHoraInicio(hNueIniSesConst);
            recuperaSesiones.setTlHoraFin(hNueFinSesConst);
            recuperaSesiones.setNmSecuencia(Integer.toString(nmSecuencia));
            if (sesionesPorTribunalDAO.esFestivo(con, ubicacion, fcNueSesConst)) {
                recuperaSesiones.setItFestivo("S");
                msgTmp = "Al tratarse de un día festivo " + "debe estar autorizado. PULSE CANCELAR";
                correcto = false;
            }
            if (!recuperaSesiones.getItFestivo().equals("S")) {
                correcto = sesionesPorTribunalDAO.modificarSesion(con, ccb, recuperaSesiones, tipo);
                ComisionDAO comisionDAO = new ComisionDAO();
                comisionDAO.UpdateUbicacionTribunal(con, ccb, fcNueSesConst);
            }
            request.setAttribute("horaInicio", recuperaSesiones.getTlHoraInicio());
            request.setAttribute("horaFin", recuperaSesiones.getTlHoraFin());
            request.setAttribute("fcSesion", recuperaSesiones.getFcCalificacion());
            if (correcto) {
                recuperados = sesionesPorTribunalDAO.busquedaSesiones(con, ccb, tipo);
                request.setAttribute("detalle", recuperados);
                request.setAttribute("tipo", tipo);
                msgTmp = "El registro ha sido modificado correctamente";
                AyudaDao ayDao = new AyudaDao();
                AyudaBean ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_DET_SES_TRIB);
                request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_DET_SES_TRIB);
                request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);
                nombreVista = "DetalleSesionesTribunal";
            }
        } else {
            String fcUltSesNor = "";
            recuperaSesionesBean = (RecuperaSesionesBean) iterador.next();
            fcUltSesNor = recuperaSesionesBean.getFcCalificacion();

            if (sustitucionDAO.comparaFechas(con, fcNueSesConst, fcUltSesNor) == 1) {
                // Existe una sesion con fecha anterior.
                request.setAttribute("smsSoN", "SH");
            } else if (fcNueSesConst.equals(fcUltSesNor)) {
                msgTmp = "Existe ya una Sesión creada para esa fecha. PULSE CANCELAR";
            } else {
                UbicacionTribunalBean ubicacion = sesionesPorTribunalDAO.consultarUbicacionTribunal(con, ccb);
                recuperaSesiones.setItFestivo("N");
                recuperaSesiones.setNmSecuencia(Integer.toString(nmSecuencia));
                recuperaSesiones.setFcCalificacion(fcNueSesConst);
                recuperaSesiones.setTlHoraInicio(hNueIniSesConst);
                recuperaSesiones.setTlHoraFin(hNueFinSesConst);
                if (sesionesPorTribunalDAO.esFestivo(con, ubicacion, fcNueSesConst)) {
                    recuperaSesiones.setItFestivo("S");
                    msgTmp = "Al tratarse de un día festivo debe estar autorizado." + " PULSE CANCELAR";
                    correcto = false;
                }
                if (recuperaSesiones.getItFestivo().equals("N")) {
                    correcto = sesionesPorTribunalDAO.modificarSesion(con, ccb, recuperaSesiones, tipo);
                    ComisionDAO comisionDAO = new ComisionDAO();
                    comisionDAO.UpdateUbicacionTribunal(con, ccb, fcNueSesConst);
                }
                request.setAttribute("horaInicio", recuperaSesiones.getTlHoraInicio());
                request.setAttribute("horaFin", recuperaSesiones.getTlHoraFin());
                request.setAttribute("fcSesion", recuperaSesiones.getFcCalificacion());
                if (correcto) {
                    recuperados = sesionesPorTribunalDAO.busquedaSesiones(con, ccb, tipo);
                    request.setAttribute("detalle", recuperados);
                    request.setAttribute("tipo", tipo);
                    msgTmp = "El registro ha sido modificado correctamente";
                    AyudaDao ayDao = new AyudaDao();
                    AyudaBean ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_DET_SES_TRIB);
                    request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_DET_SES_TRIB);
                    request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);
                    nombreVista = "DetalleSesionesTribunal";
                }
            }
        }
        request.setAttribute("secuenciaSelec", request.getParameter("secuenciaSelec"));
        if (correcto && !fcNueSesConst.equals(fcAntSesConst)
                        && (sustitucionDAO.comparaFechas(con, fcNueSesConst, fcAntSesConst) != 1)) {
            tipcomparacion = "=";
            recuperaSesiones.setTpSesion("R");
            listaSesiones = sesionesPorTribunalDAO.obtenerFechaSesionMenor(con, ccb, recuperaSesiones, tipcomparacion);
            if (listaSesiones.size() >= 1) {
                request.setAttribute("smsSoN", "SN");
                AyudaDao ayDao = new AyudaDao();
                AyudaBean ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_GEST_SES_TRIB);
                request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_GEST_SES_TRIB);
                request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);
                nombreVista = "AltaBajaModiSesionesTribunal";

            }
        }
        return msgTmp;
    }

    /**
     * @param tipo tipo
     * @param titulo titulo
     * @param correcto correcto
     * @param ccb ccb
     * @param sesionesPorTribunalDAO sesiones
     * @param oper oper
     * @param sbMsgOut mensaje
     * @return mensaje
     * @throws DAOException si se produce algún error
     * @throws AccionException si se produce algún error
     */
    private String procesarAlta(String tipo, String titulo, boolean correcto,
                    CabeceraCalificaBean ccb, SesionesPorTribunalDAO sesionesPorTribunalDAO,
                    String oper, StringBuilder sbMsgOut)
                                    throws DAOException, AccionException {
        List recuperados;
//        String nSesiones;
        // /--------
        // ALTA: EJECUCIÓN
        String msgTmp = "";

        RecuperaSesionesBean recuperaSesiones = new RecuperaSesionesBean();

        if (request.getParameter("fcSesion") != null && !request.getParameter("fcSesion").equals("")) {
            recuperaSesiones.setFcCalificacion(request.getParameter("fcSesion"));
        } else if (request.getAttribute("fcSesion") != null && !request.getAttribute("fcSesion").equals("")) {
            recuperaSesiones.setFcCalificacion((String) request.getAttribute("fcSesion"));
        }

        if (tipo.equalsIgnoreCase(Globales.TP_SESION_ORDINARIA)
                        || tipo.equalsIgnoreCase(Globales.TP_SESION_CALIFICATORIA)
                        || tipo.equalsIgnoreCase(Globales.TP_SESION_PREPARATORIAS)
                        || tipo.equalsIgnoreCase(Globales.TP_SESION_FORMACION)
                        || tipo.equalsIgnoreCase(Globales.TP_SESION_REUNION)) {
            correcto = sesionesPorTribunalDAO.validarFechaAltaSesion(con, ccb, recuperaSesiones);
        }

        if (!correcto) {
            msgTmp = "La fecha de alta debe ser Mayor que la fecha de Constitución del Tribunal: "
                 + recuperaSesiones.getFcAlta() + ". PULSE CANCELAR";
            correcto = false;
        } else {
            sesionesPorTribunalDAO.busquedaUltimoNmSecuencia(con, ccb, tipo);

            if (request.getParameter("horaInicio") != null && !request.getParameter("horaInicio").equals("")) {
                recuperaSesiones.setTlHoraInicio(request.getParameter("horaInicio"));
            } else if (request.getAttribute("horaInicio") != null && !request.getAttribute("horaInicio").equals("")) {
                recuperaSesiones.setTlHoraInicio((String) request.getAttribute("horaInicio"));
            }

            if (request.getParameter("horaFin") != null && !request.getParameter("horaFin").equals("")) {
                recuperaSesiones.setTlHoraFin(request.getParameter("horaFin"));
            } else if (request.getAttribute("horaFin") != null && !request.getAttribute("horaFin").equals("")) {
                recuperaSesiones.setTlHoraFin((String) request.getAttribute("horaFin"));
            }

            int ultimaSesion = 0;

            ultimaSesion = sesionesPorTribunalDAO.busquedaUltimaSesion(con, ccb, tipo);
            recuperaSesiones.setNmSecuencia(Integer.toString(ultimaSesion));
            // buscamos si existe un una sesión ya creada //

            // jcc83 - control fechas existentes sesiones resto de
            // tribunales

            /*
             * "Un mismo tribunal puede evaluar distintas
             * especialidades, en estos casos funcionará como 'n'
             * tribunales en función de cada una de las especialidades,
             * pero como uno a efectos de control de gastos, es decir se
             * le aplicarán los controles de sesiones y gastos como
             * uno."
             */

            // Ver tabla AGTO_TRIBUNALES_ASOCIADOS
            MiembroTribunalBean tribunal = new MiembroTribunalBean();
            tribunal.setCdConvocatoria(ccb.getConvocatoria());
            tribunal.setCdCuerpo(ccb.getCuerpo());
            tribunal.setCdEspecialidad(ccb.getEspecialidad());
            tribunal.setNmTribunal(Integer.parseInt(ccb.getTribunal()));

            // Comprueba si el tribunal funciona como 'n tribunales' y
            // obtiene el id del grupo
            
            //E_OAR8 - INI - Los tribunales asociados ya no comparten fecha
            int nmGrupo = new TribunalesAgrupadosDAO().obtenerNmGrupoTribunal(con, tribunal);

  //          if (nmGrupo == -1) {
                correcto = sesionesPorTribunalDAO.busquedaSesionesFecha(con, ccb, tipo,
                                recuperaSesiones.getFcCalificacion());
  /*          } else {
                List tribunalesAgrupados = new TribunalesAgrupadosDAO().obtenerTribunalesAsociadosPorNmGrupo(
                                con, nmGrupo, ccb);
                correcto = true;
                for (int i = 0; i < tribunalesAgrupados.size(); i++) {
                    MiembroTribunalBean tribunalAux = (MiembroTribunalBean) tribunalesAgrupados.get(i);

                    CabeceraCalificaBean ccbAux = new CabeceraCalificaBean();
                    ccbAux.setConvocatoria(tribunalAux.getCdConvocatoria());
                    ccbAux.setCuerpo(tribunalAux.getCdCuerpo());
                    ccbAux.setEspecialidad(tribunalAux.getCdEspecialidad());
                    ccbAux.setTribunal(String.valueOf(tribunalAux.getNmTribunal()));
                    ccbAux.setOrgano("01");

                    correcto = correcto && sesionesPorTribunalDAO.busquedaSesionesFecha(
                                    con, ccbAux, tipo, recuperaSesiones.getFcCalificacion());
                }
            }
    */      //E_OAR8 - FIN
            // jcc83

            if (correcto) {
                msgTmp = procesarAltaSesion(tipo, ccb, sesionesPorTribunalDAO, msgTmp, recuperaSesiones, nmGrupo);
            } else {
                msgTmp = "Existe ya una Sesión creada para esa fecha. PULSE CANCELAR";
            }
        }

        if (correcto) {
            recuperados = sesionesPorTribunalDAO.busquedaSesiones(con, ccb, tipo);
            request.setAttribute("detalle", recuperados);
            request.setAttribute("tipo", tipo);
            AyudaDao ayDao = new AyudaDao();
            AyudaBean ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_DET_SES_TRIB);
            request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_DET_SES_TRIB);
            request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);
            nombreVista = "DetalleSesionesTribunal";
        } else {
            request.setAttribute("tipo", tipo);
            request.setAttribute("oper", oper);
            request.setAttribute("secuenciaSelec", request.getParameter("secuenciaSelec"));
            titulo = titulo + "Alta de Sesiones";
            request.setAttribute("fcSesion", request.getParameter("fechaS"));
            request.setAttribute("horaInicio", request.getParameter("horaI"));
            request.setAttribute("horaFin", request.getParameter("horaF"));
            request.setAttribute("itFestivo", request.getParameter("itFestivo"));
            request.setAttribute("msgTmp", msgTmp);
            AyudaDao ayDao = new AyudaDao();
            AyudaBean ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_GEST_SES_TRIB);
            request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_GEST_SES_TRIB);
            request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);
            nombreVista = "AltaBajaModiSesionesTribunal";
        }

        if (sbMsgOut != null) {
            sbMsgOut.append(msgTmp);
        }

        return titulo;
    }

    /**
     * @param tipo tipo
     * @param ccb ccb
     * @param sesionesPorTribunalDAO sesiones
     * @param msgTmp mensaje
     * @param recuperaSesiones sesiones
     * @param nmGrupo grupo
     * @return mensaje
     * @throws DAOException si se produce algún error
     */
    private String procesarAltaSesion(String tipo, CabeceraCalificaBean ccb,
                    SesionesPorTribunalDAO sesionesPorTribunalDAO, String msgTmp,
                    RecuperaSesionesBean recuperaSesiones, int nmGrupo) throws DAOException {
        String nSesiones;
        boolean continuar = true;

        int resultado = sesionesPorTribunalDAO.estaAutorizado(con, ccb, recuperaSesiones.getFcCalificacion());
        if (resultado == 0) {
            continuar = false;
        } else if (resultado == 1) {
            recuperaSesiones.setItFestivo(Globales.ITFESTIVO_SI);
            continuar = true;
        } else if (resultado == -1) {
            recuperaSesiones.setItFestivo(Globales.ITFESTIVO_NO);
            continuar = true;
        }

        if (continuar) {
            int rc = 0;

            /*
             * jcc83 - control de tribunales agrupados:
             * 
             * "Un mismo tribunal puede evaluar distintas
             * especialidades, en estos casos funcionará como
             * 'n' tribunales en función de cada una de las
             * especialidades, pero como uno a efectos de
             * control de gastos, es decir se le aplicarán los
             * controles de sesiones y gastos como uno."
             * 
             * Ver tabla AGTO_TRIBUNALES_ASOCIADOS
             */

            if (nmGrupo == -1) {
                // Si no es así, procede como siempre
                rc = sesionesPorTribunalDAO.validarLimite(con, ccb, tipo);
            } else {
                // Si se trata de un tribunal que funciona como
                // 'n tribunales' es necesario calcular el total
                // de sesiones
                // para poder comparar correctamente con el
                // máximo permitido.
                List tribunalesAgrupados = new TribunalesAgrupadosDAO().obtenerTribunalesAsociadosPorNmGrupo(
                                con, nmGrupo, ccb);
                for (int i = 0; i < tribunalesAgrupados.size(); i++) {
                    MiembroTribunalBean tribunalAux = (MiembroTribunalBean) tribunalesAgrupados.get(i);

                    CabeceraCalificaBean ccbAux = new CabeceraCalificaBean();
                    ccbAux.setConvocatoria(tribunalAux.getCdConvocatoria());
                    ccbAux.setCuerpo(tribunalAux.getCdCuerpo());
                    ccbAux.setEspecialidad(tribunalAux.getCdEspecialidad());
                    ccbAux.setTribunal(String.valueOf(tribunalAux.getNmTribunal()));
                    ccbAux.setOrgano("01");

                    rc += sesionesPorTribunalDAO.validarLimite(con, ccbAux, tipo);
                }
            }
            // jcc83

            // if (nSesiones.equals("")) { // este if era el culpable de la incidencia ;)
            int numSesiones = sesionesPorTribunalDAO.numeroMaximoSesiones(con, ccb, tipo);
            nSesiones = Integer.toString(numSesiones);
            request.setAttribute("nSesiones", nSesiones);
            // }

            if (rc < Integer.parseInt(nSesiones)) {
                // jcc83 - pto 20 - control sesiones miembros
                // por fecha para el alta
                // fecha candidata
                String fcSesion = recuperaSesiones.getFcCalificacion();

                // dni miembros
                List miembros = null;
                if (tipo.equalsIgnoreCase(Globales.TP_SESION_ENTREGA)) {
                    // recupera lista miembros
                    miembros = sesionesPorTribunalDAO.obtenerDNImiembrosParaSesionEntregaDocumentos(con, ccb);
                } else {
                    miembros = sesionesPorTribunalDAO.obtenerDNImiembrosParaSesion(con, ccb); // recupera lista miembros
                }

                String cdEspecialidad = ccb.getEspecialidad();
                for (int i = 0; (i < miembros.size()) && continuar; i++) {
                    // recupera el dni del miembro
                    String dni = (String) miembros.get(i);
                    
                    List lSesionesMiembro = sesionesPorTribunalDAO.buscarSesionesMiembro(con, dni, fcSesion, cdEspecialidad);

                    if (!lSesionesMiembro.isEmpty()) {
                        continuar = false;
                        String nombre = ((RecuperaSesionesBean) lSesionesMiembro.get(0)).getNombre();
                        msgTmp = "Atención: el miembro con DNI " + dni + " (" + nombre
                                        + ")  ya tiene una sesión programada para la fecha " + fcSesion
                                        + ".   PULSE CANCELAR.";
                    }
                }
                //

                if (continuar) {
                    continuar = sesionesPorTribunalDAO.insertarSesion(con, ccb, tipo, recuperaSesiones);
                    if (continuar) {
                        if (!tipo.equalsIgnoreCase(Globales.TP_SESION_ENTREGA)) {
                            if (tipo.equalsIgnoreCase(Globales.TP_SESION_FORMACION)) {
                                // E_ASB48 371500 03/06/2013 INICIO
                                // Nuevo parámetro para la fecha de sesión
                                sesionesPorTribunalDAO.insertarMiembrosSesionFormacion(con, ccb, tipo,
                                                Integer.parseInt(recuperaSesiones.getNmSecuencia()),
                                                recuperaSesiones.getFcCalificacion());
                                // E_ASB48 371500 03/06/2013 FIN
                            } else if (tipo.equalsIgnoreCase(Globales.TP_SESION_REUNION)) {
                                // E_ASB48 371500 03/06/2013 INICIO
                                // Nuevo parámetro para la fecha de sesión
                                sesionesPorTribunalDAO.insertarMiembrosSesionReunionPresidentes(con, ccb, tipo,
                                                Integer.parseInt(recuperaSesiones.getNmSecuencia()),
                                                recuperaSesiones.getFcCalificacion());
                                // E_ASB48 371500 03/06/2013 FIN
                            } else {
                                // E_ASB48 371500 03/06/2013 INICIO
                                // Actualizamos el proceso de inserción de lo 
                                // miembros de la sesión de forma que se tiene en
                                // cuenta las sustituciones y cambios de rol
                                // realizados.
                                // sesionesPorTribunalDAO.insertarMiembrosSesion(con,
                                // ccb, tipo,
                                // Integer.parseInt(recuperaSesiones.getNmSecuencia()));

                                //Buscamos si existen 

                                List<MiembroTribunalBean> lstMiembrosAlta = sesionesPorTribunalDAO
                                                .miembrosSesionesTribunalPorTipo(con, ccb, "C");
                                sesionesPorTribunalDAO.insertarMiembrosActivosSesion(con, ccb, tipo,
                                                Integer.parseInt(recuperaSesiones.getNmSecuencia()),
                                                recuperaSesiones.getFcCalificacion(), lstMiembrosAlta);
                                // E_ASB48 371500 03/06/2013 FIN
                            }
                        } else {
                            // E_ASB48 371500 03/06/2013 INICIO
                            // Nuevo parámetro para la fecha de sesión
                            sesionesPorTribunalDAO.insertarMiembrosSesionEntregaDocumentos(con, ccb, tipo,
                                            Integer.parseInt(recuperaSesiones.getNmSecuencia()),
                                            recuperaSesiones.getFcCalificacion());
                            // E_ASB48 371500 03/06/2013 FIN
                        }
                        msgTmp = "El registro ha sido dado de alta";
                    } else {
                        msgTmp = "No ha podido añadirse el registro. PULSE CANCELAR";
                    }
                }
            } else {
                msgTmp = "Número máximo de sesiones alcanzado. PULSE CANCELAR";
            }
        } else {
            msgTmp = "Al tratarse de un día festivo debe estar autorizado. PULSE CANCELAR";
        }
        return msgTmp;
    }

    /**
     * @param tipo tipo
     * @param msg mensaje
     * @param titulo titulo
     * @param ccb ccb
     * @param sesionesPorTribunalDAO sesiones
     * @param oper oper
     * @return mensaje
     * @throws DAOException si se produce algún error
     */
    private String procesarModificarSesionFormacionEspecial(String tipo, String msg, String titulo,
                    CabeceraCalificaBean ccb, SesionesPorTribunalDAO sesionesPorTribunalDAO, String oper)
                                    throws DAOException {
//        boolean correcto;
        // -------------
        // MODIFICACIÓN SESION FORMACION ESPECIAL.
        ArrayList listaSesiones = new ArrayList();
        RecuperaSesionesBean recuperaSesiones = new RecuperaSesionesBean();
        String tipComparacion = "=";

        recuperaSesiones.setTpSesion(Globales.TP_SESION_FORMACION);
        listaSesiones = sesionesPorTribunalDAO.obtenerFechaSesionMenor(con, ccb, recuperaSesiones, tipComparacion);
        if (listaSesiones.size() > 0) {
            Iterator iterador = listaSesiones.iterator();
            recuperaSesiones = (RecuperaSesionesBean) iterador.next();
            String fcSesion = recuperaSesiones.getFcCalificacion();
            UbicacionTribunalBean ubicacion = sesionesPorTribunalDAO.consultarUbicacionTribunal(con, ccb);
            recuperaSesiones.setItFestivo("N");
            if (sesionesPorTribunalDAO.esFestivo(con, ubicacion, fcSesion)) {
                recuperaSesiones.setItFestivo("S");
            }
            request.setAttribute("modoAuto", request.getParameter("modoAuto"));
            request.setAttribute("secuenciaSelec", request.getParameter("secuenciaSelec"));
            request.setAttribute("horaInicio", recuperaSesiones.getTlHoraInicio());
            request.setAttribute("horaFin", recuperaSesiones.getTlHoraFin());
            request.setAttribute("fcSesion", recuperaSesiones.getFcCalificacion());
            request.setAttribute("itFestivo", recuperaSesiones.getItFestivo());
            request.setAttribute("msg", msg);
            request.setAttribute("cabecera", ccb);
            request.setAttribute("oper", oper);
            tipo = Globales.TP_SESION_FORMACION;
            request.setAttribute("titulo", titulo);
            request.setAttribute("tipo", tipo);
        } else {
            request.setAttribute("mensaje", "No existe sesión de formación. Desde \"Definición de "
                            + "sesiones del tribunal\" en el menú puede darla de alta.");
//                correcto = false;
            nombreVista = "avisogeneral";
        }
        return tipo;
    }

    /**
     * @param tipo tipo
     * @param msg mensaje
     * @param titulo titulo
     * @param ccb ccb
     * @param sesionesPorTribunalDAO sesiones
     * @param oper oper
     * @return mensaje
     * @throws DAOException si se produce algún error
     */
    private String procesarModificarSesionReunionEspecial(String tipo, String msg, String titulo,
                    CabeceraCalificaBean ccb, SesionesPorTribunalDAO sesionesPorTribunalDAO, String oper)
                                    throws DAOException {
        // MODIFICACIÓN SESION REUNION ESPECIAL.
        ArrayList listaSesiones = new ArrayList();
        RecuperaSesionesBean recuperaSesiones = new RecuperaSesionesBean();
        String tipComparacion = "=";

        recuperaSesiones.setTpSesion(tipo);
        listaSesiones = sesionesPorTribunalDAO.obtenerFechaSesionMenor(con, ccb, recuperaSesiones, tipComparacion);

        Iterator iterador = listaSesiones.iterator();
        recuperaSesiones = (RecuperaSesionesBean) iterador.next();
        String fcSesion = recuperaSesiones.getFcCalificacion();
        UbicacionTribunalBean ubicacion = sesionesPorTribunalDAO.consultarUbicacionTribunal(con, ccb);
        recuperaSesiones.setItFestivo("N");
        if (sesionesPorTribunalDAO.esFestivo(con, ubicacion, fcSesion)) {
            recuperaSesiones.setItFestivo("S");
        }
        request.setAttribute("modoAuto", request.getParameter("modoAuto"));
        request.setAttribute("secuenciaSelec", request.getParameter("secuenciaSelec"));
        request.setAttribute("horaInicio", recuperaSesiones.getTlHoraInicio());
        request.setAttribute("horaFin", recuperaSesiones.getTlHoraFin());
        request.setAttribute("fcSesion", recuperaSesiones.getFcCalificacion());
        request.setAttribute("itFestivo", recuperaSesiones.getItFestivo());
        request.setAttribute("msg", msg);
        request.setAttribute("cabecera", ccb);
        request.setAttribute("oper", oper);
        titulo += Util.getDsTipoSesion(tipo);
        request.setAttribute("titulo", titulo);
        request.setAttribute("tipo", tipo);
        return titulo;
    }

    /**
     * busca sesiones
     * 
     * @param ccb ccb 
     * @param tipoSesion tipo sesion
     * @return int
     * @throws DAOException si se produce algún error
     */
    public int busquedaNSesiones(CabeceraCalificaBean ccb, String tipoSesion) throws DAOException {
        // String tipo = "";
        // List recuperados = null;
        List nmSesiones = null;
        int numSesiones = 0;

        tipoSesion = request.getParameter("tipo");
        // recuperados = new SesionesPorTribunalDAO().busquedaSesiones(con, ccb,
        // tipoSesion);
        nmSesiones = new SesionesPorTribunalDAO().comprobarPrimerAcceso(con, ccb);

        for (int i = 0; i < nmSesiones.size(); i++) {
            SesionesTribunalBean sesiones = new SesionesTribunalBean();
            sesiones = (SesionesTribunalBean) nmSesiones.get(i);
            if (tipoSesion.equals("O")) {
                numSesiones = sesiones.getNmSesionesOrdi();
                // tipo = "Ordinaria";
            } else {
                numSesiones = sesiones.getNmSesionesPrep();
                // tipo = "Preparatoria";
            }
        }
        return numSesiones;
    } // fin busquedaNSesiones.
} // FIN DE CLASE.