package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class ValidarDireccionamientoARoaming implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Hacer clic en Activar Roaming";
    private static final String paso2 = "Seleccionar línea postpago " + user.getNumero();
    private static final String paso3 = "Validar direccionamiento Roaming";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // 1. Hacer clic en Activar Roaming
        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ACTIVAR_ROAMING),
                WaitForResponse.withText(ACTIVA_ROAMING)
        );

        // 2. Seleccionar línea postpago (reutilizando tu clase existente)
        AndroidObject.scrollCorto2(actor, LINEA_TEXTO + " " + user.getNumero() + " " + ADMINISTRAR);

        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(user.getNumero()),
                WaitForResponse.withText(SERVICIO_ROAMING)
        );


        // 3. Validar direccionamiento Roaming (reutilizando tu clase existente)

        actor.attemptsTo(
                DireccionamientoRoaming.validarDireccionamiento()
        );
    }

    public static Performable validarDireccionamiento() {
        return instrumented(ValidarDireccionamientoARoaming.class);
    }
}