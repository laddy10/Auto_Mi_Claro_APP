package tasks.Entretenimiento.ValidarTC;

import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.AdbUtils;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.EntretenimientoPage.*;
import static utils.Constants.*;

/**
 * Task para validar términos y condiciones Netflix
 */
public class ValidarTerminosCondicionesNetflix implements Task {

    private static final String paso = "Validar y aceptar Términos y Condiciones Netflix";
    private static final String paso2 = "Aceptar TC";
    private static final String paso3 = "Continuar";
    private static final String paso4 = "Aceptar";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitForResponse.withText("Netflix"),
                ValidarTextoQueContengaX.elTextoContiene("Netflix"),
                ValidarTexto.validarTexto(ESCRIBIR_CODIGO_VENDEDOR),
                ValidarTextoQueContengaX.elTextoContiene(ACEPTAR_TERMINOS_CONDICIONES)
        );
        EvidenciaUtils.registrarCaptura(paso);

        AdbUtils.ejecutarAdbTap(259, 1487);
        EvidenciaUtils.registrarCaptura(paso2);
        actor.attemptsTo(
                ValidarTexto.validarTexto(CONTINUAR),
                Click.on(BTN_CONTINUAR)
        );
        EvidenciaUtils.registrarCaptura(paso3);
        actor.attemptsTo(
                ValidarTexto.validarTexto(CONTINUAR),
                Click.on(BTN_CONTINUAR_2)
        );
        EvidenciaUtils.registrarCaptura(paso4);
        actor.attemptsTo(
                ValidarTexto.validarTexto(ACEPTAR_2),
                Click.on(BTN_ACEPTAR_ET)
        );

    }

    public static Performable validar() {
        return instrumented(ValidarTerminosCondicionesNetflix.class);
    }
}
