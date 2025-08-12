package tasks.PagosYConsultas.DetalleDeTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.BTN_ELEGIDOS_TODO_DESTINO;
import static utils.Constants.*;

import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

public class ElegidosTodoDestino implements Task {

  private static final String paso1 = "Ingresar opción Elegidos todo destino";

  @Override
  public <T extends Actor> void performAs(T actor) {

    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        Click.on(BTN_ELEGIDOS_TODO_DESTINO), WaitForResponse.withText(ELEGIDOS_TODO_DESTINO));
  }

  public static Performable ingresarOpcion() {
    return instrumented(ElegidosTodoDestino.class);
  }
}
