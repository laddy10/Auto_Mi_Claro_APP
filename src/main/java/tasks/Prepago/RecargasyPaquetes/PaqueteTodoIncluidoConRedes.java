package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.VER_DETALLE_DEL_PAQUETE;
import static utils.ConstantsPaquetes.PAQUETES_TODO_INCLUIDO_CON_REDES;

import interactions.Scroll.ScrollAndClick;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
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

    try {
      actor.attemptsTo(
          ScrollAndClick.scrollAndClick(PAQUETES_TODO_INCLUIDO_CON_REDES),
          WaitForTextContains.withAnyTextContains(15, "PAQUETES", "2 GB", "Precio", "Min ILIMITADOS"),
          WaitFor.aTime(1500));
    } catch (Exception e) {
      actor.attemptsTo(WaitFor.aTime(2000));
    }

    CapturaDePantallaMovil.tomarCapturaPantalla("Paquetes todo incluido con redes seleccionados");
    EvidenciaUtils.registrarCaptura("Paquetes todo incluido con redes seleccionados");
  }

  public static Performable paqueteTodoIncluidoConRedes() {
    return instrumented(PaqueteTodoIncluidoConRedes.class);
  }
}
