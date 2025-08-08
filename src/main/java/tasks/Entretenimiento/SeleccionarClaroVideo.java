package tasks.Entretenimiento;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.EntretenimientoPage.*;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

/** Task para seleccionar Claro Video */
public class SeleccionarClaroVideo implements Task {
  private static final String paso = "Buscar y seleccionar Claro Video en pantalla";

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(paso);
    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(CLARO_VIDEO));
  }

  public static Performable seleccionar() {
    return instrumented(SeleccionarClaroVideo.class);
  }
}
