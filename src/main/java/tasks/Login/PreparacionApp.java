package tasks.Login;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.LoginPage.*;
import static utils.Constants.*;

import interactions.Click.ClickElementByText;
import io.appium.java_client.android.AndroidDriver;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.targets.Target;
import utils.AndroidObject;
import utils.EvidenciaUtils;

/**
 * Cierra UNA pantalla de arranque por invocación (barrido único): "Aceptar" (condiciones / permiso
 * de ubicación interno), "Omitir" (carrusel / publicidad) o el botón de cerrar banner publicitario.
 *
 * <p>El bucle y el tope de tiempo los controla {@code GestionCuenta.ejecutar()}, que solo invoca
 * esta tarea cuando el page source confirma que hay una pantalla de arranque. Por eso aquí NO hay
 * bucle propio ni manejo de implicitWait: se decide por page source (sin esperas por elementos
 * ausentes), se cierra una pantalla y se retorna.
 */
public class PreparacionApp implements Task {

  @Override
  public <T extends Actor> void performAs(T actor) {
    String xml = pageSource(actor);
    if (contiene(xml, "Aceptar") || contiene(xml, "ACEPTAR")) {
      actor.attemptsTo(ClickElementByText.clickElementByText(ACEPTAR_2)); // "Aceptar"
    } else if (contiene(xml, "Omitir") || contiene(xml, "OMITIR")) {
      actor.attemptsTo(ClickElementByText.clickElementByText(OMITIR)); // "Omitir"
    } else if (visible(actor, BTN_CERRAR_PUBLICIDAD)) {
      actor.attemptsTo(Click.on(BTN_CERRAR_PUBLICIDAD));
    }
    EvidenciaUtils.registrarCaptura("Preparación: barrido único de pantallas de arranque.");
  }

  public static Performable preparar() {
    return instrumented(PreparacionApp.class);
  }

  // ─────────────────────────── helpers privados ───────────────────────────

  private String pageSource(Actor actor) {
    try {
      AndroidDriver d = AndroidObject.androidDriver(actor);
      return d == null ? null : d.getPageSource();
    } catch (Exception e) {
      return null;
    }
  }

  private boolean contiene(String xml, String clave) {
    return xml != null && xml.contains(clave);
  }

  private <T extends Actor> boolean visible(T actor, Target t) {
    try {
      return !Presence.of(t).viewedBy(actor).resolveAll().isEmpty();
    } catch (Exception e) {
      return false;
    }
  }
}
