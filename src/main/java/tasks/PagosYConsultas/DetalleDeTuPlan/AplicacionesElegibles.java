package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
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
import static utils.Constants.*;

public class AplicacionesElegibles implements Task {

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
                ValidarTexto.validarTexto(APLICACIONES_ELEGIBLES),
              //  ValidarTextoQueContengaX.elTextoContiene(user.getNumero()),
                ValidarTexto.validarTexto(COMPRAR_APLICACIONES),
                ValidarTexto.validarTexto(ADMINISTRA_LAS_APLICACIONES_INCLUIDAS)
        );
    }

    public static Performable ingresarYValidar() {
        return instrumented(AplicacionesElegibles.class);
    }
}