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
 * Tienda Claro ("Tecnología que te transforma") o en la pestaña Comercios ("¿Qué quieres hoy?").
 * Esta task devuelve la app al home para que el caso pueda continuar.
 *
 * <p>Las dos validaciones son INDEPENDIENTES: que no esté la Tienda no impide revisar Comercios (ese
 * era el fallo: un {@code return} temprano dejaba inalcanzable el bloque de Comercios).
 *
 * <p>La detección se hace por page source (una lectura), no por {@code Presence}: así, cuando la
 * app ya está en el home, la task no paga el implicitWait de Serenity por cada elemento ausente.
 *
 * <p>Es idempotente: si no hay Tienda ni Comercios, no hace nada.
 */
public class CerrarTiendaClaro extends AndroidObject implements Task {

  private static final int MAX_INTENTOS = 3;

  private static final String PASO_TIENDA = "Se cierra la Tienda Claro para continuar el caso";
  private static final String PASO_COMERCIOS =
          "Se sale de Comercios hacia el home para continuar el caso";
  private static final String PASO_COMERCIOS_FALLIDO =
          "No se logró salir de Comercios hacia el home";

  /** Texto ancla de la Tienda Claro. */
  private static final String TXT_TIENDA_CLARO = "Tecnología que te transforma";

  /** Texto ancla de la pestaña Comercios (título tv_title de la primera tarjeta). */
  private static final String TXT_COMERCIOS = "¿Qué quieres hoy?";

  /** Encabezado del usuario: solo existe en el home, nunca en Comercios. */
  private static final String ID_HOME_USUARIO = ":id/home_user_name_tv";

  /** Contenedor clickeable de cada pestaña de la barra inferior. */
  private static final String ID_TAB_INFERIOR = "com.clarocolombia.miclaro:id/cl_home_tab_item";

  /** Pestañas que llevan al home, en orden de preferencia (la que esté presente). */
  private static final String[] TABS_HOME = {MUNDO_CLARO, PAGOS_Y_MAS};

  @Override
  public <T extends Actor> void performAs(T actor) {
    cerrarTienda(actor);
    salirDeComercios(actor);
  }

  // ─────────────────────────── Tienda Claro ───────────────────────────

  private <T extends Actor> void cerrarTienda(T actor) {
    if (!enTienda(pageSource(actor))) {
      return; // No hay tienda: sigue con la validación de Comercios.
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
      if (clickTabHome(actor, xml)) {
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
    boolean anclaComercios = contiene(xml, TXT_COMERCIOS) || contiene(xml, IR_A_CINE);
    return anclaComercios && !contiene(xml, ID_HOME_USUARIO);
  }

  /**
   * Hace clic en la pestaña que lleva al home ("Mundo Claro" o "Pagos y más", la que esté).
   *
   * <p>Se hace clic en el CONTENEDOR de la pestaña (cl_home_tab_item, que es el nodo clickable) y no
   * en el TextView: el texto de la barra inferior queda fuera de los límites de la ventana que
   * reporta el árbol (y=2220 con alto 2173), por lo que el clic directo sobre el texto no es
   * confiable. Si el contenedor no resuelve, se usa el clic por texto como respaldo.
   *
   * @return true si se hizo clic en alguna pestaña.
   */
  private <T extends Actor> boolean clickTabHome(T actor, String xml) {
    for (String tab : TABS_HOME) {
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