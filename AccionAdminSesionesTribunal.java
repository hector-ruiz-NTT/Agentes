package agto_web.acciones;

import java.util.Vector;

import sistemas.framework.acciones.ClaseAccion;
import sistemas.framework.excepciones.AccionException;
import sistemas.framework.excepciones.DAOException;
import sistemas.util.Trazas;
import agto_web.bean.AyudaBean;
import agto_web.bean.CabeceraCalificaBean;
import agto_web.bean.UbicacionTribunalBean;
import agto_web.modelo.AyudaDao;
import agto_web.modelo.SesionesPorTribunalDAO;
import agto_web.util.AvisoGeneral;
import agto_web.util.Constantes;
import agto_web.util.Util;

/**
 * Mantiene la sesion de tribunal
 */
public class AccionAdminSesionesTribunal extends ClaseAccion {

    /**
     * Proceso de una sesion de tribunal
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
        String nombreVista = "AdminSesionesTribunal";
        //String perfil = "";
        //
        CabeceraCalificaBean ccb = new CabeceraCalificaBean();
        UbicacionTribunalBean ubicacion = null;
        SesionesPorTribunalDAO sesionesPorTribunalDAO = new SesionesPorTribunalDAO();
        String usuario = Util.retornarUsuario(request);
        int numMaxSesionesPreparatorias = 0;
        int numMaxSesionesOrdinarias = 0;
        int numMaxSesionesConstitucion = 0;
        int numMaxSesionesFormacion = 0;
        int numMaxSesionesReunion = 0;
        int numMaxSesionesEntrega = 0;
        int numAutSesionesPreparatorias = 0;
        int numAutSesionesOrdinarias = 0;
        int numAutSesionesConstitucion = 0;
        int numAutSesionesFormacion = 0;
        int numAutSesionesReunion = 0;
        int numAutSesionesEntrega = 0;

        // LEO EL PERFIL
        perfil = Util.leerPerfil(con, this.request);
        request.setAttribute("perfil", perfil);

        // GUARDO/LEO LA SESIï¿½N.
        ccb = Util.guardarSesion(this.con, request.getSession().getId(), this.request, ccb);
        ccb.tracear("tras grabar");

        /*
         * OPERATIVAS:
         */
        String operacion = "";
        if ((request.getParameter("operacion") != null)) {
            operacion = request.getParameter("operacion");
        }

        String organo = "";
        if ((request.getParameter("organo") != null)) {
            organo = request.getParameter("organo");
            ccb.setOrgano(organo);
        }

