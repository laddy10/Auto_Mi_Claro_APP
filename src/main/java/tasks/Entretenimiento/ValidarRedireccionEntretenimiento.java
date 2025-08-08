package tasks.Entretenimiento;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.EntretenimientoPage.*;
import static utils.Constants.*;

import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

/** Task para validar redirección a entretenimiento */
public class ValidarRedireccionEntretenimiento implements Task {

  private static final String paso = "Validar Redirección a Entretenimiento";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        ValidarTexto.validarTexto(HECHO_PARA_TI),
        ValidarTextoQueContengaX.elTextoContiene(CONTENIDO_EXCLUSIVO_PARA_TI));

    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable validar() {
    return instrumented(ValidarRedireccionEntretenimiento.class);
  }
}
