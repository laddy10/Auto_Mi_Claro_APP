package tasks.Login;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.LoginPage.*;
import static utils.Constants.*;

import interactions.Click.ClickElementByText;
import io.appium.java_client.android.AndroidDriver;
import java.util.concurrent.TimeUnit;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.targets.Target;
import utils.AndroidObject;
import utils.EvidenciaUtils;

/**
 * Cierra las pantallas que aparecen tras un reinicio LIMPIO de la app, hasta dejar visible el botón
 * "Iniciar sesión":
 *
 * <ul>
 *   <li>"Condiciones del servicio" -> Aceptar
 *   <li>Carrusel "¡Desliza y descúbrela!" -> Omitir
 *   <li>Banner publicitario -> Omitir / cerrar
 *   <li>"Permiso de acceso a tu ubicación" (pantalla interna de la app) -> Aceptar
 * </ul>
 *
 * <p>Estas pantallas NO las cubre {@code IngresoSuperApp.aceptarPermisosIniciales} (esa maneja el
 * permiso del sistema "mientras la app está en uso", no el interno). Reduce el implicitWait durante
 * el barrido para evitar demoras (cada elemento ausente cuesta el implicitWait completo) y lo
 * restaura al terminar.
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
    AndroidDriver driver = obtenerDriver(actor);
    setImplicit(driver, 1);
    try {
      for (int i = 0; i < 15; i++) {
        boolean hizo = false;

        if (visible(actor, BTN_ACEPTAR_TXT)) {
          actor.attemptsTo(ClickElementByText.clickElementByText(ACEPTAR_2)); // "Aceptar"
          hizo = true;
        } else if (visible(actor, BTN_OMITIR_TXT)) {
          actor.attemptsTo(ClickElementByText.clickElementByText(OMITIR)); // "Omitir"
          hizo = true;
        } else if (visible(actor, BTN_CERRAR_PUBLICIDAD)) {
          actor.attemptsTo(Click.on(BTN_CERRAR_PUBLICIDAD));
          hizo = true;
        }

        if (hizo) {
          dormir(700);
          continue;
        }

        // Nada que cerrar: ¿ya llegamos al home listo para iniciar sesión?
        if (visible(actor, LBL_INICIAR_SESION) || visible(actor, LBL_ENCABEZADO_USUARIO)) {
          break;
        }
        dormir(1000);
      }
    } finally {
      setImplicit(driver, 10);
    }
    EvidenciaUtils.registrarCaptura("App preparada tras reinicio (lista para iniciar sesión)");
  }

  public static Performable preparar() {
    return instrumented(PreparacionApp.class);
  }

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
}
