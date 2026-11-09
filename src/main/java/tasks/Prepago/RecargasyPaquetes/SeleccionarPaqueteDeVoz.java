package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.ELIGE_TU_PAQUETE_IDEAL;
import static utils.ConstantsPaquetes.PAQUETES_DE_VOZ;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.CapturaDePantallaMovil;
import utils.EvidenciaUtils;

public class SeleccionarPaqueteDeVoz implements Task {

  private static final String PASO = "Seleccionar Paquetes de voz";

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(PASO);

    try {
      actor.attemptsTo(
          ClickTextoQueContengaX.elTextoContiene(PAQUETES_DE_VOZ),
          WaitForResponse.withText(ELIGE_TU_PAQUETE_IDEAL));
    } catch (Exception e) {
      actor.attemptsTo(
          ClickTextoQueContengaX.elTextoContiene(PAQUETES_DE_VOZ),
          WaitFor.aTime(2000));
    }

    CapturaDePantallaMovil.tomarCapturaPantalla("Paquetes de voz seleccionados");
    EvidenciaUtils.registrarCaptura("Paquetes de voz seleccionados");
  }

  public static Performable seleccionar() {
    return instrumented(SeleccionarPaqueteDeVoz.class);
  }
}
