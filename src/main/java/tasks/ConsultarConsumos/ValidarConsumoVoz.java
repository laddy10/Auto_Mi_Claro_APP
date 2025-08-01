package tasks.ConsultarConsumos;

import interactions.Click.ClickTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import static utils.Constants.*;

public class ValidarConsumoVoz implements Task {

    private static final String paso = "Validar Consumo de Voz";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Atras.irAtras(),
                WaitFor.aTime(2000),
                ClickTextoQueContengaX.elTextoContiene(CONSUMO_DE_VOZ),
                WaitFor.aTime(3000),
                ValidarTextoQueContengaX.elTextoContiene(AUN_NO_REGISTRAS_CONSUMOS_CUENTA)
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable validar() {
        return instrumented(ValidarConsumoVoz.class);
    }
}