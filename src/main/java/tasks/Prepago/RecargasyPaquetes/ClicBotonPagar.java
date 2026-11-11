package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.BTN_PAGAR;

import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

public class ClicBotonPagar implements Task {

  private static final String PASO = "Clic en botón Pagar de Resumen de la compra";

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(PASO);

    actor.attemptsTo(
        Click.on(BTN_PAGAR),
        WaitFor.aTime(3000));
  }

  public static Performable pagar() {
    return instrumented(ClicBotonPagar.class);
  }
}
