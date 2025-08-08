package tasks.PagosYConsultas.DetalleDeTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.validations.ValidarTextoQueContengaX;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class ValidarElegidosTodoDestino implements Task {

  private static final String paso1 =
      "Validar direccionamiento Elegidos todo destino y Ver terminos y condiciones ";

  @Override
  public <T extends Actor> void performAs(T actor) {

    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        ValidarTextoQueContengaX.elTextoContiene(ELEGIDOS_TODO_DESTINO),
        ValidarTextoQueContengaX.elTextoContiene(VER_TERMINOS_Y_CONDICIONES),
        ValidarTextoQueContengaX.elTextoContiene(AGREGAR_ELEGIDO));
  }

  public static Performable validarDireccionamiento() {
    return instrumented(ValidarElegidosTodoDestino.class);
  }
}
