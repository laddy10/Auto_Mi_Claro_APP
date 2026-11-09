package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.MediosPagoPage.BTN_SELECCIONAR_BANCOLOMBIA;
import static utils.Constants.BOTON_BANCOLOMBIA;
import static utils.Constants.RESUMEN_COMPRA;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

public class ElegirMedioPagoBancolombia implements Task {

  private static final String PASO = "Elegir medio de pago: Bancolombia";

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(PASO);

    try {
      actor.attemptsTo(
          WaitFor.aTime(2000),
          Click.on(BTN_SELECCIONAR_BANCOLOMBIA));
    } catch (Exception e) {
      actor.attemptsTo(
          ClickTextoQueContengaX.elTextoContiene(BOTON_BANCOLOMBIA));
    }

    actor.attemptsTo(
        WaitForResponse.withText(RESUMEN_COMPRA),
        ValidarResumenCompraPaqueteVoz.validar());
  }

  public static Performable elegir() {
    return instrumented(ElegirMedioPagoBancolombia.class);
  }
}
