package tasks.ConsultarConsumos;

import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class ValidarInformacionDisponibleDatos implements Task {

    private static final String paso = "Validar Información Disponible en Datos";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ValidarTexto.validarTexto(CONSUMO_DE_DATOS),
                ValidarTexto.validarTexto(TU_PLAN_INF),
                ValidarTexto.validarTexto(CONSUMIDO_DEL_PLAN),
                ValidarTextoQueContengaX.elTextoContiene("Has consumido"),
                ValidarTexto.validarTexto(ROAMING_INTERNACIONAL),
                ValidarTexto.validarTexto(PASAPORTE_AMERICA),
                ValidarTexto.validarTexto(PAQUETES_ROAMING_INTERNACIONAL),
                ValidarTextoQueContengaX.elTextoContiene("41 Países de América y Europa:"),
                ValidarTextoQueContengaX.elTextoContiene("Resto del Mundo:"),
                ValidarTextoQueContengaX.elTextoContiene("Consumos adicionales Roaming Internacional"),
                ValidarTextoQueContengaX.elTextoContiene("Consumos por demanda Internacional"),
                ValidarTextoQueContengaX.elTextoContiene("Datos Consumidos:")
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable validar() {
        return instrumented(ValidarInformacionDisponibleDatos.class);
    }
}