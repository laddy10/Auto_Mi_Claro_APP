package tasks.GestionaEquipo;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class ValidarTextoYCerrar implements Task {

  private static final String paso = "Validar Texto y Cerrar Modal";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        // ValidarTextoQueContengaX.elTextoContiene("no están reportados por robo o pérdida"),
        ClickTextoQueContengaX.elTextoContiene(CERRAR));

    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable validarYCerrar() {
    return instrumented(ValidarTextoYCerrar.class);
  }
}
