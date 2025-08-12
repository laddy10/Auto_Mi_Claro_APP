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
// SA060 - RED + TV EN VIVO
// ===========================================

/** Task para seleccionar RED + TV en vivo */
public class SeleccionarRedTVEnVivo implements Task {

  private static final String paso = "Seleccionar RED + TV en vivo";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(RED_TV_EN_VIVO), WaitFor.aTime(3000));

    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable seleccionar() {
    return instrumented(SeleccionarRedTVEnVivo.class);
  }
}
