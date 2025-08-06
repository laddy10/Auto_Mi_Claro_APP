package tasks.GestionaEquipo;

import interactions.validations.ValidarTextoQueContengaX;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class ConfirmarIngresoReporte implements Task {

    private static final String paso = "Confirmar Ingreso a Reporte";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(NUMERO_LABEL),
                ValidarTextoQueContengaX.elTextoContiene(IMEI_LABEL),
                ValidarTextoQueContengaX.elTextoContiene(MARCA_LABEL),
                ValidarTextoQueContengaX.elTextoContiene(MODELO_LABEL)
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable confirmar() {
        return instrumented(ConfirmarIngresoReporte.class);
    }
}