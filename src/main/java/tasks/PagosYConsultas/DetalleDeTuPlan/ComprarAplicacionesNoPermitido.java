package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class ComprarAplicacionesNoPermitido implements Task {

    private static final String paso1 = "Ingresar a Comprar aplicaciones";
    private static final String paso2 = "Validar mensaje de error - Plan no permite";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // COMPRAR APLICACIONES
        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(COMPRAR_APLICACIONES),
                WaitForResponse.withText(INTENTALO_MAS_TARDE)
        );

        // VALIDAR MENSAJE DE ERROR
        actor.attemptsTo(
                ValidarTexto.validarTexto(INTENTALO_MAS_TARDE),
                ValidarTextoQueContengaX.elTextoContiene(ALGO_SALIO_MAL_PROCESAR),
                ValidarTexto.validarTexto(ACEPTAR_2)
        );

        EvidenciaUtils.registrarCaptura(paso2);

        // Cerrar modal de error
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ACEPTAR_2)
        );
    }

    public static Performable ingresarYValidar() {
        return instrumented(ComprarAplicacionesNoPermitido.class);
    }
}