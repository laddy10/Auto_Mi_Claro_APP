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
 * Task para validar redirección a entretenimiento
 */
public class ValidarRedireccionEntretenimiento implements Task {

    private static final String paso = "Validar Redirección a Entretenimiento";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ValidarTexto.validarTexto(HECHO_PARA_TI),
                ValidarTextoQueContengaX.elTextoContiene(CONTENIDO_EXCLUSIVO_PARA_TI)
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable validar() {
        return instrumented(ValidarRedireccionEntretenimiento.class);
    }
}

