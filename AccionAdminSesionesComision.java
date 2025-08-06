/**
 * <p>Paquete: agto_web.acciones</p>
 * <p>Clase: AccionAbrirTribunal</p>
 * <p>Descripcion: Esta funcionalidad permitirá autorizar aumentar
 *  el número de sesiones de una comisión tanto de tipo preparatorio como de tipo ordinario</p>
 * @author Software Everis
 * @version 1.0
 */
package agto_web.acciones;

import sistemas.framework.acciones.ClaseAccion;
import sistemas.framework.excepciones.AccionException;
import sistemas.util.Trazas;
import agto_web.bean.AyudaBean;
import agto_web.bean.CabeceraCalificaBean;
import agto_web.bean.UbicacionTribunalBean;
import agto_web.modelo.AyudaDao;
import agto_web.modelo.SesionesPorTribunalDAO;
import agto_web.util.AvisoGeneral;
import agto_web.util.Constantes;
import agto_web.util.Globales;
import agto_web.util.Util;

/* E_ASB48 373580 24/06/2013
 * Incluimos nuevo tipo de sesión Reunión de Presidentes.
 */
/**
 * Clase que permitirá autorizar aumentar el número de sesiones de una comisión
 */
public class AccionAdminSesionesComision extends ClaseAccion {

