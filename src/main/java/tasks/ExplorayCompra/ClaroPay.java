package tasks.ExplorayCompra;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class ClaroPay implements Task {

  private static final String paso1 = "Hacer clic en Claro Pay";
  private static final String paso2 = "Validar direccionamiento a Claro Pay";

  @Override
  public <T extends Actor> void performAs(T actor) {

    EvidenciaUtils.registrarCaptura(paso1);

    // Hacer clic en Claro Pay
    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(CLARO_PAY),
        WaitForResponse.withText(INICIAR_SESION));

    // Validar opciones de botones
    actor.attemptsTo(
        ValidarTexto.validarTexto(INICIAR_SESION), ValidarTexto.validarTexto(REGISTRARME));

    EvidenciaUtils.registrarCaptura(paso2);
  }

  public static Performable validarDireccionamiento() {
    return instrumented(ClaroPay.class);
  }
}
