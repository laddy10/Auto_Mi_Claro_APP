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

public class VerificarOpcionConsumoDelMes implements Task {

    private static final String paso = "Verificar Opción Consumo del Mes";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONSUMO_DEL_MES),
                WaitFor.aTime(2000),
                ValidarTextoQueContengaX.elTextoContiene(EN_ESTE_MES_NO_REGISTRAS_CONSUMOS)
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable verificar() {
        return instrumented(VerificarOpcionConsumoDelMes.class);
    }
}
