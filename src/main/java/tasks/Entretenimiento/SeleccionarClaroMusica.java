package tasks.Entretenimiento;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.EntretenimientoPage.*;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

// ===========================================
// SA062 - CLARO MÚSICA
// ===========================================

/** Task para seleccionar Claro Música */
public class SeleccionarClaroMusica implements Task {

  private static final String paso = "Seleccionar Claro Música";

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(paso);
    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(CLARO_MUSICA), WaitFor.aTime(3000));
  }

  public static Performable seleccionar() {
    return instrumented(SeleccionarClaroMusica.class);
  }
}
