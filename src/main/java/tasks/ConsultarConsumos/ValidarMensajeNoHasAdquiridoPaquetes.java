package tasks.ConsultarConsumos;

import interactions.validations.ValidarTextoQueContengaX;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class ValidarMensajeNoHasAdquiridoPaquetes implements Task {

    private static final String paso = "Validar Mensaje No Has Adquirido Paquetes";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(AUN_NO_HAS_ADQUIRIDO_PAQUETES),
                ValidarTextoQueContengaX.elTextoContiene(HAZ_RECARGA_RENOVAR_VIGENCIA)
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable validar() {
        return instrumented(ValidarMensajeNoHasAdquiridoPaquetes.class);
    }
}