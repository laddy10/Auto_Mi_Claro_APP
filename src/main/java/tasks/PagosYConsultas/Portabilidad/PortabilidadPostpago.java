package tasks.PagosYConsultas.Portabilidad;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class PortabilidadPostpago implements Task {

  private static final String paso1 = "Ingresar a Portabilidad postpago";
  private static final String paso2 = "Validar redirección a selección de planes";

  @Override
  public <T extends Actor> void performAs(T actor) {

    // Ingresar a Portabilidad postpago
    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(PORTABILIDAD_POSTPAGO),
        WaitForResponse.withText(LLAMAME_AHORA));

    // Validar redirección a selección de planes
    EvidenciaUtils.registrarCaptura(paso2);

    actor.attemptsTo(
        ValidarTextoQueContengaX.elTextoContiene(LLAMAME_AHORA),
        ValidarTextoQueContengaX.elTextoContiene(AUTORIZO_EL_TRATAMIENTO_DATOS),
        ValidarTextoQueContengaX.elTextoContiene(SELECCIONA_EL_PLAN_PORTACION),
        ValidarTextoQueContengaX.elTextoContiene(FILTRAR_POR));
  }

  public static Performable validarPortabilidadPostpago() {
    return instrumented(PortabilidadPostpago.class);
  }
}
