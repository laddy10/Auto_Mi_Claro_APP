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
import static utils.Constants.*;

/**
 * Task para seleccionar plan Amazon Prime
 */
public class SeleccionarPlanAmazonPrime implements Task {

    private static final String paso = "Seleccionar Plan Amazon Prime";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitForResponse.withText(ELEGIR_PLAN),
                ValidarTextoQueContengaX.elTextoContiene("Amazon Prime")
        );
        EvidenciaUtils.registrarCaptura(paso);
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ELEGIR_PLAN)
        );
    }

    public static Performable seleccionar() {
        return instrumented(SeleccionarPlanAmazonPrime.class);
    }
}
