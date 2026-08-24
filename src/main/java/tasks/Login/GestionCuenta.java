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
 * <p>Estrategia: un único bucle acotado (deadline + máximo de pasadas). En cada pasada lee el page
 * source UNA vez y decide:
 *
 * <ul>
 *   <li>Home logueado -> lee el usuario del menú y CONTINÚA (si coincide) o reinicia (si es otra
 *       cuenta).
 *   <li>Relogin / bienvenida / home deslogueado -> reingreso SIN reinicio con {@link
 *       ReingresoRelogin}.
 *   <li>Pantalla de arranque (carrusel/condiciones/publicidad) -> cierra UNA con {@link
 *       PreparacionApp} y reintenta.
 * </ul>
 *
 * <p>Si tras el presupuesto ({@code DEADLINE_MS} / {@code MAX_PASADAS}) no se reconoce ningún estado,
 * lanza una excepción clara en vez de colgarse: así el escenario falla en segundos, dispara el
 * error.png y la sección "RESULTADO: FALLIDO" del reporte Word.
 *
 * <p>La detección se hace por page source (una lectura por pasada) y NO depende del implicitWait,
 * que Serenity re-aplica por interacción y hace que cada búsqueda ausente cueste el valor completo.
 */
public class GestionCuenta implements Task {

  private final User user = TestDataProvider.getRealUser();

  private static final int IMPLICIT_LOGIN = 2; // s, durante el login
  private static final int IMPLICIT_NORMAL = 10; // s, valor normal
  private static final long DEADLINE_MS = 60_000L; // techo duro del escenario
  private static final int MAX_PASADAS = 12; // tope de iteraciones del orquestador

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

  // ─────────────────────────── orquestación ───────────────────────────

  private <T extends Actor> void ejecutar(T actor) {
    String targetId = CuentaManager.getIdCuentaActiva();
    String objetivo = user.getNombreUsuario();
    EvidenciaUtils.registrarCaptura(
        "Gestión de cuenta | objetivo: " + targetId + " (" + objetivo + ")");

    long fin = System.currentTimeMillis() + DEADLINE_MS;
    int pasada = 0;

    while (System.currentTimeMillis() < fin && pasada < MAX_PASADAS) {
      pasada++;
      String xml = pageSource(actor); // UNA lectura por pasada

      // 1) Home con sesión (caso "sesión principal abierta") -> resolver cuenta y SALIR.
      if (esHomeLogueado(xml)) {
        cerrarPublicidad(actor); // por si un banner tapa el ícono de menú
        String actual = leerUsuarioDelMenu(actor);
        EvidenciaUtils.registrarCaptura(
            "Usuario en menú: '" + actual + "' | objetivo: '" + objetivo + "'");
        if (coincideNombre(actual, objetivo)) {
          cerrarMenu(actor);
          EvidenciaUtils.registrarCaptura("Cuenta correcta. Continúa sin reinicio.");
          CuentaManager.setUltimaCuentaLogueada(targetId);
          return;
        }
        EvidenciaUtils.registrarCaptura(
            "Es otra cuenta. Reiniciando para entrar con '" + targetId + "'.");
        reiniciarYEntrar(actor);
        CuentaManager.setUltimaCuentaLogueada(targetId);
        return;
      }

      // 2) Relogin (sesión cerrada / bienvenida / home deslogueado) -> reingreso sin reinicio.
      if (esRelogin(xml)) {
        EvidenciaUtils.registrarCaptura(
            "Sin sesión -> reingreso sin reinicio para '" + targetId + "'.");
        actor.attemptsTo(ReingresoRelogin.paraCuentaActiva());
        CuentaManager.setUltimaCuentaLogueada(targetId);
        return;
      }

      // 3) Pantallas de arranque (carrusel/condiciones/publicidad) -> cerrar UNA y reintentar.
      if (hayPantallaArranque(xml)) {
        actor.attemptsTo(PreparacionApp.preparar());
        dormir(600);
        continue;
      }

      // Nada reconocido aún: espera corta y reintenta.
      dormir(1000);
    }

    // Ningún estado conocido dentro del presupuesto -> FALLA CLARA (no cuelga).
    EvidenciaUtils.registrarCaptura("Estado no reconocido tras " + pasada + " pasadas.");
    throw new IllegalStateException(
        "GestionCuenta: no se reconoció ningún estado (home/relogin/arranque) para la cuenta '"
            + targetId
            + "'. Revisa el locator del home (home_user_name_tv) contra el page source real.");
  }

