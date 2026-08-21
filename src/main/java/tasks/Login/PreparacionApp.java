package tasks.Login;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.LoginPage.*;
import static utils.Constants.*;

import interactions.Click.ClickElementByText;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.targets.Target;
import utils.EvidenciaUtils;

/**
 * Cierra las pantallas de ARRANQUE/interstitial de la app hasta llegar a un estado conocido (home
 * logueado o un punto de ingreso: "Iniciar sesión", bienvenida/relogin o documento):
 *
 * <ul>
 *   <li>"Condiciones del servicio" -> Aceptar
 *   <li>Carrusel "¡Desliza y descúbrela!" -> Omitir
 *   <li>Banner publicitario -> Omitir / cerrar
 *   <li>"Permiso de acceso a tu ubicación" -> Aceptar
 * </ul>
 *
 * <p>Se llama tanto tras un reinicio como al INICIO de {@code GestionCuenta}, para que el manejo de
 * "Omitir"/carrusel aplique también cuando la app se abre en esa pantalla sin haber pedido reinicio.
 *
 * <p>NO maneja el implicitWait: corre dentro de {@code GestionCuenta}, que ya lo dejó bajo (2s).
 */
public class PreparacionApp implements Task {

  private static final Target BTN_ACEPTAR_TXT =
      Target.the("Botón Aceptar (condiciones / permiso ubicación)")
          .locatedBy("//*[contains(@text,'Aceptar') or contains(@text,'ACEPTAR')]");
  private static final Target BTN_OMITIR_TXT =
      Target.the("Botón Omitir (carrusel / publicidad)")
          .locatedBy("//*[contains(@text,'Omitir') or contains(@text,'OMITIR')]");

  @Override
  public <T extends Actor> void performAs(T actor) {
    for (int i = 0; i < 18; i++) {
      boolean hizo = false;

      if (visible(actor, BTN_ACEPTAR_TXT)) {
        clickSeguro(actor, ACEPTAR_2); // "Aceptar"
        hizo = true;
      } else if (visible(actor, BTN_OMITIR_TXT)) {
        clickSeguro(actor, OMITIR); // "Omitir"
        hizo = true;
      } else if (visible(actor, BTN_CERRAR_PUBLICIDAD)) {
        actor.attemptsTo(Click.on(BTN_CERRAR_PUBLICIDAD));
        hizo = true;
      }

      if (hizo) {
        dormir(600);
        continue;
      }

      // Nada que cerrar: ¿ya llegamos a un estado conocido?
      if (enEstadoConocido(actor)) {
        break;
      }
      dormir(800);
    }
    EvidenciaUtils.registrarCaptura("App preparada (pantallas de arranque cerradas).");
  }

  /** Estado desde el que ya se puede decidir/iniciar sesión (fin de las pantallas de arranque). */
  private <T extends Actor> boolean enEstadoConocido(T actor) {
    return visible(actor, LBL_ENCABEZADO_USUARIO)
        || visible(actor, LBL_INICIAR_SESION)
        || visible(actor, LBL_NOS_ALEGRA_TENERTE_DE_VUELTA)
        || visible(actor, BTN_OTROS_METODOS_INGRESO)
        || visible(actor, TXT_USERNAME);
  }

  public static Performable preparar() {
    return instrumented(PreparacionApp.class);
  }

  private <T extends Actor> void clickSeguro(T actor, String texto) {
    try {
      actor.attemptsTo(ClickElementByText.clickElementByText(texto));
    } catch (Exception ignore) {
      // si no se pudo clicar, el bucle reintenta
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
}