        Trazas.impLog("getOrgano: " + ccb.getOrgano());
        try {
            if (operacion.equalsIgnoreCase("INICIO")) { // -----------------------------MUESTRO
                                                        // LA PANTALLA

                ubicacion = sesionesPorTribunalDAO.consultarUbicacionTribunal(con, ccb);

                numMaxSesionesPreparatorias = sesionesPorTribunalDAO.numeroMaximoSesionesPorTipo(
                        con, ubicacion, "P");
                numMaxSesionesOrdinarias = sesionesPorTribunalDAO.numeroMaximoSesionesPorTipo(con,
                        ubicacion, "O");
                numMaxSesionesConstitucion = sesionesPorTribunalDAO.numeroMaximoSesionesPorTipo(
                        con, ubicacion, "C");
                numMaxSesionesFormacion = sesionesPorTribunalDAO.numeroMaximoSesionesPorTipo(con,
                        ubicacion, "F");
                numMaxSesionesReunion = sesionesPorTribunalDAO.numeroMaximoSesionesPorTipo(con,
                        ubicacion, "R");
                numMaxSesionesEntrega = sesionesPorTribunalDAO.numeroMaximoSesionesPorTipo(con,
                        ubicacion, "E");

                request.setAttribute("preparatoriaMax",
                        Integer.toString(numMaxSesionesPreparatorias));
                request.setAttribute("ordinariaMax", Integer.toString(numMaxSesionesOrdinarias));
                request.setAttribute("constitucionMax",
                        Integer.toString(numMaxSesionesConstitucion));
                request.setAttribute("formacionMax", Integer.toString(numMaxSesionesFormacion));
                request.setAttribute("reunionMax", Integer.toString(numMaxSesionesReunion));
                request.setAttribute("entregaMax", Integer.toString(numMaxSesionesEntrega));

                numAutSesionesPreparatorias = sesionesPorTribunalDAO.numeroMaximoSesionesPorOrgano(
                        con, ccb, "P");
                numAutSesionesOrdinarias = sesionesPorTribunalDAO.numeroMaximoSesionesPorOrgano(
                        con, ccb, "O");
                numAutSesionesConstitucion = sesionesPorTribunalDAO.numeroMaximoSesionesPorOrgano(
                        con, ccb, "C");
                numAutSesionesFormacion = sesionesPorTribunalDAO.numeroMaximoSesionesPorOrgano(con,
                        ccb, "F");
                numAutSesionesReunion = sesionesPorTribunalDAO.numeroMaximoSesionesPorOrgano(con,
                        ccb, "R");
                numAutSesionesEntrega = sesionesPorTribunalDAO.numeroMaximoSesionesPorOrgano(con,
                        ccb, "E");

                request.setAttribute("preparatoriaAut",
                        Integer.toString(numAutSesionesPreparatorias));
                request.setAttribute("ordinariaAut", Integer.toString(numAutSesionesOrdinarias));
                request.setAttribute("constitucionAut",
                        Integer.toString(numAutSesionesConstitucion));
                request.setAttribute("formacionAut", Integer.toString(numAutSesionesFormacion));
                request.setAttribute("reunionAut", Integer.toString(numAutSesionesReunion));
                request.setAttribute("entregaAut", Integer.toString(numAutSesionesEntrega));

            } else if (operacion.equals("MODIFICAR")) { // ---------------------------------------
                                                       // ACTUALIZA DATOS
                /*
                 * MODIFICACIï¿½N DE Nï¿½MERO DE SESIONES AUTORIZADAS POR
                 * TRIBUNAL.
                 */
                numAutSesionesPreparatorias = Integer.parseInt(request
                        .getParameter("preparatoriaAut"));
                numAutSesionesOrdinarias = Integer.parseInt(request.getParameter("ordinariaAut"));
                numAutSesionesConstitucion = Integer.parseInt(request
                        .getParameter("constitucionAut"));
                numAutSesionesFormacion = Integer.parseInt(request.getParameter("formacionAut"));
                numAutSesionesReunion = Integer.parseInt(request.getParameter("reunionAut"));
                numAutSesionesEntrega = Integer.parseInt(request.getParameter("entregaAut"));

                sesionesPorTribunalDAO.borraSesionesAutorizasTribunal(con, ccb, "P");
                sesionesPorTribunalDAO.insertaSesionesAutorizasTribunal(con, ccb,
                        numAutSesionesPreparatorias, "P", usuario);

                sesionesPorTribunalDAO.borraSesionesAutorizasTribunal(con, ccb, "O");
                sesionesPorTribunalDAO.insertaSesionesAutorizasTribunal(con, ccb,
                        numAutSesionesOrdinarias, "O", usuario);

                sesionesPorTribunalDAO.borraSesionesAutorizasTribunal(con, ccb, "C");
                sesionesPorTribunalDAO.insertaSesionesAutorizasTribunal(con, ccb,
                        numAutSesionesConstitucion, "C", usuario);

                sesionesPorTribunalDAO.borraSesionesAutorizasTribunal(con, ccb, "F");
                sesionesPorTribunalDAO.insertaSesionesAutorizasTribunal(con, ccb,
                        numAutSesionesFormacion, "F", usuario);

                sesionesPorTribunalDAO.borraSesionesAutorizasTribunal(con, ccb, "R");
                sesionesPorTribunalDAO.insertaSesionesAutorizasTribunal(con, ccb,
                        numAutSesionesReunion, "R", usuario);

                sesionesPorTribunalDAO.borraSesionesAutorizasTribunal(con, ccb, "E");
                sesionesPorTribunalDAO.insertaSesionesAutorizasTribunal(con, ccb,
                        numAutSesionesEntrega, "E", usuario);

                // --------------------------------------
                ubicacion = sesionesPorTribunalDAO.consultarUbicacionTribunal(con, ccb);

                numMaxSesionesPreparatorias = sesionesPorTribunalDAO.numeroMaximoSesionesPorTipo(
                        con, ubicacion, "P");
                numMaxSesionesOrdinarias = sesionesPorTribunalDAO.numeroMaximoSesionesPorTipo(con,
                        ubicacion, "O");
                numMaxSesionesConstitucion = sesionesPorTribunalDAO.numeroMaximoSesionesPorTipo(
                        con, ubicacion, "C");
                numMaxSesionesFormacion = sesionesPorTribunalDAO.numeroMaximoSesionesPorTipo(con,
                        ubicacion, "F");
                numMaxSesionesReunion = sesionesPorTribunalDAO.numeroMaximoSesionesPorTipo(con,
                        ubicacion, "R");
                numMaxSesionesEntrega = sesionesPorTribunalDAO.numeroMaximoSesionesPorTipo(con,
                        ubicacion, "E");

                request.setAttribute("preparatoriaMax",
                        Integer.toString(numMaxSesionesPreparatorias));
                request.setAttribute("ordinariaMax", Integer.toString(numMaxSesionesOrdinarias));
                request.setAttribute("constitucionMax",
                        Integer.toString(numMaxSesionesConstitucion));
                request.setAttribute("formacionMax", Integer.toString(numMaxSesionesFormacion));
                request.setAttribute("reunionMax", Integer.toString(numMaxSesionesReunion));
                request.setAttribute("entregaMax", Integer.toString(numMaxSesionesEntrega));

                numAutSesionesPreparatorias = sesionesPorTribunalDAO.numeroMaximoSesionesPorOrgano(
                        con, ccb, "P");
                numAutSesionesOrdinarias = sesionesPorTribunalDAO.numeroMaximoSesionesPorOrgano(
                        con, ccb, "O");
                numAutSesionesConstitucion = sesionesPorTribunalDAO.numeroMaximoSesionesPorOrgano(
                        con, ccb, "C");
                numAutSesionesFormacion = sesionesPorTribunalDAO.numeroMaximoSesionesPorOrgano(con,
                        ccb, "F");
                numAutSesionesReunion = sesionesPorTribunalDAO.numeroMaximoSesionesPorOrgano(con,
                        ccb, "R");
                numAutSesionesEntrega = sesionesPorTribunalDAO.numeroMaximoSesionesPorOrgano(con,
                        ccb, "E");

                request.setAttribute("preparatoriaAut",
                        Integer.toString(numAutSesionesPreparatorias));
                request.setAttribute("ordinariaAut", Integer.toString(numAutSesionesOrdinarias));
                request.setAttribute("constitucionAut",
                        Integer.toString(numAutSesionesConstitucion));
                request.setAttribute("formacionAut", Integer.toString(numAutSesionesFormacion));
                request.setAttribute("reunionAut", Integer.toString(numAutSesionesReunion));
                request.setAttribute("entregaAut", Integer.toString(numAutSesionesEntrega));
                // --------------------------------------
            }
            AyudaDao ayDao = new AyudaDao();
            AyudaBean ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_ADMIN_SESIONES_TRIBUNAL);
            request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_ADMIN_SESIONES_TRIBUNAL);
            request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);

        } catch (DAOException e) {
            throw new AccionException(e);
        }

        request.setAttribute("cabecera", ccb);

        return nombreVista;
    }

} // FIN DE CLASE.