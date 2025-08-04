package tasks.ConsultarConsumos;

import interactions.Click.ClickTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
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

public class ValidarAppsSinLimite implements Task {

    private static final String paso = "Validar Apps sin Límite de Consumo";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(OCULTAR_OPCIONES),
                WaitFor.aTime(2000),
                ClickTextoQueContengaX.elTextoContiene(APPS_SIN_LIMITE_CONSUMO),
                WaitFor.aTime(3000),
                ValidarTexto.validarTexto(APPS_SIN_LIMITE_CONSUMO),
                ValidarTextoQueContengaX.elTextoContiene("Has consumido")
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable validar() {
        return instrumented(ValidarAppsSinLimite.class);
    }
}
