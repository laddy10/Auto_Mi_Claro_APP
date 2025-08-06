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
 * Task para seleccionar Ver detalle en Netflix
 */
public class SeleccionarVerDetalleNetflix implements Task {

    private static final String paso = "Seleccionar Ver Detalle Netflix";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(LBL_ESPERA_UN_MOMENTO, isNotPresent()).forNoMoreThan(30).seconds(),
                ValidarTexto.validarTexto(NETFLIX_TITULO),
                ValidarTexto.validarTexto(NUMERO_DE_MOVIL),
                ValidarTexto.validarTexto(POSTPAGO),
                ClickTextoQueContengaX.elTextoContiene(VER_DETALLE),
                WaitFor.aTime(3000)
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable seleccionar() {
        return instrumented(SeleccionarVerDetalleNetflix.class);
    }
}
