package tasks.PagosYConsultas.DetalleDeTuPlan;

public final class RoamingTestLines {

  private RoamingTestLines() {}

  public static String configured(String property, String defaultValue) {
    String number = System.getProperty(property, defaultValue).replaceAll("\\s+", "");
    if (!number.matches("\\+?\\d{7,15}")) {
      throw new IllegalArgumentException(
          "El número configurado para " + property + " debe tener entre 7 y 15 dígitos");
    }
    return number;
  }
}
