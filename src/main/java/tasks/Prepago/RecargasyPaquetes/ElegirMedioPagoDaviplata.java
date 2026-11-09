package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.MediosPagoPage.BTN_SELECCIONAR_DAVIPLATA;
import static utils.Constants.DAVIPLATA;
import static utils.Constants.RESUMEN_COMPRA;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

public class ElegirMedioPagoDaviplata implements Task {

  private static final String PASO = "Elegir medio de pago: Daviplata";

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(PASO);

    try {
      actor.attemptsTo(
          WaitFor.aTime(2000),
          Click.on(BTN_SELECCIONAR_DAVIPLATA));
    } catch (Exception e) {
      actor.attemptsTo(
          ClickTextoQueContengaX.elTextoContiene(DAVIPLATA));
    }

    actor.attemptsTo(
        WaitForResponse.withText(RESUMEN_COMPRA),
        ValidarResumenCompraPaqueteApps.validar());
  }

  public static Performable elegir() {
    return instrumented(ElegirMedioPagoDaviplata.class);
  }
}
