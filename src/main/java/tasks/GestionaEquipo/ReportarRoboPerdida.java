package tasks.GestionaEquipo;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class ReportarRoboPerdida implements Task {

  private static final String paso = "Reportar por Robo o Pérdida";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(REPORTAR_POR_ROBO_PERDIDA),
        WaitFor.aTime(2000),
        ValidarTexto.validarTexto(REPORTAR_POR_ROBO_PERDIDA_TITULO),
        ValidarTextoQueContengaX.elTextoContiene(CONFIRMA_NUMERO_LINEA_DATOS),
        ValidarTexto.validarTexto(REPORTAR));

    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable reportar() {
    return instrumented(ReportarRoboPerdida.class);
  }
}
