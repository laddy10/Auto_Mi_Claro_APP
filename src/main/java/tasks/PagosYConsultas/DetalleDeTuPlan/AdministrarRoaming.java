package tasks.PagosYConsultas.DetalleDeTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class AdministrarRoaming implements Task {

  private static final User user = TestDataProvider.getRealUser();
  private static final String paso1 = "Hacer clic en Administrar Roaming";
  private static final String paso2 =
      "Seleccionar línea para administrar Roaming " + user.getNumero();

  @Override
  public <T extends Actor> void performAs(T actor) {

    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(ADMINISTRAR_ROAMING),
        WaitForResponse.withText(ACTIVA_ROAMING));

    // Seleccionar la línea específica del usuario
    AndroidObject.scrollCorto2(actor, LINEA_TEXTO + " " + user.getNumero() + " " + ADMINISTRAR);

    EvidenciaUtils.registrarCaptura(paso2);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(user.getNumero()),
        WaitForResponse.withText(SERVICIO_ROAMING));
  }

  public static Performable ingresarRoaming() {
    return instrumented(AdministrarRoaming.class);
  }
}
