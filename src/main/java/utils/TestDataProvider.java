package utils;

import models.User;

public class TestDataProvider {
  public static User getRealUser() {
    return CuentaManager.getCuentaActiva();
  }

  public static User getUsuario(String id) {
    return CuentaManager.getCuenta(id);
  }
}
