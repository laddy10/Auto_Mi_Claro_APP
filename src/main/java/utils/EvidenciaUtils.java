package utils;

import hooks.ReportHooks;

public class EvidenciaUtils {

    private static int contadorPasos = 1;

    public static void registrarCaptura(String paso) {
        String pasoNumerado = contadorPasos++ + ". " + paso;
        ReportHooks.registrarPaso(pasoNumerado);
        CapturaDePantallaMovil.tomarCapturaPantalla(pasoNumerado);
    }

    public static void reiniciarContador() {
        contadorPasos = 1;
    }
}
