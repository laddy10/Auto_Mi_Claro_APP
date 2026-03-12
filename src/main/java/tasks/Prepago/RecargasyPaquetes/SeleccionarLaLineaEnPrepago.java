package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.AndroidObject.scrollCortoSinCentrar;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class SeleccionarLaLineaEnPrepago implements Task {

  private static final User user = TestDataProvider.getRealUser();
  private static final String paso2 = "Seleccionar línea prepago " + user.getNumeroPrepago();
  ;

  @Override
  public <T extends Actor> void performAs(T actor) {

    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(PREPAGO));

    scrollCortoSinCentrar(actor, LINEA + " " + user.getNumeroPrepago() + " " + VER_DETALLE);

    EvidenciaUtils.registrarCaptura(paso2);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(user.getNumeroPrepago())
        // revisar WaitForResponse.withText(CLARO_COLOMBIA)
        );
  }

  public static Performable seleccionar() {
    return instrumented(SeleccionarLaLineaEnPrepago.class);
  }
}
