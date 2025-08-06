package agto_web.acciones;

import java.util.ArrayList;

import sistemas.framework.acciones.ClaseAccion;
import sistemas.framework.beans.InfoPaginacionBean;
import sistemas.framework.excepciones.AccionException;
import sistemas.framework.excepciones.DAOException;
import agto_web.bean.AsistentesFormacionBean;
import agto_web.bean.AyudaBean;
import agto_web.modelo.AsistentesFormacionDAO;
import agto_web.modelo.AyudaDao;
import agto_web.util.AvisoGeneral;
import agto_web.util.Constantes;
import agto_web.util.Util;

/**
 * 
 * AccionAltaBajaModiAsistentesFormacion.java
 *
 */
public class AccionAltaBajaModiAsistentesFormacion extends ClaseAccion {

    /**
     * Atributo que contiene el nombre de la vista que mostrará la acción.
     */
    public static final String NOMBRE_VISTA = "AltaBajaModiAsistentesFormacion";

    /** ATTRIB_INFOPAG */
    private static final String ATTRIB_INFOPAG = "infoPaginacion";

    /**
     * 
     */
    private static final String ATTRIB_PAGINA = "pagina";

    /**
     * 
     */
    private static final String ATTRIB_INTERVALO = "intervalo";

    /**
     * 
     */
    private static final String ATTRIB_PAGINAFIN = "paginafin";

