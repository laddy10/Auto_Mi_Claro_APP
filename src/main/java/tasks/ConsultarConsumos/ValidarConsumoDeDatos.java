package tasks.ConsultarConsumos;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class ValidarConsumoDeDatos implements Task {

    private static final String paso = "Validar Consumo de Datos";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONSUMO_DE_DATOS),
                WaitFor.aTime(3000),
                ValidarTexto.validarTexto(CONSUMO_DE_DATOS),
                ValidarTexto.validarTexto(TU_PLAN),
                ValidarTexto.validarTexto(GB_PLAN_160),
                ValidarTexto.validarTexto(CONSUMIDO_DEL_PLAN),
                ValidarTexto.validarTexto(CONSUMIDO_1_40_GB),
                ValidarTexto.validarTexto(PAQUETES_COMPLEMENTARIOS),
                ValidarTexto.validarTexto(PAQUETES_0_00_GB),
                ValidarTextoQueContengaX.elTextoContiene("Has consumido"),
                ValidarTexto.validarTexto(ROAMING_INTERNACIONAL),
                ValidarTexto.validarTexto(PASAPORTE_AMERICA),
                ValidarTexto.validarTexto(CONSUMO_0_00_GB)
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable validar() {
        return instrumented(ValidarConsumoDeDatos.class);
    }
}