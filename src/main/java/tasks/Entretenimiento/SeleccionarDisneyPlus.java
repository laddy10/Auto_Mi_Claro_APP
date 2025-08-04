package tasks.Entretenimiento;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
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
 * Task para seleccionar Disney+
 */
public class SeleccionarDisneyPlus implements Task {

    private static final String paso = "Seleccionar Disney+";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ScrollHastaTexto.conTexto(TUS_PLATAFORMAS_FAVORITAS),
                ClickTextoQueContengaX.elTextoContiene(VER_MAS),
                WaitFor.aTime(2000),
                ClickTextoQueContengaX.elTextoContiene(DISNEY_PLUS),
                WaitFor.aTime(3000)
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable seleccionar() {
        return instrumented(SeleccionarDisneyPlus.class);
    }
}
