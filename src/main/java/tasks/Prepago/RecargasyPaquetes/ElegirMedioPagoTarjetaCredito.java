package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.RESUMEN_COMPRA;
import static utils.Constants.TARJETA_C_D;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class ElegirMedioPagoTarjetaCredito implements Task {

  private static final String PASO = "Elegir medio de pago: Tarjeta de crédito/débito";

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(PASO);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(TARJETA_C_D),
        WaitForResponse.withText(RESUMEN_COMPRA),
        ValidarResumenCompraPaqueteDatos.validar());
  }

  public static Performable elegir() {
    return instrumented(ElegirMedioPagoTarjetaCredito.class);
  }
}
