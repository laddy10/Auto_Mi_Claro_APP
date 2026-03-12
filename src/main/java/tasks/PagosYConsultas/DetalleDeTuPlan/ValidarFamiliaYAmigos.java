package tasks.PagosYConsultas.DetalleDeTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class ValidarFamiliaYAmigos implements Task {

  private static final String paso1 = "Validar direccionamiento Familia y amigos";

  @Override
  public <T extends Actor> void performAs(T actor) {

    actor.attemptsTo(WaitForResponse.withText(ELEGIDOS_TODO_DESTINO));

    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        ValidarTextoQueContengaX.elTextoContiene(FAMILIA_Y_AMIGOS_OPCION),
        ValidarTextoQueContengaX.elTextoContiene(ELEGIDOS_TODO_DESTINO));
  }

  public static Performable validarDireccionamiento() {
    return instrumented(ValidarFamiliaYAmigos.class);
  }
}
