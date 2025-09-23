package tasks.PagosYConsultas.eSIM;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class SeleccionarLineaPostEsim implements Task {

  private static final String NUMERO_LINEA = "322 278 6448";


  private static final User user = TestDataProvider.getRealUser();
  private static final String paso1 = "Seleccionar línea eSIM " + NUMERO_LINEA;

  @Override
  public <T extends Actor> void performAs(T actor) {

    AndroidObject.scrollCorto2(actor, NUMERO_LINEA + " " + SELECCIONAR);

    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(NUMERO_LINEA),
        WaitForResponse.withText(SIGUIENTE),
        WaitFor.aTime(2000));
  }

  public static Performable seleccionarLinea() {
    return instrumented(SeleccionarLineaPostEsim.class);
  }
}
