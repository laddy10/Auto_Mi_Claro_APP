package tasks.Entretenimiento;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.LoginPage.BTN_ACEPTAR;
import static utils.Constants.*;
import static userinterfaces.EntretenimientoPage.*;

/**
 * Task para validar y aceptar términos y condiciones de Disney+
 */
public class ValidarTerminosCondicionesDisneyEstandar implements Task {

    private static final String paso = "Validar Plan y Términos y Condiciones Disney+";
    private static final String paso2 = "Aceptar TC";
    private static final String paso3 = "Aceptar";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitForResponse.withText(ESCRIBIR_CODIGO_VENDEDOR),
                ValidarTexto.validarTexto(ESCRIBIR_CODIGO_VENDEDOR)
        );
        EvidenciaUtils.registrarCaptura(paso);
        actor.attemptsTo(
                Click.on(CHECK_TERMINOS)
        );
        EvidenciaUtils.registrarCaptura(paso2);
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene("Activar")

        );
        EvidenciaUtils.registrarCaptura(paso3);
        actor.attemptsTo(
                ValidarTexto.validarTexto(ACEPTAR_2),
                Click.on(BTN_ACEPTAR)
        );

    }

    public static Performable validar() {
        return instrumented(ValidarTerminosCondicionesDisneyEstandar.class);
    }
}
