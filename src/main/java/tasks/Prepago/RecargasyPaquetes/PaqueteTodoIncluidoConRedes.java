package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.VER_DETALLE_DEL_PAQUETE;
import static utils.ConstantsPaquetes.PAQUETES_TODO_INCLUIDO_CON_REDES;

import interactions.Scroll.ScrollAndClick;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import utils.EvidenciaUtils;

public class PaqueteTodoIncluidoConRedes extends AndroidObject implements Task {

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura("Seleccionar Paquetes todo incluido con redes");

    actor.attemptsTo(
        ScrollAndClick.scrollAndClick(PAQUETES_TODO_INCLUIDO_CON_REDES),
        WaitForResponse.withText(VER_DETALLE_DEL_PAQUETE, 45));

    CapturaDePantallaMovil.tomarCapturaPantalla("captura_pantalla");
    EvidenciaUtils.registrarCaptura("Paquetes todo incluido con redes seleccionados");
  }

  public static Performable paqueteTodoIncluidoConRedes() {
    return instrumented(PaqueteTodoIncluidoConRedes.class);
  }
}
