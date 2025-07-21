package utils;

import hooks.ReportHooks;

public class EvidenciaUtils {

    public static void registrarCaptura(String paso) {
        ReportHooks.registrarPaso(paso);
        CapturaDePantallaMovil.tomarCapturaPantalla(paso);
    }
}