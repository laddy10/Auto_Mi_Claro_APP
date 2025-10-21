package tasks.ConsultarConsumos;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static userinterfaces.ConsultarConsumosPage.*;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.wait.WaitFor;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class SeleccionarLineaYVerDetalle implements Task {

  private static final User user = TestDataProvider.getRealUser();
  private static final String paso = "Seleccionar Línea y Ver Detalle" + user.getNumero();

  @Override
  public <T extends Actor> void performAs(T actor) {

    AndroidObject.scrollCorto2(actor, LINEA + " " + user.getNumero() + " " + VER_DETALLE);

    actor.attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(user.getNumero()),
        WaitUntil.the(LBL_ESPERA_UN_MOMENTO, isNotPresent()).forNoMoreThan(30).seconds(),
        WaitFor.aTime(3000));

    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable seleccionar() {
    return instrumented(SeleccionarLineaYVerDetalle.class);
  }
}
