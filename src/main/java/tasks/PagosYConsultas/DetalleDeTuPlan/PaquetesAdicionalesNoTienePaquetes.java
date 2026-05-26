package tasks.PagosYConsultas.DetalleDeTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.POPUP_SIN_PQ_ADICIONALES;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import java.util.List;

public class PaquetesAdicionalesNoTienePaquetes implements Task {

  private static final String paso1 = "Validar popup - No cuenta con paquetes adicionales";
  private static final String paso2 = "Validar pantalla - Paquetes adicionales activos";

  @Override
  public <T extends Actor> void performAs(T actor) {

    EvidenciaUtils.registrarCaptura(paso1);

    // Verificar si el popup está presente
    List<WebElementFacade> popup = POPUP_SIN_PQ_ADICIONALES.resolveAllFor(actor);

    if (!popup.isEmpty()) {
      // ESCENARIO 1: Popup presente - el usuario no tiene paquetes adicionales
      actor.attemptsTo(
              ValidarTexto.validarTexto(ACTUALMENTE_NO_CUENTA_PAQUETES),
              ValidarTexto.validarTexto(ACEPTAR_2),
              ClickTextoQueContengaX.elTextoContiene(ACEPTAR_2));

    } else {
      // ESCENARIO 2: Sin popup - la pantalla muestra paquetes adicionales activos
      EvidenciaUtils.registrarCaptura(paso2);

      actor.attemptsTo(
              ValidarTexto.validarTexto(PAQUETES_ADICIONALES),
              ValidarTextoQueContengaX.elTextoContiene(VER_MAS),
              ValidarTextoQueContengaX.elTextoContiene(ACTIVO));
    }
  }

  public static Performable validarPopup() {
    return instrumented(PaquetesAdicionalesNoTienePaquetes.class);
  }
}