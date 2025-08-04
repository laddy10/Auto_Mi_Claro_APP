package tasks.ConsultarConsumos;

import interactions.validations.ValidarTextoQueContengaX;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class ValidarMensajeNoRegistrasConsumos implements Task {

    private static final String paso = "Validar Mensaje No Registras Consumos";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(EN_ESTE_MES_NO_REGISTRAS_CONSUMOS)
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable validar() {
        return instrumented(ValidarMensajeNoRegistrasConsumos.class);
    }
}