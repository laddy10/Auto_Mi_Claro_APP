package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.ELIGE_TU_PAQUETE_IDEAL;
import static utils.ConstantsPaquetes.PAQUETES_DE_DATOS;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class SeleccionarPaqueteDeDatos implements Task {

  private static final String PASO = "Seleccionar Paquetes de datos";

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(PASO);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(PAQUETES_DE_DATOS),
        WaitForResponse.withText(ELIGE_TU_PAQUETE_IDEAL));
  }

  public static Performable seleccionar() {
    return instrumented(SeleccionarPaqueteDeDatos.class);
  }
}
