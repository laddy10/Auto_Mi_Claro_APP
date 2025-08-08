package tasks.Entretenimiento;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.EntretenimientoPage.*;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

/** Task para seleccionar plan Amazon Prime */
public class SeleccionarPlanAmazonPrime implements Task {

  private static final String paso = "Seleccionar Plan Amazon Prime";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(WaitFor.aTime(5000), ValidarTextoQueContengaX.elTextoContiene("Amazon Prime"));
    EvidenciaUtils.registrarCaptura(paso);
    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(ELEGIR_PLAN));
  }

  public static Performable seleccionar() {
    return instrumented(SeleccionarPlanAmazonPrime.class);
  }
}
