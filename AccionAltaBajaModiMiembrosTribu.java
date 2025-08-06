/*
 * Created on 09-may-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package agto_web.acciones;

import java.util.List;
import java.util.Vector;

import sistemas.framework.acciones.ClaseAccion;
import sistemas.framework.excepciones.AccionException;
import sistemas.framework.excepciones.DAOException;
import sistemas.util.Trazas;
import agto_web.bean.AyudaBean;
import agto_web.bean.CabeceraCalificaBean;
import agto_web.bean.PDFCT_D0D1Bean;
import agto_web.modelo.AltaTribunalDAO;
import agto_web.modelo.AyudaDao;
import agto_web.modelo.CatalogosDAO;
import agto_web.modelo.PDFCT_D0D1DAO;
import agto_web.modelo.SesionesPorTribunalDAO;
import agto_web.util.Constantes;
import agto_web.util.Globales;
import agto_web.util.Util;

/*
 * E_AGM169 - 295276 - 19/12/2011
 * Se corrije el comportamiento de recuperación de datos para un dni
 * cuando existen datos en agto_personal_docente pero no es miembro de ningún tribunal
 */
/* E_ASB48 430601 20/05/2014 
 * Incluimos en los métodos recuperaMiembroRequest(), procesarAlta() y procesarOperacionOtras()
 * el tratamiento de los campos CDIBAN, IBAN y BIC de la tabla AGTO_MIEMBROS_TRIBUNAL.
 */
/**
 * Mantiene las sesiones
 */
public class AccionAltaBajaModiMiembrosTribu extends ClaseAccion {

