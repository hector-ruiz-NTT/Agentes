package agto_web.acciones;

import java.util.Vector;

import javax.servlet.http.HttpSession;

import sistemas.framework.acciones.ClaseAccion;
import sistemas.framework.excepciones.AccionException;
import sistemas.framework.excepciones.DAOException;
import sistemas.util.Trazas;
import agto_web.bean.AyudaBean;
import agto_web.bean.CabeceraCalificaBean;
import agto_web.bean.ConsultaConvoBean;
import agto_web.modelo.AyudaDao;
import agto_web.modelo.ConsultaConvoDAO;
import agto_web.modelo.ConsultasASession;
import agto_web.util.Constantes;
import agto_web.util.Globales;
import agto_web.util.Util;

/**
 * Mantiene los accesos a la app
 */
public class AccionAccesoAdmin extends ClaseAccion {

    /**
     * Proceso de un acceso
     * 
     * @return String
     * @throws AccionException
     */
    public String procesar() throws AccionException {
        // -------------------------------------------------------------------------------
        Vector parametros = new Vector();
        parametros.add("convocatoria");
        parametros.add("dConvocatoria");
        parametros.add("cuerpo");
        parametros.add("especialidad");
        parametros.add("organo");
        parametros.add("tribunal");
        Util.tracearListaParametros(request, parametros);
        // -------------------------------------------------------------------------------
        String NOMBRE_VISTA = "acceso_admin";
        ConsultaConvoBean cconb = new ConsultaConvoBean();
        ConsultaConvoDAO ccondao = new ConsultaConvoDAO();
        String usuario = "";
        HttpSession session = request.getSession();
        String IdSesion = session.getId();
        // Util util =new Util();
        CabeceraCalificaBean ccb = new CabeceraCalificaBean(Globales.TP_ORGANO_TRIBUNAL);

        Trazas.impLog("request.getParameter(convocatoria): " + request.getParameter("convocatoria"));

        usuario = Util.retornarUsuario(request);
        if (request.getParameter("convocatoria") != null) {
            Trazas.impLog("CON CONVOCAT");
            ccb.setConvocatoria(request.getParameter("convocatoria"));
            ccb.setdConvocatoria(request.getParameter("dConvocatoria"));
            ccb.setCuerpo(request.getParameter("cuerpo"));
            ccb.setdCuerpo(request.getParameter("dCuerpo"));
            ccb.setEspecialidad(request.getParameter("especialidad"));
            ccb.setdEspecialidad(request.getParameter("dEspecialidad"));
            ccb.setOrgano(request.getParameter("organo"));
            ccb.setTribunal(request.getParameter("tribunal"));
            int nSesiones = 0;
            try {
                nSesiones = new ConsultasASession().ExisteId(con, IdSesion);

                if (nSesiones == 0) {
                    new ConsultasASession().grabarUsuarioBean(con, ccb, IdSesion, usuario);
                } else {
                    new ConsultasASession().updateUsuarioBean(con, ccb, IdSesion, usuario);
                }
            } catch (DAOException e) {
                throw new AccionException(e);
            }

            NOMBRE_VISTA = "mensajeAdministrador";
        } else {
            try {
                AyudaDao ayDao = new AyudaDao();
                AyudaBean ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_ACCESO_ADMIN);
                request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_ACCESO_ADMIN);
                request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);
            } catch (DAOException daoe) {
                Trazas.impLog(daoe.getMessage());
                throw new AccionException(daoe);
            }
    
            Trazas.impLog("SIN CONVOCAT");
            NOMBRE_VISTA = "acceso_admin_sec";
//            try {
//                cconb = ccondao.ConsultaConvo(con);

//                if (cconb.getItgrupo().equals("P")) {
//                    NOMBRE_VISTA = "acceso_admin_pri";
//                } else if (cconb.getItgrupo().equals("S")) {
//                    NOMBRE_VISTA = "acceso_admin_sec";
//                }

//            } catch (DAOException e) {
//                throw new AccionException(e);
//            }
//            request.setAttribute("convocatorias", cconb);
        }
        Trazas.impLog("NOMBRE_VISTA: " + NOMBRE_VISTA);
        return NOMBRE_VISTA;
    }

}// FIN DE CLASE.
