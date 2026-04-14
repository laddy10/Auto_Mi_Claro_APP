package tasks.PagosYConsultas.AdquirirProductos;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosyConsultasPrePage.BTN_CERRAR_POPUP;
import static userinterfaces.PagosyConsultasPrePage.LBL_MENSAJE_COOKIES;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

public class ValidarPaginaClaro implements Task {

  private static final String paso1 = "Validar redireccionamiento a página de Claro";
  private static final String paso2 = "Validar página de Claro";

  @Override
  public <T extends Actor> void performAs(T actor) {

    if (BTN_CERRAR_POPUP.resolveFor(actor).isVisible()) {
      Click.on(BTN_CERRAR_POPUP);
      actor.attemptsTo(WaitFor.aTime(2000));
    }

    System.out.println("PopUp Cerrado Correctamente 📍");

    if (LBL_MENSAJE_COOKIES.resolveFor(actor).isVisible()) {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(ENTENDIDO), WaitFor.aTime(2000));
    } else {
      EvidenciaUtils.registrarCaptura(paso1);
    }

    actor.attemptsTo(
        ScrollHastaTexto.conTexto(COMPRA_POR_CATEGORIA));

    EvidenciaUtils.registrarCaptura(paso2);

    actor.attemptsTo(
            ScrollHastaTexto.conTexto(LOS_MAS_VENDIDOS));
  }

  public static Performable validarRedireccionamiento() {
    return instrumented(ValidarPaginaClaro.class);
  }
}
