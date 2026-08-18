package tasks.Login;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static userinterfaces.LoginPage.*;
import static utils.Constants.MUNDO_CLARO;

import interactions.Click.ClickTextoQueContengaX;
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
 * <p>Lógica:
 *
 * <ul>
 *   <li>HAY sesión: abre el menú (iv_menu), lee el usuario (profile_welcome_title) y lo compara con
 *       el {@code nombreUsuario} de la cuenta objetivo (real-user.json). Aplica a AMBAS cuentas:
 *       <ul>
 *         <li>Coincide -> cierra el menú (iv_close) y CONTINÚA, sin reinicio.
 *         <li>No coincide -> reinicia y entra con la cuenta objetivo.
 *       </ul>
 *   <li>NO hay sesión (popup "por seguridad…", bienvenida o app recién abierta): reingresa SIN
 *       reiniciar con {@link ReingresoRelogin} (compara el correo recordado 'relogin_account' con el
 *       objetivo: si coincide -> Continuar; si no -> "Ingresar con otra cuenta" y login con la
 *       objetivo).
 * </ul>
 */
public class GestionCuenta implements Task {

  private final User user = TestDataProvider.getRealUser();

  private static final Target BTN_CERRAR_MENU =
      Target.the("Cerrar menú del perfil")
          .located(By.id("com.clarocolombia.miclaro:id/iv_close"));

  @Override
  public <T extends Actor> void performAs(T actor) {
    String targetId = CuentaManager.getIdCuentaActiva();
    String objetivo = user.getNombreUsuario();
    EvidenciaUtils.registrarCaptura("Gestión de cuenta | objetivo: " + targetId + " (" + objetivo + ")");

    AndroidDriver driver = obtenerDriver(actor);
    setImplicit(driver, 1);
    boolean sesion;
    String actual = "";
    try {
      esperarEstado(actor);
      sesion = haySesion(actor);
      if (sesion) {
        cerrarPublicidad(actor); // por si un banner tapa el ícono de menú
        actual = leerUsuarioDelMenu(actor);
      }
    } finally {
      setImplicit(driver, 10);
    }

    if (sesion) {
      EvidenciaUtils.registrarCaptura("Usuario en menú: '" + actual + "' | objetivo: '" + objetivo + "'");
      if (coincideNombre(actual, objetivo)) {
        // Cuenta correcta (principal o secundaria) -> cerrar menú y continuar, SIN reinicio.
        cerrarMenu(actor);
        EvidenciaUtils.registrarCaptura("Cuenta correcta. El caso continúa sin reinicio.");
        CuentaManager.setUltimaCuentaLogueada(targetId);
        return;
      }
      // Otra cuenta -> reiniciar y entrar con la objetivo.
      EvidenciaUtils.registrarCaptura("Es otra cuenta. Reiniciando para entrar con '" + targetId + "'.");
      reiniciarYEntrar(actor);
      CuentaManager.setUltimaCuentaLogueada(targetId);
      return;
    }

    // No hay sesión -> reingreso SIN reinicio (usa relogin_account para decidir Continuar / otra cuenta).
    EvidenciaUtils.registrarCaptura("Sin sesión -> reingreso sin reinicio para '" + targetId + "'.");
    actor.attemptsTo(ReingresoRelogin.paraCuentaActiva());
    CuentaManager.setUltimaCuentaLogueada(targetId);
  }

  // ─────────────────────────── pasos ───────────────────────────

  /** Abre el menú (iv_menu), lee el nombre de usuario (profile_welcome_title) y lo devuelve. */
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

  /** Cierra el menú con el botón X (iv_close) para volver al home sin salir de la app. */
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

  /** Sesión activa = home logueado (encabezado del usuario o "Tus servicios favoritos"). */
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

  private <T extends Actor> void cerrarPublicidad(T actor) {
    if (visible(actor, BTN_CERRAR_PUBLICIDAD)) {
      actor.attemptsTo(Click.on(BTN_CERRAR_PUBLICIDAD));
      dormir(500);
    }
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
