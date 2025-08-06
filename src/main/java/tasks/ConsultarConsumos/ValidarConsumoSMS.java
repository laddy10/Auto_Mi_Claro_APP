package tasks.ConsultarConsumos;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.ConsultarConsumosPage.OCULTAR_OPCIONES;
import static utils.Constants.*;

public class ValidarConsumoSMS implements Task {

    private static final String paso = "Validar Consumo de SMS";
//Click.on(OCULTAR_OPCIONES),
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
               // Click.on(OCULTAR_OPCIONES),
               // WaitFor.aTime(2000),
                ClickTextoQueContengaX.elTextoContiene(CONSUMO_DE_SMS),
                WaitFor.aTime(3000),
                ValidarTextoQueContengaX.elTextoContiene(AUN_NO_REGISTRAS_CONSUMOS_CUENTA_SMS)
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable validar() {
        return instrumented(ValidarConsumoSMS.class);
    }
}