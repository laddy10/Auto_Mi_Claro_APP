package tasks.Entretenimiento;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.Scroll.ScrollHorizontalHastaTexto;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.EntretenimientoPage.*;
import static utils.Constants.*;

/**
 * Task para seleccionar Plan Premium Disney+
 */
public class SeleccionarPlanPremiumDisney implements Task {

    private static final String paso = "Seleccionar Plan Premium Disney+";

    @Override
    public <T extends Actor> void performAs(T actor) {
        WaitForResponse.withText("Disney+ Premium");
        actor.attemptsTo(
                WaitFor.aTime(3000),
                ValidarTextoQueContengaX.elTextoContiene("Disney+ Premium")
        );
        EvidenciaUtils.registrarCaptura(paso);
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ELEGIR_PLAN)
        );
    }

    public static Performable seleccionar() {
        return instrumented(SeleccionarPlanPremiumDisney.class);
    }
}
