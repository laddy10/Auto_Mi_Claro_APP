package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.MediosPagoPage.BTN_SELECCIONAR_PSE;
import static utils.Constants.RESUMEN_COMPRA;

import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

public class ElegirMedioPagoPSE implements Task {

  private static final String PASO = "Elegir medio de pago: PSE";

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(PASO);

    actor.attemptsTo(
        WaitFor.aTime(2000),
        Click.on(BTN_SELECCIONAR_PSE),
        WaitForResponse.withText(RESUMEN_COMPRA),
        ValidarResumenCompraPaqueteDatos.validar());
  }

  public static Performable elegir() {
    return instrumented(ElegirMedioPagoPSE.class);
  }
}
