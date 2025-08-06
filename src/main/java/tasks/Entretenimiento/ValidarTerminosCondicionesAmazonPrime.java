package tasks.Entretenimiento;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static userinterfaces.EntretenimientoPage.*;
import static userinterfaces.LoginPage.BTN_ACEPTAR;
import static utils.Constants.*;

/**
 * Task para validar términos y condiciones Amazon Prime
 */
public class ValidarTerminosCondicionesAmazonPrime implements Task {

    private static final String paso = "Validar y aceptar Términos y Condiciones Amazon Prime";
    private static final String paso2 = "Aceptar TC";
    private static final String paso3 = "Aceptar";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitForResponse.withText("Amazon Prime"),
                ValidarTextoQueContengaX.elTextoContiene("Amazon Prime"),
                ValidarTexto.validarTexto(ESCRIBIR_CODIGO_VENDEDOR)
        );
        EvidenciaUtils.registrarCaptura(paso);
        actor.attemptsTo(
                Click.on(CHECK_TERMINOS_AMAZON_PRIME)
        );
        EvidenciaUtils.registrarCaptura(paso2);
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene("Activar")

        );
        EvidenciaUtils.registrarCaptura(paso3);
        actor.attemptsTo(
                ValidarTexto.validarTexto(ACEPTAR_2),
                Click.on(BTN_ACEPTAR_AMAZON)
        );
    }

    public static Performable validar() {
        return instrumented(ValidarTerminosCondicionesAmazonPrime.class);
    }
}
