package utils;

import io.appium.java_client.android.AndroidDriver;
import net.serenitybdd.screenplay.Actor;

/**
 * Reinicia por completo la app (limpia sesión/datos) aunque la capability global sea noReset=true.
 * Obtiene el driver REAL desde el actor (AndroidObject.androidDriver): el driver lo crea Serenity con
 * webdriver.driver=appium (no MyDriver), por eso MyDriver.getDriver() es null y no se usa aquí.
 */
public class AppReset {

  private static final String PACKAGE = "com.clarocolombia.miclaro";

  private AppReset() {}

  public static void reiniciarApp(Actor actor) {
    reiniciarApp(obtenerDriver(actor));
  }

  public static void reinstalarApp(Actor actor) {
    reinstalar(obtenerDriver(actor));
  }

  @SuppressWarnings("deprecation")
  public static void reiniciarApp(AndroidDriver driver) {
    if (driver == null) {
      System.err.println("\u26A0\uFE0F [AppReset] Driver no disponible; no se pudo reiniciar la app.");
      return;
    }
    try {
      driver.resetApp();
      try {
        driver.activateApp(PACKAGE);
      } catch (Throwable ignore) {
        // resetApp normalmente relanza la app
      }
      System.out.println("\u267B\uFE0F [AppReset] App reiniciada con resetApp() (sesion limpia).");
    } catch (Throwable t) {
      System.err.println("\u26A0\uFE0F [AppReset] resetApp() fallo (" + t.getMessage() + "). Reinstalando...");
      reinstalar(driver);
    }
  }

  private static AndroidDriver obtenerDriver(Actor actor) {
    try {
      return AndroidObject.androidDriver(actor);
    } catch (Exception e) {
      System.err.println("\u26A0\uFE0F [AppReset] No se pudo obtener el driver del actor: " + e.getMessage());
      return null;
    }
  }

  private static void reinstalar(AndroidDriver driver) {
    if (driver == null) {
      System.err.println("\u26A0\uFE0F [AppReset] Driver no disponible; no se pudo reinstalar la app.");
      return;
    }
    String app =
        System.getProperty("app", System.getProperty("user.dir") + "/src/test/resources/app/mi-claro.apk");
    try {
      if (driver.isAppInstalled(PACKAGE)) {
        driver.removeApp(PACKAGE);
      }
      driver.installApp(app);
      driver.activateApp(PACKAGE);
      System.out.println("\u267B\uFE0F [AppReset] App reinstalada y activada (sesion limpia).");
    } catch (Throwable t) {
      throw new RuntimeException("No se pudo reinstalar la app: " + t.getMessage(), t);
    }
  }
}
