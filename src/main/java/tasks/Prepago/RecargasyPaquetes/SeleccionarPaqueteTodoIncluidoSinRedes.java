package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.ELIGE_TU_PAQUETE_IDEAL;
import static utils.ConstantsPaquetes.PAQUETES_TODO_INCLUIDO;

import interactions.Click.ClickElementByText;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
import utils.CapturaDePantallaMovil;
import utils.EvidenciaUtils;

public class SeleccionarPaqueteTodoIncluidoSinRedes implements Task {

  private static final String PASO = "Seleccionar Paquetes todo incluido sin redes";

  private static final Target BTN_PAQUETES_TODO_INCLUIDO_EXACTO =
      Target.the("Opción Paquetes todo incluido sin redes")
          .located(
              By.xpath(
                  "//*[@text='Paquetes todo incluido' and not(contains(@text, 'con redes'))]"));

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(PASO);

    try {
      actor.attemptsTo(
          Click.on(BTN_PAQUETES_TODO_INCLUIDO_EXACTO),
          WaitForResponse.withText(ELIGE_TU_PAQUETE_IDEAL));
    } catch (Exception e) {
      try {
        actor.attemptsTo(
            ClickElementByText.clickElementByText(PAQUETES_TODO_INCLUIDO),
            WaitForResponse.withText(ELIGE_TU_PAQUETE_IDEAL));
      } catch (Exception e2) {
        actor.attemptsTo(WaitFor.aTime(2000));
      }
    }

    CapturaDePantallaMovil.tomarCapturaPantalla("Paquetes todo incluido sin redes seleccionados");
    EvidenciaUtils.registrarCaptura("Paquetes todo incluido sin redes seleccionados");
  }

  public static Performable seleccionar() {
    return instrumented(SeleccionarPaqueteTodoIncluidoSinRedes.class);
  }
}
