package tasks.Entretenimiento;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.EntretenimientoPage.*;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class AccederEntretenimiento implements Task {
  private static final String paso = "Entretenimiento en barra inferior";
  private static final String paso2 = "Acceder a Entretenimiento";

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(paso);
    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(ENTRETENIMIENTO), WaitFor.aTime(3000));
    EvidenciaUtils.registrarCaptura(paso2);
  }

  public static Performable acceder() {
    return instrumented(AccederEntretenimiento.class);
  }
}