    /**
     * Proceso de Mantenimiento las sesiones
     * 
     * @return String
     * @throws AccionException si se produce un error
     */
    public String procesar() throws AccionException {
        // -------------------------------------------------------------------------------
        AyudaDao ayDao = new AyudaDao();
        Vector parametros = new Vector();
        parametros.add("sw");
        parametros.add("operacion");
        parametros.add("ejecutar");
        parametros.add("alta");
        parametros.add("fecha_compo");
        parametros.add("hora_compo_ini");
        parametros.add("hora_compo_fin");
        Util.tracearListaParametros(request, parametros);
        String perfil = Util.leerPerfil(con, this.request);
        request.setAttribute("perfil", perfil);
        // -------------------------------------------------------------------------------
        CabeceraCalificaBean ccb = new CabeceraCalificaBean(Globales.TP_ORGANO_TRIBUNAL);
        
        String dni = "";
        String cargo = "";
        String fechaCompo = "";
        String horaCompoIni = "";
        String horaCompoFin = "";
        String flagReadOnly = "";

        // GUARDO/LEO LA SESIÓN.
        ccb = Util.guardarSesion(this.con, request.getSession().getId(), this.request, ccb);
        ccb.tracear("tras entrar");

        if (request.getParameter("fecha_compo") != null) {
            fechaCompo = request.getParameter("fecha_compo");
        } else {
            fechaCompo = "";
        }
        if (request.getParameter("hora_compo_ini") != null) {
            horaCompoIni = request.getParameter("hora_compo_ini");
        } else {
            horaCompoIni = "";
        }
        if (request.getParameter("hora_compo_fin") != null) {
            horaCompoFin = request.getParameter("hora_compo_fin");
        } else {
            horaCompoFin = "";
        }
        if (request.getParameter("flagReadOnly") != null) {
            flagReadOnly = request.getParameter("flagReadOnly");
        } else {
            flagReadOnly = "";
        }
        request.setAttribute("fecha_compo", fechaCompo);
        request.setAttribute("hora_compo_ini", horaCompoIni);
        request.setAttribute("hora_compo_fin", horaCompoFin);
        request.setAttribute("flagReadOnly", flagReadOnly);

        PDFCT_D0D1DAO dao = new PDFCT_D0D1DAO();

        try {
            AyudaBean ayuda;
            ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_ALTA_MIEMB_TRIB);
            request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_ALTA_MIEMB_TRIB);
            request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);
        } catch (DAOException daoe) {
            Trazas.impLog(daoe.getMessage());
            throw new AccionException(daoe);
        }
        String nombreVista = "altaMiembrosTribunal";
        String sw = "";

        if (request.getParameter("sw") != null) {
            Trazas.impLog("---------------sw no es nulo");
            sw = request.getParameter("sw");
        }

        if (sw.equals("1")) {
            Trazas.impLog("---------------sw es 1");
            Vector v = new Vector();
            PDFCT_D0D1Bean datosBean = new PDFCT_D0D1Bean();
            datosBean.setANIO("");
            v.add(datosBean);
            request.setAttribute("recuperados", v);

            // request.setAttribute("fecha_compo", fecha_compo);
            // request.setAttribute("hora_compo_ini", hora_compo_ini);
            // request.setAttribute("hora_compo_fin", hora_compo_fin);
            nombreVista = "altaMiembrosTribunal";
        }

        if (sw.equals("3")) {
//            if (true) {
              throw new AccionException(
                 "Llamada no permitida [sw = 3]. Contacte con el administrador de la aplicación.");
//            }
//            Trazas.impLog("---------------sw es 3");
//            PDFCT_D0D1Bean datosBean = new PDFCT_D0D1Bean();
//            datosBean.setDNI(request.getParameter("dni"));
//            try {
//                new AltaTribunalDAO().BorrarMiembrosTribunal(con, ccb, datosBean);
//                String tpMiembro = new AltaTribunalDAO().SeleccionarTipoMiembro(con,
//                        datosBean.getDNI());
//                int contador = 0;
//                if (tpMiembro.equals("N")) {
//                    Trazas.impLog("---------------paso: 3.1");
//                    contador = new AltaTribunalDAO().ContadorMiembros(con, datosBean.getDNI(),
//                            ccb.getConvocatoria(), Globales.TP_ORGANO_TRIBUNAL);
//                    if (contador == 0) {
//                        new AltaTribunalDAO().BorrarPersonalDocente(con, datosBean.getDNI());
//                    }
//                }
//                // request.setAttribute("ExisteEnOtroTribu",result);
//            } catch (DAOException e1) {
//                throw new AccionException(e1);
//            }
        }

        // Buscamos Datos del si rellenan Dni //
        /*
         * Cuando se llama desde altaMiembrosTribunal.jsp para validar el DNI al
         * hacer onFocusOut viene a esta opción. Como sólo se le llama desde esa
         * vista la devolvemos a ella.
         */
        if (request.getParameter("operacion").equals("BD")) {
            nombreVista = procesarOperacionBD(ccb, dni);
        }

        /*************** FIN DE BUSQUEDA POR DNI ****/

        if (((request.getParameter("operacion") != null)
                || (request.getParameter("ejecutar") != null) 
                || (request.getParameter("alta") != null))
                && (!request.getParameter("operacion").equals("BD"))) {
            nombreVista = procesarOperacionOtras(ccb, dao, nombreVista);

        } // fin if grande.

        Trazas.impLog("----------------------------------NOMBRE_VISTA:" + nombreVista);
        return nombreVista;

    } // fin procesar_OLD

    /**
     * @param ccb CabeceraCalificaBean
     * @param dao PDFCT_D0D1DAO
     * @param nombreVista nombreVista
     * @return vista
     * @throws AccionException si se produce un error
     */
    private String procesarOperacionOtras(CabeceraCalificaBean ccb, PDFCT_D0D1DAO dao,
            String nombreVista) throws AccionException {
        Trazas.impLog("---------------ope NO es BD y alguna de las otras NO es nula");
        Vector v = new Vector();
        PDFCT_D0D1Bean datosBean = recuperaMiembroRequest();
        CabeceraCalificaBean ccbAux = null;
        // E_ASB48 430601 20/05/2014 INICIO
        AltaTribunalDAO altaTribunalDAO = new AltaTribunalDAO();
        boolean datosBancariosCorrectos = true;
        // E_ASB48 430601 20/05/2014 FIN

        v.add(datosBean);
        request.setAttribute("recuperados", v);

        if (request.getParameter("operacion") != null) {
            Trazas.impLog("---------------ope NO es nula");
            if (request.getParameter("operacion").equals("M")) {
                Trazas.impLog("---------------ope es M");
                Trazas.impLog("--------------------------------------------------");
                Trazas.impLog("-- MUESTRA modificarMiembrosTribunal.jsp");
                Trazas.impLog("--------------------------------------------------");
                request.setAttribute("bloquear", "S");
                

                
                Trazas.impExc("NO ES UN ERROR. "+Util.leerNIF(con, request)
                		+" accede a MODIFICACION DE DATOS de "+datosBean.getDNI()+" con perfil: "+request.getAttribute("perfil"));
                
                
                if (datosBean.getCD_TIPO_PUESTO().equalsIgnoreCase("AS")
                        || datosBean.getCD_TIPO_PUESTO().equalsIgnoreCase("CO")) {
                    try {
                        ccbAux = new PDFCT_D0D1DAO().consultaOrganoPropietario(con,
                                datosBean.getDNI(), ccb.getConvocatoria());
                        // Trazas.impLog("------------------- ccbAux.getOrgano(): "
                        // + ccbAux.getOrgano());
                    } catch (DAOException e) {
                        throw new AccionException(e);
                    }
                    if ((!ccbAux.getConvocatoria().equalsIgnoreCase(ccb.getConvocatoria()))
                            || (!ccbAux.getCuerpo().equalsIgnoreCase(ccb.getCuerpo()))
                            || (!ccbAux.getEspecialidad().equalsIgnoreCase(
                                    ccb.getEspecialidad()))
                            || (!ccbAux.getOrgano().equalsIgnoreCase(ccb.getOrgano()))
                            || (!ccbAux.getTribunal().equalsIgnoreCase(ccb.getTribunal()))) {
                        request.setAttribute("msg", Util.crearMsgOrganoPropietario(ccbAux));
                    }
                }

                // request.setAttribute("fecha_compo", fecha_compo);
                // request.setAttribute("hora_compo_ini", hora_compo_ini);
                // request.setAttribute("hora_compo_fin", hora_compo_fin);
                try {
                    AyudaDao ayDao = new AyudaDao();
                    AyudaBean ayuda;
                    ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_MODIF_MIEMB_TRIB);
                    request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_MODIF_MIEMB_TRIB);
                    request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);
                } catch (DAOException daoe) {
                    Trazas.impLog(daoe.getMessage());
                    throw new AccionException(daoe);
                }
                nombreVista = "modificarMiembrosTribunal";
            } else if (request.getParameter("operacion").equals("A")) {
                // 2007: puede venir de sw=1
                Trazas.impLog("---------------ope es A");
                Trazas.impLog("--------------------------------------------------");
                Trazas.impLog("-- MUESTRA altaMiembrosTribunal.jsp");
                Trazas.impLog("--------------------------------------------------");
                datosBean.setDNI("");
                datosBean.setNOMBRE("");
                datosBean.setAPELLIDO1("");
                datosBean.setAPELLIDO2("");
                datosBean.setDsMuni("");
                datosBean.setDS_MUNI("");
                datosBean.setCdMuni("");
                datosBean.setCD_MUNI("");
                datosBean.setCdMuni("");
                datosBean.setCD_MUNI("");
                datosBean.setCD_TIPO_PUESTO("CO"); // más común que asesor
                datosBean.setCategoria("");
                datosBean.setCATEGORIA("");
                datosBean.setItPropietario("");
                // E_ASB48 430601 20/05/2014 INICIO
                datosBean.setCdIban("");
                datosBean.setIban("");
                datosBean.setBic("");
                // E_ASB48 430601 20/05/2014 FIN
                datosBean.setCdbanc("");
                datosBean.setCD_BANC("");
                datosBean.setCdsucu("");
                datosBean.setCD_SUCU("");
                datosBean.setNM_DIG_CONTROL("");
                datosBean.setNM_CUENTA("");
                datosBean.setMATRICULA("");
                datosBean.setTpVia("");
                datosBean.setNumero("");
                datosBean.setBloque("");
                datosBean.setPortal("");
                datosBean.setEscalera("");
                datosBean.setPiso("");
                datosBean.setPuerta("");
                datosBean.setItPropietario("S"); // al inicio, como no ha
                                                 // consultado a BD, va a
                                                 // introducir uno nuevo.
            } 
