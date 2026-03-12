package utils;

import hooks.ReportHooks;
import net.serenitybdd.core.Serenity;

public class EvidenciaUtils {

  private static int contadorPasos = 1;

  public static void registrarCaptura(String paso) {
    String pasoNumerado = contadorPasos++ + ". " + paso;

    // Registrar en tu sistema actual
    ReportHooks.registrarPaso(pasoNumerado);
    try {
      CapturaDePantallaMovil.tomarCapturaPantalla(pasoNumerado);
    } catch (Exception e) {
      System.out.println("La evidencia falló, pero la prueba continúa.");
    }

    // Integrar con Serenity (versión simple)
    Serenity.recordReportData().withTitle(paso).andContents(pasoNumerado);
    Serenity.takeScreenshot();
  }

  public static void reiniciarContador() {
    contadorPasos = 1;
  }
}
