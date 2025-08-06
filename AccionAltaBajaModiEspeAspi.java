/*
 * Created on 22-may-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package agto_web.acciones;

import java.util.List;

import sistemas.framework.acciones.ClaseAccion;
import sistemas.framework.excepciones.AccionException;
import sistemas.framework.excepciones.DAOException;
import sistemas.util.Trazas;
import agto_web.modelo.LimiteSesionesXEspeAspiDAO;

/**
 * @author Administrador
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class AccionAltaBajaModiEspeAspi extends ClaseAccion {

    /**
     * Nombre de la vista a devolver
     */
    private static String nombreVista = "editAspiranteEspe";

    /**
     * Método de proceso
     * 
     * @return String
     * @throws AccionException
     */
    public String procesar() throws AccionException {
        LimiteSesionesXEspeAspiDAO dao = new LimiteSesionesXEspeAspiDAO();
        String flag = "false";
        String cod_cuerpo = "";
        String des_cuerpo = "";
        String cod_espe = "";
        String des_espe = "";

        if (request.getParameter("param_cod_cuerpo") != null) {
            cod_cuerpo = request.getParameter("param_cod_cuerpo");
        }
        if (request.getParameter("param_des_cuerpo") != null) {
            des_cuerpo = request.getParameter("param_des_cuerpo");
        }
        if (request.getParameter("param_cod_espe") != null) {
            cod_espe = request.getParameter("param_cod_espe");
        }
        if (request.getParameter("param_des_espe") != null) {
            des_espe = request.getParameter("param_des_espe");
        }

        request.setAttribute("cod_cuerpo", cod_cuerpo);
        request.setAttribute("des_cuerpo", des_cuerpo);
        request.setAttribute("cod_espe", cod_espe);
        request.setAttribute("des_espe", des_espe);

        List resultadosRangos = null;
        try {
            resultadosRangos = dao.consultaRangosAspi(con, cod_espe, cod_cuerpo);
            request.setAttribute("resultadosRangos", resultadosRangos);
        } catch (DAOException e) {
            Trazas.imprimeErrorExtendido(e);
        }
        if (request.getParameter("flag") != null) {
            flag = request.getParameter("flag");
            request.setAttribute("flag", flag);
        }
        if (request.getParameter("sw") != null) {
            String sw = request.getParameter("sw");
            request.setAttribute("sw", sw);
        }

        return nombreVista;
    }
}
