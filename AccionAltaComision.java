package agto_web.acciones;

import java.util.Vector;

import sistemas.framework.acciones.ClaseAccion;
import sistemas.framework.excepciones.AccionException;
import sistemas.framework.excepciones.DAOException;
import sistemas.util.Trazas;
import agto_web.bean.CabeceraCalificaBean;
import agto_web.bean.RecuperaSesionesBean;
import agto_web.modelo.ComisionDAO;
import agto_web.modelo.SesionesPorTribunalDAO;
import agto_web.util.Globales;
import agto_web.util.Util;

/**
 * Proceso de una alta de comisión
 */
public class AccionAltaComision extends ClaseAccion {

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

        String nombreCampoNifJSP = "";
        String nombreCampoOrdenJSP = "";
        String dni = "";
        String nmTribunalOrigen = "";
        int acumula = 0;
        int secretario = 0;
        boolean estaConstituida = false;
        boolean fechaComposicionNula = false;
        ComisionDAO comDao = new ComisionDAO();

        // Vector v=new Vector();

        try {

            recuperaSesiones.setFcCalificacion(request.getParameter("fecha_compo"));
            recuperaSesiones.setTlHoraInicio(request.getParameter("hora_compo_ini"));
            recuperaSesiones.setTlHoraFin(request.getParameter("hora_compo_fin"));
            recuperaSesiones.setNmSecuencia("1");

//            correcto = this.validarSesion(ccb, recuperaSesiones, Globales.TP_SESION_CONSTITUCION);
            correcto = this.validarSesion(ccb, recuperaSesiones);
            if (correcto) {

                // recogemos quien es el Secretario y la fecha/hora.
                secretario = Integer.parseInt(request.getParameter("secre"));

                // recogemos la fecha/hora.
                String fecha_compo = request.getParameter("fecha_compo");
                String hora_compo_ini = request.getParameter("hora_compo_ini");
                fecha_compo = fecha_compo + " " + hora_compo_ini;

                // comprobamos si está creada. Si lo está borra los miembros
                estaConstituida = comDao.VerSiCreada(con, ccb);

                // si esta creada la comision esta creada en Agto-ubiccacion
                // else debemos de comprobar que esta en Agto-ubicacion
                // si no existe grabamos
                if (estaConstituida) {
                    comDao.BorrarMiembrosComision(con, ccb);
                    try {
                        comDao.AltaComisionUbicacionUpdate(con, ccb, fecha_compo); // ubicación
                    } catch (DAOException e) {
                        throw new AccionException(e);
                    }
                } else {
                    try {
                        fechaComposicionNula = comDao.ComprobarComisionUbicacion(con, ccb);
                        // DEBE haber el registro de la comision en ubicacion
                        // aunque sin fecha hasta que se constituya.
                        // fechaComposicionNula == true -- > no está
                        // constituida.
                        // fechaComposicionNula == false --> está constituida:
                        // no debería entrar por aquí.
                        if (fechaComposicionNula) {
                            fechaComposicionNula = comDao.AltaComisionUbicacionUpdate(con, ccb,
                                    fecha_compo); // ubicacion
                        }
                    } catch (DAOException e1) {
                        throw new AccionException(e1);
                    }
                }

                for (int i = 0; i < 20; i++) {
                    // ++acumula;
                    nombreCampoNifJSP = "DNI" + Integer.toString(i);
                    nombreCampoOrdenJSP = "nmOrden" + Integer.toString(i);

                    // vemos si existe la comision //
                    // si existe la comision borramos todos los miembros //
                    Trazas.impLog("nombreCampoNifJSP: " + nombreCampoNifJSP);
                    Trazas.impLog("nombreCampoOrdenJSP: " + nombreCampoOrdenJSP);
                    Trazas.impLog("request.getParameter(dato): " + request.getParameter("dato"));
                    Trazas.impLog("request.getParameter(" + nombreCampoNifJSP + "): "
                            + request.getParameter(nombreCampoNifJSP));
                    if (request.getParameter("dato") != null) {
                        Trazas.impLog("--------------------------- dato NO ES NULO ---------------------------");
                        if (request.getParameter(nombreCampoNifJSP) != null) {
                            Trazas.impLog("--------------------------- " + nombreCampoNifJSP
                                    + " NO ES NULO ");
                            dni = request.getParameter(nombreCampoNifJSP);
                            nmTribunalOrigen = request.getParameter("nmTribunalOrigen"
                                    + Integer.toString(i));
                            Trazas.impLog("--------------------------- dni:"
                                    + request.getParameter(nombreCampoNifJSP));
                        }
                        Trazas.impLog(nombreCampoNifJSP + " después: " + dni);

                        if (i == 0) {// presidente
                            comDao.altaMiembroComision(con, ccb, dni, ++acumula, "N", "PT",
                                    nmTribunalOrigen);
                        } else {
                            if (i == secretario) {// secretario
                                comDao.altaMiembroComision(con, ccb, dni, ++acumula, "S", "VT",
                                        nmTribunalOrigen);
                            } else {// resto
                                comDao.altaMiembroComision(con, ccb, dni, ++acumula, "N", "VT",
                                        nmTribunalOrigen);
                            }
                        }

                        // no esta relleno el campo de texto //
                    } else {
                        Trazas.impLog("--------------------------- dato SI ES NULO ---------------------------");
                        dni = "";
                        if (request.getParameter(nombreCampoNifJSP) != null) {
                            Trazas.impLog("--------------------------- " + nombreCampoNifJSP
                                    + " NO ES NULO ");
                            dni = request.getParameter(nombreCampoNifJSP);
                            nmTribunalOrigen = request.getParameter("nmTribunalOrigen"
                                    + Integer.toString(i));
                            Trazas.impLog("--------------------------- dni:"
                                    + request.getParameter(nombreCampoNifJSP));
                        }
                        Trazas.impLog(nombreCampoNifJSP + " después: " + dni);

                        if (!dni.equalsIgnoreCase("")) {
                            /*
                             * if(request.getParameter(dato2) != null){
                             * nmOrden=Integer
                             * .parseInt(request.getParameter(dato2)); }
                             */

                            if (i == 0) {
                                comDao.altaMiembroComision(con, ccb, dni, ++acumula, "N", "PT",
                                        nmTribunalOrigen);
                            } else {
                                if (i == secretario) {
                                    comDao.altaMiembroComision(con, ccb, dni, ++acumula, "S", "VT",
                                            nmTribunalOrigen);
                                } else {
                                    comDao.altaMiembroComision(con, ccb, dni, ++acumula, "N", "VT",
                                            nmTribunalOrigen);
                                }
                            }
                        }
                    }
                }// fin FOR

                // alta de la sesión constitutiva.
                correcto = this.altaSesion(ccb, recuperaSesiones, Globales.TP_SESION_CONSTITUCION);
                request.setAttribute("modo", "ALTA"); // Para recoger el valor
                                                      // en la pantalla
                                                      // informativa

            }// fin if validarSesion

            if (!correcto) {
                // Se recarga la comisión y muestra el error.
                AccionComision accionComision = new AccionComision();
                accionComision.iniAccionTribunal(this.con, this.request, this.response);
                return accionComision.procesarRellamada(this.msg);
            }

        } catch (DAOException e) {
            throw new AccionException(e);
        }

