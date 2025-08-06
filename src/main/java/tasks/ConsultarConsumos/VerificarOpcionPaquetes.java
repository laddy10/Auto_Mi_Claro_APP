package tasks.ConsultarConsumos;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class VerificarOpcionPaquetes implements Task {

    private static final String paso = "Verificar Opción Paquetes";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PAQUETES),
                WaitFor.aTime(2000),
                ValidarTextoQueContengaX.elTextoContiene(AUN_NO_HAS_ADQUIRIDO_PAQUETES)
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable verificar() {
        return instrumented(VerificarOpcionPaquetes.class);
    }
}
