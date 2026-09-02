package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.APLICACIONES_ELEGIBLES;
import static utils.Constants.COMPRAR_APLICACIONES;

public class AplicacionesElegiblesPermitido implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Ingresar a Aplicaciones elegibles";
    private static final String paso2 = "Validar direccionamiento Aplicaciones elegibles";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // APLICACIONES ELEGIBLES
        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(APLICACIONES_ELEGIBLES),
                WaitForResponse.withText(COMPRAR_APLICACIONES)
        );

        // VALIDAR DIRECCIONAMIENTO APLICACIONES ELEGIBLES
        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ValidarTexto.validarTexto(APLICACIONES_ELEGIBLES));
    }

    public static Performable ingresarYValidarPermitido() {
        return instrumented(AplicacionesElegiblesPermitido.class);
    }
}