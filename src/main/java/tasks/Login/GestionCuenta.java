package tasks.Login;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static userinterfaces.LoginPage.*;

import io.appium.java_client.android.AndroidDriver;
import java.util.concurrent.TimeUnit;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.By;
import utils.AndroidObject;
import utils.AppReset;
import utils.CuentaManager;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

/**
 * Orquesta el inicio de sesión según la cuenta del escenario, minimizando reinicios.
 *
 * <p>OPTIMIZACIÓN DE TIEMPOS: durante TODO el login se baja el implicitWait a 2s (por defecto es 10s)
 * y las esperas se hacen explícitas y acotadas. Así, cuando la app se cierra o cambia de pantalla, la
 * validación falla en segundos en vez de colgarse 10-15 min. Al terminar se restaura a 10s.
 *
 * <p>Como todos los sub-pasos (menú, reset, PreparacionApp, IngresoSuperApp, ReingresoRelogin) corren
 * dentro de esta tarea, heredan el implicitWait bajo; por eso NO deben manejar el implicitWait ellos
 * mismos.
 */
public class GestionCuenta implements Task {

  private final User user = TestDataProvider.getRealUser();
  private static final String PRINCIPAL = "principal";
  private static final int IMPLICIT_LOGIN = 2; // segundos, durante el login
  private static final int IMPLICIT_NORMAL = 10; // segundos, valor normal

  private static final Target BTN_CERRAR_MENU =
      Target.the("Cerrar menú del perfil")
          .located(By.id("com.clarocolombia.miclaro:id/iv_close"));

  @Override
  public <T extends Actor> void performAs(T actor) {
    AndroidDriver driver = obtenerDriver(actor);
    setImplicit(driver, IMPLICIT_LOGIN);
    try {
      ejecutar(actor);
    } finally {
      setImplicit(driver, IMPLICIT_NORMAL);
    }
  }

  private <T extends Actor> void ejecutar(T actor) {
    String targetId = CuentaManager.getIdCuentaActiva();
    String objetivo = user.getNombreUsuario();
    EvidenciaUtils.registrarCaptura("Gestión de cuenta | objetivo: " + targetId + " (" + objetivo + ")");

    // Cierra pantallas de arranque (carrusel "Omitir", condiciones, permisos, publicidad) por si la
    // app se abrió en alguna de ellas, aunque este caso no haya pedido reinicio.
    actor.attemptsTo(PreparacionApp.preparar());

    esperarEstado(actor);
    boolean sesion = haySesion(actor);

    if (sesion) {
      String actual = leerUsuarioDelMenu(actor);
      EvidenciaUtils.registrarCaptura("Usuario en menú: '" + actual + "' | objetivo: '" + objetivo + "'");
      if (coincideNombre(actual, objetivo)) {
        cerrarMenu(actor);
        EvidenciaUtils.registrarCaptura("Cuenta correcta. El caso continúa sin reinicio.");
        CuentaManager.setUltimaCuentaLogueada(targetId);
        return;
      }
      EvidenciaUtils.registrarCaptura("Es otra cuenta. Reiniciando para entrar con '" + targetId + "'.");
      reiniciarYEntrar(actor);
      CuentaManager.setUltimaCuentaLogueada(targetId);
      return;
    }

    // No hay sesión -> reingreso SIN reinicio.
    EvidenciaUtils.registrarCaptura("Sin sesión -> reingreso sin reinicio para '" + targetId + "'.");
    actor.attemptsTo(ReingresoRelogin.paraCuentaActiva());
    CuentaManager.setUltimaCuentaLogueada(targetId);
  }

  // ─────────────────────────── pasos ───────────────────────────

  private <T extends Actor> String leerUsuarioDelMenu(T actor) {
    if (!visible(actor, MENU_USUARIO)) {
      return "";
    }
    actor.attemptsTo(Click.on(MENU_USUARIO));
    for (int i = 0; i < 8; i++) {
      if (visible(actor, LBL_NOMBRE_USUARIO)) {
        try {
          return Text.of(LBL_NOMBRE_USUARIO).viewedBy(actor).asString();
        } catch (Exception ignore) {
          return "";
        }
      }
      dormir(400);
    }
    return "";
  }

  private <T extends Actor> void cerrarMenu(T actor) {
    for (int i = 0; i < 3; i++) {
      if (visible(actor, BTN_CERRAR_MENU)) {
        actor.attemptsTo(Click.on(BTN_CERRAR_MENU));
        dormir(800);
        return;
      }
      dormir(400);
    }
    EvidenciaUtils.registrarCaptura("No se encontró el botón cerrar menú (iv_close).");
  }

  private <T extends Actor> void reiniciarYEntrar(T actor) {
    AppReset.reiniciarApp(actor);
    // resetApp reinicia la app; reafirmamos el implicitWait bajo por si la sesión lo reajustó.
    setImplicit(obtenerDriver(actor), IMPLICIT_LOGIN);
    esperarSplash(actor);
    actor.attemptsTo(PreparacionApp.preparar());
    actor.attemptsTo(IngresoSuperApp.ingresoSuperApp());
  }

  private <T extends Actor> void esperarSplash(T actor) {
    try {
      actor.attemptsTo(
          WaitUntil.the(LOADING_SPLASH, isNotPresent()),
          WaitUntil.the(LOADING_ESPERA_UN_MOMENTO, isNotPresent()).forNoMoreThan(40).seconds());
    } catch (Exception ignore) {
      // continuamos
    }
    dormir(1500);
  }

  private <T extends Actor> void esperarEstado(T actor) {
    long fin = System.currentTimeMillis() + 12000;
    while (System.currentTimeMillis() < fin) {
      if (haySesion(actor)
          || visible(actor, LBL_INICIAR_SESION)
          || visible(actor, LBL_NOS_ALEGRA_TENERTE_DE_VUELTA)) {
        return;
      }
      dormir(500);
    }
  }

  private <T extends Actor> boolean haySesion(T actor) {
    if (visible(actor, LBL_ENCABEZADO_USUARIO)) {
      return true;
    }
    try {
      if (visible(actor, LBL_TUS_SERVICIOS_FAVORITOS)) {
        String t = Text.of(LBL_TUS_SERVICIOS_FAVORITOS).viewedBy(actor).asString();
        return t != null && t.contains("Tus servicios favoritos");
      }
    } catch (Exception ignore) {
      // no logueado
    }
    return false;
  }

  private boolean coincideNombre(String actual, String objetivo) {
    String a = norm(actual);
    String o = norm(objetivo);
    if (a.isEmpty() || o.isEmpty()) {
      return false;
    }
    return a.equals(o) || a.contains(o) || o.contains(a);
  }

  private String norm(String s) {
    return s == null ? "" : s.trim().toLowerCase().replaceAll(",", " ").replaceAll("\\s+", " ").trim();
  }

  // ─────────────────────────── utilidades ───────────────────────────

  private AndroidDriver obtenerDriver(Actor actor) {
    try {
      return AndroidObject.androidDriver(actor);
    } catch (Exception e) {
      return null;
    }
  }

  private void setImplicit(AndroidDriver driver, int seconds) {
    if (driver != null) {
      try {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
      } catch (Exception ignore) {
        // no crítico
      }
    }
  }

  private <T extends Actor> boolean visible(T actor, Target t) {
    try {
      return !Presence.of(t).viewedBy(actor).resolveAll().isEmpty();
    } catch (Exception e) {
      return false;
    }
  }

  private void dormir(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  public static Performable segunCuenta() {
    return instrumented(GestionCuenta.class);
  }
}
