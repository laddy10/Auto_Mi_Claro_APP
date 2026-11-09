package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.ELIGE_TU_PAQUETE_IDEAL;
import static utils.ConstantsPaquetes.PAQUETES_APPS;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.CapturaDePantallaMovil;
import utils.EvidenciaUtils;

public class SeleccionarPaqueteDeApps implements Task {

  private static final String PASO = "Seleccionar Paquetes apps";

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(PASO);

    try {
      actor.attemptsTo(
          ClickTextoQueContengaX.elTextoContiene(PAQUETES_APPS),
          WaitForResponse.withText(ELIGE_TU_PAQUETE_IDEAL));
    } catch (Exception e) {
      actor.attemptsTo(
          ClickTextoQueContengaX.elTextoContiene(PAQUETES_APPS),
          WaitFor.aTime(2000));
    }

    CapturaDePantallaMovil.tomarCapturaPantalla("Paquetes apps seleccionados");
    EvidenciaUtils.registrarCaptura("Paquetes apps seleccionados");
  }

  public static Performable seleccionar() {
    return instrumented(SeleccionarPaqueteDeApps.class);
  }
}
