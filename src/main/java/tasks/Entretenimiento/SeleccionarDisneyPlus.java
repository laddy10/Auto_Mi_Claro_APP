package tasks.Entretenimiento;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.EntretenimientoPage.*;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

/** Task para seleccionar Disney+ */
public class SeleccionarDisneyPlus implements Task {

  private static final String paso = "Scrool hasta tus plataformas favoritas y seleccionar ver mas";
  private static final String paso2 = "Seleccionar Disney+";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(ScrollHastaTexto.conTexto(TUS_PLATAFORMAS_FAVORITAS));
    EvidenciaUtils.registrarCaptura(paso);
    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(VER_MAS));
    EvidenciaUtils.registrarCaptura(paso2);
    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(DISNEY_PLUS));
  }

  public static Performable seleccionar() {
    return instrumented(SeleccionarDisneyPlus.class);
  }
}
