package utils;

public class EstadoPrueba {
  public static boolean fallo = false;
  public static String pasoFallido = "";
  public static String descripcionError = "";
  public static long inicio = 0L;
  public static long fin = 0L;

  /**
   * Guarda el mensaje del error la PRIMERA vez que ocurre en el escenario (la causa raíz). Las
   * llamadas posteriores no lo sobrescriben, para no perder el error original con fallos en cascada.
   */
  public static void capturarError(String msg) {
    if ((descripcionError == null || descripcionError.isEmpty())
        && msg != null
        && !msg.trim().isEmpty()) {
      descripcionError = msg.trim();
    }
  }

  public static void reset() {
    fallo = false;
    pasoFallido = null;
    descripcionError = "";
    inicio = System.currentTimeMillis();
    fin = 0;
  }
}
