package tasks.PagosYConsultas.DetalleDeTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class VerTerminosYCondiciones implements Task {

  private static final String paso1 = "Validar direccionamiento terminos y condiciones";

  @Override
  public <T extends Actor> void performAs(T actor) {

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(VER_TERMINOS_Y_CONDICIONES),
        WaitForResponse.withText(TERMINOS_Y_CONDICIONES_CLARO));

    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        ValidarTexto.validarTexto(TERMINOS_Y_CONDICIONES_CLARO),
        ValidarTextoQueContengaX.elTextoContiene(ELEGIDOS_TODO_DESTINO_TITULO));
  }

  public static Performable verTerminos() {
    return instrumented(VerTerminosYCondiciones.class);
  }
}