  // ─────────────────────────── detección por page source ───────────────────────────

  /** Home logueado: encabezado del usuario, ícono de menú, "Tus servicios favoritos" o el saludo. */
  private boolean esHomeLogueado(String xml) {
    return contieneAlguno(
        xml,
        ":id/home_user_name_tv",
        ":id/iv_menu",
        ":id/card_mini_program_title_tv",
        "Tus servicios favoritos",
        "Hola,");
  }

  /** Sesión cerrada / bienvenida / home deslogueado. */
  private boolean esRelogin(String xml) {
    return contieneAlguno(
        xml,
        "¡Nos alegra tenerte de vuelta!",
        "Tu sesión se ha cerrado por seguridad",
        "Otros métodos de ingreso",
        "Ingresar con otra cuenta",
        "Iniciar sesión");
  }

  /** Pantallas de arranque que cierra PreparacionApp. */
  private boolean hayPantallaArranque(String xml) {
    return contieneAlguno(xml, "Omitir", "OMITIR", "Aceptar", "ACEPTAR");
  }

  private boolean contieneAlguno(String xml, String... claves) {
    if (xml == null || xml.isEmpty()) {
      return false;
    }
    for (String c : claves) {
      if (xml.contains(c)) {
        return true;
      }
    }
    return false;
  }

  private String pageSource(Actor actor) {
    try {
      AndroidDriver d = AndroidObject.androidDriver(actor);
      return d == null ? null : d.getPageSource();
    } catch (Exception e) {
      return null;
    }
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

  private <T extends Actor> void cerrarPublicidad(T actor) {
    if (visible(actor, BTN_CERRAR_PUBLICIDAD)) {
      actor.attemptsTo(Click.on(BTN_CERRAR_PUBLICIDAD));
      dormir(500);
    }
  }

  private <T extends Actor> void reiniciarYEntrar(T actor) {
    AppReset.reiniciarApp(actor);
    esperarSplash(actor);
    prepararArranque(actor); // cierra la secuencia de pantallas tras el reinicio
    actor.attemptsTo(IngresoSuperApp.ingresoSuperApp());
  }

  /** Barrido acotado de pantallas de arranque tras un reinicio (carrusel, condiciones, permiso). */
  private <T extends Actor> void prepararArranque(T actor) {
    long fin = System.currentTimeMillis() + 20_000L;
    for (int i = 0; i < 8 && System.currentTimeMillis() < fin; i++) {
      String xml = pageSource(actor);
      if (esHomeLogueado(xml) || esRelogin(xml)) {
        return; // ya salimos de las pantallas de arranque
      }
      if (hayPantallaArranque(xml)) {
        actor.attemptsTo(PreparacionApp.preparar());
        dormir(600);
      } else {
        dormir(800);
      }
    }
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

  // ─────────────────────────── comparación de nombre ───────────────────────────

  private boolean coincideNombre(String actual, String objetivo) {
    String a = norm(actual);
    String o = norm(objetivo);
    if (a.isEmpty() || o.isEmpty()) {
      return false;
    }
    return a.equals(o) || a.contains(o) || o.contains(a);
  }

  private String norm(String s) {
    return s == null
        ? ""
        : s.trim().toLowerCase().replaceAll(",", " ").replaceAll("\\s+", " ").trim();
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
