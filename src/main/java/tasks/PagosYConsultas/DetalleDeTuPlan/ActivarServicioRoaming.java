package tasks.PagosYConsultas.DetalleDeTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

public class ActivarServicioRoaming implements Task {
  @Override
  public <T extends Actor> void performAs(T actor) {
    String line = RoamingTestLines.configured("roaming.sa097.line", "3102628443");
    try (RoamingScreen screen = new RoamingScreen(actor)) {
      screen.open(line);
      screen.prepareInactive(line);
      screen.activateIndefinite();
      DesactivarServicioRoaming.desactivar(screen);
      screen.verifyAfterReopen(line);
    }
  }

  public static void activarEnPantalla(Actor actor) {
    try (RoamingScreen screen = new RoamingScreen(actor)) {
      screen.activateIndefinite();
    }
  }

  public static Performable activarServicioRoaming() {
    return instrumented(ActivarServicioRoaming.class);
  }
}
