package tasks.PagosYConsultas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarCantidadPagosHistorial;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class HistorialDePagos implements Task {
  private final User user = TestDataProvider.getRealUser();
  private static final String paso1 = "Seleccionar Historial de pagos";
  private static final String paso2 = "Verificar información del historial";
  private static final String paso3 = "Validar detalles de los pagos registrados";

  @Override
  public <T extends Actor> void performAs(T actor) {

    // Scroll hasta la sección "Últimos pagos"
    actor.attemptsTo(ScrollHastaTexto.conTexto(ULTIMOS_PAGOS));

    EvidenciaUtils.registrarCaptura(paso1);

    // Seleccionar "Historial de pagos"
    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(HISTORIAL_DE_PAGOS),
        WaitForResponse.withText(POSTPAGO));

    // Verificar información principal del historial
    actor.attemptsTo(
        ValidarTexto.validarTexto(POSTPAGO),
        ValidarTexto.validarTexto(HISTORIAL_DE_PAGOS),
        ValidarTextoQueContengaX.elTextoContiene(ESTOS_SON_LOS_ULTIMOS_6_PAGOS));

    EvidenciaUtils.registrarCaptura(paso2);

    // Validar información detallada de los pagos (sin valores específicos)
    actor.attemptsTo(
        // Validar campos comunes de todos los pagos
        ValidarTexto.validarTexto(CANAL_DE_PAGO),
        ValidarTexto.validarTexto(FECHA_DE_PAGO),
        ValidarTexto.validarTexto(VALOR_DE_PAGO),

        // Validar que existan registros de pagos (estructura general)
        ValidarCantidadPagosHistorial.minimo(1) // Al menos 1 pago registrado
        );

    EvidenciaUtils.registrarCaptura(paso3);
  }

  public static Performable validarHistorialDePagos() {
    return instrumented(HistorialDePagos.class);
  }
}
