package tasks.ConsultarConsumos;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class VerificarOpcionRecargas implements Task {

  private static final String paso = "Verificar Opción Recargas";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(RECARGAS),
        WaitFor.aTime(2000),
        ValidarTextoQueContengaX.elTextoContiene(AUN_NO_HAS_HECHO_RECARGAS));

    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable verificar() {
    return instrumented(VerificarOpcionRecargas.class);
  }
}
