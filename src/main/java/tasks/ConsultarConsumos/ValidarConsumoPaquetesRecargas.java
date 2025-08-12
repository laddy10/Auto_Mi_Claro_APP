package tasks.ConsultarConsumos;

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

public class ValidarConsumoPaquetesRecargas implements Task {

  private static final String paso = "Validar Consumo Paquetes y Recargas";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(CONSUMO_PAQUETES_Y_RECARGAS),
        WaitFor.aTime(3000),
        ValidarTexto.validarTexto(CONSUMO_PAQUETES_Y_RECARGAS),
        ValidarTexto.validarTexto(LINEA_POST),
        ValidarTexto.validarTexto(SALDO_DISPONIBLE),
        ValidarTexto.validarTexto(FECHA_VENCIMIENTO),
        ValidarTextoQueContengaX.elTextoContiene(CONSULTA_PAQUETES_RECARGAS_ACTIVOS));

    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable validar() {
    return instrumented(ValidarConsumoPaquetesRecargas.class);
  }
}
