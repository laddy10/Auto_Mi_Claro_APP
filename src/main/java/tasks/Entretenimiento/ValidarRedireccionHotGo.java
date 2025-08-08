package tasks.Entretenimiento;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static userinterfaces.EntretenimientoPage.*;
import static utils.Constants.*;

import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;
import utils.EvidenciaUtils;

/** Task para validar redirección HotGo */
public class ValidarRedireccionHotGo implements Task {

  private static final String paso = "Validar Redirección HotGo";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        WaitUntil.the(LBL_ESPERA_UN_MOMENTO, isNotPresent()).forNoMoreThan(30).seconds(),
        WaitFor.aTime(5000));

    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable validar() {
    return instrumented(ValidarRedireccionHotGo.class);
  }
}
