package tasks.PagosYConsultas.DetalleDeTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

public class ActivarServicioRoamingVigenciaDefinida implements Task {
  @Override
  public <T extends Actor> void performAs(T actor) {
    String line = RoamingTestLines.configured("roaming.sa098.line", "3102637102");
    try (RoamingScreen screen = new RoamingScreen(actor)) {
      screen.open(line);
      screen.prepareInactive(line);
      screen.activateWithExpiry();
      DesactivarServicioRoaming.desactivar(screen);
      screen.verifyAfterReopen(line);
    }
  }

  public static Performable activarServicioRoamingVigenciaDefinida() {
    return instrumented(ActivarServicioRoamingVigenciaDefinida.class);
  }
}
