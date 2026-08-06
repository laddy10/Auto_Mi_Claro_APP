package utils;

import models.User;

/**
 * Provee los datos del usuario de prueba. Se CONSERVA con la misma firma. getRealUser() devuelve la
 * instancia viva de la cuenta activa (por defecto: principal).
 */
public class TestDataProvider {

  public static User getRealUser() {
    return CuentaManager.getCuentaActiva();
  }

  public static User getUsuario(String id) {
    return CuentaManager.getCuenta(id);
  }
}
