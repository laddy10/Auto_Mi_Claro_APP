package tasks.GestionaEquipo;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class ConsultarEquipo implements Task {

  private static final String paso = "Consultar Equipo";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(CONSULTAR_EQUIPO),
        WaitFor.aTime(2000),
        ValidarTexto.validarTexto(CONSULTAR_EQUIPO_TITULO),
        ValidarTexto.validarTexto(TU_EQUIPO_ACTUAL));

    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable consultar() {
    return instrumented(ConsultarEquipo.class);
  }
}
