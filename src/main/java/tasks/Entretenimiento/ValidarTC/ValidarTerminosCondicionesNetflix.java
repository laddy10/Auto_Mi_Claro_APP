package tasks.Entretenimiento.ValidarTC;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.EntretenimientoPage.*;
import static utils.Constants.*;

import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitElement;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

/** Task para validar términos y condiciones Netflix */
public class ValidarTerminosCondicionesNetflix implements Task {

  private static final String paso = "Validar y aceptar Términos y Condiciones Netflix";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        WaitElement.isVisible(IMAGEN_NETFLIX),
        ValidarTexto.validarTexto(ESCRIBIR_CODIGO_VENDEDOR),
        ValidarTextoQueContengaX.elTextoContiene(ACEPTAR_TERMINOS_CONDICIONES),
        ValidarTextoQueContengaX.elTextoContiene(TERMINOS_Y_CONDICIONES_2),
        ValidarTexto.validarTexto(ACTIVAR));
    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable validar() {
    return instrumented(ValidarTerminosCondicionesNetflix.class);
  }
}
