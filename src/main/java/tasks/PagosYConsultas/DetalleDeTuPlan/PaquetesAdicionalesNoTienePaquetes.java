package tasks.PagosYConsultas.DetalleDeTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class PaquetesAdicionalesNoTienePaquetes implements Task {

  private static final String paso1 = "Validar popup - No cuenta con paquetes adicionales";

  @Override
  public <T extends Actor> void performAs(T actor) {

    // VALIDAR POPUP
    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        ValidarTexto.validarTexto(ACTUALMENTE_NO_CUENTA_PAQUETES),
        ValidarTexto.validarTexto(ACEPTAR_2),
        ClickTextoQueContengaX.elTextoContiene(ACEPTAR_2));
  }

  public static Performable validarPopup() {
    return instrumented(PaquetesAdicionalesNoTienePaquetes.class);
  }
}
