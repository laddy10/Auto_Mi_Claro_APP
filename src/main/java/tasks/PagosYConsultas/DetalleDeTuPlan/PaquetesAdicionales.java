package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.POPUP_SIN_PQ_ADICIONALES;
import static utils.Constants.*;

public class PaquetesAdicionales implements Task {

    private static final String paso1 = "Validar direccionamiento Paquetes adicionales";
    private static final String paso2 = "Validar Paquetes adicionales";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        // Verificar escenario 1: Sin paquetes adicionales

        List<WebElementFacade> popuppqadicionales = POPUP_SIN_PQ_ADICIONALES.resolveAllFor(actor);
        if (!popuppqadicionales.isEmpty()) {
            actor.attemptsTo(
                    WaitForResponse.withText(ACTUALMENTE_NO_CUENTA_PAQUETES),
                    ValidarTexto.validarTexto(ACTUALMENTE_NO_CUENTA_PAQUETES),
                    ClickTextoQueContengaX.elTextoContiene(ACEPTAR)
            );

        } else {
            // Escenario 2: Con paquetes adicionales
            actor.attemptsTo(
                    ValidarTexto.validarTexto(PAQUETES_ADICIONALES),
                    ValidarTextoQueContengaX.elTextoContiene(VER_MAS),
                    ValidarTextoQueContengaX.elTextoContiene(ACTIVO),
                    ClickTextoQueContengaX.elTextoContiene(VER_MAS),
                    WaitForResponse.withText(CONOCE_LOS_DETALLES_DEL_PAQUETE),
                    ValidarTexto.validarTexto(CONOCE_LOS_DETALLES_DEL_PAQUETE),
                    ClickTextoQueContengaX.elTextoContiene(ACEPTAR)
            );

            EvidenciaUtils.registrarCaptura(paso2);
        }
    }

    public static Performable validarDireccionamiento() {
        return instrumented(PaquetesAdicionales.class);
    }
}