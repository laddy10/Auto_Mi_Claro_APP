package tasks.Entretenimiento.ValidarTC;

import interactions.Click.ClickElementByText;
import interactions.validations.ValidarElemento;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitElement;
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
    private static final String paso4 = "Se da click en Activar y se acepta verificacion de identidad";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitElement.isVisible(IMAGEN_NETFLIX),
                ValidarTexto.validarTexto(ESCRIBIR_CODIGO_VENDEDOR),
                ValidarTextoQueContengaX.elTextoContiene(ACEPTAR_TERMINOS_CONDICIONES),
                ValidarTextoQueContengaX.elTextoContiene(TERMINOS_Y_CONDICIONES_2)
        );
        EvidenciaUtils.registrarCaptura(paso);

        AdbUtils.ejecutarAdbTap(325, 1246);
        EvidenciaUtils.registrarCaptura(paso2);
        actor.attemptsTo(
                ValidarTexto.validarTexto(ACTIVAR),
                ClickElementByText.clickElementByText(ACTIVAR)
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
