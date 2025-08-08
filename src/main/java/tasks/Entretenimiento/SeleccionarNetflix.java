package tasks.Entretenimiento;

// ===========================================
// SA063 - NETFLIX
// ===========================================

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.EntretenimientoPage.*;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

/** Task para seleccionar Netflix */
public class SeleccionarNetflix implements Task {

  private static final String paso = "Seleccionar Netflix";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(ScrollHastaTexto.conTexto(TUS_PLATAFORMAS_FAVORITAS));
    EvidenciaUtils.registrarCaptura(paso);
    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(NETFLIX));
  }

  public static Performable seleccionar() {
    return instrumented(SeleccionarNetflix.class);
  }
}
