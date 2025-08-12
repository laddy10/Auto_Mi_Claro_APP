package tasks.Entretenimiento;

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

// ===========================================
// SA069 - AMAZON PRIME
// ===========================================

/** Task para seleccionar Amazon Prime */
public class SeleccionarAmazonPrime implements Task {

  private static final String paso =
      "Scroll hasta tus plataformas favoritas y Seleccionar Amazon Prime";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(ScrollHastaTexto.conTexto(TUS_PLATAFORMAS_FAVORITAS));
    EvidenciaUtils.registrarCaptura(paso);
    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(AMAZON_PRIME), WaitFor.aTime(3000));
  }

  public static Performable seleccionar() {
    return instrumented(SeleccionarAmazonPrime.class);
  }
}
