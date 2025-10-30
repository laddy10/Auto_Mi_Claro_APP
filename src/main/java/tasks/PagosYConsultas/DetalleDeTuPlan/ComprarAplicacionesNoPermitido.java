package tasks.PagosYConsultas.DetalleDeTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class ComprarAplicacionesNoPermitido implements Task {

  private static final String paso1 = "Ingresar a Comprar aplicaciones";
  private static final String paso2 = "Validar mensaje de error - Plan no permite";

  @Override
  public <T extends Actor> void performAs(T actor) {

    // COMPRAR APLICACIONES
    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(COMPRAR_APLICACIONES),
            WaitFor.aTime(2000));

    // VALIDAR MENSAJE DE ERROR
    actor.attemptsTo(
            ValidarTexto.validarTexto(TU_PLAN_NO_INCLUYE_ESTA_FUNCIONALIDAD),
            ValidarTexto.validarTexto(ACEPTAR_2));

    EvidenciaUtils.registrarCaptura(paso2);

    // Cerrar modal de error
    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(ACEPTAR_2));
  }

  public static Performable ingresarYValidar() {
    return instrumented(ComprarAplicacionesNoPermitido.class);
  }
}
