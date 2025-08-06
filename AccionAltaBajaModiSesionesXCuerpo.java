/*
 * Created on 17-may-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package agto_web.acciones;

import sistemas.framework.acciones.ClaseAccion;
import sistemas.framework.excepciones.AccionException;

/**
 * @author Administrador
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class AccionAltaBajaModiSesionesXCuerpo extends ClaseAccion {

    /**
     * Método de proceso
     * 
     * @return String
     * @throws AccionException
     */
    public String procesar() throws AccionException {

        String NOMBRE_VISTA = "AltaBajaModiSesionesXCuerpo";

        String var = request.getParameter("params");
        String[] temp = null;
        temp = var.split(";");
        String sw = temp[0];
        if ((sw.equals("2")) || (sw.equals("3"))) {
            // baja y modificación
            String cod = temp[1];
            String descripcion = temp[2];
            String nmsesiones = temp[3];
            if ((cod != null) && (cod.length() > 0)) {
                request.setAttribute("cod", cod);
                request.setAttribute("descripcion", descripcion);
                request.setAttribute("nmsesiones", nmsesiones);
            }
        }
        request.setAttribute("sw", sw);
        return NOMBRE_VISTA;
    }
}