    /**
     * Proceso de abrir un tribunal
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
        
        CabeceraCalificaBean ccb = new CabeceraCalificaBean();
        SesionesPorTribunalDAO sesionesPorTribunalDAO = new SesionesPorTribunalDAO();
        String nombreVista = "";
        //String perfil = "";
        String operacion = "";
        int numMaxSesionesPreparatorias = 0;
        int numMaxSesionesOrdinarias = 0;
        int numAutSesionesPreparatorias = 0;
        int numAutSesionesOrdinarias = 0;
        // E_ASB48 373580 24/06/2013 INICIO
        int numMaxSesionesReunion = 0;
        int numAutSesionesReunion = 0;
        // E_ASB48 373580 24/06/2013 FIN
        UbicacionTribunalBean ubicacion = null;
        // LEO EL PERFIL
        perfil = Util.leerPerfil(con, this.request);
        String usuario = Util.retornarUsuario(request);
        request.setAttribute("perfil", perfil);
        // GUARDO/LEO LA SESIÓN.
        ccb = Util.guardarSesion(this.con, request.getSession().getId(), this.request, ccb);
        ccb.tracear("tras entrar");

        operacion = request.getParameter("operacion");
        // if ( (request.getParameter("organo") != null) ) {
        // organo = request.getParameter("organo");
        // ccb.setOrgano(organo);
        // }else{
        ccb.setOrgano(Globales.TP_ORGANO_COMISION);
        ccb.setTribunal("1");
        // }
        try {
            if ("INICIO".equals(operacion)) {
                nombreVista = "AdminSesionesComision";
                ubicacion = sesionesPorTribunalDAO.consultarUbicacionTribunal(con, ccb);
                if (ubicacion.getCdDAT() == null) {
                    request.setAttribute("mensaje", "No existe ninguna Comision");
                    nombreVista = "avisogeneral";
                    return nombreVista;
                }
                numMaxSesionesPreparatorias = sesionesPorTribunalDAO.numeroMaximoSesionesPorTipo(
                        con, ubicacion, "P");
                numMaxSesionesOrdinarias = sesionesPorTribunalDAO.numeroMaximoSesionesPorTipo(con,
                        ubicacion, "O");
                request.setAttribute("preparatoriaMax",
                        Integer.toString(numMaxSesionesPreparatorias));
                request.setAttribute("ordinariaMax", Integer.toString(numMaxSesionesOrdinarias));
                // E_ASB48 373580 24/06/2013 INICIO
                numMaxSesionesReunion = sesionesPorTribunalDAO.numeroMaximoSesionesPorTipo(con,
                        ubicacion, "R");
                request.setAttribute("reunionMax", Integer.toString(numMaxSesionesReunion));
                // E_ASB48 373580 24/06/2013 FIN

                numAutSesionesPreparatorias = sesionesPorTribunalDAO.numeroMaximoSesionesPorOrgano(
                        con, ccb, "P");
                numAutSesionesOrdinarias = sesionesPorTribunalDAO.numeroMaximoSesionesPorOrgano(
                        con, ccb, "O");
                request.setAttribute("preparatoriaAut",
                        Integer.toString(numAutSesionesPreparatorias));
                request.setAttribute("ordinariaAut", Integer.toString(numAutSesionesOrdinarias));
                // E_ASB48 373580 24/06/2013 INICIO
                numAutSesionesReunion = sesionesPorTribunalDAO.numeroMaximoSesionesPorOrgano(con,
                        ccb, "R");
                request.setAttribute("reunionAut", Integer.toString(numAutSesionesReunion));
                // E_ASB48 373580 24/06/2013 FIN
            } else if ("MODIFICAR".equals(operacion)) {
                nombreVista = "AdminSesionesComision";
                numAutSesionesPreparatorias = Integer.parseInt(request
                        .getParameter("preparatoriaAut"));
                numAutSesionesOrdinarias = Integer.parseInt(request.getParameter("ordinariaAut"));
                sesionesPorTribunalDAO.borraSesionesAutorizasTribunal(con, ccb, "P");
                sesionesPorTribunalDAO.insertaSesionesAutorizasTribunal(con, ccb,
                        numAutSesionesPreparatorias, "P", usuario);

                sesionesPorTribunalDAO.borraSesionesAutorizasTribunal(con, ccb, "O");
                sesionesPorTribunalDAO.insertaSesionesAutorizasTribunal(con, ccb,
                        numAutSesionesOrdinarias, "O", usuario);

                // E_ASB48 373580 24/06/2013 INICIO
                numAutSesionesReunion = Integer.parseInt(request.getParameter("reunionAut"));
                sesionesPorTribunalDAO.borraSesionesAutorizasTribunal(con, ccb, "R");
                sesionesPorTribunalDAO.insertaSesionesAutorizasTribunal(con, ccb,
                        numAutSesionesReunion, "R", usuario);
                // E_ASB48 373580 24/06/2013 FIN

                ubicacion = sesionesPorTribunalDAO.consultarUbicacionTribunal(con, ccb);

                numMaxSesionesPreparatorias = sesionesPorTribunalDAO.numeroMaximoSesionesPorTipo(
                        con, ubicacion, "P");
                numMaxSesionesOrdinarias = sesionesPorTribunalDAO.numeroMaximoSesionesPorTipo(con,
                        ubicacion, "O");
                request.setAttribute("preparatoriaMax",
                        Integer.toString(numMaxSesionesPreparatorias));
                request.setAttribute("ordinariaMax", Integer.toString(numMaxSesionesOrdinarias));

                numAutSesionesPreparatorias = sesionesPorTribunalDAO.numeroMaximoSesionesPorOrgano(
                        con, ccb, "P");
                numAutSesionesOrdinarias = sesionesPorTribunalDAO.numeroMaximoSesionesPorOrgano(
                        con, ccb, "O");
                request.setAttribute("preparatoriaAut",
                        Integer.toString(numAutSesionesPreparatorias));
                request.setAttribute("ordinariaAut", Integer.toString(numAutSesionesOrdinarias));

                // E_ASB48 373580 24/06/2013 INICIO
                numMaxSesionesReunion = sesionesPorTribunalDAO.numeroMaximoSesionesPorTipo(con,
                        ubicacion, "R");
                numAutSesionesReunion = sesionesPorTribunalDAO.numeroMaximoSesionesPorOrgano(con,
                        ccb, "R");
                request.setAttribute("reunionMax", Integer.toString(numMaxSesionesReunion));
                request.setAttribute("reunionAut", Integer.toString(numAutSesionesReunion));
                // E_ASB48 373580 24/06/2013 FIN

            } else {
                nombreVista = "error";
            }

            AyudaDao ayDao = new AyudaDao();
            AyudaBean ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_ADMIN_SESIONES_COMISION);
            request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_ADMIN_SESIONES_COMISION);
            request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);
        } catch (Exception e1) {
            Trazas.imprimeErrorExtendido(e1);
            throw new AccionException(e1);
        }

        return nombreVista;
    }

}
