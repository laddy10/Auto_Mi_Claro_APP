package tasks.Login;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.LoginPage.*;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitFor;
import io.appium.java_client.android.AndroidDriver;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
import utils.AndroidObject;
import utils.EvidenciaUtils;

/**
 * EMERGENCIA: al abrir la app con la sesión ya iniciada, a veces NO abre en el home sino en la
 * Tienda Claro ("Tecnología que te transforma"), en la pestaña Comercios ("¿Qué quieres hoy?") o en
 * la pestaña Entretenimiento ("Hecho para ti"). Esta task devuelve la app al home para que el caso
 * pueda continuar.
 *
 * <p>Las validaciones son INDEPENDIENTES: que no esté una pantalla no impide revisar las demás.
 *
 * <p>La detección se hace por page source (una lectura), no por {@code Presence}: así, cuando la
 * app ya está en el home, la task no paga el implicitWait de Serenity por cada elemento ausente.
 *
 * <p>Es idempotente: si no hay Tienda, Comercios ni Entretenimiento, no hace nada.
 */
public class CerrarTiendaClaro extends AndroidObject implements Task {

  private static final int MAX_INTENTOS = 3;

  private static final String PASO_TIENDA = "Se cierra la Tienda Claro para continuar el caso";
  private static final String PASO_COMERCIOS =
          "Se sale de Comercios hacia el home para continuar el caso";
  private static final String PASO_COMERCIOS_FALLIDO =
          "No se logró salir de Comercios hacia el home";
  private static final String PASO_ENTRETENIMIENTO =
          "Se sale de Entretenimiento hacia Pagos y más para continuar el caso";
  private static final String PASO_ENTRETENIMIENTO_FALLIDO =
          "No se logró salir de Entretenimiento hacia Pagos y más";

  /** Texto ancla de la Tienda Claro. */
  private static final String TXT_TIENDA_CLARO = "Tecnología que te transforma";

  /** Texto ancla de la pestaña Comercios (título tv_title de la primera tarjeta). */
  private static final String TXT_COMERCIOS = "Nuestras Categorías";

  /** Texto ancla de la pestaña Entretenimiento (título de la sección principal). */
  private static final String TXT_ENTRETENIMIENTO = "Hecho para ti";

  /** Encabezado del usuario: solo existe en el home, nunca en Comercios ni Entretenimiento. */
  private static final String ID_HOME_USUARIO = ":id/home_user_name_tv";

  /** Contenedor clickeable de cada pestaña de la barra inferior. */
  private static final String ID_TAB_INFERIOR = "com.clarocolombia.miclaro:id/cl_home_tab_item";

  /** Pestañas que llevan al home desde Comercios, en orden de preferencia (la que esté presente). */
  private static final String[] TABS_HOME = {MUNDO_CLARO, PAGOS_Y_MAS};

  /** Pestaña a la que se sale desde Entretenimiento. */
  private static final String[] TABS_DESDE_ENTRETENIMIENTO = {PAGOS_Y_MAS};

  @Override
  public <T extends Actor> void performAs(T actor) {
    cerrarTienda(actor);
    salirDeComercios(actor);
    salirDeEntretenimiento(actor);
  }

  // ─────────────────────────── Tienda Claro ───────────────────────────

  private <T extends Actor> void cerrarTienda(T actor) {
    if (!enTienda(pageSource(actor))) {
      return; // No hay tienda: sigue con las demás validaciones.
    }

    for (int intento = 1; intento <= MAX_INTENTOS && enTienda(pageSource(actor)); intento++) {
      if (isVisible(actor, BTN_CERRAR_TIENDA_CLARO)) {
        actor.attemptsTo(Atras.irAtras(), WaitFor.aTime(1500));
      } else {
        actor.attemptsTo(WaitFor.aTime(1000));
      }
    }

    EvidenciaUtils.registrarCaptura(PASO_TIENDA);
  }

  private boolean enTienda(String xml) {
    return contiene(xml, TXT_TIENDA_CLARO);
  }

  // ─────────────────────────── Comercios ───────────────────────────

