package tasks.ConsultarConsumos;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class AccederConsultarConsumos implements Task {

  private static final String paso = "Acceder a Consultar Consumos";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        ScrollHastaTexto.conTexto(PAGOS_Y_CONSULTAS),
        ClickTextoQueContengaX.elTextoContiene(VER_MAS),
        ScrollHastaTexto.conTexto(CONSULTAR_CONSUMOS),
        ClickTextoQueContengaX.elTextoContiene(CONSULTAR_CONSUMOS),
        WaitFor.aTime(3000));

    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable acceder() {
    return instrumented(AccederConsultarConsumos.class);
  }
}