        NOMBRE_VISTA = "actualizacion"; // informa que ha ido bien.
        return NOMBRE_VISTA;

    }// fin procesar

    /**
     * da de alta
     * 
     * @param ccb
     * @param recuperaSesiones
     * @throws DAOException
     */
//    private boolean validarSesion(CabeceraCalificaBean ccb, RecuperaSesionesBean recuperaSesiones,
//            String tipo) throws DAOException {
    private boolean validarSesion(CabeceraCalificaBean ccb, RecuperaSesionesBean recuperaSesiones)
            throws DAOException {

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

    /**
     * da de alta
     * 
     * @param ccb
     * @param tipo
     * @throws DAOException
     */
    private boolean altaSesion(CabeceraCalificaBean ccb, RecuperaSesionesBean recuperaSesiones,
            String tipo) throws DAOException {
        SesionesPorTribunalDAO sesionesPorTribunalDAO = new SesionesPorTribunalDAO();
        boolean correcto = true;

        // borro sesión y asistentes
        sesionesPorTribunalDAO.borrarSesion(con, ccb, tipo);
        sesionesPorTribunalDAO.bajaMiembrosSesiones(con, ccb, tipo, 1);

        if (correcto) {

            correcto = sesionesPorTribunalDAO.insertarSesion(con, ccb, tipo, recuperaSesiones);
            if (correcto) {
                sesionesPorTribunalDAO.insertarMiembrosSesion(con, ccb, tipo,
                        Integer.parseInt(recuperaSesiones.getNmSecuencia()));
                this.msg = "ERROR: El registro ha sido dado de alta";
            } else {
                this.msg = "ERROR: No ha podido añadirse el registro";
            }
        }

        request.setAttribute("msg", this.msg);

        return correcto;
    }//

}// FIN DE CLASE.