  private <T extends Actor> void salirDeComercios(T actor) {
    String xml = pageSource(actor);
    if (!enComercios(xml)) {
      return; // No hay redirección a Comercios: no interrumpe el flujo normal.
    }

    for (int intento = 1; intento <= MAX_INTENTOS && enComercios(xml); intento++) {
      if (clickTab(actor, xml, TABS_HOME)) {
        actor.attemptsTo(WaitFor.aTime(1500));
      } else {
        actor.attemptsTo(WaitFor.aTime(1000)); // la barra inferior aún no terminó de pintar
      }
      xml = pageSource(actor);
    }

    EvidenciaUtils.registrarCaptura(enComercios(xml) ? PASO_COMERCIOS_FALLIDO : PASO_COMERCIOS);
  }

  /**
   * Está en Comercios si aparece su título ("¿Qué quieres hoy?") o la categoría "Ir a cine" y NO
   * está el encabezado del usuario, que solo pinta el home.
   */
  private boolean enComercios(String xml) {
    boolean anclaComercios = contiene(xml, TXT_COMERCIOS) || contiene(xml, PROTECCION);
    return anclaComercios && !contiene(xml, ID_HOME_USUARIO);
  }

  // ─────────────────────────── Entretenimiento ───────────────────────────

  private <T extends Actor> void salirDeEntretenimiento(T actor) {
    String xml = pageSource(actor);
    if (!enEntretenimiento(xml)) {
      return; // No hay redirección a Entretenimiento: no interrumpe el flujo normal.
    }

    for (int intento = 1; intento <= MAX_INTENTOS && enEntretenimiento(xml); intento++) {
      if (clickTab(actor, xml, TABS_DESDE_ENTRETENIMIENTO)) {
        actor.attemptsTo(WaitFor.aTime(1500));
      } else {
        actor.attemptsTo(WaitFor.aTime(1000)); // la barra inferior aún no terminó de pintar
      }
      xml = pageSource(actor);
    }

    EvidenciaUtils.registrarCaptura(
            enEntretenimiento(xml) ? PASO_ENTRETENIMIENTO_FALLIDO : PASO_ENTRETENIMIENTO);
  }

  /**
   * Está en Entretenimiento si aparece "Hecho para ti" y NO está el encabezado del usuario, que
   * solo pinta el home (evita un falso positivo si el home llegara a mostrar esa sección).
   */
  private boolean enEntretenimiento(String xml) {
    return contiene(xml, TXT_ENTRETENIMIENTO) && !contiene(xml, ID_HOME_USUARIO);
  }

  // ─────────────────────────── barra inferior ───────────────────────────

  /**
   * Hace clic en la primera pestaña de {@code tabs} que esté presente en la barra inferior.
   *
   * <p>Se hace clic en el CONTENEDOR de la pestaña (cl_home_tab_item, que es el nodo clickable) y no
   * en el TextView: el texto de la barra inferior queda fuera de los límites de la ventana que
   * reporta el árbol (y=2220 con alto 2173), por lo que el clic directo sobre el texto no es
   * confiable. Si el contenedor no resuelve, se usa el clic por texto como respaldo.
   *
   * @return true si se hizo clic en alguna pestaña.
   */
  private <T extends Actor> boolean clickTab(T actor, String xml, String... tabs) {
    for (String tab : tabs) {
      if (!contiene(xml, "text=\"" + tab + "\"")) {
        continue;
      }
      try {
        androidDriver(actor)
                .findElement(
                        By.xpath(
                                "//*[@resource-id='"
                                        + ID_TAB_INFERIOR
                                        + "'][.//android.widget.TextView[@text='"
                                        + tab
                                        + "']]"))
                .click();
        return true;
      } catch (Exception e) {
        try {
          actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(tab));
          return true;
        } catch (Exception ignore) {
          // se prueba la siguiente pestaña candidata
        }
      }
    }
    return false;
  }

  // ─────────────────────────── utilidades ───────────────────────────

  private String pageSource(Actor actor) {
    try {
      AndroidDriver driver = androidDriver(actor);
      return driver == null ? null : driver.getPageSource();
    } catch (Exception e) {
      return null;
    }
  }

  private boolean contiene(String xml, String clave) {
    return xml != null && xml.contains(clave);
  }

  public <T extends Actor> boolean isVisible(T actor, Target element) {
    try {
      return !Presence.of(element).viewedBy(actor).resolveAll().isEmpty();
    } catch (Exception e) {
      return false;
    }
  }

  public static Performable cerrarTiendaClaro() {
    return instrumented(CerrarTiendaClaro.class);
  }
}