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

/** Task para seleccionar Ver detalle en Claro Música */
public class SeleccionarVerDetalleClaroMusica implements Task {

  private static final String paso = "Seleccionar Ver Detalle Claro Música";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(VER_DETALLE), WaitFor.aTime(3000));

    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable seleccionar() {
    return instrumented(SeleccionarVerDetalleClaroMusica.class);
  }
}
