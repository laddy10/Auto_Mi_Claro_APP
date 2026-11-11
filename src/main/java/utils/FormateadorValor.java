package utils;

public class FormateadorValor {

  public static String aFormatoCmax(String valor) {
    if (valor == null) {
      return "";
    }
    return valor.replace("\u00a0", " ").trim();
  }
}