    /**
     * Método llamado explícitamente por el servlet Controlador una vez
     * instanciada e inicializada la clase que se desea cargar. Devuelve el
     * nombre lógico de la vista que vaya a mostrar el resultado de la acción.
     * 
     * @throws AccionException si ocurre algún error durante la ejecución del
     *             método.
     * @return NOMBRE_VISTA.
     * @throws DAOException
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

        String strEntrada = request.getParameter("entrada");
        ArrayList aSalida = new ArrayList();
        AsistentesFormacionDAO dao = new AsistentesFormacionDAO();
        AsistentesFormacionBean nuevoBean = new AsistentesFormacionBean();
        String [] aNif;

        try {

            if (strEntrada != null) {
                if (request.getParameter("hidAccion").equals("Alta")) {

                    String nif = request.getParameter("txtDni2");
                    String tribunal = request.getParameter("txtTribunal2");
                    String convocatoria = request.getParameter("txtConvocatoria2");
                    String especialidad = request.getParameter("txtEspecialidad2");
                    String cuerpo = request.getParameter("txtCuerpo2");
                    String organo = request.getParameter("txtOrgano2");

                    int tribunalFinal = 0;
                    if (!tribunal.equals("")) {
                        tribunalFinal = Integer.parseInt(tribunal);
                    }

                    nuevoBean.setStrNif(nif);
                    nuevoBean.setNumTribunal(tribunalFinal);
                    nuevoBean.setStrConvocatoria(convocatoria);
                    nuevoBean.setStrEspecialidad(especialidad);
                    nuevoBean.setStrCuerpo(cuerpo);
                    nuevoBean.setStrOrgano(organo);

                    if (!dao.dniAsistenteCorrecto(con, nuevoBean)
                            || !dao.cuerpoCorrecto(con, nuevoBean)
                            || !dao.especialidadCorrecta(con, nuevoBean)) {
                        request.setAttribute("altaKO", "true");
                    } else {
                        if (dao.altaAsistente(con, nuevoBean, Util.retornarUsuario(request))) {
                            // Cogemos el nombre y el apellido para el alert
                            AsistentesFormacionBean afb = new AsistentesFormacionBean();
                            afb = dao.consultaAsistente(con, nif, convocatoria);

                            request.setAttribute("altaOk", "true");
                            request.setAttribute("afb", afb);
                        } else {
                            request.setAttribute("altaKO", "true");
                        }
                    }

                } else if (request.getParameter("hidAccion").equals("Eliminar")) {

                    aNif = request.getParameter("seleccionAux").split(",");
                    int i = 0;

                    while (aNif.length > i) {

                        dao.bajaAsistente(con, aNif[i]);
                        i++;
                    }
                    request.setAttribute("bajaOk", "true");

                } else if (request.getParameter("hidAccion").equals("Modificar")) {

                    aNif = request.getParameter("seleccionAux").split(",");

                    int i = 0;
                    int erroneos = 0;
                    ArrayList dnisErroneos = new ArrayList();

                    while (aNif.length > i) {

                        String dniOld = request.getParameter("dni_old_" + aNif[i]);
                        String tribunalOld = request.getParameter("tribu_old_" + aNif[i]);
                        int tribunalFinal = Integer.parseInt(tribunalOld);
                        String cuerpoOld = request.getParameter("cuerpo_old_" + aNif[i]);
                        String especialidadOld = request.getParameter("espe_old_" + aNif[i]);

                        String dni = request.getParameter("dni_" + aNif[i]);
                        int tribunal = Integer.parseInt(request.getParameter("tribu_" + aNif[i]));
                        String cuerpo = request.getParameter("cuerpo_" + aNif[i]);
                        String especialidad = request.getParameter("espe_" + aNif[i]);
                        String convocatoria = request.getParameter("con_" + aNif[i]);
                        String organo = request.getParameter("organo_old");

                        nuevoBean.setStrNif(dni);
                        nuevoBean.setNumTribunal(tribunal);
                        nuevoBean.setStrCuerpo(cuerpo);
                        nuevoBean.setStrEspecialidad(especialidad);
                        nuevoBean.setStrConvocatoria(convocatoria);
                        nuevoBean.setStrOrgano(organo);

                        if (!dao.dniAsistenteCorrecto(con, nuevoBean)
                                || !dao.cuerpoCorrecto(con, nuevoBean)
                                || !dao.especialidadCorrecta(con, nuevoBean)) {
                            erroneos++;
                            dnisErroneos.add(dni.toString());
                        } else {
                            dao.modificar(con, dniOld, tribunalFinal, especialidadOld,
                                    cuerpoOld, nuevoBean, Util.retornarUsuario(request));
                        }

                        i++;
                    }
                    if (erroneos == 0) {
                        request.setAttribute("modificacionOk", "true");
                    } else {
                        request.setAttribute("modificacionKO", "true");
                        if (erroneos == 1) {
                            request.setAttribute("erroneos", "El participante con DNI " + dnisErroneos.get(0)
                                            + " tiene mal informado el cuerpo, la especialidad"
                                            + " o no existe nadie con DNI " + dnisErroneos.get(0)
                                            + " dado de alta como personal docente.");
                        } else {
                            String error = "Los participantes con DNI: ";
                            for (int j = 0; j < dnisErroneos.size(); j++) {
                                if (j < (dnisErroneos.size() - 1)) {
                                    error = error.concat(dnisErroneos.get(j) + ", ");
                                } else {
                                    error = error.concat(dnisErroneos.get(j) + " ");
                                }
                            }
                            error = error.concat("tienen mal informado el cuerpo, la especialidad o no se "
                                            + "encuentran dados de alta como personal docente.");
                            request.setAttribute("erroneos", error);
                        }
                    }

                } else {

                    String nif = request.getParameter("txtDni");
                    String tribunal = request.getParameter("txtTribunal");
                    String convocatoria = request.getParameter("txtConvocatoria");
                    String especialidad = request.getParameter("txtEspecialidad");
                    String cuerpo = request.getParameter("cuerpo");
                    String organo = request.getParameter("txtOrgano");

                    // Filtros de nuevo
                    request.setAttribute("filtroConvocatoria", request.getParameter("txtConvocatoria"));
                    request.setAttribute("filtroDescConvocatoria", request.getParameter("dConvocatoria"));
                    request.setAttribute("filtroDni", request.getParameter("txtDni"));
                    request.setAttribute("filtroEspecialidad",
                            request.getParameter("txtEspecialidad"));
                    request.setAttribute("filtroDescEspecialidad",
                            request.getParameter("dEspecialidad"));
                    request.setAttribute("filtroCuerpo", request.getParameter("cuerpo"));
                    request.setAttribute("filtroDescCuerpo", request.getParameter("dCuerpo"));
                    request.setAttribute("filtroTribunal", request.getParameter("txtTribunal"));

                    int tribunalFinal = 0;

                    if (!tribunal.equals("")) {

                        tribunalFinal = Integer.parseInt(tribunal);
                    }

                    nuevoBean.setStrNif(nif);
                    nuevoBean.setNumTribunal(tribunalFinal);
                    nuevoBean.setStrConvocatoria(convocatoria);
                    nuevoBean.setStrEspecialidad(especialidad);
                    nuevoBean.setStrCuerpo(cuerpo);
                    nuevoBean.setStrOrgano(organo);

                    // Configuramos el comportamiento de la paginacion
                    InfoPaginacionBean infoPaginacion = InfoPaginacionBean
                            .getInfoPaginacion(request);
                    infoPaginacion.setIntervalo(10);

                    aSalida = dao.consultaFiltro(con, nuevoBean, infoPaginacion);

                    // Paginación.
                    request.getSession(true).setAttribute(ATTRIB_INFOPAG, infoPaginacion);
                    int iPaginaActual = infoPaginacion.getPosActual();
                    request.setAttribute(ATTRIB_PAGINA, String.valueOf(iPaginaActual));
                    int intervalo = infoPaginacion.getTotal();
                    request.setAttribute(ATTRIB_INTERVALO, String.valueOf(intervalo));
                    int paginafin = iPaginaActual + 10;
                    if ((iPaginaActual + 10) > intervalo) {
                        paginafin = intervalo;
                    }
                    request.setAttribute(ATTRIB_PAGINAFIN, String.valueOf(paginafin));

                    request.setAttribute("array", aSalida);

                    request.setAttribute("sel", request.getParameter("seleccion"));

                }
            }
            AyudaDao ayDao = new AyudaDao();
            AyudaBean ayuda = ayDao.consultaAyudaPantalla(con, Constantes.ID_PANT_GEST_ASIST_FORMAC);
            request.setAttribute("idPantallaAyuda", Constantes.ID_PANT_GEST_ASIST_FORMAC);
            request.setAttribute("hayAyuda", ayuda.getDsAyuda() != null);

        } catch (DAOException ex) {
            throw new AccionException("Se ha producido un error en la base de datos");
        }

        request.setAttribute("ESTADO_MENU", request.getParameter("ESTADO_MENU"));

        return NOMBRE_VISTA;
    }

    /**
     * Método "destructor" de la clase, aquí se deben de restablecer los valores
     * a las variables globales de la clase, de lo contrario tendríamos graves
     * fallos en nuestra aplicación.
     */

    public void clear() {

    }

}
