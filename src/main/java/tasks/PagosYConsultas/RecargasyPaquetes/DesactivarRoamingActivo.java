package tasks.PagosYConsultas.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import tasks.PagosYConsultas.DetalleDeTuPlan.DesactivarServicioRoaming;
import tasks.PagosYConsultas.DetalleDeTuPlan.RoamingTestLines;
import tasks.PagosYConsultas.DetalleDeTuPlan.RoamingScreen;
import utils.EvidenciaUtils;

public class DesactivarRoamingActivo implements Task {

  @Override
  public <T extends Actor> void performAs(T actor) {
    String line = RoamingTestLines.configured("roaming.sa099.line", "3102635974");
    try (RoamingScreen screen = new RoamingScreen(actor)) {
      screen.open(line);
      if (!screen.state()) {
        EvidenciaUtils.registrarCaptura("Preparar Roaming activo para SA099");
        screen.activateIndefinite();
      } else {
        EvidenciaUtils.registrarCaptura("Servicio Roaming ya activo antes de desactivarlo");
      }
      DesactivarServicioRoaming.desactivar(screen);
      screen.verifyAfterReopen(line);
    }
  }

  public static Performable desactivarRoamingActivo() {
    return instrumented(DesactivarRoamingActivo.class);
  }
}
