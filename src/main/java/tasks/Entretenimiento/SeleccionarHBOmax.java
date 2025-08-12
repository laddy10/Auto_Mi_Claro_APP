package tasks.Entretenimiento;

// ===========================================
// SA065 - HBO MAX
// ===========================================

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.EntretenimientoPage.*;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

/** Task para seleccionar HBOmax */
public class SeleccionarHBOmax implements Task {

  private static final String paso = "Seleccionar HBOmax";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        ScrollHastaTexto.conTexto(TUS_PLATAFORMAS_FAVORITAS),
        ClickTextoQueContengaX.elTextoContiene(HBOMAX),
        WaitFor.aTime(3000));

    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable seleccionar() {
    return instrumented(SeleccionarHBOmax.class);
  }
}
