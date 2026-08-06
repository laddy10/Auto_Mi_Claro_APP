package utils;

import io.appium.java_client.android.AndroidDriver;
import net.serenitybdd.screenplay.Actor;

/**
 * Reinicia por completo la app (limpia sesión/datos) para escenarios que deben iniciar de cero,
 * incluso cuando la capability global es {@code noReset=true}.
 *
 * <p>IMPORTANTE: obtiene el driver REAL desde el actor ({@link AndroidObject#androidDriver}), porque
 * en este proyecto Serenity crea el driver con {@code webdriver.driver = appium} (no con MyDriver).
 * Por eso {@code MyDriver.getDriver()} devuelve null y no debe usarse aquí.
 */
public class AppReset {

  private static final String PACKAGE = "com.clarocolombia.miclaro";

  private AppReset() {}

  /** Reinicia la app usando el driver del actor (resetApp; si falla, reinstala). */
  public static void reiniciarApp(Actor actor) {
    reiniciarApp(obtenerDriver(actor));
  }

  /** Alternativa MAS FUERTE: reinstala la app (usar si resetApp no limpia la sesion recordada). */
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
        // resetApp normalmente relanza la app; activateApp es solo por si acaso
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
