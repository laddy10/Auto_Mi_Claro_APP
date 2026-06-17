package tasks.Entretenimiento.ValidarRedirecciones;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.EntretenimientoPage.*;
import static utils.Constants.SOLO_UNA_VEZ;

import interactions.Click.ClickElementByText;
import interactions.validations.ValidarTexto;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.targets.Target;
import utils.EvidenciaUtils;

public class ValidarRedireccionPlayStoreClaroVideo implements Task {

  private static final String paso = "Validar redirección a la Play Store";

  @Override
  public <T extends Actor> void performAs(T actor) {
    /* // Obtener el driver original de Appium desde el facade de Serenity
    WebDriverFacade facade = (WebDriverFacade) BrowseTheWeb.as(actor).getDriver();
    AndroidDriver driver = (AndroidDriver) facade.getProxiedDriver();

    // Activar la app Play Store
    driver.activateApp("com.android.vending");*/

    // Esperar unos segundos para que la Play Store cargue
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    // Validar textos visibles en Play Store
    actor.attemptsTo(
        ValidarTexto.validarTexto("Claro video"));

    if (isVisible(actor, TXT_DESINSTALAR)) {
      actor.attemptsTo(ValidarTexto.validarTexto("Desinstalar"));
    }else if (isVisible(actor, TXT_ABRIR)){
      actor.attemptsTo(ValidarTexto.validarTexto("ABRIR"));
    }

    EvidenciaUtils.registrarCaptura(paso);
  }
  private <T extends Actor> boolean isVisible(T actor, Target element) {
    return !Presence.of(element).viewedBy(actor).resolveAll().isEmpty();
  }

  public static Performable validar() {
    return instrumented(ValidarRedireccionPlayStoreClaroVideo.class);
  }
}
