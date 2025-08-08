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

/** Task para acceder a Ver más plataformas */
public class AccederVerMasPlataformas implements Task {

  private static final String paso = "Acceder Ver Más Plataformas";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        ScrollHastaTexto.conTexto(TUS_PLATAFORMAS_FAVORITAS),
        ClickTextoQueContengaX.elTextoContiene(VER_MAS),
        WaitFor.aTime(3000));

    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable acceder() {
    return instrumented(AccederVerMasPlataformas.class);
  }
}
