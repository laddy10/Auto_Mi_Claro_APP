package tasks.ConsultarConsumos;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class ValidarConsumoDeDatos implements Task {

  private static final String paso = "Validar Consumo de Datos";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(CONSUMO_DE_DATOS), WaitFor.aTime(3000));

    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable validar() {
    return instrumented(ValidarConsumoDeDatos.class);
  }
}
