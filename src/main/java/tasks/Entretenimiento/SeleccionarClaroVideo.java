package tasks.Entretenimiento;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.EntretenimientoPage.*;
import static userinterfaces.PagosYConsultasPage.BTN_DETALLE_DE_TU_PLAN;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.AndroidObject;
import utils.EvidenciaUtils;

/** Task para seleccionar Claro Video */
public class SeleccionarClaroVideo implements Task {
  private static final String paso = "Buscar y seleccionar Claro Video en pantalla";

  @Override
  public <T extends Actor> void performAs(T actor) {
    WaitForResponse.withText(TUS_SERVICIOS_FAVORITOS);
    EvidenciaUtils.registrarCaptura(paso);
    if(isElementoVisible(actor, CLARO_VIDEO)) {
      actor.attemptsTo(
              ClickTextoQueContengaX.elTextoContiene(CLARO_VIDEO));
    }else{
      actor.attemptsTo(Click.on(BTN_CLARO_VIDEO));
    }
  }

  // 🔹 Método auxiliar para validar si un texto está visible en pantalla
  private boolean isElementoVisible(Actor actor, String texto) {
    try {
      WebElement elemento =
              AndroidObject.androidDriver(actor)
                      .findElement(By.xpath("//*[contains(@text,'" + texto + "')]"));
      return elemento.isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  public static Performable seleccionar() {
    return instrumented(SeleccionarClaroVideo.class);
  }
}
