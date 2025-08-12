package tasks.PagosYConsultas.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.CBX_TIPO_PAQUETE;
import static utils.Constants.*;

import interactions.Click.ClickElementByText;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class PaquetesDatos implements Task {
  private static final User user = TestDataProvider.getRealUser();
  private static final String paso1 = "Seleccionar el tipo de Paquetes";
  private static final String paso2 = "Seleccionar tipo de paquete: Paquetes de datos ";
  private static final String paso3 = "Ver detalle y comprar paquete";
  private static final String paso4 = "Verificar flujo de pago: Paquetes de datos ";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        ClickElementByText.clickElementByText(PAQUETES),
        WaitForResponse.withText(ELIGE_TIPO_PAQUETE),
        ValidarTextoQueContengaX.elTextoContiene(user.getNumero()));

    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(Click.on(CBX_TIPO_PAQUETE));

    EvidenciaUtils.registrarCaptura(paso2);

    // Seleccionar tipo de paquete dinámicamente
    String tipoPaquete = "Paquetes de datos";

    actor.attemptsTo(
        ClickElementByText.clickElementByText(tipoPaquete),
        ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE));

    EvidenciaUtils.registrarCaptura(paso3);

    actor.attemptsTo(
        ClickElementByText.clickElementByText(COMPRAR),
        WaitForResponse.withText(TARJETA_C_D),
        ValidarTexto.validarTexto(ELEGIR_OTRO_MEDIO_PAGO),
        ValidarTextoQueContengaX.elTextoContiene(TARJETA_C_D),
        ValidarTextoQueContengaX.elTextoContiene(DAVIPLATA),
        ValidarTextoQueContengaX.elTextoContiene(CODENSA),
        ValidarTextoQueContengaX.elTextoContiene(OTROS_MEDIOS),
        ValidarTextoQueContengaX.elTextoContiene(VALOR_A_PAGAR));

    EvidenciaUtils.registrarCaptura(paso4);
  }

  public static Performable seleccionarPaquetes() {
    return instrumented(PaquetesDatos.class);
  }
}
