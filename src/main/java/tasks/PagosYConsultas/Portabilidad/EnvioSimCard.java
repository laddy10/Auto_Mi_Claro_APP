package tasks.PagosYConsultas.Portabilidad;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class EnvioSimCard implements Task {

  private static final String paso1 = "Ingresar a Envío de SIM Card";
  private static final String paso2 = "Validar redirección a formulario";

  @Override
  public <T extends Actor> void performAs(T actor) {

    // Ingresar a Envío de SIM Card
    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(ENVIO_DE_SIM_CARD),
        WaitForResponse.withText(CONSULTA_EL_ESTADO_TU_PEDIDO));

    // Validar redirección a formulario
    EvidenciaUtils.registrarCaptura(paso2);

    actor.attemptsTo(
        ValidarTexto.validarTexto(NUMERO_DE_PEDIDO),
        ValidarTexto.validarTexto(SELECCIONA_LA_IMAGEN_ETIQUETA),
        ValidarTexto.validarTexto(CONSULTAR));
  }

  public static Performable validarEnvioSimCard() {
    return instrumented(EnvioSimCard.class);
  }
}