//           else if (request.getParameter("operacion").equals("B")) {
//                Trazas.impLog("--------------------------------------------------");
//                Trazas.impLog("------------------operacion es B------------------");
//                Trazas.impLog("--------------------------------------------------");
//                try {
//                    String dni = request.getParameter("param_dni");
//                    altaTribunalDAO.borrarMiembrosTribunal(con, ccb, dni);
//                    request.setAttribute("msg", "La baja del miembro del tribunal "
//                                    + dni + "se ha realizado correctamente");
//                    AccionTribunal ac = new AccionTribunal();
//                    ac.inicializar(con, request, response);
//                    return ac.procesar();
//                } catch (DAOException e) {
//                    throw new AccionException(e);
//                }
//            }
        }

        if (request.getParameter("alta") != null) {
            nombreVista = procesarAlta(ccb, dao, nombreVista, datosBean);
        } // fin if NO es nula

        if (request.getParameter("ejecutar") != null) {
            Trazas.impLog("---------------ejecutar NO es nula");
            if (request.getParameter("ejecutar").equals("EM")) {
                Trazas.impLog("---------------ejecutar es EM");
                try {
                    // E_ASB48 430601 20/05/2014 INICIO
                    
                    if (datosBean.getCD_BANC() != null && !datosBean.getCD_BANC().isEmpty()) {
                        // Calculamos el código IBAN completo
                        datosBean.setIban(datosBean.getCdIban() + datosBean.getCdbanc() 
                                + datosBean.getCdsucu() + datosBean.getNm_dig_control()
                                + datosBean.getNM_CUENTA());                    
                        
                        // Validamos que el código IBAN sea correcto
                        datosBancariosCorrectos = altaTribunalDAO.validarIBAN(con, datosBean.getIban()); 
                    }
                    
                    if (!datosBancariosCorrectos) {
                        request.setAttribute("msg", "El número de cuenta informado es incorrecto");
                    } else {
                        // Obtenemos el código BIC correspondiente al banco indicado
                        if (datosBean.getCD_BANC() != null && !datosBean.getCD_BANC().isEmpty()) {
                            datosBean.setBic(new CatalogosDAO()
                                .consultaBIC(con, Integer.parseInt(datosBean.getCD_BANC())));
                        }
                        
                        // E_ASB48 430601 20/05/2014 FIN
                        altaTribunalDAO.modificarMiembrosTribunal(con, ccb, datosBean);
                        request.setAttribute("msg", "Modificación efectuada correctamente");
                    }
                } catch (DAOException e) {
                    throw new AccionException(e);
                }

                List valorest = null;
                PDFCT_D0D1Bean ob = new PDFCT_D0D1Bean();
                try {
                    int numSesionesNoComposicion = new SesionesPorTribunalDAO()
                            .obtenerNumSesionesNoConstitucionPorTribunalComision(con, ccb);
                    request.setAttribute("numSesionesNoComposicion",
                            String.valueOf(numSesionesNoComposicion));

                    int numSesiones = (new PDFCT_D0D1DAO()).tieneSesionesAsignadas(con, ccb);
                    Trazas.impLog("numSesiones: " + numSesiones);
                    if (numSesiones == 0) {
                        valorest = (new PDFCT_D0D1DAO()).busqueda(con, ob, ccb,
                                Globales.TP_ORGANO_TRIBUNAL);
                        request.setAttribute("modo", "ALTA");
                    } else {
                        valorest = (new PDFCT_D0D1DAO()).miembrosTribunalConstituido(con, ob,
                                ccb, Globales.TP_ORGANO_TRIBUNAL);
                        request.setAttribute("modo", "MODIFICACION");
                    }
                } catch (DAOException e) {
                    throw new AccionException(e);
                }
                request.setAttribute("tribunal", valorest);
                request.setAttribute("cabecera", ccb);
                // E_ASB48 430601 20/05/2014 INICIO
                try {
                    if (datosBancariosCorrectos) {
                        AyudaDao ayDao = new AyudaDao();
                        AyudaBean ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_LISTA_TRIBUNAL);
                        request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_LISTA_TRIBUNAL);
                        request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);
                        nombreVista = "lista-tribunal";
                    } else {
                        AyudaDao ayDao = new AyudaDao();
                        AyudaBean ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_MODIF_MIEMB_TRIB);
                        request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_MODIF_MIEMB_TRIB);
                        request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);
                        nombreVista = "modificarMiembrosTribunal";
                    }
                } catch (DAOException daoe) {
                    Trazas.impLog(daoe.getMessage());
                    throw new AccionException(daoe);
                }

                // E_ASB48 430601 20/05/2014 FIN
            } // fin if es EM
        } // fin if NO es nula
        return nombreVista;
    }

    /**
     * @param ccb CabeceraCalificaBean
     * @param dni dni
     * @return vista
     * @throws AccionException si se produce un error
     */
    private String procesarOperacionBD(CabeceraCalificaBean ccb, String dni)
            throws AccionException {
        String cargo;
        String nombreVista;
        Trazas.impLog("---------------ope es BD");
        if (request.getParameter("dniTitular") != null) {
            Trazas.impLog("---------------ope es BD y dniTitular no es nulo ");
            dni = request.getParameter("dniTitular");
            cargo = request.getParameter("cargoC");
            Trazas.impLog("-- cargo: " + cargo);
            Trazas.impLog("--------------------------------------------------");
            Trazas.impLog("-- MUESTRA, DE NUEVO, altaMiembrosTribunal.jsp");
            Trazas.impLog("--------------------------------------------------");
        }

        CabeceraCalificaBean cabeceraBean = null;
        
        List recuperados = null;
        // E_AGM169 - 295276 - 19/12/2011 - INICIO
        List recuperadosSinTribunal = null;
        PDFCT_D0D1DAO pdfctDod1dao = new PDFCT_D0D1DAO();
        boolean sintribunal = false;
        // E_AGM169 - 295276 - 19/12/2011 - FIN
        try {
            recuperados = pdfctDod1dao.busquedaDatosDNI(con, ccb, dni);
            // E_AGM169 - 295276 - 19/12/2011 - INICIO
            if (recuperados.size() == 0) {
                recuperadosSinTribunal = pdfctDod1dao.busquedaDatosDNISinTribunal(con, ccb,
                        dni);
            }
            // E_AGM169 - 295276 - 19/12/2011 - FIN
        } catch (DAOException e) {
            throw new AccionException(e);
        }

        PDFCT_D0D1Bean datosBean = new PDFCT_D0D1Bean();
        // E_AGM169 - 295276 - 19/12/2011 - INICIO
        // if (recuperados.size()>0) {
        // PDFCT_D0D1Bean datosBeanRecuperados =
        // (PDFCT_D0D1Bean)recuperados.get(0);
        if ((recuperados.size() > 0) || (recuperadosSinTribunal.size() > 0)) {
            PDFCT_D0D1Bean datosBeanRecuperados = null;
            if (recuperados.size() > 0) {
                datosBeanRecuperados = (PDFCT_D0D1Bean) recuperados.get(0);
            } else {
                sintribunal = true;
                recuperados = recuperadosSinTribunal;
            }
            if (!sintribunal) {
                // E_AGM169 - 295276 - 19/12/2011 - FIN
                try {
                    datosBeanRecuperados.setDsDomiLoc((new PDFCT_D0D1DAO())
                            .buscarDescripcionLocalidad(con,
                                    datosBeanRecuperados.getCdDomiLoc()));
                } catch (DAOException e) {
                    throw new AccionException(e);
                }

                Trazas.impLog("---------------ope es BD y recuperados>0");
                cargo = datosBeanRecuperados.getCD_TIPO_PUESTO();
                Trazas.impLog("---------------cargo recuperado:" + cargo);
                if (cargo.equalsIgnoreCase("AS") || cargo.equalsIgnoreCase("CO")) {
                    try {
                        cabeceraBean = new PDFCT_D0D1DAO().consultaOrganoPropietario(con, dni, ccb.getConvocatoria());
                        Trazas.impLog("------------------- cabeceraBean.getOrgano(): "
                                + cabeceraBean.getOrgano());
                    } catch (DAOException e) {
                        throw new AccionException(e);
                    }

                    if ((!cabeceraBean.getConvocatoria().equalsIgnoreCase(ccb.getConvocatoria()))
                            || (!cabeceraBean.getCuerpo().equalsIgnoreCase(ccb.getCuerpo()))
                            || (!cabeceraBean.getEspecialidad().equalsIgnoreCase(
                                    ccb.getEspecialidad()))
                            || (!cabeceraBean.getOrgano().equalsIgnoreCase(ccb.getOrgano()))
                            || (!cabeceraBean.getTribunal().equalsIgnoreCase(ccb.getTribunal()))) {
                        request.setAttribute("msg", Util.crearMsgOrganoPropietario(cabeceraBean));
                    } else {
                      request.setAttribute("msg", "Este miembro ya pertenece a este tribunal");
                    }
                } else {
                    request.setAttribute("msg", "Este miembro se encuetra asociado a un"
                            + " tribunal y no puede pertenecer a otro");
                }
                // E_AGM169 - 295276 - 19/12/2011 - INICIO
            }
            // E_AGM169 - 295276 - 19/12/2011 - FIN
            request.setAttribute("recuperados", recuperados);
            request.setAttribute("bloquear", "S");
        } else {
            Trazas.impLog("---------------ope es BD y recuperados < 0");
            recuperados = new Vector();
            datosBean = recuperaMiembroRequest();
            datosBean.setItPropietario("S");
            recuperados.add(datosBean);
            request.setAttribute("recuperados", recuperados);
        }
        // request.setAttribute("fecha_compo", fecha_compo);
        // request.setAttribute("hora_compo_ini", hora_compo_ini);
        // request.setAttribute("hora_compo_fin", hora_compo_fin);

        nombreVista = "altaMiembrosTribunal";
        return nombreVista;
    }

    /**
     * @param ccb CabeceraCalificaBean
     * @param dao PDFCT_D0D1DAO
     * @param nombreVista vista
     * @param datosBean bean
     * @return vista
     * @throws AccionException si se produce un error
     */
    private String procesarAlta(CabeceraCalificaBean ccb, PDFCT_D0D1DAO dao, String nombreVista,
            PDFCT_D0D1Bean datosBean) throws AccionException {
        // E_ASB48 430601 20/05/2014 INICIO
        AltaTribunalDAO altaTribunalDAO = new AltaTribunalDAO();
        boolean datosBancariosCorrectos = true;
        // E_ASB48 430601 20/05/2014 FIN

        Trazas.impLog("---------------alta NO es nula");
        if (request.getParameter("alta").equals("A")) {
            Trazas.impLog("---------------alta es A");
            // request.setAttribute("fecha_compo", fecha_compo);
            // request.setAttribute("hora_compo_ini", hora_compo_ini);
            // request.setAttribute("hora_compo_fin", hora_compo_fin);
            // Trazas.impLog("------2--------fecha_compo:" +
            // fecha_compo);
            // Trazas.impLog("-------2-------hora_compo_ini:" +
            // hora_compo_ini);
            // Trazas.impLog("---------2-----hora_compo_fin:" +
            // hora_compo_fin);
            try {
                AyudaDao ayDao = new AyudaDao();
                AyudaBean ayuda;
                ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_ALTA_MIEMB_TRIB);
                request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_ALTA_MIEMB_TRIB);
                request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);
            } catch (DAOException daoe) {
                Trazas.impLog(daoe.getMessage());
                throw new AccionException(daoe);
            }
            nombreVista = "altaMiembrosTribunal";
        } else {
            Trazas.impLog("---------------alta NO es A");
            if (request.getParameter("alta").equals("EA")) {
                Trazas.impLog("---------------alta es EA");
                try {
                    int contador = 0;
                    contador = new PDFCT_D0D1DAO().validarDocente(con, ccb,
                            datosBean.getDNI());
                    if (contador == 0) {
                        Trazas.impLog("----alta es EA y contador de docentes es cero");

                        contador = new PDFCT_D0D1DAO().validarColaborador(con, ccb,
                                datosBean.getDNI());
                        if (contador == 0) {
                             Trazas.impLog("--alta es EA y contador de colaboradores es cero");
                             String esDocente = "";
                             Trazas.impLog("------ ALTA: getCD_TIPO_PUESTO: "
                                     + datosBean.getCD_TIPO_PUESTO());
                             // E_ASB48 430601 20/05/2014 INICIO
                             if (datosBean.getCdbanc() != null && !datosBean.getCdbanc().isEmpty()) {
                                 // Calculamos el código IBAN completo
                                 datosBean.setIban(datosBean.getCdIban() + datosBean.getCdbanc() 
                                         + datosBean.getCdsucu() + datosBean.getNm_dig_control()
                                         + datosBean.getNM_CUENTA());

                                 // Validamos que el código IBAN sea correcto
                                 datosBancariosCorrectos = altaTribunalDAO.validarIBAN(con, datosBean.getIban()); 
                             }

                             if (!datosBancariosCorrectos) {
                                 request.setAttribute("msg", "El número de cuenta informado es incorrecto");
                             } else {
                                 // Obtenemos el código BIC correspondiente al banco indicado
                                 if (datosBean.getCdbanc() != null && !datosBean.getCdbanc().isEmpty()) {
                                     datosBean.setBic(new CatalogosDAO()
                                       .consultaBIC(con, Integer.parseInt(datosBean.getCdbanc())));
                                 }
        
                                 // E_ASB48 430601 20/05/2014 FIN
                                 altaTribunalDAO.altaMiembrosTribunal(con, ccb, datosBean);
                                 esDocente = dao.esDocente(con, datosBean.getDNI(), ccb.getConvocatoria());
                                 
                                 if (esDocente.equals("FALSO")) {
                                        Trazas.impLog("----alta es EA, contador es cero"
                                                + " Y esDocente es FALSO");
                                        altaTribunalDAO.altaPersonalDocente(con, datosBean, ccb);
                                        
                                 }
                               //E_OAR8 729857 INI
                                 int sesion = altaTribunalDAO.altaSesionPresentadoComprobar(con, ccb, datosBean);
                                 if ("presente".equals(request.getParameter("presente")) && "CO".equals(datosBean.getCD_TIPO_PUESTO())
                                		 && sesion==0){
                                  	altaTribunalDAO.altaSesionPresentado(con,ccb,datosBean);
                                 }
                                 //E_OAR8 729857 FIN
                                 request.setAttribute("msg",
                                            "Miembro asociado correctamente al Tribunal");
                                }
                        } else {
                            request.setAttribute("msg",
                                    "El Colaborador ya forma parte del Tribunal");
                        }
                    } else {
                        Trazas.impLog("-------alta es EA y "
                                + "contador de docentes no es cero");
                        request.setAttribute("msg",
                                "Este miembro se encuetra asociado a un"
                                + " tribunal y no puede pertenecer a otro");
                    }
                } catch (DAOException e) {
                    throw new AccionException(e);
                }

                List valorest = null;
                PDFCT_D0D1Bean ob = new PDFCT_D0D1Bean();
                try {
                    int numSesiones = (new PDFCT_D0D1DAO())
                            .tieneSesionesAsignadas(con, ccb);
                    Trazas.impLog("numSesiones: " + numSesiones);
                    if (numSesiones == 0) {
                        valorest = (new PDFCT_D0D1DAO()).busqueda(con, ob, ccb,
                                Globales.TP_ORGANO_TRIBUNAL);
                        request.setAttribute("modo", "ALTA");
                    } else {
                        valorest = (new PDFCT_D0D1DAO()).miembrosTribunalConstituido(con,
                                ob, ccb, Globales.TP_ORGANO_TRIBUNAL);
                        request.setAttribute("modo", "MODIFICACION");
                    }
                } catch (DAOException e) {
                    throw new AccionException(e);
                }
                request.setAttribute("tribunal", valorest);
                request.setAttribute("cabecera", ccb);
                // E_ASB48 430601 20/05/2014 INICIO
                try {
                    if (datosBancariosCorrectos) {
                        AyudaDao ayDao = new AyudaDao();
                        AyudaBean ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_LISTA_TRIBUNAL);
                        request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_LISTA_TRIBUNAL);
                        request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);
                        nombreVista = "lista-tribunal";
                    } else {
                        AyudaDao ayDao = new AyudaDao();
                        AyudaBean ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_ALTA_MIEMB_TRIB);
                        request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_ALTA_MIEMB_TRIB);
                        request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);
                        nombreVista = "altaMiembrosTribunal";
                    }
                } catch (DAOException daoe) {
                    Trazas.impLog(daoe.getMessage());
                    throw new AccionException(daoe);
                }
                // E_ASB48 430601 20/05/2014 FIN
            } // fin if es EA
        } // fin if NO es A
        return nombreVista;
    }

    /**
     * 
     * @return PDFCT_D0D1Bean
     * @throws AccionException si se produce un error
     */
    private PDFCT_D0D1Bean recuperaMiembroRequest() throws AccionException {

        PDFCT_D0D1Bean datosBean = new PDFCT_D0D1Bean();
        if (request.getParameter("dniTitular") != null) {
            datosBean.setDNI(request.getParameter("dniTitular"));
        }
        if (request.getParameter("nombre") != null) {
            datosBean.setNOMBRE(request.getParameter("nombre"));
        }
        if (request.getParameter("apellido1") != null) {
            datosBean.setAPELLIDO1(request.getParameter("apellido1"));
        }
        if (request.getParameter("apellido2") != null) {
            datosBean.setAPELLIDO2(request.getParameter("apellido2"));
        }
        if (request.getParameter("dsMuni") != null) {
            datosBean.setDsMuni(request.getParameter("dsMuni"));
            datosBean.setDS_MUNI(request.getParameter("dsMuni"));
        }
        if (request.getParameter("cdLocalidad") != null) {
            datosBean.setCdMuni(request.getParameter("cdLocalidad"));
            datosBean.setCD_MUNI(request.getParameter("cdLocalidad"));
        }
        if (request.getParameter("cdMuni") != null) {
            datosBean.setCdMuni(request.getParameter("cdMuni"));
            datosBean.setCD_MUNI(request.getParameter("cdMuni"));
        }
        if (request.getParameter("cargoC") != null) {
            Trazas.impLog("-------- request.getParameter(coCargo): "
                    + request.getParameter("coCargo"));
            datosBean.setCD_TIPO_PUESTO(request.getParameter("cargoC"));
        }
        Trazas.impLog("----------------------------------------- getCD_TIPO_PUESTO: "
                + datosBean.getCD_TIPO_PUESTO());
        if (request.getParameter("ctg") != null) {
            datosBean.setCategoria(request.getParameter("ctg"));
            datosBean.setCATEGORIA(request.getParameter("ctg"));
        }
        if (request.getParameter("propietario") != null) {
            datosBean.setItPropietario(request.getParameter("propietario"));
            Trazas.impLog("--------------- request.getParameter(propietario): "
                    + request.getParameter("propietario"));
        }
        if (request.getParameter("propietarioBis") != null) {
            datosBean.setItPropietario(request.getParameter("propietarioBis"));
            Trazas.impLog("---------------- request.getParameter(propietarioBis): "
                    + request.getParameter("propietarioBis"));
        }
        // E_ASB48 430601 20/05/2014 INICIO
        if (request.getParameter("cdIban") != null) {
            datosBean.setCdIban(request.getParameter("cdIban").toUpperCase());
        }
        // E_ASB48 430601 20/05/2014 FIN

        if (request.getParameter("banco") != null) {
            datosBean.setCdbanc(request.getParameter("banco"));
            datosBean.setCD_BANC(request.getParameter("banco"));
        }
        if (request.getParameter("sucursal") != null) {
            datosBean.setCdsucu(request.getParameter("sucursal"));
            datosBean.setCD_SUCU(request.getParameter("sucursal"));
        }
        if (request.getParameter("dc") != null) {
            datosBean.setNM_DIG_CONTROL(request.getParameter("dc"));
        }
        if (request.getParameter("nCuenta") != null) {
            datosBean.setNM_CUENTA(request.getParameter("nCuenta"));
        }
        if (request.getParameter("matricula2") != null) {
            datosBean.setMATRICULA(request.getParameter("matricula2"));
        }
        // 2007:inicio
        if (request.getParameter("dsDomicilio") != null) {
            datosBean.setDsDomicilio(request.getParameter("dsDomicilio"));
        }
        if (request.getParameter("cdPostal") != null) {
            datosBean.setCdPostal(request.getParameter("cdPostal"));
        }
        //E_OAR8 - INI - Nexus
        if (request.getParameter("tpVia") != null) {
            datosBean.setTpVia(request.getParameter("tpVia"));
        }
        if (request.getParameter("numero") != null) {
            datosBean.setNumero(request.getParameter("numero"));
        }
        if (request.getParameter("bloque") != null) {
            datosBean.setBloque(request.getParameter("bloque"));
        }
        if (request.getParameter("portal") != null) {
            datosBean.setPortal(request.getParameter("portal"));
        }
        if (request.getParameter("escalera") != null) {
            datosBean.setEscalera(request.getParameter("escalera"));
        }
        if (request.getParameter("piso") != null) {
            datosBean.setPiso(request.getParameter("piso"));
        }
        if (request.getParameter("puerta") != null) {
            datosBean.setPuerta(request.getParameter("puerta"));
        }
        
        //E_OAR8 - FIN - Nexus
        if (request.getParameter("cdDomiLoc") != null) {
            datosBean.setCdDomiLoc(request.getParameter("cdDomiLoc"));
            try {
                datosBean.setDsDomiLoc((new PDFCT_D0D1DAO()).buscarDescripcionLocalidad(con,
                        datosBean.getCdDomiLoc()));
            } catch (DAOException e) {
                throw new AccionException(e);
            }
        }
        if (request.getParameter("dsTelefono") != null) {
            datosBean.setDsTelefono(request.getParameter("dsTelefono"));
        }
        if (request.getParameter("otroTelefono") != null) {
            datosBean.setOtroTelefono(request.getParameter("otroTelefono"));
        }
        // 2007:fin

        return datosBean;
    }

} // FIN CLASE.

