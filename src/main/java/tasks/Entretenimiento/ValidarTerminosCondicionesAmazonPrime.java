package tasks.Entretenimiento;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.EntretenimientoPage.*;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

/** Task para validar términos y condiciones Amazon Prime */
public class ValidarTerminosCondicionesAmazonPrime implements Task {

  private static final String paso = "Validar y aceptar Términos y Condiciones Amazon Prime";
  private static final String paso2 = "Aceptar TC";
  private static final String paso3 = "Aceptar";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        WaitForResponse.withText("Amazon Prime"),
        ValidarTextoQueContengaX.elTextoContiene("Amazon Prime"),
        ValidarTexto.validarTexto(ESCRIBIR_CODIGO_VENDEDOR));
    EvidenciaUtils.registrarCaptura(paso);
    actor.attemptsTo(Click.on(CHECK_TERMINOS_AMAZON_PRIME));
    EvidenciaUtils.registrarCaptura(paso2);
    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene("Activar"));

    EvidenciaUtils.registrarCaptura(paso3);
    actor.attemptsTo(ValidarTexto.validarTexto(ACEPTAR_2), Click.on(BTN_ACEPTAR_AMAZON));
  }

  public static Performable validar() {
    return instrumented(ValidarTerminosCondicionesAmazonPrime.class);
  }
}
